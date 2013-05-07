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
package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Sortable;
import org.semanticwb.process.model.Process;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.ActivityConfable;
import org.semanticwb.process.model.Collectionable;
import org.semanticwb.process.model.ConnectionObject;
import org.semanticwb.process.model.Containerable;
import org.semanticwb.process.model.GraphicalElement;
import org.semanticwb.process.model.IntermediateCatchEvent;
import org.semanticwb.process.model.LoopCharacteristics;
import org.semanticwb.process.model.MultiInstanceLoopCharacteristics;
import org.semanticwb.process.model.StandarLoopCharacteristics;

/**
 * Modelador de procesos basado en SVG y Javascript.
 * @author Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
 */
public class SVGModeler extends GenericResource {
    private static Logger log = SWBUtils.getLogger(SVGModeler.class);
    public static final String MODE_MODELER = "modeler";
    public static final String MODE_GATEWAY = "gateway";
    public static final String ACT_GETPROCESSJSON = "getProcessJSON";
    private static final String PROP_CLASS = "class";
    private static final String PROP_TITLE = "title";
    private static final String PROP_DESCRIPTION = "description";
    private static final String PROP_CONNPOINTS = "connectionPoints";
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
    private static final String PROP_isMultiInstance = "isMultiInstance";
    private static final String PROP_isSeqMultiInstance = "isSequentialMultiInstance";
    private static final String PROP_isCollection = "isCollection";
    private static final String PROP_isLoop = "isLoop";
    private static final String PROP_isForCompensation = "isForCompensation";
    private static final String PROP_isAdHoc = "isAdHoc";
    private static final String PROP_isTransaction = "isTransaction";
    private static final String PROP_isInterrupting = "isInterrupting";
    private static final String PROP_labelSize = "labelSize";
    private static final String PROP_index = "index";
    private static final String JSONSTART = "JSONSTART";
    private static final String JSONEND = "JSONEND";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (MODE_MODELER.equals(mode)) {
            doModeler(request, response, paramRequest);
        } else if (MODE_GATEWAY.equals(mode)) {
            doGateway(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
    
        SWBResourceURL urlapp = paramRequest.getRenderUrl();
        urlapp.setMode("modeler");
        urlapp.setCallMethod(SWBResourceURL.Call_DIRECT);
        urlapp.setParameter("suri", request.getParameter("suri"));
        out.println("<iframe dojoType_=\"dijit.layout.ContentPane\" src=\"" + urlapp + "\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"yes\"></iframe>");
    }
        
    public void doModeler(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String JSP = "/swbadmin/jsp/process/modeler/modelerView.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(JSP);

        try {
            request.setAttribute("paramRequest", paramRequest);
            rd.include(request, response);
        } catch (Exception ex) {
            log.error(ex);
        }
    }
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        OutputStream outs = response.getOutputStream();
        String action = paramRequest.getAction();
        
        if (ACT_GETPROCESSJSON.equals(action)) {
            try {
                SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
                GenericObject go = ont.getGenericObject(request.getParameter("suri"));
                Process process = null;
                if (go != null && go instanceof Process) {
                    process = (Process) go;
                    String json = getProcessJSON(process).toString();
                    outs.write(json.getBytes("UTF-8"));
                } else {
                    log.error("Error to create JSON: Process not found");
                    outs.write("ERROR: Process not found".getBytes());
                }
            } catch (Exception e) {
                log.error("Error to create JSON...", e);
                outs.write(("ERROR:" + e.getMessage()).getBytes());
            }
        }
        
//        OutputStream outs = response.getOutputStream();
//        ServletInputStream in = request.getInputStream();
//        Document dom = SWBUtils.XML.xmlToDom(in);
//        
//        if (!dom.getFirstChild().getNodeName().equals("req")) {
//            response.sendError(404, request.getRequestURI());
//            return;
//        }
//        
//        String cmd = null;
//        if (dom.getElementsByTagName("cmd").getLength() > 0) {
//            cmd = dom.getElementsByTagName("cmd").item(0).getFirstChild().getNodeValue();
//        }
//        if (cmd == null) {
//            response.sendError(404, request.getRequestURI());
//            return;
//        }
//        //System.out.println("cmd:"+cmd);
//
//        if (cmd.equals("getProcessJSON")) {
//            try {
//                ont = SWBPlatform.getSemanticMgr().getOntology();
//                GenericObject go = ont.getGenericObject(request.getParameter("suri"));
//                org.semanticwb.process.model.Process process = null;
//                if (go != null && go instanceof org.semanticwb.process.model.Process) {
//                    process = (org.semanticwb.process.model.Process) go;
//                    String json = getProcessJSON(process).toString();
//                    //out.print(json);
//                    outs.write(json.getBytes("UTF-8"));
//                    //System.out.println(json);
//                    //out.print(SWBUtils.TEXT.decode(json, "UTF8"));
//                    //System.out.println("json:"+json);
//                } else {
//                    log.error("Error to create JSON: Process not found");
//                    //out.print("ERROR: Process not found");
//                    outs.write("ERROR: Process not found".getBytes());
//                }
//            } catch (Exception e) {
//                log.error("Error to create JSON...", e);
//                //out.print("ERROR:" + e.getMessage());
//                outs.write(("ERROR:" + e.getMessage()).getBytes());
//            }
//        } else {
//            String ret;
//            Document res = getService(cmd, dom, paramRequest.getUser(), request, response, paramRequest);
//            if (res == null) {
//                ret = SWBUtils.XML.domToXml(getError(3));
//            } else {
//                ret = SWBUtils.XML.domToXml(res, true);
//            }
//            //out.print(new String(ret.getBytes()));
//            outs.write(ret.getBytes());
//            //System.out.println("out:"+new String(ret.getBytes()));
//        }
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

        try {
            json_ret = new JSONObject();
            json_ret.put(PROP_URI, process.getURI());
            json_ret.put(PROP_TITLE, process.getTitle());
            json_ret.put(PROP_DESCRIPTION, process.getDescription());
            json_ret.put(PROP_CLASS, process.getSemanticObject().getSemanticClass().getClassCodeName());
            nodes = new JSONArray();
            json_ret.putOpt("nodes", nodes);

            Iterator<GraphicalElement> it_fo = process.listContaineds();
            while (it_fo.hasNext()) {
                GraphicalElement obj = it_fo.next();
                ele = new JSONObject();
                nodes.put(ele);
                ele.put(PROP_CLASS, obj.getSemanticObject().getSemanticClass().getClassCodeName());
                ele.put(PROP_TITLE, obj.getTitle());

                //if(obj.getDescription()==null) obj.setDescription("");

                ele.put(PROP_DESCRIPTION, obj.getDescription());
                ele.put(PROP_URI, obj.getURI());
                ele.put(PROP_X, obj.getX());
                ele.put(PROP_Y, obj.getY());
                ele.put(PROP_W, obj.getWidth());
                ele.put(PROP_H, obj.getHeight());
                if (obj.getContainer() != null) {
                    ele.put(PROP_CONTAINER, obj.getContainer().getURI());
                } else {
                    ele.put(PROP_CONTAINER, "");
                }
                if (obj.getParent() != null) {
                    ele.put(PROP_PARENT, obj.getParent().getURI());
                } else {
                    ele.put(PROP_PARENT, "");
                }

                if (obj.getLabelSize() != 0) {
                    ele.put(PROP_labelSize, obj.getLabelSize());
                } else {
                    ele.put(PROP_labelSize, 10);
                }

                if (obj instanceof Sortable) {

                    //System.out.println("Es coleccion...");
                    Sortable sorble = (Sortable) obj;
                    ele.put(PROP_index, sorble.getIndex());
                }
                
                if (obj instanceof IntermediateCatchEvent) {
                    IntermediateCatchEvent ice = (IntermediateCatchEvent) obj;
                    ele.put(PROP_isInterrupting, ice.isInterruptor());
                }

                if (obj instanceof ActivityConfable) {
                    ActivityConfable tsk = (ActivityConfable) obj;
                    if (tsk.getLoopCharacteristics() != null) {
                        LoopCharacteristics loopC = tsk.getLoopCharacteristics();
                        if (loopC instanceof MultiInstanceLoopCharacteristics) {
                            ele.put(PROP_isMultiInstance, true);
                        } else {
                            ele.put(PROP_isMultiInstance, false);
                        }

                        if (loopC instanceof StandarLoopCharacteristics) {
                            ele.put(PROP_isLoop, true);
                        } else {
                            ele.put(PROP_isLoop, false);
                        }
                    }
                    ele.put(PROP_isForCompensation, Boolean.toString(tsk.isForCompensation()));
                }

                if (obj instanceof Collectionable) {

                    //System.out.println("Es coleccion...");
                    Collectionable colble = (Collectionable) obj;
                    if (colble.isCollection()) {
                        ele.put(PROP_isCollection, true);
                    } else {
                        ele.put(PROP_isCollection, false);
                    }
                    //System.out.println("===>"+colble.isCollection());
                }


                Iterator<ConnectionObject> it = obj.listOutputConnectionObjects();
                while (it.hasNext()) {
                    ConnectionObject connectionObject = it.next();
                    coele = new JSONObject();
                    nodes.put(coele);
                    coele.put(PROP_CLASS, connectionObject.getSemanticObject().getSemanticClass().getClassCodeName());
                    coele.put(PROP_URI, connectionObject.getURI());
                    coele.put(PROP_START, connectionObject.getSource().getURI());
                    coele.put(PROP_END, connectionObject.getTarget().getURI());
                    coele.put(PROP_TITLE, connectionObject.getTitle());
                    coele.put(PROP_CONNPOINTS, connectionObject.getConnectionPoints());
                    //coele.put(PROP_DESCRIPTION, connectionObject.getDescription());
                }
                if (obj instanceof Containerable) {
                    getSubProcessJSON((Containerable) obj, nodes);
                }
            }

        } catch (Exception e) {
            log.error("Error al general el JSON del Modelo.....getModelJSON(" + process.getTitle() + ", uri:" + process.getURI() + ")", e);
        }
        return json_ret;
    }
    
    public void getSubProcessJSON(org.semanticwb.process.model.Containerable subprocess, JSONArray nodes) {

        JSONObject ele = null;
        JSONObject coele = null;
        try {
            Iterator<GraphicalElement> it_fo = subprocess.listContaineds();
            while (it_fo.hasNext()) {
                GraphicalElement obj = it_fo.next();
                ele = new JSONObject();
                nodes.put(ele);
                ele.put(PROP_CLASS, obj.getSemanticObject().getSemanticClass().getClassCodeName());
                ele.put(PROP_TITLE, obj.getTitle());

                //if(obj.getDescription()==null) obj.setDescription("");

                ele.put(PROP_DESCRIPTION, obj.getDescription());
                ele.put(PROP_URI, obj.getURI());
                ele.put(PROP_X, obj.getX());
                ele.put(PROP_Y, obj.getY());
                ele.put(PROP_W, obj.getWidth());
                ele.put(PROP_H, obj.getHeight());
                if (obj.getContainer() != null) {
                    ele.put(PROP_CONTAINER, obj.getContainer().getURI());
                } else {
                    ele.put(PROP_CONTAINER, "");
                }
                if (obj.getParent() != null) {
                    ele.put(PROP_PARENT, obj.getParent().getURI());
                } else {
                    ele.put(PROP_PARENT, "");
                }

                if (obj.getLabelSize() != 0) {
                    ele.put(PROP_labelSize, obj.getLabelSize());
                } else {
                    ele.put(PROP_labelSize, 10);
                }


                if (obj instanceof Sortable) {

                    //System.out.println("Es coleccion...");
                    Sortable sorble = (Sortable) obj;
                    ele.put(PROP_index, sorble.getIndex());
                }

                if (obj instanceof ActivityConfable) {

                    ActivityConfable tsk = (ActivityConfable) obj;
                    if (tsk.getLoopCharacteristics() != null) {
                        LoopCharacteristics loopC = tsk.getLoopCharacteristics();
                        if (loopC instanceof MultiInstanceLoopCharacteristics) {
                            ele.put(PROP_isMultiInstance, true);
                        } else {
                            ele.put(PROP_isMultiInstance, false);
                        }

                        if (loopC instanceof StandarLoopCharacteristics) {
                            ele.put(PROP_isLoop, true);
                        } else {
                            ele.put(PROP_isLoop, false);
                        }
                    }
                    ele.put(PROP_isForCompensation, Boolean.toString(tsk.isForCompensation()));
                }

                if (obj instanceof Collectionable) {
                    //System.out.println("Es coleccion subprocess...");
                    Collectionable colble = (Collectionable) obj;
                    if (colble.isCollection()) {
                        ele.put(PROP_isCollection, true);
                    } else {
                        ele.put(PROP_isCollection, false);
                    }
                    //System.out.println("===>"+colble.isCollection());
                }

                Iterator<ConnectionObject> it = obj.listOutputConnectionObjects();
                while (it.hasNext()) {
                    ConnectionObject connectionObject = it.next();
                    coele = new JSONObject();
                    nodes.put(coele);
                    coele.put(PROP_CLASS, connectionObject.getSemanticObject().getSemanticClass().getClassCodeName());
                    coele.put(PROP_URI, connectionObject.getURI());
                    coele.put(PROP_START, connectionObject.getSource().getURI());
                    coele.put(PROP_END, connectionObject.getTarget().getURI());
                    coele.put(PROP_TITLE, connectionObject.getTitle());
                    coele.put(PROP_CONNPOINTS, connectionObject.getConnectionPoints());
                    //coele.put(PROP_DESCRIPTION, connectionObject.getDescription());
                }
                if (obj instanceof Containerable) {
                    getSubProcessJSON((Containerable) obj, nodes);
                }
            }
        } catch (Exception e) {
            log.error("Error al general el JSON del Modelo.....getSubProcessJSON(" + subprocess.getId() + ", uri:" + subprocess.getURI() + ")", e);
        }
    }
    
}