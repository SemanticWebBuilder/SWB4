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
 * jTableRolesModel.java
 *
 * Created on 12 de octubre de 2004, 04:13 PM
 */

package applets.workflowadmin;
import javax.swing.table.*;
import java.util.*;
/**
 * Clase que representa el modelo de datos para roles que se muestran al editar un
 * actividad a traves de una tabla.
 * @author Victor Lorenzana
 */
public class jTableRolesModel extends AbstractTableModel{
    
    private String[] columnNames =new String[2];
     Vector roles=new Vector();
    /** Creates a new instance of jTableActivities */
     public jTableRolesModel(Locale locale)
     {
         columnNames[0]=java.util.ResourceBundle.getBundle("applets/workflowadmin/jTableRolesModel",locale).getString("Rol");
         columnNames[1]=java.util.ResourceBundle.getBundle("applets/workflowadmin/jTableRolesModel",locale).getString("Utilizar");
    }
    public void addRole(Role role)
    {
        this.roles.add(role);
    }
    public void clear()
    {
        this.roles.clear();
    }
    public String getColumnName(int col) {
        return columnNames[col].toString();
    }
    public int getRowCount() 
    {
        return this.roles.size();
    }
    public int size()
    {
        return roles.size();
    }
    public Role getRole(int index)
    {
        return (Role)roles.toArray()[index];
    }
    public Class getColumnClass(int col)
    {      
        
        switch(col)
        {
            case 0:                
                return String.class;               
            case 1:
                return Boolean.class;            
            default:
                return null;
        }   
    }

    public int getColumnCount()
    {
        return columnNames.length; 
    }
    public Object getValueAt(int row, int col)
    {
        Role role=(Role)roles.toArray()[row];
        switch(col)
        {
            case 0:                
                return role.getName();                
            case 1:
                return new Boolean(role.isChecked());
            default:
                return null;
        }        
    }
    public boolean isCellEditable(int row, int col)
    {
        if(col==1)
        {
            return true; 
        }
        else
        {
            return false; 
        }
        
    }
    public void setValueAt(Object ovalue, int row, int col) {
        if(ovalue instanceof Boolean)
        {
            Boolean value=(Boolean)ovalue;
            Role role=(Role)roles.toArray()[row];
            switch(col)
            {
               case 1:
                    role.setChecked(value.booleanValue());
            } 
        }
    }
    
}
