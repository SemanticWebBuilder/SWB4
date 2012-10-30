package org.semanticwb.domotic.model;

import java.util.Iterator;


public class DomEvent extends org.semanticwb.domotic.model.base.DomEventBase 
{    
    
    public DomEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public final void onEvent(String stat)
    {
        if(getDomContext()==null || getDomContext().isActive())
        {
            onEventImp(stat);
        }
    }
    
    public void onEventImp(String stat)
    {
        //Implementar en subclasses
    }
    
    /**
     * Ejecutar las Acciones relacionadas al evento
     */
    
    public void doActions()
    {
        Iterator<DomAction> it=listDomActions();
        while (it.hasNext())
        {
            DomAction domAction = it.next();
            domAction.doAction();
        }           
    }
}
