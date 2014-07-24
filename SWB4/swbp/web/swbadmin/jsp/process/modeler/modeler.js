/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/***************************Elementos genéricos**************************/
    var _GraphicalElement = function(obj) {
        var _this = obj;
        _this.types = [];
        _this.id = ":"+Modeler.itemsCount++;
        
        _this.setElementType = function(typeName) {
            _this.elementType = typeName;
            _this.types.push(typeName);
            var c = _this.id.substring(_this.id.indexOf(":"), _this.id.length);
            _this.id = _this.elementType+c;
        };
        
        _this.setURI = function(uri) {
            _this.id = uri;
        };
        
        _this.typeOf = function(typeName) {
            return _this.types.indexOf(typeName) > -1;
        };
        
        _this.canStartLink=function(link) {
            return true;
        };
        
        _this.canEndLink=function(link) {
            return link.fromObject !== _this;
        };
        
        _this.canAddToDiagram=function() {
            return true;
        };
        
        _this.canAttach=function(parent) {
            return false;
        };
        
        _this.getDefaultFlow = function() {
            return "SequenceFlow";
        };
        
        _this.getFirstGraphParent = function() {
            return _this.parent === null ? _this : _this.parent.getFirstGraphParent();
        };
        
        _this.getPool = function() {
            var p = _this.getFirstGraphParent();
            return (p !== null && p.elementType==="Pool") ? p : null;
        };
        
        _this.getContainer = function() {
            return _this.layer?_this.layer.parent:null;
        };
        
        _this.setElementType("GraphicalElement");
        
        return _this;
    };
    
    var _ConnectionObject = function(obj) {
        var _this = obj;
        _this.types = [];
        _this.id = ":"+Modeler.itemsCount++;
        
        _this.setElementType = function(typeName) {
            _this.elementType = typeName;
            _this.types.push(typeName);
            var c = _this.id.substring(_this.id.indexOf(":"), _this.id.length);
            _this.id = _this.elementType+c;
        };
        
        _this.setURI = function(uri) {
            _this.id = uri;
        };
        
        _this.typeOf = function(typeName) {
            return _this.types.indexOf(typeName) > -1;
        };
        
      _this.setElementType("ConnectionObject");
      return _this;
    };
    
    var _FlowNode = function (obj) {
        var _this = new _GraphicalElement(obj),
            fCanEnd = _this.canEndLink;
    
        _this.setElementType("FlowNode");

        _this.canAttach = function(parent) {
            return parent.elementType==="Pool" || parent.elementType==="Lane";
        };
        
        _this.canStartLink = function (link) {
            var ret = true,
                msg = null;
            
            if (link.elementType==="MessageFlow") {
                msg = "Un nodo de flujo no puede tener flujos de mensaje salientes";
                ret = false;
            }
            
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link),
                msg = null,
                from = link.fromObject,
                etype = link.elementType,
                fromtype = from.elementType,
                fromPool = from.getPool(),
                thisPool = _this.getPool();
            
            if (etype==="SequenceFlow") {
                if (from.getContainer() === null && fromPool !== thisPool) {
                    msg = "Un flujo de secuencia no puede cruzar los límites del Pool";
                    ret = false;
                }
            }
            
            if (etype==="AssociationFlow" && fromtype!=="AnnotationArtifact") {
                if (!(fromtype==="Artifact" || fromtype==="DataObject") && !(fromtype==="CompensationIntermediateCatchEvent" && from.parent && from.parent.typeOf("Activity"))) {
                    msg = "Un flujo de asociación no puede conectar dos nodos de flujo";
                    ret = false;
                }
            }
            
            if (etype==="MessageFlow") {
                if (fromPool === thisPool) {
                    msg = "Un flujo de mensage sólo puede darse entre pools";
                    ret = false;
                }
            }
            
            if (msg!==null) {
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
        var _this = new _FlowNode(obj),
            fCanStart = _this.canStartLink,
            fSetText = _this.setText;
        
        _this.setElementType("Event");

        _this.setText = function(text) {
            fSetText(text,0,1,80,1);
        };

        _this.canStartLink=function(link) {
            var ret = fCanStart(link),
                etype = link.elementType;
        
            if (ret && (etype==="ConditionalFlow" || etype==="DefaultFlow")) {
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
        var _this = new _Event(obj),
            fCanEnd = _this.canEndLink;
        
        _this.setElementType("ThrowEvent");
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
            var ret = true,
                c = 0,
                msg = null,
                contents = ToolKit.contents,
                i, t;
            
            if (ToolKit.layer !== null) {
                for (i = contents.length; i--;)  {
                    t = contents[i];
                    if(t.layer===_this.layer && t.typeOf && t.typeOf("StartEventNode")) {
                        c++;
                    }
                }
                
                if (c !== 1) {
                    ret = false;
                    msg = "Un subproceso no puede tener más de un evento de inicio";
                } else if (ToolKit.layer.parent.elementType==="AdhocSubProcess") {
                    ret = false;
                    msg = "Un subproceso ad-hoc no puede tener eventos de inicio";
                }
                
                if (msg!==null) {
                    ToolKit.showTooltip(0, msg, 200, "Error");
                }
            }
            
            return ret;
        };
        
        return _this;
    };
    
    var _StartEvent = function(obj) {
        var _this = new _StartEventNode(obj),
            fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("StartEvent");
        
        _this.canAddToDiagram=function() {
            var ret = fCanAdd(),
                msg = null;
            
            if (ret && ToolKit.layer !== null) {
                if (ToolKit.layer.parent.elementType==="EventSubProcess") {
                    ret = false;
                    msg = "Un subproceso de evento no puede contener un evento de inicio normal";
                }
            }
            
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _MessageStartEvent = function (obj) {
        var _this = new _StartEventNode(obj),
            fCanEnd = _this.canEndLink,
            fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("MessageStartEvent");
        
        _this.canEndLink=function(link) {
            var ret = fCanEnd(link);
            
            if (link.elementType==="MessageFlow") {
                if (_this.inConnections.length===0) {
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
            var ret = fCanAdd(),
                msg = null,
                layer = ToolKit.layer;
            
            if (ret && layer !== null) {
                if (layer.parent.elementType!=="EventSubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _TimerStartEvent = function(obj) {
        var _this = new _StartEventNode(obj),
            fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("TimerStartEvent");
        
        _this.canAddToDiagram = function () {
            var ret = fCanAdd(),
                msg = null,
                layer = ToolKit.layer;
            
            if (ret && layer !== null) {
                ret = false;
                
                if (layer.parent.elementType==="SubProcess") {
                    msg = "Un subproceso debe iniciar con un evento normal";
                }
                if (layer.parent.elementType==="EventSubProcess") {
                    msg = "Un subproceso de evento no puede iniciar con un evento temporizado";
                }
            }

            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        return _this;
    };
    
    var _RuleStartEvent = function(obj) {
        var _this = new _StartEventNode(obj),
            fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("RuleStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fCanAdd(),
                msg = null,
                layer = ToolKit.layer;
            
            if (ret && layer !== null) {
                if (layer.parent.elementType==="SubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }

                if (layer.parent.elementType==="EventSubProcess") {
                    ret = true;
                }
            }
            
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _SignalStartEvent = function(obj) {
        var _this = new _StartEventNode(obj),
            fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("SignalStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fCanAdd(),
                msg = null,
                layer = ToolKit.layer;
            
            if (ret && layer !== null) {
                if (layer.parent.elementType!=="EventSubProcess") {
                    msg = "Un subproceso debe iniciar con un evento normal";
                    ret = false;
                }

                if (layer.parent.elementType==="AdhocSubProcess") {
                    ret = false;
                    msg = "Un subproceso ad-hoc no debe tener eventos de inicio";
                }
            
                if (msg!==null) {
                    ToolKit.showTooltip(0, msg, 200, "Error");
                }
            }
            return ret;
        };
        return _this;
    };
    
    var _MultipleStartEvent = function (obj) {
        var _this = new _StartEventNode(obj),
            fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("MultipleStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fCanAdd(),
                msg = null,
                layer = ToolKit.layer;
            
            if (ret && layer !== null) {
                if (layer.parent.elementType==="SubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }

                if (layer.parent.elementType==="EventSubProcess") {
                    ret = true;
                }
            }
            
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _ParallelStartEvent = function(obj) {
        var _this = new _StartEventNode(obj),
            fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("ParallelStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fCanAdd(),
                msg = null,
                layer = ToolKit.layer;
            
            if (ret && layer !== null) {
                if (layer.parent.elementType==="SubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }
                
                if (ret && layer.parent.elementType==="EventSubProcess") {
                    msg = "Un subproceso de evento no puede contener un evento de inicio paralelo";
                    ret = false;
                }
            }
            
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _ScalationStartEvent = function(obj) {
        var _this = new _StartEventNode(obj),
            fcanAdd = _this.canAddToDiagram;
        
        _this.setElementType("ScalationStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fcanAdd(),
                msg = null,
                layer = ToolKit.layer;
            
            if (layer === null) {
                msg = "Este evento no puede usarse en un proceso de nivel superior";
                ret = false;
            } else if (ret && layer !== null) {
                if (layer.parent.elementType==="SubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }
                
                if (layer.parent.elementType==="EventSubProcess") {
                    ret = true;
                }
            }
            
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _ErrorStartEvent = function(obj) {
        var _this = new _StartEventNode(obj),
            fcanAdd = _this.canAddToDiagram;
        
        _this.setElementType("ErrorStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fcanAdd(),
                msg = null,
                layer = ToolKit.layer;
            
            if (layer === null) {
                msg = "Este evento no puede usarse en un proceso de nivel superior";
                ret = false;
            } else if (ret && layer !== null) {
                if (layer.parent.elementType==="SubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }

                if (layer.parent.elementType==="EventSubProcess") {
                    ret = true;
                }
            }
            
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _CompensationStartEvent = function(obj) {
        var _this = new _StartEventNode(obj),
            fcanAdd = _this.canAddToDiagram;
        
        _this.setElementType("CompensationStartEvent");

        _this.canAddToDiagram = function () {
            var ret = fcanAdd(),
                msg = null,
                layer = ToolKit.layer;
            
            if (layer === null) {
                msg = "Este evento no puede usarse en un proceso de nivel superior";
                ret = false;
            } else if (ret && layer !== null) {
                if (layer.parent.elementType==="SubProcess") {
                    ret = false;
                    msg = "Un subproceso debe iniciar con un evento normal";
                }

                if (layer.parent.elementType==="EventSubProcess") {
                    ret = true;
                }
            }
            
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    /***************************Eventos intermedios**************************/
    var _IntermediateCatchEvent = function (obj) {
        var _this = new _CatchEvent(obj),
            fCanAttach = _this.canAttach,
            fCanEnd = _this.canEndLink,
            fCanStart = _this.canStartLink;
    
        _this.cssClass = "intermediateEvent";
        
        _this.setInterruptor = function(interrupt) {
            _this.class = interrupt ? "intermediateInterruptingEvent" : "intermediateEvent";
            _this.setBaseClass();
        };

         _this.setBaseClass = function() {
            _this.setAttributeNS(null,"class",_this.cssClass);
        };
        
        _this.setOverClass = function() {
            _this.setAttributeNS(null,"class",_this.cssClass+"_o");
        };
        
        _this.setElementType("IntermediateCatchEvent");
        
        _this.canAttach = function(parent) {
            var ret = fCanAttach(parent),
                msg = null;
            
            if (ret || (parent.typeOf && parent.typeOf("Activity") && _this.inConnections.length===0)) {
                ret = true;
            }
            
            if (parent.typeOf && parent.typeOf("CallActivity")) {
                msg = "Este evento no puede adherirse a una actividad llamada";
                ret = false;
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canEndLink = function (link) {
            var ret = fCanEnd(link),
                msg = null,
                etype = link.elementType,
                p = _this.parent;
            
            if (ret && link.typeOf("AssociationFlow") && link.fromObject.typeOf("DataObject")) {
                ret = false;
            } else if (ret && etype==="MessageFlow") {
                msg = "Un evento intermedio no puede tener flujos de mensaje entrantes";
                ret = false;
            }
            
            if (ret && etype==="SequenceFlow") {
                if (ret && p && p !== null && p.typeOf("Activity")) {
                    msg = "Un evento adherido no puede tener flujos de secuencia entrantes";
                    ret = false;
                } else {
                    var c = 0,
                        i,
                        inConnections = _this.inConnections || [],
                        length = inConnections.length;
                
                    for (i = 0; i < length; i++) {
                        if (inConnections[i].elementType==="SequenceFlow") {
                            c++;
                        }
                    }

                    if (c !== 0) {
                        msg = "Un evento intermedio sólo puede tener un flujo de secuencia entrante";
                        ret = false;
                    }
                }
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link),
                msg = null;
            
            if (ret && link.elementType==="SequenceFlow") {
                var c = 0,
                    i,
                    outConnections = _this.outConnections || [],
                    length = outConnections.length;
                
                for (i = 0; i < length; i++) {
                    if (outConnections[i].elementType==="SequenceFlow") {
                        c++;
                    }
                }

                if (c !== 0) {
                    msg = "Un evento intermedio sólo puede tener un flujo de secuencia saliente";
                    ret = false;
                }
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _IntermediateThrowEvent = function (obj) {
        var _this = new _ThrowEvent(obj),
            fCanEnd = _this.canEndLink,
            fCanStart = _this.canStartLink;
        
        _this.setElementType("IntermediateThrowEvent");
        
        _this.canEndLink = function (link) {
            var ret = fCanEnd(link),
                msg = null,
                etype = link.elementType;
            
            if (ret && link.typeOf("AssociationFlow") && link.fromObject.typeOf("DataObject")) {
                ret = false;
            } else if (ret && etype==="MessageFlow") {
                msg = "Un evento intermedio no puede tener flujos de mensaje entrantes";
                ret = false;
            }
            
            if (ret && etype === "SequenceFlow") {
                var c = 0, i,
                    inConnections = _this.inConnections || [],
                    length = inConnections.length;
                for (i = 0; i < length; i++) {
                    if (inConnections[i].elementType==="SequenceFlow") {
                        c++;
                    }
                }
                
                if (c !== 0) {
                    msg = "Un evento intermedio sólo puede tener un flujo de secuencia entrante";
                    ret = false;
                }
            }

            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link),
                c = 0, i,
                etype = link.elementType;
            
            var msg = null;
            if (etype==="MessageFlow") {
                ToolKit.hideToolTip();
            }
            if (ret && etype==="SequenceFlow") {
                var outConnections = _this.outConnections || [],
                    length = outConnections.length;
                for (i = 0; i < length; i++) {
                    if (outConnections[i].elementType==="SequenceFlow") {
                        c++;
                    }
                }
                if (c !== 0) {
                    msg = "Un evento intermedio sólo puede tener un flujo de secuencia saliente";
                    ret = false;
                }
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _MessageIntermediateCatchEvent = function(obj) {
        var _this = new _IntermediateCatchEvent(obj),
            fCanEnd = _this.canEndLink,
            fCanStart = _this.canStartLink;
        
        _this.setElementType("MessageIntermediateCatchEvent");
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link),
                msg = null,
                etype = link.elementType,
                i;
            
            if (ret && etype==="SequenceFlow" && etype==="ExclusiveIntermediateEventGateway") {
                var outConnections = link.fromObject.outConnections || [],
                    length = outConnections.length;
                for (i = 0; i < length; i++) {
                    if (outConnections[i].elementType==="SequenceFlow" && outConnections[i].toObject && outConnections[i].toObject.elementType==="ReceiveTask") {
                        ret = false;
                        break;
                    }
                }
            } else if (ret || etype==="MessageFlow") {
                msg = null;
                ToolKit.hideToolTip();
                
                var c = 0,
                    inConnections = _this.inConnections || [],
                    length = inConnections.length;
                for (i = 0; i < length; i++) {
                    if (inConnections[i].elementType==="MessageFlow") {
                        c++;
                    }
                }
                
                if (c !== 0) {
                    msg = "Un evento intermedio de mensaje sólo puede tener un flujo de mensaje entrante";
                    ret = false;
                } else {
                    ret = true;
                }
            }

            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canStartLink = function (link) {
            var ret = fCanStart(link),
                msg = null;
            
            if (link.elementType==="MessageFlow") {
                msg = "Un evento intermedio receptor de mensaje no puede tener flujos de mensaje salientes";
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _ErrorIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj),
            fCanEnd = _this.canEndLink;
        
        _this.setElementType("ErrorIntermediateCatchEvent");
        
        _this.canEndLink = function (link) {
            var ret = fCanEnd(link),
                msg = null;
            
            if (ret) {
                msg = "Un evento intermedio de error no puede tener flujos de secuencia entrantes";
            }
            if (msg!==null) {
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
        var _this = new _IntermediateCatchEvent(obj),
            fCanAttach = _this.canAttach,
            fCanEnd = _this.canEndLink;
        
        _this.setElementType("CancelationIntermediateCatchEvent");
        
        _this.canAttach = function(parent) {
            var ret = fCanAttach(parent),
                msg = null;
            
            if (ret && parent.elementType!=="TransactionSubProcess") {
                msg = "Un evento de cancelación sólo puede adherirse a una Transacción";
                ret = false;
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canEndLink = function (link) {
            var ret = fCanEnd(link),
                msg = null;
            
            if (ret) {
                msg = "Un evento intermedio de cancelación no puede tener flujos de secuencia entrantes";
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return false;
        };
        return _this;
    };
    
    var _CompensationIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj),
            fCanStart = _this.canStartLink;
        
        _this.setElementType("CompensationIntermediateCatchEvent");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link),
                msg = null,
                p = _this.parent;
            
            if (ret && p && p !== null && p.typeOf("Activity") && link.elementType !== "DirectionalAssociation") {
                msg = "Este evento adherido sólo puede conectarse mediante asociaciones direccionales";
                ret = false;
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _LinkIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj),
            fCanAttach = _this.canAttach;
        
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
        var _this = new _IntermediateCatchEvent(obj),
            fCanEnd = _this.canEndLink;
        
        _this.setElementType("ScalationIntermediateCatchEvent");
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (ret && link.elementType==="SequenceFlow" && link.fromObject.typeOf("EventBasedGateway")) {
                ToolKit.showTooltip(0, "Una evento receptor de de escalamiento no puede conectarse con una compuerta basada en eventos", 200, "Error");
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _ParallelIntermediateCatchEvent = function (obj) {
        var _this = new _IntermediateCatchEvent(obj),
            fCanAttach = _this.canAttach,
            fCanEnd = _this.canEndLink;
        
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
        var _this = new _IntermediateThrowEvent(obj),
            fCanStart = _this.canStartLink;
        
        _this.setElementType("MessageIntermediateThrowEvent");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link),
                msg = null,
                etype = link.elementType;
            
            if (ret || etype==="MessageFlow") {
                if (etype==="MessageFlow") {
                    var c = 0, i,
                        outConnections = _this.outConnections || [],
                        length = outConnections.length;
                    for (i = 0; i < length; i++) {
                        if (outConnections[i].elementType==="MessageFlow") {
                            c++;
                        }
                    }

                    if (c === 0) {
                        ret = true;
                    } else {
                        msg = "Un evento disparador de mensaje no puede tener más de un flujo de mensaje saliente";
                    }
                    //TODO:Checar 
                }
            }
            if (msg!==null) {
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
        var _this = new _ThrowEvent(obj),
            fCanEnd = _this.canEndLink,
            fCanAdd = _this.canAddToDiagram;
        
        _this.setElementType("EndEventNode");
        
        _this.canStartLink = function(link) {
            ToolKit.showTooltip(0,"Un evento final sólo puede tener flujos de secuencia entrantes", 250, "Error")
            return false;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            
            if (ret && link.elementType==="MessageFlow") {
                ToolKit.showTooltip(0,"Un evento final sólo puede tener flujos de secuencia entrantes", 250, "Error");
                ret = false;
            } else if (ret && link.typeOf("AssociationFlow") && link.fromObject.typeOf("DataObject")) {
                ret = false;
            }
            return ret;
        };
        
        _this.canAddToDiagram = function() {
            var ret = fCanAdd(),
                msg = null;
            
            if (ret) {
                if (ToolKit.layer !== null && ToolKit.layer.parent.elementType==="AdhocSubProcess") {
                    msg = "Un subproceso ad-hoc no puede tener eventos finales";
                    ret = false;
                }
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _EndEvent = function(obj) {
        var _this = new _EndEventNode(obj);
        _this.setElementType("EndEvent");
        return _this;
    };
    
    var _MessageEndEvent = function (obj) {
        var _this = new _EndEvent(obj),
            fCanStart = _this.canStartLink;
        
        _this.setElementType("MessageEndEvent");

        _this.canStartLink = function(link) {
            var ret = fCanStart(link),
                msg = null,
                etype = link.elementType;

            if (ret || etype==="MessageFlow") {
                if (etype==="MessageFlow") {
                    ToolKit.hideToolTip();
                    ret = true;
                    var c = 0, i,
                        outConnections = _this.outConnections || [],
                        length = outConnections.length;

                    for (i = 0; i < length; i++) {
                        if (outConnections[i].elementType==="MessageFlow") {
                            c++;
                        }
                    }

                    if (c !== 0) {
                        ret = false;
                        msg = "Un evento final de mensaje no puede tener más de un flujo de mensaje saliente";
                    }
                }
            }
            if (msg!==null) {
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
        var _this = new _FlowNode(obj),
            fCanStart = _this.canStartLink,
            fCanEnd = _this.canEndLink,
            fSetText = _this.setText;
        
        _this.setElementType("Gateway");
        
        _this.setText = function(text) {
            fSetText(text,0,1,80,1);
        };
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link),
                ci = 0, co = 0,
                msg = null;
            
            if (ret && link.elementType==="SequenceFlow") {
                var inConnections = _this.inConnections || [],
                    outConnections = _this.outConnections || [],
                    ilength = inConnections.length,
                    olength = outConnections.length,
                    i;
                
                for (i = 0; i < ilength; i++) {
                    if (inConnections[i].elementType==="SequenceFlow") {
                        ci++;
                    }
                }
                
                for (i = 0; i < olength; i++) {
                    if (outConnections[i].elementType==="SequenceFlow") {
                        co++;
                    }
                }
                
                if (ci > 1 && co !== 0) {
                    msg = "Una compuerta convergente sólo puede tener un flujo de secuencia saliente";
                    ret = false;
                }
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link),
                ci = 0, co = 0,
                msg = null,
                etype = link.elementType,
                from = link.fromObject;
            
            if (ret && etype==="MessageFlow") {
                ret = false;
            } else if (ret && from.typeOf("DataObject")) {
                ret = false;
            } else if (ret && etype==="SequenceFlow") {
                if (from.typeOf("EventBasedGateway")) {
                    msg = "Esta compuerta no puede conectarse con una compuerta basada en eventos";
                    ret = false;
                } else {
                    var inConnections = _this.inConnections || [],
                        outConnections = _this.outConnections || [],
                        ilength = inConnections.length,
                        olength = outConnections.length,
                        i;
                
                    for (i = 0; i < ilength; i++) {
                        if (inConnections[i].elementType==="SequenceFlow") {
                            ci++;
                        }
                    }

                    for (i = 0; i < olength; i++) {
                        if (outConnections[i].elementType==="SequenceFlow") {
                            co++;
                        }
                    }

                    if (co > 1 && ci !== 0) {
                        msg = "Una compuerta divergente sólo puede tener un flujo de secuencia entrante";
                        ret = false;
                    }
                }
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _ExclusiveGateway = function (obj) {
        var _this = new _Gateway(obj),
            fCanStart = _this.canStartLink;
        
        _this.setElementType("ExclusiveGateway");
        
        _this.getDefaultFlow = function() {
            return "ConditionalFlow";
        };
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link),
                msg = null,
                etype = link.elementType;
            
            if (ret && !(etype==="ConditionalFlow" || etype==="DefaultFlow" || link.typeOf("AssociationFlow"))) {
                msg = "Una compuerta exclusiva sólo puede conectarse usando flujos condicionales, flujos por defecto o asociaciones";
                ret = false;
            } else if (ret && (etype==="DefaultFlow" || etype==="ConditionalFlow")) {
                var dou = 0, co = 0, ci = 0, i,
                    outConnections = _this.outConnections || [],
                    inConnections = _this.inConnections || [],
                    ilength = inConnections.length,
                    olength = outConnections.length;
                
                for (i = 0; i < olength; i++) {
                    if (outConnections[i].elementType==="DefaultFlow") {
                        dou++;
                    }
                    if (outConnections[i].elementType==="ConditionalFlow") {
                        co++;
                    }
                }
                
                co = co+dou;
                
                for (i = 0; i < ilength; i++) {
                    if (!inConnections[i].typeOf("AssociationFlow")) {
                        ci++;
                    }
                }

                if (etype==="DefaultFlow" && dou > 0) {
                    msg = "Una compuerta exclusiva sólo puede tener un flujo por defecto saliente";
                    ret = false;
                } else if (ci > 1 && co !== 0) {
                    msg = "Una compuerta convergente sólo puede tener un flujo saliente";
                    ret = false;
                }
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = true,
                msg = null,
                co = 0, ci = 1,
                etype = link.elementType,
                from = link.fromObject;
            
            if (ret && etype==="MessageFlow") {
                ret = false;
            } else if (ret && link.typeOf("AssociationFlow") && (from.typeOf("FlowNode") || from.elementType==="Pool")) {
                msg = "Un flujo de asociación no puede conectar dos nodos de flujo";
                ret = false;
            } else if (ret && from.typeOf("DataObject")) {
                ret = false;
            } else if (from.typeOf("EventBasedGateway")) {
                msg = "Esta compuerta no puede conectarse con una compuerta basada en eventos";
                ret = false;
            } else {
                var i,
                    outConnections = _this.outConnections || [],
                    inConnections = _this.inConnections || [],
                    ilength = inConnections.length,
                    olength = outConnections.length;
                    
                for (i = 0; i < olength; i++) {
                    if (!outConnections[i].typeOf("AssociationFlow")) {
                        co++;
                    }
                }

                for (i = 0; i < ilength; i++) {
                    if (!inConnections[i].typeOf("AssociationFlow")) {
                        ci++;
                    }
                }
                if (ci > 1 && co > 1) {
                    msg = "Una compuerta divergente sólo puede tener un flujo entrante";
                    ret = false;
                }
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    var _EventBasedGateway = function(obj) {
        var _this = new _Gateway(obj),
            fCanStart = _this.canStartLink;
        
        _this.setElementType("EventBasedGateway");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link),
                msg = null,
                etype = link.elementType;
            
            if (ret && (etype==="ConditionalFlow" || etype==="DefaultFlow")) {
                msg = "Una compuerta basada en eventos no puede conectarse usando flujos condicionales";
                ret = false;
            }
            if (msg!==null) {
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
        var _this = new _Gateway(obj),
            fCanStart = _this.canStartLink;
        
        _this.setElementType("InclusiveGateway");
        
        _this.getDefaultFlow = function() {
            return "ConditionalFlow";
        };
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link),
                msg = null,
                etype = link.elementType;
            
            if (ret && !(etype==="ConditionalFlow" || etype==="DefaultFlow" || link.typeOf("AssociationFlow"))) {
                msg = "Una compuerta inclusiva sólo puede conectarse usando flujos condicionales, flujos por defecto o asociaciones";
                ret = false;
            } else if (ret && (etype==="DefaultFlow" || etype==="ConditionalFlow")) {
                var i, dou = 0, co = 0, ci = 0,
                    outConnections = _this.outConnections || [],
                    inConnections = _this.inConnections || [],
                    olength = outConnections.length,
                    ilength = inConnections.length;
                
                for (i = 0; i < olength; i++) {
                    if (outConnections[i].elementType==="DefaultFlow") {
                        dou++;
                    }
                    if (outConnections[i].elementType==="ConditionalFlow") {
                        co++;
                    }
                }
                
                co = co+dou;
                
                for (i = 0; i < ilength; i++) {
                    if (!inConnections[i].typeOf("AssociationFlow")) {
                        ci++;
                    }
                }

                if (link.elementType==="DefaultFlow" && dou > 0) {
                    msg = "Una compuerta inclusiva sólo puede tener un flujo por defecto saliente";
                    ret = false;
                } else if (ci > 1 && co !== 0) {
                    msg = "Una compuerta convergente sólo puede tener un flujo saliente";
                    ret = false;
                }
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = true,
                msg = null, co = 0, ci = 0,
                from = link.fromObject;
            
            if (ret && link.elementType==="MessageFlow") {
                ret = false;
            } else if (ret && link.typeOf("AssociationFlow") && (from.typeOf("FlowNode") || from.elementType==="Pool")) {
                msg = "Un flujo de asociación no puede conectar dos nodos de flujo";
                ret = false;
            } else if (ret && from.typeOf("DataObject")) {
                ret = false;
            } else if (from.typeOf("EventBasedGateway")) {
                msg = "Esta compuerta no puede conectarse con una compuerta basada en eventos";
                ret = false;
            } else {
                var i,
                    outConnections = _this.outConnections || [],
                    inConnections = _this.inConnections || [],
                    ilength = inConnections.length,
                    olength = outConnections.length;
                    
                for (i = 0; i < olength; i++) {
                    if (!outConnections[i].typeOf("AssociationFlow")) {
                        co++;
                    }
                }

                for (i = 0; i < ilength; i++) {
                    if (!inConnections[i].typeOf("AssociationFlow")) {
                        ci++;
                    }
                }
                if (ci > 0 && co !== 0) {
                    msg = "Una compuerta divergente sólo puede tener un flujo entrante";
                    ret = false;
                }
            }
            if (msg!==null) {
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
        var _this = new _Gateway(obj),
            fCanStart = _this.canStartLink;
        
        _this.setElementType("ParallelGateway");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.elementType==="ConditionalFlow" || link.elementType==="DefaultFlow") {
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = true, msg = null, co = 0, ci = 0,
                from = link.fromObject;
            
            if (ret && link.elementType==="MessageFlow") {
                ret = false;
            } else if (ret && link.typeOf("AssociationFlow") && (from.typeOf("FlowNode") || from.elementType==="Pool")) {
                msg = "Un flujo de asociación no puede conectar dos nodos de flujo";
                ret = false;
            } else if (ret && from.typeOf("DataObject")) {
                ret = false;
            } else if (from.typeOf("EventBasedGateway")) {
                msg = "Esta compuerta no puede conectarse con una compuerta basada en eventos";
                ret = false;
            } else {
                var i,
                    inConnections = _this.inConnections || [],
                    outConnections = _this.outConnections || [],
                    ilength = inConnections.length,
                    olength = outConnections.length;
                for (i = 0; i < olength; i++) {
                    if (!outConnections[i].typeOf("AssociationFlow")) {
                        co++;
                    }
                }

                for (i = 0; i < ilength; i++) {
                    if (!inConnections[i].typeOf("AssociationFlow")) {
                        ci++;
                    }
                }
                if (ci > 0 && co !== 0) {
                    msg = "Una compuerta divergente sólo puede tener un flujo entrante";
                    ret = false;
                }
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        return _this;
    };
    
    /***************************Objetos de datos*******************************/
    var _DataObject = function(obj) {
        var _this = new _GraphicalElement(obj),
            fCanAttach = _this.canAttach,
            fCanEnd = _this.canEndLink,
            fCanStart = _this.canStartLink,
            fSetText = _this.setText;
        
        _this.setElementType("DataObject");
        
        _this.getDefaultFlow = function() {
            return "DirectionalAssociation";
        };
        
        _this.setText = function(text) {
            fSetText(text,0,1,80,1);
        };
        
        _this.canAttach = function(parent) {
            var ret = fCanAttach(parent);
            if(parent.elementType==="Pool" || parent.elementType==="Lane") {
                ret = true;
            }
            return ret;
        };
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link),
                msg = null;
            
            if (!link.typeOf("AssociationFlow")) {
                msg ="Un objeto de datos sólo puede conectarse usando flujos de asociación";
                ret = false;
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link),
                msg = null;
            
            if (ret && !link.typeOf("AssociationFlow")) {
                msg ="Un objeto de datos sólo puede conectarse usando flujos de asociación";
                ret = false;
            } else if (ret && !link.fromObject.typeOf("Activity")) {
                msg ="Los objetos de datos sólo pueden asociarse con actividades";
                ret = false;
            }
            if (msg!==null) {
                ToolKit.showTooltip(0, msg, 200, "Error");
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
        var _this = new _GraphicalElement(obj),
            fCanStart = _this.canStartLink,
            fCanEnd = _this.canEndLink;
        
        _this.setElementType("Artifact");
        
        _this.canAttach = function(parent) {
            var ret = false;
            if (parent.elementType==="Pool" || parent.elementType==="Lane") {
                ret = true;
            }
            return ret;
        };
        
        _this.canStartLink = function (link) {
            var ret = fCanStart(link);
            if (ret && !link.typeOf("AssociationFlow")) {
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.elementType!=="AssociationFlow") {
                ret = false;
            } else if (link.fromObject.typeOf("Artifact") || link.fromObject.typeOf("DataObject")) {
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _AnnotationArtifact = function(obj) {
        var _this = new _Artifact(obj),
            fCanStart = _this.canStartLink,
            fSetText = _this.setText;
        
        _this.setElementType("AnnotationArtifact");
        
        _this.getDefaultFlow = function() {
            return "AssociationFlow";
        };
        
        _this.setText = function(text) {
            fSetText(text,0,0,0,1);
        };
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (ret && link.elementType==="DirectionalAssociation") {
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
        var _this = new _GraphicalElement(obj),
            fCanAdd = _this.canAddToDiagram,
            fSetText = _this.setText,
            fUpdateText = null;
        
        _this.setElementType("Pool");
        
        _this.getDefaultFlow = function() {
            return "MessageFlow";
        };
        
        _this.setText = function(text) {
            fSetText(text,0,0,_this.getHeight(),1);
            fUpdateText = _this.text.update;
            
            _this.text.ondblclick = function(evt) {
                var txt = prompt("Titulo:",_this.text.value);                  
                if(txt && txt!==null)
                {
                    _this.text.value=txt;
                    _this.text.update();
                    _this.updateHeaderLine();
                }
            };
            
            _this.text.update = function() {
                if (fUpdateText !== null) {
                    _this.text.textW = _this.getHeight();
                    fUpdateText();
                    //console.log("width: "+_this.text.getBoundingClientRect().width+", height: "+_this.text.getBoundingClientRect().height);
                    //console.log("cwidth: "+_this.text.getBBox().width+", cheight: "+_this.text.getBBox().height);
                    var x = (_this.getX() - _this.getWidth()/2) + _this.text.getBoundingClientRect().width/2;
                    var y = _this.getY();
                    if (_this.text.childElementCount === 1) {
                        x += 5;
                    }
                    _this.text.setAttributeNS(null, "x", x);
                    _this.text.setAttributeNS(null, "y", y);
                    _this.text.setAttributeNS(null, "transform", "rotate(-90 "+x+","+y+")");
                }
            };
        };
        
        _this.canStartLink = function(link) {
            var ret = true;
            if (!(link.elementType==="AssociationFlow" || link.elementType==="MessageFlow")) {
                ToolKit.showTooltip(0,"Un pool sólo puede conectarse usando flujos de mensaje", 250, "Error");
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = true;
            if (link.elementType==="SequenceFlow") {
                ret = false;
            }
            if (link.elementType==="MessageFlow" && link.fromObject.getPool() === _this) {
                ret = false;
            }
            if (link.elementType==="AssociationFlow" && link.fromObject.elementType!=="AnnotationArtifact") {
                ret = false;
            }
            return ret;
        };
        
        _this.canAddToDiagram = function() {
            var ret = fCanAdd();
            if (ToolKit.layer !== null) {
                ToolKit.showTooltip(0,"Un subproceso no puede contener pools", 250, "Error");
                ret = false;
            }
            return ret;
        };
        return _this;
    };
    
    var _Lane = function (obj) {
        var _this = new _GraphicalElement(obj);
        //var fCanAdd = _this.canAddToDiagram;
        //var fSetText = _this.setText;
        //var fUpdateText = null;
        _this.lindex = -1;
        
        _this.setElementType("Lane");
        
        _this.setIndex = function(idx) {
            _this.lindex = idx;
        };
        
        return _this;
    };
    
    /******************************Actividades*******************************/
    var _Activity = function(obj) {
        var _this = new _FlowNode(obj),
            fCanStart = _this.canStartLink,
            fCanEnd = _this.canEndLink,
            fSetText = _this.setText,
            fMoveFirst = _this.moveFirst;
        
        _this.setElementType("Activity");
        
        _this.setText = function(text) {
            fSetText(text,0,0,0,1);    
        };
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.elementType==="DefaultFlow") {
                ret = false;
            }
            return ret;
        };
        
        _this.moveFirst = function() {
            fMoveFirst();
            var i, contents = _this.contents || [],
                length = contents.length;
            for(i = 0; i < length; i++) {
                if (contents[i].moveFirst) {
                    contents[i].moveFirst();
                }
            }
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            
            if (link.typeOf("AssociationFlow") && link.fromObject.typeOf("DataObject")) {
                ret = true;
            }
            if (ret && link.elementType === "MessageFlow") {
                ret = false;
            } else if (ret && link.fromObject.elementType==="ExclusiveIntermediateEventGateway") {
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
    
    var _CallSubProcess = function (obj) {
        var _this = new _CallActivity(obj);
        _this.setElementType("CallSubProcess");
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
        _this.setElementType("ScriptTask");
        return _this;
    };
    
    var _SendTask = function(obj) {
        var _this = new _Task(obj),
            fCanStart = _this.canStartLink;
        
        _this.setElementType("SendTask");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (ret || link.elementType==="MessageFlow") {
                ToolKit.hideToolTip();
                ret = true;
            }
            return ret; 
        };
        return _this;
    };
    
    var _ReceiveTask = function(obj) {
        var _this = new _Task(obj),
            fCanEnd = _this.canEndLink;
        
        _this.setElementType("ReceiveTask");
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (ret || link.elementType==="MessageFlow") {
                ret = true;
            }
            if (link.elementType==="SequenceFlow" && link.fromObject.elementType==="ExclusiveIntermediateEventGateway") {
                ret = true;
                var i, outConnections = link.fromObject.outConnections || [],
                    length = outConnections.length;
            
                for (i = 0; i < length; i++) {
                    if (outConnections[i].elementType==="SequenceFlow" && outConnections[i].toObject && outConnections[i].toObject.elementType==="MessageIntermediateCatchEvent") {
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
        _this.setElementType("ManualTask");
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
        var _this = new _SubProcess(obj),
            fCanStart = _this.canStartLink,
            fCanEnd = _this.canStartLink;
        
        _this.setElementType("EventSubProcess");
        
        _this.canStartLink = function(link) {
            var ret = fCanStart(link);
            if (link.typeOf("SequenceFlow")) {
                ToolKit.showTooltip(0,"Un subproceso de evento no puede conectarse usando flujos de secuencia", 250, "Error");
                ret = false;
            }
            return ret;
        };
        
        _this.canEndLink = function(link) {
            var ret = fCanEnd(link);
            if (link.typeOf("SequenceFlow")) {
                ToolKit.showTooltip(0,"Un subproceso de evento no puede conectarse usando flujos de secuencia", 250, "Error");
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
            document.getElementById("fileBar").setAttribute("class", "subbarHidden");
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
            clearTimeout(ToolBar.intervalOver);
            ToolBar.hideSubBars();
            var ele=document.getElementById(barId);
            ele.setAttribute("class", "subbar");

            if(obj)
            {
                ele.style.top=obj.offsetTop-obj.offsetParent.scrollTop+"px";
                ele.style.left=(obj.offsetLeft+47)+"px";
            }
        },

        outToolBar: function()
        {
            ToolBar.intervalOver=setTimeout(ToolBar.hideSubBars,1000);
        },

        overToolBar: function(isItem)
        {
            if (isItem || false) {
                clearTimeout(ToolBar.intervalOver);
            }
        }
    };
    
    /**********************************Modeler*******************************/
    var Modeler =
    {
        creationId:null,                                      //Objeto temporal para creacion de instancias
        itemsCount:0,
        selectedPath:null,
        navPath:null,
        window:null,
                
        init:function(svgid, options, callbackHandler)
        {
            //Set default options
            Modeler.options = {
                mode:'edit',
                layerNavigation:true
            };
            
            //Override passed options
            if (options && options !== null) {
                var mode = options.mode;
                if (mode && mode !== null && mode.length > 0) {
                    Modeler.options.mode = mode;
                }
                
                if (options.layerNavigation && options.layerNavigation !== null) {
                    Modeler.options.layerNavigation = options.layerNavigation;
                }
            }
                
            ToolKit.init(svgid);
            ToolKit.onmousedown=Modeler.onmousedown;
            ToolKit.onmousemove=Modeler.onmousemove;
            ToolKit.onmouseup=Modeler.onmouseup;
            if(!ToolKit.svg.offsetLeft)ToolKit.svg.offsetLeft=60;
            if(!ToolKit.svg.offsetTop)ToolKit.svg.offsetTop=10;
            Modeler.createNavPath();
            Modeler.mode = (options !== null && options.mode)?options.mode:"view";
            
            //Sobreescritura del método setLayer para establecer la barra de navegación
            var fSetLayer = ToolKit.setLayer;
            ToolKit.setLayer = function(layer) {
                fSetLayer(layer);
                if (Modeler.navPath !== null) {
                    Modeler.navPath.setNavigation(layer);
                }
            };
            
            if (Modeler.mode !== "view") {
                //Add Key Events
                if (window.addEventListener)
                {
                    window.addEventListener('keydown', Modeler.keydown, true);
                }
                else if (window.attachEvent)
                {
                    window.attachEvent("onkeydown", Modeler.keydown);
                }
                else
                {
                    window.onkeydown= Modeler.keydown;  
                }
                modeler.window=window;
                modeler.window.focus();
            }
            
            //Sobreescritura del método showResizeBoxes para considerar Pools con lanes y lanes
            var fShowBoxes = ToolKit.showResizeBoxes;
            ToolKit.showResizeBoxes = function() {
                var _this = ToolKit;
                if(_this.selected.length===1 && _this.selected[0].resizeable) {
                    var sel = _this.selected[0];
                    if (sel.elementType==="Pool" && sel.hasLanes()) {
                        var i, obj = sel,
                            lanes = obj.lanes,
                            llength = obj.lanes.length;
                        for (i = 0; i < llength; i++) {
                            _this.createResizeBox(lanes[i],0,1,"s-resize");
                        }
                        _this.createResizeBox(obj,-1,0,"w-resize");
                        _this.createResizeBox(obj,1,0,"e-resize");
                    } else if (sel.elementType==="Lane") {
                        _this.createResizeBox(obj,0,-1,"n-resize");
                        _this.createResizeBox(obj,0,1,"s-resize");
                    } else {
                        fShowBoxes();
                    }
                }else
                {
                    _this.hideResizeBoxes();
                }
            };
            
            //Habría que mover esto a otro lado?
            var dlg = document.getElementById("dropArea");
            
            if (dlg !== null) {
                dlg.addEventListener("dragover" , function(evt) {
                    evt.preventDefault();
                    dlg.setAttributeNS(null, "class", "dropAreaOver");
                } , false);

                dlg.addEventListener("dragleave" , function(evt) {
                    dlg.setAttributeNS(null, "class", "dropArea");
                } , false);

                dlg.addEventListener("drop" , function(evt) {
                    evt.preventDefault();

                    var file = evt.dataTransfer.files[0];

                    var reader = new FileReader();
                    reader.onload = (function(f) {
                        return function(e) {
                            Modeler.loadProcess(e.target.result);
                        };
                    })(file);

                    reader.readAsText(file);
                    dlg.setAttributeNS(null, "class", "dropArea");
                    hideLoadDialog();
                } , false);
            }
            
            //Sobreescritura de mousemove en SVG padre para manejar redimensionamiento de Lanes
            ToolKit.svg.onmousemove=function(evt)
            {
                var _this = ToolKit,
                    resizeObj = _this.svg.resizeObject || null,
                    offX = _this.svg.dragOffsetX,
                    offY = _this.svg.dragOffsetY,
                    evtX = _this.getEventX(evt),
                    evtY = _this.getEventY(evt);
            
                if(_this.onmousemove(evt)===false) {
                    return;
                }
                _this.svg.mouseX = evtX;
                _this.svg.mouseY = evtY;

                if(resizeObj && resizeObj!==null) {
                    var parent = resizeObj.parent,
                        objix = resizeObj.ix,
                        objiy = resizeObj.iy;
                    if (parent.elementType==="Lane") {
                        var w = parent.getWidth();
                        y = evtY - offY;
                        ty = y - parent.getY();
                        h = Math.abs(resizeObj.startH + ty * objiy);
                        if((parent.getY() - h / 2) < 0) {
                            h = parent.getY() * 2;
                        }
                        parent.resize(w,h);
                       
                        parent.parent.updateLanes();
                        _this.updateResizeBox();
                    } else {
                        x = evtX - offX;
                        y = evtY - offY;
                        tx = x - parent.getX();
                        ty = y - parent.getY();
                        w = Math.abs(resizeObj.startW + tx * 2 * objix);
                        h = Math.abs(resizeObj.startH + ty * 2 * objiy); 
                        if((parent.getX() - w / 2) < 0) {
                            w = parent.getX() * 2;
                        }
                        if((parent.getY() - h / 2) < 0) {
                            h = parent.getY() * 2;
                        }
                        parent.resize(w,h);
                        _this.updateResizeBox();
                    }

                }else if(_this.svg.dragObject && _this.svg.dragObject!==null)  //dragObjects
                {
                    _this.selected.unselect = false; //si hace drag no deselecciona
                    x = evtX - offX;
                    y = evtY - offY;

//                    if(_this.snap2Grid)
//                    {
//                        x=Math.round(x/_this.snap2GridSize)*_this.snap2GridSize;
//                        y=Math.round(y/_this.snap2GridSize)*_this.snap2GridSize;
//                    }

                    if(_this.cmdkey) {//Drag one
                        _this.svg.dragObject.move(x,y);
                        _this.updateResizeBox();
                    } else {//drag selecteds
                        tx = x - _this.svg.dragObject.getX();
                        ty = y - _this.svg.dragObject.getY();

                        var i, selected = _this.selected || [];
                        for (i = selected.length; i--;) {
                            selected[i].traslate(tx, ty);
                        }
                        _this.updateResizeBox();
                    }
                } else if(_this.selectBox && _this.selectBox!==null) //SelectBox
                {
                    var w = evtX - offX;
                    var h = evtY - offY;
                    var x = offX;
                    var y = offY;

                    if(w < 0) {
                        x = offX + w;
                        w = -w;
                    }
                    if(h < 0) {
                        y = offY + h;
                        h = -h;
                    }

                    _this.selectBox.setAttributeNS(null,"x",x);
                    _this.selectBox.setAttributeNS(null,"width",w);
                    _this.selectBox.setAttributeNS(null,"y",y);
                    _this.selectBox.setAttributeNS(null,"height",h);
                    //_this.svg.appendChild(_this.selectBox);

                    var i, nodes = _this.svg.childNodes, length = nodes.length;
                    for(i = 0; i < length; i++) {
                        var obj = nodes.item(i), ox, oy, bb, owidth, oheight;
                        if(obj.contents && obj.canSelect && !obj.hidden) {    //Es un objeto grafico
                            ox = obj.getX();
                            oy = obj.getY();
                            bb = _this.selectBox.getBBox();
                            owidth = obj.getWidth();
                            oheight = obj.getHeight();
                    
                            if ((ox - owidth / 2 > bb.x && ox + owidth / 2 < (bb.x + bb.width)) && (oy - oheight / 2 > bb.y && oy + oheight / 2 < bb.y + bb.height))
                            //if(ox>=x && ox<=x+w && oy>=y && oy<=y+h)
                            {
                                if(!obj.selected) {
                                    _this.selectObj(obj);
                                }
                            } else {
                                if(!_this.cmdkey && obj.selected) {
                                    _this.unSelectObj(obj);
                                }
                            }
                        }
                    }
                }
                _this.loaded = true;

            };
            
            if (callbackHandler && callbackHandler !== null) {
                callbackHandler();
            }
        },
        
        keydown:function(evt) {
            if(evt.keyCode===8 && evt.which===8) {
                if (Modeler.selectedPath && Modeler.selectedPath !== null) {
                    Modeler.selectedPath.remove();
                    Modeler.selectedPath = null;
                }
                ToolKit.stopPropagation(evt);
            }
        },
                
        onmousedown:function(evt) {
            if (Modeler.mode === "view") {
                return false;
            }
            
            var cId = Modeler.creationId || null,
                obj = Modeler.mapObject(cId) || false,
                dropObj = Modeler.creationDropObject || null,
                selectedPath = Modeler.selectedPath || null;
        
            modeler.window.focus();
            if (selectedPath !== null) {
                selectedPath.select(false);
                Modeler.selectedPath = null;
            }
        
            if (!obj) {
                return true;
            }
            
            if (obj.move) { //FlowNode
                if (!obj.canAddToDiagram()) {
                    obj.remove();
                } else if (cId === "Lane") {
                    if (dropObj && dropObj.elementType === "Pool") {
                        dropObj.addLane(obj);
                    } else {
                        obj.remove();
                    }
                    ToolKit.unSelectAll();
                } else {
                    obj.move(ToolKit.getEventX(evt), ToolKit.getEventY(evt));
                    obj.snap2Grid();
                    obj.layer = ToolKit.layer;
                    Modeler.fadeInObject(obj);
                    
                    var i, nodes = ToolKit.svg.childNodes || [],
                        length = nodes.length,
                        ox = obj.getX(), oy = obj.getY(),
                        selected = ToolKit.selected,
                        tobj;
                
                    //link parents
                    for (i = length; i-- && i >= 0;) {
                        tobj = nodes[i];
                        if(tobj && tobj.contents && tobj === obj) {
                            for (;i-- && i >= 0;) {
                                tobj = nodes[i];
                                if(!tobj.hidden && tobj.inBounds && tobj.inBounds(ox, oy)) {
                                    if(selected.indexOf(tobj) < 0) {
                                        tobj.onDropObjects([obj]);
                                        i = 0;
                                    }
                                }
                            }
                        }
                    }
                }
            } else { //ConnectionObject
                Modeler.dragConnection = obj;
                if (dropObj !== null && dropObj.canStartLink(obj)) {
                    dropObj.addOutConnection(obj);
                    obj.setEndPoint(dropObj.getX(), dropObj.getY());
                } else {
                    obj.remove();
                    Modeler.dragConnection = null;
                }
            }
            Modeler.creationId=null;
            Modeler.creationDropObject=null;
            ToolKit.stopPropagation(evt);
            return false;
        },    
                
        onmousemove:function(evt) {
            var _this = ToolKit;
            if (Modeler.mode === "view") {
                return false;
            }
            
            var dragCon = Modeler.dragConnection || null;
            if (dragCon !== null) {
                dragCon.show();
                if(dragCon.toObject && dragCon.toObject !== null) {
                    dragCon.toObject = null;
                    _this.unSelectAll();
                }
                dragCon.setEndPoint(_this.getEventX(evt), _this.getEventY(evt));
                return false;
            }
            return true;
        },
                
        onmouseup:function(evt) {
            var _this = ToolKit;
            var resizeObj = _this.svg.resizeObject || null,
                dragCon = Modeler.dragConnection || null,
                toObj = dragCon && dragCon.toObject,
                p = resizeObj && resizeObj.parent;
        
            if (Modeler.mode === "view") {
                return false;
            }
            
            if(dragCon !== null) {
                toObj === null ? dragCon.remove() :
                        toObj.canEndLink(dragCon) ? toObj.addInConnection(dragCon) : dragCon.remove();
                
                dragCon = Modeler.dragConnection = null;
            }
            
            if (resizeObj !== null && p.elementType==="Lane") {
                var oldH = ty, py = p.getY();
                y = _this.getEventY(evt) - _this.svg.dragOffsetY;
                ty = y - py;
                h = Math.abs(resizeObj.startH + ty * resizeObj.iy);
                if((p.y - h / 2 ) < 0) {
                    h = py * 2;
                }
                
                p.parent.resize(p.parent.getWidth(), p.parent.getHeight() + oldH);
                _this.updateResizeBox();
            }
            return true;
        },                  
                
        objectMouseDown:function(evt,obj) {
            if (Modeler.mode === "view") {
                return false;
            }
            
            var cId = Modeler.creationId || null;
            if(cId !== null) {
                Modeler.creationDropObject = obj.elementType !== "Lane" ? obj : null;
                return false;
            }

            if(evt.button===2) {
                var dragCon = Modeler.dragConnection = Modeler.mapObject(obj.getDefaultFlow()),
                    etype = obj.elementType;
                
                if (dragCon !== null){
                    dragCon.hide();
                    if (obj.canStartLink(dragCon)) {
                        if (dragCon.elementType==="ConditionalFlow" && etype==="ExclusiveGateway" || etype==="InclusiveGateway") {
                            dragCon.removeAttribute("marker-start");
                            dragCon.soff = 0;
                        }
                        obj.addOutConnection(dragCon);
                        //Modeler.dragConnection.setEndPoint(obj.getX(),obj.getY());
                    } else {
                        dragCon.remove();
                        Modeler.dragConnection = null;
                    }
                }
                
                //obj.select(true);
                //obj.moveFirst();
                ToolKit.stopPropagation(evt);                
                return false;
            }
            return true;            
        },      
                
        objectMouseMove:function(evt,obj) {
            if (Modeler.mode === "view") {
                return false;
            }
            
            var dragCon = Modeler.dragConnection || null;
            if(dragCon !== null) {
                if(dragCon.fromObject !== obj && dragCon.toObject !== obj) {
                    if (obj.canEndLink(dragCon)) {
                        ToolKit.unSelectAll();
                        ToolKit.selectObj(obj,true);
                        //obj.addInConnection(Modeler.dragConnection);
                        dragCon.toObject=obj;
                        dragCon.setEndPoint(obj.getX(),obj.getY());
                    } else {
                        dragCon.setEndPoint(ToolKit.getEventX(evt), ToolKit.getEventY(evt));
                    }
                }
                ToolKit.stopPropagation(evt);
            }            
        },                   
                
        creationStart:function(span)
        {
            Modeler.creationId=span.getAttribute("cId");
        },
        
        createObject:function(type, id, parent)
        {
            var obj=ToolKit.createUseObject(type,id,parent);
            obj.mousedown=function(evt){return Modeler.objectMouseDown(evt,obj);};
            obj.onmousemove=function(evt){return Modeler.objectMouseMove(evt,obj);};
            return obj;
        },

        createConnectionPath:function(marker_start, marker_mid, marker_end, dash_array, styleClass) {
            var obj = ToolKit.createConnectionPath(0,0,0,0,marker_start, marker_mid, marker_end, dash_array, styleClass);
            obj.subLine = ToolKit.createConnectionPath(0,0,0,0,null, null, null, null, null);
            obj.subLine.setStyleClass=function(cls) {
                obj.subLine.setAttributeNS(null,"class",cls);
            };
            obj.subLine.setAttributeNS(null,"class","sequenceFlowSubLine");            
            
            obj.pressed = false;
            obj.hidden = false;
            obj.setArrowType = function(type) {
                obj.setAttributeNS(null, "marker-end", "url(#"+type+")");
            };
            obj.setTailType= function(type) {
                obj.setAttributeNS(null, "marker-start", "url(#"+type+")");
            };
            obj.soff=0;
            obj.eoff=0;
//            obj.xs=0;
//            obj.ys=0;
//            obj.xe=0;
//            obj.ye=0;
            
            //obj.addPoint(0,0);
            obj.addPoint(0,0);
            obj.addPoint(0,0);
            
            obj.setStartPoint=function(x,y) {
                obj.setPoint(0,x,y);
                obj.xs=x;
                obj.ys=y;
                if(!obj.xe)obj.xe=x;
                if(!obj.ye)obj.ye=y;                
                obj.updateInterPoints();
                obj.pressed = false;
            };
                    
            obj.setEndPoint=function(x,y) {
                obj.xe=x;
                obj.ye=y;
                obj.setPoint(obj.pathSegList.numberOfItems-1,x,y);
                obj.updateInterPoints();
                obj.pressed = false;
            };
            
            obj.onmousedown = function (evt) {
                if (Modeler.mode === "view") {
                    return false;
                }
                var selectedPath = Modeler.selectedPath || null;
                obj.pressed = true;
                if (selectedPath !== null) {
                    selectedPath.select(false);
                }
                obj.select(true);
                Modeler.selectedPath = obj;
                /*if (evt.button===2) {
                    obj.createLineHandlers();
                }*/
                ToolKit.stopPropagation(evt);
                return false;
            };
            
            obj.select = function(selected) {
                obj.setAttributeNS(null, "class", selected ? "sequenceFlowLine_o" : "sequenceFlowLine");
            };
            
            obj.onmousemove = function (evt) {
                if (Modeler.mode === "view") {
                    return false;
                }
                
                var selectedPath = Modeler.selectedPath || null;
                if (selectedPath !== null && selectedPath === obj) {
                    obj.select(false);
                    Modeler.selectedPath = null;
                }
                if (obj.pressed) {
                    var inConnections = obj.toObject.inConnections,
                        idx = inConnections.indexOf(obj);
                
                    inConnections.splice(idx);
                    obj.toObject = null;
                    Modeler.dragConnection = obj;
                    obj.pressed = false;
                }
                return Modeler.onmousemove(evt);
            };
                
            obj.onmouseup = function (evt) {
                if (Modeler.mode === "view") {
                    return false;
                }
                
                var dragCon = Modeler.dragConnection || null;
                if (obj.pressed) {
                    obj.pressed = false;
                }
                if (dragCon !== null && dragCon === obj) {
                    Modeler.dragConnection = null;
                }
                return false;
            };
            
            obj.removeText = function() {
                if (obj.text) {
                    ToolKit.svg.removeChild(obj.text);
                    obj.text = undefined;
                }
            };
            
            obj.ondblclick = function(evt) {
                if (obj.elementType==="ConditionalFlow" || obj.elementType==="DefaultFlow") {
                    var txt = prompt("Título:",obj.title);
                    if (txt !== null) {
                        if (txt.length) {
                            obj.title = txt;
                            if (!obj.text) {
                                obj.createText();
                            }
                            obj.updateText();
                        } else {
                            obj.title = "";
                            if (obj.text) {
                                obj.removeText();
                            }
                        }
                    }
                }
            };
            
            obj.subLine.onmousedown = obj.onmousedown;
            obj.subLine.onmousemove = obj.onmousemove;
            obj.subLine.onmouseup = obj.onmouseup;
            obj.subLine.ondblclick = obj.ondblclick;
            obj.subLine.hide = function() {
                obj.subLine.style.display="none";
                obj.subLine.hidden=true;
            };
            obj.subLine.show=function()
            {
                obj.subLine.style.display="";
                obj.subLine.hidden=false;
            };
            
            obj.createText = function() {
                obj.text = document.createElementNS(ToolKit.svgNS, "text");
                obj.text.setAttributeNS(null,"text-anchor","middle");
                obj.text.setAttributeNS(null,"font-size","11");
                obj.text.setAttributeNS(null,"font-family","Verdana, Geneva, sans-serif");
                obj.text.setAttributeNS(null,"class","textLabel");
                
                obj.text.removeNodes = function() {
                    var child = null;
                    while((child=obj.text.firstChild)!==null) {
                        obj.text.removeChild(child);
                    }
                };
                
                obj.text.show = function() {
                    obj.text.style.display="";
                };

                obj.text.hide = function() {
                    obj.text.style.display="none";  
                };
                
                obj.text.update = function () {
                    obj.text.removeNodes();
                    
                    var txt = document.createTextNode(obj.title);
                    obj.text.appendChild(txt);
                    obj.text.updateLocation();
                };
                
                obj.text.updateLocation = function() {
                    var p1 = obj.pathSegList.getItem(1),
                        p2 = obj.pathSegList.getItem(2),
                        _x = p1.x,
                        _y = p1.y;
                    
                    if (p1.x !== p2.x) {
                        _x += (p2.x - p1.x)/2;
                    }
                    
                    if (p1.y !== p2.y) {
                        _y += (p2.y - p1.y)/2;
                    }
                    
                    obj.text.setAttributeNS(null, "x", _x);
                    obj.text.setAttributeNS(null, "y", _y);
                    ToolKit.svg.appendChild(obj.text);
                };
                
//                obj.text.ondblclick = function(evt) {
//                    var txt = prompt("Título:",obj.title);                  
//                    if(txt && txt !== null) {
//                        obj.title = txt;
//                        obj.text.update();
//                    }
//                };
            };
            
            obj.updateText = function() {
                if (obj.title && obj.title.length) {
                    if (!obj.text) {
                        obj.createText();
                    }
                    obj.text.update();
                }
            };
            
            obj.setLabel = function(label) {
                if (obj.title !== label) {
                    obj.title = label;
                    obj.updateText();
                }
            };
            
            obj.updateInterPoints=function() {
                var p1, p2, p0 = obj.pathSegList.getItem(0),
                    p3 = obj.pathSegList.getItem(3),
                    fw = 0, fh = 0, tw = 0, th = 0,
                    from = obj.fromObject,
                    to = obj.toObject, dx = obj.xe - obj.xs,
                    dy = obj.ye - obj.ys,
                    segments = obj.pathSegList;
                
                fw = from !== null ? from.getWidth() / 2 : 0;
                fh = from !== null ? from.getHeight() / 2 : 0;
                tw = to !== null ? to.getWidth() / 2 : 0;
                th = to !== null ? to.getHeight() / 2 : 0;
                
                if((Math.abs(dx) - fw - tw) >= (Math.abs(dy) - fh - th))  {//Caso X
                    if(dx > 0) {
                        dx = 1;
                    } else if(dx < 0){
                        dx = -1;
                    }
                    
                    p0.x = obj.xs + dx * (fw + obj.soff);
                    p3.x = obj.xe - dx * (tw + obj.eoff);
                    p3.y = obj.ye;
                    p0.y = obj.ys;
                    
                    
                    if(segments.numberOfItems===4 && !obj.fixed)
                    {
                        p1 = segments.getItem(1);
                        p2 = segments.getItem(2);
                        
                        p1.x = (p3.x - p0.x) / 2 + p0.x;
                        p2.x = p1.x;
                        p1.y = p0.y;
                        p2.y = p3.y;
                    }
                } else {   //Caso Y
                    if(dy > 0){
                        dy = 1;
                    } else if(dy < 0){
                        dy = -1;
                    }
                    
                    p0.y = obj.ys + dy * (fh + obj.soff);
                    p3.y = obj.ye - dy * (th + obj.eoff);
                    p3.x = obj.xe;
                    p0.x = obj.xs;
                    
                    if(segments.numberOfItems===4 && !obj.fixed) {
                        p1 = segments.getItem(1);
                        p2 = segments.getItem(2);
                    
                        p1.y = (p3.y - p0.y) / 2 + p0.y;
                        p2.y = p1.y;
                        p1.x = p0.x;
                        p2.x = p3.x;
                    }                                        
                }
                obj.updateSubLine();
                if (obj.text) {
                    obj.text.updateLocation();
                }
            };
            
            var fHide = obj.hide;
            var fShow = obj.show;
            var fRemove = obj.remove;
            
            obj.hide = function() {
                fHide();
                obj.subLine.hide();
                if (obj.text) {
                    obj.text.hide();
                }
            };
            
            obj.show = function() {
                fShow();
                obj.subLine.show();
                if (obj.text) {
                    obj.text.show();
                }
            };
            
            obj.remove=function() {
                obj.subLine.remove();
                if (obj.text) ToolKit.svg.removeChild(obj.text);
                fRemove();
            };
            
            obj.updateSubLine= function() {
                obj.subLine.pathSegList.clear();
                var segments = obj.pathSegList, i, items = segments.numberOfItems;
                for (i = 0; i < items; i++) {
                    var segment = segments.getItem(i);
                    if (segment.pathSegType===SVGPathSeg.PATHSEG_LINETO_ABS) {
                        obj.subLine.pathSegList.appendItem(obj.subLine.createSVGPathSegLinetoAbs(segment.x, segment.y));
                    } else if (segment.pathSegType===SVGPathSeg.PATHSEG_MOVETO_ABS) {
                       obj.subLine.pathSegList.appendItem(obj.subLine.createSVGPathSegMovetoAbs(segment.x, segment.y));
                    }
                }
                //ToolKit.svg.insertBefore(obj.subLine, obj);
                //obj.subLine.setAttributeNS(null,"class","sequenceFlowSubLine");
            };
            return obj;
        },
                
        createLane: function(id, parent) {
            var obj = ToolKit.createObject(function() {
                var obj = document.createElementNS(ToolKit.svgNS, "rect");
                obj.setAttributeNS(null, "bclass", "swimlane");
                obj.setAttributeNS(null, "oclass", "swimlane_o");
                return obj;
            }, id, parent);
            
            obj.canSelect = false;
            obj.resizeable = true;
            
            var fRemove = obj.remove;            
            
            obj.onmousedown = function(evt) {
                return false;
            };
            
            obj.mousedown = function(evt) {
                return false;
            };
            
            obj.remove=function() {
                fRemove();
                var parentLanes = obj.parent.lanes, totHeight = 0, i;
                //elimina el objeto de contents
                while ((ax = parentLanes.indexOf(obj)) !== -1) {
                    parentLanes.splice(ax, 1);
                }
                
                //calcula el nuevo tamaño en height
                var length = parentLanes.length;
                for (i = 0; i < length; i++) {
                    var l = parentLanes[i];
                    totHeight += l.getHeight();
                }                
                obj.parent.resize(obj.parent.getWidth(), totHeight);
                obj.parent.updateLanes();
            };
            
            var fUpdateText = null;
            var fSetText = obj.setText;
            obj.setText = function(text) {
                fSetText(text,0,0,obj.getHeight(),1);
                fUpdateText = obj.text.update;
                
                obj.text.onclick=function(evt) {
                    ToolKit.selectObj(obj,true);
                };

                obj.text.ondblclick = function(evt) {
                    var txt = prompt("Titulo:",obj.text.value);                  
                    if(txt && txt!==null)
                    {
                        obj.text.value=txt;
                        obj.text.update();
                        if(obj.updateHeaderLine)obj.updateHeaderLine();
                    }
                };

                obj.text.update = function() {
                    if (fUpdateText && fUpdateText !== null) {
                        obj.text.textW = obj.getHeight();
                        fUpdateText();
                        //console.log("width: "+_this.text.getBoundingClientRect().width+", height: "+_this.text.getBoundingClientRect().height);
                        //console.log("cwidth: "+_this.text.getBBox().width+", cheight: "+_this.text.getBBox().height);
                        var x = (obj.getX() - obj.getWidth()/2) + obj.text.getBoundingClientRect().width/2,
                            y = obj.getY();
                    
                        if (obj.text.childElementCount === 1) {
                            x += 5;
                        }
                        obj.text.setAttributeNS(null, "x", x);
                        obj.text.setAttributeNS(null, "y", y);
                        obj.text.setAttributeNS(null, "transform", "rotate(-90 "+x+","+y+")");
                    }
                };
            };
            return obj;
        },
        
        createPool:function(id, parent) {
            //Revisar ID
            var obj = ToolKit.createResizeObject(id,parent);
            obj.setAttributeNS(null,"bclass","swimlane");
            obj.setAttributeNS(null,"oclass","swimlane_o");
            obj.setBaseClass();
            
            var line=ToolKit.createBaseObject(function() {
                return document.createElementNS(ToolKit.svgNS,"path");
            },null,null);
            obj.headerLine = line;
            obj.lanes = [];
            
            obj.mousedown=function(evt) {
                if (Modeler.mode === "view") {
                    return false;
                }
                
                var cId = Modeler.creationId || null;
                if (cId === null) {
                    if(ToolKit.getEventX(evt) < obj.getX() - obj.getWidth() / 2 + obj.headerLine.lineOffset + 5) {
                        return Modeler.objectMouseDown(evt,obj);
                    }
                } else if (cId === "Lane") {
                    return Modeler.objectMouseDown(evt,obj);
                }
                return false;
            };
            
            obj.onmousemove=function(evt) {
                var dragCon = Modeler.dragConnection || null;
                if (Modeler.mode === "view" || (dragCon !== null && dragCon.fromObject.isChild(obj))) {
                    return false;
                }
                return Modeler.objectMouseMove(evt,obj);
            };
            
            var fMove = obj.move;
            var fResize = obj.resize;
            var fRemove = obj.remove;
            //var fMoveFirst = obj.moveFirst;
            
            obj.updateHeaderLine=function() {
                var hline = obj.headerLine,
                    x = obj.getX(),
                    y = obj.getY(),
                    w = obj.getWidth(),
                    h = obj.getHeight();
                hline.lineOffset = obj.text.getBoundingClientRect().width+5;
                //console.log("Text info - width: "+obj.text.getBBox().width+", height: "+obj.text.getBBox().height);
                hline.setAttributeNS(null, "d", "M"+(x - w / 2 + obj.text.getBoundingClientRect().width+5)+" "+(y - h / 2) +" L"+(x - w / 2 + obj.text.getBoundingClientRect().width +5)+" "+(y + h / 2));
                hline.setAttributeNS(null,"bclass","swimlane");
                hline.setAttributeNS(null,"oclass","swimlane_o");
                hline.setBaseClass();
                if (obj.nextSibling) {
                    ToolKit.svg.insertBefore(hline, obj.nextSibling);
                }
            };
            
            obj.hasLanes = function() {
                return (obj.lanes && obj.lanes !== null && obj.lanes.length > 0);
            };
            
            obj.addLane = function(ob) {
                var lanes = obj.lanes, i, totHeight = 0, l;
                ob.setParent(obj);
                lanes.push(ob);
                
                if (ob.lindex === -1) {
                    var mindex=0;
                    
                    for (i = 0; i < lanes.length; i++) {
                        l = lanes[i];
                        if(l.lindex > mindex) {
                            mindex = l.lindex;
                        }
                    }                    
                    ob.lindex = mindex + 1; //obj.lanes.length;
                }
                
                for (i = 0; i < lanes.length; i++) {
                    l = obj.lanes[i];
                    totHeight += l.getHeight();
                }
                
                obj.resize(obj.getWidth(), totHeight);
                
                if (totHeight === 0) {
                    ob.resize(obj.getWidth()-obj.headerLine.lineOffset, obj.getHeight());
                    ob.move(obj.getX(), obj.getY());
                }
                
                obj.updateLanes();
                //obj.updateHeaderLine();
            };
            
            obj.updateLanes = function() {
                //console.log("lineOffset:"+obj.headerLine.lineOffset);
                var totWidth = obj.getWidth() - obj.headerLine.lineOffset;
                obj.lanes.sort(function(a,b) {
                    return a.lindex - b.lindex;
                });
                
                var lanes = obj.lanes, i, ypos = obj.getY() - obj.getHeight() / 2;
                for (i = 0; i < lanes.length; i++) {
                    lanes[i].resize(totWidth, lanes[i].getHeight());
                    lanes[i].move(obj.getX() + obj.headerLine.lineOffset / 2, ypos + lanes[i].getHeight() / 2);
                    ypos = ypos + obj.lanes[i].getHeight();
                }
                obj.updateHeaderLine();
            };
            
            obj.remove=function() {
                obj.headerLine.remove();
                fRemove();
            };
            
            obj.moveFirst=function() {
                //fMoveFirst();
                obj.updateLanes();
                obj.updateHeaderLine();
            };
            
            obj.resize= function(w, h) {
                fResize(w, h);
                obj.updateLanes();
                obj.updateHeaderLine();
            };
            
            obj.move = function(x, y) {
                fMove(x,y);
                obj.updateLanes();
                obj.updateHeaderLine();
            };
            return obj;
        },
    
        createTask:function(id, parent) {
            //Revisar ID
            var obj = ToolKit.createResizeObject(id,parent);
            obj.setAttributeNS(null,"bclass","task");
            obj.setAttributeNS(null,"oclass","task_o");
            obj.setAttributeNS(null,"rx",10);
            obj.setAttributeNS(null,"ry",10);
            obj.setBaseClass();                        
            obj.mouseup=function(x,y) {
                return true;
            };
            obj.mousedown=function(evt){return Modeler.objectMouseDown(evt,obj);};
            obj.onmousemove=function(evt){return Modeler.objectMouseMove(evt,obj);};
            
            return obj;
        },
        
        createCallTask:function(id, parent) {
            //Revisar ID
            var obj = Modeler.createTask(id,parent);
            obj.setAttributeNS(null,"bclass","callactivity");
            obj.setAttributeNS(null,"oclass","callactivity_o");
            obj.setBaseClass();                        
            return obj;
        },
        
        createAnnotationArtifact:function(id, parent) {
            var obj = ToolKit.createResizeObject(id,parent);
            obj.setAttributeNS(null,"oclass","annotationArtifactRect_o");
            obj.setAttributeNS(null,"bclass","annotationArtifactRect");
            obj.setBaseClass();
            
            var line = ToolKit.createBaseObject(function(){
                return document.createElementNS(ToolKit.svgNS,"path");
            }, null, null);
            
            obj.subLine = line;
            obj.subLine.canSelect=false;
            obj.subLine.setAttributeNS(null,"bclass","annotationArtifact");
            obj.subLine.setAttributeNS(null,"oclass","annotationArtifact");
            obj.subLine.setBaseClass();
            obj.mouseup=function(x,y) {
                return Modeler.mode === "view" ? false : true;
            };
            obj.mousedown=function(evt){return Modeler.objectMouseDown(evt,obj);};
            //obj.mousedown=function(evt){return Modeler.objectMouseDown(evt,obj);}
            obj.onmousemove=function(evt){return Modeler.objectMouseMove(evt,obj);};
            
            obj.updateSubLine=function() {
                var x = obj.getX(),
                    y = obj.getY(),
                    hw = obj.getWidth() / 2,
                    hh = obj.getHeight() / 2;
                obj.subLine.setAttributeNS(null, "d", "M" + (x - hw + 20) + " " + (y - hh)
                    +" L" + (x - hw) + " "+ (y - hh)
                    +" L" + (x - hw) + " "+ (y + hh)
                    +" L" + (x - hw + 20) + " "+ (y + hh));
                if (obj.nextSibling) {
                    ToolKit.svg.insertBefore(obj.subLine, obj.nextSibling);
                }
            };

            var fMove=obj.move;
            var fRemove=obj.remove;
            var fResize=obj.resize;
            var fMoveFirst = obj.moveFirst;
            
            obj.move=function(x,y) {
                fMove(x,y);
                obj.updateSubLine();
            };
            
            obj.remove=function() {
                obj.subLine.remove();
                fRemove();
            };
            
            obj.moveFirst=function() {
                fMoveFirst();
                obj.updateSubLine();
            };
            
            obj.resize=function(w,h) {
                fResize(w,h);
                obj.updateSubLine();
            };
            
            return obj;
        },
        
        createGroupArtifact:function(id,parent) {
            var obj = ToolKit.createResizeObject(id,parent);
            obj.setAttributeNS(null,"oclass","group_o");
            obj.setAttributeNS(null,"bclass","group");
            obj.setBaseClass();
            obj.setAttributeNS(null,"stroke-dasharray","20,5,3,5");
            
            obj.subLine = document.createElementNS(ToolKit.svgNS,"rect");
            obj.subLine.onmousedown=obj.onmousedown;
            obj.subLine.onmouseup=obj.onmouseup;
            obj.subLine.onmousemove=obj.onmousemove;
            
            obj.updateSubLine=function() {
                var subLine = obj.subLine || null;
                if (subLine !== null) {
                    var w = obj.getWidth(),
                        h = obj.getHeight();
                    subLine.setAttributeNS(null,"x", obj.getX() - w / 2);
                    subLine.setAttributeNS(null,"y", obj.getY() - h / 2);
                    subLine.setAttributeNS(null,"width", w);
                    subLine.setAttributeNS(null,"height", h);
                    ToolKit.svg.insertBefore(subLine, obj);
                    subLine.setAttributeNS(null,"class","sequenceFlowSubLine");
                }
            };
            
            var fMove=obj.move;
            var fRemove=obj.remove;
            var fResize=obj.resize;
            
            obj.move=function(x,y) {
                fMove(x,y);
                obj.updateSubLine();
            };
            
            obj.resize=function(w,h) {
                fResize(w,h);
                obj.updateSubLine();
            };
            
            obj.remove=function(all) {
                ToolKit.svg.removeChild(obj.subLine);
                fRemove(all);
                obj.subLine=null;
            };
            
            return obj;
        },
        
        createSubProcess: function(id, parent, type) {
            var obj = Modeler.createTask(id,parent),
                icon = obj.addIcon("#subProcessMarker",0,1,-1,-12),
                options = Modeler.options || {};
        
            obj.subLayer={parent:obj};
            
            if (options.layerNavigation) {
                icon.obj.ondblclick=function(evt) {
                    ToolKit.setLayer(obj.subLayer);
                };
            }
            
            switch(type) {
                case "eventsubProcess":
                    obj.setAttributeNS(null,"bclass","eventSubTask");
                    obj.setAttributeNS(null,"oclass","eventSubTask_o");
                    obj.setBaseClass();
                    break;
                case "callSubProcess":
                    obj.setAttributeNS(null,"bclass","callActivity");
                    obj.setAttributeNS(null,"oclass","callActivity_o");
                    obj.setBaseClass();
                    break;
                case "transactionsubProcess":
                    obj.subSquare && obj.subSquare.remove();
                    
                    var subsquare = obj.subSquare = ToolKit.createBaseObject(function() {
                        var tx = document.createElementNS(ToolKit.svgNS,"rect");
                        tx.setAttributeNS(null,"rx",10);
                        tx.setAttributeNS(null,"ry",10);
                        tx.setAttributeNS(null,"class","transactionSquare");
                        return tx;
                    }, null, null);
                    
                    subsquare.canSelect = false;
                    subsquare.onmousedown=function(evt) {
                        obj.onmousedown(evt);
                    };
                    subsquare.onmouseup=function(evt) {
                        obj.onmouseup(evt);
                    };      
                    subsquare.onmousemove=function(evt) {
                        obj.onmousemove(evt);
                    };
                    subsquare.ondblclick=function(evt) {
                        obj.ondblclick(evt);
                    };
                    subsquare.onDropObjects=function(objs) {
                        obj.onDropObjects(objs);
                    };

                    obj.updateSubSquare=function() {
                        var subsquare = obj.subSquare;
                        subsquare.move(obj.getX(),obj.getY());
                        subsquare.resize(obj.getWidth()-8,obj.getHeight()-8);
                        if (obj.nextSibling) {
                            ToolKit.svg.insertBefore(subsquare, obj.nextSibling);
                        }
                    };
                    
                    var fMove=obj.move,
                        fResize=obj.resize,
                        fMoveFirst=obj.moveFirst,
                        fShow = obj.show,
                        fHide = obj.hide,
                        fRemove = obj.remove;

                    obj.move=function(x,y) {
                        fMove(x,y);
                        obj.subSquare && obj.subSquare!==null && obj.updateSubSquare();
                    };

                    obj.resize=function(w,h) {
                        fResize(w,h);
                        obj.subSquare && obj.subSquare!==null && obj.updateSubSquare();
                    };

                    obj.moveFirst=function() {
                        fMoveFirst();
                        obj.updateSubSquare();
                    };
                    
                    obj.show = function() {
                        fShow();
                        if (obj.subSquare) {
                            obj.subSquare.show();
                        }
                    };
                    
                    obj.hide = function() {
                        fHide();
                        if (obj.subSquare) {
                            obj.subSquare.hide();
                        }
                    };
                    
                    obj.remove = function(all) {
                        fRemove(all);
                        if (obj.subSquare) {
                            obj.subSquare.remove();
                        }
                    }
                    break;
            }
            return obj;
        },
/***********Utilerías para manipular la información de los procesos*************/
        fadeInObject: function (obj) {
            var animation = document.createElementNS(ToolKit.svgNS, 'animate');
            animation.setAttributeNS(null, 'attributeName', 'fill-opacity');
            animation.setAttributeNS(null, 'begin', "0s");
            animation.setAttributeNS(null, 'from', "0");
            animation.setAttributeNS(null, 'to', "1");
            animation.setAttributeNS(null, 'dur', "0.2s");
            animation.setAttributeNS(null, 'fill', "freeze");
            obj.appendChild(animation);

            var animation2 = document.createElementNS(ToolKit.svgNS, 'animate');
            animation2.setAttributeNS(null, 'attributeName', 'stroke-opacity');
            animation2.setAttributeNS(null, 'begin', "0s");
            animation2.setAttributeNS(null, 'from', "0");
            animation2.setAttributeNS(null, 'to', "1");
            animation2.setAttributeNS(null, 'dur', "0.2s");
            animation2.setAttributeNS(null, 'fill', "freeze");
            obj.appendChild(animation2);
            
            animation2.beginElement();
        },
                
        fadeoutObject: function(obj) {
            var animation = document.createElementNS(ToolKit.svgNS, 'animate');
            animation.setAttributeNS(null, 'attributeName', 'fill-opacity');
            animation.setAttributeNS(null, 'begin', "0s");
            animation.setAttributeNS(null, 'from', "1");
            animation.setAttributeNS(null, 'to', "0");
            animation.setAttributeNS(null, 'dur', "0.2s");
            animation.setAttributeNS(null, 'fill', "freeze");
            obj.appendChild(animation);

            var animation2 = document.createElementNS(ToolKit.svgNS, 'animate');
            animation2.setAttributeNS(null, 'attributeName', 'stroke-opacity');
            animation2.setAttributeNS(null, 'begin', "0s");
            animation2.setAttributeNS(null, 'from', "1");
            animation2.setAttributeNS(null, 'to', "0");
            animation2.setAttributeNS(null, 'dur', "0.2s");
            animation2.setAttributeNS(null, 'fill', "freeze");
            obj.appendChild(animation2);
            
            animation2.beginElement();
        },

        getJSONObject: function(obj) {
            var ret = {};
            
            if (!obj.typeOf) return null;
            
            ret.class = obj.elementType;
            ret.uri=obj.id;
            if (obj.text && obj.text !== null) {
                ret.title = obj.text.value;
            }
            if (obj.typeOf("GraphicalElement")) {
                if (obj.getContainer() !== null) {
                    ret.container = obj.getContainer().id;
                }
                if (obj.parent && obj.parent !== null) {
                    ret.parent = obj.parent.id;
                }
                ret.labelSize="12";//TODO:Extraer tamaño de la fuente
                ret.description=ret.title;//TODO:Extraer descripción
                ret.x=obj.getX();
                ret.y=obj.getY();
                ret.w=obj.getWidth();
                ret.h=obj.getHeight();
                ret.isMultiInstance=false;//TODO: agregar método para verificar multiinstancia
                ret.isSequentialMultiInstance=false;//TODO: agregar método para verificar multiinstancia
                ret.isLoop=false;//TODO: agregar método para verificar ciclo
                ret.isForCompensation=false;//TODO: agregar método para verificar compensación
                if (obj.typeOf("IntermediateCatchEvent")) {
                    ret.isInterrupting=false;//TODO: agregar método para verificar interrupción
                }
                
                if (obj.typeOf("DataObject")) {
                    ret.isCollection=false;//TODO: agregar método para verificar colección
                }
                if (obj.elementType==="Lane") {
                    ret.lindex=obj.lindex;
                }
                if (obj.index && obj.index!==null) {
                    ret.index = obj.index;
                }
            }
            
            if (obj.typeOf("ConnectionObject")) { 
                ret.start=obj.fromObject.id;
                ret.end = obj.toObject.id;
                if (obj.connectionPoints && obj.connectionPoints !== undefined) {
                    ret.connectionPoints = obj.connectionPoints;
                }
                if (obj.title && obj.title !== undefined) {
                    ret.title = obj.title;
                }
            }
            return ret;
        },

        loadProcess: function(jsonString) {
            if (jsonString === null || jsonString ==="") {
                ToolKit.showTooltip(0,"Ocurrió un problema al cargar el modelo. Archivo mal formado.", 200, "Error");
                return;
            }
            ToolKit.setLayer(null);
            
            try {
                var json = JSON.parse(jsonString),
                    jsarr = json.nodes,
                    i = 0,
                    pools = [],
                    lanes = [],
                    connObjects = [],
                    flowNodes = [];

                Modeler.clearCanvas();
                while(i < jsarr.length) {
                    var tmp = jsarr[i],
                        cls = tmp.class;
                    
                    switch(cls) {
                        case "Lane":
                            lanes.push(tmp);
                            break;
                        case "Pool":
                            pools.push(tmp);
                            break;
                        case "SequenceFlow":
                        case "ConditionalFlow":
                        case "DefaultFlow":
                        case "AssociationFlow":
                        case "DirectionalAssociation":
                        case "MessageFlow":
                            connObjects.push(tmp);
                            break;
                        default:
                            flowNodes.push(tmp);
                            break;
                    }
                    i++;
                }

                //Construir pools
                var length = pools.length;
                for (i = 0; i < length; i++) {
                    var tmp = pools[i],
                        obj = Modeler.mapObject("Pool");
                
                    obj.setURI(tmp.uri);

                    if (tmp.title && tmp.title !== null) {
                        obj.setText(tmp.title);
                    }
                    obj.layer = null;
                    obj.resize(tmp.w, tmp.h);
                    obj.move(tmp.x, tmp.y);
                    obj.snap2Grid();

                    //Evitar edición de texto
                    if (Modeler.mode === "view") {
                        if (obj.text && obj.text !== null) {
                            obj.text.ondblclick = function(evt) {
                                return false;
                            };
                        }
                    }
                }

                //Construir lanes
                length = lanes.length;
                for (i = 0; i < length; i++) {
                    var tmp = lanes[i],
                        obj = Modeler.mapObject("Lane");
                
                    obj.setURI(tmp.uri);
                    if (tmp.title && tmp.title !== null) {
                        obj.setText(tmp.title);
                    }
                    if (tmp.lindex && tmp.lindex !== null) {
                        obj.setIndex(tmp.lindex);
                    }
                    obj.resize(tmp.w, tmp.h);
                    var par = Modeler.getGraphElementByURI(null, tmp.parent);
                    par.addLane(obj);
                    obj.setParent(par);
                    obj.layer = null;
                    par.move(par.getX(), par.getY());

                    //Evitar edición de texto
                    if (Modeler.mode === "view") {
                        if (obj.text && obj.text !== null) {
                            obj.text.ondblclick = function(evt) {
                                return false;
                            };
                        }
                    }
                }

                //Construir flowNodes
                length = flowNodes.length;
                for (i = 0; i < length; i++) {
                    var tmp = flowNodes[i],
                        obj = Modeler.mapObject(tmp.class);
                
                    if (obj === null || !obj.typeOf("GraphicalElement")) {
                        continue;
                    }

                    obj.setURI(tmp.uri);
                    if (tmp.title && tmp.title !== null) {
                        obj.setText(tmp.title);
                    }
                    if (obj.resizeable && obj.resizeable !== null) {
                        obj.resize(tmp.w, tmp.h);
                    }

                    if (tmp.index !== null && tmp.index) {
                        obj.index = tmp.index;
                    }

                    if (obj.typeOf("IntermediateCatchEvent") && tmp.isInterrupting && tmp.isInterrupting !== null) {
                        var par = Modeler.getGraphElementByURI(null, tmp.parent);
                        if (par && par!==null && par.typeOf("Activity") && !tmp.isInterrupting) {
                            obj.setInterruptor(false);
                        }
                    }

                    obj.move(tmp.x, tmp.y);
                    if (obj.typeOf("IntermediateCatchEvent") && tmp.parent === "") {
                        obj.snap2Grid();
                    }

                    //Evitar edición de texto
                    if (Modeler.mode === "view") {
                        if (obj.text && obj.text !== null) {
                            obj.text.ondblclick = function(evt) {
                                return false;
                            };
                        }
                    }
                }

                //Crear objetos de conexión
                length = connObjects.length;
                for (i = 0; i < length; i++) {
                    var tmp = connObjects[i],
                        start = Modeler.getGraphElementByURI(null, tmp.start) || null,
                        end = Modeler.getGraphElementByURI(null, tmp.end) || null,
                        obj, etype;
                    
                    if (start !== null && end !== null) {
                        obj = Modeler.mapObject(tmp.class);
                        etype = obj.elementType;
                        obj.setURI(tmp.uri);
                    
                        if (tmp.connectionPoints) {
                            obj.connectionPoints = tmp.connectionPoints;
                        }
                    
                        if (etype === "ConditionalFlow" && start.typeOf("Gateway")) {
                            obj.removeAttribute("marker-start");
                            obj.soff = 0;
                        }
                        start.addOutConnection(obj);
                        end.addInConnection(obj);
                        
                        if (etype === "ConditionalFlow" || etype === "DefaultFlow") {
                            if (obj.setLabel && typeof obj.setLabel === "function") {
                                obj.setLabel(tmp.title);
                            }
                        }
                    }
                }

                //Asignar contenedores de los flowNodes y lineas
                length = flowNodes.length;
                for (i = 0; i < length; i++) {
                    var tmp = flowNodes[i],
                        obj = Modeler.getGraphElementByURI(null, tmp.uri);
                
                    if (tmp.container && tmp.container !== null) {
                        var cont = Modeler.getGraphElementByURI(null, tmp.container);
                        if (cont !== null && obj !== null) {
                            obj.layer = cont.typeOf("SubProcess") ? cont.subLayer : null;
                        }
                    }
                }

                 //Asignar padres de los flowNodes
                length = flowNodes.length;
                for (i = 0; i < length; i++) {
                    var tmp = flowNodes[i],
                        obj = Modeler.getGraphElementByURI(null, tmp.uri),
                        cont = Modeler.getGraphElementByURI(null, tmp.container);
                
                    if (tmp.parent && tmp.parent !== null) {
                        var par = Modeler.getGraphElementByURI(null, tmp.parent);
                        if (par !== null && obj !== null) {
                            if (cont !== null && (par.elementType === "Lane" || par.elementType === "Pool")) {
                                obj.setParent(null);
                            } else {
                                obj.setParent(par);
                                if (obj.typeOf("IntermediateCatchEvent") && obj.parent.typeOf("Task")) {
                                    obj.moveFirst();
                                }
                            }
                        }
                    } else if (obj !== null && cont === null) {
                        ToolKit.svg.dragObject = obj;
                        ToolKit.selectObj(obj, false);
                        ToolKit.svg.onmouseup();
                    }
                }
                ToolKit.unSelectAll();
                ToolKit.setLayer(null);
            } catch (err) {
                console.log(err);
                ToolKit.showTooltip(0,"Ocurrió un problema al cargar el modelo."+err, 200, "Error");
            }
        },
                
        getNavPath: function(layer) {
            if (layer === null) {
                return null;
            }
            var p, ret = [],
                currentLayer = layer;
        
            do {
                p = currentLayer.parent || null;
                var pid = p !== null ? p.id : "";
                if (pid !== "") {
                    ret.push({label:p.text.value, layer:pid});
                    currentLayer = p.layer;
                }
            } while (currentLayer !== null);
            ret.push({label:"Principal", layer:""});
            return ret;
        },
                
        createNavPath: function() {
            Modeler.navPath && Modeler.navPath !== null && Modeler.navPath.bar.remove();
            
            var bar = ToolKit.createBaseObject(function() {
                var obj = document.createElementNS(ToolKit.svgNS, "rect");
                obj.setAttributeNS(null, "rx", "10");
                obj.setAttributeNS(null, "ry", "10");
                return obj;
            }, null, null);
            bar.setAttributeNS(null,"class","navPath");
            bar.resize(500, 50);
            bar.moveFirst();
            bar.move((window.pageXOffset+window.innerWidth)-bar.getWidth()/2-ToolKit.svg.offsetLeft-18, 0);
            
            var g = document.createElementNS(ToolKit.svgNS, "g");
            g.bar = bar;
            g.appendChild(bar);
            
            Modeler.navPath = g;
            
            Modeler.navPath.setNavigation = function(layer) {
                var links = Modeler.getNavPath(layer);
                
                if (Modeler.navPath.text && Modeler.navPath.text !== null) {
                    Modeler.navPath.removeChild(Modeler.navPath.text);
                    Modeler.navPath.text = null;
                }
                
                if (links !== null) {
                    links.reverse();
                    var text = document.createElementNS(ToolKit.svgNS, "text");
                    text.setAttributeNS(null, "x", bar.getX());
                    text.setAttributeNS(null, "y", bar.getY());
                    text.setAttributeNS(null, "style", "fill:black");
                    text.setAttributeNS(null,"text-anchor","middle");
                    text.setAttributeNS(null,"font-size","12");
                    text.setAttributeNS(null,"font-family","Verdana, Geneva, sans-serif");
                    
                    var i, length = links.length;
                    for (i = 0; i < length; i++) {
                        var tspan = document.createElementNS(ToolKit.svgNS, "tspan"),
                            tspanSeparator = null,
                            link = links[i],
                            l = link.label;
                        
                        tspan.appendChild(document.createTextNode(l));
                        if (link.layer === layer.parent.id) {
                            tspan.setAttributeNS(null,"font-weight","bold");
                        } else {
                            tspan.setAttributeNS(null,"onclick","if (Modeler.getGraphElementByURI(null,'"+link.layer+"') != null) {ToolKit.setLayer(Modeler.getGraphElementByURI(null,'"+link.layer+"').subLayer);} else {ToolKit.setLayer(null);}");
                        }
                        
                        if (i+1 < length) {
                            tspanSeparator = document.createElementNS(ToolKit.svgNS, "tspan");
                            tspanSeparator.appendChild(document.createTextNode(" > "));
                        }
                        
                        text.appendChild(tspan);
                        
                        if (tspanSeparator !== null) {
                            text.appendChild(tspanSeparator);
                        }
                    }
                    Modeler.navPath.appendChild(text);
                    Modeler.navPath.text=text;
                    Modeler.navPath.bar.style.display="";
                    Modeler.navPath.bar.resize(Modeler.navPath.text.getBBox().width+30, 30);
                    Modeler.navPath.bar.move((window.pageXOffset+window.innerWidth)-Modeler.navPath.bar.getWidth()/2-ToolKit.svg.offsetLeft-18, window.pageYOffset+Modeler.navPath.bar.getHeight()/2);
                } else {
                    Modeler.navPath.bar.style.display="none";
                }
            };
            
            var fMove = Modeler.navPath.bar.move;
            Modeler.navPath.bar.move = function(x, y) {
                fMove(x, y);
                if (Modeler.navPath.text && Modeler.navPath.text !== null) {
                    Modeler.navPath.text.setAttributeNS(null, "x", x);
                    Modeler.navPath.text.setAttributeNS(null, "y", y+4);
                }
            };
            Modeler.navPath.bar.style.display="none";
            ToolKit.svg.appendChild(g);
            window.onscroll = function(){if (Modeler.navPath && Modeler.navPath !== null) {Modeler.navPath.bar.move((window.pageXOffset+window.innerWidth)-Modeler.navPath.bar.getWidth()/2-ToolKit.svg.offsetLeft-18, window.pageYOffset+Modeler.navPath.bar.getHeight()/2);};};
        },

        getGraphElementByURI:function(parent, uri) {
            var i, par = parent === null ? ToolKit : parent,
                contents = par.contents || [],
                length = contents.length, c;
            
            for (i = 0; i < length; i++) {
                c = contents[i];
                if (c.id && c.id === uri) {
                    return c;
                }
            }
            return null;
        },

        submitCommand:function(url, data, callbackHandler) {
            var xhqr = $.post(url, {jsonString:data})
                .done(function(data) {
                    ToolKit.hideToolTip();
                    callbackHandler(data);
                    return data;
                }).fail(function(error) {
                    ToolKit.showTooltip(0,"Ocurrió un problema al ejecutar la operación: ", 200, "Error");
                    return error;
                });
        },
                
        clearCanvas: function() {
            var i, contents = ToolKit.contents || [];
        
            for (i = contents.length; i--;) {
                contents[i] && contents[i].remove && contents[i].remove(true);
                contents[i] = null;
            }
            
            ToolKit.contents = [];
            Modeler.count = 0;
        },
                
        getProcessJSON:function() {
            var i, j, ret = {uri:"test", nodes:[]},
                uris = "", contents = ToolKit.contents || [],
                length = contents.length,
                ele, json, nodes = [], outConnections, clength;
        
            for (i = 0; i < length; i++) {
                ele = contents[i];
                if (ele !== null && ele.typeOf && (ele.typeOf("GraphicalElement") || ele.typeOf("ConnectionObject"))) {
                    json = Modeler.getJSONObject(ele);
                    if (json !== null) {
                        nodes.push(json);
                    }
                    
                    outConnections = ele.outConnections;
                    clength = outConnections.length;
                    for (j = 0; j < clength; j++) {
                        if (uris.indexOf(outConnections[j].id) < 0) {
                            json = Modeler.getJSONObject(outConnections[j]);
                            if (json !== null) {
                                nodes.push(json);
                            }
                            uris += "|";
                            uris += outConnections[j].id;
                        }
                    }
                }
            }
            ret.nodes = nodes;
            return ret;
        },

        mapObject:function(type) {
            if (!type) {
                return null;
            }
            
            var ret = null;
            switch(type) {
                case 'Pool':
                    ret = new _Pool(Modeler.createPool(null, null));
                    ret.setText("Pool");
                    ret.resize(600,200);
                    break;
                case 'Lane':
                    ret = new _Lane(Modeler.createLane(null, null));
                    ret.setText("Lane");
                    ret.resize(600,200);
                    break;
                case 'SequenceFlow':
                    ret = new _SequenceFlow(Modeler.createConnectionPath(null, null, "sequenceArrow", null,"sequenceFlowLine"));
                    ret.eoff = 10;
                    break;
                case 'MessageFlow':
                    ret = new _MessageFlow(Modeler.createConnectionPath("messageTail", null, "messageArrow", "5,5", "sequenceFlowLine"));
                    ret.eoff = ret.soff = 0;
                    break;
                case 'ConditionalFlow':
                    ret = new _ConditionalFlow(Modeler.createConnectionPath("conditionTail", null, "sequenceArrow", null, "sequenceFlowLine"));
                    ret.setLabel("");
                    ret.eoff = ret.soff = 10;
                    break;
                case 'DefaultFlow':
                    ret = new _DefaultFlow(Modeler.createConnectionPath("defaultTail", null, "sequenceArrow", null, "sequenceFlowLine"));
                    ret.eoff = 10;
                    break;
                case 'AssociationFlow':
                    ret = new _AssociationFlow(Modeler.createConnectionPath(null, null, null, "5,5", "sequenceFlowLine"));
                    break;
                case 'DirectionalAssociation':
                    ret = new _DirectionalAssociation(Modeler.createConnectionPath(null, null, "messageArrow", "5,5", "sequenceFlowLine"));
                    ret.eoff = 10;
                    break;
                case 'StartEvent':
                    ret = new _StartEvent(Modeler.createObject("#startEvent",null,null));
                    ret.setText("Inicio Normal");
                    break;
                case 'MessageStartEvent':
                    ret = new _MessageStartEvent(Modeler.createObject("#messageStartEvent",null,null));
                    ret.setText("Inicio por mensaje");
                    break;
                case 'TimerStartEvent':
                    ret = new _TimerStartEvent(Modeler.createObject("#timerStartEvent",null,null));
                    ret.setText("Inicio temporizado");
                    break;
                case 'RuleStartEvent':
                    ret = new _RuleStartEvent(Modeler.createObject("#ruleStartEvent",null,null));
                    ret.setText("Inicio por regla de negocio");
                    break;
                case 'SignalStartEvent':
                    ret = new _SignalStartEvent(Modeler.createObject("#signalStartEvent",null,null));
                    ret.setText("Inicio por señal");
                    break;
                case 'MultipleStartEvent':
                    ret = new _MultipleStartEvent(Modeler.createObject("#multipleStartEvent",null,null));
                    ret.setText("Inicio múltiple");
                    break;
                case 'ParallelStartEvent':
                    ret = new _ParallelStartEvent(Modeler.createObject("#parallelStartEvent",null,null));
                    ret.setText("Inicio paralelo");
                    break;
                case 'ScalationStartEvent':
                    ret = new _ScalationStartEvent(Modeler.createObject("#scalationStartEvent",null,null));
                    ret.setText("Inicio por escalamiento");
                    break;
                case 'ErrorStartEvent':
                    ret = new _ErrorStartEvent(Modeler.createObject("#errorStartEvent",null,null));
                    ret.setText("Inicio por error");
                    break;
                case 'CompensationStartEvent':
                    ret = new _CompensationStartEvent(Modeler.createObject("#compensationStartEvent",null,null));
                    ret.setText("Inicio por compensación");
                    break;
                case 'MessageIntermediateCatchEvent':
                    ret = new _MessageIntermediateCatchEvent(Modeler.createObject("#messageIntermediateCatchEvent",null,null));
                    ret.setText("Recepción de mensaje");
                    break;
                case 'MessageIntermediateThrowEvent':
                    ret = new _MessageIntermediateThrowEvent(Modeler.createObject("#messageIntermediateThrowEvent",null,null));
                    ret.setText("Envío de mensaje");
                    break;
                case 'TimerIntermediateCatchEvent':
                    ret = new _TimerIntermediateCatchEvent(Modeler.createObject("#timerIntermediateEvent",null,null));
                    ret.setText("Temporizador");
                    break;
                case 'ErrorIntermediateCatchEvent':
                    ret = new _ErrorIntermediateCatchEvent(Modeler.createObject("#errorIntermediateEvent",null,null));
                    ret.setText("Recepción de error");
                    break;
                case 'CancelationIntermediateCatchEvent':
                    ret = new _CancelationIntermediateCatchEvent(Modeler.createObject("#cancelIntermediateEvent",null,null));
                    ret.setText("Cancelación");
                    break;
                case 'CompensationIntermediateCatchEvent':
                    ret = new _CompensationIntermediateCatchEvent(Modeler.createObject("#compensationIntermediateCatchEvent",null,null));
                    ret.setText("Recepción de compensación");
                    break;
                case 'CompensationIntermediateThrowEvent':
                    ret = new _CompensationIntermediateThrowEvent(Modeler.createObject("#compensationIntermediateThrowEvent",null,null));
                    ret.setText("Disparo de compensación");
                    break;
                case 'RuleIntermediateCatchEvent':
                    ret = new _RuleIntermediateCatchEvent(Modeler.createObject("#ruleIntermediateEvent",null,null));
                    ret.setText("Regla de negocio");
                    break;
                case 'LinkIntermediateCatchEvent':
                    ret = new _LinkIntermediateCatchEvent(Modeler.createObject("#linkIntermediateCatchEvent",null,null));
                    ret.setText("Recepción de enlace");
                    break;
                case 'LinkIntermediateThrowEvent':
                    ret = new _LinkIntermediateThrowEvent(Modeler.createObject("#linkIntermediateThrowEvent",null,null));
                    ret.setText("Disparo de enlace");
                    break;
                case 'SignalIntermediateCatchEvent':
                    ret = new _SignalIntermediateCatchEvent(Modeler.createObject("#signalIntermediateCatchEvent",null,null));
                    ret.setText("Recepción de señal");
                    break;
                case 'SignalIntermediateThrowEvent':
                    ret = new _SignalIntermediateThrowEvent(Modeler.createObject("#signalIntermediateThrowEvent",null,null));
                    ret.setText("Disparo de señal");
                    break;
                case 'MultipleIntermediateCatchEvent':
                    ret = new _MultipleIntermediateCatchEvent(Modeler.createObject("#multipleIntermediateCatchEvent",null,null));
                    ret.setText("Recepción múltiple");
                    break;
                case 'MultipleIntermediateThrowEvent':
                    ret = new _MultipleIntermediateThrowEvent(Modeler.createObject("#multipleIntermediateThrowEvent",null,null));
                    ret.setText("Disparo múltiple");
                    break;
                case 'ScalationIntermediateCatchEvent':
                    ret = new _ScalationIntermediateCatchEvent(Modeler.createObject("#scalationIntermediateCatchEvent",null,null));
                    ret.setText("Recepción de escalamiento");
                    break;
                case 'ScalationIntermediateThrowEvent':
                    ret = new _ScalationIntermediateThrowEvent(Modeler.createObject("#scalationIntermediateThrowEvent",null,null));
                    ret.setText("Disparo de escalamiento");
                    break;
                case 'ParallelIntermediateCatchEvent':
                    ret = new _ParallelIntermediateCatchEvent(Modeler.createObject("#parallelIntermediateEvent",null,null));
                    ret.setText("Paralelo");
                    break;
                case 'EndEvent':
                    ret = new _EndEvent(Modeler.createObject("#endEvent",null,null));
                    ret.setText("Fin normal");
                    break;
                case 'MessageEndEvent':
                    ret = new _MessageEndEvent(Modeler.createObject("#messageEndEvent",null,null));
                    ret.setText("Fin con mensaje");
                    break;
                case 'ErrorEndEvent':
                    ret = new _ErrorEndEvent(Modeler.createObject("#errorEndEvent",null,null));
                    ret.setText("Fin con error");
                    break;
                case 'CancelationEndEvent':
                    ret = new _CancelationEndEvent(Modeler.createObject("#cancelationEndEvent",null,null));
                    ret.setText("Fin con cancelación");
                    break;
                case 'CompensationEndEvent':
                    ret = new _CompensationEndEvent(Modeler.createObject("#compensationEndEvent",null,null));
                    ret.setText("Fin con compensación");
                    break;
                case 'SignalEndEvent':
                    ret = new _SignalEndEvent(Modeler.createObject("#signalEndEvent",null,null));
                    ret.setText("Fin con señal");
                    break;
                case 'MultipleEndEvent':
                    ret = new _MultipleEndEvent(Modeler.createObject("#multipleEndEvent",null,null));
                    ret.setText("Fin múltiple");
                    break;
                case 'ScalationEndEvent':
                    ret = new _ScalationEndEvent(Modeler.createObject("#scalationEndEvent",null,null));
                    ret.setText("Fin con escalamiento");
                    break;
                case 'TerminationEndEvent':
                    ret= new _TerminationEndEvent(Modeler.createObject("#terminationEndEvent",null,null));
                    ret.setText("Terminación");
                    break;
                case 'ExclusiveGateway':
                    ret = new _ExclusiveGateway(Modeler.createObject("#exclusiveDataGateway",null,null));
                    ret.setText("Exclusiva (datos)");
                    break;
                case 'InclusiveGateway':
                    ret = new _InclusiveGateway(Modeler.createObject("#inclusiveDataGateway",null,null));
                    ret.setText("Inclusiva (datos)");
                    break;
                case 'ExclusiveStartEventGateway':
                    ret = new _ExclusiveStartEventGateway(Modeler.createObject("#exclusiveStartGateway",null,null));
                    ret.setText("Exclusiva (evetos iniciales)");
                    break;
                case 'ExclusiveIntermediateEventGateway':
                    ret = new _ExclusiveIntermediateEventGateway(Modeler.createObject("#eventGateway",null,null));
                    ret.setText("Exclusiva (evetos intermedios)");
                    break;
                case 'ParallelGateway':
                    ret = new _ParallelGateway(Modeler.createObject("#parallelGateway",null,null));
                    ret.setText("Paralela");
                    break;
                case 'ParallelStartEventGateway':
                    ret = new _ParallelStartEventGateway(Modeler.createObject("#parallelStartGateway",null,null));
                    ret.setText("Paralela (eventos iniciales)");
                    break;
                case 'ComplexGateway':
                    ret = new _ComplexGateway(Modeler.createObject("#complexGateway",null,null));
                    ret.setText("Compleja");
                    break;
                case 'GroupArtifact':
                    ret = new _Group(Modeler.createGroupArtifact(null,null));
                    ret.resize(300,300);
                    break;
                case 'AnnotationArtifact':
                    ret = new _AnnotationArtifact(Modeler.createAnnotationArtifact(null, null));
                    ret.setText("Anotación de texto");
                    ret.resize(200,60);
                    break;
                case 'DataObject':
                    ret = new _DataObject(Modeler.createObject("#data",null,null));
                    ret.setText("Dato");
                    break;
                case 'DataInput':
                    ret = new _DataInput(Modeler.createObject("#dataInput",null,null));
                    ret.setText("Dato de entrada");
                    break;
                case 'DataOutput':
                    ret = new _DataOutput(Modeler.createObject("#dataOutput",null,null));
                    ret.setText("Dato de salida");
                    break;
                case 'DataStore':
                    ret = new _DataStore(Modeler.createObject("#dataStore",null,null));
                    ret.setText("Almacén de datos");
                    break;
                case 'UserTask':
                    ret = new _UserTask(Modeler.createTask(null,null));
                    ret.addIcon("#userMarker",-1,-1,13,8);
                    ret.setText("Tarea de Usuario");
                    ret.resize(100,60);
                    break;
                case 'ServiceTask':
                    ret = new _ServiceTask(Modeler.createTask(null,null));
                    ret.addIcon("#serviceMarker",-1,-1,13,8);
                    ret.setText("Tarea de Servicio");
                    ret.resize(100,60);
                    break;
                case 'ScriptTask':
                    ret = new _ScriptTask(Modeler.createTask(null,null));
                    ret.addIcon("#scriptMarker",-1,-1,7,13);
                    ret.setText("Tarea de Script");
                    ret.resize(100,60);
                    break;
                case 'BusinessRuleTask':
                    ret = new _BusinessRuleTask(Modeler.createTask(null,null));
                    ret.addIcon("#taskRuleMarker",-1,-1,12,12);
                    ret.setText("Tarea de regla de negocio");
                    ret.resize(100,60);
                    break;
                case 'ManualTask':
                    ret = new _ManualTask(Modeler.createTask(null,null));
                    ret.addIcon("#manualMarker",-1,-1,9,6);
                    ret.setText("Tarea Manual");
                    ret.resize(100,60);
                    break;
                case 'SendTask':
                    ret = new _SendTask(Modeler.createTask(null,null));
                    ret.addIcon("#taskMessageThrowMarker",-1,-1,13,10);
                    ret.setText("Tarea de envío de mensaje");
                    ret.resize(100,60);
                    break;
                case 'ReceiveTask':
                    ret = new _ReceiveTask(Modeler.createTask(null,null));
                    ret.addIcon("#taskMessageCatchMarker",-1,-1,13,10);
                    ret.setText("Tarea de recepción de mensaje");
                    ret.resize(100,60);
                    break;
                case 'Task':
                    ret = new _Task(Modeler.createTask(null,null));
                    ret.setText("Tarea abstracta");
                    ret.resize(100,60);
                    break;
                case 'CallTask':
                    ret = new _CallTask(Modeler.createCallTask(null,null));
                    ret.setText("Tarea reusada");
                    ret.resize(100,60);
                    break;
                case 'CallManualTask':
                    ret = new _CallManualTask(Modeler.createCallTask(null,null));
                    ret.addIcon("#manualMarker",-1,-1,9,6);
                    ret.setText("Tarea manual reusada");
                    ret.resize(100,60);
                    break;
                case 'CallBusinessRuleTask':
                    ret = new _CallBusinessRuleTask(Modeler.createCallTask(null,null));
                    ret.addIcon("#taskRuleMarker",-1,-1,12,12);
                    ret.setText("Tarea de regla de negocio reusada");
                    ret.resize(100,60);
                    break;
                case 'CallScriptTask':
                    ret = new _CallScriptTask(Modeler.createCallTask(null,null));
                    ret.addIcon("#scriptMarker",-1,-1,7,13);
                    ret.setText("Tarea de script reusada");
                    ret.resize(100,60);
                    break;
                case 'CallUserTask':
                    ret = new _CallBusinessRuleTask(Modeler.createCallTask(null,null));
                    ret.addIcon("#userMarker",-1,-1,13,8);
                    ret.setText("Tarea de usuario reusada");
                    ret.resize(100,60);
                    break;
                case 'SubProcess':
                    ret = new _SubProcess(Modeler.createSubProcess(null, null, ""));
                    ret.setText("Subproceso");
                    ret.resize(100,60);
                    break;
                case 'CallSubProcess':
                    ret = new _CallSubProcess(Modeler.createSubProcess(null, null, "callSubProcess"));
                    ret.setText("Subproceso");
                    ret.resize(100,60);
                    break;
                case 'AdhocSubProcess':
                    ret = new _AdhocSubProcess(Modeler.createSubProcess(null, null, ""));
                    ret.setText("Subproceso adhoc");
                    ret.resize(100,60);
                    break;
                case 'EventSubProcess':
                    ret = new _EventSubProcess(Modeler.createSubProcess(null, null, "eventsubProcess"));
                    ret.setText("Subproceso de evento");
                    ret.resize(100,60);
                    break;
                case 'TransactionSubProcess':
                    ret = new _TransactionSubProcess(Modeler.createSubProcess(null, null, "transactionsubProcess"));
                    ret.setText("Transacción");
                    ret.resize(100,60);
                    break;
            }
            return ret;
        }
    };