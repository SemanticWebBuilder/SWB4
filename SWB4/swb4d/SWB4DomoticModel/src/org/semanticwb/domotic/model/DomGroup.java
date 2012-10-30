package org.semanticwb.domotic.model;

import java.util.Iterator;
import org.semanticwb.domotic.server.WebSocketServlet;


public class DomGroup extends org.semanticwb.domotic.model.base.DomGroupBase 
{
    private int oldStat=0;
    
    public DomGroup(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public void setStatus(int stat)
    {
        setStatus(stat, true);
    }
    
    public void setStatus(int stat, boolean notifyParents)
    {
        System.out.println("DomGroup:setStatus:"+stat+" "+notifyParents);
        
        Iterator<DomDevice> it=listDomDevices();
        while (it.hasNext())
        {
            DomDevice domDevice = it.next();
            domDevice.setStatus(stat,true,false);
        }
        
        Iterator<DomGroup> it2=listChildGroups();
        while (it2.hasNext())
        {
            DomGroup domGroup = it2.next();
            domGroup.setStatus(stat,false);
        }        
        
        sendWebSocketStatus(notifyParents);
        
    }
    
    public int getStatus()
    {
        int ret=0;
        Iterator<DomDevice> it=listDomDevices();
        while (it.hasNext())
        {
            DomDevice domDevice = it.next();
            if(domDevice.getStatus()>0)
            {
                ret=16;
                break;
            }
        }
        
        if(ret==0)
        {        
            Iterator<DomGroup> it2=listChildGroups();
            while (it2.hasNext())
            {
                DomGroup domGroup = it2.next();
                if(domGroup.getStatus()>0)
                {
                    ret=16;
                    break;
                }
            }        
        }
        
        if(oldStat!=ret)
        {
            Iterator<OnGroupChange> it3=listOnGroupChanges();
            while (it3.hasNext())
            {
                OnGroupChange onGroupChange = it3.next();
                onGroupChange.onEvent(""+ret);
            }            
        }
        
        oldStat=ret;
        return ret;
    }
    
    
    /**
     * Notifica a los grupos por websockets si notifyParents es true 
     * @param notifyParents 
     */
    public void sendWebSocketStatus(boolean notifyParents)
    {
        WebSocketServlet.broadcast(getShortURI()+" "+getStatus());
        if(notifyParents)
        {
            DomGroup domGroup=getParentGroup();
            if(domGroup!=null)domGroup.sendWebSocketStatus(notifyParents);
        }
    }    
    
}
