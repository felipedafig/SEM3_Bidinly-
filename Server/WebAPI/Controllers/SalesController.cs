using Microsoft.AspNetCore.Mvc;
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
            IQueryable<Sale> query = saleRepository.GetMany();

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

            List<Sale> filteredSales = query.ToList();

            IEnumerable<Task<SaleDto>> tasks = filteredSales.Select(async s =>
            {
                Bid bid = await bidRepository.GetSingleAsync(s.BidId);
                Property property = await propertyRepository.GetSingleAsync(s.PropertyId);
                User buyer = await userRepository.GetSingleAsync(s.BuyerId);
                User agent = await userRepository.GetSingleAsync(s.AgentId);
                return new SaleDto
                {
                    Id = s.Id,
                    PropertyId = s.PropertyId,
                    PropertyTitle = property.Title,
                    BuyerUsername = buyer.Username,
                    AgentUsername = agent.Username,
                    FinalAmount = bid.Amount,
                    TimeOfSale = s.TimeOfSale,
                    WinningBidId = s.BidId
                };
            });

            SaleDto[] responseDtos = await Task.WhenAll(tasks); //doesnt complete until all dtos async taks are done

            return Ok(responseDtos.ToList());
        }

    }
}

