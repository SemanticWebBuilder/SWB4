/*
 * ModelRelation.java
 *
 * Created on 19 de febrero de 2002, 15:30
 */
package applets.dragdrop;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author  Administrador
 * @version 
 */
class ModelRelation extends DefaultTableModel 
{
    public ModelRelation()
    {
        super();
    }

    public ModelRelation(Object[] columnNames,int numRows)
    {
        super(columnNames,numRows);
    }

    //new DefaultTableModel(


    public boolean isCellEditable(int rowIndex,int columIndex)
    {
        return false;
    }
}

