package io.whim.rpc.protocol.simple;

import io.netty.buffer.ByteBuf;
import io.whim.rpc.protocol.DataPackage;
import io.whim.rpc.serialize.Serializer;
import io.whim.rpc.serialize.SerializerManager;
import io.whim.rpc.service.ServiceManager;
import io.whim.rpc.service.api.ApiInfo;
import io.whim.rpc.service.invoke.RpcMeta;
import io.whim.rpc.service.invoke.RpcRequest;
import io.whim.rpc.service.invoke.RpcResponse;

import static io.whim.rpc.protocol.simple.SimpleProtocol.SERIALIZE_TYPE;

/**
 * the simple protocol data package desc:
 * <p>
 * |-9527(int)-|-packageSize(int)-|-headerSize(int)-|---header-data(byte[])---|-bodySize(int)-|---body-data(byte[])--|
 */

public class SimpleDataPackage implements DataPackage {


    public static final int PACKAGE_SIZE_LENGTH = 4;
    public static final int HEADER_SIZE_LENGTH = 4;
    public static final int DATA_SIZE_LENGTH = 4;

    private Serializer serializer = SerializerManager.getSerializer(SERIALIZE_TYPE);

    private int packageSize;

    private int headerSize;

    private ByteBuf headerData;

    private int bodySize;

    private ByteBuf bodyData;

    @Override
    public RpcRequest deserializeRequest() {

        RpcMeta rpcMeta = serializer.deserialize(headerData.array(), RpcMeta.class);
        String apiKey = rpcMeta.getApiKey();
        ApiInfo apiInfo = ServiceManager.getApiInfo(apiKey);
        if (apiInfo == null) {
            throw new IllegalStateException("api not found! api:" + apiKey);
        }
        Class<?> paramterType = apiInfo.getParamterType();
        Object reuqet = serializer.deserialize(bodyData.array(), paramterType);

        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setMeta(rpcMeta);
        rpcRequest.setRequest(reuqet);
        return rpcRequest;
    }

    @Override
    public RpcResponse deserializeResponse() {
        return null;
    }

    @Override
    public DataPackage serialize(RpcResponse rpcResponse) {
        return null;
    }

    @Override
    public DataPackage serialize(RpcRequest rpcRequest) {
        return null;
    }

    public int getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(int packageSize) {
        this.packageSize = packageSize;
    }

    public int getHeaderSize() {
        return headerSize;
    }

    public void setHeaderSize(int headerSize) {
        this.headerSize = headerSize;
    }

    public ByteBuf getHeaderData() {
        return headerData;
    }

    public void setHeaderData(ByteBuf headerData) {
        this.headerData = headerData;
    }

    public int getBodySize() {
        return bodySize;
    }

    public void setBodySize(int bodySize) {
        this.bodySize = bodySize;
    }

    public ByteBuf getBodyData() {
        return bodyData;
    }

    public void setBodyData(ByteBuf bodyData) {
        this.bodyData = bodyData;
    }
}
