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

        [HttpGet("{id}")]
        public async Task<ActionResult<UserDto>> GetUser(int id)
        {
            try
            {
                var userResponse = await dataTierClient.GetUserAsync(id);

                string? roleName = null;
                if (userResponse.RoleId > 0)
                {
                    try
                    {
                        var roleResponse = await dataTierClient.GetRoleAsync(userResponse.RoleId);
                        roleName = roleResponse.RoleName;
                    }
                    catch
                    {
                        // If role lookup fails, roleName remains null
                    }
                }

                var userDto = new UserDto
                {
                    Id = userResponse.Id,
                    Username = userResponse.Username,
                    RoleName = roleName
                };

                return Ok(userDto);
            }
            catch (Exception ex) when (ex.InnerException is Grpc.Core.RpcException rpcEx && rpcEx.StatusCode == Grpc.Core.StatusCode.NotFound)
            {
                return NotFound(new { message = $"User with id {id} not found" });
            }
            catch (Exception ex) when (ex.Message.Contains("NOT_FOUND") || ex.Message.Contains("not found"))
            {
                return NotFound(new { message = $"User with id {id} not found" });
            }
            catch (Exception ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
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

                string? roleName = null;
                if (userResponse.RoleId > 0)
                {
                   
                        GetRoleResponse roleResponse = await dataTierClient.GetRoleAsync(userResponse.RoleId);
                        roleName = roleResponse.RoleName;
                    
                }

                UserDto userDto = new UserDto
                {
                    Id = userResponse.Id,
                    Username = userResponse.Username,
                    RoleName = roleName
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

