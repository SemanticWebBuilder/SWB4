package org.semanticwb.process;

import java.util.Date;
import org.semanticwb.model.User;


public class TaskInstance extends org.semanticwb.process.base.TaskInstanceBase 
{
    public TaskInstance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void accept(User user)
    {
        if(getTaskType().isKeepOpen())
        {
            setStatus(Activity.STATUS_OPEN);
        }else
        {
            setStatus(Activity.STATUS_CLOSED);
        }
        setEnded(new Date());
        setEndedby(user);
        getProcessInstance().checkStatus(user);
    }
}
