/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest.publish;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import org.semanticwb.platform.SemanticClass;

/**
 *
 * @author victor.lorenzana
 */
public class PublishDefinition {
    private final SemanticClass clazz;
    private final Set<RepresentationMediaType> publishType;
    public PublishDefinition(SemanticClass clazz)
    {
        this.clazz=clazz;
        publishType=new HashSet(EnumSet.of(RepresentationMediaType.XML));
    }
    public PublishDefinition(SemanticClass clazz,Set<RepresentationMediaType> publishType)
    {
        this.clazz=clazz;
        this.publishType=publishType;
    }
    public SemanticClass getSemanticClass()
    {
        return clazz;
    }

    public Representation[] getRepresentations()
    {
        Set<Representation> getRepresentations=new HashSet<Representation>();
        for(RepresentationMediaType RepresentationMediaType : publishType)
        {
            if(RepresentationMediaType==RepresentationMediaType.XML)
            {
                getRepresentations.add(new XMLRepresentation(clazz));
            }
            if(RepresentationMediaType==RepresentationMediaType.JSON)
            {
                getRepresentations.add(new JSONRepresentation(clazz));
            }
        }
        return getRepresentations.toArray(new Representation[getRepresentations.size()]);
    }
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PublishDefinition other = (PublishDefinition) obj;
        if (this.clazz != other.clazz && (this.clazz == null || !this.clazz.equals(other.clazz)))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 19 * hash + (this.clazz != null ? this.clazz.hashCode() : 0);
        return hash;
    }
    
}
