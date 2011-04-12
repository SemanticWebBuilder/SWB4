package org.semanticwb.process.model;

import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;


public class CallActivity extends org.semanticwb.process.model.base.CallActivityBase 
{
    public CallActivity(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public GenericIterator<GraphicalElement> listChilds()
    {
        return super.listChilds();
    }



//    @Override
//    public void execute(FlowNodeInstance instance, User user)
//    {
//        super.execute(instance, user);
//        CallActivity call=(CallActivity)instance.getFlowNodeType();
//        if(call instanceof CallProcess)
//        {
//            //System.out.println("ok...");
//            ProcessInstance inst=((Process)call.getCalledElement()).createInstance();
//            inst.start(user);
//        }
//    }




}
