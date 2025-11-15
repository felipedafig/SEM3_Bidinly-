using Microsoft.AspNetCore.Authentication;
using Microsoft.Extensions.Options;
using System.Text.Encodings.Web;

namespace BlazorApp.Auth;

/// <summary>
/// Minimal authentication handler that always succeeds.
/// This satisfies the IAuthenticationService requirement for [Authorize] attribute
/// without interfering with Blazor's component-level authorization via AuthProvider.
/// </summary>
public class AlwaysAllowAuthenticationHandler : AuthenticationHandler<AuthenticationSchemeOptions>
{
    public AlwaysAllowAuthenticationHandler(
        IOptionsMonitor<AuthenticationSchemeOptions> options,
        ILoggerFactory logger,
        UrlEncoder encoder)
        : base(options, logger, encoder)
    {
    }

    protected override Task<AuthenticateResult> HandleAuthenticateAsync()
    {
        // Return Success with an authenticated principal - this satisfies HTTP auth requirements
        // Blazor uses AuthenticationStateProvider (your AuthProvider) for actual auth
        var claims = new List<System.Security.Claims.Claim>
        {
            new System.Security.Claims.Claim(System.Security.Claims.ClaimTypes.Name, "AlwaysAllow")
        };
        var identity = new System.Security.Claims.ClaimsIdentity(claims, "AlwaysAllow", System.Security.Claims.ClaimTypes.Name, System.Security.Claims.ClaimTypes.Role);
        var principal = new System.Security.Claims.ClaimsPrincipal(identity);
        var ticket = new AuthenticationTicket(principal, "AlwaysAllow");
        return Task.FromResult(AuthenticateResult.Success(ticket));
    }

    protected override Task HandleChallengeAsync(AuthenticationProperties properties)
    {
        // Don't do anything - let the request continue to Blazor
        // Blazor's AuthorizeRouteView will handle authorization
        return Task.CompletedTask;
    }

    protected override Task HandleForbiddenAsync(AuthenticationProperties properties)
    {
        // Don't block - let the request continue to Blazor
        // Blazor's AuthorizeRouteView will handle authorization using AuthProvider
        Response.StatusCode = 200;
        return Task.CompletedTask;
    }
}

