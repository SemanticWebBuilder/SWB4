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
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class ProcessInstance extends org.semanticwb.process.model.base.ProcessInstanceBase 
{
    private static Logger log=SWBUtils.getLogger(ProcessInstance.class);
    
    
    public ProcessInstance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Se ejecuta cada que se crea la intancia del objeto de flujo desde un evento de inicio normal
     * @param user
     */
    @Override
    public void start(User user)
    {
        super.start(user);
        Process process=getProcessType();
        Iterator<GraphicalElement> actit=process.listContaineds();
        while (actit.hasNext())
        {
            GraphicalElement ele=actit.next();
            if(ele instanceof FlowNode)
            {
                FlowNode flownode = (FlowNode)ele;
                if(flownode instanceof StartEvent)
                {
                    StartEvent init=(StartEvent)flownode;
                    FlowNodeInstance eventins=startEvent(user, init);
                    eventins.start(user);
                    return;
                }
            }
        }
    }

    /**
     * Se ejecuta cada que se crea la intancia del objeto de flujo desde el processObserver
     * @param user
     */
    public void start(User user, StartEventNode event)
    {
        super.start(user);
        FlowNodeInstance eventins=startEvent(user, event);
        eventins.start(user);
    }
    
    public void start(User user, StartEventNode event, FlowNodeInstance invoker)
    {
        super.start(user);
        FlowNodeInstance eventins=startEvent(user, event);
        eventins.start(invoker,null,user);
    }    
    
    private FlowNodeInstance startEvent(User user, StartEventNode event)
    {
        initItemAwares(user, event);
        return event.createInstance(this);
    }     
    
    private void initItemAwares(User user, StartEventNode startEvent)
    {
        //Se crean las instancias de los objetos de datos y se referencían con el proceso.
        Iterator<ItemAware> it=getProcessType().listRelatedItemAware().iterator();
        while (it.hasNext())
        {
            ItemAware item = it.next();
            SemanticClass scls=item.getItemSemanticClass();
            SemanticProperty sprop=null;
            SWBModel model=this.getProcessSite();
            String id=null;
            String code=null;
            if(item instanceof Collectionable) //Es un dato temporal
            {
                model=this.getProcessSite().getProcessDataInstanceModel();
            }else //Es un dato persistente
            {
                id=((DataStore)item).getDataObjectId();
                if(id!=null && id.length()==0)id=null;
                code=((DataStore)item).getInitializationCode();
                if(code!=null && code.length()==0)code=null;
            }

            if(scls!=null)//El elemento fué configurado
            {
                SemanticObject ins=null;
                if(code!=null) //Ejecutar código de inicialización de la instancia
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
                    if(ret!=null && ret instanceof SemanticObject && ((SemanticObject)ret).instanceOf(scls)) //El código se ejecutó bien y devolvió un objeto
                    {
                        ins=((SemanticObject)ret);
                    }

                    if(ret!=null && ret instanceof SWBClass && ((SWBClass)ret).getSemanticObject().instanceOf(scls)) //El código se ejecutó bien y devolvió una clase semántica
                    {
                        ins=((SWBClass)ret).getSemanticObject();
                    }
                }
                if(ins==null) //No se creó objeto a partir del código de inicialización
                {
                    if(id==null) //No se especificó Id de objeto a recuperar, se crea uno nuevo id
                    {
                        id=String.valueOf(model.getSemanticModel().getCounter(scls));
                    }else //Se especificó Id de objeto a recuperar, se recupera
                    {
                        ins=SemanticObject.createSemanticObject(model.getSemanticModel().getObjectUri(id, scls));
                    }
                    if(ins==null) //No se recuperó ningún objeto
                    {
                        //Verificar mapeos de eventos iniciales de mensaje
                        Iterator<ItemAwareMapping> auxit=ItemAwareMapping.ClassMgr.listItemAwareMappingByLocalItemAware(item, item.getProcessSite());
                        if(!auxit.hasNext() || startEvent==null || !(startEvent instanceof MessageStartEvent)) //No hay mapeos, crear nueva instancia
                        {
                            ins=model.getSemanticModel().createSemanticObjectById(id, scls);
                        } else {
                            //Recorrer los mapeos
                            boolean hasMapping=false;
                            while (auxit.hasNext())
                            {
                                ItemAwareMapping itemAwareMapping = auxit.next();
                                MessageStartEvent stevent=(MessageStartEvent)startEvent;
                                if(stevent.hasItemAwareMapping(itemAwareMapping))
                                {
                                    hasMapping=true;
                                    break;
                                }                                
                            }
                            if(!hasMapping)
                            {
                                ins=model.getSemanticModel().createSemanticObjectById(id, scls);
                            }
                        }
                    }
                }
                
                //Crear referencia entre el proceso y la isntancia del objeto de datos
                ItemAwareReference ref=ItemAwareReference.ClassMgr.createItemAwareReference(this.getProcessSite());
                ref.setItemAware(item); 
                if(ins!=null)
                {
                    SWBClass c = (SWBClass)ins.createGenericInstance();
                    ref.setProcessObject(c);
                }
                addItemAwareReference(ref);
                //System.out.println("addItemAwareReference:"+ref);
            }
        }        
    }

    private void listAllItemAwareReferences(Instance inst, ArrayList arr)
    {
        Iterator<ItemAwareReference> it=inst.listItemAwareReferences();
        while(it.hasNext())
        {
            ItemAwareReference item=it.next();
            arr.add(item);
        }

        if(inst instanceof ContainerInstanceable)
        {
            Iterator<FlowNodeInstance> it2=((ContainerInstanceable)inst).listFlowNodeInstances();
            while(it2.hasNext())
            {
                FlowNodeInstance obj = it2.next();
                listAllItemAwareReferences(obj, arr);
            }
        }

    }

    @Override
    public void close(User user, int status, String action)
    {
        super.close(user, status, action);
        
        connectItemsAware();
        
        Iterator<FlowNodeInstance> it=listAllFlowNodeInstance();
        while (it.hasNext())
        {
            FlowNodeInstance flowNodeInstance = it.next();
            if(flowNodeInstance.getStatus()!=Instance.STATUS_CLOSED && flowNodeInstance.getStatus()!=Instance.STATUS_ABORTED)
            {
                flowNodeInstance.setStatus(Instance.STATUS_ABORTED);
            }
        }

        removeTemporallyDataobjects();
    }

    /**
     * Relaciona los ItemAware de entrada o globales con los ItemAware de salida
     */
    protected void connectItemsAware()
    {
        //TODO: Revisar variables de salida
    }

    /**
     * Regresa todos los ItemAwareReferences del proceso y subprocesos
     * @return
     */
    public Iterator<ItemAwareReference> listAllItemAwareReferences()
    {
        ArrayList<ItemAwareReference> ret=new ArrayList();
        listAllItemAwareReferences(this, ret);
        return ret.iterator();
    }

    /**
     * Regresa todos los FlowNodeInstance del proceso y subprocesos
     * @return 
     */
    public Iterator<FlowNodeInstance> listAllFlowNodeInstance()
    {
        ArrayList<FlowNodeInstance> arr=new ArrayList();
        Iterator<FlowNodeInstance> it=listFlowNodeInstances();
        while (it.hasNext())
        {
            FlowNodeInstance flowNodeInstance = it.next();
            arr.add(flowNodeInstance);
            if(flowNodeInstance instanceof SubProcessInstance)
            {
                Iterator<FlowNodeInstance> it2=((SubProcessInstance)(flowNodeInstance)).listAllFlowNodeInstance();
                while (it2.hasNext())
                {
                    FlowNodeInstance flowNodeInstance2 = it2.next();
                    arr.add(flowNodeInstance2);
                }
            }
        }
        return arr.iterator();
    }
}
