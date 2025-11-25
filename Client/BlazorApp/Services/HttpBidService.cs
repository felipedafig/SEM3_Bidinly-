using Shared.DTOs.Bids;

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

    public async Task<BidDto?> GetByIdAsync(int id)
    {
        return await client.GetFromJsonAsync<BidDto>($"bids/{id}");
    }

    public async Task<BidDto> CreateAsync(CreateBidDto dto)
    {
        var response = await client.PostAsJsonAsync("bids", dto);

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();
            throw new Exception(error);
        }

        var created = await response.Content.ReadFromJsonAsync<BidDto>();
        return created!;
    }

    public async Task DeleteAsync(int id)
    {
        var response = await client.DeleteAsync($"bids/{id}");

        if (!response.IsSuccessStatusCode)
        {
            string error = await response.Content.ReadAsStringAsync();
            throw new Exception(error);
        }
    }
}