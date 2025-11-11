using Microsoft.AspNetCore.Mvc;
using MainServer.WebAPI.Services;
using MainServer.WebAPI.Protos;
using PropertyDto = shared.DTOs.Properties.PropertyDto;

namespace MainServer.WebAPI.Controllers
{
    [ApiController]
    [Route("properties")]
    public class PropertiesController : ControllerBase
    {
        private readonly DataTierGrpcClient dataTierClient;

        public PropertiesController(DataTierGrpcClient dataTierClient)
        {
            this.dataTierClient = dataTierClient;
        }

        [HttpGet]
        public async Task<ActionResult<List<PropertyDto>>> GetManyProperties([FromQuery] string? status = null)
        {
            try
            {
                GetPropertiesResponse response = await dataTierClient.GetPropertiesAsync(null, status);

                // Get unique agent IDs to fetch user names
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
                PropertyResponse propertyResponse = await dataTierClient.GetPropertyAsync(id);

                // Get agent name
                UserResponse? agentUser = null;
                try
                {
                    agentUser = await dataTierClient.GetUserAsync(propertyResponse.AgentId);
                }
                catch
                {
                    // Agent not found, continue without agent name
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
    }
}
