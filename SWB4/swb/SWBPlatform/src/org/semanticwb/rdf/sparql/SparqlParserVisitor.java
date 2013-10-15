// Generated from /programming/proys/SWB4/swb/SWBPlatform/src/org/semanticwb/rdf/sparql/SparqlParser.g4 by ANTLR 4.0
package org.semanticwb.rdf.sparql;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface SparqlParserVisitor<T> extends ParseTreeVisitor<T> {
	T visitQuadPattern(SparqlParser.QuadPatternContext ctx);

	T visitUnaryMultiplicativeExpression(SparqlParser.UnaryMultiplicativeExpressionContext ctx);

	T visitAggregate(SparqlParser.AggregateContext ctx);

	T visitUpdateCommand(SparqlParser.UpdateCommandContext ctx);

	T visitLimitOffsetClauses(SparqlParser.LimitOffsetClausesContext ctx);

	T visitArgList(SparqlParser.ArgListContext ctx);

	T visitVerbPath(SparqlParser.VerbPathContext ctx);

	T visitPrefixDecl(SparqlParser.PrefixDeclContext ctx);

	T visitExpressionList(SparqlParser.ExpressionListContext ctx);

	T visitCollectionPath(SparqlParser.CollectionPathContext ctx);

	T visitTriplesTemplate(SparqlParser.TriplesTemplateContext ctx);

	T visitInlineData(SparqlParser.InlineDataContext ctx);

	T visitSelectVariables(SparqlParser.SelectVariablesContext ctx);

	T visitSolutionModifier(SparqlParser.SolutionModifierContext ctx);

	T visitOrderCondition(SparqlParser.OrderConditionContext ctx);

	T visitGraphOrDefault(SparqlParser.GraphOrDefaultContext ctx);

	T visitMove(SparqlParser.MoveContext ctx);

	T visitCopy(SparqlParser.CopyContext ctx);

	T visitPrefixedName(SparqlParser.PrefixedNameContext ctx);

	T visitConstructQuery(SparqlParser.ConstructQueryContext ctx);

	T visitStrReplaceExpression(SparqlParser.StrReplaceExpressionContext ctx);

	T visitGroupGraphPatternSub(SparqlParser.GroupGraphPatternSubContext ctx);

	T visitObjectPath(SparqlParser.ObjectPathContext ctx);

	T visitGraphRef(SparqlParser.GraphRefContext ctx);

	T visitBuiltInCall(SparqlParser.BuiltInCallContext ctx);

	T visitSelectQuery(SparqlParser.SelectQueryContext ctx);

	T visitCreate(SparqlParser.CreateContext ctx);

	T visitVarOrIRI(SparqlParser.VarOrIRIContext ctx);

	T visitQuadsNotTriples(SparqlParser.QuadsNotTriplesContext ctx);

	T visitHavingCondition(SparqlParser.HavingConditionContext ctx);

	T visitConstructTriples(SparqlParser.ConstructTriplesContext ctx);

	T visitServiceGraphPattern(SparqlParser.ServiceGraphPatternContext ctx);

	T visitLoad(SparqlParser.LoadContext ctx);

	T visitGraphNodePath(SparqlParser.GraphNodePathContext ctx);

	T visitDeleteClause(SparqlParser.DeleteClauseContext ctx);

	T visitGroupOrUnionGraphPattern(SparqlParser.GroupOrUnionGraphPatternContext ctx);

	T visitRdfLiteral(SparqlParser.RdfLiteralContext ctx);

	T visitQuery(SparqlParser.QueryContext ctx);

	T visitGraphTerm(SparqlParser.GraphTermContext ctx);

	T visitQuadsDetails(SparqlParser.QuadsDetailsContext ctx);

	T visitSubSelect(SparqlParser.SubSelectContext ctx);

	T visitConstraint(SparqlParser.ConstraintContext ctx);

	T visitCollection(SparqlParser.CollectionContext ctx);

	T visitHavingClause(SparqlParser.HavingClauseContext ctx);

	T visitClear(SparqlParser.ClearContext ctx);

	T visitTriplesBlock(SparqlParser.TriplesBlockContext ctx);

	T visitPathMod(SparqlParser.PathModContext ctx);

	T visitOrderClause(SparqlParser.OrderClauseContext ctx);

	T visitGroupGraphPattern(SparqlParser.GroupGraphPatternContext ctx);

	T visitQuadData(SparqlParser.QuadDataContext ctx);

	T visitBaseExpression(SparqlParser.BaseExpressionContext ctx);

	T visitUsingClause(SparqlParser.UsingClauseContext ctx);

	T visitNumericLiteralPositive(SparqlParser.NumericLiteralPositiveContext ctx);

	T visitNumericLiteral(SparqlParser.NumericLiteralContext ctx);

	T visitIriRefOrFunction(SparqlParser.IriRefOrFunctionContext ctx);

	T visitDataBlockValue(SparqlParser.DataBlockValueContext ctx);

	T visitRelationalSetExpression(SparqlParser.RelationalSetExpressionContext ctx);

	T visitGraphNode(SparqlParser.GraphNodeContext ctx);

	T visitGraphRefAll(SparqlParser.GraphRefAllContext ctx);

	T visitInteger(SparqlParser.IntegerContext ctx);

	T visitInlineDataFull(SparqlParser.InlineDataFullContext ctx);

	T visitBooleanLiteral(SparqlParser.BooleanLiteralContext ctx);

	T visitNumericLiteralUnsigned(SparqlParser.NumericLiteralUnsignedContext ctx);

	T visitUnarySignedLiteralExpression(SparqlParser.UnarySignedLiteralExpressionContext ctx);

	T visitOffsetClause(SparqlParser.OffsetClauseContext ctx);

	T visitPathElt(SparqlParser.PathEltContext ctx);

	T visitInlineDataOneVar(SparqlParser.InlineDataOneVarContext ctx);

	T visitAskQuery(SparqlParser.AskQueryContext ctx);

	T visitNotExistsFunction(SparqlParser.NotExistsFunctionContext ctx);

	T visitString(SparqlParser.StringContext ctx);

	T visitBlankNodePropertyListPath(SparqlParser.BlankNodePropertyListPathContext ctx);

	T visitMinusGraphPattern(SparqlParser.MinusGraphPatternContext ctx);

	T visitExistsFunction(SparqlParser.ExistsFunctionContext ctx);

	T visitWhereClause(SparqlParser.WhereClauseContext ctx);

	T visitGraphGraphPattern(SparqlParser.GraphGraphPatternContext ctx);

	T visitLimitClause(SparqlParser.LimitClauseContext ctx);

	T visitPropertyListNotEmpty(SparqlParser.PropertyListNotEmptyContext ctx);

	T visitUnaryExpression(SparqlParser.UnaryExpressionContext ctx);

	T visitDrop(SparqlParser.DropContext ctx);

	T visitBaseDecl(SparqlParser.BaseDeclContext ctx);

	T visitPropertyList(SparqlParser.PropertyListContext ctx);

	T visitInsertClause(SparqlParser.InsertClauseContext ctx);

	T visitPrimaryExpression(SparqlParser.PrimaryExpressionContext ctx);

	T visitUnaryNegationExpression(SparqlParser.UnaryNegationExpressionContext ctx);

	T visitInsertData(SparqlParser.InsertDataContext ctx);

	T visitPathNegatedPropertySet(SparqlParser.PathNegatedPropertySetContext ctx);

	T visitSelectClause(SparqlParser.SelectClauseContext ctx);

	T visitObjectListPath(SparqlParser.ObjectListPathContext ctx);

	T visitSubStringExpression(SparqlParser.SubStringExpressionContext ctx);

	T visitFunctionCall(SparqlParser.FunctionCallContext ctx);

	T visitAnon(SparqlParser.AnonContext ctx);

	T visitTriplesSameSubjectPath(SparqlParser.TriplesSameSubjectPathContext ctx);

	T visitUnaryAdditiveExpression(SparqlParser.UnaryAdditiveExpressionContext ctx);

	T visitVerbSimple(SparqlParser.VerbSimpleContext ctx);

	T visitDescribeQuery(SparqlParser.DescribeQueryContext ctx);

	T visitTriplesSameSubject(SparqlParser.TriplesSameSubjectContext ctx);

	T visitPrologue(SparqlParser.PrologueContext ctx);

	T visitConditionalAndExpression(SparqlParser.ConditionalAndExpressionContext ctx);

	T visitNumericLiteralNegative(SparqlParser.NumericLiteralNegativeContext ctx);

	T visitAdditiveExpression(SparqlParser.AdditiveExpressionContext ctx);

	T visitGroupCondition(SparqlParser.GroupConditionContext ctx);

	T visitQuads(SparqlParser.QuadsContext ctx);

	T visitDataBlock(SparqlParser.DataBlockContext ctx);

	T visitBlankNode(SparqlParser.BlankNodeContext ctx);

	T visitTriplesNodePath(SparqlParser.TriplesNodePathContext ctx);

	T visitPropertyListPath(SparqlParser.PropertyListPathContext ctx);

	T visitGroupClause(SparqlParser.GroupClauseContext ctx);

	T visitDataBlockValues(SparqlParser.DataBlockValuesContext ctx);

	T visitConstructTemplate(SparqlParser.ConstructTemplateContext ctx);

	T visitFilter(SparqlParser.FilterContext ctx);

	T visitMultiplicativeExpression(SparqlParser.MultiplicativeExpressionContext ctx);

	T visitUpdate(SparqlParser.UpdateContext ctx);

	T visitVarOrTerm(SparqlParser.VarOrTermContext ctx);

	T visitVar(SparqlParser.VarContext ctx);

	T visitDeleteWhere(SparqlParser.DeleteWhereContext ctx);

	T visitRegexExpression(SparqlParser.RegexExpressionContext ctx);

	T visitTriplesNode(SparqlParser.TriplesNodeContext ctx);

	T visitPathPrimary(SparqlParser.PathPrimaryContext ctx);

	T visitObjectList(SparqlParser.ObjectListContext ctx);

	T visitObject(SparqlParser.ObjectContext ctx);

	T visitAdd(SparqlParser.AddContext ctx);

	T visitIri(SparqlParser.IriContext ctx);

	T visitPathSequence(SparqlParser.PathSequenceContext ctx);

	T visitGroupGraphPatternSubList(SparqlParser.GroupGraphPatternSubListContext ctx);

	T visitPathEltOrInverse(SparqlParser.PathEltOrInverseContext ctx);

	T visitVerb(SparqlParser.VerbContext ctx);

	T visitOptionalGraphPattern(SparqlParser.OptionalGraphPatternContext ctx);

	T visitRelationalExpression(SparqlParser.RelationalExpressionContext ctx);

	T visitValuesClause(SparqlParser.ValuesClauseContext ctx);

	T visitBlankNodePropertyList(SparqlParser.BlankNodePropertyListContext ctx);

	T visitPath(SparqlParser.PathContext ctx);

	T visitConditionalOrExpression(SparqlParser.ConditionalOrExpressionContext ctx);

	T visitDatasetClause(SparqlParser.DatasetClauseContext ctx);

	T visitDeleteData(SparqlParser.DeleteDataContext ctx);

	T visitModify(SparqlParser.ModifyContext ctx);

	T visitGraphPatternNotTriples(SparqlParser.GraphPatternNotTriplesContext ctx);

	T visitUnaryLiteralExpression(SparqlParser.UnaryLiteralExpressionContext ctx);

	T visitPropertyListPathNotEmptyList(SparqlParser.PropertyListPathNotEmptyListContext ctx);

	T visitPathOneInPropertySet(SparqlParser.PathOneInPropertySetContext ctx);

	T visitPathAlternative(SparqlParser.PathAlternativeContext ctx);

	T visitBind(SparqlParser.BindContext ctx);

	T visitPropertyListPathNotEmpty(SparqlParser.PropertyListPathNotEmptyContext ctx);

	T visitNil(SparqlParser.NilContext ctx);
}