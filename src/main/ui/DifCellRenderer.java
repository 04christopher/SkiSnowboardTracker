package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

//taken from stack overflow
//changes color of difficulty text based on value
public class DifCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component
            getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                      boolean hasFocus, int row, int column) {
        Component rendererComponent
                = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


        //looking at it now I can go back and change the Integer.toString for difficulty
        //and then just make it so 1 == difficulty in the if stat3ement etc., you can clean uyp later.
        String difficulty = (String) value;
        if ("1".equalsIgnoreCase(difficulty)) {
            rendererComponent.setForeground(Color.GREEN);
        } else if ("2".equalsIgnoreCase(difficulty)) {
            rendererComponent.setForeground(Color.BLUE);
        } else if ("3".equalsIgnoreCase(difficulty)) {
            rendererComponent.setForeground(Color.BLACK);
        } else if ("4".equalsIgnoreCase(difficulty)) {
            rendererComponent.setForeground(Color.RED);
        }

        return rendererComponent;
    }
}