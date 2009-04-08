var displayed = false;
var pdisplayed = false;
/**
 * Clears the suggestions list and gives focus to the textarea.
 */
function clearSuggestions() {
    //Delete list contents (hides list)
    if (dojo.byId('results')) {
        dojo.byId('results').innerHTML = "";
    }

    //Reset list parameters
    displayed = false;
    curSelected = 0;

    //Give focus to textarea
    dojo.byId('naturalQuery').focus();
}

/**
 * Replaces the current word in the textarea with the selected word from the
 * suggestions list.
 * @param selected index of current selected item
 * @param prep
 */
function setSelection(selected, prep) {
    //Gets word at current cursor position
    var word = getCurrentWord('naturalQuery');
    var newText = dojo.byId('id' + selected).innerHTML.replace(/<(.|\n)+?>/g, "");
    newText = prep + "[" + newText + "]";
    //Replaces textarea content to include a selected word from the suggestions list
    var valText = dojo.byId('naturalQuery').value;
    dojo.byId('naturalQuery').value = valText.substring(0, word.startP) +
    newText + valText.substring(word.endP, valText.length);
}

/**
 * Highlights an option in the suggestions list.
 * @param id identifier of the list item to highlight
 * @param high toggles highlight color.
 */
function highLightSelection(id, high) {
    var ele = dojo.byId('id' + id);
    if (high) {
        //TODO:Change for dojo.addClass();
        dojo.style(ele, {
            "background":"LightBlue"
        });
    } else {
        //TODO:Change for dojo.addClass();
        dojo.style(ele, {
            "background":"white"
        });
    }
}

/**
 * Gets information from an URL via AJAX.
 * @param url URL of the resource to invoke.
 * @param tagid of the HTML element where results must be placed.
 */
function getHtml (url, tagid) {
    dojo.xhrGet ({
        url: url,
        load: function(response, ioArgs) {
            var tag = dojo.byId(tagid);
            if (tag) {
                tag.innerHTML = response;
            } else {
                alert('No existe ningún elemento con id ' + tagid);
            }
            highLightSelection(0, true);
            return response;
        },
        error: function(response, ioArgs) {
            if (dojo.byId(tagid)) {
                dojo.byId(tagid).innerHTML =
                "<font color='red'>Cannot load suggestions, try again</font>";
            } else {
                alert('No existe ningún elemento con id ' + tagid);
            }
            return response;
        },
        handleAs: "text",
        timeout: 5000
    });
}

/**
 * Creates a suggestion list based on word at current cursor position.
 * @param word word at cursor position
 * @param src url of dataStore
 * @param clear wheter or not to clear previous list
 * @param props wheter or not to get properties of the current word as SemanticClass
 */
function getSuggestions(word, src, clear, props) {
    if (clear) {
        clearSuggestions();
    }
    if(word.word == '') {
        return;
    }
    //Put a loading message
    if (dojo.byId('results') && word.word != '') {
        dojo.byId('results').innerHTML = '<font color="Green">Loading...</font>';
    }
    //Create the suggestions list using a jsp page
    getHtml(src + "?word=" + word.word + "&lang=" + lang + "&props=" + props, 'results');

    //Set suggestions list status as displayed
    displayed = true;
}

/**
 * Gets cursor current position in a textarea.
 * @param elm textarea to calculate caret position from.
 */
function getCaretPos(elm) {
    var pos;
    //If browser is IExplorer (selectionStart & selectionEnd not defined)
    if (dojo.doc.selection) {
        var Sel = document.selection.createRange();
        var SelLength = document.selection.createRange().text.length;
        Sel.moveStart ('character', -dojo.byId(elm).value.length);
        pos = Sel.text.length - SelLength;
    //If Gecko based browser
    } else if (typeof dojo.byId(elm).selectionStart != undefined) {
        pos = dojo.byId(elm).selectionStart;
    }
    return pos;
}

/**
 * Gets the word at current cursor position in a textarea.
 * @param elm textarea to extract word from.
 */
function getCurrentWord(elm) {
    //Get current cursor position
    var cPos = getCaretPos(elm);
    //Get text from textarea
    var txt = dojo.byId(elm).value;
    var prevBlank = -1;
    var aftBlank = -1;
    var found = false;
    var wd = null;
    var wo = "undefined";
    var delimiters = "\n\t ";

    if (txt != '') {
        //find first blank space before cursor position
        for (var i = 0; i < txt.length; i++) {
            if (delimiters.indexOf(txt.charAt(i)) != -1 && cPos > i) {
                prevBlank = i;
            }
        }
        //find first blank space after cursor position
        for (i = cPos; i < txt.length && !found; i++) {
            if (delimiters.indexOf(txt.charAt(i)) != -1) {
                aftBlank = i;
                found = true;
            }
        }
        //If no blank space before and after (just one word in the textarea)
        if (prevBlank == -1) {
            if (aftBlank == -1) {
                //Return the current word
                wd = txt;
                wo = {
                    word: wd,
                    startP: 0,
                    endP: txt.length
                };
            } else {
                wd = txt.substring(0, aftBlank);
                wo = {
                    word: wd,
                    startP: 0,
                    endP: aftBlank
                };
            }
        } else if (aftBlank == -1) {
            wd = txt.substring(prevBlank + 1, txt.length);
            wo = {
                word: wd,
                startP: prevBlank + 1,
                endP: txt.length
            };
        } else {
            wd = txt.substring(prevBlank + 1, aftBlank);
            wo = {
                word: wd,
                startP: prevBlank + 1,
                endP: aftBlank
            };
        }
    }
    return wo;
}

function queryOnKeyUp (evt) {
    var wd = getCurrentWord('naturalQuery');
    //console.log("displayed: " + displayed + ", enter:" + evt.keyCode);
    if (evt.target.value == '' || wd.word.length < 3) {
        clearSuggestions();
        return;
    }

    if((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {
        /*console.log("ENTER up");
        setSelection(curSelected, (wd.word == "con")?"con ":"");
        clearSuggestions();
        pdisplayed = false;*/
    } else if (!displayed && !pdisplayed && wd.word == "con") {
        var pwd = getPreviousName(wd);
        if (pwd != "undefined") {
            getSuggestions(getPreviousName(wd), source, true, true);
            pdisplayed = true;
        }
    } else if (!displayed && pdisplayed && wd.word != "con") {
        clearSuggestions();
        pdisplayed = false;
    } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ESCAPE) {
        clearSuggestions();
        pdisplayed = false;
    //dojo.stopEvent(evt);
    }
    //Flecha ARRIBA
    else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.UP_ARROW) {
        console.log("UP_ARROW");
        dojo.query('.resultEntry').style('background', 'white');
        curSelected--;
        if (curSelected < 0) {
            curSelected = 0;
        }
        highLightSelection(curSelected, true);
        dojo.byId('resultlist').scrollTop = dojo.coords(dojo.byId('id' + curSelected)).t;
        dojo.byId('naturalQuery').focus();
        dojo.stopEvent(evt);
    //Flecha ABAJO
    } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.DOWN_ARROW) {
        dojo.query('.resultEntry').style('background', 'white');
        curSelected++;
        if (curSelected > dojo.byId('resultlist').childNodes.length - 2) {
            curSelected = dojo.byId('resultlist').childNodes.length - 2;
        }
        highLightSelection(curSelected, true);
        dojo.byId('resultlist').scrollTop = dojo.coords(dojo.byId('id'+curSelected)).t;
        dojo.byId('naturalQuery').focus();
        dojo.stopEvent(evt);
    }/*else if (evt.keyCode != dojo.keys.DOWN_ARROW &&
        evt.keyCode != dojo.keys.UP_ARROW &&
        (evt.ctrlKey && evt.keyCode != dojo.keys.SPACE) &&
        evt.keyCode != dojo.keys.ENTER) {
        clearSuggestions();
        pdisplayed = false;
        dojo.stopEvent(evt);
    }*/
}

function queryOnKeyDown (evt) {
    var wd = getCurrentWord('naturalQuery');

    if (evt.target.value == '' || wd.word.length < 3) {
        clearSuggestions();
        return;
    }

    //Tecla CTRL+SPACE
    if (evt.ctrlKey && evt.keyCode == dojo.keys.SPACE) {
        //console.log(wd.word);
            getSuggestions(wd, source, true, false);
            dojo.stopEvent(evt);
    } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {
        setSelection(curSelected, (wd.word == "con")?"con ":"");
        clearSuggestions();
        pdisplayed = false;
        dojo.stopEvent(evt);
    }
}

function getPreviousName (word) {
    var pName = "";
    var prevBrk = -1;
    var firstBrk = -1;
    var txt = dojo.byId('naturalQuery').value;
    var cPos = word.startP;
    var wd = null;
    var wo = "undefined";
    var found = false;

    //find first blank space before cursor position
    for (var i = cPos; i >= 0 && !found; i--) {
        if (txt.charAt(i) == ']') {
            prevBrk = i;
            found = true;
        }
    }

    found = false;
    //Find next blank space from previous blank found
    for (i = prevBrk; i > 0 && !found; i--) {
        if (txt.charAt(i) == '[') {
            firstBrk = i;
            found = true;
        }
    }

    if (prevBrk == -1) {
        return wo;
    }

    firstBrk++;
    wd = txt.substring((firstBrk==0)?++firstBrk:firstBrk, prevBrk);
    wo = {
        word: wd,
        startP: firstBrk,
        endP: prevBrk
    };
    return wo;
}