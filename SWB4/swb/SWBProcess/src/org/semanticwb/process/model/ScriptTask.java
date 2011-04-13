package org.semanticwb.process.model;

import bsh.Interpreter;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenerator;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.script.util.MemoryClassLoader;

public class ScriptTask extends org.semanticwb.process.model.base.ScriptTaskBase 
{
    public static Logger log=SWBUtils.getLogger(ProcessRule.class);
    
    public ScriptTask(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }  
    
    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);
        String code=getScriptCode();

        try
        {
            Interpreter i = SWBPClassMgr.getInterpreter(instance, user);
            Object ret=i.eval(code);
        }catch(Exception e)
        {
            log.error(e);

            Iterator<GraphicalElement> it=listChilds();
            while (it.hasNext())
            {
                GraphicalElement graphicalElement = it.next();
                if(graphicalElement instanceof ErrorIntermediateCatchEvent)
                {
                    ErrorIntermediateCatchEvent event=(ErrorIntermediateCatchEvent)graphicalElement;
                    //TODO:Validar excepciones
                    //String c1=event.getActionCode();
                    //String c2=((Event)instance.getFlowNodeType()).getActionCode();
                    //if((c1!=null && c1.equals(c2)) || c1==null && c2==null)
                    {
                        FlowNodeInstance source=(FlowNodeInstance)instance;
                        source.close(user, Instance.STATUS_ABORTED, Instance.ACTION_EVENT, false);

                        FlowNodeInstance fn=((FlowNodeInstance)instance).getRelatedFlowNodeInstance(event);
                        fn.setSourceInstance(instance);
                        event.notifyEvent(fn, instance);
                        return;
                    }
                }
            }

        }
        instance.close(user);
    }

}
