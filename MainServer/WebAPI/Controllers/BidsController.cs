using Microsoft.AspNetCore.Mvc;
using MainServer.WebAPI.Services;
using MainServer.WebAPI.Protos;
using BidDto = shared.DTOs.Bids.BidDto;

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

        [HttpGet]
        public async Task<ActionResult<List<BidDto>>> GetManyBids()
        {
            try
            {
                logger.LogInformation("GetManyBids called - retrieving all bids");

                GetBidsResponse response = await dataTierClient.GetBidsAsync(null, null, null);
                
                logger.LogInformation("GetBidsAsync returned {Count} bids", response.Bids.Count);

            var uniquePropertyIds = response.Bids.Select(b => b.PropertyId).Distinct().ToList();
            var uniqueBuyerIds = response.Bids.Select(b => b.BuyerId).Distinct().ToList();

            var propertyTasks = uniquePropertyIds.Select(async id =>
            {
                try
                {
                    var property = await dataTierClient.GetPropertyAsync(id);
                    logger.LogInformation("Successfully fetched property id: {Id}, title: {Title}", id, property?.Title);
                    return new { Id = id, Property = property };
                }
                catch (Exception ex)
                {
                    logger.LogWarning(ex, "Failed to fetch property id: {Id}, error: {Message}", id, ex.Message);
                    return new { Id = id, Property = (PropertyResponse?)null };
                }
            });

            var userTasks = uniqueBuyerIds.Select(async id =>
            {
                try
                {
                    var user = await dataTierClient.GetUserAsync(id);
                    logger.LogInformation("Successfully fetched user id: {Id}, username: {Username}", id, user?.Username);
                    return new { Id = id, User = user };
                }
                catch (Exception ex)
                {
                    logger.LogWarning(ex, "Failed to fetch user id: {Id}, error: {Message}", id, ex.Message);
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

                logger.LogInformation("Successfully mapped {Count} bids to DTOs", responseDtos.Count);
                return Ok(responseDtos);
            }
            catch (Exception ex)
            {
                logger.LogError(ex, "Error in GetManyBids: {Message}", ex.Message);
                throw; 
            }
        }

    }
}
