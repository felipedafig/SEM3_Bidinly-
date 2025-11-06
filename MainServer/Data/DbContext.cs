using Microsoft.EntityFrameworkCore;
using MainServer.Model;

namespace MainServer.Data
{
    public class DbContext : Microsoft.EntityFrameworkCore.DbContext
    {
        public DbContext(Microsoft.EntityFrameworkCore.DbContextOptions<DbContext> options)
            : base(options)
        {
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Role> Roles { get; set; }
        public DbSet<Property> Properties { get; set; }
        public DbSet<Bid> Bids { get; set; }
        public DbSet<Sale> Sales { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            
            modelBuilder.Entity<Role>(entity =>
            {
                entity.ToTable("Role");
                entity.HasKey(e => e.Id);
                entity.Property(e => e.Id).HasColumnName("Id");
                entity.Property(e => e.Name).HasColumnName("Name").HasMaxLength(50).IsRequired();
            });

            modelBuilder.Entity<User>(entity =>
            {
                entity.ToTable("User");
                entity.HasKey(e => e.Id);
                entity.Property(e => e.Id).HasColumnName("Id");
                entity.Property(e => e.Username).HasColumnName("Username").HasMaxLength(50).IsRequired();
                entity.Property(e => e.Password).HasColumnName("Password").HasMaxLength(100).IsRequired();
                entity.Property(e => e.RoleId).HasColumnName("RoleId");
                
                entity.HasIndex(e => e.Username).IsUnique();
                
                entity.HasOne(e => e.Role)
                      .WithMany()
                      .HasForeignKey(e => e.RoleId)
                      .HasConstraintName("fk_role")
                      .OnDelete(DeleteBehavior.Restrict);
            });

            modelBuilder.Entity<Property>(entity =>
            {
                entity.ToTable("Property");
                entity.HasKey(e => e.Id);
                entity.Property(e => e.Id).HasColumnName("Id");
                entity.Property(e => e.AgentId).HasColumnName("AgentId");
                entity.Property(e => e.Title).HasColumnName("Title").IsRequired().HasMaxLength(2000);
                entity.Property(e => e.Address).HasColumnName("Address").HasMaxLength(500);
                entity.Property(e => e.StartingPrice).HasColumnName("StartingPrice").HasColumnType("decimal(18,2)");
                entity.Property(e => e.Bedrooms).HasColumnName("Bedrooms");
                entity.Property(e => e.Bathrooms).HasColumnName("Bathrooms");
                entity.Property(e => e.SizeInSquareFeet).HasColumnName("SizeInSquareFeet");
                entity.Property(e => e.Description).HasColumnName("Description").HasMaxLength(5000);
                entity.Property(e => e.Status)
                    .HasColumnName("Status")
                    .HasColumnType("varchar(50)")
                    .HasDefaultValue("Available");
                
                entity.HasOne(e => e.Agent)
                      .WithMany()
                      .HasForeignKey(e => e.AgentId)
                      .HasConstraintName("fk_agent")
                      .OnDelete(DeleteBehavior.Restrict);
            });

            modelBuilder.Entity<Bid>(entity =>
            {
                entity.ToTable("Bid");
                entity.HasKey(e => e.Id);
                entity.Property(e => e.Id).HasColumnName("Id");
                entity.Property(e => e.BuyerId).HasColumnName("BuyerId");
                entity.Property(e => e.PropertyId).HasColumnName("PropertyId");
                entity.Property(e => e.Amount).HasColumnName("Amount").HasColumnType("decimal(18,2)");
                entity.Property(e => e.ExpiryDate).HasColumnName("ExpiryDate").HasColumnType("timestamptz");
                entity.Property(e => e.Status)
                    .HasColumnName("Status")
                    .HasColumnType("varchar(50)")
                    .HasDefaultValue("Pending");
                
                entity.HasOne(e => e.Buyer)
                      .WithMany()
                      .HasForeignKey(e => e.BuyerId)
                      .HasConstraintName("fk_buyer")
                      .OnDelete(DeleteBehavior.Restrict);
                
                entity.HasOne(e => e.Property)
                      .WithMany()
                      .HasForeignKey(e => e.PropertyId)
                      .HasConstraintName("fk_property")
                      .OnDelete(DeleteBehavior.Restrict);
            });

            modelBuilder.Entity<Sale>(entity =>
            {
                entity.ToTable("Sale");
                entity.HasKey(e => e.Id);
                entity.Property(e => e.Id).HasColumnName("Id");
                entity.Property(e => e.TimeOfSale).HasColumnName("TimeOfSale").IsRequired().HasColumnType("timestamptz");
                entity.Property(e => e.PropertyId).HasColumnName("PropertyId");
                entity.Property(e => e.BidId).HasColumnName("BidId");
                entity.Property(e => e.BuyerId).HasColumnName("BuyerId");
                entity.Property(e => e.AgentId).HasColumnName("AgentId");
                
                entity.HasOne(e => e.Property)
                      .WithMany()
                      .HasForeignKey(e => e.PropertyId)
                      .HasConstraintName("fk_sold_property")
                      .OnDelete(DeleteBehavior.Restrict);
                
                entity.HasOne(e => e.WinningBid)
                      .WithMany()
                      .HasForeignKey(e => e.BidId)
                      .HasConstraintName("fk_winning_bid")
                      .OnDelete(DeleteBehavior.Restrict);
                
                entity.HasOne(e => e.Buyer)
                      .WithMany()
                      .HasForeignKey(e => e.BuyerId)
                      .HasConstraintName("fk_selling_buyer")
                      .OnDelete(DeleteBehavior.Restrict);
                
                entity.HasOne(e => e.Agent)
                      .WithMany()
                      .HasForeignKey(e => e.AgentId)
                      .HasConstraintName("fk_selling_agent")
                      .OnDelete(DeleteBehavior.Restrict);
            });
        }
    }
}

