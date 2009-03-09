/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ContentProperties.java
 *
 * Created on 10/02/2009, 07:20:58 PM
 */
package org.semanticwb.openoffice.ui.wizard;

import java.awt.BorderLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.netbeans.spi.wizard.WizardPanelNavResult;
import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.openoffice.OfficeApplication;

/**
 *
 * @author victor.lorenzana
 */
public class ContentProperties extends WizardPage
{
    private String contentID;
    public static final String PROPERTIES = "PROPERTIES";

    public ContentProperties()
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
    public void setContentId(String contentID)
    {
        this.contentID=contentID;
    }
    @Override
    protected void renderingPage()
    {
        Map map = this.getWizardDataMap();
        if(map!=null && map.get(SelectCategory.REPOSITORY_ID)!=null &&   map.get(TitleAndDescription.NODE_TYPE)!=null)
        {
            String repositoryName = map.get(SelectCategory.REPOSITORY_ID).toString();
            String type = map.get(TitleAndDescription.NODE_TYPE).toString();
            loadProperties(repositoryName, type);
        }
        super.renderingPage();
    }

    public void loadProperties(String repositoryName, String type)
    {
        TableColumn col = jTableProperties.getColumnModel().getColumn(1);
        col.setCellEditor(new PropertyEditor());
        col.setCellRenderer(new PropertyRender());
        DefaultTableModel model = (DefaultTableModel) jTableProperties.getModel();
        int rows = model.getRowCount();
        for (int i = 1; i <= rows; i++)
        {
            model.removeRow(0);
        }
        try
        {
            PropertyInfo[] props = OfficeApplication.getOfficeDocumentProxy().getContentProperties(repositoryName, type);
            if (props.length == 0)
            {
                this.remove(this.jScrollPane1);
                JPanel panel=new JPanel();
                panel.setBackground(new Color(255,255,255));
                panel.setLayout(new BorderLayout());
                JLabel label=new JLabel("No se tienen propiedades para este tipo de contenido, puede continuar.");
                panel.add(label,BorderLayout.NORTH);
                this.add(panel);
            }
            else
            {
                for (PropertyInfo info : props)
                {
                    Object defaultValue = null;
                    if (info.type.equalsIgnoreCase("string"))
                    {
                        defaultValue = "";
                    }
                    if (info.type.equalsIgnoreCase("integer"))
                    {
                        defaultValue = 0;
                    }
                    if (info.type.equalsIgnoreCase("boolean"))
                    {
                        defaultValue = false;
                    }
                    if(contentID!=null)
                    {
                        defaultValue=OfficeApplication.getOfficeDocumentProxy().getContentProperty(info,repositoryName, contentID);
                    }
                    Object[] data =
                    {
                        info, defaultValue
                    };
                    model.addRow(data);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public WizardPanelNavResult allowNext(String arg, Map map, Wizard wizard)
    {
        return allowFinish(arg, map, wizard);
    }

    @Override
    public WizardPanelNavResult allowFinish(String arg, Map map, Wizard wizard)
    {
        WizardPanelNavResult result = WizardPanelNavResult.PROCEED;
        HashMap<PropertyInfo, String> properties = new HashMap<PropertyInfo, String>();
        int rows = jTableProperties.getRowCount();
        for (int i = 0; i < rows; i++)
        {
            PropertyInfo prop = (PropertyInfo) jTableProperties.getModel().getValueAt(i, 0);
            String value = jTableProperties.getModel().getValueAt(i, 0).toString();
            if (value.isEmpty() && prop.isRequired)
            {
                JOptionPane.showMessageDialog(this, "¡Debe indicar " + prop + "!", getDescription(), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                jTableProperties.changeSelection(i, 1, false, false);
                return WizardPanelNavResult.REMAIN_ON_PAGE;
            }
        }
        PropertyInfo[] props = new PropertyInfo[rows];
        Object[] values = new Object[rows];
        for (int i = 0; i < rows; i++)
        {
            PropertyInfo prop = (PropertyInfo) jTableProperties.getModel().getValueAt(i, 0);
            Object value = jTableProperties.getModel().getValueAt(i, 1);
            props[i] = prop;
            values[i] = value;
        }
        for (int i = 0; i < rows; i++)
        {
            PropertyInfo prop = (PropertyInfo) jTableProperties.getModel().getValueAt(i, 0);
            String value = jTableProperties.getModel().getValueAt(i, 1).toString();
            properties.put(prop, value);
        }
        String repositoryName = map.get(SelectCategory.REPOSITORY_ID).toString();
        String type = map.get(TitleAndDescription.NODE_TYPE).toString();
        loadProperties(repositoryName, type);
        try
        {
            OfficeApplication.getOfficeDocumentProxy().validateContentValues(repositoryName, props, values);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), getDescription(), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            return WizardPanelNavResult.REMAIN_ON_PAGE;
        }
        map.put(PROPERTIES, properties);
        return result;
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
        jTableProperties.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTableProperties;
    // End of variables declaration//GEN-END:variables

    public static String getDescription()
    {
        return "Indicar propiedades de contenido";
    }
}
