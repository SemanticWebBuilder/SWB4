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
 * ActivityGModel.java
 *
 * Created on 9 de noviembre de 2004, 11:05 PM
 */

package applets.workflowadmin;
import javax.swing.table.*;
import java.util.*;
import javax.swing.*;
/**
 * Modelo que representa actividades, usado en la pesta�a Dise�o dentro de
 * formulario del flujo.
 * @author Victor Lorenzana
 */
public class ActivityGModel  extends AbstractTableModel{
    ArrayList activities=new ArrayList();
    private String[] columnNames =new String[5];
    Locale locale;
    /** Creates a new instance of ActivityGModel */
    public ActivityGModel(Locale locale) {
        this.locale=locale;
        columnNames[0]=java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityGModel",locale).getString("step");
        columnNames[1]=java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityGModel",locale).getString("activity");
        columnNames[2]=java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityGModel",locale).getString("description");
        columnNames[3]=java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityGModel",locale).getString("rols");
        columnNames[4]=java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityGModel",locale).getString("users");
    }
    public ActivityG getActivity(int index)
    {
        return (ActivityG)activities.get(index);
    }
    public int getColumnCount() {
        return columnNames.length;
    }
    public void addActivity(ActivityG activity)
    {
        this.activities.add(activity);
    }
     public String getColumnName(int col) {
        return columnNames[col].toString();
    }
    public void clear()
    {
        this.activities.clear();
    }
    public int getRowCount() {
        return activities.size();
    }
    public Object getValueAt(int row, int col)
    {
        ActivityG activity=(ActivityG)activities.toArray()[row];        
        switch(col)
        {
            case 0:                
                return activity.getStep();
            case 1:                
                return activity.getName();                
            case 2:
                return activity.getDescription();   
            case 3:
                String rols="";
                if(activity.getActivity()!=null && activity.getActivity().getRoleModel()!=null)
                {
                    int iRoles=activity.getActivity().getRoleModel().getRowCount();                    
                    for(int i=0;i<iRoles;i++)
                    {
                        Role role=activity.getActivity().getRoleModel().getRole(i);
                        rols+=role.getName()+" , ";
                    }
                }
                if(rols.endsWith(" , "))
                {
                    rols=rols.substring(0,rols.length()-3);
                }
                return rols;
            case 4:
                String users="";
                if(activity.getActivity()!=null && activity.getActivity().getUserModel()!=null)
                {
                    int iUsers=activity.getActivity().getUserModel().getRowCount();                                    
                    for(int i=0;i<iUsers;i++)
                    {
                        User user=activity.getActivity().getUserModel().getUser(i);                        
                        users+=user.getName()+" , ";                        
                    }
                }
                if(users.endsWith(" , "))
                {
                    users=users.substring(0,users.length()-3);
                }
                return users;
            default:
                return null;
        }        
    }
    public Iterator iterator()
    {
        return this.activities.iterator();
    }
    public boolean isCellEditable(int row, int col)
    {
        
        return false; 
    }    
    
}
