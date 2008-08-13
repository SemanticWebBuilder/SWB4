package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Localeable 
{

    public void setLanguage(org.semanticwb.model.Language language);

    public void removeLanguage();

    public Language getLanguage();
}
