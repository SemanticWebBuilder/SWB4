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
