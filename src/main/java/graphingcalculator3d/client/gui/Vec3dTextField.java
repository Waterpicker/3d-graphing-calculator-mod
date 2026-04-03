package graphingcalculator3d.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.math.Vec3d;

import java.util.function.Consumer;

public class Vec3dTextField extends TextFieldWidget {
    private final String name;
    private final GuiGC gui;
    private final Consumer<Vec3d> consumer;

    public Vec3dTextField(String name, GuiGC gui, int id, FontRenderer fontRenderer, int x, int y, int width, int height, Consumer<Vec3d> consumer) {
        super(fontRenderer, x, y, width, height, "");
        this.name = name;
        this.gui = gui;
        this.consumer = consumer;
    }

    public void done() throws GuiParseException {
        try {
            String[] array = getText().split(",|\\s+");
            consumer.accept(new Vec3d(Double.parseDouble(array[0]), Double.parseDouble(array[1]), Double.parseDouble(array[2])));
        } catch (NumberFormatException e) {
            gui.setErrored("Invalid number formatting.");
        } catch (Exception e) {
            gui.setErrored("Invalid " + name + ". Please enter three (3) doubles.");
        }
    }
}
