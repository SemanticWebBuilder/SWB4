/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applets.ftp;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author victor.lorenzana
 */
public class ColumnHeaderListener extends MouseAdapter {

    
    private final JTable table;
    public ColumnHeaderListener(JTable table)
    {        
        this.table=table;
    }
    @Override
    public void mouseClicked(MouseEvent evt) {
        JTable table = ((JTableHeader) evt.getSource()).getTable();
        TableColumnModel colModel = table.getColumnModel();

        int index = colModel.getColumnIndexAtX(evt.getX());
        if (index == -1) {
          return;
        }
        Rectangle headerRect = table.getTableHeader().getHeaderRect(index);
        if (index == 0) {
          headerRect.width -= 10;
        } else {
          headerRect.grow(-10, 0);
        }
        if (!headerRect.contains(evt.getX(), evt.getY())) {
          int vLeftColIndex = index;
          if (evt.getX() < headerRect.x) {
            vLeftColIndex--;
          }
        }
        jTableFileModel model=(jTableFileModel)table.getModel();
        switch(index)
        {
            case 0:
                System.out.println("index: "+index);
                model.reorderByName();
                model.fireTableDataChanged();
                table.updateUI();
                break;
            case 1:
                System.out.println("index: "+index);
                model.reorderBySize();
                model.fireTableDataChanged();
                table.updateUI();
                break;
            case 2:
                System.out.println("index: "+index);
                model.reorderByDate();
                model.fireTableDataChanged();
                table.updateUI();
        }
        
  }

}
