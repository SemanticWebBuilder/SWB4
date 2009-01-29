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
 * WBTreeCellEditor.java
 *
 * Created on 26 de agosto de 2002, 17:12
 */

package applets.generictree;

import javax.swing.tree.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

import applets.commons.*;

/**
 * Permite la edición de los elementos del árbol.
 *
 * It allows the edition of the tree elements.
 *
 * @author  Administrador
 * @version 
 */
public class WBTreeCellEditor extends DefaultTreeCellEditor
{
    private JApplet parent=null;
    private JTree tree;
    private WBTreeNode node=null;
    
    public WBTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer, TreeCellEditor editor, JApplet parent) 
    {
        this(tree, renderer, editor);
        this.parent=parent;
        this.tree=tree;
    }
    
    public WBTreeCellEditor(final JTree tree, final DefaultTreeCellRenderer renderer, TreeCellEditor editor) 
    {
        super(tree,renderer,editor);
    }
    
    public boolean stopCellEditing()
    {
        System.out.println("CellEditor stopCellEditing");
        return super.stopCellEditing();
    }
   
    public void prepareForEditing()
    {
        DefaultMutableTreeNode selected=(DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
        Object obj=selected.getUserObject();
        
        if(obj instanceof WBTreeNode)
        {
            node=(WBTreeNode)obj;
        }
        else node=null;
        
        System.out.println("CellEditor prepareForEditing:"+node);
        //System.out.println("name:"+scope.getName());
        //System.out.println("languege:"+scope.getLanguage());
        //System.out.println("scope:"+scope.getScope());
        super.prepareForEditing();
    }
    
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("CellEditor actionPerformed:"+e);
        super.actionPerformed(e);
    }

    /** Getter for property scope.
     * @return Value of property scope.
     */
    public WBTreeNode getNode() {
        return node;
    }
   
}
