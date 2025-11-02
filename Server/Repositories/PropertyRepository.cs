using Microsoft.EntityFrameworkCore;
using RepositoryInterfaces;
using Server.Data;
using Server.Model;

namespace Server.Repositories
{
    public class PropertyRepository : IPropertyRepository
    {
        private readonly DbContext _context;

        public PropertyRepository(DbContext context)
        {
            _context = context;
        }

        public async Task<Property> AddAsync(Property property)
        {
            await _context.Properties.AddAsync(property);
            await _context.SaveChangesAsync();
            return property;
        }

        public async Task<Property> UpdateAsync(Property property)
        {
            _context.Properties.Update(property);
            await _context.SaveChangesAsync();
            return property;
        }

        public async Task DeleteAsync(int id)
        {
            var property = await _context.Properties.FindAsync(id);
            if (property != null)
            {
                _context.Properties.Remove(property);
                await _context.SaveChangesAsync();
            }
        }

        public async Task<Property> GetSingleAsync(int id)
        {
            return await _context.Properties.FindAsync(id) 
                ?? throw new KeyNotFoundException($"Property with ID {id} not found");
        }

        public IQueryable<Property> GetMany()
        {
            return _context.Properties.AsQueryable();
        }
    }
}

