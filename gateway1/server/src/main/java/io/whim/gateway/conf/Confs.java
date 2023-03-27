package io.whim.gateway.conf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static io.whim.gateway.conf.ConfOptions.CONF_FILE_LOCATION;

public class Confs {
    private static List<Conf> confs = new ArrayList<>();

    private static Comparator<Conf> comparator = (c1, c2)->{
        return c1.order() - c2.order();
    };

    public static String get(String key) {
        for (Conf c : confs) {
            Object value = c.get(key);
            if (value != null) {
                return value.toString();
            }
        }
        return null;
    }

    public static void loadConf(String[] args) {
        confs.add(new EnvConf());
        confs.add(new ArgsConf(args));
        confs.sort(comparator);
        String confFileLocation = get(CONF_FILE_LOCATION);
        confs.add(new PropertiesConf(confFileLocation));
        confs.sort(comparator);
    }
}
