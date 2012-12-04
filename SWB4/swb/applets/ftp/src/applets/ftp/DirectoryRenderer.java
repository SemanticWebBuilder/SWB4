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
 * DirectoryRenderer.java
 *
 * Created on 11 de noviembre de 2004, 11:38 AM
 */

package applets.ftp;

import javax.swing.JTree;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.*;
/**
 * Clase que da la logica de presentación gráfica de un directorio en el applet.
 * @author Victor Lorenzana
 */
public class DirectoryRenderer extends JPanel implements TreeCellRenderer{
    
    /** Creates a new instance of DirectoryRenderer */
    JTable table;
    public DirectoryRenderer(JTable table) {
        this.table=table;
    }
    
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if(value instanceof Directory)
        {
            Directory dir=(Directory)value;
            dir.getComponent().setFont(tree.getFont());
            if(expanded)
            {
                dir.getComponent().setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/open.gif")));
            }
            else
            {
                dir.getComponent().setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/close.gif")));
            }                 
            if(selected)
            {                
                dir.getComponent().setBackground(table.getSelectionBackground());
                dir.getComponent().setForeground(table.getSelectionForeground());
                //dir.getComponent().setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/open.gif")));                
            }
            else
            {
                dir.getComponent().setBackground(tree.getBackground());
                dir.getComponent().setForeground(table.getForeground());
            }
            return  dir.getComponent();
        }
        return this;
    }
    
}
