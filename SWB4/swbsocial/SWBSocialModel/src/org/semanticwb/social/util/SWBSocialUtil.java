/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.util;

import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBModel;
import org.semanticwb.social.WordsToMonitor;

/**
 *
 * @author jorge.jimenez
 */
public class SWBSocialUtil {

      /**
     * Holds a reference to a log utility.
     * <p>Mantiene una referencia a la utiler&iacute;a de generaci&oacute;n de bit&aacute;coras.</p>
     */
    private static Logger log = SWBUtils.getLogger(SWBSocialUtil.class);
    /**
     * Holds a reference to an object of this class.
     * <p>Mantiene una referencia a un objeto de esta clase.</p>
     */
    static private SWBSocialUtil instance;

     /**
     * Creates a new object of this class.
     */
    private SWBSocialUtil()
    {
        init();
    }

    /*
     * Initializes the class variables needed to provide this object's services
     * <p>Inicializa las variables de clase necesarias para proveer los servicios de este objeto.</p>
     */
    /**
     * Inits the.
     */
    private void init()
    {

    }

    /**
     * Retrieves a reference to the only one existing object of this class.
     * <p>Obtiene una referencia al &uacute;nico objeto existente de esta clase.</p>
     * @param applicationPath a string representing the path for this application
     * @return a reference to the only one existing object of this class
     */
    static public synchronized SWBSocialUtil createInstance()
    {
        if (SWBSocialUtil.instance == null)
        {
            SWBSocialUtil.instance = new SWBSocialUtil();
        }
        return SWBSocialUtil.instance;
    }

     public static class words2Monitor {

         /*
          * Regresa arrglo de string de todas las palabras (separadas x punto y coma(;))
          * en el campo de palabras de la "Empresa" en todos los objetos de tipo WordsToMonitor
          */
         public static String[] getCompanyWords(SWBModel model)
         {
            String words=null;
            String[] awords=null;
            Iterator <WordsToMonitor> itWords2Monitor=WordsToMonitor.ClassMgr.listWordsToMonitors();
            while(itWords2Monitor.hasNext())
            {
                WordsToMonitor words2monitor=itWords2Monitor.next();
                String wordsTmp=words2monitor.getCompany();
                if(wordsTmp!=null){
                    if(words==null) words=wordsTmp;
                    else words+= wordsTmp;
                }
            }
            if(words!=null) awords=words.split("\\;");
            return awords;
         }

         /*
          * Regresa arrglo de string de todas las palabras (separadas x punto y coma(;))
          * en el campo de palabras de la "Competencia" en todos los objetos de tipo WordsToMonitor
          */
         public static String[] getCompetitionWords(SWBModel model)
         {
            String words=null;
            String[] awords=null;
            Iterator <WordsToMonitor> itWords2Monitor=WordsToMonitor.ClassMgr.listWordsToMonitors();
            while(itWords2Monitor.hasNext())
            {
                WordsToMonitor words2monitor=itWords2Monitor.next();
                String wordsTmp=words2monitor.getCompetition();
                if(wordsTmp!=null){
                    if(words==null) words=wordsTmp;
                    else words+= wordsTmp;
                }
            }
            if(words!=null) awords=words.split("\\;");
            return awords;
         }

         /*
          * Regresa arrglo de string de todas las palabras (separadas x punto y coma(;))
          * en el campo de palabras de "Productos y Servicios" en todos los objetos de tipo WordsToMonitor
          */
         public static String[] getProductAndServicesWords(SWBModel model)
         {
            String words=null;
            String[] awords=null;
            Iterator <WordsToMonitor> itWords2Monitor=WordsToMonitor.ClassMgr.listWordsToMonitors();
            while(itWords2Monitor.hasNext())
            {
                WordsToMonitor words2monitor=itWords2Monitor.next();
                String wordsTmp=words2monitor.getProductsAndServices();
                if(wordsTmp!=null){
                    if(words==null) words=wordsTmp;
                    else words+= wordsTmp;
                }
            }
            if(words!=null) awords=words.split("\\;");
            return awords;
         }

         /*
          * Regresa arrglo de string de todas las palabras (separadas x punto y coma(;))
          * en el campo de palabras de "Otras Palabras" en todos los objetos de tipo WordsToMonitor
          */
         public static String[] getOtherWords(SWBModel model)
         {
            String words=null;
            String[] awords=null;
            Iterator <WordsToMonitor> itWords2Monitor=WordsToMonitor.ClassMgr.listWordsToMonitors();
            while(itWords2Monitor.hasNext())
            {
                WordsToMonitor words2monitor=itWords2Monitor.next();
                String wordsTmp=words2monitor.getOtherWords();
                if(wordsTmp!=null){
                    if(words==null) words=wordsTmp;
                    else words+= wordsTmp;
                }
            }
            if(words!=null) awords=words.split("\\;");
            return awords;
         }


        public static String getWords2Monitor(String delimiter, SWBModel model)
        {
                //Palabras acerca de la compañia
                String words2monitor="";
                String[] companyWords=getCompanyWords(model);
                for(int i=0;i<companyWords.length;i++)
                {
                    System.out.println("companyWord["+i+"]:"+companyWords[i]);
                    if(words2monitor.length()==0) words2monitor=companyWords[i];
                    else words2monitor+=delimiter+companyWords[i];
                }
                //Palabras acerca de la competencia
                String[] competitionWords=getCompetitionWords(model);
                for(int i=0;i<competitionWords.length;i++)
                {
                    System.out.println("competitionWords["+i+"]:"+competitionWords[i]);
                    if(words2monitor.length()==0) words2monitor=competitionWords[i];
                    else words2monitor+=delimiter+competitionWords[i];
                }
                //Palabras acerca de productos y servicios
                String[] pAndServWords=getProductAndServicesWords(model);
                for(int i=0;i<pAndServWords.length;i++)
                {
                    System.out.println("pAndServWords["+i+"]:"+pAndServWords[i]);
                    if(words2monitor.length()==0) words2monitor=pAndServWords[i];
                    else words2monitor+=delimiter+pAndServWords[i];
                }
                //Palabras acerca de productos y servicios
                String[] otherWords=getOtherWords(model);
                for(int i=0;i<otherWords.length;i++)
                {
                    System.out.println("otherWords["+i+"]:"+otherWords[i]);
                    if(words2monitor.length()==0) words2monitor=otherWords[i];
                    else words2monitor+=delimiter+otherWords[i];
                }
                return words2monitor;
        }
    
     }

}
