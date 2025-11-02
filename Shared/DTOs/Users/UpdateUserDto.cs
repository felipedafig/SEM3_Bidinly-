using System.ComponentModel.DataAnnotations;

namespace shared.DTOs.Users
{
    public class UpdateUserDto
    {
        [Required]
        public int Id { get; set; }

        [StringLength(50, MinimumLength = 5)]
        public string? Username { get; set; }

        //Password update??
    }
}
