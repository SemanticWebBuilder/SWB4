package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;


public class Process extends org.semanticwb.process.model.base.ProcessBase 
{
    public Process(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Crea una instancia del Proceso
     * @param fobj
     * @param pinst
     * @return
     */
    public ProcessInstance createInstance()
    {
        System.out.println("createInstance process:"+this);
        ProcessInstance inst=null;
        inst=this.getProcessSite().createProcessInstance();
        inst.setProcessType(this);
        inst.setStatus(Instance.STATUS_INIT);

        Iterator<SemanticObject> it=listProcessClasses();
        while(it.hasNext())
        {
            SemanticObject sobj=it.next();
            SemanticModel model=inst.getSemanticObject().getModel();
            SemanticClass cls=sobj.transformToSemanticClass();
            long id=model.getCounter(cls);
            SemanticObject ins=model.createSemanticObjectById(String.valueOf(id), cls);
            inst.addProcessObject((ProcessObject)ins.createGenericInstance());
        }
        return inst;
    }

}
