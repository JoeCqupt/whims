package io.github.joecqupt.protocol.simple;

import io.github.joecqupt.invoke.ServiceManager;
import io.github.joecqupt.protocol.DataPackage;
import io.github.joecqupt.protocol.ProtocolType;
import io.github.joecqupt.serialization.RpcMeta;
import io.github.joecqupt.serialization.RpcRequest;
import io.github.joecqupt.serialization.RpcResponse;
import io.github.joecqupt.serialization.SerializeType;
import io.github.joecqupt.serialization.Serializer;
import io.github.joecqupt.serialization.SerializerManager;

import java.nio.ByteBuffer;

import static io.github.joecqupt.protocol.Protocol.MASK_SIZE;
import static io.github.joecqupt.protocol.simple.SimpleProtocol.PACKAGE_SIZE;

/**
 * the simple protocol data package desc:
 * <p>
 * |-9527(int)-|-packageSize(int)-|-headerSize(int)-|---header-data(byte[])---|-bodySize(int)-|---body-data(byte[])--|
 */
public class SimpleDataPackage implements DataPackage {

    public final static int MASK = 9527;

    private int packageSize;

    private int headerSize;
    private byte[] header;

    private int bodySize;
    private byte[] body;


    public void setPackageSize(int packageSize) {
        this.packageSize = packageSize;
    }


    public void setHeaderSize(int headerSize) {
        this.headerSize = headerSize;
    }


    public void setHeader(byte[] header) {
        this.header = header;
    }


    public void setBodySize(int bodySize) {
        this.bodySize = bodySize;
    }


    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public RpcRequest deserialize() {
        // 反序列化header为RpcRequest.
        Serializer serializer = SerializerManager.getSerializer(SerializeType.JSON);
        RpcMeta rpcMeta = serializer.deserialize(header, RpcMeta.class);
        rpcMeta.setProtocolType(ProtocolType.SIMPLE);
        // 获取request的类型
        String apiKey = rpcMeta.getApiKey();
        ServiceManager.ApiMeta apiMeta = ServiceManager.getApiMeta(apiKey);
        if (apiMeta == null) {
            throw new RuntimeException("no such rpc api: " + apiKey);
        }
        Class<?> requestType = apiMeta.getMethod().getParameterTypes()[0];
        // 反序列化body为rpc方法真正的request
        Object request = serializer.deserialize(body, requestType);
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRpcMeta(rpcMeta);
        rpcRequest.setRequest(request);
        return rpcRequest;
    }


    @Override
    public DataPackage serialize(RpcResponse rpcResponse) {
        Serializer serializer = SerializerManager.getSerializer(SerializeType.JSON);
        byte[] header = serializer.serialize(rpcResponse.getRpcMeta());
        int headerSize = header.length;
        byte[] body = serializer.serialize(rpcResponse.getResponse());
        int bodySize = body.length;
        int packageSize = headerSize + bodySize;
        this.packageSize = packageSize;
        this.headerSize = headerSize;
        this.header = header;
        this.bodySize = bodySize;
        this.body = body;
        return this;
    }

    @Override
    public ByteBuffer toByteBuffer() {
        int totalSize = MASK_SIZE + PACKAGE_SIZE + headerSize + bodySize;
        // array copy 性能差
        ByteBuffer data = ByteBuffer.allocate(totalSize);
        data.putInt(MASK);
        data.putInt(packageSize);
        data.putInt(headerSize);
        data.put(header);
        data.putInt(bodySize);
        data.put(body);
        return data;
    }
}
