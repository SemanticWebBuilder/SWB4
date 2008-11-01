package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface PFlowRefable extends GenericObject
{

    public GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRefs();

    public void addPFlowRef(org.semanticwb.model.PFlowRef pflowref);

    public void removeAllPFlowRef();

    public void removePFlowRef(org.semanticwb.model.PFlowRef pflowref);

    public PFlowRef getPFlowRef();
}
