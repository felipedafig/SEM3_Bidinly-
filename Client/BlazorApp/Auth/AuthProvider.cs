using System.Security.Claims;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.JSInterop;
using shared.DTOs.Users;

namespace BlazorApp.Auth;

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

    public override async Task<AuthenticationState> GetAuthenticationStateAsync()
    {
        if (currentClaimsPrincipal != null)
        {
            return new AuthenticationState(currentClaimsPrincipal);
        }
        
        try
        {
            if (jsRuntime != null)
            {
                string? storedUserJson = null;
                int retries = 0;
                while (storedUserJson == null && retries < 5)
                {
                    try
                    {
                        storedUserJson = await jsRuntime.InvokeAsync<string>("sessionStorage.getItem", "authUser");
                        break;
                    }
                    catch (Microsoft.JSInterop.JSException)
                    {
                        retries++;
                        if (retries < 5) await Task.Delay(100);
                    }
                }
                
                if (!string.IsNullOrEmpty(storedUserJson))
                {
                    var user = System.Text.Json.JsonSerializer.Deserialize<UserDto>(
                        storedUserJson, 
                        new System.Text.Json.JsonSerializerOptions { PropertyNameCaseInsensitive = true });
                    
                    if (user != null)
                    {
                        List<Claim> claims = new List<Claim>
                        {
                            new Claim(ClaimTypes.Name, user.Username ?? string.Empty),
                            new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
                            new Claim("Id", user.Id.ToString())
                        };
                        
                        ClaimsIdentity identity = new ClaimsIdentity(claims, "apiauth", ClaimTypes.Name, ClaimTypes.Role);
                        currentClaimsPrincipal = new ClaimsPrincipal(identity);
                    }
                }
            }
        }
        catch (Microsoft.JSInterop.JSException)
        {
        }
        catch (Exception)
        {
            // Other errors - return unauthenticated state
        }
        
        var principal = currentClaimsPrincipal ?? new ClaimsPrincipal();
        return new AuthenticationState(principal);
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
            new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
            new Claim("Id", user.Id.ToString())
        };

        ClaimsIdentity identity = new ClaimsIdentity(claims, "apiauth", ClaimTypes.Name, ClaimTypes.Role);
        currentClaimsPrincipal = new ClaimsPrincipal(identity);

        var isAuth = currentClaimsPrincipal.Identity?.IsAuthenticated ?? false;
        try
        {
            await jsRuntime.InvokeVoidAsync("console.log", $"[AuthProvider] Login complete - IsAuthenticated: {isAuth}, Name: {identity.Name}");
        }
        catch { }

        try
        {
            var userJson = System.Text.Json.JsonSerializer.Serialize(user);
            await jsRuntime.InvokeVoidAsync("sessionStorage.setItem", "authUser", userJson);
            await jsRuntime.InvokeVoidAsync("console.log", "[AuthProvider] User saved to sessionStorage");
        }
        catch (Exception ex)
        {
            try
            {
                await jsRuntime.InvokeVoidAsync("console.error", $"[AuthProvider] Failed to save to sessionStorage: {ex.Message}");
            }
            catch { }
        }

        NotifyAuthenticationStateChanged(Task.FromResult(new AuthenticationState(currentClaimsPrincipal)));
    }

    public Task Logout()
    {
        currentClaimsPrincipal = null;
        NotifyAuthenticationStateChanged(Task.FromResult(new AuthenticationState(new ClaimsPrincipal())));
        return Task.CompletedTask;
    }
}