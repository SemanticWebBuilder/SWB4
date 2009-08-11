/**  
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
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.xmlrpc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author victor.lorenzana
 */
class AnnotatedClassImpl implements AnnotatedClass
{

    private final Class<?> theClass;
    private Map<Class<?>, Annotation> classToAnnotationMap = null;
    private Map<Method, AnnotatedMethod> methodToAnnotatedMap = null;
    private Annotation[] annotations = null;
    private AnnotatedMethod[] annotatedMethods = null;

    AnnotatedClassImpl(Class<?> theClass)
    {
        super();
        this.theClass = theClass;
    }

    /**
     * @return the cached map of classes to annotations
     */
    private Map<Class<?>, Annotation> getAllAnnotationMap()
    {
        if ( classToAnnotationMap == null )
        {
            classToAnnotationMap = getAllAnnotationMapCalculated();
        }
        return classToAnnotationMap;
    }

    /**
     * @return the calculated map of classes to annotations
     */
    private Map<Class<?>, Annotation> getAllAnnotationMapCalculated()
    {
        HashMap<Class<?>, Annotation> result = new HashMap<Class<?>, Annotation>();

        final Class<?> superClass = getTheClass().getSuperclass();
        // Get the superclass's annotations
        if ( superClass != null )
        {
            fillAnnotationsForOneClass(result, superClass);
        }

        // Get the superinterfaces' annotations
        for ( Class<?> c : getTheClass().getInterfaces() )
        {
            fillAnnotationsForOneClass(result, c);
        }

        // Get its own annotations. They have preferece to inherited annotations.
        for ( Annotation annotation : getTheClass().getDeclaredAnnotations() )
        {
            result.put(annotation.getClass().getInterfaces()[0], annotation);
        }

        return result;
    }

    /**
     * @param result map of classes to annotations
     * @param baseClass is the superclass or one of the superinterfaces. 
     */
    private void fillAnnotationsForOneClass(HashMap<Class<?>, Annotation> result,
            Class<?> baseClass)
    {
        addAnnotations(result, AnnotationManager.getAnnotatedClass(baseClass).getAllAnnotations());
    }

    /**
     * @param result map of classes to annotations
     * @param annotations to add to the result
     */
    private void addAnnotations(HashMap<Class<?>, Annotation> result,
            Annotation[] annotations)
    {
        for ( Annotation annotation : annotations )
        {
            if ( annotation == null )
            {
                continue;
            }
            if ( result.containsKey(annotation.getClass().getInterfaces()[0]) )
            {
                result.put(annotation.getClass().getInterfaces()[0],
                        null /*it means not to take the annotation at all*/);
            }
            else
            {
                result.put(annotation.getClass().getInterfaces()[0], annotation);
            }
        }
    }

    public Class<?> getTheClass()
    {
        return theClass;
    }

    public Annotation[] getAllAnnotations()
    {
        if ( annotations == null )
        {
            annotations = getAllAnnotationsCalculated();
        }
        return annotations;
    }

    private Annotation[] getAllAnnotationsCalculated()
    {
        return getAllAnnotationMap().values().toArray(new Annotation[0]);
    }

    public Annotation getAnnotation(Class<?> annotationClass)
    {
        return getAllAnnotationMap().get(annotationClass);
    }

    private Map<Method, AnnotatedMethod> getMethodMap()
    {
        if ( methodToAnnotatedMap == null )
        {
            methodToAnnotatedMap = getMethodMapCalculated();
        }
        return methodToAnnotatedMap;
    }

    private Map<Method, AnnotatedMethod> getMethodMapCalculated()
    {
        HashMap<Method, AnnotatedMethod> result = new HashMap<Method, AnnotatedMethod>();

        for ( Method method : getTheClass().getMethods() )
        {
            result.put(method, new AnnotatedMethodImpl(this, method));
        }

        return result;
    }

    public AnnotatedMethod getAnnotatedMethod(Method method)
    {
        return getMethodMap().get(method);
    }

    public AnnotatedMethod[] getAnnotatedMethods()
    {
        if ( annotatedMethods == null )
        {
            annotatedMethods = getAnnotatedMethodsCalculated();
        }
        return annotatedMethods;
    }

    private AnnotatedMethod[] getAnnotatedMethodsCalculated()
    {
        final Collection<AnnotatedMethod> values = getMethodMap().values();
        return values.toArray(new AnnotatedMethod[0]);
    }

    public AnnotatedMethod getAnnotatedMethod(String name, Class<?>[] parameterType)
    {
        AnnotatedMethod annotatedMethod=null;
        try
        {
            annotatedMethod=getAnnotatedMethod(getTheClass().getMethod(name, parameterType));
        }
        catch ( SecurityException e )
        {
            throw new RuntimeException(e);
        }
        catch ( NoSuchMethodException e )
        {
            annotatedMethod=null;
        }
        return annotatedMethod;
    }
}

