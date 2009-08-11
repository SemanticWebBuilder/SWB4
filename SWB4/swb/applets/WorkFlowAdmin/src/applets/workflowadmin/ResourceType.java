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
 * ResourceType.java
 *
 * Created on 11 de octubre de 2004, 06:51 PM
 */

package applets.workflowadmin;
import java.util.*;

/**
 * Clase que representa un tipo de recurso, esta informaci�n est mostrada en una
 * tabla al editar un flujo de publicaci�n.
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
