package org.semanticwb.domotic.model;

import java.util.Iterator;
import org.semanticwb.domotic.server.WebSocketServlet;
import org.semanticwb.model.SWBModel;


public class DomContext extends org.semanticwb.domotic.model.base.DomContextBase 
{
    public DomContext(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void setActive(boolean value)
    {
        super.setActive(value);
        if(value==true)
        {
            SWBModel model=(SWBModel)getSemanticObject().getModel().getModelObject().createGenericInstance();
            Iterator<DomContext> it=ClassMgr.listDomContexts(model);
            while (it.hasNext())
            {
                DomContext domContext = it.next();
                if(!domContext.equals(this))domContext.setActive(false);
            }
        }
        sendWebSocketStatus();
    }        
    
    /**
     * Notifica a los grupos por websockets si notifyParents es true 
     * @param notifyParents 
     */
    public void sendWebSocketStatus()
    {
        WebSocketServlet.broadcast(getShortURI()+" "+isActive());
    }    
}
