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

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletSecurityException;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;


/**
 ** Supporting class for the <CODE>renderURL</CODE> tag.
 ** Creates a url that points to the current Portlet and triggers an render request
 ** with the supplied parameters.
 **
 **/
public class RenderURLTag extends BasicURLTag {
    /* (non-Javadoc)
         * @see javax.servlet.jsp.tagext.Tag#doStartTag()
         */
    public int doStartTag() throws JspException {
        if (var != null) {
            pageContext.removeAttribute(var, PageContext.PAGE_SCOPE);
        }

        RenderResponse renderResponse = (RenderResponse) pageContext.getRequest()
                                                                    .getAttribute("javax.portlet.response");

        if (renderResponse != null) {
            setUrl(renderResponse.createRenderURL());

            if (portletMode != null) {
                try {
                    PortletMode mode = new PortletMode(portletMode);
                    url.setPortletMode(mode);
                } catch (PortletModeException e) {
                    throw new JspException(e);
                }
            }

            if (windowState != null) {
                try {
                    WindowState state = new WindowState(windowState);
                    url.setWindowState(state);
                } catch (WindowStateException e) {
                    throw new JspException(e);
                }
            }

            if (secure != null) {
                try {
                    url.setSecure(getSecureBoolean());
                } catch (PortletSecurityException e) {
                    throw new JspException(e);
                }
            }
        }

        return EVAL_PAGE;
    }
}
