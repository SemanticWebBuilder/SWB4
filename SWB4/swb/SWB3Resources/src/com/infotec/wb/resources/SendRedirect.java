/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 


/*
 * SendRedirect.java
 *
 * Created on 6 de julio de 2006, 11:17 AM
 */

package com.infotec.wb.resources;

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
    
    /** Creates a new instance of SendRedirect */
    public SendRedirect()
    {
        //pedir llave en la administracion
        byte key[] = new java.math.BigInteger("05fe858d86df4b909a8c87cb8d9ad596", 16).toByteArray();
        encryptor = new org.semanticwb.util.Encryptor(key);
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String url=paramRequest.getResourceBase().getAttribute("url");
        if(url!=null)
        {
            //response.reset();
            //response.resetBuffer();
            response.sendRedirect(replaceTags(url,request,paramRequest));
            //throw new TemplateInterruptedException();
        }
    }
    
    public String replaceTags(String str, HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        //System.out.print("\nstr:"+str+"-->");
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
        //System.out.println(str);
        return str;
    }
}
