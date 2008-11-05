/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

      var paneId=1;


      dojo.require("dijit.Menu");
      dojo.require("dijit._Calendar");
      dojo.require("dijit.ColorPalette");
      dojo.require("dijit.ProgressBar");
      dojo.require("dijit.TitlePane");
      dojo.require("dijit.Tooltip");
      dojo.require("dijit.Tree");

      dojo.require("dojox.form.TimeSpinner");

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
      //dojo.require("dojo.data.ItemFileReadStore");

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
          dojo.parser.parse(dojo.byId('container')); 
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
      
      function getContentPanel(reference)
      {
          if(!reference)return null;
          //alert("reference:"+reference.id);
          var att=reference.getAttribute("dojoType");
          if(att && att=="dijit.layout.ContentPane")
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
          if(!objd || objd.isValid())
          {
              //alert("entra2");
              dojo.xhrPost ({
                  // The page that parses the POST request
                  contentType: "application/x-www-form-urlencoded; charset=utf-8",                        

                  //handleAs: "text",

                  url: obj.action,

                  // Name of the Form we want to submit
                  form: formid,

                  // Loads this function if everything went ok
                  load: function (data) {
                          var panel=getContentPanel(obj);
                          //alert("div:"+panel);
                          if(panel)
                          {
                              //alert("id:"+panel.id);
                              panel.setContent(data);
                          }
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
                  title:"<span class='mailIconOptions' align='left'></span>"+title, 
                  href:'/swb/swbadmin/jsp/objectTab.jsp?id='+encodeURIComponent(id)
              });
              newTab.closable=true;
              tabs.addChild(newTab);
              tabs.selectChild(newTab);                                    
          }else
          {
              tabs.selectChild(newTab);   
          }

      }
      
      function actionDone(){
          alert("Acción realizada.");
      }
      
      function actionFailed(){
          alert("Error al ejecutar la acción.");
      }      
            
