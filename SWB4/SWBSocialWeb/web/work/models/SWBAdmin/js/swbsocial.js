/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

    function resetTabTitle (objUri, tabId, title){
        var tabId = objUri + tabId;
        var pane = dijit.byId(tabId);                   
        try{
            var aux=title;
            pane.title = aux;
            pane.controlButton.containerNode.innerHTML = aux;
        }catch(noe){
            console.log('Error setting title to default value: ' + noe);
        }
    }
      
    function appendHtmlAt(url, tagid, location){
     dojo.xhrPost({
        url: url,
        load: function(response)
        {
            var tag=dojo.byId(tagid);
            if(tag){
                var pan=dijit.byId(tagid);
                if(pan && pan.attr)
                {
                    if(location == "bottom"){
                       pan.attr('content', tag.innerHTML + response);
                    }else if(location == "top"){
                       pan.attr('content', response + tag.innerHTML);
                    }
                }else
                {
                    if(location == "bottom"){
                       tag.innerHTML = tag.innerHTML + response;
                    }else if(location == "top"){
                       tag.innerHTML = response + tag.innerHTML;
                    }
                }
            }else {
                //alert("No existe ningun elemento con id " + tagid);
            }
            return response;
        },
        error: function(response)
        {
            if(dojo.byId(tagid)) {
                //dojo.byId(tagid).innerHTML = "<p>Ocurrio un error con respuesta:<br />" + response + "</p>";
            }else {
                //alert("No existe ningun elemento con id " + tagid);
            }
            return response;
        },
        handleAs: "text"
    });
    }
    
    function postSocialHtml(url, tagid)
      {
          dojo.xhrPost({
              url: url,
              load: function(response)
              {
                  var tag=dojo.byId(tagid);
                  if(tag){
                      var pan=dijit.byId(tagid);
                      if(pan && pan.attr)
                      {
                          pan.attr('content',response);
                      }else
                      {
                          tag.innerHTML = response;
                      }
                  }else {
                      console.log('Tag not found: ' + tagid);
                  }
                  return response;
              },
              error: function(response)
              {
                  if(dojo.byId(tagid)) {
                      //dojo.byId(tagid).innerHTML = "<p>Ocurrio un error con respuesta:<br />" + response + "</p>";
                  }else {
                      console.log('Tag not found: ' + tagid);
                  }
                  return response;
              },
              handleAs: "text"
          });
      }
    
    function imageLoad(imgObject, imgParam) {
        var tag = dojo.byId(imgParam);
        var img = new Image();
        img = imgObject;

        // what's the size of this image and it's parent
        var w = img.width;        
        var h = img.height;
        var tw = tag.style.width.replace('px', '');
        var th = tag.style.height.replace('px', '');
        
        var scale;
        var wi;
        var he;
        
        // compute the new size and offsets
        if(w < tw && h < th){//If the image fits
            tag.style.width = w + 'px';
            tag.style.height = h + 'px';
            return;
        }else if(w > h){//landscape
            scale = tw / w ;            
        }else if(h > w){//portrait
            scale = th / h ;            
        }else{//squared
            scale = th / w ;
        }
        wi = scale * w;
        he = scale * h;
        img.width = wi;
        img.height = he;
        
        tag.style.width = wi + 'px';
        tag.style.height = he + 'px';
    }
    
    //Used to display video and pictures
    function showSocialDialog(url, title){
        dojo.xhrGet({
            url: url,
            load: function(response, ioArgs){
                dijit.byId('swbSocialDialogImp').attr('content',response);
                dijit.byId('swbSocialDialog').show();
                setSocialDialogTitle(title);
                return response;
            },
            error: function(response, ioArgs){
                showStatus('Error:'+response);
                return response;
            },
            handleAs: "text"
        });
    }
	  
    function setSocialDialogTitle(title){
        if(title)dijit.byId('swbSocialDialog').titleNode.innerHTML=title;
    }
	  
    function hideSocialDialog(){
        dijit.byId('swbSocialDialog').hide();
        dijit.byId('swbSocialDialogImp').attr('content','');        
    }
    
    function scaleImage(srcwidth, srcheight, targetwidth, targetheight, fLetterBox) {
        var result = { width: 0, height: 0, fScaleToTargetWidth: true };

        if ((srcwidth <= 0) || (srcheight <= 0) || (targetwidth <= 0) || (targetheight <= 0)) {
            return result;
        }

        // scale to the target width
        var scaleX1 = targetwidth;
        var scaleY1 = (srcheight * targetwidth) / srcwidth;

        // scale to the target height
        var scaleX2 = (srcwidth * targetheight) / srcheight;
        var scaleY2 = targetheight;

        // now figure out which one we should use
        var fScaleOnWidth = (scaleX2 > targetwidth);
        if (fScaleOnWidth) {
            fScaleOnWidth = fLetterBox;
        }
        else {
           fScaleOnWidth = !fLetterBox;
        }

        if (fScaleOnWidth) {
            result.width = Math.floor(scaleX1);
            result.height = Math.floor(scaleY1);
            result.fScaleToTargetWidth = true;
        }
        else {
            result.width = Math.floor(scaleX2);
            result.height = Math.floor(scaleY2);
            result.fScaleToTargetWidth = false;
        }
        result.targetleft = Math.floor((targetwidth - result.width) / 2);
        result.targettop = Math.floor((targetheight - result.height) / 2);
        return result;
    }

    function showFullImage (image) {
	var img = new Image();
	img = image;

	// what's the size of this image and it's parent
	var w = img.width;        
	var h = img.height;
	var tw=img.parentNode.style.width.replace('px', '');
	var th=img.parentNode.style.height.replace('px', '');
        
        /*if(w <= 320 && h <= 240){
            tw= 320;
            th=240;
            img.parentNode.style.width = '320px';
            img.parentNode.style.height = '240px';
        }*/

        // compute the new size and offsets
        var result = scaleImage(w, h, tw, th, true);
        // adjust the image coordinates and size
        img.width = result.width;
        img.height = result.height;
        //offset
        img.style.left =result.targetleft + 'px';
        img.style.top = result.targettop + 'px';
    }
    
      function changeClassT(objUri, sourceCall) {
          var divId = objUri +  sourceCall +'Txt';
          console.log('DIV:' + divId);
       if(document.getElementById(divId).className == 'sel-txt'){
            document.getElementById(objUri +  sourceCall +'Txt').className ='sel-txt2';
            document.getElementById(objUri +  sourceCall +'Img').className ='sel-img';
            document.getElementById(objUri +  sourceCall +'Video').className ='sel-vid';
        }
    }
     function changeClassI(objUri,sourceCall) { 
       if(document.getElementById(objUri +  sourceCall +'Img').className =='sel-img'){
            document.getElementById(objUri +  sourceCall +'Img').className ='sel-img2';
            document.getElementById(objUri +  sourceCall +'Txt').className ='sel-txt';
            document.getElementById(objUri +  sourceCall +'Video').className ='sel-vid';
        }
     }
     function changeClassV(objUri,sourceCall) { 
       if(document.getElementById(objUri +  sourceCall +'Video').className =='sel-vid'){
            document.getElementById(objUri +  sourceCall +'Video').className ='sel-vid2';
            document.getElementById(objUri +  sourceCall +'Txt').className ='sel-txt';
            document.getElementById(objUri +  sourceCall +'Img').className ='sel-img';
        }
    }
    function showListCategory(objUri,sourceCall) {
                var frm = document.getElementById(objUri+sourceCall+'frmUploadVideo');
                var div = document.getElementById(objUri+sourceCall+'divCategory');

                for (i = 0; i < frm.checkYT.length; ++i) {
                    if (frm.checkYT[i].checked)
                    {
                        div.removeAttribute('style');
                        break;
                    }
                    if (frm.checkYT[i].checked == false)
                    {
                        div.setAttribute('style', 'display:none;');
                    }
                }
            }
     function checksRedesPhoto(objUri,sourceCall){
          var frm = document.getElementById(objUri+sourceCall+'frmUploadPhoto');
          var checkRed = false;
           for (i = 0; i < frm.checkRedes.length; i++) {
                    if (frm.checkRedes[i].checked)
                    {
                        checkRed = true;
                        break;
                    }
           }
           if(checkRed == false){
                  alert("Debes seleccionar al menos una red");
                   return false;
                }
                else{
                    return true;
                }
     }
     function checksRedesText(objUri,sourceCall){
          var frm = document.getElementById(objUri+sourceCall+'frmUploadText');
          var checkRed = false;
           for (i = 0; i < frm.checkRedes.length; i++) {
                    if (frm.checkRedes[i].checked)
                    {
                        checkRed = true;
                        break;
                    }
           }
           if(checkRed == false){
                  alert("Debes seleccionar al menos una red");
                   return false;
                }
                else{
                    return true;
                }
     }
     function validateChecks(objUri,sourceCall){
          var frm = document.getElementById(objUri+sourceCall+'frmUploadVideo');
          var checkYT = false;
          var checkRed = false;
           for (i = 0; i < frm.checkYT.length; i++) {
                    if (frm.checkYT[i].checked)
                    {
                        checkYT = true;
                        break;
                    }
           }
           for (j = 0; j < frm.checkRedes.length; j++) {
                    if (frm.checkRedes[j].checked)
                    {
                        checkRed = true;
                        break;
                    }
           }
                if((checkYT == false) && (checkRed == false)){
                    alert("Debes seleccionar al menos una red");
                    return false;
                }
                else{
                    return true;
                }
            }