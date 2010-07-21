package org.semanticwb.process.model;

import bsh.Interpreter;
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
    
    private String getClassName(SemanticClass clazz)
    {
        
        return clazz.getUpperClassName();
    }
    private void addSemanticClass(SemanticClass clazz,MemoryClassLoader mls) throws Exception
    {
        CodeGenerator cg=new CodeGenerator();
        String createdClass=cg.createClassBase(clazz,false);
        mls.add(clazz.getUpperClassName(), createdClass);
    }
    private void addSemanticObject(Interpreter i,SemanticObject object) throws Exception
    {        
        SemanticClass clazz=object.getSemanticClass();
        String clasName=getClassName(clazz);
        String varname=object.getSemanticClass().getName().toLowerCase();
        i.set(clasName+" "+varname+"=", object);
    }
    private void addSemanticClasses(SemanticClass clazz,MemoryClassLoader mcls) throws Exception
    {
        addSemanticClass(clazz,mcls);
    }
    private MemoryClassLoader loadClasses(FlowNodeInstance instance) throws Exception
    {
        MemoryClassLoader mcls=new MemoryClassLoader();
        List<ProcessObject> processObjects=instance.getProcessInstance().listHeraquicalProcessObjects();
        for(ProcessObject po : processObjects)
        {
            addSemanticClasses(po.getSemanticObject().getSemanticClass(),mcls);
        }
        return mcls;
    }
    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);
        String code=getScriptCode();

        try
        {
            long ini=System.currentTimeMillis();
            Interpreter i = new Interpreter();  // Construct an interpreter
            i.setClassLoader(loadClasses(instance));
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
            
            List<ProcessObject> processObjects=instance.getProcessInstance().listHeraquicalProcessObjects();
            for(ProcessObject po : processObjects)
            {
                addSemanticObject(i,po.getSemanticObject());
            }

            Object ret=i.eval(code);
            System.out.println("ret:"+ret);
            System.out.println("time:"+ (System.currentTimeMillis()-ini ));

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
