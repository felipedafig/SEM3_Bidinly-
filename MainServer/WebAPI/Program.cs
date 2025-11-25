using Microsoft.EntityFrameworkCore;
using Npgsql;
using MainServer.WebAPI.Middlewares;
using MainServer.WebAPI.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();

builder.Services.AddTransient<GlobalExceptionHandlerMiddleware>();

builder.Services.AddOpenApi();

// gRPC Client as Singleton
builder.Services.AddSingleton<UserGrpcClient>();
builder.Services.AddSingleton<BidGrpcClient>();
builder.Services.AddSingleton<PropertyGrpcClient>();
builder.Services.AddSingleton<SaleGrpcClient>();
builder.Services.AddSingleton<RoleGrpcClient>();
builder.Services.AddSingleton<AuthGrpcClient>();

var app = builder.Build();

app.UseHttpsRedirection();

app.UseMiddleware<GlobalExceptionHandlerMiddleware>();

app.MapControllers();

if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}

app.Run();