package org.semanticwb.process.model;

import java.util.Iterator;
import java.util.Map;
import org.semanticwb.platform.SemanticClass;

public interface Containerable extends org.semanticwb.process.model.base.ContainerableBase
{
    public org.semanticwb.process.model.ProcessSite getProcessSite();

    public Map<ItemAware, SemanticClass> getRelatedItemAwareClasses();

    public Map<ItemAware, SemanticClass> getHerarquicalRelatedItemAwareClasses();

}
