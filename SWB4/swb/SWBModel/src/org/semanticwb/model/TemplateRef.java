package org.semanticwb.model;

import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class TemplateRef extends TemplateRefBase 
{
    public static final int INHERIT_ACTUALANDCHILDS=1;
    public static final int INHERIT_ACTUAL=2;
    public static final int INHERIT_CHILDS=3;

    public TemplateRef(SemanticObject base)
    {
        super(base);
    }
}
