package org.semanticwb.process.model;

import com.hp.hpl.jena.util.iterator.Map1;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;


public class FlowNode extends org.semanticwb.process.model.base.FlowNodeBase 
{
    public FlowNode(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Crea una instancia del objeto de flujo
     * @param fobj
     * @param pinst
     * @return
     */
    public FlowNodeInstance createInstance(ContainerInstanceable pinst)
    {
        FlowNodeInstance inst=null;
        if(this instanceof SubProcess)
        {
            inst=pinst.getProcessSite().createSubProcessInstance();
        }else if(this instanceof CallProcess)
        {
            inst=pinst.getProcessSite().createSubProcessInstance();
        }else
        {
            inst=pinst.getProcessSite().createFlowNodeInstance();
        }
        inst.setFlowNodeType(this);
        inst.setStatus(Instance.STATUS_INIT);
        inst.setContainerInstance(pinst);
        
        //
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

    public void execute(FlowNodeInstance instance, User user)
    {
        //Implementar en subclases
    }

    public void close(FlowNodeInstance instance, User user)
    {
        //Implementar en subclases
    }

    /**
     * Continua el flujo al siguiente FlowNode
     * @param user
     */
    public void nextObject(FlowNodeInstance instance, User user)
    {
        //System.out.println("nextObject:"+getId()+" "+getFlowNodeType().getClass().getName()+" "+getFlowNodeType().getTitle());
        DefaultFlow def=null;
        boolean execute=false;
        Iterator<ConnectionObject> it=listOutputConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            if(!(connectionObject instanceof DefaultFlow))
            {
                if(connectionObject.evaluate(instance, user))
                {
                    connectionObject.execute(instance, user);
                    execute=true;
                }
            }else
            {
                def=(DefaultFlow)connectionObject;
            }
        }
        if(def!=null && !execute)def.execute(instance, user);
    }

    /**
     * Regresa proceso padre asociado
     * @return
     */
    public Process getProcess()
    {
        Process ret=null;
        Containerable cont=getContainer();
        if(cont!=null)
        {
            if(cont instanceof  Process)
            {
                ret = (Process) cont;
            }else
            {
                ret=((FlowNode)cont).getProcess();
            }
        }
        return ret;
    }


    /**
     * Regresa las ItemAware y la Classes relacionadas con el FlowNode,
     * en el caso de un SubProceso regresa las Globales del Sub Proceso
     * @return
     */
    public Map<ItemAware, SemanticClass> getRelatedItemAwareClasses()
    {
        System.out.println("getRelatedItemAwareClasses:"+this);
        HashMap<ItemAware,SemanticClass> map=new HashMap();
        if(this instanceof SubProcess)
        {
            Iterator<GraphicalElement> it=((SubProcess)this).listContaineds();
            while (it.hasNext())
            {
                //Igual al del Process
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
        }else
        {
            Iterator<ConnectionObject> it=listInputConnectionObjects();
            while (it.hasNext())
            {
                ConnectionObject connectionObject = it.next();
                GraphicalElement gele=connectionObject.getSource();
                if(gele instanceof ItemAware)
                {
                    ItemAware item=(ItemAware)gele;
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
     * Regresa las ItemAware y la Classes relacionadas con el FlowNode, asi como de sus contenedores,
     * en el caso de un SubProceso regresa las Globales del Sub Proceso y de sus contenedores
     * @return
     */
    public Map<ItemAware, SemanticClass> getHerarquicalRelatedItemAwareClasses()
    {
        System.out.println("getHerarquicalRelatedItemAwareClasses:"+this);

        Map<ItemAware,SemanticClass> map=getRelatedItemAwareClasses();
        map.putAll(getContainer().getHerarquicalRelatedItemAwareClasses());
        return map;
    }
}
