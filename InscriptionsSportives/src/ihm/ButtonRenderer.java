package ihm;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

public class ButtonRenderer extends JButton implements TableCellRenderer{

 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
    setText((value != null) ? value.toString() : "");
    return this;
 }
}
