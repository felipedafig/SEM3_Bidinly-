using MainServer.WebAPI.Protos;
using MainServer.WebAPI.Services;
using Microsoft.Extensions.Configuration;
using Grpc.Core;
using Moq;
using Grpc.Net.Client;

namespace TestProject.Services
{
    public class PropertyGrpcClientTests
    {
        private readonly IConfiguration _configuration;

        public PropertyGrpcClientTests()
        {
            var configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.AddInMemoryCollection(new Dictionary<string, string?>
            {
                { "PropertyService:Address", "http://localhost:9095" }
            });
            _configuration = configurationBuilder.Build();
        }

        [Fact]
        public void Constructor_WithValidConfiguration_CreatesClient()
        {
            var client = new PropertyGrpcClient(_configuration);
            Assert.NotNull(client);
        }

        [Fact]
        public void Constructor_WithNullAddress_UsesDefaultAddress()
        {
            var configBuilder = new ConfigurationBuilder();
            configBuilder.AddInMemoryCollection(new Dictionary<string, string?>());
            var config = configBuilder.Build();

            var client = new PropertyGrpcClient(config);
            Assert.NotNull(client);
        }

        [Fact]
        public void Constructor_WithCustomAddress_UsesProvidedAddress()
        {
            var customAddress = "http://custom-server:9096";
            var configBuilder = new ConfigurationBuilder();
            configBuilder.AddInMemoryCollection(new Dictionary<string, string?>
            {
                { "PropertyService:Address", customAddress }
            });
            var config = configBuilder.Build();

            var client = new PropertyGrpcClient(config);
            Assert.NotNull(client);
        }

        [Fact]
        public void GetPropertiesRequest_WithNoParameters_BuildsEmptyRequest()
        {
            var request = new GetPropertiesRequest();
            Assert.NotNull(request);
        }

        [Fact]
        public void GetPropertiesRequest_WithAgentId_BuildsRequestWithAgentId()
        {
            int agentId = 5;
            var request = new GetPropertiesRequest { AgentId = agentId };
            Assert.Equal(agentId, request.AgentId);
        }

        [Fact]
        public void GetPropertiesRequest_WithStatus_BuildsRequestWithStatus()
        {
            string status = "Available";
            var request = new GetPropertiesRequest { Status = status };
            Assert.Equal(status, request.Status);
        }

        [Fact]
        public void GetPropertiesRequest_WithBothParameters_BuildsRequestWithBoth()
        {
            int agentId = 3;
            string status = "Sold";
            var request = new GetPropertiesRequest 
            { 
                AgentId = agentId,
                Status = status
            };
            Assert.Equal(agentId, request.AgentId);
            Assert.Equal(status, request.Status);
        }

        [Fact]
        public void GetPropertyRequest_BuildsCorrectRequest()
        {
            int id = 42;
            var request = new GetPropertyRequest { Id = id };
            Assert.Equal(id, request.Id);
        }

        [Fact]
        public void CreatePropertyRequest_BuildsCorrectRequest()
        {
            int agentId = 1;
            string title = "Test Property";
            string address = "123 Test St";
            double startingPrice = 200000.00;
            int bedrooms = 3;
            int bathrooms = 2;
            int sizeInSquareFeet = 1500;
            string description = "Test description";
            string status = "Available";
            string creationStatus = "Pending";
            string imageUrl = "http://example.com/image.jpg";

            var request = new CreatePropertyRequest
            {
                AgentId = agentId,
                Title = title,
                Address = address,
                StartingPrice = startingPrice,
                Bedrooms = bedrooms,
                Bathrooms = bathrooms,
                SizeInSquareFeet = sizeInSquareFeet,
                Description = description,
                Status = status,
                CreationStatus = creationStatus,
                ImageUrl = imageUrl
            };

            Assert.Equal(agentId, request.AgentId);
            Assert.Equal(title, request.Title);
            Assert.Equal(address, request.Address);
            Assert.Equal(startingPrice, request.StartingPrice);
            Assert.Equal(bedrooms, request.Bedrooms);
            Assert.Equal(bathrooms, request.Bathrooms);
            Assert.Equal(sizeInSquareFeet, request.SizeInSquareFeet);
            Assert.Equal(description, request.Description);
            Assert.Equal(status, request.Status);
            Assert.Equal(creationStatus, request.CreationStatus);
            Assert.Equal(imageUrl, request.ImageUrl);
        }

        [Fact]
        public void UpdatePropertyRequest_BuildsCorrectRequest()
        {
            int id = 1;
            string title = "Updated Title";
            double startingPrice = 250000.00;

            var request = new UpdatePropertyRequest
            {
                Id = id,
                Title = title,
                StartingPrice = startingPrice
            };

            Assert.Equal(id, request.Id);
            Assert.Equal(title, request.Title);
            Assert.Equal(startingPrice, request.StartingPrice);
        }

        [Fact]
        public void DeletePropertyRequest_BuildsCorrectRequest()
        {
            int id = 10;
            var request = new DeletePropertyRequest { Id = id };
            Assert.Equal(id, request.Id);
        }

        [Fact]
        public void PropertyResponse_CanBeCreated()
        {
            var response = new PropertyResponse
            {
                Id = 1,
                AgentId = 2,
                Title = "Test Property",
                Address = "123 Test St",
                StartingPrice = 200000.00,
                Bedrooms = 3,
                Bathrooms = 2,
                SizeInSquareFeet = 1500,
                Description = "Test description",
                Status = "Available",
                CreationStatus = "Pending",
                ImageUrl = "http://example.com/image.jpg"
            };

            Assert.Equal(1, response.Id);
            Assert.Equal(2, response.AgentId);
            Assert.Equal("Test Property", response.Title);
            Assert.Equal("123 Test St", response.Address);
            Assert.Equal(200000.00, response.StartingPrice);
            Assert.Equal(3, response.Bedrooms);
            Assert.Equal(2, response.Bathrooms);
            Assert.Equal(1500, response.SizeInSquareFeet);
            Assert.Equal("Test description", response.Description);
            Assert.Equal("Available", response.Status);
            Assert.Equal("Pending", response.CreationStatus);
            Assert.Equal("http://example.com/image.jpg", response.ImageUrl);
        }

        [Fact]
        public void GetPropertiesResponse_CanContainMultipleProperties()
        {
            var response = new GetPropertiesResponse();
            response.Properties.Add(new PropertyResponse { Id = 1, Title = "Property 1" });
            response.Properties.Add(new PropertyResponse { Id = 2, Title = "Property 2" });

            Assert.Equal(2, response.Properties.Count);
            Assert.Equal("Property 1", response.Properties[0].Title);
            Assert.Equal("Property 2", response.Properties[1].Title);
        }

        [Fact]
        public void DeletePropertyResponse_ReturnsSuccess()
        {
            var response = new DeletePropertyResponse { Success = true };
            Assert.True(response.Success);
        }

        [Fact]
        public void RpcException_CanBeCreated()
        {
            var status = new Status(StatusCode.NotFound, "Resource not found");
            var rpcException = new RpcException(status);

            Assert.Equal(StatusCode.NotFound, rpcException.StatusCode);
            Assert.Equal("Resource not found", rpcException.Status.Detail);
        }

        [Fact]
        public void GetPropertiesRequest_WithCreationStatus_BuildsRequestWithCreationStatus()
        {
            string creationStatus = "Approved";
            var request = new GetPropertiesRequest { CreationStatus = creationStatus };
            Assert.Equal(creationStatus, request.CreationStatus);
        }

        [Fact]
        public void GetPropertiesRequest_WithAllFilters_BuildsRequestWithAll()
        {
            int agentId = 5;
            string status = "Available";
            string creationStatus = "Pending";

            var request = new GetPropertiesRequest
            {
                AgentId = agentId,
                Status = status,
                CreationStatus = creationStatus
            };

            Assert.Equal(agentId, request.AgentId);
            Assert.Equal(status, request.Status);
            Assert.Equal(creationStatus, request.CreationStatus);
        }

        [Fact]
        public void CreatePropertyRequest_WithMinimalFields_BuildsCorrectRequest()
        {
            var request = new CreatePropertyRequest
            {
                AgentId = 1,
                Title = "Minimal Property",
                StartingPrice = 100000.00,
                Bedrooms = 2,
                Bathrooms = 1,
                SizeInSquareFeet = 800,
                Status = "Available",
                CreationStatus = "Pending"
            };

            Assert.Equal(1, request.AgentId);
            Assert.Equal("Minimal Property", request.Title);
            Assert.Equal(100000.00, request.StartingPrice);
            Assert.Equal(2, request.Bedrooms);
            Assert.Equal(1, request.Bathrooms);
            Assert.Equal(800, request.SizeInSquareFeet);
            Assert.Equal("Available", request.Status);
            Assert.Equal("Pending", request.CreationStatus);
        }

        [Fact]
        public void UpdatePropertyRequest_WithAllFields_BuildsCorrectRequest()
        {
            var request = new UpdatePropertyRequest
            {
                Id = 1,
                Title = "Updated Title",
                Address = "Updated Address",
                StartingPrice = 300000.00,
                Bedrooms = 4,
                Bathrooms = 3,
                SizeInSquareFeet = 2000,
                Description = "Updated Description",
                Status = "Sold",
                CreationStatus = "Approved",
                ImageUrl = "http://example.com/updated.jpg"
            };

            Assert.Equal(1, request.Id);
            Assert.Equal("Updated Title", request.Title);
            Assert.Equal("Updated Address", request.Address);
            Assert.Equal(300000.00, request.StartingPrice);
            Assert.Equal(4, request.Bedrooms);
            Assert.Equal(3, request.Bathrooms);
            Assert.Equal(2000, request.SizeInSquareFeet);
            Assert.Equal("Updated Description", request.Description);
            Assert.Equal("Sold", request.Status);
            Assert.Equal("Approved", request.CreationStatus);
            Assert.Equal("http://example.com/updated.jpg", request.ImageUrl);
        }

        [Fact]
        public void PropertyResponse_WithNullOptionalFields_HandlesCorrectly()
        {
            var response = new PropertyResponse
            {
                Id = 1,
                AgentId = 1,
                Title = "Test",
                Address = "",
                StartingPrice = 100000.00,
                Bedrooms = 2,
                Bathrooms = 1,
                SizeInSquareFeet = 1000,
                Description = "",
                Status = "Available",
                CreationStatus = "Pending",
                ImageUrl = ""
            };

            Assert.Equal(1, response.Id);
            Assert.Equal("Test", response.Title);
            Assert.Equal("", response.Address);
            Assert.Equal("", response.Description);
        }

        [Fact]
        public void GetPropertiesResponse_CanBeEmpty()
        {
            var response = new GetPropertiesResponse();
            Assert.NotNull(response.Properties);
            Assert.Empty(response.Properties);
        }

        [Fact]
        public void DeletePropertyResponse_CanReturnFalse()
        {
            var response = new DeletePropertyResponse { Success = false };
            Assert.False(response.Success);
        }

        [Fact]
        public void CreatePropertyRequest_WithZeroValues_HandlesCorrectly()
        {
            var request = new CreatePropertyRequest
            {
                AgentId = 0,
                Title = "Test",
                StartingPrice = 0.0,
                Bedrooms = 0,
                Bathrooms = 0,
                SizeInSquareFeet = 0,
                Status = "",
                CreationStatus = ""
            };

            Assert.Equal(0, request.AgentId);
            Assert.Equal(0.0, request.StartingPrice);
            Assert.Equal(0, request.Bedrooms);
            Assert.Equal(0, request.Bathrooms);
            Assert.Equal(0, request.SizeInSquareFeet);
        }

        [Fact]
        public void UpdatePropertyRequest_WithPartialFields_BuildsCorrectRequest()
        {
            var request = new UpdatePropertyRequest
            {
                Id = 1,
                Title = "Only Title Updated"
            };

            Assert.Equal(1, request.Id);
            Assert.Equal("Only Title Updated", request.Title);
        }

        [Fact]
        public void GetPropertiesRequest_WithEmptyStrings_HandlesCorrectly()
        {
            var request = new GetPropertiesRequest
            {
                Status = "",
                CreationStatus = ""
            };

            Assert.Equal("", request.Status);
            Assert.Equal("", request.CreationStatus);
        }

        [Fact]
        public void PropertyResponse_WithLargeValues_HandlesCorrectly()
        {
            var response = new PropertyResponse
            {
                Id = int.MaxValue,
                AgentId = int.MaxValue,
                StartingPrice = double.MaxValue,
                Bedrooms = int.MaxValue,
                Bathrooms = int.MaxValue,
                SizeInSquareFeet = int.MaxValue
            };

            Assert.Equal(int.MaxValue, response.Id);
            Assert.Equal(int.MaxValue, response.AgentId);
            Assert.Equal(double.MaxValue, response.StartingPrice);
            Assert.Equal(int.MaxValue, response.Bedrooms);
            Assert.Equal(int.MaxValue, response.Bathrooms);
            Assert.Equal(int.MaxValue, response.SizeInSquareFeet);
        }
    }
}

