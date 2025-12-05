using System.ComponentModel.DataAnnotations;

namespace shared.DTOs.Notifications
{
    public class CreateNotificationDto
    {
        [Required]
        public int BidId { get; set; }

        [Required]
        public string RecipientType { get; set; } = string.Empty; // "Buyer" or "Agent"

        public int? BuyerId { get; set; }

        public int? AgentId { get; set; }

        [Required]
        public int PropertyId { get; set; }

        public string? Status { get; set; } // e.g., "Accepted", "Rejected"

        [Required]
        public string Message { get; set; } = string.Empty;

        public string? PropertyTitle { get; set; }

        // Id, CreatedAt, and IsRead are omitted as they are server-generated/managed.
    }
}

