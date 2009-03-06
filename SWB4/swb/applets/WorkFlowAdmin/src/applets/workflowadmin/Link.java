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
 * Link.java
 *
 * Created on 27 de octubre de 2004, 07:21 PM
 */

package applets.workflowadmin;
import java.util.*;
/**
 * Clase que representa una secuencia de una actividad a otra.
 * @author Victor Lorenzana
 */
public class Link {
    ArrayList users=new ArrayList();
    ArrayList roles=new ArrayList();
    Activity to,from;
    boolean sendEmail=true;
    String type;
    boolean autorized;
    boolean publish;
    /** Creates a new instance of Link */
    public Link(Activity to,Activity from,String type) {
        this.to=to;
        this.from=from;
        this.type=type;
    }
    public void setAuthorized(boolean autorized)
    {
        this.autorized=autorized;
    }
    public void setPublish(boolean publish)
    {
        this.publish=publish;
    }
    public boolean isAuthorized()
    {
       return autorized;
    }
    public boolean isPublish()
    {
       return publish;
    }
    public void  setType(String type)
    {
        this.type=type;
    }
    public String getType()
    {
        return type;
    }
    public Activity getActivityTo()
    {
        return to;
    }
    public Activity getActivityFrom()
    {
        return from;
    }
    public void setActivityTo(Activity activity)
    {
        this.to=activity;
    }
    public void setSendEmail()
    {
        this.sendEmail=sendEmail;
    }
    public boolean sendEmail()
    {
        return sendEmail;
    }
    public void addUser(User user)
    {
        users.add(user);
    }
    public void removeUser(User user)
    {
        users.remove(user);
    }
    public void addRole(Role role)
    {
        roles.add(role);
    }
    public void removeRole(Role role)
    {
        roles.remove(role);
    }
    public void clearUsers()
    {
        users.clear();
    }
    public void clearRoles()
    {
        roles.clear();
    }
    public ArrayList getUsers()
    {
        return users;
    }
    public ArrayList getRoles()
    {
        return roles;
    }
    
}
