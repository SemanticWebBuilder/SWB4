/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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

      // for the colorpalette
      function setColor(color){
          var theSpan = dojo.byId("outputSpan");
          dojo.style(theSpan,"color",color); 
          theSpan.innerHTML = color;
      }

      // for the calendar
      function myHandler(id,newValue){
          console.debug("onChange for id = " + id + ", value: " + newValue);
      }

      dojo.addOnLoad(function() {

          var start = new Date().getTime();
          //dojo.parser.parse(dojo.byId('container')); 
          console.info("Total parse time: " + (new Date().getTime() - start) + "ms");

          dojo.byId('loaderInner').innerHTML += " done.";
          setTimeout(function hideLoader(){
              var loader = dojo.byId('loader'); 
              dojo.fadeOut({ node: loader, duration:500,
                  onEnd: function(){ 
                      loader.style.display = "none"; 
                  }
              }).play();
          }, 250);
      });

      function getHtml(url, tagid)
      {
          dojo.xhrGet({
              url: url,
              load: function(response, ioArgs){
                  dojo.byId(tagid).innerHTML = response;
                  return response;
              },
              error: function(response, ioArgs){
                  dojo.byId(tagid).innerHTML = 
                      "An error occurred, with response: " + response;
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
                  return data;
              },
              error: function(data){
                  alert("An error occurred, with response: " + data);
                  return data;
              },
              handleAs: "json"
          });
          //alert(url+" "+ret);
          return ret;
      }


      function showDialog(url)
      {
          dojo.xhrGet({
              url: url,
              load: function(response, ioArgs){
                  dijit.byId('swbDialogImp').setContent(response);
                  dijit.byId('swbDialog').show();
                  return response;
              },
              error: function(response, ioArgs){
                  dijit.byId('swbDialogImp').setContent("Error: "+response);
                  dijit.byId('swbDialog').show();
                  return response;
              },
              handleAs: "text"
          });
      }
      
      function getContentPanel(reference)
      {
          if(!reference)return null;
          //alert("reference:"+reference.id);
          var att=reference.getAttribute("dojoType");
          if(att && (att=="dijit.layout.ContentPane" || att=="dojox.layout.ContentPane"))
          {
                  return dijit.byNode(reference);
          }else
          {
                  return getContentPanel(reference.parentNode);
          }
      }
      
      function submitUrl(url, reference)
      {
          var panel=getContentPanel(reference);
          dojo.xhrGet({
              url: url,
              load: function(response, ioArgs){
                  if(panel!=null)panel.setContent(response);
                  return response;
              },
              error: function(response, ioArgs){
                  if(panel!=null)panel.setContent("An error occurred, with response: " + response);
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
              //alert("entra2");
              dojo.xhrPost({
                  // The page that parses the POST request
                  contentType: "application/x-www-form-urlencoded; charset=utf-8",                        

                  //handleAs: "text",

                  url: obj.action,

                  // Name of the Form we want to submit
                  form: fid,

                  // Loads this function if everything went ok
                  load: function (data) {
                          var panel=getContentPanel(obj);
                          //alert("div:"+panel);
                          if(panel)
                          {
                              //alert("id:"+panel.id);
                              panel.setContent(data);
                          }
                          //dijit.byId('swbDialog').hide();
                          //div_node.innerHTML = data;
                          //dojo.parser.parse(div_node,true);
                  },
                  // Call this function if an error happened
                  error: function (error) {
                          console.error ('Error: ', error);
                  }
              });
          }else
          {
              alert("Datos Invalidos...");
          }
      }

      function addNewTab(id, title, icon)
      {
          var objid=id+"/tab";
          var newTab = dijit.byId(objid);
          if(newTab==null)
          {
              newTab = new dijit.layout.ContentPane(
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
                                //d.closeChild(arr[n]);
                                arr[n].setContent(null);
                            }
                          }
                          //alert(d.getChildren());
                          //if(d)d.destroyRecursive();
                          //this.destroy();
                          //return false;
                      }
                      return ret;
                  },
                  title:title, 
                  href:'/swb/swbadmin/jsp/objectTab.jsp?id='+encodeURIComponent(id)
              });
              newTab.closable=true;
              tabs.addChild(newTab);
              tabs.selectChild(newTab);                                    
              //dojo.parser.parse(newTab);
          }else
          {
              tabs.selectChild(newTab);   
          }
      }

      function executeTreeNodeEvent(store, item, eventname)
      {
            var event=getTreeNodeEvent(store,item,eventname);
            if(event)
            {
                //alert("event:"+event.name+" "+event.action);
                if(event.action=="reload")
                {
                    reloadTreeNode(store,item);
                }
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
          var items=item.children;
          if(items)
          {
              for (var i=0; i<items.length;i++)
              {
                  removeChilds(store,items[i]);
                  store.deleteItem(items[i]);
              }
              store.save();
          }
      }

      function reloadTreeNode(store, item)
      {
          //alert("reload:"+item.id);
          removeChilds(store,item);

          var items=getJSON("/swb/swbadmin/jsp/Tree.jsp?suri="+encodeURIComponent(item.id))

          //alert("nitem:"+items.length);
          for (var i=0; i<items.length;i++)
          {
                //alert("item:"+i+" "+items[i].id);
                var pInfo={parent:item, attribute:"children"};
                var ite=store.newItem(items[i],pInfo);
                if(items[i].hasChilds)
                {
                    //alert("hasChilds:"+items[i].id+" "+ite);
                    pInfo={parent:ite, attribute:"children"};
                    var dummy={"id":items[i].id+"_tmp_","icon":"swbIconWebSite","title":"dummy"};
                    store.newItem(dummy,pInfo);
                }
          }
          store.save();
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

