using System.Net.Http.Headers;
using Microsoft.JSInterop;

namespace BlazorApp.Services;

public class JwtAuthMessageHandler : DelegatingHandler
{
    private readonly IJSRuntime jsRuntime;

    public JwtAuthMessageHandler(IJSRuntime jsRuntime)
    {
        this.jsRuntime = jsRuntime;
    }

    protected override async Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, CancellationToken cancellationToken)
    {
        string? token = null;

        try
        {
            token = await jsRuntime.InvokeAsync<string>("authStorage.getToken");
        }
        catch
        { 
            // JSRuntime not available (prerendering) — skip token injection
        }

        if (!string.IsNullOrWhiteSpace(token))
        {
            request.Headers.Authorization = new AuthenticationHeaderValue("Bearer", token);
        }

        return await base.SendAsync(request, cancellationToken);
    }
}