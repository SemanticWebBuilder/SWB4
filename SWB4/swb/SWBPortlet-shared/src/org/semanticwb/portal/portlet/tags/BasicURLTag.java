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

import java.io.IOException;

import javax.portlet.PortletURL;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.VariableInfo;


/**
 * Supporting class for the <CODE>actionURL</CODE> and <CODE>renderURL</CODE> tag.
 * Creates a url that points to the current Portlet and triggers an action request
 * with the supplied parameters.
 *
 */
public abstract class BasicURLTag extends TagSupport {
    protected String portletMode;
    protected String secure;
    protected Boolean secureBoolean;
    protected String windowState;
    protected PortletURL url;
    protected String var;

    /**
     * Processes the <CODE>actionURL</CODE> or <CODE>renderURL</CODE> tag.
     * @return int
     */
    public abstract int doStartTag() throws JspException;

    /**
     *
     * @return int
     */
    public int doEndTag() throws JspException {
        if (var == null) {
            try {
                JspWriter writer = pageContext.getOut();
                writer.print(url);
                writer.flush();
            } catch (IOException ioe) {
                throw new JspException(
                    "actionURL/renderURL Tag Exception: cannot write to the output writer.");
            }
        } else {
            pageContext.setAttribute(var, url.toString(), PageContext.PAGE_SCOPE);
        }

        return EVAL_PAGE;
    }

    /**
     * Returns the portletMode.
     * @return String
     */
    public String getPortletMode() {
        return portletMode;
    }

    /**
     * @return secure as String
     */
    public String getSecure() {
        return secure;
    }

    /**
     * @return secure as Boolean
     */
    public boolean getSecureBoolean() {
        return this.secureBoolean.booleanValue();
    }

    /**
     * Returns the windowState.
     * @return String
     */
    public String getWindowState() {
        return windowState;
    }

    /**
     * @return PortletURL
     */
    public PortletURL getUrl() {
        return url;
    }

    /**
     * Returns the var.
     * @return String
     */
    public String getVar() {
        return var;
    }

    /**
     * Sets the portletMode.
     * @param portletMode The portletMode to set
     */
    public void setPortletMode(String portletMode) {
        this.portletMode = portletMode;
    }

    /**
     * Sets secure to boolean value of the string
     * @param secure
     */
    public void setSecure(String secure) {
        this.secure = secure;
        this.secureBoolean = new Boolean(secure);
    }

    /**
     * Sets the windowState.
     * @param windowState The windowState to set
     */
    public void setWindowState(String windowState) {
        this.windowState = windowState;
    }

    /**
     * Sets the url.
     * @param url The url to set
     */
    public void setUrl(PortletURL url) {
        this.url = url;
    }

    /**
     * Sets the var.
     * @param var The var to set
     */
    public void setVar(String var) {
        this.var = var;
    }

    public static class TEI extends TagExtraInfo {
        public VariableInfo[] getVariableInfo(TagData tagData) {
            VariableInfo[] vi = null;
            String var = tagData.getAttributeString("var");

            if (var != null) {
                vi = new VariableInfo[1];
                vi[0] = new VariableInfo(var, "java.lang.String", true,
                        VariableInfo.AT_END);
            }

            return vi;
        }
    }
}
