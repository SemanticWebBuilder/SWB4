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


    public static final String CONTENT_PROPERTIES = "PROPERTIES";

    public ContentProperties()
    {
        initComponents();

    }

    

    @Override
    protected void renderingPage()
    {
        Map map = this.getWizardDataMap();
        map.put(CONTENT_PROPERTIES, null);
        if (map != null && map.get(SelectCategory.REPOSITORY_ID) != null && map.get(TitleAndDescription.NODE_TYPE) != null)
        {
            String repositoryName = map.get(SelectCategory.REPOSITORY_ID).toString();
            String type = map.get(TitleAndDescription.NODE_TYPE).toString();
            loadProperties(repositoryName, type);
        }
        super.renderingPage();
    }

    public void loadProperties(String repositoryName, String type)
    {

        try
        {
            PropertyInfo[] props = OfficeApplication.getOfficeDocumentProxy().getContentProperties(repositoryName, type);
            if (props.length == 0)
            {
                this.remove(this.panelPropertyEditor1);
                JPanel panel = new JPanel();
                panel.setBackground(new Color(255, 255, 255));
                panel.setLayout(new BorderLayout());
                JLabel label = new JLabel("No se tienen propiedades para este tipo de contenido, puede continuar.");
                panel.add(label, BorderLayout.NORTH);
                this.add(panel);
            }
            else
            {
                HashMap<PropertyInfo, Object> properties = new HashMap<PropertyInfo, Object>();
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
                    properties.put(info, defaultValue);                    
                }
                this.panelPropertyEditor1.loadProperties(properties);
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
        Map<PropertyInfo, String> properties = this.panelPropertyEditor1.getProperties();

        for (PropertyInfo prop : properties.keySet())
        {
            String value = this.panelPropertyEditor1.getProperties().get(prop);
            if (value.isEmpty() && prop.isRequired)
            {
                JOptionPane.showMessageDialog(this, "¡Debe indicar " + prop + "!", getDescription(), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                panelPropertyEditor1.requestFocus();
                return WizardPanelNavResult.REMAIN_ON_PAGE;
            }
        }
        int iproperties = properties.keySet().size();
        PropertyInfo[] props = new PropertyInfo[iproperties];
        Object[] values = new Object[iproperties];
        int i = 0;
        for (PropertyInfo prop : properties.keySet())
        {
            Object value = properties.get(prop);
            props[i] = prop;
            values[i] = value;
            i++;
        }
        String repositoryName = map.get(SelectCategory.REPOSITORY_ID).toString();
        String type = map.get(TitleAndDescription.NODE_TYPE).toString();
        loadProperties(repositoryName, type);
        try
        {
            OfficeApplication.getOfficeDocumentProxy().validateContentValues(repositoryName, props, values,type);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), getDescription(), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            return WizardPanelNavResult.REMAIN_ON_PAGE;
        }
        map.put(CONTENT_PROPERTIES, properties);
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

        panelPropertyEditor1 = new org.semanticwb.openoffice.components.PanelPropertyEditor();

        setLayout(new java.awt.BorderLayout());
        add(panelPropertyEditor1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.semanticwb.openoffice.components.PanelPropertyEditor panelPropertyEditor1;
    // End of variables declaration//GEN-END:variables

    public static String getDescription()
    {
        return "Propiedades de contenido";
    }
}
