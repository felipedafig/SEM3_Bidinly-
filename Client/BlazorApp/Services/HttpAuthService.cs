namespace BlazorApp.Services;

using System.Net.Http.Json;
using Shared.DTOs.Auth;
using shared.DTOs.Users;

public class AuthService
{
    private readonly HttpClient client;

    public AuthService(HttpClient client)
    {
        this.client = client;
    }

    public async Task<UserDto?> LoginAsync(LoginRequestDTO request)
    {
        var response = await client.PostAsJsonAsync("auth/login", request);

        if (!response.IsSuccessStatusCode)
            return null;

        return await response.Content.ReadFromJsonAsync<UserDto>();
    }
}