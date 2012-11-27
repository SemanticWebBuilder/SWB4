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
 *
 * @author hasdai
 */
// $ANTLR 3.2 Sep 23, 2009 12:02:23 /home/hasdai/Documentos/INFOTEC/CSSLexer.g 2009-10-27 15:57:02
import org.antlr.runtime.*;

public class CSSLexer extends Lexer {

    public static final int COMMA = 57;
    public static final int IMPORTANT_SYM = 68;
    public static final int PAGE_SYM = 64;
    public static final int O = 26;
    public static final int MINUS = 52;
    public static final int HASH = 62;
    public static final int DASHMATCH = 42;
    public static final int P = 27;
    public static final int NUMBER = 77;
    public static final int V = 33;
    public static final int I = 20;
    public static final int INVALID = 59;
    public static final int LBRACKET = 46;
    public static final int F = 17;
    public static final int U = 32;
    public static final int INCLUDES = 41;
    public static final int URL = 11;
    public static final int URI = 78;
    public static final int EMS = 69;
    public static final int LBRACE = 44;
    public static final int DOT = 58;
    public static final int RBRACE = 45;
    public static final int S = 30;
    public static final int D = 15;
    public static final int NONASCII = 5;
    public static final int NL = 79;
    public static final int ANGLE = 72;
    public static final int K = 22;
    public static final int R = 29;
    public static final int UNICODE = 6;
    public static final int IMPORT_SYM = 63;
    public static final int B = 13;
    public static final int Q = 28;
    public static final int RBRACKET = 47;
    public static final int ESCAPE = 7;
    public static final int RPAREN = 56;
    public static final int LPAREN = 55;
    public static final int M = 24;
    public static final int GREATER = 43;
    public static final int PLUS = 53;
    public static final int NMCHAR = 9;
    public static final int T = 31;
    public static final int IDENT = 61;
    public static final int W = 34;
    public static final int H = 19;
    public static final int LENGTH = 71;
    public static final int EXS = 70;
    public static final int WS = 67;
    public static final int STRING = 60;
    public static final int G = 18;
    public static final int COMMENT = 38;
    public static final int OPEQ = 48;
    public static final int A = 12;
    public static final int CDC = 40;
    public static final int N = 25;
    public static final int X = 35;
    public static final int SEMI = 49;
    public static final int Z = 37;
    public static final int NMSTART = 8;
    public static final int TIME = 73;
    public static final int DIMENSION = 75;
    public static final int C = 14;
    public static final int EOF = -1;
    public static final int HEXCHAR = 4;
    public static final int L = 23;
    public static final int COLON = 50;
    public static final int SOLIDUS = 51;
    public static final int CDO = 39;
    public static final int STAR = 54;
    public static final int MEDIA_SYM = 65;
    public static final int J = 21;
    public static final int Y = 36;
    public static final int NAME = 10;
    public static final int PERCENTAGE = 76;
    public static final int CHARSET_SYM = 66;
    public static final int FREQ = 74;
    public static final int E = 16;

    // delegates
    // delegators
    public CSSLexer() {
        ;
    }

    public CSSLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }

    public CSSLexer(CharStream input, RecognizerSharedState state) {
        super(input, state);

    }

    public String getGrammarFileName() {
        return "/home/hasdai/Documentos/INFOTEC/CSSLexer.g";
    }

    // $ANTLR start "HEXCHAR"
    public final void mHEXCHAR() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:46:19: ( ( 'a' .. 'f' | 'A' .. 'F' | '0' .. '9' ) )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:46:21: ( 'a' .. 'f' | 'A' .. 'F' | '0' .. '9' )
            {
                if ((input.LA(1) >= '0' && input.LA(1) <= '9') || (input.LA(1) >= 'A' && input.LA(1) <= 'F') || (input.LA(1) >= 'a' && input.LA(1) <= 'f')) {
                    input.consume();
                    state.failed = false;
                } else {
                    if (state.backtracking > 0) {
                        state.failed = true;
                        return;
                    }
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    recover(mse);
                    throw mse;
                }


            }

        } finally {
        }
    }
    // $ANTLR end "HEXCHAR"

    // $ANTLR start "NONASCII"
    public final void mNONASCII() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:48:19: ( '\\u0080' .. '\\uFFFF' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:48:21: '\\u0080' .. '\\uFFFF'
            {
                matchRange('\u0080', '\uFFFF');
                if (state.failed) {
                    return;
                }

            }

        } finally {
        }
    }
    // $ANTLR end "NONASCII"

    // $ANTLR start "UNICODE"
    public final void mUNICODE() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:50:19: ( '\\\\' HEXCHAR ( HEXCHAR ( HEXCHAR ( HEXCHAR ( HEXCHAR ( HEXCHAR )? )? )? )? )? ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:50:21: '\\\\' HEXCHAR ( HEXCHAR ( HEXCHAR ( HEXCHAR ( HEXCHAR ( HEXCHAR )? )? )? )? )? ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
            {
                match('\\');
                if (state.failed) {
                    return;
                }
                mHEXCHAR();
                if (state.failed) {
                    return;
                }
                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:51:9: ( HEXCHAR ( HEXCHAR ( HEXCHAR ( HEXCHAR ( HEXCHAR )? )? )? )? )?
                int alt5 = 2;
                int LA5_0 = input.LA(1);

                if (((LA5_0 >= '0' && LA5_0 <= '9') || (LA5_0 >= 'A' && LA5_0 <= 'F') || (LA5_0 >= 'a' && LA5_0 <= 'f'))) {
                    alt5 = 1;
                }
                switch (alt5) {
                    case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:51:10: HEXCHAR ( HEXCHAR ( HEXCHAR ( HEXCHAR ( HEXCHAR )? )? )? )?
                    {
                        mHEXCHAR();
                        if (state.failed) {
                            return;
                        }
                        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:52:10: ( HEXCHAR ( HEXCHAR ( HEXCHAR ( HEXCHAR )? )? )? )?
                        int alt4 = 2;
                        int LA4_0 = input.LA(1);

                        if (((LA4_0 >= '0' && LA4_0 <= '9') || (LA4_0 >= 'A' && LA4_0 <= 'F') || (LA4_0 >= 'a' && LA4_0 <= 'f'))) {
                            alt4 = 1;
                        }
                        switch (alt4) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:52:11: HEXCHAR ( HEXCHAR ( HEXCHAR ( HEXCHAR )? )? )?
                            {
                                mHEXCHAR();
                                if (state.failed) {
                                    return;
                                }
                                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:53:11: ( HEXCHAR ( HEXCHAR ( HEXCHAR )? )? )?
                                int alt3 = 2;
                                int LA3_0 = input.LA(1);

                                if (((LA3_0 >= '0' && LA3_0 <= '9') || (LA3_0 >= 'A' && LA3_0 <= 'F') || (LA3_0 >= 'a' && LA3_0 <= 'f'))) {
                                    alt3 = 1;
                                }
                                switch (alt3) {
                                    case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:53:12: HEXCHAR ( HEXCHAR ( HEXCHAR )? )?
                                    {
                                        mHEXCHAR();
                                        if (state.failed) {
                                            return;
                                        }
                                        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:54:12: ( HEXCHAR ( HEXCHAR )? )?
                                        int alt2 = 2;
                                        int LA2_0 = input.LA(1);

                                        if (((LA2_0 >= '0' && LA2_0 <= '9') || (LA2_0 >= 'A' && LA2_0 <= 'F') || (LA2_0 >= 'a' && LA2_0 <= 'f'))) {
                                            alt2 = 1;
                                        }
                                        switch (alt2) {
                                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:54:13: HEXCHAR ( HEXCHAR )?
                                            {
                                                mHEXCHAR();
                                                if (state.failed) {
                                                    return;
                                                }
                                                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:54:21: ( HEXCHAR )?
                                                int alt1 = 2;
                                                int LA1_0 = input.LA(1);

                                                if (((LA1_0 >= '0' && LA1_0 <= '9') || (LA1_0 >= 'A' && LA1_0 <= 'F') || (LA1_0 >= 'a' && LA1_0 <= 'f'))) {
                                                    alt1 = 1;
                                                }
                                                switch (alt1) {
                                                    case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:54:21: HEXCHAR
                                                    {
                                                        mHEXCHAR();
                                                        if (state.failed) {
                                                            return;
                                                        }

                                                    }
                                                    break;

                                                }


                                            }
                                            break;

                                        }


                                    }
                                    break;

                                }


                            }
                            break;

                        }


                    }
                    break;

                }

                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:58:9: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                loop6:
                do {
                    int alt6 = 2;
                    int LA6_0 = input.LA(1);

                    if (((LA6_0 >= '\t' && LA6_0 <= '\n') || (LA6_0 >= '\f' && LA6_0 <= '\r') || LA6_0 == ' ')) {
                        alt6 = 1;
                    }


                    switch (alt6) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                        {
                            if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }


                        }
                        break;

                        default:
                            break loop6;
                    }
                } while (true);


            }

        } finally {
        }
    }
    // $ANTLR end "UNICODE"

    // $ANTLR start "ESCAPE"
    public final void mESCAPE() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:60:18: ( UNICODE | '\\\\' ~ ( '\\r' | '\\n' | '\\f' | HEXCHAR ) )
            int alt7 = 2;
            int LA7_0 = input.LA(1);

            if ((LA7_0 == '\\')) {
                int LA7_1 = input.LA(2);

                if (((LA7_1 >= '\u0000' && LA7_1 <= '\t') || LA7_1 == '\u000B' || (LA7_1 >= '\u000E' && LA7_1 <= '/') || (LA7_1 >= ':' && LA7_1 <= '@') || (LA7_1 >= 'G' && LA7_1 <= '`') || (LA7_1 >= 'g' && LA7_1 <= '\uFFFF'))) {
                    alt7 = 2;
                } else if (((LA7_1 >= '0' && LA7_1 <= '9') || (LA7_1 >= 'A' && LA7_1 <= 'F') || (LA7_1 >= 'a' && LA7_1 <= 'f'))) {
                    alt7 = 1;
                } else {
                    if (state.backtracking > 0) {
                        state.failed = true;
                        return;
                    }
                    NoViableAltException nvae =
                            new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:60:20: UNICODE
                {
                    mUNICODE();
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:60:30: '\\\\' ~ ( '\\r' | '\\n' | '\\f' | HEXCHAR )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    if ((input.LA(1) >= '\u0000' && input.LA(1) <= '\t') || input.LA(1) == '\u000B' || (input.LA(1) >= '\u000E' && input.LA(1) <= '/') || (input.LA(1) >= ':' && input.LA(1) <= '@') || (input.LA(1) >= 'G' && input.LA(1) <= '`') || (input.LA(1) >= 'g' && input.LA(1) <= '\uFFFF')) {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "ESCAPE"

    // $ANTLR start "NMSTART"
    public final void mNMSTART() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:62:19: ( '_' | 'a' .. 'z' | 'A' .. 'Z' | NONASCII | ESCAPE )
            int alt8 = 5;
            int LA8_0 = input.LA(1);

            if ((LA8_0 == '_')) {
                alt8 = 1;
            } else if (((LA8_0 >= 'a' && LA8_0 <= 'z'))) {
                alt8 = 2;
            } else if (((LA8_0 >= 'A' && LA8_0 <= 'Z'))) {
                alt8 = 3;
            } else if (((LA8_0 >= '\u0080' && LA8_0 <= '\uFFFF'))) {
                alt8 = 4;
            } else if ((LA8_0 == '\\')) {
                alt8 = 5;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:62:21: '_'
                {
                    match('_');
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:63:9: 'a' .. 'z'
                {
                    matchRange('a', 'z');
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:64:9: 'A' .. 'Z'
                {
                    matchRange('A', 'Z');
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 4: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:65:9: NONASCII
                {
                    mNONASCII();
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 5: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:66:9: ESCAPE
                {
                    mESCAPE();
                    if (state.failed) {
                        return;
                    }

                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "NMSTART"

    // $ANTLR start "NMCHAR"
    public final void mNMCHAR() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:69:18: ( '_' | 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | NONASCII | ESCAPE )
            int alt9 = 7;
            int LA9_0 = input.LA(1);

            if ((LA9_0 == '_')) {
                alt9 = 1;
            } else if (((LA9_0 >= 'a' && LA9_0 <= 'z'))) {
                alt9 = 2;
            } else if (((LA9_0 >= 'A' && LA9_0 <= 'Z'))) {
                alt9 = 3;
            } else if (((LA9_0 >= '0' && LA9_0 <= '9'))) {
                alt9 = 4;
            } else if ((LA9_0 == '-')) {
                alt9 = 5;
            } else if (((LA9_0 >= '\u0080' && LA9_0 <= '\uFFFF'))) {
                alt9 = 6;
            } else if ((LA9_0 == '\\')) {
                alt9 = 7;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:69:20: '_'
                {
                    match('_');
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:70:9: 'a' .. 'z'
                {
                    matchRange('a', 'z');
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:71:9: 'A' .. 'Z'
                {
                    matchRange('A', 'Z');
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 4: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:72:9: '0' .. '9'
                {
                    matchRange('0', '9');
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 5: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:73:9: '-'
                {
                    match('-');
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 6: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:74:9: NONASCII
                {
                    mNONASCII();
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 7: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:75:9: ESCAPE
                {
                    mESCAPE();
                    if (state.failed) {
                        return;
                    }

                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "NMCHAR"

    // $ANTLR start "NAME"
    public final void mNAME() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:78:16: ( ( NMCHAR )+ )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:78:18: ( NMCHAR )+
            {
                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:78:18: ( NMCHAR )+
                int cnt10 = 0;
                loop10:
                do {
                    int alt10 = 2;
                    int LA10_0 = input.LA(1);

                    if ((LA10_0 == '-' || (LA10_0 >= '0' && LA10_0 <= '9') || (LA10_0 >= 'A' && LA10_0 <= 'Z') || LA10_0 == '\\' || LA10_0 == '_' || (LA10_0 >= 'a' && LA10_0 <= 'z') || (LA10_0 >= '\u0080' && LA10_0 <= '\uFFFF'))) {
                        alt10 = 1;
                    }


                    switch (alt10) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:78:18: NMCHAR
                        {
                            mNMCHAR();
                            if (state.failed) {
                                return;
                            }

                        }
                        break;

                        default:
                            if (cnt10 >= 1) {
                                break loop10;
                            }
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            EarlyExitException eee =
                                    new EarlyExitException(10, input);
                            throw eee;
                    }
                    cnt10++;
                } while (true);


            }

        } finally {
        }
    }
    // $ANTLR end "NAME"

    // $ANTLR start "URL"
    public final void mURL() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:80:16: ( ( '[' | '!' | '#' | '$' | '%' | '&' | '*' | '-' | '~' | NONASCII | ESCAPE )* )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:80:18: ( '[' | '!' | '#' | '$' | '%' | '&' | '*' | '-' | '~' | NONASCII | ESCAPE )*
            {
                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:80:18: ( '[' | '!' | '#' | '$' | '%' | '&' | '*' | '-' | '~' | NONASCII | ESCAPE )*
                loop11:
                do {
                    int alt11 = 12;
                    alt11 = dfa11.predict(input);
                    switch (alt11) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:81:10: '['
                        {
                            match('[');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:81:14: '!'
                        {
                            match('!');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:81:18: '#'
                        {
                            match('#');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 4: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:81:22: '$'
                        {
                            match('$');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 5: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:81:26: '%'
                        {
                            match('%');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 6: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:81:30: '&'
                        {
                            match('&');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 7: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:81:34: '*'
                        {
                            match('*');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 8: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:81:38: '-'
                        {
                            match('-');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 9: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:81:42: '~'
                        {
                            match('~');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 10: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:82:10: NONASCII
                        {
                            mNONASCII();
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 11: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:83:10: ESCAPE
                        {
                            mESCAPE();
                            if (state.failed) {
                                return;
                            }

                        }
                        break;

                        default:
                            break loop11;
                    }
                } while (true);


            }

        } finally {
        }
    }
    // $ANTLR end "URL"

    // $ANTLR start "A"
    public final void mA() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:93:12: ( ( 'a' | 'A' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '1' )
            int alt17 = 2;
            int LA17_0 = input.LA(1);

            if ((LA17_0 == 'A' || LA17_0 == 'a')) {
                alt17 = 1;
            } else if ((LA17_0 == '\\')) {
                alt17 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:93:14: ( 'a' | 'A' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:93:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop12:
                    do {
                        int alt12 = 2;
                        int LA12_0 = input.LA(1);

                        if (((LA12_0 >= '\t' && LA12_0 <= '\n') || (LA12_0 >= '\f' && LA12_0 <= '\r') || LA12_0 == ' ')) {
                            alt12 = 1;
                        }


                        switch (alt12) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop12;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:94:8: '\\\\' ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '1'
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:94:13: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                    int alt16 = 2;
                    int LA16_0 = input.LA(1);

                    if ((LA16_0 == '0')) {
                        alt16 = 1;
                    }
                    switch (alt16) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:94:14: '0' ( '0' ( '0' ( '0' )? )? )?
                        {
                            match('0');
                            if (state.failed) {
                                return;
                            }
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:94:18: ( '0' ( '0' ( '0' )? )? )?
                            int alt15 = 2;
                            int LA15_0 = input.LA(1);

                            if ((LA15_0 == '0')) {
                                alt15 = 1;
                            }
                            switch (alt15) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:94:19: '0' ( '0' ( '0' )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:94:23: ( '0' ( '0' )? )?
                                    int alt14 = 2;
                                    int LA14_0 = input.LA(1);

                                    if ((LA14_0 == '0')) {
                                        alt14 = 1;
                                    }
                                    switch (alt14) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:94:24: '0' ( '0' )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:94:28: ( '0' )?
                                            int alt13 = 2;
                                            int LA13_0 = input.LA(1);

                                            if ((LA13_0 == '0')) {
                                                alt13 = 1;
                                            }
                                            switch (alt13) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:94:28: '0'
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }

                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }


                        }
                        break;

                    }

                    if (input.LA(1) == '4' || input.LA(1) == '6') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    match('1');
                    if (state.failed) {
                        return;
                    }

                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "A"

    // $ANTLR start "B"
    public final void mB() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:96:12: ( ( 'b' | 'B' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '2' )
            int alt23 = 2;
            int LA23_0 = input.LA(1);

            if ((LA23_0 == 'B' || LA23_0 == 'b')) {
                alt23 = 1;
            } else if ((LA23_0 == '\\')) {
                alt23 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:96:14: ( 'b' | 'B' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'B' || input.LA(1) == 'b') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:96:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop18:
                    do {
                        int alt18 = 2;
                        int LA18_0 = input.LA(1);

                        if (((LA18_0 >= '\t' && LA18_0 <= '\n') || (LA18_0 >= '\f' && LA18_0 <= '\r') || LA18_0 == ' ')) {
                            alt18 = 1;
                        }


                        switch (alt18) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop18;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:97:8: '\\\\' ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '2'
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:97:13: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                    int alt22 = 2;
                    int LA22_0 = input.LA(1);

                    if ((LA22_0 == '0')) {
                        alt22 = 1;
                    }
                    switch (alt22) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:97:14: '0' ( '0' ( '0' ( '0' )? )? )?
                        {
                            match('0');
                            if (state.failed) {
                                return;
                            }
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:97:18: ( '0' ( '0' ( '0' )? )? )?
                            int alt21 = 2;
                            int LA21_0 = input.LA(1);

                            if ((LA21_0 == '0')) {
                                alt21 = 1;
                            }
                            switch (alt21) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:97:19: '0' ( '0' ( '0' )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:97:23: ( '0' ( '0' )? )?
                                    int alt20 = 2;
                                    int LA20_0 = input.LA(1);

                                    if ((LA20_0 == '0')) {
                                        alt20 = 1;
                                    }
                                    switch (alt20) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:97:24: '0' ( '0' )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:97:28: ( '0' )?
                                            int alt19 = 2;
                                            int LA19_0 = input.LA(1);

                                            if ((LA19_0 == '0')) {
                                                alt19 = 1;
                                            }
                                            switch (alt19) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:97:28: '0'
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }

                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }


                        }
                        break;

                    }

                    if (input.LA(1) == '4' || input.LA(1) == '6') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    match('2');
                    if (state.failed) {
                        return;
                    }

                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "B"

    // $ANTLR start "C"
    public final void mC() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:99:12: ( ( 'c' | 'C' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '3' )
            int alt29 = 2;
            int LA29_0 = input.LA(1);

            if ((LA29_0 == 'C' || LA29_0 == 'c')) {
                alt29 = 1;
            } else if ((LA29_0 == '\\')) {
                alt29 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:99:14: ( 'c' | 'C' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'C' || input.LA(1) == 'c') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:99:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop24:
                    do {
                        int alt24 = 2;
                        int LA24_0 = input.LA(1);

                        if (((LA24_0 >= '\t' && LA24_0 <= '\n') || (LA24_0 >= '\f' && LA24_0 <= '\r') || LA24_0 == ' ')) {
                            alt24 = 1;
                        }


                        switch (alt24) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop24;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:100:8: '\\\\' ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '3'
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:100:13: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                    int alt28 = 2;
                    int LA28_0 = input.LA(1);

                    if ((LA28_0 == '0')) {
                        alt28 = 1;
                    }
                    switch (alt28) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:100:14: '0' ( '0' ( '0' ( '0' )? )? )?
                        {
                            match('0');
                            if (state.failed) {
                                return;
                            }
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:100:18: ( '0' ( '0' ( '0' )? )? )?
                            int alt27 = 2;
                            int LA27_0 = input.LA(1);

                            if ((LA27_0 == '0')) {
                                alt27 = 1;
                            }
                            switch (alt27) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:100:19: '0' ( '0' ( '0' )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:100:23: ( '0' ( '0' )? )?
                                    int alt26 = 2;
                                    int LA26_0 = input.LA(1);

                                    if ((LA26_0 == '0')) {
                                        alt26 = 1;
                                    }
                                    switch (alt26) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:100:24: '0' ( '0' )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:100:28: ( '0' )?
                                            int alt25 = 2;
                                            int LA25_0 = input.LA(1);

                                            if ((LA25_0 == '0')) {
                                                alt25 = 1;
                                            }
                                            switch (alt25) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:100:28: '0'
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }

                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }


                        }
                        break;

                    }

                    if (input.LA(1) == '4' || input.LA(1) == '6') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    match('3');
                    if (state.failed) {
                        return;
                    }

                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "C"

    // $ANTLR start "D"
    public final void mD() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:102:12: ( ( 'd' | 'D' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '4' )
            int alt35 = 2;
            int LA35_0 = input.LA(1);

            if ((LA35_0 == 'D' || LA35_0 == 'd')) {
                alt35 = 1;
            } else if ((LA35_0 == '\\')) {
                alt35 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:102:14: ( 'd' | 'D' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'D' || input.LA(1) == 'd') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:102:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop30:
                    do {
                        int alt30 = 2;
                        int LA30_0 = input.LA(1);

                        if (((LA30_0 >= '\t' && LA30_0 <= '\n') || (LA30_0 >= '\f' && LA30_0 <= '\r') || LA30_0 == ' ')) {
                            alt30 = 1;
                        }


                        switch (alt30) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop30;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:103:8: '\\\\' ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '4'
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:103:13: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                    int alt34 = 2;
                    int LA34_0 = input.LA(1);

                    if ((LA34_0 == '0')) {
                        alt34 = 1;
                    }
                    switch (alt34) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:103:14: '0' ( '0' ( '0' ( '0' )? )? )?
                        {
                            match('0');
                            if (state.failed) {
                                return;
                            }
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:103:18: ( '0' ( '0' ( '0' )? )? )?
                            int alt33 = 2;
                            int LA33_0 = input.LA(1);

                            if ((LA33_0 == '0')) {
                                alt33 = 1;
                            }
                            switch (alt33) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:103:19: '0' ( '0' ( '0' )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:103:23: ( '0' ( '0' )? )?
                                    int alt32 = 2;
                                    int LA32_0 = input.LA(1);

                                    if ((LA32_0 == '0')) {
                                        alt32 = 1;
                                    }
                                    switch (alt32) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:103:24: '0' ( '0' )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:103:28: ( '0' )?
                                            int alt31 = 2;
                                            int LA31_0 = input.LA(1);

                                            if ((LA31_0 == '0')) {
                                                alt31 = 1;
                                            }
                                            switch (alt31) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:103:28: '0'
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }

                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }


                        }
                        break;

                    }

                    if (input.LA(1) == '4' || input.LA(1) == '6') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    match('4');
                    if (state.failed) {
                        return;
                    }

                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "D"

    // $ANTLR start "E"
    public final void mE() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:105:12: ( ( 'e' | 'E' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '5' )
            int alt41 = 2;
            int LA41_0 = input.LA(1);

            if ((LA41_0 == 'E' || LA41_0 == 'e')) {
                alt41 = 1;
            } else if ((LA41_0 == '\\')) {
                alt41 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 41, 0, input);

                throw nvae;
            }
            switch (alt41) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:105:14: ( 'e' | 'E' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:105:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop36:
                    do {
                        int alt36 = 2;
                        int LA36_0 = input.LA(1);

                        if (((LA36_0 >= '\t' && LA36_0 <= '\n') || (LA36_0 >= '\f' && LA36_0 <= '\r') || LA36_0 == ' ')) {
                            alt36 = 1;
                        }


                        switch (alt36) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop36;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:106:8: '\\\\' ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '5'
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:106:13: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                    int alt40 = 2;
                    int LA40_0 = input.LA(1);

                    if ((LA40_0 == '0')) {
                        alt40 = 1;
                    }
                    switch (alt40) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:106:14: '0' ( '0' ( '0' ( '0' )? )? )?
                        {
                            match('0');
                            if (state.failed) {
                                return;
                            }
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:106:18: ( '0' ( '0' ( '0' )? )? )?
                            int alt39 = 2;
                            int LA39_0 = input.LA(1);

                            if ((LA39_0 == '0')) {
                                alt39 = 1;
                            }
                            switch (alt39) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:106:19: '0' ( '0' ( '0' )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:106:23: ( '0' ( '0' )? )?
                                    int alt38 = 2;
                                    int LA38_0 = input.LA(1);

                                    if ((LA38_0 == '0')) {
                                        alt38 = 1;
                                    }
                                    switch (alt38) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:106:24: '0' ( '0' )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:106:28: ( '0' )?
                                            int alt37 = 2;
                                            int LA37_0 = input.LA(1);

                                            if ((LA37_0 == '0')) {
                                                alt37 = 1;
                                            }
                                            switch (alt37) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:106:28: '0'
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }

                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }


                        }
                        break;

                    }

                    if (input.LA(1) == '4' || input.LA(1) == '6') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    match('5');
                    if (state.failed) {
                        return;
                    }

                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "E"

    // $ANTLR start "F"
    public final void mF() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:108:12: ( ( 'f' | 'F' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '6' )
            int alt47 = 2;
            int LA47_0 = input.LA(1);

            if ((LA47_0 == 'F' || LA47_0 == 'f')) {
                alt47 = 1;
            } else if ((LA47_0 == '\\')) {
                alt47 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:108:14: ( 'f' | 'F' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'F' || input.LA(1) == 'f') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:108:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop42:
                    do {
                        int alt42 = 2;
                        int LA42_0 = input.LA(1);

                        if (((LA42_0 >= '\t' && LA42_0 <= '\n') || (LA42_0 >= '\f' && LA42_0 <= '\r') || LA42_0 == ' ')) {
                            alt42 = 1;
                        }


                        switch (alt42) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop42;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:109:8: '\\\\' ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '6'
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:109:13: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                    int alt46 = 2;
                    int LA46_0 = input.LA(1);

                    if ((LA46_0 == '0')) {
                        alt46 = 1;
                    }
                    switch (alt46) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:109:14: '0' ( '0' ( '0' ( '0' )? )? )?
                        {
                            match('0');
                            if (state.failed) {
                                return;
                            }
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:109:18: ( '0' ( '0' ( '0' )? )? )?
                            int alt45 = 2;
                            int LA45_0 = input.LA(1);

                            if ((LA45_0 == '0')) {
                                alt45 = 1;
                            }
                            switch (alt45) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:109:19: '0' ( '0' ( '0' )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:109:23: ( '0' ( '0' )? )?
                                    int alt44 = 2;
                                    int LA44_0 = input.LA(1);

                                    if ((LA44_0 == '0')) {
                                        alt44 = 1;
                                    }
                                    switch (alt44) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:109:24: '0' ( '0' )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:109:28: ( '0' )?
                                            int alt43 = 2;
                                            int LA43_0 = input.LA(1);

                                            if ((LA43_0 == '0')) {
                                                alt43 = 1;
                                            }
                                            switch (alt43) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:109:28: '0'
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }

                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }


                        }
                        break;

                    }

                    if (input.LA(1) == '4' || input.LA(1) == '6') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    match('6');
                    if (state.failed) {
                        return;
                    }

                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "F"

    // $ANTLR start "G"
    public final void mG() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:111:12: ( ( 'g' | 'G' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'g' | 'G' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '7' ) )
            int alt54 = 2;
            int LA54_0 = input.LA(1);

            if ((LA54_0 == 'G' || LA54_0 == 'g')) {
                alt54 = 1;
            } else if ((LA54_0 == '\\')) {
                alt54 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 54, 0, input);

                throw nvae;
            }
            switch (alt54) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:111:14: ( 'g' | 'G' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'G' || input.LA(1) == 'g') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:111:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop48:
                    do {
                        int alt48 = 2;
                        int LA48_0 = input.LA(1);

                        if (((LA48_0 >= '\t' && LA48_0 <= '\n') || (LA48_0 >= '\f' && LA48_0 <= '\r') || LA48_0 == ' ')) {
                            alt48 = 1;
                        }


                        switch (alt48) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop48;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:112:8: '\\\\' ( 'g' | 'G' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '7' )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:113:7: ( 'g' | 'G' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '7' )
                    int alt53 = 3;
                    switch (input.LA(1)) {
                        case 'g': {
                            alt53 = 1;
                        }
                        break;
                        case 'G': {
                            alt53 = 2;
                        }
                        break;
                        case '0':
                        case '4':
                        case '6': {
                            alt53 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 53, 0, input);

                            throw nvae;
                    }

                    switch (alt53) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:114:10: 'g'
                        {
                            match('g');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:115:10: 'G'
                        {
                            match('G');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:116:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '7'
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:116:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt52 = 2;
                            int LA52_0 = input.LA(1);

                            if ((LA52_0 == '0')) {
                                alt52 = 1;
                            }
                            switch (alt52) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:116:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:116:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt51 = 2;
                                    int LA51_0 = input.LA(1);

                                    if ((LA51_0 == '0')) {
                                        alt51 = 1;
                                    }
                                    switch (alt51) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:116:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:116:20: ( '0' ( '0' )? )?
                                            int alt50 = 2;
                                            int LA50_0 = input.LA(1);

                                            if ((LA50_0 == '0')) {
                                                alt50 = 1;
                                            }
                                            switch (alt50) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:116:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:116:25: ( '0' )?
                                                    int alt49 = 2;
                                                    int LA49_0 = input.LA(1);

                                                    if ((LA49_0 == '0')) {
                                                        alt49 = 1;
                                                    }
                                                    switch (alt49) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:116:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '4' || input.LA(1) == '6') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            match('7');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "G"

    // $ANTLR start "H"
    public final void mH() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:119:12: ( ( 'h' | 'H' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'h' | 'H' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '8' ) )
            int alt61 = 2;
            int LA61_0 = input.LA(1);

            if ((LA61_0 == 'H' || LA61_0 == 'h')) {
                alt61 = 1;
            } else if ((LA61_0 == '\\')) {
                alt61 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 61, 0, input);

                throw nvae;
            }
            switch (alt61) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:119:14: ( 'h' | 'H' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'H' || input.LA(1) == 'h') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:119:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop55:
                    do {
                        int alt55 = 2;
                        int LA55_0 = input.LA(1);

                        if (((LA55_0 >= '\t' && LA55_0 <= '\n') || (LA55_0 >= '\f' && LA55_0 <= '\r') || LA55_0 == ' ')) {
                            alt55 = 1;
                        }


                        switch (alt55) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop55;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:120:7: '\\\\' ( 'h' | 'H' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '8' )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:121:7: ( 'h' | 'H' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '8' )
                    int alt60 = 3;
                    switch (input.LA(1)) {
                        case 'h': {
                            alt60 = 1;
                        }
                        break;
                        case 'H': {
                            alt60 = 2;
                        }
                        break;
                        case '0':
                        case '4':
                        case '6': {
                            alt60 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 60, 0, input);

                            throw nvae;
                    }

                    switch (alt60) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:122:10: 'h'
                        {
                            match('h');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:123:10: 'H'
                        {
                            match('H');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:124:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '8'
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:124:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt59 = 2;
                            int LA59_0 = input.LA(1);

                            if ((LA59_0 == '0')) {
                                alt59 = 1;
                            }
                            switch (alt59) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:124:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:124:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt58 = 2;
                                    int LA58_0 = input.LA(1);

                                    if ((LA58_0 == '0')) {
                                        alt58 = 1;
                                    }
                                    switch (alt58) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:124:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:124:20: ( '0' ( '0' )? )?
                                            int alt57 = 2;
                                            int LA57_0 = input.LA(1);

                                            if ((LA57_0 == '0')) {
                                                alt57 = 1;
                                            }
                                            switch (alt57) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:124:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:124:25: ( '0' )?
                                                    int alt56 = 2;
                                                    int LA56_0 = input.LA(1);

                                                    if ((LA56_0 == '0')) {
                                                        alt56 = 1;
                                                    }
                                                    switch (alt56) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:124:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '4' || input.LA(1) == '6') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            match('8');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "H"

    // $ANTLR start "I"
    public final void mI() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:127:12: ( ( 'i' | 'I' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'i' | 'I' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '9' ) )
            int alt68 = 2;
            int LA68_0 = input.LA(1);

            if ((LA68_0 == 'I' || LA68_0 == 'i')) {
                alt68 = 1;
            } else if ((LA68_0 == '\\')) {
                alt68 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 68, 0, input);

                throw nvae;
            }
            switch (alt68) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:127:14: ( 'i' | 'I' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:127:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop62:
                    do {
                        int alt62 = 2;
                        int LA62_0 = input.LA(1);

                        if (((LA62_0 >= '\t' && LA62_0 <= '\n') || (LA62_0 >= '\f' && LA62_0 <= '\r') || LA62_0 == ' ')) {
                            alt62 = 1;
                        }


                        switch (alt62) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop62;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:128:7: '\\\\' ( 'i' | 'I' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '9' )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:129:7: ( 'i' | 'I' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '9' )
                    int alt67 = 3;
                    switch (input.LA(1)) {
                        case 'i': {
                            alt67 = 1;
                        }
                        break;
                        case 'I': {
                            alt67 = 2;
                        }
                        break;
                        case '0':
                        case '4':
                        case '6': {
                            alt67 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 67, 0, input);

                            throw nvae;
                    }

                    switch (alt67) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:130:10: 'i'
                        {
                            match('i');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:131:10: 'I'
                        {
                            match('I');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:132:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) '9'
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:132:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt66 = 2;
                            int LA66_0 = input.LA(1);

                            if ((LA66_0 == '0')) {
                                alt66 = 1;
                            }
                            switch (alt66) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:132:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:132:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt65 = 2;
                                    int LA65_0 = input.LA(1);

                                    if ((LA65_0 == '0')) {
                                        alt65 = 1;
                                    }
                                    switch (alt65) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:132:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:132:20: ( '0' ( '0' )? )?
                                            int alt64 = 2;
                                            int LA64_0 = input.LA(1);

                                            if ((LA64_0 == '0')) {
                                                alt64 = 1;
                                            }
                                            switch (alt64) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:132:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:132:25: ( '0' )?
                                                    int alt63 = 2;
                                                    int LA63_0 = input.LA(1);

                                                    if ((LA63_0 == '0')) {
                                                        alt63 = 1;
                                                    }
                                                    switch (alt63) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:132:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '4' || input.LA(1) == '6') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            match('9');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "I"

    // $ANTLR start "J"
    public final void mJ() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:135:12: ( ( 'j' | 'J' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'j' | 'J' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'A' | 'a' ) ) )
            int alt75 = 2;
            int LA75_0 = input.LA(1);

            if ((LA75_0 == 'J' || LA75_0 == 'j')) {
                alt75 = 1;
            } else if ((LA75_0 == '\\')) {
                alt75 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 75, 0, input);

                throw nvae;
            }
            switch (alt75) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:135:14: ( 'j' | 'J' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'J' || input.LA(1) == 'j') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:135:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop69:
                    do {
                        int alt69 = 2;
                        int LA69_0 = input.LA(1);

                        if (((LA69_0 >= '\t' && LA69_0 <= '\n') || (LA69_0 >= '\f' && LA69_0 <= '\r') || LA69_0 == ' ')) {
                            alt69 = 1;
                        }


                        switch (alt69) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop69;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:136:7: '\\\\' ( 'j' | 'J' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'A' | 'a' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:137:7: ( 'j' | 'J' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'A' | 'a' ) )
                    int alt74 = 3;
                    switch (input.LA(1)) {
                        case 'j': {
                            alt74 = 1;
                        }
                        break;
                        case 'J': {
                            alt74 = 2;
                        }
                        break;
                        case '0':
                        case '4':
                        case '6': {
                            alt74 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 74, 0, input);

                            throw nvae;
                    }

                    switch (alt74) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:138:10: 'j'
                        {
                            match('j');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:139:10: 'J'
                        {
                            match('J');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:140:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'A' | 'a' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:140:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt73 = 2;
                            int LA73_0 = input.LA(1);

                            if ((LA73_0 == '0')) {
                                alt73 = 1;
                            }
                            switch (alt73) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:140:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:140:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt72 = 2;
                                    int LA72_0 = input.LA(1);

                                    if ((LA72_0 == '0')) {
                                        alt72 = 1;
                                    }
                                    switch (alt72) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:140:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:140:20: ( '0' ( '0' )? )?
                                            int alt71 = 2;
                                            int LA71_0 = input.LA(1);

                                            if ((LA71_0 == '0')) {
                                                alt71 = 1;
                                            }
                                            switch (alt71) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:140:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:140:25: ( '0' )?
                                                    int alt70 = 2;
                                                    int LA70_0 = input.LA(1);

                                                    if ((LA70_0 == '0')) {
                                                        alt70 = 1;
                                                    }
                                                    switch (alt70) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:140:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '4' || input.LA(1) == '6') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "J"

    // $ANTLR start "K"
    public final void mK() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:143:12: ( ( 'k' | 'K' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'k' | 'K' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'B' | 'b' ) ) )
            int alt82 = 2;
            int LA82_0 = input.LA(1);

            if ((LA82_0 == 'K' || LA82_0 == 'k')) {
                alt82 = 1;
            } else if ((LA82_0 == '\\')) {
                alt82 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 82, 0, input);

                throw nvae;
            }
            switch (alt82) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:143:14: ( 'k' | 'K' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'K' || input.LA(1) == 'k') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:143:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop76:
                    do {
                        int alt76 = 2;
                        int LA76_0 = input.LA(1);

                        if (((LA76_0 >= '\t' && LA76_0 <= '\n') || (LA76_0 >= '\f' && LA76_0 <= '\r') || LA76_0 == ' ')) {
                            alt76 = 1;
                        }


                        switch (alt76) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop76;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:144:7: '\\\\' ( 'k' | 'K' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'B' | 'b' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:145:7: ( 'k' | 'K' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'B' | 'b' ) )
                    int alt81 = 3;
                    switch (input.LA(1)) {
                        case 'k': {
                            alt81 = 1;
                        }
                        break;
                        case 'K': {
                            alt81 = 2;
                        }
                        break;
                        case '0':
                        case '4':
                        case '6': {
                            alt81 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 81, 0, input);

                            throw nvae;
                    }

                    switch (alt81) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:146:10: 'k'
                        {
                            match('k');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:147:10: 'K'
                        {
                            match('K');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:148:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'B' | 'b' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:148:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt80 = 2;
                            int LA80_0 = input.LA(1);

                            if ((LA80_0 == '0')) {
                                alt80 = 1;
                            }
                            switch (alt80) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:148:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:148:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt79 = 2;
                                    int LA79_0 = input.LA(1);

                                    if ((LA79_0 == '0')) {
                                        alt79 = 1;
                                    }
                                    switch (alt79) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:148:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:148:20: ( '0' ( '0' )? )?
                                            int alt78 = 2;
                                            int LA78_0 = input.LA(1);

                                            if ((LA78_0 == '0')) {
                                                alt78 = 1;
                                            }
                                            switch (alt78) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:148:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:148:25: ( '0' )?
                                                    int alt77 = 2;
                                                    int LA77_0 = input.LA(1);

                                                    if ((LA77_0 == '0')) {
                                                        alt77 = 1;
                                                    }
                                                    switch (alt77) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:148:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '4' || input.LA(1) == '6') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            if (input.LA(1) == 'B' || input.LA(1) == 'b') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "K"

    // $ANTLR start "L"
    public final void mL() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:151:12: ( ( 'l' | 'L' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'l' | 'L' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'C' | 'c' ) ) )
            int alt89 = 2;
            int LA89_0 = input.LA(1);

            if ((LA89_0 == 'L' || LA89_0 == 'l')) {
                alt89 = 1;
            } else if ((LA89_0 == '\\')) {
                alt89 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 89, 0, input);

                throw nvae;
            }
            switch (alt89) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:151:14: ( 'l' | 'L' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'L' || input.LA(1) == 'l') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:151:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop83:
                    do {
                        int alt83 = 2;
                        int LA83_0 = input.LA(1);

                        if (((LA83_0 >= '\t' && LA83_0 <= '\n') || (LA83_0 >= '\f' && LA83_0 <= '\r') || LA83_0 == ' ')) {
                            alt83 = 1;
                        }


                        switch (alt83) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop83;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:152:7: '\\\\' ( 'l' | 'L' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'C' | 'c' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:153:7: ( 'l' | 'L' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'C' | 'c' ) )
                    int alt88 = 3;
                    switch (input.LA(1)) {
                        case 'l': {
                            alt88 = 1;
                        }
                        break;
                        case 'L': {
                            alt88 = 2;
                        }
                        break;
                        case '0':
                        case '4':
                        case '6': {
                            alt88 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 88, 0, input);

                            throw nvae;
                    }

                    switch (alt88) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:154:10: 'l'
                        {
                            match('l');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:155:10: 'L'
                        {
                            match('L');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:156:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'C' | 'c' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:156:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt87 = 2;
                            int LA87_0 = input.LA(1);

                            if ((LA87_0 == '0')) {
                                alt87 = 1;
                            }
                            switch (alt87) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:156:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:156:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt86 = 2;
                                    int LA86_0 = input.LA(1);

                                    if ((LA86_0 == '0')) {
                                        alt86 = 1;
                                    }
                                    switch (alt86) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:156:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:156:20: ( '0' ( '0' )? )?
                                            int alt85 = 2;
                                            int LA85_0 = input.LA(1);

                                            if ((LA85_0 == '0')) {
                                                alt85 = 1;
                                            }
                                            switch (alt85) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:156:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:156:25: ( '0' )?
                                                    int alt84 = 2;
                                                    int LA84_0 = input.LA(1);

                                                    if ((LA84_0 == '0')) {
                                                        alt84 = 1;
                                                    }
                                                    switch (alt84) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:156:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '4' || input.LA(1) == '6') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            if (input.LA(1) == 'C' || input.LA(1) == 'c') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "L"

    // $ANTLR start "M"
    public final void mM() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:159:12: ( ( 'm' | 'M' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'm' | 'M' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'D' | 'd' ) ) )
            int alt96 = 2;
            int LA96_0 = input.LA(1);

            if ((LA96_0 == 'M' || LA96_0 == 'm')) {
                alt96 = 1;
            } else if ((LA96_0 == '\\')) {
                alt96 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 96, 0, input);

                throw nvae;
            }
            switch (alt96) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:159:14: ( 'm' | 'M' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'M' || input.LA(1) == 'm') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:159:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop90:
                    do {
                        int alt90 = 2;
                        int LA90_0 = input.LA(1);

                        if (((LA90_0 >= '\t' && LA90_0 <= '\n') || (LA90_0 >= '\f' && LA90_0 <= '\r') || LA90_0 == ' ')) {
                            alt90 = 1;
                        }


                        switch (alt90) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop90;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:160:7: '\\\\' ( 'm' | 'M' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'D' | 'd' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:161:7: ( 'm' | 'M' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'D' | 'd' ) )
                    int alt95 = 3;
                    switch (input.LA(1)) {
                        case 'm': {
                            alt95 = 1;
                        }
                        break;
                        case 'M': {
                            alt95 = 2;
                        }
                        break;
                        case '0':
                        case '4':
                        case '6': {
                            alt95 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 95, 0, input);

                            throw nvae;
                    }

                    switch (alt95) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:162:10: 'm'
                        {
                            match('m');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:163:10: 'M'
                        {
                            match('M');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:164:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'D' | 'd' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:164:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt94 = 2;
                            int LA94_0 = input.LA(1);

                            if ((LA94_0 == '0')) {
                                alt94 = 1;
                            }
                            switch (alt94) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:164:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:164:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt93 = 2;
                                    int LA93_0 = input.LA(1);

                                    if ((LA93_0 == '0')) {
                                        alt93 = 1;
                                    }
                                    switch (alt93) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:164:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:164:20: ( '0' ( '0' )? )?
                                            int alt92 = 2;
                                            int LA92_0 = input.LA(1);

                                            if ((LA92_0 == '0')) {
                                                alt92 = 1;
                                            }
                                            switch (alt92) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:164:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:164:25: ( '0' )?
                                                    int alt91 = 2;
                                                    int LA91_0 = input.LA(1);

                                                    if ((LA91_0 == '0')) {
                                                        alt91 = 1;
                                                    }
                                                    switch (alt91) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:164:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '4' || input.LA(1) == '6') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            if (input.LA(1) == 'D' || input.LA(1) == 'd') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "M"

    // $ANTLR start "N"
    public final void mN() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:167:12: ( ( 'n' | 'N' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'n' | 'N' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'E' | 'e' ) ) )
            int alt103 = 2;
            int LA103_0 = input.LA(1);

            if ((LA103_0 == 'N' || LA103_0 == 'n')) {
                alt103 = 1;
            } else if ((LA103_0 == '\\')) {
                alt103 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 103, 0, input);

                throw nvae;
            }
            switch (alt103) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:167:14: ( 'n' | 'N' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'N' || input.LA(1) == 'n') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:167:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop97:
                    do {
                        int alt97 = 2;
                        int LA97_0 = input.LA(1);

                        if (((LA97_0 >= '\t' && LA97_0 <= '\n') || (LA97_0 >= '\f' && LA97_0 <= '\r') || LA97_0 == ' ')) {
                            alt97 = 1;
                        }


                        switch (alt97) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop97;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:168:7: '\\\\' ( 'n' | 'N' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'E' | 'e' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:169:7: ( 'n' | 'N' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'E' | 'e' ) )
                    int alt102 = 3;
                    switch (input.LA(1)) {
                        case 'n': {
                            alt102 = 1;
                        }
                        break;
                        case 'N': {
                            alt102 = 2;
                        }
                        break;
                        case '0':
                        case '4':
                        case '6': {
                            alt102 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 102, 0, input);

                            throw nvae;
                    }

                    switch (alt102) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:170:10: 'n'
                        {
                            match('n');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:171:10: 'N'
                        {
                            match('N');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:172:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'E' | 'e' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:172:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt101 = 2;
                            int LA101_0 = input.LA(1);

                            if ((LA101_0 == '0')) {
                                alt101 = 1;
                            }
                            switch (alt101) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:172:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:172:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt100 = 2;
                                    int LA100_0 = input.LA(1);

                                    if ((LA100_0 == '0')) {
                                        alt100 = 1;
                                    }
                                    switch (alt100) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:172:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:172:20: ( '0' ( '0' )? )?
                                            int alt99 = 2;
                                            int LA99_0 = input.LA(1);

                                            if ((LA99_0 == '0')) {
                                                alt99 = 1;
                                            }
                                            switch (alt99) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:172:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:172:25: ( '0' )?
                                                    int alt98 = 2;
                                                    int LA98_0 = input.LA(1);

                                                    if ((LA98_0 == '0')) {
                                                        alt98 = 1;
                                                    }
                                                    switch (alt98) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:172:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '4' || input.LA(1) == '6') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "N"

    // $ANTLR start "O"
    public final void mO() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:175:12: ( ( 'o' | 'O' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'o' | 'O' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'F' | 'f' ) ) )
            int alt110 = 2;
            int LA110_0 = input.LA(1);

            if ((LA110_0 == 'O' || LA110_0 == 'o')) {
                alt110 = 1;
            } else if ((LA110_0 == '\\')) {
                alt110 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 110, 0, input);

                throw nvae;
            }
            switch (alt110) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:175:14: ( 'o' | 'O' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'O' || input.LA(1) == 'o') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:175:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop104:
                    do {
                        int alt104 = 2;
                        int LA104_0 = input.LA(1);

                        if (((LA104_0 >= '\t' && LA104_0 <= '\n') || (LA104_0 >= '\f' && LA104_0 <= '\r') || LA104_0 == ' ')) {
                            alt104 = 1;
                        }


                        switch (alt104) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop104;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:176:7: '\\\\' ( 'o' | 'O' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'F' | 'f' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:177:7: ( 'o' | 'O' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'F' | 'f' ) )
                    int alt109 = 3;
                    switch (input.LA(1)) {
                        case 'o': {
                            alt109 = 1;
                        }
                        break;
                        case 'O': {
                            alt109 = 2;
                        }
                        break;
                        case '0':
                        case '4':
                        case '6': {
                            alt109 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 109, 0, input);

                            throw nvae;
                    }

                    switch (alt109) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:178:10: 'o'
                        {
                            match('o');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:179:10: 'O'
                        {
                            match('O');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:180:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '4' | '6' ) ( 'F' | 'f' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:180:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt108 = 2;
                            int LA108_0 = input.LA(1);

                            if ((LA108_0 == '0')) {
                                alt108 = 1;
                            }
                            switch (alt108) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:180:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:180:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt107 = 2;
                                    int LA107_0 = input.LA(1);

                                    if ((LA107_0 == '0')) {
                                        alt107 = 1;
                                    }
                                    switch (alt107) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:180:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:180:20: ( '0' ( '0' )? )?
                                            int alt106 = 2;
                                            int LA106_0 = input.LA(1);

                                            if ((LA106_0 == '0')) {
                                                alt106 = 1;
                                            }
                                            switch (alt106) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:180:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:180:25: ( '0' )?
                                                    int alt105 = 2;
                                                    int LA105_0 = input.LA(1);

                                                    if ((LA105_0 == '0')) {
                                                        alt105 = 1;
                                                    }
                                                    switch (alt105) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:180:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '4' || input.LA(1) == '6') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            if (input.LA(1) == 'F' || input.LA(1) == 'f') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "O"

    // $ANTLR start "P"
    public final void mP() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:183:12: ( ( 'p' | 'P' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'p' | 'P' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '0' ) ) )
            int alt117 = 2;
            int LA117_0 = input.LA(1);

            if ((LA117_0 == 'P' || LA117_0 == 'p')) {
                alt117 = 1;
            } else if ((LA117_0 == '\\')) {
                alt117 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 117, 0, input);

                throw nvae;
            }
            switch (alt117) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:183:14: ( 'p' | 'P' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'P' || input.LA(1) == 'p') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:183:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop111:
                    do {
                        int alt111 = 2;
                        int LA111_0 = input.LA(1);

                        if (((LA111_0 >= '\t' && LA111_0 <= '\n') || (LA111_0 >= '\f' && LA111_0 <= '\r') || LA111_0 == ' ')) {
                            alt111 = 1;
                        }


                        switch (alt111) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop111;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:184:7: '\\\\' ( 'p' | 'P' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '0' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:185:7: ( 'p' | 'P' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '0' ) )
                    int alt116 = 3;
                    switch (input.LA(1)) {
                        case 'p': {
                            alt116 = 1;
                        }
                        break;
                        case 'P': {
                            alt116 = 2;
                        }
                        break;
                        case '0':
                        case '5':
                        case '7': {
                            alt116 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 116, 0, input);

                            throw nvae;
                    }

                    switch (alt116) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:186:10: 'p'
                        {
                            match('p');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:187:10: 'P'
                        {
                            match('P');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:188:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '0' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:188:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt115 = 2;
                            int LA115_0 = input.LA(1);

                            if ((LA115_0 == '0')) {
                                alt115 = 1;
                            }
                            switch (alt115) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:188:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:188:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt114 = 2;
                                    int LA114_0 = input.LA(1);

                                    if ((LA114_0 == '0')) {
                                        alt114 = 1;
                                    }
                                    switch (alt114) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:188:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:188:20: ( '0' ( '0' )? )?
                                            int alt113 = 2;
                                            int LA113_0 = input.LA(1);

                                            if ((LA113_0 == '0')) {
                                                alt113 = 1;
                                            }
                                            switch (alt113) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:188:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:188:25: ( '0' )?
                                                    int alt112 = 2;
                                                    int LA112_0 = input.LA(1);

                                                    if ((LA112_0 == '0')) {
                                                        alt112 = 1;
                                                    }
                                                    switch (alt112) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:188:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '5' || input.LA(1) == '7') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:188:45: ( '0' )
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:188:46: '0'
                            {
                                match('0');
                                if (state.failed) {
                                    return;
                                }

                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "P"

    // $ANTLR start "Q"
    public final void mQ() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:191:12: ( ( 'q' | 'Q' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'q' | 'Q' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '1' ) ) )
            int alt124 = 2;
            int LA124_0 = input.LA(1);

            if ((LA124_0 == 'Q' || LA124_0 == 'q')) {
                alt124 = 1;
            } else if ((LA124_0 == '\\')) {
                alt124 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 124, 0, input);

                throw nvae;
            }
            switch (alt124) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:191:14: ( 'q' | 'Q' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'Q' || input.LA(1) == 'q') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:191:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop118:
                    do {
                        int alt118 = 2;
                        int LA118_0 = input.LA(1);

                        if (((LA118_0 >= '\t' && LA118_0 <= '\n') || (LA118_0 >= '\f' && LA118_0 <= '\r') || LA118_0 == ' ')) {
                            alt118 = 1;
                        }


                        switch (alt118) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop118;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:192:7: '\\\\' ( 'q' | 'Q' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '1' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:193:7: ( 'q' | 'Q' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '1' ) )
                    int alt123 = 3;
                    switch (input.LA(1)) {
                        case 'q': {
                            alt123 = 1;
                        }
                        break;
                        case 'Q': {
                            alt123 = 2;
                        }
                        break;
                        case '0':
                        case '5':
                        case '7': {
                            alt123 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 123, 0, input);

                            throw nvae;
                    }

                    switch (alt123) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:194:10: 'q'
                        {
                            match('q');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:195:10: 'Q'
                        {
                            match('Q');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:196:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '1' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:196:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt122 = 2;
                            int LA122_0 = input.LA(1);

                            if ((LA122_0 == '0')) {
                                alt122 = 1;
                            }
                            switch (alt122) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:196:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:196:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt121 = 2;
                                    int LA121_0 = input.LA(1);

                                    if ((LA121_0 == '0')) {
                                        alt121 = 1;
                                    }
                                    switch (alt121) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:196:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:196:20: ( '0' ( '0' )? )?
                                            int alt120 = 2;
                                            int LA120_0 = input.LA(1);

                                            if ((LA120_0 == '0')) {
                                                alt120 = 1;
                                            }
                                            switch (alt120) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:196:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:196:25: ( '0' )?
                                                    int alt119 = 2;
                                                    int LA119_0 = input.LA(1);

                                                    if ((LA119_0 == '0')) {
                                                        alt119 = 1;
                                                    }
                                                    switch (alt119) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:196:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '5' || input.LA(1) == '7') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:196:45: ( '1' )
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:196:46: '1'
                            {
                                match('1');
                                if (state.failed) {
                                    return;
                                }

                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "Q"

    // $ANTLR start "R"
    public final void mR() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:199:12: ( ( 'r' | 'R' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'r' | 'R' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '2' ) ) )
            int alt131 = 2;
            int LA131_0 = input.LA(1);

            if ((LA131_0 == 'R' || LA131_0 == 'r')) {
                alt131 = 1;
            } else if ((LA131_0 == '\\')) {
                alt131 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 131, 0, input);

                throw nvae;
            }
            switch (alt131) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:199:14: ( 'r' | 'R' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:199:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop125:
                    do {
                        int alt125 = 2;
                        int LA125_0 = input.LA(1);

                        if (((LA125_0 >= '\t' && LA125_0 <= '\n') || (LA125_0 >= '\f' && LA125_0 <= '\r') || LA125_0 == ' ')) {
                            alt125 = 1;
                        }


                        switch (alt125) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop125;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:200:7: '\\\\' ( 'r' | 'R' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '2' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:201:7: ( 'r' | 'R' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '2' ) )
                    int alt130 = 3;
                    switch (input.LA(1)) {
                        case 'r': {
                            alt130 = 1;
                        }
                        break;
                        case 'R': {
                            alt130 = 2;
                        }
                        break;
                        case '0':
                        case '5':
                        case '7': {
                            alt130 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 130, 0, input);

                            throw nvae;
                    }

                    switch (alt130) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:202:10: 'r'
                        {
                            match('r');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:203:10: 'R'
                        {
                            match('R');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:204:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '2' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:204:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt129 = 2;
                            int LA129_0 = input.LA(1);

                            if ((LA129_0 == '0')) {
                                alt129 = 1;
                            }
                            switch (alt129) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:204:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:204:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt128 = 2;
                                    int LA128_0 = input.LA(1);

                                    if ((LA128_0 == '0')) {
                                        alt128 = 1;
                                    }
                                    switch (alt128) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:204:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:204:20: ( '0' ( '0' )? )?
                                            int alt127 = 2;
                                            int LA127_0 = input.LA(1);

                                            if ((LA127_0 == '0')) {
                                                alt127 = 1;
                                            }
                                            switch (alt127) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:204:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:204:25: ( '0' )?
                                                    int alt126 = 2;
                                                    int LA126_0 = input.LA(1);

                                                    if ((LA126_0 == '0')) {
                                                        alt126 = 1;
                                                    }
                                                    switch (alt126) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:204:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '5' || input.LA(1) == '7') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:204:45: ( '2' )
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:204:46: '2'
                            {
                                match('2');
                                if (state.failed) {
                                    return;
                                }

                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "R"

    // $ANTLR start "S"
    public final void mS() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:207:12: ( ( 's' | 'S' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 's' | 'S' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '3' ) ) )
            int alt138 = 2;
            int LA138_0 = input.LA(1);

            if ((LA138_0 == 'S' || LA138_0 == 's')) {
                alt138 = 1;
            } else if ((LA138_0 == '\\')) {
                alt138 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 138, 0, input);

                throw nvae;
            }
            switch (alt138) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:207:14: ( 's' | 'S' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'S' || input.LA(1) == 's') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:207:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop132:
                    do {
                        int alt132 = 2;
                        int LA132_0 = input.LA(1);

                        if (((LA132_0 >= '\t' && LA132_0 <= '\n') || (LA132_0 >= '\f' && LA132_0 <= '\r') || LA132_0 == ' ')) {
                            alt132 = 1;
                        }


                        switch (alt132) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop132;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:208:7: '\\\\' ( 's' | 'S' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '3' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:209:7: ( 's' | 'S' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '3' ) )
                    int alt137 = 3;
                    switch (input.LA(1)) {
                        case 's': {
                            alt137 = 1;
                        }
                        break;
                        case 'S': {
                            alt137 = 2;
                        }
                        break;
                        case '0':
                        case '5':
                        case '7': {
                            alt137 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 137, 0, input);

                            throw nvae;
                    }

                    switch (alt137) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:210:10: 's'
                        {
                            match('s');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:211:10: 'S'
                        {
                            match('S');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:212:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '3' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:212:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt136 = 2;
                            int LA136_0 = input.LA(1);

                            if ((LA136_0 == '0')) {
                                alt136 = 1;
                            }
                            switch (alt136) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:212:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:212:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt135 = 2;
                                    int LA135_0 = input.LA(1);

                                    if ((LA135_0 == '0')) {
                                        alt135 = 1;
                                    }
                                    switch (alt135) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:212:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:212:20: ( '0' ( '0' )? )?
                                            int alt134 = 2;
                                            int LA134_0 = input.LA(1);

                                            if ((LA134_0 == '0')) {
                                                alt134 = 1;
                                            }
                                            switch (alt134) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:212:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:212:25: ( '0' )?
                                                    int alt133 = 2;
                                                    int LA133_0 = input.LA(1);

                                                    if ((LA133_0 == '0')) {
                                                        alt133 = 1;
                                                    }
                                                    switch (alt133) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:212:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '5' || input.LA(1) == '7') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:212:45: ( '3' )
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:212:46: '3'
                            {
                                match('3');
                                if (state.failed) {
                                    return;
                                }

                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "S"

    // $ANTLR start "T"
    public final void mT() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:215:12: ( ( 't' | 'T' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 't' | 'T' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '4' ) ) )
            int alt145 = 2;
            int LA145_0 = input.LA(1);

            if ((LA145_0 == 'T' || LA145_0 == 't')) {
                alt145 = 1;
            } else if ((LA145_0 == '\\')) {
                alt145 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 145, 0, input);

                throw nvae;
            }
            switch (alt145) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:215:14: ( 't' | 'T' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'T' || input.LA(1) == 't') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:215:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop139:
                    do {
                        int alt139 = 2;
                        int LA139_0 = input.LA(1);

                        if (((LA139_0 >= '\t' && LA139_0 <= '\n') || (LA139_0 >= '\f' && LA139_0 <= '\r') || LA139_0 == ' ')) {
                            alt139 = 1;
                        }


                        switch (alt139) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop139;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:216:7: '\\\\' ( 't' | 'T' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '4' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:217:7: ( 't' | 'T' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '4' ) )
                    int alt144 = 3;
                    switch (input.LA(1)) {
                        case 't': {
                            alt144 = 1;
                        }
                        break;
                        case 'T': {
                            alt144 = 2;
                        }
                        break;
                        case '0':
                        case '5':
                        case '7': {
                            alt144 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 144, 0, input);

                            throw nvae;
                    }

                    switch (alt144) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:218:10: 't'
                        {
                            match('t');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:219:10: 'T'
                        {
                            match('T');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:220:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '4' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:220:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt143 = 2;
                            int LA143_0 = input.LA(1);

                            if ((LA143_0 == '0')) {
                                alt143 = 1;
                            }
                            switch (alt143) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:220:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:220:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt142 = 2;
                                    int LA142_0 = input.LA(1);

                                    if ((LA142_0 == '0')) {
                                        alt142 = 1;
                                    }
                                    switch (alt142) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:220:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:220:20: ( '0' ( '0' )? )?
                                            int alt141 = 2;
                                            int LA141_0 = input.LA(1);

                                            if ((LA141_0 == '0')) {
                                                alt141 = 1;
                                            }
                                            switch (alt141) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:220:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:220:25: ( '0' )?
                                                    int alt140 = 2;
                                                    int LA140_0 = input.LA(1);

                                                    if ((LA140_0 == '0')) {
                                                        alt140 = 1;
                                                    }
                                                    switch (alt140) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:220:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '5' || input.LA(1) == '7') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:220:45: ( '4' )
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:220:46: '4'
                            {
                                match('4');
                                if (state.failed) {
                                    return;
                                }

                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "T"

    // $ANTLR start "U"
    public final void mU() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:223:12: ( ( 'u' | 'U' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'u' | 'U' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '5' ) ) )
            int alt152 = 2;
            int LA152_0 = input.LA(1);

            if ((LA152_0 == 'U' || LA152_0 == 'u')) {
                alt152 = 1;
            } else if ((LA152_0 == '\\')) {
                alt152 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 152, 0, input);

                throw nvae;
            }
            switch (alt152) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:223:14: ( 'u' | 'U' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'U' || input.LA(1) == 'u') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:223:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop146:
                    do {
                        int alt146 = 2;
                        int LA146_0 = input.LA(1);

                        if (((LA146_0 >= '\t' && LA146_0 <= '\n') || (LA146_0 >= '\f' && LA146_0 <= '\r') || LA146_0 == ' ')) {
                            alt146 = 1;
                        }


                        switch (alt146) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop146;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:224:7: '\\\\' ( 'u' | 'U' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '5' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:225:7: ( 'u' | 'U' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '5' ) )
                    int alt151 = 3;
                    switch (input.LA(1)) {
                        case 'u': {
                            alt151 = 1;
                        }
                        break;
                        case 'U': {
                            alt151 = 2;
                        }
                        break;
                        case '0':
                        case '5':
                        case '7': {
                            alt151 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 151, 0, input);

                            throw nvae;
                    }

                    switch (alt151) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:226:10: 'u'
                        {
                            match('u');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:227:10: 'U'
                        {
                            match('U');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:228:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '5' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:228:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt150 = 2;
                            int LA150_0 = input.LA(1);

                            if ((LA150_0 == '0')) {
                                alt150 = 1;
                            }
                            switch (alt150) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:228:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:228:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt149 = 2;
                                    int LA149_0 = input.LA(1);

                                    if ((LA149_0 == '0')) {
                                        alt149 = 1;
                                    }
                                    switch (alt149) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:228:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:228:20: ( '0' ( '0' )? )?
                                            int alt148 = 2;
                                            int LA148_0 = input.LA(1);

                                            if ((LA148_0 == '0')) {
                                                alt148 = 1;
                                            }
                                            switch (alt148) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:228:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:228:25: ( '0' )?
                                                    int alt147 = 2;
                                                    int LA147_0 = input.LA(1);

                                                    if ((LA147_0 == '0')) {
                                                        alt147 = 1;
                                                    }
                                                    switch (alt147) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:228:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '5' || input.LA(1) == '7') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:228:45: ( '5' )
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:228:46: '5'
                            {
                                match('5');
                                if (state.failed) {
                                    return;
                                }

                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "U"

    // $ANTLR start "V"
    public final void mV() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:231:12: ( ( 'v' | 'V' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'v' | 'V' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '6' ) ) )
            int alt159 = 2;
            int LA159_0 = input.LA(1);

            if ((LA159_0 == 'V' || LA159_0 == 'v')) {
                alt159 = 1;
            } else if ((LA159_0 == '\\')) {
                alt159 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 159, 0, input);

                throw nvae;
            }
            switch (alt159) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:231:14: ( 'v' | 'V' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'V' || input.LA(1) == 'v') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:231:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop153:
                    do {
                        int alt153 = 2;
                        int LA153_0 = input.LA(1);

                        if (((LA153_0 >= '\t' && LA153_0 <= '\n') || (LA153_0 >= '\f' && LA153_0 <= '\r') || LA153_0 == ' ')) {
                            alt153 = 1;
                        }


                        switch (alt153) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop153;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:232:7: '\\\\' ( 'v' | 'V' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '6' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:233:7: ( 'v' | 'V' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '6' ) )
                    int alt158 = 3;
                    switch (input.LA(1)) {
                        case 'v': {
                            alt158 = 1;
                        }
                        break;
                        case 'V': {
                            alt158 = 2;
                        }
                        break;
                        case '0':
                        case '5':
                        case '7': {
                            alt158 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 158, 0, input);

                            throw nvae;
                    }

                    switch (alt158) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:233:11: 'v'
                        {
                            match('v');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:234:10: 'V'
                        {
                            match('V');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:235:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '6' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:235:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt157 = 2;
                            int LA157_0 = input.LA(1);

                            if ((LA157_0 == '0')) {
                                alt157 = 1;
                            }
                            switch (alt157) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:235:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:235:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt156 = 2;
                                    int LA156_0 = input.LA(1);

                                    if ((LA156_0 == '0')) {
                                        alt156 = 1;
                                    }
                                    switch (alt156) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:235:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:235:20: ( '0' ( '0' )? )?
                                            int alt155 = 2;
                                            int LA155_0 = input.LA(1);

                                            if ((LA155_0 == '0')) {
                                                alt155 = 1;
                                            }
                                            switch (alt155) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:235:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:235:25: ( '0' )?
                                                    int alt154 = 2;
                                                    int LA154_0 = input.LA(1);

                                                    if ((LA154_0 == '0')) {
                                                        alt154 = 1;
                                                    }
                                                    switch (alt154) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:235:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '5' || input.LA(1) == '7') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:235:45: ( '6' )
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:235:46: '6'
                            {
                                match('6');
                                if (state.failed) {
                                    return;
                                }

                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "V"

    // $ANTLR start "W"
    public final void mW() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:238:12: ( ( 'w' | 'W' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'w' | 'W' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '7' ) ) )
            int alt166 = 2;
            int LA166_0 = input.LA(1);

            if ((LA166_0 == 'W' || LA166_0 == 'w')) {
                alt166 = 1;
            } else if ((LA166_0 == '\\')) {
                alt166 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 166, 0, input);

                throw nvae;
            }
            switch (alt166) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:238:14: ( 'w' | 'W' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'W' || input.LA(1) == 'w') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:238:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop160:
                    do {
                        int alt160 = 2;
                        int LA160_0 = input.LA(1);

                        if (((LA160_0 >= '\t' && LA160_0 <= '\n') || (LA160_0 >= '\f' && LA160_0 <= '\r') || LA160_0 == ' ')) {
                            alt160 = 1;
                        }


                        switch (alt160) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop160;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:239:7: '\\\\' ( 'w' | 'W' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '7' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:240:7: ( 'w' | 'W' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '7' ) )
                    int alt165 = 3;
                    switch (input.LA(1)) {
                        case 'w': {
                            alt165 = 1;
                        }
                        break;
                        case 'W': {
                            alt165 = 2;
                        }
                        break;
                        case '0':
                        case '5':
                        case '7': {
                            alt165 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 165, 0, input);

                            throw nvae;
                    }

                    switch (alt165) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:241:10: 'w'
                        {
                            match('w');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:242:10: 'W'
                        {
                            match('W');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:243:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '7' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:243:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt164 = 2;
                            int LA164_0 = input.LA(1);

                            if ((LA164_0 == '0')) {
                                alt164 = 1;
                            }
                            switch (alt164) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:243:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:243:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt163 = 2;
                                    int LA163_0 = input.LA(1);

                                    if ((LA163_0 == '0')) {
                                        alt163 = 1;
                                    }
                                    switch (alt163) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:243:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:243:20: ( '0' ( '0' )? )?
                                            int alt162 = 2;
                                            int LA162_0 = input.LA(1);

                                            if ((LA162_0 == '0')) {
                                                alt162 = 1;
                                            }
                                            switch (alt162) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:243:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:243:25: ( '0' )?
                                                    int alt161 = 2;
                                                    int LA161_0 = input.LA(1);

                                                    if ((LA161_0 == '0')) {
                                                        alt161 = 1;
                                                    }
                                                    switch (alt161) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:243:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '5' || input.LA(1) == '7') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:243:45: ( '7' )
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:243:46: '7'
                            {
                                match('7');
                                if (state.failed) {
                                    return;
                                }

                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "W"

    // $ANTLR start "X"
    public final void mX() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:246:12: ( ( 'x' | 'X' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'x' | 'X' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '8' ) ) )
            int alt173 = 2;
            int LA173_0 = input.LA(1);

            if ((LA173_0 == 'X' || LA173_0 == 'x')) {
                alt173 = 1;
            } else if ((LA173_0 == '\\')) {
                alt173 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 173, 0, input);

                throw nvae;
            }
            switch (alt173) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:246:14: ( 'x' | 'X' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'X' || input.LA(1) == 'x') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:246:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop167:
                    do {
                        int alt167 = 2;
                        int LA167_0 = input.LA(1);

                        if (((LA167_0 >= '\t' && LA167_0 <= '\n') || (LA167_0 >= '\f' && LA167_0 <= '\r') || LA167_0 == ' ')) {
                            alt167 = 1;
                        }


                        switch (alt167) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop167;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:247:7: '\\\\' ( 'x' | 'X' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '8' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:248:7: ( 'x' | 'X' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '8' ) )
                    int alt172 = 3;
                    switch (input.LA(1)) {
                        case 'x': {
                            alt172 = 1;
                        }
                        break;
                        case 'X': {
                            alt172 = 2;
                        }
                        break;
                        case '0':
                        case '5':
                        case '7': {
                            alt172 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 172, 0, input);

                            throw nvae;
                    }

                    switch (alt172) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:249:10: 'x'
                        {
                            match('x');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:250:10: 'X'
                        {
                            match('X');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:251:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '8' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:251:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt171 = 2;
                            int LA171_0 = input.LA(1);

                            if ((LA171_0 == '0')) {
                                alt171 = 1;
                            }
                            switch (alt171) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:251:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:251:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt170 = 2;
                                    int LA170_0 = input.LA(1);

                                    if ((LA170_0 == '0')) {
                                        alt170 = 1;
                                    }
                                    switch (alt170) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:251:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:251:20: ( '0' ( '0' )? )?
                                            int alt169 = 2;
                                            int LA169_0 = input.LA(1);

                                            if ((LA169_0 == '0')) {
                                                alt169 = 1;
                                            }
                                            switch (alt169) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:251:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:251:25: ( '0' )?
                                                    int alt168 = 2;
                                                    int LA168_0 = input.LA(1);

                                                    if ((LA168_0 == '0')) {
                                                        alt168 = 1;
                                                    }
                                                    switch (alt168) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:251:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '5' || input.LA(1) == '7') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:251:45: ( '8' )
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:251:46: '8'
                            {
                                match('8');
                                if (state.failed) {
                                    return;
                                }

                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "X"

    // $ANTLR start "Y"
    public final void mY() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:254:12: ( ( 'y' | 'Y' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'y' | 'Y' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '9' ) ) )
            int alt180 = 2;
            int LA180_0 = input.LA(1);

            if ((LA180_0 == 'Y' || LA180_0 == 'y')) {
                alt180 = 1;
            } else if ((LA180_0 == '\\')) {
                alt180 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 180, 0, input);

                throw nvae;
            }
            switch (alt180) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:254:14: ( 'y' | 'Y' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'Y' || input.LA(1) == 'y') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:254:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop174:
                    do {
                        int alt174 = 2;
                        int LA174_0 = input.LA(1);

                        if (((LA174_0 >= '\t' && LA174_0 <= '\n') || (LA174_0 >= '\f' && LA174_0 <= '\r') || LA174_0 == ' ')) {
                            alt174 = 1;
                        }


                        switch (alt174) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop174;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:255:7: '\\\\' ( 'y' | 'Y' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '9' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:256:7: ( 'y' | 'Y' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '9' ) )
                    int alt179 = 3;
                    switch (input.LA(1)) {
                        case 'y': {
                            alt179 = 1;
                        }
                        break;
                        case 'Y': {
                            alt179 = 2;
                        }
                        break;
                        case '0':
                        case '5':
                        case '7': {
                            alt179 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 179, 0, input);

                            throw nvae;
                    }

                    switch (alt179) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:257:10: 'y'
                        {
                            match('y');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:258:10: 'Y'
                        {
                            match('Y');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:259:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( '9' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:259:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt178 = 2;
                            int LA178_0 = input.LA(1);

                            if ((LA178_0 == '0')) {
                                alt178 = 1;
                            }
                            switch (alt178) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:259:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:259:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt177 = 2;
                                    int LA177_0 = input.LA(1);

                                    if ((LA177_0 == '0')) {
                                        alt177 = 1;
                                    }
                                    switch (alt177) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:259:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:259:20: ( '0' ( '0' )? )?
                                            int alt176 = 2;
                                            int LA176_0 = input.LA(1);

                                            if ((LA176_0 == '0')) {
                                                alt176 = 1;
                                            }
                                            switch (alt176) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:259:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:259:25: ( '0' )?
                                                    int alt175 = 2;
                                                    int LA175_0 = input.LA(1);

                                                    if ((LA175_0 == '0')) {
                                                        alt175 = 1;
                                                    }
                                                    switch (alt175) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:259:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '5' || input.LA(1) == '7') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:259:45: ( '9' )
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:259:46: '9'
                            {
                                match('9');
                                if (state.failed) {
                                    return;
                                }

                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "Y"

    // $ANTLR start "Z"
    public final void mZ() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:262:12: ( ( 'a' | 'Z' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )* | '\\\\' ( 'z' | 'Z' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( 'A' | 'a' ) ) )
            int alt187 = 2;
            int LA187_0 = input.LA(1);

            if ((LA187_0 == 'Z' || LA187_0 == 'a')) {
                alt187 = 1;
            } else if ((LA187_0 == '\\')) {
                alt187 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 187, 0, input);

                throw nvae;
            }
            switch (alt187) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:262:14: ( 'a' | 'Z' ) ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                {
                    if (input.LA(1) == 'Z' || input.LA(1) == 'a') {
                        input.consume();
                        state.failed = false;
                    } else {
                        if (state.backtracking > 0) {
                            state.failed = true;
                            return;
                        }
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        recover(mse);
                        throw mse;
                    }

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:262:24: ( '\\r' | '\\n' | '\\t' | '\\f' | ' ' )*
                    loop181:
                    do {
                        int alt181 = 2;
                        int LA181_0 = input.LA(1);

                        if (((LA181_0 >= '\t' && LA181_0 <= '\n') || (LA181_0 >= '\f' && LA181_0 <= '\r') || LA181_0 == ' ')) {
                            alt181 = 1;
                        }


                        switch (alt181) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                            {
                                if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ') {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop181;
                        }
                    } while (true);


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:263:7: '\\\\' ( 'z' | 'Z' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( 'A' | 'a' ) )
                {
                    match('\\');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:264:7: ( 'z' | 'Z' | ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( 'A' | 'a' ) )
                    int alt186 = 3;
                    switch (input.LA(1)) {
                        case 'z': {
                            alt186 = 1;
                        }
                        break;
                        case 'Z': {
                            alt186 = 2;
                        }
                        break;
                        case '0':
                        case '5':
                        case '7': {
                            alt186 = 3;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 186, 0, input);

                            throw nvae;
                    }

                    switch (alt186) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:265:10: 'z'
                        {
                            match('z');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:266:10: 'Z'
                        {
                            match('Z');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:267:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )? ( '5' | '7' ) ( 'A' | 'a' )
                        {
                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:267:10: ( '0' ( '0' ( '0' ( '0' )? )? )? )?
                            int alt185 = 2;
                            int LA185_0 = input.LA(1);

                            if ((LA185_0 == '0')) {
                                alt185 = 1;
                            }
                            switch (alt185) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:267:11: '0' ( '0' ( '0' ( '0' )? )? )?
                                {
                                    match('0');
                                    if (state.failed) {
                                        return;
                                    }
                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:267:15: ( '0' ( '0' ( '0' )? )? )?
                                    int alt184 = 2;
                                    int LA184_0 = input.LA(1);

                                    if ((LA184_0 == '0')) {
                                        alt184 = 1;
                                    }
                                    switch (alt184) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:267:16: '0' ( '0' ( '0' )? )?
                                        {
                                            match('0');
                                            if (state.failed) {
                                                return;
                                            }
                                            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:267:20: ( '0' ( '0' )? )?
                                            int alt183 = 2;
                                            int LA183_0 = input.LA(1);

                                            if ((LA183_0 == '0')) {
                                                alt183 = 1;
                                            }
                                            switch (alt183) {
                                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:267:21: '0' ( '0' )?
                                                {
                                                    match('0');
                                                    if (state.failed) {
                                                        return;
                                                    }
                                                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:267:25: ( '0' )?
                                                    int alt182 = 2;
                                                    int LA182_0 = input.LA(1);

                                                    if ((LA182_0 == '0')) {
                                                        alt182 = 1;
                                                    }
                                                    switch (alt182) {
                                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:267:25: '0'
                                                        {
                                                            match('0');
                                                            if (state.failed) {
                                                                return;
                                                            }

                                                        }
                                                        break;

                                                    }


                                                }
                                                break;

                                            }


                                        }
                                        break;

                                    }


                                }
                                break;

                            }

                            if (input.LA(1) == '5' || input.LA(1) == '7') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }

                            if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }


                        }
                        break;

                    }


                }
                break;

            }
        } finally {
        }
    }
    // $ANTLR end "Z"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:278:11: ( '/*' ( options {greedy=false; } : ( . )* ) '*/' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:278:13: '/*' ( options {greedy=false; } : ( . )* ) '*/'
            {
                match("/*");
                if (state.failed) {
                    return;
                }

                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:278:18: ( options {greedy=false; } : ( . )* )
                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:278:48: ( . )*
                {
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:278:48: ( . )*
                    loop188:
                    do {
                        int alt188 = 2;
                        int LA188_0 = input.LA(1);

                        if ((LA188_0 == '*')) {
                            int LA188_1 = input.LA(2);

                            if ((LA188_1 == '/')) {
                                alt188 = 2;
                            } else if (((LA188_1 >= '\u0000' && LA188_1 <= '.') || (LA188_1 >= '0' && LA188_1 <= '\uFFFF'))) {
                                alt188 = 1;
                            }


                        } else if (((LA188_0 >= '\u0000' && LA188_0 <= ')') || (LA188_0 >= '+' && LA188_0 <= '\uFFFF'))) {
                            alt188 = 1;
                        }


                        switch (alt188) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:278:48: .
                            {
                                matchAny();
                                if (state.failed) {
                                    return;
                                }

                            }
                            break;

                            default:
                                break loop188;
                        }
                    } while (true);


                }

                match("*/");
                if (state.failed) {
                    return;
                }

                if (state.backtracking == 0) {

                    _channel = 2;	// Comments on channel 2 in case we want to find them

                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "CDO"
    public final void mCDO() throws RecognitionException {
        try {
            int _type = CDO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:291:8: ( '<!--' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:291:10: '<!--'
            {
                match("<!--");
                if (state.failed) {
                    return;
                }

                if (state.backtracking == 0) {

                    _channel = 3;	// CDO on channel 3 in case we want it later

                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "CDO"

    // $ANTLR start "CDC"
    public final void mCDC() throws RecognitionException {
        try {
            int _type = CDC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:304:8: ( '-->' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:304:10: '-->'
            {
                match("-->");
                if (state.failed) {
                    return;
                }

                if (state.backtracking == 0) {

                    _channel = 4;	// CDC on channel 4 in case we want it later

                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "CDC"

    // $ANTLR start "INCLUDES"
    public final void mINCLUDES() throws RecognitionException {
        try {
            int _type = INCLUDES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:311:11: ( '~=' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:311:13: '~='
            {
                match("~=");
                if (state.failed) {
                    return;
                }


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "INCLUDES"

    // $ANTLR start "DASHMATCH"
    public final void mDASHMATCH() throws RecognitionException {
        try {
            int _type = DASHMATCH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:312:12: ( '|=' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:312:14: '|='
            {
                match("|=");
                if (state.failed) {
                    return;
                }


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "DASHMATCH"

    // $ANTLR start "GREATER"
    public final void mGREATER() throws RecognitionException {
        try {
            int _type = GREATER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:314:11: ( '>' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:314:13: '>'
            {
                match('>');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "GREATER"

    // $ANTLR start "LBRACE"
    public final void mLBRACE() throws RecognitionException {
        try {
            int _type = LBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:315:10: ( '{' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:315:12: '{'
            {
                match('{');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "LBRACE"

    // $ANTLR start "RBRACE"
    public final void mRBRACE() throws RecognitionException {
        try {
            int _type = RBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:316:10: ( '}' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:316:12: '}'
            {
                match('}');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "RBRACE"

    // $ANTLR start "LBRACKET"
    public final void mLBRACKET() throws RecognitionException {
        try {
            int _type = LBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:317:11: ( '[' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:317:13: '['
            {
                match('[');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "LBRACKET"

    // $ANTLR start "RBRACKET"
    public final void mRBRACKET() throws RecognitionException {
        try {
            int _type = RBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:318:11: ( ']' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:318:13: ']'
            {
                match(']');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "RBRACKET"

    // $ANTLR start "OPEQ"
    public final void mOPEQ() throws RecognitionException {
        try {
            int _type = OPEQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:319:8: ( '=' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:319:10: '='
            {
                match('=');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "OPEQ"

    // $ANTLR start "SEMI"
    public final void mSEMI() throws RecognitionException {
        try {
            int _type = SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:320:8: ( ';' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:320:10: ';'
            {
                match(';');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "SEMI"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:321:9: ( ':' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:321:11: ':'
            {
                match(':');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "SOLIDUS"
    public final void mSOLIDUS() throws RecognitionException {
        try {
            int _type = SOLIDUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:322:11: ( '/' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:322:13: '/'
            {
                match('/');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "SOLIDUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:323:9: ( '-' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:323:11: '-'
            {
                match('-');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:324:8: ( '+' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:324:10: '+'
            {
                match('+');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "STAR"
    public final void mSTAR() throws RecognitionException {
        try {
            int _type = STAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:325:8: ( '*' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:325:10: '*'
            {
                match('*');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "STAR"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:326:10: ( '(' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:326:12: '('
            {
                match('(');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:327:10: ( ')' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:327:12: ')'
            {
                match(')');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:328:9: ( ',' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:328:11: ','
            {
                match(',');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:329:8: ( '.' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:329:10: '.'
            {
                match('.');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "INVALID"
    public final void mINVALID() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:334:18: ()
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:334:19:
            {
            }

        } finally {
        }
    }
    // $ANTLR end "INVALID"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:335:10: ( '\\'' (~ ( '\\n' | '\\r' | '\\f' | '\\'' ) )* ( '\\'' | ) | '\"' (~ ( '\\n' | '\\r' | '\\f' | '\"' ) )* ( '\"' | ) )
            int alt193 = 2;
            int LA193_0 = input.LA(1);

            if ((LA193_0 == '\'')) {
                alt193 = 1;
            } else if ((LA193_0 == '\"')) {
                alt193 = 2;
            } else {
                if (state.backtracking > 0) {
                    state.failed = true;
                    return;
                }
                NoViableAltException nvae =
                        new NoViableAltException("", 193, 0, input);

                throw nvae;
            }
            switch (alt193) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:335:12: '\\'' (~ ( '\\n' | '\\r' | '\\f' | '\\'' ) )* ( '\\'' | )
                {
                    match('\'');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:335:17: (~ ( '\\n' | '\\r' | '\\f' | '\\'' ) )*
                    loop189:
                    do {
                        int alt189 = 2;
                        int LA189_0 = input.LA(1);

                        if (((LA189_0 >= '\u0000' && LA189_0 <= '\t') || LA189_0 == '\u000B' || (LA189_0 >= '\u000E' && LA189_0 <= '&') || (LA189_0 >= '(' && LA189_0 <= '\uFFFF'))) {
                            alt189 = 1;
                        }


                        switch (alt189) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:335:19: ~ ( '\\n' | '\\r' | '\\f' | '\\'' )
                            {
                                if ((input.LA(1) >= '\u0000' && input.LA(1) <= '\t') || input.LA(1) == '\u000B' || (input.LA(1) >= '\u000E' && input.LA(1) <= '&') || (input.LA(1) >= '(' && input.LA(1) <= '\uFFFF')) {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop189;
                        }
                    } while (true);

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:336:6: ( '\\'' | )
                    int alt190 = 2;
                    int LA190_0 = input.LA(1);

                    if ((LA190_0 == '\'')) {
                        alt190 = 1;
                    } else {
                        alt190 = 2;
                    }
                    switch (alt190) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:337:9: '\\''
                        {
                            match('\'');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:338:9:
                        {
                            if (state.backtracking == 0) {
                                _type = INVALID;
                            }

                        }
                        break;

                    }


                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:341:7: '\"' (~ ( '\\n' | '\\r' | '\\f' | '\"' ) )* ( '\"' | )
                {
                    match('\"');
                    if (state.failed) {
                        return;
                    }
                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:341:11: (~ ( '\\n' | '\\r' | '\\f' | '\"' ) )*
                    loop191:
                    do {
                        int alt191 = 2;
                        int LA191_0 = input.LA(1);

                        if (((LA191_0 >= '\u0000' && LA191_0 <= '\t') || LA191_0 == '\u000B' || (LA191_0 >= '\u000E' && LA191_0 <= '!') || (LA191_0 >= '#' && LA191_0 <= '\uFFFF'))) {
                            alt191 = 1;
                        }


                        switch (alt191) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:341:13: ~ ( '\\n' | '\\r' | '\\f' | '\"' )
                            {
                                if ((input.LA(1) >= '\u0000' && input.LA(1) <= '\t') || input.LA(1) == '\u000B' || (input.LA(1) >= '\u000E' && input.LA(1) <= '!') || (input.LA(1) >= '#' && input.LA(1) <= '\uFFFF')) {
                                    input.consume();
                                    state.failed = false;
                                } else {
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    MismatchedSetException mse = new MismatchedSetException(null, input);
                                    recover(mse);
                                    throw mse;
                                }


                            }
                            break;

                            default:
                                break loop191;
                        }
                    } while (true);

                    // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:342:6: ( '\"' | )
                    int alt192 = 2;
                    int LA192_0 = input.LA(1);

                    if ((LA192_0 == '\"')) {
                        alt192 = 1;
                    } else {
                        alt192 = 2;
                    }
                    switch (alt192) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:343:9: '\"'
                        {
                            match('\"');
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:344:9:
                        {
                            if (state.backtracking == 0) {
                                _type = INVALID;
                            }

                        }
                        break;

                    }


                }
                break;

            }
            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "IDENT"
    public final void mIDENT() throws RecognitionException {
        try {
            int _type = IDENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:351:9: ( ( '-' )? NMSTART ( NMCHAR )* )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:351:11: ( '-' )? NMSTART ( NMCHAR )*
            {
                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:351:11: ( '-' )?
                int alt194 = 2;
                int LA194_0 = input.LA(1);

                if ((LA194_0 == '-')) {
                    alt194 = 1;
                }
                switch (alt194) {
                    case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:351:11: '-'
                    {
                        match('-');
                        if (state.failed) {
                            return;
                        }

                    }
                    break;

                }

                mNMSTART();
                if (state.failed) {
                    return;
                }
                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:351:24: ( NMCHAR )*
                loop195:
                do {
                    int alt195 = 2;
                    int LA195_0 = input.LA(1);

                    if ((LA195_0 == '-' || (LA195_0 >= '0' && LA195_0 <= '9') || (LA195_0 >= 'A' && LA195_0 <= 'Z') || LA195_0 == '\\' || LA195_0 == '_' || (LA195_0 >= 'a' && LA195_0 <= 'z') || (LA195_0 >= '\u0080' && LA195_0 <= '\uFFFF'))) {
                        alt195 = 1;
                    }


                    switch (alt195) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:351:24: NMCHAR
                        {
                            mNMCHAR();
                            if (state.failed) {
                                return;
                            }

                        }
                        break;

                        default:
                            break loop195;
                    }
                } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "IDENT"

    // $ANTLR start "HASH"
    public final void mHASH() throws RecognitionException {
        try {
            int _type = HASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:356:8: ( '#' NAME )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:356:10: '#' NAME
            {
                match('#');
                if (state.failed) {
                    return;
                }
                mNAME();
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "HASH"

    // $ANTLR start "IMPORT_SYM"
    public final void mIMPORT_SYM() throws RecognitionException {
        try {
            int _type = IMPORT_SYM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:358:13: ( '@' I M P O R T )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:358:15: '@' I M P O R T
            {
                match('@');
                if (state.failed) {
                    return;
                }
                mI();
                if (state.failed) {
                    return;
                }
                mM();
                if (state.failed) {
                    return;
                }
                mP();
                if (state.failed) {
                    return;
                }
                mO();
                if (state.failed) {
                    return;
                }
                mR();
                if (state.failed) {
                    return;
                }
                mT();
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "IMPORT_SYM"

    // $ANTLR start "PAGE_SYM"
    public final void mPAGE_SYM() throws RecognitionException {
        try {
            int _type = PAGE_SYM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:359:11: ( '@' P A G E )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:359:13: '@' P A G E
            {
                match('@');
                if (state.failed) {
                    return;
                }
                mP();
                if (state.failed) {
                    return;
                }
                mA();
                if (state.failed) {
                    return;
                }
                mG();
                if (state.failed) {
                    return;
                }
                mE();
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "PAGE_SYM"

    // $ANTLR start "MEDIA_SYM"
    public final void mMEDIA_SYM() throws RecognitionException {
        try {
            int _type = MEDIA_SYM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:360:12: ( '@' M E D I A )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:360:14: '@' M E D I A
            {
                match('@');
                if (state.failed) {
                    return;
                }
                mM();
                if (state.failed) {
                    return;
                }
                mE();
                if (state.failed) {
                    return;
                }
                mD();
                if (state.failed) {
                    return;
                }
                mI();
                if (state.failed) {
                    return;
                }
                mA();
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "MEDIA_SYM"

    // $ANTLR start "CHARSET_SYM"
    public final void mCHARSET_SYM() throws RecognitionException {
        try {
            int _type = CHARSET_SYM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:361:14: ( '@charset ' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:361:16: '@charset '
            {
                match("@charset ");
                if (state.failed) {
                    return;
                }


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "CHARSET_SYM"

    // $ANTLR start "IMPORTANT_SYM"
    public final void mIMPORTANT_SYM() throws RecognitionException {
        try {
            int _type = IMPORTANT_SYM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:363:15: ( '!' ( WS | COMMENT )* I M P O R T A N T )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:363:17: '!' ( WS | COMMENT )* I M P O R T A N T
            {
                match('!');
                if (state.failed) {
                    return;
                }
                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:363:21: ( WS | COMMENT )*
                loop196:
                do {
                    int alt196 = 3;
                    int LA196_0 = input.LA(1);

                    if ((LA196_0 == '\t' || LA196_0 == ' ')) {
                        alt196 = 1;
                    } else if ((LA196_0 == '/')) {
                        alt196 = 2;
                    }


                    switch (alt196) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:363:22: WS
                        {
                            mWS();
                            if (state.failed) {
                                return;
                            }

                        }
                        break;
                        case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:363:25: COMMENT
                        {
                            mCOMMENT();
                            if (state.failed) {
                                return;
                            }

                        }
                        break;

                        default:
                            break loop196;
                    }
                } while (true);

                mI();
                if (state.failed) {
                    return;
                }
                mM();
                if (state.failed) {
                    return;
                }
                mP();
                if (state.failed) {
                    return;
                }
                mO();
                if (state.failed) {
                    return;
                }
                mR();
                if (state.failed) {
                    return;
                }
                mT();
                if (state.failed) {
                    return;
                }
                mA();
                if (state.failed) {
                    return;
                }
                mN();
                if (state.failed) {
                    return;
                }
                mT();
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "IMPORTANT_SYM"

    // $ANTLR start "EMS"
    public final void mEMS() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:375:16: ()
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:375:17:
            {
            }

        } finally {
        }
    }
    // $ANTLR end "EMS"

    // $ANTLR start "EXS"
    public final void mEXS() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:376:16: ()
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:376:17:
            {
            }

        } finally {
        }
    }
    // $ANTLR end "EXS"

    // $ANTLR start "LENGTH"
    public final void mLENGTH() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:377:18: ()
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:377:19:
            {
            }

        } finally {
        }
    }
    // $ANTLR end "LENGTH"

    // $ANTLR start "ANGLE"
    public final void mANGLE() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:378:17: ()
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:378:18:
            {
            }

        } finally {
        }
    }
    // $ANTLR end "ANGLE"

    // $ANTLR start "TIME"
    public final void mTIME() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:379:16: ()
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:379:17:
            {
            }

        } finally {
        }
    }
    // $ANTLR end "TIME"

    // $ANTLR start "FREQ"
    public final void mFREQ() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:380:16: ()
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:380:17:
            {
            }

        } finally {
        }
    }
    // $ANTLR end "FREQ"

    // $ANTLR start "DIMENSION"
    public final void mDIMENSION() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:381:20: ()
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:381:21:
            {
            }

        } finally {
        }
    }
    // $ANTLR end "DIMENSION"

    // $ANTLR start "PERCENTAGE"
    public final void mPERCENTAGE() throws RecognitionException {
        try {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:382:21: ()
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:382:22:
            {
            }

        } finally {
        }
    }
    // $ANTLR end "PERCENTAGE"

    // $ANTLR start "NUMBER"
    public final void mNUMBER() throws RecognitionException {
        try {
            int _type = NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:385:2: ( ( '0' .. '9' ( '.' ( '0' .. '9' )+ )? | '.' ( '0' .. '9' )+ ) ( ( E ( M | X ) )=> E ( M | X ) | ( P ( X | T | C ) )=> P ( X | T | C ) | ( C M )=> C M | ( M ( M | S ) )=> M ( M | S ) | ( I N )=> I N | ( D E G )=> D E G | ( R A D )=> R A D | ( S )=> S | ( ( K )? H Z )=> ( K )? H Z | IDENT | '%' | ) )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:385:4: ( '0' .. '9' ( '.' ( '0' .. '9' )+ )? | '.' ( '0' .. '9' )+ ) ( ( E ( M | X ) )=> E ( M | X ) | ( P ( X | T | C ) )=> P ( X | T | C ) | ( C M )=> C M | ( M ( M | S ) )=> M ( M | S ) | ( I N )=> I N | ( D E G )=> D E G | ( R A D )=> R A D | ( S )=> S | ( ( K )? H Z )=> ( K )? H Z | IDENT | '%' | )
            {
                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:385:4: ( '0' .. '9' ( '.' ( '0' .. '9' )+ )? | '.' ( '0' .. '9' )+ )
                int alt200 = 2;
                int LA200_0 = input.LA(1);

                if (((LA200_0 >= '0' && LA200_0 <= '9'))) {
                    alt200 = 1;
                } else if ((LA200_0 == '.')) {
                    alt200 = 2;
                } else {
                    if (state.backtracking > 0) {
                        state.failed = true;
                        return;
                    }
                    NoViableAltException nvae =
                            new NoViableAltException("", 200, 0, input);

                    throw nvae;
                }
                switch (alt200) {
                    case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:386:6: '0' .. '9' ( '.' ( '0' .. '9' )+ )?
                    {
                        matchRange('0', '9');
                        if (state.failed) {
                            return;
                        }
                        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:386:15: ( '.' ( '0' .. '9' )+ )?
                        int alt198 = 2;
                        int LA198_0 = input.LA(1);

                        if ((LA198_0 == '.')) {
                            alt198 = 1;
                        }
                        switch (alt198) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:386:16: '.' ( '0' .. '9' )+
                            {
                                match('.');
                                if (state.failed) {
                                    return;
                                }
                                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:386:20: ( '0' .. '9' )+
                                int cnt197 = 0;
                                loop197:
                                do {
                                    int alt197 = 2;
                                    int LA197_0 = input.LA(1);

                                    if (((LA197_0 >= '0' && LA197_0 <= '9'))) {
                                        alt197 = 1;
                                    }


                                    switch (alt197) {
                                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:386:20: '0' .. '9'
                                        {
                                            matchRange('0', '9');
                                            if (state.failed) {
                                                return;
                                            }

                                        }
                                        break;

                                        default:
                                            if (cnt197 >= 1) {
                                                break loop197;
                                            }
                                            if (state.backtracking > 0) {
                                                state.failed = true;
                                                return;
                                            }
                                            EarlyExitException eee =
                                                    new EarlyExitException(197, input);
                                            throw eee;
                                    }
                                    cnt197++;
                                } while (true);


                            }
                            break;

                        }


                    }
                    break;
                    case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:387:6: '.' ( '0' .. '9' )+
                    {
                        match('.');
                        if (state.failed) {
                            return;
                        }
                        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:387:10: ( '0' .. '9' )+
                        int cnt199 = 0;
                        loop199:
                        do {
                            int alt199 = 2;
                            int LA199_0 = input.LA(1);

                            if (((LA199_0 >= '0' && LA199_0 <= '9'))) {
                                alt199 = 1;
                            }


                            switch (alt199) {
                                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:387:10: '0' .. '9'
                                {
                                    matchRange('0', '9');
                                    if (state.failed) {
                                        return;
                                    }

                                }
                                break;

                                default:
                                    if (cnt199 >= 1) {
                                        break loop199;
                                    }
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    EarlyExitException eee =
                                            new EarlyExitException(199, input);
                                    throw eee;
                            }
                            cnt199++;
                        } while (true);


                    }
                    break;

                }

                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:389:3: ( ( E ( M | X ) )=> E ( M | X ) | ( P ( X | T | C ) )=> P ( X | T | C ) | ( C M )=> C M | ( M ( M | S ) )=> M ( M | S ) | ( I N )=> I N | ( D E G )=> D E G | ( R A D )=> R A D | ( S )=> S | ( ( K )? H Z )=> ( K )? H Z | IDENT | '%' | )
                int alt205 = 12;
                alt205 = dfa205.predict(input);
                switch (alt205) {
                    case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:390:6: ( E ( M | X ) )=> E ( M | X )
                    {
                        mE();
                        if (state.failed) {
                            return;
                        }
                        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:392:7: ( M | X )
                        int alt201 = 2;
                        switch (input.LA(1)) {
                            case 'M':
                            case 'm': {
                                alt201 = 1;
                            }
                            break;
                            case '\\': {
                                switch (input.LA(2)) {
                                    case '5':
                                    case '7':
                                    case 'X':
                                    case 'x': {
                                        alt201 = 2;
                                    }
                                    break;
                                    case '0': {
                                        switch (input.LA(3)) {
                                            case '0': {
                                                switch (input.LA(4)) {
                                                    case '0': {
                                                        switch (input.LA(5)) {
                                                            case '0': {
                                                                int LA201_7 = input.LA(6);

                                                                if ((LA201_7 == '4' || LA201_7 == '6')) {
                                                                    alt201 = 1;
                                                                } else if ((LA201_7 == '5' || LA201_7 == '7')) {
                                                                    alt201 = 2;
                                                                } else {
                                                                    if (state.backtracking > 0) {
                                                                        state.failed = true;
                                                                        return;
                                                                    }
                                                                    NoViableAltException nvae =
                                                                            new NoViableAltException("", 201, 7, input);

                                                                    throw nvae;
                                                                }
                                                            }
                                                            break;
                                                            case '4':
                                                            case '6': {
                                                                alt201 = 1;
                                                            }
                                                            break;
                                                            case '5':
                                                            case '7': {
                                                                alt201 = 2;
                                                            }
                                                            break;
                                                            default:
                                                                if (state.backtracking > 0) {
                                                                    state.failed = true;
                                                                    return;
                                                                }
                                                                NoViableAltException nvae =
                                                                        new NoViableAltException("", 201, 6, input);

                                                                throw nvae;
                                                        }

                                                    }
                                                    break;
                                                    case '4':
                                                    case '6': {
                                                        alt201 = 1;
                                                    }
                                                    break;
                                                    case '5':
                                                    case '7': {
                                                        alt201 = 2;
                                                    }
                                                    break;
                                                    default:
                                                        if (state.backtracking > 0) {
                                                            state.failed = true;
                                                            return;
                                                        }
                                                        NoViableAltException nvae =
                                                                new NoViableAltException("", 201, 5, input);

                                                        throw nvae;
                                                }

                                            }
                                            break;
                                            case '5':
                                            case '7': {
                                                alt201 = 2;
                                            }
                                            break;
                                            case '4':
                                            case '6': {
                                                alt201 = 1;
                                            }
                                            break;
                                            default:
                                                if (state.backtracking > 0) {
                                                    state.failed = true;
                                                    return;
                                                }
                                                NoViableAltException nvae =
                                                        new NoViableAltException("", 201, 4, input);

                                                throw nvae;
                                        }

                                    }
                                    break;
                                    case '4':
                                    case '6':
                                    case 'M':
                                    case 'm': {
                                        alt201 = 1;
                                    }
                                    break;
                                    default:
                                        if (state.backtracking > 0) {
                                            state.failed = true;
                                            return;
                                        }
                                        NoViableAltException nvae =
                                                new NoViableAltException("", 201, 2, input);

                                        throw nvae;
                                }

                            }
                            break;
                            case 'X':
                            case 'x': {
                                alt201 = 2;
                            }
                            break;
                            default:
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                NoViableAltException nvae =
                                        new NoViableAltException("", 201, 0, input);

                                throw nvae;
                        }

                        switch (alt201) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:393:10: M
                            {
                                mM();
                                if (state.failed) {
                                    return;
                                }
                                if (state.backtracking == 0) {
                                    _type = EMS;
                                }

                            }
                            break;
                            case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:394:10: X
                            {
                                mX();
                                if (state.failed) {
                                    return;
                                }
                                if (state.backtracking == 0) {
                                    _type = EXS;
                                }

                            }
                            break;

                        }


                    }
                    break;
                    case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:396:6: ( P ( X | T | C ) )=> P ( X | T | C )
                    {
                        mP();
                        if (state.failed) {
                            return;
                        }
                        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:398:5: ( X | T | C )
                        int alt202 = 3;
                        alt202 = dfa202.predict(input);
                        switch (alt202) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:399:8: X
                            {
                                mX();
                                if (state.failed) {
                                    return;
                                }

                            }
                            break;
                            case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:400:8: T
                            {
                                mT();
                                if (state.failed) {
                                    return;
                                }

                            }
                            break;
                            case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:401:8: C
                            {
                                mC();
                                if (state.failed) {
                                    return;
                                }

                            }
                            break;

                        }

                        if (state.backtracking == 0) {
                            _type = LENGTH;
                        }

                    }
                    break;
                    case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:404:6: ( C M )=> C M
                    {
                        mC();
                        if (state.failed) {
                            return;
                        }
                        mM();
                        if (state.failed) {
                            return;
                        }
                        if (state.backtracking == 0) {
                            _type = LENGTH;
                        }

                    }
                    break;
                    case 4: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:406:6: ( M ( M | S ) )=> M ( M | S )
                    {
                        mM();
                        if (state.failed) {
                            return;
                        }
                        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:408:5: ( M | S )
                        int alt203 = 2;
                        switch (input.LA(1)) {
                            case 'M':
                            case 'm': {
                                alt203 = 1;
                            }
                            break;
                            case '\\': {
                                switch (input.LA(2)) {
                                    case '4':
                                    case '6':
                                    case 'M':
                                    case 'm': {
                                        alt203 = 1;
                                    }
                                    break;
                                    case '0': {
                                        switch (input.LA(3)) {
                                            case '0': {
                                                switch (input.LA(4)) {
                                                    case '0': {
                                                        switch (input.LA(5)) {
                                                            case '0': {
                                                                int LA203_7 = input.LA(6);

                                                                if ((LA203_7 == '5' || LA203_7 == '7')) {
                                                                    alt203 = 2;
                                                                } else if ((LA203_7 == '4' || LA203_7 == '6')) {
                                                                    alt203 = 1;
                                                                } else {
                                                                    if (state.backtracking > 0) {
                                                                        state.failed = true;
                                                                        return;
                                                                    }
                                                                    NoViableAltException nvae =
                                                                            new NoViableAltException("", 203, 7, input);

                                                                    throw nvae;
                                                                }
                                                            }
                                                            break;
                                                            case '5':
                                                            case '7': {
                                                                alt203 = 2;
                                                            }
                                                            break;
                                                            case '4':
                                                            case '6': {
                                                                alt203 = 1;
                                                            }
                                                            break;
                                                            default:
                                                                if (state.backtracking > 0) {
                                                                    state.failed = true;
                                                                    return;
                                                                }
                                                                NoViableAltException nvae =
                                                                        new NoViableAltException("", 203, 6, input);

                                                                throw nvae;
                                                        }

                                                    }
                                                    break;
                                                    case '5':
                                                    case '7': {
                                                        alt203 = 2;
                                                    }
                                                    break;
                                                    case '4':
                                                    case '6': {
                                                        alt203 = 1;
                                                    }
                                                    break;
                                                    default:
                                                        if (state.backtracking > 0) {
                                                            state.failed = true;
                                                            return;
                                                        }
                                                        NoViableAltException nvae =
                                                                new NoViableAltException("", 203, 5, input);

                                                        throw nvae;
                                                }

                                            }
                                            break;
                                            case '4':
                                            case '6': {
                                                alt203 = 1;
                                            }
                                            break;
                                            case '5':
                                            case '7': {
                                                alt203 = 2;
                                            }
                                            break;
                                            default:
                                                if (state.backtracking > 0) {
                                                    state.failed = true;
                                                    return;
                                                }
                                                NoViableAltException nvae =
                                                        new NoViableAltException("", 203, 4, input);

                                                throw nvae;
                                        }

                                    }
                                    break;
                                    case '5':
                                    case '7':
                                    case 'S':
                                    case 's': {
                                        alt203 = 2;
                                    }
                                    break;
                                    default:
                                        if (state.backtracking > 0) {
                                            state.failed = true;
                                            return;
                                        }
                                        NoViableAltException nvae =
                                                new NoViableAltException("", 203, 2, input);

                                        throw nvae;
                                }

                            }
                            break;
                            case 'S':
                            case 's': {
                                alt203 = 2;
                            }
                            break;
                            default:
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                NoViableAltException nvae =
                                        new NoViableAltException("", 203, 0, input);

                                throw nvae;
                        }

                        switch (alt203) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:409:8: M
                            {
                                mM();
                                if (state.failed) {
                                    return;
                                }
                                if (state.backtracking == 0) {
                                    _type = LENGTH;
                                }

                            }
                            break;
                            case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:411:8: S
                            {
                                mS();
                                if (state.failed) {
                                    return;
                                }
                                if (state.backtracking == 0) {
                                    _type = TIME;
                                }

                            }
                            break;

                        }


                    }
                    break;
                    case 5: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:413:6: ( I N )=> I N
                    {
                        mI();
                        if (state.failed) {
                            return;
                        }
                        mN();
                        if (state.failed) {
                            return;
                        }
                        if (state.backtracking == 0) {
                            _type = LENGTH;
                        }

                    }
                    break;
                    case 6: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:416:6: ( D E G )=> D E G
                    {
                        mD();
                        if (state.failed) {
                            return;
                        }
                        mE();
                        if (state.failed) {
                            return;
                        }
                        mG();
                        if (state.failed) {
                            return;
                        }
                        if (state.backtracking == 0) {
                            _type = ANGLE;
                        }

                    }
                    break;
                    case 7: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:418:6: ( R A D )=> R A D
                    {
                        mR();
                        if (state.failed) {
                            return;
                        }
                        mA();
                        if (state.failed) {
                            return;
                        }
                        mD();
                        if (state.failed) {
                            return;
                        }
                        if (state.backtracking == 0) {
                            _type = ANGLE;
                        }

                    }
                    break;
                    case 8: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:421:6: ( S )=> S
                    {
                        mS();
                        if (state.failed) {
                            return;
                        }
                        if (state.backtracking == 0) {
                            _type = TIME;
                        }

                    }
                    break;
                    case 9: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:423:6: ( ( K )? H Z )=> ( K )? H Z
                    {
                        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:424:5: ( K )?
                        int alt204 = 2;
                        int LA204_0 = input.LA(1);

                        if ((LA204_0 == 'K' || LA204_0 == 'k')) {
                            alt204 = 1;
                        } else if ((LA204_0 == '\\')) {
                            switch (input.LA(2)) {
                                case 'K':
                                case 'k': {
                                    alt204 = 1;
                                }
                                break;
                                case '0': {
                                    int LA204_4 = input.LA(3);

                                    if ((LA204_4 == '0')) {
                                        int LA204_6 = input.LA(4);

                                        if ((LA204_6 == '0')) {
                                            int LA204_7 = input.LA(5);

                                            if ((LA204_7 == '0')) {
                                                int LA204_8 = input.LA(6);

                                                if ((LA204_8 == '4' || LA204_8 == '6')) {
                                                    int LA204_5 = input.LA(7);

                                                    if ((LA204_5 == 'B' || LA204_5 == 'b')) {
                                                        alt204 = 1;
                                                    }
                                                }
                                            } else if ((LA204_7 == '4' || LA204_7 == '6')) {
                                                int LA204_5 = input.LA(6);

                                                if ((LA204_5 == 'B' || LA204_5 == 'b')) {
                                                    alt204 = 1;
                                                }
                                            }
                                        } else if ((LA204_6 == '4' || LA204_6 == '6')) {
                                            int LA204_5 = input.LA(5);

                                            if ((LA204_5 == 'B' || LA204_5 == 'b')) {
                                                alt204 = 1;
                                            }
                                        }
                                    } else if ((LA204_4 == '4' || LA204_4 == '6')) {
                                        int LA204_5 = input.LA(4);

                                        if ((LA204_5 == 'B' || LA204_5 == 'b')) {
                                            alt204 = 1;
                                        }
                                    }
                                }
                                break;
                                case '4':
                                case '6': {
                                    int LA204_5 = input.LA(3);

                                    if ((LA204_5 == 'B' || LA204_5 == 'b')) {
                                        alt204 = 1;
                                    }
                                }
                                break;
                            }

                        }
                        switch (alt204) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:424:5: K
                            {
                                mK();
                                if (state.failed) {
                                    return;
                                }

                            }
                            break;

                        }

                        mH();
                        if (state.failed) {
                            return;
                        }
                        mZ();
                        if (state.failed) {
                            return;
                        }
                        if (state.backtracking == 0) {
                            _type = FREQ;
                        }

                    }
                    break;
                    case 10: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:426:6: IDENT
                    {
                        mIDENT();
                        if (state.failed) {
                            return;
                        }
                        if (state.backtracking == 0) {
                            _type = DIMENSION;
                        }

                    }
                    break;
                    case 11: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:428:6: '%'
                    {
                        match('%');
                        if (state.failed) {
                            return;
                        }
                        if (state.backtracking == 0) {
                            _type = PERCENTAGE;
                        }

                    }
                    break;
                    case 12: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:431:3:
                    {
                    }
                    break;

                }


            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "NUMBER"

    // $ANTLR start "URI"
    public final void mURI() throws RecognitionException {
        try {
            int _type = URI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:437:5: ( U R L '(' ( ( WS )=> WS )? ( URL | STRING ) ( WS )? ')' )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:437:7: U R L '(' ( ( WS )=> WS )? ( URL | STRING ) ( WS )? ')'
            {
                mU();
                if (state.failed) {
                    return;
                }
                mR();
                if (state.failed) {
                    return;
                }
                mL();
                if (state.failed) {
                    return;
                }
                match('(');
                if (state.failed) {
                    return;
                }
                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:439:4: ( ( WS )=> WS )?
                int alt206 = 2;
                int LA206_0 = input.LA(1);

                if ((LA206_0 == '\t' || LA206_0 == ' ')) {
                    int LA206_1 = input.LA(2);

                    if ((synpred10_CSSLexer())) {
                        alt206 = 1;
                    }
                }
                switch (alt206) {
                    case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:439:5: ( WS )=> WS
                    {
                        mWS();
                        if (state.failed) {
                            return;
                        }

                    }
                    break;

                }

                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:439:16: ( URL | STRING )
                int alt207 = 2;
                int LA207_0 = input.LA(1);

                if ((LA207_0 == '\t' || (LA207_0 >= ' ' && LA207_0 <= '!') || (LA207_0 >= '#' && LA207_0 <= '&') || (LA207_0 >= ')' && LA207_0 <= '*') || LA207_0 == '-' || (LA207_0 >= '[' && LA207_0 <= '\\') || LA207_0 == '~' || (LA207_0 >= '\u0080' && LA207_0 <= '\uFFFF'))) {
                    alt207 = 1;
                } else if ((LA207_0 == '\"' || LA207_0 == '\'')) {
                    alt207 = 2;
                } else {
                    if (state.backtracking > 0) {
                        state.failed = true;
                        return;
                    }
                    NoViableAltException nvae =
                            new NoViableAltException("", 207, 0, input);

                    throw nvae;
                }
                switch (alt207) {
                    case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:439:17: URL
                    {
                        mURL();
                        if (state.failed) {
                            return;
                        }

                    }
                    break;
                    case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:439:21: STRING
                    {
                        mSTRING();
                        if (state.failed) {
                            return;
                        }

                    }
                    break;

                }

                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:439:29: ( WS )?
                int alt208 = 2;
                int LA208_0 = input.LA(1);

                if ((LA208_0 == '\t' || LA208_0 == ' ')) {
                    alt208 = 1;
                }
                switch (alt208) {
                    case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:439:29: WS
                    {
                        mWS();
                        if (state.failed) {
                            return;
                        }

                    }
                    break;

                }

                match(')');
                if (state.failed) {
                    return;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "URI"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:448:5: ( ( ' ' | '\\t' )+ )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:448:7: ( ' ' | '\\t' )+
            {
                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:448:7: ( ' ' | '\\t' )+
                int cnt209 = 0;
                loop209:
                do {
                    int alt209 = 2;
                    int LA209_0 = input.LA(1);

                    if ((LA209_0 == '\t' || LA209_0 == ' ')) {
                        alt209 = 1;
                    }


                    switch (alt209) {
                        case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:
                        {
                            if (input.LA(1) == '\t' || input.LA(1) == ' ') {
                                input.consume();
                                state.failed = false;
                            } else {
                                if (state.backtracking > 0) {
                                    state.failed = true;
                                    return;
                                }
                                MismatchedSetException mse = new MismatchedSetException(null, input);
                                recover(mse);
                                throw mse;
                            }


                        }
                        break;

                        default:
                            if (cnt209 >= 1) {
                                break loop209;
                            }
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            EarlyExitException eee =
                                    new EarlyExitException(209, input);
                            throw eee;
                    }
                    cnt209++;
                } while (true);

                if (state.backtracking == 0) {
                    _channel = HIDDEN;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "NL"
    public final void mNL() throws RecognitionException {
        try {
            int _type = NL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:449:5: ( ( '\\r' ( '\\n' )? | '\\n' ) )
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:449:7: ( '\\r' ( '\\n' )? | '\\n' )
            {
                // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:449:7: ( '\\r' ( '\\n' )? | '\\n' )
                int alt211 = 2;
                int LA211_0 = input.LA(1);

                if ((LA211_0 == '\r')) {
                    alt211 = 1;
                } else if ((LA211_0 == '\n')) {
                    alt211 = 2;
                } else {
                    if (state.backtracking > 0) {
                        state.failed = true;
                        return;
                    }
                    NoViableAltException nvae =
                            new NoViableAltException("", 211, 0, input);

                    throw nvae;
                }
                switch (alt211) {
                    case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:449:8: '\\r' ( '\\n' )?
                    {
                        match('\r');
                        if (state.failed) {
                            return;
                        }
                        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:449:13: ( '\\n' )?
                        int alt210 = 2;
                        int LA210_0 = input.LA(1);

                        if ((LA210_0 == '\n')) {
                            alt210 = 1;
                        }
                        switch (alt210) {
                            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:449:13: '\\n'
                            {
                                match('\n');
                                if (state.failed) {
                                    return;
                                }

                            }
                            break;

                        }


                    }
                    break;
                    case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:449:21: '\\n'
                    {
                        match('\n');
                        if (state.failed) {
                            return;
                        }

                    }
                    break;

                }

                if (state.backtracking == 0) {
                    _channel = HIDDEN;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
        }
    }
    // $ANTLR end "NL"

    public void mTokens() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:8: ( COMMENT | CDO | CDC | INCLUDES | DASHMATCH | GREATER | LBRACE | RBRACE | LBRACKET | RBRACKET | OPEQ | SEMI | COLON | SOLIDUS | MINUS | PLUS | STAR | LPAREN | RPAREN | COMMA | DOT | STRING | IDENT | HASH | IMPORT_SYM | PAGE_SYM | MEDIA_SYM | CHARSET_SYM | IMPORTANT_SYM | NUMBER | URI | WS | NL )
        int alt212 = 33;
        alt212 = dfa212.predict(input);
        switch (alt212) {
            case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:10: COMMENT
            {
                mCOMMENT();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:18: CDO
            {
                mCDO();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:22: CDC
            {
                mCDC();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 4: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:26: INCLUDES
            {
                mINCLUDES();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 5: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:35: DASHMATCH
            {
                mDASHMATCH();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 6: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:45: GREATER
            {
                mGREATER();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 7: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:53: LBRACE
            {
                mLBRACE();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 8: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:60: RBRACE
            {
                mRBRACE();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 9: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:67: LBRACKET
            {
                mLBRACKET();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 10: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:76: RBRACKET
            {
                mRBRACKET();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 11: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:85: OPEQ
            {
                mOPEQ();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 12: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:90: SEMI
            {
                mSEMI();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 13: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:95: COLON
            {
                mCOLON();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 14: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:101: SOLIDUS
            {
                mSOLIDUS();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 15: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:109: MINUS
            {
                mMINUS();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 16: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:115: PLUS
            {
                mPLUS();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 17: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:120: STAR
            {
                mSTAR();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 18: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:125: LPAREN
            {
                mLPAREN();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 19: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:132: RPAREN
            {
                mRPAREN();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 20: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:139: COMMA
            {
                mCOMMA();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 21: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:145: DOT
            {
                mDOT();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 22: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:149: STRING
            {
                mSTRING();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 23: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:156: IDENT
            {
                mIDENT();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 24: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:162: HASH
            {
                mHASH();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 25: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:167: IMPORT_SYM
            {
                mIMPORT_SYM();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 26: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:178: PAGE_SYM
            {
                mPAGE_SYM();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 27: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:187: MEDIA_SYM
            {
                mMEDIA_SYM();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 28: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:197: CHARSET_SYM
            {
                mCHARSET_SYM();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 29: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:209: IMPORTANT_SYM
            {
                mIMPORTANT_SYM();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 30: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:223: NUMBER
            {
                mNUMBER();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 31: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:230: URI
            {
                mURI();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 32: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:234: WS
            {
                mWS();
                if (state.failed) {
                    return;
                }

            }
            break;
            case 33: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:1:237: NL
            {
                mNL();
                if (state.failed) {
                    return;
                }

            }
            break;

        }

    }

    // $ANTLR start synpred1_CSSLexer
    public final void synpred1_CSSLexer_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:390:6: ( E ( M | X ) )
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:390:7: E ( M | X )
        {
            mE();
            if (state.failed) {
                return;
            }
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:390:9: ( M | X )
            int alt213 = 2;
            switch (input.LA(1)) {
                case 'M':
                case 'm': {
                    alt213 = 1;
                }
                break;
                case '\\': {
                    switch (input.LA(2)) {
                        case '4':
                        case '6':
                        case 'M':
                        case 'm': {
                            alt213 = 1;
                        }
                        break;
                        case '0': {
                            switch (input.LA(3)) {
                                case '0': {
                                    switch (input.LA(4)) {
                                        case '0': {
                                            switch (input.LA(5)) {
                                                case '0': {
                                                    int LA213_7 = input.LA(6);

                                                    if ((LA213_7 == '5' || LA213_7 == '7')) {
                                                        alt213 = 2;
                                                    } else if ((LA213_7 == '4' || LA213_7 == '6')) {
                                                        alt213 = 1;
                                                    } else {
                                                        if (state.backtracking > 0) {
                                                            state.failed = true;
                                                            return;
                                                        }
                                                        NoViableAltException nvae =
                                                                new NoViableAltException("", 213, 7, input);

                                                        throw nvae;
                                                    }
                                                }
                                                break;
                                                case '5':
                                                case '7': {
                                                    alt213 = 2;
                                                }
                                                break;
                                                case '4':
                                                case '6': {
                                                    alt213 = 1;
                                                }
                                                break;
                                                default:
                                                    if (state.backtracking > 0) {
                                                        state.failed = true;
                                                        return;
                                                    }
                                                    NoViableAltException nvae =
                                                            new NoViableAltException("", 213, 6, input);

                                                    throw nvae;
                                            }

                                        }
                                        break;
                                        case '5':
                                        case '7': {
                                            alt213 = 2;
                                        }
                                        break;
                                        case '4':
                                        case '6': {
                                            alt213 = 1;
                                        }
                                        break;
                                        default:
                                            if (state.backtracking > 0) {
                                                state.failed = true;
                                                return;
                                            }
                                            NoViableAltException nvae =
                                                    new NoViableAltException("", 213, 5, input);

                                            throw nvae;
                                    }

                                }
                                break;
                                case '5':
                                case '7': {
                                    alt213 = 2;
                                }
                                break;
                                case '4':
                                case '6': {
                                    alt213 = 1;
                                }
                                break;
                                default:
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    NoViableAltException nvae =
                                            new NoViableAltException("", 213, 4, input);

                                    throw nvae;
                            }

                        }
                        break;
                        case '5':
                        case '7':
                        case 'X':
                        case 'x': {
                            alt213 = 2;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 213, 2, input);

                            throw nvae;
                    }

                }
                break;
                case 'X':
                case 'x': {
                    alt213 = 2;
                }
                break;
                default:
                    if (state.backtracking > 0) {
                        state.failed = true;
                        return;
                    }
                    NoViableAltException nvae =
                            new NoViableAltException("", 213, 0, input);

                    throw nvae;
            }

            switch (alt213) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:390:10: M
                {
                    mM();
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:390:12: X
                {
                    mX();
                    if (state.failed) {
                        return;
                    }

                }
                break;

            }


        }
    }
    // $ANTLR end synpred1_CSSLexer

    // $ANTLR start synpred2_CSSLexer
    public final void synpred2_CSSLexer_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:396:6: ( P ( X | T | C ) )
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:396:7: P ( X | T | C )
        {
            mP();
            if (state.failed) {
                return;
            }
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:396:8: ( X | T | C )
            int alt214 = 3;
            alt214 = dfa214.predict(input);
            switch (alt214) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:396:9: X
                {
                    mX();
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:396:11: T
                {
                    mT();
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 3: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:396:13: C
                {
                    mC();
                    if (state.failed) {
                        return;
                    }

                }
                break;

            }


        }
    }
    // $ANTLR end synpred2_CSSLexer

    // $ANTLR start synpred3_CSSLexer
    public final void synpred3_CSSLexer_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:404:6: ( C M )
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:404:7: C M
        {
            mC();
            if (state.failed) {
                return;
            }
            mM();
            if (state.failed) {
                return;
            }

        }
    }
    // $ANTLR end synpred3_CSSLexer

    // $ANTLR start synpred4_CSSLexer
    public final void synpred4_CSSLexer_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:406:6: ( M ( M | S ) )
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:406:7: M ( M | S )
        {
            mM();
            if (state.failed) {
                return;
            }
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:406:9: ( M | S )
            int alt215 = 2;
            switch (input.LA(1)) {
                case 'M':
                case 'm': {
                    alt215 = 1;
                }
                break;
                case '\\': {
                    switch (input.LA(2)) {
                        case '5':
                        case '7':
                        case 'S':
                        case 's': {
                            alt215 = 2;
                        }
                        break;
                        case '0': {
                            switch (input.LA(3)) {
                                case '0': {
                                    switch (input.LA(4)) {
                                        case '0': {
                                            switch (input.LA(5)) {
                                                case '0': {
                                                    int LA215_7 = input.LA(6);

                                                    if ((LA215_7 == '5' || LA215_7 == '7')) {
                                                        alt215 = 2;
                                                    } else if ((LA215_7 == '4' || LA215_7 == '6')) {
                                                        alt215 = 1;
                                                    } else {
                                                        if (state.backtracking > 0) {
                                                            state.failed = true;
                                                            return;
                                                        }
                                                        NoViableAltException nvae =
                                                                new NoViableAltException("", 215, 7, input);

                                                        throw nvae;
                                                    }
                                                }
                                                break;
                                                case '4':
                                                case '6': {
                                                    alt215 = 1;
                                                }
                                                break;
                                                case '5':
                                                case '7': {
                                                    alt215 = 2;
                                                }
                                                break;
                                                default:
                                                    if (state.backtracking > 0) {
                                                        state.failed = true;
                                                        return;
                                                    }
                                                    NoViableAltException nvae =
                                                            new NoViableAltException("", 215, 6, input);

                                                    throw nvae;
                                            }

                                        }
                                        break;
                                        case '4':
                                        case '6': {
                                            alt215 = 1;
                                        }
                                        break;
                                        case '5':
                                        case '7': {
                                            alt215 = 2;
                                        }
                                        break;
                                        default:
                                            if (state.backtracking > 0) {
                                                state.failed = true;
                                                return;
                                            }
                                            NoViableAltException nvae =
                                                    new NoViableAltException("", 215, 5, input);

                                            throw nvae;
                                    }

                                }
                                break;
                                case '5':
                                case '7': {
                                    alt215 = 2;
                                }
                                break;
                                case '4':
                                case '6': {
                                    alt215 = 1;
                                }
                                break;
                                default:
                                    if (state.backtracking > 0) {
                                        state.failed = true;
                                        return;
                                    }
                                    NoViableAltException nvae =
                                            new NoViableAltException("", 215, 4, input);

                                    throw nvae;
                            }

                        }
                        break;
                        case '4':
                        case '6':
                        case 'M':
                        case 'm': {
                            alt215 = 1;
                        }
                        break;
                        default:
                            if (state.backtracking > 0) {
                                state.failed = true;
                                return;
                            }
                            NoViableAltException nvae =
                                    new NoViableAltException("", 215, 2, input);

                            throw nvae;
                    }

                }
                break;
                case 'S':
                case 's': {
                    alt215 = 2;
                }
                break;
                default:
                    if (state.backtracking > 0) {
                        state.failed = true;
                        return;
                    }
                    NoViableAltException nvae =
                            new NoViableAltException("", 215, 0, input);

                    throw nvae;
            }

            switch (alt215) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:406:10: M
                {
                    mM();
                    if (state.failed) {
                        return;
                    }

                }
                break;
                case 2: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:406:12: S
                {
                    mS();
                    if (state.failed) {
                        return;
                    }

                }
                break;

            }


        }
    }
    // $ANTLR end synpred4_CSSLexer

    // $ANTLR start synpred5_CSSLexer
    public final void synpred5_CSSLexer_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:413:6: ( I N )
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:413:7: I N
        {
            mI();
            if (state.failed) {
                return;
            }
            mN();
            if (state.failed) {
                return;
            }

        }
    }
    // $ANTLR end synpred5_CSSLexer

    // $ANTLR start synpred6_CSSLexer
    public final void synpred6_CSSLexer_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:416:6: ( D E G )
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:416:7: D E G
        {
            mD();
            if (state.failed) {
                return;
            }
            mE();
            if (state.failed) {
                return;
            }
            mG();
            if (state.failed) {
                return;
            }

        }
    }
    // $ANTLR end synpred6_CSSLexer

    // $ANTLR start synpred7_CSSLexer
    public final void synpred7_CSSLexer_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:418:6: ( R A D )
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:418:7: R A D
        {
            mR();
            if (state.failed) {
                return;
            }
            mA();
            if (state.failed) {
                return;
            }
            mD();
            if (state.failed) {
                return;
            }

        }
    }
    // $ANTLR end synpred7_CSSLexer

    // $ANTLR start synpred8_CSSLexer
    public final void synpred8_CSSLexer_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:421:6: ( S )
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:421:7: S
        {
            mS();
            if (state.failed) {
                return;
            }

        }
    }
    // $ANTLR end synpred8_CSSLexer

    // $ANTLR start synpred9_CSSLexer
    public final void synpred9_CSSLexer_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:423:6: ( ( K )? H Z )
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:423:7: ( K )? H Z
        {
            // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:423:7: ( K )?
            int alt216 = 2;
            int LA216_0 = input.LA(1);

            if ((LA216_0 == 'K' || LA216_0 == 'k')) {
                alt216 = 1;
            } else if ((LA216_0 == '\\')) {
                switch (input.LA(2)) {
                    case 'K':
                    case 'k': {
                        alt216 = 1;
                    }
                    break;
                    case '0': {
                        int LA216_4 = input.LA(3);

                        if ((LA216_4 == '0')) {
                            int LA216_6 = input.LA(4);

                            if ((LA216_6 == '0')) {
                                int LA216_7 = input.LA(5);

                                if ((LA216_7 == '0')) {
                                    int LA216_8 = input.LA(6);

                                    if ((LA216_8 == '4' || LA216_8 == '6')) {
                                        int LA216_5 = input.LA(7);

                                        if ((LA216_5 == 'B' || LA216_5 == 'b')) {
                                            alt216 = 1;
                                        }
                                    }
                                } else if ((LA216_7 == '4' || LA216_7 == '6')) {
                                    int LA216_5 = input.LA(6);

                                    if ((LA216_5 == 'B' || LA216_5 == 'b')) {
                                        alt216 = 1;
                                    }
                                }
                            } else if ((LA216_6 == '4' || LA216_6 == '6')) {
                                int LA216_5 = input.LA(5);

                                if ((LA216_5 == 'B' || LA216_5 == 'b')) {
                                    alt216 = 1;
                                }
                            }
                        } else if ((LA216_4 == '4' || LA216_4 == '6')) {
                            int LA216_5 = input.LA(4);

                            if ((LA216_5 == 'B' || LA216_5 == 'b')) {
                                alt216 = 1;
                            }
                        }
                    }
                    break;
                    case '4':
                    case '6': {
                        int LA216_5 = input.LA(3);

                        if ((LA216_5 == 'B' || LA216_5 == 'b')) {
                            alt216 = 1;
                        }
                    }
                    break;
                }

            }
            switch (alt216) {
                case 1: // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:423:7: K
                {
                    mK();
                    if (state.failed) {
                        return;
                    }

                }
                break;

            }

            mH();
            if (state.failed) {
                return;
            }
            mZ();
            if (state.failed) {
                return;
            }

        }
    }
    // $ANTLR end synpred9_CSSLexer

    // $ANTLR start synpred10_CSSLexer
    public final void synpred10_CSSLexer_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:439:5: ( WS )
        // /home/hasdai/Documentos/INFOTEC/CSSLexer.g:439:6: WS
        {
            mWS();
            if (state.failed) {
                return;
            }

        }
    }
    // $ANTLR end synpred10_CSSLexer

    public final boolean synpred1_CSSLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_CSSLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred9_CSSLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_CSSLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred2_CSSLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_CSSLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred5_CSSLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_CSSLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred6_CSSLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_CSSLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred8_CSSLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_CSSLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred7_CSSLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_CSSLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred10_CSSLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_CSSLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred4_CSSLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_CSSLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred3_CSSLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_CSSLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA205 dfa205 = new DFA205(this);
    protected DFA202 dfa202 = new DFA202(this);
    protected DFA212 dfa212 = new DFA212(this);
    protected DFA214 dfa214 = new DFA214(this);
    static final String DFA11_eotS =
            "\1\1\14\uffff";
    static final String DFA11_eofS =
            "\15\uffff";
    static final String DFA11_minS =
            "\1\41\14\uffff";
    static final String DFA11_maxS =
            "\1\uffff\14\uffff";
    static final String DFA11_acceptS =
            "\1\uffff\1\14\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13";
    static final String DFA11_specialS =
            "\15\uffff}>";
    static final String[] DFA11_transitionS = {
        "\1\3\1\uffff\1\4\1\5\1\6\1\7\3\uffff\1\10\2\uffff\1\11\55\uffff" +
        "\1\2\1\14\41\uffff\1\12\1\uffff\uff80\13",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    };
    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i = 0; i < numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }

        public String getDescription() {
            return "()* loopback of 80:18: ( '[' | '!' | '#' | '$' | '%' | '&' | '*' | '-' | '~' | NONASCII | ESCAPE )*";
        }
    }
    static final String DFA205_eotS =
            "\1\30\1\14\1\uffff\6\14\1\uffff\2\14\1\uffff\7\14\1\uffff\2\14\10" +
            "\uffff\15\14\2\uffff\2\14\27\uffff\1\14\1\uffff\3\14\4\uffff\1\14" +
            "\1\uffff\1\14\7\uffff\3\14\1\uffff\16\14\4\uffff\3\14\3\uffff\2" +
            "\14\3\uffff\2\14\1\uffff\1\14\2\uffff\2\14\4\uffff\2\14\4\uffff" +
            "\6\14\2\uffff\5\14\3\uffff\5\14\1\uffff\11\14\1\uffff\2\14\2\uffff" +
            "\5\14\3\uffff\2\14\2\uffff\3\14\3\uffff\2\14\3\uffff\1\14\1\uffff" +
            "\16\14\2\uffff\3\14\3\uffff\16\14\3\uffff\4\14\1\uffff\2\14\2\uffff" +
            "\3\14\3\uffff\2\14\2\uffff\3\14\3\uffff\2\14\2\uffff\2\14\1\uffff" +
            "\5\14\1\uffff\3\14\2\uffff\3\14\1\uffff\2\14\2\uffff\3\14\3\uffff" +
            "\4\14\1\uffff\11\14\1\uffff\2\14\2\uffff\2\14\2\uffff\3\14\3\uffff" +
            "\2\14\2\uffff\3\14\3\uffff\2\14\2\uffff\2\14\1\uffff\5\14\1\uffff" +
            "\3\14\2\uffff\3\14\1\uffff\2\14\2\uffff\2\14\3\uffff\2\14\1\uffff" +
            "\11\14\2\uffff\2\14\1\uffff\2\14\2\uffff\2\14\3\uffff\1\14\2\uffff" +
            "\2\14\3\uffff\1\14\2\uffff\2\14\1\uffff\4\14\1\uffff\2\14\2\uffff" +
            "\2\14\1\uffff\1\14\17\uffff\1\14\1\uffff\2\14\1\uffff\1\14\2\uffff" +
            "\1\14\5\uffff";
    static final String DFA205_eofS =
            "\u01c2\uffff";
    static final String DFA205_minS =
            "\1\45\1\11\1\0\6\11\1\0\2\11\1\uffff\7\11\1\0\2\11\2\uffff\3\0\1" +
            "\uffff\2\0\2\116\1\115\1\60\1\63\1\115\2\103\1\110\1\60\1\110\2" +
            "\132\2\0\2\101\3\0\1\uffff\7\0\1\uffff\3\0\1\uffff\5\0\2\uffff\1" +
            "\11\1\0\3\11\1\0\3\uffff\1\11\1\0\1\11\3\0\1\uffff\3\0\1\60\1\104" +
            "\1\70\1\0\2\60\1\63\1\116\2\115\1\110\1\132\1\115\1\105\1\115\1" +
            "\110\1\103\1\101\4\0\1\60\1\64\1\63\3\0\1\60\1\104\3\0\1\60\1\63" +
            "\1\0\1\104\2\0\1\60\1\105\3\0\1\uffff\1\60\1\65\1\uffff\3\0\1\60" +
            "\1\61\2\132\1\60\1\70\2\0\1\60\1\101\1\60\1\104\1\70\3\0\1\60\1" +
            "\63\1\60\1\103\1\101\1\0\1\105\1\115\1\116\1\110\1\115\1\132\1\115" +
            "\1\110\1\115\1\0\2\11\2\0\2\11\1\60\1\63\1\64\3\0\1\60\1\104\2\0" +
            "\1\60\1\104\1\63\3\0\1\60\1\105\3\0\1\60\1\0\1\67\1\60\1\65\1\107" +
            "\1\60\1\64\1\60\1\61\1\104\1\60\1\70\1\132\1\60\1\101\2\0\1\60\1" +
            "\104\1\70\3\0\1\64\1\60\1\63\1\115\1\116\1\132\1\115\1\110\1\115" +
            "\1\105\1\110\1\115\1\103\1\101\3\0\4\11\1\0\2\11\2\0\1\60\1\64\1" +
            "\63\3\0\1\60\1\104\2\0\1\60\1\104\1\63\3\0\1\60\1\105\2\0\1\60\1" +
            "\67\1\0\1\60\1\65\1\107\1\60\1\64\1\0\1\60\1\61\1\104\2\0\1\60\1" +
            "\70\1\132\1\0\1\60\1\101\2\0\1\64\1\70\1\104\3\0\1\60\1\63\1\103" +
            "\1\101\1\0\1\110\1\132\1\105\2\115\1\116\1\115\1\110\1\115\1\0\2" +
            "\11\2\0\2\11\2\0\2\64\1\63\3\0\1\64\1\104\2\0\1\64\1\104\1\63\3" +
            "\0\1\64\1\105\2\0\1\60\1\67\1\0\1\64\1\65\1\107\1\60\1\64\1\0\1" +
            "\64\1\61\1\104\2\0\1\64\1\70\1\132\1\0\1\65\1\101\2\0\1\70\1\104" +
            "\3\0\1\103\1\101\1\0\1\115\1\110\2\115\1\105\1\116\1\132\1\110\1" +
            "\115\2\0\2\11\1\0\2\11\2\0\1\64\1\63\3\0\1\104\2\0\1\63\1\104\3" +
            "\0\1\105\2\0\1\64\1\67\1\0\1\65\1\107\2\64\1\0\1\61\1\104\2\0\1" +
            "\70\1\132\1\0\1\101\17\0\1\67\1\0\1\107\1\64\1\0\1\104\2\0\1\132" +
            "\5\0";
    static final String DFA205_maxS =
            "\1\uffff\1\170\1\uffff\1\170\1\155\1\163\1\156\1\145\1\141\1\0\1" +
            "\150\1\141\1\uffff\2\170\1\155\1\163\1\156\1\145\1\141\1\0\1\150" +
            "\1\141\2\uffff\2\0\1\uffff\1\uffff\2\0\2\156\1\163\1\67\1\144\1" +
            "\163\2\170\1\150\1\63\1\150\2\141\2\0\2\141\2\0\1\uffff\1\uffff" +
            "\6\0\1\uffff\1\uffff\2\0\1\uffff\1\uffff\4\0\1\uffff\2\uffff\1\147" +
            "\1\uffff\1\147\2\144\1\uffff\3\uffff\1\141\1\uffff\1\141\2\0\1\uffff" +
            "\1\uffff\3\0\1\67\1\144\1\70\1\0\1\67\1\63\1\144\1\156\1\163\1\170" +
            "\1\150\1\141\1\155\1\145\1\163\1\150\1\170\1\141\4\0\1\67\1\70\1" +
            "\63\3\0\1\66\1\144\3\0\1\67\1\63\1\0\1\144\2\0\1\66\1\145\2\0\1" +
            "\uffff\1\uffff\1\66\1\65\1\uffff\1\0\1\uffff\1\0\1\66\1\61\2\141" +
            "\1\66\1\70\2\0\1\67\1\141\1\67\1\144\1\70\3\0\1\67\1\144\1\63\1" +
            "\170\1\141\1\0\1\145\1\155\1\156\1\150\1\163\1\141\1\170\1\150\1" +
            "\163\1\0\2\147\2\0\2\144\1\67\1\63\1\70\3\0\1\66\1\144\2\0\1\67" +
            "\1\144\1\63\3\0\1\66\1\145\3\0\1\66\1\0\1\67\1\66\1\65\1\147\1\66" +
            "\1\64\1\66\1\61\1\144\1\66\1\70\1\141\1\67\1\141\2\0\1\67\1\144" +
            "\1\70\3\0\1\67\1\63\1\144\1\155\1\156\1\141\1\170\1\150\1\163\1" +
            "\145\1\150\1\163\1\170\1\141\3\0\2\144\2\147\1\0\1\147\1\144\2\0" +
            "\1\67\1\70\1\63\3\0\1\66\1\144\2\0\1\67\1\144\1\63\3\0\1\66\1\145" +
            "\2\0\1\66\1\67\1\0\1\66\1\65\1\147\1\66\1\64\1\0\1\66\1\61\1\144" +
            "\2\0\1\66\1\70\1\141\1\0\1\67\1\141\2\0\1\67\1\70\1\144\3\0\1\63" +
            "\1\144\1\170\1\141\1\0\1\150\1\141\1\145\1\170\1\163\1\156\1\155" +
            "\1\150\1\163\1\0\2\147\2\0\2\144\2\0\1\67\1\70\1\63\3\0\1\66\1\144" +
            "\2\0\1\67\1\144\1\63\3\0\1\66\1\145\2\0\1\66\1\67\1\0\1\66\1\65" +
            "\1\147\1\66\1\64\1\0\1\66\1\61\1\144\2\0\1\66\1\70\1\141\1\0\1\67" +
            "\1\141\2\0\1\70\1\144\3\0\1\170\1\141\1\0\1\155\1\150\1\170\1\163" +
            "\1\145\1\156\1\141\1\150\1\163\2\0\2\144\1\0\2\147\2\0\1\70\1\63" +
            "\3\0\1\144\2\0\1\63\1\144\3\0\1\145\2\0\1\66\1\67\1\0\1\65\1\147" +
            "\1\66\1\64\1\0\1\61\1\144\2\0\1\70\1\141\1\0\1\141\17\0\1\67\1\0" +
            "\1\147\1\64\1\0\1\144\2\0\1\141\5\0";
    static final String DFA205_acceptS =
            "\14\uffff\1\12\12\uffff\1\13\1\14\3\uffff\1\1\26\uffff\1\2\7\uffff" +
            "\1\3\3\uffff\1\4\5\uffff\1\5\1\6\6\uffff\1\7\1\10\1\11\6\uffff\1" +
            "\11\57\uffff\1\6\2\uffff\1\7\u0138\uffff";
    static final String DFA205_specialS =
            "\1\uffff\1\14\1\u009a\1\u00a9\1\113\1\26\1\36\1\121\1\103\1\u00bc" +
            "\1\11\1\u00a1\1\uffff\1\30\1\u009c\1\136\1\32\1\44\1\134\1\73\1" +
            "\u00a5\1\u00a6\1\u008a\2\uffff\1\146\1\145\1\57\1\uffff\1\10\1\16" +
            "\15\uffff\1\u0087\1\u008e\2\uffff\1\u009b\1\u009f\1\110\1\uffff" +
            "\1\107\1\53\1\177\1\u008b\1\u0085\1\105\1\u00bf\1\uffff\1\144\1" +
            "\170\1\150\1\uffff\1\u00c0\1\u00a2\1\6\1\62\1\61\2\uffff\1\142\1" +
            "\66\1\151\1\u00ab\1\60\1\u0081\3\uffff\1\47\1\54\1\63\1\2\1\u00c8" +
            "\1\u00bd\1\uffff\1\100\1\46\1\u0094\3\uffff\1\104\16\uffff\1\71" +
            "\1\u0091\1\172\1\u00b6\3\uffff\1\u00c6\1\u00b2\1\u00b7\2\uffff\1" +
            "\122\1\17\1\124\2\uffff\1\43\1\uffff\1\24\1\5\2\uffff\1\155\1\147" +
            "\1\u00b3\4\uffff\1\114\1\111\1\120\6\uffff\1\u0097\1\u0093\5\uffff" +
            "\1\u008f\1\77\1\175\5\uffff\1\u00b5\11\uffff\1\164\2\uffff\1\76" +
            "\1\u0096\5\uffff\1\35\1\154\1\u00b8\2\uffff\1\u009e\1\3\3\uffff" +
            "\1\41\1\127\1\101\2\uffff\1\52\1\37\1\171\1\uffff\1\67\16\uffff" +
            "\1\31\1\12\3\uffff\1\u00af\1\u0086\1\u00be\16\uffff\1\176\1\u009d" +
            "\1\u00ad\4\uffff\1\u0098\2\uffff\1\140\1\72\3\uffff\1\u00c9\1\15" +
            "\1\50\2\uffff\1\161\1\173\3\uffff\1\64\1\123\1\167\2\uffff\1\132" +
            "\1\116\2\uffff\1\34\5\uffff\1\27\3\uffff\1\u00ae\1\u00ac\3\uffff" +
            "\1\45\2\uffff\1\115\1\152\3\uffff\1\130\1\126\1\u0089\4\uffff\1" +
            "\u0095\11\uffff\1\153\2\uffff\1\u00c4\1\162\2\uffff\1\65\1\74\3" +
            "\uffff\1\51\1\u00ba\1\117\2\uffff\1\70\1\u008d\3\uffff\1\40\1\u00a0" +
            "\1\137\2\uffff\1\131\1\25\2\uffff\1\21\5\uffff\1\106\3\uffff\1\u00b9" +
            "\1\42\3\uffff\1\u00b4\2\uffff\1\7\1\157\2\uffff\1\u0092\1\u00a4" +
            "\1\u0080\2\uffff\1\156\11\uffff\1\u00c3\1\u00c1\2\uffff\1\135\2" +
            "\uffff\1\u00a7\1\u00a8\2\uffff\1\133\1\56\1\102\1\uffff\1\166\1" +
            "\u00ca\2\uffff\1\u0084\1\33\1\165\1\uffff\1\u0082\1\u0083\2\uffff" +
            "\1\u008c\4\uffff\1\13\2\uffff\1\160\1\u00c5\2\uffff\1\u0090\1\uffff" +
            "\1\u00cb\1\u00c2\1\u00c7\1\112\1\75\1\22\1\20\1\0\1\u00bb\1\4\1" +
            "\u00a3\1\143\1\141\1\174\1\163\1\uffff\1\u00b1\2\uffff\1\55\1\uffff" +
            "\1\23\1\1\1\uffff\1\125\1\u00aa\1\u00b0\1\u0088\1\u0099}>";
    static final String[] DFA205_transitionS = {
        "\1\27\7\uffff\1\14\23\uffff\2\14\1\17\1\22\1\15\2\14\1\26\1" +
        "\21\1\14\1\25\1\14\1\20\2\14\1\16\1\14\1\23\1\24\7\14\1\uffff" +
        "\1\2\2\uffff\1\14\1\uffff\2\14\1\4\1\7\1\1\2\14\1\13\1\6\1\14" +
        "\1\12\1\14\1\5\2\14\1\3\1\14\1\10\1\11\7\14\5\uffff\uff80\14",
        "\2\34\1\uffff\2\34\22\uffff\1\34\54\uffff\1\32\12\uffff\1\36" +
        "\3\uffff\1\33\20\uffff\1\31\12\uffff\1\35",
        "\12\14\1\uffff\1\14\2\uffff\42\14\1\42\3\14\1\43\1\50\1\43" +
        "\1\50\20\14\1\53\1\40\1\14\1\51\1\14\1\44\2\14\1\46\1\14\1\57" +
        "\1\55\24\14\1\52\1\37\1\14\1\47\1\14\1\41\2\14\1\45\1\14\1\56" +
        "\1\54\uff8c\14",
        "\2\63\1\uffff\2\63\22\uffff\1\63\42\uffff\1\67\20\uffff\1\65" +
        "\3\uffff\1\61\3\uffff\1\62\6\uffff\1\66\20\uffff\1\64\3\uffff" +
        "\1\60",
        "\2\73\1\uffff\2\73\22\uffff\1\73\54\uffff\1\71\16\uffff\1\72" +
        "\20\uffff\1\70",
        "\2\77\1\uffff\2\77\22\uffff\1\77\54\uffff\1\75\5\uffff\1\101" +
        "\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\2\105\1\uffff\2\105\22\uffff\1\105\55\uffff\1\103\15\uffff" +
        "\1\104\21\uffff\1\102",
        "\2\106\1\uffff\2\106\22\uffff\1\106\44\uffff\1\111\26\uffff" +
        "\1\110\10\uffff\1\107",
        "\2\115\1\uffff\2\115\22\uffff\1\115\40\uffff\1\113\32\uffff" +
        "\1\114\4\uffff\1\112",
        "\1\uffff",
        "\2\117\1\uffff\2\117\22\uffff\1\117\47\uffff\1\122\23\uffff" +
        "\1\121\13\uffff\1\120",
        "\2\126\1\uffff\2\126\22\uffff\1\126\71\uffff\1\124\1\uffff" +
        "\1\125\4\uffff\1\123",
        "",
        "\2\34\1\uffff\2\34\22\uffff\1\34\54\uffff\1\32\12\uffff\1\36" +
        "\3\uffff\1\33\20\uffff\1\31\12\uffff\1\35",
        "\2\63\1\uffff\2\63\22\uffff\1\63\42\uffff\1\67\20\uffff\1\65" +
        "\3\uffff\1\61\3\uffff\1\62\6\uffff\1\66\20\uffff\1\64\3\uffff" +
        "\1\60",
        "\2\73\1\uffff\2\73\22\uffff\1\73\54\uffff\1\71\16\uffff\1\72" +
        "\20\uffff\1\70",
        "\2\77\1\uffff\2\77\22\uffff\1\77\54\uffff\1\75\5\uffff\1\101" +
        "\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\2\105\1\uffff\2\105\22\uffff\1\105\55\uffff\1\103\15\uffff" +
        "\1\104\21\uffff\1\102",
        "\2\106\1\uffff\2\106\22\uffff\1\106\44\uffff\1\111\26\uffff" +
        "\1\110\10\uffff\1\107",
        "\2\115\1\uffff\2\115\22\uffff\1\115\40\uffff\1\113\32\uffff" +
        "\1\114\4\uffff\1\112",
        "\1\uffff",
        "\2\117\1\uffff\2\117\22\uffff\1\117\47\uffff\1\122\23\uffff" +
        "\1\121\13\uffff\1\120",
        "\2\126\1\uffff\2\126\22\uffff\1\126\71\uffff\1\124\1\uffff" +
        "\1\125\4\uffff\1\123",
        "",
        "",
        "\1\uffff",
        "\1\uffff",
        "\12\14\1\uffff\1\14\2\uffff\42\14\1\132\3\14\1\133\1\134\1" +
        "\133\1\134\25\14\1\130\12\14\1\135\24\14\1\127\12\14\1\131\uff87" +
        "\14",
        "",
        "\1\uffff",
        "\1\uffff",
        "\1\103\15\uffff\1\104\21\uffff\1\102",
        "\1\103\15\uffff\1\104\21\uffff\1\102",
        "\1\75\5\uffff\1\101\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\1\136\3\uffff\1\140\1\137\1\140\1\137",
        "\1\146\1\147\1\143\2\uffff\1\145\1\141\10\uffff\1\151\1\uffff" +
        "\1\150\35\uffff\1\144\1\uffff\1\142",
        "\1\75\5\uffff\1\101\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\1\67\20\uffff\1\65\3\uffff\1\61\3\uffff\1\62\6\uffff\1\66" +
        "\20\uffff\1\64\3\uffff\1\60",
        "\1\67\20\uffff\1\65\3\uffff\1\61\3\uffff\1\62\6\uffff\1\66" +
        "\20\uffff\1\64\3\uffff\1\60",
        "\1\122\23\uffff\1\121\13\uffff\1\120",
        "\1\152\1\uffff\1\153\1\154",
        "\1\122\23\uffff\1\121\13\uffff\1\120",
        "\1\124\1\uffff\1\125\4\uffff\1\123",
        "\1\124\1\uffff\1\125\4\uffff\1\123",
        "\1\uffff",
        "\1\uffff",
        "\1\113\32\uffff\1\114\4\uffff\1\112",
        "\1\113\32\uffff\1\114\4\uffff\1\112",
        "\1\uffff",
        "\1\uffff",
        "\12\14\1\uffff\1\14\2\uffff\42\14\1\160\3\14\1\162\1\161\1" +
        "\162\1\161\34\14\1\163\3\14\1\156\33\14\1\157\3\14\1\155\uff87" +
        "\14",
        "",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\12\14\1\uffff\1\14\2\uffff\42\14\1\166\3\14\1\167\1\14\1\167" +
        "\26\14\1\165\37\14\1\164\uff92\14",
        "",
        "\1\uffff",
        "\1\uffff",
        "\12\14\1\uffff\1\14\2\uffff\42\14\1\173\3\14\1\176\1\174\1" +
        "\176\1\174\25\14\1\175\5\14\1\171\31\14\1\172\5\14\1\170\uff8c" +
        "\14",
        "",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\12\14\1\uffff\1\14\2\uffff\42\14\1\u0081\3\14\1\u0082\1\14" +
        "\1\u0082\27\14\1\u0080\37\14\1\177\uff91\14",
        "",
        "",
        "\2\u0086\1\uffff\2\u0086\22\uffff\1\u0086\46\uffff\1\u0084" +
        "\24\uffff\1\u0085\12\uffff\1\u0083",
        "\12\14\1\uffff\1\14\2\uffff\42\14\1\u0087\3\14\1\u0088\1\14" +
        "\1\u0088\uffc9\14",
        "\2\u0086\1\uffff\2\u0086\22\uffff\1\u0086\46\uffff\1\u0084" +
        "\24\uffff\1\u0085\12\uffff\1\u0083",
        "\2\u0089\1\uffff\2\u0089\22\uffff\1\u0089\43\uffff\1\u008c" +
        "\27\uffff\1\u008b\7\uffff\1\u008a",
        "\2\u0089\1\uffff\2\u0089\22\uffff\1\u0089\43\uffff\1\u008c" +
        "\27\uffff\1\u008b\7\uffff\1\u008a",
        "\12\14\1\uffff\1\14\2\uffff\42\14\1\u008d\3\14\1\u008e\1\14" +
        "\1\u008e\uffc9\14",
        "",
        "",
        "",
        "\2\126\1\uffff\2\126\22\uffff\1\126\71\uffff\1\124\1\uffff" +
        "\1\125\4\uffff\1\123",
        "\12\14\1\uffff\1\14\2\uffff\42\14\1\u0091\3\14\1\u0092\1\14" +
        "\1\u0092\21\14\1\u0090\37\14\1\u008f\uff97\14",
        "\2\126\1\uffff\2\126\22\uffff\1\126\71\uffff\1\124\1\uffff" +
        "\1\125\4\uffff\1\123",
        "\1\uffff",
        "\1\uffff",
        "\12\14\1\uffff\1\14\2\uffff\42\14\1\u0095\4\14\1\u0096\1\14" +
        "\1\u0096\42\14\1\u0094\37\14\1\u0093\uff85\14",
        "",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u0097\3\uffff\1\u0098\1\u0099\1\u0098\1\u0099",
        "\1\u009b\37\uffff\1\u009a",
        "\1\u009c",
        "\1\uffff",
        "\1\u009d\3\uffff\1\u009e\1\u009f\1\u009e\1\u009f",
        "\1\u00a0\1\uffff\1\u00a1\1\u00a2",
        "\1\u00a4\1\u00a3\1\u00a9\2\uffff\1\u00a8\1\u00a5\10\uffff\1" +
        "\u00aa\1\uffff\1\u00ab\35\uffff\1\u00a6\1\uffff\1\u00a7",
        "\1\103\15\uffff\1\104\21\uffff\1\102",
        "\1\75\5\uffff\1\101\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\1\32\12\uffff\1\36\3\uffff\1\33\20\uffff\1\31\12\uffff\1\35",
        "\1\122\23\uffff\1\121\13\uffff\1\120",
        "\1\124\1\uffff\1\125\4\uffff\1\u00ac",
        "\1\71\16\uffff\1\72\20\uffff\1\70",
        "\1\u00ae\26\uffff\1\110\10\uffff\1\u00ad",
        "\1\75\5\uffff\1\101\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\1\122\23\uffff\1\121\13\uffff\1\120",
        "\1\u00b0\20\uffff\1\65\3\uffff\1\61\3\uffff\1\62\6\uffff\1" +
        "\u00af\20\uffff\1\64\3\uffff\1\60",
        "\1\u00b2\32\uffff\1\114\4\uffff\1\u00b1",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u00b3\3\uffff\1\u00b4\1\u00b5\1\u00b4\1\u00b5",
        "\1\u00b7\3\uffff\1\u00b6",
        "\1\u00b8",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u00b9\3\uffff\1\u00ba\1\uffff\1\u00ba",
        "\1\u00bc\37\uffff\1\u00bb",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u00bd\3\uffff\1\u00be\1\u00bf\1\u00be\1\u00bf",
        "\1\u00c0",
        "\1\uffff",
        "\1\u00c2\37\uffff\1\u00c1",
        "\1\uffff",
        "\1\uffff",
        "\1\u00c3\3\uffff\1\u00c4\1\uffff\1\u00c4",
        "\1\u00c6\37\uffff\1\u00c5",
        "\1\uffff",
        "\1\uffff",
        "\12\14\1\uffff\1\14\2\uffff\42\14\1\u00c8\3\14\1\u00ca\1\14" +
        "\1\u00ca\20\14\1\u00c9\37\14\1\u00c7\uff98\14",
        "",
        "\1\u00cb\3\uffff\1\u00cc\1\uffff\1\u00cc",
        "\1\u00cd",
        "",
        "\1\uffff",
        "\12\14\1\uffff\1\14\2\uffff\42\14\1\u00ce\3\14\1\u00cf\1\14" +
        "\1\u00cf\uffc9\14",
        "\1\uffff",
        "\1\u00d0\3\uffff\1\u00d1\1\uffff\1\u00d1",
        "\1\u00d2",
        "\1\124\1\uffff\1\125\4\uffff\1\123",
        "\1\124\1\uffff\1\125\4\uffff\1\123",
        "\1\u00d3\3\uffff\1\u00d4\1\uffff\1\u00d4",
        "\1\u00d5",
        "\1\uffff",
        "\1\uffff",
        "\1\u00d6\4\uffff\1\u00d7\1\uffff\1\u00d7",
        "\1\u00d9\37\uffff\1\u00d8",
        "\1\u00da\3\uffff\1\u00db\1\u00dc\1\u00db\1\u00dc",
        "\1\u00de\37\uffff\1\u00dd",
        "\1\u00df",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u00e0\3\uffff\1\u00e2\1\u00e1\1\u00e2\1\u00e1",
        "\1\u00e3\1\u00e9\1\u00e6\2\uffff\1\u00e5\1\u00e4\10\uffff\1" +
        "\u00ea\1\uffff\1\u00eb\35\uffff\1\u00e7\1\uffff\1\u00e8",
        "\1\u00ec\1\uffff\1\u00ed\1\u00ee",
        "\1\u00f0\20\uffff\1\65\3\uffff\1\61\3\uffff\1\62\6\uffff\1" +
        "\u00ef\20\uffff\1\64\3\uffff\1\60",
        "\1\u00f2\32\uffff\1\114\4\uffff\1\u00f1",
        "\1\uffff",
        "\1\u00f4\26\uffff\1\110\10\uffff\1\u00f3",
        "\1\71\16\uffff\1\72\20\uffff\1\70",
        "\1\103\15\uffff\1\104\21\uffff\1\102",
        "\1\122\23\uffff\1\121\13\uffff\1\120",
        "\1\75\5\uffff\1\101\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\1\124\1\uffff\1\125\4\uffff\1\u00f5",
        "\1\32\12\uffff\1\36\3\uffff\1\33\20\uffff\1\31\12\uffff\1\35",
        "\1\122\23\uffff\1\121\13\uffff\1\120",
        "\1\75\5\uffff\1\101\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\1\uffff",
        "\2\u00f6\1\uffff\2\u00f6\22\uffff\1\u00f6\46\uffff\1\u0084" +
        "\24\uffff\1\u0085\12\uffff\1\u0083",
        "\2\u00f6\1\uffff\2\u00f6\22\uffff\1\u00f6\46\uffff\1\u0084" +
        "\24\uffff\1\u0085\12\uffff\1\u0083",
        "\1\uffff",
        "\1\uffff",
        "\2\u00f7\1\uffff\2\u00f7\22\uffff\1\u00f7\43\uffff\1\u00f9" +
        "\27\uffff\1\u008b\7\uffff\1\u00f8",
        "\2\u00f7\1\uffff\2\u00f7\22\uffff\1\u00f7\43\uffff\1\u00f9" +
        "\27\uffff\1\u008b\7\uffff\1\u00f8",
        "\1\u00fa\3\uffff\1\u00fc\1\u00fb\1\u00fc\1\u00fb",
        "\1\u00fd",
        "\1\u00ff\3\uffff\1\u00fe",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u0100\3\uffff\1\u0101\1\uffff\1\u0101",
        "\1\u0103\37\uffff\1\u0102",
        "\1\uffff",
        "\1\uffff",
        "\1\u0104\3\uffff\1\u0105\1\u0106\1\u0105\1\u0106",
        "\1\u0108\37\uffff\1\u0107",
        "\1\u0109",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u010a\3\uffff\1\u010b\1\uffff\1\u010b",
        "\1\u010d\37\uffff\1\u010c",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u010e\3\uffff\1\u010f\1\uffff\1\u010f",
        "\1\uffff",
        "\1\u0110",
        "\1\u0111\3\uffff\1\u0112\1\uffff\1\u0112",
        "\1\u0113",
        "\1\u0084\24\uffff\1\u0085\12\uffff\1\u0083",
        "\1\u0114\3\uffff\1\u0115\1\uffff\1\u0115",
        "\1\u0116",
        "\1\u0117\3\uffff\1\u0118\1\uffff\1\u0118",
        "\1\u0119",
        "\1\u011b\27\uffff\1\u008b\7\uffff\1\u011a",
        "\1\u011c\3\uffff\1\u011d\1\uffff\1\u011d",
        "\1\u011e",
        "\1\124\1\uffff\1\125\4\uffff\1\u011f",
        "\1\u0120\4\uffff\1\u0121\1\uffff\1\u0121",
        "\1\u0123\37\uffff\1\u0122",
        "\1\uffff",
        "\1\uffff",
        "\1\u0124\3\uffff\1\u0126\1\u0125\1\u0126\1\u0125",
        "\1\u0128\37\uffff\1\u0127",
        "\1\u0129",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u012b\1\u012a\1\u012b\1\u012a",
        "\1\u012c\1\uffff\1\u012d\1\u012e",
        "\1\u0135\1\u0131\1\u0132\2\uffff\1\u0130\1\u0134\10\uffff\1" +
        "\u0136\1\uffff\1\u0137\35\uffff\1\u012f\1\uffff\1\u0133",
        "\1\71\16\uffff\1\72\20\uffff\1\70",
        "\1\103\15\uffff\1\104\21\uffff\1\102",
        "\1\124\1\uffff\1\125\4\uffff\1\u0138",
        "\1\32\12\uffff\1\36\3\uffff\1\33\20\uffff\1\31\12\uffff\1\35",
        "\1\122\23\uffff\1\121\13\uffff\1\120",
        "\1\75\5\uffff\1\101\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\1\u013a\26\uffff\1\110\10\uffff\1\u0139",
        "\1\122\23\uffff\1\121\13\uffff\1\120",
        "\1\75\5\uffff\1\101\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\1\u013c\20\uffff\1\65\3\uffff\1\61\3\uffff\1\62\6\uffff\1" +
        "\u013b\20\uffff\1\64\3\uffff\1\60",
        "\1\u013e\32\uffff\1\114\4\uffff\1\u013d",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\2\u00f7\1\uffff\2\u00f7\22\uffff\1\u00f7\43\uffff\1\u0140" +
        "\27\uffff\1\u008b\7\uffff\1\u013f",
        "\2\u00f7\1\uffff\2\u00f7\22\uffff\1\u00f7\43\uffff\1\u0140" +
        "\27\uffff\1\u008b\7\uffff\1\u013f",
        "\2\u00f6\1\uffff\2\u00f6\22\uffff\1\u00f6\46\uffff\1\u0084" +
        "\24\uffff\1\u0085\12\uffff\1\u0083",
        "\2\u00f6\1\uffff\2\u00f6\22\uffff\1\u00f6\46\uffff\1\u0084" +
        "\24\uffff\1\u0085\12\uffff\1\u0083",
        "\1\uffff",
        "\2\u00f6\1\uffff\2\u00f6\22\uffff\1\u00f6\46\uffff\1\u0084" +
        "\24\uffff\1\u0085\12\uffff\1\u0083",
        "\2\u00f7\1\uffff\2\u00f7\22\uffff\1\u00f7\43\uffff\1\u008c" +
        "\27\uffff\1\u008b\7\uffff\1\u008a",
        "\1\uffff",
        "\1\uffff",
        "\1\u0141\3\uffff\1\u0143\1\u0142\1\u0143\1\u0142",
        "\1\u0145\3\uffff\1\u0144",
        "\1\u0146",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u0147\3\uffff\1\u0148\1\uffff\1\u0148",
        "\1\u014a\37\uffff\1\u0149",
        "\1\uffff",
        "\1\uffff",
        "\1\u014b\3\uffff\1\u014c\1\u014d\1\u014c\1\u014d",
        "\1\u014f\37\uffff\1\u014e",
        "\1\u0150",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u0151\3\uffff\1\u0152\1\uffff\1\u0152",
        "\1\u0154\37\uffff\1\u0153",
        "\1\uffff",
        "\1\uffff",
        "\1\u0155\3\uffff\1\u0156\1\uffff\1\u0156",
        "\1\u0157",
        "\1\uffff",
        "\1\u0158\3\uffff\1\u0159\1\uffff\1\u0159",
        "\1\u015a",
        "\1\u0084\24\uffff\1\u0085\12\uffff\1\u0083",
        "\1\u015b\3\uffff\1\u015c\1\uffff\1\u015c",
        "\1\u015d",
        "\1\uffff",
        "\1\u015e\3\uffff\1\u015f\1\uffff\1\u015f",
        "\1\u0160",
        "\1\u0162\27\uffff\1\u008b\7\uffff\1\u0161",
        "\1\uffff",
        "\1\uffff",
        "\1\u0163\3\uffff\1\u0164\1\uffff\1\u0164",
        "\1\u0165",
        "\1\124\1\uffff\1\125\4\uffff\1\u0166",
        "\1\uffff",
        "\1\u0167\4\uffff\1\u0168\1\uffff\1\u0168",
        "\1\u016a\37\uffff\1\u0169",
        "\1\uffff",
        "\1\uffff",
        "\1\u016c\1\u016b\1\u016c\1\u016b",
        "\1\u016d",
        "\1\u016f\37\uffff\1\u016e",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u0170\1\uffff\1\u0171\1\u0172",
        "\1\u0173\1\u0177\1\u0175\2\uffff\1\u0179\1\u0178\10\uffff\1" +
        "\u017a\1\uffff\1\u017b\35\uffff\1\u0174\1\uffff\1\u0176",
        "\1\u017d\20\uffff\1\65\3\uffff\1\61\3\uffff\1\62\6\uffff\1" +
        "\u017c\20\uffff\1\64\3\uffff\1\60",
        "\1\u017f\32\uffff\1\114\4\uffff\1\u017e",
        "\1\uffff",
        "\1\122\23\uffff\1\121\13\uffff\1\120",
        "\1\124\1\uffff\1\125\4\uffff\1\u0180",
        "\1\u0182\26\uffff\1\110\10\uffff\1\u0181",
        "\1\32\12\uffff\1\36\3\uffff\1\33\20\uffff\1\31\12\uffff\1\35",
        "\1\75\5\uffff\1\101\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\1\103\15\uffff\1\104\21\uffff\1\102",
        "\1\71\16\uffff\1\72\20\uffff\1\70",
        "\1\122\23\uffff\1\121\13\uffff\1\120",
        "\1\75\5\uffff\1\101\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\1\uffff",
        "\2\u00f6\1\uffff\2\u00f6\22\uffff\1\u00f6\46\uffff\1\u0084" +
        "\24\uffff\1\u0085\12\uffff\1\u0083",
        "\2\u00f6\1\uffff\2\u00f6\22\uffff\1\u00f6\46\uffff\1\u0084" +
        "\24\uffff\1\u0085\12\uffff\1\u0083",
        "\1\uffff",
        "\1\uffff",
        "\2\u00f7\1\uffff\2\u00f7\22\uffff\1\u00f7\43\uffff\1\u0184" +
        "\27\uffff\1\u008b\7\uffff\1\u0183",
        "\2\u00f7\1\uffff\2\u00f7\22\uffff\1\u00f7\43\uffff\1\u0184" +
        "\27\uffff\1\u008b\7\uffff\1\u0183",
        "\1\uffff",
        "\1\uffff",
        "\1\u0186\1\u0185\1\u0186\1\u0185",
        "\1\u0188\3\uffff\1\u0187",
        "\1\u0189",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u018a\1\uffff\1\u018a",
        "\1\u018c\37\uffff\1\u018b",
        "\1\uffff",
        "\1\uffff",
        "\1\u018e\1\u018d\1\u018e\1\u018d",
        "\1\u0190\37\uffff\1\u018f",
        "\1\u0191",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u0192\1\uffff\1\u0192",
        "\1\u0194\37\uffff\1\u0193",
        "\1\uffff",
        "\1\uffff",
        "\1\u0195\3\uffff\1\u0196\1\uffff\1\u0196",
        "\1\u0197",
        "\1\uffff",
        "\1\u0198\1\uffff\1\u0198",
        "\1\u0199",
        "\1\u0084\24\uffff\1\u0085\12\uffff\1\u0083",
        "\1\u019a\3\uffff\1\u019b\1\uffff\1\u019b",
        "\1\u019c",
        "\1\uffff",
        "\1\u019d\1\uffff\1\u019d",
        "\1\u019e",
        "\1\u01a0\27\uffff\1\u008b\7\uffff\1\u019f",
        "\1\uffff",
        "\1\uffff",
        "\1\u01a1\1\uffff\1\u01a1",
        "\1\u01a2",
        "\1\124\1\uffff\1\125\4\uffff\1\u01a3",
        "\1\uffff",
        "\1\u01a4\1\uffff\1\u01a4",
        "\1\u01a6\37\uffff\1\u01a5",
        "\1\uffff",
        "\1\uffff",
        "\1\u01a7",
        "\1\u01a9\37\uffff\1\u01a8",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\67\20\uffff\1\65\3\uffff\1\61\3\uffff\1\62\6\uffff\1\66" +
        "\20\uffff\1\64\3\uffff\1\60",
        "\1\113\32\uffff\1\114\4\uffff\1\112",
        "\1\uffff",
        "\1\71\16\uffff\1\72\20\uffff\1\70",
        "\1\122\23\uffff\1\121\13\uffff\1\120",
        "\1\32\12\uffff\1\36\3\uffff\1\33\20\uffff\1\31\12\uffff\1\35",
        "\1\75\5\uffff\1\101\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\1\111\26\uffff\1\110\10\uffff\1\107",
        "\1\103\15\uffff\1\104\21\uffff\1\102",
        "\1\124\1\uffff\1\125\4\uffff\1\123",
        "\1\122\23\uffff\1\121\13\uffff\1\120",
        "\1\75\5\uffff\1\101\10\uffff\1\76\20\uffff\1\74\5\uffff\1\100",
        "\1\uffff",
        "\1\uffff",
        "\2\u00f7\1\uffff\2\u00f7\22\uffff\1\u00f7\43\uffff\1\u008c" +
        "\27\uffff\1\u008b\7\uffff\1\u008a",
        "\2\u00f7\1\uffff\2\u00f7\22\uffff\1\u00f7\43\uffff\1\u008c" +
        "\27\uffff\1\u008b\7\uffff\1\u008a",
        "\1\uffff",
        "\2\u00f6\1\uffff\2\u00f6\22\uffff\1\u00f6\46\uffff\1\u0084" +
        "\24\uffff\1\u0085\12\uffff\1\u0083",
        "\2\u00f6\1\uffff\2\u00f6\22\uffff\1\u00f6\46\uffff\1\u0084" +
        "\24\uffff\1\u0085\12\uffff\1\u0083",
        "\1\uffff",
        "\1\uffff",
        "\1\u01ab\3\uffff\1\u01aa",
        "\1\u01ac",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u01ae\37\uffff\1\u01ad",
        "\1\uffff",
        "\1\uffff",
        "\1\u01af",
        "\1\u01b1\37\uffff\1\u01b0",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u01b3\37\uffff\1\u01b2",
        "\1\uffff",
        "\1\uffff",
        "\1\u01b4\1\uffff\1\u01b4",
        "\1\u01b5",
        "\1\uffff",
        "\1\u01b6",
        "\1\u0084\24\uffff\1\u0085\12\uffff\1\u0083",
        "\1\u01b7\1\uffff\1\u01b7",
        "\1\u01b8",
        "\1\uffff",
        "\1\u01b9",
        "\1\u01bb\27\uffff\1\u008b\7\uffff\1\u01ba",
        "\1\uffff",
        "\1\uffff",
        "\1\u01bc",
        "\1\124\1\uffff\1\125\4\uffff\1\u01bd",
        "\1\uffff",
        "\1\u01bf\37\uffff\1\u01be",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\u01c0",
        "\1\uffff",
        "\1\u0084\24\uffff\1\u0085\12\uffff\1\u0083",
        "\1\u01c1",
        "\1\uffff",
        "\1\u008c\27\uffff\1\u008b\7\uffff\1\u008a",
        "\1\uffff",
        "\1\uffff",
        "\1\124\1\uffff\1\125\4\uffff\1\123",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff",
        "\1\uffff"
    };
    static final short[] DFA205_eot = DFA.unpackEncodedString(DFA205_eotS);
    static final short[] DFA205_eof = DFA.unpackEncodedString(DFA205_eofS);
    static final char[] DFA205_min = DFA.unpackEncodedStringToUnsignedChars(DFA205_minS);
    static final char[] DFA205_max = DFA.unpackEncodedStringToUnsignedChars(DFA205_maxS);
    static final short[] DFA205_accept = DFA.unpackEncodedString(DFA205_acceptS);
    static final short[] DFA205_special = DFA.unpackEncodedString(DFA205_specialS);
    static final short[][] DFA205_transition;

    static {
        int numStates = DFA205_transitionS.length;
        DFA205_transition = new short[numStates][];
        for (int i = 0; i < numStates; i++) {
            DFA205_transition[i] = DFA.unpackEncodedString(DFA205_transitionS[i]);
        }
    }

    class DFA205 extends DFA {

        public DFA205(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 205;
            this.eot = DFA205_eot;
            this.eof = DFA205_eof;
            this.min = DFA205_min;
            this.max = DFA205_max;
            this.accept = DFA205_accept;
            this.special = DFA205_special;
            this.transition = DFA205_transition;
        }

        public String getDescription() {
            return "389:3: ( ( E ( M | X ) )=> E ( M | X ) | ( P ( X | T | C ) )=> P ( X | T | C ) | ( C M )=> C M | ( M ( M | S ) )=> M ( M | S ) | ( I N )=> I N | ( D E G )=> D E G | ( R A D )=> R A D | ( S )=> S | ( ( K )? H Z )=> ( K )? H Z | IDENT | '%' | )";
        }

        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
            int _s = s;
            switch (s) {
                case 0:
                    int LA205_428 = input.LA(1);


                    int index205_428 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_428);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 1:
                    int LA205_443 = input.LA(1);


                    int index205_443 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_443);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 2:
                    int LA205_83 = input.LA(1);


                    int index205_83 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_83);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 3:
                    int LA205_188 = input.LA(1);


                    int index205_188 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_188);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 4:
                    int LA205_430 = input.LA(1);


                    int index205_430 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_430);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 5:
                    int LA205_128 = input.LA(1);


                    int index205_128 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_128);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 6:
                    int LA205_66 = input.LA(1);


                    int index205_66 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_66);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 7:
                    int LA205_361 = input.LA(1);


                    int index205_361 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_361);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 8:
                    int LA205_29 = input.LA(1);


                    int index205_29 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_29);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 9:
                    int LA205_10 = input.LA(1);


                    int index205_10 = input.index();
                    input.rewind();
                    s = -1;
                    if (((LA205_10 >= '\t' && LA205_10 <= '\n') || (LA205_10 >= '\f' && LA205_10 <= '\r') || LA205_10 == ' ') && (synpred9_CSSLexer())) {
                        s = 79;
                    } else if ((LA205_10 == 'h')) {
                        s = 80;
                    } else if ((LA205_10 == '\\')) {
                        s = 81;
                    } else if ((LA205_10 == 'H')) {
                        s = 82;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_10);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 10:
                    int LA205_217 = input.LA(1);


                    int index205_217 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_217);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 11:
                    int LA205_412 = input.LA(1);


                    int index205_412 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_412);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 12:
                    int LA205_1 = input.LA(1);


                    int index205_1 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_1 == 'm')) {
                        s = 25;
                    } else if ((LA205_1 == 'M')) {
                        s = 26;
                    } else if ((LA205_1 == '\\')) {
                        s = 27;
                    } else if (((LA205_1 >= '\t' && LA205_1 <= '\n') || (LA205_1 >= '\f' && LA205_1 <= '\r') || LA205_1 == ' ') && (synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((LA205_1 == 'x')) {
                        s = 29;
                    } else if ((LA205_1 == 'X')) {
                        s = 30;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_1);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 13:
                    int LA205_254 = input.LA(1);


                    int index205_254 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_254);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 14:
                    int LA205_30 = input.LA(1);


                    int index205_30 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_30);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 15:
                    int LA205_121 = input.LA(1);


                    int index205_121 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_121);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 16:
                    int LA205_427 = input.LA(1);


                    int index205_427 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_427);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 17:
                    int LA205_343 = input.LA(1);


                    int index205_343 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred6_CSSLexer())) {
                        s = 134;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_343);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 18:
                    int LA205_426 = input.LA(1);


                    int index205_426 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_426);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 19:
                    int LA205_442 = input.LA(1);


                    int index205_442 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_442);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 20:
                    int LA205_127 = input.LA(1);


                    int index205_127 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_127);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 21:
                    int LA205_340 = input.LA(1);


                    int index205_340 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_340);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 22:
                    int LA205_5 = input.LA(1);


                    int index205_5 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_5 == 'm')) {
                        s = 60;
                    } else if ((LA205_5 == 'M')) {
                        s = 61;
                    } else if ((LA205_5 == '\\')) {
                        s = 62;
                    } else if (((LA205_5 >= '\t' && LA205_5 <= '\n') || (LA205_5 >= '\f' && LA205_5 <= '\r') || LA205_5 == ' ') && (synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((LA205_5 == 's')) {
                        s = 64;
                    } else if ((LA205_5 == 'S')) {
                        s = 65;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_5);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 23:
                    int LA205_278 = input.LA(1);


                    int index205_278 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_278);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 24:
                    int LA205_13 = input.LA(1);


                    int index205_13 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_13 == 'm')) {
                        s = 25;
                    } else if ((LA205_13 == 'M')) {
                        s = 26;
                    } else if ((LA205_13 == '\\')) {
                        s = 27;
                    } else if (((LA205_13 >= '\t' && LA205_13 <= '\n') || (LA205_13 >= '\f' && LA205_13 <= '\r') || LA205_13 == ' ') && (synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((LA205_13 == 'x')) {
                        s = 29;
                    } else if ((LA205_13 == 'X')) {
                        s = 30;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_13);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 25:
                    int LA205_216 = input.LA(1);


                    int index205_216 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_216);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 26:
                    int LA205_16 = input.LA(1);


                    int index205_16 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_16 == 'm')) {
                        s = 60;
                    } else if ((LA205_16 == 'M')) {
                        s = 61;
                    } else if ((LA205_16 == '\\')) {
                        s = 62;
                    } else if (((LA205_16 >= '\t' && LA205_16 <= '\n') || (LA205_16 >= '\f' && LA205_16 <= '\r') || LA205_16 == ' ') && (synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((LA205_16 == 's')) {
                        s = 64;
                    } else if ((LA205_16 == 'S')) {
                        s = 65;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_16);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 27:
                    int LA205_400 = input.LA(1);


                    int index205_400 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_400);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 28:
                    int LA205_272 = input.LA(1);


                    int index205_272 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred6_CSSLexer())) {
                        s = 134;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_272);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 29:
                    int LA205_182 = input.LA(1);


                    int index205_182 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_182);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 30:
                    int LA205_6 = input.LA(1);


                    int index205_6 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_6 == 'n')) {
                        s = 66;
                    } else if ((LA205_6 == 'N')) {
                        s = 67;
                    } else if ((LA205_6 == '\\')) {
                        s = 68;
                    } else if (((LA205_6 >= '\t' && LA205_6 <= '\n') || (LA205_6 >= '\f' && LA205_6 <= '\r') || LA205_6 == ' ') && (synpred5_CSSLexer())) {
                        s = 69;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_6);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 31:
                    int LA205_198 = input.LA(1);


                    int index205_198 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_198);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 32:
                    int LA205_334 = input.LA(1);


                    int index205_334 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_334);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 33:
                    int LA205_192 = input.LA(1);


                    int index205_192 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_192);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 34:
                    int LA205_354 = input.LA(1);


                    int index205_354 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_354);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 35:
                    int LA205_125 = input.LA(1);


                    int index205_125 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_125);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 36:
                    int LA205_17 = input.LA(1);


                    int index205_17 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_17 == 'n')) {
                        s = 66;
                    } else if ((LA205_17 == 'N')) {
                        s = 67;
                    } else if ((LA205_17 == '\\')) {
                        s = 68;
                    } else if (((LA205_17 >= '\t' && LA205_17 <= '\n') || (LA205_17 >= '\f' && LA205_17 <= '\r') || LA205_17 == ' ') && (synpred5_CSSLexer())) {
                        s = 69;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_17);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 37:
                    int LA205_287 = input.LA(1);


                    int index205_287 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_287);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 38:
                    int LA205_88 = input.LA(1);


                    int index205_88 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_88);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 39:
                    int LA205_80 = input.LA(1);


                    int index205_80 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_80 == 'a')) {
                        s = 83;
                    } else if ((LA205_80 == 'Z')) {
                        s = 84;
                    } else if ((LA205_80 == '\\')) {
                        s = 85;
                    } else if (((LA205_80 >= '\t' && LA205_80 <= '\n') || (LA205_80 >= '\f' && LA205_80 <= '\r') || LA205_80 == ' ') && (synpred9_CSSLexer())) {
                        s = 86;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_80);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 40:
                    int LA205_255 = input.LA(1);


                    int index205_255 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_255);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 41:
                    int LA205_324 = input.LA(1);


                    int index205_324 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_324);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 42:
                    int LA205_197 = input.LA(1);


                    int index205_197 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_197);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 43:
                    int LA205_53 = input.LA(1);


                    int index205_53 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_53);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 44:
                    int LA205_81 = input.LA(1);

                    s = -1;
                    if ((LA205_81 == 'h')) {
                        s = 143;
                    } else if ((LA205_81 == 'H')) {
                        s = 144;
                    } else if (((LA205_81 >= '\u0000' && LA205_81 <= '\t') || LA205_81 == '\u000B' || (LA205_81 >= '\u000E' && LA205_81 <= '/') || (LA205_81 >= '1' && LA205_81 <= '3') || LA205_81 == '5' || (LA205_81 >= '7' && LA205_81 <= 'G') || (LA205_81 >= 'I' && LA205_81 <= 'g') || (LA205_81 >= 'i' && LA205_81 <= '\uFFFF'))) {
                        s = 12;
                    } else if ((LA205_81 == '0')) {
                        s = 145;
                    } else if ((LA205_81 == '4' || LA205_81 == '6')) {
                        s = 146;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 45:
                    int LA205_440 = input.LA(1);


                    int index205_440 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_440);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 46:
                    int LA205_392 = input.LA(1);


                    int index205_392 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_392);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 47:
                    int LA205_27 = input.LA(1);

                    s = -1;
                    if ((LA205_27 == 'm')) {
                        s = 87;
                    } else if ((LA205_27 == 'M')) {
                        s = 88;
                    } else if ((LA205_27 == 'x')) {
                        s = 89;
                    } else if ((LA205_27 == '0')) {
                        s = 90;
                    } else if ((LA205_27 == '4' || LA205_27 == '6')) {
                        s = 91;
                    } else if ((LA205_27 == '5' || LA205_27 == '7')) {
                        s = 92;
                    } else if ((LA205_27 == 'X')) {
                        s = 93;
                    } else if (((LA205_27 >= '\u0000' && LA205_27 <= '\t') || LA205_27 == '\u000B' || (LA205_27 >= '\u000E' && LA205_27 <= '/') || (LA205_27 >= '1' && LA205_27 <= '3') || (LA205_27 >= '8' && LA205_27 <= 'L') || (LA205_27 >= 'N' && LA205_27 <= 'W') || (LA205_27 >= 'Y' && LA205_27 <= 'l') || (LA205_27 >= 'n' && LA205_27 <= 'w') || (LA205_27 >= 'y' && LA205_27 <= '\uFFFF'))) {
                        s = 12;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 48:
                    int LA205_75 = input.LA(1);


                    int index205_75 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_75 == 'd')) {
                        s = 138;
                    } else if ((LA205_75 == 'D')) {
                        s = 140;
                    } else if ((LA205_75 == '\\')) {
                        s = 139;
                    } else if (((LA205_75 >= '\t' && LA205_75 <= '\n') || (LA205_75 >= '\f' && LA205_75 <= '\r') || LA205_75 == ' ') && (synpred7_CSSLexer())) {
                        s = 137;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_75);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 49:
                    int LA205_68 = input.LA(1);

                    s = -1;
                    if ((LA205_68 == 'n')) {
                        s = 127;
                    } else if ((LA205_68 == 'N')) {
                        s = 128;
                    } else if (((LA205_68 >= '\u0000' && LA205_68 <= '\t') || LA205_68 == '\u000B' || (LA205_68 >= '\u000E' && LA205_68 <= '/') || (LA205_68 >= '1' && LA205_68 <= '3') || LA205_68 == '5' || (LA205_68 >= '7' && LA205_68 <= 'M') || (LA205_68 >= 'O' && LA205_68 <= 'm') || (LA205_68 >= 'o' && LA205_68 <= '\uFFFF'))) {
                        s = 12;
                    } else if ((LA205_68 == '0')) {
                        s = 129;
                    } else if ((LA205_68 == '4' || LA205_68 == '6')) {
                        s = 130;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 50:
                    int LA205_67 = input.LA(1);


                    int index205_67 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_67);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 51:
                    int LA205_82 = input.LA(1);


                    int index205_82 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_82 == 'a')) {
                        s = 83;
                    } else if ((LA205_82 == 'Z')) {
                        s = 84;
                    } else if ((LA205_82 == '\\')) {
                        s = 85;
                    } else if (((LA205_82 >= '\t' && LA205_82 <= '\n') || (LA205_82 >= '\f' && LA205_82 <= '\r') || LA205_82 == ' ') && (synpred9_CSSLexer())) {
                        s = 86;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_82);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 52:
                    int LA205_263 = input.LA(1);


                    int index205_263 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_263);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 53:
                    int LA205_319 = input.LA(1);


                    int index205_319 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_319);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 54:
                    int LA205_72 = input.LA(1);

                    s = -1;
                    if (((LA205_72 >= '\u0000' && LA205_72 <= '\t') || LA205_72 == '\u000B' || (LA205_72 >= '\u000E' && LA205_72 <= '/') || (LA205_72 >= '1' && LA205_72 <= '3') || LA205_72 == '5' || (LA205_72 >= '7' && LA205_72 <= '\uFFFF'))) {
                        s = 12;
                    } else if ((LA205_72 == '0')) {
                        s = 135;
                    } else if ((LA205_72 == '4' || LA205_72 == '6')) {
                        s = 136;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 55:
                    int LA205_201 = input.LA(1);


                    int index205_201 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred6_CSSLexer())) {
                        s = 134;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_201);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 56:
                    int LA205_329 = input.LA(1);


                    int index205_329 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_329);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 57:
                    int LA205_108 = input.LA(1);


                    int index205_108 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred8_CSSLexer())) {
                        s = 78;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_108);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 58:
                    int LA205_249 = input.LA(1);


                    int index205_249 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_249);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 59:
                    int LA205_19 = input.LA(1);


                    int index205_19 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_19 == 'a')) {
                        s = 74;
                    } else if ((LA205_19 == 'A')) {
                        s = 75;
                    } else if ((LA205_19 == '\\')) {
                        s = 76;
                    } else if (((LA205_19 >= '\t' && LA205_19 <= '\n') || (LA205_19 >= '\f' && LA205_19 <= '\r') || LA205_19 == ' ') && (synpred7_CSSLexer())) {
                        s = 77;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_19);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 60:
                    int LA205_320 = input.LA(1);


                    int index205_320 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_320);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 61:
                    int LA205_425 = input.LA(1);


                    int index205_425 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_425);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 62:
                    int LA205_175 = input.LA(1);


                    int index205_175 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_175);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 63:
                    int LA205_155 = input.LA(1);


                    int index205_155 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_155);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 64:
                    int LA205_87 = input.LA(1);


                    int index205_87 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_87);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 65:
                    int LA205_194 = input.LA(1);


                    int index205_194 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_194);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 66:
                    int LA205_393 = input.LA(1);


                    int index205_393 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_393);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 67:
                    int LA205_8 = input.LA(1);


                    int index205_8 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_8 == 'a')) {
                        s = 74;
                    } else if ((LA205_8 == 'A')) {
                        s = 75;
                    } else if ((LA205_8 == '\\')) {
                        s = 76;
                    } else if (((LA205_8 >= '\t' && LA205_8 <= '\n') || (LA205_8 >= '\f' && LA205_8 <= '\r') || LA205_8 == ' ') && (synpred7_CSSLexer())) {
                        s = 77;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_8);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 68:
                    int LA205_93 = input.LA(1);


                    int index205_93 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_93);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 69:
                    int LA205_57 = input.LA(1);


                    int index205_57 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_57);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 70:
                    int LA205_349 = input.LA(1);


                    int index205_349 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_349);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 71:
                    int LA205_52 = input.LA(1);


                    int index205_52 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_52);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 72:
                    int LA205_50 = input.LA(1);

                    s = -1;
                    if ((LA205_50 == 'x')) {
                        s = 109;
                    } else if ((LA205_50 == 'X')) {
                        s = 110;
                    } else if ((LA205_50 == 't')) {
                        s = 111;
                    } else if ((LA205_50 == '0')) {
                        s = 112;
                    } else if ((LA205_50 == '5' || LA205_50 == '7')) {
                        s = 113;
                    } else if ((LA205_50 == '4' || LA205_50 == '6')) {
                        s = 114;
                    } else if ((LA205_50 == 'T')) {
                        s = 115;
                    } else if (((LA205_50 >= '\u0000' && LA205_50 <= '\t') || LA205_50 == '\u000B' || (LA205_50 >= '\u000E' && LA205_50 <= '/') || (LA205_50 >= '1' && LA205_50 <= '3') || (LA205_50 >= '8' && LA205_50 <= 'S') || (LA205_50 >= 'U' && LA205_50 <= 'W') || (LA205_50 >= 'Y' && LA205_50 <= 's') || (LA205_50 >= 'u' && LA205_50 <= 'w') || (LA205_50 >= 'y' && LA205_50 <= '\uFFFF'))) {
                        s = 12;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 73:
                    int LA205_139 = input.LA(1);

                    s = -1;
                    if (((LA205_139 >= '\u0000' && LA205_139 <= '\t') || LA205_139 == '\u000B' || (LA205_139 >= '\u000E' && LA205_139 <= '/') || (LA205_139 >= '1' && LA205_139 <= '3') || LA205_139 == '5' || (LA205_139 >= '7' && LA205_139 <= '\uFFFF'))) {
                        s = 12;
                    } else if ((LA205_139 == '0')) {
                        s = 206;
                    } else if ((LA205_139 == '4' || LA205_139 == '6')) {
                        s = 207;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 74:
                    int LA205_424 = input.LA(1);


                    int index205_424 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_424);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 75:
                    int LA205_4 = input.LA(1);


                    int index205_4 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_4 == 'm')) {
                        s = 56;
                    } else if ((LA205_4 == 'M')) {
                        s = 57;
                    } else if ((LA205_4 == '\\')) {
                        s = 58;
                    } else if (((LA205_4 >= '\t' && LA205_4 <= '\n') || (LA205_4 >= '\f' && LA205_4 <= '\r') || LA205_4 == ' ') && (synpred3_CSSLexer())) {
                        s = 59;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_4);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 76:
                    int LA205_138 = input.LA(1);


                    int index205_138 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_138);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 77:
                    int LA205_290 = input.LA(1);


                    int index205_290 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_290);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 78:
                    int LA205_269 = input.LA(1);


                    int index205_269 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_269);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 79:
                    int LA205_326 = input.LA(1);


                    int index205_326 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_326);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 80:
                    int LA205_140 = input.LA(1);


                    int index205_140 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_140);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 81:
                    int LA205_7 = input.LA(1);


                    int index205_7 = input.index();
                    input.rewind();
                    s = -1;
                    if (((LA205_7 >= '\t' && LA205_7 <= '\n') || (LA205_7 >= '\f' && LA205_7 <= '\r') || LA205_7 == ' ') && (synpred6_CSSLexer())) {
                        s = 70;
                    } else if ((LA205_7 == 'e')) {
                        s = 71;
                    } else if ((LA205_7 == '\\')) {
                        s = 72;
                    } else if ((LA205_7 == 'E')) {
                        s = 73;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_7);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 82:
                    int LA205_120 = input.LA(1);


                    int index205_120 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_120);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 83:
                    int LA205_264 = input.LA(1);


                    int index205_264 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_264);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 84:
                    int LA205_122 = input.LA(1);


                    int index205_122 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_122);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 85:
                    int LA205_445 = input.LA(1);


                    int index205_445 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_445);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 86:
                    int LA205_296 = input.LA(1);


                    int index205_296 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_296);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 87:
                    int LA205_193 = input.LA(1);


                    int index205_193 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_193);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 88:
                    int LA205_295 = input.LA(1);


                    int index205_295 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_295);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 89:
                    int LA205_339 = input.LA(1);


                    int index205_339 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_339);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 90:
                    int LA205_268 = input.LA(1);


                    int index205_268 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_268);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 91:
                    int LA205_391 = input.LA(1);


                    int index205_391 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_391);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 92:
                    int LA205_18 = input.LA(1);


                    int index205_18 = input.index();
                    input.rewind();
                    s = -1;
                    if (((LA205_18 >= '\t' && LA205_18 <= '\n') || (LA205_18 >= '\f' && LA205_18 <= '\r') || LA205_18 == ' ') && (synpred6_CSSLexer())) {
                        s = 70;
                    } else if ((LA205_18 == 'e')) {
                        s = 71;
                    } else if ((LA205_18 == '\\')) {
                        s = 72;
                    } else if ((LA205_18 == 'E')) {
                        s = 73;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_18);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 93:
                    int LA205_384 = input.LA(1);


                    int index205_384 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_384);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 94:
                    int LA205_15 = input.LA(1);


                    int index205_15 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_15 == 'm')) {
                        s = 56;
                    } else if ((LA205_15 == 'M')) {
                        s = 57;
                    } else if ((LA205_15 == '\\')) {
                        s = 58;
                    } else if (((LA205_15 >= '\t' && LA205_15 <= '\n') || (LA205_15 >= '\f' && LA205_15 <= '\r') || LA205_15 == ' ') && (synpred3_CSSLexer())) {
                        s = 59;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_15);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 95:
                    int LA205_336 = input.LA(1);


                    int index205_336 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_336);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 96:
                    int LA205_248 = input.LA(1);


                    int index205_248 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_248);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 97:
                    int LA205_433 = input.LA(1);


                    int index205_433 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_433);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 98:
                    int LA205_71 = input.LA(1);


                    int index205_71 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_71 == 'g')) {
                        s = 131;
                    } else if ((LA205_71 == 'G')) {
                        s = 132;
                    } else if ((LA205_71 == '\\')) {
                        s = 133;
                    } else if (((LA205_71 >= '\t' && LA205_71 <= '\n') || (LA205_71 >= '\f' && LA205_71 <= '\r') || LA205_71 == ' ') && (synpred6_CSSLexer())) {
                        s = 134;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_71);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 99:
                    int LA205_432 = input.LA(1);


                    int index205_432 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_432);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 100:
                    int LA205_60 = input.LA(1);


                    int index205_60 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_60);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 101:
                    int LA205_26 = input.LA(1);


                    int index205_26 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_26);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 102:
                    int LA205_25 = input.LA(1);


                    int index205_25 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_25);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 103:
                    int LA205_132 = input.LA(1);


                    int index205_132 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred6_CSSLexer())) {
                        s = 134;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_132);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 104:
                    int LA205_62 = input.LA(1);

                    s = -1;
                    if ((LA205_62 == 's')) {
                        s = 120;
                    } else if ((LA205_62 == 'S')) {
                        s = 121;
                    } else if ((LA205_62 == 'm')) {
                        s = 122;
                    } else if ((LA205_62 == '0')) {
                        s = 123;
                    } else if ((LA205_62 == '5' || LA205_62 == '7')) {
                        s = 124;
                    } else if ((LA205_62 == 'M')) {
                        s = 125;
                    } else if (((LA205_62 >= '\u0000' && LA205_62 <= '\t') || LA205_62 == '\u000B' || (LA205_62 >= '\u000E' && LA205_62 <= '/') || (LA205_62 >= '1' && LA205_62 <= '3') || (LA205_62 >= '8' && LA205_62 <= 'L') || (LA205_62 >= 'N' && LA205_62 <= 'R') || (LA205_62 >= 'T' && LA205_62 <= 'l') || (LA205_62 >= 'n' && LA205_62 <= 'r') || (LA205_62 >= 't' && LA205_62 <= '\uFFFF'))) {
                        s = 12;
                    } else if ((LA205_62 == '4' || LA205_62 == '6')) {
                        s = 126;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 105:
                    int LA205_73 = input.LA(1);


                    int index205_73 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_73 == 'g')) {
                        s = 131;
                    } else if ((LA205_73 == 'G')) {
                        s = 132;
                    } else if ((LA205_73 == '\\')) {
                        s = 133;
                    } else if (((LA205_73 >= '\t' && LA205_73 <= '\n') || (LA205_73 >= '\f' && LA205_73 <= '\r') || LA205_73 == ' ') && (synpred6_CSSLexer())) {
                        s = 134;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_73);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 106:
                    int LA205_291 = input.LA(1);


                    int index205_291 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_291);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 107:
                    int LA205_312 = input.LA(1);


                    int index205_312 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_312);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 108:
                    int LA205_183 = input.LA(1);


                    int index205_183 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_183);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 109:
                    int LA205_131 = input.LA(1);


                    int index205_131 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred6_CSSLexer())) {
                        s = 134;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_131);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 110:
                    int LA205_370 = input.LA(1);


                    int index205_370 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred8_CSSLexer())) {
                        s = 78;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_370);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 111:
                    int LA205_362 = input.LA(1);


                    int index205_362 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_362);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 112:
                    int LA205_415 = input.LA(1);


                    int index205_415 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_415);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 113:
                    int LA205_258 = input.LA(1);


                    int index205_258 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_258);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 114:
                    int LA205_316 = input.LA(1);


                    int index205_316 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_316);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 115:
                    int LA205_435 = input.LA(1);


                    int index205_435 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_435);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 116:
                    int LA205_172 = input.LA(1);


                    int index205_172 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_172);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 117:
                    int LA205_401 = input.LA(1);


                    int index205_401 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_401);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 118:
                    int LA205_395 = input.LA(1);


                    int index205_395 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_395);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 119:
                    int LA205_265 = input.LA(1);


                    int index205_265 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_265);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 120:
                    int LA205_61 = input.LA(1);


                    int index205_61 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_61);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 121:
                    int LA205_199 = input.LA(1);


                    int index205_199 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred6_CSSLexer())) {
                        s = 134;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_199);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 122:
                    int LA205_110 = input.LA(1);


                    int index205_110 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_110);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 123:
                    int LA205_259 = input.LA(1);


                    int index205_259 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_259);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 124:
                    int LA205_434 = input.LA(1);


                    int index205_434 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_434);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 125:
                    int LA205_156 = input.LA(1);


                    int index205_156 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_156);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 126:
                    int LA205_238 = input.LA(1);


                    int index205_238 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred8_CSSLexer())) {
                        s = 78;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_238);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 127:
                    int LA205_54 = input.LA(1);


                    int index205_54 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_54);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 128:
                    int LA205_367 = input.LA(1);


                    int index205_367 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_367);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 129:
                    int LA205_76 = input.LA(1);

                    s = -1;
                    if (((LA205_76 >= '\u0000' && LA205_76 <= '\t') || LA205_76 == '\u000B' || (LA205_76 >= '\u000E' && LA205_76 <= '/') || (LA205_76 >= '1' && LA205_76 <= '3') || LA205_76 == '5' || (LA205_76 >= '7' && LA205_76 <= '\uFFFF'))) {
                        s = 12;
                    } else if ((LA205_76 == '0')) {
                        s = 141;
                    } else if ((LA205_76 == '4' || LA205_76 == '6')) {
                        s = 142;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 130:
                    int LA205_403 = input.LA(1);


                    int index205_403 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_403);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 131:
                    int LA205_404 = input.LA(1);


                    int index205_404 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_CSSLexer())) {
                        s = 69;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_404);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 132:
                    int LA205_399 = input.LA(1);


                    int index205_399 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_399);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 133:
                    int LA205_56 = input.LA(1);


                    int index205_56 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_56);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 134:
                    int LA205_222 = input.LA(1);


                    int index205_222 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_222);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 135:
                    int LA205_44 = input.LA(1);


                    int index205_44 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred8_CSSLexer())) {
                        s = 78;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_44);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 136:
                    int LA205_448 = input.LA(1);


                    int index205_448 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred6_CSSLexer())) {
                        s = 134;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_448);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 137:
                    int LA205_297 = input.LA(1);


                    int index205_297 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_297);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 138:
                    int LA205_22 = input.LA(1);


                    int index205_22 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_22 == 'a')) {
                        s = 83;
                    } else if ((LA205_22 == 'Z')) {
                        s = 84;
                    } else if ((LA205_22 == '\\')) {
                        s = 85;
                    } else if (((LA205_22 >= '\t' && LA205_22 <= '\n') || (LA205_22 >= '\f' && LA205_22 <= '\r') || LA205_22 == ' ') && (synpred9_CSSLexer())) {
                        s = 86;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_22);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 139:
                    int LA205_55 = input.LA(1);


                    int index205_55 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_55);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 140:
                    int LA205_407 = input.LA(1);


                    int index205_407 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred6_CSSLexer())) {
                        s = 134;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_407);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 141:
                    int LA205_330 = input.LA(1);


                    int index205_330 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_330);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 142:
                    int LA205_45 = input.LA(1);


                    int index205_45 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred8_CSSLexer())) {
                        s = 78;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_45);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 143:
                    int LA205_154 = input.LA(1);


                    int index205_154 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_154);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 144:
                    int LA205_419 = input.LA(1);


                    int index205_419 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_419);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 145:
                    int LA205_109 = input.LA(1);


                    int index205_109 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_109);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 146:
                    int LA205_365 = input.LA(1);


                    int index205_365 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_365);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 147:
                    int LA205_148 = input.LA(1);


                    int index205_148 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_148);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 148:
                    int LA205_89 = input.LA(1);


                    int index205_89 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_89);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 149:
                    int LA205_302 = input.LA(1);


                    int index205_302 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred8_CSSLexer())) {
                        s = 78;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_302);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 150:
                    int LA205_176 = input.LA(1);


                    int index205_176 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_176);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 151:
                    int LA205_147 = input.LA(1);


                    int index205_147 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_147);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 152:
                    int LA205_245 = input.LA(1);


                    int index205_245 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_245);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 153:
                    int LA205_449 = input.LA(1);


                    int index205_449 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_449);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 154:
                    int LA205_2 = input.LA(1);

                    s = -1;
                    if ((LA205_2 == 'i')) {
                        s = 31;
                    } else if ((LA205_2 == 'I')) {
                        s = 32;
                    } else if ((LA205_2 == 'm')) {
                        s = 33;
                    } else if ((LA205_2 == '0')) {
                        s = 34;
                    } else if ((LA205_2 == '4' || LA205_2 == '6')) {
                        s = 35;
                    } else if ((LA205_2 == 'M')) {
                        s = 36;
                    } else if ((LA205_2 == 'p')) {
                        s = 37;
                    } else if ((LA205_2 == 'P')) {
                        s = 38;
                    } else if ((LA205_2 == 'k')) {
                        s = 39;
                    } else if ((LA205_2 == '5' || LA205_2 == '7')) {
                        s = 40;
                    } else if ((LA205_2 == 'K')) {
                        s = 41;
                    } else if ((LA205_2 == 'h')) {
                        s = 42;
                    } else if ((LA205_2 == 'H')) {
                        s = 43;
                    } else if ((LA205_2 == 's')) {
                        s = 44;
                    } else if ((LA205_2 == 'S')) {
                        s = 45;
                    } else if ((LA205_2 == 'r')) {
                        s = 46;
                    } else if (((LA205_2 >= '\u0000' && LA205_2 <= '\t') || LA205_2 == '\u000B' || (LA205_2 >= '\u000E' && LA205_2 <= '/') || (LA205_2 >= '1' && LA205_2 <= '3') || (LA205_2 >= '8' && LA205_2 <= 'G') || LA205_2 == 'J' || LA205_2 == 'L' || (LA205_2 >= 'N' && LA205_2 <= 'O') || LA205_2 == 'Q' || (LA205_2 >= 'T' && LA205_2 <= 'g') || LA205_2 == 'j' || LA205_2 == 'l' || (LA205_2 >= 'n' && LA205_2 <= 'o') || LA205_2 == 'q' || (LA205_2 >= 't' && LA205_2 <= '\uFFFF'))) {
                        s = 12;
                    } else if ((LA205_2 == 'R')) {
                        s = 47;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 155:
                    int LA205_48 = input.LA(1);


                    int index205_48 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_48);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 156:
                    int LA205_14 = input.LA(1);


                    int index205_14 = input.index();
                    input.rewind();
                    s = -1;
                    if (((LA205_14 >= '\t' && LA205_14 <= '\n') || (LA205_14 >= '\f' && LA205_14 <= '\r') || LA205_14 == ' ') && (synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((LA205_14 == 'x')) {
                        s = 48;
                    } else if ((LA205_14 == '\\')) {
                        s = 50;
                    } else if ((LA205_14 == 't')) {
                        s = 52;
                    } else if ((LA205_14 == 'c')) {
                        s = 54;
                    } else if ((LA205_14 == 'X')) {
                        s = 49;
                    } else if ((LA205_14 == 'T')) {
                        s = 53;
                    } else if ((LA205_14 == 'C')) {
                        s = 55;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_14);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 157:
                    int LA205_239 = input.LA(1);


                    int index205_239 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_239);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 158:
                    int LA205_187 = input.LA(1);


                    int index205_187 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_187);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 159:
                    int LA205_49 = input.LA(1);


                    int index205_49 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_49);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 160:
                    int LA205_335 = input.LA(1);


                    int index205_335 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_335);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 161:
                    int LA205_11 = input.LA(1);


                    int index205_11 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_11 == 'a')) {
                        s = 83;
                    } else if ((LA205_11 == 'Z')) {
                        s = 84;
                    } else if ((LA205_11 == '\\')) {
                        s = 85;
                    } else if (((LA205_11 >= '\t' && LA205_11 <= '\n') || (LA205_11 >= '\f' && LA205_11 <= '\r') || LA205_11 == ' ') && (synpred9_CSSLexer())) {
                        s = 86;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_11);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 162:
                    int LA205_65 = input.LA(1);


                    int index205_65 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_65);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 163:
                    int LA205_431 = input.LA(1);


                    int index205_431 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_431);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 164:
                    int LA205_366 = input.LA(1);


                    int index205_366 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_366);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 165:
                    int LA205_20 = input.LA(1);


                    int index205_20 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred8_CSSLexer())) {
                        s = 78;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_20);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 166:
                    int LA205_21 = input.LA(1);


                    int index205_21 = input.index();
                    input.rewind();
                    s = -1;
                    if (((LA205_21 >= '\t' && LA205_21 <= '\n') || (LA205_21 >= '\f' && LA205_21 <= '\r') || LA205_21 == ' ') && (synpred9_CSSLexer())) {
                        s = 79;
                    } else if ((LA205_21 == 'h')) {
                        s = 80;
                    } else if ((LA205_21 == '\\')) {
                        s = 81;
                    } else if ((LA205_21 == 'H')) {
                        s = 82;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_21);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 167:
                    int LA205_387 = input.LA(1);


                    int index205_387 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_387);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 168:
                    int LA205_388 = input.LA(1);


                    int index205_388 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_388);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 169:
                    int LA205_3 = input.LA(1);


                    int index205_3 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA205_3 == 'x')) {
                        s = 48;
                    } else if ((LA205_3 == 'X')) {
                        s = 49;
                    } else if ((LA205_3 == '\\')) {
                        s = 50;
                    } else if (((LA205_3 >= '\t' && LA205_3 <= '\n') || (LA205_3 >= '\f' && LA205_3 <= '\r') || LA205_3 == ' ') && (synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((LA205_3 == 't')) {
                        s = 52;
                    } else if ((LA205_3 == 'T')) {
                        s = 53;
                    } else if ((LA205_3 == 'c')) {
                        s = 54;
                    } else if ((LA205_3 == 'C')) {
                        s = 55;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_3);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 170:
                    int LA205_446 = input.LA(1);


                    int index205_446 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_446);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 171:
                    int LA205_74 = input.LA(1);


                    int index205_74 = input.index();
                    input.rewind();
                    s = -1;
                    if (((LA205_74 >= '\t' && LA205_74 <= '\n') || (LA205_74 >= '\f' && LA205_74 <= '\r') || LA205_74 == ' ') && (synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((LA205_74 == 'd')) {
                        s = 138;
                    } else if ((LA205_74 == '\\')) {
                        s = 139;
                    } else if ((LA205_74 == 'D')) {
                        s = 140;
                    } else {
                        s = 12;
                    }


                    input.seek(index205_74);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 172:
                    int LA205_283 = input.LA(1);


                    int index205_283 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_283);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 173:
                    int LA205_240 = input.LA(1);


                    int index205_240 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_240);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 174:
                    int LA205_282 = input.LA(1);


                    int index205_282 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_282);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 175:
                    int LA205_221 = input.LA(1);


                    int index205_221 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_221);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 176:
                    int LA205_447 = input.LA(1);


                    int index205_447 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_447);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 177:
                    int LA205_437 = input.LA(1);


                    int index205_437 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred6_CSSLexer())) {
                        s = 134;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_437);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 178:
                    int LA205_116 = input.LA(1);


                    int index205_116 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_116);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 179:
                    int LA205_133 = input.LA(1);

                    s = -1;
                    if ((LA205_133 == 'g')) {
                        s = 199;
                    } else if ((LA205_133 == '0')) {
                        s = 200;
                    } else if ((LA205_133 == 'G')) {
                        s = 201;
                    } else if (((LA205_133 >= '\u0000' && LA205_133 <= '\t') || LA205_133 == '\u000B' || (LA205_133 >= '\u000E' && LA205_133 <= '/') || (LA205_133 >= '1' && LA205_133 <= '3') || LA205_133 == '5' || (LA205_133 >= '7' && LA205_133 <= 'F') || (LA205_133 >= 'H' && LA205_133 <= 'f') || (LA205_133 >= 'h' && LA205_133 <= '\uFFFF'))) {
                        s = 12;
                    } else if ((LA205_133 == '4' || LA205_133 == '6')) {
                        s = 202;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 180:
                    int LA205_358 = input.LA(1);


                    int index205_358 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_358);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 181:
                    int LA205_162 = input.LA(1);


                    int index205_162 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred8_CSSLexer())) {
                        s = 78;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_162);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 182:
                    int LA205_111 = input.LA(1);


                    int index205_111 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_111);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 183:
                    int LA205_117 = input.LA(1);


                    int index205_117 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_117);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 184:
                    int LA205_184 = input.LA(1);


                    int index205_184 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_184);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 185:
                    int LA205_353 = input.LA(1);


                    int index205_353 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_353);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 186:
                    int LA205_325 = input.LA(1);


                    int index205_325 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_325);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 187:
                    int LA205_429 = input.LA(1);


                    int index205_429 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_429);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 188:
                    int LA205_9 = input.LA(1);


                    int index205_9 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred8_CSSLexer())) {
                        s = 78;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_9);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 189:
                    int LA205_85 = input.LA(1);

                    s = -1;
                    if ((LA205_85 == 'z')) {
                        s = 147;
                    } else if ((LA205_85 == 'Z')) {
                        s = 148;
                    } else if (((LA205_85 >= '\u0000' && LA205_85 <= '\t') || LA205_85 == '\u000B' || (LA205_85 >= '\u000E' && LA205_85 <= '/') || (LA205_85 >= '1' && LA205_85 <= '4') || LA205_85 == '6' || (LA205_85 >= '8' && LA205_85 <= 'Y') || (LA205_85 >= '[' && LA205_85 <= 'y') || (LA205_85 >= '{' && LA205_85 <= '\uFFFF'))) {
                        s = 12;
                    } else if ((LA205_85 == '0')) {
                        s = 149;
                    } else if ((LA205_85 == '5' || LA205_85 == '7')) {
                        s = 150;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 190:
                    int LA205_223 = input.LA(1);


                    int index205_223 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_223);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 191:
                    int LA205_58 = input.LA(1);

                    s = -1;
                    if ((LA205_58 == 'm')) {
                        s = 116;
                    } else if ((LA205_58 == 'M')) {
                        s = 117;
                    } else if (((LA205_58 >= '\u0000' && LA205_58 <= '\t') || LA205_58 == '\u000B' || (LA205_58 >= '\u000E' && LA205_58 <= '/') || (LA205_58 >= '1' && LA205_58 <= '3') || LA205_58 == '5' || (LA205_58 >= '7' && LA205_58 <= 'L') || (LA205_58 >= 'N' && LA205_58 <= 'l') || (LA205_58 >= 'n' && LA205_58 <= '\uFFFF'))) {
                        s = 12;
                    } else if ((LA205_58 == '0')) {
                        s = 118;
                    } else if ((LA205_58 == '4' || LA205_58 == '6')) {
                        s = 119;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 192:
                    int LA205_64 = input.LA(1);


                    int index205_64 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred4_CSSLexer())) {
                        s = 63;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_64);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 193:
                    int LA205_381 = input.LA(1);


                    int index205_381 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_381);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 194:
                    int LA205_422 = input.LA(1);


                    int index205_422 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_422);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 195:
                    int LA205_380 = input.LA(1);


                    int index205_380 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_380);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 196:
                    int LA205_315 = input.LA(1);


                    int index205_315 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_315);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 197:
                    int LA205_416 = input.LA(1);


                    int index205_416 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred7_CSSLexer())) {
                        s = 137;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_416);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 198:
                    int LA205_115 = input.LA(1);


                    int index205_115 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_115);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 199:
                    int LA205_423 = input.LA(1);


                    int index205_423 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred1_CSSLexer())) {
                        s = 28;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_423);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 200:
                    int LA205_84 = input.LA(1);


                    int index205_84 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_84);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 201:
                    int LA205_253 = input.LA(1);


                    int index205_253 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred2_CSSLexer())) {
                        s = 51;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_253);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 202:
                    int LA205_396 = input.LA(1);


                    int index205_396 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_CSSLexer())) {
                        s = 59;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_396);
                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 203:
                    int LA205_421 = input.LA(1);


                    int index205_421 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred9_CSSLexer())) {
                        s = 86;
                    } else if ((true)) {
                        s = 12;
                    }


                    input.seek(index205_421);
                    if (s >= 0) {
                        return s;
                    }
                    break;
            }
            if (state.backtracking > 0) {
                state.failed = true;
                return -1;
            }
            NoViableAltException nvae =
                    new NoViableAltException(getDescription(), 205, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA202_eotS =
            "\12\uffff";
    static final String DFA202_eofS =
            "\12\uffff";
    static final String DFA202_minS =
            "\1\103\1\uffff\1\60\2\uffff\1\60\1\64\2\60\1\64";
    static final String DFA202_maxS =
            "\1\170\1\uffff\1\170\2\uffff\1\67\1\70\3\67";
    static final String DFA202_acceptS =
            "\1\uffff\1\1\1\uffff\1\2\1\3\5\uffff";
    static final String DFA202_specialS =
            "\12\uffff}>";
    static final String[] DFA202_transitionS = {
        "\1\4\20\uffff\1\3\3\uffff\1\1\3\uffff\1\2\6\uffff\1\4\20\uffff" +
        "\1\3\3\uffff\1\1",
        "",
        "\1\5\3\uffff\1\4\1\6\1\4\1\6\34\uffff\1\3\3\uffff\1\1\33\uffff" +
        "\1\3\3\uffff\1\1",
        "",
        "",
        "\1\7\3\uffff\1\4\1\6\1\4\1\6",
        "\1\3\3\uffff\1\1",
        "\1\10\3\uffff\1\4\1\6\1\4\1\6",
        "\1\11\3\uffff\1\4\1\6\1\4\1\6",
        "\1\4\1\6\1\4\1\6"
    };
    static final short[] DFA202_eot = DFA.unpackEncodedString(DFA202_eotS);
    static final short[] DFA202_eof = DFA.unpackEncodedString(DFA202_eofS);
    static final char[] DFA202_min = DFA.unpackEncodedStringToUnsignedChars(DFA202_minS);
    static final char[] DFA202_max = DFA.unpackEncodedStringToUnsignedChars(DFA202_maxS);
    static final short[] DFA202_accept = DFA.unpackEncodedString(DFA202_acceptS);
    static final short[] DFA202_special = DFA.unpackEncodedString(DFA202_specialS);
    static final short[][] DFA202_transition;

    static {
        int numStates = DFA202_transitionS.length;
        DFA202_transition = new short[numStates][];
        for (int i = 0; i < numStates; i++) {
            DFA202_transition[i] = DFA.unpackEncodedString(DFA202_transitionS[i]);
        }
    }

    class DFA202 extends DFA {

        public DFA202(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 202;
            this.eot = DFA202_eot;
            this.eof = DFA202_eof;
            this.min = DFA202_min;
            this.max = DFA202_max;
            this.accept = DFA202_accept;
            this.special = DFA202_special;
            this.transition = DFA202_transition;
        }

        public String getDescription() {
            return "398:5: ( X | T | C )";
        }
    }
    static final String DFA212_eotS =
            "\1\uffff\1\40\1\uffff\1\42\17\uffff\1\43\2\uffff\2\25\15\uffff\1" +
            "\25\1\uffff\5\25\5\uffff\1\25\1\uffff\10\25\2\uffff\12\25\1\uffff" +
            "\12\25\1\uffff\11\25\1\uffff\15\25";
    static final String DFA212_eofS =
            "\152\uffff";
    static final String DFA212_minS =
            "\1\11\1\52\1\uffff\1\55\17\uffff\1\60\2\uffff\2\11\1\0\1\uffff\1" +
            "\111\12\uffff\1\11\1\0\1\11\2\122\1\60\1\65\2\uffff\1\60\2\uffff" +
            "\1\11\1\0\1\11\1\114\1\60\1\114\1\62\1\60\1\65\1\122\1\60\1\71\2" +
            "\50\1\60\1\103\1\60\1\62\1\114\1\60\1\65\1\122\2\60\1\103\2\50\1" +
            "\60\1\62\1\114\2\65\1\122\2\60\1\103\2\50\1\65\1\62\1\114\1\65\1" +
            "\122\2\64\1\103\2\50\1\62\1\114\1\122\1\103\2\50\1\114\2\50";
    static final String DFA212_maxS =
            "\1\uffff\1\52\1\uffff\1\uffff\17\uffff\1\71\2\uffff\2\162\1\uffff" +
            "\1\uffff\1\160\12\uffff\1\154\1\uffff\1\154\2\162\1\67\1\65\2\uffff" +
            "\1\160\2\uffff\1\50\1\uffff\1\50\1\154\1\67\1\154\1\62\1\67\1\65" +
            "\1\162\1\67\1\144\2\50\1\66\1\143\1\67\1\62\1\154\1\67\1\65\1\162" +
            "\1\67\1\66\1\143\2\50\1\67\1\62\1\154\1\67\1\65\1\162\1\67\1\66" +
            "\1\143\2\50\1\67\1\62\1\154\1\65\1\162\1\67\1\66\1\143\2\50\1\62" +
            "\1\154\1\162\1\143\2\50\1\154\2\50";
    static final String DFA212_acceptS =
            "\2\uffff\1\2\1\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1" +
            "\15\1\20\1\21\1\22\1\23\1\24\1\uffff\1\26\1\27\3\uffff\1\30\1\uffff" +
            "\1\35\1\36\1\40\1\41\1\1\1\16\1\3\1\17\1\25\1\37\7\uffff\1\34\1" +
            "\32\1\uffff\1\33\1\31\71\uffff";
    static final String DFA212_specialS =
            "\30\uffff\1\2\15\uffff\1\0\13\uffff\1\1\67\uffff}>";
    static final String[] DFA212_transitionS = {
        "\1\35\1\36\2\uffff\1\36\22\uffff\1\35\1\33\1\24\1\31\3\uffff" +
        "\1\24\1\20\1\21\1\17\1\16\1\22\1\3\1\23\1\1\12\34\1\15\1\14" +
        "\1\2\1\13\1\6\1\uffff\1\32\24\25\1\27\5\25\1\11\1\30\1\12\1" +
        "\uffff\1\25\1\uffff\24\25\1\26\5\25\1\7\1\5\1\10\1\4\1\uffff" +
        "\uff80\25",
        "\1\37",
        "",
        "\1\41\23\uffff\32\25\1\uffff\1\25\2\uffff\1\25\1\uffff\32\25" +
        "\5\uffff\uff80\25",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "\12\34",
        "",
        "",
        "\2\44\1\uffff\2\44\22\uffff\1\44\61\uffff\1\47\11\uffff\1\46" +
        "\25\uffff\1\45",
        "\2\44\1\uffff\2\44\22\uffff\1\44\61\uffff\1\47\11\uffff\1\46" +
        "\25\uffff\1\45",
        "\12\25\1\uffff\1\25\2\uffff\42\25\1\52\4\25\1\53\1\25\1\53" +
        "\35\25\1\51\37\25\1\50\uff8a\25",
        "",
        "\1\60\3\uffff\1\57\2\uffff\1\55\13\uffff\1\56\6\uffff\1\54" +
        "\5\uffff\1\60\3\uffff\1\57\2\uffff\1\55",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "\2\44\1\uffff\2\44\22\uffff\1\44\53\uffff\1\63\17\uffff\1\62" +
        "\17\uffff\1\61",
        "\12\25\1\uffff\1\25\2\uffff\42\25\1\65\4\25\1\67\1\25\1\67" +
        "\32\25\1\66\37\25\1\64\uff8d\25",
        "\2\44\1\uffff\2\44\22\uffff\1\44\53\uffff\1\63\17\uffff\1\62" +
        "\17\uffff\1\61",
        "\1\47\11\uffff\1\46\25\uffff\1\45",
        "\1\47\11\uffff\1\46\25\uffff\1\45",
        "\1\70\4\uffff\1\71\1\uffff\1\71",
        "\1\72",
        "",
        "",
        "\1\73\3\uffff\1\74\1\55\1\74\1\55\21\uffff\1\60\3\uffff\1\57" +
        "\2\uffff\1\55\30\uffff\1\60\3\uffff\1\57\2\uffff\1\55",
        "",
        "",
        "\2\44\1\uffff\2\44\22\uffff\1\44\7\uffff\1\44",
        "\12\25\1\uffff\1\25\2\uffff\42\25\1\77\3\25\1\100\1\25\1\100" +
        "\25\25\1\76\37\25\1\75\uff93\25",
        "\2\44\1\uffff\2\44\22\uffff\1\44\7\uffff\1\44",
        "\1\63\17\uffff\1\62\17\uffff\1\61",
        "\1\101\4\uffff\1\102\1\uffff\1\102",
        "\1\63\17\uffff\1\62\17\uffff\1\61",
        "\1\103",
        "\1\104\4\uffff\1\105\1\uffff\1\105",
        "\1\106",
        "\1\47\11\uffff\1\46\25\uffff\1\45",
        "\1\107\3\uffff\1\74\1\55\1\74\1\55",
        "\1\60\12\uffff\1\57\37\uffff\1\57",
        "\1\44",
        "\1\44",
        "\1\110\3\uffff\1\111\1\uffff\1\111",
        "\1\113\37\uffff\1\112",
        "\1\114\4\uffff\1\115\1\uffff\1\115",
        "\1\116",
        "\1\63\17\uffff\1\62\17\uffff\1\61",
        "\1\117\4\uffff\1\120\1\uffff\1\120",
        "\1\121",
        "\1\47\11\uffff\1\46\25\uffff\1\45",
        "\1\122\3\uffff\1\74\1\55\1\74\1\55",
        "\1\123\3\uffff\1\124\1\uffff\1\124",
        "\1\126\37\uffff\1\125",
        "\1\44",
        "\1\44",
        "\1\127\4\uffff\1\130\1\uffff\1\130",
        "\1\131",
        "\1\63\17\uffff\1\62\17\uffff\1\61",
        "\1\132\1\uffff\1\132",
        "\1\133",
        "\1\47\11\uffff\1\46\25\uffff\1\45",
        "\1\134\3\uffff\1\74\1\55\1\74\1\55",
        "\1\135\3\uffff\1\136\1\uffff\1\136",
        "\1\140\37\uffff\1\137",
        "\1\44",
        "\1\44",
        "\1\141\1\uffff\1\141",
        "\1\142",
        "\1\63\17\uffff\1\62\17\uffff\1\61",
        "\1\143",
        "\1\47\11\uffff\1\46\25\uffff\1\45",
        "\1\74\1\55\1\74\1\55",
        "\1\144\1\uffff\1\144",
        "\1\146\37\uffff\1\145",
        "\1\44",
        "\1\44",
        "\1\147",
        "\1\63\17\uffff\1\62\17\uffff\1\61",
        "\1\47\11\uffff\1\46\25\uffff\1\45",
        "\1\151\37\uffff\1\150",
        "\1\44",
        "\1\44",
        "\1\63\17\uffff\1\62\17\uffff\1\61",
        "\1\44",
        "\1\44"
    };
    static final short[] DFA212_eot = DFA.unpackEncodedString(DFA212_eotS);
    static final short[] DFA212_eof = DFA.unpackEncodedString(DFA212_eofS);
    static final char[] DFA212_min = DFA.unpackEncodedStringToUnsignedChars(DFA212_minS);
    static final char[] DFA212_max = DFA.unpackEncodedStringToUnsignedChars(DFA212_maxS);
    static final short[] DFA212_accept = DFA.unpackEncodedString(DFA212_acceptS);
    static final short[] DFA212_special = DFA.unpackEncodedString(DFA212_specialS);
    static final short[][] DFA212_transition;

    static {
        int numStates = DFA212_transitionS.length;
        DFA212_transition = new short[numStates][];
        for (int i = 0; i < numStates; i++) {
            DFA212_transition[i] = DFA.unpackEncodedString(DFA212_transitionS[i]);
        }
    }

    class DFA212 extends DFA {

        public DFA212(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 212;
            this.eot = DFA212_eot;
            this.eof = DFA212_eof;
            this.min = DFA212_min;
            this.max = DFA212_max;
            this.accept = DFA212_accept;
            this.special = DFA212_special;
            this.transition = DFA212_transition;
        }

        public String getDescription() {
            return "1:1: Tokens : ( COMMENT | CDO | CDC | INCLUDES | DASHMATCH | GREATER | LBRACE | RBRACE | LBRACKET | RBRACKET | OPEQ | SEMI | COLON | SOLIDUS | MINUS | PLUS | STAR | LPAREN | RPAREN | COMMA | DOT | STRING | IDENT | HASH | IMPORT_SYM | PAGE_SYM | MEDIA_SYM | CHARSET_SYM | IMPORTANT_SYM | NUMBER | URI | WS | NL );";
        }

        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
            int _s = s;
            switch (s) {
                case 0:
                    int LA212_38 = input.LA(1);

                    s = -1;
                    if ((LA212_38 == 'r')) {
                        s = 52;
                    } else if ((LA212_38 == '0')) {
                        s = 53;
                    } else if ((LA212_38 == 'R')) {
                        s = 54;
                    } else if (((LA212_38 >= '\u0000' && LA212_38 <= '\t') || LA212_38 == '\u000B' || (LA212_38 >= '\u000E' && LA212_38 <= '/') || (LA212_38 >= '1' && LA212_38 <= '4') || LA212_38 == '6' || (LA212_38 >= '8' && LA212_38 <= 'Q') || (LA212_38 >= 'S' && LA212_38 <= 'q') || (LA212_38 >= 's' && LA212_38 <= '\uFFFF'))) {
                        s = 21;
                    } else if ((LA212_38 == '5' || LA212_38 == '7')) {
                        s = 55;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 1:
                    int LA212_50 = input.LA(1);

                    s = -1;
                    if ((LA212_50 == 'l')) {
                        s = 61;
                    } else if ((LA212_50 == 'L')) {
                        s = 62;
                    } else if (((LA212_50 >= '\u0000' && LA212_50 <= '\t') || LA212_50 == '\u000B' || (LA212_50 >= '\u000E' && LA212_50 <= '/') || (LA212_50 >= '1' && LA212_50 <= '3') || LA212_50 == '5' || (LA212_50 >= '7' && LA212_50 <= 'K') || (LA212_50 >= 'M' && LA212_50 <= 'k') || (LA212_50 >= 'm' && LA212_50 <= '\uFFFF'))) {
                        s = 21;
                    } else if ((LA212_50 == '0')) {
                        s = 63;
                    } else if ((LA212_50 == '4' || LA212_50 == '6')) {
                        s = 64;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
                case 2:
                    int LA212_24 = input.LA(1);

                    s = -1;
                    if ((LA212_24 == 'u')) {
                        s = 40;
                    } else if ((LA212_24 == 'U')) {
                        s = 41;
                    } else if (((LA212_24 >= '\u0000' && LA212_24 <= '\t') || LA212_24 == '\u000B' || (LA212_24 >= '\u000E' && LA212_24 <= '/') || (LA212_24 >= '1' && LA212_24 <= '4') || LA212_24 == '6' || (LA212_24 >= '8' && LA212_24 <= 'T') || (LA212_24 >= 'V' && LA212_24 <= 't') || (LA212_24 >= 'v' && LA212_24 <= '\uFFFF'))) {
                        s = 21;
                    } else if ((LA212_24 == '0')) {
                        s = 42;
                    } else if ((LA212_24 == '5' || LA212_24 == '7')) {
                        s = 43;
                    }

                    if (s >= 0) {
                        return s;
                    }
                    break;
            }
            if (state.backtracking > 0) {
                state.failed = true;
                return -1;
            }
            NoViableAltException nvae =
                    new NoViableAltException(getDescription(), 212, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA214_eotS =
            "\12\uffff";
    static final String DFA214_eofS =
            "\12\uffff";
    static final String DFA214_minS =
            "\1\103\1\uffff\1\60\2\uffff\1\60\1\64\2\60\1\64";
    static final String DFA214_maxS =
            "\1\170\1\uffff\1\170\2\uffff\1\67\1\70\3\67";
    static final String DFA214_acceptS =
            "\1\uffff\1\1\1\uffff\1\2\1\3\5\uffff";
    static final String DFA214_specialS =
            "\12\uffff}>";
    static final String[] DFA214_transitionS = {
        "\1\4\20\uffff\1\3\3\uffff\1\1\3\uffff\1\2\6\uffff\1\4\20\uffff" +
        "\1\3\3\uffff\1\1",
        "",
        "\1\5\3\uffff\1\4\1\6\1\4\1\6\34\uffff\1\3\3\uffff\1\1\33\uffff" +
        "\1\3\3\uffff\1\1",
        "",
        "",
        "\1\7\3\uffff\1\4\1\6\1\4\1\6",
        "\1\3\3\uffff\1\1",
        "\1\10\3\uffff\1\4\1\6\1\4\1\6",
        "\1\11\3\uffff\1\4\1\6\1\4\1\6",
        "\1\4\1\6\1\4\1\6"
    };
    static final short[] DFA214_eot = DFA.unpackEncodedString(DFA214_eotS);
    static final short[] DFA214_eof = DFA.unpackEncodedString(DFA214_eofS);
    static final char[] DFA214_min = DFA.unpackEncodedStringToUnsignedChars(DFA214_minS);
    static final char[] DFA214_max = DFA.unpackEncodedStringToUnsignedChars(DFA214_maxS);
    static final short[] DFA214_accept = DFA.unpackEncodedString(DFA214_acceptS);
    static final short[] DFA214_special = DFA.unpackEncodedString(DFA214_specialS);
    static final short[][] DFA214_transition;

    static {
        int numStates = DFA214_transitionS.length;
        DFA214_transition = new short[numStates][];
        for (int i = 0; i < numStates; i++) {
            DFA214_transition[i] = DFA.unpackEncodedString(DFA214_transitionS[i]);
        }
    }

    class DFA214 extends DFA {

        public DFA214(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 214;
            this.eot = DFA214_eot;
            this.eof = DFA214_eof;
            this.min = DFA214_min;
            this.max = DFA214_max;
            this.accept = DFA214_accept;
            this.special = DFA214_special;
            this.transition = DFA214_transition;
        }

        public String getDescription() {
            return "396:8: ( X | T | C )";
        }
    }
}
