package io.whim.rpc.common;

import io.netty.util.AttributeKey;
import io.whim.rpc.service.invoke.RpcMeta;

public interface Constants {
    String HASH_SIGN = "#";
    String SEMICOLON = ";";
    String COLON = ":";


    byte STATUS_SUCCESS = 1;
    byte STATUS_FAIL = 0;



    String RPC_META = "RPC_META";

    AttributeKey<RpcMeta> RPC_META_ATTRIBUTE_KEY = AttributeKey.valueOf(RPC_META);
}