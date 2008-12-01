package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Templateable extends GenericObject
{

    public GenericIterator<org.semanticwb.model.Template> listTemplates();

    public void addTemplate(org.semanticwb.model.Template template);

    public void removeAllTemplate();

    public void removeTemplate(org.semanticwb.model.Template template);

    public Template getTemplate();
}
