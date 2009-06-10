package org.semanticwb.model;


public class ResourceType extends org.semanticwb.model.base.ResourceTypeBase 
{
    public static int MODE_CONTENT=1;
    public static int MODE_STRATEGY=2;
    public static int MODE_SYSTEM=3;

    public ResourceType(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
