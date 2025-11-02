using System.ComponentModel.DataAnnotations;

namespace shared.DTOs.Properties
{
   
    public class CreatePropertyDto
    {
        [Required] 
        public int AgentId { get; set; } //links to the agent creating the property

        [Required]
        [StringLength(2000, MinimumLength = 1)]
        public string Title { get; set; }
        
        [Required]
        public string? Address { get; set; }

        [Required]
        public decimal StartingPrice { get; set; }

        [Required]
        [Range(0, 20)] //positive integer
        public int Bedrooms { get; set; }

        [Required]
        [Range(0, 20)] //positive integer
        public int Bathrooms { get; set; }

        [Required]
        public double SizeInSquareFeet { get; set; }

        [StringLength(5000)]
        public string? Description { get; set; }

    }
}
