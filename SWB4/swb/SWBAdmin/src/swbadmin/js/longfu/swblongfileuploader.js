function LongFileUploader(baseURL, ruta, eleid, endurl) {
    this.baseURL = baseURL;
    this.ruta = ruta;
    this.toSendFiles = new Array();
    start = this.get(this.baseURL + "/start");
    this.chunkSize = start.chunkSize;
    this.eleid = eleid;
    this.endurl = endurl;
}

LongFileUploader.prototype.get = function(url, postdata) {
    //console.log("get: url: "+url);
    cliente = new XMLHttpRequest();
    tipo = "GET";
    if (postdata)
        tipo = "POST";
    cliente.open(tipo, url, false);
    if (postdata) {
        cliente.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        cliente.send(postdata);
    } else {
        cliente.send();
    }
    cadena = cliente.response;
    //console.log(cadena);
    objeto = JSON.parse(cadena);
    return objeto;
}

LongFileUploader.prototype.getFragment = function(blob, ini, fin) {
    //console.log("GetFragment: "+ini+" - "+fin);
    if (blob.mozSlice) {
        return blob.mozSlice(ini, fin);
    } else if (blob.webkitSlice) {
        return blob.webkitSlice(ini, fin);
    } else {
        return blob.slice(ini, fin);
    }
}


LongFileUploader.prototype.sendFragment = function(url, blob, crc) {
    //console.log("sendFragment: "+crc);
    var formData = new FormData();
    formData.append("crc", crc);
    formData.append("uploadedfile", blob);
    cliente = new XMLHttpRequest();
    cliente.open("POST", url, false);
    cliente.send(formData);
}

LongFileUploader.prototype.calcIDCRC = function(blob, callback, me) {
    var blockSize = 8192;
    if (blob.size < 8192) {
        blockSize = blob.size;
    }
    var fileReader = new FileReader();
    fileReader.onloadend = function(rev) {
        if (rev.target.readyState == FileReader.DONE) {
            callback(decimalToHexString(crc32(rev.target.result)), blob, me);
        }
    };
    fileReader.readAsBinaryString(this.getFragment(blob, 0, blockSize));
}

LongFileUploader.prototype.preparaEnvio = function(url, porEnviar) {
    datos = "filename=" + porEnviar.name + "&size=" + porEnviar.size
            + "&iniCRC=" + porEnviar.crc + "&sDate=" + porEnviar.date + "&localId=0";
    //console.log("preparaEnvio: "+datos);
    var prepara = this.get(url, datos);
    porEnviar.id = prepara.file2Upload.id;
}

LongFileUploader.prototype.sendFile = function(file) {
    var arch = file.files[0];
    //console.log("sendFile:"+arch.name);
    this.calcIDCRC(arch, function(crc, file, me) {
        var porEnviar = new Object();
        porEnviar.blob = file;
        porEnviar.crc = crc;
        porEnviar.name = file.name;
        porEnviar.size = file.size;
        porEnviar.date = file.lastModifiedDate;
        porEnviar.id = null;
        porEnviar.sending = false;
        me.toSendFiles[me.toSendFiles.length] = porEnviar;
        me.preparaEnvio(me.baseURL + "/uploadSolicitude", porEnviar);
        setTimeout(me.processFile(porEnviar), 500);
    }, this);
}

LongFileUploader.prototype.finishFile = function(porEnviar) {
    document.getElementById(this.eleid + "_percentage").style.width = 100 + '%';
    document.getElementById(this.eleid + "_label").innerHTML = 100 + '%';
    var result = this.get(this.baseURL + "/eofcheck/" + porEnviar.id + "?dirToPlace=" + this.ruta);
    if (result.saved=="true")
        alert("El archivo " + porEnviar.name + " fue recibido");
    else
        alert("El archivo no pudo moverse a " + this.ruta);
    window.location=this.endurl;
}

LongFileUploader.prototype.processFile = function(porEnviar) {
    //console.log("processFile:"+porEnviar.name+" "+porEnviar.sending);
    if (!porEnviar.sending) {
        porEnviar.sending = true;
        var status = this.get(this.baseURL + "/status/" + porEnviar.id);
        var ini = status.bytesRecived;
        var end = ini + this.chunkSize;
        var chunk = this.getFragment(porEnviar.blob, ini, end);
        var reader = new FileReader();
        var me = this;
        var progress = Math.round(ini * 100 / porEnviar.size);
        document.getElementById(this.eleid + "_percentage").style.width = progress + '%';
        document.getElementById(this.eleid + "_label").innerHTML = progress + '%';
        reader.onloadend = function(e) {
            if (e.target.readyState == FileReader.DONE) {
                var digest = crc32(e.target.result);
                me.sendFragment(me.baseURL + "/uploadchunk/" + porEnviar.id, chunk,
                        decimalToHexString(digest));
                porEnviar.sending = false;
                if (end < porEnviar.size) {
                    setTimeout(me.processFile(porEnviar), 10);
                } else {
                    setTimeout(me.finishFile(porEnviar), 10);
                }
            }
        };
        reader.readAsBinaryString(chunk);
    }
}








/*  
 ===============================================================================
 Crc32 is a JavaScript function for computing the CRC32 of a string
 ...............................................................................
 
 Version: 1.2 - 2006/11 - http://noteslog.com/post/crc32-for-javascript/
 
 -------------------------------------------------------------------------------
 Copyright (c) 2006 Andrea Ercolino      
 http://www.opensource.org/licenses/mit-license.php
 ===============================================================================
 */

var strTable = "00000000 77073096 EE0E612C 990951BA 076DC419 706AF48F E963A535 9E6495A3 0EDB8832 79DCB8A4 E0D5E91E 97D2D988 09B64C2B 7EB17CBD E7B82D07 90BF1D91 1DB71064 6AB020F2 F3B97148 84BE41DE 1ADAD47D 6DDDE4EB F4D4B551 83D385C7 136C9856 646BA8C0 FD62F97A 8A65C9EC 14015C4F 63066CD9 FA0F3D63 8D080DF5 3B6E20C8 4C69105E D56041E4 A2677172 3C03E4D1 4B04D447 D20D85FD A50AB56B 35B5A8FA 42B2986C DBBBC9D6 ACBCF940 32D86CE3 45DF5C75 DCD60DCF ABD13D59 26D930AC 51DE003A C8D75180 BFD06116 21B4F4B5 56B3C423 CFBA9599 B8BDA50F 2802B89E 5F058808 C60CD9B2 B10BE924 2F6F7C87 58684C11 C1611DAB B6662D3D 76DC4190 01DB7106 98D220BC EFD5102A 71B18589 06B6B51F 9FBFE4A5 E8B8D433 7807C9A2 0F00F934 9609A88E E10E9818 7F6A0DBB 086D3D2D 91646C97 E6635C01 6B6B51F4 1C6C6162 856530D8 F262004E 6C0695ED 1B01A57B 8208F4C1 F50FC457 65B0D9C6 12B7E950 8BBEB8EA FCB9887C 62DD1DDF 15DA2D49 8CD37CF3 FBD44C65 4DB26158 3AB551CE A3BC0074 D4BB30E2 4ADFA541 3DD895D7 A4D1C46D D3D6F4FB 4369E96A 346ED9FC AD678846 DA60B8D0 44042D73 33031DE5 AA0A4C5F DD0D7CC9 5005713C 270241AA BE0B1010 C90C2086 5768B525 206F85B3 B966D409 CE61E49F 5EDEF90E 29D9C998 B0D09822 C7D7A8B4 59B33D17 2EB40D81 B7BD5C3B C0BA6CAD EDB88320 9ABFB3B6 03B6E20C 74B1D29A EAD54739 9DD277AF 04DB2615 73DC1683 E3630B12 94643B84 0D6D6A3E 7A6A5AA8 E40ECF0B 9309FF9D 0A00AE27 7D079EB1 F00F9344 8708A3D2 1E01F268 6906C2FE F762575D 806567CB 196C3671 6E6B06E7 FED41B76 89D32BE0 10DA7A5A 67DD4ACC F9B9DF6F 8EBEEFF9 17B7BE43 60B08ED5 D6D6A3E8 A1D1937E 38D8C2C4 4FDFF252 D1BB67F1 A6BC5767 3FB506DD 48B2364B D80D2BDA AF0A1B4C 36034AF6 41047A60 DF60EFC3 A867DF55 316E8EEF 4669BE79 CB61B38C BC66831A 256FD2A0 5268E236 CC0C7795 BB0B4703 220216B9 5505262F C5BA3BBE B2BD0B28 2BB45A92 5CB36A04 C2D7FFA7 B5D0CF31 2CD99E8B 5BDEAE1D 9B64C2B0 EC63F226 756AA39C 026D930A 9C0906A9 EB0E363F 72076785 05005713 95BF4A82 E2B87A14 7BB12BAE 0CB61B38 92D28E9B E5D5BE0D 7CDCEFB7 0BDBDF21 86D3D2D4 F1D4E242 68DDB3F8 1FDA836E 81BE16CD F6B9265B 6FB077E1 18B74777 88085AE6 FF0F6A70 66063BCA 11010B5C 8F659EFF F862AE69 616BFFD3 166CCF45 A00AE278 D70DD2EE 4E048354 3903B3C2 A7672661 D06016F7 4969474D 3E6E77DB AED16A4A D9D65ADC 40DF0B66 37D83BF0 A9BCAE53 DEBB9EC5 47B2CF7F 30B5FFE9 BDBDF21C CABAC28A 53B39330 24B4A3A6 BAD03605 CDD70693 54DE5729 23D967BF B3667A2E C4614AB8 5D681B02 2A6F2B94 B40BBE37 C30C8EA1 5A05DF1B 2D02EF8D".split(' ');

var table = new Array();
for (var i = 0; i < strTable.length; ++i) {
    table[i] = parseInt("0x" + strTable[i]);
}

/* Number */
function crc32(/* String */str) {
    var crc = 0;
    var n = 0; //a number between 0 and 255
    var x = 0; //an hex number

    crc = crc ^ (-1);
    for (var i = 0, iTop = str.length; i < iTop; i++) {
        n = (crc ^ str.charCodeAt(i)) & 0xFF;
        crc = (crc >>> 8) ^ table[n];
    }
    return crc ^ (-1);
}

function decimalToHexString(number) {
    if (number < 0) {
        number = 0xFFFFFFFF + number + 1;
    }

    return number.toString(16).toLowerCase();
}