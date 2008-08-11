package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class LocaleableBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public LocaleableBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public void setLanguage(org.semanticwb.model.Language language)
    {
        addObjectProperty(vocabulary.language, language);
    }

    public void removeLanguage()
    {
        removeProperty(vocabulary.language);
    }

    public Language getLanguage()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.language.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Language> it=new SemanticIterator<org.semanticwb.model.Language>(Language.class, stit);
         return it.next();
    }
}
