using BlazorApp.Auth;
using BlazorApp.Components;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Components.Authorization;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

builder.Services.AddScoped(sp => new HttpClient
{
    BaseAddress = new Uri("http://localhost:5141/")
});

builder.Services.AddAuthentication(options =>
{
    options.DefaultAuthenticateScheme = AuthCookieAuthenticationHandler.SchemeName;
    options.DefaultChallengeScheme = AuthCookieAuthenticationHandler.SchemeName;
}).AddScheme<AuthCookieAuthenticationOptions, AuthCookieAuthenticationHandler>(
    AuthCookieAuthenticationHandler.SchemeName, _ => { });

builder.Services.AddAuthorization();

builder.Services.AddScoped<AuthenticationStateProvider, AuthProvider>();


var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    app.UseHsts();
}

app.UseStatusCodePagesWithReExecute("/not-found");

app.UseHttpsRedirection();

app.UseAuthentication();
app.UseAuthorization();

app.UseStaticFiles();

app.UseAntiforgery();

app.MapStaticAssets();
app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

app.Run();
