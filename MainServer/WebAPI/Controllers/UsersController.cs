using Microsoft.AspNetCore.Mvc;
using shared.DTOs.Users;
using MainServer.WebAPI.Services;
using MainServer.WebAPI.Protos;

namespace MainServer.WebAPI.Controllers
{
    [ApiController]
    [Route("users")]
    public class UsersController : ControllerBase
    {
        private readonly DataTierGrpcClient dataTierClient;

        public UsersController(DataTierGrpcClient dataTierClient)
        {
            this.dataTierClient = dataTierClient;
        }

        [HttpPost]
        public async Task<ActionResult<UserDto>> CreateUser([FromBody] CreateUserDto createUserDto)
        {
            try
            {
                if (string.IsNullOrWhiteSpace(createUserDto.Username))
                {
                    return BadRequest(new { message = "Username is required" });
                }

                if (string.IsNullOrWhiteSpace(createUserDto.Password))
                {
                    return BadRequest(new { message = "Password is required" });
                }

                UserResponse userResponse = await dataTierClient.CreateUserAsync(
                    createUserDto.Username,
                    createUserDto.Password,
                    createUserDto.RoleId
                );

                UserDto userDto = new UserDto
                {
                    Id = userResponse.Id,
                    Username = userResponse.Username,
                    RoleName = null // Role name lookup can be added later if needed
                };

                return Created($"/users/{userDto.Id}", userDto);
            }
            catch (Exception ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }
    }
}

