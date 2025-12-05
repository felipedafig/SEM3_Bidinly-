using Microsoft.AspNetCore.SignalR;

public class UserIdProvider : IUserIdProvider
{
    public string? GetUserId(HubConnectionContext connection)
    {
        //get the http context
        var http = connection.GetHttpContext();
        
        //read the authorization header
        var authHeader = http?.Request.Headers["Authorization"].ToString();
        if (!string.IsNullOrWhiteSpace(authHeader) && authHeader.StartsWith("Bearer "))
        
        {
            //extract userId (token)
            var token = authHeader.Substring("Bearer ".Length);
            Console.WriteLine($"UserId from Auth: {token}");
            return token;
        }
        
        return null;
    }
}