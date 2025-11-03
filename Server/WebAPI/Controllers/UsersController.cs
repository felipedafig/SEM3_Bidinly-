using Microsoft.AspNetCore.Mvc;
using RepositoryInterfaces;
using Server.Model;
using shared.DTOs.Users;

namespace Server.WebAPI.Controllers
{
    [ApiController]
    [Route("users")]
    public class UsersController : ControllerBase
    {
        private readonly IUserRepository userRepository;
        private readonly IRoleRepository roleRepository;

        public UsersController(IUserRepository userRepository, IRoleRepository roleRepository)
        {
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
        }

        private async Task VerifyUsernameIsAvailableAsync(string username)
        {
            var existingUser = await userRepository.GetByUsernameAsync(username);
            
            if (existingUser != null)
            {
                throw new InvalidOperationException($"Username '{username}' is already taken.");
            }
        }

        [HttpPost]
        public async Task<ActionResult<GetUserDto>> AddUser([FromBody] CreateUserDto request)
        {
            await VerifyUsernameIsAvailableAsync(request.Username ?? "");

            User user = new()
            {
                Username = request.Username,
                Password = request.Password ?? "", //must Hash password
                RoleId = 2 
            };

            User created = await userRepository.AddAsync(user);

            var role = await roleRepository.GetSingleAsync(created.RoleId);

            GetUserDto dto = new()
            {
                Id = created.Id,
                Username = created.Username,
                RoleName = role.Name
            };

            return Created($"/api/users/{dto.Id}", dto);
        }

        [HttpGet("{id:int}")]
        public async Task<ActionResult<GetUserDto>> GetSingleUser([FromRoute] int id)
        {
            User user = await userRepository.GetSingleAsync(id);
            var role = await roleRepository.GetSingleAsync(user.RoleId);

            GetUserDto responseDto = new()
            {
                Id = user.Id,
                Username = user.Username,
                RoleName = role.Name
            };

            return Ok(responseDto);
        }

        [HttpGet]
        public async Task<ActionResult<List<GetUserDto>>> GetManyUsers([FromQuery] string? username = null)
        {
            IQueryable<User> query = userRepository.GetMany();

            if (!string.IsNullOrWhiteSpace(username))
            {
                query = query.Where(u => u.Username != null && u.Username.ToLower().Contains(username.ToLower()));
            }

            List<User> filteredUsers = query.ToList();

            var tasks = filteredUsers.Select(async u =>
            {
                var role = await roleRepository.GetSingleAsync(u.RoleId);
                return new GetUserDto
                {
                    Id = u.Id,
                    Username = u.Username,
                    RoleName = role.Name
                };
            });

            var responseDtos = await Task.WhenAll(tasks);

            return Ok(responseDtos.ToList());
        }

        [HttpPut("{id:int}")]
        public async Task<ActionResult<GetUserDto>> UpdateUser([FromRoute] int id, [FromBody] UpdateUserDto request)
        {
            if (id != request.Id)
            {
                return BadRequest("Route ID does not match User ID in body.");
            }

            User existingUser = await userRepository.GetSingleAsync(id);

            if (!string.IsNullOrWhiteSpace(request.Username))
            {
                var userWithSameUsername = await userRepository.GetByUsernameAsync(request.Username);
                if (userWithSameUsername != null && userWithSameUsername.Id != id)
                {
                    throw new InvalidOperationException($"Username '{request.Username}' is already taken.");
                }
                existingUser.Username = request.Username;
            }

            await userRepository.UpdateAsync(existingUser);

            var role = await roleRepository.GetSingleAsync(existingUser.RoleId);

            GetUserDto responseDto = new()
            {
                Id = existingUser.Id,
                Username = existingUser.Username,
                RoleName = role.Name
            };

            return Ok(responseDto);
        }

        [HttpDelete("{id:int}")]
        public async Task<IActionResult> DeleteUser([FromRoute] int id)
        {
            try
            {
                await userRepository.GetSingleAsync(id);
            }
            catch (KeyNotFoundException)
            {
                return NotFound();
            }

            await userRepository.DeleteAsync(id);

            return NoContent();
        }
    }
}

