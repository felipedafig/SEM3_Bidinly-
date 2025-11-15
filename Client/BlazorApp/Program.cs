using BlazorApp.Auth;
using BlazorApp.Components;
using Microsoft.AspNetCore.Components.Authorization;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

builder.Services.AddScoped(sp => new HttpClient
{
    BaseAddress = new Uri("http://localhost:5141/")
});

builder.Services.AddScoped<AuthenticationStateProvider, AuthProvider>();

// Minimal authentication services required for [Authorize] attribute in .NET 10
// This satisfies the IAuthenticationService requirement without interfering
// Blazor still uses your AuthProvider for actual authentication via AuthorizeRouteView
builder.Services.AddAuthentication("AlwaysAllow")
    .AddScheme<Microsoft.AspNetCore.Authentication.AuthenticationSchemeOptions, AlwaysAllowAuthenticationHandler>("AlwaysAllow", options => { });
builder.Services.AddAuthorization(options =>
{
    // Allow all requests at HTTP level - Blazor handles authorization via AuthorizeRouteView
    options.FallbackPolicy = new Microsoft.AspNetCore.Authorization.AuthorizationPolicyBuilder()
        .RequireAssertion(_ => true)
        .Build();
});

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    app.UseHsts();
}
app.UseStatusCodePagesWithReExecute("/not-found", createScopeForErrors: true);

app.UseHttpsRedirection();

app.UseAntiforgery();

app.MapStaticAssets();
app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

app.Run();
