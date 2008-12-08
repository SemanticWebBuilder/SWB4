/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.repository;

import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticClass;

/**
 *
 * @author victor.lorenzana
 */
public class SWBOfficeManager implements OfficeManager
{

    private static final String DESCRIPTION_BY_DEFAULT = "Sin descripción";
    private static Logger log = SWBUtils.getLogger(SWBOfficeManager.class);
    SemanticClass cm_content = BaseNode.vocabulary.cm_OfficeContent;

    public HashMap<String, String> getContentTypes()
    {
        HashMap<String, String> types = new HashMap<String, String>();

        String label = DESCRIPTION_BY_DEFAULT;
        String literal = cm_content.getDisplayName("es");
        if (literal != null)
        {
            label = literal;
        }
        types.put(cm_content.getPrefix() + ":" + cm_content.getName(), label);
        Iterator<SemanticClass> childClases = cm_content.listSubClasses();
        while (childClases.hasNext())
        {
            SemanticClass clazz = childClases.next();
            label = DESCRIPTION_BY_DEFAULT;
            literal = clazz.getDisplayName("es");
            if (literal != null)
            {
                label = literal;
            }
            types.put(clazz.getPrefix() + ":" + clazz.getName(), label);
        }
        return types;
    }

    public String getCategoryType()
    {
        return BaseNode.vocabulary.cm_OfficeCategory.getPrefix() + ":" + BaseNode.vocabulary.cm_OfficeCategory.getName();
    }

    public String getPropertyTitleType()
    {
        return BaseNode.vocabulary.cm_title.getPrefix() + ":" + BaseNode.vocabulary.cm_title.getName();
    }

    public String getPropertyDescriptionType()
    {
        return BaseNode.vocabulary.cm_description.getPrefix() + ":" + BaseNode.vocabulary.cm_description.getName();
    }
}
