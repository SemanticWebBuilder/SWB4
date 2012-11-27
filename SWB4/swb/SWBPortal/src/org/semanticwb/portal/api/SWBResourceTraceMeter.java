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
package org.semanticwb.portal.api;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBResourceTraceMeter.
 * 
 * @author Javier Solis Gonzalez
 */
public class SWBResourceTraceMeter
{
    
    /** The id. */
    private String id;
    
    /** The type map. */
    private String typeMap;
    
    /** The name. */
    private String name;
    
    /** The min time. */
    private long minTime;
    
    /** The max time. */
    private long maxTime;
    
    /** The avg time. */
    private long avgTime;
    
    /** The count. */
    private long count=0;
    
    /**
     * Creates a new instance of WBResourceTraceMeter.
     */
    public SWBResourceTraceMeter()
    {
    }
    
    /**
     * Adds the time.
     * 
     * @param time the time
     */
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

    /**
     * Gets the min time.
     * 
     * @return the min time
     */
    public long getMinTime()
    {
        return minTime;
    }

    /**
     * Gets the max time.
     * 
     * @return the max time
     */
    public long getMaxTime()
    {
        return maxTime;
    }

    /**
     * Gets the avg time.
     * 
     * @return the avg time
     */
    public long getAvgTime()
    {
        return avgTime;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id the new id
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Gets the type map.
     * 
     * @return the type map
     */
    public String getTypeMap()
    {
        return typeMap;
    }

    /**
     * Sets the type map.
     * 
     * @param typeMap the new type map
     */
    public void setTypeMap(String typeMap)
    {
        this.typeMap = typeMap;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name the new name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the count.
     * 
     * @return the count
     */
    public long getCount()
    {
        return count;
    }
}
