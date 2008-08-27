/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.blogger;

import org.semantic.blogger.interfaces.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.semanticwb.xmlrpc.XMLRPCServlet;

/**
 *
 * @author victor.lorenzana
 */
public class MetaWebBlogServlet extends XMLRPCServlet
{   
    
    @Override
    public void init() throws ServletException
    {          
        addMappingType("blogger", MetaWeblogImp.class);
        addMappingType("metaWeblog", MetaWeblogImp.class);        
    }             
     
}
