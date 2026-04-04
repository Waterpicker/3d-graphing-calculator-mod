package graphingcalculator3d.client.gui;

@FunctionalInterface
public interface ThrowingRunnable<T extends NumberFormatException>
{
    void run() throws T;
}
