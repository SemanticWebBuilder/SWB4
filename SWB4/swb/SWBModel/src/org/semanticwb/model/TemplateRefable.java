package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface TemplateRefable 
{

    public SemanticIterator<org.semanticwb.model.TemplateRef> listTemplateRef();

    public void addTemplateRef(org.semanticwb.model.TemplateRef templateref);

    public void removeAllTemplateRef();

    public void removeTemplateRef(org.semanticwb.model.TemplateRef templateref);

    public TemplateRef getTemplateRef();
}
