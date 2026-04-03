package graphingcalculator3d.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;

public class DiscContinutityTextField extends TextFieldWidget {
    private final GuiGC gui;

    public DiscContinutityTextField(GuiGC gui, FontRenderer fontRenderer, int x, int y, int fieldWidth, int defaultHeight) {
        super(fontRenderer, x, y, fieldWidth, defaultHeight, StringTextComponent.EMPTY);
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
