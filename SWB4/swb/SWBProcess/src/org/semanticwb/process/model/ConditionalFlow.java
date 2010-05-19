package org.semanticwb.process.model;

import bsh.Interpreter;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;


public class ConditionalFlow extends org.semanticwb.process.model.base.ConditionalFlowBase 
{
    public static Logger log=SWBUtils.getLogger(ConditionalFlow.class);

    public ConditionalFlow(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public boolean evaluate(FlowNodeInstance sourceInstance, User user)
    {
        boolean cond=false;
        String scond=getFlowCondition();

        FlowNodeInstance targetInstance=null;
        GraphicalElement target=getTarget();
        if(target instanceof FlowNode)
        {
            FlowNode node=(FlowNode)target;
            targetInstance=sourceInstance.getRelatedFlowNodeInstance(node);
        }
        
        Object ret=null;
        try
        {
            long ini=System.currentTimeMillis();
            Interpreter i = new Interpreter();  // Construct an interpreter
            //i.set("this",this);
            i.set("source", sourceInstance);
            i.set("target", targetInstance);
            i.set("user", user);
            i.set("accepted", Instance.ACTION_ACCEPT.equals(sourceInstance.getAction()));
            i.set("rejected", Instance.ACTION_REJECT.equals(sourceInstance.getAction()));
            i.set("canceled", Instance.ACTION_CANCEL.equals(sourceInstance.getAction()));
            i.eval("import org.semanticwb.process.model.*");

            ret=i.eval(getFlowCondition());
            System.out.println("ret:"+ret);
            System.out.println("time:"+ (System.currentTimeMillis()-ini ));
        }catch(Exception e)
        {
            log.error(e);
        }
        //String action=source.getAction();
        if(ret!=null && ret instanceof Boolean)
        {
            if((Boolean)ret)cond=true;
        }
        return cond;
    }



    @Override
    public void execute(FlowNodeInstance source, User user)
    {
        super.execute(source,user);
    }

}
