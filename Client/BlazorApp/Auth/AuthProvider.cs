using System.Security.Claims;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.JSInterop;
using shared.DTOs.Users;

public class AuthProvider : AuthenticationStateProvider
{
    private readonly HttpClient httpClient;
    private readonly IJSRuntime jsRuntime;

    private ClaimsPrincipal? currentClaimsPrincipal;

    public AuthProvider(HttpClient httpClient, IJSRuntime jsRuntime)
    {
        this.httpClient = httpClient;
        this.jsRuntime = jsRuntime;
    }

    public override Task<AuthenticationState> GetAuthenticationStateAsync() //not async
    {
        return Task.FromResult(new AuthenticationState(currentClaimsPrincipal ?? new ClaimsPrincipal()));
    }

    public async Task Login(string username, string password)
    {
        HttpResponseMessage response = await httpClient.PostAsJsonAsync("auth/login", new { Username = username, Password = password });
        
        string content = await response.Content.ReadAsStringAsync();
         
         if (!response.IsSuccessStatusCode)
        {
            throw new Exception(content);
        }

        UserDto user = System.Text.Json.JsonSerializer.Deserialize<UserDto>(content, new System.Text.Json.JsonSerializerOptions { PropertyNameCaseInsensitive = true })!;

        List<Claim> claims = new List<Claim>
        {
            new Claim(ClaimTypes.Name, user.Username ?? string.Empty),
            new Claim(ClaimTypes.NameIdentifier, user.Id.ToString())
        };

        ClaimsIdentity identity = new ClaimsIdentity(claims, "apiauth");
        currentClaimsPrincipal = new ClaimsPrincipal(identity);

        NotifyAuthenticationStateChanged(Task.FromResult(new AuthenticationState(currentClaimsPrincipal)));
    }

    public Task Logout()
    {
        currentClaimsPrincipal = null;
        NotifyAuthenticationStateChanged(Task.FromResult(new AuthenticationState(new ClaimsPrincipal())));
        return Task.CompletedTask;
    }
}