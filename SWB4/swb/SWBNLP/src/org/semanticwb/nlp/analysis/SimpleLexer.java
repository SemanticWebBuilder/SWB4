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
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
// $ANTLR 3.2 Sep 23, 2009 12:02:23 /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g 2010-06-07 13:45:01

import org.antlr.runtime.*;

public class SimpleLexer extends Lexer {
    public static final int SIGN=5;
    public static final int BVAR=22;
    public static final int SIGI=28;
    public static final int SIGL=6;
    public static final int SIGE=8;
    public static final int DEL=27;
    public static final int SIGG=7;
    public static final int LBRK=20;
    public static final int PREC=11;
    public static final int SIGGE=10;
    public static final int PRED=13;
    public static final int ORDOP=23;
    public static final int LIT=19;
    public static final int WHITESPACE=4;
    public static final int BOL=17;
    public static final int EOF=-1;
    public static final int NUM=18;
    public static final int SIGLE=9;
    public static final int LPAR=25;
    public static final int MODT=12;
    public static final int RPAR=26;
    public static final int VAR=24;
    public static final int MODO=15;
    public static final int MODC=16;
    public static final int RBRK=21;
    public static final int MODE=14;

    // delegates
    // delegators

    public SimpleLexer() {;}
    public SimpleLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public SimpleLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g"; }

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:4:2: ( ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:4:4: ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' )
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:5:6: ( '+' | '-' )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:6:6: ( '<' )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:6:8: '<'
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:7:6: ( '>' )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:7:8: '>'
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:8:6: ( '=' )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:8:8: '='
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:9:7: ( '<=' )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:9:9: '<='
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:10:7: ( '>=' )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:10:9: '>='
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:11:6: ( 'CON' | 'Con' | 'con' )
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
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:11:8: 'CON'
                    {
                    match("CON");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:11:16: 'Con'
                    {
                    match("Con");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:11:24: 'con'
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:12:6: ( 'TODO' | 'Todo' | 'todo' )
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
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:12:8: 'TODO'
                    {
                    match("TODO");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:12:17: 'Todo'
                    {
                    match("Todo");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:12:26: 'todo'
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:13:6: ( 'DE' | 'De' | 'de' )
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
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:13:8: 'DE'
                    {
                    match("DE");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:13:15: 'De'
                    {
                    match("De");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:13:22: 'de'
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

    // $ANTLR start "MODE"
    public final void mMODE() throws RecognitionException {
        try {
            int _type = MODE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:14:6: ( 'DESDE' | 'Desde' | 'desde' )
            int alt4=3;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='D') ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1=='E') ) {
                    alt4=1;
                }
                else if ( (LA4_1=='e') ) {
                    alt4=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA4_0=='d') ) {
                alt4=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:14:8: 'DESDE'
                    {
                    match("DESDE");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:14:18: 'Desde'
                    {
                    match("Desde");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:14:28: 'desde'
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:15:6: ( 'ORDENAR' | 'Ordenar' | 'ordenar' )
            int alt5=3;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='O') ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1=='R') ) {
                    alt5=1;
                }
                else if ( (LA5_1=='r') ) {
                    alt5=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA5_0=='o') ) {
                alt5=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:15:8: 'ORDENAR'
                    {
                    match("ORDENAR");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:15:20: 'Ordenar'
                    {
                    match("Ordenar");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:15:32: 'ordenar'
                    {
                    match("ordenar");


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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:16:9: ( 'COMO' | 'Como' | 'como' )
            int alt6=3;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='C') ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1=='O') ) {
                    alt6=1;
                }
                else if ( (LA6_1=='o') ) {
                    alt6=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA6_0=='c') ) {
                alt6=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:16:17: 'COMO'
                    {
                    match("COMO");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:16:26: 'Como'
                    {
                    match("Como");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:16:35: 'como'
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:17:5: ( 'true' | 'TRUE' | 'false' | 'FALSE' | 'True' | 'False' )
            int alt7=6;
            switch ( input.LA(1) ) {
            case 't':
                {
                alt7=1;
                }
                break;
            case 'T':
                {
                int LA7_2 = input.LA(2);

                if ( (LA7_2=='R') ) {
                    alt7=2;
                }
                else if ( (LA7_2=='r') ) {
                    alt7=5;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 2, input);

                    throw nvae;
                }
                }
                break;
            case 'f':
                {
                alt7=3;
                }
                break;
            case 'F':
                {
                int LA7_4 = input.LA(2);

                if ( (LA7_4=='A') ) {
                    alt7=4;
                }
                else if ( (LA7_4=='a') ) {
                    alt7=6;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 4, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:17:7: 'true'
                    {
                    match("true");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:17:16: 'TRUE'
                    {
                    match("TRUE");


                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:17:25: 'false'
                    {
                    match("false");


                    }
                    break;
                case 4 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:17:35: 'FALSE'
                    {
                    match("FALSE");


                    }
                    break;
                case 5 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:17:45: 'True'
                    {
                    match("True");


                    }
                    break;
                case 6 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:17:54: 'False'
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:5: ( ( SIGN )? ( ( '0' .. '9' )+ | ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ) ) )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:7: ( SIGN )? ( ( '0' .. '9' )+ | ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ) )
            {
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:7: ( SIGN )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='+'||LA8_0=='-') ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:7: SIGN
                    {
                    mSIGN();

                    }
                    break;

            }

            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:13: ( ( '0' .. '9' )+ | ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ) )
            int alt12=2;
            alt12 = dfa12.predict(input);
            switch (alt12) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:14: ( '0' .. '9' )+
                    {
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:14: ( '0' .. '9' )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:14: '0' .. '9'
                    	    {
                    	    matchRange('0','9');

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:26: ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ )
                    {
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:26: ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ )
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:27: ( '0' .. '9' )* '.' ( '0' .. '9' )+
                    {
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:27: ( '0' .. '9' )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:27: '0' .. '9'
                    	    {
                    	    matchRange('0','9');

                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);

                    match('.');
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:41: ( '0' .. '9' )+
                    int cnt11=0;
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:18:41: '0' .. '9'
                    	    {
                    	    matchRange('0','9');

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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:19:5: ( '\"' (~ ( '\"' | '\\n' | '\\r' | '\\t' ) )* '\"' )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:19:7: '\"' (~ ( '\"' | '\\n' | '\\r' | '\\t' ) )* '\"'
            {
            match('\"');
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:19:10: (~ ( '\"' | '\\n' | '\\r' | '\\t' ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='\u0000' && LA13_0<='\b')||(LA13_0>='\u000B' && LA13_0<='\f')||(LA13_0>='\u000E' && LA13_0<='!')||(LA13_0>='#' && LA13_0<='\uFFFF')) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:19:11: ~ ( '\"' | '\\n' | '\\r' | '\\t' )
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
            	    break loop13;
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:20:6: ( LBRK (~ ( ']' | '[' | '\\n' | '\\r' | '\\t' ) )* RBRK )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:20:8: LBRK (~ ( ']' | '[' | '\\n' | '\\r' | '\\t' ) )* RBRK
            {
            mLBRK();
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:20:12: (~ ( ']' | '[' | '\\n' | '\\r' | '\\t' ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>='\u0000' && LA14_0<='\b')||(LA14_0>='\u000B' && LA14_0<='\f')||(LA14_0>='\u000E' && LA14_0<='Z')||LA14_0=='\\'||(LA14_0>='^' && LA14_0<='\uFFFF')) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:20:13: ~ ( ']' | '[' | '\\n' | '\\r' | '\\t' )
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
            	    break loop14;
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:21:7: ( 'ASC' | 'DES' )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='A') ) {
                alt15=1;
            }
            else if ( (LA15_0=='D') ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:21:9: 'ASC'
                    {
                    match("ASC");


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:21:15: 'DES'
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:22:5: ( ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | 'á' | 'é' | 'í' | 'ó' | 'ú' | '\\u00C1' | 'É' | 'Í' | 'Ó' | 'Ú' | 'Ñ' | 'ñ' )+ )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:22:7: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | 'á' | 'é' | 'í' | 'ó' | 'ú' | '\\u00C1' | 'É' | 'Í' | 'Ó' | 'Ú' | 'Ñ' | 'ñ' )+
            {
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:22:7: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | 'á' | 'é' | 'í' | 'ó' | 'ú' | '\\u00C1' | 'É' | 'Í' | 'Ó' | 'Ú' | 'Ñ' | 'ñ' )+
            int cnt16=0;
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0=='-'||(LA16_0>='0' && LA16_0<='9')||(LA16_0>='A' && LA16_0<='Z')||LA16_0=='_'||(LA16_0>='a' && LA16_0<='z')||LA16_0=='\u00C1'||LA16_0=='\u00C9'||LA16_0=='\u00CD'||LA16_0=='\u00D1'||LA16_0=='\u00D3'||LA16_0=='\u00DA'||LA16_0=='\u00E1'||LA16_0=='\u00E9'||LA16_0=='\u00ED'||LA16_0=='\u00F1'||LA16_0=='\u00F3'||LA16_0=='\u00FA') ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:
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
            	    if ( cnt16 >= 1 ) break loop16;
                        EarlyExitException eee =
                            new EarlyExitException(16, input);
                        throw eee;
                }
                cnt16++;
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:23:6: ( '(' )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:23:8: '('
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:24:6: ( ')' )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:24:8: ')'
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:25:6: ( '[' )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:25:8: '['
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:26:6: ( ']' )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:26:8: ']'
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:27:5: ( ',' )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:27:7: ','
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
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:28:6: ( '\\?' )
            // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:28:8: '\\?'
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
        // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:8: ( WHITESPACE | SIGN | SIGL | SIGG | SIGE | SIGLE | SIGGE | PREC | MODT | PRED | MODE | MODO | MODC | BOL | NUM | LIT | BVAR | ORDOP | VAR | LPAR | RPAR | LBRK | RBRK | DEL | SIGI )
        int alt17=25;
        alt17 = dfa17.predict(input);
        switch (alt17) {
            case 1 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:10: WHITESPACE
                {
                mWHITESPACE();

                }
                break;
            case 2 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:21: SIGN
                {
                mSIGN();

                }
                break;
            case 3 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:26: SIGL
                {
                mSIGL();

                }
                break;
            case 4 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:31: SIGG
                {
                mSIGG();

                }
                break;
            case 5 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:36: SIGE
                {
                mSIGE();

                }
                break;
            case 6 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:41: SIGLE
                {
                mSIGLE();

                }
                break;
            case 7 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:47: SIGGE
                {
                mSIGGE();

                }
                break;
            case 8 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:53: PREC
                {
                mPREC();

                }
                break;
            case 9 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:58: MODT
                {
                mMODT();

                }
                break;
            case 10 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:63: PRED
                {
                mPRED();

                }
                break;
            case 11 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:68: MODE
                {
                mMODE();

                }
                break;
            case 12 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:73: MODO
                {
                mMODO();

                }
                break;
            case 13 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:78: MODC
                {
                mMODC();

                }
                break;
            case 14 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:83: BOL
                {
                mBOL();

                }
                break;
            case 15 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:87: NUM
                {
                mNUM();

                }
                break;
            case 16 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:91: LIT
                {
                mLIT();

                }
                break;
            case 17 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:95: BVAR
                {
                mBVAR();

                }
                break;
            case 18 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:100: ORDOP
                {
                mORDOP();

                }
                break;
            case 19 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:106: VAR
                {
                mVAR();

                }
                break;
            case 20 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:110: LPAR
                {
                mLPAR();

                }
                break;
            case 21 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:115: RPAR
                {
                mRPAR();

                }
                break;
            case 22 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:120: LBRK
                {
                mLBRK();

                }
                break;
            case 23 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:125: RBRK
                {
                mRBRK();

                }
                break;
            case 24 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:130: DEL
                {
                mDEL();

                }
                break;
            case 25 :
                // /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/SimpleLexer.g:1:134: SIGI
                {
                mSIGI();

                }
                break;

        }

    }


    protected DFA12 dfa12 = new DFA12(this);
    protected DFA17 dfa17 = new DFA17(this);
    static final String DFA12_eotS =
        "\1\uffff\1\3\2\uffff";
    static final String DFA12_eofS =
        "\4\uffff";
    static final String DFA12_minS =
        "\2\56\2\uffff";
    static final String DFA12_maxS =
        "\2\71\2\uffff";
    static final String DFA12_acceptS =
        "\2\uffff\1\2\1\1";
    static final String DFA12_specialS =
        "\4\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\2\1\uffff\12\1",
            "",
            ""
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
            return "18:13: ( ( '0' .. '9' )+ | ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ) )";
        }
    }
    static final String DFA17_eotS =
        "\2\uffff\1\34\1\36\1\40\1\uffff\12\26\1\21\2\uffff\1\63\1\26\1\34"+
        "\13\uffff\11\26\3\103\6\26\2\uffff\1\26\1\115\1\26\1\115\1\26\1"+
        "\115\7\26\1\130\1\uffff\10\26\1\130\1\uffff\3\141\2\142\2\143\1"+
        "\142\1\143\1\26\1\uffff\10\26\3\uffff\3\155\3\26\3\143\1\uffff\3"+
        "\26\3\164\1\uffff";
    static final String DFA17_eofS =
        "\165\uffff";
    static final String DFA17_minS =
        "\1\11\1\uffff\1\55\2\75\1\uffff\1\117\1\157\1\117\1\157\1\105\1"+
        "\145\1\122\1\162\1\141\1\101\1\55\2\uffff\1\0\1\123\1\56\13\uffff"+
        "\1\115\2\155\1\104\1\144\1\125\1\165\1\144\1\165\3\55\1\104\2\144"+
        "\1\154\1\114\1\154\2\uffff\1\103\1\55\1\117\1\55\1\157\1\55\1\157"+
        "\1\117\1\157\1\105\1\145\1\157\1\145\1\55\1\uffff\2\144\1\105\2"+
        "\145\1\163\1\123\1\163\1\55\1\uffff\11\55\1\105\1\uffff\2\145\1"+
        "\116\2\156\1\145\1\105\1\145\3\uffff\3\55\1\101\2\141\3\55\1\uffff"+
        "\1\122\2\162\3\55\1\uffff";
    static final String DFA17_maxS =
        "\1\u00fa\1\uffff\1\u00fa\2\75\1\uffff\2\157\2\162\2\145\2\162\2"+
        "\141\1\u00fa\2\uffff\1\uffff\1\123\1\71\13\uffff\1\116\2\156\1\104"+
        "\1\144\1\125\1\165\1\144\1\165\3\u00fa\1\104\2\144\1\154\1\114\1"+
        "\154\2\uffff\1\103\1\u00fa\1\117\1\u00fa\1\157\1\u00fa\1\157\1\117"+
        "\1\157\1\105\1\145\1\157\1\145\1\u00fa\1\uffff\2\144\1\105\2\145"+
        "\1\163\1\123\1\163\1\u00fa\1\uffff\11\u00fa\1\105\1\uffff\2\145"+
        "\1\116\2\156\1\145\1\105\1\145\3\uffff\3\u00fa\1\101\2\141\3\u00fa"+
        "\1\uffff\1\122\2\162\3\u00fa\1\uffff";
    static final String DFA17_acceptS =
        "\1\uffff\1\1\3\uffff\1\5\13\uffff\1\17\1\20\3\uffff\1\23\1\24\1"+
        "\25\1\27\1\30\1\31\1\2\1\6\1\3\1\7\1\4\22\uffff\1\26\1\21\16\uffff"+
        "\1\12\11\uffff\1\10\12\uffff\1\22\10\uffff\1\15\1\11\1\16\11\uffff"+
        "\1\13\6\uffff\1\14";
    static final String DFA17_specialS =
        "\23\uffff\1\0\141\uffff}>";
    static final String[] DFA17_transitionS = {
            "\2\1\1\uffff\2\1\22\uffff\1\1\1\uffff\1\22\5\uffff\1\27\1\30"+
            "\1\uffff\1\25\1\32\1\2\1\21\1\uffff\12\20\2\uffff\1\3\1\5\1"+
            "\4\1\33\1\uffff\1\24\1\26\1\6\1\12\1\26\1\17\10\26\1\14\4\26"+
            "\1\10\6\26\1\23\1\uffff\1\31\1\uffff\1\26\1\uffff\2\26\1\7\1"+
            "\13\1\26\1\16\10\26\1\15\4\26\1\11\6\26\106\uffff\1\26\7\uffff"+
            "\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff\1\26\6\uffff\1\26\6"+
            "\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff\1"+
            "\26\6\uffff\1\26",
            "",
            "\1\26\1\21\1\uffff\12\20\7\uffff\32\26\4\uffff\1\26\1\uffff"+
            "\32\26\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26"+
            "\1\uffff\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff"+
            "\1\26\3\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\35",
            "\1\37",
            "",
            "\1\41\37\uffff\1\42",
            "\1\43",
            "\1\44\2\uffff\1\46\34\uffff\1\45\2\uffff\1\47",
            "\1\50\2\uffff\1\51",
            "\1\52\37\uffff\1\53",
            "\1\54",
            "\1\55\37\uffff\1\56",
            "\1\57",
            "\1\60",
            "\1\61\37\uffff\1\62",
            "\1\26\2\uffff\12\20\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "",
            "",
            "\11\64\2\uffff\2\64\1\uffff\115\64\1\uffff\uffa4\64",
            "\1\65",
            "\1\21\1\uffff\12\21",
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
            "\1\67\1\66",
            "\1\71\1\70",
            "\1\73\1\72",
            "\1\74",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "\1\101",
            "\1\26\2\uffff\12\26\7\uffff\22\26\1\102\7\26\4\uffff\1\26\1"+
            "\uffff\32\26\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\1\26\1\uffff\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3"+
            "\uffff\1\26\3\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\22\26"+
            "\1\104\7\26\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\1\26\1\uffff\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3"+
            "\uffff\1\26\3\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\22\26"+
            "\1\105\7\26\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\1\26\1\uffff\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3"+
            "\uffff\1\26\3\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "",
            "",
            "\1\114",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\116",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\117",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\120",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\26\2\uffff\12\26\7\uffff\3\26\1\127\26\26\4\uffff\1\26\1"+
            "\uffff\32\26\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\1\26\1\uffff\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3"+
            "\uffff\1\26\3\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "",
            "\1\131",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\140",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\144",
            "",
            "\1\145",
            "\1\146",
            "\1\147",
            "\1\150",
            "\1\151",
            "\1\152",
            "\1\153",
            "\1\154",
            "",
            "",
            "",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\156",
            "\1\157",
            "\1\160",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "",
            "\1\161",
            "\1\162",
            "\1\163",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            "\1\26\2\uffff\12\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32\26"+
            "\106\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3\uffff\1\26\1\uffff"+
            "\1\26\6\uffff\1\26\6\uffff\1\26\7\uffff\1\26\3\uffff\1\26\3"+
            "\uffff\1\26\1\uffff\1\26\6\uffff\1\26",
            ""
    };

    static final short[] DFA17_eot = DFA.unpackEncodedString(DFA17_eotS);
    static final short[] DFA17_eof = DFA.unpackEncodedString(DFA17_eofS);
    static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars(DFA17_minS);
    static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars(DFA17_maxS);
    static final short[] DFA17_accept = DFA.unpackEncodedString(DFA17_acceptS);
    static final short[] DFA17_special = DFA.unpackEncodedString(DFA17_specialS);
    static final short[][] DFA17_transition;

    static {
        int numStates = DFA17_transitionS.length;
        DFA17_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
        }
    }

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = DFA17_eot;
            this.eof = DFA17_eof;
            this.min = DFA17_min;
            this.max = DFA17_max;
            this.accept = DFA17_accept;
            this.special = DFA17_special;
            this.transition = DFA17_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( WHITESPACE | SIGN | SIGL | SIGG | SIGE | SIGLE | SIGGE | PREC | MODT | PRED | MODE | MODO | MODC | BOL | NUM | LIT | BVAR | ORDOP | VAR | LPAR | RPAR | LBRK | RBRK | DEL | SIGI );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA17_19 = input.LA(1);

                        s = -1;
                        if ( ((LA17_19>='\u0000' && LA17_19<='\b')||(LA17_19>='\u000B' && LA17_19<='\f')||(LA17_19>='\u000E' && LA17_19<='Z')||(LA17_19>='\\' && LA17_19<='\uFFFF')) ) {s = 52;}

                        else s = 51;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 17, _s, input);
            error(nvae);
            throw nvae;
        }
    }


}