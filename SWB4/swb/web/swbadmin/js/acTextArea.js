/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Manejador del evento keyUp del textArea. Está a la espera de la combinación
 * CTRL + SPACE para desplegar las sugerencias.
 */
var displayed = false;
var curSelected = 0;

function queryOnKeyUp (evt) {
    var target = evt.target;
    var oldCurPos = getCaretPos(target);

    if (evt.target.value == "") {
        if (displayed) {
            displayed = false;
            curSelected = 0;
            dojo.byId("suggestions").innerHTML = "";
            dojo.addClass(dojo.byId("suggestions"), "hidden");
        }
    }
    if (displayed) {
        if (evt.keyCode == dojo.keys.ESCAPE) {
            displayed = false;
            curSelected = 0;
            dojo.byId("suggestions").innerHTML = "";
            dojo.addClass(dojo.byId("suggestions"), "hidden");
        } else if (evt.keyCode == dojo.keys.UP_ARROW) {
            //console.log(dojo.byId("suggestionsList").childNodes.length - 1);
            if (curSelected <= 0) {
                curSelected = 0;
            } else {
                curSelected--;
            }
            console.log(curSelected);
            dojo.byId("id"+curSelected-1).focus();
            //console.log("indice seleccionado: " + curSelected -1);
        } else if (evt.keyCode == dojo.keys.DOWN_ARROW) {
            if (curSelected == dojo.byId("suggestionsList").childNodes.length - 1) {
                curSelected = dojo.byId("suggestionsList").childNodes.length - 1;
            } else {
                curSelected++;
            }
            console.log(curSelected);
            //console.log("indice seleccionado: " + curSelected -1);
        } else {
            curSelected = 0;
            createDataList2("suggestions", getCurrentWord(target, oldCurPos));
            dojo.removeClass(dojo.byId("suggestions"), "hidden");
            dojo.byId("suggestions").focus();
        }
    } else if (evt.keyCode == dojo.keys.SPACE && evt.ctrlKey) {
        if (!displayed) {
            displayed = true;
            curSelected = 0;

            createDataList2("suggestions", getCurrentWord(target, oldCurPos));
            dojo.removeClass(dojo.byId("suggestions"), "hidden");
            dojo.byId("suggestions").focus();
        }
    }
    target.focus();
}

/**
 * Obtiene la posición actual del cursor en el elemento 'elm'
 */
function getCaretPos(elm) {
    var pos;
    //Si es IExplorer (selectionStart y selectionEnd no definidas)
    if (dojo.doc.selection) {
        var Sel = document.selection.createRange();
        var SelLength = document.selection.createRange().text.length;
        Sel.moveStart ('character', -elm.value.length);
        pos = Sel.text.length - SelLength;
    //Si es Gecko
    } else if (typeof elm.selectionStart != undefined) {
        pos = elm.selectionStart;
    }
    return pos;
}

/**
 * Obtiene la palabra sobre la que se posiciona el cursor en 'elm'
 *
 */
function getCurrentWord(elm, cPos) {
    var txt = elm.value;
    var prevBlank = -1;
    var aftBlank = -1;
    var found = false;
    var wd = null;
    var wo = "undefined";

    //Seleccionar la palabra actual en el elemento
    if (txt != "") {
        //encontrar el primer espacio en blanco a la izquierda del cursor
        for (var i = 0; i < txt.length; i++) {
            if (txt.charAt(i) == " " && cPos > i) {
                prevBlank = i;
            }
        }

        //encontrar el primer espacio en blanco a la derecha del cursor
        for (i = cPos; i < txt.length && !found; i++) {
            if (txt.charAt(i) == " ") {
                aftBlank = i;
                found = true;
            }
        }

        if (prevBlank == -1) {
            if (aftBlank == -1) {
                wd = txt.substring(0, txt.length);
                wo = {word: wd, startP: 0, endP: txt.length};
            } else {
                wd = txt.substring(0, aftBlank);
                wo = {word: wd, startP: 0, endP: aftBlank};
            }
        } else if (aftBlank == -1) {
                wd = txt.substring(prevBlank + 1, txt.length);
                wo = {word: wd, startP: prevBlank + 1, endP: txt.length};
            } else {
                wd = txt.substring(prevBlank + 1, aftBlank);
                wo = {word: wd, startP: prevBlank + 1, endP: aftBlank};
        }
    }
    return wo;
}

/**
 * Reemplaza la palabra wd en elm con el txt especificado
 */
function replaceText(elm, start, end, txt) {
    var valText = elm.value;
    elm.value = valText.substring(0, start) + txt + valText.substring(end, valText.length);
}

/**
 * Establece un rango de selección para 'elm'
 */
function setSelection(elm, start, end) {
    if(dojo.doc.selection) { //IE
        if(dojo.doc.selection.createRange().text != '') {
            dojo.doc.selection.createRange().text = elm.value;
        }
    } else {
        elm.selectionStart = start;
        elm.selectionEnd = end;
    }
}

function createDataList2(elm, word) {
    elm.innerHTML = "";

    if(word.word == "") {
            return;
    } else {
        getHtml("/swb/swbadmin/jsp/acTextAreaStore.jsp?word="+
        word.word + "&start=" + word.startP + "&end=" + word.endP + "&elm=" + elm, dojo.byId(elm));
    }
    dojo.byId(elm).focus();
}

function createDataList(elm, word) {
    if(word.word == "") {
        elm.innerHTML = "";
        dojo.addClass(elm, "hidden");
        return;
    } else {
        var list = "<ul class=\"resultlist\">";

        for (var i = 0; i < 15; i++) {
            list = list + "<li><a class=\"link\" href=# onmousedown=\"replaceText(dojo.byId(\"query\"),"+
                word.startP + "," + word.endP + "," + "\'" + word.word + i + "\'" +")\">" +
                word.word + i + "</a></li>";
        }
        list += "</ul>";
        elm.innerHTML = list;
        elm.focus();
    }
}

function getHtml (url, tagid) {
    dojo.xhrGet ({
    url: url,
    load: function(response, ioArgs)
    {
        var tag = dojo.byId(tagid);
        if (tag) {
            var pan = dijit.byId(tagid);
            //alert("-"+tagid+"-"+tag+"-"+pan+"-");
            if (pan && pan.attr)
            {
                pan.attr('content',response);
            } else {
                tag.innerHTML = response;
            }
          } else {
              alert("No existe ningún elemento con id " + tagid);
          }
          return response;
      },
      error: function(response, ioArgs)
      {
          if (dojo.byId(tagid)) {
              dojo.byId(tagid).innerHTML = "<p>Ocurrió un error con respuesta:<br />" + response + "</p>";
          } else {
              alert("No existe ningún elemento con id " + tagid);
          }
          return response;
      },
      handleAs: "text"
  });
}