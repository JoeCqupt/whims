package io.whim.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.whim.rpc.registry.Registry;
import io.whim.rpc.registry.RegistryConfig;
import io.whim.rpc.registry.RegistryManager;
import io.whim.rpc.service.LocalServiceManager;
import io.whim.rpc.transport.handler.*;

public class RpcServer {
    private RegistryConfig config;
    private int port;
    private Class<?> interfaceClass;
    private Object service;


    public RpcServer registerConfig(RegistryConfig config) {
        this.config = config;
        return this;
    }

    public RpcServer port(int port) {
        this.port = port;
        return this;
    }

    public void export(Class<?> interfaceClass, Object service) {

        //step1: service local register
        LocalServiceManager.registerService(interfaceClass, service);

        //step2: service remote register
        Registry register = RegistryManager.getRegister(config);
        LocalServiceManager.export(register, port);
    }

    public void start() throws Exception {


        //step2: start server
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup workers = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());

        // handlers
        RpcRequestCodecHandler requestCodecHandler = new RpcRequestCodecHandler();
        RpcResponseCodecHandler responseCodecHandler = new RpcResponseCodecHandler();
        RpcServerHandler serverHandler = new RpcServerHandler();

        ChannelFuture future = serverBootstrap.group(boss, workers)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(
                                new RpcCodecHandler(), requestCodecHandler, responseCodecHandler, serverHandler
                        );
                    }
                }).bind(port).sync();

        if (!future.isSuccess()) {
            throw new IllegalStateException("start rpc service fail", future.cause());
        }
    }
}
