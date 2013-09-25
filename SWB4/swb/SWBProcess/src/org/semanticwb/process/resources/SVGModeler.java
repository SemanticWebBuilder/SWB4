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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Sortable;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.ActivityConfable;
import org.semanticwb.process.model.CatchEvent;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.Collectionable;
import org.semanticwb.process.model.ConnectionObject;
import org.semanticwb.process.model.Containerable;
import org.semanticwb.process.model.GraphicalElement;
import org.semanticwb.process.model.IntermediateCatchEvent;
import org.semanticwb.process.model.LoopCharacteristics;
import org.semanticwb.process.model.MultiInstanceLoopCharacteristics;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.StandarLoopCharacteristics;
import org.semanticwb.process.model.UserTask;

/**
 * Modelador de procesos basado en SVG y Javascript.
 * @author Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
 */
public class SVGModeler extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(SVGModeler.class);
    public static final String MODE_MODELER = "modeler";
    public static final String MODE_GATEWAY = "gateway";
    public static final String MODE_EXPORT = "export";
    public static final String ACT_GETPROCESSJSON = "getProcessJSON";
    public static final String ACT_STOREPROCESS = "storeProcess";
    public static final String ACT_LOADFILE = "loadFile";
    private static final String PROP_CLASS = "class";
    private static final String PROP_TITLE = "title";
    private static final String ERRORSTRING = "{error:\"_JSONERROR_\"}";
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
    private SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (MODE_MODELER.equals(mode)) {
            doModeler(request, response, paramRequest);
        } else if (MODE_GATEWAY.equals(mode)) {
            doGateway(request, response, paramRequest);
        }  else if (MODE_EXPORT.equals(mode)) {
            doExport(request, response, paramRequest);
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
        Resource base = getResourceBase();
        boolean isViewMode = false;
        
        response.setContentType("text/html; charset=UTF-8");

        if (base.getAttribute("viewMode") != null && base.getAttribute("viewMode").equals("true")) {
            isViewMode = true;
        }

        try {
            request.setAttribute("isViewMode", isViewMode);
            request.setAttribute("paramRequest", paramRequest);
            rd.include(request, response);
        } catch (Exception ex) {
            log.error(ex);
        }
    }
    
    /**
     * Modo para administrar las peticiones del modelador. Actúa como gateway para ejecutar las acciones.
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException 
     */
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        OutputStream outs = response.getOutputStream();
        String action = paramRequest.getAction();
        GenericObject go = ont.getGenericObject(request.getParameter("suri"));
        
        if (ACT_GETPROCESSJSON.equals(action)) { //Obtener el JSON del modelo almacenado
            String json = "";
            try {
                if (go != null && go instanceof Process) {
                    Process process = (Process) go;
                    JSONObject pJson = getProcessJSON(process);
                    if (pJson != null) {
                        json = pJson.toString();
                    } else {
                        json = ERRORSTRING.replace("_JSONERROR_", paramRequest.getLocaleString("msgJSONPError"));
                    }
                    outs.write(json.getBytes("UTF-8"));
                } else {
                    log.error("Error to create JSON: Process not found");
                    outs.write(ERRORSTRING.replace("_JSONERROR_", paramRequest.getLocaleString("msgNoProcess")).getBytes("UTF-8"));
                }
            } catch (Exception e) {
                log.error("Error to create JSON...", e);
                outs.write(ERRORSTRING.replace("_JSONERROR_", paramRequest.getLocaleString("msgJSONPError")).getBytes("UTF-8"));
            }
        } else if (ACT_LOADFILE.equals(action)) { //Carga de modelo desde archivo swp
            response.setContentType("text/html");
            String json = processFile(request);
            if (json == null) {
                json =  ERRORSTRING.replace("_JSONERROR_", paramRequest.getLocaleString("msgJSONPError"));
            }
            outs.write(json.getBytes("UTF-8"));
        } else if (ACT_STOREPROCESS.equals(action)) { //Persistir modelo del proceso
            String jsonStr = request.getParameter("jsonString");
            HashMap<String, JSONObject> hmjson = new HashMap();

            if (go != null && go instanceof Process) {
                Process process = (Process) go;
                String str_uri = null;
                JSONArray jsarr = null;
                JSONObject jsobj = null;
                
                try {
                    //System.out.println("json recibido: "+node.getTextContent());
                    if (jsonStr.startsWith(JSONSTART) && jsonStr.endsWith(JSONEND)) {
                        jsonStr = jsonStr.replace(JSONSTART, "");
                        jsonStr = jsonStr.replace(JSONEND, "");

                        try {
                            jsobj = new JSONObject(jsonStr);
                            jsarr = jsobj.getJSONArray("nodes");

                            //identificando los elementos asociados directamente al proceso
                            for (int i = 0; i < jsarr.length(); i++) {
                                try {
                                    jsobj = jsarr.getJSONObject(i);
                                    str_uri = jsobj.getString(PROP_URI);
                                    //System.out.println("json uri:"+str_uri);
                                    hmjson.put(str_uri, jsobj);
                                } catch (Exception ej) {
                                    log.error("Error en elemento del JSON. ", ej);
                                }
                            }
                            boolean endsGood = createProcessElements(process, request, response, paramRequest, hmjson);
                            if (endsGood) {
                                process.setData(jsonStr);
                            } else {
                                outs.write(ERRORSTRING.replace("_JSONERROR_", getError(3)).getBytes());
                            }
                        } catch (Exception ejs) {
                            log.error("Error en el JSON recibido. ", ejs);
                            outs.write(ERRORSTRING.replace("_JSONERROR_", getError(3)).getBytes());
                        }
                    }
                } catch (Exception e) {
                    log.error("Error al leer JSON...", e);
                    outs.write(ERRORSTRING.replace("_JSONERROR_", getError(3)).getBytes());
                }
            } else {
                log.error("Error to create JSON: Process not found");
                outs.write(ERRORSTRING.replace("_JSONERROR_", paramRequest.getLocaleString("msgNoProcess")).getBytes());
            }
        }
    }
    
    public String getError(int id) {
        String ret = "ERR:";
            if (id == 0) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_loginfail") + "...";
            } else if (id == 1) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_nouser") + "...";
            } else if (id == 2) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noservice") + "...";
            } else if (id == 3) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_serviceprocessfail") + "...";
            } else if (id == 4) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_parametersprocessfail") + "...";
            } else if (id == 5) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopicmap") + "...";
            } else if (id == 6) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopic") + "...";
            } else if (id == 7) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_usernopermiss") + "...";
            } else if (id == 8) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicAlreadyexist") + "...";
            } else if (id == 9) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_byImplement") + "...";
            } else if (id == 10) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicMapAlreadyExist") + "...";
            } else if (id == 11) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_FileNotFound") + "...";
            } else if (id == 12) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noversions") + "...";
            } else if (id == 13) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_xmlinconsistencyversion") + "...";
            } else if (id == 14) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noResourcesinMemory") + "...";
            } else if (id == 15) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noTemplatesinMemory") + "...";
            } else if (id == 16) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_TemplatenotRemovedfromFileSystem") + "...";
            } else if (id == 17) {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_adminUsernotCreated") + "...";
            } else {
                ret += SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_errornotfound") + "...";
            }
            return ret;
    }
    
    /**
     * Crea los elementos del proceso. Realiza la verificación del JSON del proceso y crea los elementos especificados.
     * @param process Proceso a reconstruir.
     * @param request 
     * @param response
     * @param paramsRequest
     * @param hmjson Mapa de objetos recuperados del JSON.
     * @return true si la creación de los elementos tuvo éxito.
     */
    public boolean createProcessElements(org.semanticwb.process.model.Process process, HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest, HashMap<String, JSONObject> hmjson) {
        //Revisando si existen problemas
        boolean ret = false;
        if(reviewJSON(process,request,response,paramsRequest,hmjson,false))
        {
            // no se encontraron problemas en el proceso, actualiza
            ret = reviewJSON(process,request,response,paramsRequest,hmjson,true);
        }
        return ret;
    }
    
    /** Utilizado para identificar que elementos del proceso que se han eliminado
     *  Aquí también se eliminan todos los elementos de coneccion existentes
     *  Este sólo se debe utilizar cuando se actualiza el modelo delproceso.
     *
     * @param process, Modelo a cargar los elementos del proceso
     * @return Vector, con los uris de los elementos existentes.
     */
    public HashMap<String, String> loadProcessElements(org.semanticwb.process.model.Process process) {
        HashMap<String, String> hmori = new HashMap();

        try {
            Iterator<GraphicalElement> it_fo = process.listContaineds();
            while (it_fo.hasNext()) {
                GraphicalElement obj = it_fo.next();
                hmori.put(obj.getURI(), obj.getURI());

                // Se eliminan las conexiones entre GraphicalElements
                Iterator<ConnectionObject> it = obj.listOutputConnectionObjects();
                while (it.hasNext()) {
                    ConnectionObject connectionObject = it.next();
                    hmori.put(connectionObject.getURI(), connectionObject.getURI());
                    //connectionObject.remove();
                }
                if (obj instanceof Containerable) {
                    loadSubProcessElements((Containerable) obj, hmori);
                }
            }

        } catch (Exception e) {
            log.error("Error al general el JSON del Modelo.....loadProcessElements(" + process.getTitle() + ", uri:" + process.getURI() + ")", e);
        }
        return hmori;
    }

    public void loadSubProcessElements(org.semanticwb.process.model.Containerable subprocess, HashMap hmori) {
        try {
            Iterator<GraphicalElement> it_fo = subprocess.listContaineds();
            while (it_fo.hasNext()) {
                GraphicalElement obj = it_fo.next();
                hmori.put(obj.getURI(), obj.getURI());

                // Se eliminan las conexiones entre GraphicalElements
                Iterator<ConnectionObject> it = obj.listOutputConnectionObjects();
                while (it.hasNext()) {
                    ConnectionObject connectionObject = it.next();
                    hmori.put(connectionObject.getURI(), connectionObject.getURI());
                    //connectionObject.remove();
                }
                if (obj instanceof Containerable) {
                    loadSubProcessElements((Containerable) obj, hmori);
                }
            }
        } catch (Exception e) {
            log.error("Error al general el JSON del Modelo.....loadSubProcessElements(" + subprocess.getId() + ", uri:" + subprocess.getURI() + ")", e);
        }
    }
    
    /**
     * Verifica que el JSON del moelo está bien formado.
     * @param process Proceso.
     * @param request
     * @param response
     * @param paramsRequest
     * @param hmjson
     * @param bupdate
     * @return 
     */
    public boolean reviewJSON(org.semanticwb.process.model.Process process, HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest, HashMap<String, JSONObject> hmjson, boolean bupdate){
        boolean ret = Boolean.TRUE;
        HashMap<String, String> hmori = loadProcessElements(process);
        HashMap<String, String> hmnew = new HashMap();

        ProcessSite procsite = process.getProcessSite();
        GenericObject go = null;
        String uri = null, sclass = null, title = null, description = null, container = null, parent = null, start = null, end = null;
        int x = 0, y = 0, w = 0, h = 0, labelSize = 10, index = 0;
        Boolean isMultiInstance = null, isSeqMultiInstance = null, isLoop = null, isForCompensation = null, isCollection = null, isAdHoc = null, isTransaction = null, isInterrupting = null;

        boolean tmpBoolean = false;

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
                //System.out.println("json element: "+json.toString());
                uri = json.getString(PROP_URI);
                sclass = json.getString(PROP_CLASS);
                semclass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(PROCESS_PREFIX + "#" + sclass);
                if (semclass == null) {
                    continue;
                }
                try {
                    // primero se crean los elementos graficos del modelo
                    if (semclass.isSubClass(GraphicalElement.swp_GraphicalElement)) {
                        title = json.optString(PROP_TITLE, "");
                        try {
                            description = json.optString(PROP_DESCRIPTION,"");
                        } catch (Exception e) {
                            description = "";
                        }

                        try {
                            isMultiInstance = json.getBoolean(PROP_isMultiInstance);
                            //System.out.println("MultiInstancia: " + isMultiInstance.booleanValue());

                        } catch (Exception e) {
                            isMultiInstance = null;
                        }
                        try {
                            isSeqMultiInstance = json.getBoolean(PROP_isSeqMultiInstance);
                            //System.out.println("SeqMultiInstancia: " + isMultiInstance.booleanValue());

                        } catch (Exception e) {
                            isMultiInstance = null;
                        }
                        try {
                            isLoop = json.getBoolean(PROP_isLoop);
                            //System.out.println("Ciclo: " + isLoop.booleanValue());
                        } catch (Exception e) {
                            isLoop = null;
                            //System.out.println("Ciclo: null");
                        }
                        try {
                            isForCompensation = json.getBoolean(PROP_isForCompensation);
                            //System.out.println("Compensacion");

                        } catch (Exception e) {
                            isForCompensation = null;
                        }
                        try {
                            isAdHoc = json.getBoolean(PROP_isAdHoc);
                        } catch (Exception e) {
                            isAdHoc = null;
                        }
                        try {
                            isTransaction = json.getBoolean(PROP_isTransaction);
                        } catch (Exception e) {
                            isTransaction = null;
                        }
                        try {
                            isInterrupting = json.getBoolean(PROP_isInterrupting);
                        } catch (Exception e) {
                            isInterrupting = true;
                        }

                        try {
                            isCollection = json.getBoolean(PROP_isCollection);
                            //System.out.println("Viene isCollecion:"+isCollection.booleanValue());
                        } catch (Exception e) {
                            isCollection = null;
                            //System.out.println("No viene isCollecion....."+title);
                        }

                        try {
                            index = json.getInt(PROP_index);
                            //System.out.println("Viene isCollecion:"+isCollection.booleanValue());
                        } catch (Exception e) {
                            index = 0;
                            //System.out.println("No viene isCollecion....."+title);
                        }

                        //System.out.println("uri: "+uri);

                        x = json.getInt(PROP_X);
                        y = json.getInt(PROP_Y);
                        w = json.getInt(PROP_W);
                        h = json.getInt(PROP_H);
                        parent = json.optString(PROP_PARENT);
                        container = json.optString(PROP_CONTAINER);
                        labelSize = json.getInt(PROP_labelSize);

                        // revisando si el elemento existe
                        if (hmori.get(uri) != null) {
                            //System.out.println("revisando si el elemento existe");
                            go = ont.getGenericObject(uri);

                            if (go instanceof GraphicalElement) {
                                // actualizando datos en elemento existente
                                ge = (GraphicalElement) go;
                                if (!ge.getTitle().equals(title)) {
                                    if(bupdate) ge.setTitle(title);
                                }
                                if ((null != description && ge.getDescription() != null && !ge.getDescription().equals(description)) || (null != description && ge.getDescription() == null)) {
                                    if(bupdate)  ge.setDescription(description);
                                }

                                if (ge.getX() != x) {
                                    if(bupdate) ge.setX(x);
                                }
                                if (ge.getY() != y) {
                                    if(bupdate) ge.setY(y);
                                }
                                if (ge.getWidth() != w) {
                                    if(bupdate) ge.setWidth(w);
                                }
                                if (ge.getHeight() != h) {
                                    if(bupdate) ge.setHeight(h);
                                }
                                if (ge.getLabelSize() != labelSize) {
                                    if(bupdate) ge.setLabelSize(labelSize);
                                }

                                // si es un Sortable se revisa si tiene index
                                if (ge instanceof Sortable) {
                                    Sortable sorble = (Sortable) go;
                                    if(bupdate) sorble.setIndex(index);
                                }
                                
                                if (ge instanceof CatchEvent) {
                                    CatchEvent ice = (CatchEvent) ge;
                                    if (bupdate) ice.setInterruptor(isInterrupting);
                                }

                                if (go instanceof ActivityConfable) {  //Task
                                    ActivityConfable tsk = (ActivityConfable) go;

                                    if (null != isForCompensation && isForCompensation.booleanValue()) {
                                        if(bupdate) tsk.setForCompensation(isForCompensation.booleanValue());
                                    }

                                    if (null != isMultiInstance) {

                                        if (isMultiInstance.booleanValue()) {
                                            // si existe no se hace nada se deja el MultiInstanceLoopCharacteristics
                                            LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                            if (loopchar == null) // si no existe lo crea
                                            {
                                                // si no existe se crea uno nuevo y se asigna al task
                                                if(bupdate) {
                                                    loopchar = MultiInstanceLoopCharacteristics.ClassMgr.createMultiInstanceLoopCharacteristics(procsite);
                                                    tsk.setLoopCharacteristics(loopchar);
                                                }
                                            } else if (!(loopchar instanceof MultiInstanceLoopCharacteristics)) {
                                                if(bupdate) loopchar.getSemanticObject().remove();
                                            }
                                            // si no existe se crea uno nuevo y se asigna al task
                                            if(bupdate) {
                                                loopchar = MultiInstanceLoopCharacteristics.ClassMgr.createMultiInstanceLoopCharacteristics(procsite);
                                                tsk.setLoopCharacteristics(loopchar);
                                            }
                                        } else {
                                            // si existe y cambio y ya no es MultiInstance se elimina el MultiInstanceLoopCharacteristics asociado
                                            LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                            if (null != loopchar && loopchar instanceof MultiInstanceLoopCharacteristics) {
                                                if(bupdate) loopchar.getSemanticObject().remove();
                                            }
                                        }
                                    }

                                    if (null != isLoop) {
                                        //System.out.println("LoopCharacteristic....");
                                        if (isLoop.booleanValue()) {
                                            // si existe no se hace nada se deja el LoopCharacteristics
                                            LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                            if (loopchar == null) // si no existe lo crea
                                            {
                                                // si no existe se crea uno nuevo y se asigna al task
                                                //System.out.println("creando LoopCharacteristic....");
                                                if(bupdate) {
                                                    loopchar = StandarLoopCharacteristics.ClassMgr.createStandarLoopCharacteristics(procsite);
                                                    tsk.setLoopCharacteristics(loopchar);
                                                }
                                            } else if (!(loopchar instanceof StandarLoopCharacteristics)) {
                                                if(bupdate) loopchar.getSemanticObject().remove();
                                                // si no existe se crea uno nuevo y se asigna al task
                                                //System.out.println("eliminando y creando LoopCharacteristic....");
                                                if(bupdate) {
                                                    loopchar = StandarLoopCharacteristics.ClassMgr.createStandarLoopCharacteristics(procsite);
                                                    tsk.setLoopCharacteristics(loopchar);
                                                }
                                            }

                                        } else {
                                            // si existe y cambio y ya no es Loop se elimina el StandarLoopCharacteristics asociado
                                            LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                            if (null != loopchar && loopchar instanceof StandarLoopCharacteristics) {
                                                //System.out.println("eliminando LoopCharacteristic....");
                                                if(bupdate) loopchar.getSemanticObject().remove();
                                            }
                                        }
                                    }
                                }

                                // si es un Collectionable se revisa si es colección
                                if (ge instanceof Collectionable) {
                                    Collectionable colble = (Collectionable) go;
                                    if (isCollection != null) {
                                        //System.out.println("Save Collection ===>"+isCollection.booleanValue());
                                        if(bupdate) colble.setCollection(isCollection.booleanValue());
                                    }
                                }
                                // se agrega en este hm para la parte de la secuencia del proceso
                                if(bupdate) hmnew.put(uri, go.getURI());
                            }
                            // se quita elemento que ha sido actualizado
                            if(bupdate) hmori.remove(uri);

                        } else {
                            //Se genera el nuevo elemento
                            //System.out.println("new element: "+semclass);
                            long id = model.getCounter(semclass);
                            GenericObject gi = null;
                            if(bupdate){
                                gi = model.createGenericObject(model.getObjectUri(String.valueOf(id), semclass), semclass);
                            
                                ge = (GraphicalElement) gi;
                                ge.setTitle(title);
                                if (null != description) {
                                    ge.setDescription(description);
                                }
                                ge.setX(x);
                                ge.setY(y);
                                ge.setHeight(h);
                                ge.setWidth(w);
                            
                            //System.out.println("uri: "+uri+" new uri: "+gi.getURI());

                            // si es un Sortable se revisa si tiene index
                            if (ge instanceof Sortable) {
                                Sortable sorble = (Sortable) gi;
                                sorble.setIndex(index);
                            }
                            
                            if (ge instanceof IntermediateCatchEvent) {
                                IntermediateCatchEvent ice = (IntermediateCatchEvent) ge;
                                ice.setInterruptor(isInterrupting);
                            }

                            if (ge instanceof ActivityConfable) {  //Task
                                ActivityConfable tsk = (ActivityConfable) gi;

                                if (null != isForCompensation && isForCompensation.booleanValue()) {
                                    tsk.setForCompensation(isForCompensation.booleanValue());
                                }

                                if (null != isMultiInstance) {

                                    if (isMultiInstance.booleanValue()) {
                                        // si existe no se hace nada se deja el MultiInstanceLoopCharacteristics
                                        LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                        if (loopchar == null) // si no existe lo crea
                                        {
                                            // si no existe se crea uno nuevo y se asigna al task
                                            loopchar = MultiInstanceLoopCharacteristics.ClassMgr.createMultiInstanceLoopCharacteristics(procsite);
                                            tsk.setLoopCharacteristics(loopchar);
                                        } else if (!(loopchar instanceof MultiInstanceLoopCharacteristics)) {
                                            loopchar.getSemanticObject().remove();
                                        }
                                        // si no existe se crea uno nuevo y se asigna al task
                                        loopchar = MultiInstanceLoopCharacteristics.ClassMgr.createMultiInstanceLoopCharacteristics(procsite);
                                        tsk.setLoopCharacteristics(loopchar);
                                    } else {
                                        // si existe y cambio y ya no es MultiInstance se elimina el MultiInstanceLoopCharacteristics asociado
                                        LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                        if (null != loopchar && loopchar instanceof MultiInstanceLoopCharacteristics) {
                                            loopchar.getSemanticObject().remove();
                                        }
                                    }
                                }

                                if (null != isLoop) {
                                    //System.out.println("LoopCharacteristic....");
                                    if (isLoop.booleanValue()) {
                                        // si existe no se hace nada se deja el LoopCharacteristics
                                        LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                        if (loopchar == null) // si no existe lo crea
                                        {
                                            // si no existe se crea uno nuevo y se asigna al task
                                            //System.out.println("creando LoopCharacteristic....");
                                            loopchar = StandarLoopCharacteristics.ClassMgr.createStandarLoopCharacteristics(procsite);
                                            tsk.setLoopCharacteristics(loopchar);
                                        } else if (!(loopchar instanceof StandarLoopCharacteristics)) {
                                            loopchar.getSemanticObject().remove();
                                            // si no existe se crea uno nuevo y se asigna al task
                                            //System.out.println("eliminando y creando LoopCharacteristic....");
                                            loopchar = StandarLoopCharacteristics.ClassMgr.createStandarLoopCharacteristics(procsite);
                                            tsk.setLoopCharacteristics(loopchar);
                                        }

                                    } else {
                                        // si existe y cambio y ya no es Loop se elimina el StandarLoopCharacteristics asociado
                                        LoopCharacteristics loopchar = tsk.getLoopCharacteristics();
                                        if (null != loopchar && loopchar instanceof StandarLoopCharacteristics) {
                                            //System.out.println("eliminando LoopCharacteristic....");
                                            loopchar.getSemanticObject().remove();
                                        }
                                    }
                                }
                            }

                            // si es un Collectionable se revisa si es colección
                            if (ge instanceof Collectionable) {
                                Collectionable colble = (Collectionable) gi;
                                if (isCollection != null) {
                                    //System.out.println("Save Collection ===>"+isCollection.booleanValue());
                                    colble.setCollection(isCollection.booleanValue());
                                }
                            }

                            }


                            // se agrega nuevo elemento en el hmnew
                            if(bupdate) hmnew.put(uri, gi.getURI());

                            ///////////////////////////////////////
//                        if(semclass.isSubClass(Task.swp_Task))
//                        {
//                                Task tsk = (Task) gi;
//                                if(isForCompensation)
//                                {
//                                    tsk.setForCompensation(isForCompensation);
//                                } else tsk.setForCompensation(false);
//
//                                Resource res = procsite.createResource();
//                                res.setResourceType(procsite.getResourceType("ProcessForm"));
//                                res.setTitle(title);
//                                res.setActive(Boolean.TRUE);
//                                ((UserTask)gi).addResource(res);
//
//                        }

                            if (semclass.equals(UserTask.swp_UserTask)) {

                                if (procsite.getResourceType("ProcessForm") == null && bupdate) {
                                    ResourceType resType = procsite.createResourceType("ProcessForm");
                                    resType.setTitle("ProcessForm");
                                    resType.setTitle("ProcessForm", "en");
                                    resType.setDescription("ProcessForm");
                                    resType.setDescription("ProcessForm", "es");
                                    resType.setResourceClassName("org.semanticwb.process.resources.ProcessForm");
                                    resType.setResourceBundle("org.semanticwb.process.resources.ProcessForm");
                                    resType.setResourceMode(ResourceType.MODE_SYSTEM);
                                }

                                if (procsite.getResourceType("ProcessForm") != null && bupdate) {
                                    Resource res = procsite.createResource();
                                    res.setResourceType(procsite.getResourceType("ProcessForm"));
                                    res.setTitle(title);
                                    res.setActive(Boolean.TRUE);
                                    ((UserTask) gi).addResource(res);
                                }
                            }
                            ////////////////////////////////////////
                        } // termina else
                    } // termina if graphicalElement
                } catch (Exception e) {
                    log.error("Error al procesar el JSON Object para la creacion y/o actualizar GraphicalElement... ", e);
                    ret = false;
                    break;
                }
            } //termina while

            // Parte para relacionar elementos container y parents
            it = hmjson.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                JSONObject json = (JSONObject) hmjson.get(key);
                uri = json.getString(PROP_URI);
                sclass = json.getString(PROP_CLASS);

                semclass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(PROCESS_PREFIX + "#" + sclass);
                if (semclass == null) {
                    continue;
                }

                if (semclass.isSubClass(GraphicalElement.swp_GraphicalElement)) {
                    parent = json.optString(PROP_PARENT, "");
                    container = json.optString(PROP_CONTAINER, "");
                    go = ont.getGenericObject(hmnew.get(uri));
                    ge = null;
                    if (go instanceof GraphicalElement) {
                        ge = (GraphicalElement) go;
                    }
                    if (ge != null && bupdate) {
                        if (container != null && container.trim().length() == 0) {
                            ge.setContainer(process);
                        } else {
                            GenericObject subproc = ont.getGenericObject(hmnew.get(container));
                            if (subproc != null) // && (subproc instanceof SubProcess || subproc.getSemanticObject().getSemanticClass().isSubClass(SubProcess.swp_SubProcess)))
                            {
                                ge.setContainer((Containerable) subproc);
                            }
                        }
                        if (parent != null && parent.trim().length() > 0) {
                            GenericObject go_ge = ont.getGenericObject(hmnew.get(parent));
                            if (go_ge != null && go_ge instanceof GraphicalElement) {
                                ge.setParent((GraphicalElement) go_ge);
                            }
                        }

                    }
                }
            }

            GenericObject gostart = null;
            GenericObject goend = null;
            String sconnpoints = null;

            // Parte para generar el flujo del proceso
            it = hmjson.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                JSONObject json = (JSONObject) hmjson.get(key);
                uri = json.getString(PROP_URI);

                sclass = json.getString(PROP_CLASS);

                semclass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(PROCESS_PREFIX + "#" + sclass);
                if (semclass == null) {
                    continue;
                }

                if (semclass.isSubClass(ConnectionObject.swp_ConnectionObject)) {
                    start = json.getString(PROP_START);
                    end = json.getString(PROP_END);

                    title = json.optString(PROP_TITLE,"");
                    try {
                        description = json.optString(PROP_DESCRIPTION,"");
                    } catch (Exception e) {
                        description = "";
                    }

                    try {
                        sconnpoints = json.getString(PROP_CONNPOINTS);
                    } catch (Exception e) {
                        sconnpoints = "";
                    }

                    // revisando si existe elemento coneccion
                    if (hmori.get(uri) != null) {
                        go = ont.getGenericObject(hmori.get(uri));
                        co = (ConnectionObject) go;
                        if (!co.getTitle().equals(title)) {
                            if(bupdate) co.setTitle(title);
                        }

                        if ((null != description && co.getDescription() != null && !co.getDescription().equals(description)) || (null != description && co.getDescription() == null)) {
                            if(bupdate) co.setDescription(description);
                        }

                        if ((null != sconnpoints && co.getConnectionPoints() != null && !co.getConnectionPoints().equals(sconnpoints)) || (null != sconnpoints && co.getConnectionPoints() == null)) {
                            if(bupdate) co.setConnectionPoints(sconnpoints);
                        }

                        if (!co.getSource().getURI().equals(start)) {
                            if (hmnew.get(start) != null) {
                                gostart = ont.getGenericObject(hmnew.get(start));
                                if(bupdate) co.setSource((GraphicalElement) gostart);
                            }
                        }
                        if (!co.getTarget().getURI().equals(end)) {
                            if (hmnew.get(end) != null) {
                                goend = ont.getGenericObject(hmnew.get(end));
                                if(bupdate) co.setTarget((GraphicalElement) goend);
                            }
                        }
                        if(bupdate) hmori.remove(uri);
                    } else {
                        if (hmnew.get(start) != null && hmnew.get(end) != null) {
                            long id = model.getCounter(semclass);
                            go = model.createGenericObject(model.getObjectUri(String.valueOf(id), semclass), semclass);
                            co = (ConnectionObject) go;
                            if(bupdate) co.setTitle(title);
                            if (null != description) {
                                if(bupdate) co.setDescription(description);
                            }
                            if (null != sconnpoints) {
                                if(bupdate) co.setConnectionPoints(sconnpoints);
                            }
                            gostart = ont.getGenericObject(hmnew.get(start));
                            goend = ont.getGenericObject(hmnew.get(end));
                            if(bupdate) co.setSource((GraphicalElement) gostart);
                            if(bupdate) co.setTarget((GraphicalElement) goend);
                        }
                    }
                }
            }

            // Eliminando elementos que fueron borrados en el applet Modeler
            // que se tenian en el original pero ya no se necesitan
            it = hmori.keySet().iterator();
            SemanticObject so = null;
            while (it.hasNext()) {
                String key = it.next();
                try {
                    so = ont.getSemanticObject(hmori.get(key));
                    if (so != null) {
                        if(bupdate) so.remove();
                    }
                } catch (Exception e) {
                    log.error("Error al tratar de eliminar elemento del proceso...", e);
                    ret=false;
                    break;
                }
            }

        } catch (Exception e) {
            log.error("Error al actualizar el modelo del proceso. Modeler.createProcessElements()", e);
            ret=false;
        }
        return ret;
    }
    
    /**
     * Modo para hacer la exportación del modelo del proceso a distintos formatos.
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException 
     */
    public void doExport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        OutputStream outs = response.getOutputStream();
        String format = request.getParameter("output_format");
        String data = request.getParameter("data");
        
        Process p = (Process) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(request.getParameter("suri"));
        
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + (p != null ? p.getTitle() : "Proceso") + "."+format+"\"");

        if ("svg".equalsIgnoreCase(format)) {
            String svg = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                         "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n";
            svg += data;

            //Corregir clases en objetos de datos
            svg = svg.replace("<g id=\"data\" bclass=\"itemaware\" oclass=\"itemaware_o\">", "<g id=\"data\" bclass=\"itemaware\" oclass=\"itemaware_o\" class=\"itemAware\">");
            svg = svg.replace("<g id=\"dataStore\" bclass=\"itemaware\" oclass=\"itemaware_o\" transform=\"translate(-12,-10)\">", "<g id=\"dataStore\" bclass=\"itemaware\" oclass=\"itemaware_o\" transform=\"translate(-12,-10)\" class=\"itemAware\">");

            response.setContentType("image/svg+xml");
            outs.write(svg.getBytes());
        } else if ("swp".equalsIgnoreCase(format)) {
            response.setContentType("application/json");
            String json = "";
            if (p != null) {
                JSONObject pJson = getProcessJSON(p);
                if (pJson != null) {
                    json = pJson.toString();
                } else {
                    json = ERRORSTRING.replace("_JSONERROR_", paramRequest.getLocaleString("msgJSONPError"));
                }
            } else {
                if (data != null && data.length() > 0) {
                    json = data;
                }
            }
            outs.write(json.getBytes("UTF-8"));
        }
//            } else if ("png".equalsIgnoreCase(format)) {
//                String svg = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
//                             "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">";
//                svg += data.trim();
//                
//                InputStream strStream = new ByteArrayInputStream(svg.toString().getBytes("UTF-8"));
//                TranscoderInput ti = new TranscoderInput(strStream/*svgFile.toURI().toString()*/);
//                TranscoderOutput to = new TranscoderOutput(outs);
//                
//                PNGTranscoder t = new PNGTranscoder();
//                t.addTranscodingHint(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE, Boolean.TRUE);
//                try {                
//                    t.transcode(ti, to);
//                } catch (TranscoderException ex) {
//                    log.error("Ocurrió un problema al generar la imagen", ex);
//                }
//            }
        outs.flush();
        outs.close();
    }
    
    /** Utilizado para generar un JSON del modelo, para la comunicacion con el modelador
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
            json_ret = null;
            log.error("Error al general el JSON del Modelo.....getModelJSON(" + process.getTitle() + ", uri:" + process.getURI() + ")", e);
        }
        return json_ret;
    }
    
    /**
     * Obtiene el JSON de un subproceso en el modelo.
     * @param subprocess Subproceso.
     * @param nodes Arreglo donde se almacenarán los nodos generados.
     */
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
    
    /**
     * Procesa los archivos enviados para la carga de modelo.
     * @param request
     * @return Cadena con el JSON contenido en el archivo.
     */
    private String processFile(HttpServletRequest request) {
        String data = null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        
        if (isMultipart) {
            ServletFileUpload upload = new ServletFileUpload();
            try {
                FileItemIterator it = upload.getItemIterator(request);
                while(it.hasNext()) {
                    FileItemStream item = it.next();
                    if (!item.isFormField()) {//Es un archivo
                        if (item.getName().endsWith(".swp")) { //Es un archivo swp
                            InputStream stream = item.openStream();
                            java.util.Scanner scanner = new Scanner(stream).useDelimiter("\\A");
                            if (scanner.hasNext()) {
                                data = scanner.next();
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                data = null;
                log.error("Error al cargar el archivo", ex);
            }
        }
        return data;
    }
}