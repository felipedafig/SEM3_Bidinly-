using Grpc.Net.Client;
using MainServer.WebAPI.Protos;
using System.Net.Http;

namespace MainServer.WebAPI.Services
{
    public class PaymentGrpcClient
    {
        private readonly PaymentService.PaymentServiceClient paymentClient;
        private readonly GrpcChannel channel;

        public PaymentGrpcClient(IConfiguration configuration)
        {
            var paymentServiceAddress = configuration["PaymentService:Address"] ?? "http://localhost:9094";
            
            var httpHandler = new HttpClientHandler();
            
            channel = GrpcChannel.ForAddress(paymentServiceAddress, new GrpcChannelOptions
            {
                HttpHandler = httpHandler,
                MaxReceiveMessageSize = null,
                MaxSendMessageSize = null
            });
            
            paymentClient = new PaymentService.PaymentServiceClient(channel);
        }

        // Payment operations
        public async Task<ValidateCardResponse> ValidateCardAsync(string cardNumber, string expirationDate, string cvc, string name)
        {
            try
            {
                var request = new ValidateCardRequest
                {
                    CardNumber = cardNumber,
                    ExpirationDate = expirationDate,
                    Cvc = cvc,
                    Name = name
                };
                
                var response = await paymentClient.ValidateCardAsync(request);
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
    }
}

