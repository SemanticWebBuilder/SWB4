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
 * CheckListener.java
 *
 * Created on 23 de noviembre de 2004, 12:33 PM
 */

package applets.filters;

/**
 *
 * @author  Victor Lorenzana
 */
import javax.swing.JTree;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase necesaria para escuchar cuando a un elemento de tipo checklist se le da
 * click dentro del �rbol de secciones.
 * @author Victor Lorenzana
 */
public class CheckListener extends MouseAdapter {
   public void mousePressed(MouseEvent e) {
      //get the tree
       if(e.getButton()==e.BUTTON1 && e.getClickCount()==1)
       {           
          JTree tree = (JTree)e.getSource ();    
          if(tree.getLastSelectedPathComponent() instanceof SelectableNode)
          {              
              SelectableNode node = 
                 (SelectableNode)tree.getLastSelectedPathComponent ();              
              
              if(node.isSelected() && node.isEditable())
              {
                  if(node.getCheckBox().isEnabled())
                  {
                     if (node.getChecked ())
                     {                    
                        node.setChecked (false);            
                     }
                     else
                     {         

                        node.setChecked (true);
                     } 
                  }
              }
              
              
          }
          tree.updateUI();
       }
   }
}


