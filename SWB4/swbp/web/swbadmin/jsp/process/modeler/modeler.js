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
                obj.setX(ToolKit.getEventX(evt));
                obj.setY(ToolKit.getEventY(evt));
                //desc(evt,true);
                Modeler.creationId=null;
                return false;
            }
            return true;
        },
       
        mapObject:function(type)
        {
            if(type=='normalStartEvent')return Modeler.createObject("#startEvent",null,null);
            else if(type=='messageStartEvent')return Modeler.createObject("#messageStartEvent",null,null);
            else if(type=='timerStartEvent')return Modeler.createObject("#timerStartEvent",null,null);
            else if(type=='ruleStartEvent')return Modeler.createObject("#ruleStartEvent",null,null);
            else if(type=='signalStartEvent')return Modeler.createObject("#signalStartEvent",null,null);
            else if(type=='multiStartEvent')return Modeler.createObject("#multipleStartEvent",null,null);
            else if(type=='parallelStartEvent')return Modeler.createObject("#parallelStartEvent",null,null);
            else if(type=='scalaStartEvent')return Modeler.createObject("#scalationStartEvent",null,null);
            else if(type=='errorStartEvent')return Modeler.createObject("#errorStartEvent",null,null);
            else if(type=='compensaStartEvent')return Modeler.createObject("#compensationStartEvent",null,null);
            else if(type=='messageInterCatchEvent')return Modeler.createObject("#messageIntermediateCatchEvent",null,null);
            else if(type=='messageInterThrowEvent')return Modeler.createObject("#messageIntermediateThrowEvent",null,null);
            else if(type=='timerInterEvent')return Modeler.createObject("#timerIntermediateEvent",null,null);
            else if(type=='errorInterEvent')return Modeler.createObject("#errorIntermediateEvent",null,null);
            else if(type=='cancelInterEvent')return Modeler.createObject("#cancelIntermediateEvent",null,null);
            else if(type=='compensaInterCatchEvent')return Modeler.createObject("#compensationIntermediateCatchEvent",null,null);
            else if(type=='compensaInterThrowEvent')return Modeler.createObject("#compensationIntermediateThrowEvent",null,null);
            else if(type=='ruleInterEvent')return Modeler.createObject("#xxxxxxxx",null,null);
            else if(type=='linkInterCatchEvent')return Modeler.createObject("#linkIntermediateCatchEvent",null,null);
            else if(type=='linkInterThrowEvent')return Modeler.createObject("#linkIntermediateThrowEvent",null,null);
            else if(type=='signalInterCatchEvent')return Modeler.createObject("#signalIntermediateCatchEvent",null,null);
            else if(type=='signalInterThrowEvent')return Modeler.createObject("#signalIntermediateThrowEvent",null,null);
            else if(type=='multipleInterCatchEvent')return Modeler.createObject("#multipleIntermediateCatchEvent",null,null);
            else if(type=='multipleInterThrowEvent')return Modeler.createObject("#multipleIntermediateThrowEvent",null,null);
            else if(type=='scalaInterCatchEvent')return Modeler.createObject("#scalationIntermediateCatchEvent",null,null);
            else if(type=='scalaInterThrowEvent')return Modeler.createObject("#scalationIntermediateThrowEvent",null,null);
            else if(type=='parallelInterEvent')return Modeler.createObject("#parallelIntermediateEvent",null,null);
            else if(type=='normalEndEvent')return Modeler.createObject("#endEvent",null,null);
            else if(type=='messageEndEvent')return Modeler.createObject("#messageEndEvent",null,null);
            else if(type=='errorEndEvent')return Modeler.createObject("#errorEndEvent",null,null);
            else if(type=='cancelEndEvent')return Modeler.createObject("#cancelationEndEvent",null,null);
            else if(type=='compensaEndEvent')return Modeler.createObject("#compensationEndEvent",null,null);
            else if(type=='signalEndEvent')return Modeler.createObject("#signalEndEvent",null,null);
            else if(type=='multiEndEvent')return Modeler.createObject("#multipleEndEvent",null,null);
            else if(type=='escalaEndEvent')return Modeler.createObject("#scalationEndEvent",null,null);
            else if(type=='terminalEndEvent')return Modeler.createObject("#terminationEndEvent",null,null);
            else if(type=='exclusiveDataGateway')return Modeler.createObject("#exclusiveDataGateway",null,null);
            else if(type=='inclusiveDataGateway')return Modeler.createObject("#inclusiveDataGateway",null,null);
            else if(type=='exclusiveStartEventGateway')return Modeler.createObject("#exclusiveStartGateway",null,null);
            else if(type=='exclusiveEventGateway')return Modeler.createObject("#eventGateway",null,null);
            else if(type=='parallelGateway')return Modeler.createObject("#parallelGateway",null,null);
            else if(type=='parallelStartGateway')return Modeler.createObject("#parallelStartGateway",null,null);
            else if(type=='complexGateway')return Modeler.createObject("#complexGateway",null,null);
            else if(type=='annotation')return Modeler.createObject("#xxxxxxx",null,null);
            else if(type=='group')return Modeler.createObject("#xxxxxxx",null,null);
            else if(type=='dataObject')return Modeler.createObject("#data",null,null);
            else if(type=='dataInput')return Modeler.createObject("#dataInput",null,null);
            else if(type=='dataOutput')return Modeler.createObject("#dataOutput",null,null);
            else if(type=='dataStore')return Modeler.createObject("#dataStore",null,null);

            
        }
        
    }    
    
    function draw()
    {
        var obj;

        //obj = ToolKit.createUseObject("#task");

        //obj2 = ToolKit.createUseObject("#endEvent",null,obj);
        //obj2.move(100,200);                

        for(i=0;i<100;i++)
        {
            //obj3 = ToolKit.createStartEvent(null, obj2);
            //obj3.move(i,100);
        }

        obj = Modeler.createObject("#barra");                

        //obj = Modeler.createObject("#test");                

        obj2 = Modeler.createObject("#startEvent",null,null);
        obj2.move(100,20);
        obj2 = Modeler.createObject("#signalStartEvent",null,null);
        obj2.move(130,20);
        obj2 = Modeler.createObject("#timerStartEvent",null,null);
        obj2.move(160,20);
        obj2 = Modeler.createObject("#ruleStartEvent",null,null);
        obj2.move(190,20);
        obj2 = Modeler.createObject("#multipleStartEvent",null,null);
        obj2.move(220,20);
        obj2 = Modeler.createObject("#parallelStartEvent",null,null);
        obj2.move(250,20);
        obj2 = Modeler.createObject("#errorStartEvent",null,null);
        obj2.move(280,20);
        obj2 = Modeler.createObject("#compensationStartEvent",null,null);
        obj2.move(310,20);
        obj2 = Modeler.createObject("#scalationStartEvent",null,null);
        obj2.move(340,20);
        obj2 = Modeler.createObject("#messageStartEvent",null,null);
        obj2.move(370,20);

        obj2 = Modeler.createObject("#intermediateEvent",null,null);
        obj2.move(100,70);
        obj2 = Modeler.createObject("#messageIntermediateThrowEvent",null,null);
        obj2.move(130,70);
        obj2 = Modeler.createObject("#timerIntermediateEvent",null,null);
        obj2.move(160,70);
        obj2 = Modeler.createObject("#errorIntermediateEvent",null,null);
        obj2.move(190,70);
        obj2 = Modeler.createObject("#cancelIntermediateEvent",null,null);
        obj2.move(220,70);
        obj2 = Modeler.createObject("#compensationIntermediateCatchEvent",null,null);
        obj2.move(250,70);
        obj2 = Modeler.createObject("#compensationIntermediateThrowEvent",null,null);
        obj2.move(280,70);
        obj2 = Modeler.createObject("#linkIntermediateCatchEvent",null,null);
        obj2.move(310,70);
        obj2 = Modeler.createObject("#linkIntermediateThrowEvent",null,null);
        obj2.move(340,70);
        obj2 = Modeler.createObject("#signalIntermediateCatchEvent",null,null);
        obj2.move(370,70);
        obj2 = Modeler.createObject("#signalIntermediateThrowEvent",null,null);
        obj2.move(400,70);
        obj2 = Modeler.createObject("#multipleIntermediateCatchEvent",null,null);
        obj2.move(430,70);
        obj2 = Modeler.createObject("#multipleIntermediateThrowEvent",null,null);
        obj2.move(460,70);
        obj2 = Modeler.createObject("#scalationIntermediateCatchEvent",null,null);
        obj2.move(490,70);
        obj2 = Modeler.createObject("#scalationIntermediateThrowEvent",null,null);
        obj2.move(520,70);
        obj2 = Modeler.createObject("#parallelIntermediateEvent",null,null);
        obj2.move(550,70);
        obj2 = Modeler.createObject("#messageIntermediateCatchEvent",null,null);
        obj2.move(580,70);


        obj2 = Modeler.createObject("#endEvent",null,null);
        obj2.move(100,120);
        obj2 = Modeler.createObject("#messageEndEvent",null,null);
        obj2.move(130,120);
        obj2 = Modeler.createObject("#errorEndEvent",null,null);
        obj2.move(160,120);
        obj2 = Modeler.createObject("#cancelationEndEvent",null,null);
        obj2.move(190,120);
        obj2 = Modeler.createObject("#compensationEndEvent",null,null);
        obj2.move(220,120);
        obj2 = Modeler.createObject("#signalEndEvent",null,null);
        obj2.move(250,120);
        obj2 = Modeler.createObject("#multipleEndEvent",null,null);
        obj2.move(280,120);
        obj2 = Modeler.createObject("#scalationEndEvent",null,null);
        obj2.move(310,120);
        obj2 = Modeler.createObject("#terminationEndEvent",null,null);
        obj2.move(340,120);

        obj2 = Modeler.createObject("#exclusiveDataGateway",null,null);
        obj2.move(100,180);
        obj2 = Modeler.createObject("#inclusiveDataGateway",null,null);
        obj2.move(160,180);
        obj2 = Modeler.createObject("#exclusiveStartGateway",null,null);
        obj2.move(220,180);
        obj2 = Modeler.createObject("#eventGateway",null,null);
        obj2.move(280,180);
        obj2 = Modeler.createObject("#parallelGateway",null,null);
        obj2.move(340,180);
        obj2 = Modeler.createObject("#parallelStartGateway",null,null);
        obj2.move(400,180);
        obj2 = Modeler.createObject("#complexGateway",null,null);
        obj2.move(460,180);

        obj2 = Modeler.createObject("#data",null,null);
        obj2.move(100,240);
        obj2 = Modeler.createObject("#dataInput",null,null);
        obj2.move(150,240);
        obj2 = Modeler.createObject("#dataOutput",null,null);
        obj2.move(200,240);
        obj2 = Modeler.createObject("#dataStore",null,null);
        obj2.move(250,240);


        obj2 = Modeler.createObject("#userTask",null,null);
        obj2.move(100,320);
        obj2 = Modeler.createObject("#serviceTask",null,null);
        obj2.move(200,320);
        obj2 = Modeler.createObject("#scriptTask",null,null);
        obj2.move(300,320);
        obj2 = Modeler.createObject("#ruleTask",null,null);
        obj2.move(400,320);
        obj2 = Modeler.createObject("#sendTask",null,null);
        obj2.move(500,320);
        obj2 = Modeler.createObject("#receiveTask",null,null);
        obj2.move(600,320);
        obj2 = Modeler.createObject("#manualTask",null,null);
        obj2.move(700,320);

        obj2 = Modeler.createObject("#pool",null,null);
        obj2.move(350,470);

        obj2 = Modeler.createTask(null,null);
        obj2.addIcon("#userMarker",-1,-1,13,8);
        obj2.setText("Tarea de Usuario");
        obj2.move(100,100);
        obj2.resize(100,60);

        //Modeler.createStartEvent();

        //Modeler.createMessageStartEvent();

    }

