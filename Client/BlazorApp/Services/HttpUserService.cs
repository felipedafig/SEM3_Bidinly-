using System.Net.Http.Json;
using shared.DTOs.Users;

namespace BlazorApp.Services;

public class HttpUserService
{
    private readonly HttpClient client;

    public HttpUserService(HttpClient client)
    {
        this.client = client;
    }

    public async Task CreateAsync(CreateUserDto dto)
    {
        var response = await client.PostAsJsonAsync("users", dto);
        if (!response.IsSuccessStatusCode)
        {
            string err = await response.Content.ReadAsStringAsync();
            throw new Exception(string.IsNullOrWhiteSpace(err) ? "Unable to create user" : err);
        }
    }
}

