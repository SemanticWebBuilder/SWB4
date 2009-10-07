package org.semanticwb.process;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.User;


public class ProcessInstance extends org.semanticwb.process.base.ProcessInstanceBase 
{
    public ProcessInstance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void checkStatus(User user)
    {
        boolean finish=true;
        Iterator<ActivityInstance> it=listActivityInstances();
        while (finish && it.hasNext())
        {
            ActivityInstance activityInstance = it.next();
            if(activityInstance.getStatus()<Process.STATUS_CLOSED)finish=false;
        }
        if(finish)
        {
            setStatus(Process.STATUS_CLOSED);
            setEnded(new Date());
            setEndedby(user);
        }

    }
}
