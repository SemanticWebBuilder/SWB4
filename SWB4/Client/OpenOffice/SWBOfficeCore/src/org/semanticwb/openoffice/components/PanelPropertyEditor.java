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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
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

    class PropertyEditor extends AbstractCellEditor implements TableCellEditor, ChangeListener, KeyListener, ActionListener
    {

        public PropertyEditor()
        {
        }

        @Override
        public Object getCellEditorValue()
        {
            int row = jTableProperties.getEditingRow();
            int col = jTableProperties.getEditingColumn();
            Object obj = jTableProperties.getModel().getValueAt(row, col);
            return obj;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column)
        {

            Object prop = table.getModel().getValueAt(row, 0);
            if (prop instanceof PropertyInfo)
            {
                PropertyInfo propertyInfo = (PropertyInfo) prop;
                if (propertyInfo.type.equalsIgnoreCase("boolean"))
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
                if (propertyInfo.type.equalsIgnoreCase("integer"))
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
                if (propertyInfo.type.equalsIgnoreCase("string"))
                {
                    if (propertyInfo.values == null || propertyInfo.values.length == 0)
                    {
                        StringEditor stringEditor = new StringEditor(row, column);
                        stringEditor.addKeyListener(this);
                        stringEditor.addFocusListener(new FocusListener()
                        {

                            @Override
                            public void focusGained(FocusEvent e)
                            {
                                if (e.getSource() instanceof StringEditor)
                                {
                                    StringEditor stringEditor = (StringEditor) e.getSource();
                                    stringEditor.setSelectionStart(0);
                                    stringEditor.setSelectionEnd(stringEditor.getText().length());
                                }
                            }

                            @Override
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
                    else
                    {
                        MultiValueEditor multiValueEditor = new MultiValueEditor(row, column);
                        for (String valueToCombo : propertyInfo.values)
                        {
                            multiValueEditor.addItem(valueToCombo);
                        }
                        if (value != null)
                        {
                            multiValueEditor.setSelectedItem(value);
                        }
                        else
                        {
                            multiValueEditor.setSelectedIndex(0);
                        }
                        multiValueEditor.addActionListener(this);
                        return multiValueEditor;
                    }
                }
            }
            return null;
        }

        @Override
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

        @Override
        public void keyTyped(KeyEvent e)
        {
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
        }

        @Override
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

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() instanceof MultiValueEditor)
            {
                MultiValueEditor multiValueEditor = (MultiValueEditor) e.getSource();
                DefaultTableModel model = (DefaultTableModel) jTableProperties.getModel();
                String valueSelected = multiValueEditor.getSelectedItem().toString();
                model.setValueAt(valueSelected, multiValueEditor.row, multiValueEditor.col);
            }
        }
    }

    public void loadProperties(Map<PropertyInfo, Object> properties)
    {
        DefaultTableModel model = (DefaultTableModel) jTableProperties.getModel();
        TableColumn col = jTableProperties.getColumnModel().getColumn(1);
        col.setCellEditor(new PropertyEditor());
        col.setCellRenderer(new PropertyRender());
        int rows = model.getRowCount();
        for (int i = 0; i < rows; i++)
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

        @Override
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
