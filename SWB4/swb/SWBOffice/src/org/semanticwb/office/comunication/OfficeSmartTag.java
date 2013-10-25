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
package org.semanticwb.office.comunication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Searchable;
import org.semanticwb.office.interfaces.IOfficeSmartTag;
import org.semanticwb.office.interfaces.ObjecInfo;
import org.semanticwb.office.interfaces.PropertyObjectInfo;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.indexer.SWBIndexer;
import org.semanticwb.portal.indexer.searcher.SearchDocument;
import org.semanticwb.portal.indexer.searcher.SearchQuery;
import org.semanticwb.portal.indexer.searcher.SearchResults;
import org.semanticwb.portal.indexer.searcher.SearchTerm;
import org.semanticwb.xmlrpc.XmlRpcObject;

// TODO: Auto-generated Javadoc
/**
 * The Class OfficeSmartTag.
 * 
 * @author victor.lorenzana
 */
public class OfficeSmartTag extends XmlRpcObject implements IOfficeSmartTag
{

    /** The log. */
    private static Logger log = SWBUtils.getLogger(OfficeSmartTag.class);
    
    /** The prefix statement. */
    private StringBuilder prefixStatement = new StringBuilder("");
    
    /** The Constant NL. */
    private static final String NL = System.getProperty("line.separator");
    
    /** The Constant preps. */
    private static final String[] preps;//= {"a", "ante", "bajo", "con", "contra", "de", "desde", "para", "sin", "sobre", "tras"};

    static {
        ArrayList<String> norelevant = new ArrayList<String>();
        InputStream in = OfficeSmartTag.class.getResourceAsStream("nonrelevant_es.txt");
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(in));
        try {
            String word = br.readLine();
            while (word != null) {
                word = word.trim();
                if (!word.startsWith("#")) {
                    norelevant.add(word);
                }
                word = br.readLine();
            }
        }
        catch (Exception e) {
            log.error(e);
        }

        preps = norelevant.toArray(new String[norelevant.size()]);
    }

    /**
     * Search.
     * 
     * @param text the text
     * @return the objec info[]
     * @throws Exception the exception
     */
    public ObjecInfo[] search(String text) throws Exception
    {
        ArrayList<ObjecInfo> search = new ArrayList<ObjecInfo>();
        Iterator<SemanticObject> result = searchObject(text);
        while (result.hasNext()) {
            SemanticObject obj = result.next();
            ObjecInfo info = new ObjecInfo();
            info.uri = obj.getURI();
            if (obj.getSemanticClass().getLabel("es") == null) {
                info.name = obj.getSemanticClass().getName() + ":" + obj.getDisplayName();
            }
            else {
                info.name = obj.getSemanticClass().getLabel("es") + ":" + obj.getDisplayName();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            ArrayList<PropertyObjectInfo> properties = new ArrayList<PropertyObjectInfo>();
            Iterator<SemanticProperty> props = obj.getSemanticClass().listProperties();
            while (props.hasNext()) {
                SemanticProperty prop = props.next();
                if (prop.getDisplayProperty() != null && prop.isDataTypeProperty()) {
                    PropertyObjectInfo propInfo = new PropertyObjectInfo();
                    propInfo.uri = prop.getURI();
                    propInfo.title = prop.getLabel();

                    SemanticLiteral literal = obj.getLiteralProperty(prop);
                    if (literal != null) {
                        if (prop.isDate()) {
                            propInfo.value = dateFormat.format(literal.getDate());
                        }
                        else if (prop.isDateTime()) {
                            propInfo.value = dateTimeFormat.format(new java.util.Date(literal.getDateTime().getTime()));
                        }
                        else if (prop.isBoolean()) {
                            propInfo.value = literal.getBoolean() ? "Sí" : "No";
                        }
                        else if (prop.isBinary()) {
                        }
                        else {
                            propInfo.value = literal.getString();
                        }
                    }
                    properties.add(propInfo);
                }
            }
            info.properties = properties.toArray(new PropertyObjectInfo[properties.size()]);
            search.add(info);
        }
        return search.toArray(new ObjecInfo[search.size()]);
    }

    /**
     * Search object.
     * 
     * @param text the text
     * @return the iterator
     */
    private Iterator<SemanticObject> searchObject(String text)
    {
        ArrayList<SemanticObject> searchObject = new ArrayList<SemanticObject>();
        SearchQuery query = new SearchQuery();
        SearchQuery tquery = new SearchQuery(SearchQuery.OPER_AND);
        query.addQuery(tquery);

        tquery.addTerm(new SearchTerm(SWBIndexer.ATT_TITLE, text, SearchTerm.OPER_OR));
        tquery.addTerm(new SearchTerm(SWBIndexer.ATT_DESCRIPTION, text, SearchTerm.OPER_OR));
        tquery.addTerm(new SearchTerm(SWBIndexer.ATT_TAGS, text, SearchTerm.OPER_OR));
        tquery.addTerm(new SearchTerm(SWBIndexer.ATT_DATA, text, SearchTerm.OPER_OR));
        tquery.addTerm(new SearchTerm(SWBIndexer.ATT_CLASS, text, SearchTerm.OPER_OR));

        SWBIndexer indexer = SWBPortal.getIndexMgr().getDefaultIndexer();
        if (indexer != null) {
            SearchResults results = indexer.search(query, null);
            Iterator<SearchDocument> it = results.listDocuments();
            while (it.hasNext()) {
                SearchDocument obj = it.next();
                Searchable srch = obj.getSearchable();
                searchObject.add(srch.getSemanticObject());
            }
        }
        
        return searchObject.iterator();
    }

    /**
     * Checks if is smart tag.
     * 
     * @param text the text
     * @return true, if is smart tag
     * @throws Exception the exception
     */
    public boolean isSmartTag(String text) throws Exception
    {
        if (isRelevantWord(text)) {
            return searchObject(text).hasNext();


        }
        return false;


    }

    /**
     * Checks if is relevant word.
     * 
     * @param token the token
     * @return true, if is relevant word
     */
    private boolean isRelevantWord(String token)
    {
        if (token == null) {
            return false;


        }
        token = token.trim();


        if (token.length() == 1) {
            return false;


        }
        boolean isRelevantWord = true;


        for (String prep : preps) {
            if (prep.equalsIgnoreCase(token)) {
                return false;


            }
        }
        return isRelevantWord;


    }

    /**
     * Pre proccess token.
     * 
     * @param token the token
     * @return the string
     */
    private String preProccessToken(String token)
    {
        return token;


    }

    /**
     * Gets the tokens.
     * 
     * @param text the text
     * @return the tokens
     * @throws Exception the exception
     */
    @Override
    public String[] getTokens(String text) throws Exception
    {
        ArrayList<String> getTokens = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(text, " ");


        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            token = preProccessToken(token);



            if (isRelevantWord(token)) {
                getTokens.add(token);


            }
        }
        return getTokens.toArray(new String[getTokens.size()]);

    }
}
