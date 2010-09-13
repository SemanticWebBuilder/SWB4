
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

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

//~--- JDK imports ------------------------------------------------------------

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class TimeElement.
 */
public class TimeElement extends org.semanticwb.model.base.TimeElementBase {
    
    /** The log. */
    private static Logger           log    = SWBUtils.getLogger(TimeElement.class);
    
    /** The format. */
    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    /**
     * Instantiates a new time element.
     * 
     * @param base the base
     */
    public TimeElement(org.semanticwb.platform.SemanticObject base) {
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

        StringBuffer   ret      = new StringBuffer();
        String         name     = prop.getName();
        String         label    = prop.getDisplayName(lang);
        SemanticObject sobj     = prop.getDisplayProperty();
        boolean        required = prop.isRequired();
        String         pmsg     = null;
        String         imsg     = null;
        boolean        disabled = false;

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);

            pmsg     = dobj.getPromptMessage();
            imsg     = dobj.getInvalidMessage();
            disabled = dobj.isDisabled();
        }

        if (DOJO) {
            if (imsg == null) {
                if (required) {
                    imsg = label + " es requerido.";

                    if (lang.equals("en")) {
                        imsg = label + " is required.";
                    }
                } else {
                    imsg = "Formato invalido.";

                    if (lang.equals("en")) {
                        imsg = "Invalid Format.";
                    }
                }
            }

            if (pmsg == null) {
                pmsg = "Captura " + label + ".";

                if (lang.equals("en")) {
                    pmsg = "Enter " + label + ".";
                }
            }
        }

        String ext = "";

        if (disabled) {
            ext += " disabled=\"disabled\"";
        }

        String value = request.getParameter(prop.getName());

        if (value != null) {
            System.out.println("from request: " + value);
        }

        if (value == null) {
            Timestamp dt = obj.getDateTimeProperty(prop);

            if (dt != null) {
                value = format.format(dt);
                System.out.println("from model: " + value);
            }
        }

        if (value == null) {
            value = "";
        }

        if (mode.equals("edit") || mode.equals("create")) {
            ret.append("<input name=\"" + name + "\" value=\"" + (DOJO
                    ? "T"
                    : "") + value + "\"");

            if (DOJO) {
                ret.append(" dojoType=\"dijit.form.TimeTextBox\"");
            }

            if (DOJO) {
                ret.append(" required=\"" + required + "\"");
            }

//          ret.append(" propercase=\"true\"");
            if (DOJO) {
                ret.append(" promptMessage=\"" + pmsg + "\"");
            }

            // if(DOJO)ret.append(((getRegExp()!=null)?(" regExp=\""+getRegExp()+"\""):""));
            if (DOJO) {
                ret.append(" invalidMessage=\"" + imsg + "\"");
            }

            if (DOJO && (getTimeConstraints() != null)) {
                ret.append(" constraints=\"" + getTimeConstraints() + "\"");
            }

            ret.append(" " + getAttributes());

            if (DOJO) {
                ret.append(" trim=\"true\"");
            }

            ret.append(ext);
            ret.append("/>");
        } else if (mode.equals("view")) {
            ret.append("<span name=\"" + name + "\">" + value + "</span>");
        }

        return ret.toString();
    }

    // TODO:borra
    /**
     * Gets the time constraints.
     * 
     * @return the time constraints
     */
    public String getTimeConstraints() {
        return null;
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

        // System.out.println("process...:"+obj.getURI()+" "+prop.getURI());
        if (prop.getDisplayProperty() == null) {
            return;
        }

        String value = request.getParameter(prop.getName());

        // System.out.println("com:"+old+"-"+value+"-");
        if ((value != null) && (value.length() > 0)) {

            // System.out.println("old:"+old+" value:"+value);
            if (value.startsWith("T")) {
                value = value.substring(1);
            }

            try {
                obj.setDateTimeProperty(prop, new Timestamp(format.parse(value).getTime()));
            } catch (Exception e) {
                log.error(e);
            }
        } else {
            obj.removeProperty(prop);
        }
    }
}
