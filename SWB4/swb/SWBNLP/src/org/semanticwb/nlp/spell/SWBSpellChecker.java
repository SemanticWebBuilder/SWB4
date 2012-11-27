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
package org.semanticwb.nlp.spell;

import java.io.File;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * Spell checker for SemanticWebBuilder resources. This class is a wrapper for
 * Lucene {@link SpellChecker} class.
 * <p>
 * Corrector ortográfico para algunos recursos de SemanticWebBuilder. Esta clase
 * es un envoltorio para la clase {@link SpellChecker} de Lucene.
 *
 * @see org.apache.lucene.search.spell.SpellChecker
 * 
 * @author Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
 */
public class SWBSpellChecker {
    /**Lucene SpellChecker object*/
    private SpellChecker checker = null;
    /**Lucene Dictionary to store words for spelling suggestions*/
    private LuceneDictionary spellDict = null;
    /**Directory to store spell indexes*/
    private Directory spellDir = null;
    /**Number of max suggestions to retrieve*/
    private int numSug = 5;

    private static Logger log = SWBUtils.getLogger(SWBSpellChecker.class);

    /**
     * Creates a new instance of a {@link SWBSpellChecker}.
     * Builds a Lucene {@code SpellChecker} using a {@link Directory}
     * containing the display name of Semantic Classes and Semantic Properties.
     * <p>
     * Crea una nueva instancia de {@link SWBSpellChecker}.
     * Construye un corrector ortográfico a partir de un Directorio de Lucene
     * que contiene los display names de las clases y propiedades semánticas.
     *
     * @param directoryPath path of the Lucene {@link Directory} to extract
     *                      words from. Ruta del directorio de Lucene del cual
     *                      se extraerán las palabras.
     * 
     * @param fieldName     name of the Lucene field to take words from. Nombre
     *                      del campo de interés en el directorio.
     */
    public SWBSpellChecker(String directoryPath, String fieldName) {
        indexSpellDir(directoryPath, fieldName);
    }

    /**
     * Creates a new instance of a {@link SWBSpellChecker}.
     * Builds a Lucene {@link SpellChecker} using a text file containing the 
     * display name of Semantic Classes and Semantic Properties.
     * <p>
     * Crea una nueva instancia de un {@link SWBSpellChecker}.
     * Construye un corrector ortográfico a partir de un archivo de texto que
     * contiene los display names de las clases y propiedades semánticas.
     *
     * @param txtDictFilePath path of the text file to extract words from. Ruta
     * del archivo de texto que contiene el vocabulario.
     */
    public SWBSpellChecker(String txtDictFilePath) {
        File f = new File(txtDictFilePath);

        if (f.exists()) {
            indexSpellTextFile(f);
        }
    }

    /**
     * Creates and indexes a new {@link Directory} for spell checking.
     * Uses an existing Lucene {@link Directory} to create the spelling
     * index.
     * <p>
     * Crea e indexa un nuevo directorio de Lucene para efectuar la corrección
     * ortográfica. Usa un directorio existente para crear los índices de
     * corrección.
     *
     * @param dirPath   path of the original index directory. Ruta al directorio
     *                  de índices original.
     * 
     * @param fieldName name of the field in the spell index directory. Nombre
     *                  del nuevo campo en el directorio de índices de corrección.
     */
    public void indexSpellDir(String dirPath, String fieldName) {
        try {
            spellDir = new RAMDirectory();
            checker = new SpellChecker(spellDir);
            spellDict = new LuceneDictionary(IndexReader.open(FSDirectory.open(new File(dirPath))), fieldName);
            checker.indexDictionary(spellDict, new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36)), false);
        } catch (Exception ex) {
           log.error(ex);
        }
    }

    /**
     * Creates and indexes a new {@link Directory} for spell checking.
     * This method uses an existing text file to create the spell dictionary.
     * <p>
     * Crea e indexa un nuevo directorio de Lucene para efectuar la corrección
     * ortográfica. Usa un archivo de texto existente para crear los índices de
     * corrección.
     *
     * @param txtDictFile   path of the text file containing the spelling dictionary.
     *                      ruta del archivo de texto que contiene el diccionario
     *                      de corrección.
     */
    public void indexSpellTextFile(File txtDictFile) {
         try {
            spellDir = new RAMDirectory();
            checker = new SpellChecker(spellDir);
            checker.indexDictionary(new PlainTextDictionary(txtDictFile), new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36)), false);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    /**
     * Gets the number of suggestions to obtain from the {@link SWBSpellChecker}.
     * <p>
     * Obtiene el número de sugerencias a obtener de la corrección ortográfica.
     */
    public int getNumSug() {
        return numSug;
    }

    /**
     * Sets the number of suggestions to obtain from the {@link SWBSpellChecker}.
     * <p>
     * Establece el número de sugerencias a obtener de la corrección ortográfica.
     *
     * @param numSug number of suggestions. Número de sugerencias.
     *
     */
    public void setNumSug(int numSug) {
        this.numSug = numSug;
    }    

    /**
     * Gets a set of words similar to the given {@code input}.
     * <p>
     * Obtiene un conjunto de palabras similares a la cadena de entrada.
     * 
     * @param word  word to have spell check done on. Palabra sobre la que se
     *              realizará la corrección ortográfica.
     * 
     * @return      Set of words similar to the {@code input} string or null if
     *              no similar words exist. Conjunto de palabras similares a la
     *              cadena de entrada o null si no existen palabras similares.
     */
    public String[] suggestSimilar(String word) {
        if (checker != null) {
            try {
                if (checker.exist(word)) {
                    return null;
                }
                String res[] = checker.suggestSimilar(word, numSug);
                return res;
            } catch (Exception ex) {
                log.error(ex);
            }
        }
        return null;
    }
}