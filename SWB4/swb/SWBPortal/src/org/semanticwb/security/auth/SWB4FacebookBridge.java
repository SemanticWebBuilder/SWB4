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
import java.util.Iterator;
import org.json.JSONException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import java.io.InputStream;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;
import org.semanticwb.base.util.SWBSoftkHashMap;
import org.semanticwb.model.GenericIterator;

// TODO: Auto-generated Javadoc
/**
 * The Class SWB4FacebookBridge.
 * 
 * @author serch
 */
public class SWB4FacebookBridge extends ExtUserRepInt
{
    
    /** The log. */
    static Logger log = SWBUtils.getLogger(SWB4FacebookBridge.class);
    
    /** The user rep. */
    private UserRepository userRep;
    
    /** The props. */
    private Properties props;
    
    /** The app key. */
    private String appKey;
    
    /** The app secret. */
    private String appSecret;
    
    /** The app id. */
    private String appID;
    
    /** The app base domain. */
    private String appBaseDomain;

    private String loginURL;

    private SWBSoftkHashMap<String, FBUserPojo> fbuserbuffer;

    /**
     * Instantiates a new sW b4 facebook bridge.
     * 
     * @param UserRep the user rep
     * @param props the props
     */
    public SWB4FacebookBridge(UserRepository UserRep, Properties props)
    {
        this.userRep = UserRep;
        this.props = props;
        this.appKey = props.getProperty("appKey", null);
        this.appSecret = props.getProperty("appSecret", null);
        this.appID = props.getProperty("appID", null);
        this.appBaseDomain = props.getProperty("appBaseDomain", null);
        this.loginURL = props.getProperty("loginURL", null);
        this.fbuserbuffer = new SWBSoftkHashMap<String, FBUserPojo>(50);
        UserRep.setAlternateLoginURL("https://www.facebook.com/dialog/oauth?client_id="+appID+"&redirect_uri="+loginURL+"&scope=email");
        
//        String loginClass = props.getProperty("loginClass", "");
//        try
//        {
//            Class clase = Class.forName(loginClass);
//            InternalServlet fblogin = (InternalServlet)clase.getConstructors()[0].newInstance();
//            ((SWBVirtualHostFilter)SWBPortal.getVirtualHostFilter()).addMapping("fblogin", fblogin);
//            fblogin.init(SWBPortal.getServletContext());
//        } catch (Exception ex)
//        {
//            log.error(ex);
//        }

    }

    /* (non-Javadoc)
     * @see org.semanticwb.security.auth.ExtUserRepInt#syncUsers()
     */
    /**
     * Sync users.
     */
    @Override
    public void syncUsers()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.security.auth.ExtUserRepInt#validateCredential(java.lang.String, java.lang.Object)
     */
    /**
     * Validate credential.
     * 
     * @param login the login
     * @param credential the credential
     * @return true, if successful
     */
    @Override
    public boolean validateCredential(String login, Object credential) //login==code
    {
//        System.out.println("============validateCredential==========");
//        System.out.println("login: "+login);
//        System.out.println("credential: "+credential.toString());
//        System.out.println("****************************************" + login);
//        Thread.currentThread().dumpStack();
//        System.out.println("****************************************");

        FBUserPojo pojo=null;
        String lcred = null;
        if (credential instanceof char[]) lcred= new String((char[])credential);
        else lcred = (String)credential;
        try {
            pojo=authenticateFB(lcred);
        } catch (Exception noe) {}
        return null!=pojo;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.security.auth.ExtUserRepInt#syncUser(java.lang.String, org.semanticwb.model.User)
     */
    /**
     * Sync user.
     * 
     * @param login the login
     * @param user the user
     * @return true, if successful
     */
    @Override
    public boolean syncUser(String login, User user)
    {
//        System.out.println("============syncUser==========");
//        System.out.println("user"+user);
//        System.out.println("****************************************" + login);
//        Thread.currentThread().dumpStack();
//        System.out.println("****************************************");
        User ret = user;
        FBUserPojo pojo = null;
        pojo = fbuserbuffer.get(login);
        if (null != pojo)
        {
            if (null == ret)
            {
                Iterator aux = userRep.getSemanticObject().getRDFResource().getModel().listStatements(null, User.swb_usrLogin.getRDFProperty(),
                        userRep.getSemanticObject().getModel().getRDFModel().createLiteral(login));
                Iterator it = new GenericIterator(aux, true);
                if (it.hasNext())
                {
                    ret = (User) it.next();
                } else
                {
                    ret = userRep.createUser();
                }
            }
//        try {
//            pojo=authenticateFB(login);
            ret.setLogin(pojo.usrId);
            ret.setLanguage(null != pojo.locale ? pojo.locale.substring(0, 2) : "es");
            ret.setFirstName(pojo.usrFirstName);
            ret.setLastName(pojo.usrLastName);
            ret.setEmail(pojo.usrMail);
            ret.setActive(true);
            ret.setPassword("{CRYPT}" + pojo.access_token);
            //user=ret; 
//        } catch (Exception noe) {}
        }
//        System.out.println("login:"+login+" pojo:"+pojo);
        return null != pojo;
    }

    /**
     * Gets the app base domain.
     * 
     * @return the app base domain
     */
    public String getAppBaseDomain()
    {
        return appBaseDomain;
    }

    /**
     * Gets the app id.
     * 
     * @return the app id
     */
    public String getAppID()
    {
        return appID;
    }

    /**
     * Gets the app key.
     * 
     * @return the app key
     */
    public String getAppKey()
    {
        return appKey;
    }

    /**
     * Gets the app secret.
     * 
     * @return the app secret
     */
    public String getAppSecret()
    {
        return appSecret;
    }

    public FBUserPojo authenticateFB(String code) throws IOException, JSONException
    {
//        System.out.println("***************authenticateFB******************");
//
//        System.out.println(code);
//        System.out.println("****************************************");
//        Thread.currentThread().dumpStack();
//        System.out.println("****************************************");
        FBUserPojo pojo = null;
        URL url = new URL("https://graph.facebook.com/oauth/access_token"
                        + "?client_id="+appID
                        + "&redirect_uri="+loginURL //http://logindemo.com:8084/DemoFB/FaceBookAcc
                        + "&client_secret="+appSecret
                        + "&code=" + code);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        int resc = con.getResponseCode();
        String resm = con.getResponseMessage();
        InputStream in = null;
        if (resc <300)
        in = con.getInputStream();
        else
            in = con.getErrorStream();
        String datos = SWBUtils.IO.readInputStream(in);
//        System.out.println("error:"+datos);
        String access_token=datos.substring(datos.indexOf("=")+1,datos.indexOf("&"));
        in.close();
        url = new URL("https://graph.facebook.com/me?access_token="+access_token);
        con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        resc = con.getResponseCode();
        resm = con.getResponseMessage();
        in = con.getInputStream();
        String datosUser = SWBUtils.IO.readInputStream(in);
        in.close();
        JSONObject jobj = new JSONObject(datosUser);
        pojo = new FBUserPojo();
        pojo.usrId=jobj.getString("id");
        pojo.usrFirstName=jobj.getString("first_name");
        pojo.usrLastName=jobj.getString("last_name");
        pojo.usrMail=jobj.getString("email");
        pojo.locale=jobj.getString("locale");
        pojo.access_token=access_token;
        fbuserbuffer.put(pojo.usrId, pojo);
        return pojo;
    }

}

class FBUserPojo {
    String usrId;
    String usrFirstName;
    String usrLastName;
    String usrMail;
    String locale;
    String access_token;
}