	var updater = null;

	function checkStatus(field) {
        uploadProxy.getStatus(function(stat) {
	        if (stat.status == 2) {
		    updateProgressBar(field,100);
		    return;
		}
		if (stat.status == 3) {
		    alert("Ha ocurrido un error! " + stat.message);
		    return;
		}

		if (stat.status == 4) {
		    alert("Ha ocurrido un error! " + stat.message);
		    return;
		}
		// do something with the percentage (nice loading bar, simply show the percentage, etc)
		updateProgressBar(field,stat.percentComplete);
		window.setTimeout("checkStatus('"+field+"')", 500);
	    });
	}

	function updateProgressBar(field, percentage) {
        var progress = document.getElementById("uploadProgressBar_"+field);
        var indicator = document.getElementById("uploadIndicator_"+field);
        var maxWidth = parseIntWithPx(progress.style.width) - 4;
        var width = percentage * maxWidth / 100;
        indicator.style.width = width + "px";
	    var perc = document.getElementById("uploadPercentage_"+field);
        perc.innerHTML = percentage + "%";
	}


	function parseIntWithPx(str) {
	    var strArray = str.split("p");
	    return parseInt(strArray[0]);
	}

	function startUploadMonitoring(field) {
	    window.setTimeout("checkStatus('"+field+"')", 500);
	    return true;
	}

