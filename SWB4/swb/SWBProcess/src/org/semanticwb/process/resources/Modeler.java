/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.ConditionalFlow;
import org.semanticwb.process.ConnectionObject;
import org.semanticwb.process.FlowObject;
import org.semanticwb.process.InitEvent;
import org.semanticwb.process.SequenceFlow;
import org.semanticwb.process.UserTask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author juan.fernandez
 */
public class Modeler extends GenericResource
{
    private Logger log = SWBUtils.getLogger(Modeler.class);
    private static final String PROP_CLASS = "class";
    private static final String PROP_TITLE = "title";
    private static final String PROP_URI = "uri";
    private static final String PROP_X = "x";
    private static final String PROP_Y = "y";
    private static final String PROP_LANE = "lane";
    private static final String PROP_START = "start";
    private static final String PROP_END = "end";
    private static final String PROP_ACTION = "action";

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
        //log.debug("doGateway: URI"+request.getParameter("suri")+", id:"+request.getParameter("id"));
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

        if (cmd.equals("getProcessJSON"))
        {
            try
            {
                SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
                GenericObject go = ont.getGenericObject(request.getParameter("suri"));
                org.semanticwb.process.Process process = null;
                if(go!=null && go instanceof org.semanticwb.process.Process)
                {
                    process = (org.semanticwb.process.Process)go;
                    String json=getProcessJSON(process).toString();
                    System.out.println("json:"+json);
                    out.print(json);
                }else
                {
                    log.error("Error to create JSON: Process not found");
                    out.print("ERROR: Process not found");
                }
            }catch(Exception e)
            {
                log.error("Error to create JSON...",e);
                out.print("ERROR:"+e.getMessage());
            }
        }else
        {
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

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject go = ont.getGenericObject(request.getParameter("suri"));
        SemanticClass sc = go.getSemanticObject().getSemanticClass();

        HashMap<String, FlowObject> hm_new = new HashMap();

        String tmpcmd = cmd, tm = null, id = null;
        if (null != cmd && cmd.indexOf('.') != -1) {
            tmpcmd = cmd.substring(0, cmd.indexOf('.'));
            tm = cmd.substring(cmd.indexOf('.') + 1, cmd.lastIndexOf('.'));
            id = cmd.substring(cmd.lastIndexOf('.') + 1);
        }
        
        org.semanticwb.process.Process process = null;
        org.semanticwb.process.ProcessSite pross = null;

        if(sc.equals(org.semanticwb.process.Process.swbps_Process))
        {
            process = (org.semanticwb.process.Process)go;
            pross = process.getProcessSite();
        }
        else return null;

//        System.out.println("tmpcmd:"+tmpcmd);
        log.debug("getService: " + request.getParameter("suri"));
        if (tmpcmd.equals("updateModel"))
        {
            // Cargando los uris de los elementos existentes en el proceso
            // eliminando las conexiones entre ellos para generarlas nuevamente
            Vector<String> vpe = loadProcessElements(process);

            String str_class = null;
            String str_title = null;
            String str_uri = null;


            Node node=src.getElementsByTagName("json").item(0);
            System.out.println("json:"+node.getTextContent());

            JSONArray jsarr = null;
            JSONObject jsobj = null;
            try {
                jsobj = new JSONObject(node.getTextContent());
                jsarr = jsobj.getJSONArray("nodes");

                System.out.println("======================================");
                System.out.println("JSONObjets found:"+jsarr.length());

                
                // primero para crear POOL
                for(int i=0; i<jsarr.length();i++)
                {
                    jsobj = jsarr.getJSONObject(i);
//                    System.out.println("jsobj:"+jsobj.toString()+", i: "+i);

                    // Propiedades que siempre traen los elementos del modelo
                    str_class = jsobj.getString(PROP_CLASS);
                    str_title = jsobj.getString(PROP_TITLE);
                    str_uri = jsobj.getString(PROP_URI);

//                    System.out.println("class:"+str_class);
//                    System.out.println("title:"+str_title);
//                    System.out.println("uri:"+str_uri);

                    String cls_ends = str_class.substring(str_class.lastIndexOf("."));
//                    System.out.println("ends...."+cls_ends);

                    GenericObject lgo = null;
                    FlowObject fgo = null;
                    // Tipo de clase a crear o actualizar
                    if(str_uri.startsWith("new:") && cls_ends.equals(".Pool"))
                    {
//                        fgo=createPool(process, str_title);
//                        process.addFlowObject(fgo);
                    }
                    else
                    {
                        // para obtener el FlowObject existente y actualizar las propiedades
                        lgo = ont.getGenericObject(str_uri);
                    }

                    //se agregan todos los FlowObject encontrados
                    if (lgo instanceof FlowObject) {
                        fgo = (FlowObject) lgo;
                    }

                    if(fgo!=null&&cls_ends.endsWith(".Pool"))
                    {
                        hm_new.put(str_uri, fgo);

                        if(str_title!=null)fgo.setTitle(str_title);

                        // Para agregar las propiedades al fgo
                        int x=jsobj.getInt(PROP_X);
                        int y=jsobj.getInt(PROP_Y);
                        fgo.setX(x);
                        fgo.setY(y);
//                        System.out.println("x:"+x);
//                        System.out.println("y:"+y);

                        //TODO:lane property
                        String str_lane=jsobj.getString(PROP_LANE);
//                        System.out.println("lane:"+str_lane);
                        //eliminando el elemento encontrado del vector
                        vpe.remove(fgo.getURI());
                    }
                }

                // segundo para crear Lane
                for(int i=0; i<jsarr.length();i++)
                {
                    jsobj = jsarr.getJSONObject(i);
//                    System.out.println("jsobj:"+jsobj.toString()+", i: "+i);

                    // Propiedades que siempre traen los elementos del modelo
                    str_class = jsobj.getString(PROP_CLASS);
                    str_title = jsobj.getString(PROP_TITLE);
                    str_uri = jsobj.getString(PROP_URI);

//                    System.out.println("class:"+str_class);
//                    System.out.println("title:"+str_title);
//                    System.out.println("uri:"+str_uri);

                    String cls_ends = str_class.substring(str_class.lastIndexOf("."));
//                    System.out.println("ends...."+cls_ends);

                    GenericObject lgo = null;
                    FlowObject fgo = null;
                    // Tipo de clase a crear o actualizar
                    if(str_uri.startsWith("new:")&& cls_ends.equals(".Lane"))
                    {
//                        fgo=createLane(process, str_title);
//                        process.addFlowObject(fgo);
                    }
                    else
                    {
                        // para obtener el FlowObject existente y actualizar las propiedades
                        lgo = ont.getGenericObject(str_uri);
                    }

                    //se agregan todos los FlowObject encontrados
                    if (lgo instanceof FlowObject) {
                        fgo = (FlowObject) lgo;
                    }

                    if(fgo!=null&&cls_ends.endsWith(".Lane"))
                    {
                        hm_new.put(str_uri, fgo);

                        if(str_title!=null)fgo.setTitle(str_title);

                        // Para agregar las propiedades al fgo
                        int x=jsobj.getInt(PROP_X);
                        int y=jsobj.getInt(PROP_Y);
                        fgo.setX(x);
                        fgo.setY(y);
//                        System.out.println("x:"+x);
//                        System.out.println("y:"+y);

                        //TODO:lane property
                        String str_lane=jsobj.getString(PROP_LANE);
//                        System.out.println("lane:"+str_lane);
                        //eliminando el elemento encontrado del vector
                        vpe.remove(fgo.getURI());
                    }
                }


                // tercero crear los FlowObjects restantes
                for(int i=0; i<jsarr.length();i++)
                {
                    jsobj = jsarr.getJSONObject(i);
//                    System.out.println("jsobj:"+jsobj.toString()+", i: "+i);

                    // Propiedades que siempre traen los elementos del modelo
                    str_class = jsobj.getString(PROP_CLASS);
                    str_title = jsobj.getString(PROP_TITLE);
                    str_uri = jsobj.getString(PROP_URI);

//                    System.out.println("class:"+str_class);
//                    System.out.println("title:"+str_title);
//                    System.out.println("uri:"+str_uri);

                    String cls_ends = str_class.substring(str_class.lastIndexOf("."));
//                    System.out.println("ends...."+cls_ends);

                    GenericObject lgo = null;
                    FlowObject fgo = null;
                    // Tipo de clase a crear o actualizar
                    if(str_uri.startsWith("new:")&& !cls_ends.equals(".SequenceFlow"))
                    {
                        if(cls_ends.endsWith(".StartEvent"))
                        {
                            fgo=pross.createInitEvent();
                        }
                        else if(cls_ends.endsWith(".EndEvent"))
                        {
                            fgo=pross.createEndEvent();
                        }
                        else if(cls_ends.endsWith(".Task"))
                        {
                            fgo = createTask(process, str_title);
                        }
                        else if(cls_ends.endsWith(".InterEvent"))
                        {
                            fgo = pross.createInterEvent();
                        }
                        else if(cls_ends.endsWith(".GateWay"))
                        {
                            fgo=pross.createGateWay();
                        }
                        else if(cls_ends.endsWith(".ORGateWay"))
                        {
                            fgo=pross.createORGateWay();
                        }
                        else if(cls_ends.endsWith(".ANDGateWay"))
                        {
                            fgo=pross.createANDGateWay();
                        }
                        else if(cls_ends.endsWith(".SubProcess"))
                        {
                            fgo=createSubProcess(process, str_title);
                        }
//                        else if(cls_ends.endsWith(".Artifact"))
//                        {
//                            fgo=createSubProcess(process, str_title);
//                        }
                        if(fgo!=null) process.addFlowObject(fgo);
                    }
                    else
                    {
                        // para obtener el FlowObject existente y actualizar las propiedades
                        lgo = ont.getGenericObject(str_uri);
                    }

                    //se agregan todos los FlowObject encontrados
                    if (lgo instanceof FlowObject) {
                        fgo = (FlowObject) lgo;
                    }

                    if(fgo!=null&&!cls_ends.endsWith(".SequenceFlow"))
                    {
                        hm_new.put(str_uri, fgo);

                        if(str_title!=null)fgo.setTitle(str_title);

                        // Para agregar las propiedades al fgo
                        int x=jsobj.getInt(PROP_X);
                        int y=jsobj.getInt(PROP_Y);
                        fgo.setX(x);
                        fgo.setY(y);
//                        System.out.println("x:"+x);
//                        System.out.println("y:"+y);

                        //TODO:lane property
                        String str_lane=jsobj.getString(PROP_LANE);
//                        System.out.println("lane:"+str_lane);
                        //eliminando el elemento encontrado del vector
                        vpe.remove(fgo.getURI());
                    }
                }

                // Eliminando del Proceso los elementos que fueron eliminados del modelo
                // que son los que quedaron en el vector.
                Enumeration<String> enuele = vpe.elements();
                while (enuele.hasMoreElements()) {

                    String o_uri = enuele.nextElement();
                    System.out.println("Elemento a quitar URI:"+o_uri);
                    SemanticObject so = ont.getSemanticObject(o_uri);
                    so.remove();
                }

                // Generando los ConnectionObject
                for(int i=0; i<jsarr.length();i++)
                {
//                    System.out.println("Revisando FlowLinks");
                    jsobj = jsarr.getJSONObject(i);
//                    System.out.println("jsobj:"+jsobj.toString()+", i: "+i);

                    // Propiedades que siempre traen los elementos del modelo
                    str_class = jsobj.getString(PROP_CLASS);
                    //str_title = jsobj.getString(PROP_TITLE);
                    str_uri = jsobj.getString(PROP_URI);

                    if(str_class.endsWith(".SequenceFlow"))
                    {
                        String str_start = jsobj.getString(PROP_START);
                        String str_end = jsobj.getString(PROP_END);

                        FlowObject fos = hm_new.get(str_start);
                        FlowObject foe = hm_new.get(str_end);

                        if(fos!=null&&foe!=null)
                        {
                            SequenceFlow sf = linkObject(fos, foe);
                        }
//                        System.out.println("start:"+str_start);
//                        System.out.println("end:"+str_end);
                    }
                    else if(str_class.endsWith(".ConditionalFlow"))
                    {
                        String str_start = jsobj.getString(PROP_START);
                        String str_end = jsobj.getString(PROP_END);

                        String str_action = jsobj.getString(PROP_ACTION);

                        FlowObject fos = hm_new.get(str_start);
                        FlowObject foe = hm_new.get(str_end);

                        if(fos!=null&&foe!=null)
                        {
                            SequenceFlow sf = linkConditionObject(foe, foe, str_action);
                        }
//                        System.out.println("start:"+str_start);
//                        System.out.println("end:"+str_end);
                    }
                }
            } catch (Exception e) {
                log.error("Error al leer JSON...",e);
                return getError(3);
            }
            String retComm = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req>OK</req>";
            Document dom = SWBUtils.XML.xmlToDom(retComm);
            return dom;
            
        }
        return getError(2);
    }

    /** Utilizado para identificar que elementos del proceso han eliminado
     *  Aquí también se eliminan todos los elementos de coneccion existentes
     *
     * @param process, Modelo a cargar los elementos del proceso
     * @return Vector, con los uris de los elementos existentes.
     */
    public Vector loadProcessElements(org.semanticwb.process.Process process)
    {
        Vector retvec = new Vector();
        try {
            Iterator<FlowObject> it_fo = process.listFlowObjects();
            while (it_fo.hasNext()) {
                FlowObject obj = it_fo.next();
                retvec.add(obj.getURI());
            }
            Iterator<ConnectionObject> it_co = null;
            it_fo = process.listFlowObjects();
            while (it_fo.hasNext())
            {
                FlowObject obj = it_fo.next();
                it_co = obj.listToConnectionObjects();
                while (it_co.hasNext())
                {
                    ConnectionObject cobj = it_co.next();
                    cobj.remove();
                }
            }
        } catch (Exception e) {
            log.error("Error al general el JSON del Modelo.....getModelJSON(" + process.getTitle() + ", uri:" + process.getURI() + ")", e);
        }
        return retvec;
    }


    /** Utilizado para generar un JSON del modelo, para la comunicacion con el applet
     *
     * @param process, Modelo a convertir en formato JSON
     * @return JSONObject, informacion del proceso en formato JSON
     */
    public JSONObject getProcessJSON(org.semanticwb.process.Process process) {

        JSONObject json_ret = null;
        JSONArray nodes = null;
        JSONObject ele = null;

//        System.out.println("getProcessJSON()");

        try {
            json_ret = new JSONObject();
            json_ret.put("uri", process.getURI());
            json_ret.put("title",process.getTitle());
            nodes = new JSONArray();
            json_ret.putOpt("nodes", nodes);

            Iterator<FlowObject> it_fo = process.listFlowObjects();
            while (it_fo.hasNext()) {
                FlowObject obj = it_fo.next();
                ele = new JSONObject();
                nodes.put(ele);
                ele.put("class", obj.getClass().getName());
                ele.put("title", obj.getTitle());
                ele.put("uri", obj.getURI());
                ele.put("x", obj.getX());
                ele.put("y", obj.getY());
                //ele.put("lane", obj.getX());
            }

            Iterator<ConnectionObject> it_co = null;
            it_fo = process.listFlowObjects();
            while (it_fo.hasNext())
            {
                FlowObject obj = it_fo.next();
                it_co = obj.listToConnectionObjects();
                while (it_co.hasNext())
                {
                    ConnectionObject cobj = it_co.next();
                    ele = new JSONObject();
                    nodes.put(ele);
                    ele.put("class", cobj.getClass().getName());
                    //ele.put("title", obj.getTitle());
                    ele.put("uri", cobj.getURI());
                    ele.put("start",cobj.getFromFlowObject().getURI());
                    ele.put("end",cobj.getToFlowObject().getURI());
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
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }


    public void doApplet(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String suri = request.getParameter("suri");
        PrintWriter out=response.getWriter();
        SWBResourceURL urlapp = paramsRequest.getRenderUrl();
        urlapp.setMode("gateway");
        urlapp.setCallMethod(urlapp.Call_DIRECT);
        urlapp.setParameter("suri", suri);

        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
        out.println("<script src=\"http://dl.javafx.com/1.2/dtfx.js\"></script>");
        out.println("</head>");
        out.println(" <body style=\"margin-top:0; margin-left:0;\">");
        out.println("    <script>");
        out.println("    javafx(");
        out.println("        {");
        out.println("              archive: \""+ SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAppBPMNModeler.jar,"+ SWBPlatform.getContextPath() + "/swbadmin/lib/json.jar,"+ SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAplCommons.jar\",");
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
        //out.println("<IFRAME alt=\"Modeler\" scrolling=\"no\" frameborder=\"0\" src=\"/test.html\" height=\"100%\" width=\"100%\" ></IFRAME>");
        out.println("<IFRAME alt=\"Modeler\" scrolling=\"no\" frameborder=\"0\" src=\""+urlapp+"\" height=\"100%\" width=\"100%\" ></IFRAME>");
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


    public org.semanticwb.process.Process createSubProcess(org.semanticwb.process.Process process,String name)
    {
        org.semanticwb.process.Process sps=process.getProcessSite().createProcess();
        sps.setTitle(name);
        //process.addFlowObject(sps);
        return sps;
    }

    public UserTask createTask(org.semanticwb.process.Process process,String name)
    {
        UserTask task=process.getProcessSite().createUserTask(SWBPlatform.getIDGenerator().getID(name, null, true));
        task.setTitle(name);
        task.setActive(true);
        //process.addFlowObject(task);
        Resource res=task.getWebSite().createResource();
        res.setTitle(name);
        res.setActive(true);
        res.setResourceType(task.getWebSite().getResourceType("ProcessForm"));
        task.addResource(res);
        return task;
    }

    public SequenceFlow linkObject(FlowObject from, FlowObject to)
    {
        SequenceFlow seq=from.getProcessSite().createSequenceFlow();
        from.addToConnectionObject(seq);
        seq.setToFlowObject(to);
        return seq;
    }

    public SequenceFlow linkConditionObject(FlowObject from, FlowObject to, String condition)
    {
        ConditionalFlow seq=from.getProcessSite().createConditionalFlow();
        from.addToConnectionObject(seq);
        seq.setToFlowObject(to);
        seq.setFlowCondition(condition);
        return seq;
    }

}
