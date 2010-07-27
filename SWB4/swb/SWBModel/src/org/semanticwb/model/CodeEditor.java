
/**
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
package org.semanticwb.model;

//~--- non-JDK imports --------------------------------------------------------

import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

//~--- JDK imports ------------------------------------------------------------

import javax.servlet.http.HttpServletRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class CodeEditor.
 */
public class CodeEditor extends org.semanticwb.model.base.CodeEditorBase {
    
    /**
     * Instantiates a new code editor.
     * 
     * @param base the base
     */
    public CodeEditor(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.TextArea#renderXHTML(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type,
                                String mode, String lang)
    {
        StringBuffer   ret      = new StringBuffer(250);
        String         id       = obj.getURI() + "/" + prop.getName() + "_editArea";
        String         name     = prop.getName();
        String         label    = prop.getDisplayName(lang);
        SemanticObject sobj     = prop.getDisplayProperty();
        boolean        required = prop.isRequired();
        String         pmsg     = null;
        String         imsg     = null;
        String         language = "es";

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);

            pmsg = dobj.getPromptMessage();
            imsg = dobj.getInvalidMessage();
        }

        if (imsg == null && required)
        {
            imsg = label + " es requerido.";

            if (lang.equals("en")) {
                imsg = label + " is required.";
            }
        }

        if (pmsg == null) {
            pmsg = "Captura " + label + ".";
            if (lang.equals("en")) {
                pmsg = "Enter " + label + ".";
            }
        }

        if (lang.equals("en")) {
            language = lang;
        }

        String value = request.getParameter(prop.getName());

        if (value == null) {
            value = obj.getProperty(prop);
        }

        if (value == null) {
            value = "";
        }

        if (mode.equals("edit") || mode.equals("create")) {
            ret.append("<script language=\"javascript\" type=\"text/javascript\" charset=\"UTF-8\">\n");
            ret.append("editAreaLoader.init({\n");
            ret.append("    id : \"" + id + "\"\n");
            ret.append("    ,language: \"" + language + "\"\n");
            ret.append("    ,syntax: \"" + getLanguage().toLowerCase() + "\"\n");
            ret.append("    ,start_highlight: true\n");
            ret.append("    ,toolbar: \"search, go_to_line,");
            ret.append(" |, undo, redo, |, select_font,|, change_smooth_selection,");
            ret.append(" highlight, reset_highlight, |, help\"\n");
            ret.append("    ,is_multi_files: false\n");
            ret.append("    ,allow_toggle: false\n");
            ret.append("});\n");
            ret.append("\n");
            ret.append("  function my_save(id, content){\n");
            ret.append("\n");
            ret.append("  }\n");
            ret.append("</script>\n");
            ret.append("<textarea id=\"" + id + "\" name=\"" + name + "\" rows=\"");
            ret.append(getRows() + "\" cols=\"" + getCols() + "\">");
            ret.append(value + "</textarea>\n");
            ret.append("\n");
        } else if (mode.equals("view")) {
            ret.append("<span _id=\"" + id + "\" name=\"" + name + "\">" + value + "</span>\n");
        }

        ret.append("Test:" + getLanguage());

        return ret.toString();
    }
}

