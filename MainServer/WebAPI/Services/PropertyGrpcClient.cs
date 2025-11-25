using Grpc.Net.Client;
using MainServer.WebAPI.Protos;

namespace MainServer.WebAPI.Services;

public class PropertyGrpcClient
{
    private readonly PropertyService.PropertyServiceClient client;
    private readonly GrpcChannel channel;

    public PropertyGrpcClient(IConfiguration configuration)
    {
        var dataTierAddress = configuration["DataTier:Address"] ?? "http://localhost:9093";
            
        var httpHandler = new HttpClientHandler();
            
        channel = GrpcChannel.ForAddress(dataTierAddress, new GrpcChannelOptions
        {
            HttpHandler = httpHandler,
            MaxReceiveMessageSize = null,
            MaxSendMessageSize = null
        });
            
        client = new PropertyService.PropertyServiceClient(channel);
    }
    
    public async Task<PropertyResponse> GetPropertyAsync(int id)
    {
        try
        {
            var request = new GetPropertyRequest { Id = id };
            var response = await client.GetPropertyAsync(request);
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

    public async Task<GetPropertiesResponse> GetPropertiesAsync(int? agentId = null, string? status = null)
    {
        var request = new GetPropertiesRequest();
        if (agentId.HasValue) request.AgentId = agentId.Value;
        if (!string.IsNullOrEmpty(status)) request.Status = status;
            
        return await client.GetPropertiesAsync(request);
    }
}