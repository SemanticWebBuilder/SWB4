
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

import org.semanticwb.SWBPlatform;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class TextArea.
 */
public class TextArea extends TextAreaBase {
    
    /**
     * Instantiates a new text area.
     * 
     * @param base the base
     */
    public TextArea(SemanticObject base) {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.FormElementBase#renderElement(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type,
                                String mode, String lang) 
    {
        if (obj == null) {
            obj = new SemanticObject();
        }        
//        boolean IPHONE = false;
//        boolean XHTML  = false;
        boolean DOJO   = false;

//        if (type.equals("iphone")) {
//            IPHONE = true;
//        } else if (type.equals("xhtml")) {
//            XHTML = true;
//        } else
        if (type.equals("dojo")) {
            DOJO = true;
        }
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
        if (required && imsg == null) {
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
            // *******************************************************
            //Iterator itAttr = attributes.keySet().iterator();

//            while (itAttr.hasNext()) {
//                String attrName = (String) itAttr.next();
//
//                System.out.println("attrName:" + attrName + "attrValue:" + attributes.get(attrName));
//            }

            //String path = SWBPlatform.getContextPath() + "/resources/jsp/forum/images/emotion/";

            ret = "<textarea name=\"" + name + "\" dojoType_=\"dijit.Editor\"";

            if (getRows() > 0) {
                ret += " rows=\"" + getRows() + "\"";
            }

            if (getCols() > 0) {
                ret += " cols=\"" + getCols() + "\"";
            }

            if ((getRows() == 0) && (getCols() == 0)) {
                ret += " style=\"width:300px;height:50px;\"";
            }

            ret += " " + getAttributes();
            ret += ">" + value + "</textarea>";

            if (DOJO) {
                if (!mode.equals("create") && prop.isLocaleable()) {
                    ret += " <a href=\"#\" onClick=\"javascript:showDialog('" + SWBPlatform.getContextPath()
                               + "/swbadmin/jsp/propLocaleTextAreaEdit.jsp?suri=" + obj.getEncodedURI() + "&prop="
                               + prop.getEncodedURI() + "','Idiomas de la Propiedad " + prop.getDisplayName(lang)
                               + "');\">locale</a>";
                }
            }
        } else if (mode.equals("view")) {
            ret = "<span _id=\"" + name + "\" name=\"" + name + "\">" + value + "</span>";
        }        
        return ret;
    }
}
