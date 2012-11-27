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
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBAUserNotes.
 * 
 * @author Jorge Jiménez
 */
public class SWBAUserNotes extends GenericAdmResource
{

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, java.io.IOException {
        User user=paramReq.getUser();
        PrintWriter out = response.getWriter();
        SWBResourceURL url=paramReq.getActionUrl();
        out.println("<div id=\"notas\">");
        out.println("<h2>"+paramReq.getLocaleString("myNotes")+"</h2>");
        out.println("<div id=\"notasEstilo\">");
        out.println("<form name=\"notes\" method=\"post\" action=\""+url.toString()+"\" dojoType=\"dijit.form.Form\" class=\"swbform\">");
        out.println("<ul>");
        out.println("<li id=\"notaGuardar\"><a href=\"javascript:document.notes.submit();\" onClick=\"document.notes.submit();\" title=\""+paramReq.getLocaleString("save")+"\">Guardar</a></li>");
        out.println("</ul>");
        out.println("<textarea id=\"usrNotes\" name=\"usrNotes\" wrap=\"hard\">"+user.getProperty("admNotes","")+"</textarea>");
        //out.println("<button class=\"botonnotas\" dojoType='dijit.form.Button' type=\"submit\">"+paramReq.getLocaleString("save")+"</button>");
       // out.println("<input class=\"botonnotas\" type=\"submit\" value=\""+paramReq.getLocaleString("save")+"\"/>");
        //out.println("<input type=\"button\" onclick=\"postHtml('"+url+"'+'?usrNotes='+this.form.usrNotes.value,'usrNotes');\" value=\""+paramReq.getLocaleString("save")+"\"/>");
        out.println("</form>");
        out.println("<div>");
        out.println("</div>");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        User user=response.getUser();
        if(request.getParameter("usrNotes")!=null){
            user.setProperty("admNotes", request.getParameter("usrNotes"));
        }
    }
}
