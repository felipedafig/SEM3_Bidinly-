using BlazorApp.Auth;
using BlazorApp.Components;
using BlazorApp.Services;
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

// API services
builder.Services.AddScoped<HttpAuthService>();
builder.Services.AddScoped<HttpRoleService>();
builder.Services.AddScoped<HttpUserService>();
builder.Services.AddScoped<HttpBidService>();
builder.Services.AddScoped<HttpPropertyService>();
builder.Services.AddScoped<HttpSaleService>();

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
