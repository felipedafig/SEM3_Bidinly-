using Grpc.Net.Client;
using MainServer.WebAPI.Protos;

namespace MainServer.WebAPI.Services;

public class SaleGrpcClient
{
    private readonly SaleService.SaleServiceClient client;
    private readonly GrpcChannel channel;

    public SaleGrpcClient(IConfiguration configuration)
    {
        var dataTierAddress = configuration["DataTier:Address"] ?? "http://localhost:9093";
            
        var httpHandler = new HttpClientHandler();
            
        channel = GrpcChannel.ForAddress(dataTierAddress, new GrpcChannelOptions
        {
            HttpHandler = httpHandler,
            MaxReceiveMessageSize = null,
            MaxSendMessageSize = null
        });
            
        client = new SaleService.SaleServiceClient(channel);
    }
    
    public async Task<GetSalesResponse> GetSalesAsync()
    {
        try
        {
            var request = new GetSalesRequest();
            var response = await client.GetSalesAsync(request);
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