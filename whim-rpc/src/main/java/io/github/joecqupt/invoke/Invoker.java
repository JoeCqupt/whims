package io.github.joecqupt.invoke;

import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.protocol.DataPackage;
import io.github.joecqupt.protocol.DataPackageFactory;
import io.github.joecqupt.serialization.RpcMeta;
import io.github.joecqupt.serialization.RpcRequest;
import io.github.joecqupt.serialization.RpcResponse;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Invoker {
    /**
     * 业务执行线程池
     */
    private static ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void invokeApi(RpcRequest rpcRequest, RpcChannel channel) {
        executorService.submit(new Caller(rpcRequest, channel));
    }


    public static class Caller implements Runnable {
        RpcRequest rpcRequest;
        RpcChannel channel;

        public Caller(RpcRequest rpcRequest, RpcChannel channel) {
            this.rpcRequest = rpcRequest;
            this.channel = channel;
        }

        @Override
        public void run() {
            try {
                RpcMeta rpcMeta = rpcRequest.getRpcMeta();
                String apiKey = rpcMeta.getApiKey();
                ServiceManager.ApiMeta apiMeta = ServiceManager.getApiMeta(apiKey);
                Object instance = apiMeta.getInstance();
                Method method = apiMeta.getMethod();
                Object response = method.invoke(instance, rpcRequest.getRequest());
                RpcResponse rpcResponse = new RpcResponse(rpcMeta, response);
                channel.write(rpcResponse);
                channel.flush();
                // TODO
//                channel.pipeline().write(rpcResponse).addListener();
            } catch (Exception e) {
                // todo build response
                // TODO
//                channel.pipeline().write()
            }
        }
    }
}
