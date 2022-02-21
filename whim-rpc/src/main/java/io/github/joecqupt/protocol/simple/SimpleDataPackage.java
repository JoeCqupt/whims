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
    public byte[] serialize(RpcResponse rpcResponse) {
        return new byte[0];
    }
}
