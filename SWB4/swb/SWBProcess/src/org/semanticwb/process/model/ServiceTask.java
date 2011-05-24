package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class ServiceTask extends org.semanticwb.process.model.base.ServiceTaskBase 
{
    public ServiceTask(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);

        ProcessService service=getProcessService();
        if(service!=null)
        {
            service.execute(instance, user);
        }
        
        instance.close(user);
    }

}
