/*
 * File Name: fckplugin.js
 * Plugin to launch the Insert Code dialog in FCKeditor
 */

// Register the related command.
FCKCommands.RegisterCommand( 'insertHtmlCode', new FCKDialogCommand( FCKLang['insertHTMLCodeDlgTitle'], FCKLang['insertHTMLCodeDlgTitle'], FCKPlugins.Items['insertHtmlCode'].Path + 'fck_insertHtmlCode.html', 415, 300 ) ) ;

// Create the "insertHtmlCode" toolbar button.
var oinsertHtmlCodeItem = new FCKToolbarButton( 'insertHtmlCode', FCKLang['insertHTMLCodeDlgTitle'], FCKLang['insertHTMLCodeDlgTitle']) ; //, null, null, false, true
oinsertHtmlCodeItem.IconPath = FCKPlugins.Items['insertHtmlCode'].Path + 'insertHtmlCode_icon.png' ;

FCKToolbarItems.RegisterItem( 'insertHtmlCode', oinsertHtmlCodeItem ) ;

