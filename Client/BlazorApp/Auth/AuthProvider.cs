using System.Security.Claims;
using System.Text.Json;
using BlazorApp.Services;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.JSInterop;
using Shared.DTOs.Users;

namespace BlazorApp.Auth;

public class AuthProvider : AuthenticationStateProvider
{
    
    private readonly IJSRuntime jsRuntime;
    private readonly HttpAuthService authService;
    private string userAsJson = string.Empty;

    public AuthProvider(HttpAuthService authService, IJSRuntime jsRuntime)
    {
        this.authService = authService;
        this.jsRuntime = jsRuntime;
    }

    private Task<ClaimsPrincipal> CreateClaimsPrincipalAsync(UserDto userDto)
    {
        var claims = new List<Claim>
        {
            new Claim(ClaimTypes.Name, userDto.Username ?? ""),
            new Claim("Id", userDto.Id.ToString()),
            new Claim("RoleName", userDto.RoleName ?? "")
        };

        var identity = new ClaimsIdentity(claims, "apiauth");
        return Task.FromResult(new ClaimsPrincipal(identity));
    }

    public async Task Login(string username, string password)
    {
        UserDto userDto = await authService.LoginAsync(username, password);
        userAsJson = JsonSerializer.Serialize(userDto);

        // Save to browser storage ONLY if JS is available (not during prerender)
        if (jsRuntime is IJSInProcessRuntime)
        {
            await jsRuntime.InvokeVoidAsync("authStorage.saveUser", userAsJson);
        }

        ClaimsPrincipal cp = await CreateClaimsPrincipalAsync(userDto);

        NotifyAuthenticationStateChanged(
            Task.FromResult(new AuthenticationState(cp))
        );
    }

    public override async Task<AuthenticationState> GetAuthenticationStateAsync()
    {
        if (jsRuntime is not IJSInProcessRuntime)
        {
            return new AuthenticationState(new ClaimsPrincipal());
        }

        string? stored = await jsRuntime.InvokeAsync<string?>("authStorage.getUser");
        userAsJson = stored ?? string.Empty;

        if (string.IsNullOrEmpty(userAsJson))
            return new AuthenticationState(new ClaimsPrincipal());

        UserDto userDto = JsonSerializer.Deserialize<UserDto>(userAsJson)!;
        ClaimsPrincipal cp = await CreateClaimsPrincipalAsync(userDto);

        return new AuthenticationState(cp);
    }

    public async Task Logout()
    {
        if (jsRuntime is IJSInProcessRuntime)
        {
            await jsRuntime.InvokeVoidAsync("authStorage.clearUser");
        }

        NotifyAuthenticationStateChanged(
            Task.FromResult(new AuthenticationState(new ClaimsPrincipal()))
        );
    }
    
}