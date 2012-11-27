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

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.VariableInfo;
import org.semanticwb.portal.portlet.WBPortletContainer;


/**
 * Supporting class for the <CODE>defineObjects</CODE> tag.
 * Creates the following variables to be used in the JSP:
 * <UL>
 * <LI><CODE>renderRequest</CODE>
 * <LI><CODE>renderResponse</CODE>
 * <LI><CODE>portletConfig</CODE>
 * </UL>
 * @see   javax.portlet.PortletRequest
 * @see   javax.portlet.RenderResponse
 * @see   javax.portlet.PortletConfig
 *
 */
public class DefineObjectsTag extends TagSupport {
    /**
     * Processes the <CODE>defineObjects</CODE> tag.
     * @return <CODE>SKIP_BODY</CODE>
     */
    public int doStartTag() throws JspException {
        PortletRequest renderRequest = (PortletRequest) pageContext.getRequest()
                                                                   .getAttribute(WBPortletContainer.ATT_PORTLET_REQUEST);
        RenderResponse renderResponse = (RenderResponse) pageContext.getRequest()
                                                                    .getAttribute(WBPortletContainer.ATT_PORTLET_RESPONSE);
        PortletConfig portletConfig = (PortletConfig) pageContext.getRequest()
                                                                 .getAttribute(WBPortletContainer.ATT_PORTLET_CONFIG);

        if (pageContext.getAttribute("renderRequest") == null) //Set attributes only once
         {
            //System.out.println("renderRequest:"+renderRequest);
            pageContext.setAttribute("renderRequest", renderRequest,
                PageContext.PAGE_SCOPE);
        }

        if (pageContext.getAttribute("renderResponse") == null) {
            //System.out.println("renderResponse:"+renderResponse);
            pageContext.setAttribute("renderResponse", renderResponse,
                PageContext.PAGE_SCOPE);
        }

        if (pageContext.getAttribute("portletConfig") == null) {
            //System.out.println("portletConfig:"+portletConfig);
            pageContext.setAttribute("portletConfig", portletConfig,
                PageContext.PAGE_SCOPE);
        }

        return SKIP_BODY;
    }

    public static class TEI extends TagExtraInfo {
        public VariableInfo[] getVariableInfo(TagData tagData) {
            VariableInfo[] info = new VariableInfo[] {
                    new VariableInfo("renderRequest",
                        "javax.portlet.RenderRequest", true,
                        VariableInfo.AT_BEGIN),
                    new VariableInfo("renderResponse",
                        "javax.portlet.RenderResponse", true,
                        VariableInfo.AT_BEGIN),
                    new VariableInfo("portletConfig",
                        "javax.portlet.PortletConfig", true,
                        VariableInfo.AT_BEGIN)
                };

            return info;
        }
    }
}
