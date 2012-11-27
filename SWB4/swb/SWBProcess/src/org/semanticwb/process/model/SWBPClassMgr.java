/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.process.model;

import bsh.Interpreter;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.openjena.atlas.iterator.Iterator2;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
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
    private static MemoryClassLoader mcls=null;

    static 
    {
        getClassLoader();
    }

    public static Class getClassDefinition(SemanticClass scls)
    {
        //System.out.println("className:"+className);
        Class clazz=null;
        try
        {
            clazz=getClassLoader().loadClass(scls.getVirtualClassName());
        }catch(Exception e){
            log.debug(e);
        }
        return clazz;
    }
    
    public static ClassLoader getClassLoader()
    {
        if(mcls==null)
        {
            mcls=new MemoryClassLoader(SWBPClassMgr.class.getClassLoader());
            
            HashMap<String,String> classes=new HashMap<String, String>();
            CodeGenerator cg=new CodeGenerator();
            cg.setGenerateVirtualClasses(true);
            
            Iterator<SemanticClass> it=SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
            while (it.hasNext())
            {
                SemanticClass scls = it.next();
                if(scls.isSWBVirtualClass())
                {
                    String className=scls.getVirtualClassName();
                    try
                    {
                        String code=cg.createClassBase(scls,false);
                        
                        //System.out.println(code);
                        classes.put(className, code);
                    }catch(Exception e)
                    {
                        log.error("Error compiling:" +className,e);
                    }
                }
            }                        
            if (classes.size()>0) mcls.addAll(classes);
            SWBPlatform.getSemanticMgr().setClassLoader(mcls);
        }
        return mcls;
    }

    public static void reset()
    {
        mcls=null;
        getClassLoader();
    }
        
    
    public static Interpreter getInterpreter(Instance instance, User user)
    {
        Interpreter i = new Interpreter();  // Construct an interpreter
        i.setClassLoader(getClassLoader());
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
    
    public static Interpreter getInterpreter()
    {
        Interpreter i = new Interpreter();  // Construct an interpreter
        i.setClassLoader(getClassLoader());
        i.setStrictJava(true);
        try
        {
            i.eval("import org.semanticwb.process.model.*");        
        }catch(Exception e)
        {
            log.error(e);
        }
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
                //System.out.println("Cargando clase "+item.getItemAware().getName()+" ...");
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
