/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Element;
import java.util.Date;
import java.io.File;
import org.semanticwb.process.modeler.TransactionSubProcess;
import org.semanticwb.process.modeler.TimerStartEvent;
import org.semanticwb.process.modeler.RuleStartEvent;
import org.semanticwb.process.modeler.SignalStartEvent;
import org.semanticwb.process.modeler.MultipleStartEvent;

/**
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */

public class XPDLTransformer {
    def namespaceUri: String = "http://www.wfmc.org/2008/XPDL2.1";
    def xsi: String = "http://www.w3.org/2001/XMLSchema-instance";
    def xsiLocation = "http://www.wfmc.org/2008/XPDL2.1 http://www.wfmc.org/standards/docs/bpmnxpdl_31.xsd";
    def namespacePrefix: String = "xpdl";
    def toolId: String = "SemanticWebBuilder_Process_Modeler";
    def vendorName: String = "INFOTEC";
    def xpdlVersion: String = "2.1";
    def ORIENTATION_HORIZONTAL = "HORIZONTAL";
    def ORIENTATION_VERTICAL = "VERTICAL";
    var doc: Document;
    var partners: Element;
    var pools: Element;
    var messages: Element;
    var associations: Element;
    var artifacts: Element;
    var workflows: Element;
    var pkg:Element;


    postinit  {
        var dbfac = DocumentBuilderFactory.newInstance();
        var docBuilder = dbfac.newDocumentBuilder();
        doc = docBuilder.newDocument();
        doc.setXmlStandalone(false);
        doc.setXmlVersion("1.0");
        workflows = null;

        getPackageDefinition();
    }

    public function getXPDLDocument(modeler: Modeler) {//: Document {
        for (ele in modeler.contents where ele instanceof Pool) {
            addPoolDefinition(ele as Pool);
        }
        
        //return doc;
    }


    public function saveXPDL(file : File) {
        var transf = TransformerFactory.newInstance();
        var trans = transf.newTransformer();
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        trans.setOutputProperty(OutputKeys.METHOD, "xml");

        var result = new StreamResult(file);
        var source = new DOMSource(doc);
        trans.transform(source, result);
    }
    
    function getGraphicsInfos(ge: GraphicalElement): Element {
        var graphicInfos = doc.createElementNS(namespaceUri, "{namespacePrefix}:NodeGraphicsInfos");
        var graphicInfo = doc.createElementNS(namespaceUri, "{namespacePrefix}:NodeGraphicsInfo");
        var coords = doc.createElementNS(namespaceUri, "{namespacePrefix}:Coordinates");

        graphicInfo.appendChild(coords);
        graphicInfos.appendChild(graphicInfo);

        graphicInfo.setAttribute("ToolId", toolId);
        graphicInfo.setAttribute("IsVisible", "{ge.canView()}");
        graphicInfo.setAttribute("Width", "{ge.w}");
        graphicInfo.setAttribute("Height", "{ge.h}");

        if (ge.getGraphParent() instanceof Lane) {
            graphicInfo.setAttribute("LaneId", "{ge.getGraphParent().uri}");
        }

        coords.setAttribute("XCoordinate", "{ge.x}");
        coords.setAttribute("YCoordinate", "{ge.y}");
        return graphicInfos;
    }

    public function getPackageDefinition() {
        if (pkg == null) {
            pkg = doc.createElementNS(namespaceUri, "{namespacePrefix}:Package");
            doc.appendChild(pkg);
        }
        pools = doc.createElementNS(namespaceUri, "{namespacePrefix}:Pools");

        pkg.setAttribute("xmlns", namespaceUri);
        pkg.setAttribute("xmlns:xsi", xsi);
        pkg.setAttribute("xsi:location", xsiLocation);
        //TODO: Ver cómo se van a crear los IDs y nombres para los paquetes (pueden ser los grupos de procesos?)
        pkg.setAttribute("Id", "processID");
        pkg.setAttribute("Name", "processName");
        
        pkg.appendChild(getPackageHeader());
        pkg.appendChild(pools);
    }

    function getPackageHeader() : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:PackageHeader");
        var version = doc.createElementNS(namespaceUri, "{namespacePrefix}:XPDLVersion");
        var vendor = doc.createElementNS(namespaceUri, "{namespacePrefix}:Vendor");
        var created = doc.createElementNS(namespaceUri, "{namespacePrefix}:Created");

        version.setTextContent(xpdlVersion);
        vendor.setTextContent(vendorName);
        created.setTextContent(new Date().toString());

        ret.appendChild(version);
        ret.appendChild(vendor);
        ret.appendChild(created);
        return ret;
    }

    function addPoolDefinition(pool: Pool) {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:Pool");
        var lanes = doc.createElementNS(namespaceUri, "{namespacePrefix}:Lanes");

        ret.setAttribute("BoundaryVisible", "true");
        ret.setAttribute("Id", pool.getURI());
        ret.setAttribute("MainPool", "false");
        ret.setAttribute("Name", pool.getTitle());
        ret.setAttribute("Orientation", ORIENTATION_HORIZONTAL);
        ret.setAttribute("Process", pool.getURI());

        ret.appendChild(getGraphicsInfos(pool));
        if (not pool.lanes.isEmpty()) {
            for (lane in pool.lanes) {
                lanes.appendChild(getLaneDefinition(lane));
            }
            ret.appendChild(lanes);
        }
        pools.appendChild(ret);
        addWorkFlowDefinition(pool);
    }

    function getActivitySet(subprocess: SubProcess): Element {
        return null;
    }

    function getActivityDefinition(ge: GraphicalElement) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:Activity");
        ret.setAttribute("StartQuantity", "1");
        ret.setAttribute("CompletionQuantity", "1");
        ret.setAttribute("Id", ge.getURI());
        ret.setAttribute("Name", ge.getTitle());
        ret.setAttribute("IsForCompensation", "{ge.isForCompensation}");

        if (ge instanceof TransactionSubProcess) {
            ret.setAttribute("IsATransaction", "true");
        }

        if (ge.isLoop) {
            var loop = doc.createElementNS(namespaceUri, "{namespacePrefix}:Loop");
            var standard = doc.createElementNS(namespaceUri, "{namespacePrefix}:LoopStandard");

            standard.setAttribute("TestTime", "After");
            loop.setAttribute("LoopType", "Standard");
            loop.appendChild(standard);
            ret.appendChild(loop);
        }

        if (ge.isSequentialMultiInstance or ge.isMultiInstance) {
            var loop = doc.createElementNS(namespaceUri, "{namespacePrefix}:Loop");
            var multi = doc.createElementNS(namespaceUri, "{namespacePrefix}:LoopMultiInstance");

            multi.setAttribute("MI_FlowCondition", "All");
            if (ge.isSequentialMultiInstance) {
                multi.setAttribute("MI_Ordering", "Sequential");
            } else if (ge.isMultiInstance) {
                multi.setAttribute("MI_Ordering", "Parallel");
            }
            loop.setAttribute("LoopType", "MultiInstance");
            loop.appendChild(multi);
            ret.appendChild(loop);
        }
        
        if (ge instanceof Event) ret.appendChild(getEventDefinition(ge as Event));
        if (ge instanceof Task) ret.appendChild(getTaskDefinition(ge as Task));

        ret.appendChild(getGraphicsInfos(ge));
        return ret;
    }

    function getTaskDefinition(task: Task) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:Implementation");
        var tsk = doc.createElementNS(namespaceUri, "{namespacePrefix}:Task");
        
        if (task instanceof UserTask) {
            var tskuser = doc.createElementNS(namespaceUri, "{namespacePrefix}:TaskUser");
            tskuser.setAttribute("Implementation", "Unspecified");
            tsk.appendChild(tskuser);
            ret.appendChild(tsk);
        } else if (task instanceof ServiceTask) {
            var tskserv = doc.createElementNS(namespaceUri, "{namespacePrefix}:TaskService");
            tskserv.setAttribute("Implementation", "WebService");
            tsk.appendChild(tskserv);
            ret.appendChild(tsk);
        } else if (task instanceof ScriptTask) {
            var tskserv = doc.createElementNS(namespaceUri, "{namespacePrefix}:TaskScript");
            tskserv.appendChild(doc.createElementNS(namespaceUri, "{namespacePrefix}:Script"));
            tsk.appendChild(tskserv);
            ret.appendChild(tsk);
        } else if (task instanceof ManualTask) {
            var tskuser = doc.createElementNS(namespaceUri, "{namespacePrefix}:TaskManual");
            tsk.appendChild(tskuser);
            ret.appendChild(tsk);
        } else if (task instanceof SendTask) {
            var tskserv = doc.createElementNS(namespaceUri, "{namespacePrefix}:TaskSend");
            tskserv.setAttribute("Implementation", "WebService");
            tsk.appendChild(tskserv);
            ret.appendChild(tsk);
        } else if (task instanceof ReceiveTask) {
            var tskserv = doc.createElementNS(namespaceUri, "{namespacePrefix}:TaskReceive");
            tskserv.setAttribute("Implementation", "WebService");
            tsk.appendChild(tskserv);
            ret.appendChild(tsk);
        } else {
            ret.appendChild(doc.createElementNS(namespaceUri, "{namespacePrefix}:No"));
        }

        return ret;
    }

    public function addWorkFlowDefinition(pool: Pool) {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:WorkflowProcess");
        var header = doc.createElementNS(namespaceUri, "{namespacePrefix}:ProcessHeader");
        var created = doc.createElementNS(namespaceUri, "{namespacePrefix}:Created");
        var activitySets = doc.createElementNS(namespaceUri, "{namespacePrefix}:ActivitySet");
        var activities = doc.createElementNS(namespaceUri, "{namespacePrefix}:Activities");

        created.setTextContent(new Date().toString());

        ret.setAttribute("Id", pool.getURI());
        ret.setAttribute("Name", pool.getTitle());
        header.appendChild(created);
        ret.appendChild(header);

        if (workflows == null) {
            workflows = doc.createElementNS(namespaceUri, "{namespacePrefix}:WorkflowProcesses");
            pkg.appendChild(workflows);
        }

        var hasActivities = false;
        var hasActivitySets = false;
        if (not pool.lanes.isEmpty()) {
            for (lane in pool.lanes) {
                for (child in lane.getgraphChilds()) {
                    if (hasActivities == false) {
                        hasActivities = true;
                    }
                    if (child instanceof SubProcess) {
                        if (hasActivitySets == false) {
                            hasActivitySets = true;
                        }
                    } else {
                        activities.appendChild(getActivityDefinition(child));
                    }
                }
            }
        } else {
            for (child in pool.getgraphChilds()) {
                if (hasActivities == false) {
                    hasActivities = true;
                }
                if (child instanceof SubProcess) {
                    if (hasActivitySets == false) {
                        hasActivitySets = true;
                    }
                } else {
                    activities.appendChild(getActivityDefinition(child));
                }
            }
        }

        if (hasActivities) {
            ret.appendChild(activities);
        }
        if (hasActivitySets) {
            ret.appendChild(activitySets);
        }
        workflows.appendChild(ret);
    }

    function getLaneDefinition(lane: Lane) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:Lane");

        ret.setAttribute("Id", lane.getURI());
        ret.setAttribute("Name", lane.getTitle());
        if (lane.getGraphParent() instanceof Lane) {
            ret.setAttribute("ParentLane", lane.getGraphParent().getURI());
        }
        ret.setAttribute("ParentPool", lane.getPool().getURI());
        ret.appendChild(getGraphicsInfos(lane));
        
        return ret;
    }

    function getEventDefinition(evt: Event) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:Event");
        if (evt instanceof StartEvent) {
            ret.appendChild(getStartEventDefinition(evt as StartEvent));
        }
        return ret;
    }

    function getStartEventDefinition(evt: StartEvent) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:StartEvent");
        var trig = "None";
        
        if (evt instanceof TimerStartEvent) {
            trig = "Timer";
            var resMessage = doc.createElementNS(namespaceUri, "{namespacePrefix}:TriggerTimer");
            resMessage.appendChild(doc.createElementNS(namespaceUri, "{namespacePrefix}:TimeDate"));
            ret.appendChild(resMessage);
        } else if (evt instanceof MessageStartEvent) {
            var resMessage = doc.createElementNS(namespaceUri, "{namespacePrefix}:TriggerResultMessage");
            resMessage.setAttribute("CatchThrow", "CATCH");
            trig = "Message";
            ret.setAttribute("Implementation", "Unspecified");
            ret.appendChild(resMessage);
        } else if (evt instanceof RuleStartEvent) {
            trig = "Conditional"
        } else if (evt instanceof SignalStartEvent) {
            var resMessage = doc.createElementNS(namespaceUri, "{namespacePrefix}:TriggerResultSignal");
            resMessage.setAttribute("CatchThrow", "CATCH");
            ret.appendChild(resMessage);
            trig = "Signal";
        } else if (evt instanceof MultipleStartEvent) {
            var resMessage = doc.createElementNS(namespaceUri, "{namespacePrefix}:TriggerMultiple");
            var rMessage = doc.createElementNS(namespaceUri, "{namespacePrefix}:TriggerResultMessage");
            var tMessage = doc.createElementNS(namespaceUri, "{namespacePrefix}:TriggerTimer");

            tMessage.appendChild(doc.createElementNS(namespaceUri, "{namespacePrefix}:TimeDate"));
            rMessage.setAttribute("CatchThrow", "CATCH");

            resMessage.appendChild(rMessage);
            resMessage.appendChild(tMessage);
            ret.appendChild(resMessage);
            trig = "Multiple";
        }
        ret.setAttribute("Trigger", trig);
        return ret;
    }
}
