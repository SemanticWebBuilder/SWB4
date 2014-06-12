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
        snap2Grid:true,
        snap2GridSize:10,
        layer:null,
        tooltip:null,
        loaded:false,
        tmHandler:null,

        setLayer:function(layer)
        {
            var _this=ToolKit;
            _this.layer=layer;
            for (var i = _this.contents.length; i--;)
            {
                if(_this.contents[i].layer===layer)
                {
                    _this.contents[i].show();
                }else
                {
                    if(_this.contents[i].hide)_this.contents[i].hide();
                }
            }
        },
                
        removeLayer:function(layer)
        {
            var _this=ToolKit;
            _this.layer=layer;
            for (var i = _this.contents.length; i--;) 
            {
                if(_this.contents[i].layer===layer)
                {
                    _this.contents[i].remove();
                }
            } 
        },                

        /*Revisar*/
        getWidth:function()
        {
            if (this.svg.width.baseVal) {
                return this.svg.width.baseVal.value;
            } else {
                return this.svg.width;
            }
        },

        getHeight:function()
        {
            if (this.svg.height.baseVal) {
                return this.svg.height.baseVal.value;
            } else {
                return this.svg.height;
            }
        },

        setWidth:function(width)
        {
            if (this.svg.width.baseVal) {
                this.svg.width.baseVal.value=width;
            } else {
                this.svg.width=width;
            }
        },

        setHeight:function(height)
        {
            if (this.svg.height.baseVal) {
                this.svg.height.baseVal.value=height;
            } else {
                this.svg.height=height;
            }
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
                    if((_this.svg.resizeObject.parent.getX()-w/2)<0)w=_this.svg.resizeObject.parent.getX()*2;
                    if((_this.svg.resizeObject.parent.getY()-h/2)<0)h=_this.svg.resizeObject.parent.getY()*2;
                    _this.svg.resizeObject.parent.resize(w,h);
                    _this.updateResizeBox();

                }else if(_this.svg.dragObject!=null)  //dragObjects
                {
                    _this.selected.unselect=false; //si hace drag no deselecciona
                    x=_this.getEventX(evt)-_this.svg.dragOffsetX;
                    y=_this.getEventY(evt)-_this.svg.dragOffsetY;

//                    if(_this.snap2Grid)
//                    {
//                        x=Math.round(x/_this.snap2GridSize)*_this.snap2GridSize;
//                        y=Math.round(y/_this.snap2GridSize)*_this.snap2GridSize;
//                    }

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
                    //_this.svg.appendChild(_this.selectBox);

                    var nodes=_this.svg.childNodes;
                    for(i=0;i<nodes.length;i++)
                    {
                        var obj=nodes.item(i);
                        if(obj.contents && obj.canSelect==true && !obj.hidden)    //Es un objeto grafico
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
                _this.loaded = true;

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
                        //_this.selectBox.setAttributeNS(null,"stroke-dasharray","4,4");
                        _this.svg.appendChild(_this.selectBox);
                        _this.svg.dragOffsetX=_this.getEventX(evt);
                        _this.svg.dragOffsetY=_this.getEventY(evt);                                    
                    }
                }
                evt.preventDefault();
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
                                    if(obj.hidden==false && obj.inBounds && obj.inBounds(_this.svg.dragObject.getX(), _this.svg.dragObject.getY()))
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
                    
                    if(_this.snap2Grid)
                    {
                        for (var i = _this.selected.length; i--;)
                        {
                            _this.selected[i].snap2Grid();
                        }                          
                        _this.updateResizeBox();
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
                if(b.parent!==undefined)
                {
                    b.setAttributeNS(null,"x",b.parent.getX()+(b.parent.getWidth()/2)*b.ix);
                    b.setAttributeNS(null,"y",b.parent.getY()+(b.parent.getHeight()/2)*b.iy);
                }
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
                
        createToolTip:function() {
            var _this=ToolKit;
            var constructor = function() {
                var obj = document.createElementNS(_this.svgNS,"rect");
                obj.setAttributeNS(null, "rx", "10");
                obj.setAttributeNS(null, "ry", "10");
                return obj;
            };
            
            var msgBox = _this.createBaseObject(constructor, null, null);
            msgBox.setAttributeNS(null,"class","toolTip");
            msgBox.setAttributeNS(null,"filter","url(#dropshadow)");
            msgBox.canSelect=false;
            msgBox.onmousemove = function() {
                return false;
            };
            msgBox.onmouseup = function() {
                return false;
            };
            
            _this.tooltip=msgBox;
        },

        showTooltip:function(pos, tooltipText, width, tooltipType) {
            var _this=ToolKit;
            
            if (_this.tmHandler && _this.tmHandler !== null) {
                clearTimeout(_this.tmHandler);
            }
            
            if (_this.tooltip === null) _this.createToolTip();
            
            if (tooltipType==="Error") {
                _this.tooltip.setAttributeNS(null,"class","errorToolTip");
            } else if (tooltipType==="Warning") {
                _this.tooltip.setAttributeNS(null,"class","warningToolTip");
            }
            _this.tooltip.setText(tooltipText,0,0,width,1);
            _this.tooltip.resize(width, 60);
            _this.tooltip.move(window.pageXOffset+_this.tooltip.getWidth()/2+10, window.pageYOffset+_this.tooltip.getHeight()/2+10);
    
//            var anim = document.createElementNS(_this.svgNS, "animate");
//            anim.setAttributeNS(null, "attributeType", "CSS");
//            anim.setAttributeNS(null, "attributeName", "opacity");
//            anim.setAttributeNS(null, "from", "0");
//            anim.setAttributeNS(null, "to", "1");
//            anim.setAttributeNS(null, "dur", "2s");
//            _this.tooltip.appendChild(anim);
            _this.tooltip.show();
            _this.tooltip.moveFirst();
            _this.tooltip.text.moveFirst();
            _this.tmHandler = setTimeout(function(){_this.tooltip.hide();},3000);
        },
                
        hideToolTip:function() {
            var _this=ToolKit;
            if (_this.tooltip != null) {
                _this.tooltip.hide();
                //_this.svg.removeChild(_this.tooltip);
            }
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
                _this.hideResizeBoxes()

            }
        },
                
        hideResizeBoxes:function()
        {
            var _this=ToolKit;
            while((o=_this.resizeBox.pop())!=null)
            {
                _this.svg.removeChild(o);
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
            if(i>-1)
            {
                _this.hideResizeBoxes();
            }
        },

        unSelectAll:function()
        {
            var _this=ToolKit;
            while((o=_this.selected.pop())!=null)
            {
                o.setBaseClass();
                o.selected=false;
            }        
            _this.hideResizeBoxes();
        },

        keydown:function(evt)
        {
            //desc(evt,true);
            var _this=ToolKit;
            if((evt.keyCode==8 && evt.which==8) || evt.keyCode==46 && evt.which==46)
            //if(evt.keyCode==32 && evt.which==32)
            {
                try
                {
                    for (var i = _this.selected.length; i--;) 
                    {
                        _this.selected[i].remove();
                    }                             
                    _this.unSelectAll();
                }catch(e){console.log(e)};
                _this.stopPropagation(evt);
            }else if((evt.keyCode==91 && evt.which==91) || (evt.keyCode==224 && evt.which==224))
            {
                _this.cmdkey=true;
                _this.stopPropagation(evt);
            }else if(evt.keyCode==17 && evt.which==17)
            {
                _this.ctrlkey=true;
                
//                if(_this.selected.length>0)
//                {
//                    for (var i = _this.selected.length; i--;) 
//                    {
//                        _this.selected[i].hide();
//                    } 
//                    _this.unSelectAll();
//                }else
//                {
//                    for (var i = _this.contents.length; i--;) 
//                    {
//                        _this.contents[i].show();
//                    } 
//                }
                _this.stopPropagation(evt);
            }else if(evt.keyCode==65 && evt.which==65)
            {
                if(_this.cmdkey)
                {
                    for (var i = _this.contents.length; i--;) 
                    {
                        if(!_this.contents[i].hidden && _this.contents[i].canSelect)
                        {
                            _this.selectObj(_this.contents[i])
                        }
                        
                    }                 }
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
            var obj = document.createElementNS(_this.svgNS,"path"); 

            obj.fixed=false;
            obj.fromObject=null;
            obj.toObject=null;
            
            obj.setAttributeNS(null, "d", "M0 0");
            if (dash_array && dash_array != null) {
                obj.setAttributeNS(null, "stroke-dasharray", dash_array);
            }
            if (marker_start && marker_start != null) {
                obj.setAttributeNS(null, "marker-start", "url(#"+marker_start+")");
            }
            if (marker_mid && marker_mid != null) {
                obj.setAttributeNS(null, "marker-mid", "url(#"+marker_mid+")");
            }
            if (marker_end && marker_end != null) {
                obj.setAttributeNS(null, "marker-end", "url(#"+marker_end+")");
            }
            if (styleClass && styleClass != null) {
                obj.setAttributeNS(null, "class", styleClass);
            }
            //obj.resizeable=false;
            //desc(obj, true);
            obj.addPoint = function (x,y) {
                var seg = obj.createSVGPathSegLinetoAbs(x, y);
                obj.pathSegList.appendItem(seg);
            }
            
            obj.setStartPoint=function(x,y) {
                obj.setPoint(0,x,y);
            }
                    
            obj.setEndPoint=function(x,y) {
                obj.setPoint(obj.pathSegList.numberOfItems-1,x,y);
            }            
            
            obj.setPoint = function(p,x,y)
            {
                //alert(p+" "+x+" "+y);
                obj.pathSegList.getItem(p).x=x;
                obj.pathSegList.getItem(p).y=y;
            }
                    
            obj.listSegments=function() {
                for (var i=0; i < obj.pathSegList.numberOfItems; i++) {
                    //desc(obj.pathSegList.getItem(i),true);
                    var segment=obj.pathSegList.getItem(i);
                    if (segment.pathSegType==SVGPathSeg.PATHSEG_LINETO_ABS) {
                       //console.log("lineto "+segment);
                    } else if (segment.pathSegType==SVGPathSeg.PATHSEG_MOVETO_ABS) {
                       //console.log("moveto "+segment);
                    }
                }
            }
            
            obj.translate=function(x,y) 
            {
                for (var i=0; i < obj.pathSegList.numberOfItems; i++) 
                {
                    var segment=obj.pathSegList.getItem(i);
                    segment.x=segment.x+x;
                    segment.y=segment.y+y;
                }                
            }
            
            obj.remove=function()
            {
                //remove fromObject
                if (obj.fromObject && obj.fromObject.outConnections) {
                    ax = obj.fromObject.outConnections.indexOf(obj);
                    if(obj.fromObject!==null && ax !== -1) {
                        obj.fromObject.outConnections.splice(ax, 1);
                    }
                }
                
                //remove toObject
                if (obj.toObject && obj.toObject.inConnections) {
                    ax = obj.toObject.inConnections.indexOf(obj);
                    if(obj.toObject!==null && ax !== -1) {
                        obj.toObject.inConnections.splice(ax, 1);
                    }
                }
                
                try
                {
                    _this.svg.removeChild(obj);
                }catch(noe){console.log(noe);}
            },
            
            obj.hide=function()
            {
                obj.style.display="none";
                obj.hidden=true;
            },
            
            obj.show=function()
            {
                obj.style.display="";
                obj.hidden=false;
            },
            
            obj.setPoint(0,x1,y1);
            obj.addPoint(x2,y2);
            
            obj.setClass=function(styleC) {
                obj.setAttributeNS(null, "class", styleC);
            },
            
            obj.moveFirst = function() {
                _this.svg.appendChild(obj);
            };

            _this.svg.appendChild(obj);
            return obj;
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
            
            obj.elementType="BaseObject";
            obj.text=null;
            obj.contents=[];                        
            obj.icons=[];      
            obj.inConnections=[];
            obj.outConnections=[];
            obj.canSelect=true;
            obj.hidden=false;
            obj.layer=_this.layer;

            if(id && id!=null)obj.setAttributeNS(null,"id",id);       
            
            obj.canAddToDiagram=function() {
                return true;
            };
            
            obj.canStartLink=function(link) {
                return true;
            };
            
            obj.canEndLink=function(link) {
                return true;
            };
            
            obj.canAttach=function(parent) {
                return true;
            };
            
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
                //Crecemos pantalla
                if(x+obj.getWidth()/2>_this.getWidth())
                {
                    _this.setWidth(x+obj.getWidth()/2);
                }
                if(y+obj.getHeight()/2>_this.getHeight())
                {
                    _this.setHeight(y+obj.getHeight()/2);
                }    
                
                if(obj.canSelect==true)
                {
                    //Validamos bordes
                    if(x-obj.getWidth()/2<0)
                    {
                        x=obj.getWidth()/2;
                    }
                    if(y-obj.getHeight()/2<0)
                    {
                        y=obj.getHeight()/2;
                    }                    
                }
                
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
            };    

            obj.remove = function(all) {
                if(!all)
                {
                    //En algunos casos, el parent es nulo, es decir, el canvas
                    var parent = obj.parent;
                    if (parent && parent != null) 
                    {
                        while ((ax = parent.contents.indexOf(obj)) !== -1) {
                            parent.contents.splice(ax, 1);
                        }
                    }
                    //elimina el objeto de contents
                    while ((ax = _this.contents.indexOf(obj)) !== -1) {
                        _this.contents.splice(ax, 1);
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
                
                //_this.unSelectObj(obj);
                if(_this.selected.indexOf(obj)>-1)
                {
                    _this.hideResizeBoxes();
                }                
                return this;
            };
            
            obj.hide = function() {
                
                obj.style.display="none";
                obj.hidden=true;
                
                for (var i = obj.contents.length; i--;)
                {
                    obj.contents[i].hide();
                }
                
                //Elimina Iconos
                if(obj.icons)
                {
                    for (var i = obj.icons.length; i--;)
                    {
                        obj.icons[i].obj.hide();
                    }
                }
                //Elimina Texto
                if(obj.text!=null)obj.text.hide();
                
                //Eliminar Conexiones
                //Move InConnections
                for(var i = obj.inConnections.length; i--;)
                {
                    obj.inConnections[i].hide();
                }                
                
                //Move OutConnections
                for(var i = obj.outConnections.length; i--;)
                {
                    obj.outConnections[i].hide();
                }                  
                
                //_this.unSelectObj(obj);
                if(_this.selected.indexOf(obj)>-1)
                {
                    _this.hideResizeBoxes();               
                }
                return this;
            };     
            
            obj.show = function(all) {
                
                obj.style.display="";
                obj.hidden=false;
                
                for (var i = obj.contents.length; i--;)
                {
                    obj.contents[i].show();
                }
                
                //Elimina Iconos
                if(obj.icons)
                {
                    for (var i = obj.icons.length; i--;)
                    {
                        obj.icons[i].obj.show();
                    }
                }
                //Elimina Texto
                if(obj.text!=null)obj.text.show();
                
                //Eliminar Conexiones
                //Move InConnections
                for(var i = obj.inConnections.length; i--;)
                {
                    obj.inConnections[i].show();
                }                
                
                //Move OutConnections
                for(var i = obj.outConnections.length; i--;)
                {
                    obj.outConnections[i].show();
                }                  
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
                
                //mueve conexiones
                for (var i = obj.inConnections.length; i--;)
                {
                    obj.inConnections[i].moveFirst();
                }
                
                for (var i = obj.outConnections.length; i--;)
                {
                    obj.outConnections[i].moveFirst();
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
                
                if(newParent && newParent!==null)
                {
                    if (newParent.contents.indexOf(obj) === -1) {
                        newParent.contents.push(obj);
                        obj.parent=newParent;
                    }
                }else
                {
                    if (_this.contents.indexOf(obj) === -1) {
                        _this.contents.push(obj);
                        //obj.parent=_this;
                    }
                    obj.parent=null;
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
                iobj.ondblclick=function(evt)
                {
                    obj.ondblclick(evt);
                };                
                var icon={obj:iobj,posx:posx,posy:posy,offx:offx,offy:offy};
                obj.icons.push(icon);
                return icon;

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
                        if (objs[i].canAttach(obj)) {
                            objs[i].setParent(obj);
                        }
                    }
                }
            };
            
            obj.inBounds=function(x,y)
            {
                return (obj.getWidth()/2-(Math.abs(obj.getX()-x))>=0 && (obj.getHeight()/2-Math.abs(obj.getY()-y))>=0);
            };
            
            obj.addInConnection=function(connectionPath)
            {
                if (obj.inConnections.indexOf(connectionPath) === -1) {
                    obj.inConnections.push(connectionPath);
                    connectionPath.toObject=obj;
                    connectionPath.setEndPoint(obj.getX(),obj.getY());
                }
                
            };
            
            obj.removeInConnection=function(connectionPath) {
                var idx = obj.inConnections.indexOf(connectionPath);
                if (idx !== -1) {
                    connectionPath.toObject = null;
                    obj.inConnections.splice(idx);
                }
            }
            
            obj.addOutConnection=function(connectionPath)
            {
                if (obj.outConnections.indexOf(connectionPath) === -1) {
                    obj.outConnections.push(connectionPath);
                    connectionPath.fromObject=obj;
                    connectionPath.setStartPoint(obj.getX(),obj.getY());
                }
            };
            
            obj.removeOutConnection=function(connectionPath) {
                var idx = obj.outConnections.indexOf(connectionPath);
                if (idx !== -1) {
                    connectionPath.fromObject = null;
                    obj.outConnections.splice(idx);
                }
            }
            
            obj.snap2Grid=function()
            {
                if(ToolKit.snap2Grid)
                {
                    obj.move(Math.round(obj.getX()/_this.snap2GridSize)*_this.snap2GridSize,Math.round(obj.getY()/_this.snap2GridSize)*_this.snap2GridSize);
                }
            }
            
            obj.setParent(parent);
            if(_this.svg)
            {
                _this.svg.appendChild(obj);
            }
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
                //console.log(evt);
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
                if(parent.onmouseup)
                    parent.onmouseup(evt);
            };  
            
            obj.onmousemove=function(evt)
            {
                //desc(evt,true);
                if(parent.onmousemove)
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
