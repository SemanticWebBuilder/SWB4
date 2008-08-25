package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class PortletClassBase extends GenericObjectBase implements Groupable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PortletClassBase(SemanticObject base)
    {
        super(base);
    }

    public GenericIterator<org.semanticwb.model.ObjectGroup> listGroup()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasGroup.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.ObjectGroup>(org.semanticwb.model.ObjectGroup.class, stit);
    }

    public void addGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasGroup, objectgroup.getSemanticObject());
    }

    public void removeAllGroup()
    {
        getSemanticObject().removeProperty(vocabulary.hasGroup);
    }

    public void removeGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasGroup,objectgroup.getSemanticObject());
    }

    public ObjectGroup getGroup()
    {
         ObjectGroup ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasGroup.getRDFProperty());
         GenericIterator<org.semanticwb.model.ObjectGroup> it=new GenericIterator<org.semanticwb.model.ObjectGroup>(ObjectGroup.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
