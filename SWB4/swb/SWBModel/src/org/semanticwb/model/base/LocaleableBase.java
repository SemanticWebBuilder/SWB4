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

    public void addUseLanguage(org.semanticwb.model.Language language)
    {
        addObjectProperty(vocabulary.useLanguage, language);
    }

    public void removeUseLanguage()
    {
        getRDFResource().removeAll(vocabulary.useLanguage.getRDFProperty());
    }

    public Language getUseLanguage()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.useLanguage.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Language> it=new SemanticIterator<org.semanticwb.model.Language>(Language.class, stit);
         return it.next();
    }
}
