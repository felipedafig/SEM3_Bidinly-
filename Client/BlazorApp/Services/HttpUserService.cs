namespace BlazorApp.Services;

using System.Net.Http.Json;
using Shared.DTOs.Users;

public class HttpUserService
{
    private readonly HttpClient client;

    public HttpUserService(HttpClient client)
    {
        this.client = client;
    }

    public async Task<UserDto> CreateUserAsync(string username, string password, int roleId)
    {
        var dto = new
        {
            Username = username,
            Password = password,
            RoleId = roleId
        };

        var response = await client.PostAsJsonAsync("users", dto);

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();
            throw new Exception(error);
        }

        return await response.Content.ReadFromJsonAsync<UserDto>()
               ?? throw new Exception("Failed to create user");
    }
}