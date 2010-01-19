parser grammar SimpleCSSParser;
//Gramatica simplificada para parsear hojas de estilo sencillas, solo con lista de clases o una clase seguida de elementos HTML

//OPCIONES DEL PARSER
options {
	tokenVocab = CSSLexer; //Indica que el vocabulario a usar provendra del CSSLexer
	output = AST;   //Indica que la salida del parser sera un arbol de sintaxis abstracta (AST)
	backtrack=true;
}

//Etiquetas de los nodos imaginarios para el analisis semantico
tokens {
	STYLESHEET; CLASS; SELECTOR; STYLE; HTMLELEMENT; ELEMENTS; NAME; VAL; EXPR;
}

//Metodos de ayuda para saber el estado del parser
@members {
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
}

//Una hoja de estilos se compone de un bodyList, el resultado de la ejecucion de esta regla sera un nodo llamado STYLESHEET
//cuyos hijos seran los resultados de las derivaciones de bodyList
styleSheet
:	bodyList EOF -> ^(STYLESHEET bodyList)
;

//Un bodyList es cero o mas bodysets
bodyList
:	bodyset*
;

//Un bodyset es un ruleSet
bodyset
: 	ruleSet
	;

//Un ruleSet es uno o mas selectores separados por comas, seguidos de una llave que abre, un conjunto de declaraciones separadas
//por punto y coma y una llave que cierra
//El resultado de la ejecucion de esta produccion sera un arbol con raiz llamada SELECTOR y dos hijos: ELEMENTS y STYLE.
ruleSet
:	selector (COMMA selector)*
	LBRACE
		declaration SEMI (declaration SEMI)*
	RBRACE -> ^(SELECTOR ^(ELEMENTS selector) ^(STYLE declaration+))
	|selector (COMMA selector)*
	LBRACE
	RBRACE -> ^(SELECTOR ^(ELEMENTS selector) ^(STYLE))
	;

//Un selector es una lista de simpleSelectores separados por un signo de mas, un signo mayor que o un espacio.
selector
:	simpleSelector (combinator simpleSelector)*
	;

combinator
:	PLUS
	|GREATER
	|
	;

//Un simpleSelector es un nombre de un elemento HTML o el nombre de una clase CSS (que comienza con un punto)
//En caso de ser una clase, se genera un nodo llamado CLASS cuyos hijos son las derivaciones de la produccion cssClass
//En caso de ser un elementName se genera un nodo llamado HTMLELEMENT cuyos hijos son las derivaciones de la produccion elementName
simpleSelector
:	//elementName -> ^(HTMLELEMENT elementName)
	cssClass -> ^(CLASS cssClass)
;

//Un nombre de elemento HTML es un identificador
elementName
:	IDENT
	;

//Una clase en CSS es un identificador precedido por un punto. El signo de admiracion enseguida del token DOT indica que no se generara
//un nodo para ese token en el arbol de analisis semantico
cssClass
:	DOT! IDENT
	;

//Una declaracion es una propiedad seguida de dos puntos seguidos de una expresion
declaration
:	property COLON expr -> ^(EXPR ^(NAME property) ^(VAL expr))
;

//Una propiedad es un identificador
property
:	IDENT
	;

//Una expresion es uno o mas terminos separados por un operador
expr
: term (operator term)*
;

//Un termino puede ser un numero, un porcentaje, un tamaño, un EMS, un EXS, un ANGLE, un TIME, un FREQ, una cadena, una funcion
//un URI o un color hexadecimal
term
:	unaryOperator? (NUMBER|PERCENTAGE|LENGTH|EMS|EXS|ANGLE|TIME|FREQ)
	|STRING
	|IDENT (LPAREN expr RPAREN)? //funcion
	|URI
	|hexColor
	;

unaryOperator
:	MINUS
	|PLUS
;

hexColor
:	HASH
	;

//Un operador puede ser una diagonal, una coma o un espacio
operator
:	SOLIDUS
	|COMMA
	|
	;