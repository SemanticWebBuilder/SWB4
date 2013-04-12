/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
                ret.elementType="____";
                ret.setText("Inicio por mensaje",0,1,80,1);
            }
            else if(type=='timerStartEvent'){
                ret=Modeler.createObject("#timerStartEvent",null,null);
                ret.elementType="____";
                ret.setText("Inicio temporizado",0,1,80,1);
            }
            else if(type=='ruleStartEvent') {
                ret=Modeler.createObject("#ruleStartEvent",null,null);
                ret.elementType="____";
                ret.setText("Inicio por regla de negocio",0,1,80,1);
            }
            else if(type=='signalStartEvent') {
                ret=Modeler.createObject("#signalStartEvent",null,null);
                ret.elementType="____";
                ret.setText("Inicio por señal",0,1,80,1);
            }
            else if(type=='multiStartEvent') {
                ret=Modeler.createObject("#multipleStartEvent",null,null);
                ret.elementType="____";
                ret.setText("Inicio múltiple",0,1,80,1);
            }
            else if(type=='parallelStartEvent') {
                ret=Modeler.createObject("#parallelStartEvent",null,null);
                ret.elementType="____";
                ret.setText("Inicio paralelo",0,1,80,1);
            }
            else if(type=='scalaStartEvent') {
                ret= Modeler.createObject("#scalationStartEvent",null,null);
                ret.elementType="____";
                ret.setText("Inicio por escalamiento",0,1,80,1);
            }
            else if(type=='errorStartEvent') {
                ret= Modeler.createObject("#errorStartEvent",null,null);
                ret.elementType="____";
                ret.setText("Inicio por error",0,1,80,1);
            }
            else if(type=='compensaStartEvent') {
                ret= Modeler.createObject("#compensationStartEvent",null,null);
                ret.elementType="____";
                ret.setText("Inicio por compensación",0,1,80,1);
            }
            else if(type=='messageInterCatchEvent') {
                ret= Modeler.createObject("#messageIntermediateCatchEvent",null,null);
                ret.elementType="____";
                ret.setText("Recepción de mensaje",0,1,80,1);
            }
            else if(type=='messageInterThrowEvent') {
                ret= Modeler.createObject("#messageIntermediateThrowEvent",null,null);
                ret.elementType="____";
                ret.setText("Envío de mensaje",0,1,80,1);
            }
            else if(type=='timerInterEvent') {
                ret= Modeler.createObject("#timerIntermediateEvent",null,null);
                ret.elementType="____";
                ret.setText("Temporizador",0,1,80,1);
            }
            else if(type=='errorInterEvent') {
                ret= Modeler.createObject("#errorIntermediateEvent",null,null);
                ret.elementType="____";
                ret.setText("Recepción de error",0,1,80,1);
            }
            else if(type=='cancelInterEvent') {
                ret= Modeler.createObject("#cancelIntermediateEvent",null,null);
                ret.elementType="____";
                ret.setText("Cancelación",0,1,80,1);
            }
            else if(type=='compensaInterCatchEvent') {
                ret= Modeler.createObject("#compensationIntermediateCatchEvent",null,null);
                ret.elementType="____";
                ret.setText("Recepción de compensación",0,1,80,1);
            }
            else if(type=='compensaInterThrowEvent') {
                ret= Modeler.createObject("#compensationIntermediateThrowEvent",null,null);
                ret.elementType="____";
                ret.setText("Disparo de compensación",0,1,80,1);
            }
            else if(type=='ruleInterEvent') {
                ret= Modeler.createObject("#ruleIntermediateEvent",null,null);
                ret.elementType="____";
                ret.setText("Regla de negocio",0,1,80,1);
            }
            else if(type=='linkInterCatchEvent') {
                ret= Modeler.createObject("#linkIntermediateCatchEvent",null,null);
                ret.elementType="____";
                ret.setText("Recepción de enlace",0,1,80,1);
            }
            else if(type=='linkInterThrowEvent') {
                ret= Modeler.createObject("#linkIntermediateThrowEvent",null,null);
                ret.elementType="____";
                ret.setText("Disparo de enlace",0,1,80,1);
            }
            else if(type=='signalInterCatchEvent') {
                ret= Modeler.createObject("#signalIntermediateCatchEvent",null,null);
                ret.elementType="____";
                ret.setText("Recepción de señal",0,1,80,1);
            }
            else if(type=='signalInterThrowEvent') {
                ret= Modeler.createObject("#signalIntermediateThrowEvent",null,null);
                ret.elementType="____";
                ret.setText("Disparo de señal",0,1,80,1);
            }
            else if(type=='multipleInterCatchEvent') {
                ret= Modeler.createObject("#multipleIntermediateCatchEvent",null,null);
                ret.elementType="____";
                ret.setText("Recepción múltiple",0,1,80,1);
            }
            else if(type=='multipleInterThrowEvent') {
                ret= Modeler.createObject("#multipleIntermediateThrowEvent",null,null);
                ret.elementType="____";
                ret.setText("Disparo múltiple",0,1,80,1);
            }
            else if(type=='scalaInterCatchEvent') {
                ret= Modeler.createObject("#scalationIntermediateCatchEvent",null,null);
                ret.elementType="____";
                ret.setText("Recepción de escalamiento",0,1,80,1);
            }
            else if(type=='scalaInterThrowEvent') {
                ret= Modeler.createObject("#scalationIntermediateThrowEvent",null,null);
                ret.elementType="____";
                ret.setText("Disparo de escalamiento",0,1,80,1);
            }
            else if(type=='parallelInterEvent') {
                ret= Modeler.createObject("#parallelIntermediateEvent",null,null);
                ret.elementType="____";
                ret.setText("Paralelo",0,1,80,1);
            }
            else if(type=='normalEndEvent') {
                ret= Modeler.createObject("#endEvent",null,null);
                ret.elementType="____";
                ret.setText("Fin normal",0,1,80,1);
            }
            else if(type=='messageEndEvent') {
                ret= Modeler.createObject("#messageEndEvent",null,null);
                ret.elementType="____";
                ret.setText("Fin con mensaje",0,1,80,1);
            }
            else if(type=='errorEndEvent') {
                ret= Modeler.createObject("#errorEndEvent",null,null);
                ret.elementType="____";
                ret.setText("Fin con error",0,1,80,1);
            }
            else if(type=='cancelEndEvent') {
                ret= Modeler.createObject("#cancelationEndEvent",null,null);
                ret.elementType="____";
                ret.setText("Fin con cancelación",0,1,80,1);
            }
            else if(type=='compensaEndEvent') {
                ret= Modeler.createObject("#compensationEndEvent",null,null);
                ret.elementType="____";
                ret.setText("Fin con compensación",0,1,80,1);
            }
            else if(type=='signalEndEvent') {
                ret= Modeler.createObject("#signalEndEvent",null,null);
                ret.elementType="____";
                ret.setText("Fin con señal",0,1,80,1);
            }
            else if(type=='multiEndEvent') {
                ret= Modeler.createObject("#multipleEndEvent",null,null);
                ret.elementType="____";
                ret.setText("Fin múltiple",0,1,80,1);
            }
            else if(type=='escalaEndEvent') {
                ret= Modeler.createObject("#scalationEndEvent",null,null);
                ret.elementType="____";
                ret.setText("Fin con escalamiento",0,1,80,1);
            }
            else if(type=='terminalEndEvent') {
                ret= Modeler.createObject("#terminationEndEvent",null,null);
                ret.elementType="____";
                ret.setText("Terminación",0,1,80,1);
            }
            else if(type=='exclusiveDataGateway') {
                ret= Modeler.createObject("#exclusiveDataGateway",null,null);
                ret.elementType="____";
                ret.setText("Exclusiva (datos)",0,1,80,1);
            }
            else if(type=='inclusiveDataGateway') {
                ret= Modeler.createObject("#inclusiveDataGateway",null,null);
                ret.elementType="____";
                ret.setText("Inclusiva (datos)",0,1,80,1);
            }
            else if(type=='exclusiveStartEventGateway') {
                ret= Modeler.createObject("#exclusiveStartGateway",null,null);
                ret.elementType="____";
                ret.setText("Exclusiva de inicio",0,1,80,1);
            }
            else if(type=='exclusiveEventGateway') {
                ret= Modeler.createObject("#eventGateway",null,null);
                ret.elementType="____";
                ret.setText("Exclusiva (eventos)",0,1,80,1);
            }
            else if(type=='parallelGateway') {
                ret= Modeler.createObject("#parallelGateway",null,null);
                ret.elementType="____";
                ret.setText("Paralela",0,1,80,1);
            }
            else if(type=='parallelStartGateway') {
                ret= Modeler.createObject("#parallelStartGateway",null,null);
                ret.elementType="____";
                ret.setText("Paralela de inicio",0,1,80,1);
            }
            else if(type=='complexGateway') {
                ret= Modeler.createObject("#complexGateway",null,null);
                ret.elementType="____";
                ret.setText("Compleja",0,1,80,1);
            }
            else if(type=='group') {
                ret= Modeler.createGroupArtifact(null,null);
                ret.elementType="____";
                ret.resize(300,300);
            }
            else if(type=='annotation'){
                ret= Modeler.createAnnotationArtifact(null, null);
                ret.elementType="____";
                ret.setText("Anotación de texto",0,0,0,1);
                ret.resize(200,60);
            }
            else if(type=='dataObject') {
                ret= Modeler.createObject("#data",null,null);
                ret.elementType="____";
                ret.setText("Dato",0,1,80,1);
            }
            else if(type=='dataInput') {
                ret= Modeler.createObject("#dataInput",null,null);
                ret.elementType="____";
                ret.setText("Dato de entrada",0,1,80,1);
            }
            else if(type=='dataOutput') {
                ret= Modeler.createObject("#dataOutput",null,null);
                ret.elementType="____";
                ret.setText("Dato de salida",0,1,80,1);
            }
            else if(type=='dataStore') {
                ret= Modeler.createObject("#dataStore",null,null);
                ret.elementType="____";
                ret.setText("Almacén de datos",0,1,80,1);
            }
            else if(type=='userTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="____";
                ret.addIcon("#userMarker",-1,-1,13,8);
                ret.setText("Tarea de Usuario",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='serviceTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="____";
                ret.addIcon("#serviceMarker",-1,-1,13,8);
                ret.setText("Tarea de Servicio",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='scriptTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="____";
                ret.addIcon("#scriptMarker",-1,-1,7,13);
                ret.setText("Tarea de Script",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='ruleTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="____";
                ret.addIcon("#taskRuleMarker",-1,-1,12,12);
                ret.setText("Tarea de regla de negocio",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='manualTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="____";
                ret.addIcon("#manualMarker",-1,-1,9,6);
                ret.setText("Tarea Manual",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='sendTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="____";
                ret.addIcon("#taskMessageThrowMarker",-1,-1,13,10);
                ret.setText("Tarea de envío de mensaje",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='receiveTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="____";
                ret.addIcon("#taskMessageCatchMarker",-1,-1,13,10);
                ret.setText("Tarea de recepción de mensaje",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='abstractTask') {
                ret = Modeler.createTask(null,null);
                ret.elementType="____";
                ret.setText("Tarea abstracta",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callTask') {
                ret = Modeler.createCallTask(null,null);
                ret.elementType="____";
                ret.setText("Tarea reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callmanualTask') {
                ret = Modeler.createCallTask(null,null);
                ret.elementType="____";
                ret.addIcon("#manualMarker",-1,-1,9,6);
                ret.setText("Tarea manual reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callruleTask') {
                ret = Modeler.createCallTask(null,null);
                ret.elementType="____";
                ret.addIcon("#taskRuleMarker",-1,-1,12,12);
                ret.setText("Tarea de regla de negocio reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callscriptTask') {
                ret = Modeler.createCallTask(null,null);
                ret.elementType="____";
                ret.addIcon("#scriptMarker",-1,-1,7,13);
                ret.setText("Tarea de script reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='calluserTask') {
                ret = Modeler.createCallTask(null,null);
                ret.elementType="____";
                ret.addIcon("#userMarker",-1,-1,13,8);
                ret.setText("Tarea de usuario reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='subProcess') {
                ret = Modeler.createSubProcess(null, null, "");
                ret.elementType="____";
                ret.setText("SubProceso",0,0,200,1);
                ret.resize(100,60);
            }
            else if(type=='eventsubProcess') {
                ret = Modeler.createSubProcess(null, null, "eventsubProcess");
                ret.elementType="____";
                ret.setText("SubProceso",0,0,200,1);
                ret.resize(100,60);
            }
            else if(type=='transactionsubProcess') {
                ret = Modeler.createSubProcess(null, null, "transactionsubProcess");
                ret.elementType="____";
                ret.setText("SubProceso",0,0,200,1);
                ret.resize(100,60);
            }
            else if (type=="pool") {
                ret = Modeler.createSwimLane(null, null);
                ret.elementType="____";
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

