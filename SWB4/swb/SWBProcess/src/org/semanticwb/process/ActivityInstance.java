package org.semanticwb.process;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.User;


public class ActivityInstance extends org.semanticwb.process.base.ActivityInstanceBase 
{
    public ActivityInstance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void checkStatus(User user)
    {
        boolean finish=true;
        Iterator<TaskInstance> it=listTaskinstances();
        while (finish && it.hasNext())
        {
            TaskInstance taskInstance = it.next();
            if(taskInstance.getStatus()<Process.STATUS_CLOSED)finish=false;
        }
        if(finish)
        {
            setStatus(Process.STATUS_CLOSED);
            setEnded(new Date());
            setEndedby(user);
            getProcessInstance().checkStatus(user);
        }

    }
}
