using Microsoft.EntityFrameworkCore;
using RepositoryInterfaces;
using Server.Data;
using Server.Model;

namespace Server.Repositories
{
    public class RoleRepository : IRoleRepository
    {
        private readonly DbContext _context;

        public RoleRepository(DbContext context)
        {
            _context = context;
        }

        public async Task<Role> AddAsync(Role role)
        {
            await _context.Roles.AddAsync(role);
            await _context.SaveChangesAsync();
            return role;
        }

        public async Task<Role> UpdateAsync(Role role)
        {
            _context.Roles.Update(role);
            await _context.SaveChangesAsync();
            return role;
        }

        public async Task DeleteAsync(int id)
        {
            var role = await _context.Roles.FindAsync(id);
            if (role != null)
            {
                _context.Roles.Remove(role);
                await _context.SaveChangesAsync();
            }
        }

        public async Task<Role> GetSingleAsync(int id)
        {
            return await _context.Roles.FindAsync(id) 
                ?? throw new KeyNotFoundException($"Role with ID {id} not found");
        }

        public IQueryable<Role> GetMany()
        {
            return _context.Roles.AsQueryable();
        }
    }
}

