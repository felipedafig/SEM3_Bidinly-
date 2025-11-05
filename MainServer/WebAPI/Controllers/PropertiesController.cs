using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using RepositoryInterfaces;
using MainServer.Model;
using shared.DTOs.Properties;
using shared.DTOs.Users;

namespace MainServer.WebAPI.Controllers
{
    [ApiController]
    [Route("properties")]
    public class PropertiesController : ControllerBase
    {
        private readonly IPropertyRepository propertyRepository;
        private readonly IUserRepository userRepository;

        public PropertiesController(IPropertyRepository propertyRepository, IUserRepository userRepository)
        {
            this.propertyRepository = propertyRepository;
            this.userRepository = userRepository;
        }

        [HttpPost]
        public async Task<ActionResult<PropertyDto>> CreateProperty([FromBody] CreatePropertyDto request)
        {
           
            await userRepository.GetSingleAsync(request.AgentId); //verify

            Property property = new()
            {
                AgentId = request.AgentId,
                Title = request.Title,
                Address = request.Address,
                StartingPrice = request.StartingPrice,
                Bedrooms = request.Bedrooms,
                Bathrooms = request.Bathrooms,
                SizeInSquareFeet = request.SizeInSquareFeet,
                Description = request.Description,
                Status = "Available"
            };

            Property created = await propertyRepository.AddAsync(property);

            User agent = await userRepository.GetSingleAsync(created.AgentId);

            PropertyDto dto = new()
            {
                Id = created.Id,
                Title = created.Title,
                Address = created.Address,
                StartingPrice = created.StartingPrice,
                Bedrooms = created.Bedrooms,
                Bathrooms = created.Bathrooms,
                SizeInSquareFeet = created.SizeInSquareFeet,
                Description = created.Description,
                Status = created.Status,
                AgentName = agent.Username
            };

            return Created($"properties/{dto.Id}", dto);
        }

        [HttpGet("{id:int}")]
        public async Task<ActionResult<PropertyDto>> GetSingleProperty([FromRoute] int id)
        {
            Property property = await propertyRepository.GetSingleAsync(id);
            User agent = await userRepository.GetSingleAsync(property.AgentId);

            PropertyDto responseDto = new()
            {
                Id = property.Id,
                Title = property.Title,
                Address = property.Address,
                StartingPrice = property.StartingPrice,
                Bedrooms = property.Bedrooms,
                Bathrooms = property.Bathrooms,
                SizeInSquareFeet = property.SizeInSquareFeet,
                Description = property.Description,
                Status = property.Status,
                AgentName = agent.Username
            };

            return Ok(responseDto);
        }

        [HttpGet]
        public async Task<ActionResult<List<PropertyDto>>> GetManyProperties([FromQuery] string? title = null, [FromQuery] string? status = null)
        {
            IQueryable<Property> query = propertyRepository.GetMany()
                .Include(p => p.Agent); 

            if (!string.IsNullOrWhiteSpace(title))
            {
                query = query.Where(p => p.Title != null && p.Title.ToLower().Contains(title.ToLower()));
            }

            if (!string.IsNullOrWhiteSpace(status))
            {
                query = query.Where(p => p.Status == status);
            }

            List<Property> filteredProperties = await query.ToListAsync();

            List<PropertyDto> responseDtos = filteredProperties.Select(p => new PropertyDto
            {
                Id = p.Id,
                Title = p.Title,
                Address = p.Address,
                StartingPrice = p.StartingPrice,
                Bedrooms = p.Bedrooms,
                Bathrooms = p.Bathrooms,
                SizeInSquareFeet = p.SizeInSquareFeet,
                Description = p.Description,
                Status = p.Status,
                AgentName = p.Agent?.Username
            }).ToList();

            return Ok(responseDtos);
        }

        [HttpPut("{id:int}")]
        public async Task<ActionResult<PropertyDto>> UpdateProperty([FromRoute] int id, [FromBody] UpdatePropertyDto request)
        {
            if (id != request.Id)
            {
                return BadRequest("Route ID does not match Property ID in body.");
            }

            Property existingProperty = await propertyRepository.GetSingleAsync(id);

            
            if (existingProperty.AgentId != request.AgentId) //verify agent owns the property
            {
                return Forbid("Only the property agent can update this property.");
            }

            if (!string.IsNullOrWhiteSpace(request.Title))
            {
                existingProperty.Title = request.Title;
            }

            if (!string.IsNullOrWhiteSpace(request.Address))
            {
                existingProperty.Address = request.Address;
            }

            if (request.StartingPrice.HasValue)
            {
                existingProperty.StartingPrice = request.StartingPrice.Value;
            }

            if (request.Bedrooms.HasValue)
            {
                existingProperty.Bedrooms = request.Bedrooms.Value;
            }

            if (request.Bathrooms.HasValue)
            {
                existingProperty.Bathrooms = request.Bathrooms.Value;
            }

            if (request.SizeInSquareFeet.HasValue)
            {
                existingProperty.SizeInSquareFeet = request.SizeInSquareFeet.Value;
            }

            if (request.Description != null)
            {
                existingProperty.Description = request.Description;
            }

            await propertyRepository.UpdateAsync(existingProperty);

            User agent = await userRepository.GetSingleAsync(existingProperty.AgentId);

            PropertyDto responseDto = new()
            {
                Id = existingProperty.Id,
                Title = existingProperty.Title,
                Address = existingProperty.Address,
                StartingPrice = existingProperty.StartingPrice,
                Bedrooms = existingProperty.Bedrooms,
                Bathrooms = existingProperty.Bathrooms,
                SizeInSquareFeet = existingProperty.SizeInSquareFeet,
                Description = existingProperty.Description,
                Status = existingProperty.Status,
                AgentName = agent.Username
            };

            return Ok(responseDto);
        }

        [HttpDelete("{id:int}")]
        public async Task<IActionResult> DeleteProperty([FromRoute] int id)
        {
            await propertyRepository.GetSingleAsync(id); //verified by Global...

            await propertyRepository.DeleteAsync(id);

            return NoContent();
        }
    }
}
