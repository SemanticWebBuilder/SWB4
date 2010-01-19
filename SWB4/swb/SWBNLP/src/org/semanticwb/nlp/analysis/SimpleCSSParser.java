package org.semanticwb.nlp.analysis;

/**
 *
 * @author hasdai
 */
// $ANTLR 3.2 Sep 23, 2009 12:02:23 /home/hasdai/Documentos/INFOTEC/SimpleCSSParser.g 2009-12-09 11:49:56

// $ANTLR 3.1.2 /home/hasdai/Documentos/SimpleCSSParser.g 2010-01-19 13:00:18

import org.antlr.runtime.*;

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
    // /home/hasdai/Documentos/SimpleCSSParser.g:55:1: ruleSet : selector ( COMMA selector )* LBRACE ( declaration SEMI )? ( declaration SEMI )* RBRACE -> ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ( declaration )? ) ) ;
    public final SimpleCSSParser.ruleSet_return ruleSet() throws RecognitionException {
        SimpleCSSParser.ruleSet_return retval = new SimpleCSSParser.ruleSet_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA6=null;
        Token LBRACE8=null;
        Token SEMI10=null;
        Token SEMI12=null;
        Token RBRACE13=null;
        SimpleCSSParser.selector_return selector5 = null;

        SimpleCSSParser.selector_return selector7 = null;

        SimpleCSSParser.declaration_return declaration9 = null;

        SimpleCSSParser.declaration_return declaration11 = null;


        Object COMMA6_tree=null;
        Object LBRACE8_tree=null;
        Object SEMI10_tree=null;
        Object SEMI12_tree=null;
        Object RBRACE13_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_SEMI=new RewriteRuleTokenStream(adaptor,"token SEMI");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_selector=new RewriteRuleSubtreeStream(adaptor,"rule selector");
        RewriteRuleSubtreeStream stream_declaration=new RewriteRuleSubtreeStream(adaptor,"rule declaration");
        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:56:1: ( selector ( COMMA selector )* LBRACE ( declaration SEMI )? ( declaration SEMI )* RBRACE -> ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ( declaration )? ) ) )
            // /home/hasdai/Documentos/SimpleCSSParser.g:56:3: selector ( COMMA selector )* LBRACE ( declaration SEMI )? ( declaration SEMI )* RBRACE
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

            // /home/hasdai/Documentos/SimpleCSSParser.g:58:3: ( declaration SEMI )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==IDENT) ) {
                int LA3_1 = input.LA(2);

                if ( (synpred3_SimpleCSSParser()) ) {
                    alt3=1;
                }
            }
            switch (alt3) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:58:4: declaration SEMI
                    {
                    pushFollow(FOLLOW_declaration_in_ruleSet148);
                    declaration9=declaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_declaration.add(declaration9.getTree());
                    SEMI10=(Token)match(input,SEMI,FOLLOW_SEMI_in_ruleSet150); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SEMI.add(SEMI10);


                    }
                    break;

            }

            // /home/hasdai/Documentos/SimpleCSSParser.g:58:23: ( declaration SEMI )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==IDENT) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/hasdai/Documentos/SimpleCSSParser.g:58:24: declaration SEMI
            	    {
            	    pushFollow(FOLLOW_declaration_in_ruleSet155);
            	    declaration11=declaration();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_declaration.add(declaration11.getTree());
            	    SEMI12=(Token)match(input,SEMI,FOLLOW_SEMI_in_ruleSet157); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_SEMI.add(SEMI12);


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            RBRACE13=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_ruleSet162); if (state.failed) return retval;
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
            // 59:9: -> ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ( declaration )? ) )
            {
                // /home/hasdai/Documentos/SimpleCSSParser.g:59:12: ^( SELECTOR ^( ELEMENTS selector ) ^( STYLE ( declaration )? ) )
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
                // /home/hasdai/Documentos/SimpleCSSParser.g:59:44: ^( STYLE ( declaration )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STYLE, "STYLE"), root_2);

                // /home/hasdai/Documentos/SimpleCSSParser.g:59:52: ( declaration )?
                if ( stream_declaration.hasNext() ) {
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
    // /home/hasdai/Documentos/SimpleCSSParser.g:63:1: selector : simpleSelector ( combinator simpleSelector )* ;
    public final SimpleCSSParser.selector_return selector() throws RecognitionException {
        SimpleCSSParser.selector_return retval = new SimpleCSSParser.selector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SimpleCSSParser.simpleSelector_return simpleSelector14 = null;

        SimpleCSSParser.combinator_return combinator15 = null;

        SimpleCSSParser.simpleSelector_return simpleSelector16 = null;



        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:64:1: ( simpleSelector ( combinator simpleSelector )* )
            // /home/hasdai/Documentos/SimpleCSSParser.g:64:3: simpleSelector ( combinator simpleSelector )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_simpleSelector_in_selector192);
            simpleSelector14=simpleSelector();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, simpleSelector14.getTree());
            // /home/hasdai/Documentos/SimpleCSSParser.g:64:18: ( combinator simpleSelector )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==GREATER||LA5_0==PLUS||LA5_0==DOT) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/hasdai/Documentos/SimpleCSSParser.g:64:19: combinator simpleSelector
            	    {
            	    pushFollow(FOLLOW_combinator_in_selector195);
            	    combinator15=combinator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, combinator15.getTree());
            	    pushFollow(FOLLOW_simpleSelector_in_selector197);
            	    simpleSelector16=simpleSelector();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, simpleSelector16.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
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
    // /home/hasdai/Documentos/SimpleCSSParser.g:67:1: combinator : ( PLUS | GREATER | );
    public final SimpleCSSParser.combinator_return combinator() throws RecognitionException {
        SimpleCSSParser.combinator_return retval = new SimpleCSSParser.combinator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PLUS17=null;
        Token GREATER18=null;

        Object PLUS17_tree=null;
        Object GREATER18_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:68:1: ( PLUS | GREATER | )
            int alt6=3;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt6=1;
                }
                break;
            case GREATER:
                {
                alt6=2;
                }
                break;
            case DOT:
                {
                alt6=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:68:3: PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    PLUS17=(Token)match(input,PLUS,FOLLOW_PLUS_in_combinator209); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS17_tree = (Object)adaptor.create(PLUS17);
                    adaptor.addChild(root_0, PLUS17_tree);
                    }

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:69:3: GREATER
                    {
                    root_0 = (Object)adaptor.nil();

                    GREATER18=(Token)match(input,GREATER,FOLLOW_GREATER_in_combinator213); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    GREATER18_tree = (Object)adaptor.create(GREATER18);
                    adaptor.addChild(root_0, GREATER18_tree);
                    }

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:71:2:
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
    // /home/hasdai/Documentos/SimpleCSSParser.g:76:1: simpleSelector : cssClass -> ^( CLASS cssClass ) ;
    public final SimpleCSSParser.simpleSelector_return simpleSelector() throws RecognitionException {
        SimpleCSSParser.simpleSelector_return retval = new SimpleCSSParser.simpleSelector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SimpleCSSParser.cssClass_return cssClass19 = null;


        RewriteRuleSubtreeStream stream_cssClass=new RewriteRuleSubtreeStream(adaptor,"rule cssClass");
        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:77:1: ( cssClass -> ^( CLASS cssClass ) )
            // /home/hasdai/Documentos/SimpleCSSParser.g:78:2: cssClass
            {
            pushFollow(FOLLOW_cssClass_in_simpleSelector231);
            cssClass19=cssClass();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_cssClass.add(cssClass19.getTree());


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
            // 78:11: -> ^( CLASS cssClass )
            {
                // /home/hasdai/Documentos/SimpleCSSParser.g:78:14: ^( CLASS cssClass )
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
    // /home/hasdai/Documentos/SimpleCSSParser.g:82:1: elementName : IDENT ;
    public final SimpleCSSParser.elementName_return elementName() throws RecognitionException {
        SimpleCSSParser.elementName_return retval = new SimpleCSSParser.elementName_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENT20=null;

        Object IDENT20_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:83:1: ( IDENT )
            // /home/hasdai/Documentos/SimpleCSSParser.g:83:3: IDENT
            {
            root_0 = (Object)adaptor.nil();

            IDENT20=(Token)match(input,IDENT,FOLLOW_IDENT_in_elementName249); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IDENT20_tree = (Object)adaptor.create(IDENT20);
            adaptor.addChild(root_0, IDENT20_tree);
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
    // /home/hasdai/Documentos/SimpleCSSParser.g:88:1: cssClass : DOT IDENT ;
    public final SimpleCSSParser.cssClass_return cssClass() throws RecognitionException {
        SimpleCSSParser.cssClass_return retval = new SimpleCSSParser.cssClass_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOT21=null;
        Token IDENT22=null;

        Object DOT21_tree=null;
        Object IDENT22_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:89:1: ( DOT IDENT )
            // /home/hasdai/Documentos/SimpleCSSParser.g:89:3: DOT IDENT
            {
            root_0 = (Object)adaptor.nil();

            DOT21=(Token)match(input,DOT,FOLLOW_DOT_in_cssClass261); if (state.failed) return retval;
            IDENT22=(Token)match(input,IDENT,FOLLOW_IDENT_in_cssClass264); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IDENT22_tree = (Object)adaptor.create(IDENT22);
            adaptor.addChild(root_0, IDENT22_tree);
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
    // /home/hasdai/Documentos/SimpleCSSParser.g:93:1: declaration : property COLON expr -> ^( EXPR ^( NAME property ) ^( VAL expr ) ) ;
    public final SimpleCSSParser.declaration_return declaration() throws RecognitionException {
        SimpleCSSParser.declaration_return retval = new SimpleCSSParser.declaration_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COLON24=null;
        SimpleCSSParser.property_return property23 = null;

        SimpleCSSParser.expr_return expr25 = null;


        Object COLON24_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleSubtreeStream stream_property=new RewriteRuleSubtreeStream(adaptor,"rule property");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:94:1: ( property COLON expr -> ^( EXPR ^( NAME property ) ^( VAL expr ) ) )
            // /home/hasdai/Documentos/SimpleCSSParser.g:94:3: property COLON expr
            {
            pushFollow(FOLLOW_property_in_declaration275);
            property23=property();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_property.add(property23.getTree());
            COLON24=(Token)match(input,COLON,FOLLOW_COLON_in_declaration277); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_COLON.add(COLON24);

            pushFollow(FOLLOW_expr_in_declaration279);
            expr25=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr25.getTree());


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
            // 94:23: -> ^( EXPR ^( NAME property ) ^( VAL expr ) )
            {
                // /home/hasdai/Documentos/SimpleCSSParser.g:94:26: ^( EXPR ^( NAME property ) ^( VAL expr ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR, "EXPR"), root_1);

                // /home/hasdai/Documentos/SimpleCSSParser.g:94:33: ^( NAME property )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_property.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // /home/hasdai/Documentos/SimpleCSSParser.g:94:50: ^( VAL expr )
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
    // /home/hasdai/Documentos/SimpleCSSParser.g:98:1: property : IDENT ;
    public final SimpleCSSParser.property_return property() throws RecognitionException {
        SimpleCSSParser.property_return retval = new SimpleCSSParser.property_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENT26=null;

        Object IDENT26_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:99:1: ( IDENT )
            // /home/hasdai/Documentos/SimpleCSSParser.g:99:3: IDENT
            {
            root_0 = (Object)adaptor.nil();

            IDENT26=(Token)match(input,IDENT,FOLLOW_IDENT_in_property307); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IDENT26_tree = (Object)adaptor.create(IDENT26);
            adaptor.addChild(root_0, IDENT26_tree);
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
    // /home/hasdai/Documentos/SimpleCSSParser.g:103:1: expr : term ( operator term )* ;
    public final SimpleCSSParser.expr_return expr() throws RecognitionException {
        SimpleCSSParser.expr_return retval = new SimpleCSSParser.expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SimpleCSSParser.term_return term27 = null;

        SimpleCSSParser.operator_return operator28 = null;

        SimpleCSSParser.term_return term29 = null;



        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:104:1: ( term ( operator term )* )
            // /home/hasdai/Documentos/SimpleCSSParser.g:104:3: term ( operator term )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_term_in_expr318);
            term27=term();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, term27.getTree());
            // /home/hasdai/Documentos/SimpleCSSParser.g:104:8: ( operator term )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>=SOLIDUS && LA7_0<=PLUS)||LA7_0==COMMA||(LA7_0>=STRING && LA7_0<=HASH)||(LA7_0>=EMS && LA7_0<=FREQ)||(LA7_0>=PERCENTAGE && LA7_0<=URI)) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /home/hasdai/Documentos/SimpleCSSParser.g:104:9: operator term
            	    {
            	    pushFollow(FOLLOW_operator_in_expr321);
            	    operator28=operator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, operator28.getTree());
            	    pushFollow(FOLLOW_term_in_expr323);
            	    term29=term();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, term29.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
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
    // /home/hasdai/Documentos/SimpleCSSParser.g:109:1: term : ( ( unaryOperator )? ( NUMBER | PERCENTAGE | LENGTH | EMS | EXS | ANGLE | TIME | FREQ ) | STRING | IDENT ( LPAREN expr RPAREN )? | URI | hexColor );
    public final SimpleCSSParser.term_return term() throws RecognitionException {
        SimpleCSSParser.term_return retval = new SimpleCSSParser.term_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set31=null;
        Token STRING32=null;
        Token IDENT33=null;
        Token LPAREN34=null;
        Token RPAREN36=null;
        Token URI37=null;
        SimpleCSSParser.unaryOperator_return unaryOperator30 = null;

        SimpleCSSParser.expr_return expr35 = null;

        SimpleCSSParser.hexColor_return hexColor38 = null;


        Object set31_tree=null;
        Object STRING32_tree=null;
        Object IDENT33_tree=null;
        Object LPAREN34_tree=null;
        Object RPAREN36_tree=null;
        Object URI37_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:110:1: ( ( unaryOperator )? ( NUMBER | PERCENTAGE | LENGTH | EMS | EXS | ANGLE | TIME | FREQ ) | STRING | IDENT ( LPAREN expr RPAREN )? | URI | hexColor )
            int alt10=5;
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
                alt10=1;
                }
                break;
            case STRING:
                {
                alt10=2;
                }
                break;
            case IDENT:
                {
                alt10=3;
                }
                break;
            case URI:
                {
                alt10=4;
                }
                break;
            case HASH:
                {
                alt10=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:110:3: ( unaryOperator )? ( NUMBER | PERCENTAGE | LENGTH | EMS | EXS | ANGLE | TIME | FREQ )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /home/hasdai/Documentos/SimpleCSSParser.g:110:3: ( unaryOperator )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( ((LA8_0>=MINUS && LA8_0<=PLUS)) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /home/hasdai/Documentos/SimpleCSSParser.g:0:0: unaryOperator
                            {
                            pushFollow(FOLLOW_unaryOperator_in_term336);
                            unaryOperator30=unaryOperator();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryOperator30.getTree());

                            }
                            break;

                    }

                    set31=(Token)input.LT(1);
                    if ( (input.LA(1)>=EMS && input.LA(1)<=FREQ)||(input.LA(1)>=PERCENTAGE && input.LA(1)<=NUMBER) ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set31));
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
                    // /home/hasdai/Documentos/SimpleCSSParser.g:111:3: STRING
                    {
                    root_0 = (Object)adaptor.nil();

                    STRING32=(Token)match(input,STRING,FOLLOW_STRING_in_term359); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRING32_tree = (Object)adaptor.create(STRING32);
                    adaptor.addChild(root_0, STRING32_tree);
                    }

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:112:3: IDENT ( LPAREN expr RPAREN )?
                    {
                    root_0 = (Object)adaptor.nil();

                    IDENT33=(Token)match(input,IDENT,FOLLOW_IDENT_in_term363); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IDENT33_tree = (Object)adaptor.create(IDENT33);
                    adaptor.addChild(root_0, IDENT33_tree);
                    }
                    // /home/hasdai/Documentos/SimpleCSSParser.g:112:9: ( LPAREN expr RPAREN )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==LPAREN) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // /home/hasdai/Documentos/SimpleCSSParser.g:112:10: LPAREN expr RPAREN
                            {
                            LPAREN34=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_term366); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            LPAREN34_tree = (Object)adaptor.create(LPAREN34);
                            adaptor.addChild(root_0, LPAREN34_tree);
                            }
                            pushFollow(FOLLOW_expr_in_term368);
                            expr35=expr();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, expr35.getTree());
                            RPAREN36=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_term370); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            RPAREN36_tree = (Object)adaptor.create(RPAREN36);
                            adaptor.addChild(root_0, RPAREN36_tree);
                            }

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:113:3: URI
                    {
                    root_0 = (Object)adaptor.nil();

                    URI37=(Token)match(input,URI,FOLLOW_URI_in_term377); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    URI37_tree = (Object)adaptor.create(URI37);
                    adaptor.addChild(root_0, URI37_tree);
                    }

                    }
                    break;
                case 5 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:114:3: hexColor
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_hexColor_in_term381);
                    hexColor38=hexColor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, hexColor38.getTree());

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
    // /home/hasdai/Documentos/SimpleCSSParser.g:117:1: unaryOperator : ( MINUS | PLUS );
    public final SimpleCSSParser.unaryOperator_return unaryOperator() throws RecognitionException {
        SimpleCSSParser.unaryOperator_return retval = new SimpleCSSParser.unaryOperator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set39=null;

        Object set39_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:118:1: ( MINUS | PLUS )
            // /home/hasdai/Documentos/SimpleCSSParser.g:
            {
            root_0 = (Object)adaptor.nil();

            set39=(Token)input.LT(1);
            if ( (input.LA(1)>=MINUS && input.LA(1)<=PLUS) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set39));
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
    // /home/hasdai/Documentos/SimpleCSSParser.g:122:1: hexColor : HASH ;
    public final SimpleCSSParser.hexColor_return hexColor() throws RecognitionException {
        SimpleCSSParser.hexColor_return retval = new SimpleCSSParser.hexColor_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token HASH40=null;

        Object HASH40_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:123:1: ( HASH )
            // /home/hasdai/Documentos/SimpleCSSParser.g:123:3: HASH
            {
            root_0 = (Object)adaptor.nil();

            HASH40=(Token)match(input,HASH,FOLLOW_HASH_in_hexColor404); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            HASH40_tree = (Object)adaptor.create(HASH40);
            adaptor.addChild(root_0, HASH40_tree);
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
    // /home/hasdai/Documentos/SimpleCSSParser.g:126:1: operator : ( SOLIDUS | COMMA | );
    public final SimpleCSSParser.operator_return operator() throws RecognitionException {
        SimpleCSSParser.operator_return retval = new SimpleCSSParser.operator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SOLIDUS41=null;
        Token COMMA42=null;

        Object SOLIDUS41_tree=null;
        Object COMMA42_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleCSSParser.g:127:1: ( SOLIDUS | COMMA | )
            int alt11=3;
            switch ( input.LA(1) ) {
            case SOLIDUS:
                {
                alt11=1;
                }
                break;
            case COMMA:
                {
                alt11=2;
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
                alt11=3;
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
                    // /home/hasdai/Documentos/SimpleCSSParser.g:128:2: SOLIDUS
                    {
                    root_0 = (Object)adaptor.nil();

                    SOLIDUS41=(Token)match(input,SOLIDUS,FOLLOW_SOLIDUS_in_operator416); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SOLIDUS41_tree = (Object)adaptor.create(SOLIDUS41);
                    adaptor.addChild(root_0, SOLIDUS41_tree);
                    }

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:129:3: COMMA
                    {
                    root_0 = (Object)adaptor.nil();

                    COMMA42=(Token)match(input,COMMA,FOLLOW_COMMA_in_operator420); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMMA42_tree = (Object)adaptor.create(COMMA42);
                    adaptor.addChild(root_0, COMMA42_tree);
                    }

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/SimpleCSSParser.g:131:1:
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

    // $ANTLR start synpred3_SimpleCSSParser
    public final void synpred3_SimpleCSSParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/SimpleCSSParser.g:58:4: ( declaration SEMI )
        // /home/hasdai/Documentos/SimpleCSSParser.g:58:4: declaration SEMI
        {
        pushFollow(FOLLOW_declaration_in_synpred3_SimpleCSSParser148);
        declaration();

        state._fsp--;
        if (state.failed) return ;
        match(input,SEMI,FOLLOW_SEMI_in_synpred3_SimpleCSSParser150); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_SimpleCSSParser

    // Delegated rules

    public final boolean synpred3_SimpleCSSParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_SimpleCSSParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }




    public static final BitSet FOLLOW_bodyList_in_styleSheet88 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_styleSheet90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bodyset_in_bodyList108 = new BitSet(new long[]{0x0400000000000002L});
    public static final BitSet FOLLOW_ruleSet_in_bodyset120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_selector_in_ruleSet133 = new BitSet(new long[]{0x0200100000000000L});
    public static final BitSet FOLLOW_COMMA_in_ruleSet136 = new BitSet(new long[]{0x0600100000000000L});
    public static final BitSet FOLLOW_selector_in_ruleSet138 = new BitSet(new long[]{0x0200100000000000L});
    public static final BitSet FOLLOW_LBRACE_in_ruleSet143 = new BitSet(new long[]{0x2000200000000000L});
    public static final BitSet FOLLOW_declaration_in_ruleSet148 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMI_in_ruleSet150 = new BitSet(new long[]{0x2000200000000000L});
    public static final BitSet FOLLOW_declaration_in_ruleSet155 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMI_in_ruleSet157 = new BitSet(new long[]{0x2000200000000000L});
    public static final BitSet FOLLOW_RBRACE_in_ruleSet162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleSelector_in_selector192 = new BitSet(new long[]{0x0420080000000000L});
    public static final BitSet FOLLOW_combinator_in_selector195 = new BitSet(new long[]{0x0420080000000000L});
    public static final BitSet FOLLOW_simpleSelector_in_selector197 = new BitSet(new long[]{0x0420080000000002L});
    public static final BitSet FOLLOW_PLUS_in_combinator209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GREATER_in_combinator213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cssClass_in_simpleSelector231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_elementName249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_cssClass261 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_cssClass264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_property_in_declaration275 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_COLON_in_declaration277 = new BitSet(new long[]{0x7030000000000000L,0x00000000000077E0L});
    public static final BitSet FOLLOW_expr_in_declaration279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_property307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_expr318 = new BitSet(new long[]{0x7238000000000002L,0x00000000000077E0L});
    public static final BitSet FOLLOW_operator_in_expr321 = new BitSet(new long[]{0x7030000000000000L,0x00000000000077E0L});
    public static final BitSet FOLLOW_term_in_expr323 = new BitSet(new long[]{0x7238000000000002L,0x00000000000077E0L});
    public static final BitSet FOLLOW_unaryOperator_in_term336 = new BitSet(new long[]{0x0000000000000000L,0x00000000000037E0L});
    public static final BitSet FOLLOW_set_in_term339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_term359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_term363 = new BitSet(new long[]{0x0080000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_term366 = new BitSet(new long[]{0x7030000000000000L,0x00000000000077E0L});
    public static final BitSet FOLLOW_expr_in_term368 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_RPAREN_in_term370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_URI_in_term377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hexColor_in_term381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_unaryOperator0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_hexColor404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SOLIDUS_in_operator416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_operator420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declaration_in_synpred3_SimpleCSSParser148 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMI_in_synpred3_SimpleCSSParser150 = new BitSet(new long[]{0x0000000000000002L});

}