package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;

public class UserRepository extends UserRepositoryBase
{

    public static final String SWBUR_AuthMethod = "SWBUR_AuthMethod";
    public static final String SWBUR_LoginContext = "SWBUR_LoginContext";
    public static final String SWBUR_CallBackHandlerClassName = "SWBUR_CallBackHandlerClassName";
    public static final String SWBUR_ClassHold = "extendedAttributes";
    public static final String SWBUR_ClassPost = "#clsExtendedAttibutes";
    public static final String SWBUR_ClassUserTypeHold = "userType";
    public static final String SWBUR_ClassUserTypePost = "/clsUserType";
    private static ArrayList<String> userTypes = new ArrayList<String>();

    public UserRepository(SemanticObject base)
    {
        super(base);
        String uri = getProperty(SWBUR_ClassHold);
        if (uri != null)
        {
            uri = getId() + SWBUR_ClassPost;
            getSemanticObject().getModel().registerClass(uri);
        }
        StmtIterator ptopIt = getSemanticObject().getModel().getRDFModel().listStatements(getSemanticObject().getRDFResource(), null, (String) null);
        while (ptopIt.hasNext())
        {
            Statement sp = (Statement) ptopIt.next();
            if (sp.getPredicate().getLocalName().startsWith(SWBUR_ClassUserTypeHold))
            {
                uri = sp.getObject().toString();
                userTypes.add(uri.split("#")[1]);
                getSemanticObject().getModel().registerClass(uri);
            }
        }
    }

    public User getUserByLogin(String login)
    {
        User ret = null;
        if (null != login)
        {
            SWBVocabulary voc = SWBContext.getVocabulary();
            Iterator aux = getSemanticObject().getRDFResource().getModel().listStatements(null, voc.swb_usrLogin.getRDFProperty(), getSemanticObject().getModel().getRDFModel().createLiteral(login));
            Iterator it = new GenericIterator(voc.swb_User, aux, true);
            if (it.hasNext())
            {
                ret = (User) it.next();
            }
        }
        return ret;
    }

    public SemanticProperty getExtendedAttribute(String name)
    {
        return getExtendedAttributesClass().getProperty(name);
    }

    public SemanticProperty createIntExtendedAttribute(String name)
    {
        return createIntExtendedAttribute(name, null);
    }

    public SemanticProperty createIntExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_INT);
        }
        return sp;
    }

    public SemanticProperty createLongExtendedAttribute(String name)
    {
        return createLongExtendedAttribute(name, null);
    }

    public SemanticProperty createLongExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_LONG);
        }
        return sp;
    }

    public SemanticProperty createBooleanExtendedAttribute(String name)
    {
        return createBooleanExtendedAttribute(name, null);
    }

    public SemanticProperty createBooleanExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_BOOLEAN);
        }
        return sp;
    }

    public SemanticProperty createDateTimeExtendedAttribute(String name)
    {
        return createDateTimeExtendedAttribute(name, null);
    }

    public SemanticProperty createDateTimeExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_DATETIME);
        }
        return sp;
    }

    public SemanticProperty createDoubleExtendedAttribute(String name)
    {
        return createDoubleExtendedAttribute(name, null);
    }

    public SemanticProperty createDoubleExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_DOUBLE);
        }
        return sp;
    }

    public SemanticProperty createFloatExtendedAttribute(String name)
    {
        return createFloatExtendedAttribute(name, null);
    }

    public SemanticProperty createFloatExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_FLOAT);
        }
        return sp;
    }

    public SemanticProperty createStringExtendedAttribute(String name)
    {
        return createStringExtendedAttribute(name, null);
    }

    public SemanticProperty createStringExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_STRING);
        }
        return sp;
    }

    public Iterator<SemanticProperty> listAttributes()
    {
        ArrayList<SemanticProperty> alsp = new ArrayList<SemanticProperty>();
        String uri = null;
        SWBVocabulary voc = SWBContext.getVocabulary();
        SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(voc.swb_User.getURI());
        if (null != sc)
        {
            Iterator<SemanticProperty> itsp = sc.listProperties();
            while (itsp.hasNext())
            {
                SemanticProperty sp = itsp.next();
                if (null == sp.getRange())
                {
                    continue;
                }
                alsp.add(sp);
            }
        }
        uri = getProperty(SWBUR_ClassHold);
        sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
        if (null != sc)
        {
            Iterator<SemanticProperty> itsp = sc.listProperties();
            while (itsp.hasNext())
            {
                SemanticProperty sp = itsp.next();
                if (null == sp.getRange())
                {
                    continue;
                }
                alsp.add(sp);
            }
        }
        StmtIterator ptopIt = getSemanticObject().getModel().getRDFModel().listStatements(getSemanticObject().getRDFResource(), null, (String) null);
        while (ptopIt.hasNext())
        {
            Statement st = (Statement) ptopIt.next();
            if (st.getPredicate().getLocalName().startsWith(SWBUR_ClassUserTypeHold))
            {
                uri = st.getObject().toString();
                sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
                if (null != sc)
                {
                    Iterator<SemanticProperty> itsp = sc.listProperties();
                    while (itsp.hasNext())
                    {
                        SemanticProperty sp = itsp.next();
                        if (null == sp.getRange())
                        {
                            continue;
                        }
                        alsp.add(sp);
                    }
                }
            }
        }
        return alsp.iterator();
    }

    public SemanticClass getExtendedAttributesClass()
    {
        SemanticClass cls = null;
        String uri = getProperty(SWBUR_ClassHold);
        if (uri == null)
        {
            uri = getId() + SWBUR_ClassPost;
            cls = getSemanticObject().getModel().createSemanticClass(uri);
            setProperty(SWBUR_ClassHold, uri);
        } else
        {
            cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
        }
        return cls;
    }

    public SemanticClass getUserType(String name)
    {
        SemanticClass cls = null;
        String uri = getProperty(SWBUR_ClassUserTypeHold + name);
        if (uri == null)
        {
            uri = getId() + SWBUR_ClassUserTypePost + "#" + name;
            cls = getSemanticObject().getModel().createSemanticClass(uri);
            setProperty(SWBUR_ClassUserTypeHold + name, uri);
            userTypes.add(uri.split("#")[1]);
        } else
        {
            cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
        }
        return cls;
    }

    public Iterator<String> getUserTypes()
    {
        return userTypes.iterator();
    }
}
