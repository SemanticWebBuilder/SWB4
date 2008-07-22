/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmlrpc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 *
 * @author victor.lorenzana
 */
public interface AnnotatedMethod {
	/**
	 * @return the annotated class where the method is declared.
	 */
	AnnotatedClass getAnnotatedClass();
	
	/**
	 * @return the method wrapped by the annotated method.
	 */
	Method getMethod();
	
	/**
	 * @return all inherited and declared annotations of the method.
	 */
	Annotation[] getAllAnnotations();
	
	/**
	 * @param annotationClass of the annotation to find.
	 * @return the inherited or declared annotation of the specified class.
	 */
	Annotation getAnnotation(Class<?> annotationClass);
}

