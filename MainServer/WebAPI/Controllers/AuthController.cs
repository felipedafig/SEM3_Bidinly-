using MainServer.WebAPI.Services;
using Microsoft.AspNetCore.Mvc;
using Shared.DTOs.Users;
using Shared.DTOs.Auth;

namespace MainServer.WebAPI.Controllers
{
    [ApiController]
    [Route("auth")]
    public class AuthController : ControllerBase
    {
        private readonly AuthGrpcClient _authClient;
        private readonly RoleGrpcClient _roleClient;

        public AuthController(AuthGrpcClient _authClient, RoleGrpcClient _roleClient)
        {
            this._authClient = _authClient;
            this._roleClient = _roleClient;
        }

        [HttpPost("login")]
        public async Task<ActionResult<UserDto>> Login([FromBody] LoginRequestDTO request)
        {
            if (request == null || string.IsNullOrEmpty(request.Username) || string.IsNullOrEmpty(request.Password))
            {
                return BadRequest("Username and password are required");
            }

            try
            {
                var response = await _authClient.AuthenticateUserAsync(request.Username, request.Password);

                string? roleName = null;
                if (response.RoleId > 0)
                {
                    try
                    {
                        var roleResponse = await _roleClient.GetRoleAsync(response.RoleId);
                        roleName = roleResponse.RoleName;
                    }
                    catch
                    {
                        // If role lookup fails, roleName remains null
                    }
                }

                var responseDto = new UserDto
                {
                    Id = response.Id,
                    Username = response.Username,
                    RoleName = roleName
                };

                return Ok(responseDto);
            }
            catch (Exception ex)
            {
                return Unauthorized(new { message = ex.Message });
            }
        }
    }
}
