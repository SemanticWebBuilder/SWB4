package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Versionable extends GenericObject
{

    public void setActualVersion(org.semanticwb.model.VersionInfo versioninfo);

    public void removeActualVersion();

    public VersionInfo getActualVersion();

    public void setLastVersion(org.semanticwb.model.VersionInfo versioninfo);

    public void removeLastVersion();

    public VersionInfo getLastVersion();
}
