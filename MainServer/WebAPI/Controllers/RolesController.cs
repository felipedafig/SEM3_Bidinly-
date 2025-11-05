using Microsoft.AspNetCore.Mvc;
using RepositoryInterfaces;
using MainServer.Model;
using Shared.DTOs.Roles;

namespace MainServer.WebAPI.Controllers
{
    [ApiController]
    [Route("roles")]
    public class RolesController : ControllerBase
    {
        private readonly IRoleRepository roleRepository;

        public RolesController(IRoleRepository roleRepository)
        {
            this.roleRepository = roleRepository;
        }

        [HttpGet("{id:int}")]
        public async Task<ActionResult<RoleDto>> GetSingleRole([FromRoute] int id)
        {
            Role role = await roleRepository.GetSingleAsync(id);

            RoleDto responseDto = new()
            {
                Id = role.Id,
                Name = role.Name
            };

            return Ok(responseDto);
        }

    }
}

