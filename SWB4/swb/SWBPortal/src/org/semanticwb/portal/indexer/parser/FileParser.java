/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.indexer.parser;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.User;
import org.semanticwb.portal.indexer.FileSearchWrapper;
import org.semanticwb.portal.indexer.IndexTerm;
import org.semanticwb.portal.indexer.SWBIndexer;
import org.semanticwb.portal.services.DocumentExtractorSrv;

/**
 *
 * @author javier.solis.g
 */
public class FileParser extends GenericParser {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(FileParser.class);

    @Override
    public boolean canIndex(Searchable gen) {
        if (gen instanceof FileSearchWrapper) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canUserView(Searchable gen, User user) {
        Searchable page = ((FileSearchWrapper) gen).getSearchable();
        if (page == null) {
            return false;
        }
        return user.haveAccess(page);
    }

    @Override
    public String getImage(Searchable gen) {
        //Implementar de acuerdo a extensioned de archivos
        return null;
    }

    @Override
    public String getIndexCategory(Searchable gen) {
        Searchable page = ((FileSearchWrapper) gen).getSearchable();
        if (page == null) {
            return null;
        }else
        {
            if(page instanceof Resource)
            {
                Resourceable res=((Resource)page).getResourceable();
                if(res instanceof Searchable)
                {
                    page=(Searchable)res;
                }
            }
        }
        String ret=super.getIndexCategory(page);
        //System.out.println("getIndexCategory:"+ret+" "+gen);
        return ret;
    }

    @Override
    public String getIndexClass(Searchable gen) {
        return "FileSearchWrapper";
    }

    /** Obtiene el texto de documentos Word, Excel, PPT, PDF y TXT únicamente.
 *
 * 
 */
    @Override
    public String getIndexData(Searchable gen) {
        //Implementar validar por tipo de archivo

        //System.out.println("FilePARSER....");
        FileSearchWrapper fsw = (FileSearchWrapper) gen;
        File f = fsw.getFile();
        String nombre = f.getName();
        String docdata = null;
        
        //System.out.println("Nombre archivo..."+nombre);
        
        if (nombre != null && nombre.lastIndexOf(".") > -1) {
            String ftype = nombre.substring(nombre.lastIndexOf(".") + 1);
            DocumentExtractorSrv dxsrv = new DocumentExtractorSrv();
            try {
                if (ftype.toLowerCase().equals("txt")) {
                    docdata = dxsrv.TxtExtractor(f);
                } else if (ftype.toLowerCase().equals("pdf")) {
                    docdata = dxsrv.pdfExtractor(f);
                } else if (ftype.toLowerCase().equals("doc") || ftype.toLowerCase().equals("docx")) {
                    docdata = dxsrv.WordExtractor(f,ftype);
                } else if (ftype.toLowerCase().equals("xls") || ftype.toLowerCase().equals("xlsx")) {
                    docdata = dxsrv.ExcelExtractor(f,ftype);
                } else if (ftype.toLowerCase().equals("ppt") || ftype.toLowerCase().equals("pptx")) {
                    docdata = dxsrv.PPTExtractor(f,ftype);
                }
            } catch (Exception e) {
                log.error("Error al obtener el texto del documento. FileParser",e);
            }
        }
        return docdata;
    }

    @Override
    public String getIndexDescription(Searchable gen) {
        return ((FileSearchWrapper) gen).getDescription();
    }

    @Override
    public String getIndexLastUpdated(Searchable gen) {
        return new Date(((FileSearchWrapper) gen).getFile().lastModified()).toString();
    }

    @Override
    public String getIndexModel(Searchable gen) {
        Searchable page = ((FileSearchWrapper) gen).getSearchable();
        if (page == null) {
            return null;
        }
        return ((FileSearchWrapper) gen).getModelId();
    }

    @Override
    public String getIndexTags(Searchable gen) {
        return ((FileSearchWrapper) gen).getTags();
    }

    @Override
    public String getIndexTitle(Searchable gen) {
        return ((FileSearchWrapper) gen).getTitle();
    }

    public Map<String, IndexTerm> getIndexTerms(Searchable gen) {
        HashMap<String, IndexTerm> map = new HashMap();

        map.put(SWBIndexer.ATT_URI, new IndexTerm(SWBIndexer.ATT_URI, gen.getURI(), true, IndexTerm.INDEXED_NO_ANALYZED));
        map.put(SWBIndexer.ATT_CLASS, new IndexTerm(SWBIndexer.ATT_CLASS, getIndexClass(gen), false, IndexTerm.INDEXED_TOKENIZED_NO_ANALYZED));
        map.put(SWBIndexer.ATT_CATEGORY, new IndexTerm(SWBIndexer.ATT_CATEGORY, getIndexCategory(gen), false, IndexTerm.INDEXED_TOKENIZED_NO_ANALYZED));
        map.put(SWBIndexer.ATT_MODEL, new IndexTerm(SWBIndexer.ATT_MODEL, getIndexModel(gen), false, IndexTerm.INDEXED_ANALYZED));
        map.put(SWBIndexer.ATT_TITLE, new IndexTerm(SWBIndexer.ATT_TITLE, getIndexTitle(gen), true, IndexTerm.INDEXED_ANALYZED));
        map.put(SWBIndexer.ATT_DESCRIPTION, new IndexTerm(SWBIndexer.ATT_DESCRIPTION, getIndexDescription(gen), true, IndexTerm.INDEXED_ANALYZED));
        map.put(SWBIndexer.ATT_TAGS, new IndexTerm(SWBIndexer.ATT_TAGS, getIndexTags(gen), true, IndexTerm.INDEXED_ANALYZED));
        map.put(SWBIndexer.ATT_UPDATED, new IndexTerm(SWBIndexer.ATT_UPDATED, getIndexLastUpdated(gen), true, IndexTerm.INDEXED_NO_ANALYZED));
        map.put(SWBIndexer.ATT_URL, new IndexTerm(SWBIndexer.ATT_URL, getUrl(gen), true, IndexTerm.INDEXED_NO_ANALYZED));
        map.put(SWBIndexer.ATT_DATA, new IndexTerm(SWBIndexer.ATT_DATA, getIndexData(gen), false, IndexTerm.INDEXED_ANALYZED));
        map.put("wuri", new IndexTerm("wuri", ((FileSearchWrapper) gen).getSearchable().getURI(), true, IndexTerm.INDEXED_NO_ANALYZED));
        return map;
    }

    @Override
    public String getPath(Searchable gen, String lang) {
        return super.getPath(((FileSearchWrapper) gen).getSearchable(), lang);
    }

    @Override
    public String getSummary(Searchable gen, String lang) {
        //System.out.println("fparser summary:"+((FileSearchWrapper) gen).getDescription());
        return ((FileSearchWrapper) gen).getDescription();
    }

    @Override
    public String getTitle(Searchable gen, String lang) {
        //System.out.println("fparser titulo:"+((FileSearchWrapper) gen).getTitle());
        return ((FileSearchWrapper) gen).getTitle();
    }

    @Override
    public String getType(Searchable gen) {
        return "File";
    }

    @Override
    public String getTypeDisplayLabel(Searchable gen) {
        return "File";
    }

    @Override
    public String getUpdated(Searchable gen) {
        return new Date(((FileSearchWrapper) gen).getFile().lastModified()).toString();
    }

    @Override
    public String getUrl(Searchable gen) {
        return ((FileSearchWrapper) gen).getUrl();
    }
}
