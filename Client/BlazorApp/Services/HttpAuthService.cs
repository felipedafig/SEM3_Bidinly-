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

    public async Task<(string Token, UserDto User)> LoginAsync(string username, string password)
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

        var result = await response.Content.ReadFromJsonAsync<LoginResponse>()
                     ?? throw new Exception("Invalid login response");

        return (result.Token, result.User);
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