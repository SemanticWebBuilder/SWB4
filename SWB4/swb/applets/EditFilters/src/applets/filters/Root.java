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
 * Root.java
 *
 * Created on 23 de noviembre de 2004, 12:55 PM
 */

package applets.filters;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
/**
 * Clase que representa un nodo simple, normalmente mostrado como ra�z dentro del
 * �rbol de secciones.
 * @author Victor Lorenzana
 */
public class Root extends DefaultMutableTreeNode implements Node{
    
    /** Creates a new instance of Root */
    boolean selected=false;
    JLabel label=new JLabel();
    public Root(String text,ImageIcon img) {
        label.setOpaque(true);
        this.label.setBackground(Color.WHITE);
        label.setText(text);
        label.setIcon(img);
    }
    
    public java.awt.Component getComponent() {
        return label;
    }
    
    public javax.swing.JLabel getJLabel() {
        return label;
    }
    public JLabel getLabel()
    {
        return this.label;
    }
    public void setSelected(boolean selected) {
       /* if(selected)
        {
            this.label.setBackground(new Color(204,204,204));
        }
        else
        {
            this.label.setBackground(Color.WHITE);
        }*/
        this.selected=selected;
    }
    
}
