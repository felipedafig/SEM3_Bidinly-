using Microsoft.AspNetCore.SignalR;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;
using System.Text.Json;
using Shared.DTOs.Notifications;

namespace MainServer.WebAPI.Notifications;

public class RabbitMqListener : BackgroundService
{
    private readonly IHubContext<NotificationHub> hubContext;
    private IConnection? connection;
    private IModel? channel;

    private const string ExchangeName = "bid_notifications_exchange";
    private const string QueueName = "webapi_notifications_queue";
    private const string RoutingKey = "bid.notifications";

    public RabbitMqListener(IHubContext<NotificationHub> hubContext)
    {
        this.hubContext = hubContext;
    }

    protected override Task ExecuteAsync(CancellationToken stoppingToken)
    {
        var factory = new ConnectionFactory
        {
            HostName = "localhost",
            UserName = "guest",
            Password = "guest"
        };
        
        connection = factory.CreateConnection();
        channel = connection.CreateModel();
        
        channel.ExchangeDeclare(
            exchange: ExchangeName,
            type: ExchangeType.Direct,
            durable: true
        );
        
        channel.QueueDeclare(
            queue: QueueName,
            durable: true,
            exclusive: false,
            autoDelete: false
        );
        
        channel.QueueBind(
            queue: QueueName,
            exchange: ExchangeName,
            routingKey: RoutingKey
        );

        var consumer = new EventingBasicConsumer(channel!);

        consumer.Received += async (model, ea) =>
        {
            var json = Encoding.UTF8.GetString(ea.Body.ToArray());

            Console.WriteLine("RabbitMQ message received by WebAPI:");
            Console.WriteLine(json);

            var dto = JsonSerializer.Deserialize<BidNotificationDto>(json);

            if (dto != null)
            { 
                await hubContext.Clients.User(dto.BuyerId.ToString()).SendAsync("ReceiveNotification", dto);
            }
        };
        
        channel.BasicConsume(
            queue: QueueName,
            autoAck: true,
            consumer: consumer
        );

        return Task.CompletedTask;
    }

    public override void Dispose()
    {
        channel?.Close();
        connection?.Close();
        base.Dispose();
    }
}