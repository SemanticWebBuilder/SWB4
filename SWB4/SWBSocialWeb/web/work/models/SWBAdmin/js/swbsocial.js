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
            alert(response.status);
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
    
function postSocialHtmlListeners(url, tagid)
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
            //If server is down or session expired
            if(response.status == 0 || response.status == 403){
                //Clear all possible active intervals
                for(var i = 0;  i < 1000; i++){
                    clearInterval(i);
                }
            }
                  
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
    dijit.byId('swbDialog').hide();
    dijit.byId('swbDialogImp').attr('content','');        
}
    
function scaleImage(srcwidth, srcheight, targetwidth, targetheight, fLetterBox) {
    var result = {
        width: 0, 
        height: 0, 
        fScaleToTargetWidth: true
    };

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
    
function showHideConversation(id){
    if (document.getElementById(id)){
        var el = document.getElementById(id);
        if(el.style.display == 'none'){
            el.removeAttribute('style');
        }else{
            el.setAttribute('style','display:none;');
        }
    }
}
    
function stopListener(url){
    dojo.xhrGet({
        url: url,
        load: function(response, ioArgs)
        {
            //console.log('Listener stoped:' + response)
            return response;
        },
        error: function(response, ioArgs){
            console.log("Error Stoping listener!");
        },
        handleAs: "text"
    });
}
    
function saveInterval(url){
    dojo.xhrGet({
        url: url,
        load: function(response, ioArgs)
        {
            //console.log('Saving interval:' + response)
        },
        error: function(response, ioArgs){
            console.log("Error saving interval!");
        },
        handleAs: "text"
    });
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

    if(frm.checkYT.length == null){//when there is only one Youtube account
        if(frm.checkYT.checked){
            div.removeAttribute('style');
        }else{
            div.setAttribute('style', 'display:none;');
        }
    }else{//Has length method, then it's an array
        var isChecked = false;
        for (i = 0; i < frm.checkYT.length; i++) {
            if(frm.checkYT[i].checked){//When a checkbox is checked
                isChecked = true;
                break;
            }
        }
        if(isChecked){
            div.removeAttribute('style');
        }else{
            div.setAttribute('style', 'display:none;');
        }
    }
}
/**
 * selectNetwork - cuando se responde a un mensaje de entrada no se debe de validar
 *                 porque ya hay una red hacia donde se va a responder
 *                 true-> debe de seleccionar una red
 *                 false-> ya hay una red seleccionada (la red que recibio el mensaje)
 * //TODO: REVISAR DE NUEVO LAS VALIDACIONES PORQUE SE PUEDE RESPONDER DE OTRAS REDES!!!
 */       
function checksRedesPhoto(objUri, sourceCall, selectNetwork){
    var frm = document.getElementById(objUri+sourceCall+'frmUploadPhoto');
    var checkRed = false;          
    if(selectNetwork == false){//Ya hay una red seleccionada porque se esta respondiendo
        return true;
    }
          
    if(frm.checkRedes == null){//No networks
        alert("Debes crear primero una red en donde publicar");
        return false;
    }
          
    if(frm.checkRedes.length == null){
        if(frm.checkRedes.checked){//there is only one network
            checkRed = true;
        }
    }else{
        for (i = 0; i < frm.checkRedes.length; i++) {
            if (frm.checkRedes[i].checked){
                checkRed = true;
                break;
            }
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
function checksRedesText(objUri, sourceCall, selectNetwork){
    var frm = document.getElementById(objUri+sourceCall+'frmUploadText');
    var checkRed = false;
          
    if(selectNetwork == false){//Ya hay una red seleccionada porque se esta respondiendo
        return true;
    }
    if(frm.checkRedes == null){//No networks
        alert("Debes crear primero una red en donde publicar");
        return false;
    }
          
    if(frm.checkRedes.length == null){
        if(frm.checkRedes.checked){
            checkRed = true;
        }
    }else{
        for (i = 0; i < frm.checkRedes.length; i++) {
            if(frm.checkRedes[i].checked){
                checkRed = true;
                break;
            }
        }
    }
    if(checkRed == false){
        alert("Debes seleccionar al menos una red.");
        return false;
    }else{
        return true;
    }
}
function validateChecks(objUri, sourceCall, selectNetwork){
    var frm = document.getElementById(objUri+sourceCall+'frmUploadVideo');
    var checkYT = false;
    var checkRed = false;
          
    if(selectNetwork == false){//Ya hay una red seleccionada porque se esta respondiendo
        return true;
    }
    if(frm.checkRedes == null && frm.checkYT == null){//No networks found for facebook or youtube
        alert("Debes crear primero una red en donde publicar");
        return false;
    }
    if(frm.checkYT != null){
        if(frm.checkYT.length == null){
            if(frm.checkYT.checked){
                checkYT = true;
            }
        }else{
            for (i = 0; i < frm.checkYT.length; i++) {
                if (frm.checkYT[i].checked){
                    checkYT = true;
                    break;
                }
            }
        }
    }
          
    if(frm.checkRedes != null){
        if(frm.checkRedes.length == null){
            if(frm.checkRedes.checked){
                checkRed = true;
            }
        }else{
            for (j = 0; j < frm.checkRedes.length; j++) {
                if (frm.checkRedes[j].checked){
                    checkRed = true;
                    break;
                }
            }
        }
    }
        
    if(checkYT == false && checkRed == false){
        alert("Debes seleccionar al menos una red");
        return false;
    }
    else{
        return true;
    }
}
      
      
function disableSelect(source){
    var children = source.parentNode.childNodes;
    for(var i = 0; i < children.length; i++){
        if(children[i].nodeName == "SELECT"){
            if(source.checked){
                children[i].removeAttribute('style');
                children[i].removeAttribute('disabled');
            }else{
                children[i].setAttribute('disabled','disabled');
                children[i].setAttribute('style','display:none');
            }
        }
    }  
}
function reloadSocialTab(uri){
    var objid=uri;
    var tab = dijit.byId(objid);
    if(tab)
    {
        var arr=tab.getChildren();
        for (var n = 0; n < arr.length; n++)
        {
            if(arr[n].selected === true){        
                arr[n].refresh();
                break;
            }
        }
    }
}

function validateImages(uri, formId){
    var obj = document.getElementById(uri);
    var images = new Array();
    var children = obj.childNodes;
    var haveFacebook = false;
    var haveTwitter = false;
    var typeFile = ['.jpg','.jpeg','.gif','.png']; 
    var checkYT = false;
    var checkRed = false;
    
    if(children != null && children.length > 0){
        lookForInputs(children, images);
    }

    if(images.length === 0){
        alert("Debes adjuntar al menos una imagen");
        return false;
    }
    var frm = document.getElementById(formId);
    
    if(frm.checkRedes == null){//No networks
        alert("Debes crear primero una red en donde publicar");
        return false;
    }


    if(frm.checkYT != null){

        if(frm.checkRedes.length == null){
            if(frm.checkRedes.checked){//there is only one network
                //checkRed = true;
                checkYT = true;
                if(frm.checkRedes.name.indexOf("#social_Twitter:") != -1){
                    haveTwitter = true;
                    //console.log('twitterFOUND');
                }
                if(frm.checkRedes.name.indexOf("#social_Facebook:") != -1){
                    haveFacebook = true
                    //console.log('FacebookFOUND');
                }
            }
        }else{
            for (i = 0; i < frm.checkRedes.length; i++) {
                if (frm.checkRedes[i].checked){//Network is selected
                    checkYT = true;
                    if(frm.checkRedes[i].name.indexOf("#social_Twitter:") != -1){
                        haveTwitter = true;
                        //console.log('twitterFOUND');
                    }
                    if(frm.checkRedes[i].name.indexOf("#social_Facebook:") != -1){
                        haveFacebook = true
                        //console.log('FacebookFOUND');
                    }
                }
            }
        }
    }
    if(frm.checkRedes != null){
        if(frm.checkRedes.length == null){
            if(frm.checkRedes.checked){
                checkRed = true;
            }
        }else{
            for (j = 0; j < frm.checkRedes.length; j++) {
                if (frm.checkRedes[j].checked){
                    checkRed = true;
                    break;
                }
            }
        }
    }
    
    if(checkYT == false && checkRed == false){
        alert("Debes seleccionar al menos una red");
        return false;
    }
    
    //console.log("haveFacebook:" + haveFacebook);
    //console.log("haveTwitter:" + haveTwitter);
    var count = 0;
    //if(haveTwitter){
    for(i = 0; i < typeFile.length; i++){
       console.log("typeFile"+typeFile[i]);
        
        for(j=0; j < images.length ; j++ ){
            var image = images[j].toLowerCase();
            var imgExt = image.substring(image.lastIndexOf(".") + 1);
            
            if(typeFile[i].indexOf(imgExt)!= -1 ){//si valido           
                count++;               
            }            
                
        }
        //}
    }
    
    if(count != images.length  ){
        alert('Extension de archivo no valida');
        return false;         
    }
    return true;
}

function lookForInputs(children, images){
    if(children == null){
        return;
    }else{
        for(var i = 0; i < children.length; i++){
            //console.log(children[i]);
            if(children[i].nodeName === "INPUT"){
                if(children[i].value != null && children[i].value != "")
                {
                    images.push(children[i].value);
                }
            }
            var siblings = children[i].childNodes;            
            lookForInputs(siblings, images);
        }
    }
}

function validateVideo(id, formId){
    var nameFile = document.getElementById(id).value;
    var arrFacebook = [".3g2", ".3gp", ".3gpp" , ".asf", ".avi", ".dat", ".divx", ".dv", ".asf", ".f4v", ".flv", ".m2ts", ".m4v", ".mkv", ".mod", ".mov", ".mp4", ".mpe", ".mpeg", ".mpeg4", ".mpg", ".mts", ".nsv", ".ogm", ".nsv", ".ogv", ".qt", ".tod", ".ts", ".vob", ".wmv"];                           
    var arrYoutube = ['mp3','.jpg','.jpeg','.gif','.png','.bmp','.wav','.aac','.mswmm','.wlmp'];
    var j =0 ;
    
    if(nameFile == ""){        
        alert('Debes adjuntar un video');
        return false;        
    }
    
    
    var haveFacebook = false;
    var haveYoutube = false;
    var checkYT = false;
    var checkRed = false;
     

    var frm = document.getElementById(formId);
    
    if(frm.checkRedes == null){//No networks
        alert("Debes crear primero una red en donde publicar");
        return false;
    }


    if(frm.checkYT != null){

        if(frm.checkYT.length == null){
            if(frm.checkYT.checked){//there is only one network
                //checkRed = true;
                checkYT = true;
                if(frm.checkRedes.name.indexOf("#social_Youtube:") != -1){
                    haveYoutube = true;
                }
                if(frm.checkRedes.name.indexOf("#social_Facebook:") != -1){
                    haveFacebook = true
                }
            }
        }else{
            for (i = 0; i < frm.checkYT.length; i++) {
                if (frm.checkYT[i].checked){//Network is selected
                    checkYT = true;
                    if(frm.checkRedes[i].name.indexOf("#social_Youtube:") != -1){
                        haveYoutube = true;
                    }
                    if(frm.checkRedes[i].name.indexOf("#social_Facebook:") != -1){
                        haveFacebook = true
                    }
                }
            }
        }
    }
    if(frm.checkRedes != null){
        if(frm.checkRedes.length == null){
            if(frm.checkRedes.checked){
                checkRed = true;
                    if(frm.checkRedes.name.indexOf("#social_Youtube:") != -1){
                        haveYoutube = true;
                    }
                    if(frm.checkRedes.name.indexOf("#social_Facebook:") != -1){
                        haveFacebook = true
                    }
            }
        }else{
            for (j = 0; j < frm.checkRedes.length; j++) {
                if (frm.checkRedes[j].checked){
                    checkRed = true;
                        if(frm.checkRedes.name.indexOf("#social_Youtube:") != -1){
                        haveYoutube = true;
                    }
                    if(frm.checkRedes.name.indexOf("#social_Facebook:") != -1){
                        haveFacebook = true
                    }
                    break;
                }
            }
        }
    }
    
    if(checkYT == false && checkRed == false){
        alert("Debes seleccionar al menos una red");
        return false;
    }
    
    
    var countFacebook = 0;
    var countYoutube =0


  
    if(haveFacebook){        
        for(i = 0; i < arrFacebook.length; i++){
            
            if(nameFile.indexOf(arrFacebook[i]) != -1){ //la extension si es valida    
                countFacebook++;
                break;
            }            
        } 
    }
    
    if(haveYoutube){        
        for(i = 0; i < arrYoutube.length; i++){
            if(nameFile.indexOf(arrYoutube[i]) != -1){ //la extension si es valida    
                countYoutube++;
                break;
            }            
        } 
    }
    
    if(countFacebook == 0 || countYoutube != 0){
        alert('Extension de archivo no valida');
        return false; 
        
    }
    
    return true;
}