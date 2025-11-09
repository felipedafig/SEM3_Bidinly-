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
            // Get Data Tier Server address from configuration
            var dataTierAddress = configuration["DataTier:Address"] ?? "http://localhost:9093";
            
            logger.LogInformation("Initializing DataTierGrpcClient with address: {Address}", dataTierAddress);
            
            // Configure HTTP/2 for gRPC
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
        public async Task<BidResponse> CreateBidAsync(CreateBidRequest request)
        {
            try
            {
                return await client.CreateBidAsync(request);
            }
            catch (Grpc.Core.RpcException ex)
            {
                throw new Exception($"gRPC error ({ex.StatusCode}): {ex.Status.Detail}");
            }
        }

        public async Task<BidResponse> GetBidAsync(int id)
        {
            var request = new GetBidRequest { Id = id };
            return await client.GetBidAsync(request);
        }

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

        public async Task<BidResponse> UpdateBidAsync(UpdateBidRequest request)
        {
            return await client.UpdateBidAsync(request);
        }

        public async Task<bool> DeleteBidAsync(int id)
        {
            var request = new DeleteBidRequest { Id = id };
            var response = await client.DeleteBidAsync(request);
            return response.Success;
        }

        // Property operations (similar pattern)
        public async Task<PropertyResponse> CreatePropertyAsync(CreatePropertyRequest request)
        {
            return await client.CreatePropertyAsync(request);
        }

        public async Task<PropertyResponse> GetPropertyAsync(int id)
        {
            var request = new GetPropertyRequest { Id = id };
            return await client.GetPropertyAsync(request);
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
            var request = new GetUserRequest { Id = id };
            return await client.GetUserAsync(request);
        }

        // Add other property methods similarly...
    }
}