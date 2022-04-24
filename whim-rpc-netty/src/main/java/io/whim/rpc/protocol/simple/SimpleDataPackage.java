package io.whim.rpc.protocol.simple;

import io.netty.buffer.ByteBuf;
import io.whim.rpc.protocol.DataPackage;
import io.whim.rpc.protocol.ProtocolType;
import io.whim.rpc.serialize.SerializeType;
import io.whim.rpc.serialize.Serializer;
import io.whim.rpc.serialize.SerializerManager;
import io.whim.rpc.service.LocalServiceManager;
import io.whim.rpc.service.api.ApiInfo;
import io.whim.rpc.service.invoke.RpcMeta;
import io.whim.rpc.service.invoke.RpcRequest;
import io.whim.rpc.service.invoke.RpcResponse;

import static io.whim.rpc.protocol.Protocol.PROTOCOL_MASK_SIZE;


/**
 * the simple protocol data package desc:
 * <p>
 * |-9527(int)-|-packageSize(int)-|-headerSize(int)-|---header-data(byte[])---|-bodySize(int)-|---body-data(byte[])--|
 */

public class SimpleDataPackage implements DataPackage {

    /**
     * simple 协议默认绑定的序列化类型是 JSON
     */
    public static final SerializeType SERIALIZE_TYPE = SerializeType.JSON;

    public static final int SIMPLE_PROTOCOL_MASK = 9527;

    public static final int PACKAGE_SIZE_LENGTH = 4;
    public static final int HEADER_SIZE_LENGTH = 4;
    public static final int BODY_SIZE_LENGTH = 4;

    private Serializer serializer = SerializerManager.getSerializer(SERIALIZE_TYPE);

    private int packageSize;

    private int headerSize;

    private byte[] headerData;

    private int bodySize;

    private byte[] bodyData;

    @Override
    public int getProtocolMaskCode() {
        return SIMPLE_PROTOCOL_MASK;
    }

    @Override
    public ProtocolType getProtocolType() {
        return ProtocolType.SIMPLE;
    }

    @Override
    public RpcRequest deserializeRequest() {
        RpcMeta rpcMeta = serializer.deserialize(headerData, RpcMeta.class);
        String apiKey = rpcMeta.getApiKey();
        ApiInfo apiInfo = LocalServiceManager.getApiInfo(apiKey);
        if (apiInfo == null) {
            throw new IllegalStateException("api not found! api:" + apiKey);
        }
        Class<?> parameterType = apiInfo.getParameterType();
        Object request = serializer.deserialize(bodyData, parameterType);
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setMeta(rpcMeta);
        rpcRequest.setRequest(request);
        return rpcRequest;
    }

    @Override
    public RpcResponse deserializeResponse() {
        RpcMeta rpcMeta = serializer.deserialize(headerData, RpcMeta.class);
        String apiKey = rpcMeta.getApiKey();
        ApiInfo apiInfo = LocalServiceManager.getApiInfo(apiKey);
        if (apiInfo == null) {
            throw new IllegalStateException("api not found! api:" + apiKey);
        }
        Class<?> returnType = apiInfo.getReturnType();
        Object response = serializer.deserialize(bodyData, returnType);
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRpcMeta(rpcMeta);
        rpcResponse.setResponse(response);
        return rpcResponse;
    }

    @Override
    public DataPackage serialize(RpcResponse rpcResponse) {
        RpcMeta rpcMeta = rpcResponse.getRpcMeta();
        byte[] headerBytes = serializer.serialize(rpcMeta);
        this.headerSize = headerBytes.length;
        this.headerData = headerBytes;

        Object response = rpcResponse.getResponse();
        byte[] bodyBytes = serializer.serialize(response);
        this.bodySize = bodyBytes.length;
        this.bodyData = bodyBytes;

        this.packageSize = PROTOCOL_MASK_SIZE +
                PACKAGE_SIZE_LENGTH +
                HEADER_SIZE_LENGTH + headerSize +
                BODY_SIZE_LENGTH + bodySize;

        return this;
    }

    @Override
    public DataPackage serialize(RpcRequest rpcRequest) {
        RpcMeta rpcMeta = rpcRequest.getMeta();
        byte[] headerBytes = serializer.serialize(rpcMeta);
        this.headerSize = headerBytes.length;
        this.headerData = headerBytes;

        Object request = rpcRequest.getRequest();
        byte[] bodyBytes = serializer.serialize(request);
        this.bodySize = bodyBytes.length;
        this.bodyData = bodyBytes;

        this.packageSize = PROTOCOL_MASK_SIZE +
                PACKAGE_SIZE_LENGTH +
                HEADER_SIZE_LENGTH + headerSize +
                BODY_SIZE_LENGTH + bodySize;

        return this;
    }

    @Override
    public void writeData(ByteBuf out) {
        out.writeInt(SIMPLE_PROTOCOL_MASK);
        out.writeInt(packageSize);
        out.writeInt(headerSize);
        out.writeBytes(headerData);
        out.writeInt(bodySize);
        out.writeBytes(bodyData);
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

    public byte[] getHeaderData() {
        return headerData;
    }

    public void setHeaderData(byte[] headerData) {
        this.headerData = headerData;
    }

    public byte[] getBodyData() {
        return bodyData;
    }

    public void setBodyData(byte[] bodyData) {
        this.bodyData = bodyData;
    }

    public int getBodySize() {
        return bodySize;
    }

    public void setBodySize(int bodySize) {
        this.bodySize = bodySize;
    }


}
