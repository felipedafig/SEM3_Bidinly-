namespace BlazorApp.Services;

using System.Net.Http.Json;
using Shared.DTOs.Roles;

public class HttpRoleService
{
    private readonly HttpClient client;

    public HttpRoleService(HttpClient client)
    {
        this.client = client;
    }

    public async Task<List<RoleDto>> GetAllAsync()
    {
        var result = await client.GetFromJsonAsync<List<RoleDto>>("roles");
        return result ?? new List<RoleDto>();
    }

    public async Task<RoleDto?> GetByIdAsync(int id)
    {
        return await client.GetFromJsonAsync<RoleDto>($"roles/{id}");
    }
}