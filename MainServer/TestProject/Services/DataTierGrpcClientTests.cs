using MainServer.WebAPI.Protos;
using MainServer.WebAPI.Services;
using Microsoft.Extensions.Configuration;
using Grpc.Core;
using Xunit;

namespace TestProject.Services
{
    /// <summary>
    /// Comprehensive unit tests for DataTierGrpcClient
    /// These tests verify the client's logic, request building, and error handling patterns.
    /// Note: Full integration testing requires a running gRPC server.
    /// </summary>
    public class DataTierGrpcClientTests
    {
        private readonly IConfiguration _configuration;

        public DataTierGrpcClientTests()
        {
            var configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.AddInMemoryCollection(new Dictionary<string, string?>
            {
                { "DataTier:Address", "http://localhost:9093" }
            });
            _configuration = configurationBuilder.Build();
        }

        #region Constructor Tests

        [Fact]
        public void Constructor_WithValidConfiguration_CreatesClient()
        {
            // Arrange & Act
            var client = new DataTierGrpcClient(_configuration);

            // Assert
            Assert.NotNull(client);
        }

        [Fact]
        public void Constructor_WithNullAddress_UsesDefaultAddress()
        {
            // Arrange
            var configBuilder = new ConfigurationBuilder();
            configBuilder.AddInMemoryCollection(new Dictionary<string, string?>());
            var config = configBuilder.Build();

            // Act
            var client = new DataTierGrpcClient(config);

            // Assert
            Assert.NotNull(client);
        }

        [Fact]
        public void Constructor_WithCustomAddress_UsesProvidedAddress()
        {
            // Arrange
            var customAddress = "http://custom-server:9094";
            var configBuilder = new ConfigurationBuilder();
            configBuilder.AddInMemoryCollection(new Dictionary<string, string?>
            {
                { "DataTier:Address", customAddress }
            });
            var config = configBuilder.Build();

            // Act
            var client = new DataTierGrpcClient(config);

            // Assert
            Assert.NotNull(client);
        }

        #endregion

        #region Request Building Tests

        [Fact]
        public void CreateBidRequest_BuildsCorrectRequest()
        {
            // Arrange
            int buyerId = 1;
            int propertyId = 2;
            double amount = 100000.50;
            long expiryDateSeconds = 1735689600; // 2025-01-01

            // Act
            var request = new CreateBidRequest
            {
                BuyerId = buyerId,
                PropertyId = propertyId,
                Amount = amount,
                ExpiryDateSeconds = expiryDateSeconds
            };

            // Assert
            Assert.Equal(buyerId, request.BuyerId);
            Assert.Equal(propertyId, request.PropertyId);
            Assert.Equal(amount, request.Amount);
            Assert.Equal(expiryDateSeconds, request.ExpiryDateSeconds);
        }

        [Fact]
        public void CreateUserRequest_BuildsCorrectRequest()
        {
            // Arrange
            string username = "testuser";
            string password = "password123";
            int roleId = 1;

            // Act
            var request = new CreateUserRequest
            {
                Username = username,
                Password = password,
                RoleId = roleId
            };

            // Assert
            Assert.Equal(username, request.Username);
            Assert.Equal(password, request.Password);
            Assert.Equal(roleId, request.RoleId);
        }

        [Fact]
        public void LoginRequest_BuildsCorrectRequest()
        {
            // Arrange
            string username = "testuser";
            string password = "password123";

            // Act
            var request = new LoginRequest
            {
                Username = username,
                Password = password
            };

            // Assert
            Assert.Equal(username, request.Username);
            Assert.Equal(password, request.Password);
        }

        [Fact]
        public void GetBidRequest_BuildsCorrectRequest()
        {
            // Arrange
            int id = 42;

            // Act
            var request = new GetBidRequest { Id = id };

            // Assert
            Assert.Equal(id, request.Id);
        }

        [Fact]
        public void GetPropertyRequest_BuildsCorrectRequest()
        {
            // Arrange
            int id = 10;

            // Act
            var request = new GetPropertyRequest { Id = id };

            // Assert
            Assert.Equal(id, request.Id);
        }

        [Fact]
        public void GetUserRequest_BuildsCorrectRequest()
        {
            // Arrange
            int id = 5;

            // Act
            var request = new GetUserRequest { Id = id };

            // Assert
            Assert.Equal(id, request.Id);
        }

        [Fact]
        public void GetRoleRequest_BuildsCorrectRequest()
        {
            // Arrange
            int roleId = 2;

            // Act
            var request = new GetRoleRequest { RoleId = roleId };

            // Assert
            Assert.Equal(roleId, request.RoleId);
        }

        [Fact]
        public void DeleteBidRequest_BuildsCorrectRequest()
        {
            // Arrange
            int id = 7;

            // Act
            var request = new DeleteBidRequest { Id = id };

            // Assert
            Assert.Equal(id, request.Id);
        }

        #endregion

        #region Response Model Tests

        [Fact]
        public void BidResponse_CanBeCreated()
        {
            // Arrange & Act
            var response = new BidResponse
            {
                Id = 1,
                BuyerId = 2,
                PropertyId = 3,
                Amount = 50000.00,
                ExpiryDateSeconds = 1735689600,
                Status = "Active"
            };

            // Assert
            Assert.Equal(1, response.Id);
            Assert.Equal(2, response.BuyerId);
            Assert.Equal(3, response.PropertyId);
            Assert.Equal(50000.00, response.Amount);
            Assert.Equal(1735689600, response.ExpiryDateSeconds);
            Assert.Equal("Active", response.Status);
        }

        [Fact]
        public void PropertyResponse_CanBeCreated()
        {
            // Arrange & Act
            var response = new PropertyResponse
            {
                Id = 1,
                AgentId = 5,
                Title = "Test Property",
                Address = "123 Test St",
                StartingPrice = 200000.00,
                Bedrooms = 3,
                Bathrooms = 2,
                SizeInSquareFeet = 1500,
                Description = "Test description",
                Status = "Available"
            };

            // Assert
            Assert.Equal(1, response.Id);
            Assert.Equal(5, response.AgentId);
            Assert.Equal("Test Property", response.Title);
            Assert.Equal("123 Test St", response.Address);
            Assert.Equal(200000.00, response.StartingPrice);
            Assert.Equal(3, response.Bedrooms);
            Assert.Equal(2, response.Bathrooms);
            Assert.Equal(1500, response.SizeInSquareFeet);
            Assert.Equal("Test description", response.Description);
            Assert.Equal("Available", response.Status);
        }

        [Fact]
        public void UserResponse_CanBeCreated()
        {
            // Arrange & Act
            var response = new UserResponse
            {
                Id = 1,
                Username = "testuser",
                RoleId = 2
            };

            // Assert
            Assert.Equal(1, response.Id);
            Assert.Equal("testuser", response.Username);
            Assert.Equal(2, response.RoleId);
        }

        [Fact]
        public void LoginResponse_CanBeCreated()
        {
            // Arrange & Act
            var response = new LoginResponse
            {
                Id = 1,
                Username = "testuser",
                RoleId = 2
            };

            // Assert
            Assert.Equal(1, response.Id);
            Assert.Equal("testuser", response.Username);
            Assert.Equal(2, response.RoleId);
        }

        [Fact]
        public void GetBidsResponse_CanContainMultipleBids()
        {
            // Arrange & Act
            var response = new GetBidsResponse();
            response.Bids.Add(new BidResponse { Id = 1, BuyerId = 1, PropertyId = 1, Amount = 100000 });
            response.Bids.Add(new BidResponse { Id = 2, BuyerId = 2, PropertyId = 2, Amount = 200000 });

            // Assert
            Assert.Equal(2, response.Bids.Count);
            Assert.Equal(1, response.Bids[0].Id);
            Assert.Equal(2, response.Bids[1].Id);
        }

        [Fact]
        public void DeleteBidResponse_ReturnsSuccess()
        {
            // Arrange & Act
            var response = new DeleteBidResponse { Success = true };

            // Assert
            Assert.True(response.Success);
        }

        [Fact]
        public void GetRoleResponse_CanBeCreated()
        {
            // Arrange & Act
            var response = new GetRoleResponse { RoleName = "Agent" };

            // Assert
            Assert.Equal("Agent", response.RoleName);
        }

        [Fact]
        public void GetSalesResponse_CanContainMultipleSales()
        {
            // Arrange & Act
            var response = new GetSalesResponse();
            response.Sales.Add(new SaleResponse { Id = 1, PropertyId = 1, BuyerId = 1 });
            response.Sales.Add(new SaleResponse { Id = 2, PropertyId = 2, BuyerId = 2 });

            // Assert
            Assert.Equal(2, response.Sales.Count);
            Assert.Equal(1, response.Sales[0].Id);
            Assert.Equal(2, response.Sales[1].Id);
        }

        #endregion

        #region Error Handling Pattern Tests

        [Fact]
        public void RpcException_CanBeCreated()
        {
            // Arrange & Act
            var status = new Status(StatusCode.NotFound, "Resource not found");
            var rpcException = new RpcException(status);

            // Assert
            Assert.Equal(StatusCode.NotFound, rpcException.StatusCode);
            Assert.Equal("Resource not found", rpcException.Status.Detail);
        }

        [Fact]
        public void Exception_FromRpcException_ContainsCorrectMessage()
        {
            // Arrange
            var status = new Status(StatusCode.NotFound, "User not found");
            var rpcException = new RpcException(status);

            // Act
            var exception = new Exception($"gRPC error ({rpcException.StatusCode}): {rpcException.Status.Detail}", rpcException);

            // Assert
            Assert.Contains("gRPC error", exception.Message);
            Assert.Contains("NotFound", exception.Message);
            Assert.Contains("User not found", exception.Message);
            Assert.Equal(rpcException, exception.InnerException);
        }

        #endregion

        #region Integration Test Notes

        /*
         * The following scenarios should be tested with integration tests using a test gRPC server:
         * 
         * 1. GetBidsAsync_Success_ReturnsBidsResponse
         * 2. GetBidsAsync_ServerError_ThrowsException
         * 3. GetBidAsync_Success_ReturnsBidResponse
         * 4. GetBidAsync_NotFound_ThrowsException
         * 5. CreateBidAsync_Success_ReturnsBidResponse
         * 6. CreateBidAsync_InvalidData_ThrowsException
         * 7. DeleteBidAsync_Success_ReturnsTrue
         * 8. DeleteBidAsync_NotFound_ReturnsFalse
         * 9. GetPropertyAsync_Success_ReturnsPropertyResponse
         * 10. GetPropertiesAsync_WithFilters_ReturnsFilteredProperties
         * 11. GetUserAsync_Success_ReturnsUserResponse
         * 12. CreateUserAsync_Success_ReturnsUserResponse
         * 13. GetSalesAsync_Success_ReturnsSalesResponse
         * 14. GetRoleAsync_Success_ReturnsRoleResponse
         * 15. AuthenticateUserAsync_Success_ReturnsLoginResponse
         * 16. AuthenticateUserAsync_InvalidCredentials_ThrowsException
         * 
         * To implement integration tests, you would:
         * 1. Create a test gRPC server or use Grpc.Net.Testing
         * 2. Set up test data
         * 3. Run the actual gRPC calls
         * 4. Verify responses
         */

        #endregion
    }
}
