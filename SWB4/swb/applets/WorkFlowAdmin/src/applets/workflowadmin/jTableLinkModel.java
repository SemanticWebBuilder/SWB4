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
 * jTableLinkModel.java
 *
 * Created on 8 de noviembre de 2004, 09:52 PM
 */

package applets.workflowadmin;
import javax.swing.table.*;
import java.util.*;
import javax.swing.*;
/**
 * Clase que representa un modelo de datos para Links, que se san en las tablas de
 * secuencia de una actividad.
 * @author Victor Lorenzana
 */
public class jTableLinkModel extends AbstractTableModel
{
    private String[] columnNames =new String[2];
    /** Creates a new instance of jTableLinkModel */
    HashSet links=new HashSet();
    JTable table;
    /** Creates a new instance of jTableActivities */
     public jTableLinkModel(Locale locale) {         
         columnNames[0]=java.util.ResourceBundle.getBundle("applets/workflowadmin/jTableActivitiesModel",locale).getString("Actividad");
         columnNames[1]=java.util.ResourceBundle.getBundle("applets/workflowadmin/jTableActivitiesModel",locale).getString("Descripción");
         
    }
     public HashSet getLinks()
     {
        return links;
     }
     public jTableLinkModel(JTable table) {         
         this.table=table;
         
    }
     public String getColumnName(int col) {
        return columnNames[col].toString();
    }
    public int getRowCount() 
    {
        return links.size();
    }
    public int getColumnCount()
    {
        return columnNames.length; 
    }
    
    public Object getValueAt(int row, int col)
    {
        Link link=(Link)links.toArray()[row];
        switch(col)
        {
            case 0:                
                return link.getActivityTo().getName();                
            case 1:
                return link.getActivityTo().getDescription();                
            default:
                return null;
        }        
    }
    public boolean isCellEditable(int row, int col)
    {
        return false; 
    }
    public void setValueAt(Object value, int row, int col) {
        
    }
    public Link getLink(int index)
    {
        return (Link)this.links.toArray()[index];
    }
    public void addLink(Link link)
    {
        links.add(link);                
        
        if(table!=null)
            table.updateUI();        
    }
    public int size()
    {
        return this.links.size();
    }
    public boolean contains(Link link)
    {
        return this.links.contains(link);
    }
    public void clear()
    {
        this.links.clear();
    }
    public void setTable(JTable table)
    {
        this.table=table;
    }     
    public void removeLink(Link link)
    {
        this.links.remove(link);
        if(table!=null)
            table.updateUI();
        
    }
    
}
