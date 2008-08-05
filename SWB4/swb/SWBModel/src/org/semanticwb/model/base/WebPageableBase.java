package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class WebPageableBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public WebPageableBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public SemanticIterator<org.semanticwb.model.WebPage> listWebPage()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasWebPage.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit);
    }

    public void addWebPage(org.semanticwb.model.WebPage webpage)
    {
        addObjectProperty(vocabulary.hasWebPage, webpage);
    }

    public void removeAllWebPage()
    {
        getRDFResource().removeAll(vocabulary.hasWebPage.getRDFProperty());
    }

    public WebPage getWebPage()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasWebPage.getRDFProperty());
         SemanticIterator<org.semanticwb.model.WebPage> it=new SemanticIterator<org.semanticwb.model.WebPage>(WebPage.class, stit);
         return it.next();
    }
}
