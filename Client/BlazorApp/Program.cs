using BlazorApp.Auth;
using BlazorApp.Components;
using BlazorApp.Services;
using Microsoft.AspNetCore.Components.Authorization;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

builder.Services.AddHttpClient();
builder.Services.AddTransient<JwtAuthMessageHandler>();

builder.Services.AddHttpClient("AuthorizedClient", client =>
    {
        client.BaseAddress = new Uri("https://localhost:7141/");
    })
    .AddHttpMessageHandler<JwtAuthMessageHandler>();

builder.Services.AddScoped(sp =>
{
    var factory = sp.GetRequiredService<IHttpClientFactory>();
    return factory.CreateClient("AuthorizedClient");
});

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
