/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.script.util;

import java.io.ByteArrayOutputStream;
import java.net.URI;
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

/**
 * @author Sergey A. Malenkov
 */
public final class MemoryClassLoader extends ClassLoader {


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

    @Override
    protected synchronized Class loadClass(String name, boolean resolve)
            throws ClassNotFoundException
    {
        // First, check if the class has already been loaded
        //System.out.println("Enter loadClass:"+name);
        Class c = findLoadedClass(name);
        if (c == null)
        {
            c=getParentClass(name);
        }
        if (resolve)
        {
            resolveClass(c);
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
        try
        {
            Thread.currentThread().getContextClassLoader().loadClass("org.semanticwb.process.model.ProcessObject");
        }
        catch(ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }
        JavaCompiler.CompilationTask task=this.compiler.getTask(null, this.manager, null, null, null, list);
        task.call();
    }    


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        synchronized (this.manager) {
            Output mc = this.manager.map.remove(name);
            if (mc != null) {
                byte[] array = mc.toByteArray();
                return defineClass(name, array, 0, array.length);
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