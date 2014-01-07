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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.w3c.dom.*;

import javax.servlet.http.*;
import javax.servlet.*;
import org.semanticwb.*;

import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/** Recurso para la administarci�n de WebBuilder que permite seleccionar los t�picos
 * en los cuales se mostrar� el recurso seleccionado.
 *
 * WebBuilder administration resource that allows select the topics to show the
 * selected resource.
 */
public class SWBAUserFilter extends SWBATree {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBAFilterResource.class);
    /** The Constant pathValids. */
    static final String[] pathValids = {"getServer", "getTopic", "getTopicMap"};
    /** The Constant namevalids. */
    static final String[] namevalids = {"node", "config", "icons", "icon", "res", "events", "willExpand"};

    /**
     * Process request.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        //System.out.println("processRequest:"+paramRequest.getMode());
        if (paramRequest.getMode().equals("gateway")) {
            doGateway(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Do gateway.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        ServletInputStream in = request.getInputStream();
        Document dom = SWBUtils.XML.xmlToDom(in);
        if (!dom.getFirstChild().getNodeName().equals("req")) {
            response.sendError(404, request.getRequestURI());
            return;
        }
        String cmd = null;
        if (dom.getElementsByTagName("cmd").getLength() > 0) {
            cmd = dom.getElementsByTagName("cmd").item(0).getFirstChild().getNodeValue();
        }

        if (cmd == null) {
            response.sendError(404, request.getRequestURI());
            return;
        }
        //System.out.println("doGateway:"+cmd);
        
        String ret = "";
        try {
            Document res = null;
            if (cmd.equals("update")) {
                res = updateFilter(cmd, dom, paramRequest.getUser(), request, response);
            } else if (cmd.equals("getFilter")) {
                res = getFilter(cmd, dom, paramRequest.getUser(), request, response);
            } else {
                res = getService(cmd, dom, paramRequest.getUser(), request, response);
            }
            if (res == null) {
                ret = SWBUtils.XML.domToXml(getError(3));
            } else {
                ret = SWBUtils.XML.domToXml(res, true);
            }
        } catch (Exception e) {
            log.error(e);
        }
        out.print(new String(ret.getBytes()));

    }

    /**
     * Adds the.
     * 
     * @param cmd the cmd
     * @param src the src
     * @param user the user
     * @param request the request
     * @param response the response
     * @return the document
     * @return
     */
    public Document add(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response) {
        Document doc = null;
        try {
            doc = SWBUtils.XML.getNewDocument();
            Element res = doc.createElement("res");
            doc.appendChild(res);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            log.error(e);
        }
        return doc;
    }

    /**
     * Update filter.
     * 
     * @param cmd the cmd
     * @param src the src
     * @param user the user
     * @param request the request
     * @param response the response
     * @return the document
     * @return
     */
    public Document updateFilter(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response) {

        if (src.getElementsByTagName("filter").getLength() > 0) {
            String id = src.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
            String tm = src.getElementsByTagName("tm").item(0).getFirstChild().getNodeValue();
            SWBModel map = SWBContext.getSWBModel(tm);
            SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
            
            ArrayList<User> arr=new ArrayList();
            
            if(id.startsWith("mv:"))
            {
                String id2=id.substring(3);
                //System.out.println("id2:"+id2);
                StringTokenizer st=new StringTokenizer(id2,"|");
                while(st.hasMoreTokens())
                {
                    arr.add(User.ClassMgr.getUser(st.nextToken(), SWBContext.getSWBModel(tm)));
                    //System.out.println("add");
                }
            }else
            {
                arr.add((User)ont.getGenericObject(id));
            }
            
            Iterator<User> it=arr.iterator();
            while (it.hasNext())
            {
                User recres = it.next();
                map = recres.getUserRepository();                
                //System.out.println("user:"+recres);
                
                log.debug("updateFilter...id:" + id + ", tm:" + tm);
                String xml = "<resource><filter/></resource>";
                UserFilter pfil = recres.getUserFilter();
                if (pfil != null) {
                    xml = pfil.getXml();
                }
                
                if (null == pfil) {
                    pfil = UserFilter.ClassMgr.createUserFilter(recres.getUserRepository());
                    recres.setUserFilter(pfil);
                }

                try {
                    Document docxmlConf = null;
                    Element eResource = null;
                    if (xml == null || xml.equals("")) {
                        docxmlConf = SWBUtils.XML.getNewDocument();
                        eResource = docxmlConf.createElement("resource");
                        docxmlConf.appendChild(eResource);
                    } else {

                        //System.out.println("XML antes de actualizar:\n"+xml);

                        if(xml.indexOf("<resource>")==-1)
                        {
                            int intIniIdx = xml.indexOf("<filter>");
                            int intEndIdx = xml.lastIndexOf("</filter>");
                            if (intIniIdx != -1 && intEndIdx != -1) {
                                xml = xml.substring(intIniIdx,intEndIdx+9);
                                xml = "<resource>"+xml+"</resource>";
                                //System.out.println("XML:"+xml);
                            }
                        }

                        docxmlConf = SWBUtils.XML.xmlToDom(xml);
                        if (docxmlConf.getElementsByTagName("resource").getLength() > 0) {
                            eResource = (Element) docxmlConf.getElementsByTagName("resource").item(0);
                        } else {
                            eResource = docxmlConf.createElement("resource");
                            docxmlConf.appendChild(eResource);
                        }
                    }
                    NodeList filters = eResource.getElementsByTagName("filter");
                    for (int i = 0; i < filters.getLength(); i++) {
                        Element filter = (Element) filters.item(i);
                        filter.getParentNode().removeChild(filter);
                    }
                    filters = src.getElementsByTagName("filter");
                    for (int i = 0; i < filters.getLength(); i++) {
                        Element filter = (Element) filters.item(i);
                        filter = (Element) docxmlConf.importNode(filter, true);
                        eResource.appendChild(filter);
                    }

                    log.debug(SWBUtils.XML.domToXml(docxmlConf));

                    pfil = recres.getUserFilter();
                    if (null != recres && null != pfil) {
                        pfil.setXml(SWBUtils.XML.domToXml(docxmlConf));
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                    log.error(e);
                }                
            }

            Document docresp = SWBUtils.XML.getNewDocument();
            Element filterresp = docresp.createElement("filter");
            org.w3c.dom.Text t = docresp.createTextNode(id);
            filterresp.appendChild(t);
            docresp.appendChild(filterresp);
            return docresp;
        }
        return null;
    }

    /**
     * Inits the tree.
     * 
     * @param user the user
     * @param src the src
     * @return the document
     * @return
     */
    @Override
    public Document initTree(User user, Document src) {
        Document doc = super.initTree(user, src, true);
        RevisaNodo(doc.getFirstChild());
        return doc;
    }

    /**
     * Checks if is name valid.
     * 
     * @param e the e
     * @return true, if is name valid
     * @return
     */
    @SuppressWarnings({"static-access", "static-access"})
    public boolean isNameValid(Element e) {

        for (int i = 0; i < SWBAFilterResource.namevalids.length; i++) {
            if (e.getNodeName().equals(SWBAFilterResource.namevalids[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if is valid.
     * 
     * @param path the path
     * @return true, if is valid
     * @return
     */
    @SuppressWarnings({"static-access", "static-access"})
    public boolean isValid(String path) {
        if (path == null) {
            return true;
        }
        StringTokenizer st = new StringTokenizer(path, ".");
        if (st.countTokens() > 0) {
            String pathinit = st.nextToken();
            for (int i = 0; i < SWBAFilterResource.pathValids.length; i++) {
                if (pathinit.equals(SWBAFilterResource.pathValids[i])) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    /**
     * Revisa nodo.
     * 
     * @param ele the ele
     */
    public void RevisaNodo(Node ele) {
        Vector vnodes = new Vector();
        NodeList nodes = ele.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            vnodes.add(nodes.item(i));
        }
        for (int i = 0; i < vnodes.size(); i++) {
            if (vnodes.elementAt(i) instanceof Element) {
                Element e = (Element) vnodes.elementAt(i);
                if (!isNameValid(e) || !isValid(e.getAttribute("reload"))) {
                    ele.removeChild((Node) vnodes.elementAt(i));
                } else {
                    RevisaNodo(e);
                }
            } else {
                RevisaNodo((Node) vnodes.elementAt(i));
            }
        }
    }

    /**
     * Gets the filter.
     * 
     * @param cmd the cmd
     * @param src the src
     * @param user the user
     * @param request the request
     * @param response the response
     * @return the filter
     * @return
     */
    public Document getFilter(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response) {
        String id = src.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
        String tm = src.getElementsByTagName("tm").item(0).getFirstChild().getNodeValue();

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        
        if(id.startsWith("mv:"))
        {
            id=id.substring(3);
            StringTokenizer st=new StringTokenizer(id,"|");
            if(st.hasMoreTokens())
            {
                id=User.ClassMgr.getUser(st.nextToken(), SWBContext.getSWBModel(tm)).getURI();
            }
        }
        
        GenericObject gobj = ont.getGenericObject(id);

        SWBModel map = null;

        User recres = null;

        if (gobj instanceof User) {
            recres = (User) gobj;
            map = recres.getUserRepository();
        } else {
            return null;
        }

        String xml = null;
        UserFilter pfil = recres.getUserFilter();
        if (null != pfil) {
            xml = pfil.getXml();
        }

        Document docres = null;
        if (xml != null) {

            try {
                docres = SWBUtils.XML.getNewDocument();
                Element res = docres.createElement("resource");
                docres.appendChild(res);
                Document docconf = SWBUtils.XML.xmlToDom(xml);
                if (docconf != null) {
                    NodeList filters = docconf.getElementsByTagName("filter");
                    for (int i = 0; i < filters.getLength(); i++) {
                        Element filter = (Element) filters.item(i);
                        filter = (Element) docres.importNode(filter, true);
                        res.appendChild(filter);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
                log.error(e);
            }
        }
        //System.out.println("loaded xml:"+SWBUtils.XML.domToXml(docres));
        return docres;
    }

    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        String strWBAction = request.getParameter("act");
        if (strWBAction == null || strWBAction != null && strWBAction.equals("") || strWBAction != null && strWBAction.equals("view")) {
            getIniForm(request, response, paramRequest, paramRequest.getUser());
        } else if (strWBAction != null && strWBAction.equals("add")) {
        } else if (strWBAction != null && strWBAction.equals("edit")) {
        }
    }

    /**
     * Process action.
     * 
     * @param request the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        //String strWBAction=request.getParameter("act");
        //System.out.println("Llego aqui");
        String tm = request.getParameter("tm");
        String[] strTopics = request.getParameterValues("tps");
        Document dom = null;
        Element elmRes = null;
        String id = "0";
        if (request.getParameter("id") != null) {
            id = request.getParameter("id");
        }
        User recRes = SWBContext.getUserRepository(tm).getUser(id);
        UserFilter pfil = recRes.getUserFilter();
        String strXml = null;
        if (null != pfil) {
            strXml = pfil.getXml();
        }
        // Se genera el XML
        if (strXml == null || strXml != null && strXml.equals("")) {
            try {

                dom = SWBUtils.XML.getNewDocument();
                elmRes = dom.createElement("resource");
                dom.appendChild(elmRes);
                Element filter = dom.createElement("filter");
                elmRes.appendChild(filter);
                String strTm = null;
                String strTp = null;
                if (strTopics != null) {
                    for (int counter = 0; counter < strTopics.length; counter++) {
                        StringTokenizer st = new StringTokenizer(strTopics[counter], "|");
                        while (st.hasMoreTokens()) {
                            strTm = st.nextToken();
                            strTp = st.nextToken();
                        }
                        dom = setElement(dom, strTm, strTp, "add");
                    }
                }
                pfil.setXml(SWBUtils.XML.domToXml(dom));
                response.setRenderParameter("confirm", "added");
            } catch (Exception e) {
                log.error("Error while updating resource with id:" + id + "- SWBAFilterResource:processAction", e);
            }
        } else {
            try {
                int intIniIdx = strXml.indexOf("<filter>");
                int intEndIdx = strXml.lastIndexOf("</filter>");
                if (intIniIdx != -1 && intEndIdx != -1) {
                    strXml = strXml.substring(intIniIdx,intEndIdx + 9);
                }
                dom = SWBUtils.XML.xmlToDom(strXml);
                elmRes = (Element) dom.getFirstChild();
                String strTm = null;
                String strTp = null;
                if (strTopics != null) {
                    for (int counter = 0; counter < strTopics.length; counter++) {
                        StringTokenizer st = new StringTokenizer(strTopics[counter], "|");
                        while (st.hasMoreTokens()) {
                            strTm = st.nextToken();
                            strTp = st.nextToken();
                        }
                        dom = setElement(dom, strTm, strTp, "update");
                    }
                }
                pfil = UserFilter.ClassMgr.createUserFilter(recRes.getUserRepository());
                pfil.setXml(SWBUtils.XML.domToXml(dom));
                recRes.setUserFilter(pfil);
                response.setRenderParameter("confirm", "added");
            } catch (Exception e) {
                log.error("Error while updating resource with id:" + id + "- SWBAFilterResource:processAction", e);
            }
        }
        response.setRenderParameter("id", id);
        response.setRenderParameter("suri", id);
        response.setRenderParameter("tm", tm);
        //System.out.println("Lo guardo");
    }

    /**
     * Gets the ini form.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @param user the user
     * @return the ini form
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void getIniForm(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, User user) throws IOException {
        PrintWriter out = response.getWriter();
        //String tp=paramRequest.getWebPage().getId();
        String tm = request.getParameter("tm");
        String id = request.getParameter("suri");
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = ont.getGenericObject(id);

        SWBModel map = null;

//        if (request.getParameter("suri")!=null && !request.getParameter("suri").equals("id"))
//            id=request.getParameter("suri");
        User recRes = null;
        if (gobj instanceof User) {
            recRes = (User) gobj;
            map = recRes.getUserRepository();
            tm = map.getId();
            String strConfirm = request.getParameter("confirm");
            if (strConfirm != null && strConfirm.equals("added")) {
                out.println("<script type=\"text/javascript\">");
                out.println("   showStatus('Filter updated');"); //out.println("wbStatus('Filter updated');");
                out.println("</script>");
            }
            try {
                //recRes = map.getResource(id);
                UserFilter pfil = recRes.getUserFilter();
                String strXml = null;
                if (null != pfil) {
                    strXml = pfil.getXml();
                }

                if (null == strXml || (strXml != null && strXml.trim().length() == 0)) {
                    if (null == pfil) {
                        pfil = UserFilter.ClassMgr.createUserFilter(recRes.getUserRepository());
                        recRes.setUserFilter(pfil);
                    }
                    pfil.setXml("<resource><filter/></resource>");
                }
                out.println("<div class=\"applet\">");
                out.println("<applet id=\"editfilter\" name=\"editfilter\" code=\"applets.filterSection.FilterSection.class\" codebase=\"" + SWBPlatform.getContextPath() + "/\" ARCHIVE=\"swbadmin/lib/SWBAplFilterSection.jar, swbadmin/lib/SWBAplCommons.jar\" width=\"100%\" height=\"500\">");
                SWBResourceURL url = paramRequest.getRenderUrl();
                url.setMode("gateway");
                url.setCallMethod(url.Call_DIRECT);
                out.println("<param name=\"jsess\" value=\"" + request.getSession().getId() + "\">");
                //out.println("<param name =\"idfilter\" value=\"" + id + "\">");
                out.println("<param name =\"cgipath\" value=\"" + url + "\">");
                out.println("<param name =\"locale\" value=\"" + user.getLanguage() + "\">");
                out.println("<param name =\"tm\" value=\"" + map.getId() + "\">");
                out.println("<param name =\"idresource\" value=\"" + id + "\">");
//                boolean global = false;
//                if (map.getId().equalsIgnoreCase(SWBContext.getGlobalWebSite().getId())) {
//                    global = true;
//                }
//                out.println("<param name =\"isGlobalTM\" value=\"" + global + "\">");
                
                String maps="";
                Iterator<WebSite> it=SWBContext.listWebSites();
                while (it.hasNext())
                {
                    WebSite webSite = it.next();
                    if(webSite.getUserRepository().equals(map))
                    {
                        maps+="|"+webSite.getId();
                    }
                }
                
                out.println("<param name =\"maps\" value=\""+maps+"\">");                
                out.println("<param name =\"isGlobalTM\" value=\"true\">");                
                
                out.println("</applet>");
                out.println("</div>");
            } catch (Exception e) {
                log.error("Error while getting resource with id:" + id + "- SWBAFilterResource:getIniForm()", e);
            }
        }
    }

    /**
     * Gets the java script.
     * 
     * @param paramRequest the param request
     * @return the java script
     */
    private String getJavaScript(SWBParamRequest paramRequest) {
        StringBuffer sbRet = new StringBuffer();
        SWBResourceURL urlResAct = paramRequest.getActionUrl();
        sbRet.append("<script type=\"text/javascript\" language=\"JavaScript\">");
        sbRet.append("  function send (_f) {\n");
        sbRet.append("      alert (_f);\n");
        sbRet.append("      var tp=document.frmIni.tpp.value;\n");
        sbRet.append("      if (tp!=undefined && tp!='') {\n");
        sbRet.append("          if (isRepeated())\n");
        sbRet.append("              document.frmIni.tpp.value='';\n");
        sbRet.append("          else\n");
        sbRet.append("              document.frmIni.submit();\n");
        sbRet.append("      }\n");
        sbRet.append("      if (_f=='save' && document.frmIni.tps!=undefined) {\n");
        sbRet.append("          document.frmIni.act.value=_f;\n");
        sbRet.append("          document.frmIni.action='" + urlResAct + "';\n");
        sbRet.append("          selAll('true');\n");
        sbRet.append("          document.frmIni.submit();\n");
        sbRet.append("      }\n");
        sbRet.append("  }\n");

        sbRet.append("  function selAll(_v) {");
        sbRet.append("	    for(var i=0;i<document.frmIni.tps.length;i++)");
        sbRet.append("		    document.frmIni.tps[i].selected=_v;");
        sbRet.append("  }");

        sbRet.append("  function isRepeated() {");
        sbRet.append("	    if (document.frmIni.tps!=undefined)");
        sbRet.append("	    for(var i=0;i<document.frmIni.tps.length;i++)");
        sbRet.append("		    if ('Topic|'+document.frmIni.tps[i].value==document.frmIni.tpp.value)");
        sbRet.append("		        return true;");
        sbRet.append("		return false;");
        sbRet.append("  }");

        sbRet.append("  function Borra() {");
        sbRet.append("      for (var i=0 ; i<document.frmIni.tps.length;i++) {");
        sbRet.append("          if(document.frmIni.tps.options[i].selected==true) {");
        sbRet.append("              document.frmIni.tps.options[i]=null;");
        sbRet.append("              document.frmIni.act.value='save';");
        sbRet.append("              document.frmIni.action='" + urlResAct + "';");
        sbRet.append("              selAll('true');");
        sbRet.append("              document.frmIni.submit();");
        sbRet.append("          }");
        sbRet.append("      }");
        sbRet.append("  }");
        sbRet.append("");
        sbRet.append("</script>");
        return sbRet.toString();
    }

    /**
     * Sets the element.
     * 
     * @param dom the dom
     * @param tm the tm
     * @param tp the tp
     * @param action the action
     * @return the document
     */
    private Document setElement(Document dom, String tm, String tp, String action) {
        Element res = null;
        Element eTm = null;
        Element eTp = null;
        if (action != null && action.equals("add")) {
            NodeList nodelist = dom.getFirstChild().getChildNodes();
            for (int i = 0; i < nodelist.getLength(); i++) {
                if (nodelist.item(i).getNodeName().equals("filter")) {
                    res = (Element) nodelist.item(i);
                    eTm = dom.createElement("topicmap");
                    eTm.setAttribute("id", tm);
                    res.appendChild(eTm);
                    eTp = dom.createElement("topic");
                    eTp.setAttribute("id", tp);
                    eTp.setAttribute("childs", "true");
                    eTm.appendChild(eTp);
                }
            }
        } else if (action != null && action.equals("update")) {
            res = (Element) dom.getFirstChild();
            Element filter = dom.createElement("filter");
            res.appendChild(filter);
            eTm = dom.createElement("topicmap");
            eTm.setAttribute("id", tm);
            filter.appendChild(eTm);
            eTp = dom.createElement("topic");
            eTp.setAttribute("id", tp);
            eTp.setAttribute("childs", "true");
            eTm.appendChild(eTp);
        }
        return dom;
    }

    /**
     * Gets the filters.
     * 
     * @param strXml the str xml
     * @return the filters
     */
    private Vector getFilters(String strXml) {
        Vector vFilters = new Vector();
        if (strXml != null && !strXml.equals("")) {
            Document dom = SWBUtils.XML.xmlToDom(strXml);
            NodeList nodelist = dom.getFirstChild().getChildNodes();
            for (int i = 0; i < nodelist.getLength(); i++) {
                if (nodelist.item(i).getNodeName().equals("filter")) {
                    NodeList nlFilter = nodelist.item(i).getChildNodes();
                    for (int j = 0; j < nlFilter.getLength(); j++) {
                        vFilters.add(nlFilter.item(j).getAttributes().getNamedItem("id").getNodeValue() + "|" + nlFilter.item(j).getFirstChild().getAttributes().getNamedItem("id").getNodeValue());
                    }
                }
            }
        }
        return vFilters;
    }

    /**
     * Gets the combo filters.
     * 
     * @param strXml the str xml
     * @return the combo filters
     */
    private String getComboFilters(String strXml) {
        StringBuffer sbRet = new StringBuffer();
        Vector vFilters = getFilters(strXml);
        if (vFilters.size() > 0) {
            sbRet.append("<select name=\"tps\" size=\"4\" multiple>");
            for (int i = 0; i < vFilters.size(); i++) {
                StringTokenizer st = new StringTokenizer((String) vFilters.get(i), "|");
                String strTm = null;
                String strTp = null;
                while (st.hasMoreTokens()) {
                    strTm = st.nextToken();
                    strTp = st.nextToken();
                }
                sbRet.append(" <option value=\"" + vFilters.get(i) + "\">" + SWBContext.getWebSite(strTm).getWebPage(strTp).getDisplayName() + "</option> ");
            }
            sbRet.append("</select>");
        }
        return sbRet.toString();
    }

    /**
     * Gets the valores.
     * 
     * @param strXml the str xml
     * @return the valores
     */
    private String getValores(String strXml) {
        String strValores = "";
        Vector vFilters = getFilters(strXml);
        if (vFilters.size() > 0) {
            for (int i = 0; i < vFilters.size(); i++) {
                strValores += "topic|" + vFilters.get(i) + "*";
            }
        }
        return strValores;
    }
}
