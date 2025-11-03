using Microsoft.EntityFrameworkCore;
using RepositoryInterfaces;
using Server.Data;
using Server.Model;
using DbContext = Server.Data.DbContext;

namespace Server.Repositories
{
    public class RoleRepository : IRoleRepository
    {
        private readonly DbContext context;

        public RoleRepository(DbContext context)
        {
            this.context = context;
        }

        public async Task<Role> AddAsync(Role role)
        {
            await context.Roles.AddAsync(role);
            await context.SaveChangesAsync();
            return role;
        }

        public async Task<Role> UpdateAsync(Role role)
        {
            context.Roles.Update(role);
            await context.SaveChangesAsync();
            return role;
        }

        public async Task DeleteAsync(int id)
        {
            var role = await context.Roles.FindAsync(id);
            if (role != null)
            {
                context.Roles.Remove(role);
                await context.SaveChangesAsync();
            }
        }

        public async Task<Role> GetSingleAsync(int id)
        {
            return await context.Roles.FindAsync(id) 
                ?? throw new KeyNotFoundException($"Role with ID {id} not found");
        }

        public IQueryable<Role> GetMany()
        {
            return context.Roles.AsQueryable();
        }
    }
}

