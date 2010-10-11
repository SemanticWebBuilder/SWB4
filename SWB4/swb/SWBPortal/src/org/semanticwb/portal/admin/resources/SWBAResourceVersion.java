/**  
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
 **/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.PFlow;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.Versionable;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.PFlowManager;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBAResourceVersion.
 * 
 * @author juan.fernandez
 */
public class SWBAResourceVersion extends GenericResource {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBAResourceVersion.class);
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

        PFlowManager pfmgr = SWBPortal.getPFlowManager();

        boolean isInFlow = false, needAut = false;

        //System.out.println("URI:"+id);
        if (request.getParameter("dialog") != null && request.getParameter("dialog").equals("close")) {
            //System.out.println("doView(dialog):"+id);
            out.println("<script type=\"javascript\">");
            out.println(" hideDialog(); ");
            GenericObject obj = ont.getGenericObject(id);
            SWBResource swres = (SWBResource) obj;
            if (swres != null) {
                out.println(" reloadTab('" + swres.getResourceBase().getURI() + "');");
            }
            out.println("</script>");
            //out.println();
            return;
        }

        if (null == id) {
            out.println("<fieldset>");
            out.println("URI faltante");
            out.println("</fieldset>");
        } else {
            String action = request.getParameter("act");
            GenericObject obj = ont.getGenericObject(id);

            SWBResource swres = (SWBResource) obj;

//            System.out.println("web Work Path:"+SWBPlatform.getWebWorkPath());
//            System.out.println("context Path:"+SWBPlatform.getContextPath());
//            System.out.println("Work Path:"+SWBPlatform.getWorkPath()+"/models/"+tmpl.getWebSiteId()+"/Template/");
//            System.out.println("template work path: "+tmpl.getWorkPath());

            int vanum=1,vlnum=1;

            log.debug("doView(), suri: " + id);
            VersionInfo via = null;
            VersionInfo vio = null;
            VersionInfo vil = null;
            boolean subject2flow = false;
            boolean versionExists = false;
            if (obj instanceof Versionable) {
                vio = (VersionInfo) findFirstVersion(obj);
                via = ((Versionable) obj).getActualVersion();
                vil = ((Versionable) obj).getLastVersion();
                vanum = via.getVersionNumber();
                vlnum = vil.getVersionNumber();

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
                    out.println(paramRequest.getLocaleString("msgComment"));
                    out.println("</th>");
                    out.println("</tr>");
                    out.println("</thead>");
                    out.println("<tbody>");
                    if (null != vio) {
                        versionExists = true;
                        Resource res = null;
                        if (swres.getResourceBase() != null) {
                            res = swres.getResourceBase();
                            isInFlow = pfmgr.isInFlow(res);
                            needAut = pfmgr.needAnAuthorization(res);
                        }

                        PFlow[] arrpf = pfmgr.getFlowsToSendContent(res);

                        if(arrpf.length>0) subject2flow = true;

                        while (vio != null) {


                            out.println("<tr>");
                            out.println("<td align=\"center\">");
                            out.println(vio.getVersionNumber());
                            out.println("</td>");
                            out.println("<td>");

                            //System.out.println("La version ("+vio.getVersionNumber()+") autorizada: "+vio.isVersionAuthorized());
                            //System.out.println("IsInFlow( "+isInFlow+" ), need Auth ( "+needAut+" )");
                            // Revisión flujo

                            SWBResourceURL urle = paramRequest.getRenderUrl();
                            urle.setParameter("suri", id);
                            urle.setParameter("sobj", vio.getURI());
                            urle.setParameter("vnum", Integer.toString(vio.getVersionNumber()));
                            urle.setParameter("act", "edit_temp");
                            urle.setMode(SWBResourceURL.Mode_EDIT);
                            
                            if (subject2flow){ 
                                if(isInFlow ) //|| needAut
                                {
                                    out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"version por autorizar, no editable.\" title=\"version por autorizar, no editable.\">");
                                }
                                else if(res.getPflowInstance()==null&&!isInFlow&&needAut&&!vio.isVersionAuthorized() && vlnum==vio.getVersionNumber() && vio.getVersionNumber()==vanum && vio.getVersionNumber()==1 && vlnum==vanum) // Version inicial
                                {
                                    out.println("<a href=\"" + urle + "\" onclick=\"submitUrl('" + urle + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"editar version\" title=\"editar version\"></a>");
                                }
                                else if(!vio.isVersionAuthorized() && vlnum>vio.getVersionNumber() && vio.getVersionNumber()!=vanum)
                                {
                                    out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"version no autorizada, no editable.\" title=\"version no autorizada, no editable.\">");
                                }
                                else if(vio.isVersionAuthorized() || (vlnum>vio.getVersionNumber() && vio.getVersionNumber()!=vanum))
                                {
                                    out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"version autorizada, no editable.\" title=\"version autorizada, no editable.\">");
                                }
                                else if(vio.isVersionAuthorized() && ((vlnum>vio.getVersionNumber() && vio.getVersionNumber()==vanum)||(vlnum==vio.getVersionNumber()&&vio.getVersionNumber()==vanum)))
                                {
                                    out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"version actual autorizada, no editable.\" title=\"version actual autorizada, no editable.\">");
                                }
                                else if(!vio.isVersionAuthorized() && vlnum>=vio.getVersionNumber() && vio.getVersionNumber()==vanum)
                                {
                                    out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"version actual no autorizada, no editable.\" title=\"version actual no autorizada, no editable.\">");
                                }
                                else if(!vio.isVersionAuthorized() && vlnum==vio.getVersionNumber() && vio.getVersionNumber()!=vanum)
                                {
                                    out.println("<a href=\"" + urle + "\" onclick=\"submitUrl('" + urle + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"editar version\" title=\"editar version\"></a>");
                                }
                                else if(vio.getVersionNumber()>vanum || vio.getVersionNumber()<vlnum || needAut )
                                {
                                    out.println("<a href=\"" + urle + "\" onclick=\"submitUrl('" + urle + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"editar version\" title=\"editar version\"></a>");
                                }
                                else
                                {
                                    out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"version no autorizada, no editable.\" title=\"version no autorizada, no editable.\"></a>");
                                }
                            } else {
                                out.println("<a href=\"" + urle + "\" onclick=\"submitUrl('" + urle + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"editar version\" title=\"editar version\"></a>");
                            }

                            SWBResourceURL urlnv = paramRequest.getRenderUrl();
                            urlnv.setParameter("suri", id);
                            urlnv.setParameter("sobj", vio.getURI());
                            urlnv.setParameter("vnum", Integer.toString(vio.getVersionNumber()));
                            urlnv.setParameter("act", "newversion");
                            urlnv.setMode(SWBResourceURL.Mode_EDIT);
                            out.println("<a href=\"#\" onclick=\"showDialog('" + urlnv + "','Nueva versión de Recurso'); hideDialog(); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/nueva_version.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgNewVersion") + "\"></a>");

                            if (subject2flow){
                                if (vio.isVersionAuthorized()&&vio.getVersionNumber()!=vanum)
                                {
                                    SWBResourceURL urlsa = paramRequest.getActionUrl();
                                    urlsa.setParameter("suri", id);
                                    urlsa.setParameter("sval", vio.getURI());
                                    urlsa.setAction("setactual");
                                    out.println("<a href=\"#\" onclick=\"submitUrl('" + urlsa + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/cambio_version.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("logTplSetActual") + "\" title=\"" + paramRequest.getLocaleString("logTplSetActual") + "\"></a>");
                                }
                                else if (!vio.isVersionAuthorized())
                                {
                                    out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/cambio_version.gif\" border=\"0\" alt=\"" + "Version no autorizada, no se puede cambiar." + "\" title=\"" + "Version no autorizada, no se puede cambiar." + "\"></a>");
                                }
                                else if(vio.getVersionNumber()==vanum)
                                {
                                    out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/activa.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgActualVersion") + "\" title=\"" + paramRequest.getLocaleString("msgActualVersion") + "\">");
                                }
                            }
                            else if(vanum==vio.getVersionNumber())
                            {
                                out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/activa.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgActualVersion") + "\">");
                            }
                            else
                            {
                                SWBResourceURL urlsa = paramRequest.getActionUrl();
                                urlsa.setParameter("suri", id);
                                urlsa.setParameter("sval", vio.getURI());
                                urlsa.setAction("setactual");
                                out.println("<a href=\"#\" onclick=\"submitUrl('" + urlsa + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/cambio_version.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("logTplSetActual") + "\"></a>");
                            }

                            // Llamado al doView del recurso.
                            SWBResourceURLImp urlV = (SWBResourceURLImp) paramRequest.getRenderUrl();
                            urlV.setResourceBase(swres.getResourceBase());
                            urlV.setParameter("numversion", Integer.toString(vio.getVersionNumber()));
                            urlV.setMode(SWBResourceURLImp.Mode_VIEW);
                            urlV.setCallMethod(SWBResourceURLImp.Call_DIRECT);
                            //out.println("<a href=\"#\" onclick=\"window.open('" + SWBPlatform.getWebWorkPath() + swres.getResourceBase().getWorkPath() + "/" + vio.getVersionNumber() + "/" + vio.getVersionFile() + "','Preview','scrollbars, resizable, width=550, height=550');\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/preview.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgPreview") + "\"></a>"); //submitUrl('" + urlec + "',this); return false;
                            out.println("<a href=\"#\" onclick=\"window.open('" + urlV + "','Preview','scrollbars, resizable, width=550, height=550');\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/preview.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgPreview") + "\"></a>"); //submitUrl('" + urlec + "',this); return false;

                            SWBResourceURL urlr = paramRequest.getActionUrl();
                            urlr.setParameter("suri", id);
                            urlr.setParameter("sval", vio.getURI());
                            urlr.setAction("remove");
                            out.println("<a href=\"#\" onclick=\"if(confirm('" + paramRequest.getLocaleString("q_removeVersion") + "')){submitUrl('" + urlr + "',this);} return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgRemoveVersion") + "\"></a>");
                            if (vio.equals(via)) {
                                out.println("( " + paramRequest.getLocaleString("msgActualVersion") + " ) ");
                            }
                            out.println("</td>");
                            out.println("<td>");

                            String comment = vio.getVersionComment() != null && vio.getVersionComment().trim().length() > 0 && !vio.getVersionComment().equals("null") ? vio.getVersionComment() : "";
                            out.println(comment + "</td>");
                            out.println("</tr>");
                            vio = vio.getNextVersion();
                        }
                    }
                    out.println("</tbody>");
                    out.println("</table>");
                    out.println("</fieldset>");
                    out.println("<fieldset>");

                    if (!versionExists) {
                        SWBResourceURL urlNew = paramRequest.getRenderUrl();
                        urlNew.setParameter("suri", id);
                        urlNew.setParameter("act", "newversion");
                        urlNew.setMode(SWBResourceURL.Mode_EDIT);
                        out.println("<button dojoType=\"dijit.form.Button\" onclick=\"showDialog('" + urlNew + "','Agregar nueva versión'); return false;\">" + paramRequest.getLocaleString("btn_addnew") + "</button>");
                    } else {
                        SWBResourceURL urlVR = paramRequest.getActionUrl();
                        urlVR.setParameter("suri", id);
                        urlVR.setAction("resetversion");
                        urlVR.setMode(SWBResourceURL.Mode_VIEW);
                        out.println("<button dojoType=\"dijit.form.Button\" onclick=\"if(confirm('" + paramRequest.getLocaleString("q_resetVersion") + "')){submitUrl('" + urlVR.toString() + "',this.domNode);} return false;\">" + paramRequest.getLocaleString("btn_versionreset") + "</button>");
                    }
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
        } else {
            //create Initial Version
            GenericObject go = obj;
            int vnum = 1;
            SWBResource swres = (SWBResource) go;
            VersionInfo vi = swres.getResourceBase().getWebSite().createVersionInfo();
            //WebSite site=(WebSite)obj.getSemanticObject().getModel().getModelObject().createGenericInstance();
            //VersionInfo vi = site.createVersionInfo();
            vi.setVersionFile("index.html");
            vi.setVersionNumber(vnum);
            vi.setVersionComment("Versión Inicial");

            Versionable vswres = (Versionable) go;
            vswres.setActualVersion(vi);
            vswres.setLastVersion(vi);


            String rutaFS_target_path = SWBPortal.getWorkPath() + swres.getResourceBase().getWorkPath() + "/" + vnum + "/";
            File f = new File(rutaFS_target_path);
            if (!f.exists()) {
                f.mkdirs();
            }

            File ftmpl = new File(SWBPortal.getWorkPath() + swres.getResourceBase().getWorkPath() + "/" + vnum + "/index.html");
            Writer output = null;
            try {
                output = new BufferedWriter(new FileWriter(ftmpl));
                output.write(" ");
            } catch (Exception e) {
            } finally {
                try {
                    output.close();
                } catch (Exception ex) {
                }
            }
            ver = vi;
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
        String vnum = request.getParameter("vnum");
        SemanticObject so = null;
        SWBResource swres = null;
        PrintWriter out = response.getWriter();
        SWBFormMgr fm = null;

        if (action.equals("newversion")) {

            SemanticObject soref = ont.getSemanticObject(id);
            SWBResourceURL urla = paramRequest.getActionUrl();
            urla.setAction("newversion");

            //WBFormElement sfe = new SWBFormElement(so);

            fm = new SWBFormMgr(Versionable.swb_VersionInfo, soref, null);
            fm.addHiddenParameter("suri", id);
            fm.addHiddenParameter("psuri", id);
            if (vnum != null) {
                fm.addHiddenParameter("vnum", vnum);
            }
            //fm.addHiddenParameter("sobj", so.getURI());
            fm.setAction(urla.toString());
            out.println("<div class=\"swbform\">");
            out.println("<form id=\"" + id + "/" + idvi + "/" + base.getId() + "/FVIComment\" action=\"" + urla + "\" method=\"post\" onsubmit=\"submitForm('" + id + "/" + idvi + "/" + base.getId() + "/FVIComment');return false;\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
            if (vnum != null) {
                out.println("<input type=\"hidden\" name=\"vnum\" value=\"" + vnum + "\">");
            }
            out.println("<fieldset>");
            out.println("<table>");
            out.println("<tbody>");
            out.println("<tr>");
            out.println("<td>");
            out.println(fm.renderElement(request, VersionInfo.swb_versionComment.getLabel()) != null ? fm.renderElement(request, VersionInfo.swb_versionComment.getLabel()) : "Comment");
            out.println("</td>");
            out.println("<td>");
            out.println(fm.renderElement(request, VersionInfo.swb_versionComment, SWBFormMgr.MODE_EDIT));
            out.println("</td>");
            out.println("</tr>");
            out.println("</tbody>");
            out.println("</table>");
            out.println("</filedset>");
            out.println("<filedset>");
            out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" >Guardar</button>"); //_onclick=\"submitForm('"+id+"/"+idvi+"/"+base.getId()+"/FVIComment');return false;\"
            SWBResourceURL urlb = paramRequest.getRenderUrl();
            urlb.setMode(SWBResourceURL.Mode_VIEW);
            urlb.setParameter("act", "");
            urlb.setParameter("suri", id);
            out.println("<button dojoType=\"dijit.form.Button\" onclick=\"hideDialog(); return false;\">Cancelar</button>"); //submitUrl('" + urlb + "',this.domNode); hideDialog();
            out.println("</filedset>");
            out.println("</form>");
            out.println("</div>");

        } else if (action.equals("edit")) {

            SWBResourceURL urla = paramRequest.getActionUrl();
            urla.setAction("update");
            log.debug("VI id:" + idvi);
            so = ont.getSemanticObject(idvi);
            fm = new SWBFormMgr(so, null, SWBFormMgr.MODE_EDIT);
            fm.addHiddenParameter("suri", id);
            fm.addHiddenParameter("psuri", id);
            fm.addHiddenParameter("sobj", so.getURI());
            fm.setAction(urla.toString());

            out.println(fm.renderForm(request));
        } else if (action.equals("edit_temp")) {
            //System.out.println("VNUM: " + vnum);
            //SemanticObject obj = SemanticObject.createSemanticObject(id);
            GenericObject gobj = ont.getGenericObject(id);

            swres = (SWBResource) gobj;
            if (swres != null) {
                SWBResourceURLImp urlb = (SWBResourceURLImp) paramRequest.getRenderUrl();
                urlb.setResourceBase(swres.getResourceBase());
                urlb.setMode(SWBResourceURLImp.Mode_EDIT);
                urlb.setCallMethod(SWBResourceURLImp.Call_DIRECT);
                urlb.setParameter("suri", id);
                urlb.setParameter("numversion", vnum);
                out.println("<iframe id=\"" + getResourceBase().getId() + "swbResVersionFrame\" dojoType=\"dijit.layout.ContentPane\" src=\"" + urlb + "\" width=\"100%\" height=\"100%\" frameborder=\"0\"></iframe>");
            }
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String id = request.getParameter("suri"); // uri de la plantilla
        String act = response.getAction();
        log.debug("Action:" + act + ", suri: " + id);
        //System.out.println("act: " + act + ",suri: " + id);
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
                    vil = gov.getLastVersion();
                    vnum = vil.getVersionNumber() + 1;
                    log.debug("version num:" + vnum);
                    nvinf.setObjectProperty(VersionInfo.swb_previousVersion, vil.getSemanticObject()); //vin.setVersionComment(VersionComment);
                    vil.getSemanticObject().setObjectProperty(VersionInfo.swb_nextVersion, nvinf);
                } else {
                    gov.getSemanticObject().setObjectProperty(Versionable.swb_actualVersion, nvinf);
                }
                nvinf.setIntProperty(VersionInfo.swb_versionNumber, vnum);
                nvinf.setProperty(VersionInfo.swb_versionFile, "index.html");
                String VersionComment = request.getParameter("versionComment");
                log.debug(VersionComment);
                if (VersionComment != null) {
                    nvinf.setProperty(VersionInfo.swb_versionComment, VersionComment); //vin.setVersionComment(VersionComment);
                }
                gov.getSemanticObject().setObjectProperty(Versionable.swb_lastVersion, nvinf);


                SWBResource tmpl = (SWBResource) go;
                if (request.getParameter("vnum") != null) {
                    // copiar archivos
                    String rutaFS_source_path = SWBPortal.getWorkPath() + tmpl.getResourceBase().getWorkPath() + "/" + request.getParameter("vnum") + "/";
                    String rutaFS_target_path = SWBPortal.getWorkPath() + tmpl.getResourceBase().getWorkPath() + "/" + vnum + "/";
                    String rutaWeb_source_path = SWBPortal.getWebWorkPath() + tmpl.getResourceBase().getWorkPath() + "/" + request.getParameter("vnum");
                    String rutaWeb_target_path = SWBPortal.getWebWorkPath() + tmpl.getResourceBase().getWorkPath() + "/" + vnum;

                    if (SWBUtils.IO.copyStructure(rutaFS_source_path, rutaFS_target_path, true, rutaWeb_source_path, rutaWeb_target_path)) {
                        //System.out.println("Copied OK");
                    }
                } else {

                    SWBResource swres = (SWBResource) go;
                    VersionInfo vi = swres.getResourceBase().getWebSite().createVersionInfo();
                    vi.setVersionFile("index.html");
                    vi.setVersionNumber(1);
                    vi.setVersionComment(VersionComment);

                    Versionable vswres = (Versionable) go;
                    vswres.setActualVersion(vi);
                    vswres.setLastVersion(vi);

                    String rutaFS_target_path = SWBPortal.getWorkPath() + swres.getResourceBase().getWorkPath() + "/" + vnum + "/";
                    File f = new File(rutaFS_target_path);
                    if (!f.exists()) {
                        f.mkdirs();
                    }

                    File ftmpl = new File(SWBPortal.getWorkPath() + swres.getResourceBase().getWorkPath() + "/" + vnum + "/index.html");
                    Writer output = new BufferedWriter(new FileWriter(ftmpl));
                    try {
                        output.write(" ");
                    } finally {
                        output.close();
                    }
                }

                tmpl.getResourceBase().removePflowInstance();

                //SWBPortal.getResourceMgr().reloadTemplate(tmpl);

                response.setRenderParameter("dialog", "close");
                response.setRenderParameter("act", "");
                response.setMode(response.Mode_VIEW);

            }
        } //        else if ("update".equals(act)) {
        //            id = request.getParameter("psuri");
        //            response.setRenderParameter(act, "");
        //            response.setMode(response.Mode_VIEW);
        //        }
        else if ("setactual".equals(act)) {
            String idval = request.getParameter("sval");
            SemanticObject sobase = ont.getSemanticObject(id);
            SemanticObject soactual = ont.getSemanticObject(idval);
            sobase.setObjectProperty(Versionable.swb_actualVersion, soactual);

            // Para recargar el recurso
            SWBResource swres = (SWBResource) sobase.getGenericInstance();
            SWBPortal.getResourceMgr().getResourceCacheMgr().removeResource(swres.getResourceBase());
            //swres.setResourceBase(swres.getResourceBase());

            response.setRenderParameter(act, "");
            response.setMode(response.Mode_VIEW);
        } else if ("remove".equals(act)) {
            //System.out.println("remove");
            String idval = request.getParameter("sval"); // version a eliminar
            log.debug("suri:" + id + "sval:" + idval);
            SemanticObject sobj = ont.getSemanticObject(id);
            GenericObject sobase = ont.getGenericObject(idval); //version info a eliminar
            vio = null;
            VersionInfo vip = null;
            vin = null;

            GenericObject gobj = ont.getGenericObject(id); // se obtiene la plantilla y se verifica que sea versionable
            if (gobj instanceof Versionable) {
                Versionable vigo = (Versionable) gobj;
                via = vigo.getActualVersion(); // Version Actual de la plantilla
                vil = vigo.getLastVersion(); // Última versión de la plantilla
                if (sobase instanceof VersionInfo) {
                    vio = (VersionInfo) sobase; // version a eliminar
                    vip = vio.getPreviousVersion();
                    vin = vio.getNextVersion();

                    if (null != vip) // si es una versión intermedia ó la última versión
                    {
                        log.debug("version intermedia o ultima --- ");
                        if (null != vin) // si es una version intermedia a eliminarse
                        {
                            log.debug("Version intermedia");
                            vip.setNextVersion(vin);
                            vin.setPreviousVersion(vip);

                            if (via.equals(vio)) {
                                sobj.setObjectProperty(Versionable.swb_actualVersion, vin.getSemanticObject());
                            }
                            if (vil.equals(vio)) {
                                sobj.setObjectProperty(Versionable.swb_lastVersion, vin.getSemanticObject());
                            }
                        } else // la ultima version
                        {
                            log.debug("Ultima version");
                            vip.getSemanticObject().removeProperty(VersionInfo.swb_nextVersion);
                            if (via.equals(vio)) {
                                sobj.setObjectProperty(Versionable.swb_actualVersion, vip.getSemanticObject());
                            }
                            if (vil.equals(vio)) {
                                sobj.setObjectProperty(Versionable.swb_lastVersion, vip.getSemanticObject());
                            }
                        }
                    } else if (null != vin) //  era la primera version
                    {
                        log.debug("primera version");
                        vin.getSemanticObject().removeProperty(VersionInfo.swb_previousVersion);
                        if (via.equals(vio)) {
                            sobj.setObjectProperty(Versionable.swb_actualVersion, vin.getSemanticObject());
                        }
                        if (vil.equals(vio)) {
                            sobj.setObjectProperty(Versionable.swb_lastVersion, vin.getSemanticObject());
                        }
                    } else if (vip == null && vin == null) // es la única version
                    {
                        log.debug("Unica version");
                        gobj.getSemanticObject().removeProperty(Versionable.swb_actualVersion);
                        gobj.getSemanticObject().removeProperty(Versionable.swb_lastVersion);
                    }
                    int vnumdel = vio.getVersionNumber();
                    SWBResource tmpl = (SWBResource) go;
                    String rutaFS_source_path = SWBPortal.getWorkPath() + tmpl.getResourceBase().getWorkPath() + "/" + vnumdel;
                    if (SWBUtils.IO.removeDirectory(rutaFS_source_path)) {
                        //System.out.println("Remove OK");
                    }


                    vio.removeNextVersion();
                    vio.removePreviousVersion();
                    vio.remove();
                    //sobase.getSemanticObject().remove();
                }
            }

            response.setRenderParameter(act, "");
            response.setMode(response.Mode_VIEW);
        } else if ("resetversion".equals(act)) {
            //System.out.println("Reset - Version");
            Versionable versiones = null;
            if (go instanceof Versionable) {
                versiones = (Versionable) go;
            }

            SemanticObject sobase = ont.getSemanticObject(id);
            SWBResource wres = (SWBResource) go;


            VersionInfo va = versiones.getActualVersion();
            VersionInfo vl = versiones.getLastVersion();

            VersionInfo temp = va.getPreviousVersion();
            VersionInfo temp2 = null;

            while (temp != null) {
                //System.out.println("version anterior != null ("+temp.getVersionNumber()+")");
                temp2 = temp;
                String rutaFS_source_path = SWBPortal.getWorkPath() + wres.getResourceBase().getWorkPath() + "/" + temp2.getVersionNumber();
                if (SWBUtils.IO.removeDirectory(rutaFS_source_path)) {
                    //System.out.println("Remove back OK by Reset Version: " + temp2.getVersionNumber());
                }
                temp = temp.getPreviousVersion();
                temp2.remove();
            }

            temp = va.getNextVersion();
            if (temp != null) {
                temp.removePreviousVersion();
            }

            // eliminación de archivos de las versiones de recursos
            while (temp != null) {
                temp2 = temp;
                temp = temp.getNextVersion();
                String rutaFS_source_path = SWBPortal.getWorkPath() + wres.getResourceBase().getWorkPath() + "/" + temp2.getVersionNumber();
                if (SWBUtils.IO.removeDirectory(rutaFS_source_path)) {
                    //System.out.println("Remove next OK by Reset Version: " + temp2.getVersionNumber());
                }
            }

            va.removeNextVersion();

            // eliminando versiones de la ultima a una despues de la actual
            if (!vl.equals(va)) {
                vl.remove();
            }

            int va_num = va.getVersionNumber();
            //System.out.println("Version actual num: "+va_num);
            if (va_num != 1) {
                String rutaFS_source_path = SWBPortal.getWorkPath() + wres.getResourceBase().getWorkPath() + "/" + va_num + "/";
                String rutaFS_target_path = SWBPortal.getWorkPath() + wres.getResourceBase().getWorkPath() + "/1/";
                String rutaWeb_source_path = SWBPortal.getWebWorkPath() + wres.getResourceBase().getWorkPath() + "/" + va_num;
                String rutaWeb_target_path = SWBPortal.getWebWorkPath() + wres.getResourceBase().getWorkPath() + "/1";

                if (SWBUtils.IO.copyStructure(rutaFS_source_path, rutaFS_target_path, true, rutaWeb_source_path, rutaWeb_target_path)) {
                    //System.out.println("Copied actual to 1 OK by Reset Version");
                }

                // Eliminando la version actual
                if (SWBUtils.IO.removeDirectory(rutaFS_source_path)) {
                    //System.out.println("Remove OK actual");
                }
            }
            va.setVersionNumber(1);

            versiones.setActualVersion(va);
            versiones.setLastVersion(va);

            response.setRenderParameter("dialog", "close");
            response.setRenderParameter("act", "");
            response.setMode(response.Mode_VIEW);
            id = sobase.getURI();
        }
        response.setRenderParameter("suri", id);
    }
}
