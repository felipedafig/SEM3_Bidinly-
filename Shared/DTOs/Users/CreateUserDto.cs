using System.ComponentModel.DataAnnotations;

namespace shared.DTOs.Users
{
    public class CreateUserDto
    {
        public int Id { get; set; }
        public string? Username { get; set; }
        public string? Password { get; set; }//hash it
        public int RoleId { get; set; }
        public string? Email { get; set; }
    }
}
