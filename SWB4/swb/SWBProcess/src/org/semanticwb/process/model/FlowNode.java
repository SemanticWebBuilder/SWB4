package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


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
        
        Iterator<ItemAware> it=listRelatedItemAware().iterator();
        while (it.hasNext())
        {
            ItemAware item = it.next();

            if(!item.listInputConnectionObjects().hasNext())
            {
                SemanticClass scls=item.getItemSemanticClass();
                SemanticProperty sprop=null;
                SWBModel model=this.getProcessSite();
                if(item instanceof Collectionable)
                {
                    model=this.getProcessSite().getProcessDataInstanceModel();
                }

                if(scls!=null)
                {
                    ItemAwareReference ref=ItemAwareReference.ClassMgr.createItemAwareReference(this.getProcessSite());
                    ref.setItemAware(item);

                    long id=model.getSemanticModel().getCounter(scls);
                    SemanticObject ins=model.getSemanticModel().createSemanticObjectById(String.valueOf(id), scls);
                    ref.setProcessObject((SWBClass)ins.createGenericInstance());
                    inst.addItemAwareReference(ref);
                    //System.out.println("addItemAwareReference:"+ref);
                }
            }else //Reutiliza los data objects
            {
                //No se hace nada ya que los datos ya se agregaron en la salida del ItemAware
            }

            //TODO: Si es una propiedad
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
     * Regresa las ItemAware relacionadas con el FlowNode,
     * en el caso de un SubProceso regresa las Globales del Sub Proceso
     * @return
     */
    public List<ItemAware> listRelatedItemAware()
    {
        //System.out.println("listRelatedItemAwareClasses:"+this);
        ArrayList<ItemAware> arr=new ArrayList();
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
                        arr.add(item);
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
                    arr.add(item);
                }
            }
        }
        return arr;
    }


    /**
     * Regresa las ItemAware relacionadas con el FlowNode, asi como de sus contenedores,
     * en el caso de un SubProceso regresa las Globales del Sub Proceso y de sus contenedores
     * @return
     */
    public List<ItemAware> listHerarquicalRelatedItemAware()
    {
        //System.out.println("getHerarquicalRelatedItemAwareClasses:"+this);
        ArrayList<ItemAware> arr=new ArrayList(listRelatedItemAware());
        arr.addAll(getContainer().listHerarquicalRelatedItemAware());
        return arr;
    }
}
