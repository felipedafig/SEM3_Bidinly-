namespace MainServer.Model
{
    public class Bid
    {
        public int Id { get; set; }
        
        public int BuyerId { get; set; }
        public User? Buyer { get; set; }
        
        public int PropertyId { get; set; }
        public Property? Property { get; set; }

        public decimal Amount { get; set; }
        
        public DateTime ExpiryDate { get; set; }
        
        public string Status { get; set; } = "Pending"; // e.g., "Pending", "Accepted", "Rejected", "Expired"
    }
}

