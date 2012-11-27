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
package org.semanticwb.portal.portlet.tags;

import javax.portlet.PortletURL;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 ** Supporting class for the <CODE>param</CODE> tag.
 ** defines a parameter that can be added to a <CODE>actionURL</CODE> or
 ** a <CODE>renderURL</CODE>
 ** <BR>The following attributes are mandatory
 ** <UL>
 ** <LI><CODE>name</CODE>
 ** <LI><CODE>value</CODE>
 ** </UL>
 **/
public class ParamTag extends TagSupport {
    private String name;
    private String value;

    /**
     * Processes the <CODE>param</CODE> tag.
     * @return <CODE>SKIP_BODY</CODE>
     */
    public int doStartTag() throws JspException {
        BasicURLTag urlTag = (BasicURLTag) findAncestorWithClass(this,
                BasicURLTag.class);

        if (urlTag == null) {
            throw new JspException(
                "the 'param' Tag must have actionURL or renderURL as a parent");
        }

        PortletURL url = urlTag.getUrl();

        if (getName() != null) {
            url.setParameter(getName(), getValue());
        }

        return SKIP_BODY;
    }

    /**
     * Returns the name.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value.
     * @return String
     */
    public String getValue() {
        if (value == null) {
            value = "";
        }

        return value;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the value.
     * @param value The value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
