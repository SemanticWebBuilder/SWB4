/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */




/*
 * Allow Internet Explorer and older browsers to use the method indexOf
 * This prototype is provided by the Mozilla foundation and
 * is distributed under the MIT license.
 * http://www.ibiblio.org/pub/Linux/LICENSES/mit.license
 */

if (!Array.prototype.indexOf)
{
  Array.prototype.indexOf = function(elt /*, from*/)
  {
    var len = this.length;

    var from = Number(arguments[1]) || 0;
    from = (from < 0)
         ? Math.ceil(from)
         : Math.floor(from);
    if (from < 0)
      from += len;

    for (; from < len; from++)
    {
      if (from in this &&
          this[from] === elt)
        return from;
    }
    return -1;
  };
}
/*
 * fin
 * indexOf
 * 
 */

  
  
  
  
  /*
   * comboxDataBind.
   * Permite asociar dos comboboxes en una relación maestro-esclavo.
   * Para resolver cuestiones como comboboxes de entidad y población.
   * El combobox maestro se escribe como sigue y el esclavo usando dojoType='ComboBox'
   * Ejemplo:
   * 
   * Este el combobox maestro:
   * <label for="provincia">Provincia</label>
   * <select name="provincia" id="provincia" onchange="comboxDataBind(this.value);">
   *	<option value="1" selected>Alava</option>
   *            .
   *            .
   *            .
   *	<option value="50" >Zaragoza</option>
   * </select>
   *	
   * A continuación viene el combobox esclavo
   * <label for="comboPoblaciones">Poblaci&oacute;n</label>
   * <input dojoType='ComboBox' dataUrl='json/ajax_poblacionesjson1' style='width: 300px;' autoComplete='true' name="poblacion" id="comboPoblaciones" />
   * 
   * 
   */
  
  
  function comboxDataBind(url, comboxSlaveId) {
    //var url = 'json/ajax_poblacionesjson' + prov;
    var combo = dojo.widget.byId(comboxSlaveId);

    if(!combo) {
      var comboArgs = {
        dataUrl: url,
        maxListLength: 10,
        fadeTime: 100
      };
      combo = dojo.widget.fromScript("ComboBox", comboArgs, dojo.byId(comboxSlaveId));
      combo.name = comboxSlaveId;
      combo.formInputName = comboxSlaveId;
    }

    dojo.io.bind({ 
      url: url, 
      load: function(type, data, evt) { 
        combo.dataProvider.setData(data)
      },
      mimetype: "text/json"
    });		
}

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  