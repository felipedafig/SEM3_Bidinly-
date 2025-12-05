namespace shared.DTOs.Notifications
{
    public class NotificationDto
    {
        public int Id { get; set; }
        
        public int BidId { get; set; }
        
        public int BuyerId { get; set; }
        
        public int PropertyId { get; set; }
        
        public string Status { get; set; } = string.Empty; 
        
        public string Message { get; set; } = string.Empty;
        
        public string? PropertyTitle { get; set; }
        
        public DateTime CreatedAt { get; set; }
        
        public bool IsRead { get; set; }
    }
}

