package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Templateable extends GenericObject
{

    public void setTemplate(org.semanticwb.model.Template template);

    public void removeTemplate();

    public Template getTemplate();
}
