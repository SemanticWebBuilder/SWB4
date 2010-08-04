//** DD Drop Down Panel- (c) Dynamic Drive DHTML code library: http://www.dynamicdrive.com
//** Dec 7th, 08'- Script created (Requires jquery 1.2.x)

/*var simpleGallery_navpanel={
	panel: {height:'45px', opacity:0.5, paddingTop:'5px', fontStyle:'bold 9px Verdana'}, //customize nav panel container
	images: [ 'themes/control_rewind_blue.png', 'themes/control_play_blue.png', 'themes/control_fastforward_blue.png', 'themes/control_pause_blue.png'], //nav panel images (in that order)
	imageSpacing: {offsetTop:[-3, 0, -3], spacing:10}, //top offset of left, play, and right images, PLUS spacing between the 3 images
	slideduration: 500 //duration of slide up animation to reveal panel
}*/

function simpleGallery(settingarg){
    this.setting=settingarg;
    settingarg=null
    var setting=this.setting
    setting.pause=parseInt(setting.pause)
    setting.fadeduration=parseInt(setting.fadeduration)
    setting.curimage=(setting.persist)? simpleGallery.routines.getCookie("gallery-"+setting.wrapperid) : 0
    setting.curimage=setting.curimage || 0 //account for curimage being null if cookie is empty
    setting.ispaused=!setting.autoplay //ispaused reflects current state of gallery, autoplay indicates whether gallery is set to auto play
    setting.fglayer=0, setting.bglayer=1 //index of active and background layer (switches after each change of slide)
    setting.oninit=setting.oninit || function(){}
    setting.onslide=setting.onslide || function(){}
    this.thumbnails=[];
    this.images=[];
    for (var i=0; i<setting.imagearray.length; i++){
        this.thumbnails[i]=new Image();
        this.thumbnails[i].src=setting.imagearray[i][1];
        this.images[i]=new Image();
        this.images[i].src=setting.imagearray[i][0];
    }
        
    var slideshow=this
    jQuery(document).ready(function($){
        var setting=slideshow.setting
        setting.$wrapperdiv=$('#'+setting.wrapperid).css({position:'relative', visibility:'visible', background:'black', overflow:'hidden', width:setting.dimensions[0], height:setting.dimensions[1]}).empty() //main gallery DIV
        if (setting.$wrapperdiv.length==0){ //if no wrapper DIV found
                alert("Error: DIV with ID \""+setting.wrapperid+"\" not found on page.")
                return
        }
        setting.$gallerylayers=$('<div class="gallerylayer"></div><div class="gallerylayer"></div>') //two stacked DIVs to display the actual slide
                .css({position:'absolute', left:0, top:0})
                .appendTo(setting.$wrapperdiv)
        setting.gallerylayers=setting.$gallerylayers.get() //cache stacked DIVs as DOM objects
        setting.navbuttons=simpleGallery.routines.addnavpanel(setting) //get 4 nav buttons DIVs as DOM objects
        $(setting.navbuttons).filter('img.navimages').css({opacity:0.8})
                .bind('mouseover mouseout', function(e){
                        $(this).css({opacity:(e.type=="mouseover")? 1 : 0.8})
                })
                .bind('click', function(e){
                        var keyword=e.target.title.toLowerCase()
                        slideshow.navigate(keyword) //assign behavior to nav images
                })
        setting.$wrapperdiv.bind('mouseenter', function(){slideshow.showhidenavpanel('show')})
        setting.$wrapperdiv.bind('mouseleave', function(){slideshow.showhidenavpanel('hide')})
        slideshow.showslide(setting.curimage) //show initial slide
        setting.oninit() //trigger oninit() event
        $(window).bind('unload', function(){ //clean up and persist
                $(slideshow.setting.navbuttons).unbind()
                if (slideshow.setting.persist) //remember last shown image's index
                        simpleGallery.routines.setCookie("gallery-"+setting.wrapperid, setting.curimage)
                jQuery.each(slideshow.setting, function(k){
                        if (slideshow.setting[k] instanceof Array){
                                for (var i=0; i<slideshow.setting[k].length; i++){
                                        if (slideshow.setting[k][i].tagName=="DIV")
                                                slideshow.setting[k][i].innerHTML=null
                                        slideshow.setting[k][i]=null
                                }
                        }
                        slideshow.setting[k]=null
                })
                slideshow=slideshow.setting=null
        })
    })
}

simpleGallery.prototype={

    navigate:function(keyword){
        clearTimeout(this.setting.playtimer)
        if(!isNaN(parseInt(keyword))) {
            this.showslide(parseInt(keyword))
        }
        else if(/(prev)|(next)/i.test(keyword)) {
            this.showslide(keyword.toLowerCase())
        }
        else { //if play|pause button
            var slideshow=this
            var $playbutton=$(this.setting.navbuttons).eq(1)
            if(!this.setting.ispaused ){ //if pause Gallery
                this.setting.autoplay=false
                $playbutton.attr({title:'Play', src:simpleGallery_navpanel.images[1]})
            }
            else if(this.setting.ispaused) { //if play Gallery
                this.setting.autoplay=true
                this.setting.playtimer=setTimeout(function(){slideshow.showslide('next')}, this.setting.pause)
                $playbutton.attr({title:'Pause', src:simpleGallery_navpanel.images[3]})
            }
            slideshow.setting.ispaused=!slideshow.setting.ispaused
        }
    },

    showslide:function(keyword){
        var slideshow=this
        var setting=slideshow.setting
        var totalimages=setting.imagearray.length
        var imgindex=(keyword=="next")? (setting.curimage<totalimages-1? setting.curimage+1 : 0) : (keyword=="prev")? (setting.curimage>0? setting.curimage-1 : totalimages-1) : Math.min(keyword, totalimages-1)
        
        setting.gallerylayers[setting.bglayer].innerHTML=simpleGallery.routines.getSlideHTML( this.thumbnails[imgindex],this.images[imgindex], setting.dimensions[0], setting.dimensions[1] );
        setting.$gallerylayers.eq(setting.bglayer).css({zIndex:1000, opacity:0}) //background layer becomes foreground
                .stop().css({opacity:0}).animate({opacity:1}, setting.fadeduration, function(){ //Callback function after fade animation is complete:
                        clearTimeout(setting.playtimer)
                        setting.gallerylayers[setting.bglayer].innerHTML=null  //empty bglayer (previously fglayer before setting.fglayer=setting.bglayer was set below)
                        try{
                                setting.onslide(setting.gallerylayers[setting.fglayer], setting.curimage)
                        }catch(e){
                                alert("Simple Controls Gallery: An error has occured somwhere in your code attached to the \"onslide\" event: "+e)
                        }
                        if (setting.autoplay){
                                setting.playtimer=setTimeout(function(){slideshow.showslide('next')}, setting.pause)
                        }
                })
        setting.gallerylayers[setting.fglayer].style.zIndex=999 //foreground layer becomes background
        setting.fglayer=setting.bglayer
        setting.bglayer=(setting.bglayer==0)? 1 : 0
        setting.curimage=imgindex
        setting.navbuttons[3].innerHTML=(setting.curimage+1) + '/' + setting.imagearray.length
    },

    showhidenavpanel:function(state){
        var setting=this.setting
        var endpoint=(state=="show")? setting.dimensions[1]-parseInt(simpleGallery_navpanel.panel.height) : this.setting.dimensions[1]
        setting.$navpanel.stop().animate({top:endpoint}, simpleGallery_navpanel.slideduration)
    }
}

simpleGallery.routines={
    getSlideHTML:function(thumb, img, thumbWidth, thumbHeight){
    var layerHTML='<a href="#" onclick="simpleGallery.routines.displayImage(\'cover01\',\'#000000\',80,\''+img.src+'\','+img.width+','+img.height+')" style="outline:none" title="Close">';
    layerHTML+='<img src="'+thumb.src+'" style="border:none" width="'+thumbWidth+'" height="'+thumbHeight+'" alt="" />';
    layerHTML+='</a>';
    return layerHTML;

    /*
    * Este codigo pertenece a la version original de esta libreria
    */
    /*var layerHTML=(imgelement[1])? '<a href="'+imgelement[1]+'" target="'+imgelement[2]+'">\n' : '' //hyperlink slide?
    layerHTML+='<img src="'+imgelement[0]+'" style="border-width:0" />'
    layerHTML+=(imgelement[1])? '</a>' : ''
    return layerHTML //return HTML for this layer*/
  },

  addnavpanel:function(setting){
    var interfaceHTML=''
    for (var i=0; i<3; i++){
        var imgstyle='position:relative; border:0; cursor:hand; cursor:pointer; top:'+simpleGallery_navpanel.imageSpacing.offsetTop[i]+'px; margin-right:'+(i!=2? simpleGallery_navpanel.imageSpacing.spacing+'px' : 0)
        var title=(i==0? 'Prev' : (i==1)? (setting.ispaused? 'Play' : 'Pause') : 'Next')
        var imagesrc=(i==1)? simpleGallery_navpanel.images[(setting.ispaused)? 1 : 3] : simpleGallery_navpanel.images[i]
        interfaceHTML+='<img class="navimages" title="' + title + '" src="'+ imagesrc +'" style="'+imgstyle+'" /> '
    }
    interfaceHTML+='<div class="gallerystatus" style="margin-top:1px">' + (setting.curimage+1) + '/' + setting.imagearray.length + '</div>'
    setting.$navpanel=$('<div class="navpanellayer"></div>')
        .css({position:'absolute', width:'100%', height:simpleGallery_navpanel.panel.height, left:0, top:setting.dimensions[1], font:simpleGallery_navpanel.panel.fontStyle, zIndex:'1001'})
        .appendTo(setting.$wrapperdiv)
    $('<div class="navpanellayerbg"></div><div class="navpanellayerfg"></div>')
        .css({position:'absolute', left:0, top:0, width:'100%', height:'100%'})
        .eq(0).css({background:'black', opacity:simpleGallery_navpanel.panel.opacity}) //"navpanellayerbg" div
        .end().eq(1).css({paddingTop:simpleGallery_navpanel.panel.paddingTop, textAlign:'center', color:'white'}).html(interfaceHTML) //"navpanellayerfg" div
        .end().appendTo(setting.$navpanel)
    return setting.$navpanel.find('img.navimages, div.gallerystatus').get() ////return 4 nav buttons DIVs as DOM objects
  },

  getCookie:function(Name){ 
    var re=new RegExp(Name+"=[^;]+", "i"); //construct RE to search for target name/value pair
    if (document.cookie.match(re)) //if cookie found
        return document.cookie.match(re)[0].split("=")[1] //return its value
    return null
  },

  setCookie:function(name, value){
    document.cookie = name+"=" + value + ";path=/"
  }

  ,createCoverDiv:function(divId, bgcolor, opacity) {
    var layer=document.createElement('div');
    layer.id=divId;
    layer.style.width='100%';
    layer.style.height='100%';
    layer.style.backgroundColor=bgcolor;
    layer.style.position='fixed';

    layer.align = 'center';

    layer.style.top=0;
    layer.style.left=0;
    layer.style.zIndex=1000;
    layer.style.filter='alpha(opacity='+opacity+')';
    layer.style.opacity=opacity/100;
    document.body.appendChild(layer);
  }

  ,removeCoverDiv:function(divId) {
    var layer=document.getElementById(divId);
    var superlayer=document.getElementById('s_'+divId);
    if(layer && superlayer) {
        document.body.removeChild(superlayer);
        document.body.removeChild(layer);
    }            
  }

  ,displayImage:function(divId, bgcolor, opacity, src, width, height) {
    simpleGallery.routines.createCoverDiv(divId, bgcolor, opacity);

    var foto=document.createElement('img');
    foto.src=src;
    foto.border='2px solid #FFFFFF';
    foto.onclick=function() {simpleGallery.routines.removeCoverDiv(divId)};
    if( width>=screen.width || height>=screen.height ) {
        width = screen.width;
        height = screen.height;
    }
    foto.width=width;
    foto.height=height;
    
    var superlayer=document.createElement('div');
    superlayer.id='s_'+divId;
    superlayer.style.zIndex=1001;
    superlayer.style.position='absolute';
    superlayer.align = 'center';
    superlayer.style.width=width+'px';
    superlayer.style.height=height+'px';
    superlayer.style.top='50%';
    superlayer.style.left='50%';
    superlayer.style.marginLeft=-width/2+'px';
    superlayer.style.marginTop=-height/2+'px';
    superlayer.onclick='function(){simpleGallery.routines.removeCoverDiv(divId);}';    

    superlayer.appendChild(foto);
    document.body.appendChild(superlayer);
  }
}
