namespace BlazorApp.Notifications;

using Microsoft.AspNetCore.SignalR.Client;
using Shared.DTOs.Notifications;

public class NotificationService : IAsyncDisposable
{
    private HubConnection? _hubConnection;

    public event Action<BidNotificationDto>? OnNotificationReceived;

    public async Task StartAsync()
    {
        if (_hubConnection != null)
            return;

        _hubConnection = new HubConnectionBuilder()
            .WithUrl("http://localhost:5141/notificationsHub")
            .WithAutomaticReconnect()
            .Build();
        
        _hubConnection.On<BidNotificationDto>("ReceiveNotification", (dto) =>
        {
            OnNotificationReceived?.Invoke(dto);
        });

        await _hubConnection.StartAsync();
        Console.WriteLine("Connected to NotificationHub.");
    }

    public async ValueTask DisposeAsync()
    {
        if (_hubConnection != null)
            await _hubConnection.DisposeAsync();
    }
}