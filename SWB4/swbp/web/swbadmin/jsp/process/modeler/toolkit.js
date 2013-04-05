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

    var ToolKit =
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
        
        getEventX:function(evt)
        {
            if(ToolKit.svg.offsetLeft)
            {
                return evt.pageX-ToolKit.svg.offsetLeft;
            }else
            {
                return evt.pageX-60;
            }
        },
        
        getEventY:function(evt)
        {
            if(ToolKit.svg.offsetTop)
            {
                return evt.pageY-ToolKit.svg.offsetTop;
            }else
            {
                return evt.pageY-10;
            }
        },

        init:function(svgid)
        {
            var _this=this;
            _this.svg=document.getElementById(svgid);
            //desc(_this.svg,true);

            _this.svg.oncontextmenu=function(evt)
            {
                return false;
            };

            _this.svg.onmousemove=function(evt)
            {
                if(_this.onmousemove(evt)==false)return;
                
                _this.svg.mouseX=_this.getEventX(evt);
                _this.svg.mouseY=_this.getEventY(evt);

                if(_this.svg.resizeObject!=null)
                {
                    x=_this.getEventX(evt)-_this.svg.dragOffsetX;
                    y=_this.getEventY(evt)-_this.svg.dragOffsetY;
                    tx=x-_this.svg.resizeObject.parent.getX();
                    ty=y-_this.svg.resizeObject.parent.getY();
                    w=Math.abs(_this.svg.resizeObject.startW+tx*2*_this.svg.resizeObject.ix);
                    h=Math.abs(_this.svg.resizeObject.startH+ty*2*_this.svg.resizeObject.iy);                                
                    _this.svg.resizeObject.parent.resize(w,h);
                    _this.updateResizeBox();

                }else if(_this.svg.dragObject!=null)  //dragObjects
                {
                    _this.selected.unselect=false; //si hace drag no deselecciona
                    x=_this.getEventX(evt)-_this.svg.dragOffsetX;
                    y=_this.getEventY(evt)-_this.svg.dragOffsetY;

                    if(_this.cmdkey==true) //Drag one
                    {
                        _this.svg.dragObject.move(x,y);
                        _this.updateResizeBox();
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
                    var w=_this.getEventX(evt)-_this.svg.dragOffsetX;
                    var h=_this.getEventY(evt)-_this.svg.dragOffsetY;
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
                            var bb = _this.selectBox.getBBox();
                            if ((ox-obj.getWidth()/2 > bb.x && ox+obj.getWidth()/2 < (bb.x+bb.width)) && (oy-obj.getHeight()/2 > bb.y && oy+obj.getHeight()/2 < bb.y+bb.height))
                            //if(ox>=x && ox<=x+w && oy>=y && oy<=y+h)
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
                if(_this.onmousedown(evt)==false)return;
                
                //SelectBox
                if(_this.svg.dragObject==null)
                {
                    if(!_this.cmdkey)_this.unSelectAll();
                    if(_this.selectBox==null)
                    {
                        _this.selectBox=document.createElementNS(_this.svgNS,"rect");
                        _this.selectBox.setAttributeNS(null,"class","selectBox");
                        _this.selectBox.setAttributeNS(null,"x",_this.getEventX(evt));
                        _this.selectBox.setAttributeNS(null,"y",_this.getEventY(evt));
                        _this.selectBox.setAttributeNS(null,"width",0);
                        _this.selectBox.setAttributeNS(null,"height",0);
                        _this.selectBox.setAttributeNS(null,"stroke-dasharray","4,4");
                        _this.svg.appendChild(_this.selectBox);
                        _this.svg.dragOffsetX=_this.getEventX(evt);
                        _this.svg.dragOffsetY=_this.getEventY(evt);                                    
                    }
                }
                
            };                         

            _this.svg.onmouseup=function(evt)
            {
                if(_this.onmouseup(evt)==false)return;
                
                //Drop
                if(_this.svg.dragObject!=null)
                {
                    //alert(_this.selected);
                    //_this.selected
                    //desc(_this.svg.childNodes[0],true);
                    var droped=false;
                    
                    for (var i = _this.svg.childNodes.length; i-- && i>=0;)
                    {
                        var obj=_this.svg.childNodes[i];
                        if(obj && obj.contents)
                        {   
                            if(obj==_this.svg.dragObject)
                            {
                                for (;i-- && i>=0;)
                                {
                                    obj=_this.svg.childNodes[i];
                                    if(obj.inBounds && obj.inBounds(_this.svg.dragObject.getX(), _this.svg.dragObject.getY()))
                                    {
                                        if(_this.selected.indexOf(obj)==-1)
                                        {
                                            obj.onDropObjects(_this.selected);
                                            i=0;
                                            droped=true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(droped==false)
                    {
                        for (var i = _this.selected.length; i--;)
                        {
                            if(_this.selected.indexOf(_this.selected[i].parent)==-1)
                            {
                                _this.selected[i].setParent(null);
                            }
                        }                          
                    }
                    
                }
                
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
        
        onmousemove:function(evt)
        {
            //implementar
            return true;
        },
        
        onmousedown:function(evt)
        {
            //implementar
            return true;
        },
        
        onmouseup:function(evt)
        {
            //implementar
            return true;
        },

        updateResizeBox:function()
        {
            var _this=ToolKit;
            for(i=0;i<_this.resizeBox.length;i++)
            {
                _this.resizeBox[i].update();
            }
        },

        createResizeBox:function(obj, ix, iy, cursor)
        {
            var _this=ToolKit;
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
            var _this=ToolKit;
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

        selectObj:function(obj,noShowResize)
        {
            var _this=ToolKit;
            _this.selected.push(obj);
            obj.selected=true;   
            obj.setOverClass();   
            if(!noShowResize)
            {
                setTimeout(function()
                {
                    obj.moveFirst();
                    _this.showResizeBoxes();
                },10); //Se invoca en un thread para evitar problema de chrome            
            }
        },

        unSelectObj:function(obj)
        {
            var _this=ToolKit;
            var i=_this.selected.indexOf(obj);
            _this.selected.splice(i, 1);
            obj.selected=false;
            obj.setBaseClass();
            _this.showResizeBoxes();
        },

        unSelectAll:function()
        {
            var _this=ToolKit;
            while((o=_this.selected.pop())!=null)
            {
                o.setBaseClass();
                o.selected=false;
            }        
            _this.showResizeBoxes();
        },

        keydown:function(evt)
        {
            var _this=ToolKit;
            //desc(evt,true);
            if(evt.keyCode==8 && evt.which==8)
            //if(evt.keyCode==32 && evt.which==32)
            {
                for (var i = _this.selected.length; i--;) 
                {
                    _this.selected[i].remove();
                }                             
                _this.unSelectAll();
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
            var _this=ToolKit;
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
        
        
        createConnectionPath:function(x1, y1, x2, y2, marker_start, marker_mid, marker_end, dash_array, styleClass) 
        {
            var _this=ToolKit;
            //console.log(_this);
            var ret = document.createElementNS(_this.svgNS,"path"); 

            ret.fixed=false;
            ret.fromObject=null;
            ret.toObject=null;
            
            ret.setAttributeNS(null, "d", "M0 0");
            if (dash_array && dash_array != null) {
                ret.setAttributeNS(null, "stroke-dasharray", dash_array);
            }
            if (marker_start && marker_start != null) {
                ret.setAttributeNS(null, "marker-start", "url(#"+marker_start+")");
            }
            if (marker_mid && marker_mid != null) {
                ret.setAttributeNS(null, "marker-mid", "url(#"+marker_mid+")");
            }
            if (marker_end && marker_end != null) {
                ret.setAttributeNS(null, "marker-end", "url(#"+marker_end+")");
            }
            if (styleClass && styleClass != null) {
                ret.setAttributeNS(null, "class", styleClass);
            }
            //obj.resizeable=false;
            //desc(ret, true);
            ret.addPoint = function (x,y) {
                var seg = ret.createSVGPathSegLinetoAbs(x, y);
                ret.pathSegList.appendItem(seg);
            }
            
            ret.setStartPoint=function(x,y) {
                ret.setPoint(0,x,y);
            }
                    
            ret.setEndPoint=function(x,y) {
                ret.setPoint(ret.pathSegList.numberOfItems-1,x,y);
            }            
            
            ret.setPoint = function(p,x,y)
            {
                ret.pathSegList.getItem(p).x=x;
                ret.pathSegList.getItem(p).y=y;
            }
                    
            ret.listSegments=function() {
                for (var i=0; i < ret.pathSegList.numberOfItems; i++) {
                    //desc(ret.pathSegList.getItem(i),true);
                    var segment=ret.pathSegList.getItem(i);
                    if (segment.pathSegType==SVGPathSeg.PATHSEG_LINETO_ABS) {
                       console.log("lineto "+segment);
                    } else if (segment.pathSegType==SVGPathSeg.PATHSEG_MOVETO_ABS) {
                        console.log("moveto "+segment);
                    }
                }
            }
            
            ret.translate=function(x,y) 
            {
                for (var i=0; i < ret.pathSegList.numberOfItems; i++) 
                {
                    var segment=ret.pathSegList.getItem(i);
                    segment.x=segment.x+x;
                    segment.y=segment.y+y;
                }                
            }
            
            ret.remove=function()
            {
                //remove fromObject
                if(ret.fromObject!=null && (ax = ret.fromObject.outConnections.indexOf(ret)) !== -1) {
                    ret.fromObject.outConnections.splice(ax, 1);
                }                
                
                //remove toObject
                if(ret.toObject!=null && (ax = ret.toObject.inConnections.indexOf(ret)) !== -1) {
                    ret.toObject.inConnections.splice(ax, 1);
                }                
                
                try
                {
                    _this.svg.removeChild(ret);
                }catch(noe){}
            }
            
            ret.setPoint(x1,y1);
            ret.addPoint(x2,y2);
            
            ret.setClass=function(styleC) {
                ret.setAttributeNS(null, "class", styleC);
            }

            _this.svg.appendChild(ret);
            return ret;
        },

        createResizeObject:function(id, parent)
        {
            var _this=ToolKit;
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
            var _this=ToolKit;
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
            var _this=ToolKit;
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
            var _this=ToolKit;
            var obj=constructor();
            
            obj.text=null;
            obj.contents=[];                        
            obj.icons=[];      
            obj.inConnections=[];
            obj.outConnections=[];
            obj.canSelect=true;

            if(id && id!=null)obj.setAttributeNS(null,"id",id);       
            
            obj.oncontextmenu=function(evt)
            {
                return false;
            };            

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
                var _this=ToolKit;
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
                if(obj.text!=null)obj.text.update();      
                
                //Move InConnections
                for(var i = obj.inConnections.length; i--;)
                {
                    obj.inConnections[i].updateInterPoints();
                }                
                
                //Move OutConnections
                for(var i = obj.outConnections.length; i--;)
                {
                    obj.outConnections[i].updateInterPoints();
                }                 
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
                if(obj.text!=null)
                {
                    //obj.text.traslate(offx,offy);
                    obj.text.PX=offx;
                    obj.text.PY=offy;
                    obj.text.update();
                    for (var i = obj.text.childNodes.length; i--;)
                    {
                        obj.text.childNodes[i].setAttributeNS(null,"x",obj.text.getX());
                    }
                }
                
                //Move InConnections
                for(var i = obj.inConnections.length; i--;)
                {
                    obj.inConnections[i].setEndPoint(x,y);
                }                
                
                //Move OutConnections
                for(var i = obj.outConnections.length; i--;)
                {
                    obj.outConnections[i].setStartPoint(x,y);
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
                
                //Elimina Iconos
                if(obj.icons)
                {
                    for (var i = obj.icons.length; i--;)
                    {
                        obj.icons[i].obj.remove();
                    }
                }
                //Elimina Texto
                if(obj.text!=null)obj.text.remove();
                
                //Eliminar Conexiones
                //Move InConnections
                for(var i = obj.inConnections.length; i--;)
                {
                    obj.inConnections[i].remove();
                }                
                
                //Move OutConnections
                for(var i = obj.outConnections.length; i--;)
                {
                    obj.outConnections[i].remove();
                }                  
                
                try
                {
                    _this.svg.removeChild(obj);
                }catch(noe){}
                
                _this.unSelectObj(obj);
                _this.showResizeBoxes();
                
                return this;
            };
            
            //Mueve el elemento al primer plano
            obj.moveFirst = function()
            {
                //alert("obj:"+obj.icons.length);
                _this.svg.appendChild(obj);
                
                //mueve Iconos
                if(obj.icons)
                {
                    for (var i = obj.icons.length; i--;)
                    {
                        obj.icons[i].obj.moveFirst();
                    }
                }
                //mueve texto
                if(obj.text!=null)obj.text.moveFirst();
                
                //mueve contenidos
                for (var i = obj.contents.length; i--;)
                {
                    obj.contents[i].moveFirst();
                }
                
            };
            
            obj.setParent = function(newParent)
            {
                if(obj.parent)
                {
                    while ((ax = obj.parent.contents.indexOf(obj)) !== -1) {
                        obj.parent.contents.splice(ax, 1);
                    }                    
                }
                
                if(newParent && newParent!=null)
                {
                    newParent.contents.push(obj);
                    obj.parent=newParent;
                }else
                {
                    _this.contents.push(obj);
                    obj.parent=_this;
                }                 
            };
            
            obj.isChild = function(parent)
            {
                if(obj.parent!=null)
                {
                    if(obj.parent==parent)return true;
                    else 
                    {
                        if(obj.parent.isChild)
                        {
                            return obj.parent.isChild(parent);
                        }
                        return false;
                    }
                }
                return false;
            };
            
            //pos validas del 1 al 9, 1=esquina superior izquierda, 5=centro, 9=esquina inferior derecha
            obj.addIcon=function(type,posx,posy,offx,offy)
            {
                var iobj=_this.createUseBaseObject(type,null,null);
                iobj.canSelect=false;
                iobj.onmousedown=function(evt)
                {
                    obj.onmousedown(evt);
                };
                iobj.onmouseup=function(evt)
                {
                    obj.onmouseup(evt);
                };      
                iobj.onmousemove=function(evt)
                {
                    obj.onmousemove(evt);
                };                 
                var icon={obj:iobj,posx:posx,posy:posy,offx:offx,offy:offy};
                obj.icons.push(icon);

            };

            //pos validas del 1 al 9, 1=esquina superior izquierda, 5=centro, 9=esquina inferior derecha
            obj.setText=function(text,posx,posy,width,orientation)
            {
                if(obj.text!=null)obj.text.remove();
                obj.text=_this.createText(text,obj);
                obj.text.textPX=posx;
                obj.text.textPY=posy;
                obj.text.textW=width;
                obj.text.textO=orientation;
                obj.text.update();
            };  
            
            obj.onDropObjects=function(objs)
            {
                for (var i = objs.length; i--;)
                {
                    if(objs.indexOf(objs[i].parent)==-1)
                    {
                        objs[i].setParent(obj);
                    }
                }
            };
            
            obj.inBounds=function(x,y)
            {
                return (obj.getWidth()/2-(Math.abs(obj.getX()-x))>=0 && (obj.getHeight()/2-Math.abs(obj.getY()-y))>=0)
            };
            
            obj.addInConnection=function(connectionPath)
            {
                obj.inConnections.push(connectionPath);
                connectionPath.toObject=obj;
                connectionPath.setEndPoint(obj.getX(),obj.getY());
                
            };
            
            obj.addOutConnection=function(connectionPath)
            {
                obj.outConnections.push(connectionPath);
                connectionPath.fromObject=obj;
                connectionPath.setStartPoint(obj.getX(),obj.getY());
            };

            obj.setParent(parent);
            _this.svg.appendChild(obj);
            if(obj.getWidth()==0 && obj.getHeight()==0)
            {
                var bb=obj.getBBox();
                obj.setWidth(bb.width);
                obj.setHeight(bb.height);
            }            
            
            return obj;
        },                    

        createObject:function(constructor, id, parent)
        {
            var _this=ToolKit;
            var obj=_this.createBaseObject(constructor, id, parent);

            obj.onmousedown=function(evt)
            {
                console.log(evt);
                if(obj.mousedown(evt))
                {
                    obj.startX=_this.getEventX(evt);
                    obj.startY=_this.getEventY(evt);
                    _this.svg.dragOffsetX=_this.svg.mouseX-obj.getAttributeNS(null,"x");
                    _this.svg.dragOffsetY=_this.svg.mouseY-obj.getAttributeNS(null,"y");
                    _this.svg.dragObject=obj;
                    obj.select(true);
                }
            };

//            obj.onmousemove=function(evt)
//            {
//                //console.log("id:"+obj.getAttributeNS(null,"id"));                            
//                if(obj.mousemove(evt))
//                {
//                    //
//                }
//            };

//            obj.mousemove=function(evt)
//            {
//                //Sobreescribir
//                return true;
//            };  

            obj.select=function(down)
            {
                //desc(obj.getBBox(),true);
                var _this=ToolKit;
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
                return true;
            };

            obj.onmouseup=function(evt)
            {
                _this=ToolKit;
                if(obj.mouseup(evt))
                {
                    obj.select(false);
                }

                //if(_this.selected.length==1 && obj.startX==_this.getEventX(evt) && obj.startY==_this.getEventY(evt))
                //{
                //    //evento click de objeto
                //}
            };

            obj.mouseup=function(evt)
            {
                //Sobreescribir
                return true;
            };    
            
            obj.onclick=function(evt)
            {
                if(this.mouseclick(evt))
                {

                }
                //return this.mouseclick(evt);
            }

            obj.mouseclick=function(evt)
            {
                //Sobre escribir
                return true;
            }
            return obj;
        },

        createText:function(text,parent)
        {
            var _this=ToolKit;
            var constructor=function()
            {
                var tx = document.createElementNS(_this.svgNS,"text"); //to create a circle, for rectangle use rectangle
                //tx.appendChild(document.createTextNode(text));
                tx.setAttributeNS(null,"text-anchor","middle");
                tx.setAttributeNS(null,"font-size","11");
                tx.setAttributeNS(null,"font-family","Verdana, Geneva, sans-serif");
                return tx;
            };
            var obj=_this.createBaseObject(constructor,null,null);                  
            obj.value=text;                                     //Valor de la caja de texto
            obj.canSelect=false;
            obj.setAttributeNS(null,"class","textLabel");
            obj.onmousedown=function(evt)
            {
                //desc(evt,true);
                parent.onmousedown(evt);
            }; 
            
            obj.onmouseup=function(evt)
            {
                //desc(evt,true);
                parent.onmouseup(evt);
            };  
            
            obj.onmousemove=function(evt)
            {
                //desc(evt,true);
                parent.onmousemove(evt);
            };              

            parent.ondblclick=function(evt)
            {
                obj.ondblclick(evt);
            };

            obj.ondblclick=function(evt)
            {
                var txt=prompt("Texto:",obj.value);                  
                if(txt && txt!=null)
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
                
                var w=parent.getWidth();
                if(obj.textW)w=obj.textW;
                
                for(var i=1; i<words.length; i++)
                {
                    var len = tspan_element.firstChild.data.length;				// Find number of letters in string
                    tspan_element.firstChild.data += " " + words[i];			// Add next word
                    
                    if (tspan_element.getComputedTextLength() > w-10)
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
                
                var offy=0;
                if(obj.textPY)offy=obj.textPY*(parent.getHeight()/2+lin*dy/2+dy/2);            
                text_element.setY(parent.getY()+dy-lin*dy/2+offy);
                
                var offx=0;
                if(obj.textPX)offx=obj.textPX*((w/2)+dy/2);            
                text_element.setX(parent.getX()+offx);
                
                if(obj.textO==2) {
                    text_element.setAttributeNS(null, "transform", "rotate(-90,"+text_element.getX()+","+text_element.getY()+")");
                    //text_element.setAttributeNS(null, "style", "writing-mode:tb");
                }
            };

            return obj;
        }
    };
