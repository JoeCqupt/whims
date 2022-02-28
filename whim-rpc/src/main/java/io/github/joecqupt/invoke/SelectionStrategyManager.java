package io.github.joecqupt.invoke;

public class SelectionStrategyManager {
    private static SelectionStrategy random = new RandomSelectionStrategy();

    public static SelectionStrategy random() {
        return random;
    }
}
