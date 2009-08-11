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
 * WBPriorityComaprator.java
 *
 * Created on 19 de julio de 2002, 12:39
 */

package org.semanticwb.portal.util;

import java.util.*;
import org.semanticwb.portal.api.SWBResource;

/** objeto: compara la prioridad de dos recirsos
 * @author Javier Solis Gonzalez
 * @version 1.0
 */
public class SWBPriorityComparator implements Comparator
{

    boolean content=false;
    
    /** Creates new WBPriorityComaprator */
    public SWBPriorityComparator()
    {
    }
    
    /** Creates new WBPriorityComaprator */
    public SWBPriorityComparator(boolean content)
    {
        this.content=content;
    }

    /**
     * @param obj
     * @param obj1
     * @return  */
    public int compare(java.lang.Object obj, java.lang.Object obj1)
    {
        int x;
        int y;
        
        if(content)
        {
            y = ((SWBResource) obj).getResourceBase().getIndex();
            x = ((SWBResource) obj1).getResourceBase().getIndex();
        }else
        {
            x = ((SWBResource) obj).getResourceBase().getRandPriority();
            y = ((SWBResource) obj1).getResourceBase().getRandPriority();
        }
        
        //if(x==y)return 0;
        if (x > y)
            return -1;
        else
            return 1;
    }

}
