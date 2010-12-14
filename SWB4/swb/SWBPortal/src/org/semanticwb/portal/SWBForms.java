/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

/**
 *
 * @author jei
 */
public interface SWBForms {

    /** The Constant MODE_VIEW. */
    public static final String MODE_VIEW="view";

    /** The Constant MODE_EDIT. */
    public static final String MODE_EDIT="edit";

    /** The Constant MODE_CREATE. */
    public static final String MODE_CREATE="create";

    /** The Constant TYPE_XHTML. */
    public static final String TYPE_XHTML="xhtml";

    /** The Constant TYPE_DOJO. */
    public static final String TYPE_DOJO="dojo";

    /** The Constant TYPE_IPHONE. */
    public static final String TYPE_IPHONE="iphone";

    /** The Constant PRM_ID. */
    public static final String PRM_ID="id";

    /** The Constant PRM_MODE. */
    public static final String PRM_MODE="smode";

    /** The Constant PRM_REF. */
    public static final String PRM_REF="sref";

    /** The Constant PRM_URI. */
    public static final String PRM_URI="suri";

    /** The Constant PRM_CLS. */
    public static final String PRM_CLS="scls";

    /** The DOJ o_ required. */
    public static String DOJO_REQUIRED=
        "    <script type=\"text/javascript\">"+"\n"+
        "      // scan page for widgets and instantiate them"+"\n"+
        "      dojo.require(\"dojo.parser\");"+"\n"+
        "      dojo.require(\"dijit._Calendar\");"+"\n"+
        "      dojo.require(\"dijit.ProgressBar\");"+"\n"+
        ""+"\n"+
        "      // editor:"+"\n"+
        "      dojo.require(\"dijit.Editor\");"+"\n"+
        ""+"\n"+
        "      // various Form elemetns"+"\n"+
        "      dojo.require(\"dijit.form.Form\");"+"\n"+
        "      dojo.require(\"dijit.form.CheckBox\");"+"\n"+
        "      dojo.require(\"dijit.form.Textarea\");"+"\n"+
        "      dojo.require(\"dijit.form.FilteringSelect\");"+"\n"+
        "      dojo.require(\"dijit.form.TextBox\");"+"\n"+
        "      dojo.require(\"dijit.form.DateTextBox\");"+"\n"+
        "      dojo.require(\"dijit.form.TimeTextBox\");"+"\n"+
        "      dojo.require(\"dijit.form.Button\");"+"\n"+
        "      dojo.require(\"dijit.form.NumberSpinner\");"+"\n"+
        "      dojo.require(\"dijit.form.Slider\");"+"\n"+
        "      dojo.require(\"dojox.form.BusyButton\");"+"\n"+
        "      dojo.require(\"dojox.form.TimeSpinner\");"+"\n"+
        "    </script>"+"\n";

}
