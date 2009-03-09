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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
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

    public static final String PROPERTIES = "PROPERTIES";

    public ContentProperties()
    {
        initComponents();
    }

    @Override
    protected void renderingPage()
    {
        Map map = this.getWizardDataMap();
        String repositoryName = map.get(SelectCategory.REPOSITORY_ID).toString();
        String type = map.get(TitleAndDescription.NODE_TYPE).toString();
        loadProperties(repositoryName, type);
        super.renderingPage();
    }

    private void loadProperties(String repositoryName, String type)
    {
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

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableProperties;
    // End of variables declaration//GEN-END:variables

    public static String getDescription()
    {
        return "Indicar propiedades de contenido";
    }
}
