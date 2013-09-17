
//      // Para el recurso SWBASchedule           


 function enableTime(suri) {
     var time='off';
   if (dijit.byId(suri+"_time").checked) {
       time='on';
   }
   if (time=='on') {
       dijit.byId(suri+"_starthour").setDisabled(false);
       dijit.byId(suri+"_endhour").setDisabled(false);
       dijit.byId(suri+"_starthour").focus();
   }
   else if (time=='off') {
       dijit.byId(suri+"_starthour").setDisabled(true);
       dijit.byId(suri+"_endhour").setDisabled(true);
   }
}

 function enableTimer(suri) {
   var waittime='off';
   if (dijit.byId(suri+"_timerinter1").checked) {
       waittime='on';
   } else if (dijit.byId(suri+"_timerinter2").checked) {
       waittime='off';
   }
   if (waittime=='on') {
       dijit.byId(suri+"_timer").setDisabled(false);
       dijit.byId(suri+"_exechour").setDisabled(true);
       dijit.byId(suri+"_endhour").setDisabled(true);
       dijit.byId(suri+"_inter").setDisabled(true);
       dijit.byId(suri+"_time").setDisabled(true);
       dijit.byId(suri+"_timer").focus();
   }
   else if (waittime=='off') {
       dijit.byId(suri+"_timer").setDisabled(true);
       dijit.byId(suri+"_exechour").setDisabled(false);
       dijit.byId(suri+"_time").setDisabled(false);
       enableIntervalTime(suri);
       dijit.byId(suri+"_exechour").focus();
   }
}


 function enableIntervalTime(suri) {
     var time='off';
   if (dijit.byId(suri+"_time").checked) {
       time='on';
   }
   if (time=='on') {
       dijit.byId(suri+"_inter").setDisabled(false);
       dijit.byId(suri+"_endhour").setDisabled(false);
       dijit.byId(suri+"_inter").focus();
   }
   else if (time=='off') {
       dijit.byId(suri+"_inter").setDisabled(true);
       dijit.byId(suri+"_endhour").setDisabled(true);
   }
}
 
 function disablePeriodicity(suri) {
    var useP = false;
    if(dijit.byId(suri+'_periodicidad').checked)
        useP = true;
    if(useP)
     {
         dijit.byId(suri+"_period1").setDisabled(false);
         dijit.byId(suri+"_period2").setDisabled(false);
         dijit.byId(suri+"_period3").setDisabled(false);
      } 
    enableWeekly(suri);
    enableMonthly(suri);
    enableYearly(suri);   
    if(!useP)
     {
         dijit.byId(suri+"_period1").setDisabled(true);
         dijit.byId(suri+"_period2").setDisabled(true);
         dijit.byId(suri+"_period3").setDisabled(true);
     }
 }
 
 function enableWeekly(suri) {
     if(dijit.byId(suri+"_periodicidad").checked && dijit.byId(suri+"_period1").checked)
     {
        dijit.byId(suri+"_period1").setDisabled(false);
        dijit.byId(suri+"_period2").setDisabled(false);
        dijit.byId(suri+"_period3").setDisabled(false);
        dijit.byId(suri+"_wday1").setDisabled(false);
        dijit.byId(suri+"_wday2").setDisabled(false);
        dijit.byId(suri+"_wday3").setDisabled(false);
        dijit.byId(suri+"_wday4").setDisabled(false);
        dijit.byId(suri+"_wday5").setDisabled(false);
        dijit.byId(suri+"_wday6").setDisabled(false);
        dijit.byId(suri+"_wday7").setDisabled(false);
     }
     else if(!dijit.byId(suri+"_periodicidad").checked || !dijit.byId(suri+"_period1").checked)
     {
        dijit.byId(suri+"_wday1").setDisabled(true);
        dijit.byId(suri+"_wday2").setDisabled(true);
        dijit.byId(suri+"_wday3").setDisabled(true);
        dijit.byId(suri+"_wday4").setDisabled(true);
        dijit.byId(suri+"_wday5").setDisabled(true);
        dijit.byId(suri+"_wday6").setDisabled(true);
        dijit.byId(suri+"_wday7").setDisabled(true);
     }
 }

 function enableMonthly(suri) {
      if(dijit.byId(suri+"_periodicidad").checked && dijit.byId(suri+"_period2").checked)
     {
         dijit.byId(suri+"_smonth1").setDisabled(false); 
         dijit.byId(suri+"_smonth2").setDisabled(false);
         if(!dijit.byId(suri+"_smonth1").checked && !dijit.byId(suri+"_smonth2").checked)
             {
                 dijit.byId(suri+"_smonth1").set('checked',true);  
                 dijit.byId(suri+"_smonth2").set('checked',false);
             } 
     }
     
   if(dijit.byId(suri+"_smonth1").checked && dijit.byId(suri+"_smonth2").checked)
     {
         dijit.byId(suri+"_smonth1").set('checked',true);
         dijit.byId(suri+"_smonth2").set('checked',false);
     } 
     
   
   if(dijit.byId(suri+"_periodicidad").checked && dijit.byId(suri+"_period2").checked && dijit.byId(suri+"_smonth1").checked)
     {
         //alert('periodicidad..1');
         
        dijit.byId(suri+"_smonth1").setDisabled(false); 
        dijit.byId(suri+"_mmday").setDisabled(false); 
        dijit.byId(suri+"_mmday2").setDisabled(false); 
        dijit.byId(suri+"_mmonths1").setDisabled(false); 
        
        dijit.byId(suri+"_smonth2").setDisabled(false);
        dijit.byId(suri+"_mweek").setDisabled(true);
        dijit.byId(suri+"_mmonths2").setDisabled(true);
        
        dijit.byId(suri+"_mday1").setDisabled(true);
        dijit.byId(suri+"_mday2").setDisabled(true);
        dijit.byId(suri+"_mday3").setDisabled(true);
        dijit.byId(suri+"_mday4").setDisabled(true);
        dijit.byId(suri+"_mday5").setDisabled(true);
        dijit.byId(suri+"_mday6").setDisabled(true);
        dijit.byId(suri+"_mday7").setDisabled(true);

     }
   else if(dijit.byId(suri+"_periodicidad").checked && dijit.byId(suri+"_period2").checked && dijit.byId(suri+"_smonth2").checked)
     {
         //alert('periodicidad..2');
         
        dijit.byId(suri+"_smonth1").setDisabled(false);
        dijit.byId(suri+"_mmday").setDisabled(true); 
        dijit.byId(suri+"_mmday2").setDisabled(true); 
        dijit.byId(suri+"_mmonths1").setDisabled(true); 
        
        dijit.byId(suri+"_smonth2").setDisabled(false);
        dijit.byId(suri+"_mweek").setDisabled(false);
        dijit.byId(suri+"_mmonths2").setDisabled(false);
        
        dijit.byId(suri+"_mday1").setDisabled(false);
        dijit.byId(suri+"_mday2").setDisabled(false);
        dijit.byId(suri+"_mday3").setDisabled(false);
        dijit.byId(suri+"_mday4").setDisabled(false);
        dijit.byId(suri+"_mday5").setDisabled(false);
        dijit.byId(suri+"_mday6").setDisabled(false);
        dijit.byId(suri+"_mday7").setDisabled(false);   
     }
     else if(!dijit.byId(suri+"_periodicidad").checked || !dijit.byId(suri+"_period2").checked)
     {
         //alert('periodicidad..3');
         
        dijit.byId(suri+"_smonth1").setDisabled(true);
        dijit.byId(suri+"_mmday").setDisabled(true); 
        dijit.byId(suri+"_mmday2").setDisabled(true); 
        dijit.byId(suri+"_mmonths1").setDisabled(true); 
        
        dijit.byId(suri+"_smonth2").setDisabled(true);
        dijit.byId(suri+"_mweek").setDisabled(true);
        dijit.byId(suri+"_mmonths2").setDisabled(true);
        
        dijit.byId(suri+"_mday1").setDisabled(true);
        dijit.byId(suri+"_mday2").setDisabled(true);
        dijit.byId(suri+"_mday3").setDisabled(true);
        dijit.byId(suri+"_mday4").setDisabled(true);
        dijit.byId(suri+"_mday5").setDisabled(true);
        dijit.byId(suri+"_mday6").setDisabled(true);
        dijit.byId(suri+"_mday7").setDisabled(true);
     }
 }
 
 
 function enableYearly(suri) {
     
   if(dijit.byId(suri+"_periodicidad").checked && dijit.byId(suri+"_period3").checked)
     {
         dijit.byId(suri+"_radio1").setDisabled(false); 
         dijit.byId(suri+"_radio2").setDisabled(false); 
         if(!dijit.byId(suri+"_radio1").checked && !dijit.byId(suri+"_radio2").checked)
             {
                 dijit.byId(suri+"_radio1").set('checked',true);
                 dijit.byId(suri+"_radio2").set('checked',false);
             } 
     }
     
   if(dijit.byId(suri+"_radio1").checked && dijit.byId(suri+"_radio2").checked)
     {
         dijit.byId(suri+"_radio1").set('checked',true);
         dijit.byId(suri+"_radio2").set('checked',false);
     } 
   
   if(dijit.byId(suri+"_periodicidad").checked && dijit.byId(suri+"_period3").checked && dijit.byId(suri+"_radio1").checked)
     {
        dijit.byId(suri+"_radio1").setDisabled(false); 
        dijit.byId(suri+"_text1").setDisabled(false); 
        dijit.byId(suri+"_text12").setDisabled(false); 
        dijit.byId(suri+"_selectm1").setDisabled(false); 
        dijit.byId(suri+"_text2").setDisabled(false); 
        
        dijit.byId(suri+"_radio2").setDisabled(false);
        dijit.byId(suri+"_select1").setDisabled(true);
        dijit.byId(suri+"_selectm2").setDisabled(true);
        dijit.byId(suri+"_text3").setDisabled(true); 
        
        dijit.byId(suri+"_yday1").setDisabled(true);
        dijit.byId(suri+"_yday2").setDisabled(true);
        dijit.byId(suri+"_yday3").setDisabled(true);
        dijit.byId(suri+"_yday4").setDisabled(true);
        dijit.byId(suri+"_yday5").setDisabled(true);
        dijit.byId(suri+"_yday6").setDisabled(true);
        dijit.byId(suri+"_yday7").setDisabled(true);

     }
   else if(dijit.byId(suri+"_periodicidad").checked && dijit.byId(suri+"_period3").checked && dijit.byId(suri+"_radio2").checked)
     {
        dijit.byId(suri+"_radio1").setDisabled(false);
        dijit.byId(suri+"_text1").setDisabled(true); 
        dijit.byId(suri+"_text12").setDisabled(true); 
        dijit.byId(suri+"_selectm1").setDisabled(true); 
        dijit.byId(suri+"_text2").setDisabled(true); 
        
        dijit.byId(suri+"_radio2").setDisabled(false);
        dijit.byId(suri+"_select1").setDisabled(false);
        dijit.byId(suri+"_selectm2").setDisabled(false);
        dijit.byId(suri+"_text3").setDisabled(false); 
        
        dijit.byId(suri+"_yday1").setDisabled(false);
        dijit.byId(suri+"_yday2").setDisabled(false);
        dijit.byId(suri+"_yday3").setDisabled(false);
        dijit.byId(suri+"_yday4").setDisabled(false);
        dijit.byId(suri+"_yday5").setDisabled(false);
        dijit.byId(suri+"_yday6").setDisabled(false);
        dijit.byId(suri+"_yday7").setDisabled(false);
   
     }
     else if(!dijit.byId(suri+"_periodicidad").checked || !dijit.byId(suri+"_period3").checked)
     {
        dijit.byId(suri+"_radio1").setDisabled(true);
        dijit.byId(suri+"_text1").setDisabled(true);
        dijit.byId(suri+"_text12").setDisabled(true);
        dijit.byId(suri+"_selectm1").setDisabled(true);
        dijit.byId(suri+"_text2").setDisabled(true);
        
        dijit.byId(suri+"_radio2").setDisabled(true);
        dijit.byId(suri+"_select1").setDisabled(true);
        dijit.byId(suri+"_selectm2").setDisabled(true);
        dijit.byId(suri+"_text3").setDisabled(true); 
        
        dijit.byId(suri+"_yday1").setDisabled(true);
        dijit.byId(suri+"_yday2").setDisabled(true);
        dijit.byId(suri+"_yday3").setDisabled(true);
        dijit.byId(suri+"_yday4").setDisabled(true);
        dijit.byId(suri+"_yday5").setDisabled(true);
        dijit.byId(suri+"_yday6").setDisabled(true);
        dijit.byId(suri+"_yday7").setDisabled(true);
     }
  
 }
 
function selectCombo(obj,id) 
{
   obj.selectedIndex=id;
}