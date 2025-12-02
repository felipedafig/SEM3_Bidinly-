namespace shared.DTOs.Payments
{
    public class ValidateCardResponseDto
    {
        public bool IsValid { get; set; }
        public string Message { get; set; } = string.Empty;
    }
}

