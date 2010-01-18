/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp.analysis;

import java.io.IOException;
import java.io.StringReader;

/**
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class EntityRecognizer {

    public EntityRecognizer() throws IOException {
        String test = "nombre(s),  correo electrónico  de usuario";
        StringReader reader = new StringReader(test);

        NgramWordTokenizer ngwt = new NgramWordTokenizer(reader, 2, 5);

        Token tk = new Token();
        while((tk = ngwt.next(tk)) != null) {
            String text = tk.getText();
            System.out.println("===> " + text);
        }        
    }
}
