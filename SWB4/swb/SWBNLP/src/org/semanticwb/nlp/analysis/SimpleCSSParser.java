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
// $ANTLR 3.1.2 /home/hasdai/Documentos/SimpleCSSParser.g 2010-01-19 13:10:48

import org.antlr.runtime.*;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.*;

public class SimpleCSSParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "HEXCHAR", "NONASCII", "UNICODE", "ESCAPE", "NMSTART", "NMCHAR", "NAME", "URL", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "COMMENT", "CDO", "CDC", "INCLUDES", "DASHMATCH", "GREATER", "LBRACE", "RBRACE", "LBRACKET", "RBRACKET", "OPEQ", "SEMI", "COLON", "SOLIDUS", "MINUS", "PLUS", "STAR", "LPAREN", "RPAREN", "COMMA", "DOT", "INVALID", "STRING", "IDENT", "HASH", "IMPORT_SYM", "PAGE_SYM", "MEDIA_SYM", "CHARSET_SYM", "WS", "IMPORTANT_SYM", "EMS", "EXS", "LENGTH", "ANGLE", "TIME", "FREQ", "DIMENSION", "PERCENTAGE", "NUMBER", "URI", "NL", "STYLESHEET", "CLASS", "SELECTOR", "STYLE", "HTMLELEMENT", "ELEMENTS", "VAL", "EXPR"
    };
    public static final int CLASS=81;
    public static final int STAR=54;
    public static final int LBRACE=44;
    public static final int EOF=-1;
    public static final int MEDIA_SYM=65;
    public static final int STYLESHEET=80;
    public static final int LPAREN=55;
    public static final int LENGTH=71;
    public static final int IMPORTANT_SYM=68;
    public static final int HTMLELEMENT=84;
    public static final int INCLUDES=41;
    public static final int LBRACKET=46;
    public static final int TIME=73;
    public static final int RPAREN=56;
    public static final int NAME=10;
    public static final int EXPR=87;
    public static final int SELECTOR=82;
    public static final int GREATER=43;
    public static final int ESCAPE=7;
    public static final int COMMA=57;
    public static final int VAL=86;
    public static final int IDENT=61;
    public static final int DIMENSION=75;
    public static final int PLUS=53;
    public static final int FREQ=74;
    public static final int NL=79;
    public static final int RBRACKET=47;
    public static final int DOT=58;
    public static final int COMMENT=38;
    public static final int CHARSET_SYM=66;
    public static final int D=15;
    public static final int E=16;
    public static final int F=17;
    public static final int G=18;
    public static final int A=12;
    public static final int ANGLE=72;
    public static final int RBRACE=45;
    public static final int B=13;
    public static final int C=14;
    public static final int L=23;
    public static final int IMPORT_SYM=63;
    public static final int NMCHAR=9;
    public static final int M=24;
    public static final int N=25;
    public static final int O=26;
    public static final int H=19;
    public static final int I=20;
    public static final int J=21;
    public static final int K=22;
    public static final int NUMBER=77;
    public static final int U=32;
    public static final int HEXCHAR=4;
    public static final int HASH=62;
    public static final int T=31;
    public static final int W=34;
    public static final int V=33;
    public static final int Q=28;
    public static final int P=27;
    public static final int S=30;
    public static final int CDO=39;
    public static final int R=29;
    public static final int MINUS=52;
    public static final int ELEMENTS=85;
    public static final int SOLIDUS=51;
    public static final int SEMI=49;
    public static final int CDC=40;
    public static final int INVALID=59;
    public static final int Y=36;
    public static final int UNICODE=6;
    public static final int URL=11;
    public static final int PERCENTAGE=76;
    public static final int X=35;
    public static final int Z=37;
    public static final int URI=78;
    public static final int COLON=50;
    public static final int PAGE_SYM=64;
    public static final int NMSTART=8;
    public static final int WS=67;
    public static final int DASHMATCH=42;
    public static final int OPEQ=48;
    public static final int EMS=69;
    public static final int EXS=70;
    public static final int STYLE=83;
    public static final int NONASCII=5;
    public static final int STRING=60;

    // delegates
    // delegators


        public SimpleCSSParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public SimpleCSSParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);

        }

    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return SimpleCSSParser.tokenNames; }
    public String getGrammarFileName() { return "/home/hasdai/Documentos/SimpleCSSParser.g"; }


    	private int errCount = 0;

    	/*
    	 * Overrides default displayRecognitionError method in ANTLR to count total
    	 * recognition errors.
    	 */
    	public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
            	errCount++;
        	}

        	/*
        	 *  Gets the number of total recognition errors.
        	 */
        	public int getErrorCount() {
        		return errCount;
        	}


    public static class styleSheet_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "styleSheet"
    // /home/hasdai/Documentos/SimpleCSSParser.g:38:1: styleSheet : bodyList EOF -> ^( STYLESHEET bodyList ) ;
    public final SimpleCSSParser.styleSheet_return styleSheet() throws RecognitionException {
        SimpleCSSParser.styleSheet_return retval = new SimpleCSSParser.styleSheet_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF2=null;
        SimpleCSSParser.bodyList_return bodyList1 = null;


        Object EOF2_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_bodyList=new RewriteRuleSubtreeStream(adaptor,"rule bodyList");
        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:39:1: ( bodyList EOF -> ^( STYLESHEET bodyList ) )
            // /home/hasdai/Documentos/SimpleCSSParser.g:39:3: bodyList EOF
            {
            pushFollow(FOLLOW_bodyList_in_styleSheet88);
            bodyList1=bodyList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_bodyList.add(bodyList1.getTree());
            EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_styleSheet90); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_EOF.add(EOF2);



            // AST REWRITE
            // elements: bodyList
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 39:16: -> ^( STYLESHEET bodyList )
            {
                // /home/hasdai/Documentos/SimpleCSSParser.g:39:19: ^( STYLESHEET bodyList )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STYLESHEET, "STYLESHEET"), root_1);

                adaptor.addChild(root_1, stream_bodyList.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "styleSheet"

    public static class bodyList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bodyList"
    // /home/hasdai/Documentos/SimpleCSSParser.g:43:1: bodyList : ( bodyset )* ;
    public final SimpleCSSParser.bodyList_return bodyList() throws RecognitionException {
        SimpleCSSParser.bodyList_return retval = new SimpleCSSParser.bodyList_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SimpleCSSParser.bodyset_return bodyset3 = null;



        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:44:1: ( ( bodyset )* )
            // /home/hasdai/Documentos/SimpleCSSParser.g:44:3: ( bodyset )*
            {
            root_0 = (Object)adaptor.nil();

            // /home/hasdai/Documentos/SimpleCSSParser.g:44:3: ( bodyset )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==DOT) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/hasdai/Documentos/SimpleCSSParser.g:0:0: bodyset
            	    {
            	    pushFollow(FOLLOW_bodyset_in_bodyList108);
            	    bodyset3=bodyset();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, bodyset3.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "bodyList"

    public static class bodyset_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bodyset"
    // /home/hasdai/Documentos/SimpleCSSParser.g:48:1: bodyset : ruleSet ;
    public final SimpleCSSParser.bodyset_return bodyset() throws RecognitionException {
        SimpleCSSParser.bodyset_return retval = new SimpleCSSParser.bodyset_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SimpleCSSParser.ruleSet_return ruleSet4 = null;



        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:49:1: ( ruleSet )
            // /home/hasdai/Documentos/SimpleCSSParser.g:49:4: ruleSet
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_ruleSet_in_bodyset120);
            ruleSet4=ruleSet();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, ruleSet4.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "bodyset"

    public static class ruleSet_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ruleSet"
    // /home/hasdai/Documentos/SimpleCSSParser.g:55:1: ruleSet : ( selector ( COMMA selector )* LBRACE declaration SEMI ( declaration SEMI )* RBRACE -> ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ( declaration )+ ) ) | selector ( COMMA selector )* LBRACE RBRACE -> ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ) ) );
    public final SimpleCSSParser.ruleSet_return ruleSet() throws RecognitionException {
        SimpleCSSParser.ruleSet_return retval = new SimpleCSSParser.ruleSet_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA6=null;
        Token LBRACE8=null;
        Token SEMI10=null;
        Token SEMI12=null;
        Token RBRACE13=null;
        Token COMMA15=null;
        Token LBRACE17=null;
        Token RBRACE18=null;
        SimpleCSSParser.selector_return selector5 = null;

        SimpleCSSParser.selector_return selector7 = null;

        SimpleCSSParser.declaration_return declaration9 = null;

        SimpleCSSParser.declaration_return declaration11 = null;

        SimpleCSSParser.selector_return selector14 = null;

        SimpleCSSParser.selector_return selector16 = null;


        Object COMMA6_tree=null;
        Object LBRACE8_tree=null;
        Object SEMI10_tree=null;
        Object SEMI12_tree=null;
        Object RBRACE13_tree=null;
        Object COMMA15_tree=null;
        Object LBRACE17_tree=null;
        Object RBRACE18_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_SEMI=new RewriteRuleTokenStream(adaptor,"token SEMI");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_selector=new RewriteRuleSubtreeStream(adaptor,"rule selector");
        RewriteRuleSubtreeStream stream_declaration=new RewriteRuleSubtreeStream(adaptor,"rule declaration");
        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:56:1: ( selector ( COMMA selector )* LBRACE declaration SEMI ( declaration SEMI )* RBRACE -> ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ( declaration )+ ) ) | selector ( COMMA selector )* LBRACE RBRACE -> ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ) ) )
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:56:3: selector ( COMMA selector )* LBRACE declaration SEMI ( declaration SEMI )* RBRACE
                    {
                    pushFollow(FOLLOW_selector_in_ruleSet133);
                    selector5=selector();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_selector.add(selector5.getTree());
                    // /home/hasdai/Documentos/SimpleCSSParser.g:56:12: ( COMMA selector )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==COMMA) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/SimpleCSSParser.g:56:13: COMMA selector
                    	    {
                    	    COMMA6=(Token)match(input,COMMA,FOLLOW_COMMA_in_ruleSet136); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_COMMA.add(COMMA6);

                    	    pushFollow(FOLLOW_selector_in_ruleSet138);
                    	    selector7=selector();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_selector.add(selector7.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);

                    LBRACE8=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_ruleSet143); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE8);

                    pushFollow(FOLLOW_declaration_in_ruleSet147);
                    declaration9=declaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_declaration.add(declaration9.getTree());
                    SEMI10=(Token)match(input,SEMI,FOLLOW_SEMI_in_ruleSet149); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SEMI.add(SEMI10);

                    // /home/hasdai/Documentos/SimpleCSSParser.g:58:20: ( declaration SEMI )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==IDENT) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/SimpleCSSParser.g:58:21: declaration SEMI
                    	    {
                    	    pushFollow(FOLLOW_declaration_in_ruleSet152);
                    	    declaration11=declaration();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_declaration.add(declaration11.getTree());
                    	    SEMI12=(Token)match(input,SEMI,FOLLOW_SEMI_in_ruleSet154); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_SEMI.add(SEMI12);


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    RBRACE13=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_ruleSet159); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE13);



                    // AST REWRITE
                    // elements: declaration, selector
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 59:9: -> ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ( declaration )+ ) )
                    {
                        // /home/hasdai/Documentos/SimpleCSSParser.g:59:12: ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ( declaration )+ ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECTOR, "SELECTOR"), root_1);

                        // /home/hasdai/Documentos/SimpleCSSParser.g:59:23: ^( ELEMENTS selector )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(ELEMENTS, "ELEMENTS"), root_2);

                        adaptor.addChild(root_2, stream_selector.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // /home/hasdai/Documentos/SimpleCSSParser.g:59:44: ^( STYLE ( declaration )+ )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STYLE, "STYLE"), root_2);

                        if ( !(stream_declaration.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_declaration.hasNext() ) {
                            adaptor.addChild(root_2, stream_declaration.nextTree());

                        }
                        stream_declaration.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:60:3: selector ( COMMA selector )* LBRACE RBRACE
                    {
                    pushFollow(FOLLOW_selector_in_ruleSet182);
                    selector14=selector();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_selector.add(selector14.getTree());
                    // /home/hasdai/Documentos/SimpleCSSParser.g:60:12: ( COMMA selector )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==COMMA) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/SimpleCSSParser.g:60:13: COMMA selector
                    	    {
                    	    COMMA15=(Token)match(input,COMMA,FOLLOW_COMMA_in_ruleSet185); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_COMMA.add(COMMA15);

                    	    pushFollow(FOLLOW_selector_in_ruleSet187);
                    	    selector16=selector();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_selector.add(selector16.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    LBRACE17=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_ruleSet192); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE17);

                    RBRACE18=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_ruleSet196); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE18);



                    // AST REWRITE
                    // elements: selector
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 62:9: -> ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ) )
                    {
                        // /home/hasdai/Documentos/SimpleCSSParser.g:62:12: ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECTOR, "SELECTOR"), root_1);

                        // /home/hasdai/Documentos/SimpleCSSParser.g:62:23: ^( ELEMENTS selector )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(ELEMENTS, "ELEMENTS"), root_2);

                        adaptor.addChild(root_2, stream_selector.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // /home/hasdai/Documentos/SimpleCSSParser.g:62:44: ^( STYLE )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STYLE, "STYLE"), root_2);

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "ruleSet"

    public static class selector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "selector"
    // /home/hasdai/Documentos/SimpleCSSParser.g:66:1: selector : simpleSelector ( combinator simpleSelector )* ;
    public final SimpleCSSParser.selector_return selector() throws RecognitionException {
        SimpleCSSParser.selector_return retval = new SimpleCSSParser.selector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SimpleCSSParser.simpleSelector_return simpleSelector19 = null;

        SimpleCSSParser.combinator_return combinator20 = null;

        SimpleCSSParser.simpleSelector_return simpleSelector21 = null;



        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:67:1: ( simpleSelector ( combinator simpleSelector )* )
            // /home/hasdai/Documentos/SimpleCSSParser.g:67:3: simpleSelector ( combinator simpleSelector )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_simpleSelector_in_selector223);
            simpleSelector19=simpleSelector();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, simpleSelector19.getTree());
            // /home/hasdai/Documentos/SimpleCSSParser.g:67:18: ( combinator simpleSelector )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==GREATER||LA6_0==PLUS||LA6_0==DOT) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/hasdai/Documentos/SimpleCSSParser.g:67:19: combinator simpleSelector
            	    {
            	    pushFollow(FOLLOW_combinator_in_selector226);
            	    combinator20=combinator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, combinator20.getTree());
            	    pushFollow(FOLLOW_simpleSelector_in_selector228);
            	    simpleSelector21=simpleSelector();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, simpleSelector21.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "selector"

    public static class combinator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "combinator"
    // /home/hasdai/Documentos/SimpleCSSParser.g:70:1: combinator : ( PLUS | GREATER | );
    public final SimpleCSSParser.combinator_return combinator() throws RecognitionException {
        SimpleCSSParser.combinator_return retval = new SimpleCSSParser.combinator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PLUS22=null;
        Token GREATER23=null;

        Object PLUS22_tree=null;
        Object GREATER23_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:71:1: ( PLUS | GREATER | )
            int alt7=3;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt7=1;
                }
                break;
            case GREATER:
                {
                alt7=2;
                }
                break;
            case DOT:
                {
                alt7=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:71:3: PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    PLUS22=(Token)match(input,PLUS,FOLLOW_PLUS_in_combinator240); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS22_tree = (Object)adaptor.create(PLUS22);
                    adaptor.addChild(root_0, PLUS22_tree);
                    }

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:72:3: GREATER
                    {
                    root_0 = (Object)adaptor.nil();

                    GREATER23=(Token)match(input,GREATER,FOLLOW_GREATER_in_combinator244); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    GREATER23_tree = (Object)adaptor.create(GREATER23);
                    adaptor.addChild(root_0, GREATER23_tree);
                    }

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:74:2:
                    {
                    root_0 = (Object)adaptor.nil();

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "combinator"

    public static class simpleSelector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "simpleSelector"
    // /home/hasdai/Documentos/SimpleCSSParser.g:79:1: simpleSelector : cssClass -> ^( CLASS cssClass ) ;
    public final SimpleCSSParser.simpleSelector_return simpleSelector() throws RecognitionException {
        SimpleCSSParser.simpleSelector_return retval = new SimpleCSSParser.simpleSelector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SimpleCSSParser.cssClass_return cssClass24 = null;


        RewriteRuleSubtreeStream stream_cssClass=new RewriteRuleSubtreeStream(adaptor,"rule cssClass");
        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:80:1: ( cssClass -> ^( CLASS cssClass ) )
            // /home/hasdai/Documentos/SimpleCSSParser.g:81:2: cssClass
            {
            pushFollow(FOLLOW_cssClass_in_simpleSelector262);
            cssClass24=cssClass();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_cssClass.add(cssClass24.getTree());


            // AST REWRITE
            // elements: cssClass
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 81:11: -> ^( CLASS cssClass )
            {
                // /home/hasdai/Documentos/SimpleCSSParser.g:81:14: ^( CLASS cssClass )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CLASS, "CLASS"), root_1);

                adaptor.addChild(root_1, stream_cssClass.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "simpleSelector"

    public static class elementName_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "elementName"
    // /home/hasdai/Documentos/SimpleCSSParser.g:85:1: elementName : IDENT ;
    public final SimpleCSSParser.elementName_return elementName() throws RecognitionException {
        SimpleCSSParser.elementName_return retval = new SimpleCSSParser.elementName_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENT25=null;

        Object IDENT25_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:86:1: ( IDENT )
            // /home/hasdai/Documentos/SimpleCSSParser.g:86:3: IDENT
            {
            root_0 = (Object)adaptor.nil();

            IDENT25=(Token)match(input,IDENT,FOLLOW_IDENT_in_elementName280); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IDENT25_tree = (Object)adaptor.create(IDENT25);
            adaptor.addChild(root_0, IDENT25_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "elementName"

    public static class cssClass_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "cssClass"
    // /home/hasdai/Documentos/SimpleCSSParser.g:91:1: cssClass : DOT IDENT ;
    public final SimpleCSSParser.cssClass_return cssClass() throws RecognitionException {
        SimpleCSSParser.cssClass_return retval = new SimpleCSSParser.cssClass_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOT26=null;
        Token IDENT27=null;

        Object DOT26_tree=null;
        Object IDENT27_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:92:1: ( DOT IDENT )
            // /home/hasdai/Documentos/SimpleCSSParser.g:92:3: DOT IDENT
            {
            root_0 = (Object)adaptor.nil();

            DOT26=(Token)match(input,DOT,FOLLOW_DOT_in_cssClass292); if (state.failed) return retval;
            IDENT27=(Token)match(input,IDENT,FOLLOW_IDENT_in_cssClass295); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IDENT27_tree = (Object)adaptor.create(IDENT27);
            adaptor.addChild(root_0, IDENT27_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "cssClass"

    public static class declaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declaration"
    // /home/hasdai/Documentos/SimpleCSSParser.g:96:1: declaration : property COLON expr -> ^( EXPR ^( NAME property ) ^( VAL expr ) ) ;
    public final SimpleCSSParser.declaration_return declaration() throws RecognitionException {
        SimpleCSSParser.declaration_return retval = new SimpleCSSParser.declaration_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COLON29=null;
        SimpleCSSParser.property_return property28 = null;

        SimpleCSSParser.expr_return expr30 = null;


        Object COLON29_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleSubtreeStream stream_property=new RewriteRuleSubtreeStream(adaptor,"rule property");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:97:1: ( property COLON expr -> ^( EXPR ^( NAME property ) ^( VAL expr ) ) )
            // /home/hasdai/Documentos/SimpleCSSParser.g:97:3: property COLON expr
            {
            pushFollow(FOLLOW_property_in_declaration306);
            property28=property();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_property.add(property28.getTree());
            COLON29=(Token)match(input,COLON,FOLLOW_COLON_in_declaration308); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_COLON.add(COLON29);

            pushFollow(FOLLOW_expr_in_declaration310);
            expr30=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr30.getTree());


            // AST REWRITE
            // elements: property, expr
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 97:23: -> ^( EXPR ^( NAME property ) ^( VAL expr ) )
            {
                // /home/hasdai/Documentos/SimpleCSSParser.g:97:26: ^( EXPR ^( NAME property ) ^( VAL expr ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR, "EXPR"), root_1);

                // /home/hasdai/Documentos/SimpleCSSParser.g:97:33: ^( NAME property )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_property.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // /home/hasdai/Documentos/SimpleCSSParser.g:97:50: ^( VAL expr )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VAL, "VAL"), root_2);

                adaptor.addChild(root_2, stream_expr.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "declaration"

    public static class property_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "property"
    // /home/hasdai/Documentos/SimpleCSSParser.g:101:1: property : IDENT ;
    public final SimpleCSSParser.property_return property() throws RecognitionException {
        SimpleCSSParser.property_return retval = new SimpleCSSParser.property_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENT31=null;

        Object IDENT31_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:102:1: ( IDENT )
            // /home/hasdai/Documentos/SimpleCSSParser.g:102:3: IDENT
            {
            root_0 = (Object)adaptor.nil();

            IDENT31=(Token)match(input,IDENT,FOLLOW_IDENT_in_property338); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IDENT31_tree = (Object)adaptor.create(IDENT31);
            adaptor.addChild(root_0, IDENT31_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "property"

    public static class expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // /home/hasdai/Documentos/SimpleCSSParser.g:106:1: expr : term ( operator term )* ;
    public final SimpleCSSParser.expr_return expr() throws RecognitionException {
        SimpleCSSParser.expr_return retval = new SimpleCSSParser.expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SimpleCSSParser.term_return term32 = null;

        SimpleCSSParser.operator_return operator33 = null;

        SimpleCSSParser.term_return term34 = null;



        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:107:1: ( term ( operator term )* )
            // /home/hasdai/Documentos/SimpleCSSParser.g:107:3: term ( operator term )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_term_in_expr349);
            term32=term();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, term32.getTree());
            // /home/hasdai/Documentos/SimpleCSSParser.g:107:8: ( operator term )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>=SOLIDUS && LA8_0<=PLUS)||LA8_0==COMMA||(LA8_0>=STRING && LA8_0<=HASH)||(LA8_0>=EMS && LA8_0<=FREQ)||(LA8_0>=PERCENTAGE && LA8_0<=URI)) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /home/hasdai/Documentos/SimpleCSSParser.g:107:9: operator term
            	    {
            	    pushFollow(FOLLOW_operator_in_expr352);
            	    operator33=operator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, operator33.getTree());
            	    pushFollow(FOLLOW_term_in_expr354);
            	    term34=term();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, term34.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expr"

    public static class term_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "term"
    // /home/hasdai/Documentos/SimpleCSSParser.g:112:1: term : ( ( unaryOperator )? ( NUMBER | PERCENTAGE | LENGTH | EMS | EXS | ANGLE | TIME | FREQ ) | STRING | IDENT ( LPAREN expr RPAREN )? | URI | hexColor );
    public final SimpleCSSParser.term_return term() throws RecognitionException {
        SimpleCSSParser.term_return retval = new SimpleCSSParser.term_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set36=null;
        Token STRING37=null;
        Token IDENT38=null;
        Token LPAREN39=null;
        Token RPAREN41=null;
        Token URI42=null;
        SimpleCSSParser.unaryOperator_return unaryOperator35 = null;

        SimpleCSSParser.expr_return expr40 = null;

        SimpleCSSParser.hexColor_return hexColor43 = null;


        Object set36_tree=null;
        Object STRING37_tree=null;
        Object IDENT38_tree=null;
        Object LPAREN39_tree=null;
        Object RPAREN41_tree=null;
        Object URI42_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:113:1: ( ( unaryOperator )? ( NUMBER | PERCENTAGE | LENGTH | EMS | EXS | ANGLE | TIME | FREQ ) | STRING | IDENT ( LPAREN expr RPAREN )? | URI | hexColor )
            int alt11=5;
            switch ( input.LA(1) ) {
            case MINUS:
            case PLUS:
            case EMS:
            case EXS:
            case LENGTH:
            case ANGLE:
            case TIME:
            case FREQ:
            case PERCENTAGE:
            case NUMBER:
                {
                alt11=1;
                }
                break;
            case STRING:
                {
                alt11=2;
                }
                break;
            case IDENT:
                {
                alt11=3;
                }
                break;
            case URI:
                {
                alt11=4;
                }
                break;
            case HASH:
                {
                alt11=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:113:3: ( unaryOperator )? ( NUMBER | PERCENTAGE | LENGTH | EMS | EXS | ANGLE | TIME | FREQ )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /home/hasdai/Documentos/SimpleCSSParser.g:113:3: ( unaryOperator )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( ((LA9_0>=MINUS && LA9_0<=PLUS)) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // /home/hasdai/Documentos/SimpleCSSParser.g:0:0: unaryOperator
                            {
                            pushFollow(FOLLOW_unaryOperator_in_term367);
                            unaryOperator35=unaryOperator();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryOperator35.getTree());

                            }
                            break;

                    }

                    set36=(Token)input.LT(1);
                    if ( (input.LA(1)>=EMS && input.LA(1)<=FREQ)||(input.LA(1)>=PERCENTAGE && input.LA(1)<=NUMBER) ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set36));
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:114:3: STRING
                    {
                    root_0 = (Object)adaptor.nil();

                    STRING37=(Token)match(input,STRING,FOLLOW_STRING_in_term390); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRING37_tree = (Object)adaptor.create(STRING37);
                    adaptor.addChild(root_0, STRING37_tree);
                    }

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:115:3: IDENT ( LPAREN expr RPAREN )?
                    {
                    root_0 = (Object)adaptor.nil();

                    IDENT38=(Token)match(input,IDENT,FOLLOW_IDENT_in_term394); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IDENT38_tree = (Object)adaptor.create(IDENT38);
                    adaptor.addChild(root_0, IDENT38_tree);
                    }
                    // /home/hasdai/Documentos/SimpleCSSParser.g:115:9: ( LPAREN expr RPAREN )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==LPAREN) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // /home/hasdai/Documentos/SimpleCSSParser.g:115:10: LPAREN expr RPAREN
                            {
                            LPAREN39=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_term397); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            LPAREN39_tree = (Object)adaptor.create(LPAREN39);
                            adaptor.addChild(root_0, LPAREN39_tree);
                            }
                            pushFollow(FOLLOW_expr_in_term399);
                            expr40=expr();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, expr40.getTree());
                            RPAREN41=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_term401); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            RPAREN41_tree = (Object)adaptor.create(RPAREN41);
                            adaptor.addChild(root_0, RPAREN41_tree);
                            }

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:116:3: URI
                    {
                    root_0 = (Object)adaptor.nil();

                    URI42=(Token)match(input,URI,FOLLOW_URI_in_term408); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    URI42_tree = (Object)adaptor.create(URI42);
                    adaptor.addChild(root_0, URI42_tree);
                    }

                    }
                    break;
                case 5 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:117:3: hexColor
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_hexColor_in_term412);
                    hexColor43=hexColor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, hexColor43.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "term"

    public static class unaryOperator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaryOperator"
    // /home/hasdai/Documentos/SimpleCSSParser.g:120:1: unaryOperator : ( MINUS | PLUS );
    public final SimpleCSSParser.unaryOperator_return unaryOperator() throws RecognitionException {
        SimpleCSSParser.unaryOperator_return retval = new SimpleCSSParser.unaryOperator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set44=null;

        Object set44_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:121:1: ( MINUS | PLUS )
            // /home/hasdai/Documentos/SimpleCSSParser.g:
            {
            root_0 = (Object)adaptor.nil();

            set44=(Token)input.LT(1);
            if ( (input.LA(1)>=MINUS && input.LA(1)<=PLUS) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set44));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "unaryOperator"

    public static class hexColor_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "hexColor"
    // /home/hasdai/Documentos/SimpleCSSParser.g:125:1: hexColor : HASH ;
    public final SimpleCSSParser.hexColor_return hexColor() throws RecognitionException {
        SimpleCSSParser.hexColor_return retval = new SimpleCSSParser.hexColor_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token HASH45=null;

        Object HASH45_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:126:1: ( HASH )
            // /home/hasdai/Documentos/SimpleCSSParser.g:126:3: HASH
            {
            root_0 = (Object)adaptor.nil();

            HASH45=(Token)match(input,HASH,FOLLOW_HASH_in_hexColor435); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            HASH45_tree = (Object)adaptor.create(HASH45);
            adaptor.addChild(root_0, HASH45_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "hexColor"

    public static class operator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "operator"
    // /home/hasdai/Documentos/SimpleCSSParser.g:130:1: operator : ( SOLIDUS | COMMA | );
    public final SimpleCSSParser.operator_return operator() throws RecognitionException {
        SimpleCSSParser.operator_return retval = new SimpleCSSParser.operator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SOLIDUS46=null;
        Token COMMA47=null;

        Object SOLIDUS46_tree=null;
        Object COMMA47_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:131:1: ( SOLIDUS | COMMA | )
            int alt12=3;
            switch ( input.LA(1) ) {
            case SOLIDUS:
                {
                alt12=1;
                }
                break;
            case COMMA:
                {
                alt12=2;
                }
                break;
            case MINUS:
            case PLUS:
            case STRING:
            case IDENT:
            case HASH:
            case EMS:
            case EXS:
            case LENGTH:
            case ANGLE:
            case TIME:
            case FREQ:
            case PERCENTAGE:
            case NUMBER:
            case URI:
                {
                alt12=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:131:3: SOLIDUS
                    {
                    root_0 = (Object)adaptor.nil();

                    SOLIDUS46=(Token)match(input,SOLIDUS,FOLLOW_SOLIDUS_in_operator446); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SOLIDUS46_tree = (Object)adaptor.create(SOLIDUS46);
                    adaptor.addChild(root_0, SOLIDUS46_tree);
                    }

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:132:3: COMMA
                    {
                    root_0 = (Object)adaptor.nil();

                    COMMA47=(Token)match(input,COMMA,FOLLOW_COMMA_in_operator450); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMMA47_tree = (Object)adaptor.create(COMMA47);
                    adaptor.addChild(root_0, COMMA47_tree);
                    }

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:134:2:
                    {
                    root_0 = (Object)adaptor.nil();

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "operator"

    // Delegated rules


    protected DFA5 dfa5 = new DFA5(this);
    static final String DFA5_eotS =
        "\21\uffff";
    static final String DFA5_eofS =
        "\21\uffff";
    static final String DFA5_minS =
        "\1\72\1\75\1\53\2\72\1\75\1\72\1\55\1\53\1\75\2\uffff\1\53\2\72"+
        "\1\75\1\53";
    static final String DFA5_maxS =
        "\1\72\1\75\3\72\1\75\1\72\1\75\1\72\1\75\2\uffff\3\72\1\75\1\72";
    static final String DFA5_acceptS =
        "\12\uffff\1\2\1\1\5\uffff";
    static final String DFA5_specialS =
        "\21\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\1",
            "\1\2",
            "\1\4\1\7\10\uffff\1\3\3\uffff\1\6\1\5",
            "\1\5",
            "\1\5",
            "\1\10",
            "\1\11",
            "\1\12\17\uffff\1\13",
            "\1\4\1\7\10\uffff\1\3\3\uffff\1\6\1\5",
            "\1\14",
            "",
            "",
            "\1\16\1\7\10\uffff\1\15\3\uffff\1\6\1\17",
            "\1\17",
            "\1\17",
            "\1\20",
            "\1\16\1\7\10\uffff\1\15\3\uffff\1\6\1\17"
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "55:1: ruleSet : ( selector ( COMMA selector )* LBRACE declaration SEMI ( declaration SEMI )* RBRACE -> ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ( declaration )+ ) ) | selector ( COMMA selector )* LBRACE RBRACE -> ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ) ) );";
        }
    }


    public static final BitSet FOLLOW_bodyList_in_styleSheet88 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_styleSheet90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bodyset_in_bodyList108 = new BitSet(new long[]{0x0400000000000002L});
    public static final BitSet FOLLOW_ruleSet_in_bodyset120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_selector_in_ruleSet133 = new BitSet(new long[]{0x0200100000000000L});
    public static final BitSet FOLLOW_COMMA_in_ruleSet136 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_selector_in_ruleSet138 = new BitSet(new long[]{0x0200100000000000L});
    public static final BitSet FOLLOW_LBRACE_in_ruleSet143 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_declaration_in_ruleSet147 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMI_in_ruleSet149 = new BitSet(new long[]{0x2000200000000000L});
    public static final BitSet FOLLOW_declaration_in_ruleSet152 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMI_in_ruleSet154 = new BitSet(new long[]{0x2000200000000000L});
    public static final BitSet FOLLOW_RBRACE_in_ruleSet159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_selector_in_ruleSet182 = new BitSet(new long[]{0x0200100000000000L});
    public static final BitSet FOLLOW_COMMA_in_ruleSet185 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_selector_in_ruleSet187 = new BitSet(new long[]{0x0200100000000000L});
    public static final BitSet FOLLOW_LBRACE_in_ruleSet192 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_RBRACE_in_ruleSet196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleSelector_in_selector223 = new BitSet(new long[]{0x0420080000000002L});
    public static final BitSet FOLLOW_combinator_in_selector226 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_simpleSelector_in_selector228 = new BitSet(new long[]{0x0420080000000002L});
    public static final BitSet FOLLOW_PLUS_in_combinator240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GREATER_in_combinator244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cssClass_in_simpleSelector262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_elementName280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_cssClass292 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_cssClass295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_property_in_declaration306 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_COLON_in_declaration308 = new BitSet(new long[]{0x7030000000000000L,0x00000000000077E0L});
    public static final BitSet FOLLOW_expr_in_declaration310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_property338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_expr349 = new BitSet(new long[]{0x7238000000000002L,0x00000000000077E0L});
    public static final BitSet FOLLOW_operator_in_expr352 = new BitSet(new long[]{0x7030000000000000L,0x00000000000077E0L});
    public static final BitSet FOLLOW_term_in_expr354 = new BitSet(new long[]{0x7238000000000002L,0x00000000000077E0L});
    public static final BitSet FOLLOW_unaryOperator_in_term367 = new BitSet(new long[]{0x0000000000000000L,0x00000000000037E0L});
    public static final BitSet FOLLOW_set_in_term370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_term390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_term394 = new BitSet(new long[]{0x0080000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_term397 = new BitSet(new long[]{0x7030000000000000L,0x00000000000077E0L});
    public static final BitSet FOLLOW_expr_in_term399 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_RPAREN_in_term401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_URI_in_term408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hexColor_in_term412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_unaryOperator0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_hexColor435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SOLIDUS_in_operator446 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_operator450 = new BitSet(new long[]{0x0000000000000002L});

}