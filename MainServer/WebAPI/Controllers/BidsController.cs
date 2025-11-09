using Microsoft.AspNetCore.Mvc;
using MainServer.WebAPI.Services;
using MainServer.WebAPI.Protos;
using CreateBidDto = Shared.DTOs.Bids.CreateBidDto;
using BidDto = shared.DTOs.Bids.BidDto;
using UpdateBidDto = shared.DTOs.Bids.UpdateBidDto;

namespace MainServer.WebAPI.Controllers
{
    [ApiController]
    [Route("bids")]
    public class BidsController : ControllerBase
    {
        private readonly DataTierGrpcClient dataTierClient;
        private readonly ILogger<BidsController> logger;

        public BidsController(DataTierGrpcClient dataTierClient, ILogger<BidsController> logger)
        {
            this.dataTierClient = dataTierClient;
            this.logger = logger;
        }

        // [HttpPost]
        // public async Task<ActionResult<BidDto>> CreateBid([FromBody] CreateBidDto request)
        // {
           
        // }

        // [HttpGet("{id:int}")]
        // public async Task<ActionResult<BidDto>> GetSingleBid([FromRoute] int id)
        // {
          
        // }

        [HttpGet]
        public async Task<ActionResult<List<BidDto>>> GetManyBids()
        {
            try
            {
                logger.LogInformation("GetManyBids called - retrieving all bids");

                // Get all bids from DataTierServer via gRPC (no filtering)
                GetBidsResponse response = await dataTierClient.GetBidsAsync(null, null, null);
                
                logger.LogInformation("GetBidsAsync returned {Count} bids", response.Bids.Count);

            // Get unique property and user IDs to fetch them efficiently
            var uniquePropertyIds = response.Bids.Select(b => b.PropertyId).Distinct().ToList();
            var uniqueBuyerIds = response.Bids.Select(b => b.BuyerId).Distinct().ToList();

            // Fetch all properties and users in parallel
            var propertyTasks = uniquePropertyIds.Select(async id =>
            {
                try
                {
                    return new { Id = id, Property = await dataTierClient.GetPropertyAsync(id) };
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
                    return new { Id = id, User = await dataTierClient.GetUserAsync(id) };
                }
                catch
                {
                    return new { Id = id, User = (UserResponse?)null };
                }
            });

            var propertyResults = await Task.WhenAll(propertyTasks);
            var userResults = await Task.WhenAll(userTasks);

            // Create lookup dictionaries for O(1) access
            var propertyLookup = propertyResults.ToDictionary(r => r.Id, r => r.Property);
            var userLookup = userResults.ToDictionary(r => r.Id, r => r.User);

            // Convert gRPC BidResponse to BidDto with enrichment
            List<BidDto> responseDtos = response.Bids.Select(bidResponse =>
            {
                // Convert Unix timestamp to DateTime
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

                logger.LogInformation("Successfully mapped {Count} bids to DTOs", responseDtos.Count);
                return Ok(responseDtos);
            }
            catch (Exception ex)
            {
                logger.LogError(ex, "Error in GetManyBids: {Message}", ex.Message);
                throw; // Re-throw to let GlobalExceptionHandlerMiddleware handle it
            }
        }

        // [HttpPut("{id:int}")]
        // public async Task<ActionResult<BidDto>> UpdateBid([FromRoute] int id, [FromBody] UpdateBidDto request)
        // {
           
        // }

        // [HttpDelete("{id:int}")]
        // public async Task<IActionResult> DeleteBid([FromRoute] int id)
        // {
          
        // }
    }
}
