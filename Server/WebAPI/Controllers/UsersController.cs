using Microsoft.AspNetCore.Mvc;
using RepositoryInterfaces;
using MainServer.Model;
using shared.DTOs.Users;

namespace MainServer.WebAPI.Controllers
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
            User? existingUser = await userRepository.GetByUsernameAsync(username);
            
            if (existingUser != null)
            {
                throw new InvalidOperationException($"Username '{username}' is already taken.");
            }
        }

        [HttpPost]
        public async Task<ActionResult<UserDto>> AddUser([FromBody] CreateUserDto request)
        {
            await VerifyUsernameIsAvailableAsync(request.Username ?? "");

            await roleRepository.GetSingleAsync(request.RoleId);//GlobalExceptionHandlerMiddleware will catch if RoleId is invalid

            User user = new()
            {
                Username = request.Username,
                Password = request.Password ?? "", //hash it later
                RoleId = request.RoleId
            };

            User created = await userRepository.AddAsync(user);

            Role role = await roleRepository.GetSingleAsync(created.RoleId);

            UserDto dto = new()
            {
                Id = created.Id, //set by database after creation
                Username = created.Username,
                RoleName = role.Name
            };

            return Created($"users/{dto.Id}", dto);
        }

        [HttpGet("{id:int}")]
        public async Task<ActionResult<UserDto>> GetSingleUser([FromRoute] int id)
        {
            User user = await userRepository.GetSingleAsync(id);
            Role role = await roleRepository.GetSingleAsync(user.RoleId);

            UserDto responseDto = new()
            {
                Id = user.Id,
                Username = user.Username,
                RoleName = role.Name
            };

            return Ok(responseDto);
        }

        [HttpGet]
        public async Task<ActionResult<List<UserDto>>> GetManyUsers([FromQuery] string? username = null)
        {
            IQueryable<User> query = userRepository.GetMany();

            if (!string.IsNullOrWhiteSpace(username))
            {
                query = query.Where(u => u.Username != null && u.Username.ToLower().Contains(username.ToLower()));
            }

            List<User> filteredUsers = query.ToList();

            IEnumerable<Task<UserDto>> tasks = filteredUsers.Select(async u =>
            {
                Role role = await roleRepository.GetSingleAsync(u.RoleId);
                return new UserDto
                {
                    Id = u.Id,
                    Username = u.Username,
                    RoleName = role.Name
                };
            });

            UserDto[] responseDtos = await Task.WhenAll(tasks);

            return Ok(responseDtos.ToList());
        }

        [HttpPut("{id:int}")]
        public async Task<ActionResult<UserDto>> UpdateUser([FromRoute] int id, [FromBody] UpdateUserDto request)
        {
            if (id != request.Id)
            {
                return BadRequest("Route ID does not match User ID in body.");
            }

            User existingUser = await userRepository.GetSingleAsync(id);

            await userRepository.UpdateAsync(existingUser);

            Role role = await roleRepository.GetSingleAsync(existingUser.RoleId);

            UserDto responseDto = new()
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
            
            await userRepository.GetSingleAsync(id); //verified by Global...

            await userRepository.DeleteAsync(id);

            return NoContent();
        }
    }
}

