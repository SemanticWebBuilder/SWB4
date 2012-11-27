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
package org.semanticwb.sip;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author martha.jimenez
 */
public class ExternalLinks extends GenericResource{

    private static Logger log = SWBUtils.getLogger(ExternalLinks.class);
    ArrayList links = new ArrayList();

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        ArrayList list=new ArrayList();
        Resource base = paramRequest.getResourceBase();
        String title="";
        Iterator atrib = base.getAttributeNames();
        while(atrib.hasNext()){
            String attr = atrib.next().toString();
            if(attr.startsWith("name")){
                String x = attr.substring(4);
                String name = base.getAttribute(attr);
                String url=base.getAttribute("link"+x);
                list.add(url);
                list.add(name);
            }
            if(attr.equals("title")){
                title=base.getAttribute("title");
            }
        }
        int pos=title.indexOf(' ');
        String title1 =title.substring(0,pos);
        String title2 = title.substring(pos);
        Iterator it = list.iterator();
        int count=0;
        out.println("<div class=\"masLigas\">");
        out.println("    <h2 class=\"tituloBloque\">"+title1+"<span class=\"span_tituloBloque\">"+title2+"</span></h2>");
        out.println("      <ul>");
        while(it.hasNext()){
           if(count<8){
              count=count+1;
              String ref = it.next().toString();
              String titleRef = it.next().toString();
              out.println("         <li><a href=\""+ref+"\">"+titleRef+"</a></li>");
            }
         }
         out.println("      </ul>");
         out.println("</div>");
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = paramRequest.getResourceBase();
        PrintWriter out = response.getWriter();
        SWBResourceURL addLink = paramRequest.getRenderUrl();
        SWBResourceURL urltitle = paramRequest.getActionUrl();
        SWBResourceURL urlremov = paramRequest.getActionUrl();
        SWBResourceURL urlact=paramRequest.getRenderUrl();
        Iterator<String> it = base.getAttributeNames();
        String title="";
        addLink.setMode("addLink");
        urlremov.setAction("removLink");
        urltitle.setAction("addTitle");
        urlact.setMode("actLink");
        out.println("<script type=\"text/javascript\">");
        out.println(" dojo.require(\"dijit.form.ValidationTextBox\");");
        out.println(" dojo.require(\"dijit.form.Button\");");
        out.println("function valida() {");
        out.println("    var val = true;");
        out.println("    if(document.getElementById(\"title\").value==\"\") {");
        out.println("        alert('¡Debe ingresar el T&iacute;tulo del Bloque!');");
        out.println("        document.getElementById(\"title\").focus();");
        out.println("        val false;");
        out.println("    }");
        out.println("    return val");
        out.println("}");
        out.println("</script>");
        while(it.hasNext()){
            String attr = it.next();
            if(attr.equals("title")){
                title=base.getAttribute(attr);
            }
        }
        out.println("<form name=\"frmtitle\" id=\"frmtitle\" class=\"swbform\" method=\"post\" action=\""+urltitle+"\" onsubmit=\"return valida(this);\" onsubmit=\"return valida(this);\">");
        out.println("<p>");
        out.println("         <label for=\"title\">T&iacute;tulo del Bloque: </label>");
        out.println("         <input name=\"title\" id=\"title\" type=\"text\" size=\"45\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" value=\""+title+"\">");
        out.println("</p>");
        out.println("<p>");
        out.println("        <input type=\"submit\" name=\"Cambiar titulo\" >");
        out.println("</p>");
        out.println("        <a href=\""+addLink+"\">Agregar Links</a><br />");
        it = base.getAttributeNames();
        out.println("        <table dojoType=\"dojox.Grid\" CELLSPACING=6 CELLPADDING=8>");
        out.println("           <tr><th width=\"250\">Url</th>");
        out.println("               <th width=\"250\"> Nombre de la Url</th>");
        out.println("               <th> Eliminar</th>");
        out.println("               <th> Actualizar</th></tr>");
        while(it.hasNext()){
            String attr = it.next();
            if(attr.startsWith("name")){
                String x = attr.substring(4);
                out.println("                 <tr>");
                String name = base.getAttribute(attr);
                String url=base.getAttribute("link"+x);
                urlremov.setParameter("name", attr);
                urlact.setParameter("name", attr);
                out.println("                   <td>"+url+"</td>");
                out.println("                   <td>"+name+"</td>");
                out.println("                   <td><a href=\""+urlremov+"\">Eliminar</a></td>");
                out.println("                   <td><a href=\""+urlact+"\">Actualizar</a></td>");
                out.println("                 </tr>");
            }
        }
        out.println("        </table>");
        out.println("</form>");
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if(mode.equals("addLink"))
            addLink(request, response, paramRequest);
        else if(mode.equals("actLink"))
            doEdit(request, response, paramRequest);
        else
            super.processRequest(request, response, paramRequest);
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = paramRequest.getResourceBase();
        SWBResourceURL urlacts= paramRequest.getActionUrl();
        urlacts.setAction("update");
        String attrib = request.getParameter("name");
        urlacts.setParameter("name", attrib);
        String x = attrib.substring(4);
        String name = base.getAttribute(attrib);
        String url=base.getAttribute("link"+x);
        out.println("<script type=\"text/javascript\">");
        out.println(" dojo.require(\"dijit.form.ValidationTextBox\");");
        out.println(" dojo.require(\"dijit.form.Button\");");
        out.println("function validaForma() {");
        out.println("    var val = true;");
        out.println("    if(document.getElementById(\"nameLink\").value==\"\") {");
        out.println("        alert('¡Debe ingresar el nombre de la Liga!');");
        out.println("        document.getElementById(\"nameLink\").focus();");
        out.println("        val false;");
        out.println("    }");
        out.println("    if(document.getElementById(\"link\").value==\"\") {");
        out.println("        alert('¡Debe ingresar la Liga!');");
        out.println("        document.getElementById(\"link\").focus();");
        out.println("        val false;");
        out.println("    }");
        out.println("    return val");
        out.println("}");
        out.println("</script>");
        out.println("<form name=\"frmEditLink\" id=\"frmEditLink\" class=\"swbform\" method=\"post\" action=\""+urlacts+"\" onsubmit=\"return validaForma(this);\">");
        out.println("    <input type=\"hidden\" name=\"title\" value=\""+request.getParameter("title")+"\"/>");
        out.println("    <fieldset>");
        out.println("        <legend>Modificar Link</legend>");
        out.println("        <p>");
        out.println("         <label for nameLink>Nombre de la Liga: </label><br/>");
        out.println("         <input name=\"nameLink\" type=\"text\" size=\"45\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" value=\""+name+"\">");
        out.println("        </p>");
        out.println("        <p>");
        out.println("         <label for link>Liga: </label><br/>");
        out.println("         <input name=\"link\" type=\"text\" size=\"45\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\"  value=\""+url+"\">");
        out.println("        </p>");
        out.println("        <input type=\"submit\" name=\"Enviar\" value=\"Enviar\">");
        out.println("    </fieldset>");
        out.println("</form>");
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        Resource base = response.getResourceBase();
        if(action.equals("addsLink"))
        {
            int indice;
            try {
                indice = Integer.parseInt(base.getAttribute("indice", "0"));
            }catch(NumberFormatException er) {
                indice=0;
            }
            String name = "name"+indice;
            String nlink = "link"+indice;
            base.setAttribute("indice", Integer.toString(++indice));
            base.setAttribute(name, request.getParameter("nameLink"));
            base.setAttribute(nlink, request.getParameter("link"));
            try{
                base.updateAttributesToDB();
            }catch(SWBException e){
                log.error(e);
            }
        }else if(action.equals("addTitle"))
        {
            try{
                base.setAttribute("title", request.getParameter("title"));
            }catch(Exception e){
            }
        }else if(action.equals("removLink")){
            String attrib = request.getParameter("name");
            String x = attrib.substring(4);
            String url="link"+x;
            base.removeAttribute(attrib);
            base.removeAttribute(url);
            base.removeAttribute(x);
            try{
                base.updateAttributesToDB();
            }catch(SWBException e){
                log.error(e);
            }
        }else if(action.equals("update")){
            String attrib = request.getParameter("name");
            String x = attrib.substring(4);
            String nlink="link"+x;
            base.setAttribute(attrib, request.getParameter("nameLink"));
            base.setAttribute(nlink, request.getParameter("link"));
            try{
                base.updateAttributesToDB();
            }catch(SWBException e){
                log.error(e);
            }
        }
        response.setMode(response.Mode_ADMIN);
    }

    public void addLink(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL addLinks = paramRequest.getActionUrl();
        addLinks.setAction("addsLink");
        out.println("<script type=\"text/javascript\">");
        out.println(" dojo.require(\"dijit.form.ValidationTextBox\");");
        out.println(" dojo.require(\"dijit.form.Button\");");
        out.println("function validaForma() {");
        out.println("    var val = true;");
        out.println("    if(document.getElementById(\"nameLink\").value==\"\") {");
        out.println("        alert('¡Debe ingresar el nombre de la Liga!');");
        out.println("        document.getElementById(\"nameLink\").focus();");
        out.println("        val false;");
        out.println("    }");
        out.println("    if(document.getElementById(\"link\").value==\"\") {");
        out.println("        alert('¡Debe ingresar la Liga!');");
        out.println("        document.getElementById(\"link\").focus();");
        out.println("        val false;");
        out.println("    }");
        out.println("    return val");
        out.println("}");
        out.println("</script>");
        out.println("<form name=\"frmNewLink\" id=\"frmNewLink\" class=\"swbform\" method=\"post\" action=\""+addLinks+"\" onsubmit=\"return validaForma(this);\">");
        out.println("    <input type=\"hidden\" name=\"title\" value=\""+request.getParameter("title")+"\"/>");
        out.println("    <fieldset>");
        out.println("        <legend>Añadir Link</legend>");
        out.println("        <p>");
        out.println("         <label for nameLink>Nombre de la Liga: </label><br/>");
        out.println("         <input name=\"nameLink\" type=\"text\" size=\"45\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\">");
        out.println("        </p>");
        out.println("        <p>");
        out.println("         <label for link>Liga: </label><br/>");
        out.println("         <input name=\"link\" type=\"text\" size=\"45\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\">");
        out.println("        </p>");
        out.println("        <input type=\"submit\" name=\"Enviar\" value=\"Enviar\">");
        out.println("    </fieldset>");
        out.println("</form>");
    }
}
