package org.semanticwb.domotic.model;

import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;


public class OnContextChange extends org.semanticwb.domotic.model.base.OnContextChangeBase 
{
    private static Logger log = SWBUtils.getLogger(OnContextChange.class);
    
    public OnContextChange(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void onEventImp(String stat)
    {
        System.out.println("OnContextChange:onEvent:"+stat);
        try
        {
            if(stat.equals(getContextEventType()))
            {
                doActions();
            }
        }catch(Exception e)
        {
            log.error(e);
        }
    }
    
    
}
