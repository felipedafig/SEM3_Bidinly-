using System.ComponentModel.DataAnnotations;

namespace Shared.DTOs.Roles
{
    public class CreateRoleDto
    {
        [Required]
        [StringLength(50, MinimumLength = 2, ErrorMessage = "Role name must be between 2 and 50 characters")]
        public string? Name { get; set; }
    }
}

