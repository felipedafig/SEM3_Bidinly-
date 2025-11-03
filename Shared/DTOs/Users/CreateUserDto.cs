using System.ComponentModel.DataAnnotations;

namespace shared.DTOs.Users
{
    public class CreateUserDto
    {
        [Required]
        [StringLength(50, MinimumLength = 5)]
        public string? Username { get; set; }
        
        [Required]
        [StringLength(100, MinimumLength = 5)] 
        public string? Password { get; set; }//hash it

        [Required]
        public int RoleId { get; set; }
        public int Id { get; set; }
    }
}
