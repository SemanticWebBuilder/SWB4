package org.semanticwb.process;

import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;


public class Process extends org.semanticwb.process.base.ProcessBase 
{
    public Process(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public ProcessInstance createProcessInstance(ProcessSite site)
    {
        ProcessInstance pinst=site.createProcessInstance();
        pinst.setFlowObjectType(this);
        pinst.setStatus(Process.STATUS_INIT);

        Iterator<SemanticObject> it=listProcessClasses();
        while(it.hasNext())
        {
            SemanticObject sobj=it.next();
            SemanticModel model=site.getSemanticObject().getModel();
            SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(sobj.getURI());
            long id=model.getCounter(cls);
            SemanticObject ins=model.createSemanticObjectById(String.valueOf(id), cls);
            pinst.addProcessObject((ProcessObject)ins.createGenericInstance());
        }
        return pinst;
    }

    public boolean isValid()
    {
        return true;
    }

}
