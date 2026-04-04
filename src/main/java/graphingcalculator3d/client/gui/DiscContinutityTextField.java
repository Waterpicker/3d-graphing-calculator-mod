package graphingcalculator3d.client.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class DiscContinutityTextField extends EditBox {
    private final GuiGC gui;

    public DiscContinutityTextField(GuiGC gui, Font fontRenderer, int x, int y, int fieldWidth, int defaultHeight) {
        super(fontRenderer, x, y, fieldWidth, defaultHeight, Component.empty());
        this.gui = gui;
    }

    public void done() throws GuiParseException {
        try {
            String[] array = getValue().split(",|\\s+");

            gui.tile.setDiscThresh(Double.parseDouble(array[0]));
            gui.tile.setAggDiscThresh(Double.parseDouble(array[1]));
        } catch (Exception e) {
            gui.setErrored("Invalid Discontinuity Threshold. Please enter a double as the first value in the text field.");
        }
    }
}
