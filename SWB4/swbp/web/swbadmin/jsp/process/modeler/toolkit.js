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
            //desc(document.body,true);

            _this.svg.onmousemove=function(evt)
            {
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
                var ret=_this.onmousedown(evt);
                
                if(ret==false)return;
                
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
                        _this.svg.appendChild(_this.selectBox);
                        _this.svg.dragOffsetX=_this.getEventX(evt);
                        _this.svg.dragOffsetY=_this.getEventY(evt);                                    
                    }
                }
                
            };                         

            _this.svg.onmouseup=function(evt)
            {
                var ret=_this.onmouseup(evt);
                if(ret==false)return;
                
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

        selectObj:function(obj)
        {
            var _this=ToolKit;
            _this.selected.push(obj);
            obj.selected=true;   
            obj.setOverClass();
            _this.showResizeBoxes();
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
                
                //Elimina Iconos
                if(obj.icons)
                {
                    for (var i = obj.icons.length; i--;)
                    {
                        obj.icons[i].obj.remove();
                    }
                }
                //Elimina Texto
                if(obj.text && obj.text!=null)obj.text.remove();
                
                try
                {
                    _this.svg.removeChild(obj);
                }catch(noe){}
                
                _this.unSelectObj(obj);
                _this.showResizeBoxes();
                
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
                iobj.onmouseup=function(evt)
                {
                    obj.onmouseup(evt);
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
            var _this=ToolKit;
            var obj=_this.createBaseObject(constructor, id, parent);

            obj.onmousedown=function(evt)
            {
                obj.startX=_this.getEventX(evt);
                obj.startY=_this.getEventY(evt);
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
            };

            obj.onmouseup=function(evt)
            {
                _this=ToolKit;
                obj.mouseup(evt);
                obj.select(false);

                //if(_this.selected.length==1 && obj.startX==_this.getEventX(evt) && obj.startY==_this.getEventY(evt))
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
                //_this.stopPropagation(evt);
            }; 
            
            obj.onmouseup=function(evt)
            {
                //desc(evt,true);
                parent.onmouseup(evt);
                //_this.stopPropagation(evt);
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
        }
    };
