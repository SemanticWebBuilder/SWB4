/**
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
*
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
* del SemanticWebBuilder 4.0.
*
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
* de la misma.
*
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
* dirección electrónica:
*  http://www.semanticwebbuilder.org
**/

/*
 * SWBIndexer.java
 *
 * Created on 11 de mayo de 2006, 06:57 PM
 */

package org.semanticwb.portal.indexer;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDF;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.indexer.parser.GenericParser;
import org.semanticwb.portal.indexer.parser.ResourceParser;
import org.semanticwb.portal.indexer.parser.WebPageParser;
import org.semanticwb.portal.indexer.searcher.SearchQuery;
import org.semanticwb.portal.indexer.searcher.SearchResults;

/**
 *
 * @author Javier Solis Gonzalez
 */
public abstract class SWBIndexer
{
    private static Logger log=SWBUtils.getLogger(SWBIndexer.class);

    public static final String ATT_URI="uri";
    public static final String ATT_CLASS="class";
    public static final String ATT_CATEGORY="category";
    public static final String ATT_MODEL="model";
    public static final String ATT_TITLE="title";
    public static final String ATT_DESCRIPTION="description";
    public static final String ATT_TAGS="tags";
    public static final String ATT_DATA="data";

    public static final String ATT_SUMMARY="summary";
    public static final String ATT_URL="url";

    private Properties props = null;
    private String name = null;
    private Timer timer=null;

    private List<Searchable> m_add=null;
    private List<String> m_remove=null;
    private HashMap<Class, GenericParser> m_parsers=null;
    private GenericParser m_gen=new GenericParser();

    private static HashMap<String,IndexTerm> m_noAnalyzedTerms=new HashMap();

    public abstract void init();

    public abstract void reset();
    public abstract void remove();
    public abstract void unLock() throws IOException;
    public abstract boolean isLocked() throws IOException;
    public abstract void optimize();
    protected abstract void createIndex();
    public abstract String getIndexPath();

    public abstract void removeModel(String modelid);
    public abstract SearchResults search(SearchQuery query, User user);
    protected abstract void removeSearchableObj(String uri);
    protected abstract void writeSearchableObj(Searchable obj);

    /** Creates a new instance of WBIndexer */
    public SWBIndexer()
    {
    }

    protected void init(String name, Properties props) {
        this.props = props;
        this.name = name;

        m_add=Collections.synchronizedList(new LinkedList());
        m_remove=Collections.synchronizedList(new LinkedList());
        m_parsers=new HashMap();

        int delays=Integer.parseInt(props.getProperty("delay","30"));
        //System.out.println("delays:"+delays);

        TimerTask t=new TimerTask(){
            public void run()
            {
                _run();
            }
        };
        timer = new Timer();
        timer.schedule(t, delays*1000, delays*1000);

        init();

//        m_noAnalyzedTerms=new HashMap();
//        terms.put(ATT_URI, new IndexTerm(ATT_URI,true,IndexTerm.INDEXED_NO_ANALYZED));
//        terms.put(ATT_CLASS, new IndexTerm(ATT_CLASS,false,IndexTerm.INDEXED_NO_ANALYZED));
//        terms.put(ATT_CATEGORY, new IndexTerm(ATT_CATEGORY,false,IndexTerm.INDEXED_NO_ANALYZED));
//        terms.put(ATT_MODEL, new IndexTerm(ATT_MODEL,false,IndexTerm.INDEXED_NO_ANALYZED));
//        terms.put(ATT_TITLE, new IndexTerm(ATT_TITLE,false,IndexTerm.INDEXED_NO_ANALYZED));
//        terms.put(ATT_DESCRIPTION, new IndexTerm(ATT_DESCRIPTION,false,IndexTerm.INDEXED_NO_ANALYZED));
//        terms.put(ATT_TAGS, new IndexTerm(ATT_TAGS,false,IndexTerm.INDEXED_NO_ANALYZED));
//        terms.put(ATT_DATA, new IndexTerm(ATT_DATA,false,IndexTerm.INDEXED_NO_ANALYZED));

        registerParser(Resource.class, new ResourceParser());
        registerParser(WebPage.class, new WebPageParser());
    }

    public static void addNoAnalyzedIndexTerm(IndexTerm term)
    {
        m_noAnalyzedTerms.put(term.getField(),term);
    }

    public static void removeNoAnalyzedIndexTerm(String field)
    {
        m_noAnalyzedTerms.remove(field);
    }

    public static boolean containsNoAnalyzedIndexTerm(String field)
    {
        return m_noAnalyzedTerms.containsKey(field);
    }

    public void registerParser(Class cls, GenericParser parser)
    {
        m_parsers.put(cls, parser);
    }

    public GenericParser getParser(Searchable obj)
    {
        GenericParser ret=null;
        ret=m_parsers.get(obj.getClass());
        if(ret==null)
        {
            Iterator<Class> it=m_parsers.keySet().iterator();
            while (it.hasNext())
            {
                Class cls = it.next();
                if(cls.isInstance(obj))
                {
                    ret=m_parsers.get(cls);
                }
            }
        }
        if(ret==null)ret=m_gen;
        return ret;
    }

    protected void removeRun()
    {
        while(m_remove.size()>0)
        {
            String obj=m_remove.remove(0);
            //System.out.println("SWBIndexObj:"+obj);
            try
            {
                removeSearchableObj(obj);
            }catch(Throwable t)
            {
                log.error(t);
            }
        }
    }

    protected void writeRun()
    {
        int z=m_add.size();  //Para sono indexar los que ya traia en la pila
        while(m_add.size()>0 && z>0)
        {
            Searchable obj=m_add.remove(0);
            //System.out.println("SWBIndexObj:"+obj);
            try
            {
                writeSearchableObj(obj);
            }catch(Throwable t)
            {
                log.error(t);
            }
            z--;
        }
    }

    protected void _run()
    {
        log.debug("indexer:"+name+" is running...");
    }

    /**
     * Getter for property name.
     * @return Value of property name.
     */
    public java.lang.String getName()
    {
        return name;
    }

    /**
     * Getter for property props.
     * @return Value of property props.
     */
    public java.util.Properties getProperties()
    {
        return props;
    }

    public void indexModel(String modelid)
    {
        //System.out.println("indexModel:"+modelid);

        SemanticModel model=SWBPlatform.getSemanticMgr().getModel(modelid);
        if(model!=null)
        {
            Iterator<Statement> it=model.getRDFModel().listStatements(null, RDF.type, (RDFNode)null);
            while(it.hasNext())
            {
                Statement st=it.next();
                SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(st.getResource().getURI());
                //out.println(cls);
                if(cls.isSubClass(Searchable.swb_Searchable))
                {
                    SemanticObject obj=SemanticObject.createSemanticObject(st.getSubject());
                    indexSerchable((Searchable)obj.createGenericInstance());
                    //out.println("searchable:"+obj);
                }
            }
        }
    }

    public void removeSearchable(String uri)
    {
        m_remove.add(uri);
    }

    public void indexSerchable(Searchable serchable)
    {
        if(!m_add.contains(serchable) && getParser(serchable).canIndex(serchable))
        {
            //m_remove.add(serchable.getURI());
            m_add.add(serchable);
        }
    }

    public int getIndexSize()
    {
        return m_add.size()+m_remove.size();
    }

}
