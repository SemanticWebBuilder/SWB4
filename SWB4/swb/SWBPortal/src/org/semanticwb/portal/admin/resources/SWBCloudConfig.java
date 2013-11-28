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
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *Cloud Configuration Resource
 * @author serch
 */
public final class SWBCloudConfig extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SWBCloudConfig.class);

    /**
     * View Method for Cloud Configuration Resource
     * @param request the Request
     * @param response the Response
     * @param paramRequest the paramRequest
     * @throws SWBResourceException
     * @throws IOException 
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.print(jsElements());
        if (SWBPortal.getCloud()==null){
            out.println("<fieldset class=\"swbform\"><legend>Information</legend>"
                    + "<form></br></br></br></br></br>"
                    + "This function only works in an Cloud Environment..."
                    + "</br></br></br></br></br>"
                    + "</br></br></br></br></br></form></fieldset>");
        } else {
            SWBPortal.getCloud().doView(request, response, paramRequest, out);
        }
        
        
        
    }

    private static String jsElements() {
        return "    <script type=\"text/javascript\">\n"
                + "      // scan page for widgets and instantiate them\n"
                + "      dojo.require(\"dojo.parser\");\n"
                + "      dojo.require(\"dijit._Calendar\");\n"
                + "      dojo.require(\"dijit.ProgressBar\");\n\n"
                + "     // editor:\n"
                + "      dojo.require(\"dijit.Editor\");\n\n"
                + "      // various Form elemetns\n"
                + "      dojo.require(\"dijit.form.Form\");\n"
                + "      dojo.require(\"dijit.form.CheckBox\");\n"
                + "      dojo.require(\"dijit.form.Textarea\");\n"
                + "      dojo.require(\"dijit.form.FilteringSelect\");\n"
                + "      dojo.require(\"dijit.form.TextBox\");\n"
                + "      dojo.require(\"dijit.form.DateTextBox\");\n"
                + "      dojo.require(\"dijit.form.TimeTextBox\");\n"
                + "      dojo.require(\"dijit.form.Button\");\n"
                + "      dojo.require(\"dijit.form.NumberSpinner\");\n"
                + "      dojo.require(\"dijit.form.Slider\");\n"
                + "      dojo.require(\"dojox.form.BusyButton\");\n"
                + "      dojo.require(\"dojox.form.TimeSpinner\");\n"
                + "    </script>\n\n";
    }

    
    
}


