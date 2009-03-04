/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

/**
 *
 * @author hasdai
 */
// $ANTLR 3.1.2 /home/hasdai/Documentos/sParser.g 2009-03-03 11:51:42

import java.util.Iterator;
import org.antlr.runtime.*;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;

public class sParser extends org.antlr.runtime.Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "WHITESPACE", "SIGN", "SIGL", "SIGG", "SIGE", "SIGLE", "SIGGE", "PREC", "MODT", "PRED", "MODE", "MODO", "BOL", "NUM", "LIT", "ORDOP", "VAR", "DEL", "LPAR", "RPAR", "SIGI", "LIMIT", "SELECTQUERY", "MOD", "OBJLIST", "PROPLIST", "ASIGN", "SENTENCE", "COMPL", "COMPG", "COMPLE", "COMPGE", "PRECON", "PREDE", "OFFSET", "ORDER", "COMPNAME", "ASKQUERY", "MODTO"
    };
    public static final int SENTENCE=31;
    public static final int ASIGN=30;
    public static final int MODO=15;
    public static final int SIGL=6;
    public static final int RPAR=23;
    public static final int SIGGE=10;
    public static final int LPAR=22;
    public static final int PREC=11;
    public static final int SIGG=7;
    public static final int OFFSET=38;
    public static final int MODE=14;
    public static final int PREDE=37;
    public static final int COMPGE=35;
    public static final int COMPLE=34;
    public static final int MOD=27;
    public static final int LIT=18;
    public static final int PRED=13;
    public static final int BOL=16;
    public static final int SIGI=24;
    public static final int OBJLIST=28;
    public static final int WHITESPACE=4;
    public static final int SIGLE=9;
    public static final int ORDOP=19;
    public static final int VAR=20;
    public static final int ORDER=39;
    public static final int MODT=12;
    public static final int COMPNAME=40;
    public static final int EOF=-1;
    public static final int NUM=17;
    public static final int COMPG=33;
    public static final int MODTO=42;
    public static final int SELECTQUERY=26;
    public static final int SIGN=5;
    public static final int COMPL=32;
    public static final int PROPLIST=29;
    public static final int ASKQUERY=41;
    public static final int SIGE=8;
    public static final int DEL=21;
    public static final int PRECON=36;
    public static final int LIMIT=25;

    // delegates
    // delegators


        public sParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public sParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);

        }

    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return sParser.tokenNames; }
    public String getGrammarFileName() { return "/home/hasdai/Documentos/sParser.g"; }


    	private int errCount = 0;
    	private ArrayList<String> objs = new ArrayList<String>();
    	private ArrayList<String> props = new ArrayList<String>();
    	private String lim = "";
    	private String mod = "";
    	private boolean select = false;
    	private boolean pcon = false;
    	private boolean prde = false;
    	private Lexicon lex;

    @Override
    	public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
            	errCount++;
        	}

        	public String getLimit() {
        		return lim;
        	}

        	public String getModifier() {
        		return mod;
        	}

        	public ArrayList<String> getObjectList() {
        		return objs;
        	}

        	public ArrayList<String> getPropertyList() {
        		return props;
        	}

    	public int getErrorCount() {
            	return errCount;
        	}

        	public String getSparqlQuery () {
        		String res = "";
        		Iterator<String> oi;

        		if (select) {
        			res += "SELECT ";
        			if (pcon) {
        				oi = objs.iterator();
        				while (oi.hasNext()) {
        					String t = oi.next();
        					res = res + t.substring(0, t.indexOf(' ')) + " ";
        				}
        			}
        			if (prde) {
        				res += "* ";
        			}
        			res += "\nWHERE\n";
        		}
        		if (!select) {
        			res += "ASK ";
        		}
        		res += "{\n";
        		oi = objs.iterator();
                    while (oi.hasNext()) {
                    	String t = oi.next();
                    	res = res + t + ".\n";
                    	Iterator<String> pi = props.iterator();
                    	while (pi.hasNext()) {
                    		if (select) {
                    			res += "{";
                    		}
                    		res = res + t.substring(0, t.indexOf(' ')) + " " + pi.next();
                    		if (select) {
                    			res += "}";
                    		} else {
                    			res+= ".";
                    		}
                    		if (pi.hasNext() && select) {
                                   		res += " UNION\n";
                                	}
                    	}
                    	res += "}\n";
                    }
                    res += mod;
                    res += lim;
        		return res;
        	}

        	public boolean succes() {
        		return (errCount == 0) ? true : false;
        	}


    public static class squery_return extends ParserRuleReturnScope {
        Object tree;
        @Override
        public Object getTree() { return tree; }
    };

    // $ANTLR start "squery"
    // /home/hasdai/Documentos/sParser.g:107:1: squery[Lexicon dict] : ( askquery | selectquery );
    public final sParser.squery_return squery(Lexicon dict) throws RecognitionException {
        sParser.squery_return retval = new sParser.squery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        sParser.askquery_return askquery1 = null;

        sParser.selectquery_return selectquery2 = null;




        	lex = dict;

        try {
            // /home/hasdai/Documentos/sParser.g:111:1: ( askquery | selectquery )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==VAR) ) {
                int LA1_1 = input.LA(2);

                if ( (synpred1_sParser()) ) {
                    alt1=1;
                }
                else if ( (true) ) {
                    alt1=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA1_0==NUM) ) {
                alt1=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:111:3: askquery
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_askquery_in_squery121);
                    askquery1=askquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, askquery1.getTree());
                    if ( state.backtracking==0 ) {
                      select = false;
                    }

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:112:3: selectquery
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_selectquery_in_squery127);
                    selectquery2=selectquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, selectquery2.getTree());
                    if ( state.backtracking==0 ) {
                      select = true;
                    }

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
    // $ANTLR end "squery"

    public static class selectquery_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "selectquery"
    // /home/hasdai/Documentos/sParser.g:116:1: selectquery : ( limiter )? query ( modifier )? EOF -> ^( SELECTQUERY ( limiter )? query ( modifier )? ) ;
    public final sParser.selectquery_return selectquery() throws RecognitionException {
        sParser.selectquery_return retval = new sParser.selectquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF6=null;
        sParser.limiter_return limiter3 = null;

        sParser.query_return query4 = null;

        sParser.modifier_return modifier5 = null;


        Object EOF6_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_query=new RewriteRuleSubtreeStream(adaptor,"rule query");
        RewriteRuleSubtreeStream stream_modifier=new RewriteRuleSubtreeStream(adaptor,"rule modifier");
        RewriteRuleSubtreeStream stream_limiter=new RewriteRuleSubtreeStream(adaptor,"rule limiter");
        try {
            // /home/hasdai/Documentos/sParser.g:117:1: ( ( limiter )? query ( modifier )? EOF -> ^( SELECTQUERY ( limiter )? query ( modifier )? ) )
            // /home/hasdai/Documentos/sParser.g:117:3: ( limiter )? query ( modifier )? EOF
            {
            // /home/hasdai/Documentos/sParser.g:117:3: ( limiter )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==NUM) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:0:0: limiter
                    {
                    pushFollow(FOLLOW_limiter_in_selectquery140);
                    limiter3=limiter();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_limiter.add(limiter3.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_query_in_selectquery143);
            query4=query();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_query.add(query4.getTree());
            // /home/hasdai/Documentos/sParser.g:117:18: ( modifier )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0>=MODE && LA3_0<=MODO)) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:0:0: modifier
                    {
                    pushFollow(FOLLOW_modifier_in_selectquery145);
                    modifier5=modifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_modifier.add(modifier5.getTree());

                    }
                    break;

            }

            EOF6=(Token)match(input,EOF,FOLLOW_EOF_in_selectquery148); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_EOF.add(EOF6);

            if ( state.backtracking==0 ) {
            }


            // AST REWRITE
            // elements: limiter, query, modifier
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 117:35: -> ^( SELECTQUERY ( limiter )? query ( modifier )? )
            {
                // /home/hasdai/Documentos/sParser.g:117:38: ^( SELECTQUERY ( limiter )? query ( modifier )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECTQUERY, "SELECTQUERY"), root_1);

                // /home/hasdai/Documentos/sParser.g:117:52: ( limiter )?
                if ( stream_limiter.hasNext() ) {
                    adaptor.addChild(root_1, stream_limiter.nextTree());

                }
                stream_limiter.reset();
                adaptor.addChild(root_1, stream_query.nextTree());
                // /home/hasdai/Documentos/sParser.g:117:67: ( modifier )?
                if ( stream_modifier.hasNext() ) {
                    adaptor.addChild(root_1, stream_modifier.nextTree());

                }
                stream_modifier.reset();

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
    // $ANTLR end "selectquery"

    public static class askquery_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "askquery"
    // /home/hasdai/Documentos/sParser.g:121:1: askquery : query SIGI EOF -> ^( ASKQUERY query ) ;
    public final sParser.askquery_return askquery() throws RecognitionException {
        sParser.askquery_return retval = new sParser.askquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SIGI8=null;
        Token EOF9=null;
        sParser.query_return query7 = null;


        Object SIGI8_tree=null;
        Object EOF9_tree=null;
        RewriteRuleTokenStream stream_SIGI=new RewriteRuleTokenStream(adaptor,"token SIGI");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_query=new RewriteRuleSubtreeStream(adaptor,"rule query");
        try {
            // /home/hasdai/Documentos/sParser.g:122:1: ( query SIGI EOF -> ^( ASKQUERY query ) )
            // /home/hasdai/Documentos/sParser.g:122:3: query SIGI EOF
            {
            pushFollow(FOLLOW_query_in_askquery175);
            query7=query();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_query.add(query7.getTree());
            SIGI8=(Token)match(input,SIGI,FOLLOW_SIGI_in_askquery177); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_SIGI.add(SIGI8);

            EOF9=(Token)match(input,EOF,FOLLOW_EOF_in_askquery179); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_EOF.add(EOF9);



            // AST REWRITE
            // elements: query
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 122:17: -> ^( ASKQUERY query )
            {
                // /home/hasdai/Documentos/sParser.g:122:20: ^( ASKQUERY query )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ASKQUERY, "ASKQUERY"), root_1);

                adaptor.addChild(root_1, stream_query.nextTree());

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
    // $ANTLR end "askquery"

    public static class limiter_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "limiter"
    // /home/hasdai/Documentos/sParser.g:125:1: limiter : NUM -> ^( LIMIT NUM ) ;
    public final sParser.limiter_return limiter() throws RecognitionException {
        sParser.limiter_return retval = new sParser.limiter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUM10=null;

        Object NUM10_tree=null;
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");

        try {
            // /home/hasdai/Documentos/sParser.g:126:1: ( NUM -> ^( LIMIT NUM ) )
            // /home/hasdai/Documentos/sParser.g:126:3: NUM
            {
            NUM10=(Token)match(input,NUM,FOLLOW_NUM_in_limiter196); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_NUM.add(NUM10);

            if ( state.backtracking==0 ) {
              lim = " LIMIT " + NUM10.getText();
            }


            // AST REWRITE
            // elements: NUM
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 126:43: -> ^( LIMIT NUM )
            {
                // /home/hasdai/Documentos/sParser.g:126:46: ^( LIMIT NUM )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(LIMIT, "LIMIT"), root_1);

                adaptor.addChild(root_1, stream_NUM.nextNode());

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
    // $ANTLR end "limiter"

    public static class query_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "query"
    // /home/hasdai/Documentos/sParser.g:129:1: query : ( objquery | propquery );
    public final sParser.query_return query() throws RecognitionException {
        sParser.query_return retval = new sParser.query_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        sParser.objquery_return objquery11 = null;

        sParser.propquery_return propquery12 = null;



        try {
            // /home/hasdai/Documentos/sParser.g:130:1: ( objquery | propquery )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==VAR) ) {
                int LA4_1 = input.LA(2);

                if ( (synpred4_sParser()) ) {
                    alt4=1;
                }
                else if ( (true) ) {
                    alt4=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:130:3: objquery
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_objquery_in_query216);
                    objquery11=objquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, objquery11.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:131:3: propquery
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_propquery_in_query220);
                    propquery12=propquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, propquery12.getTree());

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
    // $ANTLR end "query"

    public static class objquery_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "objquery"
    // /home/hasdai/Documentos/sParser.g:134:1: objquery : ( objlist PREC proplist -> ^( PRECON ^( OBJLIST objlist ) ^( PROPLIST proplist ) ) | objlist -> ^( OBJLIST objlist ) );
    public final sParser.objquery_return objquery() throws RecognitionException {
        sParser.objquery_return retval = new sParser.objquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PREC14=null;
        sParser.objlist_return objlist13 = null;

        sParser.proplist_return proplist15 = null;

        sParser.objlist_return objlist16 = null;


        Object PREC14_tree=null;
        RewriteRuleTokenStream stream_PREC=new RewriteRuleTokenStream(adaptor,"token PREC");
        RewriteRuleSubtreeStream stream_proplist=new RewriteRuleSubtreeStream(adaptor,"rule proplist");
        RewriteRuleSubtreeStream stream_objlist=new RewriteRuleSubtreeStream(adaptor,"rule objlist");
        try {
            // /home/hasdai/Documentos/sParser.g:135:1: ( objlist PREC proplist -> ^( PRECON ^( OBJLIST objlist ) ^( PROPLIST proplist ) ) | objlist -> ^( OBJLIST objlist ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==VAR) ) {
                int LA5_1 = input.LA(2);

                if ( (synpred5_sParser()) ) {
                    alt5=1;
                }
                else if ( (true) ) {
                    alt5=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:135:3: objlist PREC proplist
                    {
                    pushFollow(FOLLOW_objlist_in_objquery230);
                    objlist13=objlist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_objlist.add(objlist13.getTree());
                    PREC14=(Token)match(input,PREC,FOLLOW_PREC_in_objquery232); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PREC.add(PREC14);

                    pushFollow(FOLLOW_proplist_in_objquery234);
                    proplist15=proplist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_proplist.add(proplist15.getTree());
                    if ( state.backtracking==0 ) {
                      pcon = true;
                    }


                    // AST REWRITE
                    // elements: objlist, proplist
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 135:40: -> ^( PRECON ^( OBJLIST objlist ) ^( PROPLIST proplist ) )
                    {
                        // /home/hasdai/Documentos/sParser.g:135:43: ^( PRECON ^( OBJLIST objlist ) ^( PROPLIST proplist ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PRECON, "PRECON"), root_1);

                        // /home/hasdai/Documentos/sParser.g:135:52: ^( OBJLIST objlist )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OBJLIST, "OBJLIST"), root_2);

                        adaptor.addChild(root_2, stream_objlist.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // /home/hasdai/Documentos/sParser.g:135:71: ^( PROPLIST proplist )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PROPLIST, "PROPLIST"), root_2);

                        adaptor.addChild(root_2, stream_proplist.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:136:3: objlist
                    {
                    pushFollow(FOLLOW_objlist_in_objquery258);
                    objlist16=objlist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_objlist.add(objlist16.getTree());


                    // AST REWRITE
                    // elements: objlist
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 136:11: -> ^( OBJLIST objlist )
                    {
                        // /home/hasdai/Documentos/sParser.g:136:14: ^( OBJLIST objlist )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(OBJLIST, "OBJLIST"), root_1);

                        adaptor.addChild(root_1, stream_objlist.nextTree());

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
    // $ANTLR end "objquery"

    public static class propquery_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propquery"
    // /home/hasdai/Documentos/sParser.g:139:1: propquery : proplist PRED objlist -> ^( PREDE ^( PROPLIST proplist ) ^( OBJLIST objlist ) ) ;
    public final sParser.propquery_return propquery() throws RecognitionException {
        sParser.propquery_return retval = new sParser.propquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRED18=null;
        sParser.proplist_return proplist17 = null;

        sParser.objlist_return objlist19 = null;


        Object PRED18_tree=null;
        RewriteRuleTokenStream stream_PRED=new RewriteRuleTokenStream(adaptor,"token PRED");
        RewriteRuleSubtreeStream stream_proplist=new RewriteRuleSubtreeStream(adaptor,"rule proplist");
        RewriteRuleSubtreeStream stream_objlist=new RewriteRuleSubtreeStream(adaptor,"rule objlist");
        try {
            // /home/hasdai/Documentos/sParser.g:140:1: ( proplist PRED objlist -> ^( PREDE ^( PROPLIST proplist ) ^( OBJLIST objlist ) ) )
            // /home/hasdai/Documentos/sParser.g:140:3: proplist PRED objlist
            {
            pushFollow(FOLLOW_proplist_in_propquery276);
            proplist17=proplist();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_proplist.add(proplist17.getTree());
            PRED18=(Token)match(input,PRED,FOLLOW_PRED_in_propquery278); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_PRED.add(PRED18);

            pushFollow(FOLLOW_objlist_in_propquery280);
            objlist19=objlist();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_objlist.add(objlist19.getTree());
            if ( state.backtracking==0 ) {
              prde = true;
            }


            // AST REWRITE
            // elements: objlist, proplist
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 140:40: -> ^( PREDE ^( PROPLIST proplist ) ^( OBJLIST objlist ) )
            {
                // /home/hasdai/Documentos/sParser.g:140:43: ^( PREDE ^( PROPLIST proplist ) ^( OBJLIST objlist ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PREDE, "PREDE"), root_1);

                // /home/hasdai/Documentos/sParser.g:140:51: ^( PROPLIST proplist )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PROPLIST, "PROPLIST"), root_2);

                adaptor.addChild(root_2, stream_proplist.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // /home/hasdai/Documentos/sParser.g:140:72: ^( OBJLIST objlist )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OBJLIST, "OBJLIST"), root_2);

                adaptor.addChild(root_2, stream_objlist.nextTree());

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
    // $ANTLR end "propquery"

    public static class objlist_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "objlist"
    // /home/hasdai/Documentos/sParser.g:144:1: objlist : ( name DEL objlist | name );
    public final sParser.objlist_return objlist() throws RecognitionException {
        sParser.objlist_return retval = new sParser.objlist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL21=null;
        sParser.name_return name20 = null;

        sParser.objlist_return objlist22 = null;

        sParser.name_return name23 = null;


        Object DEL21_tree=null;

        try {
            // /home/hasdai/Documentos/sParser.g:145:1: ( name DEL objlist | name )
            int alt6=2;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:145:3: name DEL objlist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_objlist312);
                    name20=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name20.getTree());
                    if ( state.backtracking==0 ) {

                      		WordTag wt = lex.getWordTag((name20!=null?name20.val:null));
                      		if (wt.getLabel().compareTo("OBJ") == 0) {
                      			objs.add("?" + (name20!=null?name20.val:null) + " rdf:type " + wt.getType());
                      		} else if (wt.getLabel().compareTo("VAR") == 0){
                      			objs.add("?" + (name20!=null?name20.val:null) + " rdf:type " + "?" + (name20!=null?name20.val:null) + "_t");
                      		}

                    }
                    DEL21=(Token)match(input,DEL,FOLLOW_DEL_in_objlist317); if (state.failed) return retval;
                    pushFollow(FOLLOW_objlist_in_objlist320);
                    objlist22=objlist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, objlist22.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:154:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_objlist324);
                    name23=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name23.getTree());
                    if ( state.backtracking==0 ) {

                      		WordTag wt = lex.getWordTag((name23!=null?name23.val:null));
                      		if (wt.getLabel().compareTo("OBJ") == 0) {
                      			objs.add("?" + (name23!=null?name23.val:null) + " rdf:type " + wt.getType());
                      		} else if (wt.getLabel().compareTo("VAR") == 0){
                      			objs.add("?" + (name23!=null?name23.val:null) + " rdf:type " + "?" + (name23!=null?name23.val:null) + "_t");
                      		}

                    }

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
    // $ANTLR end "objlist"

    public static class name_return extends ParserRuleReturnScope {
        public String val;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "name"
    // /home/hasdai/Documentos/sParser.g:165:1: name returns [String val] : (v1= VAR (v2= VAR )+ -> ^( COMPNAME VAR VAR ) | VAR -> ^( VAR ) );
    public final sParser.name_return name() throws RecognitionException {
        sParser.name_return retval = new sParser.name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token v1=null;
        Token v2=null;
        Token VAR24=null;

        Object v1_tree=null;
        Object v2_tree=null;
        Object VAR24_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");

        try {
            // /home/hasdai/Documentos/sParser.g:166:1: (v1= VAR (v2= VAR )+ -> ^( COMPNAME VAR VAR ) | VAR -> ^( VAR ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==VAR) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==VAR) ) {
                    alt8=1;
                }
                else if ( (LA8_1==EOF||LA8_1==SIGE||LA8_1==PREC||(LA8_1>=PRED && LA8_1<=MODO)||LA8_1==DEL||(LA8_1>=RPAR && LA8_1<=SIGI)) ) {
                    alt8=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:166:3: v1= VAR (v2= VAR )+
                    {
                    v1=(Token)match(input,VAR,FOLLOW_VAR_in_name342); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(v1);

                    // /home/hasdai/Documentos/sParser.g:166:12: (v2= VAR )+
                    int cnt7=0;
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==VAR) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/sParser.g:0:0: v2= VAR
                    	    {
                    	    v2=(Token)match(input,VAR,FOLLOW_VAR_in_name346); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_VAR.add(v2);


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt7 >= 1 ) break loop7;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(7, input);
                                throw eee;
                        }
                        cnt7++;
                    } while (true);

                    if ( state.backtracking==0 ) {
                      retval.val = v1.getText() + " " + v2.getText(); retval.val = retval.val.trim();
                    }


                    // AST REWRITE
                    // elements: VAR, VAR
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 166:82: -> ^( COMPNAME VAR VAR )
                    {
                        // /home/hasdai/Documentos/sParser.g:166:85: ^( COMPNAME VAR VAR )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPNAME, "COMPNAME"), root_1);

                        adaptor.addChild(root_1, stream_VAR.nextNode());
                        adaptor.addChild(root_1, stream_VAR.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:167:3: VAR
                    {
                    VAR24=(Token)match(input,VAR,FOLLOW_VAR_in_name363); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR24);

                    if ( state.backtracking==0 ) {
                      retval.val = VAR24.getText(); retval.val = retval.val.trim();
                    }


                    // AST REWRITE
                    // elements: VAR
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 167:52: -> ^( VAR )
                    {
                        // /home/hasdai/Documentos/sParser.g:167:55: ^( VAR )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_VAR.nextNode(), root_1);

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
    // $ANTLR end "name"

    public static class sent_return extends ParserRuleReturnScope {
        public String name;
        public String value;
        public int typ;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sent"
    // /home/hasdai/Documentos/sParser.g:172:1: sent returns [String name, String value, int typ] : ( VAR SIGE val -> ^( ASIGN VAR val ) | v= name SIGE val -> ^( ASIGN name val ) | VAR SIGL val -> ^( COMPL VAR val ) | VAR SIGG val -> ^( COMPG VAR val ) | VAR SIGLE val -> ^( COMPLE VAR val ) | VAR SIGGE val -> ^( COMPGE VAR val ) );
    public final sParser.sent_return sent() throws RecognitionException {
        sParser.sent_return retval = new sParser.sent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token VAR25=null;
        Token SIGE26=null;
        Token SIGE28=null;
        Token VAR30=null;
        Token SIGL31=null;
        Token VAR33=null;
        Token SIGG34=null;
        Token VAR36=null;
        Token SIGLE37=null;
        Token VAR39=null;
        Token SIGGE40=null;
        sParser.name_return v = null;

        sParser.val_return val27 = null;

        sParser.val_return val29 = null;

        sParser.val_return val32 = null;

        sParser.val_return val35 = null;

        sParser.val_return val38 = null;

        sParser.val_return val41 = null;


        Object VAR25_tree=null;
        Object SIGE26_tree=null;
        Object SIGE28_tree=null;
        Object VAR30_tree=null;
        Object SIGL31_tree=null;
        Object VAR33_tree=null;
        Object SIGG34_tree=null;
        Object VAR36_tree=null;
        Object SIGLE37_tree=null;
        Object VAR39_tree=null;
        Object SIGGE40_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_SIGL=new RewriteRuleTokenStream(adaptor,"token SIGL");
        RewriteRuleTokenStream stream_SIGGE=new RewriteRuleTokenStream(adaptor,"token SIGGE");
        RewriteRuleTokenStream stream_SIGE=new RewriteRuleTokenStream(adaptor,"token SIGE");
        RewriteRuleTokenStream stream_SIGLE=new RewriteRuleTokenStream(adaptor,"token SIGLE");
        RewriteRuleTokenStream stream_SIGG=new RewriteRuleTokenStream(adaptor,"token SIGG");
        RewriteRuleSubtreeStream stream_val=new RewriteRuleSubtreeStream(adaptor,"rule val");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // /home/hasdai/Documentos/sParser.g:173:1: ( VAR SIGE val -> ^( ASIGN VAR val ) | v= name SIGE val -> ^( ASIGN name val ) | VAR SIGL val -> ^( COMPL VAR val ) | VAR SIGG val -> ^( COMPG VAR val ) | VAR SIGLE val -> ^( COMPLE VAR val ) | VAR SIGGE val -> ^( COMPGE VAR val ) )
            int alt9=6;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:173:3: VAR SIGE val
                    {
                    VAR25=(Token)match(input,VAR,FOLLOW_VAR_in_sent386); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR25);

                    SIGE26=(Token)match(input,SIGE,FOLLOW_SIGE_in_sent388); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGE.add(SIGE26);

                    pushFollow(FOLLOW_val_in_sent390);
                    val27=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val27.getTree());
                    if ( state.backtracking==0 ) {
                      retval.name = VAR25.getText(); retval.value = (val27!=null?val27.val:null); retval.typ = 1;
                    }


                    // AST REWRITE
                    // elements: VAR, val
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 173:71: -> ^( ASIGN VAR val )
                    {
                        // /home/hasdai/Documentos/sParser.g:173:74: ^( ASIGN VAR val )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ASIGN, "ASIGN"), root_1);

                        adaptor.addChild(root_1, stream_VAR.nextNode());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:174:3: v= name SIGE val
                    {
                    pushFollow(FOLLOW_name_in_sent408);
                    v=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(v.getTree());
                    SIGE28=(Token)match(input,SIGE,FOLLOW_SIGE_in_sent410); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGE.add(SIGE28);

                    pushFollow(FOLLOW_val_in_sent412);
                    val29=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val29.getTree());
                    if ( state.backtracking==0 ) {
                      retval.name = (v!=null?v.val:null); retval.value = (val29!=null?val29.val:null); retval.typ = 1;
                    }


                    // AST REWRITE
                    // elements: val, name
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 174:66: -> ^( ASIGN name val )
                    {
                        // /home/hasdai/Documentos/sParser.g:174:69: ^( ASIGN name val )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ASIGN, "ASIGN"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/sParser.g:175:3: VAR SIGL val
                    {
                    VAR30=(Token)match(input,VAR,FOLLOW_VAR_in_sent428); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR30);

                    SIGL31=(Token)match(input,SIGL,FOLLOW_SIGL_in_sent430); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGL.add(SIGL31);

                    pushFollow(FOLLOW_val_in_sent432);
                    val32=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val32.getTree());
                    if ( state.backtracking==0 ) {
                      retval.name = VAR30.getText(); retval.value = (val32!=null?val32.val:null); retval.typ = 2;
                    }


                    // AST REWRITE
                    // elements: VAR, val
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 175:71: -> ^( COMPL VAR val )
                    {
                        // /home/hasdai/Documentos/sParser.g:175:74: ^( COMPL VAR val )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPL, "COMPL"), root_1);

                        adaptor.addChild(root_1, stream_VAR.nextNode());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 4 :
                    // /home/hasdai/Documentos/sParser.g:176:3: VAR SIGG val
                    {
                    VAR33=(Token)match(input,VAR,FOLLOW_VAR_in_sent448); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR33);

                    SIGG34=(Token)match(input,SIGG,FOLLOW_SIGG_in_sent450); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGG.add(SIGG34);

                    pushFollow(FOLLOW_val_in_sent452);
                    val35=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val35.getTree());
                    if ( state.backtracking==0 ) {
                      retval.name = VAR33.getText(); retval.value = (val35!=null?val35.val:null); retval.typ = 3;
                    }


                    // AST REWRITE
                    // elements: val, VAR
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 176:71: -> ^( COMPG VAR val )
                    {
                        // /home/hasdai/Documentos/sParser.g:176:74: ^( COMPG VAR val )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPG, "COMPG"), root_1);

                        adaptor.addChild(root_1, stream_VAR.nextNode());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 5 :
                    // /home/hasdai/Documentos/sParser.g:177:3: VAR SIGLE val
                    {
                    VAR36=(Token)match(input,VAR,FOLLOW_VAR_in_sent468); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR36);

                    SIGLE37=(Token)match(input,SIGLE,FOLLOW_SIGLE_in_sent470); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGLE.add(SIGLE37);

                    pushFollow(FOLLOW_val_in_sent472);
                    val38=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val38.getTree());
                    if ( state.backtracking==0 ) {
                      retval.name = VAR36.getText(); retval.value = (val38!=null?val38.val:null); retval.typ = 4;
                    }


                    // AST REWRITE
                    // elements: val, VAR
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 177:72: -> ^( COMPLE VAR val )
                    {
                        // /home/hasdai/Documentos/sParser.g:177:75: ^( COMPLE VAR val )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPLE, "COMPLE"), root_1);

                        adaptor.addChild(root_1, stream_VAR.nextNode());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 6 :
                    // /home/hasdai/Documentos/sParser.g:178:3: VAR SIGGE val
                    {
                    VAR39=(Token)match(input,VAR,FOLLOW_VAR_in_sent488); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR39);

                    SIGGE40=(Token)match(input,SIGGE,FOLLOW_SIGGE_in_sent490); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGGE.add(SIGGE40);

                    pushFollow(FOLLOW_val_in_sent492);
                    val41=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val41.getTree());
                    if ( state.backtracking==0 ) {
                      retval.name = VAR39.getText(); retval.value = (val41!=null?val41.val:null); retval.typ = 5;
                    }


                    // AST REWRITE
                    // elements: val, VAR
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 178:72: -> ^( COMPGE VAR val )
                    {
                        // /home/hasdai/Documentos/sParser.g:178:75: ^( COMPGE VAR val )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPGE, "COMPGE"), root_1);

                        adaptor.addChild(root_1, stream_VAR.nextNode());
                        adaptor.addChild(root_1, stream_val.nextTree());

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
    // $ANTLR end "sent"

    public static class proplist_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "proplist"
    // /home/hasdai/Documentos/sParser.g:180:1: proplist : ( sent DEL proplist | name DEL proplist | sent | name );
    public final sParser.proplist_return proplist() throws RecognitionException {
        sParser.proplist_return retval = new sParser.proplist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL43=null;
        Token DEL46=null;
        sParser.sent_return sent42 = null;

        sParser.proplist_return proplist44 = null;

        sParser.name_return name45 = null;

        sParser.proplist_return proplist47 = null;

        sParser.sent_return sent48 = null;

        sParser.name_return name49 = null;


        Object DEL43_tree=null;
        Object DEL46_tree=null;

        try {
            // /home/hasdai/Documentos/sParser.g:181:1: ( sent DEL proplist | name DEL proplist | sent | name )
            int alt10=4;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:181:3: sent DEL proplist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_proplist513);
                    sent42=sent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sent42.getTree());
                    if ( state.backtracking==0 ) {

                      		WordTag wt = lex.getWordTag((sent42!=null?sent42.name:null));
                      		if (wt.getLabel().compareTo("PRO") == 0) {
                      			switch((sent42!=null?sent42.typ:0)) {
                      				case 1:
                      				{
                      					props.add(wt.getType() + " " + (sent42!=null?sent42.value:null));
                      				}break;
                      			}
                      		}

                    }
                    DEL43=(Token)match(input,DEL,FOLLOW_DEL_in_proplist518); if (state.failed) return retval;
                    pushFollow(FOLLOW_proplist_in_proplist521);
                    proplist44=proplist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, proplist44.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:193:3: name DEL proplist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_proplist525);
                    name45=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name45.getTree());
                    if ( state.backtracking==0 ) {

                      		WordTag wt = lex.getWordTag((name45!=null?name45.val:null));
                      		if (wt.getLabel().compareTo("PRO") == 0) {
                      			props.add(wt.getType() + " ?" + (name45!=null?name45.val:null).replace(' ','_'));
                      		}

                    }
                    DEL46=(Token)match(input,DEL,FOLLOW_DEL_in_proplist530); if (state.failed) return retval;
                    pushFollow(FOLLOW_proplist_in_proplist533);
                    proplist47=proplist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, proplist47.getTree());

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/sParser.g:200:3: sent
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_proplist537);
                    sent48=sent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sent48.getTree());
                    if ( state.backtracking==0 ) {

                      		WordTag wt = lex.getWordTag((sent48!=null?sent48.name:null));
                      		if (wt.getLabel().compareTo("PRO") == 0) {
                      			switch((sent48!=null?sent48.typ:0)) {
                      				case 1:
                      				{
                      					props.add(wt.getType() + " " + (sent48!=null?sent48.value:null));
                      				}break;
                      			}
                      		}

                    }

                    }
                    break;
                case 4 :
                    // /home/hasdai/Documentos/sParser.g:212:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_proplist544);
                    name49=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name49.getTree());
                    if ( state.backtracking==0 ) {

                      		WordTag wt = lex.getWordTag((name49!=null?name49.val:null));
                      		if (wt.getLabel().compareTo("PRO") == 0) {
                      			props.add(wt.getType() + " ?" + (name49!=null?name49.val:null).replace(' ','_'));
                      		}

                    }

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
    // $ANTLR end "proplist"

    public static class val_return extends ParserRuleReturnScope {
        public String val;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "val"
    // /home/hasdai/Documentos/sParser.g:221:1: val returns [String val] : ( NUM | LIT | BOL );
    public final sParser.val_return val() throws RecognitionException {
        sParser.val_return retval = new sParser.val_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUM50=null;
        Token LIT51=null;
        Token BOL52=null;

        Object NUM50_tree=null;
        Object LIT51_tree=null;
        Object BOL52_tree=null;

        try {
            // /home/hasdai/Documentos/sParser.g:222:1: ( NUM | LIT | BOL )
            int alt11=3;
            switch ( input.LA(1) ) {
            case NUM:
                {
                alt11=1;
                }
                break;
            case LIT:
                {
                alt11=2;
                }
                break;
            case BOL:
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
                    // /home/hasdai/Documentos/sParser.g:222:4: NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    NUM50=(Token)match(input,NUM,FOLLOW_NUM_in_val561); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM50_tree = (Object)adaptor.create(NUM50);
                    adaptor.addChild(root_0, NUM50_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.val = NUM50.getText();
                    }

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:223:3: LIT
                    {
                    root_0 = (Object)adaptor.nil();

                    LIT51=(Token)match(input,LIT,FOLLOW_LIT_in_val567); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LIT51_tree = (Object)adaptor.create(LIT51);
                    adaptor.addChild(root_0, LIT51_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.val = LIT51.getText();
                    }

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/sParser.g:224:3: BOL
                    {
                    root_0 = (Object)adaptor.nil();

                    BOL52=(Token)match(input,BOL,FOLLOW_BOL_in_val573); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOL52_tree = (Object)adaptor.create(BOL52);
                    adaptor.addChild(root_0, BOL52_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.val = BOL52.getText();
                    }

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
    // $ANTLR end "val"

    public static class modifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "modifier"
    // /home/hasdai/Documentos/sParser.g:227:1: modifier : ( ordterm ( offsetterm )? | offsetterm );
    public final sParser.modifier_return modifier() throws RecognitionException {
        sParser.modifier_return retval = new sParser.modifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        sParser.ordterm_return ordterm53 = null;

        sParser.offsetterm_return offsetterm54 = null;

        sParser.offsetterm_return offsetterm55 = null;



        try {
            // /home/hasdai/Documentos/sParser.g:228:1: ( ordterm ( offsetterm )? | offsetterm )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==MODO) ) {
                alt13=1;
            }
            else if ( (LA13_0==MODE) ) {
                alt13=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:228:3: ordterm ( offsetterm )?
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ordterm_in_modifier585);
                    ordterm53=ordterm();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ordterm53.getTree());
                    // /home/hasdai/Documentos/sParser.g:228:11: ( offsetterm )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==MODE) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // /home/hasdai/Documentos/sParser.g:0:0: offsetterm
                            {
                            pushFollow(FOLLOW_offsetterm_in_modifier587);
                            offsetterm54=offsetterm();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, offsetterm54.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:229:3: offsetterm
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_offsetterm_in_modifier592);
                    offsetterm55=offsetterm();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, offsetterm55.getTree());

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
    // $ANTLR end "modifier"

    public static class offsetterm_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "offsetterm"
    // /home/hasdai/Documentos/sParser.g:232:1: offsetterm : MODE NUM -> ^( OFFSET NUM ) ;
    public final sParser.offsetterm_return offsetterm() throws RecognitionException {
        sParser.offsetterm_return retval = new sParser.offsetterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MODE56=null;
        Token NUM57=null;

        Object MODE56_tree=null;
        Object NUM57_tree=null;
        RewriteRuleTokenStream stream_MODE=new RewriteRuleTokenStream(adaptor,"token MODE");
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");

        try {
            // /home/hasdai/Documentos/sParser.g:233:1: ( MODE NUM -> ^( OFFSET NUM ) )
            // /home/hasdai/Documentos/sParser.g:233:3: MODE NUM
            {
            MODE56=(Token)match(input,MODE,FOLLOW_MODE_in_offsetterm602); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODE.add(MODE56);

            NUM57=(Token)match(input,NUM,FOLLOW_NUM_in_offsetterm604); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_NUM.add(NUM57);

            if ( state.backtracking==0 ) {
              mod = "OFFSET " + NUM57.getText() + " ";
            }


            // AST REWRITE
            // elements: NUM
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 233:54: -> ^( OFFSET NUM )
            {
                // /home/hasdai/Documentos/sParser.g:233:57: ^( OFFSET NUM )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(OFFSET, "OFFSET"), root_1);

                adaptor.addChild(root_1, stream_NUM.nextNode());

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
    // $ANTLR end "offsetterm"

    public static class ordterm_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ordterm"
    // /home/hasdai/Documentos/sParser.g:236:1: ordterm : MODO LPAR ordlist RPAR -> ^( ORDER ordlist ) ;
    public final sParser.ordterm_return ordterm() throws RecognitionException {
        sParser.ordterm_return retval = new sParser.ordterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MODO58=null;
        Token LPAR59=null;
        Token RPAR61=null;
        sParser.ordlist_return ordlist60 = null;


        Object MODO58_tree=null;
        Object LPAR59_tree=null;
        Object RPAR61_tree=null;
        RewriteRuleTokenStream stream_MODO=new RewriteRuleTokenStream(adaptor,"token MODO");
        RewriteRuleTokenStream stream_RPAR=new RewriteRuleTokenStream(adaptor,"token RPAR");
        RewriteRuleTokenStream stream_LPAR=new RewriteRuleTokenStream(adaptor,"token LPAR");
        RewriteRuleSubtreeStream stream_ordlist=new RewriteRuleSubtreeStream(adaptor,"rule ordlist");
        try {
            // /home/hasdai/Documentos/sParser.g:237:1: ( MODO LPAR ordlist RPAR -> ^( ORDER ordlist ) )
            // /home/hasdai/Documentos/sParser.g:237:3: MODO LPAR ordlist RPAR
            {
            MODO58=(Token)match(input,MODO,FOLLOW_MODO_in_ordterm624); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODO.add(MODO58);

            LPAR59=(Token)match(input,LPAR,FOLLOW_LPAR_in_ordterm626); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_LPAR.add(LPAR59);

            pushFollow(FOLLOW_ordlist_in_ordterm628);
            ordlist60=ordlist();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ordlist.add(ordlist60.getTree());
            if ( state.backtracking==0 ) {
              mod = "ORDER BY " + (ordlist60!=null?ordlist60.terms:null).trim().replace(' ', '_');
            }
            RPAR61=(Token)match(input,RPAR,FOLLOW_RPAR_in_ordterm632); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_RPAR.add(RPAR61);



            // AST REWRITE
            // elements: ordlist
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 237:89: -> ^( ORDER ordlist )
            {
                // /home/hasdai/Documentos/sParser.g:237:92: ^( ORDER ordlist )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ORDER, "ORDER"), root_1);

                adaptor.addChild(root_1, stream_ordlist.nextTree());

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
    // $ANTLR end "ordterm"

    public static class ordlist_return extends ParserRuleReturnScope {
        public String terms;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ordlist"
    // /home/hasdai/Documentos/sParser.g:240:1: ordlist returns [String terms] : ( name DEL v= ordlist | name );
    public final sParser.ordlist_return ordlist() throws RecognitionException {
        sParser.ordlist_return retval = new sParser.ordlist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL63=null;
        sParser.ordlist_return v = null;

        sParser.name_return name62 = null;

        sParser.name_return name64 = null;


        Object DEL63_tree=null;

        try {
            // /home/hasdai/Documentos/sParser.g:241:1: ( name DEL v= ordlist | name )
            int alt14=2;
            alt14 = dfa14.predict(input);
            switch (alt14) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:241:3: name DEL v= ordlist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_ordlist653);
                    name62=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name62.getTree());
                    if ( state.backtracking==0 ) {
                      retval.terms = "?" + (name62!=null?name62.val:null) + " "; retval.terms.trim();
                    }
                    DEL63=(Token)match(input,DEL,FOLLOW_DEL_in_ordlist657); if (state.failed) return retval;
                    pushFollow(FOLLOW_ordlist_in_ordlist662);
                    v=ordlist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, v.getTree());
                    if ( state.backtracking==0 ) {
                      retval.terms = retval.terms + (v!=null?v.terms:null); retval.terms.trim();
                    }

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:242:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_ordlist668);
                    name64=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name64.getTree());
                    if ( state.backtracking==0 ) {
                      retval.terms = "?" + (name64!=null?name64.val:null) + " "; retval.terms.trim();
                    }

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
    // $ANTLR end "ordlist"

    // $ANTLR start synpred1_sParser
    public final void synpred1_sParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/sParser.g:111:3: ( askquery )
        // /home/hasdai/Documentos/sParser.g:111:3: askquery
        {
        pushFollow(FOLLOW_askquery_in_synpred1_sParser121);
        askquery();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_sParser

    // $ANTLR start synpred4_sParser
    public final void synpred4_sParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/sParser.g:130:3: ( objquery )
        // /home/hasdai/Documentos/sParser.g:130:3: objquery
        {
        pushFollow(FOLLOW_objquery_in_synpred4_sParser216);
        objquery();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_sParser

    // $ANTLR start synpred5_sParser
    public final void synpred5_sParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/sParser.g:135:3: ( objlist PREC proplist )
        // /home/hasdai/Documentos/sParser.g:135:3: objlist PREC proplist
        {
        pushFollow(FOLLOW_objlist_in_synpred5_sParser230);
        objlist();

        state._fsp--;
        if (state.failed) return ;
        match(input,PREC,FOLLOW_PREC_in_synpred5_sParser232); if (state.failed) return ;
        pushFollow(FOLLOW_proplist_in_synpred5_sParser234);
        proplist();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_sParser

    // $ANTLR start synpred9_sParser
    public final void synpred9_sParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/sParser.g:173:3: ( VAR SIGE val )
        // /home/hasdai/Documentos/sParser.g:173:3: VAR SIGE val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred9_sParser386); if (state.failed) return ;
        match(input,SIGE,FOLLOW_SIGE_in_synpred9_sParser388); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred9_sParser390);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_sParser

    // $ANTLR start synpred10_sParser
    public final void synpred10_sParser_fragment() throws RecognitionException {
        sParser.name_return v = null;


        // /home/hasdai/Documentos/sParser.g:174:3: (v= name SIGE val )
        // /home/hasdai/Documentos/sParser.g:174:3: v= name SIGE val
        {
        pushFollow(FOLLOW_name_in_synpred10_sParser408);
        v=name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGE,FOLLOW_SIGE_in_synpred10_sParser410); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred10_sParser412);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_sParser

    // Delegated rules

    public final boolean synpred5_sParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_sParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_sParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_sParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_sParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_sParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_sParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_sParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_sParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_sParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA6 dfa6 = new DFA6(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA10 dfa10 = new DFA10(this);
    protected DFA14 dfa14 = new DFA14(this);
    static final String DFA6_eotS =
        "\5\uffff";
    static final String DFA6_eofS =
        "\1\uffff\1\2\1\uffff\1\2\1\uffff";
    static final String DFA6_minS =
        "\1\24\1\13\1\uffff\1\13\1\uffff";
    static final String DFA6_maxS =
        "\1\24\1\30\1\uffff\1\30\1\uffff";
    static final String DFA6_acceptS =
        "\2\uffff\1\2\1\uffff\1\1";
    static final String DFA6_specialS =
        "\5\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\1",
            "\1\2\2\uffff\2\2\4\uffff\1\3\1\4\2\uffff\1\2",
            "",
            "\1\2\2\uffff\2\2\4\uffff\1\3\1\4\2\uffff\1\2",
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
            return "144:1: objlist : ( name DEL objlist | name );";
        }
    }
    static final String DFA9_eotS =
        "\14\uffff";
    static final String DFA9_eofS =
        "\14\uffff";
    static final String DFA9_minS =
        "\1\24\1\6\1\20\5\uffff\3\0\1\uffff";
    static final String DFA9_maxS =
        "\2\24\1\22\5\uffff\3\0\1\uffff";
    static final String DFA9_acceptS =
        "\3\uffff\1\3\1\4\1\5\1\6\1\2\3\uffff\1\1";
    static final String DFA9_specialS =
        "\10\uffff\1\2\1\0\1\1\1\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\1",
            "\1\3\1\4\1\2\1\5\1\6\11\uffff\1\7",
            "\1\12\1\10\1\11",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            ""
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "172:1: sent returns [String name, String value, int typ] : ( VAR SIGE val -> ^( ASIGN VAR val ) | v= name SIGE val -> ^( ASIGN name val ) | VAR SIGL val -> ^( COMPL VAR val ) | VAR SIGG val -> ^( COMPG VAR val ) | VAR SIGLE val -> ^( COMPLE VAR val ) | VAR SIGGE val -> ^( COMPGE VAR val ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA9_9 = input.LA(1);


                        int index9_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_sParser()) ) {s = 11;}

                        else if ( (synpred10_sParser()) ) {s = 7;}


                        input.seek(index9_9);
                        if ( s>=0 ) return s;
                        break;
                    case 1 :
                        int LA9_10 = input.LA(1);


                        int index9_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_sParser()) ) {s = 11;}

                        else if ( (synpred10_sParser()) ) {s = 7;}


                        input.seek(index9_10);
                        if ( s>=0 ) return s;
                        break;
                    case 2 :
                        int LA9_8 = input.LA(1);


                        int index9_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_sParser()) ) {s = 11;}

                        else if ( (synpred10_sParser()) ) {s = 7;}


                        input.seek(index9_8);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 9, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA10_eotS =
        "\37\uffff";
    static final String DFA10_eofS =
        "\1\uffff\1\7\6\uffff\1\7\1\uffff\17\32\3\uffff\3\32";
    static final String DFA10_minS =
        "\1\24\1\6\5\20\1\uffff\1\10\1\uffff\17\15\1\20\2\uffff\3\15";
    static final String DFA10_maxS =
        "\1\24\1\30\5\22\1\uffff\1\30\1\uffff\17\30\1\22\2\uffff\3\30";
    static final String DFA10_acceptS =
        "\7\uffff\1\4\1\uffff\1\2\20\uffff\1\3\1\1\3\uffff";
    static final String DFA10_specialS =
        "\37\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\1",
            "\1\3\1\4\1\2\1\5\1\6\2\uffff\3\7\4\uffff\1\10\1\11\2\uffff"+
            "\1\7",
            "\1\14\1\12\1\13",
            "\1\17\1\15\1\16",
            "\1\22\1\20\1\21",
            "\1\25\1\23\1\24",
            "\1\30\1\26\1\27",
            "",
            "\1\31\4\uffff\3\7\4\uffff\1\10\1\11\2\uffff\1\7",
            "",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\1\36\1\34\1\35",
            "",
            "",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32",
            "\3\32\5\uffff\1\33\2\uffff\1\32"
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "180:1: proplist : ( sent DEL proplist | name DEL proplist | sent | name );";
        }
    }
    static final String DFA14_eotS =
        "\5\uffff";
    static final String DFA14_eofS =
        "\1\uffff\2\4\2\uffff";
    static final String DFA14_minS =
        "\3\24\2\uffff";
    static final String DFA14_maxS =
        "\1\24\2\27\2\uffff";
    static final String DFA14_acceptS =
        "\3\uffff\1\1\1\2";
    static final String DFA14_specialS =
        "\5\uffff}>";
    static final String[] DFA14_transitionS = {
            "\1\1",
            "\1\2\1\3\1\uffff\1\4",
            "\1\2\1\3\1\uffff\1\4",
            "",
            ""
    };

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
        int numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
    }

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }
        public String getDescription() {
            return "240:1: ordlist returns [String terms] : ( name DEL v= ordlist | name );";
        }
    }


    public static final BitSet FOLLOW_askquery_in_squery121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_selectquery_in_squery127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_limiter_in_selectquery140 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_query_in_selectquery143 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_modifier_in_selectquery145 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_selectquery148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_query_in_askquery175 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_SIGI_in_askquery177 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_askquery179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_limiter196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objquery_in_query216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propquery_in_query220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objlist_in_objquery230 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PREC_in_objquery232 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_proplist_in_objquery234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objlist_in_objquery258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_proplist_in_propquery276 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_PRED_in_propquery278 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_objlist_in_propquery280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_objlist312 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_DEL_in_objlist317 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_objlist_in_objlist320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_objlist324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_name342 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_VAR_in_name346 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_VAR_in_name363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent386 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SIGE_in_sent388 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent408 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SIGE_in_sent410 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent428 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_sent430 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent448 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_sent450 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent468 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_sent470 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent488 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_sent490 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_proplist513 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_DEL_in_proplist518 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_proplist_in_proplist521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_proplist525 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_DEL_in_proplist530 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_proplist_in_proplist533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_proplist537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_proplist544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_val561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LIT_in_val567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOL_in_val573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ordterm_in_modifier585 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_offsetterm_in_modifier587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_offsetterm_in_modifier592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODE_in_offsetterm602 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_NUM_in_offsetterm604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODO_in_ordterm624 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_LPAR_in_ordterm626 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_ordlist_in_ordterm628 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_RPAR_in_ordterm632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_ordlist653 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_DEL_in_ordlist657 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_ordlist_in_ordlist662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_ordlist668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_askquery_in_synpred1_sParser121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objquery_in_synpred4_sParser216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objlist_in_synpred5_sParser230 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PREC_in_synpred5_sParser232 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_proplist_in_synpred5_sParser234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred9_sParser386 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SIGE_in_synpred9_sParser388 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_synpred9_sParser390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred10_sParser408 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SIGE_in_synpred10_sParser410 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_synpred10_sParser412 = new BitSet(new long[]{0x0000000000000002L});
}