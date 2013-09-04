function setDialogLoading(show, msg) {
    if (show) {
        document.getElementById("dialogLoading").style.display="";
        document.getElementById("dialogContent").style.display="none";
        document.getElementById("dialogLoadingTitle").innerHTML=msg;
    } else {
        document.getElementById("dialogLoading").style.display="none";
        document.getElementById("dialogContent").style.display="";
    }
}

function loadPageUrl(url, paramName, paramValue) {
    var dest = url;
    if (paramName != null && paramValue != null && paramValue != "") {
        dest+="&"+paramName+"="+paramValue;
    }
    window.location = dest;
}