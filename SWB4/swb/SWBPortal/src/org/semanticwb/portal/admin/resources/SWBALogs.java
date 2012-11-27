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

import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.db.SWBRecAdmLog;
import org.w3c.dom.*;

// TODO: Auto-generated Javadoc
/** Muestra el historial de las acciones hechas sobre cada elemento de WebBuilder,
 * desde su creaci�n, modificaci�n y eliminaci�n; mostrando cada cambio realizado
 * y que usuario lo realiz�.
 *
 * It shows the history of all changes made for each WebBuilder element, since it
 * creation, modification and elimination, shows each changes made and what user
 * performed.
 * @author Juan Antonio Fern�ndez Arias
 */
public class SWBALogs extends GenericResource {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBALogs.class);

    /**
     * Creates a new instance of Logs.
     */
    public SWBALogs() {
    }

    /**
     * Admin wiew of WBALogs Resource.
     * 
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest list of objects
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doAdmin()");
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
        String actionRow = "1";
        String descriptionRow = "1";
        String dateRow = "1";
        String userRow = "1";
        String objRow = "1";
        String classRow = "1";

        String rowsHistory = "20";

        String pagesHistory = "5";
        String showPages = "1";
//        String tipo = "";

        String id = request.getParameter("suri");

        if (base.getAttribute("action") != null) {
            actionRow = base.getAttribute("action");
        }
        if (base.getAttribute("description") != null) {
            descriptionRow = base.getAttribute("description");
        }
        if (base.getAttribute("date") != null) {
            dateRow = base.getAttribute("date");
        }
        if (base.getAttribute("user") != null) {
            userRow = base.getAttribute("user");
        }
        if (base.getAttribute("obj") != null) {
            userRow = base.getAttribute("obj");
        }
        if (base.getAttribute("class") != null) {
            userRow = base.getAttribute("class");
        }
        if (base.getAttribute("rows") != null) {
            rowsHistory = base.getAttribute("rows");
        }
        if (base.getAttribute("pages") != null) {
            pagesHistory = base.getAttribute("pages");
        }
        if (base.getAttribute("showPages") != null) {
            showPages = base.getAttribute("showPages");
        }
//        if (base.getAttribute("tipo") != null) {
//            tipo = base.getAttribute("tipo");
//        }

        SWBResourceURL urlu = paramRequest.getActionUrl();
        urlu.setAction("update");
        out.println("<div class=\"swbform\">");
        out.println("<form id=\"" + id + "/logForm\" name=\"" + id + "/logForm\" action=\"" + urlu + "\" >");
        out.println("<fieldset>");
        out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\">");  //
        out.println("<tr><td width=250>");
        out.println(paramRequest.getLocaleString("LogConfig"));
        out.println("</td></tr>");
//        out.println("<tr><td  width=\"200\"  align=right >" + paramRequest.getLocaleString("LogType") + ":</td>");
//        out.println("<td class=valores align=\"left\"><select class=\"campos\" name=\"tipo\">");
//        String strSelect = "";
//        if (tipo.equals("Camp")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"Camp\" " + strSelect + ">" + paramRequest.getLocaleString("selectCamp") + "</option>");
//        strSelect = "";
//        if (tipo.equals("Content")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"Content\" " + strSelect + ">" + paramRequest.getLocaleString("selectContent") + "</option>");
//        strSelect = "";
//        if (tipo.equals("Device")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"Device\" " + strSelect + ">" + paramRequest.getLocaleString("selectDevice") + "</option>");
//        strSelect = "";
//        if (tipo.equals("DNS")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"DNS\" " + strSelect + ">" + paramRequest.getLocaleString("selectDNS") + "</option>");
//        if (tipo.equals("AdmFilter")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"AdmFilter\" " + strSelect + ">" + paramRequest.getLocaleString("selectFilter") + "</option>");
//        strSelect = "";
//        if (tipo.equals("IpFilter")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"IpFilter\" " + strSelect + ">" + paramRequest.getLocaleString("selectIpFilter") + "</option>");
//        strSelect = "";
//        if (tipo.equals("Language")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"Language\" " + strSelect + ">" + paramRequest.getLocaleString("selectLanguage") + "</option>");
//        strSelect = "";
//        if (tipo.equals("MDAttribute")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"MDAttribute\" " + strSelect + ">" + paramRequest.getLocaleString("selectMDAttr") + "</option>");
//        strSelect = "";
//        if (tipo.equals("MDTable")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"MDTable\" " + strSelect + ">" + paramRequest.getLocaleString("selectMDTable") + "</option>");
//        strSelect = "";
//        if (tipo.equals("Object")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"Object\" " + strSelect + ">" + paramRequest.getLocaleString("selectObject") + "</option>");
//        strSelect = "";
//        if (tipo.equals("PFlow")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"PFlow\" " + strSelect + ">" + paramRequest.getLocaleString("selectPFlow") + "</option>");
//        strSelect = "";
//        if (tipo.equals("Resource")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"Resource\" " + strSelect + ">" + paramRequest.getLocaleString("selectResource") + "</option>");
//        strSelect = "";
//        if (tipo.equals("ResourceType")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"ResourceType\" " + strSelect + ">" + paramRequest.getLocaleString("selectResourceType") + "</option>");
//        strSelect = "";
//        if (tipo.equals("Role")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"Role\" " + strSelect + ">" + paramRequest.getLocaleString("selectRole") + "</option>");
//        strSelect = "";
//        if (tipo.equals("Rule")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"Rule\" " + strSelect + ">" + paramRequest.getLocaleString("selectRule") + "</option>");
//        strSelect = "";
//        if (tipo.equals("SubType")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"SubType\" " + strSelect + ">" + paramRequest.getLocaleString("selectSubType") + "</option>");
//        strSelect = "";
//        if (tipo.equals("TopicMap")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"TopicMap\" " + strSelect + ">" + paramRequest.getLocaleString("selectTM") + "</option>");
//        strSelect = "";
//        if (tipo.equals("Topic")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"Topic\" " + strSelect + ">" + paramRequest.getLocaleString("selectTP") + "</option>");
//        strSelect = "";
//        if (tipo.equals("Template")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"Template\" " + strSelect + ">" + paramRequest.getLocaleString("selectTpl") + "</option>");
//        strSelect = "";
//        if (tipo.equals("GrpTemplate")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"GrpTemplate\" " + strSelect + ">" + paramRequest.getLocaleString("selectGrpTpl") + "</option>");
//        strSelect = "";
//        if (tipo.equals("User")) {
//            strSelect = "selected";
//        }
//        out.println("<option value=\"User\" " + strSelect + ">" + paramRequest.getLocaleString("selectUser") + "</option>");
//        out.println("</select></td></tr>");
        out.println("<tr><td width=250>");
        out.println(paramRequest.getLocaleString("selectRepCols") + ":");
        out.println("</td></tr>");

        String strChecked = "";
        if (actionRow != null) {
            if (actionRow.equals("1")) {
                strChecked = "checked";
            }
        }
        out.println("<tr><td width=\"250\" align=right>");
        out.println(paramRequest.getLocaleString("msgAction"));
        out.println("</td><td align=\"left\">");
        out.println("<input type=\"checkbox\" name=\"action\" value=\"1\"  " + strChecked + "></td></tr>");
        strChecked = "";
        if (descriptionRow != null) {
            if (descriptionRow.equals("1")) {
                strChecked = "checked";
            }
        }
        out.println("<tr><td width=\"250\" align=right>");
        out.println(paramRequest.getLocaleString("msgDesc"));
        out.println("</td><td align=\"left\">");
        out.println("<input type=\"checkbox\" name=\"description\" value=\"1\"  " + strChecked + "></td></tr>");
        strChecked = "";
        if (dateRow != null) {
            if (dateRow.equals("1")) {
                strChecked = "checked";
            }
        }
        out.println("<tr><td width=\"250\" align=right>");
        out.println(paramRequest.getLocaleString("msgDate"));
        out.println("</td><td align=\"left\">");
        out.println("<input type=\"checkbox\" name=\"date\" value=\"1\"  " + strChecked + "></td></tr>");
        strChecked = "";
        if (userRow != null) {
            if (userRow.equals("1")) {
                strChecked = "checked";
            }
        }
        out.println("<tr><td width=\"250\" align=right>");
        out.println(paramRequest.getLocaleString("msgPerform"));
        out.println("</td><td align=\"left\">");
        out.println("<input type=\"checkbox\" name=\"user\" value=\"1\"  " + strChecked + "></td></tr>");

        strChecked = "";
        if (objRow != null) {
            if (objRow.equals("1")) {
                strChecked = "checked";
            }
        }
        out.println("<tr><td width=\"250\" align=right>");
        out.println(paramRequest.getLocaleString("msgObj"));
        out.println("</td><td align=\"left\">");
        out.println("<input type=\"checkbox\" name=\"obj\" value=\"1\"  " + strChecked + "></td></tr>");

        strChecked = "";
        if (classRow != null) {
            if (userRow.equals("1")) {
                strChecked = "checked";
            }
        }
        out.println("<tr><td width=\"250\" align=right>");
        out.println(paramRequest.getLocaleString("msgClass"));
        out.println("</td><td align=\"left\">");
        out.println("<input type=\"checkbox\" name=\"class\" value=\"1\"  " + strChecked + "></td></tr>");


        //out.println("<td></tr>");
        out.println("<tr><td width=250 >");
        out.println(paramRequest.getLocaleString("msgPagesOptions") + ":");
        out.println("</td><td></td></tr >");
        out.println("<tr><td width=\"250\" align=right>" + paramRequest.getLocaleString("pageRows") + "</td><td align=\"left\">");
        out.println("<input type=\"text\" name=\"rows\" value=\"" + rowsHistory + "\"></td></tr>");
        strChecked = "";
        if (showPages != null) {
            if (showPages.equals("1")) {
                strChecked = "checked";
            }
        }
        out.println("<tr><td  width=\"250\" align=right>" + paramRequest.getLocaleString("showPageNumbers") + "</td><td  align=\"left\">");
        out.println("<input type=\"checkbox\" name=\"showPages\" value=\"1\"  " + strChecked + "></td></tr>");
        out.println("<tr><td  width=\"250\" align=right>" + paramRequest.getLocaleString("pageNumbersToShow") + " (1, 2, 3, 4, ...)</td><td align=\"left\">");
        out.println("<input type=\"text\" name=\"pages\" value=\"" + pagesHistory + "\"></td></tr>");
        out.println("</table>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        SWBResourceURL urlRet = paramRequest.getRenderUrl();
        urlRet.setMode(paramRequest.Mode_VIEW);
        urlRet.setParameter("title", request.getParameter("title"));
        if (request.getParameter("id") != null) {
            urlRet.setParameter("id", request.getParameter("id"));
            out.println("<input type=hidden name=id value=\"" + request.getParameter("id") + "\">");
        }

        if (request.getParameter("tp") != null) {
            urlRet.setParameter("tp", request.getParameter("tp"));
            out.println("<input type=hidden name=tp value=\"" + request.getParameter("tp") + "\">");
        } else {
            out.println("<input type=hidden name=tp value=\"" + paramRequest.getWebPage().getId() + "\">");
        }

        if (request.getParameter("tm") != null) {
            urlRet.setParameter("tm", request.getParameter("tm"));
            out.println("<input type=hidden name=tm value=\"" + request.getParameter("tm") + "\">");
        } else {
            out.println("<input type=hidden name=tm value=\"" + paramRequest.getWebPage().getWebSiteId() + "\">");
        }

        out.println("<input type=submit value=\"" + paramRequest.getLocaleString("btnSend") + "\" name=\"btn_update\">&nbsp;&nbsp;");
        out.println("</form>");
        out.println("</fieldset>");
        out.println("</div>");

    }

    /**
     * User View of WBALogs Resource.
     * 
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest a list of objects (user, topic, action, ...)
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doView()");
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        int rowNumber = 0;

        User user = paramRequest.getUser();
        String pURI = request.getParameter("suri");  //uri del objeto
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject so = ont.getSemanticObject(pURI);
        boolean isModel = so.getSemanticClass().isSWBModel();
        boolean showAllLog = false;
        if (request.getParameter("all") != null && request.getParameter("all").equals("showAllLog")) {
            showAllLog = true;
        }
        String pModelId = so.getModel().getName();
        String actionRow = "1";
        String descriptionRow = "1";
        String dateRow = "1";
        String userRow = "1";
        String objRow = "1";
        String classRow = "1";
        String rowsHistory = "20";
        String nextHistory = paramRequest.getLocaleString("defaultValueNext");
        String backHistory = paramRequest.getLocaleString("defaultValuePrevious");
        String pagesHistory = "10";
        String showPages = "1";
        String pTipo = "";
        if (base.getAttribute("action") != null) {
            actionRow = base.getAttribute("action");
        }
        if (base.getAttribute("description") != null) {
            descriptionRow = base.getAttribute("description");
        }
        if (base.getAttribute("date") != null) {
            dateRow = base.getAttribute("date");
        }
        if (base.getAttribute("user") != null) {
            userRow = base.getAttribute("user");
        }
        if (base.getAttribute("obj") != null) {
            objRow = base.getAttribute("obj");
        }
        if (base.getAttribute("class") != null) {
            classRow = base.getAttribute("class");
        }
        if (base.getAttribute("rows") != null) {
            rowsHistory = base.getAttribute("rows");
        }
        if (base.getAttribute("pages") != null) {
            pagesHistory = base.getAttribute("pages");
        }
        if (base.getAttribute("showPages") != null) {
            showPages = base.getAttribute("showPages");
        }

        boolean rowAction = false;
        boolean rowDescription = false;
        boolean rowUser = false;
        boolean rowObj = false;
        boolean rowClass = false;
        boolean rowDate = false;
        int maximo = 5;
        if (pagesHistory != null) {
            maximo = Integer.parseInt(pagesHistory);
        }
        int maxRows = 10;
        if (rowsHistory != null) {
            maxRows = Integer.parseInt(rowsHistory);
        }
        int actualPage = 1;
        if (request.getParameter("actualPage") != null) {
            actualPage = Integer.parseInt(request.getParameter("actualPage"));
        }
        out.println("<div class=\"swbform\">");
        out.println("<fieldset>");
        out.println("<table width=\"98%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" >");
        out.println("<theader>");
        out.println("<tr>");
        if (actionRow != null) {
            if (actionRow.equals("1")) {
                out.println("<th >" + paramRequest.getLocaleString("thAction") + "</th>");
                rowNumber++;
                rowAction = true;
            }
        }
        if (descriptionRow != null) {
            if (descriptionRow.equals("1")) {
                out.println("<th>" + paramRequest.getLocaleString("thDescription") + "</th>");
                rowNumber++;
                rowDescription = true;
            }
        }
        if (userRow != null) {
            if (userRow.equals("1")) {
                out.println("<th>" + paramRequest.getLocaleString("thPerformedBy") + "</th>");
                rowNumber++;
                rowUser = true;
            }
        }
        if (classRow != null) {
            if (classRow.equals("1")) {
                out.println("<th>" + paramRequest.getLocaleString("thClass") + "</th>");
                rowNumber++;
                rowClass = true;
            }
        }
        if (objRow != null) {
            if (objRow.equals("1")) {
                out.println("<th>" + paramRequest.getLocaleString("thElementID") + "</th>");
                rowNumber++;
                rowObj = true;
            }
        }

        if (dateRow != null) {
            if (dateRow.equals("1")) {
                out.println("<th>" + paramRequest.getLocaleString("thDate") + "</th>");
                rowNumber++;
                rowDate = true;
            }
        }
        out.println("</tr>");
        out.println("</theader>");
        int numReg = 0;
        try {
            SWBRecAdmLog obj = null;

            Iterator<SWBRecAdmLog> iter = null;
            if (so != null && so.createGenericInstance() instanceof User) {
                //System.out.println("Log de usuario..."+pURI+", rep usr: "+pModelId);
                iter = SWBPortal.getDBAdmLog().getUserLog(pURI);
            } else {
                iter = SWBPortal.getDBAdmLog().getBitaObjURI(pModelId, pURI);
            }

            if (isModel && showAllLog) {
                iter = SWBPortal.getDBAdmLog().getBitaObjURI(pModelId, null);
            }
            numReg = 0;
            while (iter.hasNext()) {
                obj = iter.next();
                numReg++;
            }

            //System.out.println("Registros en el iterador ... "+numReg);

            int cuenta = 0;
            if (request.getParameter("actualPage") != null) {
                actualPage = Integer.parseInt(request.getParameter("actualPage"));
            }
            int rangoIni = 1;
            int rangoFin = maxRows * actualPage;
            if (actualPage > 1) {
                rangoIni = (maxRows * (actualPage - 1)) + 1;
            }
            String rowColor = "";
            boolean cambiaColor = true;
            if (so != null && so.createGenericInstance() instanceof User) {
                //System.out.println("Log de usuario..."+pURI+", rep usr: "+pModelId);
                iter = SWBPortal.getDBAdmLog().getUserLog(pURI);
            } else {
                iter = SWBPortal.getDBAdmLog().getBitaObjURI(pModelId, pURI);
            }

            if (isModel && showAllLog) {
                iter = SWBPortal.getDBAdmLog().getBitaObjURI(pModelId, null);
            }
            out.println("<tbody>");
            while (iter.hasNext()) {
                obj = iter.next();
                cuenta++;
                rowColor = "#EFEDEC";
                if (!cambiaColor) {
                    rowColor = "#FFFFFF";
                }
                cambiaColor = !(cambiaColor);
                if (cuenta >= rangoIni && cuenta <= rangoFin) {
                    out.println("<tr>"); // bgcolor=\""+rowColor+"\"
                    if (rowAction) {
                        out.println("  <td>" + obj.getAction() + "</td>");
                    }
                    if (rowDescription) {
                        //out.println("  <td class=\"datos\">" + ont.getSemanticProperty(obj.getPropId()).getDisplayName(user.getLanguage()) + "</td>");
                        out.println("  <td>" + ont.getSemanticProperty(obj.getPropId()).getDisplayName(user.getLanguage()) + "</td>");
                    }
                    if (rowUser) {
                        String userId = obj.getUser();
                        if (userId.length() > 1) {
                            log.debug("Usuario: " + userId + " - " + so.getModel().getModelObject().getURI());
                            User rUser = (User) ont.getGenericObject(userId);
                            String usrname = userId;

                            if (null != rUser) {
                                usrname = (rUser.getFirstName() != null ? rUser.getFirstName() : " ") + " " + (rUser.getLastName() != null ? rUser.getLastName() : " ");
                            }

                            out.println("  <td>" + usrname + "</td>");
                        } else {
                            out.println("  <td>" + userId + "</td>");
                        }
                    }
                    if (rowClass) {
                        String classId = obj.getDbobject();
                        if (null != classId) {
                            log.debug("ClassID: " + classId + " - " + so.getModel().getModelObject().getURI());
                            classId = classId.substring(classId.indexOf("#") + 1, classId.lastIndexOf(":"));
                            out.println("  <td>" + classId + "</td>");
                        } else {
                            out.println("  <td>" + "---" + "</td>");
                        }
                    }
                    if (rowObj) {
                        String objId = obj.getDbobject();
                        if (objId.length() > 1) {
                            log.debug("ObjID: " + objId + " - " + so.getModel().getModelObject().getURI());
                            objId = objId.substring(objId.lastIndexOf(":") + 1);
                            out.println("  <td>" + objId + "</td>");
                        } else {
                            out.println("  <td>" + "---" + "</td>");
                        }
                    }
                    if (rowDate) {
                        out.println("  <td>" + SWBUtils.TEXT.iso8601DateFormat(obj.getDate()) + "</td>");
                    }
                    out.println("</tr>");
                }
                if (cuenta >= rangoFin) {
                    //break;
                }
            }
        } catch (Exception e) {
            log.error(e);
        }

        out.println("</table>");
        out.println("</fieldset>");
        int numTotPages = (int) Math.round(numReg / maxRows);
        if ((numReg % maxRows) > 0) {
            numTotPages++;
        }
        maximo = maximo - 1;
        StringBuffer numeros = new StringBuffer("");
        if (showPages != null) {
            if (showPages.equals("1")) {
                int numPages = 1;
                //estableciendo rangos para mostrar las paginas correspondientes
                int rInicio = 1;
                int rFinal = maximo;
                if (numTotPages >= (actualPage + maximo)) {
                    rInicio = actualPage;
                    rFinal = actualPage + maximo;
                } else {
                    if (numTotPages < (actualPage + maximo)) {
                        rInicio = numTotPages - maximo;
                        rFinal = numTotPages;
                    }
                }
                if (rInicio < 1) {
                    rInicio = 1;
                }
                for (int i = 1; i <= numTotPages; i++) {
                    SWBResourceURL urlNums = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
                    urlNums.setParameter("actualPage", Integer.toString(i));
                    urlNums.setParameter("suri", pURI);//por id
                    if (showAllLog) {
                        urlNums.setParameter("all", "showAllLog");
                    }
                    if (i >= rInicio && i <= rFinal) {
                        numPages++;
                        if (i != actualPage) {
                            numeros.append("<a href=\"#\" onclick=\"submitUrl('" + urlNums.toString() + "',this); return false;\" >" + i + "</a>");
                        } else {
                            numeros.append("" + i + "");
                        }
                    }
                    if (i < numTotPages) {
                        numeros.append("&nbsp;");
                    }
                }
                numeros.append("&nbsp;");
            }
        }
        SWBResourceURL urlBack = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
        int tmpBack = actualPage;
        if (actualPage > 1) {
            tmpBack--;
        }
        urlBack.setParameter("actualPage", Integer.toString(tmpBack));
        urlBack.setParameter("suri", pURI);
        if (showAllLog) {
            urlBack.setParameter("all", "showAllLog");
        }
        SWBResourceURL urlNext = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
        urlNext.setParameter("actualPage", Integer.toString(actualPage + 1));
        urlNext.setParameter("suri", pURI);
        if (showAllLog) {
            urlNext.setParameter("all", "showAllLog");
        }
        if (numReg <= maxRows) {
        } else {
            out.println("<fieldset>");
            out.println("<div align=\"center\">");
            if (actualPage == 1) {
                out.println(numeros + "<a href=\"#\" onclick=\"submitUrl('" + urlNext + "',this); return false;\" >" + nextHistory + "</a>");
            } else if (actualPage == numTotPages) {
                out.println("<a href=\"#\" onclick=\"submitUrl('" + urlBack + "',this); return false;\" >" + backHistory + "</a>&nbsp;" + numeros);
            } else {
                out.println("<a href=\"#\" onclick=\"submitUrl('" + urlBack + "',this); return false;\" >" + backHistory + "</a>&nbsp;" + numeros + "<a href=\"#\" onclick=\"submitUrl('" + urlNext + "',this); return false;\" >" + nextHistory + "</a>");
            }
            out.println("</div>");
            out.println("</fieldset>");
        }

        out.println("<fieldset>");
        out.println("<form method=post action=\"" + paramRequest.getRenderUrl().toString() + "\" name=\"" + pURI + "/frmContent\" id=\"" + pURI + "/frmContent\">");
        SWBResourceURL url1 = paramRequest.getRenderUrl();
        if (showAllLog) {
            url1.setParameter("all", "showAllLog");
        }
        url1.setCallMethod(SWBResourceURL.Call_DIRECT);
        url1.setMode(SWBResourceURL.Mode_XML);
        if (pURI != null) {
            url1.setParameter("suri", pURI);
        }

        SWBResourceURL url2 = paramRequest.getRenderUrl();
        if (showAllLog) {
            url2.setParameter("all", "showAllLog");
        }
        url2.setCallMethod(SWBResourceURL.Call_DIRECT);
        url2.setMode("Excel");
        if (pURI != null) {
            url2.setParameter("suri", pURI);
        }

        out.println("<button dojoType=\"dijit.form.Button\" type=\"button\"  onclick=\"window.open('" + url1 + "');\">" + paramRequest.getLocaleString("msg_export") + " XML</button>"); //submitUrl('#',this.domNode);  //_onclick=\"showDialog('"+url1+"'); return false;\"
        out.println("<button dojoType=\"dijit.form.Button\" type=\"button\"  onclick=\"window.open('" + url2 + "'); return false;\">" + paramRequest.getLocaleString("msg_export") + " Excel</button>"); //submitUrl('#',this.domNode);

        SWBResourceURL url = paramRequest.getRenderUrl();
        if (null != pURI) {
            url.setParameter("suri", pURI);
        }
        if (showAllLog) {
            url.setParameter("all", "showAllLog");
        }
        out.println("<button dojoType=\"dijit.form.Button\" type=\"button\"  onclick=\"submitUrl('" + url + "',this.domNode); return false;\">" + paramRequest.getLocaleString("msg_reload") + "</button>");
        if (isModel && !showAllLog) {
            SWBResourceURL urlsall = paramRequest.getRenderUrl();
            urlsall.setParameter("suri", pURI);
            urlsall.setParameter("all", "showAllLog");
            out.println("<button dojoType=\"dijit.form.Button\" type=\"button\"  onclick=\"submitUrl('" + urlsall + "',this.domNode); return false;\">" + paramRequest.getLocaleString("msg_allLog") + "</button>");
        } else if (isModel && showAllLog) {
            SWBResourceURL urlsalln = paramRequest.getRenderUrl();
            urlsalln.setParameter("suri", pURI);
            urlsalln.setParameter("all", "no");
            out.println("<button dojoType=\"dijit.form.Button\" type=\"button\"  onclick=\"submitUrl('" + urlsalln + "',this.domNode); return false;\">" + paramRequest.getLocaleString("msg_modelLog") + "</button>");
        }
        out.println("</form>");
        out.println("</fieldset>");
        out.println("</div>");
    }

    /**
     * Generate the log in Excel format.
     * 
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest list of objects
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException an IO Exception
     */
    public void doExcel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        //response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        //response.setHeader("Content-Disposition", "inline; filename=\"Excel Report\";");
        response.setHeader("Content-Disposition", "attachment; filename=\"Excel Report.xls\";");

        log.debug("doExcel()");
        Resource base = getResourceBase(); //paramRequest
        PrintWriter out = response.getWriter();
        int rowNumber = 0;

        User user = paramRequest.getUser();
        String pURI = request.getParameter("suri");  //uri del objeto
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject so = ont.getSemanticObject(pURI);
        boolean isModel = so.getSemanticClass().isSWBModel();
        boolean showAllLog = false;
        if (request.getParameter("all") != null && request.getParameter("all").equals("showAllLog")) {
            showAllLog = true;
        }
        String pModelId = so.getModel().getName();

        String actionRow = "1";
        String descriptionRow = "1";
        String dateRow = "1";
        String userRow = "1";
        String pTipo = "";
        boolean rowObj = false;
        boolean rowClass = false;
        String objRow = "1";
        String classRow = "1";

        if (base.getAttribute("action") != null) {
            actionRow = base.getAttribute("action");
        }
        if (base.getAttribute("description") != null) {
            descriptionRow = base.getAttribute("description");
        }
        if (base.getAttribute("date") != null) {
            dateRow = base.getAttribute("date");
        }
        if (base.getAttribute("obj") != null) {
            objRow = base.getAttribute("obj");
        }
        if (base.getAttribute("class") != null) {
            classRow = base.getAttribute("class");
        }
        if (base.getAttribute("user") != null) {
            userRow = base.getAttribute("user");
        }
        if (base.getAttribute("tipo") != null) {
            pTipo = base.getAttribute("tipo");
        }

        //if(pTopic==null && pTipo.equals("Topic")) pTopic = paramRequest.getWebPage().getId();

        boolean rowAction = false;
        boolean rowDescription = false;
        boolean rowUser = false;
        boolean rowDate = false;
        String ctype = "application/vnd.ms-excel";
        response.setContentType(ctype);
        out.println("<table>");
        out.println("<tr>");
        if (actionRow != null) {
            if (actionRow.equals("1")) {
                out.println("<th>" + paramRequest.getLocaleString("thAction") + "</th>");
                rowNumber++;
                rowAction = true;
            }
        }
        if (descriptionRow != null) {
            if (descriptionRow.equals("1")) {
                out.println("<th>" + paramRequest.getLocaleString("thDescription") + "</th>");
                rowNumber++;
                rowDescription = true;
            }
        }
        if (userRow != null) {
            if (userRow.equals("1")) {
                out.println("<th>" + paramRequest.getLocaleString("thPerformedBy") + "</th>");
                rowNumber++;
                rowUser = true;
            }
        }
        if (classRow != null) {
            if (classRow.equals("1")) {
                out.println("<th>" + paramRequest.getLocaleString("thClass") + "</th>");
                rowNumber++;
                rowClass = true;
            }
        }
        if (objRow != null) {
            if (objRow.equals("1")) {
                out.println("<th>" + paramRequest.getLocaleString("thElementID") + "</th>");
                rowNumber++;
                rowObj = true;
            }
        }
        if (dateRow != null) {
            if (dateRow.equals("1")) {
                out.println("<th>Date</th>");
                rowNumber++;
                rowDate = true;
            }
        }
        out.println("</tr>");

        int numReg = 0;
        try {
            SWBRecAdmLog obj = null;
            Iterator<SWBRecAdmLog> iter = null;
            if (so != null && so.createGenericInstance() instanceof User) {
                //System.out.println("Log de usuario..."+pURI+", rep usr: "+pModelId);
                iter = SWBPortal.getDBAdmLog().getUserLog(pURI);
            } else {
                iter = SWBPortal.getDBAdmLog().getBitaObjURI(pModelId, pURI);
            }

            if (isModel && showAllLog) {
                iter = SWBPortal.getDBAdmLog().getBitaObjURI(pModelId, null);
            }
            numReg = 0;
            while (iter.hasNext()) {
                obj = iter.next();
                numReg++;
            }

            if (so != null && so.createGenericInstance() instanceof User) {
                //System.out.println("Log de usuario..."+pURI+", rep usr: "+pModelId);
                iter = SWBPortal.getDBAdmLog().getUserLog(pURI);
            } else {
                iter = SWBPortal.getDBAdmLog().getBitaObjURI(pModelId, pURI);
            }

            if (isModel && showAllLog) {
                iter = SWBPortal.getDBAdmLog().getBitaObjURI(pModelId, null);
            }
            while (iter.hasNext()) {
                obj = iter.next();
                out.println("<tr>");
                if (rowAction) {
                    out.println("  <td>" + obj.getAction() + "</td>");
                }
                if (rowDescription) {
                    out.println("  <td>" + ont.getSemanticProperty(obj.getPropId()).getDisplayName(user.getLanguage()) + "</td>");
                }
                if (rowUser) {
                    String userId = obj.getUser();
                    if (userId.length() > 1) {
                        log.debug("Usuario: " + userId + " - " + so.getModel().getModelObject().getURI());
                        //WebSite ws = SWBContext.getWebSite(so.getModel().getModelObject().getURI());
                        User rUser = (User) ont.getGenericObject(userId);
                        out.println("  <td>" + rUser.getFirstName() + " " + (rUser.getLastName() != null ? rUser.getLastName() : "") + "</td>");
                    } else {
                        out.println("  <td>" + userId + "</td>");
                    }
                }
                if (rowClass) {
                    String classId = obj.getDbobject();
                    if (null != classId) {
                        log.debug("ClassID: " + classId + " - " + so.getModel().getModelObject().getURI());
                        classId = classId.substring(classId.indexOf("#") + 1, classId.lastIndexOf(":"));
                        out.println("  <td>" + classId + "</td>");
                    } else {
                        out.println("  <td>" + "---" + "</td>");
                    }
                }
                if (rowObj) {
                    String objId = obj.getDbobject();
                    if (objId.length() > 1) {
                        log.debug("ObjID: " + objId + " - " + so.getModel().getModelObject().getURI());
                        objId = objId.substring(objId.lastIndexOf(":") + 1);
                        out.println("  <td>" + objId + "</td>");
                    } else {
                        out.println("  <td>" + "---" + "</td>");
                    }
                }
                if (rowDate) {
                    out.println("  <td>" + SWBUtils.TEXT.iso8601DateFormat(obj.getDate()) + "</td>");
                }
                out.println("</tr>");
            }
        } catch (Exception e) {
            log.error(e);
        }
        out.println("</table>");

    }

    /**
     * Generate the log in the XML format.
     * 
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest list of objects
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        //response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doXML()");
        Resource base = paramRequest.getResourceBase();
        int rowNumber = 0;

        User user = paramRequest.getUser();
        String pURI = request.getParameter("suri");
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject so = ont.getSemanticObject(pURI);
        boolean isModel = so.getSemanticClass().isSWBModel();
        boolean showAllLog = false;
        if (request.getParameter("all") != null && request.getParameter("all").equals("showAllLog")) {
            showAllLog = true;
        }
        String pModelId = so.getModel().getName();

        Document dom = SWBUtils.XML.getNewDocument();
        String actionRow = "1";
        String descriptionRow = "1";
        String dateRow = "1";
        String userRow = "1";
        String pTipo = "";
        boolean rowObj = false;
        boolean rowClass = false;
        String objRow = "1";
        String classRow = "1";
        if (base.getAttribute("action") != null) {
            actionRow = base.getAttribute("action");
        }
        if (base.getAttribute("description") != null) {
            descriptionRow = base.getAttribute("description");
        }
        if (base.getAttribute("obj") != null) {
            objRow = base.getAttribute("obj");
        }
        if (base.getAttribute("class") != null) {
            classRow = base.getAttribute("class");
        }
        if (base.getAttribute("date") != null) {
            dateRow = base.getAttribute("date");
        }
        if (base.getAttribute("user") != null) {
            userRow = base.getAttribute("user");
        }
        if (base.getAttribute("tipo") != null) {
            pTipo = base.getAttribute("tipo");
        }

        boolean rowAction = false;
        boolean rowDescription = false;
        boolean rowUser = false;
        boolean rowDate = false;
        String ctype = "application/xml";
        response.setContentType(ctype);
        if (actionRow != null) {
            if (actionRow.equals("1")) {
                rowNumber++;
                rowAction = true;
            }
        }
        if (descriptionRow != null) {
            if (descriptionRow.equals("1")) {
                rowNumber++;
                rowDescription = true;
            }
        }
        if (userRow != null) {
            if (userRow.equals("1")) {
                rowNumber++;
                rowUser = true;
            }
        }
        if (classRow != null) {
            if (classRow.equals("1")) {
                rowNumber++;
                rowClass = true;
            }
        }
        if (objRow != null) {
            if (objRow.equals("1")) {
                rowNumber++;
                rowObj = true;
            }
        }
        if (dateRow != null) {
            if (dateRow.equals("1")) {
                rowNumber++;
                rowDate = true;
            }
        }

        int numReg = 0;
        try {
            SWBRecAdmLog obj = null;
            Iterator<SWBRecAdmLog> iter = null;
            if (so != null && so.createGenericInstance() instanceof User) {
                //System.out.println("Log de usuario..."+pURI+", rep usr: "+pModelId);
                iter = SWBPortal.getDBAdmLog().getUserLog(pURI);
            } else {
                iter = SWBPortal.getDBAdmLog().getBitaObjURI(pModelId, pURI);
            }

            if (isModel && showAllLog) {
                iter = SWBPortal.getDBAdmLog().getBitaObjURI(pModelId, null);
            }
            numReg = 0;
            while (iter.hasNext()) {
                obj = iter.next();
                numReg++;
            }

            Element reporte = dom.createElement("Reporte");
            reporte.setAttribute("name", pTipo);
            reporte.setAttribute("registros", Integer.toString(numReg));
            Timestamp ts = new Timestamp((new Date()).getTime());
            reporte.setAttribute("Fecha", SWBUtils.TEXT.iso8601DateFormat(ts));
            dom.appendChild(reporte);

            if (so != null && so.createGenericInstance() instanceof User) {
                //System.out.println("Log de usuario..."+pURI+", rep usr: "+pModelId);
                iter = SWBPortal.getDBAdmLog().getUserLog(pURI);
            } else {
                iter = SWBPortal.getDBAdmLog().getBitaObjURI(pModelId, pURI);
            }

            if (isModel && showAllLog) {
                iter = SWBPortal.getDBAdmLog().getBitaObjURI(pModelId, null);
            }
            while (iter.hasNext()) {
                obj = iter.next();
                Element eleRec = dom.createElement("registro");
                if (rowAction) {
                    Element elecol1 = dom.createElement("column");
                    elecol1.setAttribute("name", paramRequest.getLocaleString("thAction"));
                    elecol1.appendChild(dom.createTextNode(obj.getAction()));
                    eleRec.appendChild(elecol1);
                }
                if (rowDescription) {
                    Element elecol2 = dom.createElement("column");
                    elecol2.setAttribute("name", paramRequest.getLocaleString("thDescription"));
                    elecol2.appendChild(dom.createTextNode(ont.getSemanticProperty(obj.getPropId()).getDisplayName(user.getLanguage())));
                    eleRec.appendChild(elecol2);
                }
                if (rowUser) {
                    String userId = obj.getUser();
                    Element elecol3 = dom.createElement("column");
                    elecol3.setAttribute("name", paramRequest.getLocaleString("thPerformedBy"));
                    String strUsr = "";
                    if (userId.length() > 1) {
                        User rUser = (User) ont.getGenericObject(userId);
                        strUsr = rUser.getFirstName() + " " + (rUser.getLastName() != null ? rUser.getLastName() : "");
                    } else {
                        strUsr = userId;
                    }

                    elecol3.appendChild(dom.createTextNode(strUsr));
                    eleRec.appendChild(elecol3);
                }
                if (rowClass) {
                    String classId = obj.getDbobject();
                    Element elecol4 = dom.createElement("column");
                    elecol4.setAttribute("name", paramRequest.getLocaleString("thClass")); //thElementID
                    if (null != classId) {
                        classId = classId.substring(classId.indexOf("#") + 1, classId.lastIndexOf(":"));
                    } else {
                        classId = "---";
                    }
                    elecol4.appendChild(dom.createTextNode(classId));
                    eleRec.appendChild(elecol4);
                }
                if (rowObj) {
                    String objId = obj.getDbobject();
                    Element elecol5 = dom.createElement("column");
                    elecol5.setAttribute("name", paramRequest.getLocaleString("thElementID")); 
                    if (null != objId) {
                         objId = objId.substring(objId.lastIndexOf(":") + 1);
                    } else {
                        objId = "---";
                    }
                    elecol5.appendChild(dom.createTextNode(objId));
                    eleRec.appendChild(elecol5);
                }
                if (rowDate) {
                    Element elecol6 = dom.createElement("column");
                    elecol6.setAttribute("name", paramRequest.getLocaleString("thDate"));
                    elecol6.appendChild(dom.createTextNode(SWBUtils.TEXT.iso8601DateFormat(obj.getDate())));
                    eleRec.appendChild(elecol6);
                }
                reporte.appendChild(eleRec);
            }
        } catch (Exception e) {
            log.error(e);
        }

        PrintWriter out = response.getWriter();
        out.println(SWBUtils.XML.domToXml(dom));
    }

    /**
     * Add and Update of WBALogs properties.
     * 
     * @param request parameters
     * @param response answer to the request
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        String accion = response.getAction();


//        System.out.println("processAction:"+accion);
        String actionRow = request.getParameter("action");
        String dateRow = request.getParameter("date");
        String userRow = request.getParameter("user");
        String descriptionRow = request.getParameter("description");
        String rowsHistory = request.getParameter("rows");
        String objRow = request.getParameter("obj");
        String classRow = request.getParameter("class");
        String pagesHistory = request.getParameter("pages");
        String showPages = request.getParameter("showPages");
        //String tipo = request.getParameter("tipo");
//        System.out.println("action Row:"+actionRow);
//        System.out.println("date Row:"+dateRow);
//        System.out.println("user Row:"+userRow);
//        System.out.println("description Row:"+descriptionRow);
//        System.out.println("rows:"+rowsHistory);
//        System.out.println("pages:"+pagesHistory);
//        System.out.println("show pages:"+showPages);
        if (accion != null) {
            if (accion.equals("update")) {
                if (actionRow == null) {
                    actionRow = "0";
                }
                base.setAttribute("action", actionRow);
                if (descriptionRow == null) {
                    descriptionRow = "0";
                }
                base.setAttribute("description", descriptionRow);
                if (dateRow == null) {
                    dateRow = "0";
                }
                base.setAttribute("date", dateRow);
                if (userRow == null) {
                    userRow = "0";
                }
                base.setAttribute("user", userRow);
                if (objRow == null) {
                    objRow = "0";
                }
                base.setAttribute("obj", objRow);
                if (classRow == null) {
                    classRow = "0";
                }
                base.setAttribute("class", classRow);
                if (rowsHistory == null | rowsHistory.length() == 0) {
                    rowsHistory = "20";
                }
                base.setAttribute("rows", rowsHistory);
                if (pagesHistory == null) {
                    pagesHistory = "10";
                }
                base.setAttribute("pages", pagesHistory);
                if (showPages == null) {
                    showPages = "0";
                }
                base.setAttribute("showPages", showPages);
//                if (tipo == null) {
//                    tipo = "";
//                }
                //base.setAttribute("tipo", tipo);
                try {
                    base.updateAttributesToDB();
                } catch (SWBException ex) {
                    log.error(ex);
                }
                if (request.getParameter("suri") != null) {
                    response.setRenderParameter("suri", request.getParameter("suri"));
                }

            }
        }
        response.setMode(response.Mode_ADMIN);
    }

    /**
     * Process the request to show the requested view.
     * 
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest list of objects
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("Excel")) {
            doExcel(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }
}
