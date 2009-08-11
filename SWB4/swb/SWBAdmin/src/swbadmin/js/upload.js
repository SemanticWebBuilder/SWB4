/*********
* Javascript for file upload demo
* Copyright (C) Tomas Larsson 2006
* http://tomas.epineer.se/

* Licence:
* The contents of this file are subject to the Mozilla Public
* License Version 1.1 (the "License"); you may not use this file
* except in compliance with the License. You may obtain a copy of
* the License at http://www.mozilla.org/MPL/
* 
* Software distributed under this License is distributed on an "AS
* IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
* implied. See the License for the specific language governing
* rights and limitations under the License.
*/
function PeriodicalAjax(url, parameters, frequency, decay, onSuccess, onFailure) {
	function createRequestObject() {
		var xhr;
		try {
			xhr = new XMLHttpRequest();
		}
		catch (e) {
			xhr = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xhr;
	}
	
	function send() {
		if(!stopped) {
			xhr.open('post', url, true);
			xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
			xhr.onreadystatechange = function() { self.onComplete(); };
			xhr.send(parameters);
		}
	}
	
	this.stop = function() {
		stopped = true;
		clearTimeout(this.timer);
	}
	
	this.start = function() {
		stopped = false;
		this.onTimerEvent();
	}
	
	this.onComplete = function() {
		if(this.stopped) return false;
		if ( xhr.readyState == 4) {
			if(xhr.status == 200) {
				if(xhr.responseText == lastResponse) {
					decay = decay * originalDecay;
				} else {
					decay = 1;
				}
				lastResponse = xhr.responseText;
				if(onSuccess instanceof Function) {
					onSuccess(xhr);
				}
				this.timer = setTimeout(function() { self.onTimerEvent(); }, decay * frequency * 1000);
			} else {
				if(onFailure instanceof Function) {
					onFailure(xhr);
				}
			}
		}
	}
	
	this.getResponse = function() {
		if(xhr.responseText) {
			return xhr.responseText;
		}
	}
	
	this.onTimerEvent = function() {
		send();
	}
	
	var self = this;
	var stopped = false;
	var originalDecay = decay || 1.2;
	decay = originalDecay;
	var xhr = createRequestObject();
	var lastResponse = "";
	this.start();
}

function ProgressTracker(sid, options) {

	this.onSuccess = function(xhr) {
		if(parseInt(xhr.responseText) >= 100) {
			periodicalAjax.stop();
			if(options.onComplete instanceof Function) {
				options.onComplete();
			}
		} else if(xhr.responseText && xhr.responseText != lastResponse) {
			if(options.onProgressChange instanceof Function) {
				options.onProgressChange(xhr.responseText);
			}
			if(options.progressBar && options.progressBar.style) {
				options.progressBar.style.width = parseInt(xhr.responseText) + "%";
			}
		}
	}
	
	this.onFailure = function(xhr) {
		if(options.onFailure instanceof Function) {
			options.onFailure(xhr.responseText);
		} else {
			alert(xhr.responseText);
		}
		periodicalAjax.stop();
	}

	var self = this;
	var lastResponse = -1;
	options = options || {};
	var url = options.url || 'fileprogress.php';
	var frequency = options.frequency || 0.5;
	var decay = options.decay || 2;
    //MAPS74 desactivar chequeo
	var periodicalAjax = new PeriodicalAjax(url, 'sid=' + sid, frequency, decay, function(request){self.onSuccess(request);},function(request){self.onFailure(request);});
}