package org.semanticwb.domotic.model;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;


public class SendMailAction extends org.semanticwb.domotic.model.base.SendMailActionBase 
{
    private static Logger log = SWBUtils.getLogger(SendMailAction.class);
    
    public SendMailAction(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doActionImp()
    {
        System.out.println("SendMailAction:doAction");
        try
        {
            SWBUtils.EMAIL.sendBGEmail(getEmailTo(), getEmailSubject(), getEmailBody());
        }catch(Exception e)
        {
            log.event(e);
        }
    }
    
    
}
