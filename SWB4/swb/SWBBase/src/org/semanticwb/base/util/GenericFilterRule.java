/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.base.util;

/**
 *
 * @author carlos.ramos
 */
public interface GenericFilterRule<T>
{
    public boolean filter(T obj);
}

