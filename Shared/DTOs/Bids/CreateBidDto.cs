using System.ComponentModel.DataAnnotations;

namespace Shared.DTOs.Bids
{
    public class CreateBidDto
    {
        [Required]
        public int BuyerId { get; set; }

        [Required]
        public int PropertyId { get; set; }

        [Required]
        public decimal Amount { get; set; }

        // bid_id, expiry_date, and status are omitted as they are server-generated/managed.
    }
}
