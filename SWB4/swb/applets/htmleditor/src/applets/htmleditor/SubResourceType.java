/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * SubResourceType.java
 *
 * Created on 20 de enero de 2005, 06:30 PM
 */

package applets.htmleditor;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
/**
 *
 * @author  Victor Lorenzana
 */
public class SubResourceType extends DefaultMutableTreeNode implements SelectableNode{
    
    /** Creates a new instance of SubResourceType */
    JPanel panel=new JPanel();
    JLabel label=new JLabel();
    JRadioButton radio=new JRadioButton();
    String id,topicmap;
    public SubResourceType(String id,String topicmap,String name,ButtonGroup bg) {
        this.id=id;
        this.topicmap=topicmap;
       label.setText(name);
        label.setOpaque(false);
        radio.setOpaque(false);
        panel.setOpaque(false);
        this.label.setBackground(Color.WHITE);
        bg.add(radio);
        label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/f_general.gif")));
        panel.add(radio);
        panel.add(label);  
        this.radio.setBackground(Color.WHITE);
    }
    public String getID()
    {
        return id;
    }
    public String topicMapID()
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
