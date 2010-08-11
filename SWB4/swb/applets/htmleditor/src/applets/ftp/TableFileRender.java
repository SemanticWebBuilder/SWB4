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
 * TableFileRender.java
 *
 * Created on 15 de noviembre de 2004, 03:15 PM
 */

package applets.ftp;
import javax.swing.table.TableCellRenderer;
import javax.swing.*;
/**
 * Tabla que muestra graficamente archivos en el servidor en un formato de tabla,
 * con iconos.
 * @author Victor Lorenzana
 */
public class TableFileRender implements TableCellRenderer{
    
    /** Creates a new instance of TableFileRender */
    JLabel label=new JLabel();
    public TableFileRender() {
        label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/file.gif")));
    }
    
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        if(value instanceof JLabel)
        {
            
            JLabel flabel=(JLabel)value;
            flabel.setFont(table.getFont());
            if(isSelected)
            {
                flabel.setBackground(table.getSelectionBackground());
                flabel.setForeground(table.getSelectionForeground());
            }
            else
            {
                flabel.setBackground(table.getBackground());
                flabel.setForeground(table.getForeground());
            }
            return flabel;
        }
        return label;
    }
    
}
