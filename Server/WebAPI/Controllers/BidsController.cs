using Microsoft.AspNetCore.Mvc;
using RepositoryInterfaces;
using Server.Model;
using CreateBidDto = Shared.DTOs.Bids.CreateBidDto;
using GetBidDto = shared.DTOs.Bids.GetBidDto;
using UpdateBidDto = shared.DTOs.Bids.UpdateBidDto;

namespace Server.WebAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
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

        [HttpPost]
        public async Task<ActionResult<GetBidDto>> CreateBid([FromBody] CreateBidDto request)
        {
            // Verify buyer exists
            var buyer = await userRepository.GetSingleAsync(request.BuyerId);

            // Verify property exists and is available
            var property = await propertyRepository.GetSingleAsync(request.PropertyId);
            if (property.Status != PropertyStatus.Available)
            {
                return BadRequest("Property is not available for bidding.");
            }

            // Verify bid amount is higher than starting price
            if (request.Amount <= property.StartingPrice)
            {
                return BadRequest($"Bid amount must be higher than the starting price of {property.StartingPrice}.");
            }

            Bid bid = new()
            {
                BuyerId = request.BuyerId,
                PropertyId = request.PropertyId,
                Amount = request.Amount,
                ExpiryDate = DateTime.UtcNow.AddDays(7), // Default 7 days expiry
                Status = BidStatus.Pending
            };

            Bid created = await bidRepository.AddAsync(bid);

            GetBidDto dto = new()
            {
                Id = created.Id,
                BuyerUsername = buyer.Username,
                PropertyTitle = property.Title,
                Amount = created.Amount,
                ExpiryDate = created.ExpiryDate,
                Status = created.Status
            };

            return Created($"/api/bids/{dto.Id}", dto);
        }

        [HttpGet("{id:int}")]
        public async Task<ActionResult<GetBidDto>> GetSingleBid([FromRoute] int id)
        {
            Bid bid = await bidRepository.GetSingleAsync(id);
            var buyer = await userRepository.GetSingleAsync(bid.BuyerId);
            var property = await propertyRepository.GetSingleAsync(bid.PropertyId);

            GetBidDto responseDto = new()
            {
                Id = bid.Id,
                BuyerUsername = buyer.Username,
                PropertyTitle = property.Title,
                Amount = bid.Amount,
                ExpiryDate = bid.ExpiryDate,
                Status = bid.Status
            };

            return Ok(responseDto);
        }

        [HttpGet]
        public async Task<ActionResult<List<GetBidDto>>> GetManyBids([FromQuery] int? propertyId = null, [FromQuery] int? buyerId = null, [FromQuery] BidStatus? status = null)
        {
            IQueryable<Bid> query = bidRepository.GetMany();

            if (propertyId.HasValue)
            {
                query = query.Where(b => b.PropertyId == propertyId.Value);
            }

            if (buyerId.HasValue)
            {
                query = query.Where(b => b.BuyerId == buyerId.Value);
            }

            if (status.HasValue)
            {
                query = query.Where(b => b.Status == status.Value);
            }

            List<Bid> filteredBids = query.ToList();

            var tasks = filteredBids.Select(async b =>
            {
                var buyer = await userRepository.GetSingleAsync(b.BuyerId);
                var property = await propertyRepository.GetSingleAsync(b.PropertyId);
                return new GetBidDto
                {
                    Id = b.Id,
                    BuyerUsername = buyer.Username,
                    PropertyTitle = property.Title,
                    Amount = b.Amount,
                    ExpiryDate = b.ExpiryDate,
                    Status = b.Status
                };
            });

            var responseDtos = await Task.WhenAll(tasks);

            return Ok(responseDtos.ToList());
        }

        [HttpPut("{id:int}")]
        public async Task<ActionResult<GetBidDto>> UpdateBid([FromRoute] int id, [FromBody] UpdateBidDto request)
        {
            if (id != request.Id)
            {
                return BadRequest("Route ID does not match Bid ID in body.");
            }

            Bid existingBid = await bidRepository.GetSingleAsync(id);

            // Authorization check: buyer can only update their own bid
            if (existingBid.BuyerId != request.BuyerId)
            {
                return Forbid("You can only update your own bids.");
            }

            var property = await propertyRepository.GetSingleAsync(existingBid.PropertyId);

            if (request.Amount.HasValue)
            {
                // Verify new amount is higher than current amount
                if (request.Amount.Value <= existingBid.Amount)
                {
                    return BadRequest($"New bid amount must be higher than current bid amount of {existingBid.Amount}.");
                }
                existingBid.Amount = request.Amount.Value;
            }

            if (request.ExpiryDate.HasValue)
            {
                existingBid.ExpiryDate = request.ExpiryDate.Value;
            }

            await bidRepository.UpdateAsync(existingBid);

            var buyer = await userRepository.GetSingleAsync(existingBid.BuyerId);

            GetBidDto responseDto = new()
            {
                Id = existingBid.Id,
                BuyerUsername = buyer.Username,
                PropertyTitle = property.Title,
                Amount = existingBid.Amount,
                ExpiryDate = existingBid.ExpiryDate,
                Status = existingBid.Status
            };

            return Ok(responseDto);
        }

        [HttpDelete("{id:int}")]
        public async Task<IActionResult> DeleteBid([FromRoute] int id)
        {
            try
            {
                await bidRepository.GetSingleAsync(id);
            }
            catch (KeyNotFoundException)
            {
                return NotFound();
            }

            await bidRepository.DeleteAsync(id);

            return NoContent();
        }
    }
}
