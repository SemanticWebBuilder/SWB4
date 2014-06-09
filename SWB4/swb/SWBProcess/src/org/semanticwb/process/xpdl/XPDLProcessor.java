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

import java.util.HashMap;
import java.util.Stack;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Procesador de XPDL a JSON.
 * @author Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
 */
public class XPDLProcessor {
    String strings = "URI|TITLE|DESCRIPTION|CLASS|PARENT|CONNECTIONPOINTS|START|END|CONTAINER|EXCLUSIVETYPE|GATEWAYTYPE|DATASTOREREF|ACTIVITYSETID|CATCHTHROW|SOURCE|TARGET";
    String booleans = "ISFORCOMPENSATION|ISINTERRUPTING|ISLOOP|ISMULTIINSTANCE|ISSEQUENTIALMULTIINSTANCE|PARALLELEVENTBASED|INSTANTIATE|ISCOLLECTION";
    String numbers = "W|H|X|Y|LABELSIZE";
    
    /**
     * Constructor.
     */
    public XPDLProcessor () {}
    
    /**
     * Procesa un bloque XPDL correspondiente a una actividad.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processActivity(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.ACTIVITIES)) {
            JSONObject obj = new JSONObject();
            JSONObject container = elements.peek();
            
            atts.put("class",XPDLEntities.ACTIVITY);
            String c = container.optString("class", "");
            
            if (c.equals(XPDLEntities.ACTIVITYSET)) {
                atts.put("container",container.getString("uri"));
            }
            //System.out.println(elements.peek().toString(2));
            
            setAttributes(obj, atts);
            elements.push(obj);
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a una BlockActivity.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processBlockActivity(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.ACTIVITY)) {
            JSONObject obj = elements.pop();
            
            setAttributes(obj, atts);
            //System.out.println(obj.toString());
            elements.push(obj);
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un ActivitySet (SubProceso).
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processActivitySet(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.ACTIVITYSETS)) {
            JSONObject obj = new JSONObject();
            JSONObject container = elements.peek();
            
            atts.put("class",XPDLEntities.ACTIVITYSET);
            atts.put("_class","SubProcess");
            String c = container.optString("class", "");
            
            if (c.equals(XPDLEntities.ACTIVITYSET)) {
                atts.put("container",container.getString("uri"));
            }
            
            setAttributes(obj, atts);
            elements.push(obj);
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un Lane.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processLane(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.LANES)) {
            JSONObject obj = new JSONObject();
            atts.put("class",XPDLEntities.LANE);
            
            setAttributes(obj, atts);
            elements.push(obj);
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un Pool.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processPool(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.POOLS)) {
            JSONObject obj = new JSONObject();
            atts.put("class",XPDLEntities.POOL);
            
            setAttributes(obj, atts);
            elements.push(obj);
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un Artifact.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processArtifact(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.ARTIFACTS)) {
            JSONObject obj = new JSONObject();
            String cls = atts.get(XPDLAttributes.ARTIFACTTYPE);
            
            atts.put("class",XPDLEntities.ARTIFACT);
            if (cls != null) {
                obj.put("_class",cls+XPDLEntities.ARTIFACT);
            }
            
            setAttributes(obj, atts);
            elements.push(obj);
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a una Transition (Flujo).
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processTransition(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.TRANSITIONS)) {
            JSONObject obj = new JSONObject();
            atts.put("class",XPDLEntities.TRANSITION);
            setAttributes(obj, atts);
            elements.push(obj);
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un ConditionType.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processConditionType(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.TRANSITION)) {
            if (!elements.isEmpty()) {
                JSONObject obj = elements.pop();
                
                String cls = atts.get("class");
                if (cls == null) cls = "SequenceFlow";
                
                if ("CONDITION".equalsIgnoreCase(cls)) {
                    cls = "ConditionalFlow";
                } else if ("OTHERWISE".equalsIgnoreCase(cls)) {
                    cls = "DefaultFlow";
                }
                
                obj.put("_class", cls);
                elements.push(obj);
            }
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un GraphicsInfo (coordenadas).
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processNodeGraphicsInfo(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.NODEGRAPHICSINFOS)) {
            if (!elements.isEmpty()) {
                JSONObject obj = elements.pop();
                setAttributes(obj, atts);
                elements.push(obj);
            }
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un Event.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processEvent(String localName, Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.EVENT)) {
            if (!elements.isEmpty()) {
                JSONObject obj = elements.pop();
                String cls = obj.optString("class","");
                
                if (XPDLEntities.ACTIVITY.equals(cls)) {
                    obj.put("_class", localName);
                    if (XPDLEntities.ENDEVENT.equals(localName)) {
                        obj.put(XPDLAttributes.TRIGGER, atts.get(XPDLAttributes.RESULT));
                    } else {
                        obj.put(XPDLAttributes.TRIGGER, atts.get(XPDLAttributes.TRIGGER));
                    }
                }
                setAttributes(obj, atts);
                elements.push(obj);
            }
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un EventResult.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processInterEventResult(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.INTERMEDIATEEVENT)) {
            if (!elements.isEmpty()) {
                JSONObject obj = elements.pop();
                
                setAttributes(obj, atts);
                //System.out.println(obj.toString());
                elements.push(obj);
            }
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un Gateway.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processGateway(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.ACTIVITY)) {
            if (!elements.isEmpty()) {
                JSONObject obj = elements.pop();
                String cls = obj.optString("class","");
                
                if (XPDLEntities.ACTIVITY.equals(cls)) {
                    obj.put("_class", XPDLEntities.ROUTE);
                    setAttributes(obj, atts);
                }
                elements.push(obj);
            }
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un DataField.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processDataField(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.DATAOBJECT)) {
            if (!elements.isEmpty()) {
                JSONObject obj = elements.pop();
                setAttributes(obj, atts);
                elements.push(obj);
            }
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un DataObject.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processDataObject(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        JSONObject obj = new JSONObject();
        if (tags.peek().equals(XPDLEntities.DATASTOREREFERENCES)) {
            atts.put("class",XPDLEntities.DATASTOREREFERENCE);
        } else if (tags.peek().equals(XPDLEntities.DATAOBJECTS)) {
            atts.put("class",XPDLEntities.DATAOBJECT);
        } else if (tags.peek().equals(XPDLEntities.DATASTORES)) {
            atts.put("class",XPDLEntities.DATASTORE);
        }
        
        setAttributes(obj, atts);
        elements.push(obj);
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a una TaskImplementation.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processTaskImplementation(String localName, Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.TASK)) {
            JSONObject obj = elements.pop();
            obj.put(XPDLEntities.IMPLEMENTATION, localName);
            elements.push(obj);
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un LoopType.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processLoopType(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.ACTIVITY)) {
            JSONObject obj = elements.pop();
            String loopType = atts.get(XPDLAttributes.LOOPTYPE);
            if (loopType != null) {
                obj.put("loopType",loopType);
            }
            elements.push(obj);
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un Association.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processAssociation(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.ASSOCIATIONS)) {
            JSONObject obj = new JSONObject();
            
            obj.put("class", XPDLEntities.ASSOCIATION);
            setAttributes(obj, atts);
            elements.push(obj);
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un ProcessWorkFlow (Proceso).
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processWorkFlow(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.WORKFLOWPROCESSES)) {
            JSONObject obj = new JSONObject();
            obj.put("class", XPDLEntities.WORKFLOWPROCESS);
            obj.put("_class", "Process");
            setAttributes(obj, atts);
            elements.push(obj);
        }
    }
    
    /**
     * Procesa un bloque XPDL correspondiente a un Coordinates.
     * @param tags Pila de tags en el parser.
     * @param elements Lista de elementos generados en el parser.
     * @param atts Atributos del elemento.
     * @throws JSONException 
     */
    public void processCoordinates(Stack<String> tags, Stack<JSONObject> elements, HashMap<String,String> atts) throws JSONException {
        if (tags.peek().equals(XPDLEntities.NODEGRAPHICSINFO) || tags.peek().equals(XPDLEntities.CONNECTORGRAPHICSINFO)) {
            //Pop parent element from object stack (must be root element)
            if (!elements.isEmpty()) {
                JSONObject obj = elements.pop();
                String cls = obj.optString("_class","");
                String points = obj.optString("connectionPoints","");
                            
                if ("SequenceFlow".equals(cls) || "ConditionalFlow".equals(cls) || "DefaultFlow".equals(cls) || XPDLEntities.ASSOCIATION.equals(cls)) {
                    points += atts.get(XPDLAttributes.XCOORDINATE) + "," + atts.get(XPDLAttributes.YCOORDINATE) + "|";
                    atts.remove(XPDLAttributes.XCOORDINATE);
                    atts.remove(XPDLAttributes.YCOORDINATE);
                    atts.put("connectionPoints", points);
                }
                
                setAttributes(obj, atts);
                elements.push(obj);
            }
        }
    }
  
    /**
     * Establece los atributos proporcionados a on objeto JSON.
     * @param obj Objeto JSON
     * @param attributes Lista de atributos.
     * @throws JSONException 
     */
    private void setAttributes(JSONObject obj, HashMap<String, String> attributes) throws JSONException {
        for (String key : attributes.keySet()) {
            String _key = mapKeyAttribute(key);
            String val = attributes.get(key);
            if (strings.contains(_key.toUpperCase())) {
                obj.put(_key, val);
            } else if (booleans.contains(_key.toUpperCase())) {
                obj.put(_key, Boolean.valueOf(val));
            } else if (numbers.contains(_key.toUpperCase())) {
                obj.put(_key, Math.round(Float.parseFloat(val)));
            }
        }
    }
    
    /**
     * Mapea atributos específicos de XPDL hacia los atributos reconocidos por SWB Process.
     * @param attName Nombre del atributo.
     * @return Nombre del atributo mapeado.
     */
    private String mapKeyAttribute(String attName) {
        String ret = attName;
        if (XPDLAttributes.PARENTPOOL.equals(attName)) ret = "parent";
        //if (XPDLAttributes.PROCESS.equals(attName)) ret = "container";
        if (XPDLAttributes.ID.equals(attName)) ret = "uri";
        if (XPDLAttributes.XCOORDINATE.equals(attName)) ret = "x";
        if (XPDLAttributes.YCOORDINATE.equals(attName)) ret = "y";
        if (XPDLAttributes.FROM.equals(attName)) ret = "start";
        if (XPDLAttributes.TO.equals(attName)) ret = "end";
        if (XPDLEntities.DESCRIPTION.equals(attName)) ret = "description";
        if (XPDLAttributes.WIDTH.equals(attName)) ret = "w";
        if (XPDLAttributes.HEIGHT.equals(attName)) ret = "h";
        if (XPDLAttributes.NAME.equals(attName)) ret = "title";
        if (XPDLAttributes.ISARRAY.equals(attName)) ret = "isCollection";
        if (XPDLAttributes.TARGET.equals(attName)) ret = "parent";
        if (XPDLAttributes.TEXTANNOTATION.equals(attName)) ret = "title";

        return ret;
    }
    
    /**
     * Lista de entidades de XPDL.
     */
    public static class XPDLEntities {
        public static final String PACKAGE = "Package";
        public static final String PACKAGEHEADER = "PackageHeader";
        public static final String REDEFINABLEHEADER = "RedefinableHeader";
        public static final String DATASTOREREFERENCES = "DataStoreReferences";
        public static final String DATASTOREREFERENCE = "DataStoreReference";
        public static final String DATASTORES = "DataStores";
        public static final String DATASTORE = "DataStore";
        public static final String DATAOBJECTS = "DataObjects";
        public static final String DATAOBJECT = "DataObject";
        public static final String POOLS = "Pools";
        public static final String POOL = "Pool";
        public static final String LANES = "Lanes";
        public static final String LANE = "Lane";
        public static final String ASSOCIATIONS = "Associations";
        public static final String ASSOCIATION = "Association";
        public static final String ARTIFACTS = "Artifacts";
        public static final String ARTIFACT = "Artifact";
        public static final String WORKFLOWPROCESSES = "WorkflowProcesses";
        public static final String WORKFLOWPROCESS = "WorkflowProcess";
        public static final String PROCESSHEADER = "ProcessHeader";
        public static final String DATAINPUTOUTPUTS = "DataInputOutputs";
        public static final String DATAOUTPUT = "DataOutput";
        public static final String DATAINPUT = "DataInput";
        public static final String ACTIVITIES = "Activities";
        public static final String ACTIVITY = "Activity";
        public static final String BLOCKACTIVITY = "BlockActivity";
        public static final String ACTIVITYSET = "ActivitySet";
        public static final String ACTIVITYSETS = "ActivitySets";
        public static final String EVENT = "Event";
        public static final String INTERMEDIATEEVENT = "IntermediateEvent";
        public static final String IMPLEMENTATION = "Implementation";
        public static final String TASK = "Task";
        public static final String TASKUSER = "TaskUser";
        public static final String TASKMANUAL = "TaskManual";
        public static final String TASKRECEIVE = "TaskReceive";
        public static final String TASKSERVICE = "TaskService";
        public static final String TASKBUSINESSRULE = "TaskBusinessRule";
        public static final String TASKSEND = "TaskSend";
        public static final String TASKSCRIPT = "TaskScript";
        public static final String LOOP = "Loop";
        public static final String ROUTE = "Route";
        public static final String TRANSITIONS = "Transitions";
        public static final String TRANSITION = "Transition";
        public static final String CONDITION = "Condition";
        public static final String XPDLVERSION = "XPDLVersion";
        public static final String VENDOR = "Vendor";
        public static final String AUTHOR = "Author";
        public static final String VERSION = "Version";
        public static final String COUNTRYKEY = "CountryKey";
        public static final String CREATED = "Created";
        public static final String MODIFICATIONDATE = "ModificationDate";
        public static final String DOCUMENTATION = "Documentation";
        public static final String EXTERNALPACKAGES = "ExternalPackages";
        public static final String STARTEVENT = "StartEvent";
        public static final String ENDEVENT = "EndEvent";

        public static final String NODEGRAPHICSINFOS = "NodeGraphicsInfos";
        public static final String NODEGRAPHICSINFO = "NodeGraphicsInfo";
        public static final String CONNECTORGRAPHICSINFOS = "ConnectorGraphicsInfos";
        public static final String CONNECTORGRAPHICSINFO = "ConnectorGraphicsInfo";
        public static final String COORDINATES = "Coordinates";
        public static final String DESCRIPTION = "Description";
        public static final String GATEWAY = "Gateway";
        public static final String DATAFIELD = "DataField";
        public static final String TRIGGERRESULTMESSAGE = "TriggerResultMessage";
        public static final String TRIGGERRESULTSIGNAL = "TriggerResultSignal";
        public static final String TRIGGERRESULTLINK = "TriggerResultLink";
    }
    
    /**
     * Lista de atributos de XPDL.
     */
    public static class XPDLAttributes {
        public static final String ID = "Id";
        public static final String NAME = "Name";
        public static final String PROCESS = "Process";
        public static final String BOUNDARYVISIBLE = "BoundaryVisible";
        public static final String PARENTPOOL = "ParentPool";
        public static final String WIDTH = "Width";
        public static final String HEIGHT = "Height";
        public static final String XCOORDINATE = "XCoordinate";
        public static final String YCOORDINATE = "YCoordinate";
        public static final String ISCOLLECTION = "IsCollection";
        public static final String ARTIFACTID = "ArtifactId";
        public static final String FROM = "From";
        public static final String TO = "To";
        public static final String TRIGGER = "Trigger";
        public static final String RESULT = "Result";
        public static final String DATASTOREREF = "DataStoreRef";
        public static final String ISARRAY = "IsArray";
        public static final String TARGET = "Target";
        public static final String LOOPTYPE = "LoopType";
        public static final String ARTIFACTTYPE = "ArtifactType";
        public static final String TEXTANNOTATION = "TextAnnotation";
        public static final String TYPE = "Type";
        public static final String CATCHTHROW = "CatchThrow";
        public static final String EXCLUSIVETYPE = "ExclusiveType";
        public static final String GATEWAYTYPE = "GatewayType";
        public static final String INSTANTIATE = "Instantiate";
        public static final String ACTIVITYSETID = "ActivitySetId";
        public static final String SOURCE = "Source";
    }
}
