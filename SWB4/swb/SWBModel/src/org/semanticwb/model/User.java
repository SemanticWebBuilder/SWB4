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
package org.semanticwb.model;

import java.io.UnsupportedEncodingException;
import javax.security.auth.login.LoginException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBRuntimeException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.*;
import static org.semanticwb.model.base.XMLableBase.swb_xml;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User extends UserBase implements Principal
{
    
    /** The log. */
    static Logger log = SWBUtils.getLogger(User.class);
    
    /** The device. */
    private Device device = null;
    
    /** The ip. */
    private String ip = null;
    
    /** The login. */
    private boolean login = false;

    /** The History webpages of the user. */
    private final LinkedList<String> history = new LinkedList();

    /** The visited webpages of the user. */
    private final TreeSet<String> visited = new TreeSet();

    /**
     * Instantiates a new user.
     * 
     * @param base the base
     */
    public User(SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see java.security.Principal#getName()
     */
    public String getName()
    {
        return getLogin();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserBase#setPassword(java.lang.String)
     */
    @Override
    public void setPassword(String password)
    {
//        String keyString = "05fe858d86df4b909a8c87cb8d9ad577";
//        byte key[] = new BigInteger(keyString, 16).toByteArray();
//        Encryptor t = new Encryptor(key);
//        setProperty("encpwd",t.encode(password));
        //System.out.println("setPassword:"+password);
//        System.out.print("Complex:"+SWBPlatform.getSecValues().getComplexity()+" >"+(!password.matches("^.*(?=.*[a-zA-Z])(?=.*[0-9])().*$")));
        if (password.length()<SWBPlatform.getSecValues().getMinlength())
        {
            throw new SWBRuntimeException("Password don't comply with security measures: Minimal Longitude");
        }
        if (SWBPlatform.getSecValues().isDifferFromLogin() &&
                getLogin().equalsIgnoreCase(password))
        {
            throw new SWBRuntimeException("Password don't comply with security measures: is equals to Login");
        }
        if (SWBPlatform.getSecValues().getComplexity()==1 && (!password.matches("^.*(?=.*[a-zA-Z])(?=.*[0-9])().*$")))
        {
            throw new SWBRuntimeException("Password don't comply with security measures: simple");
        }
        if (SWBPlatform.getSecValues().getComplexity()==2 && (!password.matches("^.*(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[\\W])().*$")))
        {
            throw new SWBRuntimeException("Password don't comply with security measures: complex");
        }
        String tmpPasswd = null;
        try
        {
            tmpPasswd = SWBUtils.CryptoWrapper.passwordDigest(password);
            //System.out.println("tmpPasswd:"+tmpPasswd);
            super.setPassword(tmpPasswd);
            setPasswordChanged(new Date());
        } catch (Exception ex)
        //NoSuchAlgorithmException & UnsupportedEncodingException,
        //Wrapped up, it doesn't matter which one, we just can't do anything else
        {
            log.error("User: Can't set a crypted Password", ex);
        }
    }

    /**
     * Gets the device.
     * 
     * @return the device
     */
    public Device getDevice()
    {
        return device;
    }

    /**
     * Sets the device.
     * 
     * @param device the new device
     */
    public void setDevice(Device device)
    {
        this.device = device;
    }

    /**
     * Gets the ip.
     * 
     * @return the ip
     */
    public String getIp()
    {
        return ip;
    }

    /**
     * Sets the ip.
     * 
     * @param ip the new ip
     */
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    /**
     * Checks if is signed.
     * 
     * @return true, if is signed
     */
    public boolean isSigned()
    {
        return login;
    }

    /**
     * Checks if is registered.
     * 
     * @return true, if is registered
     */
    public boolean isRegistered()
    {
        return !(getSemanticObject().isVirtual());
    }

    /**
     * Check credential.
     * 
     * @param credential the credential
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public void checkCredential(Object credential) throws NoSuchAlgorithmException, LoginException
    { //System.out.println("userURI:"+getURI()+" userPassword:"+getPassword()+" userRep:"+getUserRepository());
      if (null==getURI()){ return;}
        if (getUserRepository().isExternal())
        {
            this.login = getUserRepository().getBridge().validateCredential(getLogin(), credential);
        } else
        {
            String alg = getPassword().substring(1,getPassword().indexOf("}"));
            this.login = getPassword().equals(SWBUtils.CryptoWrapper.comparablePassword(new String((char[]) credential), alg));
        }
        if (isRequestChangePassword())
        {
            this.login=false;
            throw new LoginException("Password was asked to be reset by an admin");
        }
        if (null==getLastLogin() && SWBPlatform.getSecValues().isForceChage()){
            setRequestChangePassword(true);
            this.login=false; 
            throw new LoginException("Password needs to be reset: First Usage");
        }
        if (SWBPlatform.getSecValues().getExpires()>0){
            java.util.Calendar passCal = java.util.Calendar.getInstance();
            passCal.setTime(getPasswordChanged());
            passCal.add(java.util.Calendar.DAY_OF_MONTH, SWBPlatform.getSecValues().getExpires());
            if (passCal.getTime().before(new Date())){
                setRequestChangePassword(true);
                this.login=false; 
                throw new LoginException("Password needs to be reset: time to renew");
            }

        }
        if (null!=getLastLogin() && SWBPlatform.getSecValues().getInactive()>0 ){
            java.util.Calendar passCal = java.util.Calendar.getInstance();
            passCal.setTime(getLastLogin());
            passCal.add(java.util.Calendar.DAY_OF_MONTH, SWBPlatform.getSecValues().getInactive());
            if (passCal.getTime().before(new Date())){
                setActive(false);
                this.login=false; 
                throw new LoginException("User Inactivated due to lack of usage of the account");
            }
        }
        if (this.login) {
            setLastLogin(new Date());
        }
    }

    /**
     * Sets the user type attribute.
     * 
     * @param userType the user type
     * @param name the name
     * @param value the value
     * @throws SWBException the sWB exception
     */
    public void setUserTypeAttribute(String userType, String name, Object value) throws SWBException
    {
        SemanticProperty prop = null;
        Iterator<SemanticClass> itsc = getAsociatedUserTypes(); //getSemanticObject().listSemanticClasses();
        SemanticClass current = null;
        while (itsc.hasNext())
        {
            current = itsc.next();
            if (current.getName().equals(userType))
            {
                break;
            }
        }
        if (current.getName().equals(userType))
        {
            prop = current.getProperty(name);
        }
        setExtendedAttribute(prop, value);
    }

    /**
     * Sets the extended attribute.
     * 
     * @param name the name
     * @param value the value
     * @throws SWBException the sWB exception
     */
    public void setExtendedAttribute(String name, Object value) throws SWBException
    {
        SemanticProperty prop = getUserRepository().getSemanticPropertyOf(name);//getSemanticObject().getSemanticClass().getProperty(name);
//        if (null == prop)
//        {
//            prop = getSemanticObject().getModel().getSemanticProperty(getUserRepository().getId() + "#" + name);
//        }
        setExtendedAttribute(prop, value);
    }

    /**
     * Sets the extended attribute.
     * 
     * @param prop the prop
     * @param value the value
     * @throws SWBException the sWB exception
     */
    public void setExtendedAttribute(SemanticProperty prop, Object value) throws SWBException
    {
        if (null == value)
        {
            throw new SWBException("Can't set a null value");
        }
        if (null != prop && null != prop.getRange())
        {
            if (SemanticVocabulary.XMLS_BOOLEAN.equals(prop.getRange().toString()))
            {
                if (value instanceof Boolean)
                {
                    getSemanticObject().setBooleanProperty(prop, ((Boolean) value).booleanValue());
                } else
                {
                    throw new SWBException("Boolean is not equal to " + value.getClass());
                }
            }
            if (SemanticVocabulary.XMLS_STRING.equals(prop.getRange().toString()))
            {
                if (value instanceof String)
                {
                    getSemanticObject().setProperty(prop, (String) value);
                } else
                {
                    throw new SWBException("String is not equal to " + value.getClass());
                }
            }
            if (SemanticVocabulary.XMLS_INT.equals(prop.getRange().toString()))
            {
                if (value instanceof Integer)
                {
                    getSemanticObject().setIntProperty(prop, ((Integer) value).intValue());
                } else
                {
                    throw new SWBException("Integer is not equal to " + value.getClass());
                }
            }
            if (SemanticVocabulary.XMLS_LONG.equals(prop.getRange().toString()))
            {
                if (value instanceof Long)
                {
                    getSemanticObject().setLongProperty(prop, ((Long) value).longValue());
                } else
                {
                    throw new SWBException("Long is not equal to " + value.getClass());
                }
            }
            if (SemanticVocabulary.XMLS_DATETIME.equals(prop.getRange().toString()))
            {
                if (value instanceof java.util.Date)
                {
                    getSemanticObject().setDateProperty(prop, (java.util.Date) value);
                } else
                {
                    throw new SWBException("java.util.Date is not equal to " + value.getClass());
                }
            }
            if (SemanticVocabulary.XMLS_DATE.equals(prop.getRange().toString()))
            {
                if (value instanceof java.util.Date)
                {
                    getSemanticObject().setDateProperty(prop, (java.util.Date) value);
                } else
                {
                    throw new SWBException("java.util.Date is not equal to " + value.getClass());
                }
            }
            if (SemanticVocabulary.XMLS_FLOAT.equals(prop.getRange().toString()))
            {
                if (value instanceof Float)
                {
                    getSemanticObject().setFloatProperty(prop, ((Float) value).floatValue());
                } else
                {
                    throw new SWBException("Float is not equal to " + value.getClass());
                }
            }
            if (SemanticVocabulary.XMLS_DOUBLE.equals(prop.getRange().toString()))
            {
                if (value instanceof Double)
                {
                    getSemanticObject().setDoubleProperty(prop, ((Double) value).doubleValue());
                } else
                {
                    throw new SWBException(" is not equal to " + value.getClass());
                }
            }
        }
    }

    /**
     * Gets the extended attribute.
     * 
     * @param name the name
     * @return the extended attribute
     */
    public Object getExtendedAttribute(String name)
    {
        SemanticProperty prop = getUserRepository().getSemanticPropertyOf(name);//getSemanticObject().getSemanticClass().getProperty(name);
//        if (null == prop)
//        {
//            prop = new SemanticProperty(getSemanticObject().getModel().getRDFModel().getProperty(getUserRepository().getId() + "#" + name));
//        }

        return getExtendedAttribute(prop);
    }

    /**
     * Gets the full name.
     * 
     * @return the full name
     */
    public String getFullName()
    {
        String fn = getFirstName();
        if (fn == null)
        {
            fn = "";
        }
        String ln = getLastName();
        if (ln == null)
        {
            ln = "";
        } else
        {
            ln = " " + ln;
        }
        String sln = getSecondLastName();
        if (sln == null)
        {
            sln = "";
        } else
        {
            sln = " " + sln;
        }
        return fn + ln + sln;
    }

    /**
     * Gets the extended attribute.
     * 
     * @param prop the prop
     * @return the extended attribute
     */
    public Object getExtendedAttribute(SemanticProperty prop)
    {
        Object obj = null;
        if (null != prop && null != prop.getRange())
        {
            if (SemanticVocabulary.XMLS_BOOLEAN.equals(prop.getRange().toString()))
            {
                obj = Boolean.valueOf(getSemanticObject().getBooleanProperty(prop));
            }
            if (SemanticVocabulary.XMLS_STRING.equals(prop.getRange().toString()))
            {
                obj = getSemanticObject().getProperty(prop);
            }
            if (SemanticVocabulary.XMLS_INT.equals(prop.getRange().toString()))
            {
                obj = Integer.valueOf(getSemanticObject().getIntProperty(prop));
            }
            if (SemanticVocabulary.XMLS_LONG.equals(prop.getRange().toString()))
            {
                obj = Long.valueOf(getSemanticObject().getLongProperty(prop));
            }
            if (SemanticVocabulary.XMLS_DATETIME.equals(prop.getRange().toString()))
            {
                obj = getSemanticObject().getDateProperty(prop);
            }
            if (SemanticVocabulary.XMLS_DATE.equals(prop.getRange().toString()))
            {
                obj = getSemanticObject().getDateProperty(prop);
            }
            if (SemanticVocabulary.XMLS_FLOAT.equals(prop.getRange().toString()))
            {
                obj = new Float(getSemanticObject().getFloatProperty(prop));
            }
            if (SemanticVocabulary.XMLS_DOUBLE.equals(prop.getRange().toString()))
            {
                obj = new Double(getSemanticObject().getDoubleProperty(prop));
            }
        }
        return obj;
    }

    /**
     * Removes the extended attribute.
     * 
     * @param name the name
     * @throws SWBException the sWB exception
     */
    public void removeExtendedAttribute(String name) throws SWBException
    {
        SemanticProperty prop = getUserRepository().getSemanticPropertyOf(name);//getSemanticObject().getSemanticClass().getProperty(name);
        removeExtendedAttribute(prop);
    }

    /**
     * Removes the extended attribute.
     * 
     * @param prop the prop
     * @throws SWBException the sWB exception
     */
    public void removeExtendedAttribute(SemanticProperty prop) throws SWBException
    {
        if (null != prop && null != prop.getRange())
        {
            getSemanticObject().removeProperty(prop);
        } else
        {
            throw new SWBException("Property null or maybe not an extended attribute");
        }
    }
    /*
    public void addUserType(String userType)
    {
    if (null!=userType && !"".equals(userType.trim()) && !hasUserType(userType))
    {
    getSemanticObject().addSemanticClass(getUserRepository().getUserType(userType));
    }
    }

    public void removeUserType(String userType)
    {
    if (hasUserType(userType))
    {
    getSemanticObject().removeSemanticClass(getUserRepository().getUserType(userType));
    }
    }

    public boolean hasUserType(String userType)
    {
    Iterator<SemanticClass> itse = getSemanticObject().listSemanticClasses();
    SemanticClass current = null;
    while (itse.hasNext())
    {
    current = itse.next();
    if (userType.equals(current.getName()))
    {
    break;
    }
    }
    return userType.equals(current.getName());
    }
     */

    /**
     * Checks for user type.
     * 
     * @param userType the user type
     * @return true, if successful
     */
    public boolean hasUserType(String userType)
    {
        boolean ret = false;
        Iterator<String> its = this.listUserTypes();
        while (its.hasNext())
        {
            String currUT = its.next();
            if (currUT.equals(userType))
            {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * Checks for favorite.
     * 
     * @param obj the obj
     * @return true, if successful
     */
    public boolean hasFavorite(SemanticObject obj)
    {
        boolean ret = false;
        UserFavorite fav = getUserFavorite();
        if (fav != null)
        {
            ret = fav.getSemanticObject().hasObjectProperty(UserFavorite.swb_usrfHasObject, obj);
        }
        return ret;
    }

    /**
     * Have access.
     * 
     * @param obj the obj
     * @return true, if successful
     */
    public boolean haveAccess(GenericObject obj)
    {
        //System.out.println(this+" haveAccess:"+obj);
        boolean ret = true;
        if (obj instanceof WebPage)
        {
            ret = evalFilterMap((WebPage)obj);
        }
        
        if (ret && obj instanceof RoleRefable)
        {
            Boolean temp=null;
            boolean andeval=((RoleRefable)obj).isAndEvalRoleRef();
            Iterator<RoleRef> it = ((RoleRefable) obj).listInheritRoleRefs();
            while (it.hasNext())
            {
                RoleRef ref = it.next();
                if(ref!=null)
                {
                    temp=hasRole(ref.getRole());
                    //System.out.println("ref:"+ref+" role:"+ref.getRole());
                    if (temp)
                    {
                        if(!andeval)break;
                    }else
                    {
                        if(andeval)break;
                    }
                }
            }
            if(temp!=null)ret=temp;
        }
        if (ret && obj instanceof RuleRefable)
        {
            Boolean temp=null;
            boolean andeval=((RuleRefable)obj).isAndEvalRuleRef();
            Iterator<RuleRef> it = ((RuleRefable) obj).listInheritRuleRefs();
            while (it.hasNext())
            {
                RuleRef ref = it.next();
                if(ref!=null)
                {
                    //System.out.println("ref:"+ref+" role:"+ref.getRole());
                    Rule rule = ref.getRule();
                    if (rule != null)
                    {
                        temp = Rule.getRuleMgr().eval(this, rule.getURI());
                        if (temp)
                        {
                            if(!andeval)break;
                        }else
                        {
                            if(andeval)break;
                        }
                    }
                }
            }
            if(temp!=null)ret=temp;
        }
        if (ret && obj instanceof UserGroupRefable)
        {
            Boolean temp=null;
            boolean andeval=((UserGroupRefable)obj).isAndEvalUserGroupRef();
            Iterator<UserGroupRef> it = ((UserGroupRefable) obj).listInheritUserGroupRefs();
            while (it.hasNext())
            {
                UserGroupRef ref = it.next();
                //System.out.println("ref:"+ref+" role:"+ref.getRole());
                if(ref!=null)
                {
                    UserGroup usrgrp = ref.getUserGroup();
                    if(usrgrp!=null)
                    {
                        temp=hasUserGroup(usrgrp);
                        if (temp)
                        {
                            if(!andeval)break;
                        }else
                        {
                            if(andeval)break;
                        }
                    }
                }
            }
            if(temp!=null)ret=temp;
        }
        if (ret && obj instanceof Roleable)
        {
            Boolean temp=null;
            Iterator<Role> it = ((Roleable) obj).listRoles();
            while (it.hasNext())
            {
                Role ref = it.next();
                //System.out.println("role:"+ref);
                if (hasRole(ref))
                {
                    temp = true;
                    //System.out.println("hasRole:false");
                    break;
                }else
                {
                    temp = false;
                }
            }
            if(temp!=null)ret=temp;
        }
        if (ret && obj instanceof UserGroupable)
        {
            Boolean temp=null;
            Iterator<UserGroup> it = ((UserGroupable) obj).listUserGroups();
            while (it.hasNext())
            {
                UserGroup usrgrp = it.next();
                //System.out.println("role:"+usrgrp);
                if (hasUserGroup(usrgrp))
                {
                    temp = true;
                    break;
                }else
                {
                    temp = false;
                }
            }
            if(temp!=null)ret=temp;
        }
        if (ret && obj instanceof Localeable)
        {
            Boolean temp=null;
            Language lang = ((Localeable) obj).getLanguage();
            if(lang!=null)
            {
                if(!lang.getId().equals(getLanguage()))
                {
                    ret = false;
                }
            }
        }
        if (ret && obj instanceof Countryable)
        {
            Boolean temp=null;
            Country country = ((Countryable) obj).getCountry();
            if(country!=null)
            {
                if(!country.getId().equals(getCountry()))
                {
                    ret = false;
                }
            }
        }
        //System.out.println("User:"+this+" haveAccess:"+obj+" "+ret);
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserBase#hasRole(org.semanticwb.model.Role)
     */
    @Override
    public boolean hasRole(Role role)
    {
        boolean ret = false;
        while (role != null)
        {
            if (super.hasRole(role))
            {
                ret = true;
                break;
            } else
            {
                role = role.getParent();
            }
        }
        return ret;
    }

    public boolean evalRule(Rule rule)
    {
        return evalRule(rule.getURI());
    }

    public boolean evalRule(String ruleUri)
    {
        return Rule.getRuleMgr().eval(this,ruleUri);
    }

    /**
     * Checks for device.
     * 
     * @param device the device
     * @return true, if successful
     */
    public boolean hasDevice(Device device)
    {
        boolean ret = false;
        Device act = getDevice();
        //System.out.println(act+" "+device);
        if (act != null)
        {
            while (act != null)
            {
                if (device == act)
                {
                    ret = true;
                    break;
                } else
                {
                    act = act.getParent();
                }
            }
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserBase#hasUserGroup(org.semanticwb.model.UserGroup)
     */
    @Override
    public boolean hasUserGroup(UserGroup group)
    {
        boolean ret = false;
        Iterator<UserGroup> grpit = listUserGroups();
        while (grpit.hasNext())
        {
            UserGroup grp = grpit.next();
            while (grp != null)
            {
                if (grp.equals(group))
                {
                    ret = true;
                    break;
                } else
                {
                    grp = grp.getParent();
                }
            }
        }
        return ret;
    }

    /**
     * Gets the asociated user types.
     * 
     * @return the asociated user types
     */
    private Iterator<SemanticClass> getAsociatedUserTypes()
    {
        ArrayList<SemanticClass> lista = new ArrayList<SemanticClass>();
        Iterator<String> curr = listUserTypes();
        while(curr.hasNext())
        {
            String ut = curr.next();
            SemanticClass sc = getUserRepository().getUserType(ut);
            if (null!=sc)
            {
                lista.add(sc);
            }
        }
        return lista.iterator();
    }

    /**
     * Sets the default data.
     * 
     * @param user the new default data
     */
    public void setDefaultData(User user)
    {
        if(getDevice()==null)
        {
            setDevice(user.getDevice());
        }
        if(getIp()==null)
        {
            setIp(user.getIp());
        }
        if(getLanguage()==null)
        {
            setLanguage(user.getLanguage());
        }
        if(getCountry()==null)
        {
            setCountry(user.getCountry());
        }
    }

    public List getHistory()
    {
        return history;
    }

    public TreeSet getVisited()
    {
        return visited;
    }
    
    public boolean isInHistory(WebPage page)
    {
        return history.contains(page.getId());
    }
    
    public boolean isVisited(WebPage page)
    {
        return visited.contains(page.getId());
    }

    public void addVisitedWebPage(WebPage page)
    {
        synchronized(visited){
            visited.add(page.getId());
        }
        synchronized(history)
        {
            if(!history.isEmpty() && !history.getFirst().equals(page.getId()))
            {
                history.add(page.getId());
                if(history.size()>25)history.removeLast();
            }
        }
    }
    
    public void setUserProperty(String propid, Object value)
    {
        SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(propid);
        setUserProperty(prop,value);
    }
    
    public void setUserProperty(SemanticProperty prop, Object value)
    {
        //TODO: Revisar si es literal u objeto
        if(prop.isDataTypeProperty())
        {
            getSemanticObject().setLiteralProperty(prop, new SemanticLiteral(value));
        }else
        {
            if(value instanceof SemanticObject)getSemanticObject().setObjectProperty(prop,(SemanticObject)value);
            else if(value instanceof GenericObject)getSemanticObject().setObjectProperty(prop,((GenericObject)value).getSemanticObject());
        }
    }
    
    public Object getUserProperty(String propid)
    {
        SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(propid);
        return getUserProperty(prop);
    }

    public Object getUserProperty(SemanticProperty prop)
    {
        if(prop.isDataTypeProperty())
        {
            return getSemanticObject().getLiteralProperty(prop).getValue();
        }else
        {
            return getSemanticObject().getObjectProperty(prop);
        }
    }   
    
    /**
     * Evalua el filtro de secciones
     * 
     * @param topic the topic
     * @return true, if successful
     * @return
     */
    public boolean evalFilterMap(WebPage topic) 
    {
        //System.out.println("User:evalFilterMap:"+this+" "+topic+" "+getUserFilter());
        UserFilter pfilter = getUserFilter();
        if(pfilter==null)return true;
        else return pfilter.evalFilterMap(topic);
    }    
    
}
