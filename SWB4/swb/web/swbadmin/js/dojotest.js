/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/
if(!dojo._hasResource["dijit.layout.LayoutContainer"]){
    dojo._hasResource["dijit.layout.LayoutContainer"]=true;
    dojo.provide("dijit.layout.LayoutContainer");
    dojo.require("dijit.layout._LayoutWidget");
    dojo.declare("dijit.layout.LayoutContainer",dijit.layout._LayoutWidget,{
        baseClass:"dijitLayoutContainer",
        constructor:function(){
            dojo.deprecated("dijit.layout.LayoutContainer is deprecated","use BorderContainer instead",2);
        },
        layout:function(){
            dijit.layout.layoutChildren(this.domNode,this._contentBox,this.getChildren());
        },
        addChild:function(_1,_2){
            this.inherited(arguments);
            if(this._started){
                dijit.layout.layoutChildren(this.domNode,this._contentBox,this.getChildren());
            }
        },
        removeChild:function(_3){
            this.inherited(arguments);
            if(this._started){
                dijit.layout.layoutChildren(this.domNode,this._contentBox,this.getChildren());
            }
        }
    });
    dojo.extend(dijit._Widget,{
        layoutAlign:"none"
    });
}



/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/
if(!dojo._hasResource["dijit.layout.TabContainer"]){
    dojo._hasResource["dijit.layout.TabContainer"]=true;
    dojo.provide("dijit.layout.TabContainer");
    dojo.require("dijit.layout.StackContainer");
    dojo.require("dijit._Templated");
    dojo.requireLocalization("dijit","common",null,"zh,ca,pt,da,tr,ru,de,sv,ja,he,fi,nb,el,ar,ROOT,pt-pt,cs,fr,es,ko,nl,zh-tw,pl,th,it,hu,sk,sl");
    dojo.declare("dijit.layout.TabContainer",[dijit.layout.StackContainer,dijit._Templated],{
        tabPosition:"top",
        baseClass:"dijitTabContainer",
        tabStrip:false,
        templateString:null,
        templateString:"<div class=\"dijitTabContainer\">\n\t<div dojoAttachPoint=\"tablistNode\"></div>\n\t<div dojoAttachPoint=\"tablistSpacer\" class=\"dijitTabSpacer ${baseClass}-spacer\"></div>\n\t<div class=\"dijitTabPaneWrapper ${baseClass}-container\" dojoAttachPoint=\"containerNode\"></div>\n</div>\n",
        _controllerWidget:"dijit.layout.TabController",
        postMixInProperties:function(){
            this.baseClass+=this.tabPosition.charAt(0).toUpperCase()+this.tabPosition.substr(1).replace(/-.*/,"");
            this.inherited(arguments);
        },
        postCreate:function(){
            this.inherited(arguments);
            var _1=dojo.getObject(this._controllerWidget);
            this.tablist=new _1({
                id:this.id+"_tablist",
                tabPosition:this.tabPosition,
                doLayout:this.doLayout,
                containerId:this.id,
                "class":this.baseClass+"-tabs"+(this.doLayout?"":" dijitTabNoLayout")
            },this.tablistNode);
            if(this.tabStrip){
                dojo.addClass(this.tablist.domNode,this.baseClass+"Strip");
            }
        },
        _setupChild:function(_2){
            dojo.addClass(_2.domNode,"dijitTabPane");
            this.inherited(arguments);
            return _2;
        },
        startup:function(){
            if(this._started){
                return;
            }
            this.tablist.startup();
            this.inherited(arguments);
        },
        layout:function(){
            if(!this.doLayout){
                return;
            }
            var _3=this.tabPosition.replace(/-h/,"");
            var _4=[{
                domNode:this.tablist.domNode,
                layoutAlign:_3
            },{
                domNode:this.tablistSpacer,
                layoutAlign:_3
            },{
                domNode:this.containerNode,
                layoutAlign:"client"
            }];
            dijit.layout.layoutChildren(this.domNode,this._contentBox,_4);
            this._containerContentBox=dijit.layout.marginBox2contentBox(this.containerNode,_4[2]);
            if(this.selectedChildWidget){
                this._showChild(this.selectedChildWidget);
                if(this.doLayout&&this.selectedChildWidget.resize){
                    this.selectedChildWidget.resize(this._containerContentBox);
                }
            }
        },
        destroy:function(){
            if(this.tablist){
                this.tablist.destroy();
            }
            this.inherited(arguments);
        }
    });
    dojo.declare("dijit.layout.TabController",dijit.layout.StackController,{
        templateString:"<div wairole='tablist' dojoAttachEvent='onkeypress:onkeypress'></div>",
        tabPosition:"top",
        doLayout:true,
        buttonWidget:"dijit.layout._TabButton",
        _rectifyRtlTabList:function(){
            if(0>=this.tabPosition.indexOf("-h")){
                return;
            }
            if(!this.pane2button){
                return;
            }
            var _5=0;
            for(var _6 in this.pane2button){
                var ow=this.pane2button[_6].innerDiv.scrollWidth;
                _5=Math.max(_5,ow);
            }
            for(_6 in this.pane2button){
                this.pane2button[_6].innerDiv.style.width=_5+"px";
            }
        }
    });
    dojo.declare("dijit.layout._TabButton",dijit.layout._StackButton,{
        baseClass:"dijitTab",
        templateString:"<div waiRole=\"presentation\" dojoAttachEvent='onclick:onClick,onmouseenter:_onMouse,onmouseleave:_onMouse'>\n    <div waiRole=\"presentation\" class='dijitTabInnerDiv' dojoAttachPoint='innerDiv'>\n        <div waiRole=\"presentation\" class='dijitTabContent' dojoAttachPoint='tabContent'>\n\t        <span dojoAttachPoint='containerNode,focusNode' class='tabLabel'>${!label}</span><img class =\"dijitTabButtonSpacer\" src=\"${_blankGif}\" />\n\t        <div class=\"dijitInline closeNode\" dojoAttachPoint='closeNode' dojoAttachEvent='onclick:onClickCloseButton'>\n\t        \t<img src=\"${_blankGif}\" alt=\"\" dojoAttachPoint='closeButtonNode' class='closeImage' dojoAttachEvent='onmouseenter:_onMouse, onmouseleave:_onMouse' stateModifier='CloseButton' waiRole=\"presentation\"/>\n\t            <span dojoAttachPoint='closeText' class='closeText'>x</span>\n\t        </div>\n        </div>\n    </div>\n</div>\n",
        _scroll:false,
        postCreate:function(){
            if(this.closeButton){
                dojo.addClass(this.innerDiv,"dijitClosable");
                var _8=dojo.i18n.getLocalization("dijit","common");
                if(this.closeNode){
                    dojo.attr(this.closeNode,"title",_8.itemClose);
                    dojo.attr(this.closeButtonNode,"title",_8.itemClose);
                }
            }else{
                this.closeNode.style.display="none";
            }
            this.inherited(arguments);
            dojo.setSelectable(this.containerNode,false);
        }
    });
}



/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/
if(!dojo._hasResource["dijit.TitlePane"]){
    dojo._hasResource["dijit.TitlePane"]=true;
    dojo.provide("dijit.TitlePane");
    dojo.require("dojo.fx");
    dojo.require("dijit._Templated");
    dojo.require("dijit.layout.ContentPane");
    dojo.declare("dijit.TitlePane",[dijit.layout.ContentPane,dijit._Templated],{
        title:"",
        open:true,
        duration:dijit.defaultDuration,
        baseClass:"dijitTitlePane",
        templateString:"<div class=\"${baseClass}\">\n\t<div dojoAttachEvent=\"onclick:toggle,onkeypress: _onTitleKey,onfocus:_handleFocus,onblur:_handleFocus\" tabindex=\"0\"\n\t\t\twaiRole=\"button\" class=\"dijitTitlePaneTitle\" dojoAttachPoint=\"titleBarNode,focusNode\">\n\t\t<img src=\"${_blankGif}\" alt=\"\" dojoAttachPoint=\"arrowNode\" class=\"dijitArrowNode\" waiRole=\"presentation\"\n\t\t><span dojoAttachPoint=\"arrowNodeInner\" class=\"dijitArrowNodeInner\"></span\n\t\t><span dojoAttachPoint=\"titleNode\" class=\"dijitTitlePaneTextNode\"></span>\n\t</div>\n\t<div class=\"dijitTitlePaneContentOuter\" dojoAttachPoint=\"hideNode\">\n\t\t<div class=\"dijitReset\" dojoAttachPoint=\"wipeNode\">\n\t\t\t<div class=\"dijitTitlePaneContentInner\" dojoAttachPoint=\"containerNode\" waiRole=\"region\" tabindex=\"-1\">\n\t\t\t\t<!-- nested divs because wipeIn()/wipeOut() doesn't work right on node w/padding etc.  Put padding on inner div. -->\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</div>\n",
        attributeMap:dojo.mixin(dojo.clone(dijit.layout.ContentPane.prototype.attributeMap),{
            title:{
                node:"titleNode",
                type:"innerHTML"
            }
        }),
        postCreate:function(){
            if(!this.open){
                this.hideNode.style.display=this.wipeNode.style.display="none";
            }
            this._setCss();
            dojo.setSelectable(this.titleNode,false);
            dijit.setWaiState(this.containerNode,"labelledby",this.titleNode.id);
            dijit.setWaiState(this.focusNode,"haspopup","true");
            var _1=this.hideNode,_2=this.wipeNode;
            this._wipeIn=dojo.fx.wipeIn({
                node:this.wipeNode,
                duration:this.duration,
                beforeBegin:function(){
                    _1.style.display="";
                }
            });
            this._wipeOut=dojo.fx.wipeOut({
                node:this.wipeNode,
                duration:this.duration,
                onEnd:function(){
                    _1.style.display="none";
                }
            });
            this.inherited(arguments);
        },
        _setOpenAttr:function(_3){
            if(this.open!==_3){
                this.toggle();
            }
        },
        _setContentAttr:function(_4){
            if(!this.open||!this._wipeOut||this._wipeOut.status()=="playing"){
                this.inherited(arguments);
            }else{
                if(this._wipeIn&&this._wipeIn.status()=="playing"){
                    this._wipeIn.stop();
                }
                dojo.marginBox(this.wipeNode,{
                    h:dojo.marginBox(this.wipeNode).h
                });
                this.inherited(arguments);
                if(this._wipeIn){
                    this._wipeIn.play();
                }else{
                    this.hideNode.style.display="";
                }
            }
        },
        toggle:function(){
            dojo.forEach([this._wipeIn,this._wipeOut],function(_5){
                if(_5&&_5.status()=="playing"){
                    _5.stop();
                }
            });
            var _6=this[this.open?"_wipeOut":"_wipeIn"];
            if(_6){
                _6.play();
            }else{
                this._hideNode.style.display=this.open?"":"none";
            }
            this.open=!this.open;
            this._loadCheck();
            this._setCss();
        },
        _setCss:function(){
            var _7=["dijitClosed","dijitOpen"];
            var _8=this.open;
            var _9=this.titleBarNode||this.focusNode;
            dojo.removeClass(_9,_7[!_8+0]);
            _9.className+=" "+_7[_8+0];
            this.arrowNodeInner.innerHTML=this.open?"-":"+";
        },
        _onTitleKey:function(e){
            if(e.charOrCode==dojo.keys.ENTER||e.charOrCode==" "){
                this.toggle();
            }else{
                if(e.charOrCode==dojo.keys.DOWN_ARROW&&this.open){
                    this.containerNode.focus();
                    e.preventDefault();
                }
            }
        },
        _handleFocus:function(e){
            dojo[(e.type=="focus"?"addClass":"removeClass")](this.focusNode,this.baseClass+"Focused");
        },
        setTitle:function(_c){
            dojo.deprecated("dijit.TitlePane.setTitle() is deprecated.  Use attr('title', ...) instead.","","2.0");
            this.titleNode.innerHTML=_c;
        }
    });
}

/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/
if(!dojo._hasResource["dijit._tree.dndSource"]){
    dojo._hasResource["dijit._tree.dndSource"]=true;
    dojo.provide("dijit._tree.dndSource");
    dojo.require("dijit._tree.dndSelector");
    dojo.require("dojo.dnd.Manager");
    dojo.declare("dijit._tree.dndSource",dijit._tree.dndSelector,{
        isSource:true,
        copyOnly:false,
        skipForm:false,
        dragThreshold:0,
        accept:["text"],
        constructor:function(_1,_2){
            if(!_2){
                _2={};
            }
            dojo.mixin(this,_2);
            this.isSource=typeof _2.isSource=="undefined"?true:_2.isSource;
            var _3=_2.accept instanceof Array?_2.accept:["text"];
            this.accept=null;
            if(_3.length){
                this.accept={};
                for(var i=0;i<_3.length;++i){
                    this.accept[_3[i]]=1;
                }
            }
            this.isDragging=false;
            this.mouseDown=false;
            this.targetAnchor=null;
            this.targetBox=null;
            this.before=true;
            this._lastX=0;
            this._lastY=0;
            this.sourceState="";
            if(this.isSource){
                dojo.addClass(this.node,"dojoDndSource");
            }
            this.targetState="";
            if(this.accept){
                dojo.addClass(this.node,"dojoDndTarget");
            }
            if(this.horizontal){
                dojo.addClass(this.node,"dojoDndHorizontal");
            }
            this.topics=[dojo.subscribe("/dnd/source/over",this,"onDndSourceOver"),dojo.subscribe("/dnd/start",this,"onDndStart"),dojo.subscribe("/dnd/drop",this,"onDndDrop"),dojo.subscribe("/dnd/cancel",this,"onDndCancel")];
        },
        startup:function(){
        },
        checkAcceptance:function(_5,_6){
            return true;
        },
        copyState:function(_7){
            return this.copyOnly||_7;
        },
        destroy:function(){
            this.inherited("destroy",arguments);
            dojo.forEach(this.topics,dojo.unsubscribe);
            this.targetAnchor=null;
        },
        markupFactory:function(_8,_9){
            _8._skipStartup=true;
            return new dijit._tree.dndSource(_9,_8);
        },
        onMouseMove:function(e){
            if(this.isDragging&&this.targetState=="Disabled"){
                return;
            }
            this.inherited("onMouseMove",arguments);
            var m=dojo.dnd.manager();
            if(this.isDragging){
                if(this.allowBetween){
                    var _c=false;
                    if(this.current){
                        if(!this.targetBox||this.targetAnchor!=this.current){
                            this.targetBox={
                                xy:dojo.coords(this.current,true),
                                w:this.current.offsetWidth,
                                h:this.current.offsetHeight
                            };
                        }
                        if(this.horizontal){
                            _c=(e.pageX-this.targetBox.xy.x)<(this.targetBox.w/2);
                        }else{
                            _c=(e.pageY-this.targetBox.xy.y)<(this.targetBox.h/2);
                        }
                    }
                    if(this.current!=this.targetAnchor||_c!=this.before){
                        this._markTargetAnchor(_c);
                        m.canDrop(!this.current||m.source!=this||!(this.current.id in this.selection));
                    }
                }
            }else{
                if(this.mouseDown&&this.isSource&&(Math.abs(e.pageX-this._lastX)>=this.dragThreshold||Math.abs(e.pageY-this._lastY)>=this.dragThreshold)){
                    var n=this.getSelectedNodes();
                    var _e=[];
                    for(var i in n){
                        _e.push(n[i]);
                    }
                    if(_e.length){
                        m.startDrag(this,_e,this.copyState(dojo.dnd.getCopyKeyState(e)));
                    }
                }
            }
        },
        onMouseDown:function(e){
            this.mouseDown=true;
            this.mouseButton=e.button;
            this._lastX=e.pageX;
            this._lastY=e.pageY;
            this.inherited("onMouseDown",arguments);
        },
        onMouseUp:function(e){
            if(this.mouseDown){
                this.mouseDown=false;
                this.inherited("onMouseUp",arguments);
            }
        },
        onMouseOver:function(e){
            var rt=e.relatedTarget;
            while(rt){
                if(rt==this.node){
                    break;
                }
                try{
                    rt=rt.parentNode;
                }
                catch(x){
                    rt=null;
                }
            }
            if(!rt){
                this._changeState("Container","Over");
                this.onOverEvent();
            }
            var n=this._getChildByEvent(e);
            if(this.current==n){
                return;
            }
            if(this.current){
                this._removeItemClass(this.current,"Over");
            }
            var m=dojo.dnd.manager();
            if(n){
                this._addItemClass(n,"Over");
                if(this.isDragging){
                    if(this.checkItemAcceptance(n,m.source)){
                        m.canDrop(this.targetState!="Disabled"&&(!this.current||m.source!=this||!(n in this.selection)));
                    }
                }
            }else{
                if(this.isDragging){
                    m.canDrop(false);
                }
            }
            this.current=n;
        },
        checkItemAcceptance:function(_16,_17){
            return true;
        },
        onDndSourceOver:function(_18){
            if(this!=_18){
                this.mouseDown=false;
                if(this.targetAnchor){
                    this._unmarkTargetAnchor();
                }
            }else{
                if(this.isDragging){
                    var m=dojo.dnd.manager();
                    m.canDrop(false);
                }
            }
        },
        onDndStart:function(_1a,_1b,_1c){
            if(this.isSource){
                this._changeState("Source",this==_1a?(_1c?"Copied":"Moved"):"");
            }
            var _1d=this.checkAcceptance(_1a,_1b);
            this._changeState("Target",_1d?"":"Disabled");
            if(_1d){
                dojo.dnd.manager().overSource(this);
            }
            this.isDragging=true;
        },
        itemCreator:function(_1e){
            return dojo.map(_1e,function(_1f){
                return {
                    "id":_1f.id,
                    "name":_1f.textContent||_1f.innerText||""
                };
            });
        },
        onDndDrop:function(_20,_21,_22){
            if(this.containerState=="Over"){
                var _23=this.tree,_24=_23.model,_25=this.current,_26=false;
                this.isDragging=false;
                var _27=dijit.getEnclosingWidget(_25),_28=(_27&&_27.item)||_23.item;
                var _29;
                if(_20!=this){
                    _29=this.itemCreator(_21,_25);
                }
                dojo.forEach(_21,function(_2a,idx){
                    if(_20==this){
                        var _2c=dijit.getEnclosingWidget(_2a),_2d=_2c.item,_2e=_2c.getParent().item;
                        _24.pasteItem(_2d,_2e,_28,_22);
                    }else{
                        _24.newItem(_29[idx],_28);
                    }
                },this);
                this.tree._expandNode(_27);
            }
            this.onDndCancel();
        },
        onDndCancel:function(){
            if(this.targetAnchor){
                this._unmarkTargetAnchor();
                this.targetAnchor=null;
            }
            this.before=true;
            this.isDragging=false;
            this.mouseDown=false;
            delete this.mouseButton;
            this._changeState("Source","");
            this._changeState("Target","");
        },
        onOverEvent:function(){
            this.inherited("onOverEvent",arguments);
            dojo.dnd.manager().overSource(this);
        },
        onOutEvent:function(){
            this.inherited("onOutEvent",arguments);
            dojo.dnd.manager().outSource(this);
        },
        _markTargetAnchor:function(_2f){
            if(this.current==this.targetAnchor&&this.before==_2f){
                return;
            }
            if(this.targetAnchor){
                this._removeItemClass(this.targetAnchor,this.before?"Before":"After");
            }
            this.targetAnchor=this.current;
            this.targetBox=null;
            this.before=_2f;
            if(this.targetAnchor){
                this._addItemClass(this.targetAnchor,this.before?"Before":"After");
            }
        },
        _unmarkTargetAnchor:function(){
            if(!this.targetAnchor){
                return;
            }
            this._removeItemClass(this.targetAnchor,this.before?"Before":"After");
            this.targetAnchor=null;
            this.targetBox=null;
            this.before=true;
        },
        _markDndStatus:function(_30){
            this._changeState("Source",_30?"Copied":"Moved");
        }
    });
    dojo.declare("dijit._tree.dndTarget",dijit._tree.dndSource,{
        constructor:function(_31,_32){
            this.isSource=false;
            dojo.removeClass(this.node,"dojoDndSource");
        },
        markupFactory:function(_33,_34){
            _33._skipStartup=true;
            return new dijit._tree.dndTarget(_34,_33);
        }
    });
}








/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["dojo.dnd.Manager"]){
    dojo._hasResource["dojo.dnd.Manager"]=true;
    dojo.provide("dojo.dnd.Manager");
    dojo.require("dojo.dnd.common");
    dojo.require("dojo.dnd.autoscroll");
    dojo.require("dojo.dnd.Avatar");
    dojo.declare("dojo.dnd.Manager",null,{
        constructor:function(){
            this.avatar=null;
            this.source=null;
            this.nodes=[];
            this.copy=true;
            this.target=null;
            this.canDropFlag=false;
            this.events=[];
        },
        OFFSET_X:16,
        OFFSET_Y:16,
        overSource:function(_1){
            if(this.avatar){
                this.target=(_1&&_1.targetState!="Disabled")?_1:null;
                this.canDropFlag=Boolean(this.target);
                this.avatar.update();
            }
            dojo.publish("/dnd/source/over",[_1]);
        },
        outSource:function(_2){
            if(this.avatar){
                if(this.target==_2){
                    this.target=null;
                    this.canDropFlag=false;
                    this.avatar.update();
                    dojo.publish("/dnd/source/over",[null]);
                }
            }else{
                dojo.publish("/dnd/source/over",[null]);
            }
        },
        startDrag:function(_3,_4,_5){
            this.source=_3;
            this.nodes=_4;
            this.copy=Boolean(_5);
            this.avatar=this.makeAvatar();
            dojo.body().appendChild(this.avatar.node);
            dojo.publish("/dnd/start",[_3,_4,this.copy]);
            this.events=[dojo.connect(dojo.doc,"onmousemove",this,"onMouseMove"),dojo.connect(dojo.doc,"onmouseup",this,"onMouseUp"),dojo.connect(dojo.doc,"onkeydown",this,"onKeyDown"),dojo.connect(dojo.doc,"onkeyup",this,"onKeyUp"),dojo.connect(dojo.doc,"ondragstart",dojo.stopEvent),dojo.connect(dojo.body(),"onselectstart",dojo.stopEvent)];
            var c="dojoDnd"+(_5?"Copy":"Move");
            dojo.addClass(dojo.body(),c);
        },
        canDrop:function(_7){
            var _8=Boolean(this.target&&_7);
            if(this.canDropFlag!=_8){
                this.canDropFlag=_8;
                this.avatar.update();
            }
        },
        stopDrag:function(){
            dojo.removeClass(dojo.body(),"dojoDndCopy");
            dojo.removeClass(dojo.body(),"dojoDndMove");
            dojo.forEach(this.events,dojo.disconnect);
            this.events=[];
            this.avatar.destroy();
            this.avatar=null;
            this.source=this.target=null;
            this.nodes=[];
        },
        makeAvatar:function(){
            return new dojo.dnd.Avatar(this);
        },
        updateAvatar:function(){
            this.avatar.update();
        },
        onMouseMove:function(e){
            var a=this.avatar;
            if(a){
                dojo.dnd.autoScrollNodes(e);
                var s=a.node.style;
                s.left=(e.pageX+this.OFFSET_X)+"px";
                s.top=(e.pageY+this.OFFSET_Y)+"px";
                var _c=Boolean(this.source.copyState(dojo.dnd.getCopyKeyState(e)));
                if(this.copy!=_c){
                    this._setCopyStatus(_c);
                }
            }
        },
        onMouseUp:function(e){
            if(this.avatar&&(!("mouseButton" in this.source)||(dojo.isSafari&&dojo.dnd._isMac&&this.source.mouseButton==2?e.button==0:this.source.mouseButton==e.button))){
                if(this.target&&this.canDropFlag){
                    var _e=Boolean(this.source.copyState(dojo.dnd.getCopyKeyState(e))),_f=[this.source,this.nodes,_e,this.target];
                    dojo.publish("/dnd/drop/before",_f);
                    dojo.publish("/dnd/drop",_f);
                }else{
                    dojo.publish("/dnd/cancel");
                }
                this.stopDrag();
            }
        },
        onKeyDown:function(e){
            if(this.avatar){
                switch(e.keyCode){
                    case dojo.keys.CTRL:
                        var _11=Boolean(this.source.copyState(true));
                        if(this.copy!=_11){
                            this._setCopyStatus(_11);
                        }
                        break;
                    case dojo.keys.ESCAPE:
                        dojo.publish("/dnd/cancel");
                        this.stopDrag();
                        break;
                }
            }
        },
        onKeyUp:function(e){
            if(this.avatar&&e.keyCode==dojo.keys.CTRL){
                var _13=Boolean(this.source.copyState(false));
                if(this.copy!=_13){
                    this._setCopyStatus(_13);
                }
            }
        },
        _setCopyStatus:function(_14){
            this.copy=_14;
            this.source._markDndStatus(this.copy);
            this.updateAvatar();
            dojo.removeClass(dojo.body(),"dojoDnd"+(this.copy?"Move":"Copy"));
            dojo.addClass(dojo.body(),"dojoDnd"+(this.copy?"Copy":"Move"));
        }
    });
    dojo.dnd._manager=null;
    dojo.dnd.manager=function(){
        if(!dojo.dnd._manager){
            dojo.dnd._manager=new dojo.dnd.Manager();
        }
        return dojo.dnd._manager;
    };
}


/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["dijit.layout.ContentPane"]){
    dojo._hasResource["dijit.layout.ContentPane"]=true;
    dojo.provide("dijit.layout.ContentPane");
    dojo.require("dijit._Widget");
    dojo.require("dijit.layout._LayoutWidget");
    dojo.require("dojo.parser");
    dojo.require("dojo.string");
    dojo.require("dojo.html");
    dojo.requireLocalization("dijit","loading",null,"zh,ca,pt,da,tr,ru,ROOT,de,sv,ja,he,fi,nb,el,ar,pt-pt,cs,fr,es,ko,nl,zh-tw,pl,th,it,hu,sk,sl");
    dojo.declare("dijit.layout.ContentPane",dijit._Widget,{
        href:"",
        extractContent:false,
        parseOnLoad:true,
        preventCache:false,
        preload:false,
        refreshOnShow:false,
        loadingMessage:"<span class='dijitContentPaneLoading'>${loadingState}</span>",
        errorMessage:"<span class='dijitContentPaneError'>${errorState}</span>",
        isLoaded:false,
        baseClass:"dijitContentPane",
        doLayout:true,
        _isRealContent:true,
        postMixInProperties:function(){
            this.inherited(arguments);
            var _1=dojo.i18n.getLocalization("dijit","loading",this.lang);
            this.loadingMessage=dojo.string.substitute(this.loadingMessage,_1);
            this.errorMessage=dojo.string.substitute(this.errorMessage,_1);
        },
        buildRendering:function(){
            this.inherited(arguments);
            if(!this.containerNode){
                this.containerNode=this.domNode;
            }
        },
        postCreate:function(){
            this.domNode.title="";
            if(!dijit.hasWaiRole(this.domNode)){
                dijit.setWaiRole(this.domNode,"group");
            }
            dojo.addClass(this.domNode,this.baseClass);
        },
        startup:function(){
            if(this._started){
                return;
            }
            if(this.doLayout!="false"&&this.doLayout!==false){
                this._checkIfSingleChild();
                if(this._singleChild){
                    this._singleChild.startup();
                }
            }
            this._loadCheck();
            this.inherited(arguments);
        },
        _checkIfSingleChild:function(){
            var _2=dojo.query(">",this.containerNode),_3=_2.filter(function(_4){
                return dojo.hasAttr(_4,"dojoType")||dojo.hasAttr(_4,"widgetId");
            }),_5=dojo.filter(_3.map(dijit.byNode),function(_6){
                return _6&&_6.domNode&&_6.resize;
            });
            if(_2.length==_3.length&&_5.length==1){
                this.isContainer=true;
                this._singleChild=_5[0];
            }else{
                delete this.isContainer;
                delete this._singleChild;
            }
        },
        refresh:function(){
            return this._prepareLoad(true);
        },
        setHref:function(_7){
            dojo.deprecated("dijit.layout.ContentPane.setHref() is deprecated.\tUse attr('href', ...) instead.","","2.0");
            return this.attr("href",_7);
        },
        _setHrefAttr:function(_8){
            this.href=_8;
            if(this._created){
                return this._prepareLoad();
            }
        },
        setContent:function(_9){
            dojo.deprecated("dijit.layout.ContentPane.setContent() is deprecated.  Use attr('content', ...) instead.","","2.0");
            this.attr("content",_9);
        },
        _setContentAttr:function(_a){
            this.href="";
            this.cancel();
            this._setContent(_a||"");
            this._isDownloaded=false;
            if(this.doLayout!="false"&&this.doLayout!==false){
                this._checkIfSingleChild();
                if(this._singleChild&&this._singleChild.resize){
                    this._singleChild.startup();
                    var cb=this._contentBox||dojo.contentBox(this.containerNode);
                    this._singleChild.resize({
                        w:cb.w,
                        h:cb.h
                        });
                }
            }
        },
        _getContentAttr:function(){
            return this.containerNode.innerHTML;
        },
        cancel:function(){
            if(this._xhrDfd&&(this._xhrDfd.fired==-1)){
                this._xhrDfd.cancel();
            }
            delete this._xhrDfd;
        },
        destroyRecursive:function(_c){
            if(this._beingDestroyed){
                return;
            }
            this._beingDestroyed=true;
            this.inherited(arguments);
        },
        resize:function(_d){
            dojo.marginBox(this.domNode,_d);
            var _e=this.containerNode,mb=dojo.mixin(dojo.marginBox(_e),_d||{});
            var cb=this._contentBox=dijit.layout.marginBox2contentBox(_e,mb);
            if(this._singleChild&&this._singleChild.resize){
                this._singleChild.resize({
                    w:cb.w,
                    h:cb.h
                    });
            }
        },
        _prepareLoad:function(_11){
            this.cancel();
            this.isLoaded=false;
            this._loadCheck(_11);
        },
        _isShown:function(){
            if("open" in this){
                return this.open;
            }else{
                var _12=this.domNode;
                return (_12.style.display!="none")&&(_12.style.visibility!="hidden");
            }
        },
        _loadCheck:function(_13){
            var _14=this._isShown();
            if(this.href&&(_13||(this.preload&&!this.isLoaded&&!this._xhrDfd)||(this.refreshOnShow&&_14&&!this._xhrDfd)||(!this.isLoaded&&_14&&!this._xhrDfd))){
                this._downloadExternalContent();
            }
        },
        _downloadExternalContent:function(){
            this._setContent(this.onDownloadStart(),true);
            var _15=this;
            var _16={
                preventCache:(this.preventCache||this.refreshOnShow),
                url:this.href,
                handleAs:"text"
            };
            if(dojo.isObject(this.ioArgs)){
                dojo.mixin(_16,this.ioArgs);
            }
            var _17=this._xhrDfd=(this.ioMethod||dojo.xhrGet)(_16);
            _17.addCallback(function(_18){
                try{
                    _15._isDownloaded=true;
                    _15._setContent(_18,false);
                    _15.onDownloadEnd();
                }
                catch(err){
                    _15._onError("Content",err);
                }
                delete _15._xhrDfd;
                return _18;
            });
            _17.addErrback(function(err){
                if(!_17.cancelled){
                    _15._onError("Download",err);
                }
                delete _15._xhrDfd;
                return err;
            });
        },
        _onLoadHandler:function(_1a){
            this.isLoaded=true;
            try{
                this.onLoad(_1a);
            }
            catch(e){
                console.error("Error "+this.widgetId+" running custom onLoad code");
            }
        },
        _onUnloadHandler:function(){
            this.isLoaded=false;
            try{
                this.onUnload();
            }
            catch(e){
                console.error("Error "+this.widgetId+" running custom onUnload code");
            }
        },
        destroyDescendants:function(){
            if(this._isRealContent){
                this._onUnloadHandler();
            }
            var _1b=this._contentSetter;
            if(_1b){
                _1b.empty();
            }else{
                this.inherited(arguments);
                dojo.html._emptyNode(this.containerNode);
            }
        },
        _setContent:function(_1c,_1d){
            this.destroyDescendants();
            this._isRealContent=!_1d;
            var _1e=this._contentSetter;
            if(!(_1e&&_1e instanceof dojo.html._ContentSetter)){
                _1e=this._contentSetter=new dojo.html._ContentSetter({
                    node:this.containerNode,
                    _onError:dojo.hitch(this,this._onError),
                    onContentError:dojo.hitch(this,function(e){
                        var _20=this.onContentError(e);
                        try{
                            this.containerNode.innerHTML=_20;
                        }
                        catch(e){
                            console.error("Fatal "+this.id+" could not change content due to "+e.message,e);
                        }
                    })
                    });
            }
            var _21=dojo.mixin({
                cleanContent:this.cleanContent,
                extractContent:this.extractContent,
                parseContent:this.parseOnLoad
                },this._contentSetterParams||{});
            dojo.mixin(_1e,_21);
            _1e.set((dojo.isObject(_1c)&&_1c.domNode)?_1c.domNode:_1c);
            delete this._contentSetterParams;
            if(!_1d){
                this._onLoadHandler(_1c);
            }
        },
        _onError:function(_22,err,_24){
            var _25=this["on"+_22+"Error"].call(this,err);
            if(_24){
                console.error(_24,err);
            }else{
                if(_25){
                    this._setContent(_25,true);
                }
            }
        },
        _createSubWidgets:function(){
            try{
                dojo.parser.parse(this.containerNode,true);
            }
            catch(e){
                this._onError("Content",e,"Couldn't create widgets in "+this.id+(this.href?" from "+this.href:""));
            }
        },
        onLoad:function(_26){
        },
        onUnload:function(){
        },
        onDownloadStart:function(){
            return this.loadingMessage;
        },
        onContentError:function(_27){
        },
        onDownloadError:function(_28){
            return this.errorMessage;
        },
        onDownloadEnd:function(){
        }
        });
}




/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["dijit.layout.StackContainer"]){
    dojo._hasResource["dijit.layout.StackContainer"]=true;
    dojo.provide("dijit.layout.StackContainer");
    dojo.require("dijit._Templated");
    dojo.require("dijit.layout._LayoutWidget");
    dojo.require("dijit.form.Button");
    dojo.require("dijit.Menu");
    dojo.requireLocalization("dijit","common",null,"zh,ca,pt,da,tr,ru,de,sv,ja,he,fi,nb,el,ar,ROOT,pt-pt,cs,fr,es,ko,nl,zh-tw,pl,th,it,hu,sk,sl");
    dojo.declare("dijit.layout.StackContainer",dijit.layout._LayoutWidget,{
        doLayout:true,
        baseClass:"dijitStackContainer",
        _started:false,
        postCreate:function(){
            this.inherited(arguments);
            dijit.setWaiRole(this.containerNode,"tabpanel");
            this.connect(this.domNode,"onkeypress",this._onKeyPress);
        },
        startup:function(){
            if(this._started){
                return;
            }
            var _1=this.getChildren();
            dojo.forEach(_1,this._setupChild,this);
            dojo.some(_1,function(_2){
                if(_2.selected){
                    this.selectedChildWidget=_2;
                }
                return _2.selected;
            },this);
            var _3=this.selectedChildWidget;
            if(!_3&&_1[0]){
                _3=this.selectedChildWidget=_1[0];
                _3.selected=true;
            }
            if(_3){
                this._showChild(_3);
            }
            dojo.publish(this.id+"-startup",[{
                children:_1,
                selected:_3
            }]);
            this.inherited(arguments);
        },
        _setupChild:function(_4){
            this.inherited(arguments);
            _4.domNode.style.display="none";
            _4.domNode.style.position="relative";
            _4.domNode.title="";
            return _4;
        },
        addChild:function(_5,_6){
            this.inherited(arguments);
            if(this._started){
                dojo.publish(this.id+"-addChild",[_5,_6]);
                this.layout();
                if(!this.selectedChildWidget){
                    this.selectChild(_5);
                }
            }
        },
        removeChild:function(_7){
            this.inherited(arguments);
            if(this._beingDestroyed){
                return;
            }
            if(this._started){
                dojo.publish(this.id+"-removeChild",[_7]);
                this.layout();
            }
            if(this.selectedChildWidget===_7){
                this.selectedChildWidget=undefined;
                if(this._started){
                    var _8=this.getChildren();
                    if(_8.length){
                        this.selectChild(_8[0]);
                    }
                }
            }
        },
        selectChild:function(_9){
            _9=dijit.byId(_9);
            if(this.selectedChildWidget!=_9){
                this._transition(_9,this.selectedChildWidget);
                this.selectedChildWidget=_9;
                dojo.publish(this.id+"-selectChild",[_9]);
            }
        },
        _transition:function(_a,_b){
            if(_b){
                this._hideChild(_b);
            }
            this._showChild(_a);
            if(this.doLayout&&_a.resize){
                _a.resize(this._containerContentBox||this._contentBox);
            }
        },
        _adjacent:function(_c){
            var _d=this.getChildren();
            var _e=dojo.indexOf(_d,this.selectedChildWidget);
            _e+=_c?1:_d.length-1;
            return _d[_e%_d.length];
        },
        forward:function(){
            this.selectChild(this._adjacent(true));
        },
        back:function(){
            this.selectChild(this._adjacent(false));
        },
        _onKeyPress:function(e){
            dojo.publish(this.id+"-containerKeyPress",[{
                e:e,
                page:this
            }]);
        },
        layout:function(){
            if(this.doLayout&&this.selectedChildWidget&&this.selectedChildWidget.resize){
                this.selectedChildWidget.resize(this._contentBox);
            }
        },
        _showChild:function(_10){
            var _11=this.getChildren();
            _10.isFirstChild=(_10==_11[0]);
            _10.isLastChild=(_10==_11[_11.length-1]);
            _10.selected=true;
            _10.domNode.style.display="";
            if(_10._loadCheck){
                _10._loadCheck();
            }
            if(_10.onShow){
                _10.onShow();
            }
        },
        _hideChild:function(_12){
            _12.selected=false;
            _12.domNode.style.display="none";
            if(_12.onHide){
                _12.onHide();
            }
        },
        closeChild:function(_13){
            var _14=_13.onClose(this,_13);
            if(_14){
                this.removeChild(_13);
                _13.destroyRecursive();
            }
        },
        destroy:function(){
            this._beingDestroyed=true;
            this.inherited(arguments);
        }
        });
    dojo.declare("dijit.layout.StackController",[dijit._Widget,dijit._Templated,dijit._Container],{
        templateString:"<span wairole='tablist' dojoAttachEvent='onkeypress' class='dijitStackController'></span>",
        containerId:"",
        buttonWidget:"dijit.layout._StackButton",
        postCreate:function(){
            dijit.setWaiRole(this.domNode,"tablist");
            this.pane2button={};
            this.pane2handles={};
            this.pane2menu={};
            this._subscriptions=[dojo.subscribe(this.containerId+"-startup",this,"onStartup"),dojo.subscribe(this.containerId+"-addChild",this,"onAddChild"),dojo.subscribe(this.containerId+"-removeChild",this,"onRemoveChild"),dojo.subscribe(this.containerId+"-selectChild",this,"onSelectChild"),dojo.subscribe(this.containerId+"-containerKeyPress",this,"onContainerKeyPress")];
        },
        onStartup:function(_15){
            dojo.forEach(_15.children,this.onAddChild,this);
            this.onSelectChild(_15.selected);
        },
        destroy:function(){
            for(var _16 in this.pane2button){
                this.onRemoveChild(_16);
            }
            dojo.forEach(this._subscriptions,dojo.unsubscribe);
            this.inherited(arguments);
        },
        onAddChild:function(_17,_18){
            var _19=dojo.doc.createElement("span");
            this.domNode.appendChild(_19);
            var cls=dojo.getObject(this.buttonWidget);
            var _1b=new cls({
                label:_17.title,
                closeButton:_17.closable
                },_19);
            this.addChild(_1b,_18);
            this.pane2button[_17]=_1b;
            _17.controlButton=_1b;
            var _1c=[];
            _1c.push(dojo.connect(_1b,"onClick",dojo.hitch(this,"onButtonClick",_17)));
            if(_17.closable){
                _1c.push(dojo.connect(_1b,"onClickCloseButton",dojo.hitch(this,"onCloseButtonClick",_17)));
                var _1d=dojo.i18n.getLocalization("dijit","common");
                var _1e=new dijit.Menu({
                    targetNodeIds:[_1b.id],
                    id:_1b.id+"_Menu"
                    });
                var _1f=new dijit.MenuItem({
                    label:_1d.itemClose
                    });
                _1c.push(dojo.connect(_1f,"onClick",dojo.hitch(this,"onCloseButtonClick",_17)));
                _1e.addChild(_1f);
                this.pane2menu[_17]=_1e;
            }
            this.pane2handles[_17]=_1c;
            if(!this._currentChild){
                _1b.focusNode.setAttribute("tabIndex","0");
                this._currentChild=_17;
            }
            if(!this.isLeftToRight()&&dojo.isIE&&this._rectifyRtlTabList){
                this._rectifyRtlTabList();
            }
        },
        onRemoveChild:function(_20){
            if(this._currentChild===_20){
                this._currentChild=null;
            }
            dojo.forEach(this.pane2handles[_20],dojo.disconnect);
            delete this.pane2handles[_20];
            var _21=this.pane2menu[_20];
            if(_21){
                _21.destroyRecursive();
                delete this.pane2menu[_20];
            }
            var _22=this.pane2button[_20];
            if(_22){
                _22.destroy();
                delete this.pane2button[_20];
            }
        },
        onSelectChild:function(_23){
            if(!_23){
                return;
            }
            if(this._currentChild){
                var _24=this.pane2button[this._currentChild];
                _24.attr("checked",false);
                _24.focusNode.setAttribute("tabIndex","-1");
            }
            var _25=this.pane2button[_23];
            _25.attr("checked",true);
            this._currentChild=_23;
            _25.focusNode.setAttribute("tabIndex","0");
            var _26=dijit.byId(this.containerId);
            dijit.setWaiState(_26.containerNode,"labelledby",_25.id);
        },
        onButtonClick:function(_27){
            var _28=dijit.byId(this.containerId);
            _28.selectChild(_27);
        },
        onCloseButtonClick:function(_29){
            var _2a=dijit.byId(this.containerId);
            _2a.closeChild(_29);
            var b=this.pane2button[this._currentChild];
            if(b){
                dijit.focus(b.focusNode||b.domNode);
            }
        },
        adjacent:function(_2c){
            if(!this.isLeftToRight()&&(!this.tabPosition||/top|bottom/.test(this.tabPosition))){
                _2c=!_2c;
            }
            var _2d=this.getChildren();
            var _2e=dojo.indexOf(_2d,this.pane2button[this._currentChild]);
            var _2f=_2c?1:_2d.length-1;
            return _2d[(_2e+_2f)%_2d.length];
        },
        onkeypress:function(e){
            if(this.disabled||e.altKey){
                return;
            }
            var _31=null;
            if(e.ctrlKey||!e._djpage){
                var k=dojo.keys;
                switch(e.charOrCode){
                    case k.LEFT_ARROW:
                    case k.UP_ARROW:
                        if(!e._djpage){
                            _31=false;
                        }
                        break;
                    case k.PAGE_UP:
                        if(e.ctrlKey){
                            _31=false;
                        }
                        break;
                    case k.RIGHT_ARROW:
                    case k.DOWN_ARROW:
                        if(!e._djpage){
                            _31=true;
                        }
                        break;
                    case k.PAGE_DOWN:
                        if(e.ctrlKey){
                            _31=true;
                        }
                        break;
                    case k.DELETE:
                        if(this._currentChild.closable){
                            this.onCloseButtonClick(this._currentChild);
                        }
                        dojo.stopEvent(e);
                        break;
                    default:
                        if(e.ctrlKey){
                            if(e.charOrCode===k.TAB){
                                this.adjacent(!e.shiftKey).onClick();
                                dojo.stopEvent(e);
                            }else{
                                if(e.charOrCode=="w"){
                                    if(this._currentChild.closable){
                                        this.onCloseButtonClick(this._currentChild);
                                    }
                                    dojo.stopEvent(e);
                                }
                            }
                        }
                }
                if(_31!==null){
                    this.adjacent(_31).onClick();
                    dojo.stopEvent(e);
                }
            }
        },
        onContainerKeyPress:function(_33){
            _33.e._djpage=_33.page;
            this.onkeypress(_33.e);
        }
        });
    dojo.declare("dijit.layout._StackButton",dijit.form.ToggleButton,{
        tabIndex:"-1",
        postCreate:function(evt){
            dijit.setWaiRole((this.focusNode||this.domNode),"tab");
            this.inherited(arguments);
        },
        onClick:function(evt){
            dijit.focus(this.focusNode);
        },
        onClickCloseButton:function(evt){
            evt.stopPropagation();
        }
        });
    dojo.extend(dijit._Widget,{
        title:"",
        selected:false,
        closable:false,
        onClose:function(){
            return true;
        }
        });
}



/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["dijit.Tree"]){
    dojo._hasResource["dijit.Tree"]=true;
    dojo.provide("dijit.Tree");
    dojo.require("dojo.fx");
    dojo.require("dijit._Widget");
    dojo.require("dijit._Templated");
    dojo.require("dijit._Container");
    dojo.require("dojo.cookie");
    dojo.declare("dijit._TreeNode",[dijit._Widget,dijit._Templated,dijit._Container,dijit._Contained],{
        item:null,
        isTreeNode:true,
        label:"",
        isExpandable:null,
        isExpanded:false,
        state:"UNCHECKED",
        templateString:"<div class=\"dijitTreeNode\" waiRole=\"presentation\"\n\t><div dojoAttachPoint=\"rowNode\" class=\"dijitTreeRow\" waiRole=\"presentation\"\n\t\t><img src=\"${_blankGif}\" alt=\"\" dojoAttachPoint=\"expandoNode\" class=\"dijitTreeExpando\" waiRole=\"presentation\"\n\t\t><span dojoAttachPoint=\"expandoNodeText\" class=\"dijitExpandoText\" waiRole=\"presentation\"\n\t\t></span\n\t\t><span dojoAttachPoint=\"contentNode\" dojoAttachEvent=\"onmouseenter:_onMouseEnter, onmouseleave:_onMouseLeave\"\n\t\t\tclass=\"dijitTreeContent\" waiRole=\"presentation\">\n\t\t\t<img src=\"${_blankGif}\" alt=\"\" dojoAttachPoint=\"iconNode\" class=\"dijitTreeIcon\" waiRole=\"presentation\"\n\t\t\t><span dojoAttachPoint=\"labelNode\" class=\"dijitTreeLabel\" wairole=\"treeitem\" tabindex=\"-1\" waiState=\"selected-false\" dojoAttachEvent=\"onfocus:_onNodeFocus\"></span>\n\t\t</span\n\t></div>\n\t<div dojoAttachPoint=\"containerNode\" class=\"dijitTreeContainer\" waiRole=\"presentation\" style=\"display: none;\"></div>\n</div>\n",
        postCreate:function(){
            this.setLabelNode(this.label);
            this._setExpando();
            this._updateItemClasses(this.item);
            if(this.isExpandable){
                dijit.setWaiState(this.labelNode,"expanded",this.isExpanded);
            }
        },
        markProcessing:function(){
            this.state="LOADING";
            this._setExpando(true);
        },
        unmarkProcessing:function(){
            this._setExpando(false);
        },
        _updateItemClasses:function(_1){
            var _2=this.tree,_3=_2.model;
            if(_2._v10Compat&&_1===_3.root){
                _1=null;
            }
            this.iconNode.className="dijitTreeIcon "+_2.getIconClass(_1,this.isExpanded);
            this.labelNode.className="dijitTreeLabel "+_2.getLabelClass(_1,this.isExpanded);
        },
        _updateLayout:function(){
            var _4=this.getParent();
            if(!_4||_4.rowNode.style.display=="none"){
                dojo.addClass(this.domNode,"dijitTreeIsRoot");
            }else{
                dojo.toggleClass(this.domNode,"dijitTreeIsLast",!this.getNextSibling());
            }
        },
        _setExpando:function(_5){
            var _6=["dijitTreeExpandoLoading","dijitTreeExpandoOpened","dijitTreeExpandoClosed","dijitTreeExpandoLeaf"];
            var _7=["*","-","+","*"];
            var _8=_5?0:(this.isExpandable?(this.isExpanded?1:2):3);
            dojo.forEach(_6,function(s){
                dojo.removeClass(this.expandoNode,s);
            },this);
            dojo.addClass(this.expandoNode,_6[_8]);
            this.expandoNodeText.innerHTML=_7[_8];
        },
        expand:function(){
            if(this.isExpanded){
                return;
            }
            this._wipeOut&&this._wipeOut.stop();
            this.isExpanded=true;
            dijit.setWaiState(this.labelNode,"expanded","true");
            dijit.setWaiRole(this.containerNode,"group");
            this.contentNode.className="dijitTreeContent dijitTreeContentExpanded";
            this._setExpando();
            this._updateItemClasses(this.item);
            if(!this._wipeIn){
                this._wipeIn=dojo.fx.wipeIn({
                    node:this.containerNode,
                    duration:dijit.defaultDuration
                    });
            }
            this._wipeIn.play();
        },
        collapse:function(){
            if(!this.isExpanded){
                return;
            }
            this._wipeIn&&this._wipeIn.stop();
            this.isExpanded=false;
            dijit.setWaiState(this.labelNode,"expanded","false");
            this.contentNode.className="dijitTreeContent";
            this._setExpando();
            this._updateItemClasses(this.item);
            if(!this._wipeOut){
                this._wipeOut=dojo.fx.wipeOut({
                    node:this.containerNode,
                    duration:dijit.defaultDuration
                    });
            }
            this._wipeOut.play();
        },
        setLabelNode:function(_a){
            this.labelNode.innerHTML="";
            this.labelNode.appendChild(dojo.doc.createTextNode(_a));
        },
        setChildItems:function(_b){
            var _c=this.tree,_d=_c.model;
            this.getChildren().forEach(function(_e){
                dijit._Container.prototype.removeChild.call(this,_e);
            },this);
            this.state="LOADED";
            if(_b&&_b.length>0){
                this.isExpandable=true;
                dojo.forEach(_b,function(_f){
                    var id=_d.getIdentity(_f),_11=_c._itemNodeMap[id],_12=(_11&&!_11.getParent())?_11:this.tree._createTreeNode({
                        item:_f,
                        tree:_c,
                        isExpandable:_d.mayHaveChildren(_f),
                        label:_c.getLabel(_f)
                        });
                    this.addChild(_12);
                    _c._itemNodeMap[id]=_12;
                    if(this.tree.persist){
                        if(_c._openedItemIds[id]){
                            _c._expandNode(_12);
                        }
                    }
                },this);
                dojo.forEach(this.getChildren(),function(_13,idx){
                    _13._updateLayout();
                });
            }else{
                this.isExpandable=false;
            }
            if(this._setExpando){
                this._setExpando(false);
            }
            if(this==_c.rootNode){
                var fc=this.tree.showRoot?this:this.getChildren()[0],_16=fc?fc.labelNode:this.domNode;
                _16.setAttribute("tabIndex","0");
                _c.lastFocused=fc;
            }
        },
        removeChild:function(_17){
            this.inherited(arguments);
            var _18=this.getChildren();
            if(_18.length==0){
                this.isExpandable=false;
                this.collapse();
            }
            dojo.forEach(_18,function(_19){
                _19._updateLayout();
            });
        },
        makeExpandable:function(){
            this.isExpandable=true;
            this._setExpando(false);
        },
        _onNodeFocus:function(evt){
            var _1b=dijit.getEnclosingWidget(evt.target);
            this.tree._onTreeFocus(_1b);
        },
        _onMouseEnter:function(evt){
            dojo.addClass(this.contentNode,"dijitTreeNodeHover");
        },
        _onMouseLeave:function(evt){
            dojo.removeClass(this.contentNode,"dijitTreeNodeHover");
        }
        });
    dojo.declare("dijit.Tree",[dijit._Widget,dijit._Templated],{
        store:null,
        model:null,
        query:null,
        label:"",
        showRoot:true,
        childrenAttr:["children"],
        openOnClick:false,
        templateString:"<div class=\"dijitTreeContainer\" waiRole=\"tree\"\n\tdojoAttachEvent=\"onclick:_onClick,onkeypress:_onKeyPress\">\n</div>\n",
        isExpandable:true,
        isTree:true,
        persist:true,
        dndController:null,
        dndParams:["onDndDrop","itemCreator","onDndCancel","checkAcceptance","checkItemAcceptance","dragThreshold"],
        onDndDrop:null,
        itemCreator:null,
        onDndCancel:null,
        checkAcceptance:null,
        checkItemAcceptance:null,
        dragThreshold:0,
        _publish:function(_1e,_1f){
            dojo.publish(this.id,[dojo.mixin({
                tree:this,
                event:_1e
            },_1f||{})]);
        },
        postMixInProperties:function(){
            this.tree=this;
            this._itemNodeMap={};
            if(!this.cookieName){
                this.cookieName=this.id+"SaveStateCookie";
            }
        },
        postCreate:function(){
            if(this.persist){
                var _20=dojo.cookie(this.cookieName);
                this._openedItemIds={};
                if(_20){
                    dojo.forEach(_20.split(","),function(_21){
                        this._openedItemIds[_21]=true;
                    },this);
                }
            }
            if(!this.model){
                this._store2model();
            }
            this.connect(this.model,"onChange","_onItemChange");
            this.connect(this.model,"onChildrenChange","_onItemChildrenChange");
            this.connect(this.model,"onDelete","_onItemDelete");
            this._load();
            this.inherited(arguments);
            if(this.dndController){
                if(dojo.isString(this.dndController)){
                    this.dndController=dojo.getObject(this.dndController);
                }
                var _22={};
                for(var i=0;i<this.dndParams.length;i++){
                    if(this[this.dndParams[i]]){
                        _22[this.dndParams[i]]=this[this.dndParams[i]];
                    }
                }
                this.dndController=new this.dndController(this,_22);
            }
        },
        _store2model:function(){
            this._v10Compat=true;
            dojo.deprecated("Tree: from version 2.0, should specify a model object rather than a store/query");
            var _24={
                id:this.id+"_ForestStoreModel",
                store:this.store,
                query:this.query,
                childrenAttrs:this.childrenAttr
                };
            if(this.params.mayHaveChildren){
                _24.mayHaveChildren=dojo.hitch(this,"mayHaveChildren");
            }
            if(this.params.getItemChildren){
                _24.getChildren=dojo.hitch(this,function(_25,_26,_27){
                    this.getItemChildren((this._v10Compat&&_25===this.model.root)?null:_25,_26,_27);
                });
            }
            this.model=new dijit.tree.ForestStoreModel(_24);
            this.showRoot=Boolean(this.label);
        },
        _load:function(){
            this.model.getRoot(dojo.hitch(this,function(_28){
                var rn=this.rootNode=this.tree._createTreeNode({
                    item:_28,
                    tree:this,
                    isExpandable:true,
                    label:this.label||this.getLabel(_28)
                    });
                if(!this.showRoot){
                    rn.rowNode.style.display="none";
                }
                this.domNode.appendChild(rn.domNode);
                this._itemNodeMap[this.model.getIdentity(_28)]=rn;
                rn._updateLayout();
                this._expandNode(rn);
            }),function(err){
                console.error(this,": error loading root: ",err);
            });
        },
        mayHaveChildren:function(_2b){
        },
        getItemChildren:function(_2c,_2d){
        },
        getLabel:function(_2e){
            return this.model.getLabel(_2e);
        },
        getIconClass:function(_2f,_30){
            return (!_2f||this.model.mayHaveChildren(_2f))?(_30?"dijitFolderOpened":"dijitFolderClosed"):"dijitLeaf";
        },
        getLabelClass:function(_31,_32){
        },
        _onKeyPress:function(e){
            if(e.altKey){
                return;
            }
            var dk=dojo.keys;
            var _35=dijit.getEnclosingWidget(e.target);
            if(!_35){
                return;
            }
            var key=e.charOrCode;
            if(typeof key=="string"){
                if(!e.altKey&&!e.ctrlKey&&!e.shiftKey&&!e.metaKey){
                    this._onLetterKeyNav({
                        node:_35,
                        key:key.toLowerCase()
                        });
                    dojo.stopEvent(e);
                }
            }else{
                var map=this._keyHandlerMap;
                if(!map){
                    map={};
                    map[dk.ENTER]="_onEnterKey";
                    map[this.isLeftToRight()?dk.LEFT_ARROW:dk.RIGHT_ARROW]="_onLeftArrow";
                    map[this.isLeftToRight()?dk.RIGHT_ARROW:dk.LEFT_ARROW]="_onRightArrow";
                    map[dk.UP_ARROW]="_onUpArrow";
                    map[dk.DOWN_ARROW]="_onDownArrow";
                    map[dk.HOME]="_onHomeKey";
                    map[dk.END]="_onEndKey";
                    this._keyHandlerMap=map;
                }
                if(this._keyHandlerMap[key]){
                    this[this._keyHandlerMap[key]]({
                        node:_35,
                        item:_35.item
                        });
                    dojo.stopEvent(e);
                }
            }
        },
        _onEnterKey:function(_38){
            this._publish("execute",{
                item:_38.item,
                node:_38.node
                });
            this.onClick(_38.item,_38.node);
        },
        _onDownArrow:function(_39){
            var _3a=this._getNextNode(_39.node);
            if(_3a&&_3a.isTreeNode){
                this.focusNode(_3a);
            }
        },
        _onUpArrow:function(_3b){
            var _3c=_3b.node;
            var _3d=_3c.getPreviousSibling();
            if(_3d){
                _3c=_3d;
                while(_3c.isExpandable&&_3c.isExpanded&&_3c.hasChildren()){
                    var _3e=_3c.getChildren();
                    _3c=_3e[_3e.length-1];
                }
            }else{
                var _3f=_3c.getParent();
                if(!(!this.showRoot&&_3f===this.rootNode)){
                    _3c=_3f;
                }
            }
            if(_3c&&_3c.isTreeNode){
                this.focusNode(_3c);
            }
        },
        _onRightArrow:function(_40){
            var _41=_40.node;
            if(_41.isExpandable&&!_41.isExpanded){
                this._expandNode(_41);
            }else{
                if(_41.hasChildren()){
                    _41=_41.getChildren()[0];
                    if(_41&&_41.isTreeNode){
                        this.focusNode(_41);
                    }
                }
            }
        },
        _onLeftArrow:function(_42){
            var _43=_42.node;
            if(_43.isExpandable&&_43.isExpanded){
                this._collapseNode(_43);
            }else{
                var _44=_43.getParent();
                if(_44&&_44.isTreeNode&&!(!this.showRoot&&_44===this.rootNode)){
                    this.focusNode(_44);
                }
            }
        },
        _onHomeKey:function(){
            var _45=this._getRootOrFirstNode();
            if(_45){
                this.focusNode(_45);
            }
        },
        _onEndKey:function(_46){
            var _47=this;
            while(_47.isExpanded){
                var c=_47.getChildren();
                _47=c[c.length-1];
            }
            if(_47&&_47.isTreeNode){
                this.focusNode(_47);
            }
        },
        _onLetterKeyNav:function(_49){
            var _4a=_49.node,_4b=_4a,key=_49.key;
            do{
                _4a=this._getNextNode(_4a);
                if(!_4a){
                    _4a=this._getRootOrFirstNode();
                }
            }while(_4a!==_4b&&(_4a.label.charAt(0).toLowerCase()!=key));
            if(_4a&&_4a.isTreeNode){
                if(_4a!==_4b){
                    this.focusNode(_4a);
                }
            }
        },
        _onClick:function(e){
            var _4e=e.target;
            var _4f=dijit.getEnclosingWidget(_4e);
            if(!_4f||!_4f.isTreeNode){
                return;
            }
            if((this.openOnClick&&_4f.isExpandable)||(_4e==_4f.expandoNode||_4e==_4f.expandoNodeText)){
                if(_4f.isExpandable){
                    this._onExpandoClick({
                        node:_4f
                    });
                }
            }else{
                this._publish("execute",{
                    item:_4f.item,
                    node:_4f
                });
                this.onClick(_4f.item,_4f);
                this.focusNode(_4f);
            }
            dojo.stopEvent(e);
        },
        _onExpandoClick:function(_50){
            var _51=_50.node;
            this.focusNode(_51);
            if(_51.isExpanded){
                this._collapseNode(_51);
            }else{
                this._expandNode(_51);
            }
        },
        onClick:function(_52,_53){
        },
        onOpen:function(_54,_55){
        },
        onClose:function(_56,_57){
        },
        _getNextNode:function(_58){
            if(_58.isExpandable&&_58.isExpanded&&_58.hasChildren()){
                return _58.getChildren()[0];
            }else{
                while(_58&&_58.isTreeNode){
                    var _59=_58.getNextSibling();
                    if(_59){
                        return _59;
                    }
                    _58=_58.getParent();
                }
                return null;
            }
        },
        _getRootOrFirstNode:function(){
            return this.showRoot?this.rootNode:this.rootNode.getChildren()[0];
        },
        _collapseNode:function(_5a){
            if(_5a.isExpandable){
                if(_5a.state=="LOADING"){
                    return;
                }
                _5a.collapse();
                this.onClose(_5a.item,_5a);
                if(this.persist&&_5a.item){
                    delete this._openedItemIds[this.model.getIdentity(_5a.item)];
                    this._saveState();
                }
            }
        },
        _expandNode:function(_5b){
            if(!_5b.isExpandable){
                return;
            }
            var _5c=this.model,_5d=_5b.item;
            switch(_5b.state){
                case "LOADING":
                    return;
                case "UNCHECKED":
                    _5b.markProcessing();
                    var _5e=this;
                    _5c.getChildren(_5d,function(_5f){
                        _5b.unmarkProcessing();
                        _5b.setChildItems(_5f);
                        _5e._expandNode(_5b);
                    },function(err){
                        console.error(_5e,": error loading root children: ",err);
                    });
                    break;
                default:
                    _5b.expand();
                    this.onOpen(_5b.item,_5b);
                    if(this.persist&&_5d){
                        this._openedItemIds[_5c.getIdentity(_5d)]=true;
                        this._saveState();
                    }
            }
        },
        blurNode:function(){
            var _61=this.lastFocused;
            if(!_61){
                return;
            }
            var _62=_61.labelNode;
            dojo.removeClass(_62,"dijitTreeLabelFocused");
            _62.setAttribute("tabIndex","-1");
            dijit.setWaiState(_62,"selected",false);
            this.lastFocused=null;
        },
        focusNode:function(_63){
            _63.labelNode.focus();
        },
        _onBlur:function(){
            this.inherited(arguments);
            if(this.lastFocused){
                var _64=this.lastFocused.labelNode;
                dojo.removeClass(_64,"dijitTreeLabelFocused");
            }
        },
        _onTreeFocus:function(_65){
            if(_65){
                if(_65!=this.lastFocused){
                    this.blurNode();
                }
                var _66=_65.labelNode;
                _66.setAttribute("tabIndex","0");
                dijit.setWaiState(_66,"selected",true);
                dojo.addClass(_66,"dijitTreeLabelFocused");
                this.lastFocused=_65;
            }
        },
        _onItemDelete:function(_67){
            var _68=this.model.getIdentity(_67);
            var _69=this._itemNodeMap[_68];
            if(_69){
                var _6a=_69.getParent();
                if(_6a){
                    _6a.removeChild(_69);
                }
                delete this._itemNodeMap[_68];
                _69.destroyRecursive();
            }
        },
        _onItemChange:function(_6b){
            var _6c=this.model,_6d=_6c.getIdentity(_6b),_6e=this._itemNodeMap[_6d];
            if(_6e){
                _6e.setLabelNode(this.getLabel(_6b));
                _6e._updateItemClasses(_6b);
            }
        },
        _onItemChildrenChange:function(_6f,_70){
            var _71=this.model,_72=_71.getIdentity(_6f),_73=this._itemNodeMap[_72];
            if(_73){
                _73.setChildItems(_70);
            }
        },
        _onItemDelete:function(_74){
            var _75=this.model,_76=_75.getIdentity(_74),_77=this._itemNodeMap[_76];
            if(_77){
                _77.destroyRecursive();
                delete this._itemNodeMap[_76];
            }
        },
        _saveState:function(){
            if(!this.persist){
                return;
            }
            var ary=[];
            for(var id in this._openedItemIds){
                ary.push(id);
            }
            dojo.cookie(this.cookieName,ary.join(","),{
                expires:365
            });
        },
        destroy:function(){
            if(this.rootNode){
                this.rootNode.destroyRecursive();
            }
            if(this.dndController&&!dojo.isString(this.dndController)){
                this.dndController.destroy();
            }
            this.rootNode=null;
            this.inherited(arguments);
        },
        destroyRecursive:function(){
            this.destroy();
        },
        _createTreeNode:function(_7a){
            return new dijit._TreeNode(_7a);
        }
        });
    dojo.declare("dijit.tree.TreeStoreModel",null,{
        store:null,
        childrenAttrs:["children"],
        labelAttr:"",
        root:null,
        query:null,
        constructor:function(_7b){
            dojo.mixin(this,_7b);
            this.connects=[];
            var _7c=this.store;
            if(!_7c.getFeatures()["dojo.data.api.Identity"]){
                throw new Error("dijit.Tree: store must support dojo.data.Identity");
            }
            if(_7c.getFeatures()["dojo.data.api.Notification"]){
                this.connects=this.connects.concat([dojo.connect(_7c,"onNew",this,"_onNewItem"),dojo.connect(_7c,"onDelete",this,"_onDeleteItem"),dojo.connect(_7c,"onSet",this,"_onSetItem")]);
            }
        },
        destroy:function(){
            dojo.forEach(this.connects,dojo.disconnect);
        },
        getRoot:function(_7d,_7e){
            if(this.root){
                _7d(this.root);
            }else{
                this.store.fetch({
                    query:this.query,
                    onComplete:dojo.hitch(this,function(_7f){
                        if(_7f.length!=1){
                            throw new Error(this.declaredClass+": query "+dojo.toJson(this.query)+" returned "+_7f.length+" items, but must return exactly one item");
                        }
                        this.root=_7f[0];
                        _7d(this.root);
                    }),
                    onError:_7e
                });
            }
        },
        mayHaveChildren:function(_80){
            return dojo.some(this.childrenAttrs,function(_81){
                return this.store.hasAttribute(_80,_81);
            },this);
        },
        getChildren:function(_82,_83,_84){
            var _85=this.store;
            var _86=[];
            for(var i=0;i<this.childrenAttrs.length;i++){
                var _88=_85.getValues(_82,this.childrenAttrs[i]);
                _86=_86.concat(_88);
            }
            var _89=0;
            dojo.forEach(_86,function(_8a){
                if(!_85.isItemLoaded(_8a)){
                    _89++;
                }
            });
            if(_89==0){
                _83(_86);
            }else{
                var _8b=function _8b(_8c){
                    if(--_89==0){
                        _83(_86);
                    }
                };
                dojo.forEach(_86,function(_8d){
                    if(!_85.isItemLoaded(_8d)){
                        _85.loadItem({
                            item:_8d,
                            onItem:_8b,
                            onError:_84
                        });
                    }
                });
            }
        },
        getIdentity:function(_8e){
            return this.store.getIdentity(_8e);
        },
        getLabel:function(_8f){
            if(this.labelAttr){
                return this.store.getValue(_8f,this.labelAttr);
            }else{
                return this.store.getLabel(_8f);
            }
        },
        newItem:function(_90,_91){
            var _92={
                parent:_91,
                attribute:this.childrenAttrs[0]
                };
            return this.store.newItem(_90,_92);
        },
        pasteItem:function(_93,_94,_95,_96){
            var _97=this.store,_98=this.childrenAttrs[0];
            if(_94){
                dojo.forEach(this.childrenAttrs,function(_99){
                    if(_97.containsValue(_94,_99,_93)){
                        if(!_96){
                            var _9a=dojo.filter(_97.getValues(_94,_99),function(x){
                                return x!=_93;
                            });
                            _97.setValues(_94,_99,_9a);
                        }
                        _98=_99;
                    }
                });
            }
            if(_95){
                _97.setValues(_95,_98,_97.getValues(_95,_98).concat(_93));
            }
        },
        onChange:function(_9c){
        },
        onChildrenChange:function(_9d,_9e){
        },
        onDelete:function(_9f,_a0){
        },
        _onNewItem:function(_a1,_a2){
            if(!_a2){
                return;
            }
            this.getChildren(_a2.item,dojo.hitch(this,function(_a3){
                this.onChildrenChange(_a2.item,_a3);
            }));
        },
        _onDeleteItem:function(_a4){
            this.onDelete(_a4);
        },
        _onSetItem:function(_a5,_a6,_a7,_a8){
            if(dojo.indexOf(this.childrenAttrs,_a6)!=-1){
                this.getChildren(_a5,dojo.hitch(this,function(_a9){
                    this.onChildrenChange(_a5,_a9);
                }));
            }else{
                this.onChange(_a5);
            }
        }
        });
    dojo.declare("dijit.tree.ForestStoreModel",dijit.tree.TreeStoreModel,{
        rootId:"$root$",
        rootLabel:"ROOT",
        query:null,
        constructor:function(_aa){
            this.root={
                store:this,
                root:true,
                id:_aa.rootId,
                label:_aa.rootLabel,
                children:_aa.rootChildren
                };
        },
        mayHaveChildren:function(_ab){
            return _ab===this.root||this.inherited(arguments);
        },
        getChildren:function(_ac,_ad,_ae){
            if(_ac===this.root){
                if(this.root.children){
                    _ad(this.root.children);
                }else{
                    this.store.fetch({
                        query:this.query,
                        onComplete:dojo.hitch(this,function(_af){
                            this.root.children=_af;
                            _ad(_af);
                        }),
                        onError:_ae
                    });
                }
            }else{
                this.inherited(arguments);
            }
        },
        getIdentity:function(_b0){
            return (_b0===this.root)?this.root.id:this.inherited(arguments);
        },
        getLabel:function(_b1){
            return (_b1===this.root)?this.root.label:this.inherited(arguments);
        },
        newItem:function(_b2,_b3){
            if(_b3===this.root){
                this.onNewRootItem(_b2);
                return this.store.newItem(_b2);
            }else{
                return this.inherited(arguments);
            }
        },
        onNewRootItem:function(_b4){
        },
        pasteItem:function(_b5,_b6,_b7,_b8){
            if(_b6===this.root){
                if(!_b8){
                    this.onLeaveRoot(_b5);
                }
            }
            dijit.tree.TreeStoreModel.prototype.pasteItem.call(this,_b5,_b6===this.root?null:_b6,_b7===this.root?null:_b7);
            if(_b7===this.root){
                this.onAddToRoot(_b5);
            }
        },
        onAddToRoot:function(_b9){

        },
        onLeaveRoot:function(_ba){

        },
        _requeryTop:function(){
            var _bb=this.root.children||[];
            this.store.fetch({
                query:this.query,
                onComplete:dojo.hitch(this,function(_bc){
                    this.root.children=_bc;
                    if(_bb.length!=_bc.length||dojo.some(_bb,function(_bd,idx){
                        return _bc[idx]!=_bd;
                    })){
                        this.onChildrenChange(this.root,_bc);
                    }
                })
                });
        },
        _onNewItem:function(_bf,_c0){
            this._requeryTop();
            this.inherited(arguments);
        },
        _onDeleteItem:function(_c1){
            if(dojo.indexOf(this.root.children,_c1)!=-1){
                this._requeryTop();
            }
            this.inherited(arguments);
        }
        });
}


/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


dojo.declare("dijit.tree.model",null,{
    destroy:function(){
    },
    getRoot:function(_1){
    },
    mayHaveChildren:function(_2){
    },
    getChildren:function(_3,_4){
    },
    getIdentity:function(_5){
    },
    getLabel:function(_6){
    },
    newItem:function(_7,_8){
    },
    pasteItem:function(_9,_a,_b,_c){
    },
    onChange:function(_d){
    },
    onChildrenChange:function(_e,_f){
    }
    });