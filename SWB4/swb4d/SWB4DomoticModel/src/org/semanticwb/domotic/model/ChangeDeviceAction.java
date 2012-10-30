package org.semanticwb.domotic.model;


public class ChangeDeviceAction extends org.semanticwb.domotic.model.base.ChangeDeviceActionBase 
{
    public ChangeDeviceAction(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doActionImp()
    {
        System.out.println("ChangeDeviceAction:doAction");        
        DomDevice dev=getChangeDevice();
        dev.setStatus(getChangeDeviceStat());
    }
    
    
}
