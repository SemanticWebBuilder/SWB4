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

public class SWBComments extends org.semanticwb.portal.resources.sem.base.SWBCommentsBase {
    private static final String Action_ADD = "add";
    private static final int secureCodeLength = 7;

    public SWBComments() {
    }

    public SWBComments(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

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

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        super.processRequest(request, response, paramRequest);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        StringBuilder ret = new StringBuilder();
        SWBResourceURL rUrl = paramRequest.getActionUrl();
        rUrl.setAction(Action_ADD);

        /*String name = request.getParameter("cmnt_name")==null?"":request.getParameter("cmnt_name");
        String email = request.getParameter("cmnt_email")==null?"":request.getParameter("cmnt_email");*/
        String comment = request.getParameter("cmnt_comment")==null?"":request.getParameter("cmnt_comment");
        /*String securCodeSent = request.getParameter("cmnt_seccode");
        String securCodeCreated = (String)request.getSession(true).getAttribute("cs");*/
        
        ret.append("<script type=\"text/javascript\">");

        ret.append("function isEmpty(objid) { ");
        ret.append("    var obj = dojo.byId(objid); ");
        ret.append("    if (obj==null || obj.value=='' || !isNaN(obj.value) || obj.value.charAt(0) == ' ') { ");
        ret.append("        return true; ");
        ret.append("    }else { ");
        ret.append("        return false; ");
        ret.append("    } ");
        ret.append("} ");


        ret.append("function isValidEmail(strEmail) { ");
        ret.append("    emailRegExp = /^[^@]+@[^@]+.[a-z]{2,}$/i; ");
        ret.append("    if(strEmail.search(emailRegExp) == -1) { ");
        ret.append("        return false; ");
        ret.append("    } ");
        ret.append("    return true; ");
        ret.append("} ");

        ret.append("function doApply() { ");
        ret.append("    var msgs = new Array();");
        ret.append("    if(isEmpty('cmnt_comment')) { ");
        ret.append("        msgs.push('Tienes que ingresar un comentario u opinión.'); ");
        ret.append("    } ");
        ret.append("    if(isEmpty('cmnt_seccode')) { ");
        ret.append("        msgs.push('Para poder agregar tu comentario es necesario que ingreses el código de la imagen.\\nEn caso de no ser claro puedes cambiarlo haciendo clic en <<Cambiar imagen>>.'); ");
        ret.append("    }");

        ret.append("    if(msgs.length > 0) { ");
        ret.append("          alert(msgs.join('\\n'));");
        ret.append("    }else { ");
        ret.append("        dojo.byId('cmnt_send').form.submit(); ");
        ret.append("    } ");
        ret.append("} ");

        ret.append("function changeSecureCodeImage(imgid) { ");
        ret.append("    var img = dojo.byId(imgid); ");
        ret.append("    if(img) { ");
        ret.append("        var rn = Math.floor(Math.random()*99999); ");
        ret.append("        img.src = '"+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp?nc='+rn; ");
        ret.append("    } ");
        ret.append("} ");

        ret.append("</script> ");
        
        ret.append(renderListComments(paramRequest));
        User user = paramRequest.getUser();
        ret.append("<div class=\"swb-comentario-sem\">");
        ret.append("<h2>"+paramRequest.getLocaleString("add")+"</h2>");
        ret.append("<form name=\"cmnt\" id=\"cmnt\" action=\""+rUrl+"\" method=\"post\">\n");
        /*if(user.isSigned()) {
            ret.append("<div class=\"swb-comenta-nombre\">");
            ret.append("  <label for=\"cmnt_name\">"+paramRequest.getLocaleString("nameLabel")+":</label>");
            ret.append("  <input type=\"text\" id=\"cmnt_name\" name=\"cmnt_name\" value=\""+user.getFullName()+"\" size=\"34\" />");
            ret.append("</div>");
            ret.append("<div class=\"swb-comenta-correo\">");
            ret.append("  <label for=\"cmnt_email\">"+paramRequest.getLocaleString("emailLabel")+":</label>");
            ret.append("  <input type=\"text\" id=\"cmnt_email\" name=\"cmnt_email\" value=\""+user.getEmail()+"\" size=\"34\" />");
            ret.append("</div>");
        }*/
        ret.append("<div class=\"swb-comentario-sem-comenta\">");
        ret.append("  <label for=\"comment\">"+paramRequest.getLocaleString("comment")+":</label>");
        ret.append("  <textarea id=\"cmnt_comment\" name=\"cmnt_comment\" cols=\"32\" rows=\"3\" >"+comment+"</textarea>");
        ret.append("</div>");
        ret.append("<div class=\"swb-comentario-sem-imagen\">");
        ret.append("  <img src=\""+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp\" id=\"imgseccode\" width=\"155\" height=\"65\" /><br/>");
        ret.append("  <a href=\"#\" onclick=\"changeSecureCodeImage('imgseccode');\">"+paramRequest.getLocaleString("anotherCode")+"</a>");
        ret.append("</div>");
        ret.append("<div class=\"swb-comentario-sem-captcha\">");
        ret.append("  <label for=\"cmnt_seccode\">El texto de la imagen es:</label>");
        ret.append("  <input type=\"text\" id=\"cmnt_seccode\" name=\"cmnt_seccode\" />");
        ret.append("</div>");
        ret.append("<div class=\"swb-comentario-sem-boton\">");
        ret.append("  <input type=\"button\" id=\"cmnt_send\" name=\"cmnt_send\" value=\""+paramRequest.getLocaleString("publish")+"\" onClick=\"doApply();\" />");
        ret.append("</div>");
        ret.append("</form>");
        ret.append("</div>");
        out.println(ret.toString());
        out.flush();
        out.close();
    }

    private String renderListComments(SWBParamRequest paramRequest) throws SWBResourceException{
        StringBuilder script = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy | HH:mm");
        //long ordinal = SWBUtils.Collections.sizeOf(listComments());
        //int ordinal = 1;

        Iterator<Comment> itComments = SWBComparator.sortByCreated(listComments(),false);
        script.append("<div class=\"swb-comentario-sem-lista\">");
        script.append("<h1>comentarios</h1>");
        if(itComments.hasNext()) {
            script.append("<ul>");
        }
        while(itComments.hasNext()) {
            Comment comment = itComments.next();
            script.append("<li><span>"+(comment.getCreator()==null?"An&oacute;nimo":comment.getCreator().getFullName())+"</span> "+sdf.format(comment.getCreated())+"<p>"+comment.getComment()+"</p></li>");
        }
        if(itComments.hasNext()) {
            script.append("</ul>");
        }
        script.append("</div>");
        return script.toString();
    }



}
