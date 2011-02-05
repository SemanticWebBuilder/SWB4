/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest.publish;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.rest.util.HTTPMethod;
import org.w3c.dom.Element;

/**
 *
 * @author victor.lorenzana
 */
public interface Representation {
    public RepresentationMediaType getRepresentationMediaType();
    public void addWADL(Element param,Element request,Element method, HTTPMethod HTTPMethod, String elementType);
    public void addIncludes(Element grammars, HttpServletRequest servletRequest);
}
