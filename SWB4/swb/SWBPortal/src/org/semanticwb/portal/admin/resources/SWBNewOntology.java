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
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Ontology;
import org.semanticwb.model.SWBContext;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBNewOntology.
 * 
 * @author jorge.jimenez
 */
public class SWBNewOntology extends GenericResource {
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBNewOntology.class);

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            PrintWriter out = response.getWriter();
            String action = paramRequest.getAction();
            SWBResourceURL url = paramRequest.getRenderUrl();
            if (action != null && action.trim().equals("step2")) {
                String title = request.getParameter("wstitle");
                String id = request.getParameter("wsid");
                String ns = request.getParameter("ns");
                String wstype = request.getParameter("wstype");

                Ontology ontology = null;
                //String ns="http://ont." + id + ".swb#";
                if(wstype==null)
                {
                    ontology = SWBContext.createOntology(id, ns);
                }else
                {
                    SemanticMgr mgr=SWBPlatform.getSemanticMgr();
                    SemanticClass sclass=mgr.getVocabulary().getSemanticClassById(wstype);
                    if(sclass!=null)
                    {
                        SemanticModel model=mgr.createModel(id, ns);
                        ontology=(Ontology)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
                    }
                }

                ontology.getSemanticObject().getModel().setTraceable(false);
                //site.setCreated(new java.util.Date(System.currentTimeMillis()));
                ontology.setTitle(title);
                
                //Envia estatus a pantalla
                out.println("<script type=\"text/javascript\">");
                out.println("hideDialog();");
                out.println("addItemByURI(montStore, null, '" + ontology.getURI() + "');");
                out.println("showStatus('Ontologia Creada');");
                out.println("</script>");

                ontology.getSemanticObject().getModel().setTraceable(false);
            } else { //Forma de entrada(Datos iniciales)
                url.setAction("step2");
                getStep1(out, url, paramRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e);
        }
    }

    /**
     * Gets the step1.
     *
     * @param out the out
     * @param url the url
     * @param paramRequest the param request
     * @return the step1
     */
    private void getStep1(PrintWriter out, SWBResourceURL url, SWBParamRequest paramRequest)
    {
        try {

            out.println("<form class=\"swbform\" id=\"frmImport1\" action=\"" + url.toString() + "\" dojoType=\"dijit.form.Form\" onSubmit=\"submitForm('frmImport1');try{document.getElementById('csLoading').style.display='inline';}catch(noe){};return false;\" method=\"post\">");
            out.println("<fieldset>");
            out.println("<table>");
            out.append("<tr><td align=\"right\">");
            out.println(paramRequest.getLocaleString("msgwsTitle")+" <em>*</em>");
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" name=\"wstitle\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Titulo.\" invalidMessage=\"Titulo es requerido.\" onkeyup=\"dojo.byId('swb_create_id').value=replaceChars4Id(this.textbox.value);dijit.byId('swb_create_id').validate();dojo.byId('swb_create_ns').value='http://ont.'+replaceChars4Id(this.textbox.value)+'.swb#';dijit.byId('swb_create_ns').validate()\" trim=\"true\" >");
            out.println("</td>");
            out.append("</tr>");
            out.append("<tr><td align=\"right\">");
            out.println(paramRequest.getLocaleString("msgwsID")+" <em>*</em>");
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" id=\"swb_create_id\" name=\"wsid\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Identificador.\" isValid=\"return canCreateSemanticObject(this.textbox.value);\" invalidMessage=\"Identificador invalido.\" trim=\"true\"  onkeyup=\";dojo.byId('swb_create_ns').value='http://ont.'+this.textbox.value+'.swb#';dijit.byId('swb_create_ns').validate()\" >");
            out.println("</td>");
            out.append("</tr>");
            out.append("<tr><td align=\"right\">");
            out.println(paramRequest.getLocaleString("msgwsNS")+" <em>*</em>");
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" id=\"swb_create_ns\" name=\"ns\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Namespace.\" isValid_=\"return canCreateSemanticObject(this.textbox.value);\" invalidMessage=\"Namespace invalido.\" trim=\"true\" >");
            out.println("</td>");
            out.append("</tr>");
           
            out.println("<td colspan=\"2\" align=\"center\">");
            out.println("<button dojoType='dijit.form.Button' type=\"submit\">"+paramRequest.getLocaleLogString("save")+"</button>");
            out.println("<button dojoType='dijit.form.Button' onclick=\"dijit.byId('swbDialog').hide();\">"+paramRequest.getLocaleLogString("cancel")+"</button>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("<span id=\"csLoading\" style=\"width: 100px; display: none\" align=\"center\">&nbsp;&nbsp;&nbsp;<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\"/></span>");
        } catch (Exception e) {
            log.debug(e);
        }
    }
}
