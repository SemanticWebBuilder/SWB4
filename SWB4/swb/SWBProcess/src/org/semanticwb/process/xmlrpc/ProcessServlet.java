/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.xmlrpc;

import javax.servlet.ServletException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.xmlrpc.XMLRPCServlet;

/**
 *
 * @author victor.lorenzana
 */
public class ProcessServlet extends XMLRPCServlet
{

    static Logger log = SWBUtils.getLogger(ProcessServlet.class);

    @Override
    public void init() throws ServletException
    {
        log.event("Adding mappingType RPCProcess to RPC...");
        
    }

    public boolean isAuthenticate(String pUserName, String pPassword)
    {
        return true;
    }
}
