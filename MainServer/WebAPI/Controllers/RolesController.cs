using MainServer.WebAPI.Protos;
using MainServer.WebAPI.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Shared.DTOs.Roles;

namespace MainServer.WebAPI.Controllers
{
    [Authorize (Roles = "Admin")]
    [ApiController]
    [Route("roles")]
    public class RolesController : ControllerBase
    {

        private readonly RoleGrpcClient roleClient;

        public RolesController(RoleGrpcClient roleClient)
        {
            this.roleClient = roleClient;
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<RoleDto>> GetRole(int id)
        {
            try
            {
                GetRoleResponse response = await roleClient.GetRoleAsync(id);
                
                RoleDto roleDto = new RoleDto
                {
                    Id = id,
                    Name = response.RoleName
                };
                return Ok(roleDto);
            }
            catch (Exception ex) when (ex.InnerException is Grpc.Core.RpcException rpcEx && rpcEx.StatusCode == Grpc.Core.StatusCode.NotFound)
            {
                return NotFound(new { message = $"Role with id {id} not found" });
            }
            catch (Exception ex) when (ex.Message.Contains("NOT_FOUND") || ex.Message.Contains("not found"))
            {
                return NotFound(new { message = $"Role with id {id} not found" });
            }
            catch (Exception ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }

    }
}

