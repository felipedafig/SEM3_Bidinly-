using shared.DTOs.Bids;

namespace shared.DTOs.Bids
{

    public enum BidStatus
    {
        Pending,
        Accepted,
        Rejected,
        Expired
    }
    public class GetBidDto
    {
        public int Id { get; set; }

        public string? BuyerUsername { get; set; }//enrichment
        public string? PropertyTitle { get; set; }//erichment

        public decimal Amount { get; set; }

        public DateTime ExpiryDate { get; set; } 

        public BidStatus Status { get; set; }
    }
}
