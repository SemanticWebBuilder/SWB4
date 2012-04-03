/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.servlet.internal;

import com.hp.hpl.jena.sparql.syntax.Template;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Proxy;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.TemplateImp;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.lib.SWBBridgeResponse;
import org.semanticwb.portal.lib.SWBResponse;
import org.semanticwb.portal.util.SWBBridge;
import org.semanticwb.servlet.SWBHttpServletRequestWrapper;

/**
 *
 * @author javier.solis.g
 */
public class InternalProxy 
{

    Proxy proxy=null;
    
    public InternalProxy(Proxy proxy) 
    {
        this.proxy=proxy;
    }
    
    
    public boolean doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException
    {
        boolean ret=true;
        
        String uri = request.getRequestURI();
        //String cntx = request.getContextPath();
        //String path = uri.substring(cntx.length());
        String host = request.getServerName();       
        
        //System.out.println("host:"+host);
                
        SWBBridge bridge=new SWBBridge();
        //System.out.println("Remote URL:"+proxy.getUrl()+uri);
        bridge.setAcceptEncoding(false);
//            {
//                addHeaders(bridge,headers);
//                addCookies(bridge,cookies);
//            }
        //System.out.println(""+System.currentTimeMillis()+": "+"remoteURL:"+remoteURL);

        String loginurl=null;
        String replace=null;
        
        //String iniPath=base.getAttribute("iniPath","/");
        String encode="utf-8";
//        String basePath=base.getAttribute("basePath");
//        String replace=base.getAttribute("replace");
//        String direct=base.getAttribute("direct");
//        String userinsert=base.getAttribute("userinsert");
//        String loginurl=base.getAttribute("loginurl");
//        String initext=base.getAttribute("initext",null);
//        String endtext=base.getAttribute("endtext",null);
//        String headers=base.getAttribute("headers");
//        //String removePath=base.getAttribute("removePath","http://200.33.31.6;http://172.20.174.55");
//        String cookies=base.getAttribute("cookies");
//        String instance=base.getAttribute("instance",""+base.getId());
        

        SWBBridgeResponse res = bridge.bridge(proxy.getUrl()+uri, loginurl, request, response,null,true, proxy.getId());
        String code = "" + res.getResponseCode();
//
//        System.out.println("getContentType:"+res.getContentType());
//        System.out.println("getErrorMessage:"+res.getErrorMessage());
//        System.out.println("getResponseMessage:"+res.getResponseMessage());
//        System.out.println("getHeaderSize:"+res.getHeaderSize());
        //System.out.println("getResponseCode:"+res.getResponseCode());


//        System.out.println(""+System.currentTimeMillis()+": "+"getResponseCode:"+code);

        if (code.startsWith("3"))
        {
            String redi = res.getHeaderField("Location");
            if (redi != null)
            {
                redi=redi.replace(proxy.getUrl(), "");
                //System.out.println("redi:"+redi);
                response.sendRedirect(redi);
                return true;
            } else
            {
                //response.sendError(res.getResponseCode());
            }
        } else if (code.startsWith("2"))
        {
            String contentType = res.getContentType();
            //System.out.println("contentType:"+contentType);
            if(contentType!=null && contentType.indexOf("text")>-1)
            {
                String txt=SWBUtils.IO.readInputStream(res.getInputStream());
                //System.out.println("text1:"+txt);
                txt=txt.replace(proxy.getUrl(), "");
                //System.out.println("text2:"+txt.length());
                
                response.setHeader("Content-Length", null);
                response.getWriter().print(txt);
            }else
            {
                SWBUtils.IO.copyStream(res.getInputStream(), response.getOutputStream());
            }
        } else
        {
            //bloque de error duplicado
            String err = "Error:" + code + " " + res.getResponseMessage();

            String errm=res.getErrorMessage();
            
            //System.out.println("error:"+err);
            //System.out.println("errm:"+errm);
            
        }     
        return ret;
    }
    
    
    /**
     * Replace str.
     * 
     * @param replace the replace
     * @param content the content
     * @return the string
     */
    public String replaceStr(String replace, String content)
    {
        if(replace==null)return content;
        StringTokenizer st = new StringTokenizer(replace, ";,");
        while (st.hasMoreTokens())
        {
            String wp = st.nextToken();
            int i=wp.indexOf("|");
            if(i>-1)
            {
                String a1=wp.substring(0,i);
                String a2=wp.substring(i+1);
                content = content.replaceAll(a1, a2);
            }else
            {
                content = content.replaceAll(wp, "");
            }
        }    
        return content;
    }     
       
}
