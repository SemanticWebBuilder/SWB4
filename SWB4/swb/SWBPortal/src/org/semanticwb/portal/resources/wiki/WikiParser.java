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
package org.semanticwb.portal.resources.wiki;

import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBParamRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class WikiParser.
 * 
 * @author Javier Solis Gonzalez
 */
public class WikiParser
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WikiParser.class);

    /** The image. */
    private String image;
    
    /** The page. */
    private String page;
    
    /** The mapid. */
    private String mapid;
    
    /**
     * Creates a new instance of WikiParser.
     */
    public WikiParser()
    {
    }

    /**
     * Parses the.
     * 
     * @param content the content
     * @param urlbase the urlbase
     * @return the string
     */
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
    
    /**
     * Parses the.
     * 
     * @param content the content
     * @param request the request
     * @param paramsRequest the params request
     * @return the string
     */
    public String parse(String content, javax.servlet.http.HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        String mapid=paramsRequest.getWebPage().getWebSiteId();
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
