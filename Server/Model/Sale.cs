namespace MainServer.Model
{
    public class Sale
    {
        public int Id { get; set; }
        
        public DateTime TimeOfSale { get; set; }
        
        public int PropertyId { get; set; }
        public Property? Property { get; set; }
        
        public int BidId { get; set; }
        public Bid? WinningBid { get; set; }
        
        public int BuyerId { get; set; }
        public User? Buyer { get; set; }
        
        public int AgentId { get; set; }
        public User? Agent { get; set; }
    }
}

