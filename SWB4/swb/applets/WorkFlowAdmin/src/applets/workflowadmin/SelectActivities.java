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
 * SelectActivities.java
 *
 * Created on 12 de octubre de 2004, 01:32 AM
 */

package applets.workflowadmin;

import java.awt.event.*;
import javax.swing.*;
/**
 * Clase utiler�a para seleciconar actividades dentro de la tabla de actividades.
 * @author Victor Lorenzana
 */
public class SelectActivities implements MouseListener
{
    JPopupMenu jPopupMenu1;
    
    SelectActivities(JPopupMenu jPopupMenu1)
    {
        this.jPopupMenu1=jPopupMenu1;
        
    }
    public void mouseReleased(MouseEvent e)
    {               
    }
    public void mousePressed(MouseEvent e)
    {
       if(e.getButton()==e.BUTTON3 && e.getClickCount()==1)
       {
           JMenuItem add=(JMenuItem)jPopupMenu1.getComponent(0);
           JMenuItem edit=(JMenuItem)jPopupMenu1.getComponent(1);
           JMenuItem delete=(JMenuItem)jPopupMenu1.getComponent(3);
           add.setEnabled(true);
           edit.setEnabled(true);
           delete.setEnabled(false);           
           
           if(e.getSource() instanceof JTable)
           {               
               JTable table=(JTable)e.getSource();
               if(table.getSelectedRows().length>0)
               {                    
                    delete.setEnabled(true);
                    add.setEnabled(false);                    
                    edit.setEnabled(true);
                    
                    jPopupMenu1.show(table, e.getX(),e.getY());
               }
               else
               {
                   add.setEnabled(true);                   
                   edit.setEnabled(false);
               }
           }
           else if(e.getSource() instanceof JScrollPane)
           { 
               
               add.setEnabled(true);               
               edit.setEnabled(false);
               JScrollPane table=(JScrollPane)e.getSource();
               jPopupMenu1.show(table, e.getX(),e.getY());
           }
       }
    }
    public void mouseExited(MouseEvent e)
    {

    }
    public void mouseEntered(MouseEvent e)
    {

    }
     public void mouseClicked(MouseEvent e)
    {

    }
}
