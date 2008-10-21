/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * SendRedirect.java
 *
 * Created on 6 de julio de 2006, 11:17 AM
 */

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
        
        str=SWBUtils.TEXT.replaceAll(str, "{user.login}", paramsRequest.getUser().getUsrLogin());
        str=SWBUtils.TEXT.replaceAll(str, "{user.email}", paramsRequest.getUser().getUsrEmail());
        str=SWBUtils.TEXT.replaceAll(str, "{user.language}", paramsRequest.getUser().getLanguage());
        //System.out.println(str);
        return str;
    }
}
