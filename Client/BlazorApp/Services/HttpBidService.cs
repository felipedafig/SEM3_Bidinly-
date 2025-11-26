using System.Net.Http.Json;
using Shared.DTOs.Bids;
using shared.DTOs.Bids;

namespace BlazorApp.Services;

public class HttpBidService
{
    private readonly HttpClient client;

    public HttpBidService(HttpClient client)
    {
        this.client = client;
    }

    public async Task<List<BidDto>> GetAllAsync()
    {
        var bids = await client.GetFromJsonAsync<List<BidDto>>("bids");
        return bids ?? new List<BidDto>();
    }

    public async Task<BidDto> CreateAsync(CreateBidDto dto)
    {
        var response = await client.PostAsJsonAsync("bids", dto);
        if (!response.IsSuccessStatusCode)
        {
            string err = await response.Content.ReadAsStringAsync();
            throw new Exception(string.IsNullOrWhiteSpace(err) ? "Failed to create bid" : err);
        }

        return await response.Content.ReadFromJsonAsync<BidDto>()
               ?? throw new Exception("Failed to deserialize bid");
    }

    public async Task DeleteAsync(int id)
    {
        var response = await client.DeleteAsync($"bids/{id}");
        if (!response.IsSuccessStatusCode)
        {
            string err = await response.Content.ReadAsStringAsync();
            throw new Exception(string.IsNullOrWhiteSpace(err) ? $"Failed to delete bid {id}" : err);
        }
    }
}

