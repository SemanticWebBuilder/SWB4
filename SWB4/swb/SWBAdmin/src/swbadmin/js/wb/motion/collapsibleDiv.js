// JavaScript Document


/* 
 Ejemplo 1.

    <a href="javascript:;" onmousedown="slidedown('mydiv');">Slide Down</a> |
    <a href="javascript:;" onmousedown="slideup('mydiv');">Slide Up</a>	
    <div id="mydiv" style="display:none; overflow:hidden; height:385px;">
        <h3>This is a test!<br>Can you see me?</h3>
        <h3>This is a test!<br>Can you see me?</h3>
        <h3>This is a test!<br>Can you see me?</h3>
        <h3>This is a test!<br>Can you see me?</h3>
        <h3>This is a test!<br>Can you see me?</h3>
        <h3>This is a test!<br>Can you see me?</h3>
    </div>

  Ejemplo 2.
    <img id="ctrTogle" src="mas.jpg" alt="" onmousedown="collapseDivMgr('mydiv',this.id);" />
    <div id="mydiv" style="display:none; overflow:hidden; height:385px;" class="divs">
        <h3>This is a test!<br>Can you see me?</h3>
        <h3>This is a test!<br>Can you see me?</h3>
        <h3>This is a test!<br>Can you see me?</h3>
        <h3>This is a test!<br>Can you see me?</h3>
        <h3>This is a test!<br>Can you see me?</h3>
        <h3>This is a test!<br>Can you see me?</h3>
    </div>


*/


var timerlen = 5;
var slideAniLen = 500;
var timerID = new Array();
var startTime = new Array();
var obj = new Array();
var endHeight = new Array();
var moving = new Array();
var dir = new Array();

function slidedown(objname){
  if(moving[objname])
	return false;
 
  if(document.getElementById(objname).style.display != "none")
	return false; // cannot slide down something that is already visible
 
  moving[objname] = true;
  dir[objname] = "down";
  startslide(objname);
  
  return true;
}
 
function slideup(objname){
  if(moving[objname])
	return false;
 
  if(document.getElementById(objname).style.display == "none")
	return false; // cannot slide up something that is already hidden
 
  moving[objname] = true;
  dir[objname] = "up";
  startslide(objname);
  
  return true;
}

function startslide(objname){
  obj[objname] = document.getElementById(objname);
 
  endHeight[objname] = parseInt(obj[objname].style.height);
  startTime[objname] = (new Date()).getTime();
 
  if(dir[objname] == "down"){
	obj[objname].style.height = "1px";
  }
 
  obj[objname].style.display = "block";
 
  timerID[objname] = setInterval('slidetick(\'' + objname + '\');',timerlen);
}

function slidetick(objname){
  var elapsed = (new Date()).getTime() - startTime[objname];
 
  if (elapsed > slideAniLen)
	endSlide(objname)
  else {
	var d =Math.round(elapsed / slideAniLen * endHeight[objname]);
	if(dir[objname] == "up")
	  d = endHeight[objname] - d;
 
	obj[objname].style.height = d + "px";
  }
 
  return;
}

function endSlide(objname){
  clearInterval(timerID[objname]);
 
  if(dir[objname] == "up")
	obj[objname].style.display = "none";
 
  obj[objname].style.height = endHeight[objname] + "px";
 
  delete(moving[objname]);
  delete(timerID[objname]);
  delete(startTime[objname]);
  delete(endHeight[objname]);
  delete(obj[objname]);
  delete(dir[objname]);
 
  return;
}

function togleImg(objId, srcImg){
  var e = document.getElementById(objId);
  if(e.hasAttribute("src")) {
    e.src=srcImg;
  }
}

function collapseDivMgr(divId, imgId){
  if(slidedown(divId)) {
    togleImg(imgId, "menos.jpg");
  }else if(slideup(divId)) {
    togleImg(imgId, "mas.jpg");
  }
}
	
