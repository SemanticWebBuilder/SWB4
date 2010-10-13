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
 * Role.java
 *
 * Created on 11 de octubre de 2004, 06:49 PM
 */

package applets.workflowadmin;

/**
 * Clase que representa un role de SemanticWebBuilder, el conjunto de roles es
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
