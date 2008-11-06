package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Model;
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

    public UserRepository(SemanticObject base)
    {
        super(base);
        String uri = getProperty("extendedAttributes");
        if (uri != null)
        {
            uri = getId() + SWBUR_ClassPost;
            getSemanticObject().getModel().registerClass(uri);
        }
    }

    public User getUserByLogin(String login)
    {
        User ret = null;
        if (null != login)
        {
            SWBVocabulary voc = SWBContext.getVocabulary();
            Iterator aux = getSemanticObject().getRDFResource().getModel().listStatements(null, voc.usrLogin.getRDFProperty(), getSemanticObject().getModel().getRDFModel().createLiteral(login));
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
        SemanticProperty sp = getExtendedAttributesClass().getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, getExtendedAttributesClass(), SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_INT);
        }
        return sp;
    }

    public SemanticProperty createLongExtendedAttribute(String name)
    {
        SemanticProperty sp = getExtendedAttributesClass().getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, getExtendedAttributesClass(), SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_LONG);
        }
        return sp;
    }

    public SemanticProperty createBooleanExtendedAttribute(String name)
    {
        SemanticProperty sp = getExtendedAttributesClass().getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, getExtendedAttributesClass(), SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_BOOLEAN);
        }
        return sp;
    }

    public SemanticProperty createDateTimeExtendedAttribute(String name)
    {
        SemanticProperty sp = getExtendedAttributesClass().getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, getExtendedAttributesClass(), SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_DATETIME);
        }
        return sp;
    }

    public SemanticProperty createDoubleExtendedAttribute(String name)
    {
        SemanticProperty sp = getExtendedAttributesClass().getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, getExtendedAttributesClass(), SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_DOUBLE);
        }
        return sp;
    }

    public SemanticProperty createFloatExtendedAttribute(String name)
    {
        SemanticProperty sp = getExtendedAttributesClass().getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, getExtendedAttributesClass(), SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_FLOAT);
        }
        return sp;
    }

    public SemanticProperty createStringExtendedAttribute(String name)
    {
        SemanticProperty sp = getExtendedAttributesClass().getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, getExtendedAttributesClass(), SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_STRING);
        }
        return sp;
    }

    public Iterator<SemanticProperty> listExtendedAttributes()
    {
        return getExtendedAttributesClass().listProperties();
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
}
