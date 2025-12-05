using Microsoft.AspNetCore.Mvc;
using MainServer.WebAPI.Services;
using MainServer.WebAPI.Protos;
using NotificationDto = shared.DTOs.Notifications.NotificationDto;

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

        [HttpGet]
        public async Task<ActionResult<List<NotificationDto>>> GetNotifications([FromQuery] int? buyerId, [FromQuery] bool? isRead)
        {
            try
            {
                GetNotificationsResponse response = await dataTierClient.GetNotificationsAsync(buyerId, isRead);

                List<NotificationDto> notificationDtos = response.Notifications.Select(notificationResponse =>
                {
                    DateTime createdAt = DateTimeOffset.FromUnixTimeSeconds(notificationResponse.CreatedAtSeconds).DateTime;

                    return new NotificationDto
                    {
                        Id = notificationResponse.Id,
                        BidId = notificationResponse.BidId,
                        BuyerId = notificationResponse.BuyerId,
                        PropertyId = notificationResponse.PropertyId,
                        Status = notificationResponse.Status,
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
                    BuyerId = response.BuyerId,
                    PropertyId = response.PropertyId,
                    Status = response.Status,
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
                    BuyerId = response.BuyerId,
                    PropertyId = response.PropertyId,
                    Status = response.Status,
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

