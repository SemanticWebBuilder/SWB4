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
package org.semanticwb.portal;


import java.io.File;

import java.net.URL;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import sun.misc.Resource;
import sun.misc.URLClassPath;

// TODO: Auto-generated Javadoc
/**
 * Clase que extiende de ClassLoader utilizada para la carga dinamica de recursos.
 * @author Javier Solis Gonzalez
 */
public class SWBClassLoader extends ClassLoader
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBClassLoader.class);
    
    /** The ucp. */
    URLClassPath ucp = null;
    
    /** The filter. */
    private String filter=null;

    /**
     * Creates a new class loader using the specified parent class loader
     * for delegation.
     * <p>
     * If there is a security manager, its <code>checkCreateClassLoader</code>
     * method is called. This may result in a security exception.
     *
     * @param parent the parent class loader
     *
     * @throws  java.lang.SecurityException if a security manager exists and its
     * <code>checkCreateClassLoader</code> method doesn't allow creation of a
     * new class loader.
     * @see       java.lang.SecurityException
     * @see       java.lang.SecurityManager#checkCreateClassLoader()
     * @since     1.2
     */
    public SWBClassLoader(ClassLoader parent)
    {
        super(parent);
        //System.out.println("Create WBClassLoader");
        setClassPath();
    }

    /**
     * Creates a new class loader using the <code>ClassLoader</code>
     * returned by the method <code>getSystemClassLoader()</code> as the
     * parent class loader.
     * <p>
     * If there is a security manager, its <code>checkCreateClassLoader</code>
     * method is called. This may result in a security exception.
     *
     * @throws  java.lang.SecurityException
     *    if a security manager exists and its <code>checkCreateClassLoader</code>
     *    method doesn't allow creation of a new class loader.
     *
     * @see       java.lang.SecurityException
     * @see       java.lang.SecurityManager#checkCreateClassLoader()
     */
    public SWBClassLoader()
    {
        super();
        setClassPath();
    }

    /**
     * Sets the class path.
     */
    private void setClassPath()
    {
        try
        {
            String path = SWBPlatform.getEnv("swb/resPath", "/WEB-INF/classes/");
            File dir = new File(SWBUtils.getApplicationPath() + path);
            URL url = dir.toURL();
            URL urls[] = new URL[]{url};
            ucp = new URLClassPath(urls);
            //System.out.println(url);
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    /**
     * Adds the url.
     * 
     * @param url the url
     */
    public void addURL(URL url)
    {
        ucp.addURL(url);
    }


    /**
     * Finds and loads the class with the specified name from the URL search
     * path. Any URLs referring to JAR files are loaded and opened as needed
     * until the class is found.
     * 
     * @param name the name of the class
     * @return the resulting class
     * @throws ClassNotFoundException the class not found exception
     * @exception java.lang.ClassNotFoundException if the class could not be found
     */
    @Override
    protected Class findClass(final String name)
            throws ClassNotFoundException
    {
        try
        {
            String path = name.replace('.', '/').concat(".class");
            Resource res = ucp.getResource(path, false);
            byte[] buf = res.getBytes();
            Class rtn = defineClass(name, buf, 0, buf.length);
            //System.out.println("findClass:"+name);
            return rtn;
        } catch (Exception e)
        {
            throw new ClassNotFoundException();
        }
    }

    /**
     * Load class data.
     * 
     * @param name the name
     * @return the byte[]
     */
    private byte[] loadClassData(String name)
    {
        byte[] ret = null;
        return ret;
    }

    /**
     * Loads the class with the specified name.  The default implementation of
     * this method searches for classes in the following order:<p>
     * 
     * <ol>
     * <li> Call {@link #findLoadedClass(java.lang.String)} to check if the class has
     * already been loaded. <p>
     * <li> Call the <code>loadClass</code> method on the parent class
     * loader.  If the parent is <code>null</code> the class loader
     * built-in to the virtual machine is used, instead. <p>
     * <li> Call the {@link #findClass(java.lang.String)} method to find the class. <p>
     * </ol>
     * 
     * If the class was found using the above steps, and the
     * <code>resolve</code> flag is true, this method will then call the
     * 
     * @param name the name
     * @param resolve the resolve
     * @return the class
     * @throws ClassNotFoundException the class not found exception
     * {@link #resolveClass(java.lang.Class)} method on the resulting class object.
     * <p>
     * From the Java 2 SDK, v1.2, subclasses of ClassLoader are
     * encouraged to override
     * {@link #findClass(java.lang.String)}, rather than this method.<p>
     * @return	  the resulting <code>Class</code> object
     * @exception java.lang.ClassNotFoundException if the class could not be found
     */
    @Override
    protected synchronized Class loadClass(String name, boolean resolve)
            throws ClassNotFoundException
    {
        // First, check if the class has already been loaded
        //System.out.println("Enter loadClass:"+name);
        Class c = findLoadedClass(name);
        if (c == null)
        {
            try
            {
                if(filter!=null)
                {
                    if(name.startsWith(filter))
                    {
                        c = findClass(name);
                    }else
                    {
                         throw new ClassNotFoundException();
                    }
                }else
                {
                    c = findClass(name);
                }
            } catch (ClassNotFoundException e)
            {
                //System.out.println("parent class:"+name);
                c=getParentClass(name);
            }
        }
        if (resolve)
        {
            resolveClass(c);
        }
        return c;
    }
    
    /**
     * Gets the parent class.
     * 
     * @param name the name
     * @return the parent class
     * @throws ClassNotFoundException the class not found exception
     */
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

    /**
     * Getter for property filter.
     * @return Value of property filter.
     */
    public java.lang.String getFilterClass()
    {
        return filter;
    }
    
    /**
     * Setter for property filter.
     * @param filter New value of property filter.
     */
    public void setFilterClass(java.lang.String filter)
    {
        this.filter = filter;
    }
    
}
