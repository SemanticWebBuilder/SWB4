/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.inmujeres.swb.resources.auth;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
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
import mx.gob.inmujeres.swb.UserExtended;
import mx.gob.inmujeres.swb.resources.sem.base.Autentificacion;
import mx.gob.inmujeres.swb.resources.sem.base.UserLogin;
import mx.gob.inmujeres.swb.resources.sem.base.UserSubordinado;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.security.auth.ExtUserRepInt;

/**
 *
 * @author victor.lorenzana
 */
public class InmujeresGenericLDAPBridge extends ExtUserRepInt
{

    /** The log. */
    static final Logger log = SWBUtils.getLogger(InmujeresGenericLDAPBridge.class);
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

    /**
     * Instantiates a new sW b4 generic ldap bridge.
     *
     * @param UserRep the user rep
     * @param props the props
     */
    public InmujeresGenericLDAPBridge(UserRepository UserRep, Properties props)
    {
        this.userRep = UserRep;
        this.props = props;
        this.seekField = props.getProperty("seekField", "uid");
        this.userObjectClass = props.getProperty("userObjectClass", "inetPerson");
        this.fieldFirstName = props.getProperty("fieldFirstName", "givenName");
        this.fieldLastName = props.getProperty("fieldLastName", "sn");
        this.fieldMiddleName = props.getProperty("fieldMiddleName", "null");
        this.fieldEmail = props.getProperty("fieldEmail", "mail");
        this.valueLanguage = props.getProperty("valueLanguage", "");

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
        try
        {
            NamingEnumeration ne = getUserList();
            String currLog;
            while (ne.hasMore())
            {
                SearchResult sr = (SearchResult) ne.next();
                currLog = (String) sr.getAttributes().get(seekField).get();
                userRep.getUserByLogin(currLog);
            }
        }
        catch (NamingException ex)
        {
            log.error("Syncing Users for: " + userRep.getId(), ex);
        }
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
    public boolean validateCredential(String login, Object credential)
    {
        if (credential == null || credential.toString().trim().equals(""))
        {
            log.error("credential: " + credential + " login: " + login);

            return false;
        }
        if (credential instanceof char[])
        {
            if (((char[]) credential).length == 0)
            {
                return false;
            }
        }
//        String lcred = null;
//        if (credential instanceof char[]) lcred= new String((char[])credential);
//        else lcred = (String)credential;
        login = checklogin(login);
        return AuthenticateLP(login, credential);
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
        login = checklogin(login);
        log.trace("Syncing User:" + login + "-" + user);
        boolean ret = false;
        try
        {
            Attributes att = getUserAttributes(login);
            log.trace("Attrs: " + att);
            if (null != att && att.size() > 0)
            {
                if (null == user)
                {
                    int pos = login.indexOf("@");
                    if (pos != -1)
                    {
                        login = login.substring(0, pos);
                    }

                    Iterator<User> users = userRep.listUsers();
                    while (users.hasNext())
                    {
                        User _user = users.next();
                        if (login.equals(_user.getLogin()))
                        {
                            user = _user;
                        }
                    }
                    if (null == user)
                    {
                        user = userRep.createUser();
                        user.setLogin(login);
                        user.setActive(true);
                    }
                }
                loadAttrs2RecUser(att, user);
                ret = true;
            }
        }
        catch (NamingException ex)
        {
            log.debug("Error Syncing a User: " + login, ex);
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
        login = checklogin(login);
        DirContext ctx = null;
        try
        {
            ctx = new InitialDirContext(getPropertiesHash());
        }
        catch (NamingException e)
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
        }
        catch (NamingException e)
        {
            return null; //We didn't found or we got an error so we leave
        }
    }

    private String checklogin(String login)
    {

        if (login != null)
        {
            int pos = login.indexOf("@inmujeres.local");
            if (pos == -1)
            {
                login += "@inmujeres.local";
            }
        }
        return login;
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
        login = checklogin(login);
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
        }
        catch (NamingException e)
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
        ctls.setReturningAttributes(new String[]
                {
                    seekField
                });
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
        login = checklogin(login);
        DirContext ctx = new InitialDirContext(getPropertiesHash());
        String[] attrIDs =
        {
            "*"
        };
        Attributes answer = null;

        String cn = getCNFromLogin(login);
        if (ctx == null)
        {
            throw new NamingException("Contexto de conexión nulo cn: " + cn);
        }
        answer = ctx.getAttributes(cn, attrIDs);

        ctx.close();
        return answer;
    }

    private User getUser(String login)
    {
        Iterator<User> users = userRep.listUsers();
        while (users.hasNext())
        {
            User user = users.next();
            if (login.equals(user.getLogin()))
            {
                return user;
            }
        }
        return null;
    }

    /**
     * Load attrs2 rec user.
     *
     * @param attrs the attrs
     * @param ru the ru
     */
    public void loadAttrs2RecUser(Attributes attrs, User ru)
    {
        Autentificacion aut = new Autentificacion();
        String login = ru.getLogin();
        if (login.indexOf("@") == -1)
        {
            login += "@inmujeres.local";
        }
        List<UserSubordinado> subordinados = aut.getSubordinados(login);
        Role role = userRep.getRole("evaluador");
        if (role != null)
        {
            if (ru.hasRole(role))
            {
                ru.removeRole(role);

            }


            if (!subordinados.isEmpty())
            {
                ru.addRole(role);
            }
        }
        UserExtended ext = UserExtended.ClassMgr.getUserExtended(ru.getId(), ru.getUserRepository());
        if (ext == null)
        {
            ext = UserExtended.ClassMgr.createUserExtended(ru.getId(), ru.getUserRepository());
        }
        ext.removeAllSubordinado();
        for (UserSubordinado subordinado : subordinados)
        {
            String loginSubordinado = subordinado.getLogin();
            User userSubordinado = getUser(loginSubordinado);
            ext.addSubordinado(userSubordinado);
            UserExtended extSubordinado = UserExtended.ClassMgr.getUserExtended(userSubordinado.getId(), userSubordinado.getUserRepository());
            if (extSubordinado == null)
            {
                extSubordinado = UserExtended.ClassMgr.createUserExtended(userSubordinado.getId(), userSubordinado.getUserRepository());
            }
            UserLogin infoSubordinado = aut.getCamposLogin(loginSubordinado + "@inmujeres.local");
            extSubordinado.setArea(infoSubordinado.getAreaAdscripcion());
            extSubordinado.setExtensionUser(infoSubordinado.getExtension());
            extSubordinado.setLevel(infoSubordinado.getNivel());
            extSubordinado.setNoEmpleado(infoSubordinado.getNoEmpleado());
            extSubordinado.setPuesto(infoSubordinado.getPuesto());
            extSubordinado.setRfc(infoSubordinado.getRfc());
        }
        UserLogin info = aut.getCamposLogin(login);
        ext.setArea(info.getAreaAdscripcion());
        ext.setExtensionUser(info.getExtension());
        ext.setLevel(info.getNivel());
        ext.setNoEmpleado(info.getNoEmpleado());
        ext.setPuesto(info.getPuesto());
        ext.setRfc(info.getRfc());


        try
        {
            if (!"null".equals(fieldFirstName))
            {
                ru.setFirstName((String) attrs.get(fieldFirstName).get());
            }
        }
        catch (Exception ne)
        {
        }
        try
        {
            if (!"null".equals(fieldLastName))
            {
                ru.setLastName((String) attrs.get(fieldLastName).get());
            }
        }
        catch (Exception ne)
        {
        }
        try
        { //If there is no middlename go on
            if (!"null".equals(fieldMiddleName))
            {
                ru.setSecondLastName((String) attrs.get(fieldMiddleName).get());
            }
        }
        catch (Exception ne)
        {
        }
        try
        {
            if (!"null".equals(fieldEmail))
            {
                ru.setEmail((String) attrs.get(fieldEmail).get());
            }
        }
        catch (Exception ne)
        {
        }
        // If there is no language keep the default
        try
        {
            if (valueLanguage.startsWith("|"))
            {
                ru.setLanguage(valueLanguage.substring(1));
            }
            else
            {
                ru.setLanguage((String) attrs.get(valueLanguage).get());
            }
        }
        catch (Exception ne)
        {
        }

    }
}
