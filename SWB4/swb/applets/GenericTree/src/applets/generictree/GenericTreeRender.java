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
 * GenericTreeRender.java
 *
 * Created on 24 de junio de 2004, 04:25 PM
 */

package applets.generictree;

import javax.swing.tree.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.util.*;

import applets.commons.*;
/**
 * Clase que imprime los elementos del árbol.
 *
 * Class that prints the tree elements.
 *
 * @author Javier Solis Gonzalez
 */
public class GenericTreeRender extends DefaultTreeCellRenderer
{
    private HashMap icons=new HashMap();
    
    /** Creates a new instance of GenericTreeRender */
    public GenericTreeRender()
    {
    }
    
    /** Getter for property icons.
     * @return Value of property icons.
     *
     */
    public HashMap getIcons()
    {
        return icons;
    }
    
    /** Setter for property icons.
     * @param icons New value of property icons.
     *
     */
    public void setIcons(HashMap icons)
    {
        this.icons = icons;
    }
    
/*    
    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawLine(0,0, 10,10);
    }
*/    
    public Component getTreeCellRendererComponent(JTree tree,Object value,boolean sel,boolean expanded,boolean leaf,int row,boolean hasFocus) 
    {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode dnode =(DefaultMutableTreeNode)value;
        if(dnode.getUserObject() instanceof WBTreeNode)
        {
            WBTreeNode node=(WBTreeNode)dnode.getUserObject();

            String icon=node.getAttribute("icon");
            if(icon!=null && icons.get(icon)!=null)
            {
                setIcon((Icon)icons.get(icon));
            }
            String alt=node.getAttribute("alt");
            if(alt!=null)
            {
                setToolTipText(alt);
            }else
            {
                setToolTipText("");
            }
        }else
        {
            setToolTipText("");
        }
        return this;
    }
    
    
}
