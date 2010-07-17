package org.semanticwb.process.model;

import bsh.Interpreter;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;


public class ProcessRule extends org.semanticwb.process.model.base.ProcessRuleBase 
{
    public static Logger log=SWBUtils.getLogger(ProcessRule.class);

    public ProcessRule(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean evaluate(FlowNodeInstance instance, User user)
    {
        boolean cond=false;
        String scond=getRuleCondition();

//        FlowNodeInstance targetInstance=null;
//        GraphicalElement target=getTarget();
//        if(target instanceof FlowNode)
//        {
//            FlowNode node=(FlowNode)target;
//            targetInstance=sourceInstance.getRelatedFlowNodeInstance(node);
//        }

        Object ret=null;
        try
        {
            long ini=System.currentTimeMillis();
            Interpreter i = new Interpreter();  // Construct an interpreter
            //i.set("this",this);
            i.set("instance", instance);
            //i.set("target", targetInstance);
            i.set("user", user);
            if(instance!=null)
            {
                i.set("accepted", Instance.ACTION_ACCEPT.equals(instance.getAction()));
                i.set("rejected", Instance.ACTION_REJECT.equals(instance.getAction()));
                i.set("canceled", Instance.ACTION_CANCEL.equals(instance.getAction()));
            }
            i.eval("import org.semanticwb.process.model.*");

            ret=i.eval(scond);
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
}
