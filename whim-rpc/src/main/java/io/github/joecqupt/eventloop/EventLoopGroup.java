package io.github.joecqupt.eventloop;


import io.github.joecqupt.channel.RpcChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EventLoopGroup {
    private List<EventLoop> eventLoops;
    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public EventLoopGroup(int size)  {
        eventLoops = new ArrayList<EventLoop>(size);
        for (int i = 0; i < size; i++) {
            eventLoops.add(new EventLoop());
        }
    }

    public void register(RpcChannel channel) {
        // choose a event loop
        EventLoop eventLoop = chooseEventLoop();
        channel.register(eventLoop);
    }

    private EventLoop chooseEventLoop() {
        // 随机选一个
        int i = random.nextInt();
        int size = eventLoops.size();
        return eventLoops.get(i % size);
    }
}
