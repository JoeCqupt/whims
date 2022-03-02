package io.github.joecqupt.eventloop;


import io.github.joecqupt.channel.RpcChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class EventLoop extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(EventLoop.class);
    private Selector selector;
    private long selectTimeOut = 200;

    public EventLoop() {
        try {
            selector = Selector.open();
            this.setName("EventLoopThread-" + hashCode());
        } catch (Exception e) {
            throw new RuntimeException("init eventLoop ex", e);
        }
    }

    public Selector getSelector() {
        return selector;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                int select = selector.select(selectTimeOut);
                if (select > 0) {
                    processSelectKeys();
                }
            } catch (Exception e) {
                LOG.error("event loop execute ex", e);
            }
        }
    }

    private void processSelectKeys() {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            RpcChannel rpcChannel = (RpcChannel) key.attachment();
            if (key.isReadable()) {
                rpcChannel.read();
            } else if (key.isConnectable()) {
                rpcChannel.finishConnect();
            } else {
                rpcChannel.flush();
            }
            iterator.remove();
        }
    }
}
