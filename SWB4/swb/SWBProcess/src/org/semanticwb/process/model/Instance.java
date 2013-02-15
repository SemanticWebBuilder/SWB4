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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticProperty;

/**Clase que representa una instancia de un objeto de procesos. Es la superclase
 * de todas las instancias de los nodos de flujo y almacena información del estado
 de la ejecución de cada elemento configurado de un diagrama BPMN.*/
public class Instance extends org.semanticwb.process.model.base.InstanceBase 
{
    public final static int STATUS_INIT=0;
    public final static int STATUS_PROCESSING=1;
    public final static int STATUS_STOPED=2;
    public final static int STATUS_ABORTED=3;
    public final static int STATUS_CLOSED=4;
    public final static int STATUS_OPEN=5;
    //public final static int STATUS_READY;
    //public final static int STATUS_ACTIVE;
    //public final static int STATUS_INACTIVE;
    //public final static int STATUS_COMPLETING;
    //public final static int STATUS_WITHDRAWN;
    //public final static int STATUS_COMPLETED;
    //public final static int STATUS_COMPENSATING;
    //public final static int STATUS_FAILING;
    //public final static int STATUS_TERMINATING;
    //public final static int STATUS_COMPENSATED;
    //public final static int STATUS_FAILED;
    //public final static int STATUS_TERMINATED;
    //public final static int STATUS_CLOSED;
    public final static String ACTION_ACCEPT="accept";
    public final static String ACTION_REJECT="reject";
    public final static String ACTION_CANCEL="cancel";
    public final static String ACTION_EVENT="event";

    public Instance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public ProcessInstance getProcessInstance()
    {
        if(this instanceof FlowNodeInstance)return ((FlowNodeInstance)this).getProcessInstance();
        else return (ProcessInstance)this;
    }

    /**
     * Devuelve el tipo del nodo de flujo en el modelo asociado a la instancia
     * del objeto. Por ejemplo, si el objeto es una tarea de usuario, el método
     * devolverá un objeto UserTask.
     * @return
     */
    public ProcessElement getProcessElementType()
    {
        ProcessElement ret=null;
        if(this instanceof FlowNodeInstance)ret=((FlowNodeInstance)this).getFlowNodeType();
        else ret=((ProcessInstance)this).getProcessType();
        return ret;
    }
    
    /**
     * Se ejecuta cada que se crea la intancia del nodo de flujo
     * @param user Usuario que crea la instancia.
     */
    public void start(User user)
    {
        //System.out.println("start:"+getId()+" "+getProcessElementType().getClass().getName()+" "+getProcessElementType().getTitle());
        setStatus(STATUS_PROCESSING);
    }


    /**
     * Cierra la instancia del nodo de flujo y continúa al siguiente nodo.
     * Por defecto asigna el estado CLOSED a la instancia con la acción ACCEPT.
     * @param user Usuario que cierra la instancia.
     */
    public void close(User user)
    {
        close(user, STATUS_CLOSED, ACTION_ACCEPT);
    }

    /**
     * Cierra la instancia del nodo de flujo y continúa al siguiente nodo.
     * Asigna la acción de cierre especificada en el parámetro 'action'.
     * @param user Usuario que cierra la instancia.
     * @param action Acción de cierre de la instancia.
     */
    public void close(User user, String action)
    {
        close(user, STATUS_CLOSED, action);
    }

    /**
     * Cierra la instancia del nodo de flujo marcándola como abortada y continúa
     * al siguiente nodo. Asigna el estado ABORTED a la instancia con la acción
     * CANCEL.
     * @param user Usuario que aborta la instancia.
     */
    public void abort(User user)
    {
        close(user, STATUS_ABORTED, ACTION_CANCEL);
    }

    /**
     * Cierra la instancia del nodo de flujo y continúa al siguiente nodo. Asigna
     * la información de cierre asociada con los parámetros.
     * @param user Usuario que cierra la instancia.
     * @param status Estado asignado a la instancia al momento del cierre.
     * @param action Acción asignada a la instancia al momento del cierre.
     */
    public void close(User user, int status, String action)
    {
        //System.out.println("close:"+getId()+" "+getProcessElementType().getClass().getName()+" "+getProcessElementType().getTitle()+" "+status+" "+action);
        //Thread.dumpStack();
        setStatus(status);
        setAction(action);
        setEnded(new Date());
        setEndedby(user);
    }

    /**
     * Elimina las instancias de objetos de datos temporales de una instancia de proceso.
     */
    protected void removeTemporallyDataobjects()
    {
        Iterator<ItemAwareReference> it=listItemAwareReferences();
        while (it.hasNext())
        {
            ItemAwareReference itemAwareReference = it.next();
            SWBClass obj=itemAwareReference.getProcessObject();
            //System.out.println("removeTemporallyDataobjects:"+this+" "+itemAwareReference+" "+itemAwareReference.getItemAware());
            if(itemAwareReference.getItemAware() instanceof Collectionable || itemAwareReference.isItemAwareTemporal())
            {
                //removeItemAwareReference(itemAwareReference);
                if(!itemAwareReference.isProcessObjectReused())
                {
                    obj.remove();
                }
                itemAwareReference.remove();
            }
        }
        //TODO: Revisar eliminar propiedades temporales del DataObjects
    }


    /**
     * Método que se invoca cuando el nodo de flujo se activa, es decir, cuando
     * el nodo anterior invoca su método close. Por defecto, el método incrementa
     * el contador de ejecuciones de la instancia.
     * @param user Usuario que ejecuta la instancia.
     */
    public void execute(User user)
    {
        //System.out.println("execute:"+getId()+" "+getProcessElementType().getClass().getName()+" "+getProcessElementType().getTitle());
        setExecution(getExecution()+1);
    }

    /**
     * Método que procesa la notificación de un evento propagado por el ProcessObserver.
     * @param from Instancia que genera el evento.
     */
    public void notifyEvent(FlowNodeInstance from)
    {
        //System.out.println("notifyEvent:"+getId()+" "+getProcessElementType().getClass().getName()+" "+getProcessElementType().getTitle());
    }


    /**
     * Obtiene un iterador a los objetos de datos relacionados con la instancia
     * del nodo de flujo y sus instancias padres. Es decir, si la instancia es
     * una actividad en un subproceso, también se recuperan los objetos de datos
     * del subproceso padre.
     * @return
     */
    public List<ItemAwareReference> listHeraquicalItemAwareReference()
    {
        ArrayList<ItemAwareReference> ret=new ArrayList();
        Iterator<ItemAwareReference> it=listItemAwareReferences();
        while (it.hasNext())
        {
            ItemAwareReference itemAwareReference = it.next();
            ret.add(itemAwareReference);
        }
        if(this instanceof FlowNodeInstance)
        {
            FlowNodeInstance flow=(FlowNodeInstance)this;
            Instance parent=(Instance)flow.getContainerInstance();
            if(parent!=null)
            {
                ret.addAll(parent.listHeraquicalItemAwareReference());
            }
        }
        return ret;
    }
    
    public void setStatus(int status)
    {
        if(this instanceof FlowNodeInstance)
        {
            FlowNodeInstance flow=(FlowNodeInstance)this;
            flow.setStatus(status);
        }else if(this instanceof ProcessInstance)
        {
            ProcessInstance process=(ProcessInstance)this;
            process.setStatus(status);
        }
    }
    
    public int getStatus()
    {
        if(this instanceof FlowNodeInstance)
        {
            FlowNodeInstance flow=(FlowNodeInstance)this;
            return flow.getStatus();
        }else if(this instanceof ProcessInstance)
        {
            ProcessInstance process=(ProcessInstance)this;
            return process.getStatus();
        }
        return 0;
    }
    
    public void setOwnerproperties(User user)
    {
        Iterator<OwnerProperty> it = getProcessInstance().getProcessType().listOwnerProperties();
        while (it.hasNext())
        {
            OwnerProperty ownerProperty = it.next();
            String propid = ownerProperty.getUserProperty();
            SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(propid);
            if (prop.isDataTypeProperty())
            {
                getSemanticObject().addLiteralProperty(prop, user.getSemanticObject().getLiteralProperty(prop));
            } else {
                getSemanticObject().addObjectProperty(prop, user.getSemanticObject().getObjectProperty(prop));
            }
        }
    }
    
    public void setOwnerProperty(String propid, Object value)
    {
        //TODO: Revisar si es literal u objeto
        SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(propid);
        getSemanticObject().addLiteralProperty(prop, new SemanticLiteral(value));
    }

    public Object getOwnerProperty(String propid)
    {
        SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(propid);
        return getSemanticObject().getLiteralProperty(prop).getValue();
    }

    @Override
    public void setAssignedto(User user)
    {
        setOwnerproperties(user);
    }
    
}
