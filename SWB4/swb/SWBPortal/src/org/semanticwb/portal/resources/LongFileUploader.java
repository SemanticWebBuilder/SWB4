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
 * http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author serch
 */
public class LongFileUploader extends GenericResource {

    private static Logger log = SWBUtils.getLogger(LongFileUploader.class);
    private String path = "/deposit/";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) 
            throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();

//        String req = request.getRequestURL().toString();
//        req = req.substring(0,req.indexOf("/",9)+1);
//        System.out.println("getRequestURL:"+req);

        String id = SWBUtils.TEXT.replaceSpecialCharacters(
                paramRequest.getResourceBase().getTitle(), true);
        String url = "/bduplaoder";
        out.println("<script src=\"/swbadmin/js/longfu/json2.js\"></script>"
                + "<script src=\"/swbadmin/js/longfu/swblongfileuploader.js\">"
                + "</script><script type=\"text/javascript\">"
                + "var lfu = new LongFileUploader(\"" + url + "\",\"" + path + 
                "\");</script>");
        out.println("<div id=\"" + id + "\"><form>file: <input type=\"file\" "
                + "name=\"updfile\" id=\"updfile\" "
                + "onchange=\"lfu.sendFile(this)\"/></form></div>");


    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, 
    SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String frmPath = getResourceBase().getAttribute("frmPath");

        String act = request.getParameter("act");
        if (act != null) {
            frmPath = request.getParameter("frmPath");
            getResourceBase().setAttribute("frmPath", frmPath);
            try {
                getResourceBase().updateAttributesToDB();
                path = frmPath;
            } catch (Exception e) {
                log.error(e);
            }
        }

out.println("<script type=\"text/javascript\">");
    out.println("  dojo.require(\"dijit.form.Form\");");
    out.println("  dojo.require(\"dijit.form.Button\");");
    out.println("  dojo.require(\"dijit.form.CheckBox\");");
    out.println("</script>");

    out.println("<div class=\"swbform\">");
    out.println(new StringBuilder().append(
            "<form dojoType=\"dijit.form.Form\" id=\"").append(
            getResourceBase().getId()).append("/frmPath\" action=\"").append(
            paramRequest.getRenderUrl()).append("\" method=\"post\" >"
            ).toString());
    out.println("<input type=\"hidden\" name=\"act\" value=\"upd\">");

    out.println("<fieldset>");
    out.println("<legend>Configuraci&oacute;n ruta</legend>");
    out.println("<br/>");
    out.println("Ruta para depositar los archivos que se envíen en la forma "
            + "/ruta/ relativa a /work y debe existir previamente");
    out.println("<br/>");
    out.print("<input name=\"frmPath\" value=\""+frmPath+"\">");
    
    
    out.println("<br/>");
    
    out.println("</fieldset>");
    out.println("<fieldset>");
    out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" "
            + "name=\"submit/btnSend\" >Enviar</button>");
    out.println("</fieldset>");
    out.println("</form>");
    out.println("</div>");

    }

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base); 
        path = base.getAttribute("frmPath");
    }
    
    
    
}
