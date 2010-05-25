package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.model.User;


public class Activity extends org.semanticwb.process.model.base.ActivityBase 
{

    public Activity(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        //list atached events
        Iterator<GraphicalElement> it=instance.getFlowNodeType().listChilds();
        while (it.hasNext())
        {
            GraphicalElement graphicalElement = it.next();
            if(graphicalElement instanceof IntermediateCatchEvent)
            {
                instance.executeRelatedFlowNodeInstance((IntermediateCatchEvent)graphicalElement,instance,null, user);
            }
        }
    }
}
