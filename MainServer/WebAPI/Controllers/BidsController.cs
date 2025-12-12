using Microsoft.AspNetCore.Mvc;
using MainServer.WebAPI.Services;
using MainServer.WebAPI.Protos;
using BidDto = Shared.DTOs.Bids.BidDto;
using CreateBidDto = Shared.DTOs.Bids.CreateBidDto;

namespace MainServer.WebAPI.Controllers
{
    [ApiController]
    [Route("bids")]
    public class BidsController : ControllerBase
    {
        private readonly DataTierGrpcClient dataTierClient;
        private readonly PropertyGrpcClient propertyClient;

        public BidsController(DataTierGrpcClient dataTierClient, PropertyGrpcClient propertyClient)
        {
            this.dataTierClient = dataTierClient;
            this.propertyClient = propertyClient;
        }

        [HttpGet]
        public async Task<ActionResult<List<BidDto>>> GetManyBids()
        {
            try
            {
                GetBidsResponse response = await dataTierClient.GetBidsAsync();

            var uniquePropertyIds = response.Bids.Select(b => b.PropertyId).Distinct().ToList();
            var uniqueBuyerIds = response.Bids.Select(b => b.BuyerId).Distinct().ToList();

            var propertyTasks = uniquePropertyIds.Select(async id =>
            {
                try
                {
                    var property = await propertyClient.GetPropertyAsync(id);
                    return new { Id = id, Property = (PropertyResponse?)property };
                }
                catch
                {
                    return new { Id = id, Property = (PropertyResponse?)null };
                }
            });

            var userTasks = uniqueBuyerIds.Select(async id =>
            {
                try
                {
                    var user = await dataTierClient.GetUserAsync(id);
                    return new { Id = id, User = (UserResponse?)user };
                }
                catch
                {
                    return new { Id = id, User = (UserResponse?)null };
                }
            });

            var propertyResults = await Task.WhenAll(propertyTasks);
            var userResults = await Task.WhenAll(userTasks);

            var propertyLookup = propertyResults.ToDictionary(r => r.Id, r => r.Property);
            var userLookup = userResults.ToDictionary(r => r.Id, r => r.User);

            List<BidDto> responseDtos = response.Bids.Select(bidResponse =>
            {
                DateTime expiryDate = DateTimeOffset.FromUnixTimeSeconds(bidResponse.ExpiryDateSeconds).DateTime;

                return new BidDto
                {
                    Id = bidResponse.Id,
                    PropertyId = bidResponse.PropertyId,   
                    BuyerId = bidResponse.BuyerId,  
                    PropertyTitle = propertyLookup.GetValueOrDefault(bidResponse.PropertyId)?.Title,
                    BuyerUsername = userLookup.GetValueOrDefault(bidResponse.BuyerId)?.Username,
                    Amount = (decimal)bidResponse.Amount,
                    ExpiryDate = expiryDate,
                    Status = bidResponse.Status,
                    Deal = bidResponse.Deal,
                    SignatureValid = bidResponse.SignatureValid
                };
            }).ToList();

                return Ok(responseDtos);
            }
            catch
            {
                throw; 
            }
        }
        
        [HttpGet("property/{propertyId}")]
        public async Task<ActionResult<List<BidDto>>> GetBidsByPropertyId(int propertyId)
        {
            try
            {
                GetBidsResponse response = await dataTierClient.GetBidsAsync();

                var filtered = response.Bids.Where(b => b.PropertyId == propertyId).ToList();

                if (!filtered.Any())
                    return Ok(new List<BidDto>());
                
                var buyerIds = filtered.Select(b => b.BuyerId).Distinct().ToList();
                var userTasks = buyerIds.Select(async id =>
                {
                    try { return new { Id = id, User = await dataTierClient.GetUserAsync(id) }; }
                    catch { return new { Id = id, User = (UserResponse?)null }; }
                });

                var users = await Task.WhenAll(userTasks);
                var userLookup = users.ToDictionary(u => u.Id, u => u.User);
                var property = await propertyClient.GetPropertyAsync(propertyId);
                
                var dtos = filtered.Select(b =>
                {
                    var expiry = DateTimeOffset.FromUnixTimeSeconds(b.ExpiryDateSeconds).DateTime;

                    return new BidDto
                    {
                        Id = b.Id,
                        PropertyId = b.PropertyId,
                        BuyerId = b.BuyerId,
                        BuyerUsername = userLookup[b.BuyerId]?.Username,
                        PropertyTitle = property?.Title,
                        Amount = (decimal)b.Amount,
                        ExpiryDate = expiry,
                        Status = b.Status,
                        Deal = b.Deal,
                        SignatureValid = b.SignatureValid
                    };
                }).ToList();

                return Ok(dtos);
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error retrieving bids: {ex.Message}");
            }
        }

        [HttpPost]
        public async Task<ActionResult<BidDto>> CreateBid([FromBody] CreateBidDto createBidDto)
        {
            try
            {
                long expiryDateSeconds = ((DateTimeOffset)createBidDto.ExpiryDate).ToUnixTimeSeconds();
                
                BidResponse bidResponse = await dataTierClient.CreateBidAsync(
                    createBidDto.BuyerId,
                    createBidDto.PropertyId,
                    (double)createBidDto.Amount,
                    expiryDateSeconds,
                    createBidDto.Deal 
                );


                PropertyResponse? property = null;
                UserResponse? user = null;
                
                
                    property = await propertyClient.GetPropertyAsync(bidResponse.PropertyId);
                
                    user = await dataTierClient.GetUserAsync(bidResponse.BuyerId);

               

                DateTime expiryDate = DateTimeOffset.FromUnixTimeSeconds(bidResponse.ExpiryDateSeconds).DateTime;

                BidDto bidDto = new BidDto
                {
                    Id = bidResponse.Id,
                    PropertyId = bidResponse.PropertyId,
                    BuyerId = bidResponse.BuyerId,  
                    PropertyTitle = property?.Title,
                    BuyerUsername = user?.Username,
                    Amount = (decimal)bidResponse.Amount,
                    ExpiryDate = expiryDate,
                    Status = bidResponse.Status,
                    Deal = bidResponse.Deal ,
                    SignatureValid = bidResponse.SignatureValid
                };

                return CreatedAtAction(nameof(GetManyBids), new { id = bidDto.Id }, bidDto);
            }
            catch (Exception ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }
        
        [HttpPut("{id}/accept")]
        public async Task<IActionResult> AcceptBid(int id)
        {
            // 1. Accept the bid
            await dataTierClient.SetBidStatusAsync(id, "Accepted");

            // 2. Fetch the bid explicitly (guarantees BuyerId exists)
            var bid = await dataTierClient.GetBidAsync(id);

            // 3. Fetch property title (safe, optional)
            var property = await propertyClient.GetPropertyAsync(bid.PropertyId);
            var propertyTitle = property?.Title ?? "Property";

            // 4. Create notification (safe inputs only)
            try
            {
                await dataTierClient.CreateNotificationAsync(
                    "Buyer",
                    bid.Id,
                    bid.PropertyId,
                    $"Your bid for '{propertyTitle}' was accepted.",
                    "Accepted",
                    bid.BuyerId,
                    null,
                    propertyTitle
                );
            }
            catch (Exception ex)
            {
                Console.WriteLine("Notification creation failed: " + ex.Message);
            }

            return NoContent();
        }
    

        [HttpPut("{id}/reject")]
        public async Task<IActionResult> RejectBid(int id)
        {
            try
            {
                await dataTierClient.SetBidStatusAsync(id, "Rejected");
                return NoContent();
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error rejecting bid: {ex.Message}");
            }
        }

    }
}
