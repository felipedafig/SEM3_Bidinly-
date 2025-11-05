using Microsoft.EntityFrameworkCore;
using RepositoryInterfaces;
using MainServer.Data;
using MainServer.Model;
using DbContext = MainServer.Data.DbContext;

namespace MainServer.Repositories
{
    public class PropertyRepository : IPropertyRepository
    {
        private readonly DbContext context;

        public PropertyRepository(DbContext context)
        {
            this.context = context;
        }

        public async Task<Property> AddAsync(Property property)
        {
            await context.Properties.AddAsync(property);
            await context.SaveChangesAsync();
            return property;
        }

        public async Task<Property> UpdateAsync(Property property)
        {
            context.Properties.Update(property);
            await context.SaveChangesAsync();
            return property;
        }

        public async Task DeleteAsync(int id)
        {
            var property = await context.Properties.FindAsync(id);
            if (property != null)
            {
                context.Properties.Remove(property);
                await context.SaveChangesAsync();
            }
        }

        public async Task<Property> GetSingleAsync(int id)
        {
            return await context.Properties.FindAsync(id) 
                ?? throw new KeyNotFoundException($"Property with ID {id} not found");
        }

        public IQueryable<Property> GetMany()
        {
            return context.Properties.AsQueryable();
        }
    }
}

