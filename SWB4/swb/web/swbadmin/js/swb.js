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

//      // Para el recurso SWBASchedule           


 function enableTime(suri) {
     var time='off';
   if (dijit.byId(suri+"/time").checked) {
       time='on';
   }
   if (time=='on') {
       dijit.byId(suri+"/starthour").setDisabled(false);
       dijit.byId(suri+"/endhour").setDisabled(false);
       dijit.byId(suri+"/starthour").focus();
   }
   else if (time=='off') {
       dijit.byId(suri+"/starthour").setDisabled(true);
       dijit.byId(suri+"/endhour").setDisabled(true);
   }
}
 
 function disablePeriodicity(suri) {
    var useP = false;
    if(dijit.byId(suri+'/periodicidad').checked)
        useP = true;
     if(useP)
         {
             dijit.byId(suri+"/period1").setDisabled(false);
             dijit.byId(suri+"/period2").setDisabled(false);
             dijit.byId(suri+"/period3").setDisabled(false);
          } 
     enableWeekly(suri);
     enableMonthly(suri);
     enableYearly(suri);   
     if(!useP)
         {
             dijit.byId(suri+"/period1").setDisabled(true);
             dijit.byId(suri+"/period2").setDisabled(true);
             dijit.byId(suri+"/period3").setDisabled(true);
         }
 }
 
 function enableWeekly(suri) {
     if(dijit.byId(suri+"/periodicidad").checked && dijit.byId(suri+"/period1").checked)
     {
        dijit.byId(suri+"/period1").setDisabled(false);
        dijit.byId(suri+"/period2").setDisabled(false);
        dijit.byId(suri+"/period3").setDisabled(false);
        dijit.byId(suri+"/wday1").setDisabled(false);
        dijit.byId(suri+"/wday2").setDisabled(false);
        dijit.byId(suri+"/wday3").setDisabled(false);
        dijit.byId(suri+"/wday4").setDisabled(false);
        dijit.byId(suri+"/wday5").setDisabled(false);
        dijit.byId(suri+"/wday6").setDisabled(false);
        dijit.byId(suri+"/wday7").setDisabled(false);
     }
     else if(!dijit.byId(suri+"/periodicidad").checked || !dijit.byId(suri+"/period1").checked)
     {
        dijit.byId(suri+"/wday1").setDisabled(true);
        dijit.byId(suri+"/wday2").setDisabled(true);
        dijit.byId(suri+"/wday3").setDisabled(true);
        dijit.byId(suri+"/wday4").setDisabled(true);
        dijit.byId(suri+"/wday5").setDisabled(true);
        dijit.byId(suri+"/wday6").setDisabled(true);
        dijit.byId(suri+"/wday7").setDisabled(true);
     }
 }

 function enableMonthly(suri) {
   if(dijit.byId(suri+"/periodicidad").checked && dijit.byId(suri+"/period2").checked && dijit.byId(suri+"/smonth1").checked)
     {
        dijit.byId(suri+"/smonth1").setDisabled(false); 
        dijit.byId(suri+"/mmday").setDisabled(false); 
        dijit.byId(suri+"/mmonths1").setDisabled(false); 
        
        dijit.byId(suri+"/smonth2").setDisabled(false);
        dijit.byId(suri+"/mweek").setDisabled(true);
        dijit.byId(suri+"/mmonths2").setDisabled(true);
        
        dijit.byId(suri+"/mday1").setDisabled(true);
        dijit.byId(suri+"/mday2").setDisabled(true);
        dijit.byId(suri+"/mday3").setDisabled(true);
        dijit.byId(suri+"/mday4").setDisabled(true);
        dijit.byId(suri+"/mday5").setDisabled(true);
        dijit.byId(suri+"/mday6").setDisabled(true);
        dijit.byId(suri+"/mday7").setDisabled(true);

     }
   else if(dijit.byId(suri+"/periodicidad").checked && dijit.byId(suri+"/period2").checked && dijit.byId(suri+"/smonth2").checked)
     {
        dijit.byId(suri+"/smonth1").setDisabled(false);
        dijit.byId(suri+"/mmday").setDisabled(true); 
        dijit.byId(suri+"/mmonths1").setDisabled(true); 
        
        dijit.byId(suri+"/smonth2").setDisabled(false);
        dijit.byId(suri+"/mweek").setDisabled(false);
        dijit.byId(suri+"/mmonths2").setDisabled(false);
        
        dijit.byId(suri+"/mday1").setDisabled(false);
        dijit.byId(suri+"/mday2").setDisabled(false);
        dijit.byId(suri+"/mday3").setDisabled(false);
        dijit.byId(suri+"/mday4").setDisabled(false);
        dijit.byId(suri+"/mday5").setDisabled(false);
        dijit.byId(suri+"/mday6").setDisabled(false);
        dijit.byId(suri+"/mday7").setDisabled(false);   
     }
     else if(!dijit.byId(suri+"/periodicidad").checked || !dijit.byId(suri+"/period2").checked)
     {
        dijit.byId(suri+"/smonth1").setDisabled(true);
        dijit.byId(suri+"/mmday").setDisabled(true); 
        dijit.byId(suri+"/mmonths1").setDisabled(true); 
        
        dijit.byId(suri+"/smonth2").setDisabled(true);
        dijit.byId(suri+"/mweek").setDisabled(true);
        dijit.byId(suri+"/mmonths2").setDisabled(true);
        
        dijit.byId(suri+"/mday1").setDisabled(true);
        dijit.byId(suri+"/mday2").setDisabled(true);
        dijit.byId(suri+"/mday3").setDisabled(true);
        dijit.byId(suri+"/mday4").setDisabled(true);
        dijit.byId(suri+"/mday5").setDisabled(true);
        dijit.byId(suri+"/mday6").setDisabled(true);
        dijit.byId(suri+"/mday7").setDisabled(true);
     }

 }
 
 
 function enableYearly(suri) {
   if(dijit.byId(suri+"/periodicidad").checked && dijit.byId(suri+"/period3").checked && dijit.byId(suri+"/radio1").checked)
     {
        dijit.byId(suri+"/radio1").setDisabled(false); 
        dijit.byId(suri+"/text1").setDisabled(false); 
        dijit.byId(suri+"/selectm1").setDisabled(false); 
        dijit.byId(suri+"/text2").setDisabled(false); 
        
        dijit.byId(suri+"/radio2").setDisabled(false);
        dijit.byId(suri+"/select1").setDisabled(true);
        dijit.byId(suri+"/selectm2").setDisabled(true);
        dijit.byId(suri+"/text3").setDisabled(true); 
        
        dijit.byId(suri+"/yday1").setDisabled(true);
        dijit.byId(suri+"/yday2").setDisabled(true);
        dijit.byId(suri+"/yday3").setDisabled(true);
        dijit.byId(suri+"/yday4").setDisabled(true);
        dijit.byId(suri+"/yday5").setDisabled(true);
        dijit.byId(suri+"/yday6").setDisabled(true);
        dijit.byId(suri+"/yday7").setDisabled(true);

     }
   else if(dijit.byId(suri+"/periodicidad").checked && dijit.byId(suri+"/period3").checked && dijit.byId(suri+"/radio2").checked)
     {
        dijit.byId(suri+"/radio1").setDisabled(false);
        dijit.byId(suri+"/text1").setDisabled(true); 
        dijit.byId(suri+"/selectm1").setDisabled(true); 
        dijit.byId(suri+"/text2").setDisabled(true); 
        
        dijit.byId(suri+"/radio2").setDisabled(false);
        dijit.byId(suri+"/select1").setDisabled(false);
        dijit.byId(suri+"/selectm2").setDisabled(false);
        dijit.byId(suri+"/text3").setDisabled(false); 
        
        dijit.byId(suri+"/yday1").setDisabled(false);
        dijit.byId(suri+"/yday2").setDisabled(false);
        dijit.byId(suri+"/yday3").setDisabled(false);
        dijit.byId(suri+"/yday4").setDisabled(false);
        dijit.byId(suri+"/yday5").setDisabled(false);
        dijit.byId(suri+"/yday6").setDisabled(false);
        dijit.byId(suri+"/yday7").setDisabled(false);
   
     }
     else if(!dijit.byId(suri+"/periodicidad").checked || !dijit.byId(suri+"/period3").checked)
     {
        dijit.byId(suri+"/radio1").setDisabled(true);
        dijit.byId(suri+"/text1").setDisabled(true);
        dijit.byId(suri+"/selectm1").setDisabled(true);
        dijit.byId(suri+"/text2").setDisabled(true);
        
        dijit.byId(suri+"/radio2").setDisabled(true);
        dijit.byId(suri+"/select1").setDisabled(true);
        dijit.byId(suri+"/selectm2").setDisabled(true);
        dijit.byId(suri+"/text3").setDisabled(true); 
        
        dijit.byId(suri+"/yday1").setDisabled(true);
        dijit.byId(suri+"/yday2").setDisabled(true);
        dijit.byId(suri+"/yday3").setDisabled(true);
        dijit.byId(suri+"/yday4").setDisabled(true);
        dijit.byId(suri+"/yday5").setDisabled(true);
        dijit.byId(suri+"/yday6").setDisabled(true);
        dijit.byId(suri+"/yday7").setDisabled(true);
     }
  
 }
 
