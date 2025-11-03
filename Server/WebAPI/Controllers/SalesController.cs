using Microsoft.AspNetCore.Mvc;
using RepositoryInterfaces;
using Server.Model;
using shared.DTOs.Sales;

namespace Server.WebAPI.Controllers
{
    [ApiController]
    [Route("sales")]
    public class SalesController : ControllerBase
    {
        private readonly ISaleRepository saleRepository;
        private readonly IBidRepository bidRepository;
        private readonly IPropertyRepository propertyRepository;
        private readonly IUserRepository userRepository;

        public SalesController(
            ISaleRepository saleRepository,
            IBidRepository bidRepository,
            IPropertyRepository propertyRepository,
            IUserRepository userRepository)
        {
            this.saleRepository = saleRepository;
            this.bidRepository = bidRepository;
            this.propertyRepository = propertyRepository;
            this.userRepository = userRepository;
        }

        // [HttpPost]
        // public async Task<ActionResult<GetSaleDto>> CreateSale([FromBody] int winningBidId)
        // {
        //     // Get the winning bid
        //     Bid winningBid = await bidRepository.GetSingleAsync(winningBidId);

        //     // Verify bid is accepted/won
        //     if (winningBid.Status != BidStatus.Accepted && winningBid.Status != BidStatus.Accepted)
        //     {
        //         return BadRequest("Bid must be accepted to create a sale.");
        //     }

        //     // Verify property is not already sold
        //     var property = await propertyRepository.GetSingleAsync(winningBid.PropertyId);
        //     if (property.Status == PropertyStatus.Sold)
        //     {
        //         return BadRequest("Property is already sold.");
        //     }

        //     // Create the sale
        //     Sale sale = new()
        //     {
        //         PropertyId = winningBid.PropertyId,
        //         BidId = winningBid.Id,
        //         BuyerId = winningBid.BuyerId,
        //         TimeOfSale = DateTime.UtcNow
        //     };

        //     Sale created = await saleRepository.AddAsync(sale);

        //     // Update property status to Sold
        //     property.Status = PropertyStatus.Sold;
        //     await propertyRepository.UpdateAsync(property);

        //     // Get enriched data
        //     var buyer = await userRepository.GetSingleAsync(created.BuyerId);

        //     GetSaleDto dto = new()
        //     {
        //         Id = created.Id,
        //         PropertyId = created.PropertyId,
        //         PropertyTitle = property.Title,
        //         BuyerUsername = buyer.Username,
        //         FinalAmount = winningBid.Amount,
        //         TimeOfSale = created.TimeOfSale,
        //         WinningBidId = created.BidId
        //     };

        //     return Created($"sales/{dto.Id}", dto);
        // }

        // // [HttpGet("{id:int}")]
        // // public async Task<ActionResult<GetSaleDto>> GetSingleSale([FromRoute] int id)
        // // {
        // //     Sale sale = await saleRepository.GetSingleAsync(id);
        // //     var bid = await bidRepository.GetSingleAsync(sale.BidId);
        // //     var property = await propertyRepository.GetSingleAsync(sale.PropertyId);
        // //     var buyer = await userRepository.GetSingleAsync(sale.BuyerId);

        // //     GetSaleDto responseDto = new()
        // //     {
        // //         Id = sale.Id,
        // //         PropertyId = sale.PropertyId,
        // //         PropertyTitle = property.Title,
        // //         BuyerUsername = buyer.Username,
        // //         FinalAmount = bid.Amount,
        // //         TimeOfSale = sale.TimeOfSale,
        // //         WinningBidId = sale.BidId
        // //     };

        // //     return Ok(responseDto);
        // // }

        [HttpGet]
        public async Task<ActionResult<List<GetSaleDto>>> GetManySales([FromQuery] int? propertyId = null, [FromQuery] int? buyerId = null)
        {
            IQueryable<Sale> query = saleRepository.GetMany();

            if (propertyId.HasValue)
            {
                query = query.Where(s => s.PropertyId == propertyId.Value);
            }

            if (buyerId.HasValue)
            {
                query = query.Where(s => s.BuyerId == buyerId.Value);
            }

            List<Sale> filteredSales = query.ToList();

            var tasks = filteredSales.Select(async s =>
            {
                var bid = await bidRepository.GetSingleAsync(s.BidId);
                var property = await propertyRepository.GetSingleAsync(s.PropertyId);
                var buyer = await userRepository.GetSingleAsync(s.BuyerId);
                return new GetSaleDto
                {
                    Id = s.Id,
                    PropertyId = s.PropertyId,
                    PropertyTitle = property.Title,
                    BuyerUsername = buyer.Username,
                    FinalAmount = bid.Amount,
                    TimeOfSale = s.TimeOfSale,
                    WinningBidId = s.BidId
                };
            });

            var responseDtos = await Task.WhenAll(tasks);

            return Ok(responseDtos.ToList());
        }

        // [HttpDelete("{id:int}")]
        // public async Task<IActionResult> DeleteSale([FromRoute] int id)
        // {
        //     try
        //     {
        //         await saleRepository.GetSingleAsync(id);
        //     }
        //     catch (KeyNotFoundException)
        //     {
        //         return NotFound();
        //     }

        //     await saleRepository.DeleteAsync(id);

        //     return NoContent();
        // }
    }
}

