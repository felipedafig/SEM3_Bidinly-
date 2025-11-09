using Grpc.Net.Client;
using MainServer.WebAPI.Protos;
using System.Net.Http;

namespace MainServer.WebAPI.Services
{
    public class DataTierGrpcClient
    {
        private readonly DataTierService.DataTierServiceClient client;
        private readonly GrpcChannel channel;
        private readonly ILogger<DataTierGrpcClient> logger;

        public DataTierGrpcClient(IConfiguration configuration, ILogger<DataTierGrpcClient> logger)
        {
            this.logger = logger;

            var dataTierAddress = configuration["DataTier:Address"] ?? "http://localhost:9093";
            
            logger.LogInformation("Initializing DataTierGrpcClient with address: {Address}", dataTierAddress);
            
            var httpHandler = new HttpClientHandler();
            
            channel = GrpcChannel.ForAddress(dataTierAddress, new GrpcChannelOptions
            {
                HttpHandler = httpHandler,
                MaxReceiveMessageSize = null,
                MaxSendMessageSize = null
            });
            
            client = new DataTierService.DataTierServiceClient(channel);
            logger.LogInformation("DataTierGrpcClient initialized successfully");
        }

        // Bid operations

        public async Task<GetBidsResponse> GetBidsAsync(int? propertyId = null, int? buyerId = null, string? status = null)
        {
            try
            {
                logger.LogInformation("GetBidsAsync called - propertyId: {PropertyId}, buyerId: {BuyerId}, status: {Status}", 
                    propertyId, buyerId, status);
                
                var request = new GetBidsRequest();
                if (propertyId.HasValue) request.PropertyId = propertyId.Value;
                if (buyerId.HasValue) request.BuyerId = buyerId.Value;
                if (!string.IsNullOrEmpty(status)) request.Status = status;
                
                logger.LogInformation("Sending GetBids request to DataTierServer...");
                var response = await client.GetBidsAsync(request);
                logger.LogInformation("GetBids response received with {Count} bids", response.Bids.Count);
                
                return response;
            }
            catch (Grpc.Core.RpcException ex)
            {
                logger.LogError(ex, "gRPC error in GetBidsAsync: Status={StatusCode}, Detail={Detail}", 
                    ex.StatusCode, ex.Status.Detail);
                throw new Exception($"gRPC error ({ex.StatusCode}): {ex.Status.Detail}", ex);
            }
            catch (Exception ex)
            {
                logger.LogError(ex, "Unexpected error in GetBidsAsync: {Message}", ex.Message);
                throw;
            }
        }


        public async Task<bool> DeleteBidAsync(int id)
        {
            var request = new DeleteBidRequest { Id = id };
            var response = await client.DeleteBidAsync(request);
            return response.Success;
        }

        // Property operations 
        public async Task<PropertyResponse> GetPropertyAsync(int id)
        {
            try
            {
                logger.LogInformation("GetPropertyAsync called - id: {Id}", id);
                var request = new GetPropertyRequest { Id = id };
                var response = await client.GetPropertyAsync(request);
                logger.LogInformation("GetPropertyAsync returned property with id: {Id}, title: {Title}", response.Id, response.Title);
                return response;
            }
            catch (Grpc.Core.RpcException ex)
            {
                logger.LogError(ex, "gRPC error in GetPropertyAsync: Status={StatusCode}, Detail={Detail}", 
                    ex.StatusCode, ex.Status.Detail);
                throw new Exception($"gRPC error ({ex.StatusCode}): {ex.Status.Detail}", ex);
            }
            catch (Exception ex)
            {
                logger.LogError(ex, "Unexpected error in GetPropertyAsync: {Message}", ex.Message);
                throw;
            }
        }

        public async Task<GetPropertiesResponse> GetPropertiesAsync(int? agentId = null, string? status = null)
        {
            var request = new GetPropertiesRequest();
            if (agentId.HasValue) request.AgentId = agentId.Value;
            if (!string.IsNullOrEmpty(status)) request.Status = status;
            
            return await client.GetPropertiesAsync(request);
        }

        // User operations
        public async Task<UserResponse> GetUserAsync(int id)
        {
            try
            {
                logger.LogInformation("GetUserAsync called - id: {Id}", id);
                var request = new GetUserRequest { Id = id };
                var response = await client.GetUserAsync(request);
                logger.LogInformation("GetUserAsync returned user with id: {Id}, username: {Username}", response.Id, response.Username);
                return response;
            }
            catch (Grpc.Core.RpcException ex)
            {
                logger.LogError(ex, "gRPC error in GetUserAsync: Status={StatusCode}, Detail={Detail}", 
                    ex.StatusCode, ex.Status.Detail);
                throw new Exception($"gRPC error ({ex.StatusCode}): {ex.Status.Detail}", ex);
            }
            catch (Exception ex)
            {
                logger.LogError(ex, "Unexpected error in GetUserAsync: {Message}", ex.Message);
                throw;
            }
        }

        // Sale operations

        // Role operations
    }
}