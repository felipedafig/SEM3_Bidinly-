package via.pro3.datatierserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.grpc.SaleServiceGrpc;
import via.pro3.datatierserver.model.Sale;
import via.pro3.datatierserver.repositories.ISaleRepository;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class SaleGrpcService extends SaleServiceGrpc.SaleServiceImplBase {

    @Autowired
    private ISaleRepository saleRepository;

    @Override
    public void getSales(DataTierProto.GetSalesRequest request, StreamObserver<DataTierProto.GetSalesResponse> responseObserver) {
        try {
            List<Sale> sales = saleRepository.findAll();

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
