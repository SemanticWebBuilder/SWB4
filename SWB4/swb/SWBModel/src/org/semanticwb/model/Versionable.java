package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Versionable 
{

    public void setLastVersion(org.semanticwb.model.VersionInfo versioninfo);

    public void removeLastVersion();

    public VersionInfo getLastVersion();

    public void setActualVersion(org.semanticwb.model.VersionInfo versioninfo);

    public void removeActualVersion();

    public VersionInfo getActualVersion();
}
