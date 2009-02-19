/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
      var context="/swb";
      var trees= new Array();

      var CONST_TAB="/tab";   //Constante sufijo para identificar al tab

      var act_item;
      var act_store;
      var act_treeNode;

      dojo.require("dijit.Menu");
      dojo.require("dijit._Calendar");
      dojo.require("dijit.ColorPalette");
      dojo.require("dijit.ProgressBar");
      dojo.require("dijit.TitlePane");
      dojo.require("dijit.Tooltip");
      dojo.require("dijit.Tree");

      dojo.require("dojox.form.TimeSpinner");
      dojo.require("dojox.layout.ContentPane");

      // editor:
      dojo.require("dijit.Editor");

      // dnd:
      dojo.require("dojo.dnd.Source");

      // various Form elemetns

      dojo.require("dijit.form.CheckBox");
      dojo.require("dijit.form.Textarea");
      dojo.require("dijit.form.FilteringSelect");
      dojo.require("dijit.form.TextBox");
      dojo.require("dijit.form.DateTextBox");
      dojo.require("dijit.form.TimeTextBox");
      dojo.require("dijit.form.Button");
      dojo.require("dijit.InlineEditBox");
      dojo.require("dijit.form.NumberSpinner");
      dojo.require("dijit.form.Slider");

      // layouts used in page

      dojo.require("dijit.layout.AccordionContainer");
      dojo.require("dijit.layout.ContentPane");
      dojo.require("dijit.layout.TabContainer");
      dojo.require("dijit.layout.BorderContainer");
      dojo.require("dijit.Dialog");

      // scan page for widgets and instantiate them
      dojo.require("dojo.parser");

      // humm?
      dojo.require("dojo.date.locale");

      // for the Tree
      dojo.require("dojo.data.ItemFileWriteStore");
      dojo.require("dojo.data.ItemFileReadStore");

      dojo.require("dijit._tree.dndSource");

      //dojo.require("dijit.PopupMenu");

      dojo.require("dojo.fx");

      dojo.addOnLoad(function() {

          var start = new Date().getTime();
          //dojo.parser.parse(dojo.byId('container'));
          console.info("Total parse time: " + (new Date().getTime() - start) + "ms");

          //dojo.byId('loaderInner').innerHTML += " done.";
          setTimeout(function hideLoader(){
              var loader = dojo.byId('loader');
              dojo.fadeOut({ node: loader, duration:500,
                  onEnd: function(){
                      loader.style.display = "none";
                  }
              }).play();
          }, 250);


            // assuming our tabContainer has id="bar"
//            dojo.subscribe("tabs-addChild", function(child){
//                alert("A new child was selected:"+child.id);
//            });


      });

      function getHtml(url, tagid)
      {
          dojo.xhrGet({
              url: url,
              load: function(response, ioArgs)
              {
                  var tag=dojo.byId(tagid);
                  if(tag){
                      var pan=dijit.byId(tagid);
                      //alert("-"+tagid+"-"+tag+"-"+pan+"-");
                      if(pan && pan.attr)
                      {
                          pan.attr('content',response);
                      }else
                      {
                          tag.innerHTML = response;
                      }
                  }else {
                      alert("No existe ningún elemento con id " + tagid);
                  }
                  return response;
              },
              error: function(response, ioArgs)
              {
                  if(dojo.byId(tagid)) {
                      dojo.byId(tagid).innerHTML = "<p>Ocurrió un error con respuesta:<br />" + response + "</p>";
                  }else {
                      alert("No existe ningún elemento con id " + tagid);
                  }
                  return response;
              },
              handleAs: "text"
          });
      }

      function getJSON(url)
      {
          //alert("load:"+url);
          var ret=[];
          var obj=dojo.xhrGet({
              url: url,
              sync: true,
              load: function(data){
                  //obj=data;
                  //alert("1:"+data);
                  ret=data;
                  //return data;
              },
              error: function(data){
                  alert("An error occurred, with response: " + data);
                  //return data;
              },
              handleAs: "json"
          });
          //alert(url+" "+ret);
          return ret;
      }


      function showDialog(url, title)
      {
          //alert("url:"+url);
          dojo.xhrGet({
              url: url,
              load: function(response, ioArgs){
                  //alert(response);
                  dijit.byId('swbDialogImp').attr('content',response);
                  dijit.byId('swbDialog').show();
                  setDialogTitle(title);
                  return response;
              },
              error: function(response, ioArgs){
                  showStatus('Error:'+response);
                  //dijit.byId('swbDialogImp').attr('content','Error: '+response);
                  //dijit.byId('swbDialog').show();
                  return response;
              },
              handleAs: "text"
          });
      }

      function setDialogTitle(title)
      {
          if(title)dijit.byId('swbDialog').titleNode.innerHTML=title;
      }

      function hideDialog()
      {
          dijit.byId('swbDialog').hide();
      }

      function getContentPanel(reference)
      {
          if(!reference)return null;
          //alert("reference:"+reference.getAttribute("dojoType"));
          var att=reference.getAttribute("dojoType");
          if(att && (att=="dijit.layout.ContentPane" || att=="dojox.layout.ContentPane" || att=="dijit.TitlePane"))
          {
              var x=dijit.byNode(reference);
              if(x)
              {
                if(att=="dojox.layout.ContentPane")x.suportScripts=true;
              }
              return x;
          }else
          {
              return getContentPanel(reference.parentNode);
          }
      }

      function submitUrl(url, reference)
      {
          var panel=getContentPanel(reference);
          //alert("panel:"+panel);
          dojo.xhrGet({
              url: url,
              load: function(response, ioArgs)
              {
                  if(panel!=null)
                  {
                      var aux=panel.href;
                      panel.attr('content',response);
                      panel.href=aux;
                      if(!panel.suportScripts)runScripts(response);
                  }
                  return response;
              },
              error: function(response, ioArgs){
                  if(panel!=null)panel.attr('content','An error occurred, with response: ' + response);
                  return response;
              },
              handleAs: "text"
          });
      }

      function submitForm(formid)
      {
          var obj=dojo.byId(formid);
          var objd=dijit.byId(formid);
          var fid=formid;
          //alert("id:"+formid+" "+"dojo:"+obj +" dijit:"+objd);
          if(!obj && objd) //si la forma esta dentro de un dialog
          {
              obj=objd.domNode;
              fid=obj;
          }

          if(!objd || objd.isValid())
          {
              try
              {
                //dojo.fadeOut({node: formid, duration: 1000}).play();
                dojo.fx.wipeOut({node: formid, duration: 500}).play();
              }catch(noe){}

              try {
                var framesNames = "";
                for (var i = 0; i < window.frames.length; i++) {
                    framesNames += window.frames[i].name;
                    if (framesNames && framesNames.indexOf("_editArea") != -1) {
                        area = window.frames[framesNames].editArea;
                        id = framesNames.substr(6);
                        document.getElementById(id).value = area.textarea.value;
                    }
                }
              } catch (ex) {}
              
              //alert("entra2");
              dojo.xhrPost({
                  // The page that parses the POST request
                  contentType: "application/x-www-form-urlencoded; charset=utf-8",

                  //handleAs: "text",

                  url: obj.action,

                  // Name of the Form we want to submit
                  form: fid,

                  // Loads this function if everything went ok
                  load: function (data)
                  {
                          var panel=getContentPanel(obj);
                          //alert("div:"+panel.id);
                          //alert("div:"+panel.suportScripts);
                          if(panel)
                          {
                              try
                              {
                                  var aux=panel.href;
                                  //alert("div1:"+panel.href);
                                  panel.attr('content',data);
                                  panel.href=aux;
                                  //alert("div2:"+panel.href);
                                  if(!panel.suportScripts)runScripts(data);
                              }catch(e){alert(e.message);}
                          }
                          //dijit.byId('swbDialog').hide();
                          //div_node.innerHTML = data;
                          //dojo.parser.parse(div_node,true);
                  },
                  // Call this function if an error happened
                  error: function (error) {
                          alert('Error: ', error);
                  }
              });
          }else
          {
              alert("Datos Invalidos...");
          }
      }

        function selectTab(id,url,title,tabName)
        {

          var objid=id+CONST_TAB;
          var newTab = dijit.byId(objid);
          if(!url)url=context+"/swbadmin/jsp/objectTab.jsp";
          if(newTab==null)
          {
              newTab = new dojox.layout.ContentPane(
              {
                  id: objid,
                  closeable:'true',
                  onClose: function()
                  {
                      var ret=true;
                      //ret=confirm("Do you really want to Close this?");
                      if(ret)
                      {
                          var d=dijit.byId(objid+"2");
                          if(d)
                          {
                              var arr=d.getChildren();
                              for (var n = 0; n < arr.length; n++)
                              {
                                  arr[n].attr('content',null);
                              }
                          }
                      }
                      return ret;
                  },
                  onDownloadEnd: function()
                  {
                      var ret=true;
                      //ret=confirm("Do you really want to Close this?");
                      if(ret)
                      {
                          var d=dijit.byId(objid+"2");
                          if(d)
                          {
                              var arr=d.getChildren();
                              for (var n = 0; n < arr.length; n++)
                              {
                                  if(arr[n].id==(id+"/"+tabName))
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
                  href: url+"?suri="+encodeURIComponent(id)
              });
              newTab.closable=true;
              tabs.addChild(newTab);
              tabs.selectChild(newTab);
          }else
          {
              tabs.selectChild(newTab);
              objid=id+CONST_TAB+"2";
              var tab = dijit.byId(objid);
              if(tab)
              {
                  var arr=tab.getChildren();
                  //alert(arr.length);
                  for (var n = 0; n < arr.length; n++)
                  {
                      if(arr[n].id==(id+"/"+tabName))
                      {
                           tab.selectChild(arr[n]);
                           break;
                      }
                  }
              }
          }
        }

      function addNewTab(id, url, title)
      {
          var objid=id+CONST_TAB;
          var newTab = dijit.byId(objid);
          if(!url)url=context+"/swbadmin/jsp/objectTab.jsp";
          if(newTab==null)
          {
              newTab = new dojox.layout.ContentPane(
              {
                  id: objid,
                  closeable:'true',
                  onClose: function()
                  {
                      var ret=true;
                      //ret=confirm("Do you really want to Close this?");
                      if(ret)
                      {
                          var d=dijit.byId(objid+"2");
                          if(d)
                          {
                              var arr=d.getChildren();
                              for (var n = 0; n < arr.length; n++)
                              {
                                  arr[n].attr('content',null);
                              }
                          }
                      }
                      return ret;
                  },
                  title: title,
                  href: url+"?suri="+encodeURIComponent(id)
              });
              newTab.closable=true;
              tabs.addChild(newTab);
              tabs.selectChild(newTab);
          }else
          {
              tabs.selectChild(newTab);
          }
      }

      function reloadTab(uri)
      {
          //var aux;
          var objid=uri+CONST_TAB+"2";
          //alert("id:"+objid);
          var tab = dijit.byId(objid);
          if(tab)
          {
              var arr=tab.getChildren();
              for (var n = 0; n < arr.length; n++)
              {
                  //arr[n].refresh();
                  arr[n]._prepareLoad();
              }
          }
      }

      function executeAction(store, item, action)
      {
          act_item=item;
          act_store=store;
          if(action.length)action=action[0];
          //alert("store:"+store+" action:"+action+" ["+action.name+"] "+action.length);
          if(action.name=="reload")
          {
              reloadTreeNode(store,item);
          }else if(action.name=="newTab")
          {
              addNewTab(item.id, action.value, item.title);
          }else if(action.name=="showDialog")
          {
                showDialog(action.value, action.target);
          }else if(action.name=="showStatusURL")
          {
                showStatusURL(action.value);
          }else if(action.name=="getHtml")
          {
              //alert(action.value+" "+action.target);
              var url=""+action.value;
              var tag=""+action.target;
              getHtml(url, tag);
          }
      }

      function executeTreeNodeEvent(store, item, eventname)
      {
            var event=getTreeNodeEvent(store,item,eventname);
            if(event)
            {
                //alert("event:"+event.name+" "+event.action);
                executeAction(store,item,event.action);
            }
      }

      //regrasa evento de nombre eventname asociado al nodo item del store
      function getTreeNodeEvent(store, item, eventname)
      {
            var events=store.getValues(item, "events");
            //var events=item.events;
            if(events)
            {
                for(var x=0;x<events.length;x++)
                {
                    if(events[x].name==eventname)
                    {
                        return events[x];
                    }
                }
            }
            return null;
      }

      function removeChilds(store,item)
      {
          //alert("removeChilds:"+item.children);
          var items=item.children;
          if(items)
          {
              //alert("removeChilds2:"+item.children.length);
              for (var i=0; i<items.length;i++)
              {
                  removeChilds(store,items[i]);
                  store.deleteItem(items[i]);
              }
              store.save();
          }
      }

      //recargo nodo sin hijos de todos los stores
      function updateTreeNodeByURI(uri)
      {
          for(x=0;x<trees.length;x++)
          {
              var s=trees[x].model.store;
              //alert("store:"+trees[x].store+" "+trees[x].model+" "+trees[x].model.store);
              var n=getItem(s,uri);
              if(n)
              {
                 updateTreeNode(s,n);
              }
          }
      }

      //recarga nodo sin hijos
      function updateTreeNode(store, item, jsonNode)
      {
          //alert("Store:"+store+" "+act_store.jsId);
          if(!store)store=act_store;
          if(!item)item=act_item;
          var onlyNode=false;
          if(!jsonNode)
          {
              onlyNode=true;
              jsonNode=getJSON(context+"/swbadmin/jsp/Tree.jsp?suri="+encodeURIComponent(item.id))[0];
          }

          store.setValues(item, "title", jsonNode.title);
          store.setValues(item, "type", jsonNode.type);
          store.setValues(item, "icon", jsonNode.icon);

          if(jsonNode.parent)
          {
              var pite=getItem(store, jsonNode.parent)
              if(pite)
              {
                  //TODO:
                  //alert("son:"+item.id+" newP:"+jsonNode.parent+" old:"+dijit.byId(item.id));
                  //store.setValues(pite, "children", item);
                  //store.setValues(item, "parent", pite);
              }
          }

          if(!onlyNode)
          {
            if(jsonNode.events)store.setValues(item, "events", jsonNode.events);
            else store.unsetAttribute(item, "events");
          }
          if(jsonNode.menus)store.setValues(item, "menus", jsonNode.menus);
          else store.unsetAttribute(item, "menus");
          store.save();
      }

      function setWaitCursor()
      {
          if(act_treeNode && act_treeNode.isTreeNode)
          {
              //alert(act_treeNode);
              act_treeNode.markProcessing();
          }
          document.body.style.cursor="wait";
          dojo.byId("leftAccordion").style.cursor="wait";
      }

      function setDefaultCursor()
      {
          document.body.style.cursor="default";
          dojo.byId("leftAccordion").style.cursor="default";
          if(act_treeNode && act_treeNode.isTreeNode)
          {
              act_treeNode.unmarkProcessing();
          }
      }

      function pasteItemByURIs(uri, oldParentUri, newParentUri)
      {
          for(x=0;x<trees.length;x++)
          {
              var t=trees[x];
              var childItem=getItem(t.model.store,uri);
              var oldParentItem=getItem(t.model.store,oldParentUri);
              var newParentItem=getItem(t.model.store,newParentUri);
              if(childItem && oldParentItem && newParentItem)
              {
                  t.model.pasteItem(childItem, oldParentItem, newParentItem, false);
              }
          }
      }

      function removeTreeNodeByURI(uri)
      {
          for(x=0;x<trees.length;x++)
          {
              var s=trees[x].model.store;
              var n=getItem(s,uri);
              if(n)
              {
                 removeTreeNode(s,n);
              }
          }
      }

      function removeTreeNode(store, item, closetab)
      {
          if(!store)store=act_store;
          if(!item)item=act_item;
          if(!closetab)closetab=true;
          setWaitCursor();
          removeChilds(store,item);
          store.deleteItem(item);
          store.save();

          if(closetab)
          {
              closeTab(item.id);
          }
          setDefaultCursor();
      }

      function closeTab(uri)
      {
          var objid=uri+CONST_TAB;
          var newTab = dijit.byId(objid);
          if(newTab)
          {
              tabs.closeChild(newTab);
          }
      }

      //recarga nodo e hijos
      function reloadTreeNodeByURI(uri)
      {
          for(x=0;x<trees.length;x++)
          {
              var s=trees[x].model.store;
              //alert("store:"+trees[x]+" "+trees[x].id);
              var n=getItem(s,uri);
              if(n)
              {
                 reloadTreeNode(s,n);
              }
          }
      }

      //recarca nodo e hijos
      function reloadTreeNode(store, item)
      {
          if(!store)store=act_store;
          if(!item)item=act_item;
          setWaitCursor();
          //alert("reload:"+item.id);
          removeChilds(store,item);
          var arr=getJSON(context+"/swbadmin/jsp/Tree.jsp?suri="+encodeURIComponent(item.id))
          updateTreeNode(store,item,arr[0]);
          //alert("arr:"+arr[0].id);
          var items=arr[0].children;
          //alert("nitem:"+items.length);
          for (var i=0; i<items.length;i++)
          {
              addItem(store,items[i],item);
          }
          store.save();
          setDefaultCursor(item);
      }

      function addItemByURI(store, parent, uri)
      {
          if(!store)store=act_store;
          setWaitCursor();
          //alert("reload:"+item.id);
          var arr=getJSON(context+"/swbadmin/jsp/Tree.jsp?childs=false&suri="+encodeURIComponent(uri));
          var item=arr[0];
          addItem(store,item,parent);
          store.save();
          setDefaultCursor();
      }

      function addItem(store, item, parent)
      {
          if(getItem(store, item.id))return;
            //alert("addItem:"+item+" "+parent);
            var pInfo;
            if(parent)
            {
                pInfo={parent:parent, attribute:"children"};
            }else
            {
                pInfo={attribute:"children"};
            }
            //alert("addItem2:"+store+" "+pInfo);
            var ite=store.newItem(item,pInfo);
            //alert("hasChilds:"+item.hasChilds+" "+item.children.length);

            var childs=item.children;
            if(childs && childs.length>0)
            {
                for(var x=0;x<childs.length;x++)
                {
                    addItem(store, childs[x], ite);
                }
            }else if(item.hasChilds)
            {
                //alert("hasChilds:"+items[i].id+" "+ite);
                pInfo={parent:ite, attribute:"children"};
                var dummy={"id":item.id+"_tmp_","icon":"swbIconWebSite","title":"dummy"};
                store.newItem(dummy,pInfo);
            }
      }

      function actionDone(){
          alert("Acción realizada.");
      }

      function actionFailed(){
          alert("Error al ejecutar la acción.");
      }

      function loadScript(id, filepath)
      {
         if (document.getElementById(id))
         { // Already exists
             return;
         }
         var head = document.getElementsByTagName("head")[0];
         if(head)
         {
             script = document.createElement('script');
             script.id = id;
             script.type = 'text/javascript';
             script.src = filepath;
             head.appendChild(script);
         }
     }

     var ini=-30;
     var sy=ini;
     var si=10;
     function scroll()
     {
        var t=30;
        var ele=dojo.byId('status');
        if(sy>0)
        {
            si=-2;
            t=3000;
        }
        sy+=si;
        ele.style.bottom=sy+'px';
        if(sy>ini)setTimeout(scroll,t);
     }

     function showError(msg)
     {
        showStatus(msg);
     }

     function showStatus(msg)
     {
         var ele=dijit.byId('status');
         ele.attr('content',msg);
         ele.innerHTML=msg;
         sy=ini;
         si=2;
         scroll();
     }

      function showStatusURL(url, sync)
      {
          var ret=false;
          if(!sync)sync=false;
          var obj=dojo.xhrGet({
              url: url,
              sync: sync,
              load: function(response, ioArgs){
                showStatus(response);
                ret=true;
              },
              error: function(response, ioArgs){
                showStatus("Error: "+response);
              },
              handleAs: "text"
          });
          return ret;
      }

      function getItem(store,id)
      {
          var ret=null;
          var x=store.fetchItemByIdentity(
          {
              identity: id,
              onItem: function(item)
              {
                  ret=item;
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
              for (var c = 0; c < load.length ; c++)
              {
                  var txt=load[c];
                  if(txt.indexOf("dojo/connect")>-1 || txt.indexOf("dojo/method")>-1)continue;
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
          trees[trees.length]=tree;
          //alert(stores.length);
      }

      function setTabTitle(uri,title,icon)
      {
          var objid=uri+CONST_TAB;
          var tab = dijit.byId(objid);
          if(tab!=null)
          {
              var aux=title;
              //if(icon)aux="<span><span style='position:fixed; margin:0px -3px; ' class='"+icon+"'></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+aux+"</span>";
              if(icon)aux="<span style_='height:18px;' class='dijitReset dijitInline "+icon+"'></span><span class='dijitReset dijitInline dijitButtonText'>"+aux+"</span>";
              tab.title=aux;
              tab.controlButton.containerNode.innerHTML = aux || "";
              //alert(tab.title);
          }
      }

      function isParent(parent, child)
      {
          var ret=false;
          if(parent && child && parent!=child)
          {
              while(child)
              {
                  if(child==parent)
                  {
                      ret=true;
                      break;
                  }
                  child=child.getParent();
              }
          }
          return ret;
      }

function selectAll(name,val)
   {
      var field = document.getElementsByName(name);
      for (i = 0; i < field.length; i++)
       field[i].checked = val ;
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

var oldreq="";
var oldret=false;
function canCreateSemanticObject(model, clsid, id)
{
    if(oldreq!=(model+clsid+id))
    {
        oldret=getJSON(context+'/swbadmin/jsp/canCreate.jsp?id='+id+'&clsid='+clsid+'&model='+model);
        oldreq=(model+clsid+id);
    }
    return oldret;
}

var oldlog="";
var oldlret=false;
function canAddLogin(model, slogin)
{
    if (oldlog!=(model+slogin))
    {
        oldlret=getJSON(context+'/swbadmin/jsp/canAddLogin.jsp?login='+slogin+'&model='+model);
        oldlog=(model+slogin);
    }
    return oldlret;
}

function replaceChars4Id(value)
{
    var id="";
    if(value)
    {
        value=value.toLowerCase();
        for(var x=0;x<value.length;x++)
        {
            var ch=value.charAt(x);
            if(ch==' ')ch='_';
            else if(ch=='á')ch='a';
            else if(ch=='à')ch='a';
            else if(ch=='ã')ch='a';
            else if(ch=='â')ch='a';
            else if(ch=='ä')ch='a';
            else if(ch=='å')ch='a';
            else if(ch=='é')ch='e';
            else if(ch=='è')ch='e';
            else if(ch=='ê')ch='e';
            else if(ch=='ë')ch='e';
            else if(ch=='í')ch='i';
            else if(ch=='ì')ch='i';
            else if(ch=='î')ch='i';
            else if(ch=='ï')ch='i';
            else if(ch=='ó')ch='o';
            else if(ch=='ò')ch='o';
            else if(ch=='ô')ch='o';
            else if(ch=='ö')ch='o';
            else if(ch=='õ')ch='o';
            else if(ch=='ú')ch='u';
            else if(ch=='ù')ch='u';
            else if(ch=='ü')ch='u';
            else if(ch=='û')ch='u';
            else if(ch=='ñ')ch='n';
            else if(ch=='ç')ch='c';
            else if(ch=='ÿ')ch='y';
            else if(ch=='ý')ch='y';
            if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_')
            {
                id+=ch;
            }
        }
    }
    return id;
}

   //EditArea
   include(context+"/swbadmin/js/editarea/edit_area/edit_area_full.js");

   function validateChk(name,msg)
   {
      var field = document.getElementsByName(name);
      var ok=false;
      for (i = 0; i < field.length; i++)
       {
           if(field[i].checked)
           {
               ok= true;
           }
       }
       if(ok)
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
       var sizze='width=600, height=550, scrollbars, resizable, alwaysRaised, menubar';
       window.open(url,"RepWindow",sizze);
   }

   function hideApplet(flag)
   {
       if(flag)dojo.query(".applet", "main").style('visibility', 'hidden');
       else dojo.query(".applet", "main").style('visibility', 'visible');
   }