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
 * ResourceType.java
 *
 * Created on 11 de octubre de 2004, 06:51 PM
 */

package applets.workflowadmin;
import java.util.*;

/**
 * Clase que representa un tipo de recurso, esta información est mostrada en una
 * tabla al editar un flujo de publicación.
 * @author Victor Lorenzana
 */
public class ResourceType {
    boolean selected;
    String name;
    String description;
    String id;
    String topicmap;
    String topicmapname;
    /** Creates a new instance of ResourceType */
    public ResourceType(String id,String name,String description,String topicmap,String topicmapname) {
        this.name=name;
        this.description=description;
        this.id=id;
        this.topicmap=topicmap;
        this.topicmapname=topicmapname;
    }
    public String getTopicMapName()
    {
        return topicmapname;
    }
    public String getTopicMap()
    {
        return topicmap;
    }
    public String getID()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    public String getDescription()
    {
        return this.description;
    }
    public Boolean isSelected()
    {
        return new Boolean(selected);
    }
    public void setSelected(boolean selected)
    {
        this.selected=selected;
    }
    public boolean equals(Object obj)
    {
        if(obj instanceof ResourceType)
        {
            ResourceType res=(ResourceType)obj;
            if(res.getID().equals(this.getID()) && res.getTopicMap().equals(this.getTopicMap()))
            {
                return true;
            }
        }
        return false;
    }
    
    
    
}
