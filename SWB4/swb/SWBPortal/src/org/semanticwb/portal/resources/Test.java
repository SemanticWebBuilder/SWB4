/*
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
 */
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * The Class Test.
 * 
 * @author Jei
 */
public class Test extends GenericResource
{
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        PrintWriter out=response.getWriter();
        out.println("Hola Mundo<br>");
        out.println("id:"+paramRequest.getResourceBase().getId());
        out.println("<a href='"+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT)+"'>edit</a><br>");
        out.println("<a href='"+paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT)+"'>Direct</a><br>");
        out.println("<br>");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doEdit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        PrintWriter out=response.getWriter();
        out.println("Hola Edit<br>");
        out.println("id:"+paramRequest.getResourceBase().getId());
        out.println("<a href='"+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)+"'>View</a><br>");
        out.println("<br>");
    }
    
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doAdmin(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        
        PrintWriter out=response.getWriter();
        /*
        out.println("Hola Mundo Jorge<br>");
        out.println("id:"+paramRequest.getResourceBase().getId());
        out.println("<a href='"+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT)+"'>edit</a><br>");
        out.println("<a href='"+paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT)+"'>Direct</a><br>");
        out.println("<br>");
        **/
         
        String str="Updates to the Original Java Specification Request (JSR)" +
"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003";             
    out.println(str);
}
    
}
