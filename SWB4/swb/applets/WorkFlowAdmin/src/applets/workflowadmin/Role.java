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
 * Role.java
 *
 * Created on 11 de octubre de 2004, 06:49 PM
 */

package applets.workflowadmin;

/**
 * Clase que representa un role de INFOTEC WebBuilder 3.0, el conjunto de roles es
 * mostrado al editar una actividad.
 * @author Victor Lorenzana
 */
public class Role {
    
    String name;
    String id;
    String repository;
    boolean checked=false;
    /** Creates a new instance of Role */
    public Role(String id,String name,String repository) {
        this.name=name;
        this.id=id;
        this.repository=repository;
    }    
    public String getRepository()
    {
        return repository;
    }
    public String getID()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public void setChecked(boolean checked)
    {
        this.checked=checked;
    }
    public boolean isChecked()
    {
        return checked;
    }
    public boolean equals(Object obj)
    {
        if(obj instanceof Role)
        {
            Role role=(Role)obj;
            if(role.getID().equals(this.getID()))
            {
                return true;
            }
        }
        return false;
    }
    
}
