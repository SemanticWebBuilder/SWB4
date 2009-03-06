var displayed = false;
var curSelected = 0;

/*
 * Manejador para el evento OnKeyUp del área de texto.
 */
function queryOnKeyUp (evt) {
    if (evt.target.value == "") {
        clearSuggestions();
        return;
    }

    //Flecha ARRIBA
    if (displayed && evt.keyCode == dojo.keys.UP_ARROW) {
        dojo.query(".resultEntry").style("background", "white");
        curSelected--;
        if (curSelected < 0) {
            curSelected = 0;
        }
        highLightSelection(curSelected, true);
        dojo.byId("resultlist").scrollTop = dojo.coords(dojo.byId("id" + curSelected)).t;
        dojo.stopEvent(evt);
    //Flecha ABAJO
    } else if (displayed && evt.keyCode == dojo.keys.DOWN_ARROW) {
        dojo.query(".resultEntry").style("background", "white");
        curSelected++;
        if (curSelected > dojo.byId("resultlist").childNodes.length - 2) {
            curSelected = dojo.byId("resultlist").childNodes.length - 2;
        }
        highLightSelection(curSelected, true);
        dojo.byId("resultlist").scrollTop = dojo.coords(dojo.byId("id"+curSelected)).t;
        dojo.stopEvent(evt);
    }
    //Tecla ENTER
    else if (displayed && evt.keyCode == dojo.keys.ENTER) {
        setSelection(curSelected);
        clearSuggestions();
        dojo.stopEvent(evt);
    }
}

/*
 * Manejador para el evento OnKeyDown del área de texto.
 */
function queryOnKeyDown (evt) {
    if (evt.target.value == "") {
        clearSuggestions();
        return;
    }

    //Tecla CTRL+SPACE
    if (evt.ctrlKey && evt.keyCode == dojo.keys.SPACE) {
        getSuggestions();
        dojo.stopEvent(evt);
    //Tecla ESCAPE
    } else if (displayed && evt.keyCode == dojo.keys.ESCAPE) {
        clearSuggestions();
        dojo.stopEvent(evt);
    //Cualquier otra tecla
    } else if (displayed && (evt.keyCode != dojo.keys.CTRL && evt.keyCode != dojo.keys.UP_ARROW &&
            evt.keyCode != dojo.keys.DOWN_ARROW && evt.keyCode != dojo.keys.ESC &&
            evt.keyCode != dojo.keys.ENTER)) {
        getSuggestions();
        //dojo.stopEvent(evt);
        dojo.byId("queryText").focus();
    }
}

/*
 * Crea la lista de sugerencias en base a la palabra en la posición del cursor.
*/
function getSuggestions() {
    var word = getCurrentWord("queryText");
    clearSuggestions();
    
    if(word.word == "") {
        return;
    }
    
    if (dojo.byId("results") && word.word != "") {
        dojo.byId("results").innerHTML = "<font color=\"Red\">Loading...</font>";
    }

    getHtml("/swb/swbadmin/jsp/acTextAreaStore.jsp?word=" + word.word, "results");
    displayed = true;
}

function highLightSelection(id, high) {
    var ele = dojo.byId("id" + id);

    if (high) {
        dojo.style(ele, {
            "background":"LightBlue"
        });
    } else {
        dojo.style(ele, {
            "background":"white"
        });
    }
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
        Sel.moveStart ('character', -dojo.byId(elm).value.length);
        pos = Sel.text.length - SelLength;
    //Si es Gecko
    } else if (typeof dojo.byId(elm).selectionStart != undefined) {
        pos = dojo.byId(elm).selectionStart;
    }
    return pos;
}

/**
 * Obtiene la palabra sobre la que se posiciona el cursor en 'elm'
 *
 */
function getCurrentWord(elm) {
    var cPos = getCaretPos(elm);
    var txt = dojo.byId(elm).value;
    var prevBlank = -1;
    var aftBlank = -1;
    var found = false;
    var wd = null;
    var wo = "undefined";
    var delimiters = "\n\t ";

    //Seleccionar la palabra actual en el elemento
    if (txt != "") {
        //encontrar el primer espacio en blanco a la izquierda del cursor
        for (var i = 0; i < txt.length; i++) {
            if (delimiters.indexOf(txt.charAt(i)) != -1 && cPos > i) {
                prevBlank = i;
            }
        }

        //encontrar el primer espacio en blanco a la derecha del cursor
        for (i = cPos; i < txt.length && !found; i++) {
            if (delimiters.indexOf(txt.charAt(i)) != -1) {
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

function clearSuggestions() {
    if (dojo.byId("results")) {
        dojo.byId("results").innerHTML = "";
    }
    displayed = false;
    curSelected = 0;
    dojo.byId("queryText").focus();
}

function setSelection(selected) {
    var word = getCurrentWord("queryText");
    var valText = dojo.byId("queryText").value;
    dojo.byId("queryText").value = valText.substring(0, word.startP) +
        dojo.byId("id"+selected).innerHTML + valText.substring(word.endP, valText.length);
}

/**
 * Reemplaza la palabra wd en elm con el txt especificado
 */
function replaceText(elm, start, end, txt) {
    var valText = elm.value;
    elm.value = valText.substring(0, start) + txt + valText.substring(end, valText.length);
}

function getHtml (url, tagid) {
    dojo.xhrGet ({
    url: url,
    load: function(response, ioArgs) {
        var tag = dojo.byId(tagid);

        if (tag) {
            tag.innerHTML = response;
        } else {
            alert("No existe ningún elemento con id " + tagid);
        }
        highLightSelection(0, true);
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