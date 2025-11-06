using Grpc.Net.Client;
using SecondaryServer.Protos;
using System.Net.Http;

namespace MainServer.WebAPI.Services
{
    public class BidGrpcClient
    {
        private readonly SecondaryServer.Protos.BiddingService.BiddingServiceClient _client;
        private readonly GrpcChannel _channel;

        public BidGrpcClient()
        {
            // Configure HTTP/2 for gRPC
            var httpHandler = new HttpClientHandler();
            
            // Create channel with HTTP/2 support - connect to Java server on port 9092
            _channel = GrpcChannel.ForAddress("http://localhost:9092", new GrpcChannelOptions
            {
                HttpHandler = httpHandler,
                // Ensure HTTP/2 is used
                MaxReceiveMessageSize = null,
                MaxSendMessageSize = null
            });
            
            _client = new SecondaryServer.Protos.BiddingService.BiddingServiceClient(_channel);
        }

        public async Task<SecondaryServer.Protos.PlaceBidResponse> PlaceBidAsync(SecondaryServer.Protos.PlaceBidRequest request)
        {
            try
            {
                return await _client.PlaceBidAsync(request);
            }
            catch (Grpc.Core.RpcException ex)
            {
                // Handle gRPC-specific errors
                var detail = ex.Status.Detail ?? ex.StatusCode.ToString();
                throw new Exception($"gRPC error ({ex.StatusCode}): {detail}");
            }
            catch (System.Net.Http.HttpRequestException ex)
            {
                // Handle HTTP connection errors
                throw new Exception($"Failed to connect to SecondaryServer: {ex.Message}. Make sure SecondaryServer is running on port 9092.");
            }
            catch (Exception ex)
            {
                // Handle other errors
                throw new Exception($"Error communicating with SecondaryServer: {ex.Message}");
            }
        }
    }
}

