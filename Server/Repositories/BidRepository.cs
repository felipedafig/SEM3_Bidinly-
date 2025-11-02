using Microsoft.EntityFrameworkCore;
using RepositoryInterfaces;
using Server.Data;
using Server.Model;

namespace Server.Repositories
{
    public class BidRepository : IBidRepository
    {
        private readonly DbContext _context;

        public BidRepository(DbContext context)
        {
            _context = context;
        }

        public async Task<Bid> AddAsync(Bid bid)
        {
            await _context.Bids.AddAsync(bid);
            await _context.SaveChangesAsync();
            return bid;
        }

        public async Task<Bid> UpdateAsync(Bid bid)
        {
            _context.Bids.Update(bid);
            await _context.SaveChangesAsync();
            return bid;
        }

        public async Task DeleteAsync(int id)
        {
            var bid = await _context.Bids.FindAsync(id);
            if (bid != null)
            {
                _context.Bids.Remove(bid);
                await _context.SaveChangesAsync();
            }
        }

        public async Task<Bid> GetSingleAsync(int id)
        {
            return await _context.Bids.FindAsync(id) 
                ?? throw new KeyNotFoundException($"Bid with ID {id} not found");
        }

        public IQueryable<Bid> GetMany()
        {
            return _context.Bids.AsQueryable();
        }
    }
}

