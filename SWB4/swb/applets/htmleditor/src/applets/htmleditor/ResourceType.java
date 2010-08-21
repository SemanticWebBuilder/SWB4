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
 * ResourceType.java
 *
 * Created on 20 de enero de 2005, 05:38 PM
 */

package applets.htmleditor;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
/**
 *
 * @author  Victor Lorenzana
 */

public class ResourceType extends DefaultMutableTreeNode implements SelectableNode{
    
    /** Creates a new instance of ResourceType */
    JPanel panel=new JPanel();
    JLabel label=new JLabel();
    JRadioButton radio=new JRadioButton();
    String id,topicmap;
    
    public ResourceType(String id,String topicmap,String name,ButtonGroup bg) {
        this.id=id;
        this.topicmap=topicmap;
        label.setText(name);
        radio.setText("");
        label.setOpaque(false);
        radio.setOpaque(false);
        panel.setOpaque(false);
        this.label.setBackground(Color.WHITE);
        javax.swing.ImageIcon i=new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/f_resourcetype.gif"));                
        label.setIcon(i);
        panel.setMinimumSize(new Dimension(100, i.getIconHeight()));
        panel.add(radio);
        panel.add(label);  
        this.radio.setBackground(Color.WHITE);
        bg.add(radio);        
    }    
    public String getID()
    {
        return id;
    }
    public String getTopicMapID()
    {
        return topicmap;
    }
    public String getName()
    {
        return label.getText();
    }
    public Component getComponent()
    {
        return panel;
    }    
    public void setSelected(boolean selected)
    {
        radio.setSelected(selected);
    }
    public boolean isSelected()
    {
        return radio.isSelected();
    }
    
    public JRadioButton getRadioButton() {
        return radio;
    }    
     
    
}
