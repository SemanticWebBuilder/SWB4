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

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;

public class ProcessObserver extends org.semanticwb.process.model.base.ProcessObserverBase implements SemanticObserver
{
    private static Logger log=SWBUtils.getLogger(ProcessObserver.class);

    /** The timer. */
    private transient Timer timer;

    /** The delays. */
    private transient int delays = 60;

    /** The t. */
    private transient TimerTask t=null;

    /**
     * Inits the.
     */
    public void init()
    {
        t=new TimerTask(){
            public void run()
            {
                try {
                    checkTimer();
                } catch (Exception e){
                    log.error(e);
                }
            }
        };
        timer = new Timer("ProcessObserver("+delays+"s)", true);
        timer.scheduleAtFixedRate(t, delays*1000, delays*1000);
        log.event("Initializing ProcessObserver("+delays+"s)...");

        SWBPlatform.getSemanticMgr().registerObserver(this);
    }

    public ProcessObserver(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
        init();
    }
    

    public void sendEvent(FlowNodeInstance instance)
    {
        //System.err.println("sendEvent:"+instance);
        
        Iterator<FlowNodeInstance> it=null;
        if(instance.getFlowNodeType() instanceof MessageEndEvent || instance.getFlowNodeType() instanceof MessageIntermediateThrowEvent)
        {
            it=listMessageObserverInstances();
        }else if(instance.getFlowNodeType() instanceof SignalEndEvent || instance.getFlowNodeType() instanceof SignalIntermediateThrowEvent)
        {
            it=listSignalObserverInstances();
        }
            
        while (it!=null && it.hasNext())
        {
            FlowNodeInstance flowNodeInstance = it.next();
            //System.out.println("flowNodeInstance:"+flowNodeInstance);
            if(flowNodeInstance.getFlowNodeType() instanceof ActionCodeable && instance.getFlowNodeType() instanceof ActionCodeable)
            {
                String c1=((ActionCodeable)flowNodeInstance.getFlowNodeType()).getActionCode();
                String c2=((ActionCodeable)instance.getFlowNodeType()).getActionCode();
                //System.out.println(flowNodeInstance+" "+c1+"=="+c2);
                if((c1!=null && c1.equals(c2)) || c1==null && c2==null)
                {
                    try
                    {
                        flowNodeInstance.notifyEvent(instance);
                    }catch(Exception e){log.error(e);}
                }
            }
        }

        Iterator<StartEventNode> nit=null;
        if(instance.getFlowNodeType() instanceof MessageEndEvent || instance.getFlowNodeType() instanceof MessageIntermediateThrowEvent)
        {
            nit=listMessageObserverNodes();
        }else if(instance.getFlowNodeType() instanceof SignalEndEvent || instance.getFlowNodeType() instanceof SignalIntermediateThrowEvent)
        {
            nit=listSignalObserverNodes();
        }
        
        while (nit!=null && nit.hasNext())
        {
            StartEventNode startEvent = nit.next();
            //System.out.println(startEvent);
            Containerable cont=startEvent.getContainer();
            if(cont!=null && cont instanceof Process && ((Process)cont).isActive())
            {
                if(startEvent instanceof ActionCodeable && instance.getFlowNodeType() instanceof ActionCodeable)
                {
                    String c1=((ActionCodeable)startEvent).getActionCode();
                    String c2=((ActionCodeable)instance.getFlowNodeType()).getActionCode();
                    //System.out.println(startEvent+" "+c1+"=="+c2);
                    if((c1!=null && c1.equals(c2)) || c1==null && c2==null)
                    {
                        try
                        {
                            //System.out.println("ok...");
                            ProcessInstance inst=((Process)cont).createInstance();
                            
                            inst.start(instance.getCreator(),startEvent, instance);
                            
                        }catch(Exception e){log.error(e);}
                    }
                }
            }
        }

    }

    private void checkTimer()
    {
        //System.out.println("checkTimer...");
        //Check timer
        {
            Iterator<FlowNodeInstance> it=listTimeObserverInstances();
            while (it.hasNext())
            {
                FlowNodeInstance flowNodeInstance = it.next();
                ProcessPeriodRefable pr=((ProcessPeriodRefable)flowNodeInstance.getFlowNodeType());
                Iterator<ProcessPeriodRef> it2=pr.listProcessPeriodRefs();
                while (it2.hasNext()) {
                    ProcessPeriodRef ppr = it2.next();
                    if(ppr.isActive())
                    {
                        //System.out.println("checking:"+ppr.getProcessPeriod());
                        if(ppr.getProcessPeriod().isOnSchedule(flowNodeInstance))
                        {
                            try
                            {
                                //System.out.println("ok...");
                                flowNodeInstance.notifyEvent(null);
                            }catch(Exception e){log.error(e);}
                        }
                    }
                }
            }

            Iterator<StartEventNode> sit=listTimeObserverNodes();
            while (sit.hasNext())
            {
                StartEventNode startEvent = sit.next();
                if (startEvent != null) {
                    Containerable cont=startEvent.getContainer();
                    if(cont!=null && cont instanceof Process && ((Process)cont).isActive())
                    {
                        ProcessPeriodRefable pr=((ProcessPeriodRefable)startEvent);
                        Iterator<ProcessPeriodRef> it2=pr.listProcessPeriodRefs();
                        while (it2.hasNext()) {
                            ProcessPeriodRef ppr = it2.next();
                            if(ppr.isActive())
                            {
                                //System.out.println("checking:"+ppr.getProcessPeriod());
                                if(ppr.getProcessPeriod().isOnSchedule(null))
                                {
                                    try
                                    {
                                        //System.out.println("ok...");
                                        ProcessInstance inst=((Process)cont).createInstance();
                                        inst.start(null,startEvent);
                                    }catch(Exception e){log.error(e);}
                                }
                            }
                        }
                    }
                }
            }
        }

        //Check rules
        {
            Iterator<FlowNodeInstance> it=listRuleObserverInstances();
            while (it.hasNext())
            {
                FlowNodeInstance flowNodeInstance = it.next();
                ProcessRuleRefable pr=((ProcessRuleRefable)flowNodeInstance.getFlowNodeType());
                Iterator<ProcessRuleRef> it2=pr.listProcessRuleRefs();
                while (it2.hasNext()) {
                    ProcessRuleRef ppr = it2.next();
                    if(ppr.isActive())
                    {
                        //System.out.println("checking:"+ppr.getProcessRule());
                        if(ppr.getProcessRule().evaluate(flowNodeInstance, null))
                        {
                            try
                            {
                                //System.out.println("ok...");
                                flowNodeInstance.notifyEvent(null);
                            }catch(Exception e){log.error(e);}
                        }
                    }
                }
            }

            Iterator<StartEventNode> sit=listRuleObserverNodes();
            while (sit.hasNext())
            {
                StartEventNode startEvent = sit.next();
                Containerable cont=startEvent.getContainer();
                if(cont!=null && cont instanceof Process && ((Process)cont).isActive())
                {
                    ProcessRuleRefable pr=((ProcessRuleRefable)startEvent);
                    Iterator<ProcessRuleRef> it2=pr.listProcessRuleRefs();
                    while (it2.hasNext()) {
                        ProcessRuleRef ppr = it2.next();
                        if(ppr.isActive())
                        {
                            //System.out.println("checking:"+ppr.getProcessRule());
                            if(ppr.getProcessRule().evaluate(null, null))
                            {
                                try
                                {
                                    //System.out.println("ok...");
                                    ProcessInstance inst=((Process)cont).createInstance();
                                    inst.start(null,startEvent);
                                }catch(Exception e){log.error(e);}
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public void notify(SemanticObject obj, Object prop, String lang, String action)
    {
        //System.out.println(obj+" "+prop+" "+action+" "+SemanticObject.ACT_CREATE);
        if(SemanticObject.ACT_CREATE.equals(action))
        {
            if(obj.instanceOf(TimerStartEvent.sclass))
            {
                addTimeObserverNode((StartEventNode)obj.createGenericInstance());
            }else if(obj.instanceOf(SignalStartEvent.sclass))
            {
                addSignalObserverNode((StartEventNode)obj.createGenericInstance());
            }else if(obj.instanceOf(MessageStartEvent.sclass))
            {
                addMessageObserverNode((StartEventNode)obj.createGenericInstance());
            }else if(obj.instanceOf(RuleStartEvent.sclass))
            {
                addRuleObserverNode((StartEventNode)obj.createGenericInstance());
            }
        }
    }
}
