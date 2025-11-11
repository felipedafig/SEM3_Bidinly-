using Microsoft.AspNetCore.Mvc;
using shared.DTOs.Sales;
using MainServer.WebAPI.Services;
using MainServer.WebAPI.Protos;
using BidResponse = MainServer.WebAPI.Protos.BidResponse;

namespace MainServer.WebAPI.Controllers
{
    [ApiController]
    [Route("sales")]
    public class SalesController : ControllerBase
    {
        private readonly DataTierGrpcClient dataTierClient;
        private readonly ILogger<SalesController> logger;

        public SalesController(DataTierGrpcClient dataTierClient, ILogger<SalesController> logger)
        {
            this.dataTierClient = dataTierClient;
            this.logger = logger;
        }

        [HttpGet]
        public async Task<ActionResult<List<SaleDto>>> GetManySales()
        {
            try
            {
                logger.LogInformation("GetManySales called - retrieving all sales");

                GetSalesResponse response = await dataTierClient.GetSalesAsync();
                
                logger.LogInformation("GetSalesAsync returned {Count} sales", response.Sales.Count);

                var uniquePropertyIds = response.Sales.Select(s => s.PropertyId).Distinct().ToList();
                var uniqueBuyerIds = response.Sales.Select(s => s.BuyerId).Distinct().ToList();
                var uniqueAgentIds = response.Sales.Select(s => s.AgentId).Distinct().ToList();
                var uniqueBidIds = response.Sales.Select(s => s.BidId).Distinct().ToList();

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

                var buyerTasks = uniqueBuyerIds.Select(async id =>
                {
                    try
                    {
                        var user = await dataTierClient.GetUserAsync(id);
                        logger.LogInformation("Successfully fetched buyer id: {Id}, username: {Username}", id, user?.Username);
                        return new { Id = id, User = user };
                    }
                    catch (Exception ex)
                    {
                        logger.LogWarning(ex, "Failed to fetch buyer id: {Id}, error: {Message}", id, ex.Message);
                        return new { Id = id, User = (UserResponse?)null };
                    }
                });

                var agentTasks = uniqueAgentIds.Select(async id =>
                {
                    try
                    {
                        var user = await dataTierClient.GetUserAsync(id);
                        logger.LogInformation("Successfully fetched agent id: {Id}, username: {Username}", id, user?.Username);
                        return new { Id = id, User = user };
                    }
                    catch (Exception ex)
                    {
                        logger.LogWarning(ex, "Failed to fetch agent id: {Id}, error: {Message}", id, ex.Message);
                        return new { Id = id, User = (UserResponse?)null };
                    }
                });

                var bidTasks = uniqueBidIds.Select(async id =>
                {
                    try
                    {
                        var bid = await dataTierClient.GetBidAsync(id);
                        logger.LogInformation("Successfully fetched bid id: {Id}, amount: {Amount}", id, bid?.Amount);
                        return new { Id = id, Bid = bid };
                    }
                    catch (Exception ex)
                    {
                        logger.LogWarning(ex, "Failed to fetch bid id: {Id}, error: {Message}", id, ex.Message);
                        return new { Id = id, Bid = (BidResponse?)null };
                    }
                });

                var propertyResults = await Task.WhenAll(propertyTasks);
                var buyerResults = await Task.WhenAll(buyerTasks);
                var agentResults = await Task.WhenAll(agentTasks);
                var bidResults = await Task.WhenAll(bidTasks);

                var propertyLookup = propertyResults.ToDictionary(r => r.Id, r => r.Property);
                var buyerLookup = buyerResults.ToDictionary(r => r.Id, r => r.User);
                var agentLookup = agentResults.ToDictionary(r => r.Id, r => r.User);
                var bidLookup = bidResults.ToDictionary(r => r.Id, r => r.Bid);

                List<SaleDto> responseDtos = response.Sales.Select(saleResponse =>
                {
                    DateTime timeOfSale = DateTimeOffset.FromUnixTimeSeconds(saleResponse.TimeOfSaleSeconds).DateTime;

                    return new SaleDto
                    {
                        Id = saleResponse.Id,
                        TimeOfSale = timeOfSale,
                        PropertyId = saleResponse.PropertyId,
                        PropertyTitle = propertyLookup.GetValueOrDefault(saleResponse.PropertyId)?.Title,
                        BuyerUsername = buyerLookup.GetValueOrDefault(saleResponse.BuyerId)?.Username,
                        AgentUsername = agentLookup.GetValueOrDefault(saleResponse.AgentId)?.Username,
                        WinningBidId = saleResponse.BidId,
                        FinalAmount = bidLookup.GetValueOrDefault(saleResponse.BidId) != null 
                            ? (decimal)bidLookup[saleResponse.BidId].Amount 
                            : 0
                    };
                }).ToList();

                logger.LogInformation("Successfully mapped {Count} sales to DTOs", responseDtos.Count);
                return Ok(responseDtos);
            }
            catch (Exception ex)
            {
                logger.LogError(ex, "Error in GetManySales: {Message}", ex.Message);
                throw;
            }
        }
    }
}

