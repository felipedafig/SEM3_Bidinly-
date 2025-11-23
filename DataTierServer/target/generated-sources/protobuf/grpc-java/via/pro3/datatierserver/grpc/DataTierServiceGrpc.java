package via.pro3.datatierserver.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.60.1)",
    comments = "Source: datatier.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class DataTierServiceGrpc {

  private DataTierServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "datatier.DataTierService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.CreateBidRequest,
      via.pro3.datatierserver.grpc.DataTierProto.BidResponse> getCreateBidMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateBid",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.CreateBidRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.BidResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.CreateBidRequest,
      via.pro3.datatierserver.grpc.DataTierProto.BidResponse> getCreateBidMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.CreateBidRequest, via.pro3.datatierserver.grpc.DataTierProto.BidResponse> getCreateBidMethod;
    if ((getCreateBidMethod = DataTierServiceGrpc.getCreateBidMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getCreateBidMethod = DataTierServiceGrpc.getCreateBidMethod) == null) {
          DataTierServiceGrpc.getCreateBidMethod = getCreateBidMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.CreateBidRequest, via.pro3.datatierserver.grpc.DataTierProto.BidResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateBid"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.CreateBidRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.BidResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("CreateBid"))
              .build();
        }
      }
    }
    return getCreateBidMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetBidRequest,
      via.pro3.datatierserver.grpc.DataTierProto.BidResponse> getGetBidMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBid",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.GetBidRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.BidResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetBidRequest,
      via.pro3.datatierserver.grpc.DataTierProto.BidResponse> getGetBidMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetBidRequest, via.pro3.datatierserver.grpc.DataTierProto.BidResponse> getGetBidMethod;
    if ((getGetBidMethod = DataTierServiceGrpc.getGetBidMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getGetBidMethod = DataTierServiceGrpc.getGetBidMethod) == null) {
          DataTierServiceGrpc.getGetBidMethod = getGetBidMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.GetBidRequest, via.pro3.datatierserver.grpc.DataTierProto.BidResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetBid"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetBidRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.BidResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("GetBid"))
              .build();
        }
      }
    }
    return getGetBidMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetBidsRequest,
      via.pro3.datatierserver.grpc.DataTierProto.GetBidsResponse> getGetBidsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBids",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.GetBidsRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.GetBidsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetBidsRequest,
      via.pro3.datatierserver.grpc.DataTierProto.GetBidsResponse> getGetBidsMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetBidsRequest, via.pro3.datatierserver.grpc.DataTierProto.GetBidsResponse> getGetBidsMethod;
    if ((getGetBidsMethod = DataTierServiceGrpc.getGetBidsMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getGetBidsMethod = DataTierServiceGrpc.getGetBidsMethod) == null) {
          DataTierServiceGrpc.getGetBidsMethod = getGetBidsMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.GetBidsRequest, via.pro3.datatierserver.grpc.DataTierProto.GetBidsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetBids"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetBidsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetBidsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("GetBids"))
              .build();
        }
      }
    }
    return getGetBidsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.UpdateBidRequest,
      via.pro3.datatierserver.grpc.DataTierProto.BidResponse> getUpdateBidMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateBid",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.UpdateBidRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.BidResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.UpdateBidRequest,
      via.pro3.datatierserver.grpc.DataTierProto.BidResponse> getUpdateBidMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.UpdateBidRequest, via.pro3.datatierserver.grpc.DataTierProto.BidResponse> getUpdateBidMethod;
    if ((getUpdateBidMethod = DataTierServiceGrpc.getUpdateBidMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getUpdateBidMethod = DataTierServiceGrpc.getUpdateBidMethod) == null) {
          DataTierServiceGrpc.getUpdateBidMethod = getUpdateBidMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.UpdateBidRequest, via.pro3.datatierserver.grpc.DataTierProto.BidResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateBid"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.UpdateBidRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.BidResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("UpdateBid"))
              .build();
        }
      }
    }
    return getUpdateBidMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.DeleteBidRequest,
      via.pro3.datatierserver.grpc.DataTierProto.DeleteBidResponse> getDeleteBidMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteBid",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.DeleteBidRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.DeleteBidResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.DeleteBidRequest,
      via.pro3.datatierserver.grpc.DataTierProto.DeleteBidResponse> getDeleteBidMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.DeleteBidRequest, via.pro3.datatierserver.grpc.DataTierProto.DeleteBidResponse> getDeleteBidMethod;
    if ((getDeleteBidMethod = DataTierServiceGrpc.getDeleteBidMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getDeleteBidMethod = DataTierServiceGrpc.getDeleteBidMethod) == null) {
          DataTierServiceGrpc.getDeleteBidMethod = getDeleteBidMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.DeleteBidRequest, via.pro3.datatierserver.grpc.DataTierProto.DeleteBidResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteBid"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.DeleteBidRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.DeleteBidResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("DeleteBid"))
              .build();
        }
      }
    }
    return getDeleteBidMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.CreatePropertyRequest,
      via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> getCreatePropertyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateProperty",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.CreatePropertyRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.CreatePropertyRequest,
      via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> getCreatePropertyMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.CreatePropertyRequest, via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> getCreatePropertyMethod;
    if ((getCreatePropertyMethod = DataTierServiceGrpc.getCreatePropertyMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getCreatePropertyMethod = DataTierServiceGrpc.getCreatePropertyMethod) == null) {
          DataTierServiceGrpc.getCreatePropertyMethod = getCreatePropertyMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.CreatePropertyRequest, via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateProperty"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.CreatePropertyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("CreateProperty"))
              .build();
        }
      }
    }
    return getCreatePropertyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetPropertyRequest,
      via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> getGetPropertyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProperty",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.GetPropertyRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetPropertyRequest,
      via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> getGetPropertyMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetPropertyRequest, via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> getGetPropertyMethod;
    if ((getGetPropertyMethod = DataTierServiceGrpc.getGetPropertyMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getGetPropertyMethod = DataTierServiceGrpc.getGetPropertyMethod) == null) {
          DataTierServiceGrpc.getGetPropertyMethod = getGetPropertyMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.GetPropertyRequest, via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProperty"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetPropertyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("GetProperty"))
              .build();
        }
      }
    }
    return getGetPropertyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesRequest,
      via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesResponse> getGetPropertiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProperties",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesRequest,
      via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesResponse> getGetPropertiesMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesRequest, via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesResponse> getGetPropertiesMethod;
    if ((getGetPropertiesMethod = DataTierServiceGrpc.getGetPropertiesMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getGetPropertiesMethod = DataTierServiceGrpc.getGetPropertiesMethod) == null) {
          DataTierServiceGrpc.getGetPropertiesMethod = getGetPropertiesMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesRequest, via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProperties"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("GetProperties"))
              .build();
        }
      }
    }
    return getGetPropertiesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.UpdatePropertyRequest,
      via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> getUpdatePropertyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateProperty",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.UpdatePropertyRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.UpdatePropertyRequest,
      via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> getUpdatePropertyMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.UpdatePropertyRequest, via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> getUpdatePropertyMethod;
    if ((getUpdatePropertyMethod = DataTierServiceGrpc.getUpdatePropertyMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getUpdatePropertyMethod = DataTierServiceGrpc.getUpdatePropertyMethod) == null) {
          DataTierServiceGrpc.getUpdatePropertyMethod = getUpdatePropertyMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.UpdatePropertyRequest, via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateProperty"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.UpdatePropertyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("UpdateProperty"))
              .build();
        }
      }
    }
    return getUpdatePropertyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyRequest,
      via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyResponse> getDeletePropertyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteProperty",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyRequest,
      via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyResponse> getDeletePropertyMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyRequest, via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyResponse> getDeletePropertyMethod;
    if ((getDeletePropertyMethod = DataTierServiceGrpc.getDeletePropertyMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getDeletePropertyMethod = DataTierServiceGrpc.getDeletePropertyMethod) == null) {
          DataTierServiceGrpc.getDeletePropertyMethod = getDeletePropertyMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyRequest, via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteProperty"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("DeleteProperty"))
              .build();
        }
      }
    }
    return getDeletePropertyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.CreateUserRequest,
      via.pro3.datatierserver.grpc.DataTierProto.UserResponse> getCreateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateUser",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.CreateUserRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.UserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.CreateUserRequest,
      via.pro3.datatierserver.grpc.DataTierProto.UserResponse> getCreateUserMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.CreateUserRequest, via.pro3.datatierserver.grpc.DataTierProto.UserResponse> getCreateUserMethod;
    if ((getCreateUserMethod = DataTierServiceGrpc.getCreateUserMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getCreateUserMethod = DataTierServiceGrpc.getCreateUserMethod) == null) {
          DataTierServiceGrpc.getCreateUserMethod = getCreateUserMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.CreateUserRequest, via.pro3.datatierserver.grpc.DataTierProto.UserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.CreateUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.UserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("CreateUser"))
              .build();
        }
      }
    }
    return getCreateUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetUserRequest,
      via.pro3.datatierserver.grpc.DataTierProto.UserResponse> getGetUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUser",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.GetUserRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.UserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetUserRequest,
      via.pro3.datatierserver.grpc.DataTierProto.UserResponse> getGetUserMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetUserRequest, via.pro3.datatierserver.grpc.DataTierProto.UserResponse> getGetUserMethod;
    if ((getGetUserMethod = DataTierServiceGrpc.getGetUserMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getGetUserMethod = DataTierServiceGrpc.getGetUserMethod) == null) {
          DataTierServiceGrpc.getGetUserMethod = getGetUserMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.GetUserRequest, via.pro3.datatierserver.grpc.DataTierProto.UserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.UserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("GetUser"))
              .build();
        }
      }
    }
    return getGetUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetUsersRequest,
      via.pro3.datatierserver.grpc.DataTierProto.GetUsersResponse> getGetUsersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUsers",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.GetUsersRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.GetUsersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetUsersRequest,
      via.pro3.datatierserver.grpc.DataTierProto.GetUsersResponse> getGetUsersMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetUsersRequest, via.pro3.datatierserver.grpc.DataTierProto.GetUsersResponse> getGetUsersMethod;
    if ((getGetUsersMethod = DataTierServiceGrpc.getGetUsersMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getGetUsersMethod = DataTierServiceGrpc.getGetUsersMethod) == null) {
          DataTierServiceGrpc.getGetUsersMethod = getGetUsersMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.GetUsersRequest, via.pro3.datatierserver.grpc.DataTierProto.GetUsersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUsers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetUsersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetUsersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("GetUsers"))
              .build();
        }
      }
    }
    return getGetUsersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.UpdateUserRequest,
      via.pro3.datatierserver.grpc.DataTierProto.UserResponse> getUpdateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateUser",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.UpdateUserRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.UserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.UpdateUserRequest,
      via.pro3.datatierserver.grpc.DataTierProto.UserResponse> getUpdateUserMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.UpdateUserRequest, via.pro3.datatierserver.grpc.DataTierProto.UserResponse> getUpdateUserMethod;
    if ((getUpdateUserMethod = DataTierServiceGrpc.getUpdateUserMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getUpdateUserMethod = DataTierServiceGrpc.getUpdateUserMethod) == null) {
          DataTierServiceGrpc.getUpdateUserMethod = getUpdateUserMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.UpdateUserRequest, via.pro3.datatierserver.grpc.DataTierProto.UserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.UpdateUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.UserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("UpdateUser"))
              .build();
        }
      }
    }
    return getUpdateUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.DeleteUserRequest,
      via.pro3.datatierserver.grpc.DataTierProto.DeleteUserResponse> getDeleteUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteUser",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.DeleteUserRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.DeleteUserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.DeleteUserRequest,
      via.pro3.datatierserver.grpc.DataTierProto.DeleteUserResponse> getDeleteUserMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.DeleteUserRequest, via.pro3.datatierserver.grpc.DataTierProto.DeleteUserResponse> getDeleteUserMethod;
    if ((getDeleteUserMethod = DataTierServiceGrpc.getDeleteUserMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getDeleteUserMethod = DataTierServiceGrpc.getDeleteUserMethod) == null) {
          DataTierServiceGrpc.getDeleteUserMethod = getDeleteUserMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.DeleteUserRequest, via.pro3.datatierserver.grpc.DataTierProto.DeleteUserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.DeleteUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.DeleteUserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("DeleteUser"))
              .build();
        }
      }
    }
    return getDeleteUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.CreateSaleRequest,
      via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> getCreateSaleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateSale",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.CreateSaleRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.SaleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.CreateSaleRequest,
      via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> getCreateSaleMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.CreateSaleRequest, via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> getCreateSaleMethod;
    if ((getCreateSaleMethod = DataTierServiceGrpc.getCreateSaleMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getCreateSaleMethod = DataTierServiceGrpc.getCreateSaleMethod) == null) {
          DataTierServiceGrpc.getCreateSaleMethod = getCreateSaleMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.CreateSaleRequest, via.pro3.datatierserver.grpc.DataTierProto.SaleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateSale"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.CreateSaleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.SaleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("CreateSale"))
              .build();
        }
      }
    }
    return getCreateSaleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetSaleRequest,
      via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> getGetSaleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSale",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.GetSaleRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.SaleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetSaleRequest,
      via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> getGetSaleMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetSaleRequest, via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> getGetSaleMethod;
    if ((getGetSaleMethod = DataTierServiceGrpc.getGetSaleMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getGetSaleMethod = DataTierServiceGrpc.getGetSaleMethod) == null) {
          DataTierServiceGrpc.getGetSaleMethod = getGetSaleMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.GetSaleRequest, via.pro3.datatierserver.grpc.DataTierProto.SaleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSale"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetSaleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.SaleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("GetSale"))
              .build();
        }
      }
    }
    return getGetSaleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetSalesRequest,
      via.pro3.datatierserver.grpc.DataTierProto.GetSalesResponse> getGetSalesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSales",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.GetSalesRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.GetSalesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetSalesRequest,
      via.pro3.datatierserver.grpc.DataTierProto.GetSalesResponse> getGetSalesMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetSalesRequest, via.pro3.datatierserver.grpc.DataTierProto.GetSalesResponse> getGetSalesMethod;
    if ((getGetSalesMethod = DataTierServiceGrpc.getGetSalesMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getGetSalesMethod = DataTierServiceGrpc.getGetSalesMethod) == null) {
          DataTierServiceGrpc.getGetSalesMethod = getGetSalesMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.GetSalesRequest, via.pro3.datatierserver.grpc.DataTierProto.GetSalesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSales"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetSalesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetSalesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("GetSales"))
              .build();
        }
      }
    }
    return getGetSalesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.UpdateSaleRequest,
      via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> getUpdateSaleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateSale",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.UpdateSaleRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.SaleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.UpdateSaleRequest,
      via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> getUpdateSaleMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.UpdateSaleRequest, via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> getUpdateSaleMethod;
    if ((getUpdateSaleMethod = DataTierServiceGrpc.getUpdateSaleMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getUpdateSaleMethod = DataTierServiceGrpc.getUpdateSaleMethod) == null) {
          DataTierServiceGrpc.getUpdateSaleMethod = getUpdateSaleMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.UpdateSaleRequest, via.pro3.datatierserver.grpc.DataTierProto.SaleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateSale"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.UpdateSaleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.SaleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("UpdateSale"))
              .build();
        }
      }
    }
    return getUpdateSaleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleRequest,
      via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleResponse> getDeleteSaleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteSale",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleRequest,
      via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleResponse> getDeleteSaleMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleRequest, via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleResponse> getDeleteSaleMethod;
    if ((getDeleteSaleMethod = DataTierServiceGrpc.getDeleteSaleMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getDeleteSaleMethod = DataTierServiceGrpc.getDeleteSaleMethod) == null) {
          DataTierServiceGrpc.getDeleteSaleMethod = getDeleteSaleMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleRequest, via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteSale"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("DeleteSale"))
              .build();
        }
      }
    }
    return getDeleteSaleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetRoleRequest,
      via.pro3.datatierserver.grpc.DataTierProto.GetRoleResponse> getGetRoleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRole",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.GetRoleRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.GetRoleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetRoleRequest,
      via.pro3.datatierserver.grpc.DataTierProto.GetRoleResponse> getGetRoleMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.GetRoleRequest, via.pro3.datatierserver.grpc.DataTierProto.GetRoleResponse> getGetRoleMethod;
    if ((getGetRoleMethod = DataTierServiceGrpc.getGetRoleMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getGetRoleMethod = DataTierServiceGrpc.getGetRoleMethod) == null) {
          DataTierServiceGrpc.getGetRoleMethod = getGetRoleMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.GetRoleRequest, via.pro3.datatierserver.grpc.DataTierProto.GetRoleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRole"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetRoleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.GetRoleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("GetRole"))
              .build();
        }
      }
    }
    return getGetRoleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.LoginRequest,
      via.pro3.datatierserver.grpc.DataTierProto.LoginResponse> getAuthenticateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AuthenticateUser",
      requestType = via.pro3.datatierserver.grpc.DataTierProto.LoginRequest.class,
      responseType = via.pro3.datatierserver.grpc.DataTierProto.LoginResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.LoginRequest,
      via.pro3.datatierserver.grpc.DataTierProto.LoginResponse> getAuthenticateUserMethod() {
    io.grpc.MethodDescriptor<via.pro3.datatierserver.grpc.DataTierProto.LoginRequest, via.pro3.datatierserver.grpc.DataTierProto.LoginResponse> getAuthenticateUserMethod;
    if ((getAuthenticateUserMethod = DataTierServiceGrpc.getAuthenticateUserMethod) == null) {
      synchronized (DataTierServiceGrpc.class) {
        if ((getAuthenticateUserMethod = DataTierServiceGrpc.getAuthenticateUserMethod) == null) {
          DataTierServiceGrpc.getAuthenticateUserMethod = getAuthenticateUserMethod =
              io.grpc.MethodDescriptor.<via.pro3.datatierserver.grpc.DataTierProto.LoginRequest, via.pro3.datatierserver.grpc.DataTierProto.LoginResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AuthenticateUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.LoginRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.datatierserver.grpc.DataTierProto.LoginResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DataTierServiceMethodDescriptorSupplier("AuthenticateUser"))
              .build();
        }
      }
    }
    return getAuthenticateUserMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DataTierServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataTierServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataTierServiceStub>() {
        @java.lang.Override
        public DataTierServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataTierServiceStub(channel, callOptions);
        }
      };
    return DataTierServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DataTierServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataTierServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataTierServiceBlockingStub>() {
        @java.lang.Override
        public DataTierServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataTierServiceBlockingStub(channel, callOptions);
        }
      };
    return DataTierServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DataTierServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataTierServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataTierServiceFutureStub>() {
        @java.lang.Override
        public DataTierServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataTierServiceFutureStub(channel, callOptions);
        }
      };
    return DataTierServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Bid operations
     * </pre>
     */
    default void createBid(via.pro3.datatierserver.grpc.DataTierProto.CreateBidRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.BidResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateBidMethod(), responseObserver);
    }

    /**
     */
    default void getBid(via.pro3.datatierserver.grpc.DataTierProto.GetBidRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.BidResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBidMethod(), responseObserver);
    }

    /**
     */
    default void getBids(via.pro3.datatierserver.grpc.DataTierProto.GetBidsRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetBidsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBidsMethod(), responseObserver);
    }

    /**
     */
    default void updateBid(via.pro3.datatierserver.grpc.DataTierProto.UpdateBidRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.BidResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateBidMethod(), responseObserver);
    }

    /**
     */
    default void deleteBid(via.pro3.datatierserver.grpc.DataTierProto.DeleteBidRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.DeleteBidResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteBidMethod(), responseObserver);
    }

    /**
     * <pre>
     * Property operations
     * </pre>
     */
    default void createProperty(via.pro3.datatierserver.grpc.DataTierProto.CreatePropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreatePropertyMethod(), responseObserver);
    }

    /**
     */
    default void getProperty(via.pro3.datatierserver.grpc.DataTierProto.GetPropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPropertyMethod(), responseObserver);
    }

    /**
     */
    default void getProperties(via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPropertiesMethod(), responseObserver);
    }

    /**
     */
    default void updateProperty(via.pro3.datatierserver.grpc.DataTierProto.UpdatePropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdatePropertyMethod(), responseObserver);
    }

    /**
     */
    default void deleteProperty(via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeletePropertyMethod(), responseObserver);
    }

    /**
     * <pre>
     * User operations
     * </pre>
     */
    default void createUser(via.pro3.datatierserver.grpc.DataTierProto.CreateUserRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.UserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateUserMethod(), responseObserver);
    }

    /**
     */
    default void getUser(via.pro3.datatierserver.grpc.DataTierProto.GetUserRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.UserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUserMethod(), responseObserver);
    }

    /**
     */
    default void getUsers(via.pro3.datatierserver.grpc.DataTierProto.GetUsersRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetUsersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUsersMethod(), responseObserver);
    }

    /**
     */
    default void updateUser(via.pro3.datatierserver.grpc.DataTierProto.UpdateUserRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.UserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateUserMethod(), responseObserver);
    }

    /**
     */
    default void deleteUser(via.pro3.datatierserver.grpc.DataTierProto.DeleteUserRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.DeleteUserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteUserMethod(), responseObserver);
    }

    /**
     * <pre>
     * Sale operations
     * </pre>
     */
    default void createSale(via.pro3.datatierserver.grpc.DataTierProto.CreateSaleRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateSaleMethod(), responseObserver);
    }

    /**
     */
    default void getSale(via.pro3.datatierserver.grpc.DataTierProto.GetSaleRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSaleMethod(), responseObserver);
    }

    /**
     */
    default void getSales(via.pro3.datatierserver.grpc.DataTierProto.GetSalesRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetSalesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSalesMethod(), responseObserver);
    }

    /**
     */
    default void updateSale(via.pro3.datatierserver.grpc.DataTierProto.UpdateSaleRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateSaleMethod(), responseObserver);
    }

    /**
     */
    default void deleteSale(via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteSaleMethod(), responseObserver);
    }

    /**
     * <pre>
     * Role operations
     * </pre>
     */
    default void getRole(via.pro3.datatierserver.grpc.DataTierProto.GetRoleRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetRoleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRoleMethod(), responseObserver);
    }

    /**
     * <pre>
     * Login operations
     * </pre>
     */
    default void authenticateUser(via.pro3.datatierserver.grpc.DataTierProto.LoginRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.LoginResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAuthenticateUserMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service DataTierService.
   */
  public static abstract class DataTierServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return DataTierServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service DataTierService.
   */
  public static final class DataTierServiceStub
      extends io.grpc.stub.AbstractAsyncStub<DataTierServiceStub> {
    private DataTierServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataTierServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataTierServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Bid operations
     * </pre>
     */
    public void createBid(via.pro3.datatierserver.grpc.DataTierProto.CreateBidRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.BidResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateBidMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBid(via.pro3.datatierserver.grpc.DataTierProto.GetBidRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.BidResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBidMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBids(via.pro3.datatierserver.grpc.DataTierProto.GetBidsRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetBidsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBidsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateBid(via.pro3.datatierserver.grpc.DataTierProto.UpdateBidRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.BidResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateBidMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteBid(via.pro3.datatierserver.grpc.DataTierProto.DeleteBidRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.DeleteBidResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteBidMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Property operations
     * </pre>
     */
    public void createProperty(via.pro3.datatierserver.grpc.DataTierProto.CreatePropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreatePropertyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getProperty(via.pro3.datatierserver.grpc.DataTierProto.GetPropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPropertyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getProperties(via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPropertiesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateProperty(via.pro3.datatierserver.grpc.DataTierProto.UpdatePropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdatePropertyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteProperty(via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeletePropertyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * User operations
     * </pre>
     */
    public void createUser(via.pro3.datatierserver.grpc.DataTierProto.CreateUserRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.UserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUser(via.pro3.datatierserver.grpc.DataTierProto.GetUserRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.UserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUsers(via.pro3.datatierserver.grpc.DataTierProto.GetUsersRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetUsersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUsersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateUser(via.pro3.datatierserver.grpc.DataTierProto.UpdateUserRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.UserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteUser(via.pro3.datatierserver.grpc.DataTierProto.DeleteUserRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.DeleteUserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Sale operations
     * </pre>
     */
    public void createSale(via.pro3.datatierserver.grpc.DataTierProto.CreateSaleRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateSaleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getSale(via.pro3.datatierserver.grpc.DataTierProto.GetSaleRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSaleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getSales(via.pro3.datatierserver.grpc.DataTierProto.GetSalesRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetSalesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSalesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateSale(via.pro3.datatierserver.grpc.DataTierProto.UpdateSaleRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateSaleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteSale(via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteSaleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Role operations
     * </pre>
     */
    public void getRole(via.pro3.datatierserver.grpc.DataTierProto.GetRoleRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetRoleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRoleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Login operations
     * </pre>
     */
    public void authenticateUser(via.pro3.datatierserver.grpc.DataTierProto.LoginRequest request,
        io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.LoginResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAuthenticateUserMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service DataTierService.
   */
  public static final class DataTierServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<DataTierServiceBlockingStub> {
    private DataTierServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataTierServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataTierServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Bid operations
     * </pre>
     */
    public via.pro3.datatierserver.grpc.DataTierProto.BidResponse createBid(via.pro3.datatierserver.grpc.DataTierProto.CreateBidRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateBidMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.BidResponse getBid(via.pro3.datatierserver.grpc.DataTierProto.GetBidRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBidMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.GetBidsResponse getBids(via.pro3.datatierserver.grpc.DataTierProto.GetBidsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBidsMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.BidResponse updateBid(via.pro3.datatierserver.grpc.DataTierProto.UpdateBidRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateBidMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.DeleteBidResponse deleteBid(via.pro3.datatierserver.grpc.DataTierProto.DeleteBidRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteBidMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Property operations
     * </pre>
     */
    public via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse createProperty(via.pro3.datatierserver.grpc.DataTierProto.CreatePropertyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreatePropertyMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse getProperty(via.pro3.datatierserver.grpc.DataTierProto.GetPropertyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPropertyMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesResponse getProperties(via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPropertiesMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse updateProperty(via.pro3.datatierserver.grpc.DataTierProto.UpdatePropertyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdatePropertyMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyResponse deleteProperty(via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeletePropertyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * User operations
     * </pre>
     */
    public via.pro3.datatierserver.grpc.DataTierProto.UserResponse createUser(via.pro3.datatierserver.grpc.DataTierProto.CreateUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.UserResponse getUser(via.pro3.datatierserver.grpc.DataTierProto.GetUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.GetUsersResponse getUsers(via.pro3.datatierserver.grpc.DataTierProto.GetUsersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUsersMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.UserResponse updateUser(via.pro3.datatierserver.grpc.DataTierProto.UpdateUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.DeleteUserResponse deleteUser(via.pro3.datatierserver.grpc.DataTierProto.DeleteUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteUserMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Sale operations
     * </pre>
     */
    public via.pro3.datatierserver.grpc.DataTierProto.SaleResponse createSale(via.pro3.datatierserver.grpc.DataTierProto.CreateSaleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateSaleMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.SaleResponse getSale(via.pro3.datatierserver.grpc.DataTierProto.GetSaleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSaleMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.GetSalesResponse getSales(via.pro3.datatierserver.grpc.DataTierProto.GetSalesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSalesMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.SaleResponse updateSale(via.pro3.datatierserver.grpc.DataTierProto.UpdateSaleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateSaleMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleResponse deleteSale(via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteSaleMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Role operations
     * </pre>
     */
    public via.pro3.datatierserver.grpc.DataTierProto.GetRoleResponse getRole(via.pro3.datatierserver.grpc.DataTierProto.GetRoleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRoleMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Login operations
     * </pre>
     */
    public via.pro3.datatierserver.grpc.DataTierProto.LoginResponse authenticateUser(via.pro3.datatierserver.grpc.DataTierProto.LoginRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAuthenticateUserMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service DataTierService.
   */
  public static final class DataTierServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<DataTierServiceFutureStub> {
    private DataTierServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataTierServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataTierServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Bid operations
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.BidResponse> createBid(
        via.pro3.datatierserver.grpc.DataTierProto.CreateBidRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateBidMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.BidResponse> getBid(
        via.pro3.datatierserver.grpc.DataTierProto.GetBidRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBidMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.GetBidsResponse> getBids(
        via.pro3.datatierserver.grpc.DataTierProto.GetBidsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBidsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.BidResponse> updateBid(
        via.pro3.datatierserver.grpc.DataTierProto.UpdateBidRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateBidMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.DeleteBidResponse> deleteBid(
        via.pro3.datatierserver.grpc.DataTierProto.DeleteBidRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteBidMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Property operations
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> createProperty(
        via.pro3.datatierserver.grpc.DataTierProto.CreatePropertyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreatePropertyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> getProperty(
        via.pro3.datatierserver.grpc.DataTierProto.GetPropertyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPropertyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesResponse> getProperties(
        via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPropertiesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse> updateProperty(
        via.pro3.datatierserver.grpc.DataTierProto.UpdatePropertyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdatePropertyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyResponse> deleteProperty(
        via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeletePropertyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * User operations
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.UserResponse> createUser(
        via.pro3.datatierserver.grpc.DataTierProto.CreateUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.UserResponse> getUser(
        via.pro3.datatierserver.grpc.DataTierProto.GetUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.GetUsersResponse> getUsers(
        via.pro3.datatierserver.grpc.DataTierProto.GetUsersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUsersMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.UserResponse> updateUser(
        via.pro3.datatierserver.grpc.DataTierProto.UpdateUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.DeleteUserResponse> deleteUser(
        via.pro3.datatierserver.grpc.DataTierProto.DeleteUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Sale operations
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> createSale(
        via.pro3.datatierserver.grpc.DataTierProto.CreateSaleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateSaleMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> getSale(
        via.pro3.datatierserver.grpc.DataTierProto.GetSaleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSaleMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.GetSalesResponse> getSales(
        via.pro3.datatierserver.grpc.DataTierProto.GetSalesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSalesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.SaleResponse> updateSale(
        via.pro3.datatierserver.grpc.DataTierProto.UpdateSaleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateSaleMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleResponse> deleteSale(
        via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteSaleMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Role operations
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.GetRoleResponse> getRole(
        via.pro3.datatierserver.grpc.DataTierProto.GetRoleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRoleMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Login operations
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.datatierserver.grpc.DataTierProto.LoginResponse> authenticateUser(
        via.pro3.datatierserver.grpc.DataTierProto.LoginRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAuthenticateUserMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_BID = 0;
  private static final int METHODID_GET_BID = 1;
  private static final int METHODID_GET_BIDS = 2;
  private static final int METHODID_UPDATE_BID = 3;
  private static final int METHODID_DELETE_BID = 4;
  private static final int METHODID_CREATE_PROPERTY = 5;
  private static final int METHODID_GET_PROPERTY = 6;
  private static final int METHODID_GET_PROPERTIES = 7;
  private static final int METHODID_UPDATE_PROPERTY = 8;
  private static final int METHODID_DELETE_PROPERTY = 9;
  private static final int METHODID_CREATE_USER = 10;
  private static final int METHODID_GET_USER = 11;
  private static final int METHODID_GET_USERS = 12;
  private static final int METHODID_UPDATE_USER = 13;
  private static final int METHODID_DELETE_USER = 14;
  private static final int METHODID_CREATE_SALE = 15;
  private static final int METHODID_GET_SALE = 16;
  private static final int METHODID_GET_SALES = 17;
  private static final int METHODID_UPDATE_SALE = 18;
  private static final int METHODID_DELETE_SALE = 19;
  private static final int METHODID_GET_ROLE = 20;
  private static final int METHODID_AUTHENTICATE_USER = 21;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_BID:
          serviceImpl.createBid((via.pro3.datatierserver.grpc.DataTierProto.CreateBidRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.BidResponse>) responseObserver);
          break;
        case METHODID_GET_BID:
          serviceImpl.getBid((via.pro3.datatierserver.grpc.DataTierProto.GetBidRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.BidResponse>) responseObserver);
          break;
        case METHODID_GET_BIDS:
          serviceImpl.getBids((via.pro3.datatierserver.grpc.DataTierProto.GetBidsRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetBidsResponse>) responseObserver);
          break;
        case METHODID_UPDATE_BID:
          serviceImpl.updateBid((via.pro3.datatierserver.grpc.DataTierProto.UpdateBidRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.BidResponse>) responseObserver);
          break;
        case METHODID_DELETE_BID:
          serviceImpl.deleteBid((via.pro3.datatierserver.grpc.DataTierProto.DeleteBidRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.DeleteBidResponse>) responseObserver);
          break;
        case METHODID_CREATE_PROPERTY:
          serviceImpl.createProperty((via.pro3.datatierserver.grpc.DataTierProto.CreatePropertyRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse>) responseObserver);
          break;
        case METHODID_GET_PROPERTY:
          serviceImpl.getProperty((via.pro3.datatierserver.grpc.DataTierProto.GetPropertyRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse>) responseObserver);
          break;
        case METHODID_GET_PROPERTIES:
          serviceImpl.getProperties((via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesResponse>) responseObserver);
          break;
        case METHODID_UPDATE_PROPERTY:
          serviceImpl.updateProperty((via.pro3.datatierserver.grpc.DataTierProto.UpdatePropertyRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse>) responseObserver);
          break;
        case METHODID_DELETE_PROPERTY:
          serviceImpl.deleteProperty((via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyResponse>) responseObserver);
          break;
        case METHODID_CREATE_USER:
          serviceImpl.createUser((via.pro3.datatierserver.grpc.DataTierProto.CreateUserRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.UserResponse>) responseObserver);
          break;
        case METHODID_GET_USER:
          serviceImpl.getUser((via.pro3.datatierserver.grpc.DataTierProto.GetUserRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.UserResponse>) responseObserver);
          break;
        case METHODID_GET_USERS:
          serviceImpl.getUsers((via.pro3.datatierserver.grpc.DataTierProto.GetUsersRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetUsersResponse>) responseObserver);
          break;
        case METHODID_UPDATE_USER:
          serviceImpl.updateUser((via.pro3.datatierserver.grpc.DataTierProto.UpdateUserRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.UserResponse>) responseObserver);
          break;
        case METHODID_DELETE_USER:
          serviceImpl.deleteUser((via.pro3.datatierserver.grpc.DataTierProto.DeleteUserRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.DeleteUserResponse>) responseObserver);
          break;
        case METHODID_CREATE_SALE:
          serviceImpl.createSale((via.pro3.datatierserver.grpc.DataTierProto.CreateSaleRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.SaleResponse>) responseObserver);
          break;
        case METHODID_GET_SALE:
          serviceImpl.getSale((via.pro3.datatierserver.grpc.DataTierProto.GetSaleRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.SaleResponse>) responseObserver);
          break;
        case METHODID_GET_SALES:
          serviceImpl.getSales((via.pro3.datatierserver.grpc.DataTierProto.GetSalesRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetSalesResponse>) responseObserver);
          break;
        case METHODID_UPDATE_SALE:
          serviceImpl.updateSale((via.pro3.datatierserver.grpc.DataTierProto.UpdateSaleRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.SaleResponse>) responseObserver);
          break;
        case METHODID_DELETE_SALE:
          serviceImpl.deleteSale((via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleResponse>) responseObserver);
          break;
        case METHODID_GET_ROLE:
          serviceImpl.getRole((via.pro3.datatierserver.grpc.DataTierProto.GetRoleRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.GetRoleResponse>) responseObserver);
          break;
        case METHODID_AUTHENTICATE_USER:
          serviceImpl.authenticateUser((via.pro3.datatierserver.grpc.DataTierProto.LoginRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.datatierserver.grpc.DataTierProto.LoginResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCreateBidMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.CreateBidRequest,
              via.pro3.datatierserver.grpc.DataTierProto.BidResponse>(
                service, METHODID_CREATE_BID)))
        .addMethod(
          getGetBidMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.GetBidRequest,
              via.pro3.datatierserver.grpc.DataTierProto.BidResponse>(
                service, METHODID_GET_BID)))
        .addMethod(
          getGetBidsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.GetBidsRequest,
              via.pro3.datatierserver.grpc.DataTierProto.GetBidsResponse>(
                service, METHODID_GET_BIDS)))
        .addMethod(
          getUpdateBidMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.UpdateBidRequest,
              via.pro3.datatierserver.grpc.DataTierProto.BidResponse>(
                service, METHODID_UPDATE_BID)))
        .addMethod(
          getDeleteBidMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.DeleteBidRequest,
              via.pro3.datatierserver.grpc.DataTierProto.DeleteBidResponse>(
                service, METHODID_DELETE_BID)))
        .addMethod(
          getCreatePropertyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.CreatePropertyRequest,
              via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse>(
                service, METHODID_CREATE_PROPERTY)))
        .addMethod(
          getGetPropertyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.GetPropertyRequest,
              via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse>(
                service, METHODID_GET_PROPERTY)))
        .addMethod(
          getGetPropertiesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesRequest,
              via.pro3.datatierserver.grpc.DataTierProto.GetPropertiesResponse>(
                service, METHODID_GET_PROPERTIES)))
        .addMethod(
          getUpdatePropertyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.UpdatePropertyRequest,
              via.pro3.datatierserver.grpc.DataTierProto.PropertyResponse>(
                service, METHODID_UPDATE_PROPERTY)))
        .addMethod(
          getDeletePropertyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyRequest,
              via.pro3.datatierserver.grpc.DataTierProto.DeletePropertyResponse>(
                service, METHODID_DELETE_PROPERTY)))
        .addMethod(
          getCreateUserMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.CreateUserRequest,
              via.pro3.datatierserver.grpc.DataTierProto.UserResponse>(
                service, METHODID_CREATE_USER)))
        .addMethod(
          getGetUserMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.GetUserRequest,
              via.pro3.datatierserver.grpc.DataTierProto.UserResponse>(
                service, METHODID_GET_USER)))
        .addMethod(
          getGetUsersMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.GetUsersRequest,
              via.pro3.datatierserver.grpc.DataTierProto.GetUsersResponse>(
                service, METHODID_GET_USERS)))
        .addMethod(
          getUpdateUserMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.UpdateUserRequest,
              via.pro3.datatierserver.grpc.DataTierProto.UserResponse>(
                service, METHODID_UPDATE_USER)))
        .addMethod(
          getDeleteUserMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.DeleteUserRequest,
              via.pro3.datatierserver.grpc.DataTierProto.DeleteUserResponse>(
                service, METHODID_DELETE_USER)))
        .addMethod(
          getCreateSaleMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.CreateSaleRequest,
              via.pro3.datatierserver.grpc.DataTierProto.SaleResponse>(
                service, METHODID_CREATE_SALE)))
        .addMethod(
          getGetSaleMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.GetSaleRequest,
              via.pro3.datatierserver.grpc.DataTierProto.SaleResponse>(
                service, METHODID_GET_SALE)))
        .addMethod(
          getGetSalesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.GetSalesRequest,
              via.pro3.datatierserver.grpc.DataTierProto.GetSalesResponse>(
                service, METHODID_GET_SALES)))
        .addMethod(
          getUpdateSaleMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.UpdateSaleRequest,
              via.pro3.datatierserver.grpc.DataTierProto.SaleResponse>(
                service, METHODID_UPDATE_SALE)))
        .addMethod(
          getDeleteSaleMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleRequest,
              via.pro3.datatierserver.grpc.DataTierProto.DeleteSaleResponse>(
                service, METHODID_DELETE_SALE)))
        .addMethod(
          getGetRoleMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.GetRoleRequest,
              via.pro3.datatierserver.grpc.DataTierProto.GetRoleResponse>(
                service, METHODID_GET_ROLE)))
        .addMethod(
          getAuthenticateUserMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.datatierserver.grpc.DataTierProto.LoginRequest,
              via.pro3.datatierserver.grpc.DataTierProto.LoginResponse>(
                service, METHODID_AUTHENTICATE_USER)))
        .build();
  }

  private static abstract class DataTierServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DataTierServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return via.pro3.datatierserver.grpc.DataTierProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DataTierService");
    }
  }

  private static final class DataTierServiceFileDescriptorSupplier
      extends DataTierServiceBaseDescriptorSupplier {
    DataTierServiceFileDescriptorSupplier() {}
  }

  private static final class DataTierServiceMethodDescriptorSupplier
      extends DataTierServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    DataTierServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DataTierServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DataTierServiceFileDescriptorSupplier())
              .addMethod(getCreateBidMethod())
              .addMethod(getGetBidMethod())
              .addMethod(getGetBidsMethod())
              .addMethod(getUpdateBidMethod())
              .addMethod(getDeleteBidMethod())
              .addMethod(getCreatePropertyMethod())
              .addMethod(getGetPropertyMethod())
              .addMethod(getGetPropertiesMethod())
              .addMethod(getUpdatePropertyMethod())
              .addMethod(getDeletePropertyMethod())
              .addMethod(getCreateUserMethod())
              .addMethod(getGetUserMethod())
              .addMethod(getGetUsersMethod())
              .addMethod(getUpdateUserMethod())
              .addMethod(getDeleteUserMethod())
              .addMethod(getCreateSaleMethod())
              .addMethod(getGetSaleMethod())
              .addMethod(getGetSalesMethod())
              .addMethod(getUpdateSaleMethod())
              .addMethod(getDeleteSaleMethod())
              .addMethod(getGetRoleMethod())
              .addMethod(getAuthenticateUserMethod())
              .build();
        }
      }
    }
    return result;
  }
}
