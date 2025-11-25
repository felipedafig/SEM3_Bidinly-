using Grpc.Net.Client;
using MainServer.WebAPI.Protos;

namespace MainServer.WebAPI.Services;

public class UserGrpcClient
{
    private readonly UserService.UserServiceClient client;
    private readonly GrpcChannel channel;

    public UserGrpcClient(IConfiguration configuration)
    {
        var dataTierAddress = configuration["DataTier:Address"] ?? "http://localhost:9093";
            
        var httpHandler = new HttpClientHandler();
            
        channel = GrpcChannel.ForAddress(dataTierAddress, new GrpcChannelOptions
        {
            HttpHandler = httpHandler,
            MaxReceiveMessageSize = null,
            MaxSendMessageSize = null
        });
            
        client = new UserService.UserServiceClient(channel);
    }
    
    public async Task<UserResponse> GetUserAsync(int id)
    {
        try
        {
            var request = new GetUserRequest { Id = id };
            var response = await client.GetUserAsync(request);
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

    public async Task<UserResponse> CreateUserAsync(string username, string password, int roleId)
    {
        try
        {
            var request = new CreateUserRequest
            {
                Username = username,
                Password = password,
                RoleId = roleId
            };
                
            UserResponse response = await client.CreateUserAsync(request);
            return response;
        }
        catch (Grpc.Core.RpcException ex)
        {
            throw new Exception($"gRPC error ({ex.StatusCode}): {ex.Status.Detail}", ex);
        }
    }
}