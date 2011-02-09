/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest.consume;

import java.util.List;
import org.w3c.dom.Document;

/**
 *
 * @author victor.lorenzana
 */
public interface RepresentationRequest extends Representation {

    public RepresentationResponse request(List<ParameterValue> values) throws ExecutionRestException,RestException;
    public RepresentationResponse request(List<ParameterValue> values,Document request) throws ExecutionRestException,RestException;
    public void checkParameters(List<ParameterValue> values) throws RestException;
    public Parameter[] getRequiredParameters();
    public Parameter[] getOptionalParameters();
    public Parameter[] getAllParameters();
    public Method getMethod();
    public void setMethod(Method method);
    public void addParameter(Parameter parameter);
}
