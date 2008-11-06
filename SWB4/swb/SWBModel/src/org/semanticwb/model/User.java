package org.semanticwb.model;

import java.security.AccessController;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Date;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;

public class User extends UserBase implements Principal, java.io.Serializable
{

    Logger log = SWBUtils.getLogger(User.class);
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
        String tmpPasswd = null;
        try
        {
            tmpPasswd = SWBUtils.CryptoWrapper.passwordDigest(password);
            super.setUsrPassword(tmpPasswd);
            setUsrPasswordChanged(new Date());
        } catch (NoSuchAlgorithmException ex)
        {
            log.error("User: Can't set a crypted Password", ex);
        }

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

    public void checkCredential(Object credential) throws NoSuchAlgorithmException
    {
        this.login = getUsrPassword().equals(SWBUtils.CryptoWrapper.comparablePassword(new String((char[]) credential)));
    }

    
    public void setExtendedAttribute(String name, Object value) throws SWBException
    {
        SemanticProperty prop = getSemanticObject().getSemanticClass().getProperty(name);
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

        return getExtendedAttribute(prop);
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
}
