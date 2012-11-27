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
package org.semanticwb.security.auth;

import java.util.Properties;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;

/**
 *
 * @author Serch
 */
public class RemoteURLLoginBridge extends ExtUserRepInt
{

    static Logger log = SWBUtils.getLogger(RemoteURLLoginBridge.class);
    private UserRepository userRep;
    private Properties props;
    private String URL;
    private String host;
    private String soapAction;
    private String RedirectURL;
    private String AlternateURL;

    public RemoteURLLoginBridge(UserRepository UserRep, Properties props)
    {
        this.userRep = UserRep;
        this.props = props;
        this.URL = props.getProperty("url", null);
        this.host = props.getProperty("host", null);
        this.soapAction = props.getProperty("soapAction", null);
        this.RedirectURL = props.getProperty("urlRedirec", null);
        this.AlternateURL = props.getProperty("alternateURL","/login");
        userRep.setAlternateLoginURL(AlternateURL);
    }
    

    @Override
    public void syncUsers()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean validateCredential(String login, Object credential)
    {
        if (RemoteURLLoginUtil.getUserAttributes(login, new String((char[]) credential), host, URL, soapAction) == null){
            throw new RuntimeException("Commit failed");

        }
        return true;
    }

    @Override
    public boolean syncUser(String login, User user)
    {
        return true;
    }

    @Override
    public boolean doRedirect()
    {
        return true;
    }

    @Override
    public String getRedirectURL()
    {
        return RedirectURL;
    }

    public String getURL()
    {
        return URL;
    }

    public String getHost()
    {
        return host;
    }

    public String getSoapAction()
    {
        return soapAction;
    }
}
