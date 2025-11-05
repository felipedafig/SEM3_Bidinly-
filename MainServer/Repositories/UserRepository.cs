using Microsoft.EntityFrameworkCore;
using RepositoryInterfaces;
using MainServer.Data;
using MainServer.Model;
using DbContext = MainServer.Data.DbContext;

namespace MainServer.Repositories
{
    public class UserRepository : IUserRepository
    {
        private readonly DbContext context;

        public UserRepository(DbContext context)
        {
            this.context = context;
        }

        public async Task<User> AddAsync(User user)
        {
            await context.Users.AddAsync(user);
            await context.SaveChangesAsync();
            return user;
        }

        public async Task<User> UpdateAsync(User user)
        {
            context.Users.Update(user);
            await context.SaveChangesAsync();
            return user;
        }

        public async Task DeleteAsync(int id)
        {
            var user = await context.Users.FindAsync(id);
            if (user != null)
            {
                context.Users.Remove(user);
                await context.SaveChangesAsync();
            }
        }

        public async Task<User> GetSingleAsync(int id)
        {
            return await context.Users.FindAsync(id) 
                ?? throw new KeyNotFoundException($"User with ID {id} not found");
        }

        public IQueryable<User> GetMany()
        {
            return context.Users.AsQueryable();
        }

        public async Task<User?> GetByUsernameAsync(string username)
        {
            return await context.Users
                .FirstOrDefaultAsync(u => u.Username == username);
        }
    }
}

