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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    private static final String FCTR = "fctr";
    
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
        final String action = response.getAction();
        String suri = request.getParameter("suri");
        if(suri==null) {
            return;
        }
        try {
            suri = URLDecoder.decode(suri, "UTF-8");
        }catch(Exception unsage) {
            suri = request.getParameter("suri");
        }
        String alertmsg = null;
        
        if(SWBActionResponse.Action_ADD.equals(action))
        {
            String securCodeSent = request.getParameter("seccode");
            String securCodeCreated = (String)request.getSession(true).getAttribute("cs");
            request.getSession(true).removeAttribute("cs");
            if( securCodeCreated!=null && securCodeCreated.equalsIgnoreCase(securCodeSent) )
            {
                SWBClass element = null;
                try {
                    element = (SWBClass)SemanticObject.createSemanticObject(suri).createGenericInstance();
                }catch(Exception exc) {
                    log.error(exc);
                    return;
                }
                if(element.isValid())
                {
                    String txt = SWBUtils.XML.replaceXMLChars(request.getParameter("comment"));
                    if(txt.length()>=5)
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
                            String name = SWBUtils.XML.replaceXMLChars(request.getParameter("name"));                            
                            if(!name.isEmpty())
                            {
                                String email = SWBUtils.XML.replaceXMLChars(request.getParameter("email"));
                                if(SWBUtils.EMAIL.isValidEmailAddress(email))
                                {
                                    CommentToElement comment = CommentToElement.ClassMgr.createCommentToElement(model);
                                    comment.setCommentToElement(txt);
                                    comment.setElement(element);
                                    comment.setName(name);
                                    comment.setEmail(email);
                                }
                                else
                                {
                                    alertmsg = response.getLocaleString("msgWrongEmail");
                                }
                            }
                            else
                            {
                                alertmsg = response.getLocaleString("msgWrongName");
                            }
                        }
                    }
                    else
                    {
                        alertmsg = response.getLocaleString("msgWrongComment");
                    }
                }
                else
                {
                    log.error("El objeto SWBClass "+element+" con Id "+element.getId()+" es inválido y no es posible comentarlo en "+response.getWebPage().getId());
                }                
            }
            else
            {
                alertmsg = response.getLocaleString("msgWrongCode");
            }
        }
        else if(SWBActionResponse.Action_EDIT.equals(action))
        {
            SWBClass element = null;
            try {
                element = (SWBClass)SemanticObject.createSemanticObject(suri).createGenericInstance();
            }catch(Exception exc) {
                log.error(exc);
                return;
            }
            if(element.isValid())
            {
                String txt = SWBUtils.XML.replaceXMLChars(request.getParameter("comment"));
                if(txt.length()>=5)
                {
                    WebSite model = response.getWebPage().getWebSite();
                    String commentParentId = request.getParameter("cmmt");
                    if( CommentToElement.ClassMgr.hasCommentToElement(commentParentId, model) )
                    {
                        User user = response.getUser();
                        if(user.isSigned())
                        {
                            CommentToElement commentParent = CommentToElement.ClassMgr.getCommentToElement(commentParentId, model);
                            CommentToElement comment = CommentToElement.ClassMgr.createCommentToElement(model);
                            comment.setElement(element);
                            commentParent.addAnswerBack(comment);
                            comment.setCommentToElement(txt);
                        }
                        else
                        {
                            String name = SWBUtils.XML.replaceXMLChars(request.getParameter("name"));                            
                            if(!name.isEmpty())
                            {
                                String email = SWBUtils.XML.replaceXMLChars(request.getParameter("email"));
                                if(SWBUtils.EMAIL.isValidEmailAddress(email))
                                {
                                    CommentToElement commentParent = CommentToElement.ClassMgr.getCommentToElement(commentParentId, model);
                                    CommentToElement comment = CommentToElement.ClassMgr.createCommentToElement(model);
                                    comment.setElement(element);
                                    commentParent.addAnswerBack(comment);
                                    comment.setCommentToElement(txt);
                                    comment.setName(name);
                                    comment.setEmail(email);
                                }
                                else
                                {
                                    alertmsg = response.getLocaleString("msgWrongEmail");
                                }
                            }
                            else
                            {
                                alertmsg = response.getLocaleString("msgWrongName");
                            }
                        }
                    }
                }
                else
                {
                    alertmsg = response.getLocaleString("msgWrongComment");
                }
            }
            else
            {
                log.error("El objeto SWBClass "+element+" con Id "+element.getId()+" es inválido y no es posible comentarlo en "+response.getWebPage().getId());
            }  
        }
        else
        {
            super.processAction(request, response);
        }
        
        String lang = response.getUser().getLanguage();
        response.sendRedirect(response.getWebPage().getUrl(lang)+"?suri="+URLEncoder.encode(suri,"UTF-8")+(alertmsg==null?"":"&alertmsg="+alertmsg) + (alertmsg!=null?"&name="+request.getParameter("name"):"")+(alertmsg!=null?"&email="+request.getParameter("email"):"")+(alertmsg!=null?"&comment="+request.getParameter("comment"):""));
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
        
        request.getSession(true).removeAttribute(FCTR);
        
        User user = paramRequest.getUser();
        
        SWBResourceURL rUrl = paramRequest.getActionUrl();
        rUrl.setParameter("suri", element.getEncodedURI());

        out.println("<script type=\"text/javascript\">");
        out.println("<!--");
        
        out.println("function validateFrm(frmId) {");
        out.println("  var frm = dojo.byId(frmId);");
        //out.println("alert('frm='+frm+', name='+frm.name+', email='+frm.email+', cmmt='+frm.comment);");
        out.println("  if(!frm) {");
        out.println("    return false;");
        out.println("  } ");
        if(!user.isSigned()) {
            out.println("  if(isEmpty(frm.name.value)) {");
            out.println("    alert('Al parecer falta tu nombre completo.');");
            out.println("    return false;");
            out.println("  }");        
            out.println("  if(!isValidEmail(frm.email.value)) {");
            out.println("    alert('Tu dirección de correo es incorrecta, revísala e intentalo de nuevo.');");
            out.println("    return false;");
            out.println("  }");
        }
        out.println("  if(isEmpty(frm.comment.value)) {");
        out.println("    alert('Falta tu comentario.');");
        out.println("    return false;");
        out.println("  }");        
        out.println("  if(frm.comment.value.length<5) {");
        out.println("    alert('¡Vaya&#33;&nbsp;El comentario es demasiado corto.');");
        out.println("    return false;");
        out.println("  } ");
        
        out.println("  if(frm.seccode && isEmpty('seccode')) { ");
        out.println("    msgs.push('Para poder agregar tu comentario es necesario que ingreses el código de la imagen.\\nEn caso de no ser claro puedes cambiarlo haciendo clic en <<Cambiar imagen>>.'); ");
        out.println("    return false;");
        out.println("  }");
        out.println("  return true;");       
        out.println("} ");
        
        out.println("function changeSecureCodeImage(imgid) { ");
        out.println("    var img = dojo.byId(imgid); ");
        out.println("    if(img) { ");
        out.println("        var rn = Math.floor(Math.random()*99999); ");
        out.println("        img.src = '"+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp?nc='+rn; ");
        out.println("    } ");
        out.println("} ");
        
        out.println("var pc = 1;");
        out.println("function answerBack(parentObjId, childObjId, position, cmmtId) {");
        out.println("  if(!dojo.byId(childObjId)) {");
        out.println("    var s = new String('');");
        out.println("    s = s.concat('<form id=\"'+childObjId+'\" action=\""+rUrl.setAction(SWBParamRequest.Action_EDIT)+"\" method=\"post\">');");
        if(!user.isSigned()) {
            out.println("    s =  s.concat('<div class=\"swb-comments-name\">');");
            out.println("    s =  s.concat('  <label for=\"ab_name_'+pc+'\">"+paramRequest.getLocaleString("nameLabel")+"</label>');");
            out.println("    s =  s.concat('  <input type=\"text\" id=\"ab_name_'+pc+'\" name=\"name\" />');");
            out.println("    s =  s.concat('</div>');");

            out.println("    s =  s.concat('<div class=\"swb-comments-email\">');");
            out.println("    s =  s.concat('  <label for=\"ab_email_'+pc+'\">"+paramRequest.getLocaleString("emailLabel")+"</label>');");
            out.println("    s =  s.concat('  <input type=\"text\" id=\"ab_email_'+pc+'\" name=\"email\" />');");
            out.println("    s =  s.concat('</div>');");
        }
        out.println("    s = s.concat('<input type=\"hidden\" name=\"cmmt\" value=\"'+cmmtId+'\" />');");
        
        out.println("    s = s.concat('<div class=\"swb-comments-comment\">');");
        out.println("    s = s.concat('  <label for=\"ab_comment_'+pc+'\">"+paramRequest.getLocaleString("comment")+":</label>');");
        out.println("    s = s.concat('  <textarea id=\"ab_comment_'+pc+'\" name=\"comment\" cols=\"32\" rows=\"3\"></textarea>');");
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
        out.println("    s = s.concat('  <input type=\"button\" value=\""+paramRequest.getLocaleString("publish")+"\" onclick=\"if(validateFrm('+childObjId+')){this.form.submit()}\" />');");
        out.println("    s = s.concat('</div>');");
        out.println("    s = s.concat('</form>');");
        out.println("    dojo.place(s, parentObjId, position);");
        out.println("    dojo.parser.parse(childObjId);");
        out.println("    dojo.byId(childObjId).name.focus();");
        out.println("    pc++;");
        out.println("  }else {");
        out.println("    dojo.destroy(childObjId);");
        out.println("  }");
        out.println("}");
        
        out.println("-->");
        out.println("</script> ");
        
        ;
        out.println("<div class=\"swb-comments\">");
        out.println("<p class=\"swb-comments-addcmnt\">"+paramRequest.getLocaleString("add")+"</p>");
        if(request.getParameter("alertmsg")!=null)
        {
            out.println("<div class=\"swb-alertmsg\">"+request.getParameter("alertmsg")+"</div>");
        }
        out.println("<form id=\"cmnt\" action=\""+rUrl.setAction(SWBParamRequest.Action_ADD)+"\" method=\"post\">\n");
        if(!user.isSigned()) {
            out.println("<div class=\"swb-comments-name\">");
            out.println("  <label for=\"name\">"+paramRequest.getLocaleString("nameLabel")+"</label>");
            out.println("  <input type=\"text\" id=\"name\" name=\"name\" value=\""+(request.getParameter("name")==null?"":request.getParameter("name"))+"\" />");
            out.println("</div>");
            
            out.println("<div class=\"swb-comments-email\">");
            out.println("  <label for=\"email\">"+paramRequest.getLocaleString("emailLabel")+"</label>");
            out.println("  <input type=\"text\" id=\"email\" name=\"email\" value=\""+(request.getParameter("email")==null?"":request.getParameter("email"))+"\" />");
            out.println("</div>");
        }
        out.println("<div class=\"swb-comments-comment\">");
        out.println("  <label for=\"comment\">"+paramRequest.getLocaleString("comment")+":</label>");
        out.println("  <textarea id=\"comment\" name=\"comment\" cols=\"32\" rows=\"3\" >"+(request.getParameter("comment")==null?"":request.getParameter("comment"))+"</textarea>");
        out.println("</div>");
        out.println("<div class=\"swb-comments-image\">");
        out.println("  <img src=\""+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp\" id=\"imgseccode\" width=\"155\" height=\"65\" /><br/>");
        out.println("  <a href=\"javascript:changeSecureCodeImage('imgseccode');return false;\">"+paramRequest.getLocaleString("anotherCode")+"</a>");
        out.println("</div>");
        out.println("<div class=\"swb-comments-captcha\">");
        out.println("  <label for=\"seccode\">El texto de la imagen es:</label>");
        out.println("  <input type=\"text\" id=\"seccode\" name=\"seccode\" />");
        out.println("</div>");
        out.println("<div class=\"swb-comments-send\">");
        out.println("  <input type=\"button\" value=\""+paramRequest.getLocaleString("publish")+"\" onclick=\"if(validateFrm('cmnt')){this.form.submit()}\" />");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        //Iterator<CommentToElement> comments = CommentToElement.ClassMgr.listCommentToElementByElement(element, paramRequest.getWebPage().getWebSite());
        Iterator<CommentToElement> comments = listCommentToElementByElement(element, paramRequest.getWebPage().getWebSite());
        List<CommentToElement> commentsList = SWBUtils.Collections.copyIterator(comments);
        out.println("<div class=\"swb-comments-lst\">");
        //if(comments.hasNext()) {
        if(!commentsList.isEmpty())
        {
            out.println("<p class=\"swb-comments-lblcmmt\">"+paramRequest.getLocaleString("lblComments")+"</p>");
            //out.println(renderListComments(paramRequest, comments));
            out.println(" <div class=\"swb-comments-box\" id=\"commts\">");
            out.println(renderListComments(paramRequest, commentsList.iterator()));
            if(commentsList.size()>getBlockSize()) {
                out.println("<p><a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(SWBResourceURL.Mode_INDEX).setParameter("suri",element.getEncodedURI()).setParameter(FCTR,"_")+"','commts')\" title=\""+paramRequest.getLocaleString("viewMore")+"\">"+paramRequest.getLocaleString("viewMore")+"</a></p>");
//                if(commentsList.size()>getBlockSize()*5) {
//                    out.println("<p><a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(SWBResourceURL.Mode_INDEX).setParameter("suri",element.getEncodedURI())+"','commts')\" title=\""+paramRequest.getLocaleString("viewAllComments")+"\">"+paramRequest.getLocaleString("viewAllComments")+"&nbsp;&raquo;</a></p>");
//                }
            }
            out.println("</div>");
        }
        else
        {
            out.println("<p class=\"swb-comments-noe\">"+paramRequest.getLocaleString("noComment")+"</p>");
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
        
        if(icomments.hasNext())
        {
            icomments = SWBComparator.sortByCreated(icomments, false);
//            html.append(" <div class=\"swb-comments-box\" id=\"commts\">");
            html.append("  <ul class=\"swb-comments-lst\">");
            for(int i=getBlockSize(); i>0&&icomments.hasNext(); i--) {
                CommentToElement comment = icomments.next();
//                if(!comment.isValid() || !user.haveAccess(comment)) {
//                    continue;
//                }
                html.append("  <li class=\"swb-cmmts-item\" id=\"item_"+comment.getId()+"\">");
                html.append("   <p class=\"swb-cmmts-name\">"+(comment.getCreator()==null?comment.getName():comment.getCreator().getFullName()));
                try {
                    html.append("&nbsp;<span class=\"swb-cmmts-ago\">"+paramRequest.getLocaleString("ago")+"&nbsp;"+SWBUtils.TEXT.getTimeAgo(comment.getCreated(), lang)+"</span>");
                }catch(Exception e) {
                }
                html.append("   </p>");
                html.append("   <p class=\"swb-cmmts-cmmt\">"+comment.getCommentToElement()+"</p>");
                html.append("   <p><a href=\"javascript:answerBack('cmmt_"+comment.getId()+"','child_"+comment.getId()+"', 'last','"+comment.getId()+"')\">"+paramRequest.getLocaleString("answerBack") +"</a></p>");
//                if(comment.getAnswerBackTo()!=null) {
//                    html.append("<div class=\"swb-commts-ans\" id=\"cmmt_"+comment.getId()+"\">");
//                    html.append(paramRequest.getLocaleString("inAnswerTo")+"&nbsp;");
//                    html.append(comment.getAnswerBackTo().getCreator()==null?comment.getAnswerBackTo().getName():comment.getAnswerBackTo().getCreator().getFullName());
//                    html.append("&nbsp;<a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setCallMethod(SWBParamRequest.Call_DIRECT).setMode(Mode_DETAIL).setParameter("cmmt", comment.getEncodedURI())+"','item_"+comment.getId()+"')\" title=\""+paramRequest.getLocaleString("showComment")+"\">("+paramRequest.getLocaleString("showComment")+")</a>");
//                    html.append("</div>");
//                }else {
                    html.append("   <div class=\"swb-commts-ans\" id=\"cmmt_"+comment.getId()+"\"></div>");
//                }
                //réplica a comentario-inicio
                Iterator<CommentToElement> answers = comment.listAnswerBacks();
                if(answers.hasNext())
                {
                    answers = SWBComparator.sortByCreated(answers, false);
                    html.append("  <ul class=\"swb-comments-anslst\">");
                    while(answers.hasNext()) {
                        comment = answers.next();
                        html.append("  <li class=\"swb-cmmts-ansitem\">");
                        html.append("   <p class=\"swb-cmmts-ansname\">"+(comment.getCreator()==null?comment.getName():comment.getCreator().getFullName()));
                        try {
                            html.append("&nbsp;<span class=\"swb-cmmts-ansago\">"+paramRequest.getLocaleString("ago")+"&nbsp;"+SWBUtils.TEXT.getTimeAgo(comment.getCreated(), lang)+"</span>");
                        }catch(Exception e) {
                        }
                        html.append("   </p>");
                        html.append("   <p class=\"swb-cmmts-anscmmt\">"+comment.getCommentToElement()+"</p>");
                        html.append("  </li>");
                    }
                    html.append("  </ul>");                        
                }
                //réplica a comentario-fin
                html.append("  </li>");
            }
            html.append(" </ul>");
//            html.append("</div>");
            
        }else {
            html.append("<p class=\"swb-comments-noe\">"+paramRequest.getLocaleString("noComment")+"</p>");
        }
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
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        
        PrintWriter out = response.getWriter();
        SWBClass element = getSWBClassObject(request.getParameter("suri"));
        if(element==null) {
            out.println(paramRequest.getLocaleString("noElement"));       
        }else {
            out.println(renderListComments(request, paramRequest, element));
        }
        out.flush();
        out.close();
    }
    
    private String renderListComments(HttpServletRequest request, SWBParamRequest paramRequest, SWBClass element) throws SWBResourceException
    {
        StringBuilder html = new StringBuilder();
        final User user = paramRequest.getUser();
        final String lang = user.getLanguage()==null?"es":user.getLanguage();
        String name;
        
        Iterator<CommentToElement> comments = listCommentToElementByElement(element, paramRequest.getWebPage().getWebSite());
        if(comments.hasNext())
        {
            comments = SWBComparator.sortByCreated(comments, false);
            List<CommentToElement> commentsList = SWBUtils.Collections.copyIterator(comments);
            
            //html.append(" <div class=\"swb-comments-box\" id=\"commts\">");
            html.append("  <ul class=\"swb-comments-lst\">");
            
            int fctr;
            try {
                fctr = (Integer)request.getSession(true).getAttribute(FCTR);
            }catch(Exception e) {
                fctr = 1;
            }
            fctr++;
            request.getSession(true).setAttribute(FCTR, fctr);
            fctr *= getBlockSize(); 
            
            comments = commentsList.iterator();
            CommentToElement comment;
            for(int i=fctr; i>0&&comments.hasNext(); i--) {
                comment = comments.next();
//                if(!comment.isValid() || !user.haveAccess(comment)) {
//                    continue;
//                }
                html.append("  <li class=\"swb-cmmts-item\" id=\"item_"+comment.getId()+"\">");
                html.append("   <p class=\"swb-cmmts-name\">"+(comment.getCreator()==null?comment.getName():comment.getCreator().getFullName()));
                try {
                    html.append("&nbsp;<span class=\"swb-cmmts-ago\">"+paramRequest.getLocaleString("ago")+"&nbsp;"+SWBUtils.TEXT.getTimeAgo(comment.getCreated(), lang)+"</span>");
                }catch(Exception e) {
                }
                html.append("   </p>");
                html.append("   <p class=\"swb-cmmts-cmmt\">"+comment.getCommentToElement()+"</p>");
                html.append("   <p><a href=\"#\" onclick=\"answerBack('cmmt_"+comment.getId()+"','child_"+comment.getId()+"', 'last','"+comment.getId()+"');return false;\">"+paramRequest.getLocaleString("answerBack") +"</a></p>");
//                if(comment.getAnswerBackTo()!=null) {
//                    html.append("<div class=\"swb-commts-ans\" id=\"cmmt_"+comment.getId()+"\">");
//                    html.append(paramRequest.getLocaleString("inAnswerTo")+"&nbsp;");
//                    html.append(comment.getAnswerBackTo().getCreator()==null?comment.getAnswerBackTo().getName():comment.getAnswerBackTo().getCreator().getFullName());
//                    html.append("&nbsp;<a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setCallMethod(SWBParamRequest.Call_DIRECT).setMode(Mode_DETAIL).setParameter("cmmt", comment.getEncodedURI())+"','item_"+comment.getId()+"')\" title=\""+paramRequest.getLocaleString("showComment")+"\">("+paramRequest.getLocaleString("showComment")+")</a>");
//                    html.append("</div>");
//                }else {
                    html.append("   <div class=\"swb-commts-ans\" id=\"cmmt_"+comment.getId()+"\"></div>");
//                }
                //réplica a comentario-inicio
                Iterator<CommentToElement> answers = comment.listAnswerBacks();
                if(answers.hasNext())
                {
                    answers = SWBComparator.sortByCreated(answers, false);
                    html.append("  <ul class=\"swb-comments-anslst\">");
                    while(answers.hasNext()) {
                        comment = answers.next();
                        html.append("  <li class=\"swb-cmmts-ansitem\">");
                        html.append("   <p class=\"swb-cmmts-ansname\">"+(comment.getCreator()==null?comment.getName():comment.getCreator().getFullName()));
                        try {
                            html.append("&nbsp;<span class=\"swb-cmmts-ansago\">"+paramRequest.getLocaleString("ago")+"&nbsp;"+SWBUtils.TEXT.getTimeAgo(comment.getCreated(), lang)+"</span>");
                        }catch(Exception e) {
                        }
                        html.append("   </p>");
                        html.append("   <p class=\"swb-cmmts-anscmmt\">"+comment.getCommentToElement()+"</p>");
                        html.append("  </li>");
                    }
                    html.append("  </ul>");                        
                }
                //réplica a comentario-fin
                html.append("  </li>");
            }
            html.append(" </ul>");
            if(commentsList.size()>fctr) {
                html.append("<p><a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(SWBResourceURL.Mode_INDEX).setParameter("suri",element.getEncodedURI()).setParameter(FCTR,"_")+"','commts')\" title=\""+paramRequest.getLocaleString("viewMore")+"\">"+paramRequest.getLocaleString("viewMore")+"</a></p>");
                if(commentsList.size()>fctr*5) {
                    html.append("<p><a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(SWBResourceURL.Mode_INDEX).setParameter("suri",element.getEncodedURI())+"','commts')\" title=\""+paramRequest.getLocaleString("viewAllComments")+"\">"+paramRequest.getLocaleString("viewAllComments")+"&nbsp;&raquo;</a></p>");
                }
            }
            //html.append("</div>");            
        }else {
            html.append("<p class=\"swb-commts-noe\">"+paramRequest.getLocaleString("noComment")+"</p>");
        }
        return html.toString();
    }
    
    private SWBClass getSWBClassObject(final String suri)
    {
        SWBClass element = null;
        String suri_ = suri;
        if(suri_== null) {
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
    
    public Iterator<CommentToElement> listCommentToElementByElement(SWBClass value, org.semanticwb.model.SWBModel model)
    {
        Iterator<CommentToElement> it = CommentToElement.ClassMgr.listCommentToElementByElement(value, model);
        List<CommentToElement> lst = SWBUtils.Collections.copyIterator(it);
        List<CommentToElement> rev = new ArrayList<CommentToElement>();
        CommentToElement comment;
        for(int i=0; i<lst.size(); i++) {
            comment = lst.get(i);
            if(comment.getAnswerBackTo()!=null) {
                //lst.remove(i);
                rev.add(comment);
            }
        }
        lst.removeAll(rev);
        return lst.iterator();
    }
    
    /*
    private String renderListComments(SWBParamRequest paramRequest, Iterator<CommentToElement> icomments) throws SWBResourceException
    {
        StringBuilder html = new StringBuilder();
        final User user = paramRequest.getUser();
        final String lang = user.getLanguage()==null?"es":user.getLanguage();
        String name;
        
        icomments = SWBComparator.sortByCreated(icomments, false);
        if(icomments.hasNext())
        {
            html.append(" <div class=\"swb-comments-box\" id=\"commts\">");
            html.append("  <ul class=\"swb-comments-lst\">");
            for(int i=getBlockSize(); i>0&&icomments.hasNext(); i--) {
                CommentToElement comment = icomments.next();
                if(!comment.isValid() || !user.haveAccess(comment)) {
                    continue;
                }
                html.append("  <li class=\"swb-cmmts-item\" id=\"item_"+comment.getId()+"\">");
                html.append("   <p class=\"swb-cmmts-name\">"+(comment.getCreator()==null?comment.getName():comment.getCreator().getFullName()));
                try {
                    html.append("&nbsp;<span class=\"swb-cmmts-ago\">"+paramRequest.getLocaleString("ago")+"&nbsp;"+SWBUtils.TEXT.getTimeAgo(comment.getCreated(), lang)+"</span>");
                }catch(Exception e) {
                }
                html.append("   </p>");
                html.append("   <p class=\"swb-cmmts-cmmt\">"+comment.getCommentToElement()+"</p>");
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
            html.append("</div>");
            
        }else {
            html.append("<p class=\"swb-comments-noe\">"+paramRequest.getLocaleString("noComment")+"</p>");
        }
        return html.toString();
    }
     */
}
