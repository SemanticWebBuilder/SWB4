package org.semanticwb.process.model;


public class ProcessSite extends org.semanticwb.process.model.base.ProcessSiteBase 
{
    public ProcessSite(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public synchronized ProcessObserver getProcessObserver()
    {
        ProcessObserver obs=ProcessObserver.ClassMgr.getProcessObserver("Instance",this);
        if(obs==null)
        {
            obs=ProcessObserver.ClassMgr.createProcessObserver("Instance",this);
        }
        return obs;
    }
}
