
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;


/**
 *
 * @author Javier Solis Gonzalez
 */
public class SendRedirect extends GenericAdmResource
{
    private org.semanticwb.util.Encryptor encryptor = null;
    
    public SendRedirect()
    {
        byte key[] = new java.math.BigInteger("05fe858d86df4b909a8c87cb8d9ad596", 16).toByteArray();
        encryptor = new org.semanticwb.util.Encryptor(key);
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String url=paramRequest.getResourceBase().getAttribute("url");
        if(url!=null)
        {
            response.sendRedirect(replaceTags(url,request,paramRequest));
        }
    }
    
    public String replaceTags(String str, HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        if(str==null || str.trim().length()==0){
            return null;
        }
        str=str.trim();
        //TODO: codificar cualquier atributo o texto
        Iterator it=SWBUtils.TEXT.findInterStr(str, "{encode(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{encode(\""+s+"\")}", encryptor.encode(replaceTags(s,request,paramsRequest)));
        }
        
        it=SWBUtils.TEXT.findInterStr(str, "{request.getParameter(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{request.getParameter(\""+s+"\")}", request.getParameter(replaceTags(s,request,paramsRequest)));
        }
        
        it=SWBUtils.TEXT.findInterStr(str, "{session.getAttribute(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{session.getAttribute(\""+s+"\")}", (String)request.getSession().getAttribute(replaceTags(s,request,paramsRequest)));
        }
        
        it=SWBUtils.TEXT.findInterStr(str, "{getEnv(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{getEnv(\""+s+"\")}", SWBPlatform.getEnv(replaceTags(s,request,paramsRequest)));
        }
        
        str=SWBUtils.TEXT.replaceAll(str, "{user.login}", paramsRequest.getUser().getLogin());
        str=SWBUtils.TEXT.replaceAll(str, "{user.email}", paramsRequest.getUser().getEmail());
        str=SWBUtils.TEXT.replaceAll(str, "{user.language}", paramsRequest.getUser().getLanguage());
        System.out.println("\n\n**********************url="+str);
        return str;
    }
}
