/**  
* SWB Social es una plataforma que descentraliza la publicación, seguimiento y monitoreo hacia las principales redes sociales. 
* SWB Social escucha y entiende opiniones acerca de una organización, sus productos, sus servicios e inclusive de su competencia, 
* detectando en la información sentimientos, influencia, geolocalización e idioma, entre mucha más información relevante que puede ser 
* útil para la toma de decisiones. 
* 
* SWB Social, es una herramienta basada en la plataforma SemanticWebBuilder. SWB Social, como SemanticWebBuilder, es una creación original 
* del Fondo de Información y Documentación para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SWB Social a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarla en las mismas condiciones con que INFOTEC la ha diseñado y puesto a su disposición; 
* aprender de élla; distribuirla a terceros; acceder a su código fuente y modificarla, y combinarla o enlazarla con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. y SWB Social 1.0
* 
* INFOTEC no otorga garantía sobre SWB Social, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder o SWB Social, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
package org.semanticwb.social;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * TextArea que muestra icono de ayuda 
   */
public class TextAreaHelp extends org.semanticwb.social.base.TextAreaHelpBase 
{
    public TextAreaHelp(org.semanticwb.platform.SemanticObject base)
    {
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
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type,
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
        String         name     = propName;
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
        String value = request.getParameter(propName);

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
                if (!mode.equals("create") && prop.isLocaleable() && !obj.isVirtual()) {
                    ret += " <a href=\"#\" onClick=\"javascript:showDialog('" + SWBPlatform.getContextPath()
                               + "/swbadmin/jsp/propLocaleTextAreaEdit.jsp?suri=" + obj.getEncodedURI() + "&prop="
                               + prop.getEncodedURI() + "','Idiomas de la Propiedad " + prop.getDisplayName(lang)
                               + "');\">locale</a>";
                }
            }
            if (DOJO && getHelpText()!=null) {
                ret += "<div class=\"helpText\"><a title=\""+getHelpText()+"\"><img src=\"/swbadmin/css/images/interrogative_icon.jpg\" alt=\""+getHelpText()+"\" /></a></div>";
            }
        } else if (mode.equals("view")) {
            ret = "<span _id=\"" + name + "\" name=\"" + name + "\">" + value + "</span>";
        }        
        return ret;
    }
}