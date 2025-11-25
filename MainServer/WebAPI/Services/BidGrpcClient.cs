using Grpc.Net.Client;
using MainServer.WebAPI.Protos;

namespace MainServer.WebAPI.Services;

public class BidGrpcClient
{
    private readonly BidService.BidServiceClient client;
    private readonly GrpcChannel channel;

    public BidGrpcClient(IConfiguration configuration)
    {
        var dataTierAddress = configuration["DataTier:Address"] ?? "http://localhost:9093";
            
        var httpHandler = new HttpClientHandler();
            
        channel = GrpcChannel.ForAddress(dataTierAddress, new GrpcChannelOptions
        {
            HttpHandler = httpHandler,
            MaxReceiveMessageSize = null,
            MaxSendMessageSize = null
        });
            
        client = new BidService.BidServiceClient(channel);
    }
    
    public async Task<GetBidsResponse> GetBidsAsync()
        {
            try
            {
                var request = new GetBidsRequest();
                var response = await client.GetBidsAsync(request);
                return response;
            }
            catch (Grpc.Core.RpcException ex)
            {
                throw new Exception($"gRPC error ({ex.StatusCode}): {ex.Status.Detail}", ex);
            }
            catch
            {
                throw;
            }
        }
    
        public async Task<BidResponse> GetBidAsync(int id)
        {
            try
            {
                var request = new GetBidRequest { Id = id };
                var response = await client.GetBidAsync(request);
                return response;
            }
            catch (Grpc.Core.RpcException ex)
            {
                throw new Exception($"gRPC error ({ex.StatusCode}): {ex.Status.Detail}", ex);
            }
            catch
            {
                throw;
            }
        }

        public async Task<BidResponse> CreateBidAsync(int buyerId, int propertyId, double amount, long expiryDateSeconds)
        {
            try
            {
                var request = new CreateBidRequest
                {
                    BuyerId = buyerId,
                    PropertyId = propertyId,
                    Amount = amount,
                    ExpiryDateSeconds = expiryDateSeconds
                };
                var response = await client.CreateBidAsync(request);
                return response;
            }
            catch (Grpc.Core.RpcException ex)
            {
                throw new Exception($"gRPC error ({ex.StatusCode}): {ex.Status.Detail}", ex);
            }
            catch
            {
                throw;
            }
        }

        public async Task<bool> DeleteBidAsync(int id)
        {
            var request = new DeleteBidRequest { Id = id };
            var response = await client.DeleteBidAsync(request);
            return response.Success;
        }
}