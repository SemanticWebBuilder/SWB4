//***** inicia funciones genéricas para ajax *****
	
	var ns4 = (document.layers)? true:false
	var ie4 = (document.all)? true:false


	function makeRequest(url, data, functionPointer) {

       if (window.XMLHttpRequest){
			http_request = new XMLHttpRequest();
			if (http_request.overrideMimeType) {
				http_request.overrideMimeType("text/xml");
			}
		}
		else if (window.ActiveXObject) {
			try{
				http_request = new ActiveXObject("Msxml2.XMLHTTP");
			}
			catch(e){
				try{
					http_request = new ActiveXObject("Microsoft.XMLHTTP");
				}
				catch (e){}
			}
		}

		if(null != http_request){
			http_request.onreadystatechange=functionPointer;
			http_request.open('POST', url, true);
			http_request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
			http_request.send(data);
		}
	}

   
    /**
     * Realiza la carga del HTML de la tabla de titulares seleccionados obtenido mediante una llamada de AJAX.
     */
	function realizaCargaEmpresaHTML(){

		if (http_request.readyState==4){
			if (http_request.status==200){
				var container 		= 	document.getElementById("cuponBody");
				container.innerHTML =  	http_request.responseText;
			}
		}
	}
     /**
      * Realiza la carga del HTML de la tabla de titulares seleccionados obtenido mediante una llamada de AJAX.
      */
 	function realizaCargaConfirmacionHTML(){

 		if (http_request.readyState==4){
 			if (http_request.status==200){
 				var container 		= document.getElementById("cuponConfirmacion");
			
 				container.style.display = "block";
 				
 				var containerHidden 		= document.getElementById("cuponBody");
 				containerHidden.style.display = "none";
 				container.innerHTML =  http_request.responseText;
 			}
 		}
 	}

	/**
	 * Añade los controles y sus valores de acuerdo a los nombres indicados ene los parámetros.  
	 * @param fields Cadena de identificadores de los controles separados por coma.
	 * @param postString Cadena a la cual se concatenan los datos.
	 * @param Cadena con los datos concatenados
	 */
	function addFields(fields, postString, form){
		var listArray;
		var iField, numFields, field, column;

		listArray = fields.split(',');
		numFields = listArray.length;

		for(iField = 0; iField < numFields; iField++){
			field = listArray[iField];
						
			if(eval('document.'+ form.id +'.' + field)){
				if(eval('document.'+ form.id +'.' + field + '.length')){
					numColumns = eval('document.'+ form.id +'.' + field + '.length');
					for(i=0; i < numColumns; i++){
						postString = postString + '&' + field + '=' + escape(eval('document.'+ form.id +'.' + field + '[' + i + '].value'));
					}
				}else{
					postString = postString + '&' + field + '=' + escape(eval('document.'+ form.id +'.' + field + '.value'));
				}
			}
		}

		return(postString);
	}
	 
    /**
     * Agrega un renglon asociado con un titular a la tabla de titulares seleccionados.
     * @param objControl Referencia a un control dentro de la forma.
     * @param indice Índice que hace referencia al consecutivo de titular agregado a la tabla.
     */
    function buscaEmpresa(url, objControl) {
    	
        var objForm		= objControl.form;
        
        objControl.disabled = true;

        //concatenamos los parametros    
		var lista_campos    =   '';    
		lista_campos    	+=  'idCertificado';
		
		var objCert 	= objForm.elements['idCertificado'];
		if (objCert != null && objCert.value != null && objCert.value != "") {			    
			var postString      =  '';	    
		    postString 			= addFields(lista_campos, postString, objForm);			    
		    makeRequest(url, postString, realizaCargaEmpresaHTML);
		} else {
			alert("Introduzca un certificado");
			objControl.disabled = false;
		}
		
	    return;
    }
     
     /**
      * Agrega un renglon asociado con un titular a la tabla de titulares seleccionados.
      * @param objControl Referencia a un control dentro de la forma.
      * @param indice Índice que hace referencia al consecutivo de titular agregado a la tabla.
      */
     function asociaEmpresa(url, objControl) {
     	
         var objForm		= objControl.form;
         
         objControl.disabled = true;

         //concatenamos los parametros    
 		var lista_campos    =   '';    
 		lista_campos    	+=  'ceritificadoUri,empresaUri';
 			    
 		var postString      =  '';
 	    
 	    postString 			= addFields(lista_campos, postString, objForm);
 			    
 	    makeRequest(url, postString, realizaCargaConfirmacionHTML);
 		
 	    return;
     }
      
  	function validaCheck(objBtn, idCheckGroup) {	
  		var isChecked	= false;
		var forma 		= objBtn.form
		for (var i = 0; i<forma.elements.length; i++) {
			var e = forma.elements[i];
			if ((e.id.indexOf(idCheckGroup) != -1) && (e.type=='checkbox')) {
				if (e.checked) {
					isChecked = true;
					break;
				}				
			}								
		}
		
		return isChecked;
	}
	function enviarEmpresasInteres(url, objBtn) {
		objBtn.disabled = true;		
		var forma 	= objBtn.form;
		forma.action = url;
		if (validaCheck(objBtn, 'chkEmpresas')) {
			forma.submit();
		} else {
			alert("Debe seleccionar al menos una empresa")
		}
		objBtn.disabled = false;
		return;
	}
	function enviarEmpresasInteresFicha(url, objBtn) {
		objBtn.disabled = true;		
		var forma 	= objBtn.form;
		forma.action = url;
		forma.submit();
		objBtn.disabled = false;
		return;
	}	
	function enviarBusquedas(url, objBtn) {
		objBtn.disabled = true;		
		var forma 	= objBtn.form;
		forma.action = url;
		forma.submit();		
		return;
	}

	function enviarForma(url, objBtn) {
		objBtn.disabled = true;		
		var forma 	= objBtn.form;
		forma.action = url;
		forma.submit();
		objBtn.disabled = false;		
		return;
	}
	function eliminaBusquedasCarpeta(url, objBtn, idChk) {
		if (validaCheck(objBtn, idChk)) {
			enviarForma(url, objBtn);
		} else {
			alert("Debe seleccionar al menos una consulta");
		}		
		return;
	}
	function eliminaEmpresasCarpeta(url, objBtn, idChk) {
		if (validaCheck(objBtn, idChk)) {
			enviarForma(url, objBtn);
		} else {
			alert("Debe seleccionar al menos una empresa");
		}		
		return;
	}
	function eliminaProductosCarpeta(url, objBtn, idChk) {
		if (validaCheck(objBtn, idChk)) {
			enviarForma(url, objBtn);
		} else {
			alert("Debe seleccionar al menos un producto");
		}		
		return;
	}
	function muestraMensaje(msg) {
		
		if(msg != null 
				&& msg != '') {
			alert(msg);
		}
		
		return;
	}
	
	// Funciones para despliega resultados...
	function desplieguaDescripcion(idAnchor, idCell) {
		var cell = document.getElementById(idCell);
		if (cell != null) {			
			var modDispl  = cell.style.display;
			if (modDispl == "block") {
				cell.style.display = "none";
				cambiaEstadoDisplayAllDescrip('formTableRes');
				var objAnchor = document.getElementById(idAnchor);
				objAnchor.innerHTML = '<img src="/work/models/sieps/Template/2/1/images/bulletVerde_tabla.jpg" alt=" " width="10" height="10" /> Abrir detalle';
								
			} else {
				cell.style.display = "block";
				var objAnchor = document.getElementById(idAnchor);
				objAnchor.innerHTML = '<img src="/work/models/sieps/Template/2/1/images/bulletGris_tabla.jpg" alt=" " width="10" height="10" /> Cerrar detalle';
			}
		}
		return;
	}
	function desplieguaTodasDescripcion(objChk) {
		var displayType = (objChk.checked) ? "block" : "none";
		var table = document.getElementById("tablaResultados");
		var rows  = table.rows;
		for (var i = 0; i<rows.length; i++) {
			var row = rows[i];
			//rowBullets
            //cellBulletDescrip
			if (row != null && row.id.indexOf("rowBullets")!= -1 ) {
				var cells = row.cells;
				
				cells[0].children[0].innerHTML = (displayType == 'block')
															? '<img src="/work/models/sieps/Template/2/1/images/bulletGris_tabla.jpg" alt=" " width="10" height="10" /> Cerrar detalle'
															: '<img src="/work/models/sieps/Template/2/1/images/bulletVerde_tabla.jpg" alt=" " width="10" height="10" /> Abrir detalle';		
			}
            			
			if (row != null && row.id.indexOf("rowDescrip")!= -1 ) {
				var cells = row.cells;
				cells[0].style.display = displayType;		
			}
		}
		return;
	}
	function desplieguaTodasEmpresas(objChk) {
		var checkedType = (objChk.checked) ? true : false;
		var forma 	= objChk.form

		for (var i = 0; i<forma.elements.length; i++) {
			var e = forma.elements[i];
			if ((e.id.indexOf("chkEmpresas") != -1) && (e.type=='checkbox')) {
				e.checked = checkedType;
			}								
		}
		
		return;
	}
	function cambiaEstadoSelectAllEmpresas(objChk) {
		var checkedType = (objChk.checked) ? true : false;
		var forma 	= objChk.form
		var objChkAll = forma.elements["checkAllEmpresas"];
		if (objChkAll.checked && !checkedType) {
			objChkAll.checked  =false;
		}		
		return;
	}	
	function cambiaEstadoDisplayAllDescrip(idForm) {		
		var forma 	= document.forms[idForm]
		var objChkAll = forma.elements["checkAllDescrip"];		
		objChkAll.checked  =false;
				
		return;
	}
	
	// resultados productos
	
	function desplieguaTodosProductos(objChk) {
		var checkedType = (objChk.checked) ? true : false;
		var forma 	= objChk.form

		for (var i = 0; i<forma.elements.length; i++) {
			var e = forma.elements[i];
			if ((e.id.indexOf("uriProductos") != -1) && (e.type=='checkbox')) {
				e.checked = checkedType;
			}								
		}
		
		return;
	}
	function cambiaEstadoSelectAllProductos(objChk) {
		var checkedType = (objChk.checked) ? true : false;
		var forma 	= objChk.form
		var objChkAll = forma.elements["checkAllProductos"];
		if (objChkAll.checked && !checkedType) {
			objChkAll.checked  =false;
		}		
		return;
	}
	
	function enviarProductosInteres(url, objBtn) {
		objBtn.disabled = true;		
		var forma 	= objBtn.form;
		forma.action = url;
		if (validaCheck(objBtn, 'uriProductos')) {
			forma.submit();
		} else {
			alert("Debe seleccionar al menos un producto")
		}
		objBtn.disabled = false;
		return;
	}

	function enviarProductosInteresFicha(url, objBtn) {
		objBtn.disabled = true;		
		var forma 	= objBtn.form;
		forma.action = url;
		forma.submit();		
		return;
	}
	function enviaProductoCatalago(url, objBtn) {
		objBtn.disabled = true;		
		var forma 	= objBtn.form;
		forma.action = url;
		forma.submit();		
		return;
	}	