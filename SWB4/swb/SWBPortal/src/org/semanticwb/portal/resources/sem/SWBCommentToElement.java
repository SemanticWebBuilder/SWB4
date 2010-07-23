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
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.*;


// TODO: Auto-generated Javadoc
/**
 * Agrupa un conjunto de comentarios asociados al uri recibido como parametro de un
 * HttpServletRequest y muestra el listado de los mismos correspondientes al uri recibido.
 * @author jose.jimenez
 */
public class SWBCommentToElement extends org.semanticwb.portal.resources.sem.base.SWBCommentToElementBase {
    /**
     * Instantiates a new sWB comment to element.
     */
    public SWBCommentToElement() {
    }

    /**
     * Instantiates a new sWB comment to element.
     * 
     * @param base the base
     */
    public SWBCommentToElement(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Al recibir la acción definida por <code>action_ADD</code> crea un nuevo comentario con los
     * datos existentes en el HttpServletRequest recibido.
     * 
     * @param request Petición que contiene los datos para crear comentarios.
     * @param response Contiene la información para continuar con la atención de la petición.
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        System.out.println("processAction....");
        if(action.equals(response.Action_ADD)) {
            System.out.println("adding...");
            String securCodeSent = request.getParameter("cmnt_seccode");
            String securCodeCreated = (String)request.getSession(true).getAttribute("cs");
            if( securCodeCreated!=null && securCodeCreated.equalsIgnoreCase(securCodeSent)) {
                String uri = request.getParameter("uri");
                System.out.println("uri="+uri);
                GenericObject gen = SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uri);
                System.out.println("gen="+gen);
                if( gen!=null) {
                    CommentToElement comment = CommentToElement.ClassMgr.createCommentToElement(response.getWebPage().getWebSite());
                    comment.setCommentToElement(request.getParameter("cmnt_comment"));
                    comment.setObjid(uri);
                    addComment(comment);
                    System.out.println("comentario="+request.getParameter("cmnt_comment"));


                    SWBClass swbc = SWBClass.ClassMgr.createSWBClass(uri, response.getWebPage().getWebSite());
                    comment.setGrancosa(swbc);
                }
                response.setRenderParameter("uri", uri);
                request.getSession(true).removeAttribute("cs");
            }else {
                System.out.println("no es seguro");
                Enumeration e = request.getParameterNames();
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    response.setRenderParameter(key, request.getParameter(key));
                }
            }
//            response.sendRedirect(response.getWebPage().getUrl() + "?uri=" + URLEncoder.encode(uri));
        } else {
            super.processAction(request, response);
        }
    }

    /**
     * Presenta la interfaz para capturar comentarios asociados al uri recibido.
     * O muestra el mensaje para que el usuario se firme y pueda generar comentarios.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        System.out.println("\n\ncommentToElement doView...");
        PrintWriter out = response.getWriter();
        StringBuilder ret = new StringBuilder(1000);
        SWBResourceURL rUrl = paramRequest.getActionUrl();
        User user = paramRequest.getUser();
        rUrl.setAction(paramRequest.Action_ADD);

        String name = request.getParameter("cmnt_name")==null ? "" : request.getParameter("cmnt_name");
        String email = request.getParameter("cmnt_email")==null ? "" : request.getParameter("cmnt_email");
        String comment = request.getParameter("cmnt_comment")==null ? "" : request.getParameter("cmnt_comment");
        String uri = request.getParameter("uri")==null ? "" : request.getParameter("uri");
        String securCodeSent = request.getParameter("cmnt_seccode");
        String securCodeCreated = (String) request.getSession(true).getAttribute("cs");

        if (user != null && user.isSigned()) {
            ret.append("<script type=\"text/javascript\">\n");
            ret.append("function isEmpty(objid) {\n");
            ret.append("    var obj = dojo.byId(objid);\n");
            ret.append("    if (obj==null || obj.value=='' || !isNaN(obj.value) || obj.value.charAt(0) == ' ') {\n");
            ret.append("        return true;\n");
            ret.append("    }else {\n");
            ret.append("        return false;\n");
            ret.append("    }\n");
            ret.append("}\n");

            ret.append("function isValidEmail(strEmail) {\n");
            ret.append("    emailRegExp = /^[^@]+@[^@]+.[a-z]{2,}$/i; \n");
            ret.append("    if(strEmail.search(emailRegExp) == -1) {\n");
            ret.append("        return false;\n");
            ret.append("    }\n");
            ret.append("    return true; \n");
            ret.append("}\n");

            ret.append("function doApply() {\n");
            ret.append("    var msgs = new Array();");
            ret.append("    if(isEmpty('cmnt_name')) {\n");
            ret.append("        msgs.push('Ingresa tu nombre.');\n");
            ret.append("    }\n");
            ret.append("    if(!isValidEmail(dojo.byId('cmnt_email').value)) {\n");
            ret.append("        msgs.push('Ingresa un correo electrónico válida.');\n");
            ret.append("    }\n");
            ret.append("    if(isEmpty('cmnt_comment')) {\n");
            ret.append("        msgs.push('Tienes que ingresar un comentario u opinión.');\n");
            ret.append("    }\n");
            ret.append("    if(isEmpty('cmnt_seccode')) {");
            ret.append("        msgs.push('Para poder agregar tu comentario es necesario que ingreses el código mostrado.\\nEn caso de no ser claro cambialo haciendo clic en \"Generar otro código\".');");
            ret.append("    }");

            ret.append("    if(msgs.length > 0) {\n");
            ret.append("        dojo.byId('cmnt_msg').innerHTML = msgs.join('<br />');\n");
            ret.append("        dojo.byId('cmnt_msg').style.backgroundColor = '#F89C9E';\n");
            ret.append("        dojo.byId('cmnt_msg').style.color = '#FF0000';\n");
            ret.append("        dojo.byId('cmnt_msg').style.fontSize = '12px';\n");
            ret.append("        dojo.byId('cmnt_msg').style.textAlign = 'justify';\n");
            ret.append("    }else {\n");
            ret.append("        dojo.byId('cmnt_msg').innerHTML = '';\n");
            ret.append("        dojo.byId('cmnt_send').form.submit();\n");
            ret.append("    }\n");
            ret.append("}\n");

            ret.append("function changeSecureCodeImage(imgid) {\n");
            ret.append("    var img = dojo.byId(imgid);\n");
            ret.append("    if(img) {\n");
            ret.append("        var rn = Math.floor(Math.random()*99999);\n");
            ret.append("        img.src = '" + SWBPlatform.getContextPath() + "/swbadmin/jsp/securecode.jsp?nc='+rn;\n");
            ret.append("    }\n");
            ret.append("}\n");
            ret.append("</script>\n");

            ret.append("<table width=\"99%\" class=\"cmnts\" border=0 cellpadding=\"0\" cellspacing=\"0\">\n");
            ret.append(renderListComments(paramRequest, uri));

            if (securCodeCreated != null
                    && !securCodeCreated.equalsIgnoreCase(securCodeSent)) {
                ret.append("<tr><td class=\"espcmnt\" height=\"30\" colspan=\"2\">\n");
                ret.append("<div id=\"cmnt_msg\" style=\"background-color:#F89C9E; color:#333333; font-size:12px; text-align:center\">");
                ret.append(paramRequest.getLocaleString("msgWrongCode"));
                ret.append("</div>");
                ret.append("</td></tr>");
                request.getSession(true).removeAttribute("cs");
            } else {
                ret.append("<tr><td class=\"cmnt\" height=\"10\" colspan=\"2\"><div id=\"cmnt_msg\"></div></td></tr>\n");
            }

            ret.append("<tr>\n");
            ret.append("<td colspan=\"2\" class=\"creacmnt\">\n");
            ret.append("<h4>" + paramRequest.getLocaleString("add") + "</h4>\n");
            ret.append("<form name=\"cmnt\" id=\"cmnt\" target=\"\" action=\"" + rUrl + "\" method=\"post\">\n");
            ret.append("    <input type=\"hidden\" id=\"uri\" name=\"uri\" value=\"" + uri + "\" />\n");
            ret.append("	<table border=\"0\" width=\"100%\">\n");
            ret.append("      <tr class=\"f\">\n");
            ret.append("        <td width=\"50%\">\n");
            // Fullname
            ret.append("            <p>\n");
            ret.append("            <label for=\"cmnt_name\">" + paramRequest.getLocaleString("nameLabel") + ":</label><br/>\n");
            if (user.isSigned()) {
                ret.append("            <input type=\"text\" id=\"cmnt_name\" name=\"cmnt_name\" value=\"" + user.getFullName() + "\" size=\"34\" />\n");
            } else {
                ret.append("            <input type=\"text\" id=\"cmnt_name\" name=\"cmnt_name\" value=\"" + name + "\" size=\"34\" />\n");
            }
            ret.append("            </p>\n");
            // Email
            ret.append("            <p>\n");
            ret.append("            <label for=\"email\">" + paramRequest.getLocaleString("emailLabel") + ":</label><br/>\n");
            if (user.isSigned()) {
                ret.append("            <input type=\"text\" id=\"cmnt_email\" name=\"cmnt_email\" value=\"" + user.getEmail() + "\" size=\"34\" />\n");
            } else {
                ret.append("            <input type=\"text\" id=\"cmnt_email\" name=\"cmnt_email\" value=\"" + email + "\" size=\"34\" />\n");
            }
            ret.append("            </p>\n");
            //Comment
            ret.append("            <p>\n");
            ret.append("            <label for=\"comment\">" + paramRequest.getLocaleString("comment") + ":</label><br/>\n");
            ret.append("            <textarea id=\"cmnt_comment\" name=\"cmnt_comment\" cols=\"32\" rows=\"3\" >" + comment + "</textarea>\n");
            ret.append("            </p>\n");
            ret.append("        </td>\n");
            ret.append("        <td width=\"50%\">\n");
            ret.append("            <p style=\"text-align:justify\">\n");
            ret.append(paramRequest.getLocaleString("msgSpam"));
            ret.append("            </p>\n");
            ret.append("            <span>\n");
            ret.append("            <div style=\"text-align:right\">\n");
            ret.append("                <input type=\"button\" value=\"" + paramRequest.getLocaleString("anotherCode") + "\" onClick=\"changeSecureCodeImage('imgseccode');\"/>\n");
            ret.append("            </div>\n");
            ret.append("            <div id=\"cntseccode\" style=\"text-align:center\">\n");
            ret.append("                <img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/jsp/securecode.jsp\" id=\"imgseccode\" width=\"155\" height=\"65\" />\n");
            ret.append("            </div>\n");
            ret.append("            </span>\n");
            ret.append("            <p>\n");
            ret.append("                <input type=\"text\" id=\"cmnt_seccode\" name=\"cmnt_seccode\" size=\"45\" />\n");
            ret.append("            </p>\n");
            ret.append("        </td>\n");
            ret.append("      </tr>\n");
            ret.append("      <tr>\n");
            ret.append("        <td colspan=\"2\">\n");
            ret.append("        <p>" + paramRequest.getLocaleString("msgEditorial") + "</p>\n");
            ret.append("        <p><input type=\"button\" id=\"cmnt_send\" name=\"cmnt_send\" value=\"" + paramRequest.getLocaleString("publish") + "\" onClick=\"doApply();\" /></p>\n");
            ret.append("        </td>\n");
            ret.append("      </tr>\n");
            ret.append("    </table>\n");
            ret.append("</form>\n");
            ret.append("</td></tr>\n");
            ret.append("</table>\n");
        } else {
            ret.append("<p>" + paramRequest.getLocaleString("msgLoggInFirst") + "</p>");
        }

        out.println(ret.toString());
        out.flush();
        out.close();
    }

    /*private String renderListComments(SWBParamRequest paramRequest, final String uri) throws SWBResourceException{
        StringBuilder script = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy | HH:mm");
        //long ordinal = SWBUtils.Collections.sizeOf(listComments());
        //int ordinal = 1;
        System.out.println("renderListComments... uri="+uri);

        Iterator<CommentToElement> itComments = listComments();
        script.append("<div class=\"swb-comentario-sem-lista\">");
        script.append("<h1>comentarios</h1>");
        if(itComments.hasNext()) {
            script.append("<ul>");
        }
        while(itComments.hasNext()) {
            CommentToElement comment = itComments.next();
            if( comment.getObjid().equals(uri) )
                script.append("<li><span>"+(comment.getCreator()==null?"An&oacute;nimo":comment.getCreator().getFullName())+"</span> "+sdf.format(comment.getCreated())+"<p>"+comment.getCommentToElement()+"</p></li>");
        }
        if(itComments.hasNext()) {
            script.append("</ul>");
        }
        script.append("</div>");
        return script.toString();
    }*/

    private String renderListComments(SWBParamRequest paramRequest, final String uri) throws SWBResourceException{
        StringBuilder script = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy | HH:mm");
        //long ordinal = SWBUtils.Collections.sizeOf(listComments());
        //int ordinal = 1;
        System.out.println("renderListComments... uri="+uri);

        SWBClass swbc = SWBClass.ClassMgr.createSWBClass(uri, paramRequest.getWebPage().getWebSite());
        Iterator<CommentToElement> itComments = CommentToElement.ClassMgr.listCommentToElementByGrancosa(swbc, paramRequest.getWebPage().getWebSite());


        script.append("<div class=\"swb-comentario-sem-lista\">");
        script.append("<h1>comentarios</h1>");
        if(itComments.hasNext()) {
            script.append("<ul>");
        }
        while(itComments.hasNext()) {
            CommentToElement comment = itComments.next();
            if( comment.getObjid().equals(uri) )
                script.append("<li><span>"+(comment.getCreator()==null?"An&oacute;nimo":comment.getCreator().getFullName())+"</span> "+sdf.format(comment.getCreated())+"<p>"+comment.getCommentToElement()+"</p></li>");
        }
        if(itComments.hasNext()) {
            script.append("</ul>");
        }
        script.append("</div>");
        return script.toString();
    }
}
