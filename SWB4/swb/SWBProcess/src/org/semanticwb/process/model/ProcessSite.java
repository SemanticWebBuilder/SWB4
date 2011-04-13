package org.semanticwb.process.model;


public class ProcessSite extends org.semanticwb.process.model.base.ProcessSiteBase 
{
    public ProcessSite(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
        //Initialize ProcessObserver
        ProcessObserver po=getProcessObserver();
    }

    public synchronized ProcessObserver getProcessObserver()
    {
        //TODO Remove cuando se elimine la instancia del objeto
        ProcessObserver obs=ProcessObserver.ClassMgr.getProcessObserver("Instance",this);
        if(obs==null)
        {
            obs=ProcessObserver.ClassMgr.createProcessObserver("Instance",this);
        }
        return obs;
    }

    @Override
    public ProcessDataInstanceModel getProcessDataInstanceModel()
    {
        ProcessDataInstanceModel ret=super.getProcessDataInstanceModel();

        if(ret==null)
        {
            synchronized(this)
            {
                if(super.getProcessDataInstanceModel()==null)
                {
                    ret=ProcessDataInstanceModel.ClassMgr.createProcessDataInstanceModel(getId() + "_pdim", "http://pdim." + getId() + ".swb#");
                    setProcessDataInstanceModel(ret);
                    addSubModel(ret);
                }
            }
        }   

        return ret;
    }


}
