using System.ComponentModel.DataAnnotations;

namespace Shared.DTOs.Notifications
{
    public class CreateNotificationDto
    {
        [Required]
        public int BidId { get; set; }

        [Required]
        public string RecipientType { get; set; } = string.Empty; 

        public int? BuyerId { get; set; }

        public int? AgentId { get; set; }

        [Required]
        public int PropertyId { get; set; }

        public string? Status { get; set; }

        [Required]
        public string Message { get; set; } = string.Empty;

        public string? PropertyTitle { get; set; }
        
    }
}

