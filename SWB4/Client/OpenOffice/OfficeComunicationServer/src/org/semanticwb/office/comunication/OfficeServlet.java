/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.comunication;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.semanticwb.xmlrpc.XMLRPCServlet;
import static org.semanticwb.office.comunication.Base64.*;

/**
 *
 * @author victor.lorenzana
 */
public abstract class OfficeServlet extends XMLRPCServlet
{
    
    private static String REALM = "Secure Area";
    private static String PREFIX_BASIC = "Basic ";    

    @Override
    public void init() throws ServletException
    {        
        addMappingType("OfficeDocument", OfficeDocument.class);
        addMappingType("OfficeApplication", OfficeApplication.class);                
    }  
    
    private static String getPassword(String userpassDecoded) throws IOException
    {
        String password = "";
        String[] values = userpassDecoded.split(":");
        password = values[1];
        return password;
    }

    private static String getUserName(String userpassDecoded) throws IOException
    {
        String userName = "";
        String[] values = userpassDecoded.split(":");
        userName = values[0];
        return userName;
    }

    private void checkSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        try
        {
            String pUserName = null;
            String pPassword = null;
            String authorization = request.getHeader("Authorization");
            if ( authorization == null || authorization.equals("") )
            {
                response.setHeader("WWW-Authenticate", PREFIX_BASIC + "realm=\"" + REALM + "\"");
                response.setStatus(response.SC_UNAUTHORIZED);
                return;
            }
            else
            {
                if ( authorization.startsWith(PREFIX_BASIC) )
                {
                    String userpassEncoded = authorization.substring(6);
                    String userpassDecoded = new String(decode(userpassEncoded));
                    pUserName = getUserName(userpassDecoded);
                    pPassword = getPassword(userpassDecoded);
                    if ( !this.isAuthenticate(pUserName, pPassword) )
                    {
                        response.sendError(response.SC_FORBIDDEN);
                    }
                }
                else
                {
                    response.sendError(response.SC_FORBIDDEN);
                }
            }
        }
        catch ( IOException ioe )
        {
            throw ioe;
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        checkSecurity(request, response);
        super.doPost(request, response);

    }
    public abstract boolean isAuthenticate(String pUserName, String pPassword);

    
}
