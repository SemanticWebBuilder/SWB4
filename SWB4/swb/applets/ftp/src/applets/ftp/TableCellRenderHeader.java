/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applets.ftp;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author victor.lorenzana
 */
public class TableCellRenderHeader implements TableCellRenderer {

    public final JLabel name=new JLabel();
    public final JLabel size=new JLabel();
    public final JLabel date=new JLabel();
    public TableCellRenderHeader(JTable table)
    {
        name.setOpaque(true);
        name.setHorizontalAlignment(JLabel.CENTER);
        name.setBorder(table.getTableHeader().getBorder());
        name.setForeground(table.getTableHeader().getForeground());
        name.setBackground(table.getTableHeader().getBackground());

        size.setOpaque(true);
        size.setHorizontalAlignment(JLabel.CENTER);
        size.setBorder(table.getTableHeader().getBorder());
        size.setForeground(table.getTableHeader().getForeground());
        size.setBackground(table.getTableHeader().getBackground());

        date.setOpaque(true);
        date.setHorizontalAlignment(JLabel.CENTER);
        date.setBorder(table.getTableHeader().getBorder());
        date.setForeground(table.getTableHeader().getForeground());
        date.setBackground(table.getTableHeader().getBackground());


        name.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/flecha_up.png")));
    }
    public void onNewOrder(int index,boolean  asc)
    {
        switch(index)
        {
            case 0:
                if(asc)
                {
                    name.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/flecha_up.png")));
                }
                else
                {
                    name.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/flecha_down.png")));
                }
                size.setIcon(null);
                date.setIcon(null);
                break;
             case 1:
                if(asc)
                {
                    size.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/flecha_up.png")));
                }
                else
                {
                    size.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/flecha_down.png")));
                }
                name.setIcon(null);
                date.setIcon(null);
                break;
            case 2:
                if(asc)
                {
                    date.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/flecha_up.png")));
                }
                else
                {
                    date.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/flecha_down.png")));
                }
                name.setIcon(null);
                size.setIcon(null);
        }
        
    }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        switch(column)
        {
            case 0:
                name.setFont(table.getTableHeader().getFont());
                name.setText(value.toString());
                return name;
            case 1:
                size.setFont(table.getTableHeader().getFont());
                size.setText(value.toString());
                return size;
            case 2:
                date.setFont(table.getTableHeader().getFont());
                date.setText(value.toString());
                return date;
        }
        return null;
    }

}
