package graphingcalculator3d.client.gui;

@FunctionalInterface
public interface ThrowingRunnable<T extends NumberFormatException> {
    public void run() throws T;
}
