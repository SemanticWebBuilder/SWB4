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
package org.semanticwb.portal.resources.sem;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.http.*;

import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBComments.
 */
public class SWBComments extends org.semanticwb.portal.resources.sem.base.SWBCommentsBase {
    
    /** The Constant Action_ADD. */
    private static final String Action_ADD = "add";
    
    /** The Constant secureCodeLength. */
    private static final int secureCodeLength = 7;

    /**
     * Instantiates a new sWB comments.
     */
    public SWBComments() {
    }

    /**
     * Instantiates a new sWB comments.
     * 
     * @param base the base
     */
    public SWBComments(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericSemResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        
        if(action.equalsIgnoreCase(Action_ADD)) {
            String securCodeSent = request.getParameter("cmnt_seccode");
            String securCodeCreated = (String)request.getSession(true).getAttribute("cs");
            if(securCodeCreated!=null && securCodeCreated.equalsIgnoreCase(securCodeSent)) {
                WebSite model = response.getWebPage().getWebSite();
                Comment comment = Comment.createComment(model);
                comment.setComment(request.getParameter("cmnt_comment"));
                addComment(comment);
                request.getSession(true).removeAttribute("cs");
            }else {
                Enumeration e = request.getParameterNames();
                while(e.hasMoreElements()){
                    String key = (String)e.nextElement();
                    response.setRenderParameter(key, request.getParameter(key));
                }
            }
        }else {
            super.processAction(request, response);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        super.processRequest(request, response, paramRequest);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        SWBResourceURL rUrl = paramRequest.getActionUrl();
        rUrl.setAction(Action_ADD);

        /*String name = request.getParameter("cmnt_name")==null?"":request.getParameter("cmnt_name");
        String email = request.getParameter("cmnt_email")==null?"":request.getParameter("cmnt_email");*/
        String comment = request.getParameter("cmnt_comment")==null?"":request.getParameter("cmnt_comment");
        /*String securCodeSent = request.getParameter("cmnt_seccode");
        String securCodeCreated = (String)request.getSession(true).getAttribute("cs");*/
        
        out.println("<script type=\"text/javascript\">");
        out.println("<!--");
        out.println("function isEmpty(objid) { ");
        out.println("    var obj = dojo.byId(objid); ");
        out.println("    if (obj==null || obj.value=='' || !isNaN(obj.value) || obj.value.charAt(0) == ' ') { ");
        out.println("        return true; ");
        out.println("    }else { ");
        out.println("        return false; ");
        out.println("    } ");
        out.println("} ");


        out.println("function isValidEmail(strEmail) { ");
        out.println("    emailRegExp = /^[^@]+@[^@]+.[a-z]{2,}$/i; ");
        out.println("    if(strEmail.search(emailRegExp) == -1) { ");
        out.println("        return false; ");
        out.println("    } ");
        out.println("    return true; ");
        out.println("} ");

        out.println("function doApply() { ");
        out.println("    var msgs = new Array();");
        out.println("    if(isEmpty('cmnt_comment')) { ");
        out.println("        msgs.push('Tienes que ingresar un comentario u opinión.'); ");
        out.println("    } ");
        out.println("    if(isEmpty('cmnt_seccode')) { ");
        out.println("        msgs.push('Para poder agregar tu comentario es necesario que ingreses el código de la imagen.\\nEn caso de no ser claro puedes cambiarlo haciendo clic en <<Cambiar imagen>>.'); ");
        out.println("    }");

        out.println("    if(msgs.length > 0) { ");
        out.println("          alert(msgs.join('\\n'));");
        out.println("    }else { ");
        out.println("        dojo.byId('cmnt_send').form.submit(); ");
        out.println("    } ");
        out.println("} ");

        out.println("function changeSecureCodeImage(imgid) { ");
        out.println("    var img = dojo.byId(imgid); ");
        out.println("    if(img) { ");
        out.println("        var rn = Math.floor(Math.random()*99999); ");
        out.println("        img.src = '"+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp?nc='+rn; ");
        out.println("    } ");
        out.println("} ");
        out.println("-->");
        out.println("</script> ");
        
        out.println(renderListComments(paramRequest));
        out.println("<div class=\"swb-comentario-sem\">");
        out.println("<h2>"+paramRequest.getLocaleString("add")+"</h2>");
        out.println("<form name=\"cmnt\" id=\"cmnt\" action=\""+rUrl+"\" method=\"post\">\n");
        /*if(user.isSigned()) {
            out.println("<div class=\"swb-comenta-nombre\">");
            out.println("  <label for=\"cmnt_name\">"+paramRequest.getLocaleString("nameLabel")+":</label>");
            out.println("  <input type=\"text\" id=\"cmnt_name\" name=\"cmnt_name\" value=\""+user.getFullName()+"\" size=\"34\" />");
            out.println("</div>");
            out.println("<div class=\"swb-comenta-correo\">");
            out.println("  <label for=\"cmnt_email\">"+paramRequest.getLocaleString("emailLabel")+":</label>");
            out.println("  <input type=\"text\" id=\"cmnt_email\" name=\"cmnt_email\" value=\""+user.getEmail()+"\" size=\"34\" />");
            out.println("</div>");
        }*/
        out.println("<div class=\"swb-comentario-sem-comenta\">");
        out.println("  <label for=\"comment\">"+paramRequest.getLocaleString("comment")+":</label>");
        out.println("  <textarea id=\"cmnt_comment\" name=\"cmnt_comment\" cols=\"32\" rows=\"3\" >"+comment+"</textarea>");
        out.println("</div>");
        out.println("<div class=\"swb-comentario-sem-imagen\">");
        out.println("  <img src=\""+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp\" id=\"imgseccode\" width=\"155\" height=\"65\" /><br/>");
        out.println("  <a href=\"#\" onclick=\"changeSecureCodeImage('imgseccode');\">"+paramRequest.getLocaleString("anotherCode")+"</a>");
        out.println("</div>");
        out.println("<div class=\"swb-comentario-sem-captcha\">");
        out.println("  <label for=\"cmnt_seccode\">El texto de la imagen es:</label>");
        out.println("  <input type=\"text\" id=\"cmnt_seccode\" name=\"cmnt_seccode\" />");
        out.println("</div>");
        out.println("<div class=\"swb-comentario-sem-boton\">");
        out.println("  <input type=\"button\" id=\"cmnt_send\" name=\"cmnt_send\" value=\""+paramRequest.getLocaleString("publish")+"\" onclick=\"doApply();\" />");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        
        out.flush();
        out.close();
    }

    /**
     * Render list comments.
     * 
     * @param paramRequest the param request
     * @return the string
     * @throws SWBResourceException the sWB resource exception
     */
    private String renderListComments(SWBParamRequest paramRequest) {
        StringBuilder html = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy | HH:mm");
        //long ordinal = SWBUtils.Collections.sizeOf(listComments());
        //int ordinal = 1;
        
        Iterator<Comment> itComments = SWBComparator.sortByCreated(listComments(),false);
        html.append("<div class=\"swb-comentario-sem-lista\">");
        html.append("<h2>comentarios</h2>");
        if(itComments.hasNext()) {
            html.append("<ul>");
        }
        while(itComments.hasNext()) {
            Comment comment = itComments.next();
            html.append("<li><span>"+(comment.getCreator()==null?"An&oacute;nimo":comment.getCreator().getFullName())+"</span> "+sdf.format(comment.getCreated())+"<p>"+comment.getComment()+"</p></li>");
        }
        if(itComments.hasNext()) {
            html.append("</ul>");
        }
        html.append("</div>");
        return html.toString();
    }



}
