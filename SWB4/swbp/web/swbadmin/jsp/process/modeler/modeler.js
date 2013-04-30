/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/***************************Elementos genéricos**************************/
    var _GraphicalElement = function(obj) {
        var _this = obj;
        _this.types = new Array();
        
        _this.setElementType = function(typeName) {
            _this.elementType = typeName;
            _this.types.push(typeName);
        };
        
        _this.typeOf = function(typeName) {
            var ret = false;
            for (var i = 0; i < _this.types.length; i++) {
                if(_this.types[i] == typeName) {
                    ret = true;
                    break;
                }
            }
            return ret;
        };
        
        _this.canStartLink=function(link) {
            return true;
        };
        
        _this.canEndLink=function(link) {
            if(link.fromObject!=_this) {
                return true;
            } else {
                return false;
            }
        };
        
        _this.canAddToDiagram=function() {
            return true;
        };
        
        _this.canAttach=function(parent) {
            return false;
        };
        
        _this.getDefaultFlow = function() {
            return null;
        };
        
        _this.getFirstGraphParent = function() {
            var ret = _this.parent;
            if (ret == null) {
                return _this;
            } else {
                return ret.getFirstGraphParent();
            }
        };
        
        _this.getPool = function() {
            var ret = _this.getFirstGraphParent();
            if (ret != null && ret.elementType=="Pool") {
                return ret;
            }
            return null;
        };
        
        _this.getContainer = function() {
            return _this.layer?_this.layer:null;
        };
        
        _this.setElementType("GraphicalElement");
        
        return _this;
    };
    
    var _ConnectionObject = function(obj) {
        var _this = obj;
        _this.types = new Array();
        
        _this.setElementType = function(typeName) {
            _this.elementType = typeName;
            _this.types.push(typeName);
        };
        
        _this.typeOf = function(typeName) {
            var ret = false;
            for (var i = 0; i < _this.types.length; i++) {
                if(_this.types[i] == typeName) {
                    ret = true;
                    break;
                }
            }
            return ret;
        };
        
      _this.setElementType("ConnectionObject");
      return _this;
    };
    
    var _FlowNode = function (obj) {
        var _this = new _GraphicalElement(obj);
        var fCanEnd = _this.canEndLink;
        _this.setElementType("FlowNode");

        _this.canAttach = function(parent) {
            var ret = false;
            if (parent.elementType=="Pool" || parent.elementType=="Lane") {
                ret = true;
            }
            return ret;
        };
        
        _this.canStartLink = function (link) {
            var ret = true;
            var msg = null;
            
            if (link.elementType=="MessageFlow") {
                msg = "Un nodo de flujo no puede tener flujos de mensaje salientes";
                ret = false;
            }
            
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            var msg = null;
            
            if (link.elementType=="SequenceFlow") {
                if (link.fromObject.getContainer() == null && link.fromObject.getPool() !== _this.getPool()) {
                    msg = "Un flujo de secuencia no puede cruzar los límites del Pool";
                    ret = false;
                }
            }
            
            if (link.elementType=="AssociationFlow") {
                if (!(link.fromObject.elementType=="Artifact" || link.fromObject.elementType=="DataObject") && !(link.fromObject.elementType=="CompensationIntermediateCatchEvent" && link.fromObject.parent && link.fromObject.parent.typeOf("Activity"))) {
                    msg = "Un flujo de asociación no puede conectar dos nodos de flujo";
                    ret = false;
                }
            }
            
            if (link.elementType=="MessageFlow") {
                if (link.fromObject.getPool() == _this.getPool()) {
                    msg = "Un flujo de mensage sólo puede darse entre pools";
                    ret = false;
                }
            }
            
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    /***************************Objetos de conexión**************************/
    
    var _SequenceFlow = function(obj) {
      var _this = new _ConnectionObject(obj);
      _this.setElementType("SequenceFlow");
      return _this;
    };
    
    var _MessageFlow = function(obj) {
      var _this = new _ConnectionObject(obj);
      _this.setElementType("MessageFlow");
      return _this;
    };
    
    var _ConditionalFlow = function(obj) {
      var _this = new _SequenceFlow(obj);
      _this.setElementType("ConditionalFlow");
      return _this;
    };
    
    var _DefaultFlow = function(obj) {
      var _this = new _SequenceFlow(obj);
      _this.setElementType("DefaultFlow");
      return _this;
    };
    
    var _AssociationFlow = function(obj) {
      var _this = new _ConnectionObject(obj);
      _this.setElementType("AssociationFlow");
      return _this;
    };
    
    var _DirectionalAssociation = function(obj) {
      var _this = new _AssociationFlow(obj);
      _this.setElementType("DirectionalAssociation");
      return _this;
    };
    
    /***************************Eventos iniciales****************************/
    var _Event = function (obj) {
        var _this = new _FlowNode(obj);
        var fCanStart = _this.canStartLink;
        
        _this.setElementType("Event");

        _this.canStartLink=function(link) {
            var ret = fCanStart(link);
            if (ret && (link.elementType=="ConditionalFlow" || link.elementType=="DefaultFlow")) {
                ToolKit.showTooltip(0, "Un evento no puede tener flujos condicionales de salida", 200, "Error");
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _CatchEvent = function(obj) {
        var _this = new _Event(obj);
        _this.setElementType("CatchEvent");
        
        return _this;
    };
    
    var _ThrowEvent = function(obj) {
        var _this = new _Event(obj);
        _this.setElementType("ThrowEvent");

        var fCanEnd = _this.canEndLink;
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (ret && link.fromObject.typeOf("EventBasedGateway")) {
                ToolKit.showTooltip(0, "Un evento disparador no puede conectarse con una compuerta basada en eventos", 200, "Error");
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _StartEventNode = function (obj) {
        var _this = new _CatchEvent(obj);
        
        _this.setElementType("StartEventNode");
        
        _this.canEndLink=function(link) {
            ToolKit.showTooltip(0, "Un evento inicial no puede tener flujos entrantes", 200, "Error");
            return false;
        };
        
        _this.canAddToDiagram = function() {
            var ret = true;
            var c = 0;
            var msg = null;
            
            if (ToolKit.layer != null) {
                for (var i = ToolKit.contents.length; i--;)  {
                    if(ToolKit.contents[i].layer===_this.layer && ToolKit.contents[i].typeOf && ToolKit.contents[i].typeOf("StartEventNode")) {
                        c++;
                    }
                }
                
                if (c != 1) {
                    ret = false;
                    msg = "Un subproceso no puede tener más de un evento de inicio";
                } else if (ToolKit.layer.parent.elementType=="AdhocSubProcess") {
                    ret = false;
                    msg = "Un subproceso ad-hoc no puede tener eventos de inicio";
                }
                
                if (msg!=null) {
                    ToolKit.showTooltip(0, msg, 200, "Error");
                }
            }
            
            return ret;
        };
        
        return _this;
    };
    
    var _StartEvent = function(obj) {
        var _this = new _StartEventNode(obj);
        var fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("StartEvent");
        
        _this.canAddToDiagram=function() {
            var ret = fCanAdd();
            var msg = null;
            
            if (ret && ToolKit.layer != null) {
                if (ToolKit.layer.parent.elementType=="EventSubProcess") {
                    ret = false;
                    msg = "Un subproceso de evento no puede contener un evento de inicio normal";
                }
            }
            
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _MessageStartEvent = function (obj) {
        var _this = new _StartEventNode(obj);
        var fCanEnd = _this.canEndLink;
        var fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("MessageStartEvent");
        
        _this.canEndLink=function(link) {
            var ret = fCanEnd(link);
            
            if (link.elementType=="MessageFlow") {
                if (_this.inConnections.length==0) {
                    ToolKit.hideToolTip();
                    ret = true;
                } else {
                    ToolKit.showTooltip(0, "Un evento inicial de mensage no puede tener más de un flujo entrante", 200, "Error");
                    ret = false;
                }
            } else {
                ToolKit.showTooltip(0, "Un evento inicial de mensage sólo puede tener flujos de mensaje entrantes", 200, "Error");
                ret = false;
            }
            return ret;
        };
        
        _this.canAddToDiagram=function() {
            var ret = fCanAdd();
            var msg = null;
            
            if (ret && ToolKit.layer != null) {
                if (ToolKit.layer.parent.elementType!="EventSubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _TimerStartEvent = function(obj) {
        var _this = new _StartEventNode(obj);
        var fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("TimerStartEvent");
        
        _this.canAddToDiagram = function () {
            var ret = fCanAdd();
            var msg = null;
            
            if (ret && ToolKit.layer != null) {
                ret = false;
                
                if (ToolKit.layer.parent.elementType=="SubProcess") {
                    msg = "Un subproceso debe iniciar con un evento normal";
                }
                if (ToolKit.layer.parent.elementType=="EventSubProcess") {
                    msg = "Un subproceso de evento no puede iniciar con un evento temporizado";
                }
            }

            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        return _this;
    };
    
    var _RuleStartEvent = function(obj) {
        var _this = new _StartEventNode(obj);
        var fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("RuleStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fCanAdd();
            var msg = null;
            
            if (ret && ToolKit.layer != null) {
                if (ToolKit.layer.parent.elementType=="SubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }

                if (ToolKit.layer.parent.elementType=="EventSubProcess") {
                    ret = true;
                }
            }
            
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _SignalStartEvent = function(obj) {
        var _this = new _StartEventNode(obj);
        var fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("SignalStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fCanAdd();
            var msg = null;
            
            if (ret && ToolKit.layer != null) {
                if (ToolKit.layer.parent.elementType!="EventSubProcess") {
                    msg = "Un subproceso debe iniciar con un evento normal";
                    ret = false;
                }

                if (ToolKit.layer.parent.elementType=="AdhocSubProcess") {
                    ret = false;
                    msg = "Un subproceso ad-hoc no debe tener eventos de inicio";
                }
            
                if (msg!=null) {
                    ToolKit.showTooltip(0, msg, 200, "Error");
                }
            }
            return ret;
        };
        return _this;
    };
    
    var _MultipleStartEvent = function (obj) {
        var _this = new _StartEventNode(obj);
        var fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("MultipleStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fCanAdd();
            var msg = null;
            
            if (ret && ToolKit.layer != null) {
                if (ToolKit.layer.parent.elementType=="SubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }

                if (ToolKit.layer.parent.elementType=="EventSubProcess") {
                    ret = true;
                }
            }
            
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _ParallelStartEvent = function(obj) {
        var _this = new _StartEventNode(obj);
        var fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("ParallelStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fCanAdd();
            var msg = null;
            
            if (ret && ToolKit.layer != null) {
                if (ToolKit.layer.parent.elementType=="SubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }
                
                if (ret && ToolKit.layer.parent.elementType=="EventSubProcess") {
                    msg = "Un subproceso de evento no puede contener un evento de inicio paralelo";
                    ret = false;
                }
            }
            
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _ScalationStartEvent = function(obj) {
        var _this = new _StartEventNode(obj);
        var fcanAdd = _this.canAddToDiagram;
        
        _this.setElementType("ScalationStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fcanAdd();
            var msg = null;
            
            if (ToolKit.layer == null) {
                msg = "Este evento no puede usarse en un proceso de nivel superior";
                ret = false;
            } else if (ret && ToolKit.layer != null) {
                if (ToolKit.layer.parent.elementType=="SubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }
                
                if (ToolKit.layer.parent.elementType=="EventSubProcess") {
                    ret = true;
                }
            }
            
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _ErrorStartEvent = function(obj) {
        var _this = new _StartEventNode(obj);
        var fcanAdd = _this.canAddToDiagram;
        
        _this.setElementType("ErrorStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fcanAdd();
            var msg = null;
            
            if (ToolKit.layer == null) {
                msg = "Este evento no puede usarse en un proceso de nivel superior";
                ret = false;
            } else if (ret && ToolKit.layer != null) {
                if (ToolKit.layer.parent.elementType=="SubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }

                if (ToolKit.layer.parent.elementType=="EventSubProcess") {
                    ret = true;
                }
            }
            
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _CompensationStartEvent = function(obj) {
        var _this = new _StartEventNode(obj);
        var fcanAdd = _this.canAddToDiagram;
        
        _this.setElementType("CompensationStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fcanAdd();
            var msg = null;
            
            if (ToolKit.layer == null) {
                msg = "Este evento no puede usarse en un proceso de nivel superior";
                ret = false;
            } else if (ret && ToolKit.layer != null) {
                if (ToolKit.layer.parent.elementType=="SubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }

                if (ToolKit.layer.parent.elementType=="EventSubProcess") {
                    ret = true;
                }
            }
            
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    /***************************Eventos intermedios**************************/
    var _IntermediateCatchEvent = function (obj) {
        var _this = new _CatchEvent(obj);
        var fCanAttach = _this.canAttach;
        var fCanEnd = _this.canEndLink;
        var fCanStart = _this.canStartLink;
        
        _this.setElementType("IntermediateCatchEvent");
        
        _this.canAttach = function(parent) {
            var ret = fCanAttach(parent);
            var msg = null;
            
            if (ret || (parent.typeOf && parent.typeOf("Activity") && _this.inConnections.length==0)) {
                ret = true;
            }
            
            if (parent.typeOf && parent.typeOf("CallActivity")) {
                msg = "Este evento no puede adherirse a una actividad llamada";
                ret = false;
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canEndLink = function (link) {
            var ret = fCanEnd(link);
            var msg = null;
            
            if (ret && link.elementType=="MessageFlow") {
                msg = "Un evento intermedio no puede tener flujos de mensaje entrantes";
                ret = false;
            }
            
            if (ret && link.elementType=="SequenceFlow") {
                if (ret && _this.parent && _this.parent != null && _this.parent.typeOf("Activity")) {
                    msg = "Un evento adherido no puede tener flujos de secuencia entrantes";
                    ret = false;
                } else {
                    var c = 0;
                    for (var i = 0; i < _this.inConnections.length; i++) {
                        if (_this.inConnections[i].elementType=="SequenceFlow") {
                            c++;
                        }
                    }

                    if (c != 0) {
                        msg = "Un evento intermedio sólo puede tener un flujo de secuencia entrante";
                        ret = false;
                    }
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            var msg = null;
            
            if (ret && link.elementType=="SequenceFlow") {
                var c = 0;
                for (var i = 0; i < _this.outConnections.length; i++) {
                    if (_this.outConnections[i].elementType=="SequenceFlow") {
                        c++;
                    }
                }

                if (c != 0) {
                    msg = "Un evento intermedio sólo puede tener un flujo de secuencia saliente";
                    ret = false;
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _IntermediateThrowEvent = function (obj) {
        var _this = new _ThrowEvent(obj);
        var fCanEnd = _this.canEndLink;
        var fCanStart = _this.canStartLink;
        var msg = null;
        
        _this.setElementType("IntermediateThrowEvent");
        
        _this.canEndLink = function (link) {
            var ret = fCanEnd(link);
            var msg = null;
            console.log(ret);
            if (ret && link.elementType=="MessageFlow") {
                msg = "Un evento intermedio no puede tener flujos de mensaje entrantes";
                ret = false;
            }
            
            if (ret && link.elementType == "SequenceFlow") {
                var c = 0;
                for (var i = 0; i < _this.inConnections.length; i++) {
                    if (_this.inConnections[i].elementType=="SequenceFlow") {
                        c++;
                    }
                }
                
                if (c != 0) {
                    msg = "Un evento intermedio sólo puede tener un flujo de secuencia entrante";
                    ret = false;
                }
            }

            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            var c = 0;
            
            var msg = null;
            
            if (ret && link.elementType=="SequenceFlow") {
                for (var i = 0; i < _this.outConnections.length; i++) {
                    if (_this.outConnections[i].elementType=="SequenceFlow") {
                        c++;
                    }
                }
                if (c != 0) {
                    msg = "Un evento intermedio sólo puede tener un flujo de secuencia saliente";
                    ret = false;
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _MessageIntermediateCatchEvent = function(obj) {
        var _this = new _IntermediateCatchEvent(obj);
        var fCanEnd = _this.canEndLink;
        var fCanStart = _this.canStartLink;
        
        _this.setElementType("MessageIntermediateCatchEvent");
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            var msg = null;
            
            if (ret && link.elementType=="SequenceFlow" && link.fromObject.elementType=="ExclusiveIntermediateEventGateway") {
                for (var i = 0; i < link.fromObject.outConnections.length; i++) {
                    if (link.fromObject.outConnections[i].elementType=="SequenceFlow" && link.fromObject.outConnections[i].toObject && link.fromObject.outConnections[i].toObject.elementType=="ReceiveTask") {
                        ret = false;
                        break;
                    }
                }
            } else if (ret || link.elementType=="MessageFlow") {
                msg = null;
                ToolKit.hideToolTip();
                
                var c = 0;
                for (var i = 0; i < _this.inConnections.length; i++) {
                    if (_this.inConnections[i].elementType=="MessageFlow") {
                        c++;
                    }
                }
                
                if (c != 0) {
                    msg = "Un evento intermedio de mensaje sólo puede tener un flujo de mensaje entrante";
                    ret = false;
                } else {
                    ret = true;
                }
            }

            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canStartLink = function (link) {
            var ret = fCanStart(link);
            var msg = null;
            
            if (link.elementType=="MessageFlow") {
                msg = "Un evento intermedio receptor de mensaje no puede tener flujos de mensaje salientes";
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _ErrorIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        var fCanEnd = _this.canEndLink;
        
        _this.setElementType("ErrorIntermediateCatchEvent");
        
        _this.canEndLink = function (link) {
            var ret = fCanEnd(link);
            var msg = null;
            
            if (ret) {
                msg = "Un evento intermedio de error no puede tener flujos de secuencia entrantes";
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return false;
        };
        
        return _this;
    };
    
    var _TimerIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        _this.setElementType("TimerIntermediateCatchEvent");
        return _this;
    };
    
    var _CancelationIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        var fCanAttach = _this.canAttach;
        var fCanEnd = _this.canEndLink;
        
        _this.setElementType("CancelationIntermediateCatchEvent");
        
        _this.canAttach = function(parent) {
            var ret = fCanAttach(parent);
            var msg = null;
            
            if (ret && parent.elementType!="TransactionSubProcess") {
                msg = "Un evento de cancelación sólo puede adherirse a una Transacción";
                ret = false;
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canEndLink = function (link) {
            var ret = fCanEnd(link);
            var msg = null;
            
            if (ret) {
                msg = "Un evento intermedio de cancelación no puede tener flujos de secuencia entrantes";
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return false;
        };
        
        return _this;
    };
    
    var _CompensationIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        var fCanStart = _this.canStartLink;
        
        _this.setElementType("CompensationIntermediateCatchEvent");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            var msg = null;
            
            if (ret && _this.parent && _this.parent != null && _this.parent.typeOf("Activity") && link.elementType != "DirectionalAssociation") {
                msg = "Este evento adherido sólo puede conectarse mediante asociaciones direccionales";
                ret = false;
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _LinkIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        var fCanAttach = _this.canAttach;
        
        _this.setElementType("LinkIntermediateCatchEvent");
        
        _this.canEndLink = function(link) {
            ToolKit.showTooltip(0, "Un evento receptor de enlace no puede tener flujos entrantes", 200, "Error");
            return false;
        };
        
        _this.canAttach = function(parent) {
            var ret = fCanAttach(parent);
            if (ret && parent.typeOf("Activity")) {
                ToolKit.showTooltip(0, "Un evento de enlace no puede adherirse a una actividad", 200, "Error");
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _SignalIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        _this.setElementType("SignalIntermediateCatchEvent");
        
        return _this;
    };
    
    var _MultipleIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        _this.setElementType("MultipleIntermediateCatchEvent");
        return _this;
    };
    
    var _ScalationIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        var fCanEnd = _this.canEndLink;
        
        _this.setElementType("ScalationIntermediateCatchEvent");
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (ret && link.elementType=="SequenceFlow" && link.fromObject.typeOf("EventBasedGateway")) {
                ToolKit.showTooltip(0, "Una evento receptor de de escalamiento no puede conectarse con una compuerta basada en eventos", 200, "Error");
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _ParallelIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        var fCanAttach = _this.canAttach;
        var fCanEnd = _this.canEndLink;
        
        _this.setElementType("ParallelIntermediateCatchEvent");
        
        _this.canAttach = function (parent) {
            var ret = fCanAttach(parent);
            if (ret && parent.typeOf("Activity")) {
                ToolKit.showTooltip(0, "Un evento intermedio paralelo no puede adherirse a una actividad", 200, "Error");
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.fromObject.typeOf("EventBasedGateway")) {
                ToolKit.showTooltip(0, "Un evento intermedio paralelo no puede conectarse con una compuerta basada en eventos", 200, "Error");
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _RuleIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj);
        _this.setElementType("RuleIntermediateCatchEvent");
        return _this;
    };
    
    var _MessageIntermediateThrowEvent = function(obj) {
        var _this = new _IntermediateThrowEvent(obj);
        var fCanStart = _this.canStartLink;
        
        _this.setElementType("MessageIntermediateThrowEvent");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            var msg = null;
            
            if (ret || link.elementType=="MessageFlow") {
                if (link.elementType=="MessageFlow") {
                    var c = 0;
                    for (var i = 0; i < _this.outConnections.length; i++) {
                        if (_this.outConnections[i].elementType=="MessageFlow") {
                            c++;
                        }
                    }

                    if (c == 0) {
                        ret = true;
                    } else {
                        msg = "Un evento disparador de mensaje no puede tener más de un flujo de mensaje saliente";
                    }
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        return _this;
    };
    
    var _CompensationIntermediateThrowEvent = function(obj) {
        var _this = new _IntermediateThrowEvent(obj);
        _this.setElementType("CompensationIntermediateThrowEvent");
        return _this;
    };
    
    var _LinkIntermediateThrowEvent = function(obj) {
        var _this = new _IntermediateThrowEvent(obj);
        
        _this.setElementType("LinkIntermediateThrowEvent");
        
        _this.canStartLink = function(link) {
            ToolKit.showTooltip(0, "Un evento disparador de enlace no puede tener flujos de salida", 200, "Error");
            return false;
        };
        return _this;
    };
    
    var _SignalIntermediateThrowEvent = function(obj) {
        var _this = new _IntermediateThrowEvent(obj);
        _this.setElementType("SignalIntermediateThrowEvent");
        return _this;
    };
    
    var _MultipleIntermediateThrowEvent = function(obj) {
        var _this = new _IntermediateThrowEvent(obj);
        _this.setElementType("MultipleIntermediateThrowEvent");
        return _this;
    };
    
    var _ScalationIntermediateThrowEvent = function(obj) {
        var _this = new _IntermediateThrowEvent(obj);
        _this.setElementType("ScalationIntermediateThrowEvent");
        return _this;
    };
    
    /***************************Eventos finales****************************/
    var _EndEventNode = function(obj) {
        var _this = new _ThrowEvent(obj);
        var fCanEnd = _this.canEndLink;
        var fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("EndEventNode");
        
        _this.canStartLink = function(link) {
            ToolKit.showTooltip("","Un evento final sólo puede tener flujos de secuencia entrantes", 250, "Error")
            return false;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            
            if (ret && link.elementType=="MessageFlow") {
                ToolKit.showTooltip("","Un evento final sólo puede tener flujos de secuencia entrantes", 250, "Error")
                ret = false;
            }
            return ret;
        };
        
        _this.canAddToDiagram = function() {
            var ret = fCanAdd();
            var msg = null;
            
            if (ret) {
                if (ToolKit.layer != null && ToolKit.layer.parent.elementType=="AdhocSubProcess") {
                    msg = "Un subproceso ad-hoc no puede tener eventos finales";
                    ret = false;
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
        };
        return _this;
    };
    
    var _EndEvent = function(obj) {
        var _this = new _EndEventNode(obj);
        _this.setElementType("EndEvent");
        return _this;
    };
    
    var _MessageEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        var fCanStart = _this.canStartLink;
        
        _this.setElementType("MessageEndEvent");

        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            var msg = null;

            if (ret || link.elementType=="MessageFlow") {
                if (link.elementType=="MessageFlow") {
                    ToolKit.hideToolTip();
                    ret = true;
                    var c = 0;

                    for (var i = 0; i < _this.outConnections.length; i++) {
                        if (_this.outConnections[i].elementType=="MessageFlow") {
                            c++;
                        }
                    }

                    if (c != 0) {
                        ret = false;
                        msg = "Un evento final de mensaje no puede tener más de un flujo de mensaje saliente";
                    }
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _ErrorEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.setElementType("ErrorEndEvent");
        return _this;
    };
    
    var _CancelationEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.setElementType("CancelationEndEvent");
        return _this;
    };
    
    var _CompensationEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.setElementType("CompensationEndEvent");
        return _this;
    };
    
    var _SignalEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.setElementType("SignalEvent");
        return _this;
    };
    
    var _MultipleEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.setElementType("MultipleEndEvent");
        return _this;
    };
    
    var _ScalationEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.setElementType("ScalationEndEvent");
        return _this;
    };
    
    var _TerminationEndEvent = function (obj) {
        var _this = new _EndEvent(obj);
        _this.setElementType("TerminationEndEvent");
        return _this;
    };
    
    /******************************Compuertas*******************************/
    var _Gateway = function (obj) {
        var _this = new _FlowNode(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canEndLink;
        
        _this.setElementType("Gateway");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            var ci = 0; 
            var co = 0;
            var msg = null;
            
            if (ret && link.elementType=="SequenceFlow") {
                for (var i = 0; i < _this.inConnections.length; i++) {
                    if (_this.inConnections[i].elementType=="SequenceFlow") {
                        ci++;
                    }
                }
                
                for (var i = 0; i < _this.outConnections.length; i++) {
                    if (_this.outConnections[i].elementType=="SequenceFlow") {
                        co++;
                    }
                }
                
                if (ci > 1 && co != 0) {
                    msg = "Una compuerta convergente sólo puede tener un flujo de secuencia saliente";
                    ret = false;
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            var ci = 0; 
            var co = 0;
            var msg = null;
            
            if (ret && link.elementType=="SequenceFlow") {
                if (link.fromObject.typeOf("EventBasedGateway")) {
                    msg = "Esta compuerta no puede conectarse con una compuerta basada en eventos"
                    ret = false;
                } else {
                    for (var i = 0; i < _this.inConnections.length; i++) {
                        if (_this.inConnections[i].elementType=="SequenceFlow") {
                            ci++;
                        }
                    }

                    for (var i = 0; i < _this.outConnections.length; i++) {
                        if (_this.outConnections[i].elementType=="SequenceFlow") {
                            co++;
                        }
                    }

                    if (co > 1 && ci != 0) {
                        msg = "Una compuerta divergente sólo puede tener un flujo de secuencia entrante";
                        ret = false;
                    }
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _ExclusiveGateway = function (obj) {
        var _this = new _Gateway(obj);
        var fCanStart = _this.canStartLink;
        
        _this.setElementType("ExclusiveGateway");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            var msg = null;
            
            if (ret && !(link.elementType=="ConditionalFlow" || link.elementType=="DefaultFlow" || link.typeOf("AssociationFlow"))) {
                msg = "Una compuerta exclusiva sólo puede conectarse usando flujos condicionales, flujos por defecto o asociaciones";
                ret = false;
            } else if (ret && (link.elementType=="DefaultFlow" || link.elementType=="ConditionalFlow")) {
                var dou = 0;
                var co = 0;
                var ci = 0;
                
                for (var i = 0; i < _this.outConnections.length; i++) {
                    if (_this.outConnections[i].elementType=="DefaultFlow") {
                        dou++;
                    }
                    if (_this.outConnections[i].elementType=="ConditionalFlow") {
                        co++;
                    }
                }
                
                co = co+dou;
                
                for (var i = 0; i < _this.inConnections.length; i++) {
                    if (!_this.inConnections[i].typeOf("AssociationFlow")) {
                        ci++;
                    }
                }

                if (link.elementType=="DefaultFlow" && dou > 0) {
                    msg = "Una compuerta exclusiva sólo puede tener un flujo por defecto saliente";
                    ret = false;
                } else if (ci > 1 && co != 0) {
                    msg = "Una compuerta convergente sólo puede tener un flujo saliente";
                    ret = false;
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = true;
            var msg = null;
            var co = 0;
            var ci = 0;

            if (link.fromObject.typeOf("EventBasedGateway")) {
                msg = "Esta compuerta no puede conectarse con una compuerta basada en eventos"
                ret = false;
            } else {
                for (var i = 0; i < _this.outConnections.length; i++) {
                    if (!_this.outConnections[i].typeOf("AssociationFlow")) {
                        co++;
                    }
                }

                for (var i = 0; i < _this.inConnections.length; i++) {
                    if (!_this.inConnections[i].typeOf("AssociationFlow")) {
                        ci++;
                    }
                }
                if (ci > 0 && co != 0) {
                    msg = "Una compuerta divergente sólo puede tener un flujo entrante";
                    ret = false;
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _EventBasedGateway = function(obj) {
        var _this = new _Gateway(obj);
        var fCanStart = _this.canStartLink;
        
        _this.setElementType("EventBasedGateway");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            var msg = null;
            
            if (ret && (link.elementType=="ConditionalFlow" || link.elementType=="DefaultFlow")) {
                msg = "Una compuerta basada en eventos no puede conectarse usando flujos condicionales";
                ret = false;
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _ParallelStartEventGateway = function(obj) {
        var _this = new _EventBasedGateway(obj);
        _this.setElementType("ParallelStartEventGateway");
        return _this;
    };
    
    var _InclusiveGateway = function(obj) {
        var _this = new _Gateway(obj);
        var fCanStart = _this.canStartLink;
        
        _this.setElementType("InclusiveGateway");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            var msg = null;
            
            if (ret && !(link.elementType=="ConditionalFlow" || link.elementType=="DefaultFlow" || link.typeOf("AssociationFlow"))) {
                msg = "Una compuerta inclusiva sólo puede conectarse usando flujos condicionales, flujos por defecto o asociaciones";
                ret = false;
            } else if (ret && (link.elementType=="DefaultFlow" || link.elementType=="ConditionalFlow")) {
                var dou = 0;
                var co = 0;
                var ci = 0;
                
                for (var i = 0; i < _this.outConnections.length; i++) {
                    if (_this.outConnections[i].elementType=="DefaultFlow") {
                        dou++;
                    }
                    if (_this.outConnections[i].elementType=="ConditionalFlow") {
                        co++;
                    }
                }
                
                co = co+dou;
                
                for (var i = 0; i < _this.inConnections.length; i++) {
                    if (!_this.inConnections[i].typeOf("AssociationFlow")) {
                        ci++;
                    }
                }

                if (link.elementType=="DefaultFlow" && dou > 0) {
                    msg = "Una compuerta inclusiva sólo puede tener un flujo por defecto saliente";
                    ret = false;
                } else if (ci > 1 && co != 0) {
                    msg = "Una compuerta convergente sólo puede tener un flujo saliente";
                    ret = false;
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = true;
            var msg = null;
            var co = 0;
            var ci = 0;

            if (link.fromObject.typeOf("EventBasedGateway")) {
                msg = "Esta compuerta no puede conectarse con una compuerta basada en eventos"
                ret = false;
            } else {
                for (var i = 0; i < _this.outConnections.length; i++) {
                    if (!_this.outConnections[i].typeOf("AssociationFlow")) {
                        co++;
                    }
                }

                for (var i = 0; i < _this.inConnections.length; i++) {
                    if (!_this.inConnections[i].typeOf("AssociationFlow")) {
                        ci++;
                    }
                }
                if (ci > 0 && co != 0) {
                    msg = "Una compuerta divergente sólo puede tener un flujo entrante";
                    ret = false;
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _ExclusiveStartEventGateway = function (obj) {
        var _this = new _EventBasedGateway(obj);
        _this.setElementType("ExclusiveStartEventGateway");
        return _this;
    };
    
    var _ExclusiveIntermediateEventGateway = function (obj) {
        var _this = new _EventBasedGateway(obj);
        _this.setElementType("ExclusiveIntermediateEventGateway");
        return _this;
    };
    
    var _ComplexGateway = function (obj) {
        var _this = new _Gateway(obj);
        _this.setElementType("ComplexGateway");
        return _this;
    };
    
    var _ParallelGateway = function (obj) {
        var _this = new _Gateway(obj);
        var fCanStart = _this.canStartLink;
        
        _this.setElementType("ParallelGateway");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.elementType=="ConditionalFlow" || link.elementType=="DefaultFlow") {
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = true;
            var msg = null;
            var co = 0;
            var ci = 0;

            if (link.fromObject.typeOf("EventBasedGateway")) {
                msg = "Esta compuerta no puede conectarse con una compuerta basada en eventos"
                ret = false;
            } else {
                for (var i = 0; i < _this.outConnections.length; i++) {
                    if (!_this.outConnections[i].typeOf("AssociationFlow")) {
                        co++;
                    }
                }

                for (var i = 0; i < _this.inConnections.length; i++) {
                    if (!_this.inConnections[i].typeOf("AssociationFlow")) {
                        ci++;
                    }
                }
                if (ci > 0 && co != 0) {
                    msg = "Una compuerta divergente sólo puede tener un flujo entrante";
                    ret = false;
                }
            }
            if (msg!=null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    /***************************Objetos de datos*******************************/
    var _DataObject = function(obj) {
        var _this = new _GraphicalElement(obj);
        var fCanAttach = _this.canAttach;
        var fCanEnd = _this.canEndLink;
        var fCanStart = _this.canStartLink;
        
        _this.setElementType("DataObject");
        
        _this.canAttach = function(parent) {
            var ret = fCanAttach(parent);
            if(parent.elementType=="Pool" || parent.elementType=="Lane") {
                ret = true;
            }
            return ret;
        };
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.elementType!="AssociationFlow") {
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (!link.typeOf("AssociationFlow")) {
                ret = false;
            } else if (link.fromObject.typeOf("DataObject")) {
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _DataInput = function (obj) {
        var _this = new _DataObject(obj);
        _this.setElementType("DataInput");
        return _this;
    };
    
    var _DataOutput = function (obj) {
        var _this = new _DataObject(obj);
        _this.setElementType("DataOutput");
        return _this;
    };
    
    var _DataStore = function (obj) {
        var _this = new _DataObject(obj);
        _this.setElementType("DataStore");
        return _this;
    };
    
    /***************************Artefactos*******************************/
    var _Artifact = function(obj) {
        var _this = new _GraphicalElement(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canEndLink;
        
        _this.setElementType("Artifact");
        
        _this.canAttach = function(parent) {
            var ret = false;
            if (parent.elementType=="Pool" || parent.elementType=="Lane") {
                ret = true;
            }
            return ret;
        };
        
        _this.canStartLink = function (link) {
            var ret = fCanStart(link);
            if (link.elementType!="AssociationFlow") {
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.elementType!="AssociationFlow") {
                ret = false;
            } else if (link.fromObject.typeOf("Artifact") || link.fromObject.typeOf("DataObject")) {
                
            }
            return ret;
        };
        return _this;
    };
    
    var _AnnotationArtifact = function(obj) {
        var _this = new _Artifact(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canEndLink;
        
        _this.setElementType("AnnotationArtifact");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.elementType=="DirectionalAssociation") {
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.fromObject.typeOf("Artifact")) {
                ret = false;
            } else if (link.elementType=="DirectionalAssociation") {
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _Group = function(obj) {
        var _this = new _Artifact(obj);
        
        _this.setElementType("GroupArtifact");
        _this.canStartLink = function(link) {
            return false;
        };
        
        _this.canEndLink = function(link) {
            return false;
        };
        
        return _this;
    };
    
    /******************************Swimlanes*******************************/
    var _Pool = function (obj) {
        var _this = new _GraphicalElement(obj);
        var fCanAdd = _this.canAddToDiagram;
        _this.setElementType("Pool");
        
        _this.canStartLink = function(link) {
            var ret = true;
            if (link.elementType=="SequenceFlow") {
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = true;
            if (link.elementType=="SequenceFlow") {
                ret = false;
            }
            if (link.elementType=="MessageFlow") {
                //TODO:Implementar
            }
            if (link.elementType=="AssociationFlow") {
                ret = false;
            }
            return ret;
        };
        
        _this.canAddToDiagram = function() {
            var ret = fCanAdd();
            //TODO:Implementar
            return ret;
        };
        return _this;
    };
    
    /******************************Actividades*******************************/
    var _Activity = function(obj) {
        var _this = new _FlowNode(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canEndLink;
        
        _this.setElementType("Activity");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.elementType=="DefaultFlow") {
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.elementType=="DirectionalAssociation" && link.fromObject.elementType=="CompensationIntermediateCatchEvent") {
                ret = true;            
            } else if (link.fromObject.elementType=="ExclusiveIntermediateEventGateway") {
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _CallActivity = function(obj) {
        var _this = new _Activity(obj);
        _this.setElementType("CallActivity");
        return _this;
    };
    
    var _Task = function(obj) {
        var _this = new _Activity(obj);
        _this.setElementType("Task");
        return _this;
    };
    
    var _CallTask = function(obj) {
        var _this = new _CallActivity(obj);
        _this.setElementType("CallTask");
        return _this;
    };
    
    var _UserTask = function(obj) {
        var _this = new _Task(obj);
        _this.setElementType("UserTask");
        return _this;
    };
    
    var _ServiceTask = function(obj) {
        var _this = new _Task(obj);
        _this.setElementType("ServiceTask");
        return _this;
    };
    
    var _BusinessRuleTask = function(obj) {
        var _this = new _Task(obj);
        _this.setElementType("BusinessRuleTask");
        return _this;
    };
    
    var _CallManualTask = function(obj) {
        var _this = new _CallTask(obj);
        _this.setElementType("CallManualTask");
        return _this;
    };
    
    var _CallBusinessRuleTask = function(obj) {
        var _this = new _CallTask(obj);
        _this.setElementType("CallBusinessRuleTask");
        return _this;
    };
    
    var _CallScriptTask = function(obj) {
        var _this = new _CallTask(obj);
        _this.setElementType("CallScriptTask");
        return _this;
    };
    
    var _CallUserTask = function(obj) {
        var _this = new _CallTask(obj);
        _this.setElementType("CallUserTask");
        return _this;
    };
    
    var _ScriptTask = function(obj) {
        var _this = new _Task(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canEndLink;
        
        _this.setElementType("ScriptTask");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.elementType=="MessageFlow") {
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.elementType=="MessageFlow") {
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _SendTask = function(obj) {
        var _this = new _Task(obj);
        var fCanEnd = _this.canEndLink;
        
        _this.setElementType("SendTask");
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.elementType=="MessageFlow") {
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _ReceiveTask = function(obj) {
        var _this = new _Task(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canEndLink;
        
        _this.setElementType("ReceiveTask");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.elementType=="MessageFlow") {
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.elementType=="SequenceFlow" && link.fromObject.elementType=="ExclusiveIntermediateEventGateway") {
                ret = true;
                for (var i = 0; i < link.fromObject.outConnections.length; i++) {
                    if (link.fromObject.outConnections[i].elementType=="SequenceFlow" && link.fromObject.outConnections[i].toObject && link.fromObject.outConnections[i].toObject.elementType=="MessageIntermediateCatchEvent") {
                        ret = false;
                        break;
                    }
                }
            }
            return ret;
        };
        return _this;
    };
    
    var _ManualTask = function(obj) {
        var _this = new _Task(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canEndLink;
        
        _this.setElementType("ManualTask");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.elementType=="MessageFlow") {
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.elementType=="MessageFlow") {
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _SubProcess = function(obj) {
        var _this = new _Activity(obj);
        _this.setElementType("SubProcess");
        return _this;
    };
    
    var _AdhocSubProcess = function(obj) {
        var _this = new _SubProcess(obj);
        _this.setElementType("AdhocSubProcess");
        return _this;
    };
    
    var _TransactionSubProcess = function(obj) {
        var _this = new _SubProcess(obj);
        _this.setElementType("TransactionSubProcess");
        return _this;
    };
    
    var _EventSubProcess = function(obj) {
        var _this = new _SubProcess(obj);
        var fCanStart = _this.canStartLink;
        var fCanEnd = _this.canStartLink;
        
        _this.setElementType("EventSubProcess");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.typeOf("SequenceFlow")) {
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.typeOf("SequenceFlow")) {
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    /*****************************Barra de herramientas************************/
    
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

    };
    
    /**********************************Modeler*******************************/
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
                    if (obj.canAddToDiagram()) {
                        obj.move(ToolKit.getEventX(evt), ToolKit.getEventY(evt));
                        obj.snap2Grid();
                    } else {
                        obj.remove();
                    }
                }else   //Es un ConnectionObject
                {
                    if(Modeler.creationDropObject!=null)
                    {
                        Modeler.dragConnection=obj;//Modeler.mapObject(Modeler.creationId);
                        if (Modeler.creationDropObject.canStartLink(Modeler.dragConnection)) {
                            Modeler.creationDropObject.addOutConnection(Modeler.dragConnection);
                            Modeler.dragConnection.setEndPoint(Modeler.creationDropObject.getX(),Modeler.creationDropObject.getY());                        
                        } else {
                            Modeler.dragConnection.remove();
                            Modeler.dragConnection = null;
                        }
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
                    if (Modeler.dragConnection.toObject.canEndLink(Modeler.dragConnection)) {
                        Modeler.dragConnection.toObject.addInConnection(Modeler.dragConnection);
                    } else {
                        Modeler.dragConnection.remove();
                    }
                }
                Modeler.dragConnection=null;
            }
            ToolKit.hideToolTip();
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
                if (obj.canStartLink(Modeler.dragConnection)) {
                    obj.addOutConnection(Modeler.dragConnection);
                    Modeler.dragConnection.setEndPoint(obj.getX(),obj.getY());
                } else {
                    Modeler.dragConnection.remove();
                    Modeler.dragConnection = null;
                }
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
                    if (obj.canEndLink(Modeler.dragConnection)) {
                        ToolKit.unSelectAll();
                        ToolKit.selectObj(obj,true);
                        Modeler.dragConnection.toObject=obj;
                        Modeler.dragConnection.setEndPoint(obj.getX(),obj.getY());
                    } else {
                        Modeler.dragConnection.setEndPoint(ToolKit.getEventX(evt), ToolKit.getEventY(evt));
                    }
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
                    
                    obj.subSquare.onmousedown=function(evt)
                    {
                        obj.onmousedown(evt);
                    };
                    obj.subSquare.onmouseup=function(evt)
                    {
                        obj.onmouseup(evt);
                    };      
                    obj.subSquare.onmousemove=function(evt)
                    {
                        obj.onmousemove(evt);
                    };
                    obj.subSquare.ondblclick=function(evt)
                    {
                        obj.ondblclick(evt);
                    };
                    
                    obj.subSquare.onDropObjects=function(objs) {
                        obj.onDropObjects(objs);
                    };
                    
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
                ret = new _SequenceFlow(Modeler.createConnectionPath(null, null, "sequenceArrow", null,"sequenceFlowLine"));
                ret.eoff=10;
            }
            if(type=='messageFlow') {
                ret = new _MessageFlow(Modeler.createConnectionPath("messageTail", null, "messageArrow", "5,5", "sequenceFlowLine"));
                ret.eoff=5;
                ret.soff=10;
            }
            if(type=='conditionalFlow') {
                ret = new _ConditionalFlow(Modeler.createConnectionPath("conditionTail", null, "sequenceArrow", null, "sequenceFlowLine"));
                ret.eoff=10;
                ret.soff=10;
            }
            if(type=='defaultFlow') {
                ret = new _DefaultFlow(Modeler.createConnectionPath("defaultTail", null, "sequenceArrow", null, "sequenceFlowLine"));
                ret.eoff=10;
            }
            if(type=='associationFlow') {
                ret = new _AssociationFlow(Modeler.createConnectionPath(null, null, null, "5,5", "sequenceFlowLine"));
            }
            if(type=='directionalassociationFlow') {
                ret = new _DirectionalAssociation(Modeler.createConnectionPath(null, null, "messageArrow", "5,5", "sequenceFlowLine"));
                ret.eoff=10;
            }
            else if(type=='normalStartEvent')
            {
                ret=new _StartEvent(Modeler.createObject("#startEvent",null,null));
                ret.setText("Inicio Normal",0,1,80,1);
            }
            else if(type=='messageStartEvent'){
                ret=new _MessageStartEvent(Modeler.createObject("#messageStartEvent",null,null));
                ret.setText("Inicio por mensaje",0,1,80,1);
            }
            else if(type=='timerStartEvent'){
                ret=new _TimerStartEvent(Modeler.createObject("#timerStartEvent",null,null));
                ret.setText("Inicio temporizado",0,1,80,1);
            }
            else if(type=='ruleStartEvent') {
                ret=new _RuleStartEvent(Modeler.createObject("#ruleStartEvent",null,null));
                ret.setText("Inicio por regla de negocio",0,1,80,1);
            }
            else if(type=='signalStartEvent') {
                ret=new _SignalStartEvent(Modeler.createObject("#signalStartEvent",null,null));
                ret.setText("Inicio por señal",0,1,80,1);
            }
            else if(type=='multiStartEvent') {
                ret=new _MultipleStartEvent(Modeler.createObject("#multipleStartEvent",null,null));
                ret.setText("Inicio múltiple",0,1,80,1);
            }
            else if(type=='parallelStartEvent') {
                ret=new _ParallelStartEvent(Modeler.createObject("#parallelStartEvent",null,null));
                ret.setText("Inicio paralelo",0,1,80,1);
            }
            else if(type=='scalaStartEvent') {
                ret= new _ScalationStartEvent(Modeler.createObject("#scalationStartEvent",null,null));
                ret.setText("Inicio por escalamiento",0,1,80,1);
            }
            else if(type=='errorStartEvent') {
                ret= new _ErrorStartEvent(Modeler.createObject("#errorStartEvent",null,null));
                ret.setText("Inicio por error",0,1,80,1);
            }
            else if(type=='compensaStartEvent') {
                ret= new _CompensationStartEvent(Modeler.createObject("#compensationStartEvent",null,null));
                ret.setText("Inicio por compensación",0,1,80,1);
            }
            else if(type=='messageInterCatchEvent') {
                ret=new _MessageIntermediateCatchEvent(Modeler.createObject("#messageIntermediateCatchEvent",null,null));
                ret.setText("Recepción de mensaje",0,1,80,1);
            }
            else if(type=='messageInterThrowEvent') {
                ret= new _MessageIntermediateThrowEvent(Modeler.createObject("#messageIntermediateThrowEvent",null,null));
                ret.setText("Envío de mensaje",0,1,80,1);
            }
            else if(type=='timerInterEvent') {
                ret= new _TimerIntermediateCatchEvent(Modeler.createObject("#timerIntermediateEvent",null,null));
                ret.setText("Temporizador",0,1,80,1);
            }
            else if(type=='errorInterEvent') {
                ret= new _ErrorIntermediateCatchEvent(Modeler.createObject("#errorIntermediateEvent",null,null));
                ret.setText("Recepción de error",0,1,80,1);
            }
            else if(type=='cancelInterEvent') {
                ret= new _CancelationIntermediateCatchEvent(Modeler.createObject("#cancelIntermediateEvent",null,null));
                ret.setText("Cancelación",0,1,80,1);
            }
            else if(type=='compensaInterCatchEvent') {
                ret= new _CompensationIntermediateCatchEvent(Modeler.createObject("#compensationIntermediateCatchEvent",null,null));
                ret.setText("Recepción de compensación",0,1,80,1);
            }
            else if(type=='compensaInterThrowEvent') {
                ret= new _CompensationIntermediateThrowEvent(Modeler.createObject("#compensationIntermediateThrowEvent",null,null));
                ret.setText("Disparo de compensación",0,1,80,1);
            }
            else if(type=='ruleInterEvent') {
                ret= new _RuleIntermediateCatchEvent(Modeler.createObject("#ruleIntermediateEvent",null,null));
                ret.setText("Regla de negocio",0,1,80,1);
            }
            else if(type=='linkInterCatchEvent') {
                ret= new _LinkIntermediateCatchEvent(Modeler.createObject("#linkIntermediateCatchEvent",null,null));
                ret.setText("Recepción de enlace",0,1,80,1);
            }
            else if(type=='linkInterThrowEvent') {
                ret= new _LinkIntermediateThrowEvent(Modeler.createObject("#linkIntermediateThrowEvent",null,null));
                ret.setText("Disparo de enlace",0,1,80,1);
            }
            else if(type=='signalInterCatchEvent') {
                ret= new _SignalIntermediateCatchEvent(Modeler.createObject("#signalIntermediateCatchEvent",null,null));
                ret.setText("Recepción de señal",0,1,80,1);
            }
            else if(type=='signalInterThrowEvent') {
                ret= new _SignalIntermediateThrowEvent(Modeler.createObject("#signalIntermediateThrowEvent",null,null));
                ret.setText("Disparo de señal",0,1,80,1);
            }
            else if(type=='multipleInterCatchEvent') {
                ret= new _MultipleIntermediateCatchEvent(Modeler.createObject("#multipleIntermediateCatchEvent",null,null));
                ret.setText("Recepción múltiple",0,1,80,1);
            }
            else if(type=='multipleInterThrowEvent') {
                ret= new _MultipleIntermediateThrowEvent(Modeler.createObject("#multipleIntermediateThrowEvent",null,null));
                ret.setText("Disparo múltiple",0,1,80,1);
            }
            else if(type=='scalaInterCatchEvent') {
                ret= new _ScalationIntermediateCatchEvent(Modeler.createObject("#scalationIntermediateCatchEvent",null,null));
                ret.setText("Recepción de escalamiento",0,1,80,1);
            }
            else if(type=='scalaInterThrowEvent') {
                ret= new _ScalationIntermediateThrowEvent(Modeler.createObject("#scalationIntermediateThrowEvent",null,null));
                ret.setText("Disparo de escalamiento",0,1,80,1);
            }
            else if(type=='parallelInterEvent') {
                ret= new _ParallelIntermediateCatchEvent(Modeler.createObject("#parallelIntermediateEvent",null,null));
                ret.setText("Paralelo",0,1,80,1);
            }
            else if(type=='normalEndEvent') {
                ret= new _EndEvent(Modeler.createObject("#endEvent",null,null));
                ret.setText("Fin normal",0,1,80,1);
            }
            else if(type=='messageEndEvent') {
                ret= new _MessageEndEvent(Modeler.createObject("#messageEndEvent",null,null));
                ret.setText("Fin con mensaje",0,1,80,1);
            }
            else if(type=='errorEndEvent') {
                ret= new _ErrorEndEvent(Modeler.createObject("#errorEndEvent",null,null));
                ret.setText("Fin con error",0,1,80,1);
            }
            else if(type=='cancelEndEvent') {
                ret= new _CancelationEndEvent(Modeler.createObject("#cancelationEndEvent",null,null));
                ret.setText("Fin con cancelación",0,1,80,1);
            }
            else if(type=='compensaEndEvent') {
                ret= new _CompensationEndEvent(Modeler.createObject("#compensationEndEvent",null,null));
                ret.setText("Fin con compensación",0,1,80,1);
            }
            else if(type=='signalEndEvent') {
                ret= new _SignalEndEvent(Modeler.createObject("#signalEndEvent",null,null));
                ret.setText("Fin con señal",0,1,80,1);
            }
            else if(type=='multiEndEvent') {
                ret= new _MultipleEndEvent(Modeler.createObject("#multipleEndEvent",null,null));
                ret.setText("Fin múltiple",0,1,80,1);
            }
            else if(type=='escalaEndEvent') {
                ret= new _ScalationEndEvent(Modeler.createObject("#scalationEndEvent",null,null));
                ret.setText("Fin con escalamiento",0,1,80,1);
            }
            else if(type=='terminalEndEvent') {
                ret= new _TerminationEndEvent(Modeler.createObject("#terminationEndEvent",null,null));
                ret.setText("Terminación",0,1,80,1);
            }
            else if(type=='exclusiveDataGateway') {
                ret= new _ExclusiveGateway(Modeler.createObject("#exclusiveDataGateway",null,null));
                ret.setText("Exclusiva (datos)",0,1,80,1);
            }
            else if(type=='inclusiveDataGateway') {
                ret= new _InclusiveGateway(Modeler.createObject("#inclusiveDataGateway",null,null));
                ret.setText("Inclusiva (datos)",0,1,80,1);
            }
            else if(type=='exclusiveStartEventGateway') {
                ret= new _ExclusiveStartEventGateway(Modeler.createObject("#exclusiveStartGateway",null,null));
                ret.setText("Exclusiva de inicio",0,1,80,1);
            }
            else if(type=='exclusiveEventGateway') {
                ret= new _ExclusiveIntermediateEventGateway(Modeler.createObject("#eventGateway",null,null));
                ret.setText("Exclusiva (eventos)",0,1,80,1);
            }
            else if(type=='parallelGateway') {
                ret= new _ParallelGateway(Modeler.createObject("#parallelGateway",null,null));
                ret.setText("Paralela",0,1,80,1);
            }
            else if(type=='parallelStartGateway') {
                ret= new _ParallelStartEventGateway(Modeler.createObject("#parallelStartGateway",null,null));
                ret.setText("Paralela de inicio",0,1,80,1);
            }
            else if(type=='complexGateway') {
                ret= new _ComplexGateway(Modeler.createObject("#complexGateway",null,null));
                ret.setText("Compleja",0,1,80,1);
            }
            else if(type=='group') {
                ret= new _Group(Modeler.createGroupArtifact(null,null));
                ret.resize(300,300);
            }
            else if(type=='annotation'){
                ret= new _AnnotationArtifact(Modeler.createAnnotationArtifact(null, null));
                ret.setText("Anotación de texto",0,0,0,1);
                ret.resize(200,60);
            }
            else if(type=='dataObject') {
                ret= new _DataObject(Modeler.createObject("#data",null,null));
                ret.setText("Dato",0,1,80,1);
            }
            else if(type=='dataInput') {
                ret= new _DataInput(Modeler.createObject("#dataInput",null,null));
                ret.setText("Dato de entrada",0,1,80,1);
            }
            else if(type=='dataOutput') {
                ret= new _DataOutput(Modeler.createObject("#dataOutput",null,null));
                ret.setText("Dato de salida",0,1,80,1);
            }
            else if(type=='dataStore') {
                ret= new _DataStore(Modeler.createObject("#dataStore",null,null));
                ret.setText("Almacén de datos",0,1,80,1);
            }
            else if(type=='userTask') {
                ret = new _UserTask(Modeler.createTask(null,null));
                ret.addIcon("#userMarker",-1,-1,13,8);
                ret.setText("Tarea de Usuario",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='serviceTask') {
                ret = new _ServiceTask(Modeler.createTask(null,null));
                ret.addIcon("#serviceMarker",-1,-1,13,8);
                ret.setText("Tarea de Servicio",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='scriptTask') {
                ret = new _ScriptTask(Modeler.createTask(null,null));
                ret.addIcon("#scriptMarker",-1,-1,7,13);
                ret.setText("Tarea de Script",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='ruleTask') {
                ret = new _BusinessRuleTask(Modeler.createTask(null,null));
                ret.addIcon("#taskRuleMarker",-1,-1,12,12);
                ret.setText("Tarea de regla de negocio",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='manualTask') {
                ret = new _ManualTask(Modeler.createTask(null,null));
                ret.addIcon("#manualMarker",-1,-1,9,6);
                ret.setText("Tarea Manual",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='sendTask') {
                ret = new _SendTask(Modeler.createTask(null,null));
                ret.addIcon("#taskMessageThrowMarker",-1,-1,13,10);
                ret.setText("Tarea de envío de mensaje",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='receiveTask') {
                ret = new _ReceiveTask(Modeler.createTask(null,null));
                ret.addIcon("#taskMessageCatchMarker",-1,-1,13,10);
                ret.setText("Tarea de recepción de mensaje",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='abstractTask') {
                ret = new _Task(Modeler.createTask(null,null));
                ret.setText("Tarea abstracta",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callTask') {
                ret = new _CallTask(Modeler.createCallTask(null,null));
                ret.setText("Tarea reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callmanualTask') {
                ret = new _CallManualTask(Modeler.createCallTask(null,null));
                ret.addIcon("#manualMarker",-1,-1,9,6);
                ret.setText("Tarea manual reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callruleTask') {
                ret = new _CallBusinessRuleTask(Modeler.createCallTask(null,null));
                ret.addIcon("#taskRuleMarker",-1,-1,12,12);
                ret.setText("Tarea de regla de negocio reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callscriptTask') {
                ret = new _CallScriptTask(Modeler.createCallTask(null,null));
                ret.addIcon("#scriptMarker",-1,-1,7,13);
                ret.setText("Tarea de script reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='calluserTask') {
                ret = new _CallBusinessRuleTask(Modeler.createCallTask(null,null));
                ret.addIcon("#userMarker",-1,-1,13,8);
                ret.setText("Tarea de usuario reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='subProcess') {
                ret = new _SubProcess(Modeler.createSubProcess(null, null, ""));
                ret.setText("Subproceso",0,0,200,1);
                ret.resize(100,60);
            }
            else if(type=='adhocsubProcess') {
                ret = new _AdhocSubProcess(Modeler.createSubProcess(null, null, ""));
                ret.setText("Subproceso adhoc",0,0,200,1);
                ret.resize(100,60);
            }
            else if(type=='eventsubProcess') {
                ret = new _EventSubProcess(Modeler.createSubProcess(null, null, "eventsubProcess"));
                ret.setText("Subproceso de evento",0,0,200,1);
                ret.resize(100,60);
            }
            else if(type=='transactionsubProcess') {
                ret = new _TransactionSubProcess(Modeler.createSubProcess(null, null, "transactionsubProcess"));
                ret.setText("Transacción",0,0,200,1);
                ret.resize(100,60);
            }
            else if (type=="pool") {
                ret = new _Pool(Modeler.createSwimLane(null, null));
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
        //obj.moveFirst=function(){};
        //obj.mouseup=function(evt){alert("up")};
        obj.move(350,580);
        
        obj2 = Modeler.mapObject("manualTask");
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

