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
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.Stream;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author jorge.jimenez
 * Recurso que muestra los filtros para un stream, se debe de utilizar en una pestaña separada de la de "información" del mismo stream.
 * Este recurso no se utiliza actualmente, se dejo aquí por si acaso se desea utilizar despues. * 
 */
public class StreamFilters extends GenericResource{
    
    private static Logger log = SWBUtils.getLogger(StreamFilters.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (request.getParameter("doView") == null) {
            doEdit(request, response, paramRequest);
            return;
        }
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/streamFilters.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {
                request.setAttribute("paramRequest", paramRequest);
                request.setAttribute("suri", request.getParameter("suri"));
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
            }
        }

    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<iframe width=\"100%\" height=\"100%\" src=\"" + paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW).setParameter("doView", "1").setParameter("suri", request.getParameter("suri")) + "\"></iframe> ");
    }
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        
        String streamUri=request.getParameter("suri");
        String swsite=request.getParameter("wsite");
        if(streamUri!=null && swsite!=null)
        {
            WebSite wsite = WebSite.ClassMgr.getWebSite(swsite);
            SemanticObject semObj=SemanticObject.createSemanticObject(streamUri, wsite.getSemanticModel());
            if(semObj!=null)
            {
                Stream stream=(Stream)semObj.createGenericInstance();
                //setting Sentimental Filter
                stream.setFilterSentimentalPositive(false);
                if(request.getParameter("filterSentimentalPositive")!=null)
                {
                    stream.setFilterSentimentalPositive(true);
                }
                stream.setFilterSentimentalNegative(false);
                if(request.getParameter("filterSentimentalNegative")!=null)
                {
                    stream.setFilterSentimentalNegative(true);
                }
                stream.setFilterSentimentalNeutral(false);
                if(request.getParameter("filterSentimentalNeutral")!=null)
                {
                    stream.setFilterSentimentalNeutral(true);
                }
                //setting Intensity Filter
                stream.setFilterIntensityHigh(false);
                if(request.getParameter("filterIntensityHigh")!=null)
                {
                    stream.setFilterIntensityHigh(true);
                }
                stream.setFilterIntensityMedium(false);
                if(request.getParameter("filterIntensityMedium")!=null)
                {
                    stream.setFilterIntensityMedium(true);
                }
                stream.setFilterIntensityLow(false);
                if(request.getParameter("filterIntensityLow")!=null)
                {
                    stream.setFilterIntensityLow(true);
                }
                //setting Klout-Value Filter
                stream.setStream_KloutValue(0);
                if(request.getParameter("stream_KloutValue")!=null)
                {
                    try{
                        stream.setStream_KloutValue(Integer.parseInt(request.getParameter("stream_KloutValue")));
                    }catch(Exception e){stream.setStream_KloutValue(0);}
                }
                //setting geoLanguage Filter
                stream.setGeoLanguage("es");
                if(request.getParameter("geoLanguage")!=null)
                {
                    try{
                        stream.setGeoLanguage(request.getParameter("geoLanguage"));
                    }catch(Exception e){stream.setGeoLanguage("es");}
                } 
                //setting Latitude Filter
                stream.setGeoCenterLatitude(0);
                if(request.getParameter("geoCenterLatitude")!=null)
                {
                    try{
                        stream.setGeoCenterLatitude(Float.valueOf(request.getParameter("geoCenterLatitude")).floatValue());
                    }catch(Exception e){stream.setGeoCenterLatitude(0);}
                }
                //setting Longitude Filter
                stream.setGeoCenterLongitude(0);
                if(request.getParameter("geoCenterLongitude")!=null)
                {
                    try{
                        stream.setGeoCenterLongitude(Float.valueOf(request.getParameter("geoCenterLongitude")).floatValue());
                    }catch(Exception e){
                        stream.setGeoCenterLongitude(0);
                    }
                }
                //setting geoRadio Filter
                stream.setGeoRadio(0);
                if(request.getParameter("geoRadio")!=null)
                {
                    try{
                        stream.setGeoRadio(Float.valueOf(request.getParameter("geoRadio")).floatValue());
                    }catch(Exception e){stream.setGeoRadio(0);}
                } 
                //setting geoDistanceUnit Filter
                stream.setGeoDistanceUnit("KM");
                if(request.getParameter("geoDistanceUnit")!=null)
                {
                    try{
                        stream.setGeoDistanceUnit(request.getParameter("geoDistanceUnit"));
                    }catch(Exception e){
                        stream.setGeoDistanceUnit("KM");
                    }
                }  
            }
        }
        response.setRenderParameter("suri", streamUri);
    }
    
    
}
