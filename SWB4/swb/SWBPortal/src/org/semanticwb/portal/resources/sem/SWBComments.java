package org.semanticwb.portal.resources.sem;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;

public class SWBComments extends org.semanticwb.portal.resources.sem.base.SWBCommentsBase {
    private static final String action_ADD = "ADDNEW";
    private static final int secureCodeLength = 7;

    public SWBComments() {
    }

    public SWBComments(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        
        if(action.equalsIgnoreCase(action_ADD)) {
            String securCodeSent = request.getParameter("cmnt_seccode");
            String securCodeCreated = (String)request.getSession(true).getAttribute("cs");
            if(securCodeCreated!=null && securCodeCreated.equalsIgnoreCase(securCodeSent)) {
                WebSite model = response.getTopic().getWebSite();
                Comment comment = Comment.createComment(model);
                comment.setComment(request.getParameter("cmnt_comment"));
                comment.setCreated(new Date());
                //User user = response.getUser();
                comment.setCreator(response.getUser());
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
        rUrl.setAction(action_ADD);

        String name = request.getParameter("cmnt_name")==null?"":request.getParameter("cmnt_name");
        String email = request.getParameter("cmnt_email")==null?"":request.getParameter("cmnt_email");
        String comment = request.getParameter("cmnt_comment")==null?"":request.getParameter("cmnt_comment");
        String securCodeSent = request.getParameter("cmnt_seccode");
        String securCodeCreated = (String)request.getSession(true).getAttribute("cs");
        
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
        ret.append("        img.src = '"+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp?nc='+rn;\n");
        ret.append("    }\n");
        ret.append("}\n");

        ret.append("</script>\n");
        
        ret.append("<table width=\"100%\" class=\"cmnts\" border=0 cellpadding=\"0\" cellspacing=\"0\">\n");
        ret.append(renderListComments());

        if(securCodeCreated!=null && !securCodeCreated.equalsIgnoreCase(securCodeSent)) {
            ret.append("<tr><td class=\"espcmnt\" height=\"30\" colspan=\"2\">\n");
            ret.append("<div id=\"cmnt_msg\" style=\"background-color:#F89C9E; color:#FF0000; font-size:12px; text-align:center\">");
            ret.append("El código de seguridad es incorrecto. En caso de no ser claro cámbialo haciendo clic en \"Obtener otro código\".");
            ret.append("</div>");
            ret.append("</td></tr>");
            request.getSession(true).removeAttribute("cs");
        }else {
            ret.append("<tr><td class=\"cmnt\" height=\"10\" colspan=\"2\"><div id=\"cmnt_msg\"></div></td></tr>\n");
        }
        
        ret.append("<tr>\n");
        ret.append("<td colspan=\"2\" class=\"creacmnt\">\n");
        ret.append("<h4>Agrega tu comentario</h4>\n");
        ret.append("<form name=\"cmnt\" id=\"cmnt\" target=\"\" action=\""+rUrl+"\" method=\"post\">\n");
        ret.append("	<table border=\"0\" width=\"100%\">\n");
        ret.append("      <tr class=\"f\">\n");
        ret.append("        <td width=\"312\">\n");
        // Fullname
        ret.append("            <p>\n");
        ret.append("            <label for=\"cmnt_name\">Nombre Completo:</label><br/>\n");
        User user = paramRequest.getUser();
        if(user.isSigned()) {
            ret.append("            <input type=\"text\" id=\"cmnt_name\" name=\"cmnt_name\" value=\""+user.getFullName()+"\" size=\"34\" />\n");
        }else {
            ret.append("            <input type=\"text\" id=\"cmnt_name\" name=\"cmnt_name\" value=\""+name+"\" size=\"34\" />\n");
        }
        ret.append("            </p>\n");
        // Email
        ret.append("            <p>\n");
        ret.append("            <label for=\"email\">Tu correo electrónico válido:</label><br/>\n");
        if(user.isSigned()) {
            ret.append("            <input type=\"text\" id=\"cmnt_email\" name=\"cmnt_email\" value=\""+user.getEmail()+"\" size=\"34\" />\n");
        }else {
            ret.append("            <input type=\"text\" id=\"cmnt_email\" name=\"cmnt_email\" value=\""+email+"\" size=\"34\" />\n");
        }
        ret.append("            </p>\n");
        //Comment
        ret.append("            <p>\n");
        ret.append("            <label for=\"comment\">Comentario:</label><br/>\n");
        ret.append("            <textarea id=\"cmnt_comment\" name=\"cmnt_comment\" cols=\"32\" rows=\"3\" >"+comment+"</textarea>\n");
        ret.append("            </p>\n");
        ret.append("        </td>\n");
        ret.append("        <td width=\"312\">\n");
        ret.append("            <p style=\"text-align:justify\">\n");
        ret.append("                Para enviar tu comentario ingresa la palabras que se muestra abajo.\n");
        ret.append("                En caso de no ser suficientemente claras, las puedes cambiar dando clic en \"Otro código\".\n");
        ret.append("            </p>\n");
        ret.append("            <span>\n");
        ret.append("            <div style=\"text-align:right\">\n");
        ret.append("                <input type=\"button\" value=\"Generar otro código\" onClick=\"changeSecureCodeImage('imgseccode');\"/>\n");
        ret.append("            </div>\n");
        ret.append("            <div id=\"cntseccode\" style=\"text-align:center\">\n");
        ret.append("                <img src=\""+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp\" id=\"imgseccode\" width=\"300\" height=\"59\" />\n");
        ret.append("            </div>\n");        
        ret.append("            </span>\n");
        ret.append("            <p>\n");
        ret.append("                <input type=\"text\" id=\"cmnt_seccode\" name=\"cmnt_seccode\" size=\"45\" />\n");
        ret.append("            </p>\n");
        ret.append("        </td>\n");
        ret.append("      </tr>\n");
        ret.append("      <tr>\n");
        ret.append("        <td colspan=\"2\">\n");
        ret.append("        <p>\n");
        ret.append("        	Aviso: Esta herramienta tiene la finalidad de permitir el intercambio de ideas de manera responsable, por este motivo nos reservamos el derecho de remover o editar comentarios de contenido obsceno, injurioso, pornográfico o sin relación al tema publicado.\n");
        ret.append("        </p>\n");
        ret.append("        <p><input type=\"button\" id=\"cmnt_send\" name=\"cmnt_send\" value=\"publicar comentario\" onClick=\"doApply();\" /></p>\n");
        ret.append("        </td>\n");
        ret.append("      </tr>\n");
        ret.append("    </table>\n");
        ret.append("</form>\n");
        ret.append("</td></tr>\n");
        ret.append("</table>\n");
        
        out.println(ret.toString());
        out.flush();
        out.close();
    }

    private String renderListComments() {
        StringBuilder ret = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy | HH:mm");
        //long ordinal = SWBUtils.sizeOf(listComments());
        int ordinal = 1;

        Iterator<Comment> itComments = listComments();
        while(itComments.hasNext()) {
            Comment comment = itComments.next();
            ret.append("<tr>\n");
//            ret.append("  <td class=\"cmntimg\" width=\"30\" height=\"30\">\n");
//            ret.append("  <img src=\""+SWBPlatform.getContextPath()+"/swbadmin/icons/status_online.png\" alt=\"user comment\" />\n");
//            ret.append("  </td>\n");
            ret.append("  <td colspan=2 class=\"cmnt\">"+(ordinal++)+". <strong>"+(comment.getCreator().getFullName().equalsIgnoreCase("")?"Desconocido":comment.getCreator().getFullName())+" escribió</strong> el "+sdf.format(comment.getCreated())+"<br />"+comment.getComment()+"</td>\n");
            ret.append("</tr>\n");
        }


        return ret.toString();
    }



}
