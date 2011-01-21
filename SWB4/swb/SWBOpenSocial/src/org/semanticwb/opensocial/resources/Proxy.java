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
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class Proxy {

    private static final Logger log = SWBUtils.getLogger(Proxy.class);
    private void writeContent(URL url, HttpServletResponse response) throws IOException
    {
        try
        {
        OutputStream out=response.getOutputStream();
        HttpURLConnection con=(HttpURLConnection) url.openConnection();
        String content_type=con.getContentType();
        if(content_type!=null)
        {
            response.setContentType(content_type);
        }
        InputStream in=con.getInputStream();
        byte[] buffer=new byte[1024*8];
        int read=in.read(buffer);
        while(read!=-1)
        {
            out.write(buffer, 0, read);
            read=in.read(buffer);
        }
        response.setStatus(con.getResponseCode());
        in.close();
        }
        catch(IOException e)
        {
            log.debug(e);
            throw e;            
        }
        catch(Exception e)
        {
            log.debug(e);;
        }
    }
    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        log.debug("Proxy request.getQueryString(): "+request.getQueryString());
        String url = request.getParameter("url");        
        if(url!=null && !"".equals(url))
        {
            writeContent(new URL(url), response);
        }
    }
}
