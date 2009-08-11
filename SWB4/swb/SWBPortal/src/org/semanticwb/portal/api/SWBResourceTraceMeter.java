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
 * WBResourceTraceMeter.java
 *
 * Created on 3 de septiembre de 2007, 10:51 PM
 *
 */

package org.semanticwb.portal.api;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBResourceTraceMeter
{
    private String id;
    private String typeMap;
    private String name;
    
    private long minTime;
    private long maxTime;
    private long avgTime;
    private long count=0;
    
    /** Creates a new instance of WBResourceTraceMeter */
    public SWBResourceTraceMeter()
    {
    }
    
    public void addTime(long time)
    {
        long aux=count;
        if(aux>500)aux=500;
        
        if(aux==0)
        {
            minTime=time;
            maxTime=time;
            avgTime=time;
        }else
        {
            if(time<minTime)minTime=time;
            if(time>maxTime)maxTime=time;
            avgTime=(avgTime*aux+time)/(aux+1);
        }
        count++;
    }

    public long getMinTime()
    {
        return minTime;
    }

    public long getMaxTime()
    {
        return maxTime;
    }

    public long getAvgTime()
    {
        return avgTime;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTypeMap()
    {
        return typeMap;
    }

    public void setTypeMap(String typeMap)
    {
        this.typeMap = typeMap;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getCount()
    {
        return count;
    }
}
