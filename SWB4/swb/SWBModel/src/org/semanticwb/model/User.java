package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Statement;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;

public class User extends UserBase implements Principal, java.io.Serializable
{

    static Logger log = SWBUtils.getLogger(User.class);
    private String device = null;
    private String ip = null;
    private boolean login = false;

    public User(SemanticObject base)
    {
        super(base);
    }

    public String getName()
    {
        return getUsrLogin();
    }

    @Override
    public void setUsrPassword(String password)
    {
        //System.out.println("setPassword:"+password);
        String tmpPasswd = null;
        try
        {
            tmpPasswd = SWBUtils.CryptoWrapper.passwordDigest(password);
            //System.out.println("tmpPasswd:"+tmpPasswd);

            Statement stm = getSemanticObject().getRDFResource().getProperty(User.swb_usrPassword.getRDFProperty());
            if (stm != null)
            {
                stm.changeObject(tmpPasswd);
            }
            else
            {
                getSemanticObject().getRDFResource().addProperty(User.swb_usrPassword.getRDFProperty(), tmpPasswd);
            }
            setUsrPasswordChanged(new Date());
        } catch (NoSuchAlgorithmException ex)
        {
            log.error("User: Can't set a crypted Password", ex);
        }

    }

    @Override
    public String getUsrPassword()
    {
        String ret=null;
        Statement st=getSemanticObject().getRDFResource().getProperty(User.swb_usrPassword.getRDFProperty());
        if(st!=null)
        {
            ret=st.getString();
        }
        //System.out.println("getPassword:"+ret);
        return ret;
    }


    public String getDevice()
    {
        return device;
    }

    public void setDevice(String device)
    {
        this.device = device;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public boolean isSigned()
    {
        return login;
    }

    public boolean isRegistered()
    {
        return !(getSemanticObject().isVirtual());
    }

    public void checkCredential(Object credential) throws NoSuchAlgorithmException
    {
        this.login = getUsrPassword().equals(SWBUtils.CryptoWrapper.comparablePassword(new String((char[]) credential)));
    }

    public void setUserTypeAttribute(String userType, String name, Object value) throws SWBException
    {
        SemanticProperty prop = null;
        Iterator<SemanticClass> itsc = getSemanticObject().listSemanticClasses();
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

    public void setExtendedAttribute(String name, Object value) throws SWBException
    {
        SemanticProperty prop = getSemanticObject().getSemanticClass().getProperty(name);
        if ( null == prop) prop = getSemanticObject().getModel().getSemanticProperty(getUserRepository().getId()+"#" + name);
        setExtendedAttribute(prop, value);
    }

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

    public Object getExtendedAttribute(String name)
    {
        SemanticProperty prop = getSemanticObject().getSemanticClass().getProperty(name);
        if ( null == prop) prop = new SemanticProperty(getSemanticObject().getModel().getRDFModel().getProperty(getUserRepository().getId()+"#" + name));

        return getExtendedAttribute(prop);
    }

    public String getUsrFullName()
    {
        String fn=getUsrFirstName();
        if(fn==null)fn="";
        String ln=getUsrLastName();
        if(ln==null)ln="";
        else ln=" "+ln;
        String sln=getUsrSecondLastName();
        if(sln==null)sln="";
        else ln=" "+ln;
        return fn+ln+sln;
    }

    public Object getExtendedAttribute(SemanticProperty prop)
    {
        Object obj = null; 
        if (null != prop && null != prop.getRange())
        {
            if (SemanticVocabulary.XMLS_BOOLEAN.equals(prop.getRange().toString()))
            {
                obj = new Boolean(getSemanticObject().getBooleanProperty(prop));
            }
            if (SemanticVocabulary.XMLS_STRING.equals(prop.getRange().toString()))
            {
                obj = getSemanticObject().getProperty(prop);
            }
            if (SemanticVocabulary.XMLS_INT.equals(prop.getRange().toString()))
            {
                obj = new Integer(getSemanticObject().getIntProperty(prop));
            }
            if (SemanticVocabulary.XMLS_LONG.equals(prop.getRange().toString()))
            {
                obj = new Long(getSemanticObject().getLongProperty(prop));
            }
            if (SemanticVocabulary.XMLS_DATETIME.equals(prop.getRange().toString()))
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

    public void removeExtendedAttribute(String name) throws SWBException
    {
        SemanticProperty prop = getSemanticObject().getSemanticClass().getProperty(name);
        removeExtendedAttribute(prop);
    }

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

    public boolean hasFavorite(SemanticObject obj)
    {
        boolean ret=false;
        UserFavorites fav=getUserFavorites();
        if(fav!=null)
        {
            ret=fav.getSemanticObject().hasObjectProperty(fav.swb_usrfHasObjects, obj);
        }
        return ret;
    }

    public boolean haveAccess(GenericObject obj)
    {
        //System.out.println(this+" haveAccess:"+obj);
        boolean ret=true;
        if(obj instanceof RoleRefable)
        {
            Iterator<RoleRef> it=((RoleRefable)obj).listInheritRoleRefs();
            while(it.hasNext())
            {
                RoleRef ref=it.next();
                //System.out.println("ref:"+ref+" role:"+ref.getRole());
                if(!hasRole(ref.getRole()))
                {
                    ret=false;
                    //System.out.println("hasRole:false");
                    break;
                }
            }
        }
        if(ret && obj instanceof RuleRefable)
        {
            Iterator<RuleRef> it=((RuleRefable)obj).listInheritRuleRefs();
            while(it.hasNext())
            {
                RuleRef ref=it.next();
                //System.out.println("ref:"+ref+" role:"+ref.getRole());
                Rule rule=ref.getRule();
                if(rule!=null)
                {
                    ret=Rule.getRuleMgr().eval(this, rule.getURI());
                    if(!ret)break;
                }
            }
        }
        if(ret && obj instanceof UserGroupRefable)
        {
            Iterator<UserGroupRef> it=((UserGroupRefable)obj).listUserGroupRefs();
            while(it.hasNext())
            {
                UserGroupRef ref=it.next();
                //System.out.println("ref:"+ref+" role:"+ref.getRole());
                UserGroup usrgrp=ref.getUserGroup();
                if(!hasUserGroup(usrgrp))
                {
                    ret=false;
                    break;
                }
            }
        }
        //System.out.println("User:"+this+" haveAccess:"+obj+" "+ret);
        return ret;
    }

    @Override
    public boolean hasRole(Role role)
    {
        boolean ret=false;
        while(role!=null)
        {
            if(super.hasRole(role))
            {
                ret=true;
                break;
            }else
            {
                role=role.getParent();
            }
        }
        return ret;
    }

    @Override
    public boolean hasUserGroup(UserGroup group)
    {
        boolean ret=false;
        Iterator<UserGroup> grpit=listUserGroups();
        while(grpit.hasNext())
        {
            UserGroup grp=grpit.next();
            while(grp!=null)
            {
                if(grp.equals(group))
                {
                    ret=true;
                    break;
                }else
                {
                    grp=grp.getParent();
                }
            }
        }
        return ret;
    }


}
