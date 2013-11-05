/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var context = "";
var word_separator = '_';
var trees = new Array();

var CONST_TAB = "/tab";   //Constante sufijo para identificar al tab

//var LOADING_MSG="<BR/><center><img src='"+context+"/swbadmin/images/loading.gif'><center>";

//dojo.require("dijit.form.Form");
//dojo.require("dijit.layout.ContentPane");

var act_item;
var act_store;
var act_treeNode;

function getHtml(url, tagid, parse, sync)
{
    var tag = dojo.byId(tagid);
    return getHtmlByTag(url, tag, parse, sync);
}

function getHtmlByTag(url, tag, parse, sync)
{
    if (!parse)
        parse = false;
    if (!sync)
        sync = false;
    dojo.xhrGet({
        url: url,
        sync: sync,
        load: function(response, ioArgs)
        {
            //var tag=dojo.byId(tagid);
            if (tag) {
                if (parse == true)
                    destroyChilds(tag);
                var pan = dijit.byNode(tag);
                //alert("-"+tagid+"-"+tag+"-"+pan+"-");
                if (pan && pan.attr)
                {
                    pan.attr('content', response);
                } else
                {
                    tag.innerHTML = response;
                }
                if (parse == true)
                    dojo.parser.parse(tag, true);
            } else {
                alert("No existe ningún elemento con id " + tagid);
            }
            return response;
        },
        error: function(response, ioArgs)
        {
            if (dojo.byId(tagid)) {
                dojo.byId(tagid).innerHTML = "<p>Ocurrió un error con respuesta:<br />" + response + "</p>";
            } else {
                alert("No existe ningún elemento con id " + tagid);
            }
            return response;
        },
        handleAs: "text"
    });
}

function postHtml(url, tagid)
{
    dojo.xhrPost({
        url: url,
        load: function(response)
        {
            var tag = dojo.byId(tagid);
            if (tag) {
                var pan = dijit.byId(tagid);
                //alert("-"+tagid+"-"+tag+"-"+pan+"-");
                if (pan && pan.attr)
                {
                    pan.attr('content', response);
                } else
                {
                    tag.innerHTML = response;
                }
            } else {
                alert("No existe ningún elemento con id " + tagid);
            }
            return response;
        },
        error: function(response)
        {
            if (dojo.byId(tagid)) {
                dojo.byId(tagid).innerHTML = "<p>Ocurrió un error con respuesta:<br />" + response + "</p>";
            } else {
                alert("No existe ningún elemento con id " + tagid);
            }
            return response;
        },
        handleAs: "text"
    });
}

function getSyncHtml(url)
{
    var ret = [];
    var obj = dojo.xhrGet({
        url: url,
        sync: true,
        load: function(data) {
            ret = data;
        },
        error: function(data) {
            alert("An error occurred, with response: " + data);
        }
    });
    return ret;
}

function getJSON(url)
{
    //alert("load:"+url);
    var ret = [];
    var obj = dojo.xhrGet({
        url: url,
        sync: true,
        load: function(data) {
            //obj=data;
            //alert("1:"+data);
            ret = data;
            //return data;
        },
        error: function(data) {
            alert("An error occurred, with response: " + data);
            //return data;
        },
        handleAs: "json"
    });
    //alert(url+" "+ret);
    return ret;
}

function postJSON(url)
{
    var ret = [];
    var obj = dojo.xhrPost({
        url: url,
        sync: true,
        load: function(data) {
            ret = data;
        },
        error: function(data) {
            alert("An error occurred, with response: " + data);
        },
        handleAs: "json"
    });
    return ret;
}

function postText(url)
{
    var ret = '';
    var obj = dojo.xhrPost({
        url: url,
        sync: true,
        load: function(data) {
            ret = data;
        },
        error: function(data) {
            alert("An error occurred, with response: " + data);
        },
        handleAs: "text"
    });
    return ret;
}


function showDialog(url, title)
{
    //alert("url:"+url);
    dojo.xhrGet({
        url: url,
        load: function(response, ioArgs) {
            //alert(response);
            //dijit.byId('swbDialogImp').attr('content',response);
            //console.log(dijit.byId('swbDialog'));
            dijit.byId('swbDialog').show();
            dijit.byId('swbDialogImp').attr('content', response);            
            setDialogTitle(title);
            return response;
        },
        error: function(response, ioArgs) {
            showStatus('Error:' + response);
            //dijit.byId('swbDialogImp').attr('content','Error: '+response);
            //dijit.byId('swbDialog').show();
            return response;
        },
        handleAs: "text"
    });
}

function showDialog2(url, title)
{
    //alert("url:"+url);
    dojo.xhrGet({
        url: url,
        load: function(response, ioArgs) {
            //alert(response);
            //dijit.byId('swbDialogImp').attr('content',response);
            dijit.byId('swbDialog').show();
            dijit.byId('swbDialogImp').domNode.innerHTML = response;
            console.log(dijit.byId('swbDialog'));
            setDialogTitle(title);
            return response;
        },
        error: function(response, ioArgs) {
            showStatus('Error:' + response);
            //dijit.byId('swbDialogImp').attr('content','Error: '+response);
            //dijit.byId('swbDialog').show();
            return response;
        },
        handleAs: "text"
    });
}

function setDialogTitle(title)
{
    if (title)
        dijit.byId('swbDialog').titleNode.innerHTML = title;
}

function hideDialog()
{
    dijit.byId('swbDialog').hide();
}

function getContentPanel(reference)
{
    if (!reference || reference == null)
        return null;
    //alert("reference:"+reference.getAttribute("dojoType"));
    var att = reference.getAttribute("dojoType");
    if (att && (att == "dijit.layout.ContentPane" || att == "dojox.layout.ContentPane" || att == "dijit.TitlePane"))
    {
        var x = dijit.byNode(reference);
        if (x && x != null)
        {
            if (att == "dojox.layout.ContentPane")
                x.suportScripts = true;
        }
        return x;
    } else
    {
        return getContentPanel(reference.parentNode);
    }
}

function submitUrl(url, reference)
{
    var panel = getContentPanel(reference);
    //alert("panel:"+panel);
    dojo.xhrGet({
        url: url,
        load: function(response, ioArgs)
        {
            if (panel != null)
            {
                var aux = panel.href;
                panel.attr('content', response);
                panel.href = aux;
                if (!panel.suportScripts)
                    runScripts(response);
            }
            return response;
        },
        error: function(response, ioArgs) {
            if (panel != null)
                panel.attr('content', 'An error occurred, with response: ' + response);
            return response;
        },
        handleAs: "text"
    });
}

function submitForm(formid)
{
    var obj = dojo.byId(formid);
    var objd = dijit.byId(formid);
    var fid = formid;
    //alert("id:"+formid+" "+"dojo:"+obj +" dijit:"+objd);
    if (!obj && objd) //si la forma esta dentro de un dialog
    {
        obj = objd.domNode;
        fid = obj;
    }

    if (!objd || objd.isValid())
    {
        try
        {
            //dojo.fadeOut({node: formid, duration: 1000}).play();
            dojo.fx.wipeOut({node: formid, duration: 500}).play();
        } catch (noe) {
        }

        try {
            var framesNames = "";
            for (var i = 0; i < window.frames.length; i++) 
            {
                try
                {
                    framesNames += window.frames[i].name;
                    if (framesNames && framesNames.indexOf("_editArea") != -1) {
                        area = window.frames[framesNames].editArea;
                        id = framesNames.substr(6);
                        document.getElementById(id).value = area.textarea.value;
                    }
                }catch(e){}
            }
        } catch (ex) {
        }

        //alert("entra2");
        dojo.xhrPost({
            // The page that parses the POST request
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            //handleAs: "text",

            url: obj.action,
            // Name of the Form we want to submit
            form: fid,
            // Loads this function if everything went ok
            load: function(data)
            {
                var panel = getContentPanel(obj);
                //alert("div:"+panel.id);
                //alert("div:"+panel.suportScripts);
                if (panel)
                {
                    try
                    {
                        var aux = panel.href;
                        //alert("div1:"+panel.href);
                        panel.attr('content', data);
                        panel.href = aux;
                        //alert("div2:"+panel.href);
                        if (!panel.suportScripts)
                            runScripts(data);
                    } catch (e) {
                        alert(e.message);
                    }
                }
                //dijit.byId('swbDialog').hide();
                //div_node.innerHTML = data;
                //dojo.parser.parse(div_node,true);
            },
            // Call this function if an error happened
            error: function(error) {
                alert('Error: ', error);
            }
        });
    } else
    {
        alert("Datos Inválidos...");
    }
}

function submitFormPortal(formid)
{
    var obj = dojo.byId(formid);
    var objd = dijit.byId(formid);
    var fid = formid;
    if (!obj && objd) //si la forma esta dentro de un dialog
    {
        obj = objd.domNode;
        fid = obj;
    }
    if (!objd || objd.isValid())
    {
        dojo.xhrPost({
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            url: obj.action,
            form: fid,
            load: function(data)
            {
                var panel = getContentPanel(obj);
                if (panel)
                {
                    try
                    {
                        var aux = panel.href;
                        panel.attr('content', data);
                        panel.href = aux;
                        if (!panel.suportScripts)
                            runScripts(data);
                    } catch (e) {
                        alert(e.message);
                    }
                }
            },
            error: function(error) {
                //alert('Error: ', error);
            }
        });
    } else
    {
        alert("Datos Inválidos...");
    }
}

function encodeExtendedCharacters(str)
{
    str = "" + str;
    alert("ini:" + str);
    var ret = "";
    for (x = 0; x < str.length; x++)
    {
        alert("x:" + x);
        var ch = str.charAt(x);
        var chc = str.charCodeAt(x);
        alert(x + ":" + ch + " " + chc);
        if (chc > 255)
        {
            ret += "&#" + chc + ";";
        } else
        {
            ret += ch;
        }
    }
    alert("end:" + ret);
    return ret;
}

function showPreviewURL(url)
{
    dojo.byId('swbPreviewFrame').src = url;
    dijit.byId('tabs').selectChild(dijit.byId('swbPreviewTab'));
}

function decodeExtendedCharacters(str)
{
    var ret = "";
    for (x = 0; x < str.length; x++)
    {
        var ch = str.charAt(x);
        if (ch == '&')
        {
            var i = str.indexOf(";", x);
            if (i > 2 && str.charAt(x + 1) == '#')
            {
                //alert("x:"+x+" "+i);
                var v = parseInt(str.substring(x + 2, i));
                //alert(v);
                //str.replace(x,i+1,""+(char)v);
                ret += String.fromCharCode(v);
                x = i;
            } else
                ret += ch;
        } else
            ret += ch;
    }
    return ret;
}

//alert("hola:"+decodeExtendedCharacters("á&#27721;é"));
//Depricado, usar addNewTab
function selectTab(id, url, title, tabName)
{
    addNewTab(id, url, title, tabName);
}

function addNewTab(id, url, title, tabName)
{
    //alert("addNewTab:"+title);
    //if(title)title=encodeExtendedCharacters(title);
    var objid = id + CONST_TAB;
    var newTab = dijit.byId(objid);
    if (!url)
        url = context + "/swbadmin/jsp/objectTab.jsp";
    if (newTab == null)
    {
        newTab = new dojox.layout.ContentPane(
                {
                    id: objid,
                    closeable: 'true',
                    //loadingMessage: LOADING_MSG,
                    onClose: function()
                    {
                        var ret = true;
                        //ret=confirm("Do you really want to Close this?");
                        if (ret)
                        {
                            var d = dijit.byId(objid + "2");
                            if (d)
                            {
                                var arr = d.getChildren();
                                for (var n = 0; n < arr.length; n++)
                                {
                                    arr[n].attr('content', null);
                                }
                            }
                        }
                        return ret;
                    },
                    onDownloadEnd: function()
                    {
                        var ret = true;
                        if (tabName)
                        {
                            var d = dijit.byId(objid + "2");
                            if (d)
                            {
                                var arr = d.getChildren();
                                for (var n = 0; n < arr.length; n++)
                                {
                                    if (arr[n].id == (id + "/" + tabName))
                                    {
                                        d.selectChild(arr[n]);
                                        break;
                                    }
                                }
                            }
                        }
                        return ret;
                    },
                    title: title,
                    href: url + "?suri=" + encodeURIComponent(id)
                });
        newTab.closable = true;
        tabs.addChild(newTab);
        tabs.selectChild(newTab);
        //closeAll
        if (tabs.tablist.pane2menu)
        {
            var menu = tabs.tablist.pane2menu[newTab];
            if (menu != null)
            {
                var closeall = new dijit.MenuItem({
                    label: "Cerrar todos",
                    //iconClass:"swbIconDelete",
                    onClick: function()
                    {
                        var ret = true;
                        var arr = tabs.getChildren();
                        for (var n = 0; n < arr.length; n++)
                        {
                            var tab = arr[n];
                            if (tab.closable == true)
                            {
                                tabs.closeChild(tab);
                            }
                        }
                        return ret;
                    }
                });
                //closeAthers
                menu.addChild(closeall);
                var closeothers = new dijit.MenuItem({
                    label: "Cerrar los demas",
                    //iconClass:"swbIconDelete",
                    onClick: function()
                    {
                        var ret = true;
                        var arr = tabs.getChildren();
                        for (var n = 0; n < arr.length; n++)
                        {
                            var tab = arr[n];
                            if (tab.closable == true && tab != newTab)
                            {
                                tabs.closeChild(tab);
                            }
                        }
                        tabs.selectChild(newTab);
                        return ret;
                    }
                });
                menu.addChild(closeothers);
            }
        }
    } else
    {
        tabs.selectChild(newTab);
        if (tabName)
        {
            alert("revisar");
            //objid = id + CONST_TAB + "2";
            objid = replaceId(id) + "_tab2";
            var tab = dijit.byId(objid);
            if (tab)
            {
                var arr = tab.getChildren();
                //alert(arr.length);
                for (var n = 0; n < arr.length; n++)
                {
                    if (arr[n].id == (id + "/" + tabName))
                    {
                        tab.selectChild(arr[n]);
                        break;
                    }
                }
            }
        }
    }
}

function replaceId(id)
{
    if(!id.substring)
    {
        id=id[0];
    }
    id=id.substring(id.lastIndexOf('/')+1);
    id=id.replace('#', ':');        
    return id;    
}

function reloadTab(uri)
{
    //alert("reload:"+replaceId(uri));
    //var aux;
    //var objid = uri + CONST_TAB + "2";
    var objid = replaceId(uri) + "_tab2";
    //alert("id:"+objid);
    var tab = dijit.byId(objid);
    if (tab)
    {
        var arr = tab.getChildren();
        for (var n = 0; n < arr.length; n++)
        {
            arr[n].refresh();
            //arr[n]._prepareLoad();
        }
    }
}

function executeAction(store, item, action)
{
    act_item = item;
    act_store = store;
    if (action.length)
        action = action[0];
    //alert("store:"+store+" action:"+action+" ["+action.name+"] "+action.length);
    if (action.name == "reload")
    {
        reloadTreeNode(store, item);
    } else if (action.name == "newTab")
    {
        //alert("id1"+item.id);
        var id = "" + item.id;
        var ind = id.indexOf('|');
        //alert("ind"+ind);
        if (ind > 0)
        {
            id = id.substring(0, ind);
        }
        //alert("id2"+id);
        addNewTab(id, action.value, item.title);
    } else if (action.name == "showDialog")
    {
        showDialog(action.value, action.target);
    } else if (action.name == "showStatusURL")
    {
        showStatusURL(action.value);
    } else if (action.name == "showStatusURLConfirm")
    {
        if (confirm(action.target))
            showStatusURL(action.value);
    } else if (action.name == "showPreviewURL")
    {
        showPreviewURL(action.value);
    } else if (action.name == "getHtml")
    {
        //alert(action.value+" "+action.target);
        var url = "" + action.value;
        var tag = "" + action.target;
        getHtml(url, tag);
    }
}

function executeTreeNodeEvent(store, item, eventname)
{
    if(item.id=="root")return;
    var event = getTreeNodeEvent(store, item, eventname);
    if (event)
    {
        //alert("event:"+event.name+" "+event.action);
        executeAction(store, item, event.action);
    }
}

//regrasa evento de nombre eventname asociado al nodo item del store
function getTreeNodeEvent(store, item, eventname)
{
    var events = store.getValues(item, "events");
    //var events=item.events;
    if (events)
    {
        for (var x = 0; x < events.length; x++)
        {
            if (events[x].name == eventname)
            {
                return events[x];
            }
        }
    }
    return null;
}

function removeChilds(store, item)
{
    //alert("removeChilds:"+item.children);
    var items = item.children;
    if (items)
    {
        //alert("removeChilds2:"+item.children.length);
        for (var i = 0; i < items.length; i++)
        {
            removeChilds(store, items[i]);
            store.deleteItem(items[i]);
        }
        store.save();
    }
}

//recargo nodo sin hijos de todos los stores
function updateTreeNodeByURI(uri)
{
    var refreshTitle = true;
    for (x = 0; x < trees.length; x++)
    {
        var s = trees[x].model.store;
        //alert("store:"+trees[x].store+" "+trees[x].model+" "+trees[x].model.store);
        var n = getItem(s, uri);
        if (n)
        {
            updateTreeNode(s, n, null, refreshTitle);
            if (refreshTitle == true)
                refreshTitle = false;
        }
    }
}

//recarga nodo sin hijos
function updateTreeNode(store, item, jsonNode, refreshTabTitle)
{
    //alert("Store:"+store+" "+act_store.jsId);
    if (!store)
        store = act_store;
    if (!item)
        item = act_item;
    var onlyNode = false;
    if (!jsonNode || jsonNode == null)
    {
        onlyNode = true;
        jsonNode = getJSON(context + store.controllerURL + "?suri=" + encodeURIComponent(item.id))[0];
    }

    store.setValues(item, "title", jsonNode.title);
    store.setValues(item, "type", jsonNode.type);
    store.setValues(item, "icon", jsonNode.icon);

    if (refreshTabTitle && refreshTabTitle == true)
    {
        setTabTitle(item.id, jsonNode.title, jsonNode.icon);
    }

    if (jsonNode.parent)
    {
        var pite = getItem(store, jsonNode.parent)
        if (pite)
        {
            //TODO:
            //alert("son:"+item.id+" newP:"+jsonNode.parent+" old:"+dijit.byId(item.id));
            //store.setValues(pite, "children", item);
            //store.setValues(item, "parent", pite);
        }
    }

    if (!onlyNode)
    {
        if (jsonNode.events)
            store.setValues(item, "events", jsonNode.events);
        else
            store.unsetAttribute(item, "events");
    }
    if (jsonNode.menus)
        store.setValues(item, "menus", jsonNode.menus);
    else
        store.unsetAttribute(item, "menus");
    store.save();
}

function setWaitCursor()
{
    if (act_treeNode && act_treeNode.isTreeNode)
    {
        //alert(act_treeNode);
        act_treeNode.markProcessing();
    }
    document.body.style.cursor = "wait";
    //dojo.byId("leftAccordion").style.cursor="wait";
}

function setDefaultCursor()
{
    document.body.style.cursor = "default";
    //dojo.byId("leftAccordion").style.cursor="default";
    if (act_treeNode && act_treeNode.isTreeNode)
    {
        try
        {
            act_treeNode.unmarkProcessing();
        }catch(noe){}
    }
}

function pasteItemByURIs(uri, oldParentUri, newParentUri)
{
    for (x = 0; x < trees.length; x++)
    {
        var t = trees[x];
        var childItem = getItem(t.model.store, uri);
        var oldParentItem = getItem(t.model.store, oldParentUri);
        var newParentItem = getItem(t.model.store, newParentUri);
        if (childItem && oldParentItem && newParentItem)
        {
            t.model.pasteItem(childItem, oldParentItem, newParentItem, false);
        }
    }
}

function removeTreeNodeByURI(uri)
{
    for (x = 0; x < trees.length; x++)
    {
        var s = trees[x].model.store;
        var n = getItem(s, uri);
        if (n)
        {
            removeTreeNode(s, n);
        }
    }
}

function removeTreeNode(store, item, closetab)
{
    if (!store)
        store = act_store;
    if (!item)
        item = act_item;
    if (!closetab)
        closetab = true;
    setWaitCursor();
    removeChilds(store, item);
    store.deleteItem(item);
    store.save();

    if (closetab)
    {
        closeTab(item.id);
    }
    setDefaultCursor();
}

function closeTab(uri)
{
    var objid = uri + CONST_TAB;
    var newTab = dijit.byId(objid);
    if (newTab)
    {
        tabs.closeChild(newTab);
    }
}

//recarga nodo e hijos
function reloadTreeNodeByURI(uri)
{
    for (x = 0; x < trees.length; x++)
    {
        var s = trees[x].model.store;
        //alert("store:"+trees[x]+" "+trees[x].id);
        var n = getItem(s, uri);
        if (n)
        {
            reloadTreeNode(s, n);
        }
    }
}

//recarca nodo e hijos
function reloadTreeNode(store, item)
{
    if (!store)
        store = act_store;
    if (!item)
        item = act_item;
    setWaitCursor();
    //alert("reload:"+item.id);
    removeChilds(store, item);
    var arr = getJSON(context + store.controllerURL + "?suri=" + encodeURIComponent(item.id) + "&type=" + item.type)
    updateTreeNode(store, item, arr[0]);
    //alert("arr:"+arr[0].id);
    var items = arr[0].children;
    //alert("nitem:"+items.length);
    if(items)
    {
        for (var i = 0; i < items.length; i++)
        {
            addItem(store, items[i], item);
        }
        store.save();
        setDefaultCursor(item);        
    }
}

function addItemByURI(store, parent, uri)
{
    if (!store)
        store = act_store;
    setWaitCursor();
    //alert("reload:"+item.id);
    var arr = getJSON(context + store.controllerURL + "?childs=false&suri=" + encodeURIComponent(uri));
    var item = arr[0];
    addItem(store, item, parent);
    store.save();
    //printObjProp(store);
    setDefaultCursor();
//          focusNodeByURI(uri);
}

function addItem(store, item, parent)
{
    if (getItem(store, item.id))
        return;
    //alert("addItem:"+item+" "+parent);
    var pInfo;
    if (parent)
    {
        pInfo = {parent: parent, attribute: "children"};
    } else
    {
        pInfo = {attribute: "children"};
    }
    //alert("addItem2:"+store+" "+pInfo);
    var ite = store.newItem(item, pInfo);
    //alert("hasChilds:"+item.hasChilds+" "+item.children.length);

    var childs = item.children;
    if (childs && childs.length > 0)
    {
        for (var x = 0; x < childs.length; x++)
        {
            //TODO:
            //alert("store:"+store+" childs[x]:"+childs[x].title+" item:"+ite.title);
            addItem(store, childs[x], ite);
        }
    } else if (item.hasChilds)
    {
        //alert("hasChilds:"+items[i].id+" "+ite);
        pInfo = {parent: ite, attribute: "children"};
        var dummy = {"id": item.id + "_tmp_", "icon": "swbIconWebSite", "title": "dummy"};
        store.newItem(dummy, pInfo);
    }
}

function actionDone() {
    alert("Acción realizada.");
}

function actionFailed() {
    alert("Error al ejecutar la acción.");
}

function loadScript(id, filepath)
{
    if (document.getElementById(id))
    { // Already exists
        return;
    }
    var head = document.getElementsByTagName("head")[0];
    if (head)
    {
        script = document.createElement('script');
        script.id = id;
        script.type = 'text/javascript';
        script.src = filepath;
        head.appendChild(script);
    }
}

var ini = -30;
var sy = ini;
var si = 10;
function scroll()
{
    var t = 30;
    var ele = dojo.byId('status');
    if (sy > 0)
    {
        si = -2;
        t = 3000;
    }
    sy += si;
    ele.style.bottom = sy + 'px';
    if (sy > ini)
        setTimeout(scroll, t);
    else
    {
        //self.status="hidden";
        ele.style.display = "none";
    }
    //ele.style.display = "none";
}

function showError(msg)
{
    showStatus(msg, "red");
}

function showStatus(msg, bgcolor)
{
    if (!bgcolor)
        bgcolor = "blue";
    var ele = dijit.byId('status');
    ele.style.bgColor = bgcolor;
    ele.domNode.style.display = "block";
    //self.status="visible";
    ele.attr('content', msg);
    ele.innerHTML = msg;
    sy = ini;
    si = 2;
    scroll();
}

function showStatusURL(url, sync)
{
    var ret = false;
    if (!sync)
        sync = false;
    var obj = dojo.xhrGet({
        url: url,
        sync: sync,
        load: function(response, ioArgs) {
            showStatus(response);
            ret = true;
        },
        error: function(response, ioArgs) {
            showStatus("Error: " + response);
        },
        handleAs: "text"
    });
    return ret;
}

function getItem(store, id)
{
    var ret = null;
    var x = store.fetchItemByIdentity(
            {
                identity: id,
                onItem: function(item)
                {
                    ret = item;
                }
            });
    return ret;
}

function runScripts(content)
{
    //var content=panel.attr('content');
    //alert(content);
    var src = new RegExp('<script[\\s\\S]*?</script>', 'gim');
    var load = content.match(src);
    //alert(load);
    if (load != null)
    {
        for (var c = 0; c < load.length; c++)
        {
            var txt = load[c];
            if (txt.indexOf("dojo/connect") > -1 || txt.indexOf("dojo/method") > -1)
                continue;
            // Remove begin tag
            var repl = new RegExp('<script.*?>', 'gim');
            var onloadscript = txt.replace(repl, '');

            // Remove end tag
            repl = new RegExp('</script>', 'gim');
            onloadscript = onloadscript.replace(repl, '');

            // Save scripts
            //alert("script:"+onloadscript);
            eval(onloadscript);
        }
    }
}

function registerTree(tree)
{
    trees[trees.length] = tree;
    //alert(stores.length);
}

function setTabTitle(uri, title, icon)
{
    //alert("setTabTitle:"+title);
    //if(title)title=encodeExtendedCharacters(title);
    var objid = uri + CONST_TAB;
    var tab = dijit.byId(objid);
    if (tab != null)
    {
        var aux = title;
        //if(icon)aux="<span><span style='position:fixed; margin:0px -3px; ' class='"+icon+"'></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+aux+"</span>";
        if (icon)
            aux = "<span style_='height:18px;' class='dijitReset dijitInline " + icon + "'></span><span class='dijitReset dijitInline dijitButtonText'>" + aux + "</span>";
        tab.title = aux;
        tab.controlButton.containerNode.innerHTML = aux || "";
        //alert(tab.title);
    }
}

function isParent(parent, child)
{
    var ret = false;
    if (parent && child && parent != child)
    {
        while (child)
        {
            if (child == parent)
            {
                ret = true;
                break;
            }
            child = child.getParent();
        }
    }
    return ret;
}

//regresa un substring de acuerdo al numero (number) de coincidencias del separador (separator)
function subStringSeparator(str, separator, number)
{
    var i = 0;
    var s = 0;
    var aux = "";
    while (i < str.length)
    {
        var ch = str.charAt(i);
        if (ch == separator)
        {
            if (s == number)
                break;
            s++;
        }
        aux += ch;
        i++;
    }
    return aux;
}

function matchDropLevel(dragNode, dropNode)
{
    var ret = true;
    var level = 0;
    var slevel = dragNode.item.dropMatchLevel;
    if (slevel)
        level = parseInt(slevel);

    if (level > 0)
    {
        var pathDrag;
        var pathDrop;

        var path = "";
        var aux = dragNode;
        while (aux != null)
        {
            path = aux.id + "|" + path
            aux = aux.getParent();
        }
        pathDrag = path;
        aux = dropNode;
        while (aux != null)
        {
            path = aux.id + "|" + path
            aux = aux.getParent();
        }
        pathDrop = path;

        pathDrag = subStringSeparator(pathDrag, '|', level);
        pathDrop = subStringSeparator(pathDrop, '|', level);

        if (pathDrag != pathDrop)
            ret = false;

        //self.status=level+" "+pathDrag+" "+pathDrop;
    }
    return ret;
}

var _oldTabClick;
function onClickTab(tab)
{
    if (_oldTabClick == tab)
    {
        tab.refresh();
    }
    _oldTabClick = tab;
}

/*
//RELOAD ON CLICK TAB
var _oldTabButton;
function onLoadTab(tab)
{
    if (!tab.controlButton.tab)
    {
        tab.controlButton.onClick=onclickTab;
        //alert("connect:"+tab+" "+tab.controlButton);
        //dojo.connect(tab.controlButton, 'onClick', onClickTab);
        _oldTabButton = tab.controlButton;
        _oldTabButton.tab = tab;
    }
}
function onClickTab(e)
{
    var button = dijit.byId(e.target.id);
    //alert("onclick:"+button+" "+button.tab);
    if (_oldTabButton == button)
    {
        //printObjProp(button.tab);
        //button.tab._prepareLoad();
        button.tab.refresh();
    }
    _oldTabButton = button;
}
*/

function printObjProp(obj, content)
{
    var ret = "";
    for (property in obj) {
        if (content && content == true)
        {
            ret += property + "=" + obj[property] + ", ";
        } else
        {
            ret += property + ", ";
        }
    }
    //console.log(ret);
    alert(ret);
}


function destroyChilds(tag)
{
    var widgets = dijit.findWidgets(tag);
    //printObjProp(widgets,true);
    dojo.forEach(widgets, function(w) {
        //printObjProp(w,true);
        w.destroyRecursive(true);
    });
}

function selectAll(name, val)
{
    var field = document.getElementsByName(name);
    for (i = 0; i < field.length; i++)
        field[i].checked = val;
}

function include(script_filename) {
    document.write('<' + 'script');
    document.write(' language="javascript"');
    document.write(' type="text/javascript"');
    document.write(' charset="UTF-8"');
    document.write(' src="' + script_filename + '">');
    document.write('</' + 'script' + '>');
}

function include_dom(script_filename) {
    var html_doc = document.getElementsByTagName('head').item(0);
    var js = document.createElement('script');
    js.setAttribute('language', 'javascript');
    js.setAttribute('type', 'text/javascript');
    js.setAttribute('charset', 'UTF-8');
    js.setAttribute('src', script_filename);
    html_doc.appendChild(js);
    return false;
}

var oldvalreq = "";
var oldvalret = false;
function validateElement(pname, url, value)
{
    var ret = false;
    if (pname && url && value)
    {
        url = url + "&" + pname + "=" + encodeURIComponent(value);
        if (url != oldvalreq)
        {
            ret = getJSON(url);
            oldvalret = ret;
            oldvalreq = url;
        } else
        {
            ret = oldvalret;
        }
    }
    return ret;
}

var oldreq = "";
var oldret = false;
function canCreateSemanticObject(model, clsid, id)
{
    if (model)
    {
        var aux = model;
        if (clsid)
            aux += clsid;
        if (id)
            aux += id;

        if (oldreq != aux)
        {
            var url = context + '/frmprocess/canCreate?model=' + model;
            if (clsid)
                url += '&clsid=' + clsid;
            if (id)
                url += '&id=' + id;
            oldret = getJSON(url);
            oldreq = aux;
        }
        return oldret;
    }
    return false;
}

var oldlog = "";
var oldlret = false;
function canAddLogin(model, slogin)
{
    if (oldlog != (model + slogin))
    {
        oldlret = getJSON(context + '/swbadmin/jsp/canAddLogin.jsp?login=' + slogin + '&model=' + model);
        oldlog = (model + slogin);
    }
    return oldlret;
}

var emailoldlog = "";
var emailoldlret = false;
function canAddEmail(model, semail)
{
    if (emailoldlog != (model + semail))
    {
        emailoldlret = getJSON(context + '/swbadmin/jsp/canAddEmail.jsp?email=' + semail + '&model=' + model);
        emailoldlog = (model + semail);
    }
    return emailoldlret;
}

/**
 * value: texto
 */
function replaceChars4Id(value, lowercase)
{
    var id = "";
    if (value)
    {
        if (lowercase)
            value = value.toLowerCase();
        for (var x = 0; x < value.length; x++)
        {
            var ch = value.charAt(x);
            if (ch == ' ')
                ch = word_separator;
            else if (ch == 'á')
                ch = 'a';
            else if (ch == 'à')
                ch = 'a';
            else if (ch == 'ã')
                ch = 'a';
            else if (ch == 'â')
                ch = 'a';
            else if (ch == 'ä')
                ch = 'a';
            else if (ch == 'å')
                ch = 'a';
            else if (ch == 'é')
                ch = 'e';
            else if (ch == 'è')
                ch = 'e';
            else if (ch == 'ê')
                ch = 'e';
            else if (ch == 'ë')
                ch = 'e';
            else if (ch == 'í')
                ch = 'i';
            else if (ch == 'ì')
                ch = 'i';
            else if (ch == 'î')
                ch = 'i';
            else if (ch == 'ï')
                ch = 'i';
            else if (ch == 'ó')
                ch = 'o';
            else if (ch == 'ò')
                ch = 'o';
            else if (ch == 'ô')
                ch = 'o';
            else if (ch == 'ö')
                ch = 'o';
            else if (ch == 'õ')
                ch = 'o';
            else if (ch == 'ú')
                ch = 'u';
            else if (ch == 'ù')
                ch = 'u';
            else if (ch == 'ü')
                ch = 'u';
            else if (ch == 'û')
                ch = 'u';
            else if (ch == 'ñ')
                ch = 'n';
            else if (ch == 'ç')
                ch = 'c';
            else if (ch == 'ÿ')
                ch = 'y';
            else if (ch == 'ý')
                ch = 'y';
            else if (ch == 'Á')
                ch = 'A';
            else if (ch == 'À')
                ch = 'A';
            else if (ch == 'Ã')
                ch = 'A';
            else if (ch == 'Â')
                ch = 'A';
            else if (ch == 'Ä')
                ch = 'A';
            else if (ch == 'Å')
                ch = 'A';
            else if (ch == 'É')
                ch = 'E';
            else if (ch == 'È')
                ch = 'E';
            else if (ch == 'Ê')
                ch = 'E';
            else if (ch == 'Ë')
                ch = 'E';
            else if (ch == 'Í')
                ch = 'I';
            else if (ch == 'Ì')
                ch = 'I';
            else if (ch == 'Î')
                ch = 'I';
            else if (ch == 'Ï')
                ch = 'I';
            else if (ch == 'Ó')
                ch = 'O';
            else if (ch == 'Ò')
                ch = 'O';
            else if (ch == 'Ô')
                ch = 'O';
            else if (ch == 'Ö')
                ch = 'O';
            else if (ch == 'Õ')
                ch = 'O';
            else if (ch == 'Ú')
                ch = 'U';
            else if (ch == 'Ù')
                ch = 'U';
            else if (ch == 'Ü')
                ch = 'U';
            else if (ch == 'Û')
                ch = 'U';
            else if (ch == 'Ñ')
                ch = 'N';
            else if (ch == 'Ç')
                ch = 'C';
            else if (ch == 'Ý')
                ch = 'Y';
            if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_' || ch == '-')
            {
                id += ch;
            }
        }
    }
    return id;
}

function hideApplet(flag)
{
    if (flag)
        dojo.query(".applet", "main").style('visibility', 'hidden');
    else
        dojo.query(".applet", "main").style('visibility', 'visible');
}

function init(ctxt)
{
    context = ctxt;
    //EditArea
    include(context + "/swbadmin/js/editarea/edit_area/edit_area_full.js");
}

function validateChk(name, msg)
{
    var field = document.getElementsByName(name);
    var ok = false;
    for (i = 0; i < field.length; i++)
    {
        if (field[i].checked)
        {
            ok = true;
        }
    }
    if (ok)
    {
        return true;
    }
    else
    {
        alert(msg);
        return false;
    }
}

function exportReport(url)
{
    var sizze = 'width=600, height=550, scrollbars, resizable, alwaysRaised, menubar';
    window.open(url, "RepWindow", sizze);
}

// Funciones para el administrador de indices de búsqueda

function enviar(frmid, accion)
{
    //alert('fid:'+frmid+', action: '+accion);
    var frm = dijit.byId(frmid);
    frm.attr('action', accion);

    //alert('forma action:'+frm.attr('action'));
    submitForm(frmid);
    return false;
}

function selectCombo(obj, id)
{
    obj.selectedIndex = id;
}

function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds) {
            break;
        }
    }
}


function indexcheck(url, tagid)
{
    var indx = dijit.byId(tagid).value;
    if (indx > 0)
    {
        indx = eval(indexrefresh(url, tagid));
    }
}

function indexrefresh(url, tagid)
{
    dojo.xhrPost({
        url: url,
        load: function(response)
        {
            dijit.byId(tagid).attr("value", response);
            return response;
        },
        error: function(response)
        {
            dijit.byId(tagid).attr("value", "0");
            return response;
        },
        handleAs: "text"
    });
}

//dojo.require("dojox.html.styles");
function setStyleSheetByInstance(rules, sufix, title) {
    rules = rules.split('}');
    for (i = 0; i < rules.length; i++) {
        rule = rules[i].split('{');
        if (rule[1])
            if (arguments.length == 3)
                dojox.html.insertCssRule(rule[0] + '_' + sufix, rule[1], title);
            else
                dojox.html.insertCssRule(rule[0] + '_' + sufix, rule[1]);
    }
}

function createCoverDiv(divId, bgcolor, opacity) {
    var layer = document.createElement('div');
    layer.id = divId;
    layer.style.width = '100%';
    layer.style.height = '100%';
    layer.style.backgroundColor = bgcolor;
    layer.style.position = 'fixed';
    layer.style.top = 0;
    layer.style.left = 0;
    layer.style.zIndex = 1000;
    layer.style.filter = 'alpha(opacity=' + opacity + ')';
    layer.style.opacity = opacity / 100;
    document.body.appendChild(layer);
}

function removeCoverDiv(divId) {
    var layer = document.getElementById(divId);
    var superlayer = document.getElementById('s_' + divId);
    if (layer && superlayer) {
        document.body.removeChild(superlayer);
        document.body.removeChild(layer);
    }
}

function setCookie(name, val, life) {
    document.cookie = name;
    var expDate = new Date();
    expDate.setTime(expDate.getTime() + life);
    expDate = expDate.toGMTString();
    var str1 = name + "=" + val + "; expires=" + expDate + ";Path=/";
    document.cookie = str1;
}

function getCookie(name) {
    var arg = name + "=";
    var alen = arg.length;
    var clen = document.cookie.length;
    var i = 0;
    while (i < clen) {
        var j = i + alen;
        if (document.cookie.substring(i, j) == arg)
            return getCookieVal(j);
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0)
            break;
    }
    return null;
}

function getCookieVal(offset) {
    var endstr = document.cookie.indexOf(";", offset);
    if (endstr == -1)
        endstr = document.cookie.length;
    return unescape(document.cookie.substring(offset, endstr));
}



if (!Array.prototype.indexOf)
{
    Array.prototype.indexOf = function(elt /*, from*/)
    {
        var len = this.length;

        var from = Number(arguments[1]) || 0;
        from = (from < 0)
                ? Math.ceil(from)
                : Math.floor(from);
        if (from < 0)
            from += len;

        for (; from < len; from++)
        {
            if (from in this &&
                    this[from] === elt)
                return from;
        }
        return -1;
    };
}


/*
 * Funciones de validación de formularios
 */
String.prototype.trim = function() {
    return this.replace(/^\s+|\s+$/g, "");
}
String.prototype.ltrim = function() {
    return this.replace(/^\s+/g, "");
}
String.prototype.rtrim = function() {
    return this.replace(/\s+$/g, "");
}

function isValidEmail(email) {
    /************************************************
     DESCRIPTION: Validates that una cadena contenga un email válido.
     
     PARAMETERS: email - Cadena a ser evaluada.
     
     RETURNS: true if es válida, de lo contrario false.
     *************************************************/
    var filter = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+.)+([a-zA-Z0-9]{2,4})+$/;
    if (!filter.test(email)) {
        return false;
    }
    return true;
}

function isEmpty(strValue) {
    /************************************************
     DESCRIPTION: Valida que una cadena sea vacía.
     
     PARAMETERS: strValue - Cadena a ser evaluada.
     
     RETURNS: true si es vacía, de lo contrario false.
     *************************************************/
    if (!strValue)
        return true;
    var strTemp = new String(strValue);
    strTemp = strTemp.trim();
    if (strTemp.length > 0) {
        return false;
    }
    return true;
}

function  isNumeric(strValue) {
    /*****************************************************************
     DESCRIPTION: Valida que una cadena contenga un valor numérico.
     
     PARAMETERS: strValue - Cadena a ser evaluada.
     
     RETURNS: true si es un valor numérico válido, de lo contrario false.
     ******************************************************************/
    var objRegExp = /(^-?\d\d*\.\d*$)|(^-?\d\d*$)|(^-?\.\d\d*$)/;
    return objRegExp.test(strValue);
}

function isDigit(strValue, n, m) {
    /*****************************************************************
     DESCRIPTION: Valida que una cadena contenga un valor numérico de enteros sin signo.
     
     PARAMETERS: strValue - Cadena a ser evaluada.
     n  - Longitud mínima del valor entero. Opcional.
     m  - Longitud máxima del valor entero. Opcional.
     
     RETURNS: true si cumple con las condiciones, de lo contrario false.
     ******************************************************************/
    var strRe = '^\\d+\\d*$';
    if (arguments.length == 2)
        strRe = '^\\d{' + n + '}$';
    else if (arguments.length > 2)
        strRe = '^\\d{' + n + ',' + m + '}$';

    var re = new RegExp(strRe, "g");
    return re.test(strValue);
}

function isInteger(strValue, n, m) {
    /*****************************************************************
     DESCRIPTION: Valida que una cadena contenga un valor numérico de enteros.
     Puede incluir signo.
     
     PARAMETERS: strValue - Cadena a ser evaluada.
     n  - Longitud mínima del valor entero. Opcional.
     m  - Longitud máxima del valor entero. Opcional.
     
     RETURNS: true si cumple con las condiciones, de lo contrario false.
     ******************************************************************/
    var strRe = '^\\d+\\d*$';
    if (arguments.length == 2)
        strRe = '^\\d{' + n + '}$';
    else if (arguments.length > 2)
        strRe = '^\\d{' + n + ',' + m + '}$';

    var re = new RegExp(strRe, "g");
    return re.test(strValue);
}

function isUrl(url) {
    var regex = /^(https?|ftp):\/\/[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(\/.*)*(([0-9]{1,5})?(\?(\w=\S*)+(&\w=\S*)*))?$/;
    return regex.test(url);
}


function MoveItems(lstbxFrom, lstbxTo)
{
    var varFromBox = document.getElementById(lstbxFrom);
    var varToBox = document.getElementById(lstbxTo);
    if ((varFromBox != null) && (varToBox != null))
    {
        if (varFromBox.length < 1)
        {
            alert('No hay propiedades en la lista.');
            return false;
        }
        if (varFromBox.options.selectedIndex == -1) // no hay elementos seleccionados
        {
            alert('Selecciona una propiedad de la lista.');
            return false;
        }
        while (varFromBox.options.selectedIndex >= 0)
        {
            var newOption = new Option(); // crea una opcion en el select
            newOption.text = varFromBox.options[varFromBox.options.selectedIndex].text;
            newOption.value = varFromBox.options[varFromBox.options.selectedIndex].value;
            varToBox.options[varToBox.length] = newOption; //agrega la opción al final del select destino
            varFromBox.remove(varFromBox.options.selectedIndex); //quita la opción del select origen
        }
    }
    return false;
}

function enviatodos(lstbox)
{
    var list = document.getElementById(lstbox);
    for (var i = 0; i < list.options.length; i++) {
        list.options[i].selected = true;
    }
    return true;
}

function updItem(uri, param, sel) {
    var valor = sel.options[sel.options.selectedIndex].value;
    var url = uri + '&' + param + '=' + escape(valor);
    submitUrl(url, sel);
    //window.location=url;
}

//function addcss(css){
//    var head = document.getElementsByTagName('head')[0];
//    var s = document.createElement('style');
//    s.setAttribute('type', 'text/css');
//    if (s.styleSheet) {   // IE
//        s.styleSheet.cssText = css;
//    } else {                // the world
//        s.appendChild(document.createTextNode(css));
//    }
//    head.appendChild(s);
// }

function loadjscssfile(filename, filetype) {
    if (filetype == "js") { //if filename is a external JavaScript file
        var fileref = document.createElement('script')
        fileref.setAttribute("type", "text/javascript")
        fileref.setAttribute("src", filename)
    }
    else if (filetype == "css") { //if filename is an external CSS file
        var fileref = document.createElement("link")
        fileref.setAttribute("rel", "stylesheet")
        fileref.setAttribute("type", "text/css")
        fileref.setAttribute("href", filename)
    }
    if (typeof fileref != "undefined")
        document.getElementsByTagName("head")[0].appendChild(fileref)
}

//loadjscssfile("myscript.js", "js") //dynamically load and add this .js file
//loadjscssfile("javascript.php", "js") //dynamically load "javascript.php" as a JavaScript file
//loadjscssfile("mystyle.css", "css") ////dynamically load and add this .css file