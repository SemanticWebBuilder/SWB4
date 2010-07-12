package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;


public class SubProcess extends org.semanticwb.process.model.base.SubProcessBase 
{
    public SubProcess(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Crea una instancia del objeto de flujo
     * @param fobj
     * @param pinst
     * @return
     */
    @Override
    public FlowNodeInstance createInstance(ContainerInstanceable pinst)
    {
        System.out.println("createInstance subprocess:"+this);
        SubProcessInstance inst=(SubProcessInstance)super.createInstance(pinst);
        Iterator<SemanticObject> it=listProcessClasses();
        while(it.hasNext())
        {
            SemanticObject sobj=it.next();
            SemanticModel model=pinst.getSemanticObject().getModel();
            SemanticClass cls=sobj.transformToSemanticClass();
            long id=model.getCounter(cls);
            SemanticObject ins=model.createSemanticObjectById(String.valueOf(id), cls);
            //System.out.println(sobj+" "+model+" "+cls+" "+id+" "+ins);
            inst.addProcessObject((ProcessObject)ins.createGenericInstance());

//            Iterator it2=inst.listProcessObjects();
//            while (it2.hasNext())
//            {
//                Object object = it2.next();
//                System.out.println("obj:"+object);
//            }
        }
        return inst;
    }

}
