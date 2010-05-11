/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.ConditionalFlow;
import org.semanticwb.process.model.ConnectionObject;
import org.semanticwb.process.model.GraphicalElement;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.SubProcess;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author juan.fernandez
 */
public class Modeler extends GenericResource {

    private Logger log = SWBUtils.getLogger(Modeler.class);
    private static final String PROP_CLASS = "class";
    private static final String PROP_TITLE = "title";
    private static final String PROP_URI = "uri";
    private static final String PROP_X = "x";
    private static final String PROP_Y = "y";
    private static final String PROP_W = "w";
    private static final String PROP_H = "h";
    private static final String PROP_START = "start";
    private static final String PROP_END = "end";
    private static final String PROP_PARENT = "parent";
    private static final String PROP_CONTAINER = "container";
    private static final String PROCESS_PREFIX = "http://www.semanticwebbuilder.org/swb4/process";
    private HashMap<String, JSONObject> hmjson = null;
    private HashMap<String, String> hmnew = null;
    private HashMap<String, String> hmori = null;
    private SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();

    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */
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
            System.out.println("command:" + cmd);
        }
        if (cmd == null) {
            response.sendError(404, request.getRequestURI());
            return;
        }

        if (cmd.equals("getProcessJSON")) {
            try {
                ont = SWBPlatform.getSemanticMgr().getOntology();
                GenericObject go = ont.getGenericObject(request.getParameter("suri"));
                org.semanticwb.process.model.Process process = null;
                if (go != null && go instanceof org.semanticwb.process.model.Process) {
                    process = (org.semanticwb.process.model.Process) go;
                    String json = getProcessJSON(process).toString();
                    System.out.println("json:" + json);
                    out.print(json);
                } else {
                    log.error("Error to create JSON: Process not found");
                    out.print("ERROR: Process not found");
                }
            } catch (Exception e) {
                log.error("Error to create JSON...", e);
                out.print("ERROR:" + e.getMessage());
            }
        } else {
            String ret;
            Document res = getService(cmd, dom, paramRequest.getUser(), request, response, paramRequest);
            if (res == null) {
                ret = SWBUtils.XML.domToXml(getError(3));
            } else {
                ret = SWBUtils.XML.domToXml(res, true);
            }
            out.print(new String(ret.getBytes()));
        }
    }

    private Document getService(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {

        GenericObject go = ont.getGenericObject(request.getParameter("suri"));
        SemanticClass sc = go.getSemanticObject().getSemanticClass();
        if (hmjson == null) {
            hmjson = new HashMap();
        }

        String tmpcmd = cmd, tm = null, id = null;
        if (null != cmd && cmd.indexOf('.') != -1) {
            tmpcmd = cmd.substring(0, cmd.indexOf('.'));
            tm = cmd.substring(cmd.indexOf('.') + 1, cmd.lastIndexOf('.'));
            id = cmd.substring(cmd.lastIndexOf('.') + 1);
        }

        org.semanticwb.process.model.Process process = null;

        if (sc.equals(org.semanticwb.process.model.Process.swp_Process)) {
            process = (org.semanticwb.process.model.Process) go;
        } 
        else
        {
            return null;
        }

        log.debug("getService: " + request.getParameter("suri"));
        if (tmpcmd.equals("updateModel")) {

            // Cargando los uris de los elementos existentes en el proceso
            // eliminando las conexiones entre ellos para generarlas nuevamente
            loadProcessElements(process);

            Node node = src.getElementsByTagName("json").item(0);
            System.out.println("json:" + node.getTextContent());

            try {
                createProcessElements(process, request, response, paramRequest);

            } catch (Exception e) {
                log.error("Error al leer JSON...", e);
                return getError(3);
            }
            String retComm = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req>OK</req>";
            Document dom = SWBUtils.XML.xmlToDom(retComm);
            return dom;

        }
        return getError(2);
    }

    /** Utilizado para identificar que elementos del proceso que se han eliminado
     *  Aquí también se eliminan todos los elementos de coneccion existentes
     *  Este sólo se debe utilizar cuando se actualiza el modelo delproceso.
     *
     * @param process, Modelo a cargar los elementos del proceso
     * @return Vector, con los uris de los elementos existentes.
     */
    public void loadProcessElements(org.semanticwb.process.model.Process process) {
        if (hmnew == null) {
            hmnew = new HashMap();
        }
        try {
            Iterator<GraphicalElement> it_fo = process.listContaineds();
            while (it_fo.hasNext()) {
                GraphicalElement obj = it_fo.next();
                hmnew.put(obj.getURI(), obj.getURI());
                hmori.put(obj.getURI(), obj.getURI());

                // Se eliminan las conexiones entre GraphicalElements

                Iterator<ConnectionObject> it = obj.listOutputConnectionObjects();
                while (it.hasNext()) {
                    ConnectionObject connectionObject = it.next();
                    connectionObject.remove();
                }
            }

        } catch (Exception e) {
            log.error("Error al general el JSON del Modelo.....getModelJSON(" + process.getTitle() + ", uri:" + process.getURI() + ")", e);
        }
    }

    /** Utilizado para generar un JSON del modelo, para la comunicacion con el applet
     *
     * @param process, Modelo a convertir en formato JSON
     * @return JSONObject, informacion del proceso en formato JSON
     */
    public JSONObject getProcessJSON(org.semanticwb.process.model.Process process) {

        JSONObject json_ret = null;
        JSONArray nodes = null;
        JSONObject ele = null;
        JSONObject coele = null;

        System.out.println("getProcessJSON()");

        try {
            json_ret = new JSONObject();
            json_ret.put(PROP_URI, process.getURI());
            json_ret.put(PROP_TITLE, process.getTitle());
            json_ret.put(PROP_CLASS, process.getClass().getName());
            nodes = new JSONArray();
            json_ret.putOpt("nodes", nodes);

            Iterator<GraphicalElement> it_fo = process.listContaineds();
            while (it_fo.hasNext()) {
                GraphicalElement obj = it_fo.next();
                ele = new JSONObject();
                nodes.put(ele);
                ele.put(PROP_CLASS, obj.getClass().getName());
                ele.put(PROP_TITLE, obj.getTitle());
                ele.put(PROP_URI, obj.getURI());
                ele.put(PROP_X, obj.getX());
                ele.put(PROP_Y, obj.getY());
                ele.put(PROP_W, obj.getWidth());
                ele.put(PROP_H, obj.getHeight());
                ele.put(PROP_CONTAINER, obj.getContainer().getURI());
                ele.put(PROP_PARENT, obj.getParent().getURI());
                Iterator<ConnectionObject> it = obj.listOutputConnectionObjects();
                while (it.hasNext()) {
                    ConnectionObject connectionObject = it.next();
                    coele = new JSONObject();
                    nodes.put(coele);
                    coele.put(PROP_CLASS, connectionObject.getClass().getName());
                    //coele.put(PROP_TITLE, connectionObject.getTitle());
                    coele.put(PROP_URI, connectionObject.getURI());
                    coele.put(PROP_START, connectionObject.getSource().getURI());
                    coele.put(PROP_END, connectionObject.getTarget().getURI());
                    if (connectionObject instanceof ConditionalFlow) {
                        coele.put(PROP_TITLE, ((ConditionalFlow) connectionObject).getFlowCondition());
                    } else {
                        coele.put(PROP_TITLE, "");
                    }
                }
            }

        } catch (Exception e) {
            log.error("Error al general el JSON del Modelo.....getModelJSON(" + process.getTitle() + ", uri:" + process.getURI() + ")", e);
        }

        return json_ret;
    }

    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("gateway")) {
            doGateway(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("applet")) {
            doApplet(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doApplet(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        String suri = request.getParameter("suri");
        PrintWriter out = response.getWriter();
        SWBResourceURL urlapp = paramsRequest.getRenderUrl();
        urlapp.setMode("gateway");
        urlapp.setCallMethod(urlapp.Call_DIRECT);
        urlapp.setParameter("suri", suri);

        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
        out.println("<script src=\"http://dl.javafx.com/1.3/dtfx.js\"></script>");
        out.println("</head>");
        out.println(" <body style=\"margin-top:0; margin-left:0;\">");
        out.println("    <script>");
        out.println("    javafx(");
        out.println("        {");
        out.println("              archive: \"" + SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAppBPMNModeler.jar," + SWBPlatform.getContextPath() + "/swbadmin/lib/json.jar," + SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAplCommons.jar\",");
        out.println("              draggable: true,");
        out.println("              width: 900,");
        out.println("              height: 700,");
        out.println("              code: \"org.semanticwb.process.modeler.Main\",");
        out.println("              name: \"SWBAppBPMNModeler\"");
        out.println("        },");
        out.println("        {");
        out.println("              jsess: \"" + request.getSession().getId() + "\",");
        out.println("              cgipath: \"" + urlapp + "\"");
        out.println("        }");
        out.println("    );");
        out.println("    </script>");
        out.println(" </body>");
        out.println("</html>");
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");

        SWBResourceURL urlapp = paramsRequest.getRenderUrl();
        urlapp.setMode("applet");
        urlapp.setCallMethod(urlapp.Call_DIRECT);
        urlapp.setParameter("suri", suri);

        out.println("<div class=\"applet\">");
        String idframe = "ifr_" + getResourceBase().getId();
        out.println("<iframe alt=\"Modeler\" scrolling=\"no\" frameborder=\"0\" name==\"" + idframe + "\" id=\"" + idframe + "\" src=\"" + urlapp + "\" width=\"100%\" onload=\"this.style.height = " + idframe + ".document.body.scrollHeight + 5\" ></iframe>"); //height=\"100%\"
        out.println("</div>");
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        super.processAction(request, response);
    }

    private Element addElement(String name, String value, Element parent) {
        Document doc = parent.getOwnerDocument();
        Element ele = doc.createElement(name);
        if (value != null) {
            ele.appendChild(doc.createTextNode(value));
        }
        parent.appendChild(ele);
        return ele;
    }

    private Document getError(int id) {
        Document dom = null;
        try {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            Element err = dom.createElement("err");
            res.appendChild(err);
            addElement("id", "" + id, err);
            if (id == 0) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_loginfail") + "...", err);
            } else if (id == 1) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_nouser") + "...", err);
            } else if (id == 2) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noservice") + "...", err);
            } else if (id == 3) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_serviceprocessfail") + "...", err);
            } else if (id == 4) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_parametersprocessfail") + "...", err);
            } else if (id == 5) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopicmap") + "...", err);
            } else if (id == 6) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopic") + "...", err);
            } else if (id == 7) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_usernopermiss") + "...", err);
            } else if (id == 8) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicAlreadyexist") + "...", err);
            } else if (id == 9) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_byImplement") + "...", err);
            } else if (id == 10) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicMapAlreadyExist") + "...", err);
            } else if (id == 11) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_FileNotFound") + "...", err);
            } else if (id == 12) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noversions") + "...", err);
            } else if (id == 13) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_xmlinconsistencyversion") + "...", err);
            } else if (id == 14) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noResourcesinMemory") + "...", err);
            } else if (id == 15) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noTemplatesinMemory") + "...", err);
            } else if (id == 16) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_TemplatenotRemovedfromFileSystem") + "...", err);
            } else if (id == 17) {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_adminUsernotCreated") + "...", err);
            } else {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_errornotfound") + "...", err);
            }
        } catch (Exception e) {
            log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "error_Gateway_getService_documentError") + "...", e);
        }
        return dom;
    }

    public void createProcessElements(org.semanticwb.process.model.Process process, HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) {
        ProcessSite procsite = process.getProcessSite();
        System.out.println("CreateProcessElements........................." + hmjson.size());
        String uri = null, sclass = null, title = null, container = null, parent = null, start = null, end = null;
        int x = 0, y = 0, w = 0, h = 0;
        SemanticClass semclass = null;
        SemanticModel model = procsite.getSemanticObject().getModel();
        GraphicalElement ge = null;
        ConnectionObject co = null;

        try {
            // Parte para crear y/o actualizar elementos
            Iterator<String> it = hmjson.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                JSONObject json = (JSONObject) hmjson.get(key);
                uri = json.getString(PROP_URI);
                sclass = json.getString(PROP_CLASS);

                semclass = ont.getSemanticObject(PROCESS_PREFIX + "#" + sclass).getSemanticClass();

                if (semclass.isSubClass(GraphicalElement.swp_GraphicalElement)) {
                    title = json.getString(PROP_TITLE);
                    x = json.getInt(PROP_X);
                    y = json.getInt(PROP_Y);
                    w = json.getInt(PROP_W);
                    h = json.getInt(PROP_H);

                    if (hmnew.get(uri) != null) {
                        ge = procsite.getGraphicalElement(uri);
                        ge.setTitle(title);
                        ge.setX(x);
                        ge.setY(y);
                        ge.setWidth(w);
                        ge.setHeight(h);
                    } else {
                        long id = model.getCounter(semclass);
                        GenericObject gi = model.createGenericObject(model.getObjectUri(String.valueOf(id), semclass), semclass);
                        ge = (GraphicalElement) gi;
                        ge.setTitle(sclass);
                        ge.setX(x);
                        ge.setY(y);
                        ge.setHeight(h);
                        ge.setWidth(w);
                        hmnew.put(uri, ge.getURI());
                    }
                }
            }

            // Parte para relacionar elementos container y parents
            it = hmjson.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                JSONObject json = (JSONObject) hmjson.get(key);
                uri = json.getString(PROP_URI);
                sclass = json.getString(PROP_CLASS);

                semclass = ont.getSemanticObject(PROCESS_PREFIX + "#" + sclass).getSemanticClass();
                if (semclass.isSubClass(GraphicalElement.swp_GraphicalElement)) {
                    parent = json.getString(PROP_PARENT);
                    container = json.getString(PROP_CONTAINER);
                    ge = procsite.getGraphicalElement(hmnew.get(uri));
                    if (ge != null) {
                        if (container != null && container.trim().length() == 0) {
                            ge.setContainer(process);
                        } else {
                            GenericObject subproc = procsite.getSemanticObject().getModel().getGenericObject(hmnew.get(container));
                            if (subproc != null && subproc instanceof SubProcess) {
                                ge.setContainer((SubProcess) subproc);
                            }
                        }

                        if (parent != null && parent.trim().length() > 0) {
                            GenericObject go_ge = procsite.getSemanticObject().getModel().getGenericObject(hmnew.get(parent));
                            if (go_ge != null && go_ge instanceof GraphicalElement) {
                                ge.setParent((GraphicalElement) go_ge);
                            }
                        }

                    }
                }
            }

            // Parte para generar el flujo del proceso
            it = hmjson.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                JSONObject json = (JSONObject) hmjson.get(key);
                uri = json.getString(PROP_URI);
                sclass = json.getString(PROP_CLASS);
                semclass = ont.getSemanticObject(PROCESS_PREFIX + "#" + sclass).getSemanticClass();
                if (semclass.isSubClass(ConnectionObject.swp_ConnectionObject)) {
                    start = json.getString(PROP_START);
                    end = json.getString(PROP_END);

                    if (hmnew.get(start) != null && hmnew.get(end) != null) {
                        long id = model.getCounter(semclass);
                        GenericObject go = model.createGenericObject(model.getObjectUri(String.valueOf(id), semclass), semclass);
                        co = (ConnectionObject) go;
                        co.setSource(procsite.getGraphicalElement(hmnew.get(start)));
                        co.setTarget(procsite.getGraphicalElement(hmnew.get(end)));
                        if (go instanceof ConditionalFlow) {
                            ((ConditionalFlow) co).setFlowCondition(title);
                        }
                    }
                }
            }

            // Eliminando elementos que fueron borrados en el applet Modeler

            // conservando los elementos comunes de los dos HM
            it = hmjson.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                hmori.remove(key);
            }

            // eliminando elementos que se tenian en el original pero ya no se necesitan
            it = hmori.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                procsite.getGraphicalElement(hmori.get(key)).remove();
            }

        } catch (Exception e) {
            log.error("Error al actualizar el modelo del proceso. Modeler.crreateProcessElements()",e);
        }
    }
}
