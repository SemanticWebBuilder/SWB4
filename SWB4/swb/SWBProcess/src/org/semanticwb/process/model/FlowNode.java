/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.process.model;

import bsh.Interpreter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class FlowNode extends org.semanticwb.process.model.base.FlowNodeBase 
{    
    private static Logger log=SWBUtils.getLogger(SWBPClassMgr.class);
    
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

        //Crea las instancias de los item aware de entrada  que a su vez no tengan entradas
        Iterator<ItemAware> it=listRelatedItemAware().iterator();
        while (it.hasNext())
        {
            ItemAware item = it.next();

            if(!item.listInputConnectionObjects().hasNext())
            {
                SemanticClass scls=item.getItemSemanticClass();
                SemanticProperty sprop=null;
                SWBModel model=this.getProcessSite();
                String id=null;
                String code=null;
                if(item instanceof Collectionable)
                {
                    model=this.getProcessSite().getProcessDataInstanceModel();
                }else
                {
                    id=((DataStore)item).getDataObjectId();
                    if(id!=null && id.length()==0)id=null;
                    code=((DataStore)item).getInitializationCode();
                    if(code!=null && code.length()==0)code=null;
                }

                if(scls!=null)
                {
                    SemanticObject ins=null;
                    if(code!=null)
                    {
                        Object ret=null;
                        try
                        {
                            //long ini=System.currentTimeMillis();
                            Interpreter i = SWBPClassMgr.getInterpreter();
                            ret=i.eval(code);
                            //System.out.println("ret:"+ret);
                            //System.out.println("time:"+ (System.currentTimeMillis()-ini ));
                        }catch(Exception e)
                        {
                            log.error(e);
                        }
                        //String action=source.getAction();
                        if(ret!=null && ret instanceof SemanticObject && ((SemanticObject)ret).instanceOf(scls))
                        {
                            ins=((SemanticObject)ret);
                        }
                        
                        if(ret!=null && ret instanceof SWBClass && ((SWBClass)ret).getSemanticObject().instanceOf(scls))
                        {
                            ins=((SWBClass)ret).getSemanticObject();
                        }
                    }
                    if(ins==null)
                    {
                        if(id==null)
                        {
                            id=String.valueOf(model.getSemanticModel().getCounter(scls));
                        }else
                        {
                            ins=SemanticObject.createSemanticObject(model.getSemanticModel().getObjectUri(id, scls));
                        }
                        if(ins==null)
                        {
                            Iterator auxit=ItemAwareMapping.ClassMgr.listItemAwareMappingByLocalItemAware(item, item.getProcessSite());
                            if(!auxit.hasNext())
                            {
                                ins=model.getSemanticModel().createSemanticObjectById(id, scls);
                            }
                        }
                    }
                    
                    ItemAwareReference ref=ItemAwareReference.ClassMgr.createItemAwareReference(this.getProcessSite());
                    ref.setItemAware(item);
                    if(ins!=null)
                    {
                        ref.setProcessObject((SWBClass)ins.createGenericInstance());
                    }
                    inst.addItemAwareReference(ref);
                    //System.out.println("addItemAwareReference:"+ref);
                }
            }else //Reutiliza los data objects
            {
                //No se hace nada ya que los datos ya se agregaron en la salida del ItemAware
            }
        }

        //crear instancias de itemaware temporales para los itemaware de salida que no tengan referencias herarquicas
        Iterator<ConnectionObject> it2=listOutputConnectionObjects();
        while (it2.hasNext())
        {
            ConnectionObject connectionObject = it2.next();
            GraphicalElement gele=connectionObject.getTarget();
            if(gele instanceof ItemAware)
            {
                ItemAware item=(ItemAware)gele;
                SemanticClass scls=item.getItemSemanticClass();
                if(scls!=null)
                {
                    boolean hasItemAwarereference=false;
                    //verifica si existe alguna referencia al itemAware
                    Iterator<ItemAware> it3=listHerarquicalRelatedItemAware().iterator();
                    while (it3.hasNext())
                    {
                        ItemAware itemAware = it3.next();
                        if(item.getName().equals(itemAware.getName()) && item.getItemSemanticClass().equals(itemAware.getItemSemanticClass()))
                        {
                            hasItemAwarereference=true;
                        }
                    }
                    //si no existe crea una referencia temporal al itemAware para su uso en la tarea
                    if(!hasItemAwarereference)
                    {
                        ItemAwareReference ref=ItemAwareReference.ClassMgr.createItemAwareReference(this.getProcessSite());
                        ref.setItemAware(item);
                        ref.setItemAwareTemporal(true);
                        SWBModel model=this.getProcessSite().getProcessDataInstanceModel();
                        String id=id=String.valueOf(model.getSemanticModel().getCounter(scls));
                        SemanticObject ins=model.getSemanticModel().createSemanticObjectById(id, scls);
                        ref.setProcessObject((SWBClass)ins.createGenericInstance());
                        inst.addItemAwareReference(ref);
                    }
                }
            }
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
     * en el caso de un SubProceso regresa tambien los itemAware globales contenidos dentro del Sub Proceso
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
        }

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

    /**
     * Regresa las ItemAware relacionadas con el FlowNode, asi como de sus contenedores,
     * en el caso de un SubProceso regresa las Globales del Sub Proceso y de sus contenedores,
     * ademas regresa los ItemAware de salida que no tengan referencias de entrada del FlowNodo actual
     * @return
     */
    public List<ItemAware> listHerarquicalRelatedItemAwarePlusNullOutputs()
    {
        //System.out.println("getHerarquicalRelatedItemAwareClasses:"+this);
        ArrayList<ItemAware> arr=new ArrayList(listRelatedItemAware());
        arr.addAll(getContainer().listHerarquicalRelatedItemAware());

        //crear instancias de itemaware temporales para los itemaware de salida que no tengan referencias herarquicas
        Iterator<ConnectionObject> it2=listOutputConnectionObjects();
        while (it2.hasNext())
        {
            ConnectionObject connectionObject = it2.next();
            GraphicalElement gele=connectionObject.getTarget();
            if(gele instanceof ItemAware)
            {
                ItemAware item=(ItemAware)gele;
                SemanticClass scls=item.getItemSemanticClass();
                if(scls!=null)
                {
                    boolean hasItemAwarereference=false;
                    //verifica si existe alguna referencia al itemAware
                    Iterator<ItemAware> it3=SWBUtils.Collections.copyIterator(arr.iterator()).iterator();
                    while (it3.hasNext())
                    {
                        ItemAware item2 = it3.next();
                        if(item.getName().equals(item2.getName()) && item.getItemSemanticClass().equals(item2.getItemSemanticClass()))
                        {
                            hasItemAwarereference=true;
                        }
                    }
                    if(!hasItemAwarereference)arr.add(item);
                }
            }
        }
        return arr;
    }

}
