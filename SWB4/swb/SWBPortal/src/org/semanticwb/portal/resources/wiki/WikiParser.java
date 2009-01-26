/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */

/*
 * WikiParser.java
 *
 * Created on 28 de mayo de 2008, 07:10 PM
 *
 */

package org.semanticwb.portal.resources.wiki;

import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBParamRequest;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class WikiParser
{
    private static Logger log = SWBUtils.getLogger(WikiParser.class);

    private String image;
    private String page;
    private String mapid;
    
    /** Creates a new instance of WikiParser */
    public WikiParser()
    {
    }

    public String parse(String content, String urlbase)
    {
        this.image="${image}";
        this.page=urlbase+"${title}";
        
        WikiModel wikiModel=new WikiModel(image, page);
        String htmlStr;
        try
        {
            htmlStr = wikiModel.render(content);
        }catch(Exception e)
        {
            htmlStr="Error...";
            log.error(e);
        }
        return htmlStr;
    }
    
    public String parse(String content, javax.servlet.http.HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        String mapid=paramsRequest.getTopic().getWebSiteId();
        String base=SWBPortal.getDistributorPath()+"/"+mapid+"/";
        this.image="${image}";
        this.page=base+"${title}";
        
        WikiModel wikiModel=new WikiModel(request, paramsRequest, image, page);
        String htmlStr;
        try
        {
            htmlStr = wikiModel.render(content);
        }catch(Exception e)
        {
            htmlStr="Error...";
            log.error(e);
        }
        return htmlStr;
    }
    
}
