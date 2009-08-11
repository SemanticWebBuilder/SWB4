/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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

    private final JLabel label = new JLabel("No se tienen propiedades para este tipo de contenido, puede continuar.");
    private final JPanel panel = new JPanel();
    public static final String CONTENT_PROPERTIES = "PROPERTIES";

    public ContentProperties()
    {
        initComponents();
        //panel.setBackground(new Color(255, 255, 255));
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);

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
        panelPropertyEditor1.removeProperties();
        this.remove(this.panelPropertyEditor1);
        this.remove(panel);
        this.add(panel);
        try
        {
            PropertyInfo[] props = OfficeApplication.getOfficeDocumentProxy().getContentProperties(repositoryName, type);
            if (props.length > 0)
            {
                this.remove(panel);
                this.add(this.panelPropertyEditor1);
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
            OfficeApplication.getOfficeDocumentProxy().validateContentValues(repositoryName, props, values, type);
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
