using MainServer.WebAPI.Services;
using MainServer.WebAPI.Protos;
using Microsoft.AspNetCore.Mvc;
using shared.DTOs.Users;
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

                var responseDto = new UserDto
                {
                    Id = response.Id,
                    Username = response.Username,
                    RoleName = null // Role name lookup can be added later if needed (roleId: {response.RoleId})
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