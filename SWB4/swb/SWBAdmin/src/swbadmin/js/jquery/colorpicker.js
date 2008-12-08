/**
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * (C) 2008 Syronex / J.M. Rosengard
 * http://www.syronex.com/software/jquery-color-picker
 *
 * - Check mark is either black or white, depending on the darkness 
 *   of the color selected.
 * - Fixed a bug in the original plugin that led to problems when there is 
 *   more than one colorPicker in a document.
 *
 * This is based on: 
 *
 * jQuery colorSelect plugin 0.9
 * http://plugins.jquery.com/project/colorPickerAgain
 * Copyright (c) 2008 Otaku RzO (Renzo Galo Castro Jurado).
 * (Original author URL & domain name no longer available.)
 *
 */

(function($) {
  $.fn.colorPicker = function($$options) {
    // Defaults
    var $defaults = {
      color:new Array('#ffffff', '#fff5ee', '#fff8dc', '#fffacd', '#ffffe0', '#98fb98', '#afeeee', '#e0ffff', '#e6e6fa', '#dda0dd',
                      '#d3d3d3', '#ffc0cb', '#ffe4c4', '#ffe4b5', '#f0e68c', '#90ee90', '#20b2aa', '#87cefa', '#6495ed', '#ee82ee',
                      '#c0c0c0', '#f08080', '#f4a460', '#ffa500', '#eee8aa', '#7fff00', '#48d1cc', '#87ceeb', '#7b68ee', '#da70d6',
                      '#808080', '#ff0000', '#ff4500', '#ff8c00', '#ffff00', '#32cd32', '#8fbc8f', '#4169e1', '#6a5acd', '#ba55d3',
                      '#696969', '#dc143c', '#d2691e', '#ff7f50', '#ffd700', '#228b22', '#2e8b57', '#0000ff', '#8a2be2', '#9932cc',
                      '#2f4f4f', '#b22222', '#8b4513', '#a0522d', '#808000', '#008000', '#008b8b', '#0000cd', '#483d8b', '#8b008b',
                      '#000000', '#8b0000', '#800000', '#a52a2a', '#556b2f', '#006400', '#006400', '#000080', '#4b0082', '#800080'),
      defaultColor: 0,
      columns: 0,
      click: function($color){}
    };
		
    var $settings = $.extend({}, $defaults, $$options);
		
    // Iterate and reformat each matched element
    return this.each(function() {
      var $this = $(this);
      // build element specific options
      var o = $.meta ? $.extend({}, $settings, $this.data()) : $settings;
      var $$oldIndex = typeof(o.defaultColor)=='number' ? o.defaultColor : -1;
      
      var _html = "";
      for(i=0;i<o.color.length;i++){
	_html += '<div style="background-color:'+o.color[i]+';"></div>';
	if($$oldIndex==-1 && o.defaultColor==o.color[i]) $$oldIndex = i;
      }
      
      $this.html('<div class="jColorSelect">'+_html+'</div>');
      var $color = $this.children('.jColorSelect').children('div');
      // Set container width
      var w = ($color.width()+2.5+2) * (o.columns>0 ? o.columns : o.color.length );
      $this.children('.jColorSelect').css('width',w);
      
      // Subscribe to click event of each color box
      $color.each(function(i){
	$(this).click(function(){
	  if( $$oldIndex == i ) return;	  
	  if( $$oldIndex > -1 ){
	    cell = $color.eq($$oldIndex);
	    if(cell.hasClass('check')) cell.removeClass(
	      'check').removeClass('checkwht').removeClass('checkblk');
	  }
	  // Keep index
	  $$oldIndex = i;
	  $(this).addClass('check').addClass(isdark(o.color[i]) ? 'checkwht' : 'checkblk');
	  // Trigger user event
	  o.click(o.color[i]);
	});
      });
      
      // Simulate click for defaultColor
      _tmp = $$oldIndex;
      $$oldIndex = -1;
      $color.eq(_tmp).trigger('click');
    });    
    return this;
  };
  
  
})(jQuery);

/**
 * Return true if color is dark, false otherwise.
 * (C) 2008 Syronex / J.M. Rosengard
 **/
function isdark(color){
  var colr = parseInt(color.substr(1), 16);
  return (colr >>> 16) // R
    + ((colr >>> 8) & 0x00ff) // G 
    + (colr & 0x0000ff) // B
    < 500;
}
