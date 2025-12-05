using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.AspNetCore.SignalR.Client;
using Shared.DTOs.Notifications;

namespace BlazorApp.Notifications;


public class NotificationService : IAsyncDisposable
{
    private HubConnection? _hubConnection;
    private readonly AuthenticationStateProvider authProvider;

    public event Action<BidNotificationDto>? OnNotificationReceived;

    public NotificationService(AuthenticationStateProvider authProvider)
    {
        this.authProvider = authProvider;
    }

    public async Task StartAsync()
    {
        if (_hubConnection != null)
            return;

        //connect to hub
        _hubConnection = new HubConnectionBuilder()
            .WithUrl("http://localhost:5141/notificationsHub", options =>
            {
                // send userID as token
                options.AccessTokenProvider = async () =>
                {
                    var auth = await authProvider.GetAuthenticationStateAsync();
                    var userId = auth.User.FindFirst("Id")?.Value;

                    Console.WriteLine($"Sending User ID: {userId}");

                    return userId; 
                };
            })
             
            //allow automatic reconnect
            .WithAutomaticReconnect()
            .Build();
        
        //listen for notifs
        _hubConnection.On<BidNotificationDto>("ReceiveNotification", dto =>
        {
            Console.WriteLine($"Notification received: {dto.Status} - {dto.Message}");
            OnNotificationReceived?.Invoke(dto);
        });
        
        await _hubConnection.StartAsync();
        Console.WriteLine("Connected to NotificationHub.");
    }

    //remove notif 
    public async ValueTask DisposeAsync()
    {
        if (_hubConnection != null)
            await _hubConnection.DisposeAsync();
    }
}