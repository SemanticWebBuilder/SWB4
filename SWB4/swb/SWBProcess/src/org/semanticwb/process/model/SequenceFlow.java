package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class SequenceFlow extends org.semanticwb.process.model.base.SequenceFlowBase 
{
    public SequenceFlow(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance source, User user)
    {
        GraphicalElement target=getTarget();
        if(target instanceof FlowNode)
        {
            FlowNode node=(FlowNode)target;
            FlowNodeInstance inst=source.getRelatedFlowNodeInstance(node);
            if(inst==null)
            {
                inst=node.createInstance(source.getContainerInstance());
            }else
            {
                //recrear instancia en ciclos
                int status=inst.getStatus();
                if(status==Instance.STATUS_ABORTED || status==Instance.STATUS_CLOSED)
                {
                    inst.reset();
                }
            }
            if(inst.getStatus()==Instance.STATUS_INIT)
            {
                inst.start(source,this,user);
            }else
            {
                inst.setFromConnection(this);
                inst.setSourceInstance(source);
                inst.execute(user);
            }
        }

    }    
}
