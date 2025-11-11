using Microsoft.AspNetCore.Mvc;
using MainServer.WebAPI.Services;
using MainServer.WebAPI.Protos;
using BidDto = shared.DTOs.Bids.BidDto;
using CreateBidDto = Shared.DTOs.Bids.CreateBidDto;

namespace MainServer.WebAPI.Controllers
{
    [ApiController]
    [Route("bids")]
    public class BidsController : ControllerBase
    {
        private readonly DataTierGrpcClient dataTierClient;

        public BidsController(DataTierGrpcClient dataTierClient)
        {
            this.dataTierClient = dataTierClient;
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
                    var property = await dataTierClient.GetPropertyAsync(id);
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
                    PropertyTitle = propertyLookup.GetValueOrDefault(bidResponse.PropertyId)?.Title,
                    BuyerUsername = userLookup.GetValueOrDefault(bidResponse.BuyerId)?.Username,
                    Amount = (decimal)bidResponse.Amount,
                    ExpiryDate = expiryDate,
                    Status = bidResponse.Status
                };
            }).ToList();

                return Ok(responseDtos);
            }
            catch
            {
                throw; 
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
                    expiryDateSeconds
                );

                // Get property and user for enrichment
                PropertyResponse? property = null;
                UserResponse? user = null;
                
                try
                {
                    property = await dataTierClient.GetPropertyAsync(bidResponse.PropertyId);
                }
                catch { }
                
                try
                {
                    user = await dataTierClient.GetUserAsync(bidResponse.BuyerId);
                }
                catch { }

                DateTime expiryDate = DateTimeOffset.FromUnixTimeSeconds(bidResponse.ExpiryDateSeconds).DateTime;

                BidDto bidDto = new BidDto
                {
                    Id = bidResponse.Id,
                    PropertyTitle = property?.Title,
                    BuyerUsername = user?.Username,
                    Amount = (decimal)bidResponse.Amount,
                    ExpiryDate = expiryDate,
                    Status = bidResponse.Status
                };

                return CreatedAtAction(nameof(GetManyBids), new { id = bidDto.Id }, bidDto);
            }
            catch (Exception ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

    }
}
