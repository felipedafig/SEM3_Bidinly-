using System.ComponentModel.DataAnnotations;

namespace shared.DTOs.Payments
{
    public class ValidateCardDto
    {
        [Required]
        public string CardNumber { get; set; } = string.Empty;

        [Required]
        public string ExpirationDate { get; set; } = string.Empty;

        [Required]
        public string Cvc { get; set; } = string.Empty;

        [Required]
        public string Name { get; set; } = string.Empty;
    }
}

