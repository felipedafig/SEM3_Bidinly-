using Microsoft.EntityFrameworkCore;
using Npgsql;
using MainServer.WebAPI.Middlewares;
using MainServer.WebAPI.Notifications;
using MainServer.WebAPI.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();

builder.Services.AddTransient<GlobalExceptionHandlerMiddleware>();

builder.Services.AddOpenApi();

builder.Services.AddSingleton<RabbitMqPublisher>();

// gRPC Client as Singleton
builder.Services.AddSingleton<DataTierGrpcClient>();
builder.Services.AddSingleton<PropertyGrpcClient>();

var app = builder.Build();

app.UseHttpsRedirection();

app.UseMiddleware<GlobalExceptionHandlerMiddleware>();

app.MapControllers();

if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}

app.Run();