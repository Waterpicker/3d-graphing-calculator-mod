package graphingcalculator3d.client.gui;

import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

import java.util.Random;
import java.util.Scanner;

public class ColorTextField extends GuiTextField {
    private final GuiGC gui;
    private final Random random = new Random();

    public ColorTextField(GuiGC gui, int p_i45542_1_, FontRenderer p_i45542_2_, int p_i45542_3_, int p_i45542_4_, int p_i45542_5_, int p_i45542_6_) {
        super(p_i45542_1_, p_i45542_2_, p_i45542_3_, p_i45542_4_, p_i45542_5_, p_i45542_6_);
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

            if(Boolean.parseBoolean(array[4])) {
                tile.rgba[4] = Integer.parseInt(array[5]);
            }
        } catch (Exception e) {
            gui.setErrored("Invalid RGBA Array. Please enter four (4) integers, a boolean, and one (1) more integer.");
        }

    }

    private void setColor(int[] rgba, String[] array, int index) {
        String string = array[index];

        rgba[index] = string.startsWith("r") ? index == 3 ? random.nextInt(125) + 130 : random.nextInt(255) : Integer.parseInt(string);
    }
}
