package org.semanticwb.model;

import java.util.Iterator;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class UserRepository extends UserRepositoryBase 
{
    public UserRepository(SemanticObject base)
    {
        super(base);
    }
    
    
    public User getUserByLogin(String login)
    {
        User ret=null;
        SWBVocabulary voc=SWBContext.getVocabulary();
        Iterator aux=getSemanticObject().getRDFResource().getModel().listStatements(null, voc.usrLogin.getRDFProperty(), login);
        Iterator it=new GenericIterator(voc.User, aux, true);
        if(it.hasNext())
        {
            ret=(User)it.next();
        }
        return ret;
    }
}
