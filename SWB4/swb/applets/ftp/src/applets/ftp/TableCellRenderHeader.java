/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applets.ftp;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author victor.lorenzana
 */
public class TableCellRenderHeader implements TableCellRenderer {

    public final JButton name=new JButton();
    public final JButton size=new JButton();
    public final JButton date=new JButton();
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        switch(column)
        {
            case 0:
                name.setText(value.toString());
                return name;
            case 1:
                size.setText(value.toString());
                return size;
            case 2:
                date.setText(value.toString());
                return date;
        }
        return null;
    }

}
