/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ViewProperties.java
 *
 * Created on 10/02/2009, 06:37:12 PM
 */
package org.semanticwb.openoffice.ui.wizard;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.netbeans.spi.wizard.WizardPage;
import org.semanticwb.office.interfaces.PortletInfo;
import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.util.NumericPlainDocument;

/**
 *
 * @author victor.lorenzana
 */
public class ViewProperties extends WizardPage
{

    class PropertyEditor extends AbstractCellEditor implements TableCellEditor, ChangeListener,KeyListener
    {

        public PropertyEditor()
        {
        }

        public Object getCellEditorValue()
        {
            int row = jTableProperties.getEditingRow();
            int col = jTableProperties.getEditingColumn();
            Object obj = jTableProperties.getModel().getValueAt(row, col);
            return obj;
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column)
        {

            Object prop = table.getModel().getValueAt(row, 0);
            if (prop instanceof PropertyInfo)
            {
                PropertyInfo PropertyInfo = (PropertyInfo) prop;
                if (PropertyInfo.type.equalsIgnoreCase("boolean"))
                {
                    CellComboBox JCheckBox = new CellComboBox(row, column);
                    JCheckBox.addChangeListener(this);
                    if (value == null)
                    {
                        JCheckBox.setSelected(false);
                    }
                    else
                    {
                        if (value instanceof Boolean)
                        {
                            JCheckBox.setSelected((Boolean) value);
                        }
                    }
                    return JCheckBox;
                }
                if (PropertyInfo.type.equalsIgnoreCase("integer"))
                {
                    CellTextBox JTextField = new CellTextBox(row,column);
                    JTextField.addKeyListener(this);
                    JTextField.setDocument(new NumericPlainDocument(4, new DecimalFormat()));
                    if (value == null)
                    {
                        JTextField.setText("0");
                    }
                    else
                    {
                        JTextField.setText(value.toString());
                    }
                    return JTextField;
                }
            }
            return null;
        }

        public void stateChanged(ChangeEvent e)
        {
            if (e.getSource() instanceof CellComboBox)
            {
                CellComboBox CellComboBox = (CellComboBox) e.getSource();
                jTableProperties.setValueAt(CellComboBox.isSelected(), CellComboBox.row, CellComboBox.col);
            }

        }

        public void keyTyped(KeyEvent e)
        {
            if(e.getSource() instanceof CellTextBox)
            {
                CellComboBox CellComboBox=(CellComboBox)e.getSource();
                jTableProperties.setValueAt(CellComboBox.getText(), CellComboBox.row, CellComboBox.col);
            }
        }

        public void keyPressed(KeyEvent e)
        {
            if(e.getSource() instanceof CellTextBox)
            {
                CellComboBox CellComboBox=(CellComboBox)e.getSource();
                jTableProperties.setValueAt(CellComboBox.getText(), CellComboBox.row, CellComboBox.col);
            }
        }

        public void keyReleased(KeyEvent e)
        {
            if(e.getSource() instanceof CellTextBox)
            {
                CellComboBox CellComboBox=(CellComboBox)e.getSource();
                jTableProperties.setValueAt(CellComboBox.getText(), CellComboBox.row, CellComboBox.col);
            }
        }


    }

    class CellComboBox extends JCheckBox
    {

        int row, col;

        public CellComboBox(int row, int col)
        {
            this.row = row;
            this.col = col;
        }
    }
    class CellTextBox extends JTextField
    {

        int row, col;

        public CellTextBox(int row, int col)
        {
            this.row = row;
            this.col = col;
        }
    }
    private PortletInfo portletInfo;

    /** Creates new form ViewProperties */
    public ViewProperties(PortletInfo portletInfo)
    {

        initComponents();
        this.portletInfo = portletInfo;
        loadProperties();
    }

    private void loadProperties()
    {
        DefaultTableModel model = (DefaultTableModel) jTableProperties.getModel();
        TableColumn col = jTableProperties.getColumnModel().getColumn(1);
        col.setCellEditor(new PropertyEditor());
        col.setCellRenderer(new PropertyRender());
        int rows = model.getRowCount();
        for (int i = 1; i <= rows; i++)
        {
            model.removeRow(0);
        }
        try
        {
            for (PropertyInfo info : OfficeApplication.getOfficeDocumentProxy().getPortletProperties(portletInfo))
            {
                Object[] data =
                {
                    info, null
                };
                model.addRow(data);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getDescription()
    {
        return "Indicar propiedades de presentación";
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProperties = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jTableProperties.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Propiedad", "Valor"
            }
        ));
        jTableProperties.setCellSelectionEnabled(true);
        jTableProperties.setRowHeight(24);
        jTableProperties.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableProperties);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableProperties;
    // End of variables declaration//GEN-END:variables
}

class PropertyRender implements TableCellRenderer
{

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
    {
        Object prop = table.getModel().getValueAt(row, 0);
        if (prop instanceof PropertyInfo)
        {
            PropertyInfo PropertyInfo = (PropertyInfo) prop;
            if (PropertyInfo.type.equalsIgnoreCase("boolean"))
            {
                JCheckBox JCheckBox = new JCheckBox();
                if (value == null)
                {
                    JCheckBox.setSelected(false);
                }
                else
                {
                    if (value instanceof Boolean)
                    {
                        JCheckBox.setSelected((Boolean) value);
                    }
                }
                return JCheckBox;
            }
            if (PropertyInfo.type.equalsIgnoreCase("integer"))
            {
                JTextField JTextField = new JTextField();
                JTextField.setDocument(new NumericPlainDocument(4, new DecimalFormat()));
                if (value == null)
                {
                    JTextField.setText("0");
                }
                else
                {
                    JTextField.setText(value.toString());
                }
                return JTextField;
            }
        }
        return null;
    }
}
