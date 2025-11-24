using Microsoft.AspNetCore.Mvc;
using MainServer.WebAPI.Services;
using MainServer.WebAPI.Protos;
using PropertyDto = shared.DTOs.Properties.PropertyDto;
using shared.DTOs.Properties;

namespace MainServer.WebAPI.Controllers
{
    [ApiController]
    [Route("properties")]
    public class PropertiesController : ControllerBase
    {
        private readonly PropertyGrpcClient propertyClient;
        private readonly DataTierGrpcClient dataTierClient;

        public PropertiesController(PropertyGrpcClient propertyClient, DataTierGrpcClient dataTierClient)
        {
            this.propertyClient = propertyClient;
            this.dataTierClient = dataTierClient;
        }

        [HttpGet]
        public async Task<ActionResult<List<PropertyDto>>> GetManyProperties([FromQuery] string? status = null, [FromQuery] int? agentId = null)
        {
            try
            {
                GetPropertiesResponse response = await propertyClient.GetPropertiesAsync(agentId, status);

                var uniqueAgentIds = response.Properties.Select(p => p.AgentId).Distinct().ToList();

                var agentTasks = uniqueAgentIds.Select(async id =>
                {
                    try
                    {
                        var user = await dataTierClient.GetUserAsync(id);
                        return new { Id = id, User = (UserResponse?)user };
                    }
                    catch
                    {
                        return new { Id = id, User = (UserResponse?)null };
                    }
                });

                var agentResults = await Task.WhenAll(agentTasks);
                var agentLookup = agentResults.ToDictionary(r => r.Id, r => r.User);

                List<PropertyDto> responseDtos = response.Properties.Select(propertyResponse =>
                {
                    return new PropertyDto
                    {
                        Id = propertyResponse.Id,
                        Title = propertyResponse.Title,
                        AgentName = agentLookup.GetValueOrDefault(propertyResponse.AgentId)?.Username,
                        Address = propertyResponse.Address,
                        StartingPrice = (decimal)propertyResponse.StartingPrice,
                        Bedrooms = propertyResponse.Bedrooms,
                        Bathrooms = propertyResponse.Bathrooms,
                        SizeInSquareFeet = propertyResponse.SizeInSquareFeet,
                        Description = propertyResponse.Description,
                        Status = propertyResponse.Status
                    };
                }).ToList();

                return Ok(responseDtos);
            }
            catch
            {
                throw;
            }
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<PropertyDto>> GetSingleProperty(int id)
        {
            try
            {
                PropertyResponse propertyResponse = await propertyClient.GetPropertyAsync(id);

                UserResponse? agentUser = null;
                try
                {
                    agentUser = await dataTierClient.GetUserAsync(propertyResponse.AgentId);
                }
                catch
                {
                }

                PropertyDto propertyDto = new PropertyDto
                {
                    Id = propertyResponse.Id,
                    Title = propertyResponse.Title,
                    AgentName = agentUser?.Username,
                    Address = propertyResponse.Address,
                    StartingPrice = (decimal)propertyResponse.StartingPrice,
                    Bedrooms = propertyResponse.Bedrooms,
                    Bathrooms = propertyResponse.Bathrooms,
                    SizeInSquareFeet = propertyResponse.SizeInSquareFeet,
                    Description = propertyResponse.Description,
                    Status = propertyResponse.Status
                };

                return Ok(propertyDto);
            }
            catch (Grpc.Core.RpcException ex) when (ex.StatusCode == Grpc.Core.StatusCode.NotFound)
            {
                return NotFound();
            }
            catch
            {
                throw;
            }
        }

        [HttpPost]
        public async Task<ActionResult<PropertyDto>> CreateProperty([FromBody] CreatePropertyDto createPropertyDto)
        {
            try
            {           
                CreatePropertyRequest createRequest = new CreatePropertyRequest
                {
                    Title = createPropertyDto.Title,
                    AgentId = createPropertyDto.AgentId,
                    StartingPrice = (double)createPropertyDto.StartingPrice,
                    Bedrooms = createPropertyDto.Bedrooms,
                    Bathrooms = createPropertyDto.Bathrooms,
                    SizeInSquareFeet = (int)createPropertyDto.SizeInSquareFeet,
                    Status = "Available",
                    CreationStatus = "Pending"
                };

                if (!string.IsNullOrWhiteSpace(createPropertyDto.Address))
                {
                    createRequest.Address = createPropertyDto.Address;
                }
                if (!string.IsNullOrWhiteSpace(createPropertyDto.Description))
                {
                    createRequest.Description = createPropertyDto.Description;
                }
                if (!string.IsNullOrWhiteSpace(createPropertyDto.ImageUrl))
                {
                    createRequest.ImageUrl = createPropertyDto.ImageUrl;
                }

                PropertyResponse propertyResponse = await propertyClient.CreatePropertyAsync(createRequest);

                PropertyDto createdPropertyDto = new PropertyDto
                {
                    Id = propertyResponse.Id,
                    Title = propertyResponse.Title,
                    AgentName = null,
                    Address = propertyResponse.Address,
                    StartingPrice = (decimal)propertyResponse.StartingPrice,
                    Bedrooms = propertyResponse.Bedrooms,
                    Bathrooms = propertyResponse.Bathrooms,
                    SizeInSquareFeet = propertyResponse.SizeInSquareFeet,
                    Description = propertyResponse.Description,
                    Status = propertyResponse.Status,
                    CreationStatus = propertyResponse.CreationStatus,
                    ImageUrl = propertyResponse.ImageUrl

                };

                return CreatedAtAction(nameof(GetSingleProperty), new { id = createdPropertyDto.Id }, createdPropertyDto);
            }
            catch
            {
                throw;
            }
        }
    }
}
