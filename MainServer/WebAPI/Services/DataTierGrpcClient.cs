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

        // Property operations 
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

        // User operations
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

        // Sale operations
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

        // Role operations
        public async Task<GetRoleResponse> GetRoleAsync(int roleId) // ajlks
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

        // Login operations
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
}