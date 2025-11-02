namespace Server.Model
{
    public class Sale
    {
        public int Id { get; set; }
        
        public DateTime TimeOfSale { get; set; }
        
        public int PropertyId { get; set; }
        public Property? Property { get; set; }
        
        public int WinningBidId { get; set; }
        public Bid? WinningBid { get; set; }
        
        public int BuyerId { get; set; }
        public User? Buyer { get; set; }
    }
}

