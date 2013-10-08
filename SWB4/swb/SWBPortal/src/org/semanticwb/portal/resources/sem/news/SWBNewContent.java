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
package org.semanticwb.portal.resources.sem.news;

// TODO: Auto-generated Javadoc
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.ResourceCollection;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 * The Class SWBNewContent.
 */
public class SWBNewContent extends org.semanticwb.portal.resources.sem.news.base.SWBNewContentBase {
    public static final String LOCAL_IP = "127.0.0.1";

    private static final Logger log = SWBUtils.getLogger(SWBNewContent.class);

    /**
     * Instantiates a new sWB new content.
     */
    public SWBNewContent() {
    }

    /**
     * Instantiates a new sWB new content.
     * 
     * @param base the base
     */
    public SWBNewContent(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doAdmin(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        
        SWBFormMgr mgr = new SWBFormMgr(getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
        mgr.setSubmitByAjax(true);
        mgr.setFilterHTMLTags(false);
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.setType(SWBFormMgr.TYPE_DOJO);

        boolean sndTwr = false;

        String cntUrl = null;
        String mymsg = "";
        

        if ("update".equals(paramRequest.getAction())) {
            try {
                mgr.processForm(request);
            } catch (FormValidateException e) {
                log.error(e);
            }

            SWBResourceURL url = paramRequest.getRenderUrl();
            url.setAction(null);
            url.setParameter("updated", "true");

            response.sendRedirect(url.toString());

        } else {

            if (request.getParameter("updated") != null && request.getParameter("updated").equals("true")) {
                SemanticObject so = getSemanticObject();
                GenericObject go = so.createGenericInstance();
                SWBNewContent ncnt = null;

                if (go instanceof SWBNewContent) {
                    ncnt = (SWBNewContent) go;
//                    System.out.println("Si es SWBNewContent");
                }

                String valprop = so.getProperty(SWBNewContent.swbnews_sendTwitter);


                if (null != valprop && "true".equals(valprop) && null != ncnt && ncnt.getResource().isActive()) {



                    ResourceType rtype = ncnt.getResource().getResourceType();
                    ResourceCollection rcoll=null;
                    WebPage dwp = null;
                    if(rtype.getResourceCollection()!=null)
                    {

                        rcoll = rtype.getResourceCollection();

                    }
                    if(null!=rcoll)
                    {

                        dwp = rcoll.getDisplayWebPage();

                    }

                    sndTwr = true;



                    String protocol = request.getProtocol();

                    if(protocol!=null&&protocol.startsWith("HTTP/")) protocol = "http://";
                    else if(protocol!=null&&protocol.startsWith("HTTPS/")) protocol = "https://";
                    String srvrname = request.getServerName();

                    if(srvrname!=null && "localhost".equalsIgnoreCase(srvrname))
                    {
                        srvrname = request.getRemoteAddr();
                        //if(srvrname.equals("127.0.0.1")) srvrname="www.infotec.com.mx";
                        if(srvrname.equals(LOCAL_IP)) 
                        {
                            srvrname=request.getLocalName();
                        }
                    }

                    int srvrport = request.getServerPort();
                    String ctxpath = SWBPlatform.getContextPath();
                    String wpurl = "";
                    if(dwp!=null)
                    {
                        wpurl = dwp.getUrl();
                    }

                    cntUrl = protocol + srvrname;

                    if(srvrport!=80) cntUrl += ":"+ srvrport;

                    cntUrl += ctxpath+wpurl;
                    cntUrl = URLEncoder.encode(cntUrl);
                    cntUrl += "?uri="+ ncnt.getResourceBase().getSemanticObject().getId();
                    mymsg=URLEncoder.encode(ncnt.getOriginalTitle());


                }
            }
            mgr.setAction(paramRequest.getRenderUrl().setAction("update").toString());

            out.println(mgr.renderForm(request));

            if (sndTwr) {

                out.println("<script src=\"http://platform.twitter.com/widgets.js\" type=\"text/javascript\"></script>");
                out.println("<fieldset>");
                out.println("<p align=\"center\">");
                out.println("  Si no abre la ventana pop-up da clic en el botón&nbsp;<button dojoType=\"dijit.form.Button\" onclick=\"window.open('http://twitter.com/share?url="+cntUrl+"&amp;text="+mymsg+"','Twitter','width=550,height=280,top=100,left=200');\" title=\"enviar a twitter\" > tweet </button>"); //style=\"display: block;padding: 2px 5px 2px 20px;background: url('http://a4.twimg.com/images/favicon.ico') 1px center no-repeat;border: 1px solid #ccc;\" &amp;via=jafdeza
                out.println("</p>");
                out.println("</fieldset>");

                out.println("<script type=\"text/javascript\">");
                out.println(" mywin = window.open('http://twitter.com/share?url="+cntUrl+"&amp;text="+mymsg+"','Twitter','width=550,height=280,top=100,left=200');");
                out.println(" mywin.focus();");
                out.println("</script>");

            }

        }

    }
}
