using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using RepositoryInterfaces;
using MainServer.Model;
using CreateBidDto = Shared.DTOs.Bids.CreateBidDto;
using BidDto = shared.DTOs.Bids.BidDto;
using UpdateBidDto = shared.DTOs.Bids.UpdateBidDto;

namespace MainServer.WebAPI.Controllers
{
    [ApiController]
    [Route("bids")]
    public class BidsController : ControllerBase
    {
        private readonly IBidRepository bidRepository;
        private readonly IPropertyRepository propertyRepository;
        private readonly IUserRepository userRepository;

        public BidsController(IBidRepository bidRepository, IPropertyRepository propertyRepository, IUserRepository userRepository)
        {
            this.bidRepository = bidRepository;
            this.propertyRepository = propertyRepository;
            this.userRepository = userRepository;
        }

        // [HttpPost]
        // public async Task<ActionResult<BidDto>> CreateBid([FromBody] CreateBidDto request)
        // {
           
        // }

        // [HttpGet("{id:int}")]
        // public async Task<ActionResult<BidDto>> GetSingleBid([FromRoute] int id)
        // {
          
        // }

        [HttpGet]
        public async Task<ActionResult<List<BidDto>>> GetManyBids([FromQuery] int? propertyId = null, [FromQuery] int? buyerId = null, [FromQuery] string? status = null)
        {
            IQueryable<Bid> query = bidRepository.GetMany()
                .Include(b => b.Buyer)
                .Include(b => b.Property);

            if (propertyId.HasValue)
            {
                query = query.Where(b => b.PropertyId == propertyId.Value);
            }

            if (buyerId.HasValue)
            {
                query = query.Where(b => b.BuyerId == buyerId.Value);
            }

            if (!string.IsNullOrWhiteSpace(status))
            {
                query = query.Where(b => b.Status == status);
            }

            List<Bid> filteredBids = await query.ToListAsync();

            List<BidDto> responseDtos = filteredBids.Select(b => new BidDto
            {
                Id = b.Id,
                BuyerUsername = b.Buyer?.Username,
                PropertyTitle = b.Property?.Title,
                Amount = b.Amount,
                ExpiryDate = b.ExpiryDate,
                Status = b.Status
            }).ToList();

            return Ok(responseDtos);
        }

        // [HttpPut("{id:int}")]
        // public async Task<ActionResult<BidDto>> UpdateBid([FromRoute] int id, [FromBody] UpdateBidDto request)
        // {
           
        // }

        // [HttpDelete("{id:int}")]
        // public async Task<IActionResult> DeleteBid([FromRoute] int id)
        // {
          
        // }
    }
}
