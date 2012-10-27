package org.semanticwb.domotic.model;

import java.util.Iterator;
import org.semanticwb.domotic.server.WebSocketServlet;


public class DomGroup extends org.semanticwb.domotic.model.base.DomGroupBase 
{
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
        Iterator<DomDevice> it=listDomDevices();
        while (it.hasNext())
        {
            DomDevice domDevice = it.next();
            if(domDevice.getStatus()>0)return 16;
        }
        
        Iterator<DomGroup> it2=listChildGroups();
        while (it2.hasNext())
        {
            DomGroup domGroup = it2.next();
            if(domGroup.getStatus()>0)return 16;
        }        
        return 0;
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
