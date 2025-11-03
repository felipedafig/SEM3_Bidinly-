

using Microsoft.EntityFrameworkCore;
using RepositoryInterfaces;
using MainServer.Data;
using MainServer.Model;
using DbContext = MainServer.Data.DbContext;

namespace MainServer.Repositories
{
    public class SaleRepository : ISaleRepository
    {
        private readonly DbContext context;

        public SaleRepository(DbContext context)
        {
            this.context = context;
        }

        public async Task<Sale> AddAsync(Sale sale)
        {
            await context.Sales.AddAsync(sale);
            await context.SaveChangesAsync();
            return sale;
        }

        public async Task<Sale> UpdateAsync(Sale sale)
        {
            context.Sales.Update(sale);
            await context.SaveChangesAsync();
            return sale;
        }

        public async Task DeleteAsync(int id)
        {
            var sale = await context.Sales.FindAsync(id);
            if (sale != null)
            {
                context.Sales.Remove(sale);
                await context.SaveChangesAsync();
            }
        }

        public async Task<Sale> GetSingleAsync(int id)
        {
            return await context.Sales.FindAsync(id) 
                ?? throw new KeyNotFoundException($"Sale with ID {id} not found");
        }

        public IQueryable<Sale> GetMany()
        {
            return context.Sales.AsQueryable();
        }
    }
}

