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
package org.semanticwb.domotic.model;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.domotic.server.Connection;
import org.semanticwb.domotic.server.WebSocketServlet;
import org.semanticwb.model.SWBModel;


public class DomDevice extends org.semanticwb.domotic.model.base.DomDeviceBase 
{
    public DomDevice(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /**
     * Actualiza el estatus y envia el estatus al gateway si el parametro send esta activo, notifica a los grupos por websockets si notifyParents es true 
     * @param stat
     * @param send 
     * @param notifyParents 
     */
    public void setStatus(int stat, boolean send, boolean notifyParents)
    {
        if(!isDimerizable() && stat>0)stat=16;
        
        DomDeviceStat dstat=getDomDeviceStat();
        if(dstat==null)
        {
            dstat=DomDeviceStat.ClassMgr.createDomDeviceStat((SWBModel)getSemanticObject().getModel().getModelObject().createGenericInstance());            
            setDomDeviceStat(dstat);       
            dstat.setActive(true);
        }
        dstat.setLastUpdate(new Date());
                
        int oldStat=dstat.getStatus();
        
        dstat.setStatus(stat);
        
        if(send)
        {
            Connection con=getDomGateway().getConnection();
            if(con!=null)
            {
                con.sendMessage("upd "+getDevZone()+" "+getDevId()+" "+dstat.getStatus()+"\n");
            }
        }
        sendWebSocketStatus(notifyParents);
        
        if(oldStat!=stat)
        {
            Iterator<OnDeviceChange> it=listOnDeviceChanges();
            while (it.hasNext())
            {
                OnDeviceChange onDeviceChange = it.next();
                onDeviceChange.onEvent(""+stat);
            }            
        }
    }
    
    /**
     * Notifica a los grupos por websockets si notifyParents es true 
     * @param notifyParents 
     */
    public void sendWebSocketStatus(boolean notifyParents)
    {
        WebSocketServlet.broadcast(getShortURI()+" "+getStatus());
        if(notifyParents)
        {
            Iterator<DomGroup> it=listDomGroups();
            while (it.hasNext())
            {
                DomGroup domGroup = it.next();
                domGroup.sendWebSocketStatus(notifyParents);
            }
        }
    }
    
    /**
     * Actualiza el estatus y envia el estatus al gateway si el parametro send esta activo y notifica a los grupos por websockets 
     * @param value
     * @param send 
     */
    public void setStatus(int value, boolean send)
    {
        setStatus(value, send, true);
    }

    /**
     * Actualiza el estatus y envia el estatus al gateway y  notifica a los grupos por websockets 
     * @param value 
     */
    @Override
    public void setStatus(int value)
    {
        setStatus(value, true);
    }
    
    

    @Override
    public int getStatus()
    {
        DomDeviceStat dstat=getDomDeviceStat();
        if(dstat==null)
        {
            dstat=DomDeviceStat.ClassMgr.createDomDeviceStat((SWBModel)getSemanticObject().getModel().getModelObject().createGenericInstance());            
            setDomDeviceStat(dstat);       
        }        
        return dstat.getStatus();
    }
    
}
