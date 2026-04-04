package graphingcalculator3d.client.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.joml.Vector3d;

import java.util.function.Consumer;

public class Vec3dTextField extends EditBox {
    private final String name;
    private final GuiGC gui;
    private final Consumer<Vector3d> consumer;

    public Vec3dTextField(String name, GuiGC gui, Font fontRenderer, int x, int y, int width, int height, Consumer<Vector3d> consumer) {
        super(fontRenderer, x, y, width, height, Component.empty());
        this.name = name;
        this.gui = gui;
        this.consumer = consumer;
    }

    public void done() throws GuiParseException {
        try {
            String[] array = getValue().split(",|\\s+");
            consumer.accept(new Vector3d(Double.parseDouble(array[0]), Double.parseDouble(array[1]), Double.parseDouble(array[2])));
        } catch (NumberFormatException e) {
            gui.setErrored("Invalid number formatting.");
        } catch (Exception e) {
            gui.setErrored("Invalid " + name + ". Please enter three (3) doubles.");
        }
    }
}
