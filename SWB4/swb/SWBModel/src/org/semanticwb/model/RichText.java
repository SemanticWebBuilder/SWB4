
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
 * The Class RichText.
 */
public class RichText extends org.semanticwb.model.base.RichTextBase {
    
    /**
     * Instantiates a new rich text.
     * 
     * @param base the base
     */
    public RichText(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.FormElementBase#process(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty)
     */
    /**
     * Process.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     */
    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop) {
        if (isFilterHTMLTags()) {
            setFilterHTMLTags(false);
        }

        super.process(request, obj, prop);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.TextArea#renderXHTML(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type,
                                String mode, String lang)
    {
        String         ret      = "";
        String         name     = prop.getName();
        String         label    = prop.getDisplayName(lang);
        SemanticObject sobj     = prop.getDisplayProperty();
        boolean        required = prop.isRequired();
        String         pmsg     = null;
        String         imsg     = null;

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);

            pmsg = dobj.getPromptMessage();
            imsg = dobj.getInvalidMessage();
        }

        if (imsg == null && required) {
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

        String value = request.getParameter(prop.getName());

        if (value == null) {
            value = obj.getProperty(prop);
        }

        if (value == null) {
            value = "";
        }

        if (mode.equals("edit") || mode.equals("create")) {
            ret = "<textarea name=\"" + name + "\" dojoType=\"dijit.Editor\" rows=\"" + getRows() + "\" cols=\""
                  + getCols() + "\" " + getAttributes() + ">" + value + "</textarea>";
        } else if (mode.equals("view")) {
            ret = "<span _id=\"" + name + "\" name=\"" + name + "\">" + value + "</span>";
        }

        return ret;
    }
}
