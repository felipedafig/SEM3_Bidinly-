using Microsoft.EntityFrameworkCore.Metadata;

namespace MainServer.WebAPI.Services;

using System.Text;
using System.Text.Json;
using RabbitMQ.Client;
using Shared.DTOs.Notifications;

public class RabbitMqPublisher : IDisposable
{
    private readonly IConnection _connection;
    private readonly IModel _channel;

    private const string ExchangeName = "bid_notifications_exchange";
    private const string RoutingKey = "bid.notifications";

    public RabbitMqPublisher()
    {
        var factory = new ConnectionFactory
        {
            HostName = "localhost",
            UserName = "guest",
            Password = "guest"
        };

        _connection = factory.CreateConnection();
        _channel = _connection.CreateModel();
        
        _channel.ExchangeDeclare(
            exchange: ExchangeName,
            type: ExchangeType.Direct,
            durable: true
        );
    }

    public void Publish(BidNotificationDto dto)
    {
        var json = JsonSerializer.Serialize(dto, JsonOptions);
        var body = Encoding.UTF8.GetBytes(json);

        _channel.BasicPublish(
            exchange: ExchangeName,
            routingKey: RoutingKey,
            basicProperties: null,
            body: body
        );
    }

    public void Dispose()
    {
        _channel?.Close();
        _connection?.Close();
    }
    
    private static readonly JsonSerializerOptions JsonOptions = new()
    {
        PropertyNamingPolicy = JsonNamingPolicy.CamelCase
    };
}