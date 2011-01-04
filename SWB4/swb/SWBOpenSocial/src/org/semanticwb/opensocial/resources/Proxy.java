/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.opensocial.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class Proxy {

    private void writeContent(URL url, HttpServletResponse response) throws IOException
    {
        OutputStream out=response.getOutputStream();
        HttpURLConnection con=(HttpURLConnection) url.openConnection();
        String content_type=con.getContentType();
        if(content_type!=null)
        {
            response.setContentType(content_type);
        }
        InputStream in=con.getInputStream();
        byte[] buffer=new byte[1028];
        int read=in.read(buffer);
        while(read!=-1)
        {
            out.write(buffer, 0, read);
            read=in.read(buffer);
        }
        in.close();
    }
    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        System.out.println("Proxy request"+request.getQueryString());
        String url = request.getParameter("url");        
        if(url!=null && !"".equals(url))
        {
            writeContent(new URL(url), response);
        }
    }
}
