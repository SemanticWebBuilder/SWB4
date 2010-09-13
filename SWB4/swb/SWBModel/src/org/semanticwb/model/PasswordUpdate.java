
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

import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import javax.servlet.http.HttpServletRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class PasswordUpdate.
 */
public class PasswordUpdate extends PasswordUpdateBase {
    
    /** The Constant passphrase. */
    private static final String passphrase = "{MD5}tq5RXfs6DGIXD6dlHUgeQA==";

    /**
     * Instantiates a new password update.
     * 
     * @param base the base
     */
    public PasswordUpdate(SemanticObject base) {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.FormElementBase#renderElement(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.lang.String, java.lang.String, java.lang.String)
     */
    /**
     * Render element.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @param type the type
     * @param mode the mode
     * @param lang the lang
     * @return the string
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type,
                                String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }

        String ret = "";

        if (type.endsWith("iphone")) {
            ret = renderIphone(request, obj, prop, type, mode, lang);
        } else {
            ret = renderXHTML(request, obj, prop, type, mode, lang);
        }

        return ret;
    }

    /**
     * Render iphone.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @param type the type
     * @param mode the mode
     * @param lang the lang
     * @return the string
     */
    public String renderIphone(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type,
                               String mode, String lang) {
        return "";
    }

    /**
     * Render xhtml.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @param type the type
     * @param mode the mode
     * @param lang the lang
     * @return the string
     */
    public String renderXHTML(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type,
                              String mode, String lang) {
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
            pmsg = "Captura " + label + " y confirmala.";

            if (lang.equals("en")) {
                pmsg = "Enter " + label + " and confirm it.";
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
            String localValue = mode.equals("create")
                                ? ""
                                : passphrase;

            ret = "<input name=\"" + name + "\" type=\"password\" " + " dojoType=\"dijit.form.ValidationTextBox\""
                  + " required=\"" + required + "\""    // + " propercase=\"true\""
                  + " promptMessage=\"" + pmsg + "\"" + " invalidMessage=\"" + imsg + "\"" + " trim=\"true\""
                  + " value=\"" + localValue + "\"" + ">";
            ret += "<br/><input name=\"pwd_verify\" type=\"password\" " + " dojoType=\"dijit.form.ValidationTextBox\""
                   + " required=\"" + required + "\""    // + " propercase=\"true\""
                   + " promptMessage=\"" + pmsg + "\"" + " invalidMessage=\"" + imsg + "\"" + " trim=\"true\""
                   + " value=\"" + localValue + "\"" + ">";
        } else if (mode.equals("view")) {
            ret = "<span _id=\"" + name + "\" name=\"" + name + "\">" + value + "</span>";
        }

        return ret;
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
        if (prop.isDataTypeProperty()) {
            String value    = request.getParameter(prop.getName());
            String chkvalue = request.getParameter("pwd_verify");
            String old      = obj.getProperty(prop);

            if ((value != null) && (value.length() > 0) && (chkvalue != null) && value.equals(chkvalue)) {
                if ((value.length() > 0) &&!value.equals(old) &&!value.equals(passphrase)) {
                    if (prop.isString()) {
                        obj.setProperty(prop, value);
                    }
                } else if ((value.length() == 0) && (old != null)) {
                    obj.removeProperty(prop);
                }
            } else {
                throw new FormRuntimeException("Passwords mistmatch or invalid");
            }
        }
    }
}
