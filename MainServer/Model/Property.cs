namespace MainServer.Model
{
    public class Property
    {
        public int Id { get; set; }
        
        public int AgentId { get; set; }
        public User Agent { get; set; } = null!;
        
        public string Title { get; set; } = null!;
        
        public string? Address { get; set; }
        
        public decimal StartingPrice { get; set; }
        
        public int Bedrooms { get; set; }
        
        public int Bathrooms { get; set; }
        
        public double SizeInSquareFeet { get; set; }
        
        public string? Description { get; set; }
        
        public string Status { get; set; } = "Available"; // e.g., "Available", "Sold"
    }
}

