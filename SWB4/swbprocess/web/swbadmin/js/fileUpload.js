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
    var node = document.createElement('input');
    dojo.byId(pname+'_dynamic').appendChild(node);
    var widget = new dojox.form.FileInputAuto({
            id: "" + pname + "_dynamic"+(++fileUploadCounter),
            url: "" + url,
            name: "" + pname + "_dynamic"+fileUploadCounter,
            onComplete: fileUpload_Callback,
            fileMask: ""+filters
    },node);
    widget.startup();
}



