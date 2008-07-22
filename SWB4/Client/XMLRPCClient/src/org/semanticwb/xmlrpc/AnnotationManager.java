/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmlrpc;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author victor.lorenzana
 */
class AnnotationManager {
private static Map<Class <?>, AnnotatedClass> classToAnnotatedMap =
		new HashMap<Class<?>, AnnotatedClass>();
	
	/**
	 * @param theClass to wrap.
	 * @return the annotated class wrapping the specified one.
	 */
	public static AnnotatedClass getAnnotatedClass(Class<?> theClass){
		AnnotatedClass annotatedClass = classToAnnotatedMap.get(theClass);
		if (annotatedClass == null){
			annotatedClass = new AnnotatedClassImpl(theClass);
			classToAnnotatedMap.put(theClass, annotatedClass);
		}
		return annotatedClass;
	}

}
