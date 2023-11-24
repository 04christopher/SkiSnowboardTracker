package ui;

import model.Trail;

import javax.swing.*;
import java.awt.*;

//changes the trail selector from @model to the name
public class TrailCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                         boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Trail) {
            Trail trail = (Trail) value;
            setText(trail.getName());
        }

        return this;
    }
}