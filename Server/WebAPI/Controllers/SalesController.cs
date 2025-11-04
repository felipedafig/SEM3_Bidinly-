using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using RepositoryInterfaces;
using MainServer.Model;
using shared.DTOs.Sales;

namespace MainServer.WebAPI.Controllers
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

        [HttpGet]
        public async Task<ActionResult<List<SaleDto>>> GetManySales([FromQuery] int? propertyId = null, [FromQuery] int? buyerId = null, [FromQuery] int? agentId = null)
        {
            IQueryable<Sale> query = saleRepository.GetMany()
                .Include(s => s.Property)
                .Include(s => s.WinningBid)
                .Include(s => s.Buyer)
                .Include(s => s.Agent); // Eagerly load all related entities to avoid concurrent DbContext access

            if (propertyId.HasValue)
            {
                query = query.Where(s => s.PropertyId == propertyId.Value);
            }

            if (buyerId.HasValue)
            {
                query = query.Where(s => s.BuyerId == buyerId.Value);
            }

            if (agentId.HasValue)
            {
                query = query.Where(s => s.AgentId == agentId.Value);
            }

            List<Sale> filteredSales = await query.ToListAsync();

            List<SaleDto> responseDtos = filteredSales.Select(s => new SaleDto
            {
                Id = s.Id,
                PropertyId = s.PropertyId,
                PropertyTitle = s.Property?.Title,
                BuyerUsername = s.Buyer?.Username,
                AgentUsername = s.Agent?.Username,
                FinalAmount = s.WinningBid?.Amount ?? 0,
                TimeOfSale = s.TimeOfSale,
                WinningBidId = s.BidId
            }).ToList();

            return Ok(responseDtos);
        }

    }
}

