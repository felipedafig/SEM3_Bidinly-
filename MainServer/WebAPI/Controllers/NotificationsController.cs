using Microsoft.AspNetCore.Mvc;
using MainServer.WebAPI.Services;
using MainServer.WebAPI.Protos;
using NotificationDto = Shared.DTOs.Notifications.NotificationDto;
using CreateNotificationDto = Shared.DTOs.Notifications.CreateNotificationDto;

namespace MainServer.WebAPI.Controllers
{
    [ApiController]
    [Route("notifications")]
    public class NotificationsController : ControllerBase
    {
        private readonly DataTierGrpcClient dataTierClient;

        public NotificationsController(DataTierGrpcClient dataTierClient)
        {
            this.dataTierClient = dataTierClient;
        }

        [HttpPost]
        public async Task<ActionResult<NotificationDto>> CreateNotification([FromBody] CreateNotificationDto dto)
        {
            try
            {
                NotificationResponse response = await dataTierClient.CreateNotificationAsync(
                    dto.RecipientType,
                    dto.BidId,
                    dto.PropertyId,
                    dto.Message,
                    dto.Status,
                    dto.BuyerId,
                    dto.AgentId,
                    dto.PropertyTitle);

                DateTime createdAt = DateTimeOffset.FromUnixTimeSeconds(response.CreatedAtSeconds).DateTime;

                var notificationDto = new NotificationDto
                {
                    Id = response.Id,
                    BidId = response.BidId,
                    RecipientType = response.RecipientType,
                    BuyerId = response.HasBuyerId ? response.BuyerId : null,
                    AgentId = response.HasAgentId ? response.AgentId : null,
                    PropertyId = response.PropertyId,
                    Status = response.HasStatus ? response.Status : null,
                    Message = response.Message,
                    PropertyTitle = response.HasPropertyTitle ? response.PropertyTitle : null,
                    CreatedAt = createdAt,
                    IsRead = response.IsRead
                };

                return CreatedAtAction(nameof(GetNotification), new { id = notificationDto.Id }, notificationDto);
            }
            catch (Exception ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }

        [HttpGet]
        public async Task<ActionResult<List<NotificationDto>>> GetNotifications([FromQuery] string? recipientType, [FromQuery] int? buyerId, [FromQuery] int? agentId, [FromQuery] bool? isRead)
        {
            try
            {
                GetNotificationsResponse response = await dataTierClient.GetNotificationsAsync(recipientType, buyerId, agentId, isRead);

                List<NotificationDto> notificationDtos = response.Notifications.Select(notificationResponse =>
                {
                    DateTime createdAt = DateTimeOffset.FromUnixTimeSeconds(notificationResponse.CreatedAtSeconds).DateTime;

                    return new NotificationDto
                    {
                        Id = notificationResponse.Id,
                        BidId = notificationResponse.BidId,
                        RecipientType = notificationResponse.RecipientType,
                        BuyerId = notificationResponse.HasBuyerId ? notificationResponse.BuyerId : null,
                        AgentId = notificationResponse.HasAgentId ? notificationResponse.AgentId : null,
                        PropertyId = notificationResponse.PropertyId,
                        Status = notificationResponse.HasStatus ? notificationResponse.Status : null,
                        Message = notificationResponse.Message,
                        PropertyTitle = notificationResponse.HasPropertyTitle ? notificationResponse.PropertyTitle : null,
                        CreatedAt = createdAt,
                        IsRead = notificationResponse.IsRead
                    };
                }).ToList();

                return Ok(notificationDtos);
            }
            catch (Exception ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<NotificationDto>> GetNotification(int id)
        {
            try
            {
                NotificationResponse response = await dataTierClient.GetNotificationAsync(id);

                DateTime createdAt = DateTimeOffset.FromUnixTimeSeconds(response.CreatedAtSeconds).DateTime;

                var notificationDto = new NotificationDto
                {
                    Id = response.Id,
                    BidId = response.BidId,
                    RecipientType = response.RecipientType,
                    BuyerId = response.HasBuyerId ? response.BuyerId : null,
                    AgentId = response.HasAgentId ? response.AgentId : null,
                    PropertyId = response.PropertyId,
                    Status = response.HasStatus ? response.Status : null,
                    Message = response.Message,
                    PropertyTitle = response.HasPropertyTitle ? response.PropertyTitle : null,
                    CreatedAt = createdAt,
                    IsRead = response.IsRead
                };

                return Ok(notificationDto);
            }
            catch (Grpc.Core.RpcException ex) when (ex.StatusCode == Grpc.Core.StatusCode.NotFound)
            {
                return NotFound(new { message = ex.Status.Detail });
            }
            catch (Exception ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }

        [HttpPut("{id}/read")]
        public async Task<ActionResult<NotificationDto>> MarkAsRead(int id)
        {
            try
            {
                NotificationResponse response = await dataTierClient.MarkNotificationAsReadAsync(id);

                DateTime createdAt = DateTimeOffset.FromUnixTimeSeconds(response.CreatedAtSeconds).DateTime;

                var notificationDto = new NotificationDto
                {
                    Id = response.Id,
                    BidId = response.BidId,
                    RecipientType = response.RecipientType,
                    BuyerId = response.HasBuyerId ? response.BuyerId : null,
                    AgentId = response.HasAgentId ? response.AgentId : null,
                    PropertyId = response.PropertyId,
                    Status = response.HasStatus ? response.Status : null,
                    Message = response.Message,
                    PropertyTitle = response.HasPropertyTitle ? response.PropertyTitle : null,
                    CreatedAt = createdAt,
                    IsRead = response.IsRead
                };

                return Ok(notificationDto);
            }
            catch (Grpc.Core.RpcException ex) when (ex.StatusCode == Grpc.Core.StatusCode.NotFound)
            {
                return NotFound(new { message = ex.Status.Detail });
            }
            catch (Exception ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }
    }
}

