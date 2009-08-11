/*
 * Groovy syntax v 1.0
 * (2009/07/16)
 * Info taken from: http://groovy.codehaus.org/User+Guide
 */
editAreaLoader.load_syntax["groovy"] = {
	'COMMENT_SINGLE' : {1 : '//'}
	,'COMMENT_MULTI' : {'/*' : '*/'}
	,'QUOTEMARKS' : {1: "'", 2: '"'}
	,'KEYWORD_CASE_SENSITIVE' : true
	,'KEYWORDS' : {
		'constants' : [
			'false', 'null', 'true'
		]
		,'types' : [
			'boolean', 'byte', 'char', 'double', 'enum', 'float', 'int', 'long',
            'native', 'short'
		]
		,'statements' : [
			'as', 'case', 'catch', 'do', 'else', 'finally', 'for', 'goto', 'if',
            'return', 'switch', 'throw', 'while'
			
		]
 		,'keywords' : [
			'abstract', 'assert', 'break', 'class', 'const', 'continue', 'def', 
            'default', 'extends', 'final', 'implements', 'import', 'in', 'instanceof',
            'interface', 'new', 'package', 'private', 'protected', 'public',
            'static', 'strictfp', 'super', 'synchronized', 'this', 'threadsafe',
            'throws', 'transient', 'try', 'void', 'volatile'
		]
	}
	,'OPERATORS' :[
		'+', '-', '/', '*', '=', '<', '>', '%', '!', '?', ':', '&', '|', '~'
	]
	,'DELIMITERS' :[
		'(', ')', '[', ']', '{', '}'
	]
	,'STYLES' : {
		'COMMENTS': 'color: #AAAAAA;'
		,'QUOTESMARKS': 'color: #6381F8;'
		,'KEYWORDS' : {
			'constants' : 'color: #EE0000;'
			,'types' : 'color: #0000EE;'
			,'statements' : 'color: #60CA00;'
			,'keywords' : 'color: #48BDDF;'
		}
		,'OPERATORS' : 'color: #FF00FF;'
		,'DELIMITERS' : 'color: #0038E1;'
	}
};
