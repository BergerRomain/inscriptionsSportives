package ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

public class DeleteButtonEditor extends DefaultCellEditor {
    
 protected JButton button;
 private DeleteButtonListener bListener = new DeleteButtonListener();
  
 public DeleteButtonEditor(JCheckBox checkBox) {
    super(checkBox);
    button = new JButton();
    button.setOpaque(true);
    button.addActionListener(bListener);
 }

 public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    bListener.setRow(row);
    bListener.setTable(table);
    button.setText( (value ==null) ? "" : value.toString() );
     return button;
 }
  
 class DeleteButtonListener implements ActionListener {
       
      private int row;
      private JTable table;
       
      public void setRow(int row){this.row = row;}
      public void setTable(JTable table){this.table = table;}
       
      public void actionPerformed(ActionEvent event) {
       if(table.getRowCount() > 0){
          ((ZModel)table.getModel()).removeRow(this.row);
           
       }
    }
 }        
}
