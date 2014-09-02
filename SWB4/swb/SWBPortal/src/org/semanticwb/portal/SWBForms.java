/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
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
        "      dojo.require(\"dojox.form.Uploader\");"+"\n"+
        "    </script>"+"\n";

}
