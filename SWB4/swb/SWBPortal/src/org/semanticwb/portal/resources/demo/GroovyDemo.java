/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources.demo;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author serch
 */
public class GroovyDemo extends GenericResource
{

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Binding binding = new Binding();

        binding.setVariable("out", response.getWriter());
        binding.setVariable("req", request);
        binding.setVariable("param",paramRequest);
        GroovyShell shell = new GroovyShell(binding);

        Object value = shell.evaluate("out.println 'Hello World! {$req.getParameter(\"name\")'");

    }
}
