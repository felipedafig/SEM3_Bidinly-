namespace shared.DTOs.Sales
{
    public class GetSaleDto
    {
        public int Id { get; set; }

        public DateTime TimeOfSale { get; set; }
        
        public int PropertyId { get; set; }
        public string? PropertyTitle { get; set; }//enrichment
        
        public string? BuyerUsername { get; set; }//enrichment
        
        public decimal FinalAmount { get; set; }//enrichment
        
        public int WinningBidId { get; set; } 
    }
}
