/**  
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
**/ 
 
package org.semanticwb.nlp.analysis;

/**
 * ANTLR generated lexer for spanish to SparQl translation.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
// $ANTLR 3.1.2 /home/hasdai/Documentos/INFOTEC/SpanishLexer.g 2009-07-17 11:25:29

import org.antlr.runtime.*;

public class SpanishLexer extends Lexer {
    public static final int MODO=17;
    public static final int SIGL=6;
    public static final int RPAR=29;
    public static final int SIGGE=10;
    public static final int MODN=16;
    public static final int LPAR=28;
    public static final int PREC=11;
    public static final int SIGG=7;
    public static final int MODE=15;
    public static final int LIT=21;
    public static final int PRED=13;
    public static final int BOL=19;
    public static final int SIGI=31;
    public static final int DELY=26;
    public static final int WHITESPACE=4;
    public static final int SIGLE=9;
    public static final int ORDOP=25;
    public static final int VAR=27;
    public static final int MODT=12;
    public static final int MODC=18;
    public static final int PREN=14;
    public static final int LBRK=22;
    public static final int EOF=-1;
    public static final int NUM=20;
    public static final int SIGN=5;
    public static final int BVAR=24;
    public static final int DEL=30;
    public static final int SIGE=8;
    public static final int RBRK=23;

    // delegates
    // delegators

    public SpanishLexer() {;}
    public SpanishLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public SpanishLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/home/hasdai/Documentos/INFOTEC/SpanishLexer.g"; }

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:4:2: ( ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:4:4: ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

             _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHITESPACE"

    // $ANTLR start "SIGN"
    public final void mSIGN() throws RecognitionException {
        try {
            int _type = SIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:5:6: ( '+' | '-' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:
            {
            if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SIGN"

    // $ANTLR start "SIGL"
    public final void mSIGL() throws RecognitionException {
        try {
            int _type = SIGL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:6:6: ( '<' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:6:8: '<'
            {
            match('<');

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SIGL"

    // $ANTLR start "SIGG"
    public final void mSIGG() throws RecognitionException {
        try {
            int _type = SIGG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:7:6: ( '>' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:7:8: '>'
            {
            match('>');

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SIGG"

    // $ANTLR start "SIGE"
    public final void mSIGE() throws RecognitionException {
        try {
            int _type = SIGE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:8:6: ( '=' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:8:8: '='
            {
            match('=');

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SIGE"

    // $ANTLR start "SIGLE"
    public final void mSIGLE() throws RecognitionException {
        try {
            int _type = SIGLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:9:7: ( '<=' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:9:9: '<='
            {
            match("<=");


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SIGLE"

    // $ANTLR start "SIGGE"
    public final void mSIGGE() throws RecognitionException {
        try {
            int _type = SIGGE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:10:7: ( '>=' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:10:9: '>='
            {
            match(">=");


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SIGGE"

    // $ANTLR start "PREC"
    public final void mPREC() throws RecognitionException {
        try {
            int _type = PREC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:11:6: ( 'con' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:11:8: 'con'
            {
            match("con");


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PREC"

    // $ANTLR start "MODT"
    public final void mMODT() throws RecognitionException {
        try {
            int _type = MODT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:12:6: ( 'todo' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:12:8: 'todo'
            {
            match("todo");


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MODT"

    // $ANTLR start "PRED"
    public final void mPRED() throws RecognitionException {
        try {
            int _type = PRED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:13:6: ( 'de' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:13:8: 'de'
            {
            match("de");


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PRED"

    // $ANTLR start "PREN"
    public final void mPREN() throws RecognitionException {
        try {
            int _type = PREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:14:6: ( 'en' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:14:8: 'en'
            {
            match("en");


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PREN"

    // $ANTLR start "MODE"
    public final void mMODE() throws RecognitionException {
        try {
            int _type = MODE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:15:6: ( 'desde' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:15:8: 'desde'
            {
            match("desde");


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MODE"

    // $ANTLR start "MODN"
    public final void mMODN() throws RecognitionException {
        try {
            int _type = MODN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:16:6: ( 'entre' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:16:8: 'entre'
            {
            match("entre");


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MODN"

    // $ANTLR start "MODO"
    public final void mMODO() throws RecognitionException {
        try {
            int _type = MODO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:17:6: ( 'ordenar' | 'ordenado' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='o') ) {
                int LA1_1 = input.LA(2);

                if ( (LA1_1=='r') ) {
                    int LA1_2 = input.LA(3);

                    if ( (LA1_2=='d') ) {
                        int LA1_3 = input.LA(4);

                        if ( (LA1_3=='e') ) {
                            int LA1_4 = input.LA(5);

                            if ( (LA1_4=='n') ) {
                                int LA1_5 = input.LA(6);

                                if ( (LA1_5=='a') ) {
                                    int LA1_6 = input.LA(7);

                                    if ( (LA1_6=='r') ) {
                                        alt1=1;
                                    }
                                    else if ( (LA1_6=='d') ) {
                                        alt1=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 1, 6, input);

                                        throw nvae;
                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 1, 5, input);

                                    throw nvae;
                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 1, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 1, 3, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 1, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:17:8: 'ordenar'
                    {
                    match("ordenar");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:17:18: 'ordenado'
                    {
                    match("ordenado");


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MODO"

    // $ANTLR start "MODC"
    public final void mMODC() throws RecognitionException {
        try {
            int _type = MODC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:18:9: ( 'como' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:18:15: 'como'
            {
            match("como");


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MODC"

    // $ANTLR start "BOL"
    public final void mBOL() throws RecognitionException {
        try {
            int _type = BOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:19:5: ( 'true' | 'false' )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='t') ) {
                alt2=1;
            }
            else if ( (LA2_0=='f') ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:19:7: 'true'
                    {
                    match("true");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:19:14: 'false'
                    {
                    match("false");


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BOL"

    // $ANTLR start "NUM"
    public final void mNUM() throws RecognitionException {
        try {
            int _type = NUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:5: ( ( SIGN )? ( ( '0' .. '9' )+ | ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ) ) )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:7: ( SIGN )? ( ( '0' .. '9' )+ | ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ) )
            {
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:7: ( SIGN )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='+'||LA3_0=='-') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:7: SIGN
                    {
                    mSIGN();

                    }
                    break;

            }

            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:13: ( ( '0' .. '9' )+ | ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ) )
            int alt7=2;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:14: ( '0' .. '9' )+
                    {
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:14: ( '0' .. '9' )+
                    int cnt4=0;
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:14: '0' .. '9'
                    	    {
                    	    matchRange('0','9');

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt4 >= 1 ) break loop4;
                                EarlyExitException eee =
                                    new EarlyExitException(4, input);
                                throw eee;
                        }
                        cnt4++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:26: ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ )
                    {
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:26: ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ )
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:27: ( '0' .. '9' )* '.' ( '0' .. '9' )+
                    {
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:27: ( '0' .. '9' )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:27: '0' .. '9'
                    	    {
                    	    matchRange('0','9');

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);

                    match('.');
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:41: ( '0' .. '9' )+
                    int cnt6=0;
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:20:41: '0' .. '9'
                    	    {
                    	    matchRange('0','9');

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt6 >= 1 ) break loop6;
                                EarlyExitException eee =
                                    new EarlyExitException(6, input);
                                throw eee;
                        }
                        cnt6++;
                    } while (true);


                    }


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUM"

    // $ANTLR start "LIT"
    public final void mLIT() throws RecognitionException {
        try {
            int _type = LIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:21:5: ( '\"' (~ ( '\"' | '\\n' | '\\r' | '\\t' ) )* '\"' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:21:7: '\"' (~ ( '\"' | '\\n' | '\\r' | '\\t' ) )* '\"'
            {
            match('\"');
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:21:10: (~ ( '\"' | '\\n' | '\\r' | '\\t' ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='\u0000' && LA8_0<='\b')||(LA8_0>='\u000B' && LA8_0<='\f')||(LA8_0>='\u000E' && LA8_0<='!')||(LA8_0>='#' && LA8_0<='\uFFFF')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:21:11: ~ ( '\"' | '\\n' | '\\r' | '\\t' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\b')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            match('\"');

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LIT"

    // $ANTLR start "BVAR"
    public final void mBVAR() throws RecognitionException {
        try {
            int _type = BVAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:22:6: ( LBRK (~ ( ']' | '[' | '\\n' | '\\r' | '\\t' ) )* RBRK )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:22:8: LBRK (~ ( ']' | '[' | '\\n' | '\\r' | '\\t' ) )* RBRK
            {
            mLBRK();
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:22:12: (~ ( ']' | '[' | '\\n' | '\\r' | '\\t' ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='\u0000' && LA9_0<='\b')||(LA9_0>='\u000B' && LA9_0<='\f')||(LA9_0>='\u000E' && LA9_0<='Z')||LA9_0=='\\'||(LA9_0>='^' && LA9_0<='\uFFFF')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:22:13: ~ ( ']' | '[' | '\\n' | '\\r' | '\\t' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\b')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='Z')||input.LA(1)=='\\'||(input.LA(1)>='^' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            mRBRK();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BVAR"

    // $ANTLR start "ORDOP"
    public final void mORDOP() throws RecognitionException {
        try {
            int _type = ORDOP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:23:7: ( 'asc' | 'des' )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='a') ) {
                alt10=1;
            }
            else if ( (LA10_0=='d') ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:23:9: 'asc'
                    {
                    match("asc");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:23:15: 'des'
                    {
                    match("des");


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ORDOP"

    // $ANTLR start "DELY"
    public final void mDELY() throws RecognitionException {
        try {
            int _type = DELY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:24:6: ( 'y' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:24:8: 'y'
            {
            match('y');

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DELY"

    // $ANTLR start "VAR"
    public final void mVAR() throws RecognitionException {
        try {
            int _type = VAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:25:5: ( ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | 'á' | 'é' | 'í' | 'ó' | 'ú' | 'ä' | 'ë' | 'ï' | 'ö' | 'ü' | 'Ä' | 'Ë' | 'Ï' | 'Ö' | 'Ü' | 'Á' | 'É' | 'Í' | 'Ó' | 'Ú' | 'Ñ' | 'ñ' )+ )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:25:7: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | 'á' | 'é' | 'í' | 'ó' | 'ú' | 'ä' | 'ë' | 'ï' | 'ö' | 'ü' | 'Ä' | 'Ë' | 'Ï' | 'Ö' | 'Ü' | 'Á' | 'É' | 'Í' | 'Ó' | 'Ú' | 'Ñ' | 'ñ' )+
            {
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:25:7: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | 'á' | 'é' | 'í' | 'ó' | 'ú' | 'ä' | 'ë' | 'ï' | 'ö' | 'ü' | 'Ä' | 'Ë' | 'Ï' | 'Ö' | 'Ü' | 'Á' | 'É' | 'Í' | 'Ó' | 'Ú' | 'Ñ' | 'ñ' )+
            int cnt11=0;
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0=='-'||(LA11_0>='0' && LA11_0<='9')||(LA11_0>='A' && LA11_0<='Z')||LA11_0=='_'||(LA11_0>='a' && LA11_0<='z')||LA11_0=='\u00C1'||LA11_0=='\u00C4'||LA11_0=='\u00C9'||LA11_0=='\u00CB'||LA11_0=='\u00CD'||LA11_0=='\u00CF'||LA11_0=='\u00D1'||LA11_0=='\u00D3'||LA11_0=='\u00D6'||LA11_0=='\u00DA'||LA11_0=='\u00DC'||LA11_0=='\u00E1'||LA11_0=='\u00E4'||LA11_0=='\u00E9'||LA11_0=='\u00EB'||LA11_0=='\u00ED'||LA11_0=='\u00EF'||LA11_0=='\u00F1'||LA11_0=='\u00F3'||LA11_0=='\u00F6'||LA11_0=='\u00FA'||LA11_0=='\u00FC') ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:
            	    {
            	    if ( input.LA(1)=='-'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||input.LA(1)=='\u00C1'||input.LA(1)=='\u00C4'||input.LA(1)=='\u00C9'||input.LA(1)=='\u00CB'||input.LA(1)=='\u00CD'||input.LA(1)=='\u00CF'||input.LA(1)=='\u00D1'||input.LA(1)=='\u00D3'||input.LA(1)=='\u00D6'||input.LA(1)=='\u00DA'||input.LA(1)=='\u00DC'||input.LA(1)=='\u00E1'||input.LA(1)=='\u00E4'||input.LA(1)=='\u00E9'||input.LA(1)=='\u00EB'||input.LA(1)=='\u00ED'||input.LA(1)=='\u00EF'||input.LA(1)=='\u00F1'||input.LA(1)=='\u00F3'||input.LA(1)=='\u00F6'||input.LA(1)=='\u00FA'||input.LA(1)=='\u00FC' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VAR"

    // $ANTLR start "LPAR"
    public final void mLPAR() throws RecognitionException {
        try {
            int _type = LPAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:26:6: ( '(' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:26:8: '('
            {
            match('(');

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LPAR"

    // $ANTLR start "RPAR"
    public final void mRPAR() throws RecognitionException {
        try {
            int _type = RPAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:27:6: ( ')' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:27:8: ')'
            {
            match(')');

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RPAR"

    // $ANTLR start "LBRK"
    public final void mLBRK() throws RecognitionException {
        try {
            int _type = LBRK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:28:6: ( '[' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:28:8: '['
            {
            match('[');

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRK"

    // $ANTLR start "RBRK"
    public final void mRBRK() throws RecognitionException {
        try {
            int _type = RBRK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:29:6: ( ']' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:29:8: ']'
            {
            match(']');

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRK"

    // $ANTLR start "DEL"
    public final void mDEL() throws RecognitionException {
        try {
            int _type = DEL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:30:5: ( ',' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:30:7: ','
            {
            match(',');

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DEL"

    // $ANTLR start "SIGI"
    public final void mSIGI() throws RecognitionException {
        try {
            int _type = SIGI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:31:6: ( '\\?' )
            // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:31:8: '\\?'
            {
            match('?');

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SIGI"

    public void mTokens() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:8: ( WHITESPACE | SIGN | SIGL | SIGG | SIGE | SIGLE | SIGGE | PREC | MODT | PRED | PREN | MODE | MODN | MODO | MODC | BOL | NUM | LIT | BVAR | ORDOP | DELY | VAR | LPAR | RPAR | LBRK | RBRK | DEL | SIGI )
        int alt12=28;
        alt12 = dfa12.predict(input);
        switch (alt12) {
            case 1 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:10: WHITESPACE
                {
                mWHITESPACE();

                }
                break;
            case 2 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:21: SIGN
                {
                mSIGN();

                }
                break;
            case 3 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:26: SIGL
                {
                mSIGL();

                }
                break;
            case 4 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:31: SIGG
                {
                mSIGG();

                }
                break;
            case 5 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:36: SIGE
                {
                mSIGE();

                }
                break;
            case 6 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:41: SIGLE
                {
                mSIGLE();

                }
                break;
            case 7 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:47: SIGGE
                {
                mSIGGE();

                }
                break;
            case 8 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:53: PREC
                {
                mPREC();

                }
                break;
            case 9 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:58: MODT
                {
                mMODT();

                }
                break;
            case 10 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:63: PRED
                {
                mPRED();

                }
                break;
            case 11 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:68: PREN
                {
                mPREN();

                }
                break;
            case 12 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:73: MODE
                {
                mMODE();

                }
                break;
            case 13 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:78: MODN
                {
                mMODN();

                }
                break;
            case 14 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:83: MODO
                {
                mMODO();

                }
                break;
            case 15 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:88: MODC
                {
                mMODC();

                }
                break;
            case 16 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:93: BOL
                {
                mBOL();

                }
                break;
            case 17 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:97: NUM
                {
                mNUM();

                }
                break;
            case 18 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:101: LIT
                {
                mLIT();

                }
                break;
            case 19 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:105: BVAR
                {
                mBVAR();

                }
                break;
            case 20 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:110: ORDOP
                {
                mORDOP();

                }
                break;
            case 21 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:116: DELY
                {
                mDELY();

                }
                break;
            case 22 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:121: VAR
                {
                mVAR();

                }
                break;
            case 23 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:125: LPAR
                {
                mLPAR();

                }
                break;
            case 24 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:130: RPAR
                {
                mRPAR();

                }
                break;
            case 25 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:135: LBRK
                {
                mLBRK();

                }
                break;
            case 26 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:140: RBRK
                {
                mRBRK();

                }
                break;
            case 27 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:145: DEL
                {
                mDEL();

                }
                break;
            case 28 :
                // /home/hasdai/Documentos/INFOTEC/SpanishLexer.g:1:149: SIGI
                {
                mSIGI();

                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA7_eotS =
        "\1\uffff\1\3\2\uffff";
    static final String DFA7_eofS =
        "\4\uffff";
    static final String DFA7_minS =
        "\2\56\2\uffff";
    static final String DFA7_maxS =
        "\2\71\2\uffff";
    static final String DFA7_acceptS =
        "\2\uffff\1\2\1\1";
    static final String DFA7_specialS =
        "\4\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\2\1\uffff\12\1",
            "",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "20:13: ( ( '0' .. '9' )+ | ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ) )";
        }
    }
    static final String DFA12_eotS =
        "\2\uffff\1\31\1\33\1\35\1\uffff\6\23\1\15\2\uffff\1\45\1\23\1\50"+
        "\1\31\13\uffff\3\23\1\56\1\60\2\23\2\uffff\1\23\1\uffff\1\64\3\23"+
        "\1\71\1\uffff\1\23\1\uffff\2\23\1\71\1\uffff\1\75\1\76\1\77\1\23"+
        "\1\uffff\3\23\3\uffff\1\104\1\105\1\23\1\77\2\uffff\1\23\1\111\1"+
        "\23\1\uffff\1\111";
    static final String DFA12_eofS =
        "\113\uffff";
    static final String DFA12_minS =
        "\1\11\1\uffff\1\55\2\75\1\uffff\2\157\1\145\1\156\1\162\1\141\1"+
        "\55\2\uffff\1\0\1\163\1\55\1\56\13\uffff\1\155\1\144\1\165\2\55"+
        "\1\144\1\154\2\uffff\1\143\1\uffff\1\55\2\157\1\145\1\55\1\uffff"+
        "\1\162\1\uffff\1\145\1\163\1\55\1\uffff\3\55\1\145\1\uffff\1\145"+
        "\1\156\1\145\3\uffff\2\55\1\141\1\55\2\uffff\1\144\1\55\1\157\1"+
        "\uffff\1\55";
    static final String DFA12_maxS =
        "\1\u00fc\1\uffff\1\u00fc\2\75\1\uffff\1\157\1\162\1\145\1\156\1"+
        "\162\1\141\1\u00fc\2\uffff\1\uffff\1\163\1\u00fc\1\71\13\uffff\1"+
        "\156\1\144\1\165\2\u00fc\1\144\1\154\2\uffff\1\143\1\uffff\1\u00fc"+
        "\2\157\1\145\1\u00fc\1\uffff\1\162\1\uffff\1\145\1\163\1\u00fc\1"+
        "\uffff\3\u00fc\1\145\1\uffff\1\145\1\156\1\145\3\uffff\2\u00fc\1"+
        "\141\1\u00fc\2\uffff\1\162\1\u00fc\1\157\1\uffff\1\u00fc";
    static final String DFA12_acceptS =
        "\1\uffff\1\1\3\uffff\1\5\7\uffff\1\21\1\22\4\uffff\1\26\1\27\1\30"+
        "\1\32\1\33\1\34\1\2\1\6\1\3\1\7\1\4\7\uffff\1\31\1\23\1\uffff\1"+
        "\25\5\uffff\1\12\1\uffff\1\13\3\uffff\1\10\4\uffff\1\24\3\uffff"+
        "\1\17\1\11\1\20\4\uffff\1\14\1\15\3\uffff\1\16\1\uffff";
    static final String DFA12_specialS =
        "\17\uffff\1\0\73\uffff}>";
    static final String[] DFA12_transitionS = {
            "\2\1\1\uffff\2\1\22\uffff\1\1\1\uffff\1\16\5\uffff\1\24\1\25"+
            "\1\uffff\1\22\1\27\1\2\1\15\1\uffff\12\14\2\uffff\1\3\1\5\1"+
            "\4\1\30\1\uffff\32\23\1\17\1\uffff\1\26\1\uffff\1\23\1\uffff"+
            "\1\20\1\23\1\6\1\10\1\11\1\13\10\23\1\12\4\23\1\7\4\23\1\21"+
            "\1\23\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23"+
            "\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff"+
            "\1\23\3\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4"+
            "\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1"+
            "\23\1\uffff\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "",
            "\1\23\1\15\1\uffff\12\14\7\uffff\32\23\4\uffff\1\23\1\uffff"+
            "\32\23\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23"+
            "\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff"+
            "\1\23\3\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4"+
            "\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1"+
            "\23\1\uffff\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "\1\32",
            "\1\34",
            "",
            "\1\36",
            "\1\37\2\uffff\1\40",
            "\1\41",
            "\1\42",
            "\1\43",
            "\1\44",
            "\1\23\2\uffff\12\14\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23"+
            "\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff\1\23\3"+
            "\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4\uffff\1"+
            "\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "",
            "",
            "\11\46\2\uffff\2\46\1\uffff\115\46\1\uffff\uffa4\46",
            "\1\47",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23"+
            "\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff\1\23\3"+
            "\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4\uffff\1"+
            "\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "\1\15\1\uffff\12\15",
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
            "\1\52\1\51",
            "\1\53",
            "\1\54",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\22\23"+
            "\1\55\7\23\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2"+
            "\uffff\1\23\3\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1"+
            "\23\4\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\23\23"+
            "\1\57\6\23\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2"+
            "\uffff\1\23\3\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1"+
            "\23\4\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "\1\61",
            "\1\62",
            "",
            "",
            "\1\63",
            "",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23"+
            "\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff\1\23\3"+
            "\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4\uffff\1"+
            "\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\3\23"+
            "\1\70\26\23\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2"+
            "\uffff\1\23\3\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1"+
            "\23\4\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "",
            "\1\72",
            "",
            "\1\73",
            "\1\74",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23"+
            "\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff\1\23\3"+
            "\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4\uffff\1"+
            "\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23"+
            "\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff\1\23\3"+
            "\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4\uffff\1"+
            "\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23"+
            "\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff\1\23\3"+
            "\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4\uffff\1"+
            "\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23"+
            "\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff\1\23\3"+
            "\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4\uffff\1"+
            "\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "\1\100",
            "",
            "\1\101",
            "\1\102",
            "\1\103",
            "",
            "",
            "",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23"+
            "\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff\1\23\3"+
            "\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4\uffff\1"+
            "\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23"+
            "\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff\1\23\3"+
            "\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4\uffff\1"+
            "\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "\1\106",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23"+
            "\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff\1\23\3"+
            "\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4\uffff\1"+
            "\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "",
            "",
            "\1\110\15\uffff\1\107",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23"+
            "\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff\1\23\3"+
            "\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4\uffff\1"+
            "\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23",
            "\1\112",
            "",
            "\1\23\2\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23"+
            "\106\uffff\1\23\2\uffff\1\23\4\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\2\uffff\1\23\3"+
            "\uffff\1\23\1\uffff\1\23\4\uffff\1\23\2\uffff\1\23\4\uffff\1"+
            "\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff\1\23\1\uffff"+
            "\1\23\2\uffff\1\23\3\uffff\1\23\1\uffff\1\23"
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( WHITESPACE | SIGN | SIGL | SIGG | SIGE | SIGLE | SIGGE | PREC | MODT | PRED | PREN | MODE | MODN | MODO | MODC | BOL | NUM | LIT | BVAR | ORDOP | DELY | VAR | LPAR | RPAR | LBRK | RBRK | DEL | SIGI );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA12_15 = input.LA(1);

                        s = -1;
                        if ( ((LA12_15>='\u0000' && LA12_15<='\b')||(LA12_15>='\u000B' && LA12_15<='\f')||(LA12_15>='\u000E' && LA12_15<='Z')||(LA12_15>='\\' && LA12_15<='\uFFFF')) ) {s = 38;}

                        else s = 37;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 12, _s, input);
            error(nvae);
            throw nvae;
        }
    }


}