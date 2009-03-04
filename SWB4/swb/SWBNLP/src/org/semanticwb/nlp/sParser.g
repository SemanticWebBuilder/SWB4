parser grammar sParser;

options {
	backtrack = true;
	tokenVocab = sLexer;
	output = AST;
}

tokens {
	LIMIT; SELECTQUERY; MOD; OBJLIST; PROPLIST; ASIGN; SENTENCE; COMPL; COMPG; COMPLE; COMPGE;
	PRECON; PREDE; OFFSET; ORDER; COMPNAME; ASKQUERY; MODTO;
}

@header {
	import java.util.ArrayList;
	import java.util.Iterator;
}

@members {
	private int errCount = 0;
	private ArrayList<String> objs = new ArrayList<String>();
	private ArrayList<String> props = new ArrayList<String>();
	private String lim = "";
	private String mod = "";
	private boolean select = false;
	private boolean pcon = false;
	private boolean prde = false;
	private Lexicon lex;

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
}

squery [Lexicon dict]//returns [boolean isSelect, String lim, ArrayList<Word> objs, ArrayList<Property> props, String mod]
@init {
	lex = dict;
}
:	askquery {select = false;}
	|selectquery {select = true;}

;

selectquery //returns [String lim, ArrayList<Word> objs, ArrayList<Property> props, String mod]
:	limiter? query modifier? EOF {} -> ^(SELECTQUERY limiter? query modifier?)
//	|MODT PRED objlist EOF -> ^(SELECTQUERY MODTO)
;

askquery //returns [ArrayList<Word> objs, ArrayList<Property> props]
:	query SIGI EOF-> ^(ASKQUERY query)
;

limiter //returns [String val]
:	NUM {lim = "LIMIT " + $NUM.getText();} -> ^(LIMIT NUM)
;

query //returns [ArrayList<Word> objs, ArrayList<Property> props]
:	objquery
	|propquery
;

objquery //returns [ArrayList<Word> objs, ArrayList<Property> props]
:	objlist PREC proplist {pcon = true;} -> ^(PRECON ^(OBJLIST objlist) ^(PROPLIST proplist))
	|objlist -> ^(OBJLIST objlist)
;

propquery //returns [ArrayList<Word> objs, ArrayList<Property> props]
:	proplist PRED objlist {prde = true;} -> ^(PREDE ^(PROPLIST proplist) ^(OBJLIST objlist))
	//|MODT PRED objlist {$objs = $objlist.objs; $props = null;} -> ^(PREDE MODTO objlist)
;

objlist //returns [ArrayList<Word> obj]
:	name
	{
		WordTag wt = lex.getWordTag($name.val);
		if (wt.getLabel().compareTo("OBJ") == 0) {
			objs.add("?" + $name.val + " rdf:type " + wt.getType());
		} else if (wt.getLabel().compareTo("VAR") == 0){
			objs.add("?" + $name.val + " rdf:type " + "?" + $name.val + "_t");
		}
	} DEL! objlist
	|name
	{
		WordTag wt = lex.getWordTag($name.val);
		if (wt.getLabel().compareTo("OBJ") == 0) {
			objs.add("?" + $name.val + " rdf:type " + wt.getType());
		} else if (wt.getLabel().compareTo("VAR") == 0){
			objs.add("?" + $name.val + " rdf:type " + "?" + $name.val + "_t");
		}
	}
;

name returns [String val]
:	v1=VAR v2=VAR+ {$val = v1.getText() + " " + v2.getText(); $val = $val.trim();} -> ^(COMPNAME VAR VAR)
	|VAR {$val = $VAR.getText(); $val = $val.trim();} -> ^(VAR)
;

//TODO: incorporar propiedades con nombres compuestos

sent returns [String name, String value, int typ]
:	VAR SIGE val {$name = $VAR.getText(); $value = $val.val; $typ = 1;} -> ^(ASIGN VAR val)
	|v=name SIGE val {$name = $v.val; $value = $val.val; $typ = 1;} -> ^(ASIGN name val)
	|VAR SIGL val {$name = $VAR.getText(); $value = $val.val; $typ = 2;} -> ^(COMPL VAR val)//pendiente para filters
	|VAR SIGG val {$name = $VAR.getText(); $value = $val.val; $typ = 3;} -> ^(COMPG VAR val)//pendiente para filters
	|VAR SIGLE val {$name = $VAR.getText(); $value = $val.val; $typ = 4;} -> ^(COMPLE VAR val)//pendiente para filters
	|VAR SIGGE val {$name = $VAR.getText(); $value = $val.val; $typ = 5;} -> ^(COMPGE VAR val)//pendiente para filters
;
proplist //returns [ArrayList<Property> pro]
:	sent
	{
		WordTag wt = lex.getWordTag($sent.name);
		if (wt.getLabel().compareTo("PRO") == 0) {
			switch($sent.typ) {
				case 1:
				{
					props.add(wt.getType() + " " + $sent.value);
				}break;
			}
		}
	} DEL! proplist
	|name
	{
		WordTag wt = lex.getWordTag($name.val);
		if (wt.getLabel().compareTo("PRO") == 0) {
			props.add(wt.getType() + " ?" + $name.val.replace(' ','_'));
		}
	} DEL! proplist
	|sent
	{
		WordTag wt = lex.getWordTag($sent.name);
		if (wt.getLabel().compareTo("PRO") == 0) {
			switch($sent.typ) {
				case 1:
				{
					props.add(wt.getType() + " " + $sent.value);
				}break;
			}
		}
	}
	|name
	{
		WordTag wt = lex.getWordTag($name.val);
		if (wt.getLabel().compareTo("PRO") == 0) {
			props.add(wt.getType() + " ?" + $name.val.replace(' ','_'));
		}
	}
;

val returns [String val]
: 	NUM {$val = $NUM.getText();}
	|LIT {$val = $LIT.getText();}
	|BOL {$val = $BOL.getText();}
;

modifier //returns [String mod]
:	ordterm offsetterm?
	|offsetterm
;

offsetterm //returns [String term]
:	MODE NUM {mod = "OFFSET " + $NUM.getText() + " ";} -> ^(OFFSET NUM)
;

ordterm //returns [String term]
:	MODO LPAR ordlist {mod = "ORDER BY " + $ordlist.terms.trim().replace(' ', '_');} RPAR -> ^(ORDER ordlist)
;

ordlist returns [String terms]
:	name {$terms = "?" + $name.val + " "; $terms.trim();} DEL! v=ordlist {$terms = $terms + $v.terms; $terms.trim();}
	|name {$terms = "?" + $name.val + " "; $terms.trim();}
;