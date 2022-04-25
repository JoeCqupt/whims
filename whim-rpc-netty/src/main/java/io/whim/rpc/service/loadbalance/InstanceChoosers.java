package io.whim.rpc.service.loadbalance;

import io.whim.rpc.service.loadbalance.random.RandomChooser;

public class InstanceChoosers {

    public static InstanceChooser random() {
        return new RandomChooser();
    }
}
