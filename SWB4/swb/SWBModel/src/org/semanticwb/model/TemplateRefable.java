package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface TemplateRefable extends GenericObject
{

    public GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRef();

    public void addTemplateRef(org.semanticwb.model.TemplateRef templateref);

    public void removeAllTemplateRef();

    public void removeTemplateRef(org.semanticwb.model.TemplateRef templateref);

    public TemplateRef getTemplateRef();
}
