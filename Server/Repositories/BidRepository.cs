using Microsoft.EntityFrameworkCore;
using RepositoryInterfaces;
using Server.Data;
using Server.Model;
using DbContext = Server.Data.DbContext;

namespace Server.Repositories
{
    public class BidRepository : IBidRepository
    {
        private readonly DbContext context;

        public BidRepository(DbContext context)
        {
            this.context = context;
        }

        public async Task<Bid> AddAsync(Bid bid)
        {
            await context.Bids.AddAsync(bid);
            await context.SaveChangesAsync();
            return bid;
        }

        public async Task<Bid> UpdateAsync(Bid bid)
        {
            context.Bids.Update(bid);
            await context.SaveChangesAsync();
            return bid;
        }

        public async Task DeleteAsync(int id)
        {
            var bid = await context.Bids.FindAsync(id);
            if (bid != null)
            {
                context.Bids.Remove(bid);
                await context.SaveChangesAsync();
            }
        }

        public async Task<Bid> GetSingleAsync(int id)
        {
            return await context.Bids.FindAsync(id) 
                ?? throw new KeyNotFoundException($"Bid with ID {id} not found");
        }

        public IQueryable<Bid> GetMany()
        {
            return context.Bids.AsQueryable();
        }
    }
}

