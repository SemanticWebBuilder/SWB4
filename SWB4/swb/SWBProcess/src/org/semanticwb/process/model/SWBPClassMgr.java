/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.model;

import bsh.Interpreter;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGenerator;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.script.util.MemoryClassLoader;

/**
 *
 * @author jei
 */
public class SWBPClassMgr
{
    private static Logger log=SWBUtils.getLogger(SWBPClassMgr.class);
    private static MemoryClassLoader mcls=new MemoryClassLoader(SWBPClassMgr.class.getClassLoader());

    public static Class getClassDefinition(SemanticClass scls)
    {
        String pk=scls.getCodePackage();
        String className=scls.getClassCodeName();
        if(pk!=null)
        {
            className = pk + "." + className;
        }
        System.out.println("className:"+className);
        Class clazz=null;
        try
        {
            clazz=mcls.loadClass(className);
        }catch(Exception noe){}
        if(clazz==null)
        {
            HashMap<String,String> classes=new HashMap<String, String>();
            try
            {
                CodeGenerator cg=new CodeGenerator();
                String code=cg.createClassBase(scls,false);
                classes.put(className, code);
            }catch(Exception e)
            {
                log.error("Error compiling:" +className,e);
            }
            mcls.addAll(classes);

            try
            {
                clazz=mcls.loadClass(className);
            }catch(Exception noe){}
        }
        return clazz;
    }

    public static void reset()
    {
        mcls=new MemoryClassLoader(SWBPClassMgr.class.getClassLoader());
    }
    
    public static Interpreter getInterpreter(Instance instance, User user)
    {
        Interpreter i = new Interpreter();  // Construct an interpreter
        i.setClassLoader(mcls);
        try
        {
            i.eval("import org.semanticwb.process.model.*");        
            i.set("user", user);
            if(instance!=null)
            {
                i.set("instance", instance);            
                i.set("accepted", Instance.ACTION_ACCEPT.equals(instance.getAction()));
                i.set("rejected", Instance.ACTION_REJECT.equals(instance.getAction()));
                i.set("canceled", Instance.ACTION_CANCEL.equals(instance.getAction()));
            }
        }catch(Exception e)
        {
            log.error(e);
        }
        addProcessInstanceObjectsReference(instance, i);
        return i;
    }

    public static void addProcessInstanceObjectsReference(Instance instance, Interpreter i)
    {
        List<ItemAwareReference> list=instance.listHeraquicalItemAwareReference();
        for(ItemAwareReference item : list)
        {
            String varname=item.getItemAware().getName();
            SemanticObject object=item.getProcessObject().getSemanticObject();
            try
            {
                //System.out.println("Cargando clase "+className+" ...");
                Class clazz=getClassDefinition(object.getSemanticClass());
                //System.out.println("Obteniendo constructor...");
                Constructor c=clazz.getConstructor(SemanticObject.class);
                //System.out.println("Instanciando objeto...");
                Object instanceObject=c.newInstance(object);
                //System.out.println("Agregando variable "+varname+"="+instanceObject+" de tipo "+instanceObject.getClass());
                i.set(varname, instanceObject);
                //System.out.println("Variable "+ varname +" agregada");
            }
            catch(Exception cnfe)
            {
                log.error("No se agrego variable "+varname+" a script relacionada con el objeto "+object.getURI()+" en la instancia de proceso "+instance.getURI(),cnfe);
            }
        }
    }

}
