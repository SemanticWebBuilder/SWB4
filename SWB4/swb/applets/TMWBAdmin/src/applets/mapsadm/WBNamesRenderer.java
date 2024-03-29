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
 * WBTreeRenderer.java
 *
 * Created on 13 de agosto de 2002, 16:34
 */

package applets.mapsadm;

import javax.swing.tree.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.util.*;

/**
 *
 * @author  Administrador
 * @version 
 */
public class WBNamesRenderer extends DefaultTreeCellRenderer {

    private Icon rootIcon=null;
    private Icon langIcon=null;
    private Hashtable idmIcons;
    
    /** Creates new WBTreeRenderer */
    public WBNamesRenderer() {
        idmIcons=new Hashtable();
    }

    public Component getTreeCellRendererComponent(JTree tree,Object value,boolean sel,boolean expanded,boolean leaf,int row,boolean hasFocus) 
    {
        DefaultMutableTreeNode node =(DefaultMutableTreeNode)value;
        
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        
        if(node.getLevel()==0)
        {
            setIcon(rootIcon);
            setToolTipText("Nombres de la Secci�n");
        }
        else if(node.getLevel()==2)
        {
            setIcon(langIcon);
            setToolTipText("Variante de Nombre");
        }
        else if (node.getUserObject() instanceof ScopedName) 
        {
            ScopedName sc=(ScopedName)node.getUserObject();
            Icon icon=null;
            //System.out.println("Scope:"+sc.getScope());
            if(sc.getScope()!=null)
            {
                icon=(Icon)idmIcons.get(sc.getScope());
                //System.out.println("Icon:"+icon);
                if(icon!=null)
                {
                    setIcon(icon);
                    //System.out.println("SetIcon...");
                }
                if(sc.getScope().equals("CNF_WBSortName"))
                    setToolTipText("Nombre de Ordenamiento");
                else
                    setToolTipText("Idioma:"+sc.getLanguage());
            }else
            {
                setToolTipText("Sin idioma asignado");
            }
        }else
        {
            setIcon(langIcon);
            setToolTipText("Sin idioma asignado");
        }
        return this;
    }
    
    /** Getter for property rootIcon.
     * @return Value of property rootIcon.
     */
    public javax.swing.Icon getRootIcon() {
        return rootIcon;
    }
    
    /** Setter for property rootIcon.
     * @param rootIcon New value of property rootIcon.
     */
    public void setRootIcon(javax.swing.Icon rootIcon) {
        this.rootIcon = rootIcon;
    }
    
    /** Getter for property idmIcons.
     * @return Value of property idmIcons.
     */
    public Hashtable getIdmIcons() {
        return idmIcons;
    }
    
    /** Setter for property idmIcons.
     * @param idmIcons New value of property idmIcons.
     */
    public void setIdmIcons(Hashtable idmIcons) {
        this.idmIcons = idmIcons;
    }
    
    /** Getter for property langIcon.
     * @return Value of property langIcon.
     */
    public javax.swing.Icon getLangIcon() {
        return langIcon;
    }
    
    /** Setter for property langIcon.
     * @param langIcon New value of property langIcon.
     */
    public void setLangIcon(javax.swing.Icon langIcon) {
        this.langIcon = langIcon;
    }
    
}
