/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gesti?n de procesos de negocio mediante el uso de 
* tecnolog?a sem?ntica, que permite el modelado, configuraci?n, ejecuci?n y monitoreo de los procesos de negocio
* de una organizaci?n, as? como el desarrollo de componentes y aplicaciones orientadas a la gesti?n de procesos.
* 
* Mediante el uso de tecnolog?a sem?ntica, SemanticWebBuilder Process puede generar contextos de informaci?n
* alrededor de alg?n tema de inter?s o bien integrar informaci?n y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la informaci?n se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creaci?n original del Fondo de 
* Informaci?n y Documentaci?n para la Industria INFOTEC.
* 
* INFOTEC pone a su disposici?n la herramienta SemanticWebBuilder Process a trav?s de su licenciamiento abierto 
* al p?blico (?open source?), en virtud del cual, usted podr? usarlo en las mismas condiciones con que INFOTEC 
* lo ha dise?ado y puesto a su disposici?n; aprender de ?l; distribuirlo a terceros; acceder a su c?digo fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los t?rminos y 
* condiciones de la LICENCIA ABIERTA AL P?BLICO que otorga INFOTEC para la utilizaci?n de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garant?a sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni impl?cita ni 
* expl?cita, siendo usted completamente responsable de la utilizaci?n que le d? y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposici?n la
* siguiente direcci?n electr?nica: 
*  http://www.semanticwebbuilder.org.mx
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
