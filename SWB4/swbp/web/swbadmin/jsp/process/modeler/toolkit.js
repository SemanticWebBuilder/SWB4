/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
    function desc(obj, content)
    {
        var ret="";
        for (property in obj) {
            if(content && content===true)
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
        lineHandler:null,

        setLayer:function(layer)
        {
            var _this = ToolKit,
                _contents = _this.contents || [];
            
            _this.layer = layer;
            for (var i = _contents.length; i--;)
            {
                _contents[i].layer===layer ? _contents[i].show() : _contents[i].hide();
            }
        },
                
        removeLayer:function(layer)
        {
            var _this=ToolKit,
                _contents = _this.contents || [];
        
            _this.layer=layer;
            for (var i = _contents.length; i--;) 
            {
                if(_contents[i].layer===layer)
                {
                    _contents[i].remove();
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
            var offLeft = ToolKit.svg.offsetLeft;
            return offLeft ? evt.pageX - offLeft : evt.pageX - 60;
        },
        
        getEventY:function(evt)
        {
            var offTop = ToolKit.svg.offsetTop;
            return offTop ? evt.pageY - offTop : evt.pageY - 10;
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
                var resizeObj = _this.svg.resizeObject,
                    dragObj = _this.svg.dragObject,
                    selectBox = _this.selectBox,
                    evtX = _this.getEventX(evt),
                    evtY = _this.getEventY(evt),
                    dragOffX = _this.svg.dragOffsetX,
                    dragOffY = _this.svg.dragOffsetY,
                    i;
                    
                
                if(!_this.onmousemove(evt)) return;
                _this.svg.mouseX = evtX;
                _this.svg.mouseY = evtY;

                if(resizeObj !== null)
                {
                    var p = resizeObj.parent,
                        sW = resizeObj.startW,
                        sH = resizeObj.startH,
                        ix = resizeObj.ix,
                        iy = resizeObj.iy;
                
                    x = evtX - dragOffX;
                    y = evtY - dragOffY;
                    
                    tx = x - p.getX();
                    ty = y - p.getY();
                    w = Math.abs(sW + tx * 2 * ix);
                    h = Math.abs(sH + ty * 2 * iy);
                    if((p.getX() - w / 2) < 0){
                        w = p.getX() * 2;
                    }
                    if((p.getY() - h / 2) < 0){
                        h = p.getY() * 2;
                    }
                    p.resize(w,h);
                    _this.updateResizeBox();

                }else if(dragObj!==null)  //dragObjects
                {
                    _this.selected.unselect=false; //si hace drag no deselecciona
                    x= evtX - dragOffX;
                    y= evtY - dragOffY;

//                    if(_this.snap2Grid)
//                    {
//                        x=Math.round(x/_this.snap2GridSize)*_this.snap2GridSize;
//                        y=Math.round(y/_this.snap2GridSize)*_this.snap2GridSize;
//                    }

                    if(_this.cmdkey) //Drag one
                    {
                        dragObj.move(x,y);
                        _this.updateResizeBox();
                    }else //drag selecteds
                    {
                        tx = x - dragObj.getX();
                        ty = y - dragObj.getY();

                        var _selected = _this.selected || [];
                        for (i = _selected.length; i--;) 
                        {                                
                            _selected[i].traslate(tx, ty);
                        }
                        _this.updateResizeBox();
                    }
                }else if(selectBox!==null) //SelectBox
                {
                    var w = evtX - dragOffX,
                        h = evtY - dragOffY,
                        x = dragOffX,
                        y = dragOffY;

                    if(w < 0)
                    {
                        x = dragOffX + w;
                        w = -w;
                    }
                    if(h < 0)
                    {
                        y = dragOffY + h;
                        h = -h;                                        
                    }

                    selectBox.setAttributeNS(null,"x",x);
                    selectBox.setAttributeNS(null,"width",w);
                    selectBox.setAttributeNS(null,"y",y);
                    selectBox.setAttributeNS(null,"height",h);
                    //_this.svg.appendChild(_this.selectBox);

                    var nodes=_this.svg.childNodes;
                    for(i=0 ; i < nodes.length ; i++)
                    {
                        var obj = nodes.item(i); 
                        if(obj.contents && obj.canSelect===true && !obj.hidden)    //Es un objeto grafico
                        {
                            var ox = obj.getX();
                            var oy = obj.getY();
                            var bb = selectBox.getBBox();
                            if ((ox-obj.getWidth()/2 > bb.x && ox+obj.getWidth()/2 < (bb.x+bb.width)) && (oy-obj.getHeight()/2 > bb.y && oy+obj.getHeight()/2 < bb.y+bb.height))
                            //if(ox>=x && ox<=x+w && oy>=y && oy<=y+h)
                            {
                                if(!obj.selected)
                                {                                                
                                    _this.selectObj(obj);
                                }
                            }else
                            {
                                if(!_this.cmdkey)
                                {
                                    if(obj.selected)
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
                if(!_this.onmousedown(evt))return;
                
                //SelectBox
                if(_this.svg.dragObject===null)
                {
                    var evtX = _this.getEventX(evt),
                        evtY = _this.getEventY(evt);
                
                    if(!_this.cmdkey)_this.unSelectAll();
                    
                    _this.selectBox = _this.selectBox || document.createElementNS(_this.svgNS,"rect");
                    _this.selectBox.setAttributeNS(null,"class","selectBox");
                    _this.selectBox.setAttributeNS(null,"x",evtX);
                    _this.selectBox.setAttributeNS(null,"y",evtY);
                    _this.selectBox.setAttributeNS(null,"width",0);
                    _this.selectBox.setAttributeNS(null,"height",0);
                    //_this.selectBox.setAttributeNS(null,"stroke-dasharray","4,4");
                    _this.svg.appendChild(_this.selectBox);
                    _this.svg.dragOffsetX = evtX;
                    _this.svg.dragOffsetY = evtY;
                }
                evt.preventDefault();
            };                         

            _this.svg.onmouseup=function(evt)
            {
                if(!_this.onmouseup(evt))return;
                
                var dragObject = _this.svg.dragObject;
                //Drop
                if(dragObject!==null)
                {
                    //alert(_this.selected);
                    //_this.selected
                    //desc(_this.svg.childNodes[0],true);
                    var droped=false,
                        selected = _this.selected || [],
                        i;
                    
                    var nodes = _this.svg.childNodes;
                    for (i = nodes.length; i-- && i>=0;)
                    {
                        var obj = nodes[i];
                        if(obj && obj.contents)
                        {   
                            if(obj===dragObject)
                            {
                                for (;i-- && i>=0;)
                                {
                                    obj = nodes[i];
                                    if(obj.hidden===false && obj.inBounds && obj.inBounds(dragObject.getX(), dragObject.getY()))
                                    {
                                        if(selected.indexOf(obj) < 0)
                                        {
                                            obj.onDropObjects(selected);
                                            i = 0;
                                            droped=true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(!droped)
                    {
                        for (i = selected.length; i--;)
                        {
                            if(selected.indexOf(selected[i].parent) < 0)
                            {
                                selected[i].setParent(null);
                            }
                        }
                    }
                    
                    if(_this.snap2Grid)
                    {
                        for (i = selected.length; i--;)
                        {
                            selected[i].snap2Grid();
                        }                          
                        _this.updateResizeBox();
                    }                                                            
                }
                
                _this.svg.dragObject=null;
                _this.svg.resizeObject=null;

                //SelectBox
                if(_this.selectBox!==null)
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
            var _this=ToolKit,
                i,
                resizeBox = _this.resizeBox || [];
        
            for(i=0; i < resizeBox.length; i++)
            {
                resizeBox[i].update();
            }
        },

        createResizeBox:function(obj, ix, iy, cursor)
        {
            var _this = ToolKit,
                b = document.createElementNS(_this.svgNS,"use");
        
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
            var _this = ToolKit,
                constructor = function() {
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
            var _this = ToolKit;
            
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
            if (_this.tooltip !== null) {
                _this.tooltip.hide();
                //_this.svg.removeChild(_this.tooltip);
            }
        },

        showResizeBoxes:function()
        {
            var _this=ToolKit;
            if (_this.selected.length === 1) {
                var obj = _this.selected[0].resizeable && _this.selected[0];
                if (obj) {
                    _this.createResizeBox(obj,-1,-1,"nw-resize");
                    _this.createResizeBox(obj,0,-1,"n-resize");
                    _this.createResizeBox(obj,1,-1,"ne-resize");
                    _this.createResizeBox(obj,-1,0,"w-resize");
                    _this.createResizeBox(obj,1,0,"e-resize");
                    _this.createResizeBox(obj,-1,1,"sw-resize");
                    _this.createResizeBox(obj,0,1,"s-resize");
                    _this.createResizeBox(obj,1,1,"se-resize");
                }
            }
        },
                
        hideResizeBoxes:function()
        {
            var _this=ToolKit,
                svg = _this.svg;
            while((o=_this.resizeBox.pop())!=null)
            {
                svg.removeChild(o);
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
            if((evt.keyCode===8 && evt.which===8) || evt.keyCode===46 && evt.which===46)
            //if(evt.keyCode==32 && evt.which==32)
            {
                try
                {
                    var selected = _this.selected,
                        i;
                    for (i = selected.length; i--;) 
                    {
                        selected[i].remove();
                    }                             
                    _this.unSelectAll();
                }catch(e){console.log(e);};
                _this.stopPropagation(evt);
            }else if((evt.keyCode===91 && evt.which===91) || (evt.keyCode===224 && evt.which===224))
            {
                _this.cmdkey=true;
                _this.stopPropagation(evt);
            }else if(evt.keyCode===17 && evt.which===17)
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
            }else if(evt.keyCode===65 && evt.which===65)
            {
                if(_this.cmdkey)
                {
                    var contents = _this.contents,
                        i;
                    for (i = contents.length; i--;) 
                    {
                        if(!contents[i].hidden && contents[i].canSelect)
                        {
                            _this.selectObj(contents[i]);
                        }
                    }
                }
            }
        },

        keyup:function(evt)
        {
            //alert("hola");
            var _this=ToolKit;
            if((evt.keyCode===91 && evt.which===91) || (evt.keyCode===224 && evt.which===224))
            {
                _this.cmdkey=false;
                _this.stopPropagation(evt);
            }else if(evt.keyCode===17 && evt.which===17)
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
            var _this = ToolKit,
                obj = document.createElementNS(_this.svgNS,"path");
        
            obj.autoAdjust = false;
            obj.lineHandlers = [];

            obj.fixed=false;
            obj.fromObject=null;
            obj.toObject=null;
            
            obj.setAttributeNS(null, "d", "M0 0");
            if (dash_array && dash_array !== null) {
                obj.setAttributeNS(null, "stroke-dasharray", dash_array);
            }
            if (marker_start && marker_start !== null) {
                obj.setAttributeNS(null, "marker-start", "url(#"+marker_start+")");
            }
            if (marker_mid && marker_mid !== null) {
                obj.setAttributeNS(null, "marker-mid", "url(#"+marker_mid+")");
            }
            if (marker_end && marker_end !== null) {
                obj.setAttributeNS(null, "marker-end", "url(#"+marker_end+")");
            }
            if (styleClass && styleClass !== null) {
                obj.setAttributeNS(null, "class", styleClass);
            }
            //obj.resizeable=false;
            //desc(obj, true);
            obj.addPoint = function (x,y) {
                var seg = obj.createSVGPathSegLinetoAbs(x, y);
                obj.pathSegList.appendItem(seg);
            };
            
            obj.setStartPoint=function(x,y) {
                obj.setPoint(0,x,y);
            };
                    
            obj.setEndPoint=function(x,y) {
                obj.setPoint(obj.pathSegList.numberOfItems-1,x,y);
            };
            
            obj.setPoint = function(p,x,y)
            {
                //alert(p+" "+x+" "+y);
                obj.pathSegList.getItem(p).x=x;
                obj.pathSegList.getItem(p).y=y;
            };
                    
            obj.listSegments=function() {
                var segments = obj.pathSegList,
                    i;
                for (i = 0; i < segments.numberOfItems; i++) {
                    //desc(obj.pathSegList.getItem(i),true);
                    var segment = segments.getItem(i);
                    if (segment.pathSegType === SVGPathSeg.PATHSEG_LINETO_ABS) {
                       //console.log("lineto "+segment);
                    } else if (segment.pathSegType === SVGPathSeg.PATHSEG_MOVETO_ABS) {
                       //console.log("moveto "+segment);
                    }
                }
            };
            
            obj.translate=function(x,y) 
            {
                var segments = obj.pathSegList,
                    i;
                for (i = 0; i < segments.numberOfItems; i++) 
                {
                    var segment=segments.getItem(i);
                    segment.x=segment.x+x;
                    segment.y=segment.y+y;
                }                
            };
            
            obj.remove=function()
            {
                //remove fromObject
                var outConns = (obj.fromObject && obj.fromObject.outConnections) || [];
                var inConns = (obj.toObject && obj.toObject.inConnections) || [];
                
                ax = outConns.indexOf(obj);
                if(obj.fromObject!==null && ax !== -1) {
                    outConns.splice(ax, 1);
                }
                
                //remove toObject
                ax = inConns.indexOf(obj);
                if(obj.toObject!==null && ax !== -1) {
                    inConns.splice(ax, 1);
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
            return _this.createObject(function(){
                var obj = document.createElementNS(_this.svgNS,"rect"); //to create a circle, for rectangle use rectangle
                obj.resizeable=true;
                //obj.setAttributeNS(_this.xlink,"href",type);
                return obj;
            }, id, parent);
        },                    

        createUseObject:function(type, id, parent)
        {
            var _this=ToolKit;
            return _this.createObject(function(){
                var obj = document.createElementNS(_this.svgNS,"use"); //to create a circle, for rectangle use rectangle
                obj.setAttributeNS(_this.xlink,"href",type);
                return obj;
            }, id, parent);
        },

        createUseBaseObject:function(type, id, parent)
        {
            var _this=ToolKit;
            return _this.createBaseObject(function(){
                var obj = document.createElementNS(_this.svgNS,"use"); //to create a circle, for rectangle use rectangle
                obj.setAttributeNS(_this.xlink,"href",type);
                return obj;
            }, id, parent);
        },                    

        createBaseObject:function(constructor, id, parent)
        {
            var _this = ToolKit;
            var obj = constructor();
            
            obj.elementType="BaseObject";
            obj.text=null;
            obj.contents=[];                        
            obj.icons=[];      
            obj.inConnections=[];
            obj.outConnections=[];
            obj.canSelect=true;
            obj.hidden=false;
            obj.layer=_this.layer;

            if(id && id!==null)obj.setAttributeNS(null,"id",id);       
            
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
                var l = obj.getAttributeNS(_this.xlink,"href");
                if(l && l!==null)
                {
                    var o = document.getElementById(l.substring(1));
                    if(o !== null)
                    {
                        var s = o.getAttributeNS(null, param);
                        if(s && s !== null) { 
                            obj.setAttributeNS(null, "class", s);
                        }
                    }
                }else
                {
                    var s = obj.getAttributeNS(null, param);
                    if(s !== null) {
                        obj.setAttributeNS(null,"class",s);
                    }
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
            };

            obj.resize=function(w,h)
            {
                obj.setAttributeNS(null,"transform","translate("+(-w/2)+","+(-h/2)+")");
                obj.setWidth(w);
                obj.setHeight(h);        

                //Move Icons
                var icons = obj.icons,
                    newX = obj.getX(),
                    newY = obj.getY(),    
                    i;
            
                for (i = icons.length; i--;) 
                {
                    var icon = icons[i];
                    var pw = w / 2 * icon.posx;
                    var ph = h / 2 * icon.posy;
                    icon.obj.move(newX + pw + icon.offx, newY + ph + icon.offy);
                }

                //Cambiar tamaño del texto
                if(obj.text && obj.text!==null)obj.text.update();      
                
                //Move InConnections
                var inConnections = obj.inConnections,
                    outConnections = obj.outConnections,
                    i;
                
                for(i = inConnections.length; i--;)
                {
                    inConnections[i].updateInterPoints();
                }                
                
                //Move OutConnections
                for(i = outConnections.length; i--;)
                {
                    outConnections[i].updateInterPoints();
                }                 
            };                        

            obj.move=function(x,y)
            {
                var objW = obj.getWidth(),
                    objH = obj.getHeight(),
                    thisW = _this.getWidth(),
                    thisH = _this.getHeight(),
                    contents = obj.contents || [],
                    icons = obj.icons || [],
                    inConnections = obj.inConnections || [],
                    outConnections = obj.outConnections || [],
                    i;
                //Crecemos pantalla
                if(x + objW / 2 > thisW)
                {
                    _this.setWidth(x + objW / 2);
                }
                if(y + objH / 2 > thisH)
                {
                    _this.setHeight(y + objH / 2);
                }
                
                if(obj.canSelect)
                {
                    //Validamos bordes
                    if(x - objW / 2 < 0)
                    {
                        x = objW / 2;
                    }
                    if(y - objH / 2 < 0)
                    {
                        y=objH / 2;
                    }                    
                }
                
                var offx=x-obj.getX();
                var offy=y-obj.getY();
                
                obj.setX(x);
                obj.setY(y);

                //Move Childs
                for (i = contents.length; i--;) 
                {
                    if(!contents[i].selected)
                    {
                        contents[i].traslate(offx,offy);
                    }
                }  
                //Move Icons
                for (i = icons.length; i--;) 
                {
                    icons[i].obj.traslate(offx,offy);
                }

                //Move Text
                if(obj.text && obj.text!==null)
                {
                    //obj.text.traslate(offx,offy);
                    obj.text.PX=offx;
                    obj.text.PY=offy;
                    obj.text.update();
                    var spans = obj.text.childNodes,
                        _text = obj.text;
                    
                    for (i = spans.length; i--;)
                    {
                        spans[i].setAttributeNS(null,"x",_text.getX());
                    }
                }
                
                //Move InConnections
                for(i = inConnections.length; i--;)
                {
                    inConnections[i].setEndPoint(x,y);
                }                
                
                //Move OutConnections
                for(i = outConnections.length; i--;)
                {
                    outConnections[i].setStartPoint(x,y);
                }
            };    

            obj.remove = function(all) {
                if(!all)
                {
                    //En algunos casos, el parent es nulo, es decir, el canvas
                    var parent = obj.parent;
                    if (parent && parent !== null) 
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
                
                var contents = obj.contents || [],
                    icons = obj.icons || [],
                    inConnections = obj.inConnections || [],
                    outConnections = obj.outConnections || [],
                    i;
            
                for (i = contents.length; i--;)
                {
                    contents[i].remove(true);
                }
                
                //Elimina Iconos
                for (i = icons.length; i--;)
                {
                    icons[i].obj.remove();
                }

                //Elimina Texto
                if(obj.text && obj.text!==null)obj.text.remove();
                
                //Eliminar Conexiones
                //Move InConnections
                for(i = inConnections.length; i--;)
                {
                    inConnections[i].remove();
                }                
                
                //Move OutConnections
                for(i = outConnections.length; i--;)
                {
                    outConnections[i].remove();
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
                
                var contents = obj.contents || [],
                    icons = obj.icons || [],
                    inConnections = obj.inConnections || [],
                    outConnections = obj.outConnections || [],
                    i;
            
                for (i = contents.length; i--;) {
                    contents[i].hide();
                }
                
                //Elimina Iconos
                for (i = icons.length; i--;) {
                    icons[i].obj.hide();
                }
                //Elimina Texto
                if(obj.text && obj.text!==null)obj.text.hide();
                
                //Eliminar Conexiones
                //Move InConnections
                for(i = inConnections.length; i--;) {
                    inConnections[i].hide();
                }                
                
                //Move OutConnections
                for(i = outConnections.length; i--;) {
                    outConnections[i].hide();
                }                  
                
                //_this.unSelectObj(obj);
                if(_this.selected.indexOf(obj)>-1) {
                    _this.hideResizeBoxes();               
                }
                return this;
            };     
            
            obj.show = function(all) {
                
                obj.style.display="";
                obj.hidden=false;
                var contents = obj.contents || [],
                    icons = obj.icons || [],
                    inConnections = obj.inConnections || [],
                    outConnections = obj.outConnections || [],
                    i;
                
                for (i = contents.length; i--;) {
                    contents[i].show();
                }
                
                //Elimina Iconos
                for (i = icons.length; i--;) {
                    icons[i].obj.show();
                }
                //Elimina Texto
                if(obj.text && obj.text!==null)obj.text.show();
                
                //Eliminar Conexiones
                //Move InConnections
                for(i = inConnections.length; i--;) {
                    inConnections[i].show();
                }                
                
                //Move OutConnections
                for(i = outConnections.length; i--;) {
                    outConnections[i].show();
                }                  
                return this;
            };               
            
            //Mueve el elemento al primer plano
            obj.moveFirst = function()
            {
                //alert("obj:"+obj.icons.length);
                _this.svg.appendChild(obj);
                var icons = obj.icons || [],
                    contents = obj.contents || [],
                    inConnections = obj.inConnections || [],
                    outConnections = obj.outConnections || [],
                    i;
                
                //mueve Iconos
                for (i = icons.length; i--;)
                {
                    icons[i].obj.moveFirst();
                }
                
                //mueve texto
                if(obj.text && obj.text!==null)obj.text.moveFirst();
                
                //mueve contenidos
                for (i = contents.length; i--;)
                {
                    contents[i].moveFirst();
                }
                
                //mueve conexiones
                for (i = inConnections.length; i--;)
                {
                    inConnections[i].moveFirst();
                }
                
                for (i = outConnections.length; i--;)
                {
                    outConnections[i].moveFirst();
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
                if(obj.parent!==null)
                {
                    if(obj.parent===parent)return true;
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
                if(obj.text && obj.text!==null)obj.text.remove();
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
                    if(objs.indexOf(objs[i].parent)<0 && objs[i].canAttach(obj))
                    {
                        objs[i].setParent(obj);
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
                    connectionPath.layer = obj.layer;
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
            };
            
            obj.addOutConnection=function(connectionPath)
            {
                if (obj.outConnections.indexOf(connectionPath) === -1) {
                    connectionPath.layer = obj.layer;
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
            };
            
            obj.snap2Grid=function()
            {
                if(ToolKit.snap2Grid)
                {
                    obj.move(Math.round(obj.getX()/_this.snap2GridSize)*_this.snap2GridSize,Math.round(obj.getY()/_this.snap2GridSize)*_this.snap2GridSize);
                }
            };
            
            obj.setParent(parent);
            if(_this.svg)
            {
                _this.svg.appendChild(obj);
            }
            if(obj.getWidth()===0 && obj.getHeight()===0)
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
                if(down)
                {
                    var i=_this.selected.indexOf(obj);
                    if(i<0)
                    {
                        if(!_this.cmdkey) //deselect all
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
                    if(_this.cmdkey && _this.selected.unselect) //unselect one
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
            };

            obj.mouseclick=function(evt)
            {
                //Sobre escribir
                return true;
            };
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
                if(txt && txt!==null)
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
                
                if(obj.textO===2) {
                    text_element.setAttributeNS(null, "transform", "rotate(-90,"+text_element.getX()+","+text_element.getY()+")");
                    //text_element.setAttributeNS(null, "style", "writing-mode:tb");
                }
            };

            return obj;
        }
    };
