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
package org.semanticwb.base.util.parser.html;

// TODO: Auto-generated Javadoc
/**
 * The Class TokenMgrError.
 */
public class TokenMgrError extends Error
{
    /*
     * Ordinals for various reasons why an Error of this type can be thrown.
     */

    /**
     * Lexical error occured.
     */
    static final int LEXICAL_ERROR = 0;

    /**
     * An attempt wass made to create a second instance of a static token manager.
     */
    static final int STATIC_LEXER_ERROR = 1;

    /**
     * Tried to change to an invalid lexical state.
     */
    static final int INVALID_LEXICAL_STATE = 2;

    /**
     * Detected (and bailed out of) an infinite loop in the token manager.
     */
    static final int LOOP_DETECTED = 3;

    /**
     * Indicates the reason why the exception is thrown. It will have
     * one of the above 4 values.
     */
    int errorCode;

    /**
     * Replaces unprintable characters by their espaced (or unicode escaped)
     * equivalents in the given string.
     * 
     * @param str the str
     * @return the string
     */
    protected static final String addEscapes(String str)
    {
        StringBuffer retval = new StringBuffer();
        char ch;
        for (int i = 0; i < str.length(); i++)
        {
            switch (str.charAt(i))
            {
                case 0:
                    continue;
                case '\b':
                    retval.append("\\b");
                    continue;
                case '\t':
                    retval.append("\\t");
                    continue;
                case '\n':
                    retval.append("\\n");
                    continue;
                case '\f':
                    retval.append("\\f");
                    continue;
                case '\r':
                    retval.append("\\r");
                    continue;
                case '\"':
                    retval.append("\\\"");
                    continue;
                case '\'':
                    retval.append("\\\'");
                    continue;
                case '\\':
                    retval.append("\\\\");
                    continue;
                default:
                    if ((ch = str.charAt(i)) < 0x20 || ch > 0x7e)
                    {
                        String s = "0000" + Integer.toString(ch, 16);
                        retval.append("\\u" + s.substring(s.length() - 4, s.length()));
                    } else
                    {
                        retval.append(ch);
                    }
                    continue;
            }
        }
        return retval.toString();
    }

    /**
     * Returns a detailed message for the Error when it is thrown by the
     * token manager to indicate a lexical error.
     * Parameters :
     * EOFSeen     : indicates if EOF caused the lexicl error
     * curLexState : lexical state in which this error occured
     * errorLine   : line number when the error occured
     * errorColumn : column number when the error occured
     * errorAfter  : prefix that was seen before this error occured
     * curchar     : the offending character
     * Note: You can customize the lexical error message by modifying this method.
     * 
     * @param EOFSeen the eOF seen
     * @param lexState the lex state
     * @param errorLine the error line
     * @param errorColumn the error column
     * @param errorAfter the error after
     * @param curChar the cur char
     * @return the string
     */
    protected static String LexicalError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar)
    {
        return ("Lexical error at line " +
                errorLine + ", column " +
                errorColumn + ".  Encountered: " +
                (EOFSeen ? "<EOF> " : ("\"" + addEscapes(String.valueOf(curChar)) + "\"") + " (" + (int) curChar + "), ") +
                "after : \"" + addEscapes(errorAfter) + "\"");
    }

    /**
     * You can also modify the body of this method to customize your error messages.
     * For example, cases like LOOP_DETECTED and INVALID_LEXICAL_STATE are not
     * of end-users concern, so you can return something like :
     * 
     * "Internal Error : Please file a bug report .... "
     * 
     * from this method for such cases in the release version of your parser.
     * 
     * @return the message
     */
    public String getMessage()
    {
        return super.getMessage();
    }

    /*
     * Constructors of various flavors follow.
     */

    /**
     * Instantiates a new token mgr error.
     */
    public TokenMgrError()
    {
    }

    /**
     * Instantiates a new token mgr error.
     * 
     * @param message the message
     * @param reason the reason
     */
    public TokenMgrError(String message, int reason)
    {
        super(message);
        errorCode = reason;
    }

    /**
     * Instantiates a new token mgr error.
     * 
     * @param EOFSeen the eOF seen
     * @param lexState the lex state
     * @param errorLine the error line
     * @param errorColumn the error column
     * @param errorAfter the error after
     * @param curChar the cur char
     * @param reason the reason
     */
    public TokenMgrError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar, int reason)
    {
        this(LexicalError(EOFSeen, lexState, errorLine, errorColumn, errorAfter, curChar), reason);
    }
}
