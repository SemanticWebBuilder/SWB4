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
            if(!ToolKit.svg.offsetLeft)ToolKit.svg.offsetLeft=60;
            if(!ToolKit.svg.offsetTop)ToolKit.svg.offsetTop=10;
        },
        
        creationStart:function(span)
        {
            Modeler.creationId=span.getAttribute("class");
        },
        
        createObject:function(type, id, parent)
        {
            var obj=ToolKit.createUseObject(type,id,parent);
            return obj;
        },
        
        /*createPath:function(marker_start, marker_mid, marker_end, dash_array, styleClass) {
            var obj = ToolKit.createPath(marker_start, marker_mid, marker_end, dash_array, styleClass);
            obj.setArrowType= function(type) {
                ret.setAttributeNS(null, "marker-end", "url(#"+type+")");
            }
            obj.setTailType= function(type) {
                ret.setAttributeNS(null, "marker-start", "url(#"+type+")");
            }
            return obj;
        },*/
        
        createSwimLane:function(id, parent)
        {
            //Revisar ID
            var obj=ToolKit.createResizeObject(id,parent);
            obj.setAttributeNS(null,"bclass","swimlane");
            obj.setAttributeNS(null,"oclass","swimlane_o");
            //obj.setAttributeNS(null,"rx",10);
            //obj.setAttributeNS(null,"ry",10);
            obj.setBaseClass();                        
            obj.mouseup=function(x,y)
            {
                //alert("hola1");
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
            }
            return obj;
        },
        
        createCallTask:function(id, parent)
        {
            //Revisar ID
            var obj=ToolKit.createResizeObject(id,parent);
            obj.setAttributeNS(null,"bclass","callactivity");
            obj.setAttributeNS(null,"oclass","callactivity_o");
            obj.setAttributeNS(null,"rx",10);
            obj.setAttributeNS(null,"ry",10);
            obj.setBaseClass();                        
            obj.mouseup=function(x,y)
            {
                //alert("hola1");
            }
            return obj;
        },

        createStartEvent:function(id, parent)
        {
            //Revisar ID
            var obj=this.createObject("#startEvent",id,parent);
            obj.mouseup=function(x,y)
            {
                //alert("hola1");
            }
            return obj;
        },

        createMessageStartEvent:function(id, parent)
        {
            //Revisar ID
            var obj=this.createStartEvent(id,parent);
            //obj.mouseup=function(x,y)
            //{
            //    alert("hola2");
            //}
            return obj;
        },
        
        onmousedown:function(evt)
        {
            if(Modeler.creationId!=null)
            {
                var obj=Modeler.mapObject(Modeler.creationId);
                obj.move(ToolKit.getEventX(evt), ToolKit.getEventY(evt));
//                obj.setX(ToolKit.getEventX(evt));
//                obj.setY(ToolKit.getEventY(evt));
                //desc(evt,true);
                Modeler.creationId=null;
                return false;
            }
            return true;
        },
       
        mapObject:function(type)
        {
            /*var ret = null;
            if(type=='sequenceFlow') {
                ret = Modeler.createPath(null, null, "sequenceArrow", null,"sequenceFlowLine");
            }
            if(type=='messageFlow') {
                ret = Modeler.createPath("messageTail", null, "messageArrow", "5,5", "sequenceFlowLine");
            }
            if(type=='conditionalFlow') {
                ret = Modeler.createPath("conditionTail", null, "sequenceArrow", null, "sequenceFlowLine");
            }
            if(type=='defaultFlow') {
                ret = Modeler.createPath("defaultTail", null, "sequenceArrow", null, "sequenceFlowLine");
            }
            if(type=='associationFlow') {
                ret = Modeler.createPath(null, null, null, "5,5", "sequenceFlowLine");
            }
            if(type=='directionalassociationFlow') {
                ret = Modeler.createPath(null, null, "messageArrow", "5,5", "sequenceFlowLine");
            }
            else*/ if(type=='normalStartEvent')
            {
                ret=Modeler.createObject("#startEvent",null,null);
                ret.setText("Inicio Normal",0,1,80,1);
            }
            else if(type=='messageStartEvent'){
                ret=Modeler.createObject("#messageStartEvent",null,null);
                ret.setText("Inicio por mensaje",0,1,80,1);
            }
            else if(type=='timerStartEvent'){
                ret=Modeler.createObject("#timerStartEvent",null,null);
                ret.setText("Inicio temporizado",0,1,80,1);
            }
            else if(type=='ruleStartEvent') {
                ret=Modeler.createObject("#ruleStartEvent",null,null);
                ret.setText("Inicio por regla de negocio",0,1,80,1);
            }
            else if(type=='signalStartEvent') {
                ret=Modeler.createObject("#signalStartEvent",null,null);
                ret.setText("Inicio por señal",0,1,80,1);
            }
            else if(type=='multiStartEvent') {
                ret=Modeler.createObject("#multipleStartEvent",null,null);
                ret.setText("Inicio múltiple",0,1,80,1);
            }
            else if(type=='parallelStartEvent') {
                ret=Modeler.createObject("#parallelStartEvent",null,null);
                ret.setText("Inicio paralelo",0,1,80,1);
            }
            else if(type=='scalaStartEvent') {
                ret= Modeler.createObject("#scalationStartEvent",null,null);
                ret.setText("Inicio por escalamiento",0,1,80,1);
            }
            else if(type=='errorStartEvent') {
                ret= Modeler.createObject("#errorStartEvent",null,null);
                ret.setText("Inicio por error",0,1,80,1);
            }
            else if(type=='compensaStartEvent') {
                ret= Modeler.createObject("#compensationStartEvent",null,null);
                ret.setText("Inicio por compensación",0,1,80,1);
            }
            else if(type=='messageInterCatchEvent') {
                ret= Modeler.createObject("#messageIntermediateCatchEvent",null,null);
                ret.setText("Recepción de mensaje",0,1,80,1);
            }
            else if(type=='messageInterThrowEvent') {
                ret= Modeler.createObject("#messageIntermediateThrowEvent",null,null);
                ret.setText("Envío de mensaje",0,1,80,1);
            }
            else if(type=='timerInterEvent') {
                ret= Modeler.createObject("#timerIntermediateEvent",null,null);
                ret.setText("Temporizador",0,1,80,1);
            }
            else if(type=='errorInterEvent') {
                ret= Modeler.createObject("#errorIntermediateEvent",null,null);
                ret.setText("Recepción de error",0,1,80,1);
            }
            else if(type=='cancelInterEvent') {
                ret= Modeler.createObject("#cancelIntermediateEvent",null,null);
                ret.setText("Cancelación",0,1,80,1);
            }
            else if(type=='compensaInterCatchEvent') {
                ret= Modeler.createObject("#compensationIntermediateCatchEvent",null,null);
                ret.setText("Recepción de compensación",0,1,80,1);
            }
            else if(type=='compensaInterThrowEvent') {
                ret= Modeler.createObject("#compensationIntermediateThrowEvent",null,null);
                ret.setText("Disparo de compensación",0,1,80,1);
            }
            else if(type=='ruleInterEvent') {
                ret= Modeler.createObject("#ruleIntermediateEvent",null,null);
                ret.setText("Regla de negocio",0,1,80,1);
            }
            else if(type=='linkInterCatchEvent') {
                ret= Modeler.createObject("#linkIntermediateCatchEvent",null,null);
                ret.setText("Recepción de enlace",0,1,80,1);
            }
            else if(type=='linkInterThrowEvent') {
                ret= Modeler.createObject("#linkIntermediateThrowEvent",null,null);
                ret.setText("Disparo de enlace",0,1,80,1);
            }
            else if(type=='signalInterCatchEvent') {
                ret= Modeler.createObject("#signalIntermediateCatchEvent",null,null);
                ret.setText("Recepción de señal",0,1,80,1);
            }
            else if(type=='signalInterThrowEvent') {
                ret= Modeler.createObject("#signalIntermediateThrowEvent",null,null);
                ret.setText("Disparo de señal",0,1,80,1);
            }
            else if(type=='multipleInterCatchEvent') {
                ret= Modeler.createObject("#multipleIntermediateCatchEvent",null,null);
                ret.setText("Recepción múltiple",0,1,80,1);
            }
            else if(type=='multipleInterThrowEvent') {
                ret= Modeler.createObject("#multipleIntermediateThrowEvent",null,null);
                ret.setText("Disparo múltiple",0,1,80,1);
            }
            else if(type=='scalaInterCatchEvent') {
                ret= Modeler.createObject("#scalationIntermediateCatchEvent",null,null);
                ret.setText("Recepción de escalamiento",0,1,80,1);
            }
            else if(type=='scalaInterThrowEvent') {
                ret= Modeler.createObject("#scalationIntermediateThrowEvent",null,null);
                ret.setText("Disparo de escalamiento",0,1,80,1);
            }
            else if(type=='parallelInterEvent') {
                ret= Modeler.createObject("#parallelIntermediateEvent",null,null);
                ret.setText("Paralelo",0,1,80,1);
            }
            else if(type=='normalEndEvent') {
                ret= Modeler.createObject("#endEvent",null,null);
                ret.setText("Fin normal",0,1,80,1);
            }
            else if(type=='messageEndEvent') {
                ret= Modeler.createObject("#messageEndEvent",null,null);
                ret.setText("Fin con mensaje",0,1,80,1);
            }
            else if(type=='errorEndEvent') {
                ret= Modeler.createObject("#errorEndEvent",null,null);
                ret.setText("Fin con error",0,1,80,1);
            }
            else if(type=='cancelEndEvent') {
                ret= Modeler.createObject("#cancelationEndEvent",null,null);
                ret.setText("Fin con cancelación",0,1,80,1);
            }
            else if(type=='compensaEndEvent') {
                ret= Modeler.createObject("#compensationEndEvent",null,null);
                ret.setText("Fin con compensación",0,1,80,1);
            }
            else if(type=='signalEndEvent') {
                ret= Modeler.createObject("#signalEndEvent",null,null);
                ret.setText("Fin con señal",0,1,80,1);
            }
            else if(type=='multiEndEvent') {
                ret= Modeler.createObject("#multipleEndEvent",null,null);
                ret.setText("Fin múltiple",0,1,80,1);
            }
            else if(type=='escalaEndEvent') {
                ret= Modeler.createObject("#scalationEndEvent",null,null);
                ret.setText("Fin con escalamiento",0,1,80,1);
            }
            else if(type=='terminalEndEvent') {
                ret= Modeler.createObject("#terminationEndEvent",null,null);
                ret.setText("Terminación",0,1,80,1);
            }
            else if(type=='exclusiveDataGateway') {
                ret= Modeler.createObject("#exclusiveDataGateway",null,null);
                ret.setText("Exclusiva (datos)",0,1,80,1);
            }
            else if(type=='inclusiveDataGateway') {
                ret= Modeler.createObject("#inclusiveDataGateway",null,null);
                ret.setText("Inclusiva (datos)",0,1,80,1);
            }
            else if(type=='exclusiveStartEventGateway') {
                ret= Modeler.createObject("#exclusiveStartGateway",null,null);
                ret.setText("Exclusiva de inicio",0,1,80,1);
            }
            else if(type=='exclusiveEventGateway') {
                ret= Modeler.createObject("#eventGateway",null,null);
                ret.setText("Exclusiva (eventos)",0,1,80,1);
            }
            else if(type=='parallelGateway') {
                ret= Modeler.createObject("#parallelGateway",null,null);
                ret.setText("Paralela",0,1,80,1);
            }
            else if(type=='parallelStartGateway') {
                ret= Modeler.createObject("#parallelStartGateway",null,null);
                ret.setText("Paralela de inicio",0,1,80,1);
            }
            else if(type=='complexGateway') {
                ret= Modeler.createObject("#complexGateway",null,null);
                ret.setText("Compleja",0,1,80,1);
            }
            else if(type=='annotation')ret= Modeler.createObject("#xxxxxxx",null,null);
            else if(type=='group')ret= Modeler.createObject("#xxxxxxx",null,null);
            else if(type=='dataObject') {
                ret= Modeler.createObject("#data",null,null);
                ret.setText("Dato",0,1,80,1);
            }
            else if(type=='dataInput') {
                ret= Modeler.createObject("#dataInput",null,null);
                ret.setText("Dato de entrada",0,1,80,1);
            }
            else if(type=='dataOutput') {
                ret= Modeler.createObject("#dataOutput",null,null);
                ret.setText("Dato de salida",0,1,80,1);
            }
            else if(type=='dataStore') {
                ret= Modeler.createObject("#dataStore",null,null);
                ret.setText("Almacén de datos",0,1,80,1);
            }
            else if(type=='userTask') {
                ret = Modeler.createTask(null,null);
                ret.addIcon("#userMarker",-1,-1,13,8);
                ret.setText("Tarea de Usuario",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='serviceTask') {
                ret = Modeler.createTask(null,null);
                ret.addIcon("#serviceMarker",-1,-1,13,8);
                ret.setText("Tarea de Servicio",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='scriptTask') {
                ret = Modeler.createTask(null,null);
                ret.addIcon("#scriptMarker",-1,-1,7,13);
                ret.setText("Tarea de Script",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='ruleTask') {
                ret = Modeler.createTask(null,null);
                ret.addIcon("#taskRuleMarker",-1,-1,12,12);
                ret.setText("Tarea de regla de negocio",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='manualTask') {
                ret = Modeler.createTask(null,null);
                ret.addIcon("#manualMarker",-1,-1,9,6);
                ret.setText("Tarea Manual",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='sendTask') {
                ret = Modeler.createTask(null,null);
                ret.addIcon("#taskMessageThrowMarker",-1,-1,13,10);
                ret.setText("Tarea de envío de mensaje",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='receiveTask') {
                ret = Modeler.createTask(null,null);
                ret.addIcon("#taskMessageCatchMarker",-1,-1,13,10);
                ret.setText("Tarea de recepción de mensaje",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='abstractTask') {
                ret = Modeler.createTask(null,null);
                ret.setText("Tarea abstracta",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callTask') {
                ret = Modeler.createCallTask(null,null);
                ret.setText("Tarea reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callmanualTask') {
                ret = Modeler.createCallTask(null,null);
                ret.addIcon("#manualMarker",-1,-1,9,6);
                ret.setText("Tarea manual reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callruleTask') {
                ret = Modeler.createCallTask(null,null);
                ret.addIcon("#taskRuleMarker",-1,-1,12,12);
                ret.setText("Tarea de regla de negocio reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='callscriptTask') {
                ret = Modeler.createCallTask(null,null);
                ret.addIcon("#scriptMarker",-1,-1,7,13);
                ret.setText("Tarea de script reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if(type=='calluserTask') {
                ret = Modeler.createCallTask(null,null);
                ret.addIcon("#userMarker",-1,-1,13,8);
                ret.setText("Tarea de usuario reusada",0,0,0,1);
                ret.resize(100,60);
            }
            else if (type=="pool") {
                ret = Modeler.createSwimLane(null, null);
                ret.setText("Pool",0,0,0,1);
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
        
        obj = Modeler.createObject("#pool",null,null);
        obj.moveFirst=function(){};
        //obj.mouseup=function(evt){alert("up")};
        obj.move(350,580);
        
        obj2 = Modeler.createTask(null,null);
        obj2.addIcon("#manualMarker",-1,-1,9,6);
        obj2.setText("Tarea Contenida");
        obj2.resize(100,60);
        obj2.move(400,600);        
        obj2.setParent(obj);
        
//        obj2 = Modeler.mapObject("conditionalFlow");
//        obj2.setStartPoint(10,10);
//        obj2.addPoint(10,20);
//        obj2.addPoint(30,20);
//        //obj2.addPoint(30,40);
//        obj2.setEndPoint(100,100);
//        
//        obj2 = Modeler.mapObject("messageFlow");
//        obj2.setStartPoint(150,150);
//        obj2.addPoint(150,350);
//        obj2.addPoint(260,350);
//        obj2.addPoint(280,350);
//        obj2.setEndPoint(500,500);
//        
//        obj2 = Modeler.mapObject("sequenceFlow");
//        obj2.setStartPoint(300,300);
//        obj2.setEndPoint(300,100);
//        
//        obj2 = Modeler.mapObject("defaultFlow");
//        obj2.setStartPoint(200,200);
//        obj2.setEndPoint(250,190);
//        
//        obj2 = Modeler.mapObject("associationFlow");
//        obj2.setStartPoint(350,200);
//        obj2.addPoint(350,250);
//        obj2.addPoint(400,200);
//        obj2.addPoint(410,200);
//        obj2.setEndPoint(500,190);
//        
//        obj2 = Modeler.mapObject("directionalassociationFlow");
//        obj2.setStartPoint(350,10);
//        obj2.addPoint(350,150);
//        obj2.addPoint(400,100);
//        obj2.addPoint(430,100);
        //obj2.setEndPoint(400,190);
    }

