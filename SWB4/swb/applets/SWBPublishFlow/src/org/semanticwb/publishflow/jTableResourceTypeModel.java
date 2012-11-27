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
 * Clase que representa el modelo de datos, necesario para mostrarlo en una tabla
 * dentro de la edici�n de un flujo de publicaci�n.
 * @author Victor Lorenzana
 */
public class jTableResourceTypeModel extends AbstractTableModel implements Comparator{
     private String[] columnNames =new String[4];
     ArrayList resourcestypes=new ArrayList();
    /** Creates a new instance of jTableActivities */
     public jTableResourceTypeModel(Locale locale) {
         columnNames[0]=java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/jTableResourceTypeModel",locale).getString("Tipo_de_recurso");
         columnNames[1]=java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/jTableResourceTypeModel",locale).getString("mapa");
         columnNames[2]=java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/jTableResourceTypeModel",locale).getString("Descripcion");
         columnNames[3]=java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/jTableResourceTypeModel",locale).getString("Utilizar");
    }
     public Iterator iterator()
     {
         return resourcestypes.iterator();
     }
    public String getColumnName(int col) {
        return columnNames[col].toString();
    }
    public int getRowCount() 
    {
        return this.resourcestypes.size();
    }
    public void addResourceType(ResourceType rs)
    {
        resourcestypes.add(rs);
        Collections.sort(resourcestypes,this);
    }
    public int size()
    {
        return resourcestypes.size();
    }
    public ResourceType getResourceType(int index)
    {
        return (ResourceType)resourcestypes.toArray()[index];
    }
    public ResourceType getResourceType(String name)
    {
        Iterator it=resourcestypes.iterator();
        while(it.hasNext())
        {
            ResourceType rs=(ResourceType)it.next();
            if(rs.getName().equals(name))
            {
                return rs;
            }
        }
        return null;
        
    }

    public Class getColumnClass(int col)
    {      
        
        switch(col)
        {
            case 0:                
                return String.class;               
            case 1:
                return String.class;              
            case 2:
                return String.class;              
            case 3:
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
        ResourceType resourcestype=(ResourceType)resourcestypes.toArray()[row];
        switch(col)
        {
            case 0:                
                return resourcestype.getName();                
            case 1:
                return resourcestype.getTopicMapName();
            case 2:
                return resourcestype.getDescription();                
            case 3:
                return resourcestype.isSelected();
            default:
                return null;
        }        
    }
    public boolean isCellEditable(int row, int col)
    {
        if(col==3)
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
            ResourceType resourcestype=(ResourceType)resourcestypes.toArray()[row];
            switch(col)
            {
               case 3:
                    resourcestype.setSelected(value.booleanValue());
            } 
        }
    }

   public int compare(Object o1, Object o2) {
        if(o1 instanceof ResourceType && o2 instanceof ResourceType)
        {
            ResourceType r1=(ResourceType)o1;
            ResourceType r2=(ResourceType)o2;
            return r1.getName().compareTo(r2.getName());
        }
        return 0;
    }    
    
}
