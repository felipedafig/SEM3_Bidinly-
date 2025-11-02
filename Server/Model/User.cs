namespace Server.Model
{
    public class User
    {
        public int Id { get; set; }
        
        public string? Username { get; set; }
        
        public string Password { get; set; } = null!;//hash it
        
        public int RoleId { get; set; }
        public Role? Role { get; set; }
    }
}

