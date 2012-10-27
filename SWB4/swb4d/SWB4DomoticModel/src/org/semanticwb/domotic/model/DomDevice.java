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
        }
        dstat.setActive(true);
        dstat.setLastUpdate(new Date());
        dstat.setStatus(stat);
        
        if(send)
        {
            Connection con=getDomGateway().getConnection();
            if(con!=null)
            {
                con.sendMessage("upd "+getDevId()+" "+getDevZone()+" "+dstat.getStatus()+"\n");
            }
        }
        sendWebSocketStatus(notifyParents);
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
        setStatus(value, true, true);
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
