package via.pro3.datatierserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import via.pro3.datatierserver.grpc.BidServiceGrpc;
import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.model.Bid;
import via.pro3.datatierserver.repositories.IBidRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@GrpcService
public class BidGrpcService extends BidServiceGrpc.BidServiceImplBase {

    @Autowired
    private IBidRepository bidRepository;

    @Override
    public void createBid(DataTierProto.CreateBidRequest request, StreamObserver<DataTierProto.BidResponse> responseObserver) {
        try {
            Bid newBid = new Bid();
            newBid.setBuyerId(request.getBuyerId());
            newBid.setPropertyId(request.getPropertyId());
            newBid.setAmount(BigDecimal.valueOf(request.getAmount()));
            newBid.setExpiryDate(Instant.ofEpochSecond(request.getExpiryDateSeconds()));
            newBid.setStatus("Pending");
            newBid.setDeal("");

            if (request.hasDeal()) {newBid.setDeal(request.getDeal());}

            Bid savedBid = bidRepository.save(newBid);
            
            DataTierProto.BidResponse response = convertToBidResponse(savedBid);
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error creating bid: " + e.getMessage())
                .asRuntimeException());
        }
    }

    @Override
    public void getBids(DataTierProto.GetBidsRequest request, StreamObserver<DataTierProto.GetBidsResponse> responseObserver) {
        try {
            List<Bid> bids = bidRepository.getMany();
            
            updateExpiredBids(bids);
            
            if (request.hasPropertyId() || request.hasBuyerId() || request.hasStatus()) {
                bids = bids.stream()
                    .filter(bid -> {
                        if (request.hasPropertyId() && (bid.getPropertyId() == null || !bid.getPropertyId().equals(request.getPropertyId()))) {
                            return false;
                        }
                        if (request.hasBuyerId() && (bid.getBuyerId() == null || !bid.getBuyerId().equals(request.getBuyerId()))) {
                            return false;
                        }
                        if (request.hasStatus() && (bid.getStatus() == null || !bid.getStatus().equals(request.getStatus()))) {
                            return false;
                        }
                        return true;
                    })
                    .collect(Collectors.toList());
            }
            
            List<DataTierProto.BidResponse> bidResponses = bids.stream()
                .map(this::convertToBidResponse)
                .collect(Collectors.toList());
            
            DataTierProto.GetBidsResponse response = DataTierProto.GetBidsResponse.newBuilder()
                .addAllBids(bidResponses)
                .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error retrieving bids: " + e.getMessage())
                .asRuntimeException());
        }
    }

    @Override
    public void getBid(DataTierProto.GetBidRequest request, StreamObserver<DataTierProto.BidResponse> responseObserver) {
        try {
            Optional<Bid> bidOpt = bidRepository.getSingle(request.getId());
            
            if (bidOpt.isEmpty()) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("Bid with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            Bid bid = bidOpt.get();
            
            updateExpiredBid(bid);
            
            DataTierProto.BidResponse response = convertToBidResponse(bid);
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error retrieving bid: " + e.getMessage())
                .asRuntimeException());
        }
    }

    @Override
    public void updateBid(DataTierProto.UpdateBidRequest request, StreamObserver<DataTierProto.BidResponse> responseObserver) {
        try {
            Optional<Bid> bidOpt = bidRepository.getSingle(request.getId());
            
            if (bidOpt.isEmpty()) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("Bid with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            Bid bid = bidOpt.get();
            
            if (request.hasAmount()) {
                bid.setAmount(BigDecimal.valueOf(request.getAmount()));
            }
            
            if (request.hasExpiryDateSeconds()) {
                bid.setExpiryDate(Instant.ofEpochSecond(request.getExpiryDateSeconds()));
            }
            
            if (request.hasStatus()) {
                bid.setStatus(request.getStatus());
            }

            if (request.hasDeal()) {
                bid.setDeal(request.getDeal());
            }
            
            Bid updatedBid = bidRepository.save(bid);
            
            DataTierProto.BidResponse response = convertToBidResponse(updatedBid);
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error updating bid: " + e.getMessage())
                .asRuntimeException());
        }
    }

    @Override
    public void deleteBid(DataTierProto.DeleteBidRequest request, StreamObserver<DataTierProto.DeleteBidResponse> responseObserver) {
        try {
            Optional<Bid> bidOpt = bidRepository.getSingle(request.getId());
            
            if (bidOpt.isEmpty()) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("Bid with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            bidRepository.deleteById(request.getId());
            
            DataTierProto.DeleteBidResponse response = DataTierProto.DeleteBidResponse.newBuilder()
                .setSuccess(true)
                .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error deleting bid: " + e.getMessage())
                .asRuntimeException());
        }
    }

    private void updateExpiredBids(List<Bid> bids) {
        for (Bid bid : bids) {
            updateExpiredBid(bid);
        }
    }

    private boolean updateExpiredBid(Bid bid) {
        Instant now = Instant.now();
        
        if (bid.getExpiryDate() != null 
            && bid.getExpiryDate().isBefore(now) 
            && "Pending".equals(bid.getStatus())) {
            
            bid.setStatus("Expired");
            bidRepository.save(bid);
            return true;
        }
        
        return false;
    }

    private DataTierProto.BidResponse convertToBidResponse(Bid bid) {
        long expiryDateSeconds = bid.getExpiryDate() != null 
            ? bid.getExpiryDate().getEpochSecond() 
            : 0;
        
        return DataTierProto.BidResponse.newBuilder()
            .setId(bid.getId() != null ? bid.getId() : 0)
            .setBuyerId(bid.getBuyerId() != null ? bid.getBuyerId() : 0)
            .setPropertyId(bid.getPropertyId() != null ? bid.getPropertyId() : 0)
            .setAmount(bid.getAmount() != null ? bid.getAmount().doubleValue() : 0.0)
            .setExpiryDateSeconds(expiryDateSeconds)
                .setStatus(bid.getStatus() != null ? bid.getStatus() : "Pending")
                .setDeal(bid.getDeal() != null ? bid.getDeal() : "")
            .build();
    }

    @Override
    public void setBidStatus(DataTierProto.SetBidStatusRequest request,
                             StreamObserver<DataTierProto.BidResponse> responseObserver) {
        try {
            Optional<Bid> bidOpt = bidRepository.getSingle(request.getId());

            if (bidOpt.isEmpty()) {
                responseObserver.onError(
                        io.grpc.Status.NOT_FOUND
                                .withDescription("Bid with id " + request.getId() + " not found")
                                .asRuntimeException()
                );
                return;
            }

            Bid acceptedBid = bidOpt.get();

            acceptedBid.setStatus(request.getStatus());
            bidRepository.save(acceptedBid);

            if ("Accepted".equals(request.getStatus())) {
                List<Bid> allBids = bidRepository.getMany();

                for (Bid bid : allBids) {
                    if (!bid.getId().equals(acceptedBid.getId()) &&
                            bid.getPropertyId().equals(acceptedBid.getPropertyId()) &&
                            !"Rejected".equals(bid.getStatus())) {

                        bid.setStatus("Rejected");
                        bidRepository.save(bid);
                    }
                }
            }

            DataTierProto.BidResponse response = convertToBidResponse(acceptedBid);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Error updating bid status: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}

