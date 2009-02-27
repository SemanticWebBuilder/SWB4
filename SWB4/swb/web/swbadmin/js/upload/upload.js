	var updater = null;

	function checkStatus() {
	    uploadProxy.getStatus(function(stat) {
	        if (stat.status == 2) {
		    updateProgressBar(100);
		    return;
		}
		if (stat.status == 3) {
		    alert("An error has occured! " + stat.message);
		    return;
		}

		if (stat.status == 4) {
		    alert("An error has occured! " + stat.message);
		    return;
		}
		// do something with the percentage (nice loading bar, simply show the percentage, etc)
		updateProgressBar(stat.percentComplete);
		window.setTimeout("checkStatus()", 500);
	    });
	}

	function updateProgressBar(percentage) {
	    // make sure you set the width style property for uploadProgressBar, otherwise progress.style.width won't work
	    var progress = document.getElementById("uploadProgressBar");
	    var indicator = document.getElementById("uploadIndicator");
	    var maxWidth = parseIntWithPx(progress.style.width) - 4;
	    var width = percentage * maxWidth / 100;
	    indicator.style.width = width + "px";
	    var perc = document.getElementById("uploadPercentage");
	    perc.innerHTML = percentage + "%";
	}

	function parseIntWithPx(str) {
	    var strArray = str.split("p");
	    return parseInt(strArray[0]);
	}

	function startUploadMonitoring() {
	    window.setTimeout("checkStatus()", 500);
	    return true;
	}
    