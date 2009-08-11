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
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp.analysis;

/**
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
// $ANTLR 3.1.2 /home/hasdai/Documentos/INFOTEC/SimpleLexer.g 2009-07-14 17:20:36

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class SimpleLexer extends Lexer {
    public static final int MODO=16;
    public static final int SIGL=6;
    public static final int RPAR=27;
    public static final int SIGGE=10;
    public static final int LPAR=26;
    public static final int PREC=11;
    public static final int SIGG=7;
    public static final int MODE=15;
    public static final int LIT=20;
    public static final int PRED=13;
    public static final int BOL=18;
    public static final int SIGI=29;
    public static final int WHITESPACE=4;
    public static final int SIGLE=9;
    public static final int ORDOP=24;
    public static final int VAR=25;
    public static final int MODT=12;
    public static final int MODC=17;
    public static final int PREN=14;
    public static final int LBRK=21;
    public static final int EOF=-1;
    public static final int NUM=19;
    public static final int SIGN=5;
    public static final int BVAR=23;
    public static final int DEL=28;
    public static final int SIGE=8;
    public static final int RBRK=22;

    // delegates
    // delegators

    public SimpleLexer() {;}
    public SimpleLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public SimpleLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/home/hasdai/Documentos/INFOTEC/SimpleLexer.g"; }

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:4:2: ( ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:4:4: ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' )
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:5:6: ( '+' | '-' )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:6:6: ( '<' )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:6:8: '<'
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:7:6: ( '>' )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:7:8: '>'
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:8:6: ( '=' )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:8:8: '='
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:9:7: ( '<=' )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:9:9: '<='
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:10:7: ( '>=' )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:10:9: '>='
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:11:6: ( 'CON' | 'Con' | 'con' )
            int alt1=3;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='C') ) {
                int LA1_1 = input.LA(2);

                if ( (LA1_1=='O') ) {
                    alt1=1;
                }
                else if ( (LA1_1=='o') ) {
                    alt1=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA1_0=='c') ) {
                alt1=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:11:8: 'CON'
                    {
                    match("CON");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:11:16: 'Con'
                    {
                    match("Con");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:11:24: 'con'
                    {
                    match("con");


                    }
                    break;

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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:12:6: ( 'TODO' | 'Todo' | 'todo' )
            int alt2=3;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='T') ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1=='O') ) {
                    alt2=1;
                }
                else if ( (LA2_1=='o') ) {
                    alt2=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA2_0=='t') ) {
                alt2=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:12:8: 'TODO'
                    {
                    match("TODO");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:12:17: 'Todo'
                    {
                    match("Todo");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:12:26: 'todo'
                    {
                    match("todo");


                    }
                    break;

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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:13:6: ( 'DE' | 'De' | 'de' )
            int alt3=3;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='D') ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1=='E') ) {
                    alt3=1;
                }
                else if ( (LA3_1=='e') ) {
                    alt3=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA3_0=='d') ) {
                alt3=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:13:8: 'DE'
                    {
                    match("DE");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:13:15: 'De'
                    {
                    match("De");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:13:22: 'de'
                    {
                    match("de");


                    }
                    break;

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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:14:6: ( 'EN' | 'En' | 'en' )
            int alt4=3;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='E') ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1=='N') ) {
                    alt4=1;
                }
                else if ( (LA4_1=='n') ) {
                    alt4=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA4_0=='e') ) {
                alt4=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:14:8: 'EN'
                    {
                    match("EN");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:14:15: 'En'
                    {
                    match("En");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:14:22: 'en'
                    {
                    match("en");


                    }
                    break;

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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:15:6: ( 'DESDE' | 'Desde' | 'desde' )
            int alt5=3;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='D') ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1=='E') ) {
                    alt5=1;
                }
                else if ( (LA5_1=='e') ) {
                    alt5=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA5_0=='d') ) {
                alt5=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:15:8: 'DESDE'
                    {
                    match("DESDE");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:15:18: 'Desde'
                    {
                    match("Desde");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:15:28: 'desde'
                    {
                    match("desde");


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MODE"

    // $ANTLR start "MODO"
    public final void mMODO() throws RecognitionException {
        try {
            int _type = MODO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:16:6: ( 'ORDENAR' | 'Ordenar' | 'ordenar' | 'Ordenado' )
            int alt6=4;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:16:8: 'ORDENAR'
                    {
                    match("ORDENAR");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:16:20: 'Ordenar'
                    {
                    match("Ordenar");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:16:32: 'ordenar'
                    {
                    match("ordenar");


                    }
                    break;
                case 4 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:16:42: 'Ordenado'
                    {
                    match("Ordenado");


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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:17:10: ( 'COMO' | 'Como' | 'como' )
            int alt7=3;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='C') ) {
                int LA7_1 = input.LA(2);

                if ( (LA7_1=='O') ) {
                    alt7=1;
                }
                else if ( (LA7_1=='o') ) {
                    alt7=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA7_0=='c') ) {
                alt7=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:17:19: 'COMO'
                    {
                    match("COMO");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:17:28: 'Como'
                    {
                    match("Como");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:17:37: 'como'
                    {
                    match("como");


                    }
                    break;

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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:18:5: ( 'true' | 'TRUE' | 'false' | 'FALSE' | 'True' | 'False' )
            int alt8=6;
            switch ( input.LA(1) ) {
            case 't':
                {
                alt8=1;
                }
                break;
            case 'T':
                {
                int LA8_2 = input.LA(2);

                if ( (LA8_2=='R') ) {
                    alt8=2;
                }
                else if ( (LA8_2=='r') ) {
                    alt8=5;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 2, input);

                    throw nvae;
                }
                }
                break;
            case 'f':
                {
                alt8=3;
                }
                break;
            case 'F':
                {
                int LA8_4 = input.LA(2);

                if ( (LA8_4=='A') ) {
                    alt8=4;
                }
                else if ( (LA8_4=='a') ) {
                    alt8=6;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 4, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:18:7: 'true'
                    {
                    match("true");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:18:16: 'TRUE'
                    {
                    match("TRUE");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:18:25: 'false'
                    {
                    match("false");


                    }
                    break;
                case 4 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:18:35: 'FALSE'
                    {
                    match("FALSE");


                    }
                    break;
                case 5 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:18:45: 'True'
                    {
                    match("True");


                    }
                    break;
                case 6 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:18:54: 'False'
                    {
                    match("False");


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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:5: ( ( SIGN )? ( ( '0' .. '9' )+ | ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ) ) )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:7: ( SIGN )? ( ( '0' .. '9' )+ | ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ) )
            {
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:7: ( SIGN )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='+'||LA9_0=='-') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:7: SIGN
                    {
                    mSIGN();

                    }
                    break;

            }

            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:13: ( ( '0' .. '9' )+ | ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ) )
            int alt13=2;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:14: ( '0' .. '9' )+
                    {
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:14: ( '0' .. '9' )+
                    int cnt10=0;
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:14: '0' .. '9'
                    	    {
                    	    matchRange('0','9');

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt10 >= 1 ) break loop10;
                                EarlyExitException eee =
                                    new EarlyExitException(10, input);
                                throw eee;
                        }
                        cnt10++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:26: ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ )
                    {
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:26: ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ )
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:27: ( '0' .. '9' )* '.' ( '0' .. '9' )+
                    {
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:27: ( '0' .. '9' )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:27: '0' .. '9'
                    	    {
                    	    matchRange('0','9');

                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);

                    match('.');
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:41: ( '0' .. '9' )+
                    int cnt12=0;
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:19:41: '0' .. '9'
                    	    {
                    	    matchRange('0','9');

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt12 >= 1 ) break loop12;
                                EarlyExitException eee =
                                    new EarlyExitException(12, input);
                                throw eee;
                        }
                        cnt12++;
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:20:5: ( '\"' (~ ( '\"' | '\\n' | '\\r' | '\\t' ) )* '\"' )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:20:7: '\"' (~ ( '\"' | '\\n' | '\\r' | '\\t' ) )* '\"'
            {
            match('\"');
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:20:10: (~ ( '\"' | '\\n' | '\\r' | '\\t' ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>='\u0000' && LA14_0<='\b')||(LA14_0>='\u000B' && LA14_0<='\f')||(LA14_0>='\u000E' && LA14_0<='!')||(LA14_0>='#' && LA14_0<='\uFFFF')) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:20:11: ~ ( '\"' | '\\n' | '\\r' | '\\t' )
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
            	    break loop14;
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:21:6: ( LBRK (~ ( ']' | '[' | '\\n' | '\\r' | '\\t' ) )* RBRK )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:21:8: LBRK (~ ( ']' | '[' | '\\n' | '\\r' | '\\t' ) )* RBRK
            {
            mLBRK();
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:21:12: (~ ( ']' | '[' | '\\n' | '\\r' | '\\t' ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( ((LA15_0>='\u0000' && LA15_0<='\b')||(LA15_0>='\u000B' && LA15_0<='\f')||(LA15_0>='\u000E' && LA15_0<='Z')||LA15_0=='\\'||(LA15_0>='^' && LA15_0<='\uFFFF')) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:21:13: ~ ( ']' | '[' | '\\n' | '\\r' | '\\t' )
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
            	    break loop15;
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:22:7: ( 'ASC' | 'DES' )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0=='A') ) {
                alt16=1;
            }
            else if ( (LA16_0=='D') ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:22:9: 'ASC'
                    {
                    match("ASC");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:22:15: 'DES'
                    {
                    match("DES");


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

    // $ANTLR start "VAR"
    public final void mVAR() throws RecognitionException {
        try {
            int _type = VAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:23:5: ( ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | 'á' | 'é' | 'í' | 'ó' | 'ú' | '\\u00C1' | 'É' | 'Í' | 'Ó' | 'Ú' | 'Ñ' | 'ñ' )+ )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:23:7: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | 'á' | 'é' | 'í' | 'ó' | 'ú' | '\\u00C1' | 'É' | 'Í' | 'Ó' | 'Ú' | 'Ñ' | 'ñ' )+
            {
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:23:7: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | 'á' | 'é' | 'í' | 'ó' | 'ú' | '\\u00C1' | 'É' | 'Í' | 'Ó' | 'Ú' | 'Ñ' | 'ñ' )+
            int cnt17=0;
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0=='-'||(LA17_0>='0' && LA17_0<='9')||(LA17_0>='A' && LA17_0<='Z')||LA17_0=='_'||(LA17_0>='a' && LA17_0<='z')||LA17_0=='\u00C1'||LA17_0=='\u00C9'||LA17_0=='\u00CD'||LA17_0=='\u00D1'||LA17_0=='\u00D3'||LA17_0=='\u00DA'||LA17_0=='\u00E1'||LA17_0=='\u00E9'||LA17_0=='\u00ED'||LA17_0=='\u00F1'||LA17_0=='\u00F3'||LA17_0=='\u00FA') ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:
            	    {
            	    if ( input.LA(1)=='-'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||input.LA(1)=='\u00C1'||input.LA(1)=='\u00C9'||input.LA(1)=='\u00CD'||input.LA(1)=='\u00D1'||input.LA(1)=='\u00D3'||input.LA(1)=='\u00DA'||input.LA(1)=='\u00E1'||input.LA(1)=='\u00E9'||input.LA(1)=='\u00ED'||input.LA(1)=='\u00F1'||input.LA(1)=='\u00F3'||input.LA(1)=='\u00FA' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt17 >= 1 ) break loop17;
                        EarlyExitException eee =
                            new EarlyExitException(17, input);
                        throw eee;
                }
                cnt17++;
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:24:6: ( '(' )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:24:8: '('
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:25:6: ( ')' )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:25:8: ')'
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:26:6: ( '[' )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:26:8: '['
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:27:6: ( ']' )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:27:8: ']'
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:28:5: ( ',' )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:28:7: ','
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
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:29:6: ( '\\?' )
            // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:29:8: '\\?'
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
        // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:8: ( WHITESPACE | SIGN | SIGL | SIGG | SIGE | SIGLE | SIGGE | PREC | MODT | PRED | PREN | MODE | MODO | MODC | BOL | NUM | LIT | BVAR | ORDOP | VAR | LPAR | RPAR | LBRK | RBRK | DEL | SIGI )
        int alt18=26;
        alt18 = dfa18.predict(input);
        switch (alt18) {
            case 1 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:10: WHITESPACE
                {
                mWHITESPACE();

                }
                break;
            case 2 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:21: SIGN
                {
                mSIGN();

                }
                break;
            case 3 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:26: SIGL
                {
                mSIGL();

                }
                break;
            case 4 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:31: SIGG
                {
                mSIGG();

                }
                break;
            case 5 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:36: SIGE
                {
                mSIGE();

                }
                break;
            case 6 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:41: SIGLE
                {
                mSIGLE();

                }
                break;
            case 7 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:47: SIGGE
                {
                mSIGGE();

                }
                break;
            case 8 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:53: PREC
                {
                mPREC();

                }
                break;
            case 9 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:58: MODT
                {
                mMODT();

                }
                break;
            case 10 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:63: PRED
                {
                mPRED();

                }
                break;
            case 11 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:68: PREN
                {
                mPREN();

                }
                break;
            case 12 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:73: MODE
                {
                mMODE();

                }
                break;
            case 13 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:78: MODO
                {
                mMODO();

                }
                break;
            case 14 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:83: MODC
                {
                mMODC();

                }
                break;
            case 15 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:88: BOL
                {
                mBOL();

                }
                break;
            case 16 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:92: NUM
                {
                mNUM();

                }
                break;
            case 17 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:96: LIT
                {
                mLIT();

                }
                break;
            case 18 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:100: BVAR
                {
                mBVAR();

                }
                break;
            case 19 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:105: ORDOP
                {
                mORDOP();

                }
                break;
            case 20 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:111: VAR
                {
                mVAR();

                }
                break;
            case 21 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:115: LPAR
                {
                mLPAR();

                }
                break;
            case 22 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:120: RPAR
                {
                mRPAR();

                }
                break;
            case 23 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:125: LBRK
                {
                mLBRK();

                }
                break;
            case 24 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:130: RBRK
                {
                mRBRK();

                }
                break;
            case 25 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:135: DEL
                {
                mDEL();

                }
                break;
            case 26 :
                // /home/hasdai/Documentos/INFOTEC/SimpleLexer.g:1:139: SIGI
                {
                mSIGI();

                }
                break;

        }

    }


    protected DFA6 dfa6 = new DFA6(this);
    protected DFA13 dfa13 = new DFA13(this);
    protected DFA18 dfa18 = new DFA18(this);
    static final String DFA6_eotS =
        "\13\uffff";
    static final String DFA6_eofS =
        "\13\uffff";
    static final String DFA6_minS =
        "\1\117\1\122\2\uffff\1\144\1\145\1\156\1\141\1\144\2\uffff";
    static final String DFA6_maxS =
        "\1\157\1\162\2\uffff\1\144\1\145\1\156\1\141\1\162\2\uffff";
    static final String DFA6_acceptS =
        "\2\uffff\1\3\1\1\5\uffff\1\2\1\4";
    static final String DFA6_specialS =
        "\13\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\1\37\uffff\1\2",
            "\1\3\37\uffff\1\4",
            "",
            "",
            "\1\5",
            "\1\6",
            "\1\7",
            "\1\10",
            "\1\12\15\uffff\1\11",
            "",
            ""
    };

    static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
    static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
    static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
    static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
    static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
    static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
    static final short[][] DFA6_transition;

    static {
        int numStates = DFA6_transitionS.length;
        DFA6_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
        }
    }

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = DFA6_eot;
            this.eof = DFA6_eof;
            this.min = DFA6_min;
            this.max = DFA6_max;
            this.accept = DFA6_accept;
            this.special = DFA6_special;
            this.transition = DFA6_transition;
        }
        public String getDescription() {
            return "16:1: MODO : ( 'ORDENAR' | 'Ordenar' | 'ordenar' | 'Ordenado' );";
        }
    }
    static final String DFA13_eotS =
        "\1\uffff\1\3\2\uffff";
    static final String DFA13_eofS =
        "\4\uffff";
    static final String DFA13_minS =
        "\2\56\2\uffff";
    static final String DFA13_maxS =
        "\2\71\2\uffff";
    static final String DFA13_acceptS =
        "\2\uffff\1\2\1\1";
    static final String DFA13_specialS =
        "\4\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\2\1\uffff\12\1",
            "",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "19:13: ( ( '0' .. '9' )+ | ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ) )";
        }
    }
    static final String DFA18_eotS =
        "\2\uffff\1\36\1\40\1\42\1\uffff\14\30\1\23\2\uffff\1\70\1\30\1\36"+
        "\13\uffff\11\30\3\110\3\113\6\30\2\uffff\1\30\1\123\1\30\1\123\1"+
        "\30\1\123\7\30\1\136\1\uffff\2\30\1\uffff\6\30\1\136\1\uffff\3\147"+
        "\2\150\2\151\1\150\1\151\1\30\1\uffff\10\30\3\uffff\3\163\3\30\3"+
        "\151\1\uffff\3\30\2\173\1\30\1\173\1\uffff\1\173";
    static final String DFA18_eofS =
        "\175\uffff";
    static final String DFA18_minS =
        "\1\11\1\uffff\1\55\2\75\1\uffff\1\117\1\157\1\117\1\157\1\105\1"+
        "\145\1\116\1\156\1\122\1\162\1\141\1\101\1\55\2\uffff\1\0\1\123"+
        "\1\56\13\uffff\1\115\2\155\1\104\1\144\1\125\1\165\1\144\1\165\6"+
        "\55\1\104\2\144\1\154\1\114\1\154\2\uffff\1\103\1\55\1\117\1\55"+
        "\1\157\1\55\1\157\1\117\1\157\1\105\1\145\1\157\1\145\1\55\1\uffff"+
        "\2\144\1\uffff\1\105\2\145\1\163\1\123\1\163\1\55\1\uffff\11\55"+
        "\1\105\1\uffff\2\145\1\116\2\156\1\145\1\105\1\145\3\uffff\3\55"+
        "\1\101\2\141\3\55\1\uffff\1\122\1\144\1\162\2\55\1\157\1\55\1\uffff"+
        "\1\55";
    static final String DFA18_maxS =
        "\1\u00fa\1\uffff\1\u00fa\2\75\1\uffff\2\157\2\162\2\145\2\156\2"+
        "\162\2\141\1\u00fa\2\uffff\1\uffff\1\123\1\71\13\uffff\1\116\2\156"+
        "\1\104\1\144\1\125\1\165\1\144\1\165\6\u00fa\1\104\2\144\1\154\1"+
        "\114\1\154\2\uffff\1\103\1\u00fa\1\117\1\u00fa\1\157\1\u00fa\1\157"+
        "\1\117\1\157\1\105\1\145\1\157\1\145\1\u00fa\1\uffff\2\144\1\uffff"+
        "\1\105\2\145\1\163\1\123\1\163\1\u00fa\1\uffff\11\u00fa\1\105\1"+
        "\uffff\2\145\1\116\2\156\1\145\1\105\1\145\3\uffff\3\u00fa\1\101"+
        "\2\141\3\u00fa\1\uffff\1\122\2\162\2\u00fa\1\157\1\u00fa\1\uffff"+
        "\1\u00fa";
    static final String DFA18_acceptS =
        "\1\uffff\1\1\3\uffff\1\5\15\uffff\1\20\1\21\3\uffff\1\24\1\25\1"+
        "\26\1\30\1\31\1\32\1\2\1\6\1\3\1\7\1\4\25\uffff\1\27\1\22\16\uffff"+
        "\1\12\2\uffff\1\13\7\uffff\1\10\12\uffff\1\23\10\uffff\1\16\1\11"+
        "\1\17\11\uffff\1\14\7\uffff\1\15\1\uffff";
    static final String DFA18_specialS =
        "\25\uffff\1\0\147\uffff}>";
    static final String[] DFA18_transitionS = {
            "\2\1\1\uffff\2\1\22\uffff\1\1\1\uffff\1\24\5\uffff\1\31\1\32"+
            "\1\uffff\1\27\1\34\1\2\1\23\1\uffff\12\22\2\uffff\1\3\1\5\1"+
            "\4\1\35\1\uffff\1\26\1\30\1\6\1\12\1\14\1\21\10\30\1\16\4\30"+
            "\1\10\6\30\1\25\1\uffff\1\33\1\uffff\1\30\1\uffff\2\30\1\7\1"+
            "\13\1\15\1\20\10\30\1\17\4\30\1\11\6\30\106\uffff\1\30\7\uffff"+
            "\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff\1\30\6\uffff\1\30\6"+
            "\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff\1"+
            "\30\6\uffff\1\30",
            "",
            "\1\30\1\23\1\uffff\12\22\7\uffff\32\30\4\uffff\1\30\1\uffff"+
            "\32\30\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30"+
            "\1\uffff\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff"+
            "\1\30\3\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\37",
            "\1\41",
            "",
            "\1\43\37\uffff\1\44",
            "\1\45",
            "\1\46\2\uffff\1\50\34\uffff\1\47\2\uffff\1\51",
            "\1\52\2\uffff\1\53",
            "\1\54\37\uffff\1\55",
            "\1\56",
            "\1\57\37\uffff\1\60",
            "\1\61",
            "\1\62\37\uffff\1\63",
            "\1\64",
            "\1\65",
            "\1\66\37\uffff\1\67",
            "\1\30\2\uffff\12\22\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "",
            "",
            "\11\71\2\uffff\2\71\1\uffff\115\71\1\uffff\uffa4\71",
            "\1\72",
            "\1\23\1\uffff\12\23",
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
            "\1\74\1\73",
            "\1\76\1\75",
            "\1\100\1\77",
            "\1\101",
            "\1\102",
            "\1\103",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\30\2\uffff\12\30\7\uffff\22\30\1\107\7\30\4\uffff\1\30\1"+
            "\uffff\32\30\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff"+
            "\1\30\1\uffff\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3"+
            "\uffff\1\30\3\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\22\30"+
            "\1\111\7\30\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff"+
            "\1\30\1\uffff\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3"+
            "\uffff\1\30\3\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\22\30"+
            "\1\112\7\30\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff"+
            "\1\30\1\uffff\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3"+
            "\uffff\1\30\3\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\114",
            "\1\115",
            "\1\116",
            "\1\117",
            "\1\120",
            "\1\121",
            "",
            "",
            "\1\122",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\124",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\125",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\126",
            "\1\127",
            "\1\130",
            "\1\131",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\30\2\uffff\12\30\7\uffff\3\30\1\135\26\30\4\uffff\1\30\1"+
            "\uffff\32\30\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff"+
            "\1\30\1\uffff\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3"+
            "\uffff\1\30\3\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "",
            "\1\137",
            "\1\140",
            "",
            "\1\141",
            "\1\142",
            "\1\143",
            "\1\144",
            "\1\145",
            "\1\146",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\152",
            "",
            "\1\153",
            "\1\154",
            "\1\155",
            "\1\156",
            "\1\157",
            "\1\160",
            "\1\161",
            "\1\162",
            "",
            "",
            "",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "",
            "\1\167",
            "\1\171\15\uffff\1\170",
            "\1\172",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "\1\174",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30",
            "",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30"+
            "\106\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3\uffff\1\30\1\uffff"+
            "\1\30\6\uffff\1\30\6\uffff\1\30\7\uffff\1\30\3\uffff\1\30\3"+
            "\uffff\1\30\1\uffff\1\30\6\uffff\1\30"
    };

    static final short[] DFA18_eot = DFA.unpackEncodedString(DFA18_eotS);
    static final short[] DFA18_eof = DFA.unpackEncodedString(DFA18_eofS);
    static final char[] DFA18_min = DFA.unpackEncodedStringToUnsignedChars(DFA18_minS);
    static final char[] DFA18_max = DFA.unpackEncodedStringToUnsignedChars(DFA18_maxS);
    static final short[] DFA18_accept = DFA.unpackEncodedString(DFA18_acceptS);
    static final short[] DFA18_special = DFA.unpackEncodedString(DFA18_specialS);
    static final short[][] DFA18_transition;

    static {
        int numStates = DFA18_transitionS.length;
        DFA18_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA18_transition[i] = DFA.unpackEncodedString(DFA18_transitionS[i]);
        }
    }

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = DFA18_eot;
            this.eof = DFA18_eof;
            this.min = DFA18_min;
            this.max = DFA18_max;
            this.accept = DFA18_accept;
            this.special = DFA18_special;
            this.transition = DFA18_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( WHITESPACE | SIGN | SIGL | SIGG | SIGE | SIGLE | SIGGE | PREC | MODT | PRED | PREN | MODE | MODO | MODC | BOL | NUM | LIT | BVAR | ORDOP | VAR | LPAR | RPAR | LBRK | RBRK | DEL | SIGI );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA18_21 = input.LA(1);

                        s = -1;
                        if ( ((LA18_21>='\u0000' && LA18_21<='\b')||(LA18_21>='\u000B' && LA18_21<='\f')||(LA18_21>='\u000E' && LA18_21<='Z')||(LA18_21>='\\' && LA18_21<='\uFFFF')) ) {s = 57;}

                        else s = 56;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 18, _s, input);
            error(nvae);
            throw nvae;
        }
    }
}