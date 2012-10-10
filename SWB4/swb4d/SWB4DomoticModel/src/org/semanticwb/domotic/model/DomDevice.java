package org.semanticwb.domotic.model;

import java.util.Date;
import org.semanticwb.domotic.server.Connection;
import org.semanticwb.model.SWBModel;


public class DomDevice extends org.semanticwb.domotic.model.base.DomDeviceBase 
{
    public DomDevice(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /**
     * Actualiza el estatus y envia el estatus al gateway si el parametro send esta activo
     * @param stat
     * @param send 
     */
    public void setStatus(int stat, boolean send)
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
        
    }

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
