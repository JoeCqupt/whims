package io.github.joecqupt.common;

import java.nio.channels.SelectionKey;

public class Test {

    public static void main(String[] args) {
        int read = SelectionKey.OP_READ;
        int write = SelectionKey.OP_WRITE;

        int ops = read | write;
        System.out.println(ops);

        System.out.println(ops & ~write);
    }
}
