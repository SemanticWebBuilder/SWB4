package org.semanticwb.domotic.model;

import java.util.Iterator;


public class DomGroup extends org.semanticwb.domotic.model.base.DomGroupBase 
{
    public DomGroup(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public void setStatus(int stat)
    {
        Iterator<DomDevice> it=listDomDevices();
        while (it.hasNext())
        {
            DomDevice domDevice = it.next();
            domDevice.setStatus(stat);
        }
        
        Iterator<DomGroup> it2=listChildGroups();
        while (it2.hasNext())
        {
            DomGroup domGroup = it2.next();
            domGroup.setStatus(stat);
        }
    }
}
