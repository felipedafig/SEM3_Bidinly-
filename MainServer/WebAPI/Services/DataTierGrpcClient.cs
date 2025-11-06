using Grpc.Net.Client;
using MainServer.WebAPI.Protos;
using System.Net.Http;

namespace MainServer.WebAPI.Services
{
    public class DataTierGrpcClient
    {
        private readonly DataTierService.DataTierServiceClient client;
        private readonly GrpcChannel channel;

        public DataTierGrpcClient(IConfiguration configuration)
        {
            var dataTierAddress = configuration["DataTier:Address"] ?? "http://localhost:9093";
            
            var httpHandler = new HttpClientHandler();
            
            channel = GrpcChannel.ForAddress(dataTierAddress, new GrpcChannelOptions
            {
                HttpHandler = httpHandler,
                MaxReceiveMessageSize = null,
                MaxSendMessageSize = null
            });
            
            client = new DataTierService.DataTierServiceClient(channel);
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
            var request = new GetBidsRequest();
            if (propertyId.HasValue) request.PropertyId = propertyId.Value;
            if (buyerId.HasValue) request.BuyerId = buyerId.Value;
            if (!string.IsNullOrEmpty(status)) request.Status = status;
            
            return await client.GetBidsAsync(request);
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

        // Add other property methods similarly...
    }
}