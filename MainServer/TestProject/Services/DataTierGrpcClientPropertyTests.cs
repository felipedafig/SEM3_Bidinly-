using MainServer.WebAPI.Protos;
using MainServer.WebAPI.Services;
using Grpc.Core;
using Xunit;

namespace TestProject.Services
{
    /// <summary>
    /// Comprehensive unit tests for DataTierGrpcClient property creation operations.
    /// These tests follow TDD (Test-Driven Development) - they will fail until CreatePropertyAsync is implemented.
    /// </summary>
    public class DataTierGrpcClientPropertyTests : DataTierGrpcClientTestBase
    {
        private readonly DataTierGrpcClient _client;

        public DataTierGrpcClientPropertyTests()
        {
            _client = CreateClient();
        }

        #region Success Scenarios

        [Fact]
        public async Task CreatePropertyAsync_Success_WithAllFields_ReturnsPropertyResponse()
        {
            // Arrange
            int agentId = 1;
            string title = "Luxury Downtown Apartment";
            string address = "123 Main Street, City Center";
            double startingPrice = 450000.00;
            int bedrooms = 2;
            int bathrooms = 2;
            int sizeInSquareFeet = 1200;
            string description = "Beautiful apartment with stunning city views";
            string status = "Available";

            // Act
            PropertyResponse response = await _client.CreatePropertyAsync(
                agentId, title, address, startingPrice, bedrooms, bathrooms, 
                sizeInSquareFeet, description, status);

            // Assert
            Assert.NotNull(response);
            Assert.True(response.Id > 0);
            Assert.Equal(agentId, response.AgentId);
            Assert.Equal(title, response.Title);
            Assert.Equal(address, response.Address);
            Assert.Equal(startingPrice, response.StartingPrice);
            Assert.Equal(bedrooms, response.Bedrooms);
            Assert.Equal(bathrooms, response.Bathrooms);
            Assert.Equal(sizeInSquareFeet, response.SizeInSquareFeet);
            Assert.Equal(description, response.Description);
            Assert.Equal(status, response.Status);
        }

        [Fact]
        public async Task CreatePropertyAsync_Success_WithMinimalRequiredFields_ReturnsPropertyResponse()
        {
            // Arrange
            int agentId = 1;
            string title = "Minimal Property";
            double startingPrice = 100000.00;
            int bedrooms = 1;
            int bathrooms = 1;
            int sizeInSquareFeet = 500;

            // Act
            PropertyResponse response = await _client.CreatePropertyAsync(
                agentId, title, null, startingPrice, bedrooms, bathrooms, 
                sizeInSquareFeet, null, null);

            // Assert
            Assert.NotNull(response);
            Assert.True(response.Id > 0);
            Assert.Equal(agentId, response.AgentId);
            Assert.Equal(title, response.Title);
            Assert.Equal(startingPrice, response.StartingPrice);
            Assert.Equal(bedrooms, response.Bedrooms);
            Assert.Equal(bathrooms, response.Bathrooms);
            Assert.Equal(sizeInSquareFeet, response.SizeInSquareFeet);
        }

        [Fact]
        public async Task CreatePropertyAsync_Success_WithoutStatus_SetsDefaultStatusToAvailable()
        {
            // Arrange
            int agentId = 1;
            string title = "New Property";
            double startingPrice = 200000.00;
            int bedrooms = 3;
            int bathrooms = 2;
            int sizeInSquareFeet = 1500;

            // Act
            PropertyResponse response = await _client.CreatePropertyAsync(
                agentId, title, "Test Address", startingPrice, bedrooms, bathrooms, 
                sizeInSquareFeet, "Description", null);

            // Assert
            Assert.NotNull(response);
            Assert.Equal("Available", response.Status);
        }

        #endregion

        #region Validation Error Scenarios

        [Fact]
        public async Task CreatePropertyAsync_InvalidAgentId_ThrowsRpcException()
        {
            // Arrange
            int invalidAgentId = 999;
            string title = "Test Property";
            double startingPrice = 100000.00;
            int bedrooms = 2;
            int bathrooms = 1;
            int sizeInSquareFeet = 800;

            // Act & Assert
            var exception = await Assert.ThrowsAsync<Exception>(async () =>
                await _client.CreatePropertyAsync(
                    invalidAgentId, title, "Address", startingPrice, bedrooms, bathrooms, 
                    sizeInSquareFeet, "Description", "Available"));

            Assert.Contains("gRPC error", exception.Message);
            Assert.Contains("NotFound", exception.Message);
        }

        [Fact]
        public async Task CreatePropertyAsync_EmptyTitle_ThrowsRpcException()
        {
            // Arrange
            int agentId = 1;
            string emptyTitle = "";
            double startingPrice = 100000.00;
            int bedrooms = 2;
            int bathrooms = 1;
            int sizeInSquareFeet = 800;

            // Act & Assert
            var exception = await Assert.ThrowsAsync<Exception>(async () =>
                await _client.CreatePropertyAsync(
                    agentId, emptyTitle, "Address", startingPrice, bedrooms, bathrooms, 
                    sizeInSquareFeet, "Description", "Available"));

            Assert.Contains("gRPC error", exception.Message);
            Assert.Contains("InvalidArgument", exception.Message);
        }

        [Fact]
        public async Task CreatePropertyAsync_NullTitle_ThrowsRpcException()
        {
            // Arrange
            int agentId = 1;
            string? nullTitle = null;
            double startingPrice = 100000.00;
            int bedrooms = 2;
            int bathrooms = 1;
            int sizeInSquareFeet = 800;

            // Act & Assert
            var exception = await Assert.ThrowsAsync<Exception>(async () =>
                await _client.CreatePropertyAsync(
                    agentId, nullTitle!, "Address", startingPrice, bedrooms, bathrooms, 
                    sizeInSquareFeet, "Description", "Available"));

            Assert.Contains("gRPC error", exception.Message);
            Assert.Contains("InvalidArgument", exception.Message);
        }

        [Fact]
        public async Task CreatePropertyAsync_ZeroStartingPrice_ThrowsRpcException()
        {
            // Arrange
            int agentId = 1;
            string title = "Test Property";
            double zeroPrice = 0.0;
            int bedrooms = 2;
            int bathrooms = 1;
            int sizeInSquareFeet = 800;

            // Act & Assert
            var exception = await Assert.ThrowsAsync<Exception>(async () =>
                await _client.CreatePropertyAsync(
                    agentId, title, "Address", zeroPrice, bedrooms, bathrooms, 
                    sizeInSquareFeet, "Description", "Available"));

            Assert.Contains("gRPC error", exception.Message);
            Assert.Contains("InvalidArgument", exception.Message);
        }

        [Fact]
        public async Task CreatePropertyAsync_NegativeStartingPrice_ThrowsRpcException()
        {
            // Arrange
            int agentId = 1;
            string title = "Test Property";
            double negativePrice = -1000.0;
            int bedrooms = 2;
            int bathrooms = 1;
            int sizeInSquareFeet = 800;

            // Act & Assert
            var exception = await Assert.ThrowsAsync<Exception>(async () =>
                await _client.CreatePropertyAsync(
                    agentId, title, "Address", negativePrice, bedrooms, bathrooms, 
                    sizeInSquareFeet, "Description", "Available"));

            Assert.Contains("gRPC error", exception.Message);
            Assert.Contains("InvalidArgument", exception.Message);
        }

        [Fact]
        public async Task CreatePropertyAsync_ZeroBedrooms_ThrowsRpcException()
        {
            // Arrange
            int agentId = 1;
            string title = "Test Property";
            double startingPrice = 100000.00;
            int zeroBedrooms = 0;
            int bathrooms = 1;
            int sizeInSquareFeet = 800;

            // Act & Assert
            var exception = await Assert.ThrowsAsync<Exception>(async () =>
                await _client.CreatePropertyAsync(
                    agentId, title, "Address", startingPrice, zeroBedrooms, bathrooms, 
                    sizeInSquareFeet, "Description", "Available"));

            Assert.Contains("gRPC error", exception.Message);
            Assert.Contains("InvalidArgument", exception.Message);
        }

        [Fact]
        public async Task CreatePropertyAsync_NegativeBedrooms_ThrowsRpcException()
        {
            // Arrange
            int agentId = 1;
            string title = "Test Property";
            double startingPrice = 100000.00;
            int negativeBedrooms = -1;
            int bathrooms = 1;
            int sizeInSquareFeet = 800;

            // Act & Assert
            var exception = await Assert.ThrowsAsync<Exception>(async () =>
                await _client.CreatePropertyAsync(
                    agentId, title, "Address", startingPrice, negativeBedrooms, bathrooms, 
                    sizeInSquareFeet, "Description", "Available"));

            Assert.Contains("gRPC error", exception.Message);
            Assert.Contains("InvalidArgument", exception.Message);
        }

        [Fact]
        public async Task CreatePropertyAsync_ZeroBathrooms_ThrowsRpcException()
        {
            // Arrange
            int agentId = 1;
            string title = "Test Property";
            double startingPrice = 100000.00;
            int bedrooms = 2;
            int zeroBathrooms = 0;
            int sizeInSquareFeet = 800;

            // Act & Assert
            var exception = await Assert.ThrowsAsync<Exception>(async () =>
                await _client.CreatePropertyAsync(
                    agentId, title, "Address", startingPrice, bedrooms, zeroBathrooms, 
                    sizeInSquareFeet, "Description", "Available"));

            Assert.Contains("gRPC error", exception.Message);
            Assert.Contains("InvalidArgument", exception.Message);
        }

        [Fact]
        public async Task CreatePropertyAsync_NegativeBathrooms_ThrowsRpcException()
        {
            // Arrange
            int agentId = 1;
            string title = "Test Property";
            double startingPrice = 100000.00;
            int bedrooms = 2;
            int negativeBathrooms = -1;
            int sizeInSquareFeet = 800;

            // Act & Assert
            var exception = await Assert.ThrowsAsync<Exception>(async () =>
                await _client.CreatePropertyAsync(
                    agentId, title, "Address", startingPrice, bedrooms, negativeBathrooms, 
                    sizeInSquareFeet, "Description", "Available"));

            Assert.Contains("gRPC error", exception.Message);
            Assert.Contains("InvalidArgument", exception.Message);
        }

        [Fact]
        public async Task CreatePropertyAsync_ZeroSizeInSquareFeet_ThrowsRpcException()
        {
            // Arrange
            int agentId = 1;
            string title = "Test Property";
            double startingPrice = 100000.00;
            int bedrooms = 2;
            int bathrooms = 1;
            int zeroSize = 0;

            // Act & Assert
            var exception = await Assert.ThrowsAsync<Exception>(async () =>
                await _client.CreatePropertyAsync(
                    agentId, title, "Address", startingPrice, bedrooms, bathrooms, 
                    zeroSize, "Description", "Available"));

            Assert.Contains("gRPC error", exception.Message);
            Assert.Contains("InvalidArgument", exception.Message);
        }

        [Fact]
        public async Task CreatePropertyAsync_NegativeSizeInSquareFeet_ThrowsRpcException()
        {
            // Arrange
            int agentId = 1;
            string title = "Test Property";
            double startingPrice = 100000.00;
            int bedrooms = 2;
            int bathrooms = 1;
            int negativeSize = -100;

            // Act & Assert
            var exception = await Assert.ThrowsAsync<Exception>(async () =>
                await _client.CreatePropertyAsync(
                    agentId, title, "Address", startingPrice, bedrooms, bathrooms, 
                    negativeSize, "Description", "Available"));

            Assert.Contains("gRPC error", exception.Message);
            Assert.Contains("InvalidArgument", exception.Message);
        }

        #endregion

        #region Network/Server Error Scenarios

        [Fact]
        public async Task CreatePropertyAsync_ServerUnavailable_ThrowsException()
        {
            // Arrange
            // This test would require a client configured to point to an unavailable server
            // For now, we test that exceptions are properly wrapped
            int agentId = 1;
            string title = "Test Property";
            double startingPrice = 100000.00;
            int bedrooms = 2;
            int bathrooms = 1;
            int sizeInSquareFeet = 800;

            // Act & Assert
            // This will fail if server is down, but we're testing error handling
            var exception = await Assert.ThrowsAnyAsync<Exception>(async () =>
                await _client.CreatePropertyAsync(
                    agentId, title, "Address", startingPrice, bedrooms, bathrooms, 
                    sizeInSquareFeet, "Description", "Available"));

            Assert.NotNull(exception);
        }

        #endregion

        #region Edge Cases

        [Fact]
        public async Task CreatePropertyAsync_WithVeryLongTitle_ReturnsPropertyResponse()
        {
            // Arrange
            int agentId = 1;
            string longTitle = new string('A', 500); // Very long title
            double startingPrice = 100000.00;
            int bedrooms = 2;
            int bathrooms = 1;
            int sizeInSquareFeet = 800;

            // Act
            PropertyResponse response = await _client.CreatePropertyAsync(
                agentId, longTitle, "Address", startingPrice, bedrooms, bathrooms, 
                sizeInSquareFeet, "Description", "Available");

            // Assert
            Assert.NotNull(response);
            Assert.Equal(longTitle, response.Title);
        }

        [Fact]
        public async Task CreatePropertyAsync_WithVeryHighPrice_ReturnsPropertyResponse()
        {
            // Arrange
            int agentId = 1;
            string title = "Luxury Property";
            double veryHighPrice = 999999999.99;
            int bedrooms = 5;
            int bathrooms = 4;
            int sizeInSquareFeet = 5000;

            // Act
            PropertyResponse response = await _client.CreatePropertyAsync(
                agentId, title, "Address", veryHighPrice, bedrooms, bathrooms, 
                sizeInSquareFeet, "Description", "Available");

            // Assert
            Assert.NotNull(response);
            Assert.Equal(veryHighPrice, response.StartingPrice);
        }

        [Fact]
        public async Task CreatePropertyAsync_WithSpecialCharactersInTitle_ReturnsPropertyResponse()
        {
            // Arrange
            int agentId = 1;
            string titleWithSpecialChars = "Property @ #123! $%^&*()";
            double startingPrice = 100000.00;
            int bedrooms = 2;
            int bathrooms = 1;
            int sizeInSquareFeet = 800;

            // Act
            PropertyResponse response = await _client.CreatePropertyAsync(
                agentId, titleWithSpecialChars, "Address", startingPrice, bedrooms, bathrooms, 
                sizeInSquareFeet, "Description", "Available");

            // Assert
            Assert.NotNull(response);
            Assert.Equal(titleWithSpecialChars, response.Title);
        }

        [Fact]
        public async Task CreatePropertyAsync_WithCustomStatus_ReturnsPropertyResponseWithCustomStatus()
        {
            // Arrange
            int agentId = 1;
            string title = "Sold Property";
            double startingPrice = 100000.00;
            int bedrooms = 2;
            int bathrooms = 1;
            int sizeInSquareFeet = 800;
            string customStatus = "Sold";

            // Act
            PropertyResponse response = await _client.CreatePropertyAsync(
                agentId, title, "Address", startingPrice, bedrooms, bathrooms, 
                sizeInSquareFeet, "Description", customStatus);

            // Assert
            Assert.NotNull(response);
            Assert.Equal(customStatus, response.Status);
        }

        #endregion
    }
}

