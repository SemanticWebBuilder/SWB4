var WBEditButtons = [];


addButton("images/wiki/button_bold.png","Bold text","\'\'\'","\'\'\'","Bold text");
addButton("images/wiki/button_italic.png","Italic text","\'\'","\'\'","Italic text");
addButton("images/wiki/button_link.png","Internal link","[[","]]","Link title");
addButton("images/wiki/button_extlink.png","External link (remember http:// prefix)","[","]","http://www.example.com link title");
addButton("images/wiki/button_headline.png","Level 2 headline","\n== "," ==\n","Headline text");
addButton("images/wiki/button_image.png","Embedded file","[[Image:","]]","Example.jpg");
addButton("images/wiki/button_media.png","File link","[[Media:","]]","Example.ogg");
addButton("images/wiki/button_math.png","Mathematical formula (LaTeX)","\x3cmath\x3e","\x3c/math\x3e","Insert formula here");
addButton("images/wiki/button_nowiki.png","Ignore wiki formatting","\x3cnowiki\x3e","\x3c/nowiki\x3e","Insert non-formatted text here");
addButton("images/wiki/button_sig.png","Your signature with timestamp","--~~~~","","");
addButton("images/wiki/button_hr.png","Horizontal line (use sparingly)","\n----\n","","");
addButton("images/wiki/Button_redirect.png", "Redirect", "#REDIRECT [[", "]]", "Insert text");
addButton("images/wiki/Button_strike.png", "Strike", "<s>", "</s>", "Strike-through text"); 
addButton("images/wiki/Button_enter.png", "Line break", "<br/>", "", "");
addButton("images/wiki/Button_upper_letter.png", "Superscript", "<sup>", "</sup>", "Superscript text");
addButton("images/wiki/Button_lower_letter.png", "Subscript", "<sub>", "</sub>", "Subscript text");
addButton("images/wiki/Button_small.png", "Small", "<small>", "</small>", "Small Text");
addButton("images/wiki/Button_hide_comment.png", "Insert hidden Comment", "<!-- ", " -->", "Comment");
addButton("images/wiki/Button_gallery.png", "Insert a picture gallery", "\n<gallery>\n", "\n</gallery>", "Image:Example.jpg|Caption1\nImage:Example.jpg|Caption2");
addButton("images/wiki/Button_blockquote.png", "Insert block of quoted text", "<blockquote>\n", "\n</blockquote>", "Block quote");
addButton("images/wiki/Button_insert_table.png", "Insert a table", '{| class="wikitable"\n|-\n', "\n|}", "! header 1\n! header 2\n! header 3\n|-\n| row 1, cell 1\n| row 1, cell 2\n| row 1, cell 3\n|-\n| row 2, cell 1\n| row 2, cell 2\n| row 2, cell 3");
addButton("images/wiki/Button_reflink.png", "Insert a reference", "<ref>", "</ref>", "Insert footnote text here");


function addButton(imageFile, speedTip, tagOpen, tagClose, sampleText) 
{
    imageFile=WBAdmPath+imageFile;
    WBEditButtons[WBEditButtons.length] = {
        "imageFile": imageFile,
        "speedTip": speedTip,
        "tagOpen": tagOpen,
        "tagClose": tagClose,
        "sampleText": sampleText};
}

function WBInsertEditButton(parent, item) 
{
    var image = document.createElement("img");
    image.width = 23;
    image.height = 22;
    image.className = "WBWiki-toolbar-editbutton";
    image.src = item.imageFile;
    image.border = 0;
    image.alt = item.speedTip;
    image.title = item.speedTip;
    image.style.cursor = "pointer";
    image.onclick = function() 
    {
        insertTags(item.tagOpen, item.tagClose, item.sampleText);
        return false;
    };
    parent.appendChild(image);
    return true;
}

function WBSetupToolbar() 
{
    var toolbar = document.getElementById('WBWikiToolBar');
    if (!toolbar) { return false; }

    var textbox = document.getElementById('WBWikiTextbox');
    if (!textbox) { return false; }

    if (!(document.selection && document.selection.createRange)
       && textbox.selectionStart === null) 
    {
        return false;
    }

    for (var i = 0; i < WBEditButtons.length; i++) 
    {
        WBInsertEditButton(toolbar, WBEditButtons[i]);
    }
    
    return true;
}

/*
function escapeQuotes(text) 
{
    var re = new RegExp("'","g");
    text = text.replace(re,"\\'");
    re = new RegExp("\\n","g");
    text = text.replace(re,"\\n");
    return escapeQuotesHTML(text);
}

function escapeQuotesHTML(text) 
{
    var re = new RegExp('&',"g");
    text = text.replace(re,"&amp;");
    re = new RegExp('"',"g");
    text = text.replace(re,"&quot;");
    re = new RegExp('<',"g");
    text = text.replace(re,"&lt;");
    re = new RegExp('>',"g");
    text = text.replace(re,"&gt;");
    return text;
}
*/

function insertTags(tagOpen, tagClose, sampleText) 
{
        var txtarea;
        if (document.WBWikiEditForm) //editform
        {
            txtarea = document.WBWikiEditForm.WBWikiTextbox;
        } else {
            // some alternate form? take the first one we can find
            var areas = document.getElementsByTagName('textarea');
            txtarea = areas[0];
        }
 	 var selText, isSample = false;
 	 if (document.selection && document.selection.createRange) 
 	 { // IE/Opera

 	     //save window scroll position
 	     if (document.documentElement && document.documentElement.scrollTop)
 	         var winScroll = document.documentElement.scrollTop
            else if (document.body)
                var winScroll = document.body.scrollTop;
 
            //get current selection
            txtarea.focus();
            var range = document.selection.createRange();
            selText = range.text;
            //insert tags
            checkSelectedText();
            range.text = tagOpen + selText + tagClose;
            //mark sample text as selected
            if (isSample && range.moveStart) 
            {
                if (window.opera)tagClose = tagClose.replace(/\n/g,'');
                range.moveStart('character', - tagClose.length - selText.length);
                range.moveEnd('character', - tagClose.length);
            }
            range.select();
            //restore window scroll position
            if(document.documentElement && document.documentElement.scrollTop)
                document.documentElement.scrollTop = winScroll
            else if (document.body)
                document.body.scrollTop = winScroll;

        } else if (txtarea.selectionStart || txtarea.selectionStart == '0') 
        { // Mozilla
            //save textarea scroll position
            var textScroll = txtarea.scrollTop;
            //get current selection
            txtarea.focus();
            var startPos = txtarea.selectionStart;
            var endPos = txtarea.selectionEnd;
            selText = txtarea.value.substring(startPos, endPos);
            //insert tags
            checkSelectedText();
            txtarea.value = txtarea.value.substring(0, startPos)
            + tagOpen + selText + tagClose
            + txtarea.value.substring(endPos, txtarea.value.length);
            //set new selection
            if (isSample) 
            {
                txtarea.selectionStart = startPos + tagOpen.length;
                txtarea.selectionEnd = startPos + tagOpen.length + selText.length;
            } else {
                txtarea.selectionStart = startPos + tagOpen.length + selText.length + tagClose.length;
                txtarea.selectionEnd = txtarea.selectionStart;
            }
            //restore textarea scroll position
            txtarea.scrollTop = textScroll;
        }

    function checkSelectedText()
    {
        if (!selText) 
        {
            selText = sampleText;
            isSample = true;
        } else if (selText.charAt(selText.length - 1) == ' ') 
        { //exclude ending space char
            selText = selText.substring(0, selText.length - 1);
            tagClose += ' ';
        }
    }
}


window.onload = function () 
{
   WBSetupToolbar();
}
 
