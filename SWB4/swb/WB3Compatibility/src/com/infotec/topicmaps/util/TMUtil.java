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
 * TMUtil.java
 *
 * Created on 26 de agosto de 2005, 04:39 PM
 */

package com.infotec.topicmaps.util;

import com.infotec.topicmaps.Occurrence;
import com.infotec.topicmaps.Topic;
import com.infotec.topicmaps.TopicMap;
import com.infotec.topicmaps.bean.TopicMgr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class TMUtil
{
    
    /** Creates a new instance of TMUtil */
    public TMUtil()
    {
    }
    
    /*
     * Regresa HashMap con la informacion almacenada en el resourceData de todas las
     * ocurrencias del topicmap.
     *  ket del hashmap = resourceData
     *  value del hashmap Objeto Topic
     *
     */
    public static Map getOccurrenceDataOfType(String topicmapid, String type)
    {
        HashMap map=new HashMap();
        TopicMap tm=TopicMgr.getInstance().getTopicMap(topicmapid);
        Iterator it=new ArrayList(tm.getTopics().values()).iterator();
        while(it.hasNext())
        {
            Topic tp=(Topic)it.next();
            Iterator occs=tp.getOccurrencesOfType(type);
            while(occs.hasNext())
            {
                Occurrence occ=(Occurrence)occs.next();
                if(!occ.isResourceRef())
                {
                    map.put(occ.getResourceData(), tp);
                }
            }
        }
        return map;
    }
    
}
