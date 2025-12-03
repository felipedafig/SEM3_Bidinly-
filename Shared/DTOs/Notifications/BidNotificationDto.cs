namespace Shared.DTOs.Notifications
{

    public class BidNotificationDto
    {
        
        public int BidId { get; set; }
        public int BuyerId { get; set; }
        public int PropertyId { get; set; }
        public string Status { get; set; }
        public string Message { get; set; }
        public string PropertyTitle { get; set; }
    }
}
