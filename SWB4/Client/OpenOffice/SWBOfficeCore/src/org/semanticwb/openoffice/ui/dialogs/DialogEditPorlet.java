/*
 * DialogContentPublicationInformation.java
 *
 * Created on 29 de diciembre de 2008, 04:18 PM
 */
package org.semanticwb.openoffice.ui.dialogs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.semanticwb.office.interfaces.CalendarInfo;
import org.semanticwb.office.interfaces.PortletInfo;
import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.openoffice.OfficeApplication;

/**
 *
 * @author  victor.lorenzana
 */
public class DialogEditPorlet extends javax.swing.JDialog
{

    private String repositoryName,  contentID;
    private PortletInfo pageInformation;
    public boolean isCancel = true;
    ArrayList<CalendarInfo> added = new ArrayList<CalendarInfo>();

    /** Creates new form DialogContentPublicationInformation */
    public DialogEditPorlet(java.awt.Frame parent, boolean modal, PortletInfo pageInformation, String repositoryName, String contentID)
    {
        super(parent, modal);
        initComponents();
        this.pageInformation = pageInformation;
        this.repositoryName = repositoryName;
        this.contentID = contentID;
        loadProperties();
        loadCalendars();
        setLocationRelativeTo(null);
        this.jTableScheduler.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {

            public void valueChanged(ListSelectionEvent e)
            {
                jButtonDeleteScheduler.setEnabled(false);
                jButtonEditEcheduler.setEnabled(false);
                if (e.getFirstIndex() != -1)
                {
                    jButtonDeleteScheduler.setEnabled(true);
                    jButtonEditEcheduler.setEnabled(true);
                }
            }
        });
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
                if (PropertyInfo.type != null)
                {
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

    private void loadCalendars()
    {
        DefaultTableModel model = (DefaultTableModel) jTableScheduler.getModel();
        int rows = model.getRowCount();
        for (int i = 1; i <= rows; i++)
        {
            model.removeRow(0);
        }
        try
        {
            for (CalendarInfo info : OfficeApplication.getOfficeDocumentProxy().getCalendars(pageInformation))
            {
                Object[] data =
                {
                    info, info.active
                };
                model.addRow(data);
            }
            for (CalendarInfo info : added)
            {
                Object[] data =
                {
                    info, info.active
                };
                model.addRow(data);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

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
            for (PropertyInfo info : OfficeApplication.getOfficeDocumentProxy().getPortletProperties(repositoryName, contentID))
            {
                String value = OfficeApplication.getOfficeDocumentProxy().getPropertyValue(pageInformation, info);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanelInformation = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProperties = new javax.swing.JTable();
        jPanelSchedule = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableScheduler = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonAddCalendar = new javax.swing.JButton();
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

        jLabel1.setText("Título:");

        jLabel2.setText("Descripción:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        jLabel3.setText("Activo:");

        jLabel4.setText("Sitio:");

        jLabel5.setText("Sitio de prueba");

        jLabel6.setText("Página:");

        jLabel7.setText("Página");

        jLabel8.setText("Versión publicada:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox1, 0, 270, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(5, 5, 5)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel7)))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );

        jTabbedPane1.addTab("Información", jPanel2);

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
        jTableProperties.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableProperties.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableProperties);

        jPanelInformation.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Propiedades de publicación", jPanelInformation);

        jPanelSchedule.setLayout(new java.awt.BorderLayout());

        jTableScheduler.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Titulo", "Activo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableScheduler.setColumnSelectionAllowed(true);
        jTableScheduler.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTableScheduler);
        jTableScheduler.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jPanelSchedule.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);

        jButtonAddCalendar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/add.png"))); // NOI18N
        jButtonAddCalendar.setToolTipText("Agregar");
        jButtonAddCalendar.setFocusable(false);
        jButtonAddCalendar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonAddCalendar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonAddCalendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddCalendarActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonAddCalendar);
        jToolBar1.add(jSeparator1);

        jButtonEditEcheduler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/edit.png"))); // NOI18N
        jButtonEditEcheduler.setToolTipText("Editar");
        jButtonEditEcheduler.setEnabled(false);
        jButtonEditEcheduler.setFocusable(false);
        jButtonEditEcheduler.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonEditEcheduler.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonEditEcheduler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditEchedulerActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonEditEcheduler);
        jToolBar1.add(jSeparator2);

        jButtonDeleteScheduler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/delete.png"))); // NOI18N
        jButtonDeleteScheduler.setToolTipText("Eliminar");
        jButtonDeleteScheduler.setEnabled(false);
        jButtonDeleteScheduler.setFocusable(false);
        jButtonDeleteScheduler.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonDeleteScheduler.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonDeleteScheduler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteSchedulerActionPerformed(evt);
            }
        });
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
    isCancel = true;
    this.setVisible(false);
}//GEN-LAST:event_jButtonCancelActionPerformed

private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
    isCancel = false;
    this.setVisible(false);
}//GEN-LAST:event_jButtonOKActionPerformed

private void jButtonAddCalendarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddCalendarActionPerformed
{//GEN-HEADEREND:event_jButtonAddCalendarActionPerformed
    DialogCalendar dialogCalendar = new DialogCalendar(new Frame(), true);
    dialogCalendar.setVisible(true);
    if (!dialogCalendar.isCanceled)
    {
        Document xmlCalendar = dialogCalendar.getDocument();
        XMLOutputter out = new XMLOutputter();
        CalendarInfo cal = new CalendarInfo();
        cal.xml = out.outputString(xmlCalendar);
        cal.title = dialogCalendar.jTextFieldTitle.getText();
        DefaultTableModel model = (DefaultTableModel) jTableScheduler.getModel();
        Object[] data =
        {
            cal, cal.active
        };
        added.add(cal);
        model.addRow(data);
    }
}//GEN-LAST:event_jButtonAddCalendarActionPerformed

private void jButtonEditEchedulerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonEditEchedulerActionPerformed
{//GEN-HEADEREND:event_jButtonEditEchedulerActionPerformed
    if (jTableScheduler.getSelectedRow() != -1)
    {
        CalendarInfo cal = (CalendarInfo) jTableScheduler.getModel().getValueAt(jTableScheduler.getSelectedRow(), 0);
        DialogCalendar dialogCalendar = new DialogCalendar(new Frame(), true);
        SAXBuilder SAXBuilder = new SAXBuilder();
        ByteArrayInputStream in = new ByteArrayInputStream(cal.xml.getBytes());
        try
        {
            Document document = SAXBuilder.build(in);
            dialogCalendar.setDocument(document, cal.title);
            dialogCalendar.setVisible(true);
            if (!dialogCalendar.isCanceled)
            {
                cal.title = dialogCalendar.jTextFieldTitle.getText();
                XMLOutputter out = new XMLOutputter();
                document = dialogCalendar.getDocument();
                cal.xml = out.outputString(document);
                loadCalendars();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}//GEN-LAST:event_jButtonEditEchedulerActionPerformed

private void jButtonDeleteSchedulerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonDeleteSchedulerActionPerformed
{//GEN-HEADEREND:event_jButtonDeleteSchedulerActionPerformed
    if (jTableScheduler.getSelectedRow() != -1)
    {
        int res = JOptionPane.showConfirmDialog(this, "¿Desea eliminar la calendarización?", this.getTitle(), JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION)
        {
            CalendarInfo cal = (CalendarInfo) jTableScheduler.getModel().getValueAt(jTableScheduler.getSelectedRow(), 0);
            if (cal.id == null)
            {
                DefaultTableModel model = (DefaultTableModel) jTableScheduler.getModel();
                model.removeRow(jTableScheduler.getSelectedRow());
                added.remove(cal);
                if (model.getRowCount() == 0 || jTableScheduler.getSelectedRow() == -1)
                {
                    this.jButtonDeleteScheduler.setEnabled(false);
                    this.jButtonEditEcheduler.setEnabled(false);
                }
            }
            else
            {
                try
                {
                    OfficeApplication.getOfficeDocumentProxy().deleteCalendar(pageInformation, cal);
                    loadCalendars();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}//GEN-LAST:event_jButtonDeleteSchedulerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonAddCalendar;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDeleteScheduler;
    private javax.swing.JButton jButtonEditEcheduler;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelInformation;
    private javax.swing.JPanel jPanelOptions;
    private javax.swing.JPanel jPanelSchedule;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableProperties;
    private javax.swing.JTable jTableScheduler;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
