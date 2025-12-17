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
        private readonly DataTierGrpcClient dataTierClient;

        public AuthController(DataTierGrpcClient dataTierClient)
        {
            this.dataTierClient = dataTierClient;
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
                var response = await dataTierClient.AuthenticateUserAsync(request.Username, request.Password);

                string? roleName = null;
                if (response.RoleId > 0)
                {
                    try
                    {
                        var roleResponse = await dataTierClient.GetRoleAsync(response.RoleId);
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
                    RoleName = roleName,
                    Email = response.HasEmail ? response.Email : null,
                    PublicKey = response.PublicKey,
                };

                return Ok(responseDto);
            }
            catch (Exception ex)
            {
                // Extract user-friendly error message, removing any technical details
                string errorMessage = ex.Message;
                
                // If the message contains technical gRPC prefixes, extract just the meaningful part
                if (errorMessage.Contains("gRPC error") || errorMessage.Contains("StatusCode"))
                {
                    // The error message should already be clean from DataTierGrpcClient, but double-check
                    if (errorMessage.Contains("Invalid username or password"))
                    {
                        errorMessage = "Invalid username or password";
                    }
                    else if (errorMessage.Contains("deactivated"))
                    {
                        // Keep deactivation messages as-is
                        errorMessage = ex.Message;
                    }
                    else
                    {
                        errorMessage = "Invalid username or password";
                    }
                }
                
                return Unauthorized(new { message = errorMessage });
            }
        }
    }
}
