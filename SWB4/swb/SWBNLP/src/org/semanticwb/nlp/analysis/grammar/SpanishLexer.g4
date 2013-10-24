/*
* Gramática para lexer de preposiciones y frases de uso e consultas de propiedades en español.
* @author Hasdai Pcacheco {ebenezer.sanchez@infotec.com.mx}
*/
lexer grammar SpanishLexer;

WHITESPACE
	:	(' '|'\t'|'\f'|'\n'|'\r') -> skip;
SIGN	:	'+' | '-';
SIGL	:	'<';
SIGG	:	'>';
SIGE	:	'=';
SIGLE	:	'<=';
SIGGE	:	'>=';
MODD	:	'qué es';
PREC	:	'con';
MODT	:	'todo';
PRED	:	'de';
PREN	:	'en';
MODE	:	'desde';
MODN	:	'entre';
MODO	:	'ordenar'|'ordenado';
MODC   	:    	'como';
BOL	:	'true'|'false';
NUM	:	SIGN? ('0'..'9'+ | ('0'..'9'* '.' '0'..'9'+));
LIT	:	'"'(~('"'|'\n'|'\r'|'\t'))*'"';
BVAR	:	LBRK(~(']'|'['|'\n'|'\r'|'\t'))*RBRK;
ORDOP	:	'asc'|'des';
DELY	:	'y';
VAR	:	('0'..'9'|'a'..'z'|'A'..'Z'|'_'|'-'|'á'|'é'|'í'|'ó'|'ú'|'ä'|'ë'|'ï'|'ö'|'ü'|'Ä'|'Ë'|'Ï'|'Ö'|'Ü'|'Á'|'É'|'Í'|'Ó'|'Ú'|'Ñ'|'ñ')+;
LPAR	:	'(';
RPAR	:	')';
LBRK	:	'[';
RBRK	:	']';
DEL	:	',';
CQM	:	'\?';
OQM	:	'\¿';