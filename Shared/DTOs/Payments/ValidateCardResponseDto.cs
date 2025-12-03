using System.Text.Json.Serialization;

namespace shared.DTOs.Payments
{
    public class ValidateCardResponseDto
    {
        [JsonPropertyName("isValid")]
        public bool IsValid { get; set; }
        
        [JsonPropertyName("message")]
        public string Message { get; set; } = string.Empty;
    }
}

