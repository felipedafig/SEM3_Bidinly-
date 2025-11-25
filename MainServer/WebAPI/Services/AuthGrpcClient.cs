using Grpc.Net.Client;
using MainServer.WebAPI.Protos;

namespace MainServer.WebAPI.Services;

public class AuthGrpcClient
{
    
    private readonly AuthService.AuthServiceClient client;
    private readonly GrpcChannel channel;

    public AuthGrpcClient(IConfiguration configuration)
    {
        var dataTierAddress = configuration["DataTier:Address"] ?? "http://localhost:9093";
            
        var httpHandler = new HttpClientHandler();
            
        channel = GrpcChannel.ForAddress(dataTierAddress, new GrpcChannelOptions
        {
            HttpHandler = httpHandler,
            MaxReceiveMessageSize = null,
            MaxSendMessageSize = null
        });
            
        client = new AuthService.AuthServiceClient(channel);
    }
    
    public async Task<LoginResponse> AuthenticateUserAsync(string username, string password)
    {
        try
        {
            var request = new MainServer.WebAPI.Protos.LoginRequest
            {
                Username = username,
                Password = password
            };
            var response = await client.AuthenticateUserAsync(request);
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