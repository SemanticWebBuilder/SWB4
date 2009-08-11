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
class AnnotatedMethodImpl implements AnnotatedMethod
{

    private final AnnotatedClass annotatedClass;
    private final Method method;
    private Map<Class<?>, Annotation> classToAnnotationMap = null;
    private Annotation[] annotations = null;

    AnnotatedMethodImpl(final AnnotatedClass annotatedClass, final Method method)
    {
        super();
        this.annotatedClass = annotatedClass;
        this.method = method;
    }

    private Map<Class<?>, Annotation> getAllAnnotationMap()
    {
        if ( classToAnnotationMap == null )
        {
            classToAnnotationMap = getAllAnnotationMapCalculated();
        }
        return classToAnnotationMap;
    }

    private Map<Class<?>, Annotation> getAllAnnotationMapCalculated()
    {
        HashMap<Class<?>, Annotation> result = new HashMap<Class<?>, Annotation>();

        final Class<?> superClass = getAnnotatedClass().getTheClass().getSuperclass();
        // Get the superclass's overriden method annotations
        if ( superClass != null )
        {
            fillAnnotationsForOneMethod(result, AnnotationManager.getAnnotatedClass(superClass).getAnnotatedMethod(getMethod().getName(), getMethod().getParameterTypes()));
        }

        // Get the superinterfaces' overriden method annotations
        for ( Class<?> c : getAnnotatedClass().getTheClass().getInterfaces() )
        {
            fillAnnotationsForOneMethod(result,
                    AnnotationManager.getAnnotatedClass(c).getAnnotatedMethod(getMethod().getName(),
                    getMethod().getParameterTypes()));
        }

        // Get its own annotations. They have preferece to inherited annotations.
        for ( Annotation annotation : getMethod().getDeclaredAnnotations() )
        {
            result.put(annotation.getClass().getInterfaces()[0], annotation);
        }

        return result;
    }

    /**
     * @param result is the map of classes to annotations to fill
     * @param annotatedMethod the method to get annotations. Does nothing
     * if the annotated method is null.
     */
    private void fillAnnotationsForOneMethod(final HashMap<Class<?>, Annotation> result,
            final AnnotatedMethod annotatedMethod)
    {
        if ( annotatedMethod == null )
        {
            return;
        }
        addAnnotations(result, annotatedMethod.getAllAnnotations());
    }

    /**
     * @param result map of classes to annotations
     * @param annotations to add to the result
     */
    private void addAnnotations(final HashMap<Class<?>, Annotation> result,
            final Annotation[] annotations)
    {
        for ( Annotation annotation : annotations )
        {
            if ( annotation == null )
            {
                continue;
            }
            result.put(annotation.getClass().getInterfaces()[0], annotation); /*It means to take the last annotation*/
//			if (result.containsKey(annotation.getClass().getInterfaces()[0]))
//				result.put(annotation.getClass().getInterfaces()[0], 
//						null /*it means not to take the annotation at all*/);
//			else
//				result.put(annotation.getClass().getInterfaces()[0], annotation);
        }
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
        final Collection<Annotation> values = getAllAnnotationMap().values();
        return values.toArray(new Annotation[0]);
    }

    public AnnotatedClass getAnnotatedClass()
    {
        return annotatedClass;
    }

    public Annotation getAnnotation(Class<?> annotationClass)
    {
        return getAllAnnotationMap().get(annotationClass);
    }

    public Method getMethod()
    {
        return method;
    }
}

