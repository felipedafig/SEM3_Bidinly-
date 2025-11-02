namespace shared.DTOs.Properties
{

    public enum PropertyStatus
    {
        Available,
        Sold
    }
    public class GetPropertyDto
    {
        public int Id { get; set; }
        public string? Title { get; set; }
        public string? AgentName { get; set; } //not displaying agentId
        public string? Address { get; set; }
        public decimal StartingPrice { get; set; }
        public int Bedrooms { get; set; }
        public int Bathrooms { get; set; }
        public double SizeInSquareFeet { get; set; }
        public string? Description { get; set; }
        public PropertyStatus Status { get; set; }
        
    }
}