package graphingcalculator3d.client.gui;

import graphingcalculator3d.common.util.nbthandler.Domain;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class DomainTextField extends EditBox {
    private final String name;
    private final GuiGC gui;
    private final Consumer<Domain> consumer;

    public DomainTextField(String name, GuiGC gui, Font fontRenderer, int x, int y, int width, int height, Consumer<Domain> consumer) {
        super(fontRenderer, x, y, width, height, Component.empty());
        this.name = name;
        this.gui = gui;
        this.consumer = consumer;
    }

    public void done() throws GuiParseException {
        try {
            String[] array = getValue().split(",|\\s+");
            consumer.accept(new Domain(Double.parseDouble(array[0]), Double.parseDouble(array[1])));
        } catch (NumberFormatException e) {
            gui.setErrored("Invalid number formatting.");
            e.printStackTrace();
        } catch (Exception e) {
            gui.setErrored("Invalid " + name + ". Please enter two (2) floats.");
        }
    }
}
