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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

/**
 * Agrupa un conjunto de comentarios asociados al suri recibido como parametro de un
 * HttpServletRequest y muestra el listado de los mismos correspondientes al suri recibido.
 * @author jose.jimenez
 */
public class SWBCommentToElement extends org.semanticwb.portal.resources.sem.base.SWBCommentToElementBase {
    private static Logger log = SWBUtils.getLogger(SWBCommentToElement.class);
    public static final String Mode_DETAIL = "dtl";
    
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
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        String action = response.getAction();
        String suri = request.getParameter("suri");
        if(SWBActionResponse.Action_ADD.equals(action))
        {
            String securCodeSent = request.getParameter("cmnt_seccode");
            String securCodeCreated = (String)request.getSession(true).getAttribute("cs");
            if( securCodeCreated!=null && securCodeCreated.equalsIgnoreCase(securCodeSent) )
            {
                if(suri==null) {
                    return;
                }
                try {
                    suri = URLDecoder.decode(suri, "UTF-8");
                }catch(Exception unsage) {
                    suri = null;
                }
                
                SWBClass element = null;
                try {
                    element = (SWBClass)SemanticObject.createSemanticObject(suri).createGenericInstance();
                }catch(Exception exc) {
                    log.error(exc);
                    Enumeration e = request.getParameterNames();
                    while(e.hasMoreElements()) {
                        String key = (String) e.nextElement();
                        response.setRenderParameter(key, request.getParameter(key));
                    }
                    return;
                }
                if(element.isValid())
                {
                    String txt = SWBUtils.XML.replaceXMLChars(request.getParameter("cmnt_comment"));
                    if(!txt.isEmpty())
                    {
                        WebSite model = response.getWebPage().getWebSite();
                        User user = response.getUser();
                        if(user.isSigned())
                        {
                            CommentToElement comment = CommentToElement.ClassMgr.createCommentToElement(model);
                            comment.setCommentToElement(txt);
                            comment.setElement(element);
                        }
                        else
                        {
                            String email = SWBUtils.XML.replaceXMLChars(request.getParameter("email"));
                            String name = SWBUtils.XML.replaceXMLChars(request.getParameter("name"));
                            if(!name.isEmpty() && SWBUtils.EMAIL.isValidEmailAddress(email))
                            {
                                CommentToElement comment = CommentToElement.ClassMgr.createCommentToElement(model);
                                comment.setCommentToElement(txt);
                                comment.setElement(element);
                                comment.setName(name);
                                comment.setEmail(email);
                            }
                        }
                    }
                }
            }
            else
            {
                Enumeration e = request.getParameterNames();
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    response.setRenderParameter(key, request.getParameter(key));
                }
            }
            request.getSession(true).removeAttribute("cs");
        }
        else if(SWBActionResponse.Action_EDIT.equals(action))
        {
            if(suri==null) {
                return;
            }
            try {
                suri = URLDecoder.decode(suri, "UTF-8");
            }catch(Exception unsage) {
                suri = null;
            }

            SWBClass element = null;
            try {
                element = (SWBClass)SemanticObject.createSemanticObject(suri).createGenericInstance();
            }catch(Exception exc) {
                log.error(exc);
//                Enumeration e = request.getParameterNames();
//                while(e.hasMoreElements()) {
//                    String key = (String) e.nextElement();
//                    response.setRenderParameter(key, request.getParameter(key));
//                }
                return;
            }
            if(element.isValid())
            {
                WebSite model = response.getWebPage().getWebSite();
                String commentParentId = request.getParameter("cmmt");
                if( CommentToElement.ClassMgr.hasCommentToElement(commentParentId, model) )
                {
                    String txt = SWBUtils.XML.replaceXMLChars(request.getParameter("ab_comment"));
                    if(!txt.isEmpty())
                    {
                        CommentToElement commentParent = CommentToElement.ClassMgr.getCommentToElement(commentParentId, model);
                        CommentToElement comment = CommentToElement.ClassMgr.createCommentToElement(model);
                        comment.setElement(element);
                        commentParent.addAnswerBack(comment);
                        User user = response.getUser();
                        if(user.isSigned())
                        {
                            comment.setCommentToElement(txt);
                        }
                        else
                        {
                            String name = SWBUtils.XML.replaceXMLChars(request.getParameter("ab_name"));
                            String email = SWBUtils.XML.replaceXMLChars(request.getParameter("ab_email"));
                            if(!name.isEmpty() && SWBUtils.EMAIL.isValidEmailAddress(email))
                            {
                                comment.setCommentToElement(txt);
                                comment.setName(name);
                                comment.setEmail(email);
                            }
                        }
                    }
                }
            }
        }
        else
        {
            super.processAction(request, response);
        }
        
        String lang = response.getUser().getLanguage();
        response.sendRedirect(response.getWebPage().getUrl(lang)+"?suri="+URLEncoder.encode(suri,"UTF-8"));
    }
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if(Mode_DETAIL.equals(mode)) {
            doDetail(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
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
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        
        SWBClass element = getSWBClassObject(request.getParameter("suri"));
        if(element==null) {
            out.println(paramRequest.getLocaleString("noElement"));
            return;            
        }
        
        User user = paramRequest.getUser();
        SWBResourceURL rUrl = paramRequest.getActionUrl();
//        rUrl.setAction(SWBParamRequest.Action_ADD);
        rUrl.setParameter("suri", element.getEncodedURI());

        out.println("<script type=\"text/javascript\">");
        out.println("<!--");
//        out.println("function isEmpty(objid) { ");
//        out.println("    var obj = dojo.byId(objid); ");
//        out.println("    if (obj==null || obj.value=='' || !isNaN(obj.value) || obj.value.charAt(0) == ' ') { ");
//        out.println("        return true; ");
//        out.println("    }else { ");
//        out.println("        return false; ");
//        out.println("    } ");
//        out.println("} ");
//        
//        out.println("function isValidEmail(strEmail) { ");
//        out.println("    emailRegExp = /^[^@]+@[^@]+.[a-z]{2,}$/i; ");
//        out.println("    if(strEmail.search(emailRegExp) == -1) { ");
//        out.println("        return false; ");
//        out.println("    } ");
//        out.println("    return true; ");
//        out.println("} ");
        
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
        
        out.println("function validateAnswerBack(frmId) { ");
        out.println("    var msgs = new Array();");
        out.println("    var frm = dojo.byId(frmId);");
        out.println("    alert('nombre='+frm.ab_name.value+', mail='+frm.ab_email.value+', comentario='+frm.ab_comment.value);");
        out.println("    if(isEmpty(frm.ab_name.value)) { ");
        out.println("        msgs.push('Al parecer falta tu nombre.'); ");
        out.println("    } ");
        
        out.println("    if(!isValidEmail(frm.ab_email.value)) { ");
        out.println("        msgs.push('Tu dirección de correo es incorrecta.'); ");
        out.println("    } ");
        
        out.println("    if(isEmpty(frm.ab_comment.value)) { ");
        out.println("        msgs.push('Falta tu comentario.'); ");
        out.println("    } ");
        
        out.println("    if(msgs.length > 0) { ");
        out.println("          alert(msgs.join('\\n'));");
        out.println("    }else { ");
        //out.println("        frm.submit(); ");
        out.println("    alert('la forma '+frm+' se va');");
        out.println("    } ");
        out.println("} ");
        
        out.println("function changeSecureCodeImage(imgid) { ");
        out.println("    var img = dojo.byId(imgid); ");
        out.println("    if(img) { ");
        out.println("        var rn = Math.floor(Math.random()*99999); ");
        out.println("        img.src = '"+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp?nc='+rn; ");
        out.println("    } ");
        out.println("} ");
        
        
        
        //out.println("dojo.require(\"dojo.parser\");");
        out.println("var pc = 1;");
        out.println("function answerBack(parentObjId, childObjId, position, cmmtId) {");
        out.println("  if(!dojo.byId(childObjId)) {");
        out.println("    var s = new String('');");
        out.println("    s = s.concat('<form id=\"'+childObjId+'\" action=\""+rUrl.setAction(SWBParamRequest.Action_EDIT)+"\" method=\"post\">');");
        if(!user.isSigned()) {
            out.println("    s =  s.concat('<div class=\"swb-comments-name\">');");
            out.println("    s =  s.concat('  <label for=\"ab_name_'+pc+'\">"+paramRequest.getLocaleString("nameLabel")+"</label>');");
            out.println("    s =  s.concat('  <input type=\"text\" id=\"ab_name_'+pc+'\" name=\"ab_name\" />');");
            out.println("    s =  s.concat('</div>');");

            out.println("    s =  s.concat('<div class=\"swb-comments-email\">');");
            out.println("    s =  s.concat('  <label for=\"ab_email_'+pc+'\">"+paramRequest.getLocaleString("emailLabel")+"</label>');");
            out.println("    s =  s.concat('  <input type=\"text\" id=\"ab_email_'+pc+'\" name=\"ab_email\" />');");
            out.println("    s =  s.concat('</div>');");
        }
        out.println("    s = s.concat('<input type=\"hidden\" name=\"cmmt\" value=\"'+cmmtId+'\" />');");
        out.println("    s = s.concat('<div class=\"swb-comments-comment\">');");
        out.println("    s = s.concat('  <label for=\"ab_comment_'+pc+'\">"+paramRequest.getLocaleString("comment")+":</label>');");
        out.println("    s = s.concat('  <textarea id=\"ab_comment_'+pc+'\" name=\"ab_comment\" cols=\"32\" rows=\"3\"></textarea>');");
        out.println("    s = s.concat('</div>');");
//        out.println("    s = s.concat('<div class=\"swb-comments-image\">');");
//        out.println("    s = s.concat('  <img src=\""+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp\" id=\"imgseccode_'+pc+'\" width=\"155\" height=\"65\" /><br/>');");
//        out.println("    s = s.concat('  <a href=\"#\" onclick=\"changeSecureCodeImage(\'imgseccode_\''+pc+')\">"+paramRequest.getLocaleString("anotherCode")+"</a>');");
//        out.println("    s = s.concat('</div>');");
//        out.println("    s = s.concat('<div class=\"swb-comments-captcha\">');");
//        out.println("    s = s.concat('  <label for=\"ab_seccode\">El texto de la imagen es:</label>');");
//        out.println("    s = s.concat('  <input type=\"text\" id=\"ab_seccode_'+pc+'\" name=\"ab_seccode_'+pc+'\" />');");
//        out.println("    s = s.concat('</div>');");
        out.println("    s = s.concat('<div class=\"swb-comments-send\">');");
        out.println("    s = s.concat('  <input type=\"button\" value=\""+paramRequest.getLocaleString("publish")+"\" onclick=\"validateAnswerBack('+childObjId+')\" />');");
        out.println("    s = s.concat('</div>');");
        out.println("    s = s.concat('</form>');");
        out.println("    dojo.place(s, parentObjId, position);");
        out.println("    dojo.parser.parse(childObjId);");
        out.println("    pc++;");
        out.println("  }else {");
        out.println("    dojo.destroy(childObjId);");
        out.println("  }");
        out.println("}");
        
        out.println("-->");
        out.println("</script> ");
        
        String comment = request.getParameter("cmnt_comment")==null?"":request.getParameter("cmnt_comment");
        out.println("<div class=\"swb-comments\">");
        out.println("<h2>"+paramRequest.getLocaleString("add")+"</h2>");
        out.println("<form id=\"cmnt\" action=\""+rUrl.setAction(SWBParamRequest.Action_ADD)+"\" method=\"post\">\n");
//        User user = paramRequest.getUser();
        if(!user.isSigned()) {
            out.println("<div class=\"swb-comments-name\">");
            out.println("  <label for=\"cmnt_seccode\">"+paramRequest.getLocaleString("nameLabel")+"</label>");
            out.println("  <input type=\"text\" id=\"name\" name=\"name\" />");
            out.println("</div>");
            
            out.println("<div class=\"swb-comments-email\">");
            out.println("  <label for=\"cmnt_seccode\">"+paramRequest.getLocaleString("emailLabel")+"</label>");
            out.println("  <input type=\"text\" id=\"email\" name=\"email\" />");
            out.println("</div>");
        }
        out.println("<div class=\"swb-comments-comment\">");
        out.println("  <label for=\"comment\">"+paramRequest.getLocaleString("comment")+":</label>");
        out.println("  <textarea id=\"cmnt_comment\" name=\"cmnt_comment\" cols=\"32\" rows=\"3\" >"+comment+"</textarea>");
        out.println("</div>");
        out.println("<div class=\"swb-comments-image\">");
        out.println("  <img src=\""+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp\" id=\"imgseccode\" width=\"155\" height=\"65\" /><br/>");
        out.println("  <a href=\"#\" onclick=\"changeSecureCodeImage('imgseccode');\">"+paramRequest.getLocaleString("anotherCode")+"</a>");
        out.println("</div>");
        out.println("<div class=\"swb-comments-captcha\">");
        out.println("  <label for=\"cmnt_seccode\">El texto de la imagen es:</label>");
        out.println("  <input type=\"text\" id=\"cmnt_seccode\" name=\"cmnt_seccode\" />");
        out.println("</div>");
        out.println("<div class=\"swb-comments-send\">");
        out.println("  <input type=\"button\" id=\"cmnt_send\" value=\""+paramRequest.getLocaleString("publish")+"\" onclick=\"doApply();\" />");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        Iterator<CommentToElement> comments = CommentToElement.ClassMgr.listCommentToElementByElement(element, paramRequest.getWebPage().getWebSite());
//        out.println(renderListComments(paramRequest, element.getEncodedURI()));
        out.println("<div class=\"swb-comments-lst\">");
        if(comments.hasNext()) {
            out.println("<p class=\"swb-semcommts-lblcmmt\">"+paramRequest.getLocaleString("lblComments")+"</p>");
            out.println(renderListComments(paramRequest, comments));
            out.println("<p><a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(SWBResourceURL.Mode_INDEX).setParameter("suri",element.getEncodedURI())+"','commts')\">"+paramRequest.getLocaleString("viewAllComments") +"&nbsp;&raquo;</a></p>");
        }else {
            out.println("<p class=\"swb-semcommts-noe\">"+paramRequest.getLocaleString("noComment")+"</p>");
        }
        out.println("</div>");
        
        out.flush();
        out.close();
    }

    /**
     * Render list comments.
     * 
     * @param paramRequest the param request
     * @param suri the suri
     * @return the string
     */
    private String renderListComments(SWBParamRequest paramRequest, Iterator<CommentToElement> icomments) throws SWBResourceException
    {
        StringBuilder html = new StringBuilder();
        final User user = paramRequest.getUser();
        final String lang = user.getLanguage()==null?"es":user.getLanguage();
        String name;
        
//        SWBClass element = getSWBClassObject(suri);
//        if(element==null) {
//            return paramRequest.getLocaleString("noElement");
//        }
//        
//        Iterator<CommentToElement> icomments = CommentToElement.ClassMgr.listCommentToElementByElement(element, paramRequest.getWebPage().getWebSite());
        icomments = SWBComparator.sortByCreated(icomments, false);
        
//        html.append("<div class=\"swb-semcomments\">");
        if(icomments.hasNext())
        {
//            html.append(" <p class=\"swb-semcommts-lblcmmt\">"+paramRequest.getLocaleString("lblComments")+"</p>");
            html.append(" <div class=\"swb-semcommts-box\" id=\"commts\">");
            html.append("  <ul class=\"swb-semcommts-lst\">");
//            int min = getBlockSize();
            for(int i=getBlockSize(); i>0&&icomments.hasNext(); i--) {
                CommentToElement comment = icomments.next();
                if(!comment.isValid() || !user.haveAccess(comment)) {
                    continue;
                }
                html.append("  <li class=\"swb-semcommts-item\" id=\"item_"+comment.getId()+"\">");
                html.append("   <p class=\"swb-semcommts-name\">"+(comment.getCreator()==null?comment.getName():comment.getCreator().getFullName()));
                try {
                    html.append("&nbsp;<span class=\"swb-semcommts-ago\">"+paramRequest.getLocaleString("ago")+"&nbsp;"+SWBUtils.TEXT.getTimeAgo(comment.getCreated(), lang)+"</span>");
                }catch(Exception e) {
                }
                html.append("   </p>");
                html.append("   <p class=\"swb-semcommts-cmmt\">"+comment.getCommentToElement()+"</p>");
                html.append("   <p><a href=\"#\" onclick=\"answerBack('cmmt_"+comment.getId()+"','child_"+comment.getId()+"', 'last','"+comment.getId()+"');return false;\">"+paramRequest.getLocaleString("answerBack") +"</a></p>");
                if(comment.getAnswerBackTo()!=null) {
                    html.append("<div class=\"swb-commts-ans\" id=\"cmmt_"+comment.getId()+"\">");
                    html.append(paramRequest.getLocaleString("inAnswerTo")+"&nbsp;");
                    html.append(comment.getAnswerBackTo().getCreator()==null?comment.getAnswerBackTo().getName():comment.getAnswerBackTo().getCreator().getFullName());
                    html.append("&nbsp;<a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setCallMethod(SWBParamRequest.Call_DIRECT).setMode(Mode_DETAIL).setParameter("cmmt", comment.getEncodedURI())+"','item_"+comment.getId()+"')\" title=\""+paramRequest.getLocaleString("showComment")+"\">("+paramRequest.getLocaleString("showComment")+")</a>");
                    html.append("</div>");
                }else {
                    html.append("   <div class=\"swb-commts-ans\" id=\"cmmt_"+comment.getId()+"\"></div>");
                }
                html.append("  </li>");
            }
            html.append(" </ul>");
//            html.append(" <p><a href=\"#\" onclick=\"postHtml('"+paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(SWBResourceURL.Mode_INDEX).setParameter("suri",element.getEncodedURI())+"','commts')\">"+paramRequest.getLocaleString("viewAllComments") +"&nbsp;&raquo;</a></p>");
            html.append("</div>");
        }else {
//            html.append("<p class=\"swb-semcommts-noe\">"+paramRequest.getLocaleString("noComment")+"</p>");
        }
//        html.append("</div>");
        return html.toString();
    }
    
    public void doDetail(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        
        PrintWriter out = response.getWriter();
        StringBuilder html = new StringBuilder();
        final String lang = paramRequest.getUser()==null?"es":paramRequest.getUser().getLanguage();
        String cmmt = request.getParameter("cmmt");
        try {
            cmmt = URLDecoder.decode(cmmt, "UTF-8");
        }catch(Exception unsage) {
            cmmt = null;
        }
        
        try {
            CommentToElement answer = null;
            answer = (CommentToElement)SemanticObject.createSemanticObject(cmmt).createGenericInstance();
            CommentToElement comment = answer.getAnswerBackTo();
            
            html.append("<div>");
            html.append("  <div>");
            html.append("    <p class=\"swb-commts-name\">"+(comment.getCreator()==null?comment.getName():comment.getCreator().getFullName()));
            try {
                html.append("&nbsp;<span class=\"swb-commts-ago\">"+paramRequest.getLocaleString("ago")+"&nbsp;"+SWBUtils.TEXT.getTimeAgo(comment.getCreated(), lang)+"</span>");
            }catch(Exception e) {
            }
            html.append("    </p>");
            html.append("    <p class=\"swb-commts-cmmt\">"+comment.getCommentToElement()+"</p>");
            html.append("  </div>");
            html.append("  <div>");
            html.append("    <p class=\"swb-commts-name\">"+(answer.getCreator()==null?answer.getName():answer.getCreator().getFullName()));
            try {
                html.append("&nbsp;<span class=\"swb-commts-ago\">"+paramRequest.getLocaleString("ago")+"&nbsp;"+SWBUtils.TEXT.getTimeAgo(answer.getCreated(), lang)+"</span>");
            }catch(Exception e) {
            }
            html.append("    </p>");
            html.append("    <p class=\"swb-commts-cmmt\">"+answer.getCommentToElement()+"</p>"); 
            html.append("<p><a href=\"#\" onclick=\"answerBack('cmmt_"+answer.getId()+"','child_"+answer.getId()+"', 'last','"+answer.getId()+"');return false;\">"+paramRequest.getLocaleString("answerBack") +"</a></p>");
            html.append("<div class=\"swb-commts-ans\" id=\"cmmt_"+answer.getId()+"\">");
            html.append(paramRequest.getLocaleString("inAnswerTo")+"&nbsp;");
            html.append(comment.getCreator()==null?comment.getName():comment.getCreator().getFullName());
            html.append("</div>");
            html.append("  </div>");
            html.append("</div>");
            
            out.println(html.toString());
        }catch(Exception e) {
            log.error(e);
            out.println("Auch!");
        }
        out.flush();
        out.close();
    }

    @Override
    public void doIndex(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/xml; charset=UTF-8");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        
        PrintWriter out = response.getWriter();
        
        SWBClass element = getSWBClassObject(request.getParameter("suri"));
        if(element==null) {
            out.println(paramRequest.getLocaleString("noElement"));
            return;            
        }
        
        StringBuilder html = new StringBuilder();
        final User user = paramRequest.getUser();
        final String lang = user.getLanguage()==null?"es":user.getLanguage();
        String name;        

        Iterator<CommentToElement> comments = CommentToElement.ClassMgr.listCommentToElementByElement(element, paramRequest.getWebPage().getWebSite());
        comments = SWBComparator.sortByCreated(comments, false);
        if(comments.hasNext())
        {
            html.append(" <ol class=\"swb-semcommts-lst\">");
            while(comments.hasNext()) {
                CommentToElement comment = comments.next();
                if(!comment.isValid() || !user.haveAccess(comment)) {
                    continue;
                }
                html.append("  <li class=\"swb-semcommts-item\">");
                html.append("   <p class=\"swb-semcommts-name\">"+(comment.getCreator()==null?comment.getName():comment.getCreator().getFullName()));
                try {
                    html.append("&nbsp;<span class=\"swb-semcommts-ago\">"+paramRequest.getLocaleString("ago")+"&nbsp;"+SWBUtils.TEXT.getTimeAgo(comment.getCreated(), lang)+"</span>");
                }catch(Exception e) {
                }
                html.append("   </p>");
                html.append("   <p class=\"swb-semcommts-cmmt\">"+comment.getCommentToElement()+"</p>");
                html.append("  </li>");
            }
            html.append(" </ol>");
        }
        out.println(html.toString());

    }
    
    private SWBClass getSWBClassObject(final String suri)
    {
        SWBClass element = null;
        String suri_ = suri;
        if(suri_==null) {
            return element;
        }
        try {
            suri_ = URLDecoder.decode(suri, "UTF-8");
        }catch(Exception unsee) {
            suri_ = suri;
        }
        try {
            element = (SWBClass)SemanticObject.createSemanticObject(suri_).createGenericInstance();
        }catch(Exception e) {
            element = null;
        }        
        if(element==null || !element.isValid()) {
            element = null;
        }
        return element;
    }
}
