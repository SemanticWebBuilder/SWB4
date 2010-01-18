/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp.analysis;

/**
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class Token {
        private String tokenText;
        private int tokenStart;
        private int tokenEnd;
        private int tokenSize;

        public Token () {

        }
        
        public Token(String text, int start, int end) {
            reinit(text, start, end);
        }

        public String getText() {
            return tokenText;
        }

        public void setText(String text) {
            tokenText = text;
        }

        public int getStart() {
            return tokenStart;
        }

        public void setStart(int start) {
            tokenStart = start;
            tokenSize = tokenEnd - tokenStart + 1;
        }

        public int getEnd() {
            return tokenEnd;
        }

        public void setEnd(int end) {
            tokenEnd = end;
            tokenSize = tokenEnd - tokenStart + 1;
        }

        public int getSize() {
            return tokenSize;
        }

        public void setSize(int size) {
            tokenSize = size;
        }

        public Token reinit(String text, int start, int end) {
            tokenStart = start;
            tokenEnd = end;
            tokenText = text;
            tokenSize = text.length();

            return this;
        }
    }
