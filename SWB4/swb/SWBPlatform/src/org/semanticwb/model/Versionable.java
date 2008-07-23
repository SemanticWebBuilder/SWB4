package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Versionable 
{
    public SemanticIterator<org.semanticwb.model.VersionInfo> listVersionInfo();
    public void addVersionInfo(org.semanticwb.model.VersionInfo versioninfo);
    public void removeAllVersionInfo();
    public VersionInfo getVersionInfo();
}
