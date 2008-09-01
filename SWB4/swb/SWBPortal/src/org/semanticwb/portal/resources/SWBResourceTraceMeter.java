
/*
 * WBResourceTraceMeter.java
 *
 * Created on 3 de septiembre de 2007, 10:51 PM
 *
 */

package org.semanticwb.portal.resources;

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
