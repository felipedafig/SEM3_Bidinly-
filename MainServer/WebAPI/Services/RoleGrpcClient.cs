using Grpc.Net.Client;
using MainServer.WebAPI.Protos;

namespace MainServer.WebAPI.Services;

public class RoleGrpcClient
{
    private readonly RoleService.RoleServiceClient client;
    private readonly GrpcChannel channel;

    public RoleGrpcClient(IConfiguration configuration)
    {
        var dataTierAddress = configuration["DataTier:Address"] ?? "http://localhost:9093";
            
        var httpHandler = new HttpClientHandler();
            
        channel = GrpcChannel.ForAddress(dataTierAddress, new GrpcChannelOptions
        {
            HttpHandler = httpHandler,
            MaxReceiveMessageSize = null,
            MaxSendMessageSize = null
        });
            
        client = new RoleService.RoleServiceClient(channel);
    }
    
    public async Task<GetRoleResponse> GetRoleAsync(int roleId)
    {
        try
        {
            GetRoleRequest request = new GetRoleRequest { RoleId = roleId };
            GetRoleResponse response = await client.GetRoleAsync(request);
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