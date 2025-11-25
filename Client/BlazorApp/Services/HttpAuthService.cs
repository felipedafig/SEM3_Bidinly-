namespace BlazorApp.Services;

using System.Net.Http.Json;
using Shared.DTOs.Auth;
using Shared.DTOs.Users;

public class HttpAuthService
{
    private readonly HttpClient client;

    public HttpAuthService(HttpClient client)
    {
        this.client = client;
    }

    public async Task<UserDto> LoginAsync(string username, string password)
    {
        var dto = new LoginRequestDTO
        {
            Username = username,
            Password = password
        };

        var response = await client.PostAsJsonAsync("auth/login", dto);

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();
            throw new Exception(error);
        }

        return await response.Content.ReadFromJsonAsync<UserDto>()
               ?? throw new Exception("Failed to deserialize login response");
    }

    public async Task CreateUserAsync(string username, string password, int roleId)
    {
        var signUpDto = new
        {
            Username = username,
            Password = password,
            RoleId = roleId
        };

        var response = await client.PostAsJsonAsync("users", signUpDto);

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();
            throw new Exception(error);
        }
    }
}