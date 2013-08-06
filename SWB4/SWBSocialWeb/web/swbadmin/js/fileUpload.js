dojo.require("dojox.form.FileInputAuto");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.MultiSelect");

var fileUpload_Callback = function(data,ioArgs,widgetRef){
    if(data){
        if(data.status && data.status == "success"){
            widgetRef.overlay.innerHTML = "OK";
        }else{
            widgetRef.overlay.innerHTML = "error?";
            console.log('error',data,ioArgs);
        }
    }else{
        console.log('ugh?',arguments);
    }
}

var fileUploadCounter = 0;
function fileUpload_addNewUpload(pname,filters,url)
{
    var pnameTmp=pname;
    var node = document.createElement('input');
    var post=pname.indexOf("_#swbsocial_");
    if(post>-1)
    {
       pnameTmp=pname.substring(0, post);
    }
    dojo.byId(pname+'_dynamic').appendChild(node);
    var widget = new dojox.form.FileInputAuto({	    
        id: "" + pnameTmp + "_dynamic"+(++fileUploadCounter),    
                    url: "" + url,
        blurDelay: 0,
        name: "" + pnameTmp + "_dynamic"+fileUploadCounter,
        onComplete: fileUpload_Callback,
        fileMask: ""+filters
    },node);
    widget.startup();
    
    node = document.createElement('br');
    dojo.byId(pname+'_dynamic').appendChild(node);
}



