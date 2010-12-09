/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest.consume;

import org.semanticwb.rest.util.HTTPMethod;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Element;

/**
 *
 * @author victor.lorenzana
 */
public interface MethodModule {
    public String getId();
    public HTTPMethod getHTTPMethod();
    public void addParameters(Element method);
    public void execute(HttpServletRequest request, HttpServletResponse response,String basePath) throws IOException;
}
