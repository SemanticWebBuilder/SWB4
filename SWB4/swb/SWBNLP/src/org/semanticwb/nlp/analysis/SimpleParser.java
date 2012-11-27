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
 * An antlr natural language SparQl query parser. Most of the code for this
 * class and nested classes was generated automatically by antlr.
 * Visit http://www.antlr.org for details about each undocumented class element.
 * <p>
 * Un analizador de consultas SparQl en lenguaje natural generado por antlr.
 * La mayoría del código de esta clase y las clases anidadas fue generado
 * automáticamente por antlr. Visite http://www.antlr.org para obtener detalles
 * acerca de los elementos de esta clase que no están documentados.
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
// $ANTLR 3.1.2 /home/hasdai/Documentos/SimpleParser.g 2009-03-24 17:29:36

import org.antlr.runtime.*;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.*;

public class SimpleParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "WHITESPACE", "SIGN", "SIGL", "SIGG", "SIGE", "SIGLE", "SIGGE", "PREC", "MODT", "PRED", "MODE", "MODO", "BOL", "NUM", "LIT", "ORDOP", "VAR", "LPAR", "RPAR", "LBRK", "RBRK", "DEL", "SIGI", "LIMIT", "SELECT", "MOD", "OBJLIST", "PROPLIST", "ASIGN", "SENTENCE", "COMPL", "COMPG", "COMPLE", "COMPGE", "PRECON", "PREDE", "OFFSET", "ORDER", "COMPNAME", "ASKQUERY", "MODTO", "NAME"
    };
    public static final int SIGN=5;
    public static final int SIGI=26;
    public static final int SIGL=6;
    public static final int SIGE=8;
    public static final int ORDER=41;
    public static final int LIMIT=27;
    public static final int MOD=29;
    public static final int ASKQUERY=43;
    public static final int SIGG=7;
    public static final int DEL=25;
    public static final int ORDOP=19;
    public static final int BOL=16;
    public static final int MODTO=44;
    public static final int OBJLIST=30;
    public static final int COMPL=34;
    public static final int EOF=-1;
    public static final int COMPG=35;
    public static final int NAME=45;
    public static final int LPAR=21;
    public static final int SENTENCE=33;
    public static final int COMPLE=36;
    public static final int OFFSET=40;
    public static final int MODT=12;
    public static final int PROPLIST=31;
    public static final int VAR=20;
    public static final int MODO=15;
    public static final int SELECT=28;
    public static final int RBRK=24;
    public static final int MODE=14;
    public static final int PREDE=39;
    public static final int COMPGE=37;
    public static final int PRECON=38;
    public static final int PREC=11;
    public static final int LBRK=23;
    public static final int PRED=13;
    public static final int SIGGE=10;
    public static final int LIT=18;
    public static final int WHITESPACE=4;
    public static final int NUM=17;
    public static final int SIGLE=9;
    public static final int COMPNAME=42;
    public static final int ASIGN=32;
    public static final int RPAR=22;

    // delegates
    // delegators


        public SimpleParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public SimpleParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);

        }

    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return SimpleParser.tokenNames; }
    public String getGrammarFileName() { return "/home/hasdai/Documentos/sParser.g"; }


    	private int errCount = 0;
    	private boolean isCompound = false;

    	public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
            	errCount++;
        	}

    	public int getErrorCount() {
            	return errCount;
        	}

        	public boolean isCompoundQuery() {
        		return isCompound;
        	}


    public static class squery_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "squery"
    // /home/hasdai/Documentos/SimpleParser.g:31:1: squery : selectquery ;
    public final SimpleParser.squery_return squery() throws RecognitionException {
        SimpleParser.squery_return retval = new SimpleParser.squery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SimpleParser.selectquery_return selectquery1 = null;



        try {
            // /home/hasdai/Documentos/SimpleParser.g:32:1: ( selectquery )
            // /home/hasdai/Documentos/SimpleParser.g:32:3: selectquery
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_selectquery_in_squery111);
            selectquery1=selectquery();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, selectquery1.getTree());

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
    // /home/hasdai/Documentos/SimpleParser.g:36:1: selectquery : ( limiter )? query ( modifier )? EOF -> ^( SELECT ( limiter )? query ( modifier )? ) ;
    public final SimpleParser.selectquery_return selectquery() throws RecognitionException {
        SimpleParser.selectquery_return retval = new SimpleParser.selectquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF5=null;
        SimpleParser.limiter_return limiter2 = null;

        SimpleParser.query_return query3 = null;

        SimpleParser.modifier_return modifier4 = null;


        Object EOF5_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_limiter=new RewriteRuleSubtreeStream(adaptor,"rule limiter");
        RewriteRuleSubtreeStream stream_modifier=new RewriteRuleSubtreeStream(adaptor,"rule modifier");
        RewriteRuleSubtreeStream stream_query=new RewriteRuleSubtreeStream(adaptor,"rule query");
        try {
            // /home/hasdai/Documentos/SimpleParser.g:37:1: ( ( limiter )? query ( modifier )? EOF -> ^( SELECT ( limiter )? query ( modifier )? ) )
            // /home/hasdai/Documentos/SimpleParser.g:37:3: ( limiter )? query ( modifier )? EOF
            {
            // /home/hasdai/Documentos/SimpleParser.g:37:3: ( limiter )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==NUM) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleParser.g:0:0: limiter
                    {
                    pushFollow(FOLLOW_limiter_in_selectquery121);
                    limiter2=limiter();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_limiter.add(limiter2.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_query_in_selectquery124);
            query3=query();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_query.add(query3.getTree());
            // /home/hasdai/Documentos/SimpleParser.g:37:18: ( modifier )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0>=MODE && LA2_0<=MODO)) ) {
                alt2=1;
            }
            else if ( (LA2_0==EOF) ) {
                int LA2_2 = input.LA(2);

                if ( (synpred2_sParser()) ) {
                    alt2=1;
                }
            }
            switch (alt2) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleParser.g:0:0: modifier
                    {
                    pushFollow(FOLLOW_modifier_in_selectquery126);
                    modifier4=modifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_modifier.add(modifier4.getTree());

                    }
                    break;

            }

            EOF5=(Token)match(input,EOF,FOLLOW_EOF_in_selectquery129); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_EOF.add(EOF5);



            // AST REWRITE
            // elements: query, modifier, limiter
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 37:32: -> ^( SELECT ( limiter )? query ( modifier )? )
            {
                // /home/hasdai/Documentos/SimpleParser.g:37:35: ^( SELECT ( limiter )? query ( modifier )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECT, "SELECT"), root_1);

                // /home/hasdai/Documentos/SimpleParser.g:37:44: ( limiter )?
                if ( stream_limiter.hasNext() ) {
                    adaptor.addChild(root_1, stream_limiter.nextTree());

                }
                stream_limiter.reset();
                adaptor.addChild(root_1, stream_query.nextTree());
                // /home/hasdai/Documentos/SimpleParser.g:37:59: ( modifier )?
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

    public static class limiter_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "limiter"
    // /home/hasdai/Documentos/SimpleParser.g:44:1: limiter : NUM -> ^( LIMIT NUM ) ;
    public final SimpleParser.limiter_return limiter() throws RecognitionException {
        SimpleParser.limiter_return retval = new SimpleParser.limiter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUM6=null;

        Object NUM6_tree=null;
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");

        try {
            // /home/hasdai/Documentos/SimpleParser.g:45:1: ( NUM -> ^( LIMIT NUM ) )
            // /home/hasdai/Documentos/SimpleParser.g:45:3: NUM
            {
            NUM6=(Token)match(input,NUM,FOLLOW_NUM_in_limiter155); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_NUM.add(NUM6);



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
            // 45:7: -> ^( LIMIT NUM )
            {
                // /home/hasdai/Documentos/SimpleParser.g:45:10: ^( LIMIT NUM )
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
    // /home/hasdai/Documentos/SimpleParser.g:48:1: query : ( objquery | propquery );
    public final SimpleParser.query_return query() throws RecognitionException {
        SimpleParser.query_return retval = new SimpleParser.query_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SimpleParser.objquery_return objquery7 = null;

        SimpleParser.propquery_return propquery8 = null;



        try {
            // /home/hasdai/Documentos/SimpleParser.g:49:1: ( objquery | propquery )
            int alt3=2;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleParser.g:49:3: objquery
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_objquery_in_query172);
                    objquery7=objquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, objquery7.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/SimpleParser.g:50:3: propquery
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_propquery_in_query176);
                    propquery8=propquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, propquery8.getTree());
                    if ( state.backtracking==0 ) {
                      isCompound = true;
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
    // $ANTLR end "query"

    public static class objquery_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "objquery"
    // /home/hasdai/Documentos/SimpleParser.g:53:1: objquery : ( name PREC proplist -> ^( name ^( PRECON proplist ) ) | name );
    public final SimpleParser.objquery_return objquery() throws RecognitionException {
        SimpleParser.objquery_return retval = new SimpleParser.objquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PREC10=null;
        SimpleParser.name_return name9 = null;

        SimpleParser.proplist_return proplist11 = null;

        SimpleParser.name_return name12 = null;


        Object PREC10_tree=null;
        RewriteRuleTokenStream stream_PREC=new RewriteRuleTokenStream(adaptor,"token PREC");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_proplist=new RewriteRuleSubtreeStream(adaptor,"rule proplist");
        try {
            // /home/hasdai/Documentos/SimpleParser.g:54:1: ( name PREC proplist -> ^( name ^( PRECON proplist ) ) | name )
            int alt4=2;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleParser.g:54:3: name PREC proplist
                    {
                    pushFollow(FOLLOW_name_in_objquery187);
                    name9=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name9.getTree());
                    PREC10=(Token)match(input,PREC,FOLLOW_PREC_in_objquery189); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PREC.add(PREC10);

                    pushFollow(FOLLOW_proplist_in_objquery191);
                    proplist11=proplist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_proplist.add(proplist11.getTree());


                    // AST REWRITE
                    // elements: name, proplist
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 54:22: -> ^( name ^( PRECON proplist ) )
                    {
                        // /home/hasdai/Documentos/SimpleParser.g:54:25: ^( name ^( PRECON proplist ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_name.nextNode(), root_1);

                        // /home/hasdai/Documentos/SimpleParser.g:54:32: ^( PRECON proplist )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PRECON, "PRECON"), root_2);

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
                    // /home/hasdai/Documentos/SimpleParser.g:55:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_objquery207);
                    name12=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name12.getTree());

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
    // /home/hasdai/Documentos/SimpleParser.g:58:1: propquery : v1= proplist PRED name ( PREC v2= proplist )? -> ^( name ^( PREDE $v1) ( ^( PRECON $v2) )? ) ;
    public final SimpleParser.propquery_return propquery() throws RecognitionException {
        SimpleParser.propquery_return retval = new SimpleParser.propquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRED13=null;
        Token PREC15=null;
        SimpleParser.proplist_return v1 = null;

        SimpleParser.proplist_return v2 = null;

        SimpleParser.name_return name14 = null;


        Object PRED13_tree=null;
        Object PREC15_tree=null;
        RewriteRuleTokenStream stream_PREC=new RewriteRuleTokenStream(adaptor,"token PREC");
        RewriteRuleTokenStream stream_PRED=new RewriteRuleTokenStream(adaptor,"token PRED");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_proplist=new RewriteRuleSubtreeStream(adaptor,"rule proplist");
        try {
            // /home/hasdai/Documentos/SimpleParser.g:59:1: (v1= proplist PRED name ( PREC v2= proplist )? -> ^( name ^( PREDE $v1) ( ^( PRECON $v2) )? ) )
            // /home/hasdai/Documentos/SimpleParser.g:59:3: v1= proplist PRED name ( PREC v2= proplist )?
            {
            pushFollow(FOLLOW_proplist_in_propquery218);
            v1=proplist();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_proplist.add(v1.getTree());
            PRED13=(Token)match(input,PRED,FOLLOW_PRED_in_propquery220); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_PRED.add(PRED13);

            pushFollow(FOLLOW_name_in_propquery222);
            name14=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name14.getTree());
            // /home/hasdai/Documentos/SimpleParser.g:59:25: ( PREC v2= proplist )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==PREC) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleParser.g:59:26: PREC v2= proplist
                    {
                    PREC15=(Token)match(input,PREC,FOLLOW_PREC_in_propquery225); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PREC.add(PREC15);

                    pushFollow(FOLLOW_proplist_in_propquery229);
                    v2=proplist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_proplist.add(v2.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: v2, v1, name
            // token labels:
            // rule labels: v1, retval, v2
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_v1=new RewriteRuleSubtreeStream(adaptor,"rule v1",v1!=null?v1.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_v2=new RewriteRuleSubtreeStream(adaptor,"rule v2",v2!=null?v2.tree:null);

            root_0 = (Object)adaptor.nil();
            // 59:45: -> ^( name ^( PREDE $v1) ( ^( PRECON $v2) )? )
            {
                // /home/hasdai/Documentos/SimpleParser.g:59:48: ^( name ^( PREDE $v1) ( ^( PRECON $v2) )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_name.nextNode(), root_1);

                // /home/hasdai/Documentos/SimpleParser.g:59:55: ^( PREDE $v1)
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PREDE, "PREDE"), root_2);

                adaptor.addChild(root_2, stream_v1.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // /home/hasdai/Documentos/SimpleParser.g:59:68: ( ^( PRECON $v2) )?
                if ( stream_v2.hasNext() ) {
                    // /home/hasdai/Documentos/SimpleParser.g:59:68: ^( PRECON $v2)
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PRECON, "PRECON"), root_2);

                    adaptor.addChild(root_2, stream_v2.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_v2.reset();

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

    public static class name_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "name"
    // /home/hasdai/Documentos/SimpleParser.g:62:1: name : (v1= VAR (v2= VAR )+ -> ^( COMPNAME $v1 $v2) | LBRK v1= VAR (v2= VAR )+ RBRK -> ^( COMPNAME $v1 $v2) | VAR );
    public final SimpleParser.name_return name() throws RecognitionException {
        SimpleParser.name_return retval = new SimpleParser.name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token v1=null;
        Token v2=null;
        Token LBRK16=null;
        Token RBRK17=null;
        Token VAR18=null;

        Object v1_tree=null;
        Object v2_tree=null;
        Object LBRK16_tree=null;
        Object RBRK17_tree=null;
        Object VAR18_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_RBRK=new RewriteRuleTokenStream(adaptor,"token RBRK");
        RewriteRuleTokenStream stream_LBRK=new RewriteRuleTokenStream(adaptor,"token LBRK");

        try {
            // /home/hasdai/Documentos/SimpleParser.g:63:1: (v1= VAR (v2= VAR )+ -> ^( COMPNAME $v1 $v2) | LBRK v1= VAR (v2= VAR )+ RBRK -> ^( COMPNAME $v1 $v2) | VAR )
            int alt8=3;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==VAR) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==VAR) ) {
                    alt8=1;
                }
                else if ( (LA8_1==EOF||(LA8_1>=SIGL && LA8_1<=PREC)||(LA8_1>=PRED && LA8_1<=MODO)||LA8_1==RPAR||LA8_1==DEL) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA8_0==LBRK) ) {
                alt8=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleParser.g:63:3: v1= VAR (v2= VAR )+
                    {
                    v1=(Token)match(input,VAR,FOLLOW_VAR_in_name263); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(v1);

                    // /home/hasdai/Documentos/SimpleParser.g:63:12: (v2= VAR )+
                    int cnt6=0;
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==VAR) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/SimpleParser.g:0:0: v2= VAR
                    	    {
                    	    v2=(Token)match(input,VAR,FOLLOW_VAR_in_name267); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_VAR.add(v2);


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt6 >= 1 ) break loop6;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(6, input);
                                throw eee;
                        }
                        cnt6++;
                    } while (true);



                    // AST REWRITE
                    // elements: v2, v1
                    // token labels: v1, v2
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_v1=new RewriteRuleTokenStream(adaptor,"token v1",v1);
                    RewriteRuleTokenStream stream_v2=new RewriteRuleTokenStream(adaptor,"token v2",v2);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 63:18: -> ^( COMPNAME $v1 $v2)
                    {
                        // /home/hasdai/Documentos/SimpleParser.g:63:21: ^( COMPNAME $v1 $v2)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPNAME, "COMPNAME"), root_1);

                        adaptor.addChild(root_1, stream_v1.nextNode());
                        adaptor.addChild(root_1, stream_v2.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/SimpleParser.g:64:3: LBRK v1= VAR (v2= VAR )+ RBRK
                    {
                    LBRK16=(Token)match(input,LBRK,FOLLOW_LBRK_in_name284); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_LBRK.add(LBRK16);

                    v1=(Token)match(input,VAR,FOLLOW_VAR_in_name288); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(v1);

                    // /home/hasdai/Documentos/SimpleParser.g:64:17: (v2= VAR )+
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
                    	    // /home/hasdai/Documentos/SimpleParser.g:0:0: v2= VAR
                    	    {
                    	    v2=(Token)match(input,VAR,FOLLOW_VAR_in_name292); if (state.failed) return retval;
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

                    RBRK17=(Token)match(input,RBRK,FOLLOW_RBRK_in_name295); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_RBRK.add(RBRK17);



                    // AST REWRITE
                    // elements: v1, v2
                    // token labels: v1, v2
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_v1=new RewriteRuleTokenStream(adaptor,"token v1",v1);
                    RewriteRuleTokenStream stream_v2=new RewriteRuleTokenStream(adaptor,"token v2",v2);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 64:28: -> ^( COMPNAME $v1 $v2)
                    {
                        // /home/hasdai/Documentos/SimpleParser.g:64:31: ^( COMPNAME $v1 $v2)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPNAME, "COMPNAME"), root_1);

                        adaptor.addChild(root_1, stream_v1.nextNode());
                        adaptor.addChild(root_1, stream_v2.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/SimpleParser.g:65:3: VAR
                    {
                    root_0 = (Object)adaptor.nil();

                    VAR18=(Token)match(input,VAR,FOLLOW_VAR_in_name311); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    VAR18_tree = (Object)adaptor.create(VAR18);
                    adaptor.addChild(root_0, VAR18_tree);
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
    // $ANTLR end "name"

    public static class sent_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sent"
    // /home/hasdai/Documentos/SimpleParser.g:68:1: sent : ( name SIGE val -> ^( ASIGN name val ) | name SIGL val -> ^( COMPL name val ) | name SIGG val -> ^( COMPG name val ) | name SIGLE val -> ^( COMPLE name val ) | name SIGGE val -> ^( COMPGE name val ) | name );
    public final SimpleParser.sent_return sent() throws RecognitionException {
        SimpleParser.sent_return retval = new SimpleParser.sent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SIGE20=null;
        Token SIGL23=null;
        Token SIGG26=null;
        Token SIGLE29=null;
        Token SIGGE32=null;
        SimpleParser.name_return name19 = null;

        SimpleParser.val_return val21 = null;

        SimpleParser.name_return name22 = null;

        SimpleParser.val_return val24 = null;

        SimpleParser.name_return name25 = null;

        SimpleParser.val_return val27 = null;

        SimpleParser.name_return name28 = null;

        SimpleParser.val_return val30 = null;

        SimpleParser.name_return name31 = null;

        SimpleParser.val_return val33 = null;

        SimpleParser.name_return name34 = null;


        Object SIGE20_tree=null;
        Object SIGL23_tree=null;
        Object SIGG26_tree=null;
        Object SIGLE29_tree=null;
        Object SIGGE32_tree=null;
        RewriteRuleTokenStream stream_SIGLE=new RewriteRuleTokenStream(adaptor,"token SIGLE");
        RewriteRuleTokenStream stream_SIGL=new RewriteRuleTokenStream(adaptor,"token SIGL");
        RewriteRuleTokenStream stream_SIGE=new RewriteRuleTokenStream(adaptor,"token SIGE");
        RewriteRuleTokenStream stream_SIGG=new RewriteRuleTokenStream(adaptor,"token SIGG");
        RewriteRuleTokenStream stream_SIGGE=new RewriteRuleTokenStream(adaptor,"token SIGGE");
        RewriteRuleSubtreeStream stream_val=new RewriteRuleSubtreeStream(adaptor,"rule val");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // /home/hasdai/Documentos/SimpleParser.g:69:1: ( name SIGE val -> ^( ASIGN name val ) | name SIGL val -> ^( COMPL name val ) | name SIGG val -> ^( COMPG name val ) | name SIGLE val -> ^( COMPLE name val ) | name SIGGE val -> ^( COMPGE name val ) | name )
            int alt9=6;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleParser.g:69:3: name SIGE val
                    {
                    pushFollow(FOLLOW_name_in_sent320);
                    name19=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name19.getTree());
                    SIGE20=(Token)match(input,SIGE,FOLLOW_SIGE_in_sent322); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGE.add(SIGE20);

                    pushFollow(FOLLOW_val_in_sent324);
                    val21=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val21.getTree());


                    // AST REWRITE
                    // elements: name, val
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 69:17: -> ^( ASIGN name val )
                    {
                        // /home/hasdai/Documentos/SimpleParser.g:69:20: ^( ASIGN name val )
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
                case 2 :
                    // /home/hasdai/Documentos/SimpleParser.g:70:3: name SIGL val
                    {
                    pushFollow(FOLLOW_name_in_sent338);
                    name22=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name22.getTree());
                    SIGL23=(Token)match(input,SIGL,FOLLOW_SIGL_in_sent340); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGL.add(SIGL23);

                    pushFollow(FOLLOW_val_in_sent342);
                    val24=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val24.getTree());


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
                    // 70:17: -> ^( COMPL name val )
                    {
                        // /home/hasdai/Documentos/SimpleParser.g:70:20: ^( COMPL name val )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPL, "COMPL"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/SimpleParser.g:71:3: name SIGG val
                    {
                    pushFollow(FOLLOW_name_in_sent356);
                    name25=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name25.getTree());
                    SIGG26=(Token)match(input,SIGG,FOLLOW_SIGG_in_sent358); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGG.add(SIGG26);

                    pushFollow(FOLLOW_val_in_sent360);
                    val27=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val27.getTree());


                    // AST REWRITE
                    // elements: name, val
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 71:17: -> ^( COMPG name val )
                    {
                        // /home/hasdai/Documentos/SimpleParser.g:71:20: ^( COMPG name val )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPG, "COMPG"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 4 :
                    // /home/hasdai/Documentos/SimpleParser.g:72:3: name SIGLE val
                    {
                    pushFollow(FOLLOW_name_in_sent374);
                    name28=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name28.getTree());
                    SIGLE29=(Token)match(input,SIGLE,FOLLOW_SIGLE_in_sent376); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGLE.add(SIGLE29);

                    pushFollow(FOLLOW_val_in_sent378);
                    val30=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val30.getTree());


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
                    // 72:18: -> ^( COMPLE name val )
                    {
                        // /home/hasdai/Documentos/SimpleParser.g:72:21: ^( COMPLE name val )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPLE, "COMPLE"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 5 :
                    // /home/hasdai/Documentos/SimpleParser.g:73:3: name SIGGE val
                    {
                    pushFollow(FOLLOW_name_in_sent392);
                    name31=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name31.getTree());
                    SIGGE32=(Token)match(input,SIGGE,FOLLOW_SIGGE_in_sent394); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGGE.add(SIGGE32);

                    pushFollow(FOLLOW_val_in_sent396);
                    val33=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val33.getTree());


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
                    // 73:18: -> ^( COMPGE name val )
                    {
                        // /home/hasdai/Documentos/SimpleParser.g:73:21: ^( COMPGE name val )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPGE, "COMPGE"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 6 :
                    // /home/hasdai/Documentos/SimpleParser.g:74:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_sent410);
                    name34=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name34.getTree());

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
    // /home/hasdai/Documentos/SimpleParser.g:76:1: proplist : ( sent DEL proplist | sent );
    public final SimpleParser.proplist_return proplist() throws RecognitionException {
        SimpleParser.proplist_return retval = new SimpleParser.proplist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL36=null;
        SimpleParser.sent_return sent35 = null;

        SimpleParser.proplist_return proplist37 = null;

        SimpleParser.sent_return sent38 = null;


        Object DEL36_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleParser.g:77:1: ( sent DEL proplist | sent )
            int alt10=2;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleParser.g:77:3: sent DEL proplist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_proplist418);
                    sent35=sent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sent35.getTree());
                    DEL36=(Token)match(input,DEL,FOLLOW_DEL_in_proplist420); if (state.failed) return retval;
                    pushFollow(FOLLOW_proplist_in_proplist423);
                    proplist37=proplist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, proplist37.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/SimpleParser.g:78:3: sent
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_proplist427);
                    sent38=sent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sent38.getTree());

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
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "val"
    // /home/hasdai/Documentos/SimpleParser.g:81:1: val : ( NUM | LIT | BOL );
    public final SimpleParser.val_return val() throws RecognitionException {
        SimpleParser.val_return retval = new SimpleParser.val_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set39=null;

        Object set39_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleParser.g:82:1: ( NUM | LIT | BOL )
            // /home/hasdai/Documentos/SimpleParser.g:
            {
            root_0 = (Object)adaptor.nil();

            set39=(Token)input.LT(1);
            if ( (input.LA(1)>=BOL && input.LA(1)<=LIT) ) {
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
    // $ANTLR end "val"

    public static class modifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "modifier"
    // /home/hasdai/Documentos/SimpleParser.g:87:1: modifier : ( ordterm )? ( offsetterm )? ;
    public final SimpleParser.modifier_return modifier() throws RecognitionException {
        SimpleParser.modifier_return retval = new SimpleParser.modifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SimpleParser.ordterm_return ordterm40 = null;

        SimpleParser.offsetterm_return offsetterm41 = null;



        try {
            // /home/hasdai/Documentos/SimpleParser.g:88:1: ( ( ordterm )? ( offsetterm )? )
            // /home/hasdai/Documentos/SimpleParser.g:88:3: ( ordterm )? ( offsetterm )?
            {
            root_0 = (Object)adaptor.nil();

            // /home/hasdai/Documentos/SimpleParser.g:88:3: ( ordterm )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==MODO) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleParser.g:0:0: ordterm
                    {
                    pushFollow(FOLLOW_ordterm_in_modifier454);
                    ordterm40=ordterm();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ordterm40.getTree());

                    }
                    break;

            }

            // /home/hasdai/Documentos/SimpleParser.g:88:12: ( offsetterm )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==MODE) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleParser.g:0:0: offsetterm
                    {
                    pushFollow(FOLLOW_offsetterm_in_modifier457);
                    offsetterm41=offsetterm();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, offsetterm41.getTree());

                    }
                    break;

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
    // $ANTLR end "modifier"

    public static class offsetterm_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "offsetterm"
    // /home/hasdai/Documentos/SimpleParser.g:91:1: offsetterm : MODE NUM -> ^( OFFSET NUM ) ;
    public final SimpleParser.offsetterm_return offsetterm() throws RecognitionException {
        SimpleParser.offsetterm_return retval = new SimpleParser.offsetterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MODE42=null;
        Token NUM43=null;

        Object MODE42_tree=null;
        Object NUM43_tree=null;
        RewriteRuleTokenStream stream_MODE=new RewriteRuleTokenStream(adaptor,"token MODE");
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");

        try {
            // /home/hasdai/Documentos/SimpleParser.g:92:1: ( MODE NUM -> ^( OFFSET NUM ) )
            // /home/hasdai/Documentos/SimpleParser.g:92:3: MODE NUM
            {
            MODE42=(Token)match(input,MODE,FOLLOW_MODE_in_offsetterm467); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODE.add(MODE42);

            NUM43=(Token)match(input,NUM,FOLLOW_NUM_in_offsetterm469); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_NUM.add(NUM43);



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
            // 92:12: -> ^( OFFSET NUM )
            {
                // /home/hasdai/Documentos/SimpleParser.g:92:15: ^( OFFSET NUM )
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
    // /home/hasdai/Documentos/SimpleParser.g:95:1: ordterm : MODO LPAR ordlist RPAR -> ^( ORDER ordlist ) ;
    public final SimpleParser.ordterm_return ordterm() throws RecognitionException {
        SimpleParser.ordterm_return retval = new SimpleParser.ordterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MODO44=null;
        Token LPAR45=null;
        Token RPAR47=null;
        SimpleParser.ordlist_return ordlist46 = null;


        Object MODO44_tree=null;
        Object LPAR45_tree=null;
        Object RPAR47_tree=null;
        RewriteRuleTokenStream stream_RPAR=new RewriteRuleTokenStream(adaptor,"token RPAR");
        RewriteRuleTokenStream stream_MODO=new RewriteRuleTokenStream(adaptor,"token MODO");
        RewriteRuleTokenStream stream_LPAR=new RewriteRuleTokenStream(adaptor,"token LPAR");
        RewriteRuleSubtreeStream stream_ordlist=new RewriteRuleSubtreeStream(adaptor,"rule ordlist");
        try {
            // /home/hasdai/Documentos/SimpleParser.g:96:1: ( MODO LPAR ordlist RPAR -> ^( ORDER ordlist ) )
            // /home/hasdai/Documentos/SimpleParser.g:96:3: MODO LPAR ordlist RPAR
            {
            MODO44=(Token)match(input,MODO,FOLLOW_MODO_in_ordterm486); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODO.add(MODO44);

            LPAR45=(Token)match(input,LPAR,FOLLOW_LPAR_in_ordterm488); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_LPAR.add(LPAR45);

            pushFollow(FOLLOW_ordlist_in_ordterm490);
            ordlist46=ordlist();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ordlist.add(ordlist46.getTree());
            RPAR47=(Token)match(input,RPAR,FOLLOW_RPAR_in_ordterm492); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_RPAR.add(RPAR47);



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
            // 96:26: -> ^( ORDER ordlist )
            {
                // /home/hasdai/Documentos/SimpleParser.g:96:29: ^( ORDER ordlist )
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
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ordlist"
    // /home/hasdai/Documentos/SimpleParser.g:99:1: ordlist : ( name DEL ordlist | name );
    public final SimpleParser.ordlist_return ordlist() throws RecognitionException {
        SimpleParser.ordlist_return retval = new SimpleParser.ordlist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL49=null;
        SimpleParser.name_return name48 = null;

        SimpleParser.ordlist_return ordlist50 = null;

        SimpleParser.name_return name51 = null;


        Object DEL49_tree=null;

        try {
            // /home/hasdai/Documentos/SimpleParser.g:100:1: ( name DEL ordlist | name )
            int alt13=2;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // /home/hasdai/Documentos/SimpleParser.g:100:3: name DEL ordlist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_ordlist509);
                    name48=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name48.getTree());
                    DEL49=(Token)match(input,DEL,FOLLOW_DEL_in_ordlist511); if (state.failed) return retval;
                    pushFollow(FOLLOW_ordlist_in_ordlist514);
                    ordlist50=ordlist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ordlist50.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/SimpleParser.g:101:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_ordlist518);
                    name51=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name51.getTree());

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

    // $ANTLR start synpred2_sParser
    public final void synpred2_sParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/SimpleParser.g:37:18: ( modifier )
        // /home/hasdai/Documentos/SimpleParser.g:37:18: modifier
        {
        pushFollow(FOLLOW_modifier_in_synpred2_sParser126);
        modifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_sParser

    // Delegated rules

    public final boolean synpred2_sParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_sParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA4 dfa4 = new DFA4(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA10 dfa10 = new DFA10(this);
    protected DFA13 dfa13 = new DFA13(this);
    static final String DFA3_eotS =
        "\11\uffff";
    static final String DFA3_eofS =
        "\1\uffff\1\5\1\uffff\1\5\4\uffff\1\5";
    static final String DFA3_minS =
        "\1\24\1\6\1\24\1\6\2\uffff\2\24\1\6";
    static final String DFA3_maxS =
        "\1\27\1\31\1\24\1\31\2\uffff\1\24\1\30\1\31";
    static final String DFA3_acceptS =
        "\4\uffff\1\2\1\1\3\uffff";
    static final String DFA3_specialS =
        "\11\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\1\2\uffff\1\2",
            "\5\4\1\5\1\uffff\1\4\2\5\4\uffff\1\3\4\uffff\1\4",
            "\1\6",
            "\5\4\1\5\1\uffff\1\4\2\5\4\uffff\1\3\4\uffff\1\4",
            "",
            "",
            "\1\7",
            "\1\7\3\uffff\1\10",
            "\5\4\1\5\1\uffff\1\4\2\5\11\uffff\1\4"
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "48:1: query : ( objquery | propquery );";
        }
    }
    static final String DFA4_eotS =
        "\11\uffff";
    static final String DFA4_eofS =
        "\1\uffff\1\4\1\uffff\1\4\4\uffff\1\4";
    static final String DFA4_minS =
        "\1\24\1\13\1\24\1\13\2\uffff\2\24\1\13";
    static final String DFA4_maxS =
        "\1\27\3\24\2\uffff\1\24\1\30\1\17";
    static final String DFA4_acceptS =
        "\4\uffff\1\2\1\1\3\uffff";
    static final String DFA4_specialS =
        "\11\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\1\2\uffff\1\2",
            "\1\5\2\uffff\2\4\4\uffff\1\3",
            "\1\6",
            "\1\5\2\uffff\2\4\4\uffff\1\3",
            "",
            "",
            "\1\7",
            "\1\7\3\uffff\1\10",
            "\1\5\2\uffff\2\4"
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "53:1: objquery : ( name PREC proplist -> ^( name ^( PRECON proplist ) ) | name );";
        }
    }
    static final String DFA9_eotS =
        "\15\uffff";
    static final String DFA9_eofS =
        "\1\uffff\1\10\1\uffff\1\10\10\uffff\1\10";
    static final String DFA9_minS =
        "\1\24\1\6\1\24\1\6\6\uffff\2\24\1\6";
    static final String DFA9_maxS =
        "\1\27\1\31\1\24\1\31\6\uffff\1\24\1\30\1\31";
    static final String DFA9_acceptS =
        "\4\uffff\1\3\1\1\1\4\1\2\1\6\1\5\3\uffff";
    static final String DFA9_specialS =
        "\15\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\1\2\uffff\1\2",
            "\1\7\1\4\1\5\1\6\1\11\2\uffff\3\10\4\uffff\1\3\4\uffff\1\10",
            "\1\12",
            "\1\7\1\4\1\5\1\6\1\11\2\uffff\3\10\4\uffff\1\3\4\uffff\1\10",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\13",
            "\1\13\3\uffff\1\14",
            "\1\7\1\4\1\5\1\6\1\11\2\uffff\3\10\11\uffff\1\10"
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
            return "68:1: sent : ( name SIGE val -> ^( ASIGN name val ) | name SIGL val -> ^( COMPL name val ) | name SIGG val -> ^( COMPG name val ) | name SIGLE val -> ^( COMPLE name val ) | name SIGGE val -> ^( COMPGE name val ) | name );";
        }
    }
    static final String DFA10_eotS =
        "\23\uffff";
    static final String DFA10_eofS =
        "\1\uffff\1\10\1\uffff\1\10\10\uffff\5\10\1\uffff\1\10";
    static final String DFA10_minS =
        "\1\24\1\6\1\24\1\6\2\20\1\uffff\1\20\1\uffff\2\20\1\24\5\15\1\24"+
        "\1\6";
    static final String DFA10_maxS =
        "\1\27\1\31\1\24\1\31\2\22\1\uffff\1\22\1\uffff\2\22\1\24\5\31\1"+
        "\30\1\31";
    static final String DFA10_acceptS =
        "\6\uffff\1\1\1\uffff\1\2\12\uffff";
    static final String DFA10_specialS =
        "\23\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\1\2\uffff\1\2",
            "\1\5\1\7\1\12\1\11\1\4\2\uffff\3\10\4\uffff\1\3\4\uffff\1\6",
            "\1\13",
            "\1\5\1\7\1\12\1\11\1\4\2\uffff\3\10\4\uffff\1\3\4\uffff\1\6",
            "\3\14",
            "\3\15",
            "",
            "\3\16",
            "",
            "\3\17",
            "\3\20",
            "\1\21",
            "\3\10\11\uffff\1\6",
            "\3\10\11\uffff\1\6",
            "\3\10\11\uffff\1\6",
            "\3\10\11\uffff\1\6",
            "\3\10\11\uffff\1\6",
            "\1\21\3\uffff\1\22",
            "\1\5\1\7\1\12\1\11\1\4\2\uffff\3\10\11\uffff\1\6"
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
            return "76:1: proplist : ( sent DEL proplist | sent );";
        }
    }
    static final String DFA13_eotS =
        "\11\uffff";
    static final String DFA13_eofS =
        "\1\uffff\1\5\2\uffff\1\5\3\uffff\1\5";
    static final String DFA13_minS =
        "\3\24\1\uffff\1\24\1\uffff\2\24\1\26";
    static final String DFA13_maxS =
        "\1\27\1\31\1\24\1\uffff\1\31\1\uffff\1\24\1\30\1\31";
    static final String DFA13_acceptS =
        "\3\uffff\1\1\1\uffff\1\2\3\uffff";
    static final String DFA13_specialS =
        "\11\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\1\2\uffff\1\2",
            "\1\4\1\uffff\1\5\2\uffff\1\3",
            "\1\6",
            "",
            "\1\4\1\uffff\1\5\2\uffff\1\3",
            "",
            "\1\7",
            "\1\7\3\uffff\1\10",
            "\1\5\2\uffff\1\3"
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
            return "99:1: ordlist : ( name DEL ordlist | name );";
        }
    }


    public static final BitSet FOLLOW_selectquery_in_squery111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_limiter_in_selectquery121 = new BitSet(new long[]{0x0000000000900000L});
    public static final BitSet FOLLOW_query_in_selectquery124 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_modifier_in_selectquery126 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_selectquery129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_limiter155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objquery_in_query172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propquery_in_query176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_objquery187 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PREC_in_objquery189 = new BitSet(new long[]{0x0000000000900000L});
    public static final BitSet FOLLOW_proplist_in_objquery191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_objquery207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_proplist_in_propquery218 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_PRED_in_propquery220 = new BitSet(new long[]{0x0000000000900000L});
    public static final BitSet FOLLOW_name_in_propquery222 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_PREC_in_propquery225 = new BitSet(new long[]{0x0000000000900000L});
    public static final BitSet FOLLOW_proplist_in_propquery229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_name263 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_VAR_in_name267 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_LBRK_in_name284 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_VAR_in_name288 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_VAR_in_name292 = new BitSet(new long[]{0x0000000001100000L});
    public static final BitSet FOLLOW_RBRK_in_name295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_name311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent320 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SIGE_in_sent322 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent338 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_sent340 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent356 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_sent358 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent374 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_sent376 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent392 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_sent394 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent396 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_proplist418 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_DEL_in_proplist420 = new BitSet(new long[]{0x0000000000900000L});
    public static final BitSet FOLLOW_proplist_in_proplist423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_proplist427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_val0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ordterm_in_modifier454 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_offsetterm_in_modifier457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODE_in_offsetterm467 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_NUM_in_offsetterm469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODO_in_ordterm486 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_LPAR_in_ordterm488 = new BitSet(new long[]{0x0000000000900000L});
    public static final BitSet FOLLOW_ordlist_in_ordterm490 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_RPAR_in_ordterm492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_ordlist509 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_DEL_in_ordlist511 = new BitSet(new long[]{0x0000000000900000L});
    public static final BitSet FOLLOW_ordlist_in_ordlist514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_ordlist518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_modifier_in_synpred2_sParser126 = new BitSet(new long[]{0x0000000000000002L});

}