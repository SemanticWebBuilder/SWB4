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
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.Versionable;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBAVersionInfo.
 * 
 * @author juan.fernandez
 */
public class SWBAVersionInfo extends GenericResource {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBAVersionInfo.class);
    
    /** The webpath. */
    String webpath = SWBPlatform.getContextPath();
    
    /** The base. */
    Resource base;
    
    /** The ont. */
    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        base = getResourceBase();
        User user = paramRequest.getUser();
        PrintWriter out = response.getWriter();
        String id = request.getParameter("suri");
        if (null == id) {
            out.println("<fieldset>");
            out.println("URI faltante");
            out.println("</fieldset>");
        } else {
            String action = request.getParameter("act");
            GenericObject obj = ont.getGenericObject(id);

            log.debug("doView(), suri: "+id);
            VersionInfo via = null;
            VersionInfo vio = null;

            if (obj instanceof Versionable) {
                vio = (VersionInfo) findFirstVersion(obj);
                via = ((Versionable) obj).getActualVersion();

                if (action == null || action.equals("")) {

                    log.debug("act:''");
                    out.println("<div class=\"swbform\">");
                    out.println("<fieldset>");
                    out.println("<table width=\"98%\" >");
                    out.println("<thead>");
                    out.println("<tr>");
                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("msgVersion"));
                    out.println("</th>");
                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("msgAction"));
                    out.println("</th>");
                    out.println("<th>");
                    out.println("&nbsp;");
                    out.println("</th>");
                    out.println("</tr>");
                    out.println("</thead>");
                    out.println("<tbody>");
                    if (null != vio) {
                        while (vio != null) {

                            out.println("<tr>");
                            out.println("<td align=\"center\">");
                            out.println(vio.getVersionNumber());
                            out.println("</td>");
                            out.println("<td>");
                            SWBResourceURL urle = paramRequest.getRenderUrl();
                            urle.setParameter("suri", id);
                            urle.setParameter("sobj", vio.getURI());
                            urle.setParameter("act", "edit");
                            urle.setMode(SWBResourceURL.Mode_EDIT);
                            out.println("<a href=\"#\" onclick=\"submitUrl('" + urle + "',this); return false;\">edit</a>");

                            SWBResourceURL urlr = paramRequest.getActionUrl();
                            urlr.setParameter("suri", id);
                            urlr.setParameter("sval", vio.getURI());
                            urlr.setAction("remove");
                            out.println("&nbsp;<a href=\"#\" onclick=\"submitUrl('" + urlr + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=0></a>");
                            if (!vio.equals(via)) {
                                SWBResourceURL urlsa = paramRequest.getActionUrl();
                                urlsa.setParameter("suri", id);
                                urlsa.setParameter("sval", vio.getURI());
                                urlsa.setAction("setactual");
                                out.println("&nbsp;<a href=\"#\" onclick=\"submitUrl('" + urlsa + "',this); return false;\">Cambiar</a>");
                            } else {
                                out.println("-----");
                            }
                            out.println("</td>");
                            out.println("<td>");
                            if (vio.equals(via)) {
                                out.println("Version Actual");
                            }
                            out.println("&nbsp;</td>");
                            out.println("</tr>");
                            vio = vio.getNextVersion();
                        }
                    }
                    out.println("</tbody>");
//                    out.println("<tfoot>");
//                    out.println("<tr>");
//                    out.println("<td colspan=\"3\">");
//                    SWBResourceURL urlNew = paramRequest.getRenderUrl();
//                    urlNew.setParameter("suri", id);
//                    urlNew.setParameter("act","newversion");
//                    urlNew.setMode(SWBResourceURL.Mode_EDIT);
//                    out.println("<p><a href=\"#\" onclick=\"submitUrl('" + urlNew + "',this); return false;\">Add New</a>");
//                    out.println("</p>");
//                    out.println("</td>");
//                    out.println("</tr>");
//                    out.println("</tfoot>");
                    out.println("</table>");
                    out.println("</fieldset>");
                    out.println("<fieldset>");
                    SWBResourceURL urlNew = paramRequest.getRenderUrl();
                    urlNew.setParameter("suri", id);
                    urlNew.setParameter("act","newversion");
                    urlNew.setMode(SWBResourceURL.Mode_EDIT);
                    out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlNew + "',this.domNode); return false;\">" + paramRequest.getLocaleString("btn_addnew") + "</button>");
                    out.println("</fieldset>");
                    out.println("</div>");
                }
            }
        }
    }

    /**
     * Find first version.
     * 
     * @param obj the obj
     * @return the version info
     */
    private VersionInfo findFirstVersion(GenericObject obj) {
        VersionInfo ver = null;
        if (obj != null) {
            ver = ((Versionable) obj).getActualVersion();
        }
        if (null != ver) {
            while (ver.getPreviousVersion() != null) { //
                ver = ver.getPreviousVersion();
            }
        }
        return ver;
    }

    // Edición de la VersionInfo dependiendo el SemanticObject relacionado
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doEdit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        base = getResourceBase();
        log.debug("doEdit");
        User user = paramRequest.getUser();
        String action = request.getParameter("act");
        String id = request.getParameter("suri");
        String idvi = request.getParameter("sobj");
        SemanticObject so = null;
        PrintWriter out = response.getWriter();
        SWBFormMgr fm = null;

        if (action.equals("newversion")) {

            SemanticObject soref = ont.getSemanticObject(id);
            SWBResourceURL urla = paramRequest.getActionUrl();
            urla.setAction("newversion");

            //WBFormElement sfe = new SWBFormElement(so);

            fm = new SWBFormMgr(Versionable.swb_VersionInfo, soref,null);
            fm.addHiddenParameter("suri", id);
            fm.addHiddenParameter("psuri", id);
            //fm.addHiddenParameter("sobj", so.getURI());
            fm.setAction(urla.toString());

            out.println("<div class=\"swbform\">");
            out.println("<form id=\""+id+"/"+idvi+"/"+base.getId()+"/FVIComment\" action=\""+urla+"\" method=\"post\" onsubmit=\"submitForm('"+id+"/"+idvi+"/"+base.getId()+"/FVIComment');return false;\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\""+id+"\">");
            out.println("<fieldset>");
            out.println("<table>");
            out.println("<tbody>");
            out.println("<tr>");
            out.println("<td>");
            out.println(fm.renderElement(request, VersionInfo.swb_versionComment.getLabel())!=null?fm.renderElement(request, VersionInfo.swb_versionComment.getLabel()):"Comment");
            out.println("</td>");
            out.println("<td>");
            out.println(fm.renderElement(request, VersionInfo.swb_versionComment,SWBFormMgr.MODE_EDIT));
            out.println("</td>");
            out.println("</tr>");
            out.println("</tbody>");
            out.println("</table>");
            out.println("</filedset>");
            out.println("<filedset>");
            //out.println("<hr noshade>");
            out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitForm('"+id+"/"+idvi+"/"+base.getId()+"/FVIComment');return false;\">Guardar</button>");
            //out.println("<button dojoType=\"dijit.form.Button\">Favoritos</button>");
            //out.println("<button dojoType=\"dijit.form.Button\">Eliminar</button>");
            SWBResourceURL urlb = paramRequest.getRenderUrl();
            urlb.setMode(SWBResourceURL.Mode_VIEW);
            urlb.setParameter("act","");
            urlb.setParameter("suri",id);
            out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlb + "',this.domNode); return false;\">Cancelar</button>");
            out.println("</filedset>");
            out.println("</form>");
            out.println("</div>");

        } else if (action.equals("edit")) {

            SWBResourceURL urla = paramRequest.getActionUrl();
            urla.setAction("update");
            log.debug("VI id:"+idvi);
            so = ont.getSemanticObject(idvi);
            fm = new SWBFormMgr(so, null, SWBFormMgr.MODE_EDIT);
            fm.addHiddenParameter("suri", id);
            fm.addHiddenParameter("psuri", id);
            fm.addHiddenParameter("sobj", so.getURI());
            fm.setAction(urla.toString());

            fm.setSubmitByAjax(true);
            fm.addButton(SWBFormButton.newSaveButton());
            SWBResourceURL urlback = paramRequest.getRenderUrl();
            urlback.setMode(SWBResourceURL.Mode_VIEW);
            urlback.setParameter("suri", id);
            urlback.setParameter("psuri", id);
            if(null!=so)urlback.setParameter("sobj", so.getURI());
            fm.addButton(SWBFormButton.newCancelButton().setAttribute("onclick", "submitUrl('"+urlback+"',this.domNode);return false;"));
            fm.setType(SWBFormMgr.TYPE_DOJO);


            out.println(fm.renderForm(request));
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doAdmin(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        out.println("<div class=\"swbform\">");
        out.println("<fieldset>");
        out.println("Este recurso no es administrable.");
        out.println("</fieldset>");
        out.println("</div>");

    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String id = request.getParameter("suri");
        String act = response.getAction();
        log.debug("Action:" + act + ", suri: " + id);
        GenericObject go = ont.getGenericObject(id);
        VersionInfo vio = null;
        VersionInfo via = null;
        VersionInfo vil = null;
        VersionInfo vin = null;
        if (act == null) {
            act = "";
        }
        if ("newversion".equals(act)) {
            if (go instanceof Versionable) {
                log.debug("processAction. newVersion(Versionable)");
                Versionable gov = (Versionable) go;
                SemanticObject sobase = ont.getSemanticObject(id);
                SemanticClass sc = VersionInfo.swb_VersionInfo;
                long lid = 0;
                if (sc.isAutogenId()) {
                    lid = sobase.getModel().getCounter(sc);
                }
                SemanticObject nvinf = sobase.getModel().createSemanticObject(sobase.getModel().getObjectUri("" + lid, sc), sc);
                GenericObject ngo = ont.getGenericObject(nvinf.getURI());
                vin = (VersionInfo) ngo;
                int vnum = 1;
                vio = (VersionInfo) findFirstVersion(go);
                if (vio != null) {
                    //via = gov.getActualVersion();
                    vil = gov.getLastVersion();
                    vnum = vil.getVersionNumber() + 1;
                    log.debug("version num:"+vnum);
                    nvinf.setObjectProperty(VersionInfo.swb_previousVersion, vil.getSemanticObject()); //vin.setVersionComment(VersionComment);
                    //vin.setPreviousVersion(vil);
                    vil.getSemanticObject().setObjectProperty(VersionInfo.swb_nextVersion, nvinf);
                } else {
                    gov.getSemanticObject().setObjectProperty(Versionable.swb_actualVersion, nvinf);
                }
                //vin.setVersionNumber(vnum);
                nvinf.setIntProperty(VersionInfo.swb_versionNumber, vnum);
                String VersionComment = request.getParameter("versionComment");
                log.debug(VersionComment);
                if(VersionComment!=null) nvinf.setProperty(VersionInfo.swb_versionComment, VersionComment); //vin.setVersionComment(VersionComment);

                gov.getSemanticObject().setObjectProperty(Versionable.swb_lastVersion, nvinf);
                //response.setRenderParameter("sobj", nvinf.getURI());
                response.setRenderParameter("act", "");
                response.setMode(response.Mode_VIEW);
            }
        } else if ("update".equals(act)) {
            id=request.getParameter("psuri");
            response.setRenderParameter(act, "");
            response.setMode(response.Mode_VIEW);
        } else if ("setactual".equals(act)) {
            String idval = request.getParameter("sval");
            SemanticObject sobase = ont.getSemanticObject(id);
            SemanticObject soactual = ont.getSemanticObject(idval);
            sobase.setObjectProperty(Versionable.swb_actualVersion, soactual);
            response.setRenderParameter(act, "");
            response.setMode(response.Mode_VIEW);
        } else if ("remove".equals(act)) {
            log.debug("remove");
            String idval = request.getParameter("sval");
            log.debug("suri:"+id+"sval:"+idval);
            SemanticObject sobj = ont.getSemanticObject(id);
            GenericObject sobase = ont.getGenericObject(idval);
            vio = null;
            VersionInfo vip = null;
            vin = null;

            GenericObject gobj = ont.getGenericObject(id);
            if(gobj instanceof Versionable)
            {
                Versionable vigo = (Versionable) gobj;
                via = vigo.getActualVersion(); // Version Actual
                vil = vigo.getLastVersion(); // Última versión
                if(sobase instanceof VersionInfo)
                {
                    vio = (VersionInfo)sobase;
                    vip = vio.getPreviousVersion();
                    vin = vio.getNextVersion();
                    if(null!=vip) // si es una versión intermedia ó la última versión
                    {
                        if(null!=vin)
                        {
                            vip.setNextVersion(vin);
                            vin.setPreviousVersion(vip);
                            if(via.equals(vio)) sobj.setObjectProperty(Versionable.swb_actualVersion, vin.getSemanticObject());
                            if(vil.equals(vio)) sobj.setObjectProperty(Versionable.swb_lastVersion, vin.getSemanticObject());
                        }
                        else // la ultima version
                        {
                            vip.getSemanticObject().removeProperty(VersionInfo.swb_nextVersion);
                            if(via.equals(vio)) sobj.setObjectProperty(Versionable.swb_actualVersion, vip.getSemanticObject());
                            if(vil.equals(vio)) sobj.setObjectProperty(Versionable.swb_lastVersion, vip.getSemanticObject());
                        }


                    }
                    else if(null!=vin) //  era la primera version
                    {
                        vin.getSemanticObject().removeProperty(VersionInfo.swb_previousVersion);
                        if(via.equals(vio)) sobj.setObjectProperty(Versionable.swb_actualVersion, vin.getSemanticObject());
                        if(vil.equals(vio)) sobj.setObjectProperty(Versionable.swb_lastVersion, vin.getSemanticObject());

                    }
                    else if(vip==null&&vin==null) // es la única version
                    {
                        sobj.removeProperty(Versionable.swb_actualVersion);
                        sobj.removeProperty(Versionable.swb_lastVersion);
                    }
                    vio.getSemanticObject().getModel().removeSemanticObject(sobase.getSemanticObject()); // se elimina la version
                }
            }


            //SemanticObject soactual = ont.getSemanticObject(idval);
            //sobase.setObjectProperty(voc.swb_actualVersion, soactual);
            response.setRenderParameter(act, "");
            response.setMode(response.Mode_VIEW);
        }
        response.setRenderParameter("suri", id);
    }
}
