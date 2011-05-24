package org.semanticwb.process.model;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;


public class SendMail extends org.semanticwb.process.model.base.SendMailBase 
{
    private static Logger log=SWBUtils.getLogger(SendMail.class);

    public SendMail(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);
        try
        {
            SWBUtils.EMAIL.sendMail(getTo(), getSubject(), getContent());
        }catch(Exception e)
        {
            log.error(e);
            throw new RuntimeException(e);
        }
    }


}
