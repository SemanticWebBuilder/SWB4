package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class VersionableBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public VersionableBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public void setLastVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        addObjectProperty(vocabulary.lastVersion, versioninfo);
    }

    public void removeLastVersion()
    {
        removeProperty(vocabulary.lastVersion);
    }

    public VersionInfo getLastVersion()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.lastVersion.getRDFProperty());
         SemanticIterator<org.semanticwb.model.VersionInfo> it=new SemanticIterator<org.semanticwb.model.VersionInfo>(VersionInfo.class, stit);
         return it.next();
    }

    public void setActualVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        addObjectProperty(vocabulary.actualVersion, versioninfo);
    }

    public void removeActualVersion()
    {
        removeProperty(vocabulary.actualVersion);
    }

    public VersionInfo getActualVersion()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.actualVersion.getRDFProperty());
         SemanticIterator<org.semanticwb.model.VersionInfo> it=new SemanticIterator<org.semanticwb.model.VersionInfo>(VersionInfo.class, stit);
         return it.next();
    }
}
