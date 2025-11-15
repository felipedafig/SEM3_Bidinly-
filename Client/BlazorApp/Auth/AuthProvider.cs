using System.Security.Claims;
using System.Text.Json;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.JSInterop;
using Shared.DTOs.Auth;
using shared.DTOs.Users;

namespace BlazorApp.Auth;

public class AuthProvider : AuthenticationStateProvider
{
    private readonly HttpClient httpClient;
    private readonly IJSRuntime jsRuntime;

    public AuthProvider(HttpClient httpClient, IJSRuntime jsRuntime)
    {
        this.httpClient = httpClient;
        this.jsRuntime = jsRuntime;
    }

    public async Task Login(string userName, string password)
    {
        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", $"[AuthProvider] Login called for user: {userName}");
        }
        catch { }

        HttpResponseMessage response = await httpClient.PostAsJsonAsync(
            "auth/login",
            new LoginRequestDTO { Username = userName, Password = password });
        string content = await response.Content.ReadAsStringAsync();
        
        if (!response.IsSuccessStatusCode)
        {
            try
            {
                await jsRuntime.InvokeVoidAsync("console.error", $"[AuthProvider] Login failed - Status: {response.StatusCode}, Response: {content}");
            }
            catch { }
            throw new Exception(content);
        }

        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", $"[AuthProvider] Login API call successful - Status: {response.StatusCode}");
        }
        catch { }

        UserDto userDto = JsonSerializer.Deserialize<UserDto>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true })!;

        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", $"[AuthProvider] User deserialized - Id: {userDto.Id}, Username: {userDto.Username}");
        }
        catch { }

        string serialisedData = JsonSerializer.Serialize(userDto);
        await jsRuntime.InvokeVoidAsync("sessionStorage.setItem", "currentUser", serialisedData);

        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", "[AuthProvider] User saved to sessionStorage");
        }
        catch { }

        List<Claim> claims = new List<Claim>()
        {
            new Claim(ClaimTypes.Name, userDto.Username),
            new Claim("Id", userDto.Id.ToString()),
            // Add more claims here with your own claim type as a string, e.g.:
            // new Claim("DateOfBirth", userDto.DateOfBirth.ToString("yyyy-MM-dd"))
            // new Claim("Role", userDto.Role)
            // new Claim("Email", userDto.Email)
        };

        ClaimsIdentity identity = new ClaimsIdentity(claims, "apiauth");
        ClaimsPrincipal claimsPrincipal = new ClaimsPrincipal(identity);

        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", $"[AuthProvider] ClaimsPrincipal created - IsAuthenticated: {claimsPrincipal.Identity?.IsAuthenticated}, Name: {claimsPrincipal.Identity?.Name}");
        }
        catch { }

        NotifyAuthenticationStateChanged(Task.FromResult(new AuthenticationState(claimsPrincipal)));

        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", "[AuthProvider] AuthenticationStateChanged notified");
        }
        catch { }
    }

    public override async Task<AuthenticationState> GetAuthenticationStateAsync()
    {
        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", "[AuthProvider] GetAuthenticationStateAsync called");
        }
        catch { }

        string userAsJson = "";
        try
        {
            userAsJson = await jsRuntime.InvokeAsync<string>("sessionStorage.getItem", "currentUser");
        }
        catch (InvalidOperationException e)
        {
            try
            {
                await jsRuntime.InvokeVoidAsync("console.warn", $"[AuthProvider] InvalidOperationException getting sessionStorage: {e.Message}");
            }
            catch { }
            return new AuthenticationState(new());
        }
        
        if (string.IsNullOrEmpty(userAsJson))
        {
            try
            {
                await jsRuntime.InvokeVoidAsync("console.log", "[AuthProvider] No user found in sessionStorage - returning unauthenticated state");
            }
            catch { }
            return new AuthenticationState(new());
        }

        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", $"[AuthProvider] User found in sessionStorage - Length: {userAsJson.Length}");
        }
        catch { }

        UserDto userDto = JsonSerializer.Deserialize<UserDto>(userAsJson)!;
        
        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", $"[AuthProvider] User deserialized from sessionStorage - Id: {userDto.Id}, Username: {userDto.Username}");
        }
        catch { }

        List<Claim> claims = new List<Claim>()
        {
            new Claim(ClaimTypes.Name, userDto.Username),
            new Claim("Id", userDto.Id.ToString()),
        };
        ClaimsIdentity identity = new ClaimsIdentity(claims, "apiauth");
        ClaimsPrincipal claimsPrincipal = new ClaimsPrincipal(identity);

        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", $"[AuthProvider] Returning authenticated state - IsAuthenticated: {claimsPrincipal.Identity?.IsAuthenticated}, Name: {claimsPrincipal.Identity?.Name}");
        }
        catch { }

        return new AuthenticationState(claimsPrincipal);
    }

    public async Task Logout()
    {
        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", "[AuthProvider] Logout called");
        }
        catch { }

        await jsRuntime.InvokeVoidAsync("sessionStorage.setItem", "currentUser", "");
        
        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", "[AuthProvider] User cleared from sessionStorage");
        }
        catch { }

        NotifyAuthenticationStateChanged(Task.FromResult(new AuthenticationState(new())));

        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", "[AuthProvider] AuthenticationStateChanged notified (logged out)");
        }
        catch { }
    }
}