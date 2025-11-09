package via.pro3.datatierserver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "\"Sale\"")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    private Integer id;

    @Column(name = "\"TimeOfSale\"", nullable = false)
    @NotNull
    private Instant timeOfSale;

    @Column(name = "\"PropertyId\"", nullable = false)
    @NotNull
    private Integer propertyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"PropertyId\"", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_sold_property"))
    private Property property;

    @Column(name = "\"BidId\"", nullable = false)
    @NotNull
    private Integer bidId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"BidId\"", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_winning_bid"))
    private Bid winningBid;

    @Column(name = "\"BuyerId\"", nullable = false)
    @NotNull
    private Integer buyerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"BuyerId\"", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_selling_buyer"))
    private User buyer;

    @Column(name = "\"AgentId\"", nullable = false)
    @NotNull
    private Integer agentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"AgentId\"", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_selling_agent"))
    private User agent;

    // Constructors
    public Sale() {
    }

    public Sale(Instant timeOfSale, Integer propertyId, Integer bidId,
                Integer buyerId, Integer agentId) {
        this.timeOfSale = timeOfSale;
        this.propertyId = propertyId;
        this.bidId = bidId;
        this.buyerId = buyerId;
        this.agentId = agentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getTimeOfSale() {
        return timeOfSale;
    }

    public void setTimeOfSale(Instant timeOfSale) {
        this.timeOfSale = timeOfSale;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public Bid getWinningBid() {
        return winningBid;
    }

    public void setWinningBid(Bid winningBid) {
        this.winningBid = winningBid;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }
}