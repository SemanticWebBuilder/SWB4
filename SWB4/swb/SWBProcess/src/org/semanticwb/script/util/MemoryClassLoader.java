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
package org.semanticwb.script.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;


public final class MemoryClassLoader extends ClassLoader 
{
    private static Logger log=SWBUtils.getLogger(MemoryClassLoader.class);
    
    Map<String, Class> classes = Collections.synchronizedMap(new HashMap<String, Class>());
    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private final MemoryFileManager manager = new MemoryFileManager(this.compiler);
    
    
    /*public MemoryClassLoader(String classname, String filecontent) {
        this(Collections.singletonMap(classname, filecontent));
    }
    

    public MemoryClassLoader(Map<String, String> map) {

        List<Source> list = new ArrayList<Source>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            list.add(new Source(entry.getKey(), Kind.SOURCE, entry.getValue()));
        }
        this.compiler.getTask(null, this.manager, null, null, null, list).call();
    }*/

    /*
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException
    {
        Class cls=null;
        ClassNotFoundException notf=null;
        try
        {    
            cls=super.loadClass(name);
        }catch(ClassNotFoundException noe)
        {
            notf=noe;
        }   
        
        if(cls==null)
        {
            try
            {
                SemanticClass scls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassByVirtualJavaName(name);
                if(scls!=null && scls.isSWBVirtualClass())
                {    
                    System.out.println("Compiling Class:"+name);
                    HashMap<String,String> classes=new HashMap<String, String>();
                    CodeGenerator cg=new CodeGenerator();
                    cg.setGenerateVirtualClasses(true);
                    String code=cg.createClassBase(scls,false);
                    classes.put(name, code);
                    addAll(classes);
                    System.out.println("Compiled Class:"+name);
                }    
            }catch(Exception e)
            {
                log.error("Error compiling:" +name,e);
            }

            cls=super.loadClass(name);
        }
        //if(cls==null)throw notf;
        return cls;
    }   
    */    

    @Override
    protected synchronized Class loadClass(String name, boolean resolve)
            throws ClassNotFoundException
    {
        // First, check if the class has already been loaded
        //System.out.println("Enter loadClass:"+name);
        Class c = findLoadedClass(name);
        if(c==null)
        {
            c = findClass(name);
            if (c == null)
            {
                c=getParentClass(name);
            }
            if (resolve)
            {
                resolveClass(c);
            }
        }
        return c;
    }

    private synchronized Class getParentClass(String name) throws ClassNotFoundException
    {
        Class c=null;
        if (getParent() != null)
        {
            c = getParent().loadClass(name);
        } else
        {
            c = findSystemClass(name);
        }
        return c;
    }
    
    public MemoryClassLoader(ClassLoader parent) {
        super(parent);
    }


    public void addAll(Map<String, String> map)
    {
        List<Source> list = new ArrayList<Source>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            list.add(new Source(entry.getKey(), Kind.SOURCE, entry.getValue()));
        }

        URLClassLoader urlClassLoader = (URLClassLoader) this.getParent();
        StringBuilder sb = new StringBuilder();
        List<String> options = new ArrayList<String>();
        options.add("-classpath");

        for (URL url : urlClassLoader.getURLs())
            sb.append(url.getFile()).append(File.pathSeparator);

        options.add(sb.toString());

        JavaCompiler.CompilationTask task=this.compiler.getTask(null, this.manager, null, options, null, list);
        task.call();
    }

    public Class<?> findDynamicClass(String name) throws ClassNotFoundException
    {
        synchronized (this.manager) {
            Output mc = this.manager.map.remove(name);
            if (mc != null) {
                byte[] array = mc.toByteArray();
                return defineClass(name, array, 0, array.length);
            }
        }
        throw new ClassNotFoundException();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

       if (classes.containsKey(name))
        {
            return classes.get(name);
        }
        else
        {
            synchronized (this.manager)
            {
                Output mc = this.manager.map.remove(name);
                if (mc != null)
                {
                    byte[] array = mc.toByteArray();
                    Class _clazz=defineClass(name, array, 0, array.length);
                    classes.put(name, _clazz);
                    return _clazz;
                }
            }
        }
        if(getParent()!=null)
        {
            return getParent().loadClass(name);
        }
        else
        {
            return super.findClass(name);
        }

    }
    public String[] getClasses()
    {
        ArrayList<String> names=new ArrayList<String>();
        for(String name : this.manager.map.keySet())
        {
            names.add(name);
        }
        return names.toArray(new String[names.size()]);
    }

    public byte[] getCode(String name)
    {
        synchronized (this.manager) {
            Output mc = this.manager.map.get(name);
            if (mc != null) {
                byte[] array = mc.toByteArray();
                return array;
            }
        }
        return null;
    }

    private static final class MemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        private final Map<String, Output> map = new HashMap<String, Output>();

        MemoryFileManager(JavaCompiler compiler) {
            super(compiler.getStandardFileManager(null, null, null));
        }

        @Override
        public Output getJavaFileForOutput(Location location, String name, Kind kind, FileObject source) {
            Output mc = new Output(name, kind);
            this.map.put(name, mc);
            return mc;
        }
    }

    private static final class Output extends SimpleJavaFileObject {
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Output(String name, Kind kind) {
            super(URI.create("memo:///" + name.replace('.', '/') + kind.extension), kind);
        }

        byte[] toByteArray() {
            return this.baos.toByteArray();
        }

        @Override
        public ByteArrayOutputStream openOutputStream() {
            return this.baos;
        }
    }

    private static final class Source extends SimpleJavaFileObject {
        private final String content;

        Source(String name, Kind kind, String content) {
            super(URI.create("memo:///" + name.replace('.', '/') + kind.extension), kind);
            this.content = content;
        }

        @Override
        public CharSequence getCharContent(boolean ignore) {
            return this.content;
        }
    }
}