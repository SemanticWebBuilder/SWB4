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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.semanticwb.nlp.SWBLocaleLexicon;
import org.semanticwb.nlp.Word;

/**
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class EntityRecognizer {
    private SWBLocaleLexicon lex;

    public EntityRecognizer(SWBLocaleLexicon dict) {
        lex = dict;
    }

    public String tagEntities(String input) throws IOException {
        List<String> tokens = new ArrayList<String>();
        String str = " " + input.trim() + " ";

        NgramWordTokenizer ngwt = new NgramWordTokenizer(new StringReader(input), 1, lex.getMaxWordLength());
        Token tk = new Token();
        while((tk = ngwt.next(tk)) != null) {
            tokens.add(tk.getText());
        }

        System.out.println("===> Checking string " + "\"" + str + "\"");

        for (int i = tokens.size() - 1; i >= 0; i--) {
            String s = tokens.get(i).trim();
            System.out.print("===> Checking if \"" + s + "\" is in lexicon ... ");
            Word w = lex.getWord(s, true);
            if (w == null) {
                w = lex.getWord(s, false);
            }
            if ( w != null) {
                if (w.getLexicalForm().equalsIgnoreCase(s)) {
                    System.out.println("[yes]");
                    System.out.println("===> Replacing " + "[^\\[]" + s + "[^]] with " + "[" + s + "]");
                    str = str.replaceAll("[^\\[]" + s + "[^]]", " [" + s + "] ");
                    System.out.println("===>  Result: " + str);
                } else {
                    System.out.println("[no]");
                }
            } else {
                System.out.println("[no]");
            }
        }

        return str.trim();
    }
}
