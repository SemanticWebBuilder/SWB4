package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.semanticwb.model.SWBClass;
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
        //System.out.println("createInstance process:"+this);
        ProcessInstance inst=null;
        inst=this.getProcessSite().createProcessInstance();
        inst.setProcessType(this);
        inst.setStatus(Instance.STATUS_INIT);

        Map<ItemAware,SemanticClass> map=getRelatedItemAwareClasses();
        Iterator<Map.Entry<ItemAware,SemanticClass>> it=map.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<ItemAware, SemanticClass> entry = it.next();
            ItemAwareReference ref=ItemAwareReference.ClassMgr.createItemAwareReference(this.getProcessSite());
            ref.setItemAware(entry.getKey());

            SemanticModel model=inst.getSemanticObject().getModel();
            SemanticClass cls=entry.getValue();
            long id=model.getCounter(cls);
            SemanticObject ins=model.createSemanticObjectById(String.valueOf(id), cls);
            ref.setProcessObject((SWBClass)ins.createGenericInstance());

            inst.addItemAwareReference(ref);
            System.out.println("addItemAwareReference:"+ref);
        }

        return inst;
    }

    /**
     * Regresa las ItemAware y la Classes relacionadas con el proceso (ItemAware Globales)
     * @return
     */
    public Map<ItemAware, SemanticClass> getRelatedItemAwareClasses()
    {
        System.out.println("getRelatedItemAwareClasses:"+this);
        HashMap<ItemAware,SemanticClass> map=new HashMap();
        Iterator<GraphicalElement> it=listContaineds();
        while (it.hasNext())
        {
            GraphicalElement graphicalElement = it.next();
            if(graphicalElement instanceof ItemAware)
            {
                ItemAware item=(ItemAware)graphicalElement;

                if(!item.listInputConnectionObjects().hasNext() && !item.listOutputConnectionObjects().hasNext())
                {
                    SemanticClass cls=ItemAware.getSemanticClass(item);
                    if(cls!=null)
                    {
                        map.put(item, cls);
                    }
                }
            }
        }
        return map;
    }

    /**
     * Regresa las ItemAware y la Classes relacionadas con el proceso (ItemAware Globales)
     * @return
     */
    public Map<ItemAware, SemanticClass> getHerarquicalRelatedItemAwareClasses()
    {
        System.out.println("getHerarquicalRelatedItemAwareClasses:"+this);
        return getRelatedItemAwareClasses();
    }

}
