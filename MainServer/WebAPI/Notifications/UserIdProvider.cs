using Microsoft.AspNetCore.SignalR;

namespace MainServer.WebAPI.Notifications;

using Microsoft.AspNetCore.SignalR;

public class UserIdProvider : IUserIdProvider
{
    public string? GetUserId(HubConnectionContext connection)
    {
        return connection.User?.FindFirst("Id")?.Value;
    }
}
