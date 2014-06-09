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
package org.semanticwb.process.xpdl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Stack;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parser de XPDL para generar un modelo JSON de un proceso.
 * @author Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
 */
public class XPDLParser extends DefaultHandler {
    static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    
    private String xsdPath;
    private SAXParserFactory spf;
    private SAXParser parser;
    private Stack<String> XMLElementNames;
    private Stack<JSONObject> XMLElementObjects;
    private HashMap<String, JSONObject>  elements;
    private XPDLProcessor processor;
    private JSONObject processModel;
    private boolean validate = false;
    private boolean namespaceAware = true;
    
    /**
     * Constructor. Crea e inicializa el parser sin forzar validación y con manejo de namespaces.
     */
    public XPDLParser() {
        initialize(false, true, null);
    }
    
    /**
     * Constructor. Crea e inicializa un parser forzando validación con el XSD proporcionado.
     * @param xsdPath ruta al archivo XSD para validación.
     */
    public XPDLParser(String xsdPath) {
        initialize(true, true, xsdPath);
    }
    
    /**
     * Inicializa el parser.
     * @param validate Indica si el parser forzará validación.
     * @param nsAware Indica si el parser manejará namespaces.
     * @param xsdPath Ruta al archivo XSD para validación.
     */
    private void initialize(boolean validate, boolean nsAware, String xsdPath) {
        this.validate = validate;
        this.namespaceAware = nsAware;
        this.xsdPath = xsdPath;
        
        spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(nsAware);
        spf.setValidating(validate);
        processor = new XPDLProcessor();
        
    }
    
    /**
     * Parsea el archivo XPDL y genera el modelo JSON del proceso.
     * @param istream Inputsream con el contenido del archivo XPDL.
     * @return JSON del modelo del proceso.
     * @throws Exception 
     */
    public JSONObject parse(InputStream istream) throws Exception {
        parser = spf.newSAXParser();
        
        if (validate) {
            try {
                parser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
                parser.setProperty(JAXP_SCHEMA_SOURCE, xsdPath);
            } catch (SAXNotRecognizedException ex) {
                System.err.println("Error: JAXP SAXParser property not recognized: " + JAXP_SCHEMA_LANGUAGE);
                System.err.println( "Check to see if parser conforms to the JAXP spec.");
            }
        }
        
        parser.parse(istream, this);
        return processModel;
    }
    
    /**
     * Parsea el archivo XPDL y genera el modelo JSON del proceso.
     * @param file Ruta al archivo XPDL a validar.
     * @return JSON del modelo del proceso.
     * @throws Exception 
     */
    public JSONObject parse(String file) throws Exception {
        InputStream xmlStream = new FileInputStream(new File(file));
        return parse(xmlStream);
    }
    
    @Override
    public void startDocument() throws SAXException {
        XMLElementNames = new Stack<String>();
        XMLElementObjects = new Stack<JSONObject>();
        elements = new HashMap<String, JSONObject>();
        processModel = null;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            processElement(uri, localName, qName, attributes);
        } catch (JSONException ex) {
            System.out.println("Ocurrió un error procesando el elemento"+ex);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!XMLElementNames.isEmpty()) {
            if (XMLElementNames.peek().equals(localName)) {
                XMLElementNames.pop();
            } else {
                //Unmatched closing tag
            }
        } else {
            //Unmatched opening tag
        }
        
        if (!XMLElementObjects.isEmpty()) {
            JSONObject obj = XMLElementObjects.pop();//null;
            String type = obj.optString("class","");
            
            if (localName.equalsIgnoreCase(type)) {
                elements.put(obj.optString("uri",""), obj);
            } else {
                XMLElementObjects.push(obj);
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        if (XMLElementNames.isEmpty()) {
            try {
                processModel = createProcessJSON(elements);
            } catch (JSONException ex) {
                System.out.println(ex);
            }
        }
    }
    
    /**
     * Genera un modelo JSON del proceso a partir de un hashmap de objetos.
     * @param elements HashMap de objetos recuperados en el parseo del archivo XPDL.
     * @return JSON del modelo del proceso.
     * @throws JSONException 
     */
    private JSONObject createProcessJSON(HashMap<String, JSONObject> elements) throws JSONException {
        JSONObject ret = new JSONObject();
        ret.put("class","Process");
        ret.put("title","NewProcess");
        ret.put("uri","Process:1");
        
        if (elements != null && !elements.isEmpty()) {
            JSONArray nodes = new JSONArray();
            
            for(String key : elements.keySet()) {
                JSONObject el = elements.get(key);
                String cls = el.optString("class", "");
                String fcls = el.optString("_class", cls);
                String _cls = fcls;
                String impl = el.optString(XPDLProcessor.XPDLEntities.IMPLEMENTATION, "");
                
                if (XPDLProcessor.XPDLEntities.ACTIVITY.equals(cls)) {
                    if (!"".equals(fcls)) { //No es una actividad propiamente
                        if (XPDLProcessor.XPDLEntities.STARTEVENT.equals(fcls) || XPDLProcessor.XPDLEntities.INTERMEDIATEEVENT.equals(fcls) || XPDLProcessor.XPDLEntities.ENDEVENT.equals(fcls)) {
                            String prefix = el.optString(XPDLProcessor.XPDLAttributes.TRIGGER, "");
                            String catchThrow = el.optString(XPDLProcessor.XPDLAttributes.CATCHTHROW, "");        
                            _cls = getEventType(fcls, prefix, catchThrow);

                            el.remove(XPDLProcessor.XPDLAttributes.CATCHTHROW);
                            el.remove(XPDLProcessor.XPDLAttributes.TRIGGER);
                            
                            //Offset events
                            el.put("x", el.getDouble("x")+15);
                            el.put("y", el.getDouble("y")+15);
                            el.remove("w");
                            el.remove("h");
                            //generateEventJSON(el, fcls);
                        } else if (XPDLProcessor.XPDLEntities.ROUTE.equals(fcls)) {
                            String gtwType = el.optString(XPDLProcessor.XPDLAttributes.GATEWAYTYPE,"");
                            String excType = el.optString(XPDLProcessor.XPDLAttributes.EXCLUSIVETYPE,"");
                            boolean instantiate = el.optBoolean(XPDLProcessor.XPDLAttributes.INSTANTIATE,false);
                            _cls = "";
                            
                            if ("Complex".equals(gtwType)) { //Compleja
                                _cls = "ComplexGateway";
                                //el.put("class", "ComplexGateway");
                            } else if ("Parallel".equals(gtwType)) { //Paralela
                                _cls = "ParallelGateway";
                                if (instantiate) {
                                    _cls = "ParallelStartEventGateway";
                                }
                            } else if ("Inclusive".equals(gtwType)) { //Paralela 
                                _cls = "InclusiveGateway";
                            } else if ("".equals(gtwType)) { //Es una exclusiva
                                _cls = "ExclusiveGateway";
                                if (instantiate) {
                                    _cls = "ParallelStartEventGateway";
                                } else if ("Event".equals(excType)) {
                                    _cls = "ExclusiveIntermediateEventGateway";
                                }
                            }
                            el.remove(XPDLProcessor.XPDLAttributes.GATEWAYTYPE);
                            el.remove(XPDLProcessor.XPDLAttributes.EXCLUSIVETYPE);
                            el.remove(XPDLProcessor.XPDLAttributes.INSTANTIATE);
                            //Offset gateways
                            el.put("x", el.getDouble("x")+25);
                            el.put("y", el.getDouble("y")+25);
                            el.remove("w");
                            el.remove("h");
                        } else {
                            _cls = "Task";
                            String set = el.optString(XPDLProcessor.XPDLAttributes.ACTIVITYSETID,"");
                            
                            if ("TaskSend".equals(impl)) _cls = "SendTask";
                            else if ("TaskReceive".equals(impl)) _cls = "ReceiveTask";
                            else if ("TaskUser".equals(impl)) _cls = "UserTask";
                            else if ("TaskService".equals(impl)) _cls = "ServiceTask";
                            else if ("TaskScript".equals(impl)) _cls = "ScriptTask";
                            else if ("TaskBusinessRule".equals(impl)) _cls = "BusinessRuleTask";
                            else if ("TaskManual".equals(impl)) _cls = "ManualTask";
                            else if (!"".equals(set)) {
                                _cls = "SubProcess";
                            }
                            //Offset tasks
                            el.put("x", el.getDouble("x")+(el.getDouble("w")/2));
                            el.put("y", el.getDouble("y")+(el.getDouble("h")/2));
                            el.remove(XPDLProcessor.XPDLEntities.IMPLEMENTATION);
                            el.remove(XPDLProcessor.XPDLAttributes.ACTIVITYSETID);
                        }
                    }
                } else if (XPDLProcessor.XPDLEntities.POOL.equals(cls)) {
                    el.remove("container");
                } else if (XPDLProcessor.XPDLEntities.DATASTOREREFERENCE.equals(cls)) {
                    JSONObject referred = elements.get(el.optString(XPDLProcessor.XPDLAttributes.DATASTOREREF,""));
                    if (referred != null) {
                        double x = el.optDouble("x",0)+30;
                        double y = el.optDouble("y",0)+26;
                        referred.put("x", x);
                        referred.put("y", y);
                    }
                }
                
                if (el.optString("container", "").equals("")) {
                    el.put("container", "Process:1");
                }
                if (!XPDLProcessor.XPDLEntities.TRANSITION.equals(cls)) {
                    el.put("labelSize", 12);
                    el.put("index", 0);
                }
                if (!XPDLProcessor.XPDLEntities.WORKFLOWPROCESS.equals(cls) && !XPDLProcessor.XPDLEntities.POOL.equals(cls) && !XPDLProcessor.XPDLEntities.LANE.equals(cls)) {
                    nodes.put(el);
                }
                
                el.put("class", _cls);
                if ("GroupArtifact".equals(_cls) || "AnnotationArtifact".equals(_cls)) {
                    //Offset artifact
                    el.put("x", el.getDouble("x")+(el.getDouble("w")/2));
                    el.put("y", el.getDouble("y")+(el.getDouble("h")/2));
                } else if ("DataObject".equals(_cls) || "DataInput".equals(_cls) || "DataOutput".equals(_cls)) {
                    //Offset dataobjects
                    el.put("x", el.getDouble("x")+20);
                    el.put("y", el.getDouble("y")+26);
                }
                
                el.remove("_class");
            }
            ret.put("nodes", nodes);
        }
        return ret;
    }
    
    /**
     * Determina el tipo de un evento.
     * @param type Tipo de evento segun el tag de XPDL
     * @param trigger Propiedad Trigger de XPDL
     * @param catchThrow Tipo de comportamiento del evento en XPDL.
     * @return Tipo de evento acorde a las definiciones de SWB Process.
     */
    private String getEventType(String type, String trigger, String catchThrow) {
        String ret = trigger;
        String posfix = type;
        if ("None".equals(ret)) ret = "";
        
        if ("Conditional".equals(trigger)) ret = "Rule";
        else if ("ParallelMultiple".equals(trigger)) ret = "Parallel";
        else if ("Multiple".equals(trigger)) ret = "Multiple";
        else if ("Escalation".equals(trigger)) ret = "Scalation";
        else if ("Cancel".equals(trigger)) ret = "Cancelation";
        else if ("Terminate".equals(trigger)) ret = "Termination";
        
        if (XPDLProcessor.XPDLEntities.STARTEVENT.equals(type)) {
            posfix = XPDLProcessor.XPDLEntities.STARTEVENT;
        } else if (XPDLProcessor.XPDLEntities.ENDEVENT.equals(type)) {
            posfix = XPDLProcessor.XPDLEntities.ENDEVENT;
        } else if (XPDLProcessor.XPDLEntities.INTERMEDIATEEVENT.equals(type)) {
            if (!"".equals(catchThrow)) {
                posfix = "IntermediateThrowEvent";
            } else {
                posfix = "IntermediateCatchEvent";
            }
        }
        return ret + posfix;
    }
    
    /**
     * Modifica el elemento proporcionado para representar en un evento de BPMN.
     * @param element Elemento en el parseo del archivo XPDL.
     * @param finalClass clase definitiva para el elemento.
     * @throws JSONException 
     */
//    private void generateEventJSON(JSONObject element, String finalClass) throws JSONException {
//        JSONObject ret = element;
//        String prefix = ret.optString(XPDLProcessor.XPDLAttributes.TRIGGER, "");
//        String catchThrow = ret.optString(XPDLProcessor.XPDLAttributes.CATCHTHROW, "");        
//        String cls = getEventType(finalClass, prefix, catchThrow);
//        
//        element.put("class",cls);
//        element.remove(XPDLProcessor.XPDLAttributes.CATCHTHROW);
//        element.remove(XPDLProcessor.XPDLAttributes.TRIGGER);
//        element.remove("w");
//        element.remove("h");
//    }
    
    /**
     * Crea un hashmap con los atributos de un tag en el parseo del archivo XPDL.
     * @param attributes Lista de atributos.
     * @return Mapa de atributos en llave, valor.
     */
    private HashMap<String, String> getAttributeMap(Attributes attributes) {
        HashMap<String, String> ret = new HashMap<String, String>();
        for (int i = 0; i < attributes.getLength(); i++) {
            ret.put(attributes.getLocalName(i), attributes.getValue(i));
        }
        return ret;
    }
    
    /**
     * Procesa un elemento durante el parseo del archivo XPDL.
     * @param uri URI del elemento
     * @param localName Nombre local del elemento.
     * @param qName Qualified name del elemento.
     * @param attributes Attributos del elemento.
     * @throws JSONException 
     */
    private void processElement(String uri, String localName, String qName, Attributes attributes) throws JSONException {
        HashMap<String, String> atts = getAttributeMap(attributes);

        if (XPDLProcessor.XPDLEntities.ACTIVITY.equals(localName)) {
            processor.processActivity(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.BLOCKACTIVITY.equals(localName)) {
            processor.processBlockActivity(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.LANE.equals(localName)) {
            processor.processLane(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.POOL.equals(localName)) {
            processor.processPool(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.NODEGRAPHICSINFO.equals(localName)) {
            processor.processNodeGraphicsInfo(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.COORDINATES.equals(localName)) {
            processor.processCoordinates(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.STARTEVENT.equals(localName) || XPDLProcessor.XPDLEntities.INTERMEDIATEEVENT.equals(localName) || XPDLProcessor.XPDLEntities.ENDEVENT.equals(localName)) {
            processor.processEvent(localName, XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.TRIGGERRESULTMESSAGE.equals(localName) || XPDLProcessor.XPDLEntities.TRIGGERRESULTSIGNAL.equals(localName)
                || XPDLProcessor.XPDLEntities.TRIGGERRESULTLINK.equals(localName)) {
            processor.processInterEventResult(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.ROUTE.equals(localName)) {
            processor.processGateway(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.DATASTOREREFERENCE.equals(localName) || XPDLProcessor.XPDLEntities.DATASTORE.equals(localName) || XPDLProcessor.XPDLEntities.DATAOBJECT.equals(localName)) {
            processor.processDataObject(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.DATAFIELD.equals(localName)) {
            processor.processDataField(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.TASKUSER.equals(localName) || XPDLProcessor.XPDLEntities.TASKSERVICE.equals(localName) 
                || XPDLProcessor.XPDLEntities.TASKRECEIVE.equals(localName) || XPDLProcessor.XPDLEntities.TASKSEND.equals(localName)
                || XPDLProcessor.XPDLEntities.TASKSCRIPT.equals(localName) || XPDLProcessor.XPDLEntities.TASKMANUAL.equals(localName)
                || XPDLProcessor.XPDLEntities.TASKBUSINESSRULE.equals(localName)) {
            processor.processTaskImplementation(localName, XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.LOOP.equals(localName)) {
            processor.processLoopType(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.ARTIFACT.equals(localName)) {
            processor.processArtifact(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.TRANSITION.equals(localName)) {
            processor.processTransition(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.CONDITION.equals(localName)) {
            processor.processConditionType(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.WORKFLOWPROCESS.equals(localName)) {
            processor.processWorkFlow(XMLElementNames, XMLElementObjects, atts);
        }
        if (XPDLProcessor.XPDLEntities.ACTIVITYSET.equals(localName)) {
            processor.processActivitySet(XMLElementNames, XMLElementObjects, atts);
        }
        //Put current tag on top of the stack
        XMLElementNames.push(localName);
    }
}