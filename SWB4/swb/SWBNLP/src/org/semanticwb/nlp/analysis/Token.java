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

/**
 * Representa un token, es decir, una palabra y su información asociada.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class Token {
        private String tokenText;
        private int tokenStart;
        private int tokenEnd;
        private int tokenSize;

        /**
         * Crea una nueva instancia de Token.
         */
        public Token () {

        }

        /**
         * Crea una nueva instancia de Token.
         * @param text texto del token
         * @param start índice del inicio del token en la cadena
         * @param end índice del fin del token en la cadena
         */
        public Token(String text, int start, int end) {
            reinit(text, start, end);
        }

        /**
         * Devuelve el texto del token
         * @return
         */
        public String getText() {
            return tokenText;
        }

        /**
         * Establece el texto del token
         * @param text
         */
        public void setText(String text) {
            tokenText = text;
        }

        /**
         * Obtiene el índice dentro de la cadena donde inicia el token
         * @return
         */
        public int getStart() {
            return tokenStart;
        }

        /**
         * Establece el índice dentro de la cadena donde inicia el token
         * @param start índice de inicio del token
         */
        public void setStart(int start) {
            tokenStart = start;
            tokenSize = tokenEnd - tokenStart + 1;
        }

        /**
         * Obtiene el índice dentro de la cadena donde termina el token
         * @return
         */
        public int getEnd() {
            return tokenEnd;
        }

        /**
         * Establece el índice dentro de la cadena donde termina el token
         * @param end índice de fin del token
         */
        public void setEnd(int end) {
            tokenEnd = end;
            tokenSize = tokenEnd - tokenStart + 1;
        }

        /**
         * Obtiene la longitud del token
         * @return
         */
        public int getSize() {
            return tokenSize;
        }

        /**
         * Establece la longitud del token
         * @param size
         */
        public void setSize(int size) {
            tokenSize = size;
        }

        /**
         * Reinicia un token para poder reutilizarlo
         * @param text texto del token
         * @param start índice de inicio del token
         * @param end índice del final del token
         * @return token con nuevos valores
         */
        public Token reinit(String text, int start, int end) {
            tokenStart = start;
            tokenEnd = end;
            tokenText = text;
            tokenSize = text.length();

            return this;
        }
    }
