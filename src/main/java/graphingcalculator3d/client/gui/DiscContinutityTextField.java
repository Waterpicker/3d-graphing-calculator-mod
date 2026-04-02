package graphingcalculator3d.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;

import java.util.Scanner;

public class DiscContinutityTextField extends TextFieldWidget {
    private final GuiGC gui;

    public DiscContinutityTextField(GuiGC gui, int i, FontRenderer fontRenderer, int i1, int i2, int fieldWidth, int defaultHeight) {
        super(fontRenderer, i, i1, i2, fieldWidth, "");
        this.gui = gui;
    }

    public void done() throws GuiParseException {
        try {
            String[] array = getText().split(",|\\s+");

            gui.tile.setDiscThresh(Double.parseDouble(array[0]));
            gui.tile.setAggDiscThresh(Double.parseDouble(array[1]));
        } catch (Exception e) {
            gui.setErrored("Invalid Discontinuity Threshold. Please enter a double as the first value in the text field.");
        }
    }
}
