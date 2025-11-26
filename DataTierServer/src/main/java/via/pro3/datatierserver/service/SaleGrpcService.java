package via.pro3.datatierserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.grpc.SaleServiceGrpc;
import via.pro3.datatierserver.model.Sale;
import via.pro3.datatierserver.repositories.ISaleRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@GrpcService
public class SaleGrpcService extends SaleServiceGrpc.SaleServiceImplBase {

    @Autowired
    private ISaleRepository saleRepository;

    @Override
    public void createSale(DataTierProto.CreateSaleRequest request, StreamObserver<DataTierProto.SaleResponse> responseObserver) {
        try {
            Sale newSale = new Sale();
            newSale.setTimeOfSale(Instant.ofEpochSecond(request.getTimeOfSaleSeconds()));
            newSale.setPropertyId(request.getPropertyId());
            newSale.setBidId(request.getBidId());
            newSale.setBuyerId(request.getBuyerId());
            newSale.setAgentId(request.getAgentId());
            
            Sale savedSale = saleRepository.save(newSale);
            
            DataTierProto.SaleResponse response = convertToSaleResponse(savedSale);
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error creating sale: " + e.getMessage())
                .asRuntimeException());
        }
    }

    @Override
    public void getSale(DataTierProto.GetSaleRequest request, StreamObserver<DataTierProto.SaleResponse> responseObserver) {
        try {
            Optional<Sale> saleOpt = saleRepository.findById(request.getId());
            
            if (saleOpt.isEmpty()) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("Sale with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            Sale sale = saleOpt.get();
            DataTierProto.SaleResponse response = convertToSaleResponse(sale);
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error retrieving sale: " + e.getMessage())
                .asRuntimeException());
        }
    }

    @Override
    public void getSales(DataTierProto.GetSalesRequest request, StreamObserver<DataTierProto.GetSalesResponse> responseObserver) {
        try {
            List<Sale> sales = saleRepository.findAll();
            
            // Apply filters if provided
            if (request.hasPropertyId() || request.hasBuyerId() || request.hasAgentId()) {
                sales = sales.stream()
                    .filter(sale -> {
                        if (request.hasPropertyId() && (sale.getPropertyId() == null || !sale.getPropertyId().equals(request.getPropertyId()))) {
                            return false;
                        }
                        if (request.hasBuyerId() && (sale.getBuyerId() == null || !sale.getBuyerId().equals(request.getBuyerId()))) {
                            return false;
                        }
                        if (request.hasAgentId() && (sale.getAgentId() == null || !sale.getAgentId().equals(request.getAgentId()))) {
                            return false;
                        }
                        return true;
                    })
                    .collect(Collectors.toList());
            }
            
            List<DataTierProto.SaleResponse> saleResponses = sales.stream()
                .map(this::convertToSaleResponse)
                .collect(Collectors.toList());
            
            DataTierProto.GetSalesResponse response = DataTierProto.GetSalesResponse.newBuilder()
                .addAllSales(saleResponses)
                .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error retrieving sales: " + e.getMessage())
                .asRuntimeException());
        }
    }

    @Override
    public void updateSale(DataTierProto.UpdateSaleRequest request, StreamObserver<DataTierProto.SaleResponse> responseObserver) {
        try {
            Optional<Sale> saleOpt = saleRepository.findById(request.getId());
            
            if (saleOpt.isEmpty()) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("Sale with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            Sale sale = saleOpt.get();
            
            if (request.hasTimeOfSaleSeconds()) {
                sale.setTimeOfSale(Instant.ofEpochSecond(request.getTimeOfSaleSeconds()));
            }
            
            if (request.hasPropertyId()) {
                sale.setPropertyId(request.getPropertyId());
            }
            
            if (request.hasBidId()) {
                sale.setBidId(request.getBidId());
            }
            
            if (request.hasBuyerId()) {
                sale.setBuyerId(request.getBuyerId());
            }
            
            if (request.hasAgentId()) {
                sale.setAgentId(request.getAgentId());
            }
            
            Sale updatedSale = saleRepository.save(sale);
            
            DataTierProto.SaleResponse response = convertToSaleResponse(updatedSale);
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error updating sale: " + e.getMessage())
                .asRuntimeException());
        }
    }

    @Override
    public void deleteSale(DataTierProto.DeleteSaleRequest request, StreamObserver<DataTierProto.DeleteSaleResponse> responseObserver) {
        try {
            Optional<Sale> saleOpt = saleRepository.findById(request.getId());
            
            if (saleOpt.isEmpty()) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("Sale with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            saleRepository.deleteById(request.getId());
            
            DataTierProto.DeleteSaleResponse response = DataTierProto.DeleteSaleResponse.newBuilder()
                .setSuccess(true)
                .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error deleting sale: " + e.getMessage())
                .asRuntimeException());
        }
    }

    private DataTierProto.SaleResponse convertToSaleResponse(Sale sale) {
        long timeOfSaleSeconds = sale.getTimeOfSale() != null 
            ? sale.getTimeOfSale().getEpochSecond() 
            : 0;
        
        return DataTierProto.SaleResponse.newBuilder()
            .setId(sale.getId() != null ? sale.getId() : 0)
            .setTimeOfSaleSeconds(timeOfSaleSeconds)
            .setPropertyId(sale.getPropertyId() != null ? sale.getPropertyId() : 0)
            .setBidId(sale.getBidId() != null ? sale.getBidId() : 0)
            .setBuyerId(sale.getBuyerId() != null ? sale.getBuyerId() : 0)
            .setAgentId(sale.getAgentId() != null ? sale.getAgentId() : 0)
            .build();
    }
}

