/*
 * DialogContentPublicationInformation.java
 *
 * Created on 29 de diciembre de 2008, 04:18 PM
 */
package org.semanticwb.openoffice.ui.dialogs;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.semanticwb.office.interfaces.PortletInfo;
import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.util.NumericPlainDocument;

/**
 *
 * @author  victor.lorenzana
 */
public class DialogEditPorlet extends javax.swing.JDialog
{

    private String repositoryName,  contentID;
    private PortletInfo pageInformation;
    public boolean isCancel=true;
    /** Creates new form DialogContentPublicationInformation */
    public DialogEditPorlet(java.awt.Frame parent, boolean modal, PortletInfo pageInformation, String repositoryName, String contentID)
    {
        super(parent, modal);
        initComponents();
        this.pageInformation = pageInformation;
        this.repositoryName = repositoryName;
        this.contentID = contentID;
        loadProperties();
        setLocationRelativeTo(null);        
    }

    class VersionRender implements TableCellRenderer
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
                    JTextField JTextField = new JTextField();
                    JTextField.setDocument(new NumericPlainDocument(4, new DecimalFormat("####")));
                    if (value == null)
                    {
                        JTextField.setText("0");
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
                        JTextField.setText(String.valueOf(ivalue));
                    }
                    return JTextField;
                }
                if (PropertyInfo.type.equalsIgnoreCase("String"))
                {
                    JTextField JTextField = new JTextField();
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
                    CellComboBox jCheckBox = new CellComboBox(row, column);
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
                    CellTextBox JTextField = new CellTextBox(row, column);
                    JTextField.addKeyListener(this);
                    JTextField.addFocusListener(new FocusListener()
                    {

                        public void focusGained(FocusEvent e)
                        {
                            if (e.getSource() instanceof CellTextBox)
                            {
                                CellTextBox CellTextBox = (CellTextBox) e.getSource();
                                CellTextBox.setSelectionStart(0);
                                CellTextBox.setSelectionEnd(CellTextBox.getText().length());
                            }
                        }

                        public void focusLost(FocusEvent e)
                        {
                        }
                    });

                    JTextField.setDocument(new NumericPlainDocument(4, new DecimalFormat("####")));
                    if (value == null)
                    {
                        JTextField.setText("0");
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
                        JTextField.setText(String.valueOf(ivalue));
                    }

                    return JTextField;
                }
                if (PropertyInfo.type.equalsIgnoreCase("string"))
                {
                    CellTextBox JTextField = new CellTextBox(row, column);
                    JTextField.addKeyListener(this);
                    JTextField.addFocusListener(new FocusListener()
                    {

                        public void focusGained(FocusEvent e)
                        {
                            if (e.getSource() instanceof CellTextBox)
                            {
                                CellTextBox CellTextBox = (CellTextBox) e.getSource();
                                CellTextBox.setSelectionStart(0);
                                CellTextBox.setSelectionEnd(CellTextBox.getText().length());
                            }
                        }

                        public void focusLost(FocusEvent e)
                        {
                        }
                    });

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
        }

        public void keyPressed(KeyEvent e)
        {
        }

        public void keyReleased(KeyEvent e)
        {
            if (e.getSource() instanceof CellTextBox)
            {
                CellTextBox CellTextBox = (CellTextBox) e.getSource();
                if (CellTextBox.getText().isEmpty())
                {
                    jTableProperties.setValueAt("0", CellTextBox.row, CellTextBox.col);
                }
                else
                {
                    jTableProperties.setValueAt(CellTextBox.getText(), CellTextBox.row, CellTextBox.col);
                }
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

    private void loadProperties()
    {
        DefaultTableModel model = (DefaultTableModel) jTableProperties.getModel();
        TableColumn col = jTableProperties.getColumnModel().getColumn(1);
        col.setCellEditor(new PropertyEditor());
        col.setCellRenderer(new VersionRender());
        int rows = model.getRowCount();
        for (int i = 1; i <= rows; i++)
        {
            model.removeRow(0);
        }
        try
        {
            for (PropertyInfo info : OfficeApplication.getOfficeDocumentProxy().getPortletProperties(repositoryName, contentID))
            {
                String value=OfficeApplication.getOfficeDocumentProxy().getPropertyValue(pageInformation, info);
                Object[] data =
                {
                    info, value
                };
                model.addRow(data);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanelOptions = new javax.swing.JPanel();
        jButtonOK = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelInformation = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProperties = new javax.swing.JTable();
        jPanelSchedule = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableScheduler = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonAddScheduler = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButtonEditEcheduler = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButtonDeleteScheduler = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar Propiedades");
        setResizable(false);

        jPanelOptions.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonOK.setText("Aceptar");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });
        jPanelOptions.add(jButtonOK);

        jButtonCancel.setText("Cerrar");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        jPanelOptions.add(jButtonCancel);

        getContentPane().add(jPanelOptions, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(457, 300));

        jPanelInformation.setLayout(new java.awt.BorderLayout());

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

        jPanelInformation.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Propiedades de publicación", jPanelInformation);

        jPanelSchedule.setLayout(new java.awt.BorderLayout());

        jTableScheduler.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Titulo", "Activo"
            }
        ));
        jScrollPane2.setViewportView(jTableScheduler);
        jTableScheduler.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jPanelSchedule.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);

        jButtonAddScheduler.setText("Agregar");
        jButtonAddScheduler.setFocusable(false);
        jButtonAddScheduler.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonAddScheduler.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonAddScheduler);
        jToolBar1.add(jSeparator1);

        jButtonEditEcheduler.setText("Editar");
        jButtonEditEcheduler.setFocusable(false);
        jButtonEditEcheduler.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonEditEcheduler.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonEditEcheduler);
        jToolBar1.add(jSeparator2);

        jButtonDeleteScheduler.setText("Eliminar");
        jButtonDeleteScheduler.setFocusable(false);
        jButtonDeleteScheduler.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonDeleteScheduler.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonDeleteScheduler);

        jPanelSchedule.add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.addTab("Calendarización", jPanelSchedule);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
    
}//GEN-LAST:event_jRadioButton1ActionPerformed

private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
    
}//GEN-LAST:event_jRadioButton2ActionPerformed

private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
    isCancel=true;
    this.setVisible(false);
}//GEN-LAST:event_jButtonCancelActionPerformed

private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
    isCancel=false;
    this.setVisible(false);
}//GEN-LAST:event_jButtonOKActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonAddScheduler;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDeleteScheduler;
    private javax.swing.JButton jButtonEditEcheduler;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelInformation;
    private javax.swing.JPanel jPanelOptions;
    private javax.swing.JPanel jPanelSchedule;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableProperties;
    private javax.swing.JTable jTableScheduler;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
