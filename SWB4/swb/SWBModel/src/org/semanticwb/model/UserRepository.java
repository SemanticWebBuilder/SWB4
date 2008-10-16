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
            
    public UserRepository(SemanticObject base)
    {
        super(base);
    }
    
    
    public User getUserByLogin(String login)
    {
        User ret=null;
        if (null!=login)
        {
            SWBVocabulary voc=SWBContext.getVocabulary();
            Iterator aux=getSemanticObject().getRDFResource().getModel().listStatements(null, voc.usrLogin.getRDFProperty(), getSemanticObject().getModel().getRDFModel().createLiteral(login));
            Iterator it=new GenericIterator(voc.User, aux, true);
            if(it.hasNext())
            {
                ret=(User)it.next();
            }
        }
        return ret;
    }
    
    public SemanticProperty createIntExtendedAttribute(String name)
    {
        return getSemanticObject().getModel().createSemanticProperty(getId()+"#"+name, getExtendedAttributesClass(), SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_INT);
    }    
    
    public Iterator<SemanticProperty> listExtendedAttributes()
    {
        return getExtendedAttributesClass().listProperties();
    }       
    
    public SemanticClass getExtendedAttributesClass()
    {
        SemanticClass cls=null;
        String uri=getProperty("extendedAttributes");
        if(uri==null)
        {
            uri=getId()+"#clsExtendedAttibutes";
            cls=getSemanticObject().getModel().createSemanticClass(uri);
            setProperty("extendedAttributes", uri);
        }else
        {
            cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
        }
        return cls;
    }
    
}
