/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/***************************Elementos genéricos**************************/
    var _GraphicalElement = function(obj) {
        var _this = obj;
        _this.superClasses = new Array();
        
        _this.addSuperClass = function(clsName) {
            _this.superClasses.push(clsName);
        }
        
        _this.isSubClass = function(clsName) {
            var ret = false;
            for (var i = 0; i < _this.superClasses.length; i++) {
                if(_this.superClasses[i] == clsName) {
                    ret = true;
                    break;
                }
            }
            return ret;
        }
        
        _this.canStartLink=function(link) {
            return true;
        }
        
        _this.canEndLink=function(link) {
            if(link.fromObject!=_this) {
                return true;
            } else {
                return false;
            }
        }
        
        _this.canAddToDiagram=function() {
            return true;
        }
        
        _this.canAttach=function(parent) {
            return false;
        }
        
        return _this;
    };
    
    var _FlowNode = function (obj) {
        var _this = new _GraphicalElement(obj);
        var fCanEnd = _this.canEndLink;
        _this.addSuperClass("GraphicalElement");
        _this.canAttach = function(parent) {
            var ret = false;
            if (parent.type=="Pool" || parent.type=="Lane") {
                ret = true;
            }
            return ret;
        }
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            
            if (link.type=="SequenceFlow") {
                //TODO: Implementar
            }
            
            if (link.type=="AssociationFlow") {
                if (!(link.fromObject.type=="Artifact" || link.fromObject.type=="DataObject") && !(link.fromObject.type=="CompensationIntermediateCatchEvent" && link.fromObject.parent.isSubClass("Activity"))) {
                    ret = false;
                }
            }
            
            if (link.type=="MessageFlow") {
                //TODO:Implementar
            }
            return ret;
        }
        return _this;
    }
    
    /***************************Eventos iniciales****************************/
    var _Event = function (obj) {
        var _this = new _FlowNode(obj);
        _this.addSuperClass("FlowNode");
        _this.canStartLink=function(link) {
            if (link.type=="ConditionalFlow") {
                return false;
            }
            return true;
        }
        return _this;
    };
    
    var _CatchEvent = function(obj) {
        var _this = new _Event(obj);
        _this.addSuperClass("Event");
        
        return _this;
    };
    
    var _ThrowEvent = function(obj) {
        var _this = new _Event(obj);
        _this.addSuperClass("Event");
        var fCanEnd = _this.canEndLink;
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.fromObject.type=="EventBasedGateway") {
                ret = false;
            }
            return ret;
        }
        return _this;
    };
    
    var _StartEvent = function(obj) {
        var _this = new _CatchEvent(obj);
        _this.addSuperClass("CatchEvent");
        var fCanEnd = _this.canEndLink;
        var fCanAdd = _this.canAddToDiagram;
        
        _this.canEndLink=function(link) {
            var ret = fCanEnd(link);
            if (link.type=="MessageFlow") {
                ret = false;
            }
            return ret;
        }
        
        _this.canAddToDiagram=function() {
            var ret=fCanAdd();
            
            //TODO:Implementar
            return ret;
        }
        
        return _this;
    };
    
    var _MessageStartEvent = function (obj) {
        var _this = new _StartEvent(obj);
        _this.addSuperClass("StartEvent");
        var fCanEnd = _this.canEndLink;
        
        _this.canEndLink=function(link) {
            var ret = fCanEnd(link);
            if (_this.inConnections.length==0) {
                ret = true;
            }
            return ret;
        }
        
        _this.canAddToDiagram=function() {
            var ret = true;
            //TODO:Implementar
            return ret;
        }
        
        return _this;
    };
    
    var _TimerStartEvent = function(obj) {
        var _this = new _StartEvent(obj);
        _this.addSuperClass("StartEvent");
        _this.canAddToDiagram = function () {
            var ret = true;
            //TODO: Implementar
            return ret;
        }
    };
    
    var _RuleStartEvent = function(obj) {
        var _this = new _StartEvent(obj);
        _this.addSuperClass("StartEvent");
        _this.canAddToDiagram = function () {
            var ret = true;
            //TODO: Implementar
            return ret;
        }
        
        return _this;
    };
    
    var _SignalStartEvent = function(obj) {
        var _this = new _StartEvent(obj);
        _this.addSuperClass("StartEvent");
        _this.canAddToDiagram = function () {
            var ret = true;
            //TODO: Implementar
            return ret;
        }
        
        return _this;
    };
    
    var _MultipleStartEvent = function (obj) {
        var _this = new _StartEvent(obj);
        _this.addSuperClass("StartEvent");
        _this.canAddToDiagram = function () {
            var ret = true;
            //TODO: Implementar
            return ret;
        }
        
        return _this;
    };
    
    var _ParallelStartEvent = function(obj) {
        var _this = new _StartEvent(obj);
        _this.addSuperClass("StartEvent");
        _this.canAddToDiagram = function () {
            var ret = true;
            //TODO: Implementar
            return ret;
        }
        
        return _this;
    };
    
    var _ScalationStartEvent = function(obj) {
        var _this = new _StartEvent(obj);
        _this.addSuperClass("StartEvent");
        _this.canAddToDiagram = function () {
            var ret = true;
            //TODO: Implementar
            return ret;
        }
        
        return _this;
    };
    
    var _ErrorStartEvent = function(obj) {
        var _this = new _StartEvent(obj);
        _this.addSuperClass("StartEvent");
        _this.canAddToDiagram = function () {
            var ret = true;
            //TODO: Implementar
            return ret;
        }
        
        return _this;
    };
    
    var _CompensationStartEvent = function(obj) {
        var _this = new _StartEvent(obj);
        _this.addSuperClass("StartEvent");
        _this.canAddToDiagram = function () {
            var ret = true;
            //TODO: Implementar
            return ret;
        }
        
        return _this;
    };
    
    /***************************Eventos intermedios**************************/
    var _IntermediateCatchEvent = function (obj) {
        var _this = new _CatchEvent(obj);
        _this.addSuperClass("CatchEvent");
        var fCanAttach = _this.canAttach;
        var fCanEnd = _this.canEndLink;
        var fCanStart = _this.canStartLink;
        
        _this.canAttach = function(parent) {
            var ret = fCanAttach(parent);
            if (ret || parent.isSubClass("Activity")) {
                ret = true;
            }
            
            if (parent.isSubClass("CallActivity")) {
                ret = false;
            }
            return ret;
        }
        
        _this.canEndLink = function (link) {
            var ret = fCanEnd(link);
            var c = 0;
            
            for (var i = 0; i < _this.inConnections.length; i++) {
                if (_this.inConnections[i].type=="SequenceFlow") {
                    c++;
                }
            }
            
            if (link.type=="SequenceFlow") {
                if (_this.parent.isSubClass("Activity")) {
                    ret = false;
                } else if (c != 0) {
                    ret = false;
                }
            }
            return ret;
        }
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            var c = 0;
            
            for (var i = 0; i < _this.outConnections.length; i++) {
                if (_this.inConnections[i].type=="SequenceFlow") {
                    c++;
                }
            }
            
            if (link.type=="SequenceFlow" && c != 0) {
                ret = false;
            }
            
            return ret;
        }
        
        return _this;
    };
    
    var _MessageIntermediateCatchEvent = function(obj) {
        var _this = new _IntermediateCatchEvent(obj);
        var fCanEnd = _this.canEndLink;
        var fCanStart = _this.canStartLink;
        
        _this.addSuperClass("IntermediateCatchEvent");
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            var c = 0;
            
            for (var i = 0; i < _this.inConnections.length; i++) {
                if (_this.inConnections[i].type=="MessageFlow") {
                    c++;
                }
            }
            
            if (link.type=="MessageFlow" && c != 0) {
                ret = false;
            }
            
            if (link.type=="SequenceFlow" && link.fromObject.type=="ExclusiveIntermediateEventGateway") {
                for (i = 0; i < _this.outConnections.length; i++) {
                    if (_this.inConnections[i].type=="SequenceFlow" && _this.inConnections[i].toObject.type=="ReceiveTask") {
                        ret = false;
                        break;
                    }
                }
            }
            
            return ret;
        }
        
        _this.canStartLink = function (link) {
            var ret = fCanStart(link);
            if (link.type=="MessageFlow") {
                ret = false;
            }
            return ret;
        }
        
        return _this;
    };
    
    var _ErrorIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        _this.addSuperClass("IntermediateCatchEvent");
        
        _this.canEndLink = function (link) {
            return false;
        }
        
        return _this;
    };
    
    var _CancelationIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        _this.addSuperClass("IntermediateCatchEvent");
        
        _this.canAttach = function (parent) {
            var ret = false;
            if(parent.type=="Pool" || parent.type=="Lane" || parent.type=="TransactionSubProcess") {
                ret = true;
            } else {
                ret = false;
            }
            
            return ret;
        }
        
        _this.canEndLink = function (link) {
            return false;
        }
        
        return _this;
    };
    
    var _CompensationIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        var fCanStart = _this.canStartLink;
        _this.addSuperClass("IntermediateCatchEvent");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (_this.parent.isSubClass("Activity")) {
                if (!link.type=="DirectionalAssociation") {
                    ret = false;
                }
            }
            return ret;
        }
        
        return _this;
    };
    
    var _LinkIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        var fCanStart = _this.canStartLink;
        var fCanAttach = _this.canAttach;
        _this.addSuperClass("IntermediateCatchEvent");
        
        _this.canEndLink = function(link) {
            return false;
        }
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.type=="MessageFlow") {
                ret = false;
            }
            return ret;
        }
        
        _this.canAttach = function (parent) {
            var ret = fCanAttach(parent);
            if (parent.isSubClass("Activity")) {
                ret = false;
            }
            return ret;
        }
        return _this;
    };
    
    var _SignalIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        _this.addSuperClass("IntermediateCatchEvent");
        
        _this.canAttach = function(parent) {
            var ret = false;
            if (parent.isSubClass("Activity") || parent.type=="Pool" || parent.type=="Lane") {
                ret = true;
            }
            return ret;
        }
        
        return _this;
    };
    
    var _MultipleIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        _this.addSuperClass("IntermediateCatchEvent");
        return _this;
    };
    
    var _ScalationIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        var fCanEnd = _this.canEndLink;
        
        _this.addSuperClass("IntermediateCatchEvent");
        
        _this.canAttach = function(parent) {
            var ret = false;
            if (parent.isSubClass("Activity") || parent.type=="Pool" || parent.type=="Lane") {
                ret = true;
            }
            return ret;
        }
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.type=="SequenceFlow" && link.fromObject.type=="EventBasedGateway") {
                ret = false;
            }
            return ret;
        }
        
        return _this;
    };
    
    var _ParallelIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        var fCanAttach = _this.canAttach;
        _this.addSuperClass("IntermediateCatchEvent");
        
        _this.canAttach = function (parent) {
            var ret = fCanAttach(parent);
            if (parent.isSubClass("Activity")) {
                ret = false;
            }
            return ret;
        }
        return _this;
    };
    
    var _RuleIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        _this.addSuperClass("IntermediateCatchEvent");
        
        _this.canAttach = function (parent) {
            var ret = false;
            if (parent.isSubClass("Activity") || parent.type=="Pool" || parent.type=="Lane") {
                ret = true;
            }
            return ret;
        }
        return _this;
    };
    
    var _MessageIntermediateThrowEvent = function(obj) {
        var _this = new _IntermediateThrowEvent(obj);
        var fCanEnd = _this.canEndLink;
        var fCanStart = _this.canStartLink;
        
        _this.addSuperClass("IntermediateThrowEvent");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            var c = 0;
            
            for (var i = 0; i < _this.outConnections.length; i++) {
                if (_this.outConnections[i].type=="MessageFlow") {
                    c++;
                }
            }
            
            if (link.type=="MessageFlow" && c != 0) {
                ret = false;
            }
            return ret;
        }
        
        _this.canEndLink = function (link) {
            var ret = fCanEnd(link);
            if (link.type=="MessageFlow") {
                ret = false;
            }
            return ret;
        }
        
        return _this;
    };
    
    var _CompensationIntermediateThrowEvent = function(obj) {
        var _this = new _IntermediateThrowEvent(obj);
        _this.addSuperClass("IntermediateThrowEvent");
        return _this;
    };
    
    var _LinkIntermediateThrowEvent = function(obj) {
        var _this = new _IntermediateThrowEvent(obj);
        var fCanEnd = _this.canEndLink;
        
        _this.addSuperClass("IntermediateThrowEvent");
        
        _this.canStartLink = function(link) {
            return false;
        }
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.type=="MessageFlow") {
                ret = false;
            }
            return ret;
        }
        
        return _this;
    };
    
    var _SignalIntermediateThrowEvent = function(obj) {
        var _this = new _IntermediateThrowEvent(obj);
        _this.addSuperClass("IntermediateThrowEvent");
        return _this;
    };
    
    var _MultipleIntermediateThrowEvent = function(obj) {
        var _this = new _IntermediateThrowEvent(obj);
        _this.addSuperClass("IntermediateThrowEvent");
        return _this;
    };
    
    var _ScalationIntermediateThrowEvent = function(obj) {
        var _this = new _IntermediateThrowEvent(obj);
        _this.addSuperClass("IntermediateThrowEvent");
        return _this;
    };
    
    /***************************Eventos finales****************************/
    var _EndEvent = function(obj) {
        var _this = new _ThrowEvent(obj);
        _this.addSuperClass("ThrowEvent");
        var fCanEnd = _this.canEndLink;
        var fCanAdd = _this.canAddToDiagram;
        
        _this.canStartLink = function(link) {
            return false;
        }
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.type!="SequenceFlow") {
                ret = false;
            } else if (link.type=="SequenceFlow" && link.fromObject.type=="ExclusiveIntermediateEventGateway") {
                ret = false;
            }
            return ret;
        }
        
        _this.canAddToDiagram = function () {
            var ret = fCanAdd();
            //TODO:Implementar
            return ret;
        }
        
        return _this;
    }
    
    var _MessageEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.addSuperClass("EndEvent");
        _this.canStartLink = function(link) {
            var ret = false;
            if (link.type=="MessageFlow") {
                ret = true;
            }
            return ret;
        }
        
        return _this;
    };
    
    var _ErrorEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.addSuperClass("EndEvent");
        return _this;
    };
    
    var _CancelationEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.addSuperClass("EndEvent");
        return _this;
    };
    
    var _CompensationEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.addSuperClass("EndEvent");
        return _this;
    };
    
    var _SignalEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.addSuperClass("EndEvent");
        return _this;
    };
    
    var _MultipleEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.addSuperClass("EndEvent");
        return _this;
    };
    
    var _ScalationEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.addSuperClass("EndEvent");
        return _this;
    };
    
    var _TerminationEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.addSuperClass("EndEvent");
        return _this;
    };
    
    /******************************Compuertas*******************************/
    var _Gateway = function (obj) {
        var _this = new _FlowNode(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canEndLink;
        
        _this.addSuperClass("FlowNode");
        _this.canStartLink = function (link) {
            var ret = fCanStart(link);
            var ci = 0;
            var co = 0;
            
            for (var i = 0; i < _this.inConnections.length; i++) {
                if (_this.inConnections[i].type=="SequenceFlow") {
                    ci++;
                }
            }
            
            for (i = 0; i < _this.outConnections.length; i++) {
                if (_this.outConnections[i].type=="SequenceFlow") {
                    co++;
                }
            }
            
            if (link.type=="MessageFlow") {
                ret = false;
            }
            
            if (link.type=="SequenceFlow") {
                if (ci > 1 && co != 0) {
                    ret = false;
                }
            }
            return ret;
        }
        
        _this.canEndLink = function (link) {
            var ret = fCanEnd(link);
            var ci = 0;
            var co = 0;
            
            for (var i = 0; i < _this.inConnections.length; i++) {
                if (_this.inConnections[i].type=="SequenceFlow") {
                    ci++;
                }
            }
            
            for (i = 0; i < _this.outConnections.length; i++) {
                if (_this.outConnections[i].type=="SequenceFlow") {
                    co++;
                }
            }
            
            if (link.type=="MessageFlow") {
                ret = false;
            }
            
            if (link.type=="SequenceFlow") {
                if (co > 1 && ci != 0) {
                    ret = false;
                }
            }
            return ret;
        }
        return _this;
    };
    
    var _ExclusiveGateway = function (obj) {
        var _this = new _Gateway(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canEndLink;
        
        _this.addSuperClass("Gateway");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            var c = 0;
            
            for (var i = 0; i < _this.outConnections.length; i++) {
                if (_this.outConnections[i].type=="DefaultFlow") {
                    c++;
                }
            }
            
            if (!link.type=="ConditionalFlow" || link.type=="DefaultFlow") {
                ret = false;
            }
            
            if (link.type=="DefaultFlow" && c > 0) {
                ret = false;
            }
            return ret;
        }
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.fromObject.type=="EventBasedGateway") {
                ret = false;
            }
            return ret;
        }
        
        return _this;
    };
    
    var _EventBasedGateway = function(obj) {
        var _this = new _Gateway(obj);
        var fCanStart = _this.canStartLink;
        _this.addSuperClass("Gateway");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.type=="DefaultFlow") {
                ret = false;
            } else if (link.type=="ConditionalFlow") {
                return false;
            }
            return ret;
        }
        return _this;
    };
    
    var _InclusiveGateway = function(obj) {
        var _this = new _Gateway(obj);
        var fCanStart = _this.canStartLink;
        _this.addSuperClass("Gateway");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            var c = 0;
            
            for (var i = 0; i < _this.outConnections.length; i++) {
                if (_this.outConnections[i].type=="DefaultFlow") {
                    c++;
                }
            }
            
            if (!(link.type=="ConditionalFlow" || link.type=="DefaultFlow")) {
                ret = false;
            }

            if (link.type=="DefaultFlow" && c > 0) {
                ret = false;
            }
            return ret;
        }
        return _this;
    };
    
    var _ExclusiveStartEventGateway = function (obj) {
        var _this = new _EventBasedGateway(obj);
        _this.addSuperClass("EventBasedGateway");
        return _this;
    };
    
    var _ExclusiveIntermediateEventGateway = function (obj) {
        var _this = new _EventBasedGateway(obj);
        _this.addSuperClass("EventBasedGateway");
        return _this;
    };
    
    var _ComplexGateway = function (obj) {
        var _this = new _Gateway(obj);
        _this.addSuperClass("Gateway");
        return _this;
    };
    
    var _ParallelGateway = function (obj) {
        var _this = new _Gateway(obj);
        var fCanStart = _this.canStartLink;
        
        _this.addSuperClass("Gateway");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.type=="ConditionalFlow" || link.type=="DefaultFlow") {
                ret = false;
            }
            return ret;
        }
        return _this;
    };
    
    /***************************Objetos de datos*******************************/
    var _DataObject = function(obj) {
        var _this = new _GraphicalElement(obj);
        var fCanAttach = _this.canAttach;
        var fCanEnd = _this.canEndLink;
        var fCanStart = _this.canStartLink;
        
        _this.addSuperClass("GraphicalElement");
        
        _this.canAttach = function(parent) {
            var ret = fCanAttach(parent);
            if(parent.type=="Pool" || parent.type=="Lane") {
                ret = true;
            }
            return ret;
        }
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (!link.type=="AssociationFlow") {
                ret = false;
            }
            return ret;
        }
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (!link.type=="AssociationFlow") {
                ret = false;
            } else if (link.fromObject.isSubClass("Artifact")) {
                ret = false;
            }
            return ret;
        }
        return _this;
    };
    
    var _DataInput = function (obj) {
        var _this = new _DataObject(obj);
        _this.addSuperClass("DataObject");
        return _this;
    };
    
    var _DataOutput = function (obj) {
        var _this = new _DataObject(obj);
        _this.addSuperClass("DataObject");
        return _this;
    };
    
    var _DataStore = function (obj) {
        var _this = new _DataObject(obj);
        _this.addSuperClass("DataObject");
        return _this;
    };
    /******************************Swimlanes*******************************/
    var _Pool = function (obj) {
        var _this = new _GraphicalElement(obj);
        var fCanAdd = _this.canAddToDiagram;
        
        _this.addSuperClass("GraphicalElement");
        
        _this.canStartLink = function(link) {
            var ret = true;
            if (link.type=="SequenceFlow") {
                ret = false;
            }
            return ret;
        }
        
        _this.canEndLink = function(link) {
            var ret = true;
            if (link.type=="SequenceFlow") {
                ret = false;
            }
            if (link.type=="MessageFlow") {
                //TODO:Implementar
            }
            if (link.type=="AssociationFlow") {
                ret = false;
            }
            return ret;
        }
        
        _this.canAddToDiagram = function() {
            var ret = fCanAdd();
            //TODO:Implementar
            return ret;
        }
        return _this;
    }
    
    /******************************Actividades*******************************/
    var _Activity = function(obj) {
        var _this = new _FlowNode(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canEndLink;
        
        _this.addSuperClass("FlowNode");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.type=="DefaultFlow") {
                ret = false;
            }
            return ret;
        }
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.type=="DirectionalAssociation" && link.fromObject.type=="CompensationIntermediateCatchEvent") {
                ret = true;            
            } else if (link.fromObject.type=="ExclusiveIntermediateEventGateway") {
                ret = false;
            }
            return ret;
        }
        return _this;
    };
    
    var _CallActivity = function(obj) {
        var _this = new _Activity(obj);
        _this.addSuperClass("Activity");
        return _this;
    };
    
    var _Task = function(obj) {
        var _this = new _Activity(obj);
        _this.addSuperClass("Activity");
        return _this;
    };
    
    var _CallTask = function(obj) {
        var _this = new _CallActivity(obj);
        _this.addSuperClass("CallActivity");
        return _this;
    };
    
    var _UserTask = function(obj) {
        var _this = new _Task(obj);
        _this.addSuperClass("Task");
        return _this;
    };
    
    var _ServiceTask = function(obj) {
        var _this = new _Task(obj);
        _this.addSuperClass("Task");
        return _this;
    };
    
    var _BusinessRuleTask = function(obj) {
        var _this = new _Task(obj);
        _this.addSuperClass("Task");
        return _this;
    };
    
    var _CallManualTask = function(obj) {
        var _this = new _CallTask(obj);
        _this.addSuperClass("CallTask");
        return _this;
    };
    
    var _CallBusinessRuleTask = function(obj) {
        var _this = new _CallTask(obj);
        _this.addSuperClass("CallTask");
        return _this;
    };
    
    var _CallScriptTask = function(obj) {
        var _this = new _CallTask(obj);
        _this.addSuperClass("CallTask");
        return _this;
    };
    
    var _CallUserTask = function(obj) {
        var _this = new _CallTask(obj);
        _this.addSuperClass("CallTask");
        return _this;
    };
    
    var _ScriptTask = function(obj) {
        var _this = new _Task(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canEndLink;
        
        _this.addSuperClass("Task");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.type=="MessageFlow") {
                ret = false;
            }
            return ret;
        }
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.type=="MessageFlow") {
                ret = false;
            }
            return ret;
        }
        return _this;
    };
    
    var _SendTask = function(obj) {
        var _this = new _Task(obj);
        var fCanEnd = _this.canEndLink;
        
        _this.addSuperClass("Task");
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.type=="MessageFlow") {
                ret = false;
            }
            return ret;
        }
        return _this;
    };
    
    var _ReceiveTask = function(obj) {
        var _this = new _Task(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canEndLink;
        
        _this.addSuperClass("Task");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.type=="MessageFlow") {
                ret = false;
            }
            return ret;
        }
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.type=="SequenceFlow" && link.fromObject.type=="ExclusiveIntermediateEventGateway") {
                ret = true;
                for (var i = 0; i < link.fromObject.outConnections.length; i++) {
                    if (link.fromObject.outConnections[i].type=="SequenceFlow" && link.toObject.outConnections[i].type=="MessageIntermediateCatchEvent") {
                        ret = false;
                        break;
                    }
                }
            }
            return ret;
        }
        return _this;
    };
    
    var _ManualTask = function(obj) {
        var _this = new _Task(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canEndLink;
        
        _this.addSuperClass("Task");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.type=="MessageFlow") {
                ret = false;
            }
            return ret;
        }
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.type=="MessageFlow") {
                ret = false;
            }
            return ret;
        }
        return _this;
    };
    
    
    
    var ToolBar =
    {

        intervalOver:null,

        hideSubBars: function() {
            document.getElementById("startEventsBar").setAttribute("class", "subbarHidden");
            document.getElementById("interEventsBar").setAttribute("class", "subbarHidden");
            document.getElementById("endEventsBar").setAttribute("class", "subbarHidden");
            document.getElementById("tasksBar").setAttribute("class", "subbarHidden");
            document.getElementById("subtasksBar").setAttribute("class", "subbarHidden");
            document.getElementById("calltasksBar").setAttribute("class", "subbarHidden");
            document.getElementById("gatewaysBar").setAttribute("class", "subbarHidden");
            document.getElementById("connectionsBar").setAttribute("class", "subbarHidden");
            document.getElementById("artifactsBar").setAttribute("class", "subbarHidden");
            document.getElementById("dataobjectsBar").setAttribute("class", "subbarHidden");
            document.getElementById("swimlanesBar").setAttribute("class", "subbarHidden");
        },

        showSubBar: function(barId,obj) {
            ToolBar.hideSubBars();
            var ele=document.getElementById(barId);
            ele.setAttribute("class", "subbar");

            if(obj)
            {
                //alert(obj.offsetTop);
                ele.style.top=obj.offsetTop+"px";
                ele.style.left=(obj.offsetLeft+47)+"px";
                //ele.offsetLeft=obj.offsetLeft;
                //alert(ele.offsetTop);
                //desc(obj,true);
            }
        },

        outToolBar: function()
        {
            ToolBar.intervalOver=setTimeout(ToolBar.hideSubBars,1000);
        },

        overToolBar: function()
        {
            clearTimeout(ToolBar.intervalOver);
        }

    }  
    
    var Modeler =
    {
        creationId:null,                                      //Objeto temporal para creacion de instancias
        
        init:function(svgid)
        {
            ToolKit.init(svgid);            
            ToolKit.onmousedown=Modeler.onmousedown;
            ToolKit.onmousemove=Modeler.onmousemove;
            ToolKit.onmouseup=Modeler.onmouseup;
            if(!ToolKit.svg.offsetLeft)ToolKit.svg.offsetLeft=60;
            if(!ToolKit.svg.offsetTop)ToolKit.svg.offsetTop=10;
        },
                
        onmousedown:function(evt)
        {
            if(Modeler.creationId!=null)
            {
                var obj=Modeler.mapObject(Modeler.creationId);
                if(obj.move) //es un FlowNode
                {                
                    obj.move(ToolKit.getEventX(evt), ToolKit.getEventY(evt));
                    obj.snap2Grid();
                }else   //Es un ConnectionObject
                {
                    if(Modeler.creationDropObject!=null)
                    {
                        Modeler.dragConnection=Modeler.mapObject(Modeler.creationId);
                        Modeler.creationDropObject.addOutConnection(Modeler.dragConnection);
                        Modeler.dragConnection.setEndPoint(Modeler.creationDropObject.getX(),Modeler.creationDropObject.getY());                        
                    }
                }
//                obj.setX(ToolKit.getEventX(evt));
//                obj.setY(ToolKit.getEventY(evt));
                //desc(evt,true);
                Modeler.creationId=null;
                Modeler.creationDropObject=null;
                return false;
            }
            return true;
        },    
                
        onmousemove:function(evt)
        {
            //console.log(evt);
            if(Modeler.dragConnection!=null)
            {
                if(Modeler.dragConnection.toObject!=null)
                {
                    Modeler.dragConnection.toObject=null;
                    ToolKit.unSelectAll();
                }
                Modeler.dragConnection.setEndPoint(ToolKit.getEventX(evt), ToolKit.getEventY(evt));
                return false;
            }
            return true;
        },  
                
        onmouseup:function(evt)
        {
            if(Modeler.dragConnection!=null)
            {
                if(Modeler.dragConnection.toObject==null)
                {
                    Modeler.dragConnection.remove();
                    
                }else
                {
                    Modeler.dragConnection.toObject.addInConnection(Modeler.dragConnection);
                }
                Modeler.dragConnection=null;
            }
            return true;
        },                  
                
        objectMouseDown:function(evt,obj)
        {
            if(Modeler.creationId!=null)
            {
                Modeler.creationDropObject=obj;
                return false;
            }
            
            if(evt.button==2)
            {
                Modeler.dragConnection=Modeler.mapObject("sequenceFlow");
                obj.addOutConnection(Modeler.dragConnection);
                Modeler.dragConnection.setEndPoint(obj.getX(),obj.getY());
                return false;
            }
            return true;            
        },      
                
        objectMouseMove:function(evt,obj)
        {
            //console.log("objectMouseOver:"+evt+" "+obj);
            if(Modeler.dragConnection!=null)
            {
                if(Modeler.dragConnection.fromObject!=obj && Modeler.dragConnection.toObject!=obj)
                {
                    ToolKit.unSelectAll();
                    ToolKit.selectObj(obj,true);
                    Modeler.dragConnection.toObject=obj;
                    Modeler.dragConnection.setEndPoint(obj.getX(),obj.getY());
                }
                ToolKit.stopPropagation(evt);
            }            
        },                   
                
        creationStart:function(span)
        {
            Modeler.creationId=span.getAttribute("class");
        },
        
        createObject:function(type, id, parent)
        {
            var obj=ToolKit.createUseObject(type,id,parent);
            obj.mousedown=function(evt){return Modeler.objectMouseDown(evt,obj);}
            obj.onmousemove=function(evt){return Modeler.objectMouseMove(evt,obj);}

            return obj;
        },
                
        createConnectionPath:function(marker_start, marker_mid, marker_end, dash_array, styleClass) 
        {
            var obj = ToolKit.createConnectionPath(0,0,0,0,marker_start, marker_mid, marker_end, dash_array, styleClass);
            obj.subLine = ToolKit.createConnectionPath(0,0,0,0,null, null, null, null, null);
            obj.subLine.setStyleClass=function(cls) {
                obj.subLine.setAttributeNS(null,"class",cls);
            }
            
            obj.setArrowType= function(type) {
                obj.setAttributeNS(null, "marker-end", "url(#"+type+")");
            }
            obj.setTailType= function(type) {
                obj.setAttributeNS(null, "marker-start", "url(#"+type+")");
            }
            obj.soff=0;
            obj.eoff=0;
            
            //obj.addPoint(0,0);
            obj.addPoint(0,0);
            obj.addPoint(0,0);
            
            obj.setStartPoint=function(x,y) {
                obj.setPoint(0,x,y);
                obj.xs=x;
                obj.ys=y;
                obj.updateInterPoints();
            }
                    
            obj.setEndPoint=function(x,y) {
                obj.xe=x;
                obj.ye=y;
                obj.setPoint(obj.pathSegList.numberOfItems-1,x,y);
                obj.updateInterPoints();
            }
            
            obj.updateInterPoints=function()
            {
                var p0=obj.pathSegList.getItem(0);
                var p3=obj.pathSegList.getItem(3);
                
                var fw=0;if(obj.fromObject!=null)fw=obj.fromObject.getWidth()/2;
                var fh=0;if(obj.fromObject!=null)fh=obj.fromObject.getHeight()/2;
                var tw=0;if(obj.toObject!=null)tw=obj.toObject.getWidth()/2;
                var th=0;if(obj.toObject!=null)th=obj.toObject.getHeight()/2;
                
                var dx=obj.xe-obj.xs;
                var dy=obj.ye-obj.ys;
                
                //console.log("updateInterPoints:"+obj.pathSegList.numberOfItems+" "+p1.fixed+" "+p2.fixed);
                if((Math.abs(dx)-fw-tw)>=(Math.abs(dy)-fh-th))  //Caso X
                {
                    if(dx>0)dx=1;
                    else if(dx<0)dx=-1;
                    
                    p0.x=obj.xs+dx*(fw+obj.soff);
                    p3.x=obj.xe-dx*(tw+obj.eoff);
                    p3.y=obj.ye;
                    p0.y=obj.ys;
                    
                    
                    if(obj.pathSegList.numberOfItems==4 && obj.fixed==false)
                    {
                        var p1=obj.pathSegList.getItem(1);
                        var p2=obj.pathSegList.getItem(2);
                        
                        p1.x=(p3.x-p0.x)/2+p0.x;
                        p2.x=p1.x;
                        p1.y=p0.y;
                        p2.y=p3.y;
                    }                    
                    
                }else   //Caso Y
                {
                    if(dy>0)dy=1;
                    else if(dy<0)dy=-1;
                    
                    p0.y=obj.ys+dy*(fh+obj.soff);
                    p3.y=obj.ye-dy*(th+obj.eoff);
                    p3.x=obj.xe;
                    p0.x=obj.xs;
                    
                    if(obj.pathSegList.numberOfItems==4 && obj.fixed==false)
                    {
                        p1=obj.pathSegList.getItem(1);
                        p2=obj.pathSegList.getItem(2);
                    
                        p1.y=(p3.y-p0.y)/2+p0.y;
                        p2.y=p1.y;
                        p1.x=p0.x;
                        p2.x=p3.x;
                    }                                        
                }
                obj.updateSubLine();
            }
            var fRemove = obj.remove;
            
            obj.remove=function() {
                obj.subLine.remove();
                fRemove();
            }
            
//            obj.onmouseover=function(evt) {
//                obj.subLine.setStyleClass("sequenceFlowSubLine_o");
//            }
//            
//            obj.subLine.onmouseover=function(evt) {
//                obj.subLine.setStyleClass("sequenceFlowSubLine_o");
//            }
//            
//            obj.subLine.onmouseout=function(evt) {
//                obj.subLine.setStyleClass("sequenceFlowSubLine");
//            }
//            
//            obj.onmouseout=function(evt) {
//                obj.subLine.setStyleClass("sequenceFlowSubLine");
//            }
            
            obj.updateSubLine= function() {
                obj.subLine.pathSegList.clear();
                for (var i=0; i<obj.pathSegList.numberOfItems; i++) {
                    var segment = obj.pathSegList.getItem(i);
                    if (segment.pathSegType==SVGPathSeg.PATHSEG_LINETO_ABS) {
                        obj.subLine.pathSegList.appendItem(obj.subLine.createSVGPathSegLinetoAbs(segment.x, segment.y));
                    } else if (segment.pathSegType==SVGPathSeg.PATHSEG_MOVETO_ABS) {
                       obj.subLine.pathSegList.appendItem(obj.subLine.createSVGPathSegMovetoAbs(segment.x, segment.y));
                    }
                }
                ToolKit.svg.insertBefore(obj.subLine, obj);
                obj.subLine.setAttributeNS(null,"class","sequenceFlowSubLine");
            }
            return obj;
        },
        
        createSwimLane:function(id, parent)
        {
            //Revisar ID
            var obj=ToolKit.createResizeObject(id,parent);
            obj.setAttributeNS(null,"bclass","swimlane");
            obj.setAttributeNS(null,"oclass","swimlane_o");
            obj.setBaseClass();
            
            var constructor=function()
            {
                var tx = document.createElementNS(ToolKit.svgNS,"path");
                return tx;
            };
            
            var line=ToolKit.createBaseObject(constructor,null,null);
            obj.headerLine = line;
            
            obj.mousedown=function(evt)
            {
                if(ToolKit.getEventX(evt)<obj.getX()-obj.getWidth()/2+20)
                {
                    return Modeler.objectMouseDown(evt,obj);
                }
                return false;
            }
            
            obj.onmousemove=function(evt)
            {
                if(Modeler.dragConnection!=null)  //Valida no conectar objetos hijos del pool
                {
                    if(Modeler.dragConnection.fromObject.isChild(obj))
                    {
                        return false;
                    }
                }                                
                return Modeler.objectMouseMove(evt,obj);
            }
            
            var fMove = obj.move;
            var fResize = obj.resize;
            var fRemove = obj.remove;
            var fMoveFirst = obj.moveFirst;
            
            obj.updateHeaderLine=function() {
                obj.headerLine.setAttributeNS(null, "d", "M"+(obj.getX()-obj.getWidth()/2 + 20)+" "+(obj.getY()-obj.getHeight()/2) +" L"+(obj.getX()-obj.getWidth()/2 + 20)+" "+(obj.getY()+obj.getHeight()/2));
                obj.headerLine.setAttributeNS(null,"bclass","swimlane");
                obj.headerLine.setAttributeNS(null,"oclass","swimlane_o");
                obj.headerLine.setBaseClass();
                if (obj.nextSibling) {
                    ToolKit.svg.insertBefore(obj.headerLine, obj.nextSibling);
                }
            }
            
            obj.remove=function() {
                obj.headerLine.remove();
                fRemove();
            }
            
            obj.moveFirst=function() {
                //fMoveFirst();
                obj.updateHeaderLine();
            }
            
            obj.resize= function(w, h) {
                fResize(w, h);
                obj.updateHeaderLine();
            }
            
            obj.move = function(x, y) {
                fMove(x,y);
                obj.updateHeaderLine();
            }
            return obj;
        },
    
        createTask:function(id, parent)
        {
            //Revisar ID
            var obj=ToolKit.createResizeObject(id,parent);
            obj.setAttributeNS(null,"bclass","task");
            obj.setAttributeNS(null,"oclass","task_o");
            obj.setAttributeNS(null,"rx",10);
            obj.setAttributeNS(null,"ry",10);
            obj.setBaseClass();                        
            obj.mouseup=function(x,y)
            {
                //alert("hola1");
                return true;
            }
            obj.mousedown=function(evt){return Modeler.objectMouseDown(evt,obj);}
            obj.onmousemove=function(evt){return Modeler.objectMouseMove(evt,obj);}
            
            return obj;
        },
        
        createCallTask:function(id, parent)
        {
            //Revisar ID
            var obj=Modeler.createTask(id,parent);
            obj.setAttributeNS(null,"bclass","callactivity");
            obj.setAttributeNS(null,"oclass","callactivity_o");
            obj.setBaseClass();                        
            return obj;
        },
        
        createAnnotationArtifact:function(id, parent) {
            var obj= ToolKit.createResizeObject(id,parent);
            obj.setAttributeNS(null,"oclass","annotationArtifactRect_o");
            obj.setAttributeNS(null,"bclass","annotationArtifactRect");
            obj.setBaseClass();
            
            var constructor=function()
            {
                var tx = document.createElementNS(ToolKit.svgNS,"path");
                return tx;
            };
            
            var line=ToolKit.createBaseObject(constructor,null,null);
            obj.subLine = line;
            obj.subLine.canSelect=false;
            obj.subLine.setAttributeNS(null,"bclass","annotationArtifact");
            obj.subLine.setAttributeNS(null,"oclass","annotationArtifact");
            obj.subLine.setBaseClass();
            obj.mouseup=function(x,y)
            {
                //alert("hola1");
                return true;
            };
            //obj.mousedown=function(evt){return Modeler.objectMouseDown(evt,obj);}
            obj.onmousemove=function(evt){return Modeler.objectMouseMove(evt,obj);}
            
            obj.updateSubLine=function() {
                obj.subLine.setAttributeNS(null, "d", "M"+(obj.getX()-obj.getWidth()/2 + 20)+" "+(obj.getY()-obj.getHeight()/2) 
                    +" L"+(obj.getX()-obj.getWidth()/2)+" "+(obj.getY()-obj.getHeight()/2) 
                    +" L"+(obj.getX()-obj.getWidth()/2)+" "+(obj.getY()+obj.getHeight()/2)
                    +" L"+(obj.getX()-obj.getWidth()/2+20)+" "+(obj.getY()+obj.getHeight()/2));
                if (obj.nextSibling) {
                    ToolKit.svg.insertBefore(obj.subLine, obj.nextSibling);
                }
            }

            var fMove=obj.move;
            var fRemove=obj.remove;
            var fResize=obj.resize;
            var fMoveFirst = obj.moveFirst;
            
            obj.move=function(x,y) {
                fMove(x,y);
                obj.updateSubLine();
            }
            
            obj.remove=function() {
                obj.subLine.remove();
                fRemove();
            }
            
            obj.moveFirst=function() {
                fMoveFirst();
                obj.updateSubLine();
            }
            
            obj.resize=function(w,h) {
                fResize(w,h);
                obj.updateSubLine();
            }
            
            return obj;
        },
        
        createGroupArtifact:function(id,parent) {
            var obj= ToolKit.createResizeObject(id,parent);
            obj.setAttributeNS(null,"oclass","group_o");
            obj.setAttributeNS(null,"bclass","group");
            obj.setBaseClass();
            obj.setAttributeNS(null,"stroke-dasharray","20,5,3,5");
            
            var rect=document.createElementNS(ToolKit.svgNS,"rect");
            obj.subLine = rect;
            obj.subLine.onmousedown=obj.onmousedown;
            obj.subLine.onmouseup=obj.onmouseup;
            obj.subLine.onmousemove=obj.onmousemove;
            
            obj.updateSubLine=function() {
                if (obj.subLine && obj.subLine!=null) {
                    obj.subLine.setAttributeNS(null,"x",obj.getX()-obj.getWidth()/2);
                    obj.subLine.setAttributeNS(null,"y",obj.getY()-obj.getHeight()/2);
                    obj.subLine.setAttributeNS(null,"width",obj.getWidth());
                    obj.subLine.setAttributeNS(null,"height",obj.getHeight());
                    ToolKit.svg.insertBefore(obj.subLine, obj);
                    obj.subLine.setAttributeNS(null,"class","sequenceFlowSubLine");
                }
            }
            
            var fMove=obj.move;
            var fRemove=obj.remove;
            var fResize=obj.resize;
            
            obj.move=function(x,y) {
                fMove(x,y);
                obj.updateSubLine();
            }
            
            obj.resize=function(w,h) {
                fResize(w,h);
                obj.updateSubLine();
            }
            
            obj.remove=function(all) {
                ToolKit.svg.removeChild(obj.subLine);
                fRemove(all);
                obj.subLine=null;
            }
            
            return obj;
        },
        
        createSubProcess: function(id, parent, type) {
            var obj=Modeler.createTask(id,parent);
            var icon=obj.addIcon("#subProcessMarker",0,1,-1,-12);
            obj.subLayer={parent:obj};
            
            icon.obj.ondblclick=function(evt)
            {
                ToolKit.setLayer(obj.subLayer);
            };
            
            if (type=="eventsubProcess") {
                obj.setAttributeNS(null,"bclass","eventSubTask");
                obj.setAttributeNS(null,"oclass","eventSubTask_o");
                obj.setBaseClass();                        
            }
            else if (type=="transactionsubProcess")
            {
                if (obj.subSquare && obj.subSquare!= null) {
                    obj.subSquare.remove();
                } else {
                    var constructor=function()
                    {
                        var tx = document.createElementNS(ToolKit.svgNS,"rect");
                        tx.setAttributeNS(null,"rx",10);
                        tx.setAttributeNS(null,"ry",10);
                        return tx;
                    };
                    var square=ToolKit.createBaseObject(constructor,null,null);
                    obj.subSquare = square;
                    obj.subSquare.setAttributeNS(null,"class","transactionSquare");
                    obj.subSquare.canSelect=false;
                    
                    obj.updateSubSquare=function() {
                        obj.subSquare.move(obj.getX(),obj.getY());
                        obj.subSquare.resize(obj.getWidth()-8,obj.getHeight()-8);
                        if (obj.nextSibling) {
                            ToolKit.svg.insertBefore(obj.subSquare, obj.nextSibling);
                        }
                    }
                }
                
                var fMove=obj.move;
                var fResize=obj.resize;
                var fMoveFirst=obj.moveFirst;
                
                obj.move=function(x,y) {
                    fMove(x,y);
                    if(obj.subSquare && obj.subSquare!=null) {
                        obj.updateSubSquare();
                    }
                }
                
                obj.resize=function(w,h) {
                    fResize(w,h);
                    if(obj.subSquare && obj.subSquare!=null) {
                        obj.updateSubSquare();
                    }
                }
                
                obj.moveFirst=function() {
                    fMoveFirst();
                    obj.updateSubSquare();
                }
            }
            return obj;
        },

        mapObject:function(type)
        {
            var ret = null;
            if(type=='sequenceFlow') {
                ret = Modeler.createConnectionPath(null, null, "sequenceArrow", null,"sequenceFlowLine");
                ret.elementType="SequenceFlow";
                ret.eoff=10;
            }
            if(type=='messageFlow') {
                ret = Modeler.createConnectionPath("messageTail", null, "messageArrow", "5,5", "sequenceFlowLine");
                ret.elementType="MessageFlow";
                ret.eoff=10;
            }
            if(type=='conditionalFlow') {
                ret = Modeler.createConnectionPath("conditionTail", null, "sequenceArrow", null, "sequenceFlowLine");
                ret.elementType="ConditionalFlow";
                ret.eoff=10;
            }
            if(type=='defaultFlow') {
                ret = Modeler.createConnectionPath("defaultTail", null, "sequenceArrow", null, "sequenceFlowLine");
                ret.elementType="DefaultFlow";
                ret.eoff=10;
            }
            if(type=='associationFlow') {
                ret = Modeler.createConnectionPath(null, null, null, "5,5", "sequenceFlowLine");
                ret.elementType="AssociationFlow";
            }
            if(type=='directionalassociationFlow') {
                ret = Modeler.createConnectionPath(null, null, "messageArrow", "5,5", "sequenceFlowLine");
                ret.elementType="DirectionalAssociation";
                ret.eoff=10;
            }
            else if(type=='normalStartEvent')
            {
                ret=Modeler.createObject("#startEvent",null,null);
                ret.elementType="StartEvent";
                ret.setText("Inicio Normal",0,1,80,1);
            }
            else if(type=='messageStartEvent'){
                ret=Modeler.createObject("#messageStartEvent",null,null);
                ret.elementType="MessageStartEvent";
                ret.setText("Inicio por mensaje",0,1,80,1);
            }
            else if(type=='timerStartEvent'){
                ret=Modeler.createObject("#timerStartEvent",null,null);
                ret.elementType="TimerStartEvent";
                ret.setText("Inicio temporizado",0,1,80,1);
            }
            else if(type=='ruleStartEvent') {
                ret=Modeler.createObject("#ruleStartEvent",null,null);
                ret.elementType="RuleStartEvent";
                ret.setText("Inicio por regla de negocio",0,1,80,1);
            }
            else if(type=='signalStartEvent') {
                ret=Modeler.createObject("#signalStartEvent",null,null);
                ret.elementType="SignalStartEvent";
                ret.setText("Inicio por señal",0,1,80,1);
            }
            else if(type=='multiStartEvent') {
                ret=Modeler.createObject("#multipleStartEvent",null,null);
                ret.elementType="MultipleStartEvent";
                ret.setText("Inicio múltiple",0,1,80,1);
            }
            else if(type=='parallelStartEvent') {
                ret=Modeler.createObject("#parallelStartEvent",null,null);
                ret.elementType="ParallelStartEvent";
                ret.setText("Inicio paralelo",0,1,80,1);
            }
            else if(type=='scalaStartEvent') {
                ret= Modeler.createObject("#scalationStartEvent",null,null);
                ret.elementType="ScalationStartEvent";
                ret.setText("Inicio por escalamiento",0,1,80,1);
            }
            else if(type=='errorStartEvent') {
                ret= Modeler.createObject("#errorStartEvent",null,null);
                ret.elementType="ErrorStartEvent";
                ret.setText("Inicio por error",0,1,80,1);
            }
            else if(type=='compensaStartEvent') {
                ret= Modeler.createObject("#compensationStartEvent",null,null);
                ret.elementType="CompensationStartEvent";
                ret.setText("Inicio por compensación",0,1,80,1);
            }
            else if(type=='messageInterCatchEvent') {
                ret= Modeler.createObject("#messageIntermediateCatchEvent",null,null);
                ret.elementType="MessageIntermediateCatchEvent";
                ret.setText("Recepción de mensaje",0,1,80,1);
            }
            else if(type=='messageInterThrowEvent') {
                ret= Modeler.createObject("#messageIntermediateThrowEvent",null,null);
                ret.elementType="MessageIntermediateThrowEvent";
                ret.setText("Envío de mensaje",0,1,80,1);
            }
            else if(type=='timerInterEvent') {
                ret= Modeler.createObject("#timerIntermediateEvent",null,null);
                ret.elementType="TimerIntermediateCatchEvent";
                ret.setText("Temporizador",0,1,80,1);
            }
            else if(type=='errorInterEvent') {
                ret= Modeler.createObject("#errorIntermediateEvent",null,null);
                ret.elementType="ErrorIntermediateCatchEvent";
                ret.setText("Recepción de error",0,1,80,1);
            }
            else if(type=='cancelInterEvent') {
                ret= Modeler.createObject("#cancelIntermediateEvent",null,null);
                ret.elementType="CancelationIntermediateCatchEvent";
                ret.setText("Cancelación",0,1,80,1);
            }
            else if(type=='compensaInterCatchEvent') {
                ret= Modeler.createObject("#compensationIntermediateCatchEvent",null,null);
                ret.elementType="CompensationIntermediateCatchEvent";
                ret.setText("Recepción de compensación",0,1,80,1);
            }
            else if(type=='compensaInterThrowEvent') {
                ret= Modeler.createObject("#compensationIntermediateThrowEvent",null,null);
                ret.elementType="CompensationIntermediateThrowEvent";
                ret.setText("Disparo de compensación",0,1,80,1);
            }
            else if(type=='ruleInterEvent') {
                ret= Modeler.createObject("#ruleIntermediateEvent",null,null);
                ret.elementType="RuleIntermediateCatchEvent";
                ret.setText("Regla de negocio",0,1,80,1);
            }
            else if(type=='linkInterCatchEvent') {
                ret= Modeler.createObject("#linkIntermediateCatchEvent",null,null);
                ret.elementType="LinkIntermediateCatchEvent";
                ret.setText("Recepción de enlace",0,1,80,1);
            }
            else if(type=='linkInterThrowEvent') {
                ret= Modeler.createObject("#linkIntermediateThrowEvent",null,null);
                ret.elementType="LinkIntermediateThrowEvent";
                ret.setText("Disparo de enlace",0,1,80,1);
            }
            else if(type=='signalInterCatchEvent') {
                ret= Modeler.createObject("#signalIntermediateCatchEvent",null,null);
                ret.elementType="SignalIntermediateCatchEvent";
                ret.setText("Recepción de señal",0,1,80,1);
            }
            else if(type=='signalInterThrowEvent') {
                ret= Modeler.createObject("#signalIntermediateThrowEvent",null,null);
                ret.elementType="SignalIntermediateThrowEvent";
                ret.setText("Disparo de señal",0,1,80,1);
            }
            else if(type=='multipleInterCatchEvent') {
                ret= Modeler.createObject("#multipleIntermediateCatchEvent",null,null);
                ret.elementType="MultipleIntermediateCatchEvent";
                ret.setText("Recepción múltiple",0,1,80,1);
            }
            else if(type=='multipleInterThrowEvent') {
                ret= Modeler.createObject("#multipleIntermediateThrowEvent",null,null);
                ret.elementType="MultipleIntermediateThrowEvent";
                ret.setText("Disparo múltiple",0,1,80,1);
            }
            else if(type=='scalaInterCatchEvent') {
                ret= Modeler.createObject("#scalationIntermediateCatchEvent",null,null);
                ret.elementType="ScalationIntermediateCatchEvent";
                ret.setText("Recepción de escalamiento",0,1,80,1);
            }
            else if(type=='scalaInterThrowEvent') {
                ret= Modeler.createObject("#scalationIntermediateThrowEvent",null,null);
                ret.elementType="ScalationIntermediateThrowEvent";
                ret.setText("Disparo de escalamiento",0,1,80,1);
            }
            else if(type=='parallelInterEvent') {
                ret= Modeler.createObject("#parallelIntermediateEvent",null,null);
                ret.elementType="ParallelIntermediateCatchEvent";
                ret.setText("Paralelo",0,1,80,1);
            }
            else if(type=='normalEndEvent') {
                ret= Modeler.createObject("#endEvent",null,null);
                ret.elementType="EndEvent";
                ret.setText("Fin normal",0,1,80,1);
            }
            else if(type=='messageEndEvent') {
                ret= Modeler.createObject("#messageEndEvent",null,null);
                ret.elementType="MessageEndEvent";
                ret.setText("Fin con mensaje",0,1,80,1);
            }
            else if(type=='errorEndEvent') {
                ret= Modeler.createObject("#errorEndEvent",null,null);
                ret.elementType="ErrorEndEvent";
                ret.setText("Fin con error",0,1,80,1);
            }
            else if(type=='cancelEndEvent') {
                ret= Modeler.createObject("#cancelationEndEvent",null,null);
                ret.elementType="CancelationEndEvent";
                ret.setText("Fin con cancelación",0,1,80,1);
            }
            else if(type=='compensaEndEvent') {
                ret= Modeler.createObject("#compensationEndEvent",null,null);
                ret.elementType="CompensationEndEvent";
                ret.setText("Fin con compensación",0,1,80,1);
            }
            else if(type=='signalEndEvent') {
                ret= Modeler.createObject("#signalEndEvent",null,null);
                ret.elementType="SignalEndEvent";
                ret.setText("Fin con señal",0,1,80,1);
            }
            else if(type=='multiEndEvent') {
                ret= Modeler.createObject("#multipleEndEvent",null,null);
                ret.elementType="MultipleEndEvent";
                ret.setText("Fin múltiple",0,1,80,1);
            }
            else if(type=='escalaEndEvent') {
                ret= Modeler.createObject("#scalationEndEvent",null,null);
                ret.elementType="ScalationEndEvent";
                ret.setText("Fin con escalamiento",0,1,80,1);
            }
            else if(type=='terminalEndEvent') {
                ret= Modeler.createObject("#terminationEndEvent",null,null);
                ret.elementType="TerminationEndEvent";
                ret.setText("Terminación",0,1,80,1);
            }
            else if(type=='exclusiveDataGateway') {
                ret= Modeler.createObject("#exclusiveDataGateway",null,null);
                ret.elementType="ExclusiveGateway";
                ret.setText("Exclusiva (datos)",0,1,80,1);
            }
            else if(type=='inclusiveDataGateway') {
                ret= Modeler.createObject("#inclusiveDataGateway",null,null);
                ret.elementType="InclusiveGateway";
                ret.setText("Inclusiva (datos)",0,1,80,1);
            }
            else if(type=='exclusiveStartEventGateway') {
                ret= Modeler.createObject("#exclusiveStartGateway",null,null);
                ret.elementType="ExclusiveStartEventGateway";
                ret.setText("Exclusiva de inicio",0,1,80,1);
            }
            else if(type=='exclusiveEventGateway') {
                ret= Modeler.createObject("#eventGateway",null,null);
                ret.elementType="EventBasedGateway";
                ret.setText("Exclusiva (eventos)",0,1,80,1);
            }
            else if(type=='parallelGateway') {
                ret= Modeler.createObject("#parallelGateway",null,null);
                ret.elementType="ParallelGateway";
                ret.setText("Paralela",0,1,80,1);
            }
            else if(type=='parallelStartGateway') {
                ret= Modeler.createObject("#parallelStartGateway",null,null);
                ret.elementType="ParallelStartEventGateway";
                ret.setText("Paralela de inicio",0,1,80,1);
            }
            else if(type=='complexGateway') {
                ret= Modeler.createObject("#complexGateway",null,null);
                ret.elementType="ComplexGateway";
                ret.setText("Compleja",0,1,80,1);
            }
            else if(type=='group') {
                ret= Modeler.createGroupArtifact(null,null);
                ret.elementType="GroupArtifact";
                ret.resize(300,300);
            }
            else if(type=='annotation'){
                ret= Modeler.createAnnotationArtifact(null, null);
                ret.elementType="AnnotationArtifact";
                ret.setText("Anotación de texto",0,0,0,1);
                ret.resize(200,60);
            }
            else if(type=='dataObject') {
                ret= Modeler.createObject("#data",null,null);
                ret.elementType="DataObject";
                ret.setText("Dato",0,1,80,1);
            }
            else if(type=='dataInput') {
                ret= Modeler.createObject("#dataInput",null,null);
                ret.elementType="DataInput";
                ret.setText("Dato de entrada",0,1,80,1);
            }
            else if(type=='dataOutput') {
                ret= Modeler.createObject("#dataOutput",null,null);
                ret.elementType="DataOutput";
                ret.setText("Dato de salida",0,1,80,1);
            }
            else if(type=='dataStore') {
                ret= Modeler.createObject("#dataStore",null,null);
                ret.elementType="DataStore";
                ret.setText("Almacén de datos",0,1,80,1);
            }
            else if(type=='userTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="UserTask";
                ret.addIcon("#userMarker",-1,-1,13,8);
                ret.setText("Tarea de Usuario",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='serviceTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="ServiceTask";
                ret.addIcon("#serviceMarker",-1,-1,13,8);
                ret.setText("Tarea de Servicio",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='scriptTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="ScriptTask";
                ret.addIcon("#scriptMarker",-1,-1,7,13);
                ret.setText("Tarea de Script",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='ruleTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="BusinessRuleTask";
                ret.addIcon("#taskRuleMarker",-1,-1,12,12);
                ret.setText("Tarea de regla de negocio",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='manualTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="ManualTask";
                ret.addIcon("#manualMarker",-1,-1,9,6);
                ret.setText("Tarea Manual",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='sendTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="SendTask";
                ret.addIcon("#taskMessageThrowMarker",-1,-1,13,10);
                ret.setText("Tarea de envío de mensaje",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='receiveTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="ReceiveTask";
                ret.addIcon("#taskMessageCatchMarker",-1,-1,13,10);
                ret.setText("Tarea de recepción de mensaje",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='abstractTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="Task";
                ret.setText("Tarea abstracta",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callTask') {
                ret = Modeler.createCallTask(null,null);
                ret.elementType="CallTask";
                ret.setText("Tarea reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callmanualTask') {
                ret = Modeler.createCallTask(null,null);
                ret.elementType="CallManualTask";
                ret.addIcon("#manualMarker",-1,-1,9,6);
                ret.setText("Tarea manual reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callruleTask') {
                ret = Modeler.createCallTask(null,null);
                ret.elementType="CallBusinessRuleTask";
                ret.addIcon("#taskRuleMarker",-1,-1,12,12);
                ret.setText("Tarea de regla de negocio reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callscriptTask') {
                ret = Modeler.createCallTask(null,null);
                ret.elementType="CallScriptTask";
                ret.addIcon("#scriptMarker",-1,-1,7,13);
                ret.setText("Tarea de script reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='calluserTask') {
                ret = Modeler.createCallTask(null,null);
                ret.elementType="CallUserTask";
                ret.addIcon("#userMarker",-1,-1,13,8);
                ret.setText("Tarea de usuario reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='subProcess') {
                ret = Modeler.createSubProcess(null, null, "");
                ret.elementType="SubProcess";
                ret.setText("SubProceso",0,0,200,1);
                ret.resize(100,60);
            }
            else if(type=='eventsubProcess') {
                ret = Modeler.createSubProcess(null, null, "eventsubProcess");
                ret.elementType="EventSubProcess";
                ret.setText("SubProceso",0,0,200,1);
                ret.resize(100,60);
            }
            else if(type=='transactionsubProcess') {
                ret = Modeler.createSubProcess(null, null, "transactionsubProcess");
                ret.elementType="TransactionSubProcess";
                ret.setText("SubProceso",0,0,200,1);
                ret.resize(100,60);
            }
            else if (type=="pool") {
                ret = Modeler.createSwimLane(null, null);
                ret.elementType="Pool";
                ret.setText("Pool con un nombre muy largo ajustable a 200 pixeles",-1,0,200,2);
                ret.resize(600,200);
            }
            return ret;
        }
        
    }    
    
    function draw()
    {
        //var obj;

        //obj = ToolKit.createUseObject("#task");

        //obj2 = ToolKit.createUseObject("#endEvent",null,obj);
        //obj2.move(100,200);                

        //for(i=0;i<100;i++)
        //{
            //obj3 = ToolKit.createStartEvent(null, obj2);
            //obj3.move(i,100);
        //}

        //obj = Modeler.createObject("#barra");                

        //obj = Modeler.createObject("#test");                

        obj2 = Modeler.mapObject("normalStartEvent");
        obj2.move(80,20);
        obj2 = Modeler.mapObject("messageStartEvent");
        obj2.move(160,20);
        obj2 = Modeler.mapObject("timerStartEvent");
        obj2.move(240,20);
        obj2 = Modeler.mapObject("ruleStartEvent");
        obj2.move(320,20);
        obj2 = Modeler.mapObject("signalStartEvent");
        obj2.move(400,20);
        obj2 = Modeler.mapObject("multiStartEvent");
        obj2.move(480,20);
        obj2 = Modeler.mapObject("parallelStartEvent");
        obj2.move(560,20);
        obj2 = Modeler.mapObject("scalaStartEvent");
        obj2.move(640,20);
        obj2 = Modeler.mapObject("errorStartEvent");
        obj2.move(720,20);
        obj2 = Modeler.mapObject("compensaStartEvent");
        obj2.move(800,20);

        obj2 = Modeler.mapObject("messageInterCatchEvent");
        obj2.move(80,90);
        obj2 = Modeler.mapObject("messageInterThrowEvent");
        obj2.move(160,90);
        obj2 = Modeler.mapObject("errorInterEvent");
        obj2.move(240,90);
        obj2 = Modeler.mapObject("cancelInterEvent");
        obj2.move(320,90);
        obj2 = Modeler.mapObject("compensaInterCatchEvent");
        obj2.move(400,90);
        obj2 = Modeler.mapObject("compensaInterThrowEvent");
        obj2.move(480,90);
        obj2 = Modeler.mapObject("ruleInterEvent");
        obj2.move(560,90);
        obj2 = Modeler.mapObject("linkInterCatchEvent");
        obj2.move(640,90);
        obj2 = Modeler.mapObject("linkInterThrowEvent");
        obj2.move(720,90);
        obj2 = Modeler.mapObject("signalInterCatchEvent");
        obj2.move(800,90);
        obj2 = Modeler.mapObject("signalInterThrowEvent");
        obj2.move(880,90);
        obj2 = Modeler.mapObject("multipleInterCatchEvent");
        obj2.move(960,90);
        obj2 = Modeler.mapObject("multipleInterThrowEvent");
        obj2.move(1040,90);
        obj2 = Modeler.mapObject("scalaInterCatchEvent");
        obj2.move(1120,90);
        obj2 = Modeler.mapObject("scalaInterThrowEvent");
        obj2.move(1200,90);
        obj2 = Modeler.mapObject("parallelInterEvent");
        obj2.move(1280,90);

        obj2 = Modeler.mapObject("normalEndEvent");
        obj2.move(80,160);
        obj2 = Modeler.mapObject("messageEndEvent");
        obj2.move(160,160);
        obj2 = Modeler.mapObject("errorEndEvent");
        obj2.move(240,160);
        obj2 = Modeler.mapObject("cancelEndEvent");
        obj2.move(320,160);
        obj2 = Modeler.mapObject("compensaEndEvent");
        obj2.move(400,160);
        obj2 = Modeler.mapObject("signalEndEvent");
        obj2.move(480,160);
        obj2 = Modeler.mapObject("multiEndEvent");
        obj2.move(560,160);
        obj2 = Modeler.mapObject("escalaEndEvent");
        obj2.move(640,160);
        obj2 = Modeler.mapObject("terminalEndEvent");
        obj2.move(720,160);

        obj2 = Modeler.mapObject("exclusiveDataGateway");
        obj2.move(80,240);
        obj2 = Modeler.mapObject("inclusiveDataGateway");
        obj2.move(160,240);
        obj2 = Modeler.mapObject("exclusiveStartEventGateway");
        obj2.move(240,240);
        obj2 = Modeler.mapObject("exclusiveEventGateway");
        obj2.move(320,240);
        obj2 = Modeler.mapObject("parallelGateway");
        obj2.move(400,240);
        obj2 = Modeler.mapObject("parallelStartGateway");
        obj2.move(480,240);
        obj2 = Modeler.mapObject("complexGateway");
        obj2.move(560,240);

        obj2 = Modeler.mapObject("dataObject");
        obj2.move(80,320);
        obj2 = Modeler.mapObject("dataInput");
        obj2.move(160,320);
        obj2 = Modeler.mapObject("dataOutput");
        obj2.move(240,320);
        obj2 = Modeler.mapObject("dataStore");
        obj2.move(320,320);

        obj2 = Modeler.mapObject("abstractTask");
        obj2.move(100,420);
        obj2 = Modeler.mapObject("userTask");
        obj2.move(200,420);
        obj2 = Modeler.mapObject("serviceTask");
        obj2.move(300,420);
        obj2 = Modeler.mapObject("scriptTask");
        obj2.move(400,420);
        obj2 = Modeler.mapObject("ruleTask");
        obj2.move(500,420);
        obj2 = Modeler.mapObject("sendTask");
        obj2.move(600,420);
        obj2 = Modeler.mapObject("receiveTask");
        obj2.move(700,420);
        obj2 = Modeler.mapObject("manualTask");
        obj2.move(800,420);
        obj2 = Modeler.mapObject("callTask");
        obj2.move(900,420);
        obj2 = Modeler.mapObject("callmanualTask");
        obj2.move(1000,420);
        obj2 = Modeler.mapObject("callruleTask");
        obj2.move(1100,420);
        obj2 = Modeler.mapObject("callscriptTask");
        obj2.move(1200,420);
        obj2 = Modeler.mapObject("calluserTask");
        obj2.move(1300,420);
        
        obj2 = Modeler.mapObject("pool");
        obj2.move(1300,420);
        
        obj = Modeler.mapObject("pool");
        obj.moveFirst=function(){};
        //obj.mouseup=function(evt){alert("up")};
        obj.move(350,580);
        
        obj2 = Modeler.createTask(null,null);
        obj2.addIcon("#manualMarker",-1,-1,9,6);
        obj2.setText("Tarea Contenida");
        obj2.resize(100,60);
        obj2.move(400,600);        
        obj2.setParent(obj);
/*        
        obj2 = Modeler.mapObject("conditionalFlow");
        obj2.setPoint(0,10,10);
        obj2.setPoint(1,10,20);
        obj2.setPoint(2,30,20);
        obj2.setPoint(3,100,100);
        
        obj2 = Modeler.mapObject("messageFlow");
        obj2.setPoint(0,150,150);
        obj2.setPoint(1,150,350);
        obj2.setPoint(2.260,350);
        obj2.setPoint(3,280,350);
        obj2.addPoint(500,500);
        
        obj2 = Modeler.mapObject("associationFlow");
        obj2.setPoint(0,350,200);
        obj2.setPoint(1,350,250);
        obj2.setPoint(2,400,200);
        obj2.setPoint(3,500,190);
        
        obj2 = Modeler.mapObject("directionalassociationFlow");
        obj2.setPoint(0,350,10);
        obj2.setPoint(1,350,150);
        obj2.setPoint(2,400,100);
        obj2.setPoint(3,430,100);
        obj2.addPoint(400,190);
        obj2.listSegments();
        
*/        
        
        obj1 = Modeler.mapObject("userTask");
        obj1.move(200,700);        
        
        obj2 = Modeler.mapObject("userTask");
        obj2.move(400,800);        
        
        con1 = Modeler.mapObject("sequenceFlow");
        obj1.addOutConnection(con1);
        obj2.addInConnection(con1);
        
        obj1 = Modeler.mapObject("userTask");
        obj1.move(400,700);        
        
        obj2 = Modeler.mapObject("userTask");
        obj2.move(600,800);        
        
        con1 = Modeler.mapObject("defaultFlow");
        obj1.addOutConnection(con1);
        obj2.addInConnection(con1);        
        
        
    }

