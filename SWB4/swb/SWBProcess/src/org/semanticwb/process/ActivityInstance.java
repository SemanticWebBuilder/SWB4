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

    public Activity getActivityType()
    {
        Activity ret=null;
        if(this instanceof ProcessInstance)ret=((ProcessInstance)this).getProcessType();
        if(this instanceof TaskInstance)ret=((TaskInstance)this).getTaskType();
        return ret;
    }

    public void checkStatus(User user)
    {
        if(getStatus()<Activity.STATUS_CLOSED)
        {
            if(this instanceof ProcessInstance)
            {
                ProcessInstance inst=(ProcessInstance)this;
                boolean finish=true;
                Iterator<ActivityInstance> it=inst.listActivityInstances();
                while (finish && it.hasNext())
                {
                    ActivityInstance activityInstance = it.next();
                    activityInstance.checkStatus(user);
                    if(activityInstance.getStatus()<Process.STATUS_CLOSED)finish=false;
                }
                if(finish)
                {
                    setStatus(Process.STATUS_CLOSED);
                    setEnded(new Date());
                    setEndedby(user);
                    if(getParentProcessInstance()!=null)getParentProcessInstance().checkStatus(user);
                }
            }
        }
    }

}
