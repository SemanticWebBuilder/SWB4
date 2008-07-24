package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Localeable 
{
    public SemanticIterator<org.semanticwb.model.Language> listLanguage();
    public void addLanguage(org.semanticwb.model.Language language);
    public void removeAllLanguage();
    public Language getLanguage();
}
