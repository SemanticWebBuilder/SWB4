/*Funcion para enviar informacion de una forma de un dialogo y cargar la respuesta en el mismo dialogo*/

function mySubmitForm(formid) {
    var obj = dojo.byId(formid);
    var objd = dijit.byId(formid);
    var fid = formid;
    //alert("id:"+formid+" "+"dojo:"+obj +" dijit:"+objd);
    if (!obj && objd) //si la forma esta dentro de un dialog
    {
        obj = objd.domNode;
        fid = obj;
    }

    if (!objd || objd.isValid()) {
    /*
        try
        {
            //dojo.fadeOut({node: formid, duration: 1000}).play();
            dojo.fx.wipeOut({node: formid, duration: 500}).play();
        } catch (noe) {
        }
    */
        try {
            var framesNames = "";
            for (var i = 0; i < window.frames.length; i++) {
                try {
                    framesNames += window.frames[i].name;
                    if (framesNames && framesNames.indexOf("_editArea") != -1) {
                        area = window.frames[framesNames].editArea;
                        id = framesNames.substr(6);
                        document.getElementById(id).value = area.textarea.value;
                    }
                } catch(e) {}
            }
        } catch (ex) {
        }
            //alert("entra2");
        dojo.xhrPost({
            // The page that parses the POST request
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            //handleAs: "text",
            url: obj.action,
            // Name of the Form we want to submit
            form: fid,
            // Loads this function if everything went ok
            load: function(data) {
                var panel = getContentPanel(obj);
                //alert("div:"+panel.id);
                //alert("div:"+panel.suportScripts);
                if (panel) {
                    try {
                        var aux = panel.href;
                        //alert("div1: " + panel.href);
                        panel.attr('content', data);
                        panel.href = aux;
                        var panelStyled = document.getElementById("swbDialogImp");
                        panelStyled.style.width = "150px";
                        panelStyled.style.height = "100px";
                        panelStyled.style.width = "auto";
                        panelStyled.style.height = "auto";
                        //revisar funcionalidad de dojo.fadeOut o dojo.fx.wipeOut
                        if (!panel.suportScripts) {
                            runScripts(data);
                        }
                    } catch (e) {
                        alert(e.message);
                    }
                } else {
                    alert("No hay panel asociado!!!")
                }
                //dijit.byId('swbDialog').hide();
                //div_node.innerHTML = data;
                //dojo.parser.parse(div_node,true);
            },
            // Call this function if an error happened
            error: function(error) {
                alert('Error: ', error);
            }
        });
    } else {
        alert("Datos Inválidos...");
    }
}

function deleteElement(elementType, title, path) {
    var elementName;
    var message = "¿Estás seguro de querer eliminar la ";
    if (elementType == 1) {
        elementName = "acción";
    } else if (elementType == 2) {
        elementName = "iniciativa";
    }
    if (confirm(message + elementName + ": " + title + "?")) {
        submitUrl(path);
        location.reload();
    }
}

function validateSubmit(form) {
    if (form.risk.selectedIndex > 0 && form.objType.selectedIndex > 0) {
        mySubmitForm(form.id);
    } else {
        alert("Debes seleccionar un elemento de cada opción.")
    }
}

function submitAndReload(formid) {
    var obj = dojo.byId(formid);
    var objd = dijit.byId(formid);
    var fid = formid;
    if (!obj && objd) {  //si la forma esta dentro de un dialog
        obj = objd.domNode;
        fid = obj;
    }

    if (!objd || objd.isValid()) {
        try
        {
            //dojo.fadeOut({node: formid, duration: 1000}).play();
            dojo.fx.wipeOut({node: formid, duration: 500}).play();
        } catch (noe) {
        }
        objd.submit();
        /*
        dojo.xhrPost({
            // The page that parses the POST request
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            //handleAs: "text",
            url: obj.action,
            // Name of the Form we want to submit
            form: fid,
            // Loads this function if everything went ok
            load: function(data) {
                var panel = getContentPanel(obj);
                //alert("div:"+panel.id);
                //alert("div:"+panel.suportScripts);
                if (panel) {
                    try {
                        var aux = panel.href;
                        //alert("div1: " + panel.href);
                        panel.attr('content', data);
                        panel.href = aux;
                        var panelStyled = document.getElementById("swbDialogImp");
                        panelStyled.style.width = "150px";
                        panelStyled.style.height = "100px";
                        panelStyled.style.width = "auto";
                        panelStyled.style.height = "auto";
                        //revisar funcionalidad de dojo.fadeOut o dojo.fx.wipeOut
                        if (!panel.suportScripts) {
                            runScripts(data);
                        }
                    } catch (e) {
                        alert(e.message);
                    }
                } else {
                    alert("No hay panel asociado!!!")
                }
                //dijit.byId('swbDialog').hide();
                //div_node.innerHTML = data;
                //dojo.parser.parse(div_node,true);
            },
            // Call this function if an error happened
            error: function(error) {
                alert('Error: ', error);
            }
        });
        */
    } else {
        alert("Datos Inválidos...");
    }
}

function showMyDialog(url, title) {
    dojo.xhrGet({
        url: url,
        load: function(response, ioArgs) {
            //alert(response);
            //dijit.byId('swbDialogImp').attr('content',response);
            //console.log(dijit.byId('swbDialog'));
            dijit.byId('swbDialogImp').attr('content', response);            
            dijit.byId('swbDialog').show();
            setDialogTitle(title);
            return response;
        },
        error: function(response, ioArgs) {
            showStatus('Error:' + response);
            //dijit.byId('swbDialogImp').attr('content','Error: '+response);
            //dijit.byId('swbDialog').show();
            return response;
        },
        handleAs: "text"
    });
}

