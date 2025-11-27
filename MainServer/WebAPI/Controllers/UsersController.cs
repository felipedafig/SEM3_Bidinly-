using Microsoft.AspNetCore.Mvc;
using Shared.DTOs.Users;
using MainServer.WebAPI.Services;
using MainServer.WebAPI.Protos;
using Microsoft.AspNetCore.Authorization;

namespace MainServer.WebAPI.Controllers
{
    [Authorize (Roles = "Admin, Agent")]
    [ApiController]
    [Route("users")]
    public class UsersController : ControllerBase
    {
        private readonly RoleGrpcClient roleClient;
        private readonly UserGrpcClient userClient;

        public UsersController(RoleGrpcClient roleClient, UserGrpcClient userClient)
        {
            this.roleClient = roleClient;
        }

        [HttpGet("{id}")]
        [Authorize]
        public async Task<ActionResult<UserDto>> GetUser(int id)
        {
            try
            {
                var userResponse = await userClient.GetUserAsync(id);

                string? roleName = null;
                if (userResponse.RoleId > 0)
                {
                    try
                    {
                        var roleResponse = await roleClient.GetRoleAsync(userResponse.RoleId);
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

                UserResponse userResponse = await userClient.CreateUserAsync(
                    createUserDto.Username,
                    createUserDto.Password,
                    createUserDto.RoleId
                );

                string? roleName = null;
                if (userResponse.RoleId > 0)
                {
                   
                        GetRoleResponse roleResponse = await roleClient.GetRoleAsync(userResponse.RoleId);
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

