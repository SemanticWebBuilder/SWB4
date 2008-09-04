
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
            x = ((SWBResource) obj).getResourceBase().getPriority();
            y = ((SWBResource) obj1).getResourceBase().getPriority();
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
