function showDialog(url, title) {
    dojo.xhrGet({
        url: url,
        load: function(response, ioArgs) {
            document.getElementById("dialog").style.display="";
            document.getElementById("dialogTitle").innerHTML=title;
            setDialogLoading(false);

            setDialogContent(response);
            document.getElementById("dialogBack").style.display="";
            return response;
        },
        error: function(response, ioArgs) {
            setDialogContent(response);
            return response;
        },
        handleAs: "text"
    });
}

function hideDialog() {
    document.getElementById("dialog").style.display="none";
    document.getElementById("dialogBack").style.display="none";
}

function setDialogContent(content) {
    var c = document.getElementById("dialogContent");
    c.innerHTML = "";
    c.innerHTML = content;
}

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

var intCounterToast = 0;
function loadPageUrl(url, paramName, paramValue) {
    var dest = url;
    if (paramName != null && paramValue != null && paramValue != "") {
        dest+="&"+paramName+"="+paramValue;
    }
    window.location = dest;
}

function showToast(msg) {
    var toast = document.getElementById("toast");
    toast.style.opacity = 0.7;
    toast.style.visibility = "visible";
    toast.innerHTML = "<b>"+msg+"</b>";
    intCounterToast = setInterval("hideToast()", 2000);
}

function hideToast() {
    var toast = document.getElementById("toast");
    toast.style.display = "none";
    toast.style.opacity = 0;
    clearInterval(intCounterToast);
}