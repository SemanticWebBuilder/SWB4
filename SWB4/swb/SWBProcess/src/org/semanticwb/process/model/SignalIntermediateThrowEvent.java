package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.model.User;


public class SignalIntermediateThrowEvent extends org.semanticwb.process.model.base.SignalIntermediateThrowEventBase 
{
    public SignalIntermediateThrowEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        String code=getActionCode();

        ProcessElement cont=instance.getParentInstance().getProcessElementType();
        if(cont instanceof Process)
        {
            Iterator<GraphicalElement> it=((Process)cont).listContaineds();
            while (it.hasNext())
            {
                GraphicalElement ge = it.next();
                if(ge instanceof SignalIntermediateCatchEvent)
                {
                    String code2=((SignalIntermediateCatchEvent)ge).getActionCode();
                    if((code!=null && code.equals(code2))  || (code==null && code2==null))
                    {
                        instance.executeRelatedFlowNodeInstance((FlowNode)ge,null, user);
                    }
                }
            }
        }
        instance.close(user,instance.getSourceInstance().getAction());
    }


}
