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
 * WBTreeModelListener.java
 *
 * Created on 5 de septiembre de 2002, 16:05
 */

package applets.generictree;

import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.*;

import applets.commons.*;

/**
 * Modo de datos de los elementos del �rbol.
 *
 * Data mode of the tree elements.
 *
 * @author  Administrador
 * @version 
 */
public class WBTreeModelListener implements TreeModelListener {

    JApplet tree;
    
    /** Creates new WBTreeModelListener */
    public WBTreeModelListener(JApplet tree) {
        this.tree=tree;
    }

    public void treeStructureChanged(javax.swing.event.TreeModelEvent treeModelEvent) {
        if(tree instanceof GenericTree)
            ((GenericTree)tree).treeChanged(treeModelEvent,"treeStructureChanged");
    }
    
    public void treeNodesChanged(javax.swing.event.TreeModelEvent treeModelEvent) {
        if(tree instanceof GenericTree)
            ((GenericTree)tree).treeChanged(treeModelEvent,"treeNodesChanged");
    }
    
    public void treeNodesRemoved(javax.swing.event.TreeModelEvent treeModelEvent) {
        if(tree instanceof GenericTree)
            ((GenericTree)tree).treeChanged(treeModelEvent,"treeNodesRemoved");
    }
    
    public void treeNodesInserted(javax.swing.event.TreeModelEvent treeModelEvent) {
        if(tree instanceof GenericTree)
            ((GenericTree)tree).treeChanged(treeModelEvent,"treeNodesInserted");
    }
    
}
