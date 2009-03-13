/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PropertyEditor.java
 *
 * Created on 10/03/2009, 08:52:37 AM
 */
package org.semanticwb.openoffice.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.semanticwb.office.interfaces.PropertyInfo;

/**
 *
 * @author victor.lorenzana
 */
public class PanelPropertyEditor extends javax.swing.JPanel
{

    /** Creates new form PropertyEditor */
    public PanelPropertyEditor()
    {
        initComponents();
    }

    class PropertyEditor extends AbstractCellEditor implements TableCellEditor, ChangeListener, KeyListener
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
                    BooleanEditor jCheckBox = new BooleanEditor(row, column);
                    jCheckBox.setBackground(new Color(255, 255, 255));
                    JPanel panel = new JPanel();
                    panel.add(jCheckBox);
                    panel.setBackground(new Color(255, 255, 255));
                    jCheckBox.addChangeListener(this);
                    if (value == null)
                    {
                        jCheckBox.setSelected(false);
                    }
                    else
                    {
                        if (value instanceof Boolean)
                        {
                            jCheckBox.setSelected((Boolean) value);
                        }
                    }
                    return panel;
                }
                if (PropertyInfo.type.equalsIgnoreCase("integer"))
                {
                    IntegerEditor integerEditor = new IntegerEditor(row, column);
                    integerEditor.addChangeListener(this);
                    integerEditor.addKeyListener(this);
                    if (value == null)
                    {
                        integerEditor.setValue(0);
                    }
                    else
                    {
                        int ivalue = 0;
                        try
                        {
                            ivalue = Integer.parseInt(value.toString());

                        }
                        catch (NumberFormatException nfe)
                        {
                            nfe.printStackTrace();
                        }
                        integerEditor.setValue(ivalue);
                    }

                    return integerEditor;
                }
                if (PropertyInfo.type.equalsIgnoreCase("string"))
                {
                    StringEditor stringEditor = new StringEditor(row, column);
                    stringEditor.addKeyListener(this);
                    stringEditor.addFocusListener(new FocusListener()
                    {

                        public void focusGained(FocusEvent e)
                        {
                            if (e.getSource() instanceof StringEditor)
                            {
                                StringEditor stringEditor = (StringEditor) e.getSource();
                                stringEditor.setSelectionStart(0);
                                stringEditor.setSelectionEnd(stringEditor.getText().length());
                            }
                        }

                        public void focusLost(FocusEvent e)
                        {
                        }
                    });

                    if (value == null)
                    {
                        stringEditor.setText("");
                    }
                    else
                    {
                        stringEditor.setText(value.toString());
                    }

                    return stringEditor;
                }
            }
            return null;
        }

        public void stateChanged(ChangeEvent e)
        {
            if (e.getSource() instanceof BooleanEditor)
            {
                BooleanEditor booleanEditor = (BooleanEditor) e.getSource();
                jTableProperties.setValueAt(booleanEditor.isSelected(), booleanEditor.row, booleanEditor.col);
            }
            if (e.getSource() instanceof IntegerEditor)
            {
                IntegerEditor integerEditor = (IntegerEditor) e.getSource();
                jTableProperties.setValueAt(integerEditor.getValue(), integerEditor.row, integerEditor.col);
            }
            if (e.getSource() instanceof StringEditor)
            {
                StringEditor integerEditor = (StringEditor) e.getSource();
                jTableProperties.setValueAt(integerEditor.getText(), integerEditor.row, integerEditor.col);
            }

        }

        public void keyTyped(KeyEvent e)
        {
        }

        public void keyPressed(KeyEvent e)
        {
        }

        public void keyReleased(KeyEvent e)
        {
            if (e.getSource() instanceof IntegerEditor)
            {
                IntegerEditor CellTextBox = (IntegerEditor) e.getSource();
                if (CellTextBox.getValue() == null)
                {
                    jTableProperties.setValueAt(0, CellTextBox.row, CellTextBox.col);
                }
                else
                {
                    jTableProperties.setValueAt(CellTextBox.getValue(), CellTextBox.row, CellTextBox.col);
                }
            }
            if (e.getSource() instanceof IntegerEditor)
            {
                IntegerEditor integerEditor = (IntegerEditor) e.getSource();
                jTableProperties.setValueAt(integerEditor.getValue(), integerEditor.row, integerEditor.col);
            }
            if (e.getSource() instanceof StringEditor)
            {
                StringEditor integerEditor = (StringEditor) e.getSource();
                jTableProperties.setValueAt(integerEditor.getText(), integerEditor.row, integerEditor.col);
            }
        }
    }

    public void loadProperties(Map<PropertyInfo, Object> properties)
    {
        DefaultTableModel model = (DefaultTableModel) jTableProperties.getModel();
        TableColumn col = jTableProperties.getColumnModel().getColumn(1);
        col.setCellEditor(new PropertyEditor());
        col.setCellRenderer(new PropertyRender());
        int rows=model.getRowCount();
        for(int i=0;i<rows;i++)
        {
            model.removeRow(0);
        }
        for (PropertyInfo property : properties.keySet())
        {
            Object[] data =
            {
                property, properties.get(property)
            };
            model.addRow(data);
        }
    }

    

    public Map<PropertyInfo, String> getProperties()
    {
        HashMap<PropertyInfo, String> properties = new HashMap<PropertyInfo, String>();
        int rows = jTableProperties.getRowCount();
        for (int i = 0; i < rows; i++)
        {
            PropertyInfo prop = (PropertyInfo) jTableProperties.getModel().getValueAt(i, 0);
            String value = jTableProperties.getModel().getValueAt(i, 1).toString();
            properties.put(prop, value);
        }
        return properties;
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
                    JCheckBox jCheckBox = new JCheckBox();
                    jCheckBox.setBackground(new Color(255, 255, 255));
                    JPanel panel = new JPanel();
                    panel.add(jCheckBox);
                    panel.setBackground(new Color(255, 255, 255));
                    if (value == null)
                    {
                        jCheckBox.setSelected(false);
                    }
                    else
                    {
                        if (value instanceof Boolean)
                        {
                            jCheckBox.setSelected((Boolean) value);
                        }
                    }
                    return panel;
                }
                if (PropertyInfo.type.equalsIgnoreCase("integer"))
                {
                    IntegerEditor JTextField = new IntegerEditor(row, column);

                    if (value == null)
                    {
                        JTextField.setValue(0);
                    }
                    else
                    {
                        int ivalue = 0;
                        try
                        {
                            ivalue = Integer.parseInt(value.toString());

                        }
                        catch (NumberFormatException nfe)
                        {
                            nfe.printStackTrace();
                        }
                        JTextField.setValue(ivalue);
                    }
                    return JTextField;
                }
                if (PropertyInfo.type.equalsIgnoreCase("String"))
                {
                    StringEditor JTextField = new StringEditor(row, column);
                    if (value == null)
                    {
                        JTextField.setText("");
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

    class BooleanEditor extends JCheckBox
    {

        int row, col;

        public BooleanEditor(int row, int col)
        {
            this.row = row;
            this.col = col;
        }
    }

    class IntegerEditor extends JSpinner
    {

        int row, col;

        public IntegerEditor(int row, int col)
        {
            this.row = row;
            this.col = col;
            SpinnerModel model = new SpinnerNumberModel(0, 0, 9999, 1);
            this.setModel(model);
            this.setEditor(new JSpinner.NumberEditor(this, "####"));
        }
    }

    class StringEditor extends JTextField
    {

        int row, col;

        public StringEditor(int row, int col)
        {
            this.row = row;
            this.col = col;
        }
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
