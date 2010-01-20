/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
