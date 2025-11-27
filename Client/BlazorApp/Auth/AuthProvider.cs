using System.IdentityModel.Tokens.Jwt;
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

    public AuthProvider(HttpAuthService authService, IJSRuntime jsRuntime)
    {
        this.authService = authService;
        this.jsRuntime = jsRuntime;
    }

    public async Task Login(string username, string password)
    {
        var (token, user) = await authService.LoginAsync(username, password);
        await jsRuntime.InvokeVoidAsync("authStorage.saveToken", token);

        // build ClaimsPrincipal from JWT
        var handler = new JwtSecurityTokenHandler();
        var jwt = handler.ReadJwtToken(token);

        var identity = new ClaimsIdentity(jwt.Claims, "jwt");
        var cp = new ClaimsPrincipal(identity);

        NotifyAuthenticationStateChanged(
            Task.FromResult(new AuthenticationState(cp))
        );
    }

    public override async Task<AuthenticationState> GetAuthenticationStateAsync()
    {
        var token = await jsRuntime.InvokeAsync<string?>("authStorage.getToken");

        if (string.IsNullOrEmpty(token))
            return new AuthenticationState(new ClaimsPrincipal());

        var handler = new JwtSecurityTokenHandler();
        var jwt = handler.ReadJwtToken(token);

        var identity = new ClaimsIdentity(jwt.Claims, "jwt");
        var cp = new ClaimsPrincipal(identity);

        return new AuthenticationState(cp);
    }

    public async Task Logout()
    { 
        await jsRuntime.InvokeVoidAsync("authStorage.clearToken");
       
        NotifyAuthenticationStateChanged(
            Task.FromResult(new AuthenticationState(new ClaimsPrincipal()))
        );
    }
    
}