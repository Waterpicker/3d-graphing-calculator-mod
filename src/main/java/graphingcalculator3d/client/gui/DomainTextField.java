package graphingcalculator3d.client.gui;

import graphingcalculator3d.common.util.nbthandler.Domain;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;

import java.util.function.Consumer;

public class DomainTextField extends TextFieldWidget {
    private final String name;
    private final GuiGC gui;
    private final Consumer<Domain> consumer;

    public DomainTextField(String name, GuiGC gui, int p_i45542_1_, FontRenderer p_i45542_2_, int p_i45542_3_, int p_i45542_4_, int p_i45542_5_, int p_i45542_6_, Consumer<Domain> consumer) {
        super(p_i45542_2_, p_i45542_1_, p_i45542_3_, p_i45542_4_, p_i45542_5_, "");
        this.name = name;
        this.gui = gui;
        this.consumer = consumer;
    }

    public void done() throws GuiParseException {
        try {
            String[] array = getText().split(",|\\s+");
            consumer.accept(new Domain(Double.parseDouble(array[0]), Double.parseDouble(array[1])));
        } catch (NumberFormatException e) {
            gui.setErrored("Invalid number formatting.");
        } catch (Exception e) {
            gui.setErrored("Invalid " + name + ". Please enter two (2) floats.");
        }
    }
}
