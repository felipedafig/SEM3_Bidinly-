package via.pro3.propertyserver.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.60.1)",
    comments = "Source: property.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PropertyServiceGrpc {

  private PropertyServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "property.PropertyService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.CreatePropertyRequest,
      via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> getCreatePropertyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateProperty",
      requestType = via.pro3.propertyserver.grpc.PropertyProto.CreatePropertyRequest.class,
      responseType = via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.CreatePropertyRequest,
      via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> getCreatePropertyMethod() {
    io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.CreatePropertyRequest, via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> getCreatePropertyMethod;
    if ((getCreatePropertyMethod = PropertyServiceGrpc.getCreatePropertyMethod) == null) {
      synchronized (PropertyServiceGrpc.class) {
        if ((getCreatePropertyMethod = PropertyServiceGrpc.getCreatePropertyMethod) == null) {
          PropertyServiceGrpc.getCreatePropertyMethod = getCreatePropertyMethod =
              io.grpc.MethodDescriptor.<via.pro3.propertyserver.grpc.PropertyProto.CreatePropertyRequest, via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateProperty"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.propertyserver.grpc.PropertyProto.CreatePropertyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PropertyServiceMethodDescriptorSupplier("CreateProperty"))
              .build();
        }
      }
    }
    return getCreatePropertyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.GetPropertyRequest,
      via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> getGetPropertyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProperty",
      requestType = via.pro3.propertyserver.grpc.PropertyProto.GetPropertyRequest.class,
      responseType = via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.GetPropertyRequest,
      via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> getGetPropertyMethod() {
    io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.GetPropertyRequest, via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> getGetPropertyMethod;
    if ((getGetPropertyMethod = PropertyServiceGrpc.getGetPropertyMethod) == null) {
      synchronized (PropertyServiceGrpc.class) {
        if ((getGetPropertyMethod = PropertyServiceGrpc.getGetPropertyMethod) == null) {
          PropertyServiceGrpc.getGetPropertyMethod = getGetPropertyMethod =
              io.grpc.MethodDescriptor.<via.pro3.propertyserver.grpc.PropertyProto.GetPropertyRequest, via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProperty"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.propertyserver.grpc.PropertyProto.GetPropertyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PropertyServiceMethodDescriptorSupplier("GetProperty"))
              .build();
        }
      }
    }
    return getGetPropertyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesRequest,
      via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesResponse> getGetPropertiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProperties",
      requestType = via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesRequest.class,
      responseType = via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesRequest,
      via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesResponse> getGetPropertiesMethod() {
    io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesRequest, via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesResponse> getGetPropertiesMethod;
    if ((getGetPropertiesMethod = PropertyServiceGrpc.getGetPropertiesMethod) == null) {
      synchronized (PropertyServiceGrpc.class) {
        if ((getGetPropertiesMethod = PropertyServiceGrpc.getGetPropertiesMethod) == null) {
          PropertyServiceGrpc.getGetPropertiesMethod = getGetPropertiesMethod =
              io.grpc.MethodDescriptor.<via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesRequest, via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProperties"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PropertyServiceMethodDescriptorSupplier("GetProperties"))
              .build();
        }
      }
    }
    return getGetPropertiesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.UpdatePropertyRequest,
      via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> getUpdatePropertyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateProperty",
      requestType = via.pro3.propertyserver.grpc.PropertyProto.UpdatePropertyRequest.class,
      responseType = via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.UpdatePropertyRequest,
      via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> getUpdatePropertyMethod() {
    io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.UpdatePropertyRequest, via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> getUpdatePropertyMethod;
    if ((getUpdatePropertyMethod = PropertyServiceGrpc.getUpdatePropertyMethod) == null) {
      synchronized (PropertyServiceGrpc.class) {
        if ((getUpdatePropertyMethod = PropertyServiceGrpc.getUpdatePropertyMethod) == null) {
          PropertyServiceGrpc.getUpdatePropertyMethod = getUpdatePropertyMethod =
              io.grpc.MethodDescriptor.<via.pro3.propertyserver.grpc.PropertyProto.UpdatePropertyRequest, via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateProperty"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.propertyserver.grpc.PropertyProto.UpdatePropertyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PropertyServiceMethodDescriptorSupplier("UpdateProperty"))
              .build();
        }
      }
    }
    return getUpdatePropertyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyRequest,
      via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyResponse> getDeletePropertyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteProperty",
      requestType = via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyRequest.class,
      responseType = via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyRequest,
      via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyResponse> getDeletePropertyMethod() {
    io.grpc.MethodDescriptor<via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyRequest, via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyResponse> getDeletePropertyMethod;
    if ((getDeletePropertyMethod = PropertyServiceGrpc.getDeletePropertyMethod) == null) {
      synchronized (PropertyServiceGrpc.class) {
        if ((getDeletePropertyMethod = PropertyServiceGrpc.getDeletePropertyMethod) == null) {
          PropertyServiceGrpc.getDeletePropertyMethod = getDeletePropertyMethod =
              io.grpc.MethodDescriptor.<via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyRequest, via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteProperty"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PropertyServiceMethodDescriptorSupplier("DeleteProperty"))
              .build();
        }
      }
    }
    return getDeletePropertyMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PropertyServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PropertyServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PropertyServiceStub>() {
        @java.lang.Override
        public PropertyServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PropertyServiceStub(channel, callOptions);
        }
      };
    return PropertyServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PropertyServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PropertyServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PropertyServiceBlockingStub>() {
        @java.lang.Override
        public PropertyServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PropertyServiceBlockingStub(channel, callOptions);
        }
      };
    return PropertyServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PropertyServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PropertyServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PropertyServiceFutureStub>() {
        @java.lang.Override
        public PropertyServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PropertyServiceFutureStub(channel, callOptions);
        }
      };
    return PropertyServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void createProperty(via.pro3.propertyserver.grpc.PropertyProto.CreatePropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreatePropertyMethod(), responseObserver);
    }

    /**
     */
    default void getProperty(via.pro3.propertyserver.grpc.PropertyProto.GetPropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPropertyMethod(), responseObserver);
    }

    /**
     */
    default void getProperties(via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesRequest request,
        io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPropertiesMethod(), responseObserver);
    }

    /**
     */
    default void updateProperty(via.pro3.propertyserver.grpc.PropertyProto.UpdatePropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdatePropertyMethod(), responseObserver);
    }

    /**
     */
    default void deleteProperty(via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeletePropertyMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PropertyService.
   */
  public static abstract class PropertyServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PropertyServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PropertyService.
   */
  public static final class PropertyServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PropertyServiceStub> {
    private PropertyServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PropertyServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PropertyServiceStub(channel, callOptions);
    }

    /**
     */
    public void createProperty(via.pro3.propertyserver.grpc.PropertyProto.CreatePropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreatePropertyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getProperty(via.pro3.propertyserver.grpc.PropertyProto.GetPropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPropertyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getProperties(via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesRequest request,
        io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPropertiesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateProperty(via.pro3.propertyserver.grpc.PropertyProto.UpdatePropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdatePropertyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteProperty(via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyRequest request,
        io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeletePropertyMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PropertyService.
   */
  public static final class PropertyServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PropertyServiceBlockingStub> {
    private PropertyServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PropertyServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PropertyServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse createProperty(via.pro3.propertyserver.grpc.PropertyProto.CreatePropertyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreatePropertyMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse getProperty(via.pro3.propertyserver.grpc.PropertyProto.GetPropertyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPropertyMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesResponse getProperties(via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPropertiesMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse updateProperty(via.pro3.propertyserver.grpc.PropertyProto.UpdatePropertyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdatePropertyMethod(), getCallOptions(), request);
    }

    /**
     */
    public via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyResponse deleteProperty(via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeletePropertyMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PropertyService.
   */
  public static final class PropertyServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PropertyServiceFutureStub> {
    private PropertyServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PropertyServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PropertyServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> createProperty(
        via.pro3.propertyserver.grpc.PropertyProto.CreatePropertyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreatePropertyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> getProperty(
        via.pro3.propertyserver.grpc.PropertyProto.GetPropertyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPropertyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesResponse> getProperties(
        via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPropertiesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse> updateProperty(
        via.pro3.propertyserver.grpc.PropertyProto.UpdatePropertyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdatePropertyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyResponse> deleteProperty(
        via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeletePropertyMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_PROPERTY = 0;
  private static final int METHODID_GET_PROPERTY = 1;
  private static final int METHODID_GET_PROPERTIES = 2;
  private static final int METHODID_UPDATE_PROPERTY = 3;
  private static final int METHODID_DELETE_PROPERTY = 4;

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
        case METHODID_CREATE_PROPERTY:
          serviceImpl.createProperty((via.pro3.propertyserver.grpc.PropertyProto.CreatePropertyRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse>) responseObserver);
          break;
        case METHODID_GET_PROPERTY:
          serviceImpl.getProperty((via.pro3.propertyserver.grpc.PropertyProto.GetPropertyRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse>) responseObserver);
          break;
        case METHODID_GET_PROPERTIES:
          serviceImpl.getProperties((via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesResponse>) responseObserver);
          break;
        case METHODID_UPDATE_PROPERTY:
          serviceImpl.updateProperty((via.pro3.propertyserver.grpc.PropertyProto.UpdatePropertyRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse>) responseObserver);
          break;
        case METHODID_DELETE_PROPERTY:
          serviceImpl.deleteProperty((via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyRequest) request,
              (io.grpc.stub.StreamObserver<via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyResponse>) responseObserver);
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
          getCreatePropertyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.propertyserver.grpc.PropertyProto.CreatePropertyRequest,
              via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse>(
                service, METHODID_CREATE_PROPERTY)))
        .addMethod(
          getGetPropertyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.propertyserver.grpc.PropertyProto.GetPropertyRequest,
              via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse>(
                service, METHODID_GET_PROPERTY)))
        .addMethod(
          getGetPropertiesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesRequest,
              via.pro3.propertyserver.grpc.PropertyProto.GetPropertiesResponse>(
                service, METHODID_GET_PROPERTIES)))
        .addMethod(
          getUpdatePropertyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.propertyserver.grpc.PropertyProto.UpdatePropertyRequest,
              via.pro3.propertyserver.grpc.PropertyProto.PropertyResponse>(
                service, METHODID_UPDATE_PROPERTY)))
        .addMethod(
          getDeletePropertyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyRequest,
              via.pro3.propertyserver.grpc.PropertyProto.DeletePropertyResponse>(
                service, METHODID_DELETE_PROPERTY)))
        .build();
  }

  private static abstract class PropertyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PropertyServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return via.pro3.propertyserver.grpc.PropertyProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PropertyService");
    }
  }

  private static final class PropertyServiceFileDescriptorSupplier
      extends PropertyServiceBaseDescriptorSupplier {
    PropertyServiceFileDescriptorSupplier() {}
  }

  private static final class PropertyServiceMethodDescriptorSupplier
      extends PropertyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PropertyServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (PropertyServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PropertyServiceFileDescriptorSupplier())
              .addMethod(getCreatePropertyMethod())
              .addMethod(getGetPropertyMethod())
              .addMethod(getGetPropertiesMethod())
              .addMethod(getUpdatePropertyMethod())
              .addMethod(getDeletePropertyMethod())
              .build();
        }
      }
    }
    return result;
  }
}
