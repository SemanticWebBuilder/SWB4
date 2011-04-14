package org.semanticwb.process.model;

import java.util.List;

public interface Containerable extends org.semanticwb.process.model.base.ContainerableBase
{
    public org.semanticwb.process.model.ProcessSite getProcessSite();

    public List<ItemAware> listRelatedItemAware();

    public List<ItemAware> listHerarquicalRelatedItemAware();

}
