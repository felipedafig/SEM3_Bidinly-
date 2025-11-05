using Microsoft.EntityFrameworkCore;
using Npgsql;
using RepositoryInterfaces;
using MainServer.Data;
using MainServer.Model;
using MainServer.Repositories;
using MainServer.WebAPI.Middlewares;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();

builder.Services.AddTransient<GlobalExceptionHandlerMiddleware>();

builder.Services.AddOpenApi();

builder.Services.AddDbContext<MainServer.Data.DbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

//EF Core
builder.Services.AddScoped<IBidRepository, BidRepository>();
builder.Services.AddScoped<IPropertyRepository, PropertyRepository>();
builder.Services.AddScoped<ISaleRepository, SaleRepository>();
builder.Services.AddScoped<IUserRepository, UserRepository>();
builder.Services.AddScoped<IRoleRepository, RoleRepository>();

var app = builder.Build();

app.UseHttpsRedirection();

app.UseMiddleware<GlobalExceptionHandlerMiddleware>();

app.MapControllers();

if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}

app.Run();