package io.whim.rpc.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtils {
    public static String getLocalIp() {
        try {
            InetAddress address =  InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            throw new IllegalStateException("fail getLocalHost", e);
        }
    }
}