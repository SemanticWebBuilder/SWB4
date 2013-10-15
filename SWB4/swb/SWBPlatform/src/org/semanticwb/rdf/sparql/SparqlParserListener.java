// Generated from /programming/proys/SWB4/swb/SWBPlatform/src/org/semanticwb/rdf/sparql/SparqlParser.g4 by ANTLR 4.0
package org.semanticwb.rdf.sparql;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface SparqlParserListener extends ParseTreeListener {
	void enterQuadPattern(SparqlParser.QuadPatternContext ctx);
	void exitQuadPattern(SparqlParser.QuadPatternContext ctx);

	void enterUnaryMultiplicativeExpression(SparqlParser.UnaryMultiplicativeExpressionContext ctx);
	void exitUnaryMultiplicativeExpression(SparqlParser.UnaryMultiplicativeExpressionContext ctx);

	void enterAggregate(SparqlParser.AggregateContext ctx);
	void exitAggregate(SparqlParser.AggregateContext ctx);

	void enterUpdateCommand(SparqlParser.UpdateCommandContext ctx);
	void exitUpdateCommand(SparqlParser.UpdateCommandContext ctx);

	void enterLimitOffsetClauses(SparqlParser.LimitOffsetClausesContext ctx);
	void exitLimitOffsetClauses(SparqlParser.LimitOffsetClausesContext ctx);

	void enterArgList(SparqlParser.ArgListContext ctx);
	void exitArgList(SparqlParser.ArgListContext ctx);

	void enterVerbPath(SparqlParser.VerbPathContext ctx);
	void exitVerbPath(SparqlParser.VerbPathContext ctx);

	void enterPrefixDecl(SparqlParser.PrefixDeclContext ctx);
	void exitPrefixDecl(SparqlParser.PrefixDeclContext ctx);

	void enterExpressionList(SparqlParser.ExpressionListContext ctx);
	void exitExpressionList(SparqlParser.ExpressionListContext ctx);

	void enterCollectionPath(SparqlParser.CollectionPathContext ctx);
	void exitCollectionPath(SparqlParser.CollectionPathContext ctx);

	void enterTriplesTemplate(SparqlParser.TriplesTemplateContext ctx);
	void exitTriplesTemplate(SparqlParser.TriplesTemplateContext ctx);

	void enterInlineData(SparqlParser.InlineDataContext ctx);
	void exitInlineData(SparqlParser.InlineDataContext ctx);

	void enterSelectVariables(SparqlParser.SelectVariablesContext ctx);
	void exitSelectVariables(SparqlParser.SelectVariablesContext ctx);

	void enterSolutionModifier(SparqlParser.SolutionModifierContext ctx);
	void exitSolutionModifier(SparqlParser.SolutionModifierContext ctx);

	void enterOrderCondition(SparqlParser.OrderConditionContext ctx);
	void exitOrderCondition(SparqlParser.OrderConditionContext ctx);

	void enterGraphOrDefault(SparqlParser.GraphOrDefaultContext ctx);
	void exitGraphOrDefault(SparqlParser.GraphOrDefaultContext ctx);

	void enterMove(SparqlParser.MoveContext ctx);
	void exitMove(SparqlParser.MoveContext ctx);

	void enterCopy(SparqlParser.CopyContext ctx);
	void exitCopy(SparqlParser.CopyContext ctx);

	void enterPrefixedName(SparqlParser.PrefixedNameContext ctx);
	void exitPrefixedName(SparqlParser.PrefixedNameContext ctx);

	void enterConstructQuery(SparqlParser.ConstructQueryContext ctx);
	void exitConstructQuery(SparqlParser.ConstructQueryContext ctx);

	void enterStrReplaceExpression(SparqlParser.StrReplaceExpressionContext ctx);
	void exitStrReplaceExpression(SparqlParser.StrReplaceExpressionContext ctx);

	void enterGroupGraphPatternSub(SparqlParser.GroupGraphPatternSubContext ctx);
	void exitGroupGraphPatternSub(SparqlParser.GroupGraphPatternSubContext ctx);

	void enterObjectPath(SparqlParser.ObjectPathContext ctx);
	void exitObjectPath(SparqlParser.ObjectPathContext ctx);

	void enterGraphRef(SparqlParser.GraphRefContext ctx);
	void exitGraphRef(SparqlParser.GraphRefContext ctx);

	void enterBuiltInCall(SparqlParser.BuiltInCallContext ctx);
	void exitBuiltInCall(SparqlParser.BuiltInCallContext ctx);

	void enterSelectQuery(SparqlParser.SelectQueryContext ctx);
	void exitSelectQuery(SparqlParser.SelectQueryContext ctx);

	void enterCreate(SparqlParser.CreateContext ctx);
	void exitCreate(SparqlParser.CreateContext ctx);

	void enterVarOrIRI(SparqlParser.VarOrIRIContext ctx);
	void exitVarOrIRI(SparqlParser.VarOrIRIContext ctx);

	void enterQuadsNotTriples(SparqlParser.QuadsNotTriplesContext ctx);
	void exitQuadsNotTriples(SparqlParser.QuadsNotTriplesContext ctx);

	void enterHavingCondition(SparqlParser.HavingConditionContext ctx);
	void exitHavingCondition(SparqlParser.HavingConditionContext ctx);

	void enterConstructTriples(SparqlParser.ConstructTriplesContext ctx);
	void exitConstructTriples(SparqlParser.ConstructTriplesContext ctx);

	void enterServiceGraphPattern(SparqlParser.ServiceGraphPatternContext ctx);
	void exitServiceGraphPattern(SparqlParser.ServiceGraphPatternContext ctx);

	void enterLoad(SparqlParser.LoadContext ctx);
	void exitLoad(SparqlParser.LoadContext ctx);

	void enterGraphNodePath(SparqlParser.GraphNodePathContext ctx);
	void exitGraphNodePath(SparqlParser.GraphNodePathContext ctx);

	void enterDeleteClause(SparqlParser.DeleteClauseContext ctx);
	void exitDeleteClause(SparqlParser.DeleteClauseContext ctx);

	void enterGroupOrUnionGraphPattern(SparqlParser.GroupOrUnionGraphPatternContext ctx);
	void exitGroupOrUnionGraphPattern(SparqlParser.GroupOrUnionGraphPatternContext ctx);

	void enterRdfLiteral(SparqlParser.RdfLiteralContext ctx);
	void exitRdfLiteral(SparqlParser.RdfLiteralContext ctx);

	void enterQuery(SparqlParser.QueryContext ctx);
	void exitQuery(SparqlParser.QueryContext ctx);

	void enterGraphTerm(SparqlParser.GraphTermContext ctx);
	void exitGraphTerm(SparqlParser.GraphTermContext ctx);

	void enterQuadsDetails(SparqlParser.QuadsDetailsContext ctx);
	void exitQuadsDetails(SparqlParser.QuadsDetailsContext ctx);

	void enterSubSelect(SparqlParser.SubSelectContext ctx);
	void exitSubSelect(SparqlParser.SubSelectContext ctx);

	void enterConstraint(SparqlParser.ConstraintContext ctx);
	void exitConstraint(SparqlParser.ConstraintContext ctx);

	void enterCollection(SparqlParser.CollectionContext ctx);
	void exitCollection(SparqlParser.CollectionContext ctx);

	void enterHavingClause(SparqlParser.HavingClauseContext ctx);
	void exitHavingClause(SparqlParser.HavingClauseContext ctx);

	void enterClear(SparqlParser.ClearContext ctx);
	void exitClear(SparqlParser.ClearContext ctx);

	void enterTriplesBlock(SparqlParser.TriplesBlockContext ctx);
	void exitTriplesBlock(SparqlParser.TriplesBlockContext ctx);

	void enterPathMod(SparqlParser.PathModContext ctx);
	void exitPathMod(SparqlParser.PathModContext ctx);

	void enterOrderClause(SparqlParser.OrderClauseContext ctx);
	void exitOrderClause(SparqlParser.OrderClauseContext ctx);

	void enterGroupGraphPattern(SparqlParser.GroupGraphPatternContext ctx);
	void exitGroupGraphPattern(SparqlParser.GroupGraphPatternContext ctx);

	void enterQuadData(SparqlParser.QuadDataContext ctx);
	void exitQuadData(SparqlParser.QuadDataContext ctx);

	void enterBaseExpression(SparqlParser.BaseExpressionContext ctx);
	void exitBaseExpression(SparqlParser.BaseExpressionContext ctx);

	void enterUsingClause(SparqlParser.UsingClauseContext ctx);
	void exitUsingClause(SparqlParser.UsingClauseContext ctx);

	void enterNumericLiteralPositive(SparqlParser.NumericLiteralPositiveContext ctx);
	void exitNumericLiteralPositive(SparqlParser.NumericLiteralPositiveContext ctx);

	void enterNumericLiteral(SparqlParser.NumericLiteralContext ctx);
	void exitNumericLiteral(SparqlParser.NumericLiteralContext ctx);

	void enterIriRefOrFunction(SparqlParser.IriRefOrFunctionContext ctx);
	void exitIriRefOrFunction(SparqlParser.IriRefOrFunctionContext ctx);

	void enterDataBlockValue(SparqlParser.DataBlockValueContext ctx);
	void exitDataBlockValue(SparqlParser.DataBlockValueContext ctx);

	void enterRelationalSetExpression(SparqlParser.RelationalSetExpressionContext ctx);
	void exitRelationalSetExpression(SparqlParser.RelationalSetExpressionContext ctx);

	void enterGraphNode(SparqlParser.GraphNodeContext ctx);
	void exitGraphNode(SparqlParser.GraphNodeContext ctx);

	void enterGraphRefAll(SparqlParser.GraphRefAllContext ctx);
	void exitGraphRefAll(SparqlParser.GraphRefAllContext ctx);

	void enterInteger(SparqlParser.IntegerContext ctx);
	void exitInteger(SparqlParser.IntegerContext ctx);

	void enterInlineDataFull(SparqlParser.InlineDataFullContext ctx);
	void exitInlineDataFull(SparqlParser.InlineDataFullContext ctx);

	void enterBooleanLiteral(SparqlParser.BooleanLiteralContext ctx);
	void exitBooleanLiteral(SparqlParser.BooleanLiteralContext ctx);

	void enterNumericLiteralUnsigned(SparqlParser.NumericLiteralUnsignedContext ctx);
	void exitNumericLiteralUnsigned(SparqlParser.NumericLiteralUnsignedContext ctx);

	void enterUnarySignedLiteralExpression(SparqlParser.UnarySignedLiteralExpressionContext ctx);
	void exitUnarySignedLiteralExpression(SparqlParser.UnarySignedLiteralExpressionContext ctx);

	void enterOffsetClause(SparqlParser.OffsetClauseContext ctx);
	void exitOffsetClause(SparqlParser.OffsetClauseContext ctx);

	void enterPathElt(SparqlParser.PathEltContext ctx);
	void exitPathElt(SparqlParser.PathEltContext ctx);

	void enterInlineDataOneVar(SparqlParser.InlineDataOneVarContext ctx);
	void exitInlineDataOneVar(SparqlParser.InlineDataOneVarContext ctx);

	void enterAskQuery(SparqlParser.AskQueryContext ctx);
	void exitAskQuery(SparqlParser.AskQueryContext ctx);

	void enterNotExistsFunction(SparqlParser.NotExistsFunctionContext ctx);
	void exitNotExistsFunction(SparqlParser.NotExistsFunctionContext ctx);

	void enterString(SparqlParser.StringContext ctx);
	void exitString(SparqlParser.StringContext ctx);

	void enterBlankNodePropertyListPath(SparqlParser.BlankNodePropertyListPathContext ctx);
	void exitBlankNodePropertyListPath(SparqlParser.BlankNodePropertyListPathContext ctx);

	void enterMinusGraphPattern(SparqlParser.MinusGraphPatternContext ctx);
	void exitMinusGraphPattern(SparqlParser.MinusGraphPatternContext ctx);

	void enterExistsFunction(SparqlParser.ExistsFunctionContext ctx);
	void exitExistsFunction(SparqlParser.ExistsFunctionContext ctx);

	void enterWhereClause(SparqlParser.WhereClauseContext ctx);
	void exitWhereClause(SparqlParser.WhereClauseContext ctx);

	void enterGraphGraphPattern(SparqlParser.GraphGraphPatternContext ctx);
	void exitGraphGraphPattern(SparqlParser.GraphGraphPatternContext ctx);

	void enterLimitClause(SparqlParser.LimitClauseContext ctx);
	void exitLimitClause(SparqlParser.LimitClauseContext ctx);

	void enterPropertyListNotEmpty(SparqlParser.PropertyListNotEmptyContext ctx);
	void exitPropertyListNotEmpty(SparqlParser.PropertyListNotEmptyContext ctx);

	void enterUnaryExpression(SparqlParser.UnaryExpressionContext ctx);
	void exitUnaryExpression(SparqlParser.UnaryExpressionContext ctx);

	void enterDrop(SparqlParser.DropContext ctx);
	void exitDrop(SparqlParser.DropContext ctx);

	void enterBaseDecl(SparqlParser.BaseDeclContext ctx);
	void exitBaseDecl(SparqlParser.BaseDeclContext ctx);

	void enterPropertyList(SparqlParser.PropertyListContext ctx);
	void exitPropertyList(SparqlParser.PropertyListContext ctx);

	void enterInsertClause(SparqlParser.InsertClauseContext ctx);
	void exitInsertClause(SparqlParser.InsertClauseContext ctx);

	void enterPrimaryExpression(SparqlParser.PrimaryExpressionContext ctx);
	void exitPrimaryExpression(SparqlParser.PrimaryExpressionContext ctx);

	void enterUnaryNegationExpression(SparqlParser.UnaryNegationExpressionContext ctx);
	void exitUnaryNegationExpression(SparqlParser.UnaryNegationExpressionContext ctx);

	void enterInsertData(SparqlParser.InsertDataContext ctx);
	void exitInsertData(SparqlParser.InsertDataContext ctx);

	void enterPathNegatedPropertySet(SparqlParser.PathNegatedPropertySetContext ctx);
	void exitPathNegatedPropertySet(SparqlParser.PathNegatedPropertySetContext ctx);

	void enterSelectClause(SparqlParser.SelectClauseContext ctx);
	void exitSelectClause(SparqlParser.SelectClauseContext ctx);

	void enterObjectListPath(SparqlParser.ObjectListPathContext ctx);
	void exitObjectListPath(SparqlParser.ObjectListPathContext ctx);

	void enterSubStringExpression(SparqlParser.SubStringExpressionContext ctx);
	void exitSubStringExpression(SparqlParser.SubStringExpressionContext ctx);

	void enterFunctionCall(SparqlParser.FunctionCallContext ctx);
	void exitFunctionCall(SparqlParser.FunctionCallContext ctx);

	void enterAnon(SparqlParser.AnonContext ctx);
	void exitAnon(SparqlParser.AnonContext ctx);

	void enterTriplesSameSubjectPath(SparqlParser.TriplesSameSubjectPathContext ctx);
	void exitTriplesSameSubjectPath(SparqlParser.TriplesSameSubjectPathContext ctx);

	void enterUnaryAdditiveExpression(SparqlParser.UnaryAdditiveExpressionContext ctx);
	void exitUnaryAdditiveExpression(SparqlParser.UnaryAdditiveExpressionContext ctx);

	void enterVerbSimple(SparqlParser.VerbSimpleContext ctx);
	void exitVerbSimple(SparqlParser.VerbSimpleContext ctx);

	void enterDescribeQuery(SparqlParser.DescribeQueryContext ctx);
	void exitDescribeQuery(SparqlParser.DescribeQueryContext ctx);

	void enterTriplesSameSubject(SparqlParser.TriplesSameSubjectContext ctx);
	void exitTriplesSameSubject(SparqlParser.TriplesSameSubjectContext ctx);

	void enterPrologue(SparqlParser.PrologueContext ctx);
	void exitPrologue(SparqlParser.PrologueContext ctx);

	void enterConditionalAndExpression(SparqlParser.ConditionalAndExpressionContext ctx);
	void exitConditionalAndExpression(SparqlParser.ConditionalAndExpressionContext ctx);

	void enterNumericLiteralNegative(SparqlParser.NumericLiteralNegativeContext ctx);
	void exitNumericLiteralNegative(SparqlParser.NumericLiteralNegativeContext ctx);

	void enterAdditiveExpression(SparqlParser.AdditiveExpressionContext ctx);
	void exitAdditiveExpression(SparqlParser.AdditiveExpressionContext ctx);

	void enterGroupCondition(SparqlParser.GroupConditionContext ctx);
	void exitGroupCondition(SparqlParser.GroupConditionContext ctx);

	void enterQuads(SparqlParser.QuadsContext ctx);
	void exitQuads(SparqlParser.QuadsContext ctx);

	void enterDataBlock(SparqlParser.DataBlockContext ctx);
	void exitDataBlock(SparqlParser.DataBlockContext ctx);

	void enterBlankNode(SparqlParser.BlankNodeContext ctx);
	void exitBlankNode(SparqlParser.BlankNodeContext ctx);

	void enterTriplesNodePath(SparqlParser.TriplesNodePathContext ctx);
	void exitTriplesNodePath(SparqlParser.TriplesNodePathContext ctx);

	void enterPropertyListPath(SparqlParser.PropertyListPathContext ctx);
	void exitPropertyListPath(SparqlParser.PropertyListPathContext ctx);

	void enterGroupClause(SparqlParser.GroupClauseContext ctx);
	void exitGroupClause(SparqlParser.GroupClauseContext ctx);

	void enterDataBlockValues(SparqlParser.DataBlockValuesContext ctx);
	void exitDataBlockValues(SparqlParser.DataBlockValuesContext ctx);

	void enterConstructTemplate(SparqlParser.ConstructTemplateContext ctx);
	void exitConstructTemplate(SparqlParser.ConstructTemplateContext ctx);

	void enterFilter(SparqlParser.FilterContext ctx);
	void exitFilter(SparqlParser.FilterContext ctx);

	void enterMultiplicativeExpression(SparqlParser.MultiplicativeExpressionContext ctx);
	void exitMultiplicativeExpression(SparqlParser.MultiplicativeExpressionContext ctx);

	void enterUpdate(SparqlParser.UpdateContext ctx);
	void exitUpdate(SparqlParser.UpdateContext ctx);

	void enterVarOrTerm(SparqlParser.VarOrTermContext ctx);
	void exitVarOrTerm(SparqlParser.VarOrTermContext ctx);

	void enterVar(SparqlParser.VarContext ctx);
	void exitVar(SparqlParser.VarContext ctx);

	void enterDeleteWhere(SparqlParser.DeleteWhereContext ctx);
	void exitDeleteWhere(SparqlParser.DeleteWhereContext ctx);

	void enterRegexExpression(SparqlParser.RegexExpressionContext ctx);
	void exitRegexExpression(SparqlParser.RegexExpressionContext ctx);

	void enterTriplesNode(SparqlParser.TriplesNodeContext ctx);
	void exitTriplesNode(SparqlParser.TriplesNodeContext ctx);

	void enterPathPrimary(SparqlParser.PathPrimaryContext ctx);
	void exitPathPrimary(SparqlParser.PathPrimaryContext ctx);

	void enterObjectList(SparqlParser.ObjectListContext ctx);
	void exitObjectList(SparqlParser.ObjectListContext ctx);

	void enterObject(SparqlParser.ObjectContext ctx);
	void exitObject(SparqlParser.ObjectContext ctx);

	void enterAdd(SparqlParser.AddContext ctx);
	void exitAdd(SparqlParser.AddContext ctx);

	void enterIri(SparqlParser.IriContext ctx);
	void exitIri(SparqlParser.IriContext ctx);

	void enterPathSequence(SparqlParser.PathSequenceContext ctx);
	void exitPathSequence(SparqlParser.PathSequenceContext ctx);

	void enterGroupGraphPatternSubList(SparqlParser.GroupGraphPatternSubListContext ctx);
	void exitGroupGraphPatternSubList(SparqlParser.GroupGraphPatternSubListContext ctx);

	void enterPathEltOrInverse(SparqlParser.PathEltOrInverseContext ctx);
	void exitPathEltOrInverse(SparqlParser.PathEltOrInverseContext ctx);

	void enterVerb(SparqlParser.VerbContext ctx);
	void exitVerb(SparqlParser.VerbContext ctx);

	void enterOptionalGraphPattern(SparqlParser.OptionalGraphPatternContext ctx);
	void exitOptionalGraphPattern(SparqlParser.OptionalGraphPatternContext ctx);

	void enterRelationalExpression(SparqlParser.RelationalExpressionContext ctx);
	void exitRelationalExpression(SparqlParser.RelationalExpressionContext ctx);

	void enterValuesClause(SparqlParser.ValuesClauseContext ctx);
	void exitValuesClause(SparqlParser.ValuesClauseContext ctx);

	void enterBlankNodePropertyList(SparqlParser.BlankNodePropertyListContext ctx);
	void exitBlankNodePropertyList(SparqlParser.BlankNodePropertyListContext ctx);

	void enterPath(SparqlParser.PathContext ctx);
	void exitPath(SparqlParser.PathContext ctx);

	void enterConditionalOrExpression(SparqlParser.ConditionalOrExpressionContext ctx);
	void exitConditionalOrExpression(SparqlParser.ConditionalOrExpressionContext ctx);

	void enterDatasetClause(SparqlParser.DatasetClauseContext ctx);
	void exitDatasetClause(SparqlParser.DatasetClauseContext ctx);

	void enterDeleteData(SparqlParser.DeleteDataContext ctx);
	void exitDeleteData(SparqlParser.DeleteDataContext ctx);

	void enterModify(SparqlParser.ModifyContext ctx);
	void exitModify(SparqlParser.ModifyContext ctx);

	void enterGraphPatternNotTriples(SparqlParser.GraphPatternNotTriplesContext ctx);
	void exitGraphPatternNotTriples(SparqlParser.GraphPatternNotTriplesContext ctx);

	void enterUnaryLiteralExpression(SparqlParser.UnaryLiteralExpressionContext ctx);
	void exitUnaryLiteralExpression(SparqlParser.UnaryLiteralExpressionContext ctx);

	void enterPropertyListPathNotEmptyList(SparqlParser.PropertyListPathNotEmptyListContext ctx);
	void exitPropertyListPathNotEmptyList(SparqlParser.PropertyListPathNotEmptyListContext ctx);

	void enterPathOneInPropertySet(SparqlParser.PathOneInPropertySetContext ctx);
	void exitPathOneInPropertySet(SparqlParser.PathOneInPropertySetContext ctx);

	void enterPathAlternative(SparqlParser.PathAlternativeContext ctx);
	void exitPathAlternative(SparqlParser.PathAlternativeContext ctx);

	void enterBind(SparqlParser.BindContext ctx);
	void exitBind(SparqlParser.BindContext ctx);

	void enterPropertyListPathNotEmpty(SparqlParser.PropertyListPathNotEmptyContext ctx);
	void exitPropertyListPathNotEmpty(SparqlParser.PropertyListPathNotEmptyContext ctx);

	void enterNil(SparqlParser.NilContext ctx);
	void exitNil(SparqlParser.NilContext ctx);
}