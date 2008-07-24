package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Templateable 
{
    public SemanticIterator<org.semanticwb.model.Template> listTemplate();
    public void addTemplate(org.semanticwb.model.Template template);
    public void removeAllTemplate();
    public Template getTemplate();
}
