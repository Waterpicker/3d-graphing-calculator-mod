package graphingcalculator3d.client.gui;

import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;

import java.util.Random;

public class ColorTextField extends TextFieldWidget {
    private final GuiGC gui;
    private final Random random = new Random();

    public ColorTextField(GuiGC gui, int id, FontRenderer fontRenderer, int x, int y, int width, int height) {
        super(fontRenderer, x, y, width, height, "");
        this.gui = gui;
    }

    public void done() throws GuiParseException {
        String[] array = getText().split(",|\\s+");

        TileGCBase tile = gui.tile;

        try {
            setColor(tile.rgba, array, 0);
            setColor(tile.rgba, array, 1);
            setColor(tile.rgba, array, 2);
            setColor(tile.rgba, array, 3);

            if (Boolean.parseBoolean(array[4])) {
                tile.rgba[4] = Integer.parseInt(array[5]);
            }
        } catch (Exception e) {
            gui.setErrored("Invalid RGBA Array. Please enter four (4) integers, a boolean, and one (1) more integer.");
        }
    }

    private void setColor(int[] rgba, String[] array, int index) {
        String string = array[index];
        rgba[index] = string.startsWith("r")
                ? (index == 3 ? random.nextInt(125) + 130 : random.nextInt(255))
                : Integer.parseInt(string);
    }
}
