package graphingcalculator3d.client.gui;

import graphingcalculator3d.common.util.nbthandler.Domain;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.math.Vec3d;

import java.util.function.Consumer;

public class Vec3dTextField extends GuiTextField {
    private final String name;
    private final GuiGC gui;
    private final Consumer<Vec3d> consumer;

    public Vec3dTextField(String name, GuiGC gui, int p_i45542_1_, FontRenderer p_i45542_2_, int p_i45542_3_, int p_i45542_4_, int p_i45542_5_, int p_i45542_6_, Consumer<Vec3d> consumer) {
        super(p_i45542_1_, p_i45542_2_, p_i45542_3_, p_i45542_4_, p_i45542_5_, p_i45542_6_);
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
