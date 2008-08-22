package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Localeable extends GenericObject
{

    public void setLanguage(org.semanticwb.model.Language language);

    public void removeLanguage();

    public Language getLanguage();
}
