/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.indexer.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.Tagable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.indexer.IndexTerm;
import org.semanticwb.portal.indexer.SWBIndexer;

/**
 *
 * @author javier.solis
 */
public class GenericParser
{

    public boolean canIndex(Searchable gen)
    {
        return gen.isValid();
    }

    public boolean canUserView(Searchable gen, User user)
    {
        return  gen.isValid() && user.haveAccess(gen);
    }

    public Map<String,IndexTerm> getIndexTerms(Searchable gen)
    {
        HashMap<String,IndexTerm> map=new HashMap();

        map.put(SWBIndexer.ATT_URI,new IndexTerm(SWBIndexer.ATT_URI,gen.getURI(),true,IndexTerm.INDEXED_NO_ANALYZED));
        map.put(SWBIndexer.ATT_CLASS,new IndexTerm(SWBIndexer.ATT_CLASS,getIndexClass(gen),false,IndexTerm.INDEXED_TOKENIZED_NO_ANALYZED));
        map.put(SWBIndexer.ATT_CATEGORY,new IndexTerm(SWBIndexer.ATT_CATEGORY,getIndexCategory(gen),false,IndexTerm.INDEXED_TOKENIZED_NO_ANALYZED));
        map.put(SWBIndexer.ATT_MODEL,new IndexTerm(SWBIndexer.ATT_MODEL,getIndexModel(gen),false,IndexTerm.INDEXED_ANALYZED));
        map.put(SWBIndexer.ATT_TITLE,new IndexTerm(SWBIndexer.ATT_TITLE,getIndexTitle(gen),false,IndexTerm.INDEXED_ANALYZED));
        map.put(SWBIndexer.ATT_DESCRIPTION,new IndexTerm(SWBIndexer.ATT_DESCRIPTION,getIndexDescription(gen),false,IndexTerm.INDEXED_ANALYZED));
        map.put(SWBIndexer.ATT_TAGS,new IndexTerm(SWBIndexer.ATT_TAGS,getIndexTags(gen),false,IndexTerm.INDEXED_ANALYZED));
        //implementar en subclases
        //map.put(SWBIndexer.ATT_DATA,new IndexTerm(SWBIndexer.ATT_DATA,getIndexData(gen),false,IndexTerm.INDEXED_ANALYZED));
        //map.put(SWBIndexer.ATT_SUMMARY,new IndexTerm(SWBIndexer.ATT_SUMMARY,getSummary(gen, null),true,IndexTerm.INDEXED_NO));
        return map;
    }

    public String getIndexModel(Searchable gen)
    {
        SemanticObject sobj=gen.getSemanticObject();
        return sobj.getModel().getName();
    }

    public String getIndexTitle(Searchable gen)
    {
        String ret="";
        if(gen!=null)
        {
            SemanticObject sobj=gen.getSemanticObject();
            SemanticProperty prop=sobj.getSemanticClass().getDisplayNameProperty();
            if(prop!=null)
            {
                ret=sobj.getPropertyIndexData(prop);
            }
        }
        return ret;
    }

    public String getIndexDescription(Searchable gen)
    {
        String ret="";
        if(gen!=null)
        {
            SemanticObject sobj=gen.getSemanticObject();
            if(gen instanceof Descriptiveable)
            {
                SemanticProperty prop=Descriptiveable.swb_description;
                if(prop!=null)
                {
                    ret=sobj.getPropertyIndexData(prop);
                }
            }
        }
        return ret;
    }

    public String getIndexTags(Searchable gen)
    {
        String ret="";
        if(gen!=null)
        {
            SemanticObject sobj=gen.getSemanticObject();
            if(gen instanceof Tagable)
            {
                SemanticProperty prop=Tagable.swb_tags;
                if(prop!=null)
                {
                    ret=sobj.getPropertyIndexData(prop);
                }
            }
        }
        return ret;
    }

    public String getIndexData(Searchable gen)
    {
        return "";
    }

    public String getIndexClass(Searchable gen)
    {
        String ret="";
        SemanticClass cls=gen.getSemanticObject().getSemanticClass();
        if(cls!=null)
        {
            ret+=cls.getPrefix()+cls.getClassName();
        }
        Iterator<SemanticClass> it=cls.listSuperClasses();
        while (it.hasNext())
        {
            SemanticClass semanticClass = it.next();
            ret+=" "+semanticClass.getPrefix()+semanticClass.getClassName();
        }
        return ret;
    }

    private String _getIndexCategory(Searchable gen)
    {
        String ret=gen.getId();
        SemanticObject obj=gen.getSemanticObject();
        Iterator<SemanticObject> it=obj.listHerarquicalParents();
        if(it.hasNext())
        {
            SemanticObject parent=it.next();
            if(parent.instanceOf(Searchable.swb_Searchable))
            {
                ret=_getIndexCategory((Searchable)parent.createGenericInstance())+" "+ret;
            }
        }
        return ret;
    }

    public String getIndexCategory(Searchable gen)
    {
        return _getIndexCategory(gen);
    }

    //************************ No Indexado ***********************************

    public String getTitle(Searchable gen, String lang)
    {
        return gen.getSemanticObject().getDisplayName(lang);
    }
    
    public String getPath(Searchable gen, String lang)
    {
        String ret = null;
        
        if(gen instanceof WebPage)
        {
            HashMap arg = new HashMap();
            arg.put("separator", " | ");
            arg.put("links", "false");
            arg.put("language", lang);
            WebPage page=(WebPage)gen;
            ret=page.getPath(arg);
        }
        
        return ret;
    }



    public String getSummary(Searchable gen, String lang)
    {
        return null;
    }

    public String getImage(Searchable gen)
    {
        return null;
    }

    public String getUrl(Searchable gen)
    {
        String ret=null;
        if(gen instanceof WebPage)
        {
            WebPage page=(WebPage)gen;
            ret=page.getUrl();
        }
        return ret;
    }

    public String getType(Searchable gen)
    {
        return "Generic";
    }


}
