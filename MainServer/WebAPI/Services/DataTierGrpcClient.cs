using Grpc.Net.Client;
using MainServer.WebAPI.Protos;
using System.Net.Http;

namespace MainServer.WebAPI.Services
{
    public class DataTierGrpcClient
    {
        private readonly BidService.BidServiceClient bidClient;
        private readonly UserService.UserServiceClient userClient;
        private readonly SaleService.SaleServiceClient saleClient;
        private readonly RoleService.RoleServiceClient roleClient;
        private readonly AuthService.AuthServiceClient authClient;
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
            
            bidClient = new BidService.BidServiceClient(channel);
            userClient = new UserService.UserServiceClient(channel);
            saleClient = new SaleService.SaleServiceClient(channel);
            roleClient = new RoleService.RoleServiceClient(channel);
            authClient = new AuthService.AuthServiceClient(channel);
        }

        // Bid operations

        public async Task<GetBidsResponse> GetBidsAsync()
        {
            try
            {
                var request = new GetBidsRequest();
                var response = await bidClient.GetBidsAsync(request);
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
                var response = await bidClient.GetBidAsync(request);
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
                CreateBidRequest request = new CreateBidRequest
                {
                    BuyerId = buyerId,
                    PropertyId = propertyId,
                    Amount = amount,
                    ExpiryDateSeconds = expiryDateSeconds
                };
                BidResponse response = await bidClient.CreateBidAsync(request);
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
            var response = await bidClient.DeleteBidAsync(request);
            return response.Success;
        }

        // User operations
        public async Task<UserResponse> GetUserAsync(int id)
        {
            try
            {
                var request = new GetUserRequest { Id = id };
                var response = await userClient.GetUserAsync(request);
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
                
                UserResponse response = await userClient.CreateUserAsync(request);
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
                var response = await saleClient.GetSalesAsync(request);
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
        public async Task<GetRoleResponse> GetRoleAsync(int roleId)
        {
            try
            {
                GetRoleRequest request = new GetRoleRequest { RoleId = roleId };
                GetRoleResponse response = await roleClient.GetRoleAsync(request);
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
                var response = await authClient.AuthenticateUserAsync(request);
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
        
        public async Task<BidResponse> SetBidStatusAsync(int bidId, string status)
        {
            try
            {
                var request = new SetBidStatusRequest
                {
                    Id = bidId,
                    Status = status
                };

                var response = await bidClient.SetBidStatusAsync(request);
                return response;
            }
            catch (Grpc.Core.RpcException ex)
            {
                throw new Exception($"gRPC error ({ex.StatusCode}): {ex.Status.Detail}", ex);
            }
        }
        
        
    }
}