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
    private static final String PROP_W = "w";
    private static final String PROP_H = "h";
    private static final String PROP_LANE = "lane";
    private static final String PROP_START = "start";
    private static final String PROP_END = "end";
    private static final String PROP_ACTION = "action";
    private static final String PROP_TYPE = "type";
    private static final String PROP_PARENT = "parent";
    private static final String PROP_CONTAINER = "container";
    private static final String TYPE_NORMAL = "";
    private static final String TYPE_RULE = "rule";
    private static final String TYPE_MESSAGE = "message";
    private static final String TYPE_TIMER = "timer";
    private static final String TYPE_MULTIPLE = "multiple";
    private HashMap<String,JSONObject> hmjson = null;
    private HashMap<String,String> hmnew = null;

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
            String str_type = null;


            Node node=src.getElementsByTagName("json").item(0);
            System.out.println("json:"+node.getTextContent());

            JSONArray jsarr = null;
            JSONObject jsobj = null;
            try {
                jsobj = new JSONObject(node.getTextContent());
                jsarr = jsobj.getJSONArray("nodes");

//                System.out.println("======================================");
//                System.out.println("JSONObjets found:"+jsarr.length());
//                System.out.println("JSON:"+jsobj.toString());
//                System.out.println("======================================");
                
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
                    if(str_uri.startsWith("new:")&& !cls_ends.equals(".SequenceFlow")&&!cls_ends.endsWith(".ConditionalFlow"))
                    {
                        if(cls_ends.endsWith(".StartEvent"))
                        {
                            str_type = jsobj.getString(PROP_TYPE);
                            //TODO: Validar tipo para generar el elmento correspondiente
                            if(str_type.equals(TYPE_MESSAGE))
                            {
                                fgo=pross.createInitEvent();
                            }
                            else if(str_type.equals(TYPE_MULTIPLE))
                            {
                                fgo=pross.createInitEvent();
                            }
                            else if(str_type.equals(TYPE_RULE))
                            {
                                fgo=pross.createInitEvent();
                            }
                            else if(str_type.equals(TYPE_TIMER))
                            {
                                fgo=pross.createInitEvent();
                            }
                            else //TYPE_NORMAL
                            {
                                fgo=pross.createInitEvent();
                            }
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

                    if(fgo!=null&&!cls_ends.endsWith(".SequenceFlow")&&!cls_ends.endsWith(".ConditionalFlow")) //
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
                    str_title = jsobj.getString(PROP_TITLE);
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
                            //TODO:
                            // No tiene título el sequenceflow
                            //sf.setTitle(str_title);
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
                            //TODO:
                            // No tiene título el sequenceflow
                            //sf.setTitle(str_title);
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
                //TODO:
                // validar clase para regresar el tipo correspondiente
                if(obj instanceof InitEvent) ele.put("type",""); //((InitEvent)obj).getTitle()
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
                    //ele.put("title", cobj.getTitle());
                    ele.put("uri", cobj.getURI());
                    ele.put("start",cobj.getFromFlowObject().getURI());
                    ele.put("end",cobj.getToFlowObject().getURI());
                    if(cobj instanceof ConditionalFlow) ele.put("action",((ConditionalFlow)cobj).getFlowCondition());
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
        out.println("<script src=\"http://dl.javafx.com/1.3/dtfx.js\"></script>");
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

        //test(request, response, paramsRequest);

        SWBResourceURL urlapp = paramsRequest.getRenderUrl();
        urlapp.setMode("applet");
        urlapp.setCallMethod(urlapp.Call_DIRECT);
        urlapp.setParameter("suri", suri);

        out.println("<div class=\"applet\">");
        //out.println("<IFRAME alt=\"Modeler\" scrolling=\"no\" frameborder=\"0\" src=\"/test.html\" height=\"1q00%\" width=\"100%\" ></IFRAME>");
        out.println("<IFRAME alt=\"Modeler\" scrolling=\"no\" frameborder=\"0\" src=\""+urlapp+"\" width=\"100%\" height=\"100%\"></IFRAME>");
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


    public void test(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String str_class = null;
        String str_title = null;
        String str_uri = null;
        String str_type = null;

        hmjson = new HashMap();

        JSONArray jsarr = null;
        JSONObject jsobj = null;
        try {
            jsobj = new JSONObject(getTestProcessData());
            jsarr = jsobj.getJSONArray("nodes");



            //identificando los elementos asociados directamente al proceso
            System.out.println("Elementos iniciales...");
            for(int i=0; i<jsarr.length();i++)
            {
                jsobj = jsarr.getJSONObject(i);

                // Propiedades que siempre traen los elementos del modelo
                str_class = jsobj.getString(PROP_CLASS);
                str_title = jsobj.getString(PROP_TITLE);
                str_uri = jsobj.getString(PROP_URI);

                hmjson.put(str_uri,jsobj);


                if(!str_class.endsWith("SequenceFlow")&&!str_class.endsWith("MessageFlow"))
                {
                    if(jsobj.getString(PROP_CONTAINER).trim().length()==0&&jsobj.getString(PROP_PARENT).trim().length()==0)
                    {
                        System.out.println("class:"+str_class);
                        System.out.println("title:"+str_title);
                        System.out.println("uri:"+str_uri);
                    }
                }
                System.out.println("i:"+i);
                System.out.println("==========================================");
            }
//            System.out.println("Elementos que tienen parent (Pool y Lane)");
//            for(int j=0; j<jsarr.length();j++)
//            {
//                jsobj = jsarr.getJSONObject(j);
//                // Propiedades que siempre traen los elementos del modelo
//                str_class = jsobj.getString(PROP_CLASS);
//                str_title = jsobj.getString(PROP_TITLE);
//                str_uri = jsobj.getString(PROP_URI);
//
//                if(!str_class.endsWith("SequenceFlow")&&!str_class.endsWith("MessageFlow"))
//                {
//                    if(jsobj.getString(PROP_CONTAINER).trim().length()==0&&jsobj.getString(PROP_PARENT).trim().length()>0)
//                    {
//                        System.out.println("class:"+str_class);
//                        System.out.println("title:"+str_title);
//                        System.out.println("uri:"+str_uri);
//                    }
//                }
//                System.out.println("i:"+j);
//                System.out.println("==========================================");
//            }
//            System.out.println("Elementos que tienen container (SubProcesos)");
//            for(int k=0; k<jsarr.length();k++)
//            {
//                jsobj = jsarr.getJSONObject(k);
//                // Propiedades que siempre traen los elementos del modelo
//                str_class = jsobj.getString(PROP_CLASS);
//                str_title = jsobj.getString(PROP_TITLE);
//                str_uri = jsobj.getString(PROP_URI);
//
//                if(!str_class.endsWith("SequenceFlow")&&!str_class.endsWith("MessageFlow"))
//                {
//                    if(jsobj.getString(PROP_CONTAINER).trim().length()>0&&jsobj.getString(PROP_PARENT).trim().length()==0)
//                    {
//                        System.out.println("class:"+str_class);
//                        System.out.println("title:"+str_title);
//                        System.out.println("uri:"+str_uri);
//                    }
//                }
//                System.out.println("i:"+k);
//                System.out.println("==========================================");
//            }
//            System.out.println("Elementos SequenceFlow y MessageFlow");
//            for(int k=0; k<jsarr.length();k++)
//            {
//                jsobj = jsarr.getJSONObject(k);
//                // Propiedades que siempre traen los elementos del modelo
//                str_class = jsobj.getString(PROP_CLASS);
//                str_title = jsobj.getString(PROP_TITLE);
//                str_uri = jsobj.getString(PROP_URI);
//
//                if(str_class.endsWith("SequenceFlow")||str_class.endsWith("MessageFlow"))
//                {
//                        System.out.println("class:"+str_class);
//                        System.out.println("title:"+str_title);
//                        System.out.println("uri:"+str_uri);
//                        System.out.println("start:"+jsobj.getString(PROP_START));
//                        System.out.println("end:"+jsobj.getString(PROP_END));
//                }
//                System.out.println("i:"+k);
//                System.out.println("==========================================");
//            }

            createProcessElements();

        }
        catch(Exception e){}
    }

    public void createProcessElements()
    {
        System.out.println("CreateProcessElements........................."+hmjson.size());
        try
        {
            int num=0;
            Iterator<String> it = hmjson.keySet().iterator();
            while(it.hasNext())
            {
                String key = it.next();
                JSONObject json = (JSONObject)hmjson.get(key);
                String uri = json.getString(PROP_URI);
                String sclass = json.getString(PROP_CLASS);
                if(!sclass.endsWith("SequenceFlow")&&!sclass.endsWith("MessageFlow"))
                {
                    num++;
                    String container = json.getString(PROP_CONTAINER);
                    String parent = json.getString(PROP_PARENT);
                    System.out.println("("+num+") - element uri:"+uri+", container:"+container+", parent:"+parent+", class:"+sclass);
                    // elemento que  tiene directamente al proceso como padre
                    if(parent.trim().length()==0&&container.trim().length()==0)
                    {
                        System.out.println("createElement..."+uri);
                        createElement(json);
                    }
                    // elemento que tiene como padre otro elemento
                    else if(parent.trim().length()>0&&container.trim().length()==0)
                    {
                        System.out.println("findParent(PARENT)...");
                        findParent(uri);
                        createElement(json);
                    }
                    else if(parent.trim().length()==0&&container.trim().length()>0)
                    {
                        System.out.println("findParent(CONTAINER)...");
                        findParent(container);
                        createElement(json);
                    }
                    else if(container.trim().length()>0&&parent.trim().length()>0)
                    {
                        System.out.println("findParent(CONTAINER|PARENT)...");
                        findParent(uri);
                        createElement(json);
                    }
                    System.out.println("======================================================================");
                }


            }
            it = hmjson.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if(hmnew.get(key)==null)
                {
                    JSONObject json = (JSONObject)hmjson.get(key);
                    String uri = json.getString(PROP_URI);
                    String sclass = json.getString(PROP_CLASS);
                    if(sclass.endsWith("SequenceFlow")||sclass.endsWith("MessageFlow"))
                    {
                        num++;
                        String title = json.getString("title");
                        String start = json.getString("start");
                        String end = json.getString("end");
                        if(sclass.endsWith("SequenceFlow"))
                        {
                            //create SequenceFlow
                            // el title viene vacio
                        }
                        else if(sclass.endsWith("MessageFlow"))
                        {
                            //create MessageFlow
                            // el title es el mensaje
                        }

                        System.out.println("class..."+sclass);
                        System.out.println("create link ("+title+")...from("+hmnew.get(start)+") to ("+hmnew.get(end)+")");
                    }
                }
            }
            System.out.println(""+num+" elementos procesados en el JSON....");
            System.out.println("******************************************************************************");
        } catch (Exception e) {
        }
    }

    public void findParent(String uri)
    {
        System.out.println("findParent...."+uri);
        try
        {
            JSONObject json = hmjson.get(uri);
            if(json!=null)
            {
                String parent = json.getString(PROP_PARENT);
                String container = json.getString(PROP_CONTAINER);
                if(parent.trim().length()>0)
                {
                    if(hmnew.get(parent)==null)
                    {
                        findParent(parent);
                        createElement(json);
                    }
                    else
                    {
                        createElement(json);
                        System.out.println("ligando padre...");
                    }
                }
                else if(container.trim().length()>0)
                {
                    if(hmnew.get(container)==null)
                    {
                        findParent(container);
                        createElement(json);
                    }
                    else
                    {
                        createElement(json);
                        System.out.println("ligando container...");
                    }
                }
                else
                    createElement(json);
            }
        } catch (Exception e) {
        }
        
    }

    public void createElement(JSONObject jsobj)
    {
        if(hmnew==null) hmnew = new HashMap<String, String>();
        try
        {

            String uri = jsobj.getString(PROP_URI);
            String container = jsobj.getString(PROP_CONTAINER);
            String parent = jsobj.getString(PROP_PARENT);

            if(hmnew.get(uri)==null)
            {
                System.out.println("createElement...."+uri);
                //validar por cada tipo de lemento o clase

                hmnew.put(uri, uri+"_new");
            }
            
        } catch (Exception e) 
        {
            
        }

    }

    // proceso para pruebas
    public String getTestProcessData()
    {
        StringBuffer ret = new StringBuffer("");
        ret.append("{\"nodes\":[{\"w\":600,\"title\":\"tramites\",\"container\":\"\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.Pool\",\"type\":\"\",\"uri\":\"new:pool:0\",\"h\":400,\"y\":550,\"x\":600},");
        ret.append("{\"w\":600,\"title\":\"recepcion docs\",\"container\":\"\",\"parent\":\"new:pool:0\",\"class\":\"org.semanticwb.process.modeler.Lane\",\"type\":\"\",\"uri\":\"new:Lane:2\",\"h\":200,\"y\":450,\"x\":620},");
        ret.append("{\"w\":600,\"title\":\"revision\",\"container\":\"\",\"parent\":\"new:pool:0\",\"class\":\"org.semanticwb.process.modeler.Lane\",\"type\":\"\",\"uri\":\"new:Lane:4\",\"h\":200,\"y\":650,\"x\":620},");
        ret.append("{\"w\":100,\"title\":\"recibir\",\"container\":\"\",\"parent\":\"new:Lane:2\",\"class\":\"org.semanticwb.process.modeler.Task\",\"type\":\"\",\"uri\":\"new:task:5\",\"h\":60,\"y\":450,\"x\":475},");
        ret.append("{\"w\":30,\"title\":\"Start Event\",\"container\":\"\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.StartEvent\",\"type\":\"\",\"uri\":\"new:startevent:6\",\"h\":30,\"y\":100,\"x\":175},");
        ret.append("{\"w\":100,\"title\":\"revisar\",\"container\":\"\",\"parent\":\"new:Lane:4\",\"class\":\"org.semanticwb.process.modeler.Task\",\"type\":\"\",\"uri\":\"new:task:8\",\"h\":60,\"y\":625,\"x\":475},");
        ret.append("{\"w\":100,\"title\":\"entregar resultados\",\"container\":\"\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.Task\",\"type\":\"\",\"uri\":\"new:task:10\",\"h\":60,\"y\":100,\"x\":700},");
        ret.append("{\"w\":30,\"title\":\"End Event\",\"container\":\"\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.EndEvent\",\"type\":\"\",\"uri\":\"new:endevent:15\",\"h\":30,\"y\":100,\"x\":1075},");
        ret.append("{\"w\":100,\"title\":\"recepcion documentacion\",\"container\":\"\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.Task\",\"type\":\"\",\"uri\":\"new:task:21\",\"h\":60,\"y\":100,\"x\":400},");
        ret.append("{\"title\":\"\",\"start\":\"new:startevent:6\",\"class\":\"org.semanticwb.process.modeler.SequenceFlow\",\"uri\":\"new:flowlink:7\",\"end\":\"new:task:21\"},");
        ret.append("{\"title\":\"\",\"start\":\"new:task:5\",\"class\":\"org.semanticwb.process.modeler.SequenceFlow\",\"uri\":\"new:flowlink:12\",\"end\":\"new:task:8\"},");
        ret.append("{\"title\":\"\",\"start\":\"new:task:10\",\"class\":\"org.semanticwb.process.modeler.SequenceFlow\",\"uri\":\"new:flowlink:18\",\"end\":\"new:endevent:15\"},");
        ret.append("{\"title\":\"revisar\",\"start\":\"new:task:21\",\"class\":\"org.semanticwb.process.modeler.MessageFlow\",\"uri\":\"new:messageflow:22\",\"end\":\"new:task:5\"},");
        ret.append("{\"w\":100,\"title\":\"notificar\",\"container\":\"\",\"parent\":\"new:Lane:4\",\"class\":\"org.semanticwb.process.modeler.SubProcess\",\"type\":\"\",\"uri\":\"new:subprocess:24\",\"h\":60,\"y\":625,\"x\":700},");
        ret.append("{\"title\":\"\",\"start\":\"new:task:8\",\"class\":\"org.semanticwb.process.modeler.SequenceFlow\",\"uri\":\"new:flowlink:25\",\"end\":\"new:subprocess:24\"},");
        ret.append("{\"title\":\"\",\"start\":\"new:subprocess:24\",\"class\":\"org.semanticwb.process.modeler.SequenceFlow\",\"uri\":\"new:flowlink:26\",\"end\":\"new:interevent:35\"},");
        ret.append("{\"w\":30,\"title\":\"Start Event\",\"container\":\"new:subprocess:24\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.StartEvent\",\"type\":\"\",\"uri\":\"new:startevent:27\",\"h\":30,\"y\":250,\"x\":200},");
        ret.append("{\"w\":100,\"title\":\"enviar notificacion\",\"container\":\"new:subprocess:24\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.Task\",\"type\":\"\",\"uri\":\"new:task:28\",\"h\":60,\"y\":350,\"x\":400},");
        ret.append("{\"w\":30,\"title\":\"Throwing Message Inter Event\",\"container\":\"new:subprocess:24\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.InterEvent\",\"type\":\"b_message\",\"uri\":\"new:interevent:29\",\"h\":30,\"y\":350,\"x\":575},");
        ret.append("{\"title\":\"\",\"start\":\"new:task:28\",\"class\":\"org.semanticwb.process.modeler.SequenceFlow\",\"uri\":\"new:flowlink:31\",\"end\":\"new:interevent:29\"},");
        ret.append("{\"title\":\"\",\"start\":\"new:startevent:27\",\"class\":\"org.semanticwb.process.modeler.SequenceFlow\",\"uri\":\"new:flowlink:32\",\"end\":\"new:subprocess:33\"},");
        ret.append("{\"w\":100,\"title\":\"revisar solicitud\",\"container\":\"new:subprocess:24\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.SubProcess\",\"type\":\"\",\"uri\":\"new:subprocess:33\",\"h\":60,\"y\":250,\"x\":400},");
        ret.append("{\"title\":\"\",\"start\":\"new:subprocess:33\",\"class\":\"org.semanticwb.process.modeler.SequenceFlow\",\"uri\":\"new:flowlink:34\",\"end\":\"new:task:28\"},");
        ret.append("{\"w\":30,\"title\":\"Message Inter Event\",\"container\":\"\",\"parent\":\"new:Lane:2\",\"class\":\"org.semanticwb.process.modeler.InterEvent\",\"type\":\"w_message\",\"uri\":\"new:interevent:35\",\"h\":30,\"y\":450,\"x\":700},");
        ret.append("{\"title\":\"\",\"start\":\"new:interevent:35\",\"class\":\"org.semanticwb.process.modeler.SequenceFlow\",\"uri\":\"new:flowlink:37\",\"end\":\"new:task:10\"},");
        ret.append("{\"w\":30,\"title\":\"Start Event\",\"container\":\"new:subprocess:33\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.StartEvent\",\"type\":\"\",\"uri\":\"new:startevent:38\",\"h\":30,\"y\":325,\"x\":250},");
        ret.append("{\"w\":100,\"title\":\"revisar solicitud\",\"container\":\"new:subprocess:33\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.Task\",\"type\":\"\",\"uri\":\"new:task:39\",\"h\":60,\"y\":325,\"x\":400},");
        ret.append("{\"title\":\"\",\"start\":\"new:startevent:38\",\"class\":\"org.semanticwb.process.modeler.SequenceFlow\",\"uri\":\"new:flowlink:40\",\"end\":\"new:task:39\"},");
        ret.append("{\"w\":100,\"title\":\"obtener email\",\"container\":\"new:subprocess:33\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.Task\",\"type\":\"\",\"uri\":\"new:task:41\",\"h\":60,\"y\":325,\"x\":575},");
        ret.append("{\"title\":\"\",\"start\":\"new:task:39\",\"class\":\"org.semanticwb.process.modeler.SequenceFlow\",\"uri\":\"new:flowlink:42\",\"end\":\"new:task:41\"},");
        ret.append("{\"w\":100,\"title\":\"redectar mensaje\",\"container\":\"new:subprocess:33\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.Task\",\"type\":\"\",\"uri\":\"new:task:43\",\"h\":60,\"y\":325,\"x\":775},");
        ret.append("{\"w\":30,\"title\":\"End Event\",\"container\":\"new:subprocess:33\",\"parent\":\"\",\"class\":\"org.semanticwb.process.modeler.EndEvent\",\"type\":\"\",\"uri\":\"new:endevent:45\",\"h\":30,\"y\":325,\"x\":1000},");
        ret.append("{\"title\":\"\",\"start\":\"new:task:43\",\"class\":\"org.semanticwb.process.modeler.SequenceFlow\",\"uri\":\"new:flowlink:46\",\"end\":\"new:endevent:45\"},");
        ret.append("{\"title\":\"\",\"start\":\"new:task:41\",\"class\":\"org.semanticwb.process.modeler.SequenceFlow\",\"uri\":\"new:flowlink:47\",\"end\":\"new:task:43\"}],\"uri\":\"test\"}");

        return ret.toString();
    }

}
