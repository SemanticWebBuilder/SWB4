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

import java.util.Hashtable;
import org.semanticwb.model.User;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.UserRepository;
import org.semanticwb.servlet.SWBVirtualHostFilter;
import org.semanticwb.servlet.internal.InternalServlet;
import org.semanticwb.servlet.internal.NTLMServlet;


/**
 * Bridge NTLM/LDAP - SWB
 * @author serch
 */
public class NTLMBridge extends ExtUserRepInt{
   
    /** The log. */
    static Logger log = SWBUtils.getLogger(NTLMBridge.class);

    /** The user rep. */
    private UserRepository userRep;

    /** The props. */
    private Properties props;

    /** The seek field. */
    private String seekField;

    /** The user object class. */
    private String userObjectClass;

    /** The field first name. */
    private String fieldFirstName;

    /** The field last name. */
    private String fieldLastName;

    /** The field middle name. */
    private String fieldMiddleName;

    /** The field email. */
    private String fieldEmail;

    /** The value language. */
    private String valueLanguage;

    public NTLMBridge(UserRepository userRep, Properties props)
    {
        this.userRep = userRep;
        this.props = props;
        this.seekField = props.getProperty("seekField", "uid");
        this.userObjectClass = props.getProperty("userObjectClass", "inetPerson");
        this.fieldFirstName = props.getProperty("fieldFirstName", "givenName");
        this.fieldLastName = props.getProperty("fieldLastName", "sn");
        this.fieldMiddleName = props.getProperty("fieldMiddleName", "null");
        this.fieldEmail = props.getProperty("fieldEmail", "mail");
        this.valueLanguage = props.getProperty("valueLanguage", "");
        userRep.setAlternateLoginURL(SWBPortal.getContextPath()+"/NTLMLogin/<website>/<webpage>");
        SWBVirtualHostFilter filter = (SWBVirtualHostFilter) SWBPortal.getVirtualHostFilter();
        InternalServlet svlt = new NTLMServlet();
        try {
            filter.addMapping("NTLMLogin", svlt);
        } catch (Exception noe) {
            log.error("can't load authentication Servlet");
        }
        
    }
    
    
    
    @Override
    public void syncUsers()
    {
        //System.out.println("NTLMBridge: syncUsers");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean validateCredential(String login, Object credential)
    {
        
        //System.out.println("NTLMBridge: validateCredential "+login);
        return true;
    }

    @Override
    public boolean syncUser(String login, User user)
    {
        
        log.trace("Syncing User:"+login+"-"+user);
        boolean ret = false;
        try
        {
            Attributes att = getUserAttributes(login);
            log.trace("Attrs: "+att);
            if (null!=att && att.size()>0){
            if (null == user)
            {
                user = userRep.createUser();
                user.setLogin(login);
                user.setActive(true);
            }
            loadAttrs2RecUser(att, user);
            ret = true;
            }
        } catch (NamingException ex)
        {
           log.debug("Error Syncing a User: "+login,ex);
        }

        return ret;
    }

    /**
     * Gets the properties hash.
     *
     * @return the properties hash
     */
    private Hashtable getPropertiesHash()
    {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                props.getProperty("factory", "com.sun.jndi.ldap.LdapCtxFactory"));
        env.put(Context.PROVIDER_URL, props.getProperty("url", "ldap://localhost"));
        env.put(Context.SECURITY_PRINCIPAL, props.getProperty("principal", ""));
        env.put(Context.SECURITY_CREDENTIALS, props.getProperty("credential", ""));
        return env;
    }

    /**
     * Gets the cN from login.
     *
     * @param login the login
     * @return the cN from login
     */
    private String getCNFromLogin(String login)
    {
        DirContext ctx = null;
        try
        {
            ctx = new InitialDirContext(getPropertiesHash());
        } catch (NamingException e)
        {
            return null; // Can't get the info of the repository so we leave
        }
        //System.out.println("ctx:" + ctx);
        NamingEnumeration answers = null;

        SearchControls ctls = new SearchControls();
        ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        try
        {
            answers = ctx.search(props.getProperty("base", ""),
                    "(&(objectClass=" + userObjectClass + ")(" + seekField + "=" + login + "))", ctls);

            return ((SearchResult) answers.next()).getName() + "," + props.getProperty("base", "");
        } catch (NamingException e)
        {
            return null; //We didn't found or we got an error so we leave
        }
    }

    /**
     * Authenticate lp.
     *
     * @param login the login
     * @param credential the credential
     * @return true, if successful
     */
    private boolean AuthenticateLP(String login, Object credential)
    {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                props.getProperty("factory", "com.sun.jndi.ldap.LdapCtxFactory"));
        env.put(Context.PROVIDER_URL, props.getProperty("url", "ldap://localhost"));

        env.put(Context.SECURITY_PRINCIPAL, getCNFromLogin(login));

        env.put(Context.SECURITY_CREDENTIALS, credential);
        try
        {
            DirContext ctx = new InitialDirContext(env);
            ctx.close();
        } catch (NamingException e)
        {
            return false;
        }
        return true;
    }

    /**
     * Gets the user list.
     *
     * @return the user list
     * @throws NamingException the naming exception
     */
    private NamingEnumeration getUserList() throws NamingException
    {
        Hashtable env = getPropertiesHash();
        DirContext ctx = new InitialDirContext(env);

        Attributes matchAttrs = new BasicAttributes(true); // ignore case
        matchAttrs.put(new BasicAttribute("objectClass", userObjectClass));
        // Search for objects that have those matching attributes
        NamingEnumeration answers = null;

        SearchControls ctls = new SearchControls();
        ctls.setReturningAttributes(new String[]{seekField});
        ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        answers = ctx.search(props.getProperty("base", ""),
                "objectClass=" + userObjectClass, ctls);

        /*SearchControls ctls = new SearchControls();
        ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration answers = ctx.search(DBUser.getInstance(repository).getProperty("container", ""),
        "objectClass=WBPerson"
        , ctls);*/
        ctx.close();
        return answers;
    }

    /**
     * Gets the user attributes.
     *
     * @param login the login
     * @return the user attributes
     * @throws NamingException the naming exception
     */
    public Attributes getUserAttributes(String login) throws NamingException
    {
        DirContext ctx = new InitialDirContext(getPropertiesHash());
        String[] attrIDs =
        {
            "*"
        };
        Attributes answer = null;

        String cn = getCNFromLogin(login);
        //System.out.println("CN:" + cn);
        answer = ctx.getAttributes(cn, attrIDs);

        ctx.close();
        return answer;
    }

    /**
     * Load attrs2 rec user.
     *
     * @param attrs the attrs
     * @param ru the ru
     */
    public void loadAttrs2RecUser(Attributes attrs, User ru)
    {

        try
        {
            if (!"null".equals(fieldFirstName))
            {
                ru.setFirstName((String) attrs.get(fieldFirstName).get());
            }
        } catch (Exception ne)
        {
        }
        try
        {
            if (!"null".equals(fieldLastName))
            {
                ru.setLastName((String) attrs.get(fieldLastName).get());
            }
        } catch (Exception ne)
        {
        }
        try
        { //If there is no middlename go on
            if (!"null".equals(fieldMiddleName))
            {
                ru.setSecondLastName((String) attrs.get(fieldMiddleName).get());
            }
        } catch (Exception ne)
        {
        }
        try
        {
            if (!"null".equals(fieldEmail))
            {
                ru.setEmail((String) attrs.get(fieldEmail).get());
            }
        } catch (Exception ne)
        {
        }
        // If there is no language keep the default
        try
        {
            if (valueLanguage.startsWith("|"))
            {
                ru.setLanguage(valueLanguage.substring(1));
            } else
            {
                ru.setLanguage((String) attrs.get(valueLanguage).get());
            }
        } catch (Exception ne)
        {
        }

    }

}
