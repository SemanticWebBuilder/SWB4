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
package org.semanticwb.portal.social.twitter;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
//import twitter4j.Twitter;

// TODO: Auto-generated Javadoc
/**
 * Resource that manage the integration between twitter and semanticwebbuilder (twitter java api)
 * and returns the user twitts and also can publish new twitts to twitter.
 * 
 * @author Jorge Jiménez
 */
public class TwitterResource extends GenericAdmResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(TwitterResource.class);

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/twitter/twitter.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        try {
            Resource base=response.getResourceBase();
            User user=response.getUser();
            String action=response.getAction();
            if(action.equals("saveUserData"))
            {
                if(request.getParameter("twitterLogin")!=null && request.getParameter("twitterPass")!=null)
                {
                   String data=request.getParameter("twitterLogin")+"|"+request.getParameter("twitterPass");
                   String twitterConf=base.getAttribute("twitterConf","1");
                   if(twitterConf.equals("1"))base.setData(data);
                   if(twitterConf.equals("2"))base.setData(user,data);
                   if(twitterConf.equals("3"))base.setData(response.getWebPage(),data);
                   base.updateAttributesToDB();
                }
            }else if(action.equals("send2Twitter")){
                String status=request.getParameter("status");

               String data=null;
               String twitterConf=base.getAttribute("twitterConf","1");
               if(twitterConf.equals("1"))data=base.getData();
               if(twitterConf.equals("2"))data=base.getData(user);
               if(twitterConf.equals("3"))data=base.getData(response.getWebPage());

                if(data!=null && status!=null && status.trim().length()>0){
                    int pos=data.indexOf("|");
                    if(pos>-1){
                        String userLogin=data.substring(0,pos);
                        String userPass=data.substring(pos+1);
                        //Twitter twitter = new Twitter(userLogin, userPass);
                        //twitter.updateStatus(request.getParameter("status"));
                    }
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }
}
