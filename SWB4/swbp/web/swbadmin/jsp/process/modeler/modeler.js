/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
    function desc(obj, content)
    {
        var ret="";
        for (property in obj) {
            if(content && content==true)
            {
                ret+="-->"+property+"="+obj[property]+"\r\n";
            }else
            {
                ret+="-->"+property+"\r\n";
            }
        }
        console.log(ret);
        //alert(ret);
    }

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
        svgNS:"http://www.w3.org/2000/svg",
        xlink:"http://www.w3.org/1999/xlink",
        contents:[],
        selected:[],
        resizeBox:[],
        selectBox:null,
        cmdkey:false,
        ctrlkey:false,


        getWidth:function()
        {
            return this.svg.width.baseVal.value;
        },

        getHeight:function()
        {
            return this.svg.height.baseVal.value;
        },

        setWidth:function(width)
        {
            this.svg.width.baseVal.value=width;
        },

        setHeight:function(height)
        {
            this.svg.height.baseVal.value=height;
        },                

        init:function()
        {
            var _this=this;
            _this.svg=document.getElementById("modeler");
            //desc(_this.svg,true);

            _this.svg.onmousemove=function(evt)
            {
                _this.svg.mouseX=evt.clientX;
                _this.svg.mouseY=evt.clientY;

                if(_this.svg.resizeObject!=null)
                {
                    x=evt.clientX-_this.svg.dragOffsetX;
                    y=evt.clientY-_this.svg.dragOffsetY;
                    tx=x-_this.svg.resizeObject.parent.getX();
                    ty=y-_this.svg.resizeObject.parent.getY();
                    w=Math.abs(_this.svg.resizeObject.startW+tx*2*_this.svg.resizeObject.ix);
                    h=Math.abs(_this.svg.resizeObject.startH+ty*2*_this.svg.resizeObject.iy);                                
                    _this.svg.resizeObject.parent.resize(w,h);
                    _this.updateResizeBox();

                }else if(_this.svg.dragObject!=null)  //dragObjects
                {
                    _this.selected.unselect=false; //si hace drag no deselecciona
                    x=evt.clientX-_this.svg.dragOffsetX;
                    y=evt.clientY-_this.svg.dragOffsetY;

                    if(_this.cmdkey==true) //Drag one
                    {
                        _this.svg.dragObject.move(x,y);                                    
                    }else //drag selecteds
                    {
                        tx=x-_this.svg.dragObject.getX();
                        ty=y-_this.svg.dragObject.getY();

                        for (var i = _this.selected.length; i--;) 
                        {                                
                            _this.selected[i].traslate(tx, ty);
                        }
                        _this.updateResizeBox();
                    }
                }else if(_this.selectBox!=null) //SelectBox
                {
                    var w=evt.clientX-_this.svg.dragOffsetX;
                    var h=evt.clientY-_this.svg.dragOffsetY;
                    var x=_this.svg.dragOffsetX;
                    var y=_this.svg.dragOffsetY;

                    if(w<0)
                    {
                        x=_this.svg.dragOffsetX+w;
                        w=-w;                                    
                    }
                    if(h<0)
                    {
                        y=_this.svg.dragOffsetY+h;
                        h=-h;                                        
                    }

                    _this.selectBox.setAttributeNS(null,"x",x);
                    _this.selectBox.setAttributeNS(null,"width",w);
                    _this.selectBox.setAttributeNS(null,"y",y);
                    _this.selectBox.setAttributeNS(null,"height",h);

                    var nodes=_this.svg.childNodes;
                    for(i=0;i<nodes.length;i++)
                    {
                        var obj=nodes.item(i);
                        if(obj.contents && obj.canSelect==true)    //Es un objeto grafico
                        {
                            var ox=obj.getX();
                            var oy=obj.getY();
                            if(ox>=x && ox<=x+w && oy>=y && oy<=y+h)
                            {
                                if(obj.selected!=true)
                                {                                                
                                    _this.selectObj(obj);
                                }
                            }else
                            {
                                if(!_this.cmdkey)
                                {
                                    if(obj.selected==true)
                                    {
                                        _this.unSelectObj(obj);
                                    }
                                }
                            }
                        }
                    }
                }

            };

            _this.svg.onmousedown=function(evt)
            {
                //SelectBox
                if(_this.svg.dragObject==null)
                {
                    if(!_this.cmdkey)_this.unSelectAll();
                    if(_this.selectBox==null)
                    {
                        _this.selectBox=document.createElementNS(_this.svgNS,"rect");
                        _this.selectBox.setAttributeNS(null,"class","selectBox");
                        _this.selectBox.setAttributeNS(null,"x",evt.clientX);
                        _this.selectBox.setAttributeNS(null,"y",evt.clientY);
                        _this.selectBox.setAttributeNS(null,"width",0);
                        _this.selectBox.setAttributeNS(null,"height",0);
                        _this.svg.appendChild(_this.selectBox);
                        _this.svg.dragOffsetX=evt.clientX;
                        _this.svg.dragOffsetY=evt.clientY;                                    
                    }
                }
            };                         

            _this.svg.onmouseup=function(evt)
            {
                _this.svg.dragObject=null;
                _this.svg.resizeObject=null;

                //SelectBox
                if(_this.selectBox!=null)
                {
                    _this.svg.removeChild(_this.selectBox);
                    _this.selectBox=null;
                }
            };                        

            //Add Key Events
            if (window.addEventListener)
            {
                window.addEventListener('keydown', _this.keydown, true);
                window.addEventListener('keyup', _this.keyup, true);
            }
            else if (window.attachEvent)
            {
                window.attachEvent("onkeydown", _this.keydown);
                window.attachEvent("onkeyup", _this.keyup);
            }
            else
            {
                window.onkeydown= _this.keydown;  
                window.onkeyup= _this.keyup;  
            }

            _this.setWidth(1920);
            _this.setHeight(1080);
        },

        updateResizeBox:function()
        {
            var _this=Modeler;
            for(i=0;i<_this.resizeBox.length;i++)
            {
                _this.resizeBox[i].update();
            }
        },

        createResizeBox:function(obj, ix, iy, cursor)
        {
            var _this=Modeler;
            var b=document.createElementNS(_this.svgNS,"use");
            b.setAttributeNS(_this.xlink,"href","#resizeBox");
            b.setAttributeNS(null,"style","cursor:"+cursor);
            b.init=function(_ix,_iy)
            {
                b.ix=_ix;
                b.iy=_iy;
                b.update();
            };
            b.setParent=function(_parent)
            {
                b.parent=_parent;
            };
            b.update=function()
            {
                b.setAttributeNS(null,"x",b.parent.getX()+(b.parent.getWidth()/2)*b.ix);
                b.setAttributeNS(null,"y",b.parent.getY()+(b.parent.getHeight()/2)*b.iy);
                //desc(b.sid+" "+Number(b.getAttributeNS(null,"x"))+" "+Number(b.getAttributeNS(null,"y")));
            };
            b.onmousedown=function(evt)
            {
                b.startW=b.parent.getWidth();
                b.startH=b.parent.getHeight();
                _this.svg.dragOffsetX=_this.svg.mouseX-obj.getAttributeNS(null,"x");
                _this.svg.dragOffsetY=_this.svg.mouseY-obj.getAttributeNS(null,"y");
                _this.svg.resizeObject=b;

                //Bug de chrome
                _this.stopPropagation(evt);
            };

            b.setParent(obj);
            b.init(ix,iy);
            _this.svg.appendChild(b);
            _this.resizeBox.push(b);                        
        },

        showResizeBoxes:function()
        {
            var _this=Modeler;
            if(_this.selected.length==1 && _this.selected[0].resizeable)
            {
                var obj=_this.selected[0];
                _this.createResizeBox(obj,-1,-1,"nw-resize");
                _this.createResizeBox(obj,0,-1,"n-resize");
                _this.createResizeBox(obj,1,-1,"ne-resize");
                _this.createResizeBox(obj,-1,0,"w-resize");
                _this.createResizeBox(obj,1,0,"e-resize");
                _this.createResizeBox(obj,-1,1,"sw-resize");
                _this.createResizeBox(obj,0,1,"s-resize");
                _this.createResizeBox(obj,1,1,"se-resize");
            }else
            {
                while((o=_this.resizeBox.pop())!=null)
                {
                    _this.svg.removeChild(o);
                } 

            }
        },

        selectObj:function(obj)
        {
            var _this=Modeler;
            _this.selected.push(obj);
            obj.selected=true;   
            obj.setOverClass();
            _this.showResizeBoxes();
        },

        unSelectObj:function(obj)
        {
            var _this=Modeler;
            var i=_this.selected.indexOf(obj);
            _this.selected.splice(i, 1);
            obj.selected=false;
            obj.setBaseClass();
            _this.showResizeBoxes();
        },

        unSelectAll:function()
        {
            var _this=Modeler;
            while((o=_this.selected.pop())!=null)
            {
                o.setBaseClass();
                o.selected=false;
            }        
            _this.showResizeBoxes();
        },

        keydown:function(evt)
        {
            var _this=Modeler;
            //desc(evt,true);
            if(evt.keyCode==8 && evt.which==8)
            //if(evt.keyCode==32 && evt.which==32)
            {
                for (var i = _this.selected.length; i--;) 
                {
                    _this.selected[i].remove();
                }                             
                unSelectAll();
                _this.stopPropagation(evt);
            }else if((evt.keyCode==91 && evt.which==91) || (evt.keyCode==224 && evt.which==224))
            {
                _this.cmdkey=true;
                _this.stopPropagation(evt);
            }else if(evt.keyCode==17 && evt.which==17)
            {
                _this.ctrlkey=true;
                _this.stopPropagation(evt);
            }
        },

        keyup:function(evt)
        {
            //alert("hola");
            var _this=Modeler;
            if((evt.keyCode==91 && evt.which==91) || (evt.keyCode==224 && evt.which==224))
            {
                _this.cmdkey=false;
                _this.stopPropagation(evt);
            }else if(evt.keyCode==17 && evt.which==17)
            {
                _this.ctrlkey=false;
                _this.stopPropagation(evt);
            }
        },                                        

        stopPropagation:function(evt)
        {
            if (evt.preventDefault) evt.preventDefault();
            if (evt.stopPropagation) evt.stopPropagation();
        },

        createResizeObject:function(id, parent)
        {
            var _this=Modeler;
            //console.log(_this);
            var constructor=function()
            {
                var obj = document.createElementNS(_this.svgNS,"rect"); //to create a circle, for rectangle use rectangle
                obj.resizeable=true;
                //obj.setAttributeNS(_this.xlink,"href",type);
                return obj;
            };
            return _this.createObject(constructor,id,parent);
        },                    

        createUseObject:function(type, id, parent)
        {
            var _this=Modeler;
            //console.log(_this);
            var constructor=function()
            {
                var obj = document.createElementNS(_this.svgNS,"use"); //to create a circle, for rectangle use rectangle
                obj.setAttributeNS(_this.xlink,"href",type);
                return obj;
            };
            return _this.createObject(constructor,id,parent);
        },

        createUseBaseObject:function(type, id, parent)
        {
            var _this=Modeler;
            //console.log(_this);
            var constructor=function()
            {
                var obj = document.createElementNS(_this.svgNS,"use"); //to create a circle, for rectangle use rectangle
                obj.setAttributeNS(_this.xlink,"href",type);
                return obj;
            };
            return _this.createBaseObject(constructor,id,parent);
        },                    

        createBaseObject:function(constructor, id, parent)
        {
            var _this=Modeler;
            var obj=constructor();

            obj.contents=[];                        
            obj.icons=[];      
            obj.canSelect=true;

            if(id && id!=null)obj.setAttributeNS(null,"id",id);                    

            obj.setOverClass=function()
            {
                obj.setParamClass("oclass");
            };

            obj.setBaseClass=function()
            {
                obj.setParamClass("bclass");
            };

            obj.setParamClass=function(param)
            {
                var _this=Modeler;
                var l=obj.getAttributeNS(_this.xlink,"href");
                if(l && l!=null)
                {
                    var o=document.getElementById(l.substring(1));
                    if(o!=null)
                    {
                        var s=o.getAttributeNS(null, param);
                        if(s && s!=null)obj.setAttributeNS(null,"class",s);
                    }
                }else
                {
                    var s=obj.getAttributeNS(null, param);
                    if(s!=null)obj.setAttributeNS(null,"class",s);                                
                }
            };                        

            obj.setBaseClass();                        

            obj.getX=function()
            {
                return Number(obj.getAttributeNS(null,"x"));
            };

            obj.getY=function()
            {
                return Number(obj.getAttributeNS(null,"y"));
            };

            obj.setX=function(x)
            {
                obj.setAttributeNS(null,"x",x);
            };

            obj.setY=function(y)
            {
                obj.setAttributeNS(null,"y",y);
            };   

            obj.getWidth=function()
            {
                return Number(obj.getAttributeNS(null,"width"));
            };

            obj.getHeight=function()
            {
                return Number(obj.getAttributeNS(null,"height"));
            };

            obj.setWidth=function(w)
            {
                obj.setAttributeNS(null,"width",w);
            };

            obj.setHeight=function(h)
            {
                obj.setAttributeNS(null,"height",h);
            };                           

            obj.traslate=function(dx, dy)
            {
                var ox=obj.getX();
                var oy=obj.getY();
                obj.move(ox+dx, oy+dy);
            }

            obj.resize=function(w,h)
            {
                obj.setAttributeNS(null,"transform","translate("+(-w/2)+","+(-h/2)+")");
                obj.setWidth(w);
                obj.setHeight(h);        

                //Move Icons
                for (var i = obj.icons.length; i--;) 
                {
                    var icon=obj.icons[i];
                    var pw=w/2*icon.posx;
                    var ph=h/2*icon.posy;
                    icon.obj.move(obj.getX()+pw+icon.offx,obj.getY()+ph+icon.offy);
                }

                //Cambiar tamaño del texto
                if(obj.text && obj.text!=null)obj.text.update();                            
            };                        

            obj.move=function(x,y)
            {
                var offx=x-obj.getX();
                var offy=y-obj.getY();
                obj.setX(x);
                obj.setY(y);

                //Move Childs
                for (var i = obj.contents.length; i--;) 
                {
                    if(obj.contents[i].selected!=true)
                    {
                        obj.contents[i].traslate(offx,offy);
                    }
                }  
                //Move Icons
                for (var i = obj.icons.length; i--;) 
                {
                    obj.icons[i].obj.traslate(offx,offy);
                }

                //Move Text
                if(obj.text && obj.text!=null)
                {
                    obj.text.traslate(offx,offy);
                    for (var i = obj.text.childNodes.length; i--;)
                    {
                        obj.text.childNodes[i].setAttributeNS(null,"x",obj.text.getX());
                    }
                }

                if(x+50>_this.getWidth())
                {
                    _this.setWidth(x+50);
                }
                if(y+50>_this.getHeight())
                {
                    _this.setHeight(y+50);
                }                           
            };    

            obj.remove = function(all) {
                if(!all)
                {
                    while ((ax = obj.parent.contents.indexOf(obj)) !== -1) {
                        obj.parent.contents.splice(ax, 1);
                    }
                }
                for (var i = obj.contents.length; i--;)
                {
                    obj.contents[i].remove(true);
                }
                try
                {
                    _this.svg.removeChild(obj);
                }catch(noe){}
                return this;
            };

            if(parent && parent!=null)
            {
                parent.contents.push(obj);
                obj.parent=parent;
            }else
            {
                _this.contents.push(obj);
                obj.parent=_this;
            }   

            //pos validas del 1 al 9, 1=esquina superior izquierda, 5=centro, 9=esquina inferior derecha
            obj.addIcon=function(type,posx,posy,offx,offy)
            {
                var iobj=_this.createUseBaseObject(type,null,null);
                iobj.canSelect=false;
                iobj.onmousedown=function(evt)
                {
                    obj.onmousedown(evt);
                };
                var icon={obj:iobj,posx:posx,posy:posy,offx:offx,offy:offy};
                obj.icons.push(icon);

            };

            //pos validas del 1 al 9, 1=esquina superior izquierda, 5=centro, 9=esquina inferior derecha
            obj.setText=function(text)
            {
                if(obj.text)obj.text.remove();
                obj.text=_this.createText(text,obj);
                obj.text.update();
            };                        

            _this.svg.appendChild(obj);
            return obj;
        },                    

        createObject:function(constructor, id, parent)
        {
            var _this=Modeler;
            var obj=_this.createBaseObject(constructor, id, parent);

            obj.onmousedown=function(evt)
            {
                obj.startX=evt.clientX;
                obj.startY=evt.clientY;
                _this.svg.dragOffsetX=_this.svg.mouseX-obj.getAttributeNS(null,"x");
                _this.svg.dragOffsetY=_this.svg.mouseY-obj.getAttributeNS(null,"y");
                _this.svg.dragObject=obj;
                obj.mousedown(evt);
                obj.select(true);
            };

            //obj.onmousemove=function(evt)
            //{
            //    console.log("id:"+obj.getAttributeNS(null,"id"));                            
            //};

            obj.select=function(down)
            {
                //desc(obj.getBBox(),true);
                var _this=Modeler;
                if(down==true)
                {
                    var i=_this.selected.indexOf(obj);
                    if(i==-1)
                    {
                        if(_this.cmdkey==false) //deselect all
                        {
                            _this.unSelectAll();
                        }
                        //select one
                        _this.selectObj(obj);
                    }else 
                    {
                        //can unselect
                        _this.selected.unselect=true;
                    }
                }else
                {
                    if(_this.cmdkey==true && _this.selected.unselect==true) //unselect one
                    {
                        _this.unSelectObj(obj);
                    }                                 
                    _this.selected.unselect=false;
                }
            };

            obj.mousedown=function(evt)
            {
                //Sobreescribir
            };

            obj.onmouseup=function(evt)
            {
                _this=Modeler;
                obj.mouseup(evt);
                obj.select(false);

                //if(_this.selected.length==1 && obj.startX==evt.clientX && obj.startY==evt.clientY)
                //{
                //    //evento click de objeto
                //}
            };

            obj.mouseup=function(evt)
            {
                //Sobreescribir
            };     

            obj.onclick=function(evt)
            {
                return this.mouseclick(evt);
            }

            obj.mouseclick=function(evt)
            {
                //Sobre escribir
                //previene de abrir nuevas ventanas al hacer click sobre elementos
                return false;
            }
            return obj;
        },

        createText:function(text,parent)
        {
            var _this=Modeler;
            var constructor=function()
            {
                var tx = document.createElementNS(_this.svgNS,"text"); //to create a circle, for rectangle use rectangle
                //tx.appendChild(document.createTextNode(text));
                tx.setAttributeNS(null,"text-anchor","middle");
                tx.setAttributeNS(null,"font-size","11");
                tx.setAttributeNS(null,"font-family","Verdana, Geneva, sans-serif");
                return tx;
            };
            obj=_this.createBaseObject(constructor,null,null);                        
            obj.value=text;                                     //Valor de la caja de texto
            obj.canSelect=false;
            obj.onmousedown=function(evt)
            {
                parent.onmousedown(evt);
            };  

            parent.ondblclick=function(evt)
            {
                obj.ondblclick(evt);
            };

            obj.ondblclick=function(evt)
            {
                var txt=prompt("Texto:",obj.value);  
                if(txt!=null)
                {
                    obj.value=txt;
                    obj.update();
                }
            };

            obj.update=function()
            {
                var text_element = obj;
                var dy=10;
                var words = text_element.value.split(' ');
                var start_x = text_element.getX();

                //Eliminar childs
                var child=null;
                while((child=text_element.firstChild)!=null)
                {
                    text_element.removeChild(child);
                }

                var tspan_element = document.createElementNS(_this.svgNS, "tspan");	// Create first tspan element
                tspan_element.setAttributeNS(null, "x", start_x);
                var text_node = document.createTextNode(words[0]);			// Create text in tspan element

                tspan_element.appendChild(text_node);							// Add tspan element to DOM
                text_element.appendChild(tspan_element);						// Add text to tspan element
                var lin=1;
                for(var i=1; i<words.length; i++)
                {
                    var len = tspan_element.firstChild.data.length;				// Find number of letters in string
                    tspan_element.firstChild.data += " " + words[i];			// Add next word

                    if (tspan_element.getComputedTextLength() > parent.getWidth()-10)
                    {
                        tspan_element.firstChild.data = tspan_element.firstChild.data.slice(0, len);	// Remove added word

                        var tspan_element = document.createElementNS(_this.svgNS, "tspan");		// Create new tspan element
                        tspan_element.setAttributeNS(null, "x", start_x);
                        tspan_element.setAttributeNS(null, "dy", dy);
                        text_node = document.createTextNode(words[i]);
                        tspan_element.appendChild(text_node);
                        text_element.appendChild(tspan_element);
                        lin++;
                    }
                }
                text_element.setY(parent.getY()+dy-lin*dy/2);
            };

            return obj;
        },                     

        createTask:function(id, parent)
        {
            //Revisar ID
            var obj=this.createResizeObject(id,parent);
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
            var obj=this.createUseObject("#startEvent",id,parent);
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
        }

    };

    function draw()
    {
        var obj;

        //obj = Modeler.createUseObject("#task");

        //obj2 = Modeler.createUseObject("#endEvent",null,obj);
        //obj2.move(100,200);                

        for(i=0;i<100;i++)
        {
            //obj3 = Modeler.createStartEvent(null, obj2);
            //obj3.move(i,100);
        }

        obj = Modeler.createUseObject("#barra");                

        //obj = Modeler.createUseObject("#test");                

        obj2 = Modeler.createUseObject("#startEvent",null,null);
        obj2.move(100,20);
        obj2 = Modeler.createUseObject("#signalStartEvent",null,null);
        obj2.move(130,20);
        obj2 = Modeler.createUseObject("#timerStartEvent",null,null);
        obj2.move(160,20);
        obj2 = Modeler.createUseObject("#ruleStartEvent",null,null);
        obj2.move(190,20);
        obj2 = Modeler.createUseObject("#multipleStartEvent",null,null);
        obj2.move(220,20);
        obj2 = Modeler.createUseObject("#parallelStartEvent",null,null);
        obj2.move(250,20);
        obj2 = Modeler.createUseObject("#errorStartEvent",null,null);
        obj2.move(280,20);
        obj2 = Modeler.createUseObject("#compensationStartEvent",null,null);
        obj2.move(310,20);
        obj2 = Modeler.createUseObject("#scalationStartEvent",null,null);
        obj2.move(340,20);
        obj2 = Modeler.createUseObject("#messageStartEvent",null,null);
        obj2.move(370,20);

        obj2 = Modeler.createUseObject("#intermediateEvent",null,null);
        obj2.move(100,70);
        obj2 = Modeler.createUseObject("#messageIntermediateThrowEvent",null,null);
        obj2.move(130,70);
        obj2 = Modeler.createUseObject("#timerIntermediateEvent",null,null);
        obj2.move(160,70);
        obj2 = Modeler.createUseObject("#errorIntermediateEvent",null,null);
        obj2.move(190,70);
        obj2 = Modeler.createUseObject("#cancelIntermediateEvent",null,null);
        obj2.move(220,70);
        obj2 = Modeler.createUseObject("#compensationIntermediateCatchEvent",null,null);
        obj2.move(250,70);
        obj2 = Modeler.createUseObject("#compensationIntermediateThrowEvent",null,null);
        obj2.move(280,70);
        obj2 = Modeler.createUseObject("#linkIntermediateCatchEvent",null,null);
        obj2.move(310,70);
        obj2 = Modeler.createUseObject("#linkIntermediateThrowEvent",null,null);
        obj2.move(340,70);
        obj2 = Modeler.createUseObject("#signalIntermediateCatchEvent",null,null);
        obj2.move(370,70);
        obj2 = Modeler.createUseObject("#signalIntermediateThrowEvent",null,null);
        obj2.move(400,70);
        obj2 = Modeler.createUseObject("#multipleIntermediateCatchEvent",null,null);
        obj2.move(430,70);
        obj2 = Modeler.createUseObject("#multipleIntermediateThrowEvent",null,null);
        obj2.move(460,70);
        obj2 = Modeler.createUseObject("#scalationIntermediateCatchEvent",null,null);
        obj2.move(490,70);
        obj2 = Modeler.createUseObject("#scalationIntermediateThrowEvent",null,null);
        obj2.move(520,70);
        obj2 = Modeler.createUseObject("#parallelIntermediateEvent",null,null);
        obj2.move(550,70);
        obj2 = Modeler.createUseObject("#messageIntermediateCatchEvent",null,null);
        obj2.move(580,70);


        obj2 = Modeler.createUseObject("#endEvent",null,null);
        obj2.move(100,120);
        obj2 = Modeler.createUseObject("#messageEndEvent",null,null);
        obj2.move(130,120);
        obj2 = Modeler.createUseObject("#errorEndEvent",null,null);
        obj2.move(160,120);
        obj2 = Modeler.createUseObject("#cancelationEndEvent",null,null);
        obj2.move(190,120);
        obj2 = Modeler.createUseObject("#compensationEndEvent",null,null);
        obj2.move(220,120);
        obj2 = Modeler.createUseObject("#signalEndEvent",null,null);
        obj2.move(250,120);
        obj2 = Modeler.createUseObject("#multipleEndEvent",null,null);
        obj2.move(280,120);
        obj2 = Modeler.createUseObject("#scalationEndEvent",null,null);
        obj2.move(310,120);
        obj2 = Modeler.createUseObject("#terminationEndEvent",null,null);
        obj2.move(340,120);

        obj2 = Modeler.createUseObject("#exclusiveDataGateway",null,null);
        obj2.move(100,180);
        obj2 = Modeler.createUseObject("#inclusiveDataGateway",null,null);
        obj2.move(160,180);
        obj2 = Modeler.createUseObject("#exclusiveStartGateway",null,null);
        obj2.move(220,180);
        obj2 = Modeler.createUseObject("#eventGateway",null,null);
        obj2.move(280,180);
        obj2 = Modeler.createUseObject("#parallelGateway",null,null);
        obj2.move(340,180);
        obj2 = Modeler.createUseObject("#parallelStartGateway",null,null);
        obj2.move(400,180);
        obj2 = Modeler.createUseObject("#complexGateway",null,null);
        obj2.move(460,180);

        obj2 = Modeler.createUseObject("#data",null,null);
        obj2.move(100,240);
        obj2 = Modeler.createUseObject("#dataInput",null,null);
        obj2.move(150,240);
        obj2 = Modeler.createUseObject("#dataOutput",null,null);
        obj2.move(200,240);
        obj2 = Modeler.createUseObject("#dataStore",null,null);
        obj2.move(250,240);


        obj2 = Modeler.createUseObject("#userTask",null,null);
        obj2.move(100,320);
        obj2 = Modeler.createUseObject("#serviceTask",null,null);
        obj2.move(200,320);
        obj2 = Modeler.createUseObject("#scriptTask",null,null);
        obj2.move(300,320);
        obj2 = Modeler.createUseObject("#ruleTask",null,null);
        obj2.move(400,320);
        obj2 = Modeler.createUseObject("#sendTask",null,null);
        obj2.move(500,320);
        obj2 = Modeler.createUseObject("#receiveTask",null,null);
        obj2.move(600,320);
        obj2 = Modeler.createUseObject("#manualTask",null,null);
        obj2.move(700,320);

        obj2 = Modeler.createUseObject("#pool",null,null);
        obj2.move(350,470);

        obj2 = Modeler.createTask(null,null);
        obj2.addIcon("#userMarker",-1,-1,13,8);
        obj2.setText("Tarea de Usuario");
        obj2.move(100,100);
        obj2.resize(100,60);

        //Modeler.createStartEvent();

        //Modeler.createMessageStartEvent();

    }

