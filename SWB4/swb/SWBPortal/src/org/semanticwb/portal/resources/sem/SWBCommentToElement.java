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
import java.io.UnsupportedEncodingException;
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
    public static final int MIN_LIST = 5;
    
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
        String suri = request.getParameter("suri");
        if(action.equals(SWBActionResponse.Action_ADD)) {
            String securCodeSent = request.getParameter("cmnt_seccode");
            String securCodeCreated = (String)request.getSession(true).getAttribute("cs");
            if( securCodeCreated!=null && securCodeCreated.equalsIgnoreCase(securCodeSent) )
            {
                suri = URLDecoder.decode(suri, "UTF-8");
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
                }
                if(element!=null && element.isValid())
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
        }else {
            super.processAction(request, response);
        }
        
        String lang = response.getUser().getLanguage();
        response.sendRedirect(response.getWebPage().getUrl(lang)+"?suri="+URLEncoder.encode(suri,"UTF-8"));
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
        PrintWriter out = response.getWriter();
        
        SWBClass element = getSWBClassObject(request.getParameter("suri"));
        if(element==null) {
            out.println(paramRequest.getLocaleString("noElement"));
            return;            
        }

        SWBResourceURL rUrl = paramRequest.getActionUrl();
        rUrl.setAction(SWBParamRequest.Action_ADD);
        rUrl.setParameter("suri", element.getEncodedURI());

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
        
        String comment = request.getParameter("cmnt_comment")==null?"":request.getParameter("cmnt_comment");
        out.println("<div class=\"swb-comentario-sem\">");
        out.println("<h2>"+paramRequest.getLocaleString("add")+"</h2>");
        out.println("<form name=\"cmnt\" id=\"cmnt\" action=\""+rUrl+"\" method=\"post\">\n");
        User user = paramRequest.getUser();
        if(!user.isSigned()) {
            out.println("<div class=\"swb-comentario-sem-name\">");
            out.println("  <label for=\"cmnt_seccode\">"+paramRequest.getLocaleString("nameLabel")+"</label>");
            out.println("  <input type=\"text\" id=\"name\" name=\"name\" />");
            out.println("</div>");
            
            out.println("<div class=\"swb-comentario-sem-email\">");
            out.println("  <label for=\"cmnt_seccode\">"+paramRequest.getLocaleString("emailLabel")+"</label>");
            out.println("  <input type=\"text\" id=\"email\" name=\"email\" />");
            out.println("</div>");
        }
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
        
        out.println(renderListComments(paramRequest, element.getEncodedURI()));
        
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
    private String renderListComments(SWBParamRequest paramRequest, final String suri) throws SWBResourceException {
        StringBuilder html = new StringBuilder();
        final User user = paramRequest.getUser();
        final String lang = user.getLanguage()==null?"es":user.getLanguage();
        String name;

//        SWBClass element = null;
//        try {
//            element = (SWBClass)SemanticObject.createSemanticObject(suri).createGenericInstance();
//        }catch(Exception e) {
//            log.error(e);
//            return html.toString();
//        }
        SWBClass element = getSWBClassObject(suri);
        if(element==null) {
            return paramRequest.getLocaleString("noElement");
        }
        
        Iterator<CommentToElement> icomments = CommentToElement.ClassMgr.listCommentToElementByElement(element, paramRequest.getWebPage().getWebSite());
        icomments = SWBComparator.sortByCreated(icomments, false);
        //List comments = SWBUtils.Collections.copyIterator(icomments);
        //long totalCmmts = comments.size();
        
        html.append("<div class=\"swb-semcomments\">");
        //if(!comments.isEmpty()) {
        if(icomments.hasNext()) {
            html.append(" <p class=\"swb-semcommts-lblcmmt\">"+paramRequest.getLocaleString("lblComments")+"</p>");
            //icomments = comments.iterator();
            html.append(" <div class=\"swb-semcommts-box\" id=\"commts\">");
            html.append("  <ul class=\"swb-semcommts-lst\">");
            int min = MIN_LIST;
            while(min>0 && icomments.hasNext()) {
                CommentToElement comment = icomments.next();
                if(!comment.isValid() || !user.haveAccess(comment)) {
                    continue;
                }
                html.append("  <li class=\"swb-semcommts-item\">");
                if(comment.getName()==null && user.isSigned()) {
                    html.append("   <p class=\"swb-semcommts-name\">"+(comment.getCreator()==null?"":comment.getCreator().getFullName()));
                }else {
                    html.append("   <p class=\"swb-semcommts-name\">"+comment.getName()==null?"":comment.getName());
                }
                try {
                    html.append("&nbsp;<span class=\"swb-semcommts-ago\">"+paramRequest.getLocaleString("ago")+"&nbsp;"+SWBUtils.TEXT.getTimeAgo(comment.getCreated(), lang)+"</span>");
                }catch(Exception e) {
                }
                html.append("   </p>");
                html.append("   <p class=\"swb-semcommts-cmmt\">"+comment.getCommentToElement()+"</p>");
                html.append("  </li>");
                min--;
            }
            html.append(" </ol>");
            html.append(" <p><a href=\"#\" onclick=\"postHtml('"+paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(SWBResourceURL.Mode_INDEX).setParameter("suri",element.getEncodedURI())+"','commts')\">"+paramRequest.getLocaleString("viewAllComments") +"&nbsp;&raquo;</a></p>");
            html.append("</div>");
        }else {
            html.append("<p class=\"swb-semcommts-noe\">"+paramRequest.getLocaleString("noComment")+"</p>");
        }
        html.append("</div>");
        return html.toString();
    }

    @Override
    public void doIndex(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
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
            html.append(" <p class=\"swb-semcommts-lblcmmt\">"+paramRequest.getLocaleString("lblComments")+"</p>");
            html.append(" <ol>");
            while(comments.hasNext()) {
                CommentToElement comment = comments.next();
                if(!comment.isValid() || !user.haveAccess(comment)) {
                    continue;
                }
                html.append("  <li>");
                if(comment.getName()==null && user.isSigned()) {
                    name = comment.getCreator().getFullName();
                }else {
                    name = comment.getName();
                }
                try {
                    html.append("&nbsp;"+paramRequest.getLocaleString("ago")+"&nbsp;"+SWBUtils.TEXT.getTimeAgo(comment.getCreated(), lang));
                }catch(Exception e) {
                }
                html.append("</p>");
                html.append("<p>"+comment.getCommentToElement()+"</p>");
                html.append("  </li>");
            }
            html.append(" </ol>");
        }
        out.println(html.toString());

    }
    
    private SWBClass getSWBClassObject(final String suri) {
        SWBClass element = null;
        String suri_ = suri;
        if(suri_==null) {
            return element;
        }
        try {
            suri_ = URLDecoder.decode(suri, "UTF-8");
        }catch(UnsupportedEncodingException unsee) {
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
