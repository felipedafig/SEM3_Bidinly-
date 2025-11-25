namespace BlazorApp.Services;
using System.Net.Http.Json;

using Shared.DTOs.Properties;


public class HttpPropertyService
{
    private readonly HttpClient client;

    public HttpPropertyService(HttpClient client)
    {
        this.client = client;
    }

    public async Task<List<PropertyDto>> GetAllAsync()
    {
        var result = await client.GetFromJsonAsync<List<PropertyDto>>("properties");
        return result ?? new List<PropertyDto>();
    }

    public async Task<PropertyDto?> GetByIdAsync(int id)
    {
        return await client.GetFromJsonAsync<PropertyDto>($"properties/{id}");
    }
}