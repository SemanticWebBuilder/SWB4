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
interface AnnotatedClass {
	/**
	 * @return the class which inherited annotations are calculated. 
	 */
	Class<?> getTheClass();
	
	/**
	 * @return all the inherited or declared annotations.
	 */
	Annotation[] getAllAnnotations();
	
	/**
	 * @param annotationClass to find an annotation.
	 * @return the inherited or declared annotation of the specified class. 
	 */
	Annotation getAnnotation(Class<?> annotationClass);
	
	/**
	 * @return all the annotated methods of the class (empty if none). 
	 */
	AnnotatedMethod[] getAnnotatedMethods();

	/**
	 * @param name of the method to find.
	 * @param parameterType of the method to find.
	 * @return the public method having the specified name and signature (null if 
	 * the method is not declared in the class).
	 */
	AnnotatedMethod getAnnotatedMethod(String name, Class<?>[] parameterType);

	/**
	 * @param public method of the annotated class. 
	 * @return the annotation wrapping for the method (null if the method
	 * is not declared in the class)
	 */
	AnnotatedMethod getAnnotatedMethod(Method method);
}

