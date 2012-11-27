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
package org.semanticwb.nlp.analysis;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class NgramWordTokenizer {
    public static final int DEFAULT_MIN_NGRAM_SIZE = 1;
    public static final int DEFAULT_MAX_NGRAM_SIZE = 2;
    private String s_inStr;
    private int minWordsInToken;
    private int maxWordsInToken;
    private int gramSize;
    private int inLen;
    private List<Token> chunks;
    private int pos = 0;
    private boolean started = false;
    private String[] listDelimiters = {" ", ","};
    private Reader input;
    
    public NgramWordTokenizer(Reader input) {
        this(input, DEFAULT_MIN_NGRAM_SIZE, DEFAULT_MAX_NGRAM_SIZE);
    }    

    public NgramWordTokenizer(Reader input, int minWords, int maxWords) {
        this.input = input;
        if (minWords < 1) {
            throw new IllegalArgumentException("minWordsInToken must be greater than zero");
        }

        if (minWords > maxWords) {
            throw new IllegalArgumentException("minWordsInToken must not be greater than maxWordsInToken");
        }
        minWordsInToken = minWords;
        maxWordsInToken = maxWords;
    }

    public final Token next(final Token reusableToken) throws IOException {
        assert reusableToken != null;
        if(!started) {
            started = true;            
            gramSize = minWordsInToken;
            char[] chars = new char[1024];
            input.read(chars);
            s_inStr = new String(chars).trim();
            chunks = getChunks();
            inLen = chunks.size();
        }

        if (pos + gramSize > inLen) {
            pos = 0;
            gramSize ++;
            if (gramSize > maxWordsInToken) return null;
            if (pos + gramSize > inLen) return null;
        }

        int tokenStart = chunks.get(pos).getStart();
        int tokenEnd = chunks.get(pos).getEnd();
        String ts = "";
        for (int i = pos; i < pos + gramSize; i++) {
            ts += chunks.get(i).getText() + " ";
            tokenEnd = chunks.get(i).getEnd();
        }
        ts = ts.trim();
        pos++;

        return reusableToken.reinit(ts, tokenStart , tokenEnd);
    }

    private ArrayList<Token> getChunks() {
        ArrayList<Token> ret = new ArrayList<Token>();
        String c_inStr = s_inStr;
        
        for (int i = 0; i < listDelimiters.length; i++) {
            c_inStr = c_inStr.replaceAll(listDelimiters[i], " " + listDelimiters[i] + " ");
        }

        List<String> tc = Arrays.asList(c_inStr.split("[\\s]+"));
        int lp = 0;
        for(String s : tc) {
            lp = c_inStr.indexOf(s, lp);
            ret.add(new Token(s, lp, lp + s.length() - 1));
            lp += s.length() + 1;
        }

        return ret;
    }

    public void setListDelimiters(String [] delimiters) {
        listDelimiters = delimiters;
    }
}
