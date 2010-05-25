package org.semanticwb.process.model;

import bsh.Interpreter;
import java.util.Date;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;


public class RuleIntermediateCatchEvent extends org.semanticwb.process.model.base.RuleIntermediateCatchEventBase 
{
    public static Logger log=SWBUtils.getLogger(RuleIntermediateCatchEvent.class);

    public RuleIntermediateCatchEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        boolean cond=false;
        String scond=getRule();

        Object ret=null;
        try
        {
            long ini=System.currentTimeMillis();
            Interpreter i = new Interpreter();  // Construct an interpreter
            //i.set("this",this);
            i.set("instance", instance);
            i.set("user", user);
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

        if(cond)
        {
            instance.close(user);
            GraphicalElement parent=getParent();
            if(parent!=null)
            {
                FlowNodeInstance source=instance.getRelatedFlowNodeInstance((FlowNode)parent);
                source.setStatus(Instance.STATUS_CLOSED);
                source.setAction(Instance.ACTION_EVENT);
                source.setEnded(new Date());
                source.setEndedby(user);
                source.abortDependencies(user);
            }
        }
    }
}
