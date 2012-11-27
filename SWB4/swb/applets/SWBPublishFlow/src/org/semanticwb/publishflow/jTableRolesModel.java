/*
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
 */
package org.semanticwb.publishflow;
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
         columnNames[0]=java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/jTableRolesModel",locale).getString("Rol");
         columnNames[1]=java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/jTableRolesModel",locale).getString("Utilizar");
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
