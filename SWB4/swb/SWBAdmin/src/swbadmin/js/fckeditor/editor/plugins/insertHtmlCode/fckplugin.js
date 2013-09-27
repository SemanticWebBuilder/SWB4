/*
 * File Name: fckplugin.js
 * Plugin to launch the Insert Code dialog in FCKeditor
 */

// Register the related command.
FCKCommands.RegisterCommand( 'insertHtmlCode', new FCKDialogCommand( 'InsertHtmlCode', FCKLang.InsertHtmlCode, FCKPlugins.Items['insertHtmlCode'].Path + 'fck_insertHtmlCode.html', 415, 300 ) ) ;

// Create the "insertHtmlCode" toolbar button.
var oinsertHtmlCodeItem = new FCKToolbarButton( 'insertHtmlCode', FCKLang.InsertHtmlCode, FCKLang.InsertHtmlCode, null, null, false, true) ;
oinsertHtmlCodeItem.IconPath = FCKPlugins.Items['insertHtmlCode'].Path + 'insertHtmlCode_icon.png' ;

FCKToolbarItems.RegisterItem( 'insertHtmlCode', oinsertHtmlCodeItem ) ;

