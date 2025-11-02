namespace shared.DTOs.Users
{
    public class GetUserDto
    {
        public int Id { get; set; }
        public string? Username { get; set; }
        public string? RoleName { get; set; }//we display the role name, readable text
    }
}
