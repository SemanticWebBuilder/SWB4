package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;
import org.semanticwb.model.GenericIterator;

public class ObjectActionBase extends WebPage implements Sortable,Iconable
{

    public ObjectActionBase(SemanticObject base)
    {
        super(base);
    }

    public int getIndex()
    {
        return getSemanticObject().getIntProperty(vocabulary.swb_index);
    }

    public void setIndex(int index)
    {
        getSemanticObject().setLongProperty(vocabulary.swb_index, index);
    }

    public String getIconClass()
    {
        return getSemanticObject().getProperty(vocabulary.swb_iconClass);
    }

    public void setIconClass(String iconClass)
    {
        getSemanticObject().setProperty(vocabulary.swb_iconClass, iconClass);
    }

    public String getActGroup()
    {
        return getSemanticObject().getProperty(vocabulary.swbxf_actGroup);
    }

    public void setActGroup(String actGroup)
    {
        getSemanticObject().setProperty(vocabulary.swbxf_actGroup, actGroup);
    }

    public String getActionURL()
    {
        return getSemanticObject().getProperty(vocabulary.swbxf_actionURL);
    }

    public void setActionURL(String actionURL)
    {
        getSemanticObject().setProperty(vocabulary.swbxf_actionURL, actionURL);
    }
}
