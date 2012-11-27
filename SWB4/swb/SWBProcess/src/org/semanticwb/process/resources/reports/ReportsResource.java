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
package org.semanticwb.process.resources.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author hasdai
 */
public class ReportsResource extends GenericResource {
    public static Logger log = SWBUtils.getLogger(ReportsResource.class);
    private Comparator<UserRolesSegregationBean> userNameComparator = new Comparator<UserRolesSegregationBean>() {
        @Override
        public int compare(UserRolesSegregationBean o1, UserRolesSegregationBean o2) {
            return o1.getUserName().compareToIgnoreCase(o2.getUserName());
        }
    };
    
    private Comparator<UserRolesSegregationBean> processNameComparator = new Comparator<UserRolesSegregationBean>() {
        @Override
        public int compare(UserRolesSegregationBean o1, UserRolesSegregationBean o2) {
            return o1.getProcessName().compareToIgnoreCase(o2.getProcessName());
        }
    };
    
    private Comparator<UserRolesSegregationBean> roleNameComparator = new Comparator<UserRolesSegregationBean>() {
        @Override
        public int compare(UserRolesSegregationBean o1, UserRolesSegregationBean o2) {
            return o1.getRoleName().compareToIgnoreCase(o2.getRoleName());
        }
    };

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/reports/reports.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);

        try {
            request.setAttribute("paramRequest", paramRequest);
            rd.include(request, response);
        } catch (Exception e) {
            log.error("ReportResource: Error including view JSP", e);
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode.equals("URSReport")) {
            doShowURSReport(request, response, paramRequest);
        } else if (mode.equals("TRSReport")) {
            doShowTRSReport(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doShowURSReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/reports/userRoleSegregation.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        ArrayList<UserRolesSegregationBean> beans = getURSBeans(paramRequest, request, paramRequest.getWebPage().getWebSite());
        
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("beans", beans);
            rd.include(request, response);
        } catch (Exception e) {
            log.error("ReportResource: Error including showURS JSP", e);
        }
    }
    
    public void doShowTRSReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/reports/taskRoleSegregation.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        ArrayList<ProcessBean> beans = getTRSBeans(paramRequest, request, paramRequest.getWebPage().getWebSite());
        
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("beans", beans);
            rd.include(request, response);
        } catch (Exception e) {
            log.error("ReportResource: Error including showTRS JSP", e);
        }
    }
    
    public ArrayList<UserRolesSegregationBean> getURSBeans(SWBParamRequest paramRequest, HttpServletRequest request, WebSite site) {
        ArrayList<UserRolesSegregationBean> temp = UserRolesSegregationReport.generateBeans(site);
        String inPath = SWBPortal.getWorkPath() + "/models/" + site.getId() + "/jsp/process/reports/URSReportTemplate.xls";
        String outPath = SWBPortal.getWorkPath() + "/models/" + site.getId() + "/reports/URSReport.xls";
        
        ArrayList<UserRolesSegregationBean> ret = new ArrayList<UserRolesSegregationBean>();
        
        String sortType = request.getParameter("sort");
        int itemsPerPage = 30;//getItemsPerPage();
        int page = 1;

        if (sortType == null || sortType.trim().equals("")) {
            sortType = "user";
        } else {
            sortType = sortType.trim();
        }

        if (request.getParameter("page") != null && !request.getParameter("page").trim().equals("")) {
            page = Integer.valueOf(request.getParameter("page"));
            if (page < 0) page = 1;
        }

        if (itemsPerPage < 5) itemsPerPage = 5;
        
        if (sortType.equals("user")) {
            Collections.sort(temp, userNameComparator);
        } else if (sortType.equals("process")) {
            Collections.sort(temp, processNameComparator);
        } else if (sortType.equals("role")) {
            Collections.sort(temp, roleNameComparator);
        }

        UserRolesSegregationReport.generateReport(inPath, outPath, temp);
        
        int maxPages = 1;
        if (temp.size() >= itemsPerPage) {
            maxPages = (int)Math.ceil((double)temp.size() / itemsPerPage);
        }
        
        if (page > maxPages) page = maxPages;

        int sIndex = (page - 1) * itemsPerPage;
        if (temp.size() > itemsPerPage && sIndex >= temp.size() - 1) {
            sIndex = temp.size() - itemsPerPage;
        }

        int eIndex = sIndex + itemsPerPage;
        if (eIndex >= temp.size()) eIndex = temp.size();

        request.setAttribute("maxPages", maxPages);
        for (int i = sIndex; i < eIndex; i++) {
            UserRolesSegregationBean instance = temp.get(i);
            ret.add(instance);
        }
        request.setAttribute("downPath", SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/reports/URSReport.xls");
        
        return ret;
    }
    
    public ArrayList<ProcessBean> getTRSBeans(SWBParamRequest paramRequest, HttpServletRequest request, WebSite site) {
        ArrayList<ProcessBean> temp = TaskRoleSegregationReport.generateBeans(site);
        String inPath = SWBPortal.getWorkPath() + "/models/" + site.getId() + "/jsp/process/reports/TRSReportTemplate.xls";
        String outPath = SWBPortal.getWorkPath() + "/models/" + site.getId() + "/reports/TRSReport.xls";
        
        ArrayList<ProcessBean> ret = new ArrayList<ProcessBean>();
        
        String sortType = request.getParameter("sort");
        int itemsPerPage = 30;//getItemsPerPage();
        int page = 1;

        if (sortType == null || sortType.trim().equals("")) {
            sortType = "user";
        } else {
            sortType = sortType.trim();
        }

        if (request.getParameter("page") != null && !request.getParameter("page").trim().equals("")) {
            page = Integer.valueOf(request.getParameter("page"));
            if (page < 0) page = 1;
        }

        if (itemsPerPage < 5) itemsPerPage = 5;
        
//        if (sortType.equals("user")) {
//            Collections.sort(temp, userNameComparator);
//        } else if (sortType.equals("process")) {
//            Collections.sort(temp, processNameComparator);
//        } else if (sortType.equals("role")) {
//            Collections.sort(temp, roleNameComparator);
//        }

        TaskRoleSegregationReport.generateReport(inPath, outPath, temp);
        
        int maxPages = 1;
        if (temp.size() >= itemsPerPage) {
            maxPages = (int)Math.ceil((double)temp.size() / itemsPerPage);
        }
        
        if (page > maxPages) page = maxPages;

        int sIndex = (page - 1) * itemsPerPage;
        if (temp.size() > itemsPerPage && sIndex >= temp.size() - 1) {
            sIndex = temp.size() - itemsPerPage;
        }

        int eIndex = sIndex + itemsPerPage;
        if (eIndex >= temp.size()) eIndex = temp.size();

        request.setAttribute("maxPages", maxPages);
        for (int i = sIndex; i < eIndex; i++) {
            ProcessBean instance = temp.get(i);
            ret.add(instance);
        }
        request.setAttribute("downPath", SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/reports/TRSReport.xls");
        
        return ret;
    }
}
