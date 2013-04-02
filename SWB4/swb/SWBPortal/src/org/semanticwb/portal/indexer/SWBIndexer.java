/*
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
 */
package org.semanticwb.portal.indexer;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDF;
import java.io.IOException;
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
import org.semanticwb.portal.indexer.parser.FileParser;
import org.semanticwb.portal.indexer.parser.GenericParser;
import org.semanticwb.portal.indexer.parser.ResourceParser;
import org.semanticwb.portal.indexer.parser.UserParser;
import org.semanticwb.portal.indexer.parser.WebPageParser;
import org.semanticwb.portal.indexer.searcher.SearchQuery;
import org.semanticwb.portal.indexer.searcher.SearchResults;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBIndexer.
 * 
 * @author Javier Solis Gonzalez
 */
public abstract class SWBIndexer
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(SWBIndexer.class);

    /**Field name for the URI of a {@link Searchable} object.*/
    public static final String ATT_URI="uri";
    /**Field name for the Class of a {@link Searchable} object.*/
    public static final String ATT_CLASS="class";
    /**Field name for the hierarchical path of a {@link Searchable} object.*/
    public static final String ATT_CATEGORY="category";
    /**Field name for the {@link SemanticModel} of a {@link Searchable} object.*/
    public static final String ATT_MODEL="model";
    /**Field name for the title of a {@link Searchable} object.*/
    public static final String ATT_TITLE="title";
    /**Field name for the description of a {@link Searchable} object.*/
    public static final String ATT_DESCRIPTION="description";
    /**Field name for the tags of a {@link Searchable} object.*/
    public static final String ATT_TAGS="tags";
    /**Field name for the data of a {@link Searchable} object.*/
    public static final String ATT_DATA="data";
    /**Field name for the summary of a {@link Searchable} object.*/
    public static final String ATT_SUMMARY="summary";
    /**Field name for the URL of a {@link Searchable} object.*/
    public static final String ATT_URL="url";
    /**Field name for reverse ordering of {@link Searchable} objects.*/
    public static final String ATT_INV="inv:";
    /**Order field for the last updated date of {@link Searchable} objects.*/
    public static final String ATT_UPDATED="updated";
    /**Order field for the score of {@link Searchable} objects.*/
    public static final String ATT_SCORE="score";

    /** The props. */
    private Properties props = null;
    
    /** The name. */
    private String name = null;
    
    /** The timer. */
    private Timer timer=null;

    /**List of {@link Searchable} objects to add to the index.*/
    private List<Searchable> m_add=null;
    /**List of {@link Searchable} objects to remove from the index.*/
    private List<String> m_remove=null;
    /**Map for registered parsers of {@link Searchable} objects.*/
    private HashMap<Class, GenericParser> m_parsers=null;
    /**Default parser instance.*/
    private GenericParser m_gen=new GenericParser();
    /**Map of {@link Indexterm}s that are going to be indexed in a
     * not-analyzed way.*/
    private static HashMap<String,IndexTerm> m_noAnalyzedTerms=new HashMap();

    /**
     * Inits the.
     */
    public abstract void init();

    /**
     * Reset.
     */
    public abstract void reset();
    
    /**
     * Removes the.
     */
    public abstract void remove();
    
    /**
     * Un lock.
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public abstract void unLock() throws IOException;
    
    /**
     * Checks if is locked.
     * 
     * @return true, if is locked
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public abstract boolean isLocked() throws IOException;
    
    /**
     * Optimize.
     */
    public abstract void optimize();
    
    /**
     * Creates the index.
     */
    protected abstract void createIndex();
    
    /**
     * Gets the index path.
     * 
     * @return the index path
     */
    public abstract String getIndexPath();

    /**
     * Removes the model.
     * 
     * @param modelid the modelid
     */
    public abstract void removeModel(String modelid);
    
    /**
     * Search.
     * 
     * @param query the query
     * @param user the user
     * @return the search results
     */
    public abstract SearchResults search(SearchQuery query, User user);

    /**
     * Search.
     * 
     * @param query the query
     * @param user the user
     * @param sortFields the sort fields
     * @return the search results
     */
    public abstract SearchResults search(SearchQuery query, User user, String []sortFields);
    
    /**
     * Removes the {@link Searchable} object with the specified URI from the
     * search index.
     * <p>
     * Elimina el objeto {@link Searchable} con la URI especificada del índice
     * de búsqueda.
     * 
     * @param uri the uri
     */
    protected abstract void removeSearchableObj(String uri);
    
    /**
     * Writes a {@link Searchable} object to the search index.
     * <p>
     * Escribe un objeto {@link Searchable} al índice de búsqueda.
     * 
     * @param obj the {@link Searchable} object. El objeto {@link Searchable}.
     */
    protected abstract void writeSearchableObj(Searchable obj);

    /**
     * Creates a new instance of WBIndexer.
     */
    public SWBIndexer()
    {
    }
    
    /**
     * Inits the.
     * 
     * @param name the name
     * @param props the props
     */
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

        /*Add here the required default not-analyzed fields, for example,
        /to store a term for the uri field without being indexed see the
         following line.*/
        //terms.put(ATT_URI, new IndexTerm(ATT_URI,true,IndexTerm.INDEXED_NO_ANALYZED));

        /*Register the required Searchable parsers. Add here the required
         registerParser sentences for new default parsers.*/
        registerParser(Resource.class, new ResourceParser());
        registerParser(WebPage.class, new WebPageParser());
        registerParser(User.class, new UserParser());
        registerParser(FileSearchWrapper.class, new FileParser());
    }
    

    /**
     * Inits the.
     * 
     * @param name the name
     * @param props the props
     */
    public void init(String name) {
        this.name = name;
        m_parsers=new HashMap();

        init();

        /*Add here the required default not-analyzed fields, for example,
        /to store a term for the uri field without being indexed see the
         following line.*/
        //terms.put(ATT_URI, new IndexTerm(ATT_URI,true,IndexTerm.INDEXED_NO_ANALYZED));

        /*Register the required Searchable parsers. Add here the required
         registerParser sentences for new default parsers.*/
        registerParser(Resource.class, new ResourceParser());
        registerParser(WebPage.class, new WebPageParser());
        registerParser(User.class, new UserParser());
    }

    /**
     * Adds an {@link IndexTerm} to the not-analyzed map.
     * @param term {@link IndexTerm} to be stored but not analyzed.
     */
    public static void addNoAnalyzedIndexTerm(IndexTerm term)
    {
        m_noAnalyzedTerms.put(term.getField(),term);
    }

    /**
     * Removes an {@link IndexTerm} from the not-analyzed map.
     * 
     * @param field the field
     */
    public static void removeNoAnalyzedIndexTerm(String field)
    {
        m_noAnalyzedTerms.remove(field);
    }

    /**
     * Checks wheter an {@link IndexTerm} already exists in the not-analyzed map.
     * 
     * @param field the field
     * @return true, if successful
     */
    public static boolean containsNoAnalyzedIndexTerm(String field)
    {
        return m_noAnalyzedTerms.containsKey(field);
    }

    /**
     * Registers a new {@link Searchable} parser for a given object.
     * @param cls Java class of the {@link Searchable} object.
     * @param parser Parser object for the {@link Searchable} object.
     */
    public void registerParser(Class cls, GenericParser parser)
    {
        m_parsers.put(cls, parser);
    }

    /**
     * Un-Registers the {@link Searchable} parser of a given Class. It
     * implies that the {@link Searchable} objects of the given class will not
     * be indexed anymore until its parser is registered again.
     * @param cls Java class of the {@link Searchable} object.
     * TODO:Checar con Jei la conveniencia de este método
     */
    public void unRegisterParser(Class cls) {
        if (m_parsers.get(cls) != null) {
            m_parsers.remove(cls);
        }
    }

    /**
     * Gets the parser associated to the specified {@link Searchable} object.
     * @param obj {@link Searchable} object to get parser for.
     * @return {@link GenericParser} for the {@link Searchable} object.
     */
    public GenericParser getParser(Searchable obj)
    {
        GenericParser ret=null;
        if(m_parsers!=null)
        {
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
        }
        if(ret==null)ret=m_gen;
        return ret;
    }

    /**Task for removing {@link Searchable} objets from the index.*/
    protected void removeRun()
    {
        if(m_remove!=null)
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
    }

    /**Task for adding {@link Searchable} objets from the index.*/
    protected void writeRun()
    {
        if(m_add!=null)
        {
            int z=m_add.size();  //Para solo indexar los que ya traia en la pila
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
    }

    /**
     * _run.
     */
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

    /**
     * Index a complete model. Index all {@link Searchable} objects that belongs
     * to the model with the specified id.
     * @param modelid ID of the model to index.
     */
    public void indexModel(String modelid)
    {
        //System.out.println("indexModel:"+modelid);

        SemanticModel model=SWBPlatform.getSemanticMgr().getModel(modelid);
        if(model!=null)
        {
            Iterator<Statement> it=model.getRDFModel().listStatements(null, RDF.type, (RDFNode)null);
            while(it != null && it.hasNext())
            {
                Statement st=it.next();
                SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(st.getResource().getURI());
                if(cls != null && cls.isSubClass(Searchable.swb_Searchable))
                {
                    SemanticObject obj=SemanticObject.createSemanticObject(st.getSubject());                    
                    indexSerchable((Searchable)obj.createGenericInstance());
                    //out.println("searchable:"+obj);
                }
            }
        }
    }

    /**
     * Adds a {@link Searchable} object to the remove list for future deletion
     * from the index.
     * @param uri URI of the {@link Searchable} object to remove from the index.
     */
    public void removeSearchable(String uri)
    {
        if(m_remove!=null)m_remove.add(uri);
    }

    /**
     * Adds a {@link Searchable} object to the add list for future indexing
     * in the index.
     * 
     * @param serchable the serchable
     */
    public void indexSerchable(Searchable serchable)
    {
        if(m_add!=null && !m_add.contains(serchable) && getParser(serchable).canIndex(serchable))
        {            
            //m_remove.add(serchable.getURI());
            m_add.add(serchable);
        }
    }

    /**
     * Gets the index size.
     * 
     * @return the index size
     */
    public int getIndexSize()
    {
        if(m_add!=null)
        {
            return m_add.size()+m_remove.size();
        }else
        {
            return 0;
        }
    }

}
