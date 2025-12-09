using Microsoft.EntityFrameworkCore;
using Npgsql;
using MainServer.WebAPI.Middlewares;
using MainServer.WebAPI.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowBlazor",
        policy =>
        {
            policy.WithOrigins("http://localhost:5122")
                .AllowAnyHeader()
                .AllowAnyMethod()
                .AllowCredentials();
        });
});

builder.Services.AddTransient<GlobalExceptionHandlerMiddleware>();

builder.Services.AddOpenApi();

// gRPC Client as Singleton
builder.Services.AddSingleton<DataTierGrpcClient>();
builder.Services.AddSingleton<PropertyGrpcClient>();
builder.Services.AddSingleton<PaymentGrpcClient>();

var app = builder.Build();

app.UseHttpsRedirection();

app.UseMiddleware<GlobalExceptionHandlerMiddleware>();
app.UseCors("AllowBlazor");

app.MapControllers();

if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}

app.Run();