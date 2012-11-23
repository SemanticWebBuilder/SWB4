/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gestión de procesos de negocio mediante el uso de 
* tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
* de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.
* 
* Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
* alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
* Información y Documentación para la Industria INFOTEC.
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
* al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
* lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
* condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
* explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
* siguiente dirección electrónica: 
*  http://www.semanticwebbuilder.org.mx
**/




/*
 * WBTreeCellEditor.java
 *
 * Created on 26 de agosto de 2002, 17:12
 */

package applets.mapsadm;

import javax.swing.tree.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 *
 * @author  Administrador
 * @version 
 */
public class WBTreeCellEditor extends DefaultTreeCellEditor
{
    private JApplet parent=null;
    private JTree tree;
    private ScopedName scope=null;
    
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
        //System.out.println("CellEditor stopCellEditing");
        return super.stopCellEditing();
    }
   
    public void prepareForEditing()
    {
        DefaultMutableTreeNode selected=(DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
        Object obj=selected.getUserObject();
        if(obj instanceof ScopedName)
        {
            scope=(ScopedName)obj;
        }
        else scope=null;
        //System.out.println("CellEditor prepareForEditing:"+selected);
        //System.out.println("name:"+scope.getName());
        //System.out.println("languege:"+scope.getLanguage());
        //System.out.println("scope:"+scope.getScope());
        super.prepareForEditing();
    }
    
    public void actionPerformed(ActionEvent e)
    {
        //System.out.println("CellEditor actionPerformed:"+e);
        super.actionPerformed(e);
    }

    /** Getter for property scope.
     * @return Value of property scope.
     */
    public ScopedName getScope() {
        return scope;
    }
   
}
