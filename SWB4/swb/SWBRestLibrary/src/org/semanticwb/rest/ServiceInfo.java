/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.util.Set;

/**
 *
 * @author victor.lorenzana
 */
public interface ServiceInfo {

    public String getName();

    public Set<ParameterInfo> getParameters();
}
