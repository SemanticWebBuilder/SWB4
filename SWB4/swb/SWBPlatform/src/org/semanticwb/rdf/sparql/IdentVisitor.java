/*
 *    Copyright 2007-2012 The sparkle-g Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
/**
 * @author Simone Tripodi (simone.tripodi)
 * @author Michele Mostarda (michele.mostarda)
 * @author Juergen Pfundt (Juergen.Pfundt)
 * @version $Id: Sparql.g 523 2012-02-17 23:10:57Z Juergen.Pfundt@gmail.com $
 */
package org.semanticwb.rdf.sparql;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import org.stringtemplate.v4.*;

public class IdentVisitor extends SparqlParserBaseVisitor<ST> implements SparqlParserVisitor<ST> {

    STGroup g = null;

    public void setSTGroup(STGroup g) {
        this.g = g;
    }

    @Override
    public ST visitQuery(SparqlParser.QueryContext ctx) {
        // query :
        //   prologue (selectQuery | constructQuery | describeQuery | askQuery) bindingsClause EOF
        // | update (SEMICOLON update?)* SEMICOLON? EOF

        ST query = g.getInstanceOf("query");

        if (ctx.prologue() != null) {
            query.add("prologue", visitPrologue(ctx.prologue()));

            if (ctx.selectQuery() != null) {
                query.add("selectQuery", visitSelectQuery(ctx.selectQuery()));
            } else if (ctx.constructQuery() != null) {
                query.add("constructQuery", visitConstructQuery(ctx.constructQuery()));
            } else if (ctx.describeQuery() != null) {
                query.add("describeQuery", visitDescribeQuery(ctx.describeQuery()));
            } else if (ctx.askQuery() != null) {
                query.add("askQuery", visitAskQuery(ctx.askQuery()));
            }

            query.add("valuesClause", visitValuesClause(ctx.valuesClause()));
        } else {
            query.add("updateCommand", visitUpdateCommand(ctx.updateCommand()));
        }

        return query;
    }

    @Override
    public ST visitPrologue(SparqlParser.PrologueContext ctx) {
        // prologue :
        //   (baseDecl | prefixDecl)*

        ST prologue = g.getInstanceOf("prologue");

        if (ctx.children != null) {
            for (ParseTree c : ctx.children) {
                if (c instanceof SparqlParser.BaseDeclContext) {
                    prologue.add("baseDecl", visitBaseDecl((SparqlParser.BaseDeclContext) c));
                } else if (c instanceof SparqlParser.PrefixDeclContext) {
                    prologue.add("prefixDecl", visitPrefixDecl((SparqlParser.PrefixDeclContext) c));
                }
            }
        }

        return prologue;
    }

    @Override
    public ST visitBaseDecl(SparqlParser.BaseDeclContext ctx) {
        // baseDecl :
        //   BASE IRIREF

        ST baseDecl = g.getInstanceOf("baseDecl");

        String s = ctx.IRIREF().getSymbol().getText();
        baseDecl.add("iriref", s.substring(1, s.length() - 1));

        return baseDecl;
    }

    @Override
    public ST visitPrefixDecl(SparqlParser.PrefixDeclContext ctx) {
        // prefixDecl :
        //   PREFIX PNAME_NS IRIREF

        ST prefixDecl = g.getInstanceOf("prefixDecl");

        prefixDecl.add("pname", ctx.PNAME_NS().getSymbol().getText());

        String s = ctx.IRIREF().getSymbol().getText();
        prefixDecl.add("iriref", s.substring(1, s.length() - 1));

        return prefixDecl;
    }

    @Override
    public ST visitSelectQuery(SparqlParser.SelectQueryContext ctx) {
        // selectQuery :
        //   selectClause datasetClause* whereClause solutionModifier

        ST selectQuery = g.getInstanceOf("selectQuery");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.SelectClauseContext) {
                selectQuery.add("selectClause", visitSelectClause((SparqlParser.SelectClauseContext) c));
            } else if (c instanceof SparqlParser.DatasetClauseContext) {
                selectQuery.add("datasetClause", visitDatasetClause((SparqlParser.DatasetClauseContext) c));
            } else if (c instanceof SparqlParser.WhereClauseContext) {
                selectQuery.add("whereClause", visitWhereClause((SparqlParser.WhereClauseContext) c));
            } else if (c instanceof SparqlParser.SolutionModifierContext) {
                selectQuery.add("solutionModifier", visitSolutionModifier((SparqlParser.SolutionModifierContext) c));
            }
        }

        return selectQuery;
    }

    @Override
    public ST visitSubSelect(SparqlParser.SubSelectContext ctx) {
        // subSelect
        //   selectClause whereClause solutionModifier valuesClause

        ST subSelect = g.getInstanceOf("subSelect");

        subSelect.add("selectClause", visitSelectClause(ctx.selectClause()));
        subSelect.add("whereClause", visitWhereClause(ctx.whereClause()));
        subSelect.add("solutionModifier", visitSolutionModifier(ctx.solutionModifier()));
        subSelect.add("valuesClause", visitValuesClause(ctx.valuesClause()));

        return subSelect;
    }

    /**
     *
     * @param ctx
     * @return ST
     */
    @Override
    public ST visitSelectClause(SparqlParser.SelectClauseContext ctx) {
        // selectClause :
        //   SELECT (DISTINCT | REDUCED)? (selectVariables+ | ASTERISK)

        ST selectClause = g.getInstanceOf("selectClause");

        for (ParseTree c : ctx.children) {
            if (c instanceof TerminalNode) {
                TerminalNode t = (TerminalNode) c;
                Token to = (Token) (t.getSymbol());
                if (to.getType() == SparqlParser.ASTERISK) {
                    selectClause.add("ASTERISK", to.getText());
                } else if (to.getType() == SparqlParser.DISTINCT) {
                    selectClause.add("attribute", to.getText().toUpperCase());
                } else if (to.getType() == SparqlParser.REDUCED) {
                    selectClause.add("attribute", to.getText().toUpperCase());
                }
            } else if (c instanceof SparqlParser.SelectVariablesContext) {
                selectClause.add("selectVariables", visitSelectVariables((SparqlParser.SelectVariablesContext) c));
            }
        }

        return selectClause;
    }

    @Override
    public ST visitSelectVariables(SparqlParser.SelectVariablesContext ctx) {
        // selectVariables :
        //  var | OPEN_BRACE expression AS var CLOSE_BRACE

        ST selectVariables = g.getInstanceOf("selectVariables");

        if (ctx.expression() != null) {
            selectVariables.add("expression", visit(ctx.expression()));
        }
        if (ctx.var() != null) {
            selectVariables.add("var", visitVar(ctx.var()));
        }

        return selectVariables;
    }

    @Override
    public ST visitConstructQuery(SparqlParser.ConstructQueryContext ctx) {
        // constructQuery
        //   CONSTRUCT (constructTemplate datasetClause* whereClause solutionModifier | datasetClause* WHERE OPEN_CURLY_BRACE triplesTemplate? CLOSE_CURLY_BRACE solutionModifier)

        ST constructQuery = g.getInstanceOf("constructQuery");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.ConstructTemplateContext) {
                constructQuery.add("constructTemplate", visitConstructTemplate((SparqlParser.ConstructTemplateContext) c));
            } else if (c instanceof SparqlParser.DatasetClauseContext) {
                constructQuery.add("datasetClause", visitDatasetClause((SparqlParser.DatasetClauseContext) c));
            } else if (c instanceof SparqlParser.WhereClauseContext) {
                constructQuery.add("whereClause", visitWhereClause((SparqlParser.WhereClauseContext) c));
            } else if (c instanceof SparqlParser.TriplesTemplateContext) {
                constructQuery.add("triplesTemplate", visitTriplesTemplate((SparqlParser.TriplesTemplateContext) c));
            } else if (c instanceof SparqlParser.SolutionModifierContext) {
                constructQuery.add("solutionModifier", visitSolutionModifier((SparqlParser.SolutionModifierContext) c));
            }
        }

        return constructQuery;
    }

    @Override
    public ST visitDescribeQuery(SparqlParser.DescribeQueryContext ctx) {
        // describeQuery
        //   DESCRIBE (varOrIRI+ | ASTERISK) datasetClause* whereClause? solutionModifier

        ST describeQuery = g.getInstanceOf("describeQuery");

        for (ParseTree c : ctx.children) {
            if (c instanceof TerminalNode) {
                TerminalNode t = (TerminalNode) c;
                Token to = (Token) (t.getSymbol());
                if (to.getType() == SparqlParser.ASTERISK) {
                    describeQuery.add("ASTERISK", to.getText());
                }
            } else if (c instanceof SparqlParser.VarOrIRIContext) {
                describeQuery.add("varOrIRI", visitVarOrIRI((SparqlParser.VarOrIRIContext) c));
            } else if (c instanceof SparqlParser.DatasetClauseContext) {
                describeQuery.add("datasetClause", visitDatasetClause((SparqlParser.DatasetClauseContext) c));
            } else if (c instanceof SparqlParser.WhereClauseContext) {
                describeQuery.add("whereClause", visitWhereClause((SparqlParser.WhereClauseContext) c));
            } else if (c instanceof SparqlParser.SolutionModifierContext) {
                describeQuery.add("solutionModifier", visitSolutionModifier((SparqlParser.SolutionModifierContext) c));
            }
        }

        return describeQuery;
    }

    @Override
    public ST visitAskQuery(SparqlParser.AskQueryContext ctx) {
        // askQuery
        //   ASK datasetClause* whereClause solutionModifier

        ST askQuery = g.getInstanceOf("askQuery");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.DatasetClauseContext) {
                askQuery.add("datasetClause", visitDatasetClause((SparqlParser.DatasetClauseContext) c));
            } else if (c instanceof SparqlParser.WhereClauseContext) {
                askQuery.add("whereClause", visitWhereClause((SparqlParser.WhereClauseContext) c));
            } else if (c instanceof SparqlParser.SolutionModifierContext) {
                askQuery.add("solutionModifier", visitSolutionModifier((SparqlParser.SolutionModifierContext) c));
            }
        }

        return askQuery;
    }

    @Override
    public ST visitDatasetClause(SparqlParser.DatasetClauseContext ctx) {
        // datasetClause
        //   FROM NAMED? iri

        ST datasetClause = g.getInstanceOf("datasetClause");

        if (ctx.NAMED() != null) {
            datasetClause.add("NAMED", ctx.NAMED().getSymbol().getText().toUpperCase());
        }

        datasetClause.add("iri", visitIri(ctx.iri()));

        return datasetClause;
    }

    @Override
    public ST visitWhereClause(SparqlParser.WhereClauseContext ctx) {
        // whereClause :
        //   WHERE? groupGraphPattern

        ST whereClause = g.getInstanceOf("whereClause");

        whereClause.add("groupGraphPattern", visitGroupGraphPattern(ctx.groupGraphPattern()));

        return whereClause;
    }

    @Override
    public ST visitSolutionModifier(SparqlParser.SolutionModifierContext ctx) {
        // solutionModifier :
        //   groupClause? havingClause? orderClause? limitOffsetClauses?

        ST solutionModifier = g.getInstanceOf("solutionModifier");

        if (ctx.groupClause() != null) {
            solutionModifier.add("groupClause", visitGroupClause(ctx.groupClause()));
        }

        if (ctx.havingClause() != null) {
            solutionModifier.add("havingClause", visitHavingClause(ctx.havingClause()));
        }

        if (ctx.orderClause() != null) {
            solutionModifier.add("orderClause", visitOrderClause(ctx.orderClause()));
        }

        if (ctx.limitOffsetClauses() != null) {
            solutionModifier.add("limitOffsetClauses", visitLimitOffsetClauses(ctx.limitOffsetClauses()));
        }

        return solutionModifier;
    }

    @Override
    public ST visitGroupClause(SparqlParser.GroupClauseContext ctx) {
        // groupClause
        //   GROUP BY groupCondition+

        ST groupClause = g.getInstanceOf("groupClause");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.GroupConditionContext) {
                groupClause.add("groupCondition", visitGroupCondition((SparqlParser.GroupConditionContext) c));
            }
        }

        return groupClause;
    }

    @Override
    public ST visitGroupCondition(SparqlParser.GroupConditionContext ctx) {
        // groupCondition
        //   builtInCall | functionCall | OPEN_BRACE expression (AS var)? CLOSE_BRACE | var

        ST groupCondition = g.getInstanceOf("groupCondition");

        if (ctx.builtInCall() != null) {
            groupCondition.add("builtInCall", visitBuiltInCall(ctx.builtInCall()));
        } else if (ctx.functionCall() != null) {
            groupCondition.add("functionCall", visitFunctionCall(ctx.functionCall()));
        } else if (ctx.expression() != null) {
            groupCondition.add("expression", visit(ctx.expression()));
            if (ctx.var() != null) {
                groupCondition.add("var", visitVar(ctx.var()));
            }
        } else if (ctx.var() != null) {
            groupCondition.add("var", visitVar(ctx.var()));
        }

        return groupCondition;
    }

    @Override
    public ST visitHavingClause(SparqlParser.HavingClauseContext ctx) {
        // havingClause
        //   HAVING havingCondition+

        ST havingClause = g.getInstanceOf("havingClause");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.HavingConditionContext) {
                havingClause.add("havingCondition", visitHavingCondition((SparqlParser.HavingConditionContext) c));
            }
        }

        return havingClause;
    }

    @Override
    public ST visitHavingCondition(SparqlParser.HavingConditionContext ctx) {
        // havingCondition
        //   constraint

        ST havingCondition = g.getInstanceOf("havingCondition");

        havingCondition.add("constraint", visitConstraint(ctx.constraint()));

        return havingCondition;
    }

    @Override
    public ST visitOrderClause(SparqlParser.OrderClauseContext ctx) {
        // orderClause
        //   ORDER BY orderCondition+

        ST orderClause = g.getInstanceOf("orderClause");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.OrderConditionContext) {
                orderClause.add("orderCondition", visitOrderCondition((SparqlParser.OrderConditionContext) c));
            }
        }

        return orderClause;
    }

    @Override
    public ST visitOrderCondition(SparqlParser.OrderConditionContext ctx) {
        // orderCondition
        //   (ASC|DESC) OPEN_BRACE expression CLOSE_BRACE | constraint | var

        ST orderCondition = g.getInstanceOf("orderCondition");

        if (ctx.ASC() != null) {
            orderCondition.add("ASC", ctx.ASC().getSymbol().getText().toUpperCase());
            orderCondition.add("expression", visit(ctx.expression()));
        } else if (ctx.DESC() != null) {
            orderCondition.add("DESC", ctx.DESC().getSymbol().getText().toUpperCase());
            orderCondition.add("expression", visit(ctx.expression()));
        } else if (ctx.constraint() != null) {
            orderCondition.add("constraint", visitConstraint(ctx.constraint()));
        } else if (ctx.var() != null) {
            orderCondition.add("var", visitVar(ctx.var()));
        }

        return orderCondition;
    }

    @Override
    public ST visitLimitOffsetClauses(SparqlParser.LimitOffsetClausesContext ctx) {
        // limitOffsetClauses
        //   limitClause offsetClause? | offsetClause limitClause?

        ST limitOffsetClauses = g.getInstanceOf("limitOffsetClauses");

        if (ctx.limitClause() != null) {
            limitOffsetClauses.add("limitClause", visitLimitClause(ctx.limitClause()));
        }

        if (ctx.offsetClause() != null) {
            limitOffsetClauses.add("offsetClause", visitOffsetClause(ctx.offsetClause()));
        }

        return limitOffsetClauses;
    }

    @Override
    public ST visitLimitClause(SparqlParser.LimitClauseContext ctx) {
        // limitClause
        //   LIMIT INTEGER

        ST limitClause = g.getInstanceOf("limitClause");

        limitClause.add("limit", ctx.INTEGER().getSymbol().getText());

        return limitClause;
    }

    @Override
    public ST visitOffsetClause(SparqlParser.OffsetClauseContext ctx) {
        // offsetClause
        //   OFFSET INTEGER

        ST offsetClause = g.getInstanceOf("offsetClause");

        offsetClause.add("offset", ctx.INTEGER().getSymbol().getText());

        return offsetClause;
    }

    @Override
    public ST visitValuesClause(SparqlParser.ValuesClauseContext ctx) {
        // valuesClause :
        //  (VALUES dataBlock)?

        ST valuesClause = g.getInstanceOf("valuesClause");

        if (ctx.dataBlock() != null) {
            valuesClause.add("dataBlock", visitDataBlock(ctx.dataBlock()));
        }

        return valuesClause;
    }

    @Override
    public ST visitUpdateCommand(SparqlParser.UpdateCommandContext ctx) {
        // updateCommand :
        //  prologue (update (SEMICOLON updateCommand)?)?

        ST updateCommand = g.getInstanceOf("updateCommand");

//        updateCommand.add("prologue", visitPrologue(ctx.prologue()));
//
//        if (ctx.update() != null) {
//            updateCommand.add("update", visitUpdate(ctx.update()));
//            if (ctx.updateCommand() != null) {
//                updateCommand.add("updateCommand", visitUpdateCommand(ctx.updateCommand()));
//            }
//        }

        return updateCommand;
    }

    @Override
    public ST visitUpdate(SparqlParser.UpdateContext ctx) {
        // update :
        //   load | clear | drop | add | move | copy | create | insertData | deleteData | deleteWhere | modify

        ST update = g.getInstanceOf("update");

        if (ctx.load() != null) {
            update.add("load", visitLoad(ctx.load()));
        } else if (ctx.clear() != null) {
            update.add("clear", visitClear(ctx.clear()));
        } else if (ctx.drop() != null) {
            update.add("drop", visitDrop(ctx.drop()));
        } else if (ctx.add() != null) {
            update.add("add", visitAdd(ctx.add()));
        } else if (ctx.move() != null) {
            update.add("move", visitMove(ctx.move()));
        } else if (ctx.copy() != null) {
            update.add("copy", visitCopy(ctx.copy()));
        } else if (ctx.create() != null) {
            update.add("create", visitCreate(ctx.create()));
        } else if (ctx.insertData() != null) {
            update.add("insertData", visitInsertData(ctx.insertData()));
        } else if (ctx.deleteData() != null) {
            update.add("deleteData", visitDeleteData(ctx.deleteData()));
        } else if (ctx.deleteWhere() != null) {
            update.add("deleteWhere", visitDeleteWhere(ctx.deleteWhere()));
        } else if (ctx.modify() != null) {
            update.add("modify", visitModify(ctx.modify()));
        }

        return update;
    }

    @Override
    public ST visitLoad(SparqlParser.LoadContext ctx) {
        // load :	  
        //   LOAD SILENT? iri (INTO graphRef)?

        ST load = g.getInstanceOf("load");

        if (ctx.SILENT() != null) {
            load.add("SILENT", ctx.SILENT().getSymbol().getText().toUpperCase());
        }

        load.add("iri", visitIri(ctx.iri()));

        if (ctx.graphRef() != null) {
            load.add("graphRef", visitGraphRef(ctx.graphRef()));
        }

        return load;
    }

    @Override
    public ST visitClear(SparqlParser.ClearContext ctx) {
        // clear :
        //   CLEAR  SILENT? graphRefAll

        ST clear = g.getInstanceOf("clear");

        if (ctx.SILENT() != null) {
            clear.add("SILENT", ctx.SILENT().getSymbol().getText().toUpperCase());
        }

        clear.add("graphRefAll", visitGraphRefAll(ctx.graphRefAll()));

        return clear;
    }

    @Override
    public ST visitDrop(SparqlParser.DropContext ctx) {
        // drop :
        //   DROP SILENT? graphRefAll

        ST drop = g.getInstanceOf("drop");

        if (ctx.SILENT() != null) {
            drop.add("SILENT", ctx.SILENT().getSymbol().getText().toUpperCase());
        }

        drop.add("graphRefAll", visitGraphRefAll(ctx.graphRefAll()));

        return drop;
    }

    @Override
    public ST visitCreate(SparqlParser.CreateContext ctx) {
        // create :
        //   CREATE SILENT? graphRef

        ST create = g.getInstanceOf("create");

        if (ctx.SILENT() != null) {
            create.add("SILENT", ctx.SILENT().getSymbol().getText().toUpperCase());
        }

        create.add("graphRef", visitGraphRef(ctx.graphRef()));

        return create;
    }

    @Override
    public ST visitAdd(SparqlParser.AddContext ctx) {
        // add :
        //   ADD SILENT? graphOrDefault TO graphOrDefault

        ST add = g.getInstanceOf("add");

        if (ctx.SILENT() != null) {
            add.add("SILENT", ctx.SILENT().getSymbol().getText().toUpperCase());
        }

        add.add("graphOrDefault1", visitGraphOrDefault(ctx.graphOrDefault(0)));
        add.add("graphOrDefault2", visitGraphOrDefault(ctx.graphOrDefault(1)));

        return add;
    }

    @Override
    public ST visitMove(SparqlParser.MoveContext ctx) {
        // move :
        //   MOVE SILENT? graphOrDefault TO graphOrDefault

        ST move = g.getInstanceOf("move");

        if (ctx.SILENT() != null) {
            move.add("SILENT", ctx.SILENT().getSymbol().getText().toUpperCase());
        }

        move.add("graphOrDefault1", visitGraphOrDefault(ctx.graphOrDefault(0)));
        move.add("graphOrDefault2", visitGraphOrDefault(ctx.graphOrDefault(1)));

        return move;
    }

    @Override
    public ST visitCopy(SparqlParser.CopyContext ctx) {
        // copy :
        //   COPY SILENT? graphOrDefault TO graphOrDefault

        ST copy = g.getInstanceOf("copy");

        if (ctx.SILENT() != null) {
            copy.add("SILENT", ctx.SILENT().getSymbol().getText().toUpperCase());
        }

        copy.add("graphOrDefault1", visitGraphOrDefault(ctx.graphOrDefault(0)));
        copy.add("graphOrDefault2", visitGraphOrDefault(ctx.graphOrDefault(1)));

        return copy;
    }

    @Override
    public ST visitInsertData(SparqlParser.InsertDataContext ctx) {
        // insertData :
        //   INSERT DATA quadData

        ST insertData = g.getInstanceOf("insertData");

        insertData.add("quadData", visitQuadData(ctx.quadData()));

        return insertData;
    }

    @Override
    public ST visitDeleteData(SparqlParser.DeleteDataContext ctx) {
        // deleteData :
        //   DELETE DATA quadData

        ST deleteData = g.getInstanceOf("deleteData");

        deleteData.add("quadData", visitQuadData(ctx.quadData()));

        return deleteData;
    }

    @Override
    public ST visitDeleteWhere(SparqlParser.DeleteWhereContext ctx) {
        // deleteWhere :
        //   DELETE WHERE quadPattern

        ST deleteWhere = g.getInstanceOf("deleteWhere");

        deleteWhere.add("quadPattern", visitQuadPattern(ctx.quadPattern()));

        return deleteWhere;
    }

    @Override
    public ST visitModify(SparqlParser.ModifyContext ctx) {
        // modify :
        //   (WITH iri)? (deleteClause insertClause? | insertClause) usingClause* WHERE groupGraphPattern

        ST modify = g.getInstanceOf("modify");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.IriContext) {
                modify.add("iri", visitIri((SparqlParser.IriContext) c));
            } else if (c instanceof SparqlParser.DeleteClauseContext) {
                modify.add("deleteClause", visitDeleteClause((SparqlParser.DeleteClauseContext) c));
            } else if (c instanceof SparqlParser.InsertClauseContext) {
                modify.add("insertClause", visitInsertClause((SparqlParser.InsertClauseContext) c));
            } else if (c instanceof SparqlParser.UsingClauseContext) {
                modify.add("usingClause", visitUsingClause((SparqlParser.UsingClauseContext) c));
            } else if (c instanceof SparqlParser.GroupGraphPatternContext) {
                modify.add("groupGraphPattern", visitGroupGraphPattern((SparqlParser.GroupGraphPatternContext) c));
            }
        }

        return modify;
    }

    @Override
    public ST visitDeleteClause(SparqlParser.DeleteClauseContext ctx) {
        // deleteClause :
        //   DELETE quadPattern

        ST deleteClause = g.getInstanceOf("deleteClause");

        deleteClause.add("quadPattern", visitQuadPattern(ctx.quadPattern()));

        return deleteClause;
    }

    @Override
    public ST visitInsertClause(SparqlParser.InsertClauseContext ctx) {
        // insertClause :
        //   INSERT quadPattern

        ST insertClause = g.getInstanceOf("insertClause");

        insertClause.add("quadPattern", visitQuadPattern(ctx.quadPattern()));

        return insertClause;
    }

    @Override
    public ST visitUsingClause(SparqlParser.UsingClauseContext ctx) {
        // usingClause :
        //   USING NAMED? iri

        ST usingClause = g.getInstanceOf("usingClause");

        if (ctx.NAMED() != null) {
            usingClause.add("NAMED", ctx.NAMED().getSymbol().getText().toUpperCase());
        }

        usingClause.add("iri", visitIri(ctx.iri()));

        return usingClause;
    }

    @Override
    public ST visitGraphOrDefault(SparqlParser.GraphOrDefaultContext ctx) {
        // graphOrDefault :	  
        //   DEFAULT | GRAPH? iri

        ST graphOrDefault = g.getInstanceOf("graphOrDefault");

        if (ctx.DEFAULT() != null) {
            graphOrDefault.add("DEFAULT", ctx.DEFAULT().getSymbol().getText().toUpperCase());
        } else {
            if (ctx.GRAPH() != null) {
                graphOrDefault.add("GRAPH", ctx.GRAPH().getSymbol().getText().toUpperCase());
            }
            graphOrDefault.add("iri", visitIri(ctx.iri()));
        }

        return graphOrDefault;
    }

    @Override
    public ST visitGraphRef(SparqlParser.GraphRefContext ctx) {
        // graphRef :	  
        //   GRAPH iri

        ST graphRef = g.getInstanceOf("graphRef");

        graphRef.add("iri", visitIri(ctx.iri()));

        return graphRef;
    }

    @Override
    public ST visitGraphRefAll(SparqlParser.GraphRefAllContext ctx) {
        // graphRefAll :
        //   graphRef | DEFAULT | NAMED | ALL

        ST graphRefAll = g.getInstanceOf("graphRefAll");

        if (ctx.graphRef() != null) {
            graphRefAll.add("graphRef", visitGraphRef(ctx.graphRef()));
        } else if (ctx.DEFAULT() != null) {
            graphRefAll.add("DEFAULT", ctx.DEFAULT().getSymbol().getText().toUpperCase());
        } else if (ctx.NAMED() != null) {
            graphRefAll.add("NAMED", ctx.NAMED().getSymbol().getText().toUpperCase());
        } else if (ctx.ALL() != null) {
            graphRefAll.add("ALL", ctx.ALL().getSymbol().getText().toUpperCase());
        }

        return graphRefAll;
    }

    @Override
    public ST visitQuadPattern(SparqlParser.QuadPatternContext ctx) {
        // quadPattern :
        //   OPEN_CURLY_BRACE quads CLOSE_CURLY_BRACE

        ST quadPattern = g.getInstanceOf("quadPattern");

        quadPattern.add("quads", visitQuads(ctx.quads()));

        return quadPattern;
    }

    @Override
    public ST visitQuadData(SparqlParser.QuadDataContext ctx) {
        // quadData :
        //   OPEN_CURLY_BRACE quads CLOSE_CURLY_BRACE

        ST quadData = g.getInstanceOf("quadData");

        quadData.add("quads", visitQuads(ctx.quads()));

        return quadData;
    }

    @Override
    public ST visitQuads(SparqlParser.QuadsContext ctx) {
        // quads :
        //   triplesTemplate? quadsDetails*

        ST quads = g.getInstanceOf("quads");

        int i = 0;



        return quads;
    }

    @Override
    public ST visitQuadsDetails(SparqlParser.QuadsDetailsContext ctx) {
        // quads :
        //   quadsNotTriples DOT? triplesTemplate?

        ST quadsDetails = g.getInstanceOf("quadsDetails");

        int i = 0;

        while (i < ctx.getChildCount()) {
            ParseTree c = ctx.getChild(i++);
            if (c instanceof SparqlParser.QuadsNotTriplesContext) {
                quadsDetails.add("quadsNotTriples", visitQuadsNotTriples((SparqlParser.QuadsNotTriplesContext) c));
            } else if (c instanceof SparqlParser.TriplesTemplateContext) {
                quadsDetails.add("triplesTemplate", visitTriplesTemplate((SparqlParser.TriplesTemplateContext) c));
            }
        }

        return quadsDetails;
    }

    @Override
    public ST visitQuadsNotTriples(SparqlParser.QuadsNotTriplesContext ctx) {
        // quadsNotTriples :
        //   GRAPH varOrIRI OPEN_CURLY_BRACE triplesTemplate? CLOSE_CURLY_BRACE

        ST quadsNotTriples = g.getInstanceOf("quadsNotTriples");

        quadsNotTriples.add("varOrIRI", visitVarOrIRI(ctx.varOrIRI()));

        if (ctx.triplesTemplate() != null) {
            quadsNotTriples.add("triplesTemplate", visitTriplesTemplate(ctx.triplesTemplate()));
        }

        return quadsNotTriples;
    }

    @Override
    public ST visitTriplesTemplate(SparqlParser.TriplesTemplateContext ctx) {
        // triplesTemplate :
        //   triplesSameSubject (DOT triplesSameSubject?)*

        ST triplesTemplate = g.getInstanceOf("triplesTemplate");

        int i = 0;

        while (i < ctx.getChildCount()) {
            ParseTree c = ctx.getChild(i++);
            if (c instanceof SparqlParser.TriplesSameSubjectContext) {
                triplesTemplate.add("triplesSameSubject", visitTriplesSameSubject((SparqlParser.TriplesSameSubjectContext) c));
            }
        }

        return triplesTemplate;
    }

    @Override
    public ST visitGroupGraphPattern(SparqlParser.GroupGraphPatternContext ctx) {
        // groupGraphPattern :
        //   OPEN_CURLY_BRACE (subSelect | groupGraphPatternSub) CLOSE_CURLY_BRACE

        ST groupGraphPattern = g.getInstanceOf("groupGraphPattern");

        if (ctx.subSelect() != null) {
            groupGraphPattern.add("subSelect", visitSubSelect(ctx.subSelect()));
        } else if (ctx.groupGraphPatternSub() != null) {
            groupGraphPattern.add("groupGraphPatternSub", visitGroupGraphPatternSub(ctx.groupGraphPatternSub()));
        }

        return groupGraphPattern;
    }

    @Override
    public ST visitGroupGraphPatternSub(SparqlParser.GroupGraphPatternSubContext ctx) {
        // groupGraphPatternSub :
        //   triplesBlock? groupGraphPatternSubList*

        ST groupGraphPatternSub = g.getInstanceOf("groupGraphPatternSub");

        if (ctx.children != null) {
            for (ParseTree c : ctx.children) {
                if (c instanceof SparqlParser.GroupGraphPatternSubListContext) {
                    groupGraphPatternSub.add("groupGraphPatternSubList", visitGroupGraphPatternSubList((SparqlParser.GroupGraphPatternSubListContext) c));
                } else if (c instanceof SparqlParser.TriplesBlockContext) {
                    groupGraphPatternSub.add("triplesBlock", visitTriplesBlock((SparqlParser.TriplesBlockContext) c));
                }
            }
        }

        return groupGraphPatternSub;
    }

    @Override
    public ST visitGroupGraphPatternSubList(SparqlParser.GroupGraphPatternSubListContext ctx) {
        // groupGraphPatternSubList :
        //    graphPatternNotTriples DOT? triplesBlock?

        ST groupGraphPatternSubList = g.getInstanceOf("groupGraphPatternSubList");

        groupGraphPatternSubList.add("graphPatternNotTriples", visitGraphPatternNotTriples(ctx.graphPatternNotTriples()));

        if (ctx.triplesBlock() != null) {
            groupGraphPatternSubList.add("triplesBlock", visitTriplesBlock(ctx.triplesBlock()));
        }

        return groupGraphPatternSubList;
    }

    @Override
    public ST visitTriplesBlock(SparqlParser.TriplesBlockContext ctx) {
        // triplesBlock :
        //   triplesSameSubjectPath (DOT triplesSameSubjectPath?)*

        ST triplesBlock = g.getInstanceOf("triplesBlock");

        int i = 0;
        while (i < ctx.getChildCount()) {
            ParseTree c = ctx.getChild(i++);
            if (c instanceof SparqlParser.TriplesSameSubjectPathContext) {
                triplesBlock.add("triplesSameSubjectPath", visitTriplesSameSubjectPath((SparqlParser.TriplesSameSubjectPathContext) c));
            }
        }

        return triplesBlock;
    }

    @Override
    public ST visitGraphPatternNotTriples(SparqlParser.GraphPatternNotTriplesContext ctx) {
        // graphPatternNotTriples :
        //   groupOrUnionGraphPattern | optionalGraphPattern | minusGraphPattern | graphGraphPattern | serviceGraphPattern | filter | bind | inlineData

        ST graphPatternNotTriples = g.getInstanceOf("graphPatternNotTriples");

        if (ctx.groupOrUnionGraphPattern() != null) {
            graphPatternNotTriples.add("groupOrUnionGraphPattern", visitGroupOrUnionGraphPattern(ctx.groupOrUnionGraphPattern()));
        } else if (ctx.optionalGraphPattern() != null) {
            graphPatternNotTriples.add("optionalGraphPattern", visitOptionalGraphPattern(ctx.optionalGraphPattern()));
        } else if (ctx.minusGraphPattern() != null) {
            graphPatternNotTriples.add("minusGraphPattern", visitMinusGraphPattern(ctx.minusGraphPattern()));
        } else if (ctx.graphGraphPattern() != null) {
            graphPatternNotTriples.add("graphGraphPattern", visitGraphGraphPattern(ctx.graphGraphPattern()));
        } else if (ctx.serviceGraphPattern() != null) {
            graphPatternNotTriples.add("serviceGraphPattern", visitServiceGraphPattern(ctx.serviceGraphPattern()));
        } else if (ctx.filter() != null) {
            graphPatternNotTriples.add("filter", visitFilter(ctx.filter()));
        } else if (ctx.bind() != null) {
            graphPatternNotTriples.add("bind", visitBind(ctx.bind()));
        } else if (ctx.inlineData() != null) {
            graphPatternNotTriples.add("inlineData", visitInlineData(ctx.inlineData()));
        }

        return graphPatternNotTriples;
    }

    @Override
    public ST visitOptionalGraphPattern(SparqlParser.OptionalGraphPatternContext ctx) {
        // optionalGraphPattern :
        //   OPTIONAL groupGraphPattern

        ST optionalGraphPattern = g.getInstanceOf("optionalGraphPattern");

        optionalGraphPattern.add("groupGraphPattern", visitGroupGraphPattern(ctx.groupGraphPattern()));

        return optionalGraphPattern;
    }

    @Override
    public ST visitGraphGraphPattern(SparqlParser.GraphGraphPatternContext ctx) {
        // graphGraphPattern :
        //   GRAPH varOrIRIref groupGraphPattern

        ST graphGraphPattern = g.getInstanceOf("graphGraphPattern");

        graphGraphPattern.add("varOrIRI", visitVarOrIRI(ctx.varOrIRI()));

        graphGraphPattern.add("groupGraphPattern", visitGroupGraphPattern(ctx.groupGraphPattern()));

        return graphGraphPattern;
    }

    @Override
    public ST visitServiceGraphPattern(SparqlParser.ServiceGraphPatternContext ctx) {
        // serviceGraphPattern :
        //   SERVICE SILENT? varOrIRI groupGraphPattern

        ST serviceGraphPattern = g.getInstanceOf("serviceGraphPattern");

        if (ctx.SILENT() != null) {
            serviceGraphPattern.add("SILENT", ctx.SILENT().getSymbol().getText());
        }

        serviceGraphPattern.add("varOrIRI", visitVarOrIRI(ctx.varOrIRI()));

        serviceGraphPattern.add("groupGraphPattern", visitGroupGraphPattern(ctx.groupGraphPattern()));

        return serviceGraphPattern;
    }

    @Override
    public ST visitBind(SparqlParser.BindContext ctx) {
        // bind :
        //  BIND OPEN_BRACE expression AS var CLOSE_BRACE

        ST bind = g.getInstanceOf("bind");

        bind.add("expression", visit(ctx.expression()));

        bind.add("var", visitVar(ctx.var()));

        return bind;
    }

    @Override
    public ST visitInlineData(SparqlParser.InlineDataContext ctx) {
        // inlineData :
        //  VALUES dataBlock

        ST inlineData = g.getInstanceOf("inlineData");

        inlineData.add("dataBlock", visitDataBlock(ctx.dataBlock()));

        return inlineData;
    }

    @Override
    public ST visitDataBlock(SparqlParser.DataBlockContext ctx) {
        // dataBlock :
        //  inlineDataOneVar | inlineDataFull

        ST dataBlock = g.getInstanceOf("dataBlock");

        if (ctx.inlineDataOneVar() != null) {
            dataBlock.add("inlineDataOneVar", visitInlineDataOneVar(ctx.inlineDataOneVar()));
        } else if (ctx.inlineDataFull() != null) {
            dataBlock.add("inlineDataFull", visitInlineDataFull(ctx.inlineDataFull()));
        }

        return dataBlock;
    }

    @Override
    public ST visitInlineDataOneVar(SparqlParser.InlineDataOneVarContext ctx) {
        // inlineDataOneVar :
        //  var OPEN_CURLY_BRACE dataBlockValue* CLOSE_CURLY_BRACE

        ST inlineDataOneVar = g.getInstanceOf("inlineDataOneVar");

        if (ctx.children != null) {
            for (ParseTree c : ctx.children) {
                if (c instanceof SparqlParser.DataBlockValueContext) {
                    inlineDataOneVar.add("dataBlockValue", visitDataBlockValue((SparqlParser.DataBlockValueContext) c));
                } else if (c instanceof SparqlParser.VarContext) {
                    inlineDataOneVar.add("var", visitVar((SparqlParser.VarContext) c));
                }
            }
        }
        return inlineDataOneVar;
    }

    @Override
    public ST visitInlineDataFull(SparqlParser.InlineDataFullContext ctx) {
        // inlineDataFull :
        //  (OPEN_BRACE var* CLOSE_BRACE) OPEN_CURLY_BRACE dataBlockValues* CLOSE_CURLY_BRACE

        ST inlineDataFull = g.getInstanceOf("inlineDataFull");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.VarContext) {
                inlineDataFull.add("var", visitVar((SparqlParser.VarContext) c));
            } else if (c instanceof SparqlParser.DataBlockValuesContext) {
                inlineDataFull.add("dataBlockValues", visitDataBlockValues((SparqlParser.DataBlockValuesContext) c));
            }
        }

        return inlineDataFull;
    }

    @Override
    public ST visitDataBlockValues(SparqlParser.DataBlockValuesContext ctx) {
        // dataBlockValues :
        //  OPEN_BRACE dataBlockValue* CLOSE_BRACE

        ST dataBlockValues = g.getInstanceOf("dataBlockValues");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.DataBlockValueContext) {
                dataBlockValues.add("dataBlockValue", visitDataBlockValue((SparqlParser.DataBlockValueContext) c));
            }
        }

        return dataBlockValues;
    }

    @Override
    public ST visitDataBlockValue(SparqlParser.DataBlockValueContext ctx) {
        // dataBlockValue :
        //  iri | rdfLiteral | numericLiteral | booleanLiteral | UNDEF

        ST dataBlockValue = g.getInstanceOf("dataBlockValue");

        if (ctx.iri() != null) {
            dataBlockValue.add("iri", visitIri(ctx.iri()));
        } else if (ctx.rdfLiteral() != null) {
            dataBlockValue.add("rdfLiteral", visitRdfLiteral(ctx.rdfLiteral()));
        } else if (ctx.numericLiteral() != null) {
            dataBlockValue.add("numericLiteral", visitNumericLiteral(ctx.numericLiteral()));
        } else if (ctx.booleanLiteral() != null) {
            dataBlockValue.add("booleanLiteral", visitBooleanLiteral(ctx.booleanLiteral()));
        } else if (ctx.UNDEF() != null) {
            dataBlockValue.add("UNDEF", ctx.UNDEF().getSymbol().getText());
        }

        return dataBlockValue;
    }

    @Override
    public ST visitMinusGraphPattern(SparqlParser.MinusGraphPatternContext ctx) {
        // minusGraphPattern :
        //   MINUS groupGraphPattern

        ST minusGraphPattern = g.getInstanceOf("minusGraphPattern");

        minusGraphPattern.add("groupGraphPattern", visitGroupGraphPattern(ctx.groupGraphPattern()));

        return minusGraphPattern;
    }

    @Override
    public ST visitGroupOrUnionGraphPattern(SparqlParser.GroupOrUnionGraphPatternContext ctx) {
        // groupOrUnionGraphPattern :
        //   groupGraphPattern (UNION groupGraphPattern)*

        ST groupOrUnionGraphPattern = g.getInstanceOf("groupOrUnionGraphPattern");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.GroupGraphPatternContext) {
                groupOrUnionGraphPattern.add("groupGraphPattern", visitGroupGraphPattern((SparqlParser.GroupGraphPatternContext) c));
            }
        }

        return groupOrUnionGraphPattern;
    }

    @Override
    public ST visitFilter(SparqlParser.FilterContext ctx) {
        // filter :
        //   FILTER constraint

        ST filter = g.getInstanceOf("filter");

        filter.add("constraint", visitConstraint(ctx.constraint()));

        return filter;
    }

    @Override
    public ST visitConstraint(SparqlParser.ConstraintContext ctx) {
        // constraint :
        //   '(' expression ')' | builtInCall | functionCall

        ST constraint = g.getInstanceOf("constraint");

        if (ctx.expression() != null) {
            constraint.add("expression", visit(ctx.expression()));
        } else if (ctx.builtInCall() != null) {
            constraint.add("builtInCall", visitBuiltInCall(ctx.builtInCall()));
        } else if (ctx.functionCall() != null) {
            constraint.add("functionCall", visitFunctionCall(ctx.functionCall()));
        }

        return constraint;
    }

    @Override
    public ST visitFunctionCall(SparqlParser.FunctionCallContext ctx) {
        // functionCall :
        //   iri argList

        ST functionCall = g.getInstanceOf("functionCall");

        functionCall.add("iri", visitIri(ctx.iri()));

        functionCall.add("argList", visitArgList(ctx.argList()));

        return functionCall;
    }

    @Override
    public ST visitArgList(SparqlParser.ArgListContext ctx) {
        // argList :
        //   OPEN_BRACE (DISTINCT? expressionList|) CLOSE_BRACE

        ST argList = g.getInstanceOf("argList");

        if (ctx.DISTINCT() != null) {
            argList.add("DISTINCT", ctx.DISTINCT().getSymbol().getText());
        }

        if (ctx.expressionList() != null) {
            argList.add("expressionList", visitExpressionList(ctx.expressionList()));
        }

        return argList;
    }

    @Override
    public ST visitExpressionList(SparqlParser.ExpressionListContext ctx) {
        // expressionList :
        //   expression (COMMA expression)*

        ST expressionList = g.getInstanceOf("expressionList");

        if (ctx.children != null) {
            for (ParseTree c : ctx.children) {
                if (c instanceof SparqlParser.ExpressionContext) {
                    expressionList.add("expression", visit((SparqlParser.ExpressionContext) c));
                }
            }
        }

        return expressionList;
    }

    @Override
    public ST visitConstructTemplate(SparqlParser.ConstructTemplateContext ctx) {
        // constructTemplate :
        //   OPEN_CURLY_BRACE constructTriples? CLOSE_CURLY_BRACE

        ST constructTemplate = g.getInstanceOf("constructTemplate");

        if (ctx.constructTriples() != null) {
            constructTemplate.add("constructTriples", visitConstructTriples(ctx.constructTriples()));
        }

        return constructTemplate;
    }

    @Override
    public ST visitConstructTriples(SparqlParser.ConstructTriplesContext ctx) {
        // constructTriples :
        //   triplesSameSubject (DOT constructTriples?)* 

        ST constructTriples = g.getInstanceOf("constructTriples");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.ConstructTriplesContext) {
                constructTriples.add("constructTriples", visitConstructTriples((SparqlParser.ConstructTriplesContext) c));
            } else if (c instanceof SparqlParser.TriplesSameSubjectContext) {
                constructTriples.add("triplesSameSubject", visitTriplesSameSubject((SparqlParser.TriplesSameSubjectContext) c));
            }
        }

        return constructTriples;
    }

    @Override
    public ST visitTriplesSameSubject(SparqlParser.TriplesSameSubjectContext ctx) {
        // triplesSameSubject :
        //   varOrTerm propertyListNotEmpty | triplesNode propertyList 

        ST triplesSameSubject = g.getInstanceOf("triplesSameSubject");

        if (ctx.varOrTerm() != null) {
            triplesSameSubject.add("varOrTerm", visitVarOrTerm(ctx.varOrTerm()));
            triplesSameSubject.add("propertyListNotEmpty", visitPropertyListNotEmpty(ctx.propertyListNotEmpty()));
        } else {
            triplesSameSubject.add("triplesNode", visitTriplesNode(ctx.triplesNode()));
            triplesSameSubject.add("propertyList", visitPropertyList(ctx.propertyList()));
        }

        return triplesSameSubject;
    }

    @Override
    public ST visitPropertyList(SparqlParser.PropertyListContext ctx) {
        // propertyList
        //   propertyListNotEmpty?

        ST propertyList = g.getInstanceOf("propertyList");

        if (ctx.propertyListNotEmpty() != null) {
            propertyList.add("propertyListNotEmpty", visitPropertyListNotEmpty(ctx.propertyListNotEmpty()));
        }

        return propertyList;
    }

    @Override
    public ST visitPropertyListNotEmpty(SparqlParser.PropertyListNotEmptyContext ctx) {
        // propertyListNotEmpty :
        //   verb objectList (SEMICOLON (verb objectList)?)* 

        ST propertyListNotEmpty = g.getInstanceOf("propertyListNotEmpty");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.VerbContext) {
                propertyListNotEmpty.add("verb", visitVerb((SparqlParser.VerbContext) c));
            } else if (c instanceof SparqlParser.ObjectListContext) {
                propertyListNotEmpty.add("objectList", visitObjectList((SparqlParser.ObjectListContext) c));
            }
        }

        return propertyListNotEmpty;
    }

    @Override
    public ST visitVerb(SparqlParser.VerbContext ctx) {
        // verb :
        //   varOrIRI | A

        ST verb = g.getInstanceOf("verb");

        if (ctx.varOrIRI() != null) {
            verb.add("varOrIRI", visitVarOrIRI(ctx.varOrIRI()));
        } else if (ctx.A() != null) {
            verb.add("A", ctx.A().getSymbol().getText());
        }

        return verb;
    }

    @Override
    public ST visitObjectList(SparqlParser.ObjectListContext ctx) {
        // objectList :
        //   object (COMMA object)*

        ST objectList = g.getInstanceOf("objectList");

        if (ctx != null) {
            for (ParseTree c : ctx.children) {
                if (c instanceof SparqlParser.ObjectContext) {
                    objectList.add("object", visitObject((SparqlParser.ObjectContext) c));
                }
            }
        }
        return objectList;
    }

    @Override
    public ST visitObject(SparqlParser.ObjectContext ctx) {
        // object :
        //   graphNode

        ST object = g.getInstanceOf("object");

        object.add("graphNode", visitGraphNode(ctx.graphNode()));

        return object;
    }

    @Override
    public ST visitTriplesSameSubjectPath(SparqlParser.TriplesSameSubjectPathContext ctx) {
        // triplesSameSubjectPath :
        //   varOrTerm propertyListPathNotEmpty | triplesNodePath propertyListPath

        ST triplesSameSubjectPath = g.getInstanceOf("triplesSameSubjectPath");

        if (ctx.varOrTerm() != null) {
            triplesSameSubjectPath.add("varOrTerm", visitVarOrTerm(ctx.varOrTerm()));
            triplesSameSubjectPath.add("propertyListPathNotEmpty", visitPropertyListPathNotEmpty(ctx.propertyListPathNotEmpty()));
        } else {
            triplesSameSubjectPath.add("triplesNodePath", visitTriplesNodePath(ctx.triplesNodePath()));
            triplesSameSubjectPath.add("propertyListPath", visitPropertyListPath(ctx.propertyListPath()));
        }

        return triplesSameSubjectPath;
    }

    @Override
    public ST visitPropertyListPath(SparqlParser.PropertyListPathContext ctx) {
        // propertyListPath :
        //   propertyListPathNotEmpty?

        ST propertyListPath = g.getInstanceOf("propertyListPath");

        if (ctx.propertyListPathNotEmpty() != null) {
            propertyListPath.add("propertyListPathNotEmpty", visitPropertyListPathNotEmpty(ctx.propertyListPathNotEmpty()));
        }

        return propertyListPath;
    }

    @Override
    public ST visitPropertyListPathNotEmpty(SparqlParser.PropertyListPathNotEmptyContext ctx) {
        // propertyListPathNotEmpty :
        //  (verbPath|verbSimple) objectListPath (SEMICOLON propertyListPathNotEmptyList?)* SEMICOLON?

        ST propertyListPathNotEmpty = g.getInstanceOf("propertyListPathNotEmpty");

        if (ctx.verbPath() != null) {
            propertyListPathNotEmpty.add("verbPath", visitVerbPath(ctx.verbPath()));
        } else if (ctx.verbSimple() != null) {
            propertyListPathNotEmpty.add("verbSimple", visitVerbSimple(ctx.verbSimple()));
        }

        propertyListPathNotEmpty.add("objectListPath", visitObjectListPath(ctx.objectListPath()));

        for (int i = 3; i < ctx.getChildCount(); i++) {
            ParseTree c = ctx.getChild(i);
            if (c instanceof SparqlParser.PropertyListPathNotEmptyListContext) {
                propertyListPathNotEmpty.add("propertyListPathNotEmptyList", visitPropertyListPathNotEmptyList((SparqlParser.PropertyListPathNotEmptyListContext) c));
            }
        }

        return propertyListPathNotEmpty;
    }

    @Override
    public ST visitPropertyListPathNotEmptyList(SparqlParser.PropertyListPathNotEmptyListContext ctx) {
        // propertyListPathNotEmptyList :
        //  (verbPath|verbSimple) objectList

        ST propertyListPathNotEmptyList = g.getInstanceOf("propertyListPathNotEmptyList");

        if (ctx.verbPath() != null) {
            propertyListPathNotEmptyList.add("verbPath", visitVerbPath(ctx.verbPath()));
        } else if (ctx.verbSimple() != null) {
            propertyListPathNotEmptyList.add("verbSimple", visitVerbSimple(ctx.verbSimple()));
        }

        propertyListPathNotEmptyList.add("objectList", visitObjectList(ctx.objectList()));

        return propertyListPathNotEmptyList;
    }

    @Override
    public ST visitVerbPath(SparqlParser.VerbPathContext ctx) {
        // verbPath :
        //   path

        ST verbPath = g.getInstanceOf("verbPath");

        verbPath.add("path", visitPath(ctx.path()));

        return verbPath;
    }

    @Override
    public ST visitVerbSimple(SparqlParser.VerbSimpleContext ctx) {
        // verbSimple :
        //   var

        ST verbSimple = g.getInstanceOf("verbSimple");

        verbSimple.add("var", visitVar(ctx.var()));

        return verbSimple;
    }

    @Override
    public ST visitObjectListPath(SparqlParser.ObjectListPathContext ctx) {
        // objectListPath :
        //   objectPath (COMMA objectPath)*

        ST objectListPath = g.getInstanceOf("objectListPath");

        if (ctx != null) {
            for (ParseTree c : ctx.children) {
                if (c instanceof SparqlParser.ObjectPathContext) {
                    objectListPath.add("objectPath", visitObjectPath((SparqlParser.ObjectPathContext) c));
                }
            }
        }
        return objectListPath;
    }

    @Override
    public ST visitObjectPath(SparqlParser.ObjectPathContext ctx) {
        // objectPath :
        //   graphNodePath

        ST objectPath = g.getInstanceOf("objectPath");

        objectPath.add("graphNodePath", visitGraphNodePath(ctx.graphNodePath()));

        return objectPath;
    }

    @Override
    public ST visitPath(SparqlParser.PathContext ctx) {
        // path :
        //   pathAlternative

        ST path = g.getInstanceOf("path");

        path.add("pathAlternative", visitPathAlternative(ctx.pathAlternative()));

        return path;
    }

    @Override
    public ST visitPathAlternative(SparqlParser.PathAlternativeContext ctx) {
        // pathAlternative :
        //   pathSequence (PIPE pathSequence)*

        ST pathAlternative = g.getInstanceOf("pathAlternative");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.PathSequenceContext) {
                pathAlternative.add("pathSequence", visitPathSequence((SparqlParser.PathSequenceContext) c));
            }
        }

        return pathAlternative;
    }

    @Override
    public ST visitPathSequence(SparqlParser.PathSequenceContext ctx) {
        // pathSequence :
        //   pathEltOrInverse (DIVIDE pathEltOrInverse)*

        ST pathSequence = g.getInstanceOf("pathSequence");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.PathEltOrInverseContext) {
                pathSequence.add("pathEltOrInverse", visitPathEltOrInverse((SparqlParser.PathEltOrInverseContext) c));
            }
        }

        return pathSequence;
    }

    @Override
    public ST visitPathElt(SparqlParser.PathEltContext ctx) {
        // pathElt :
        //   pathPrimary pathMod?

        ST pathElt = g.getInstanceOf("pathElt");

        pathElt.add("pathPrimary", visitPathPrimary(ctx.pathPrimary()));

        if (ctx.pathMod() != null) {
            pathElt.add("pathMod", visitPathMod(ctx.pathMod()));
        }

        return pathElt;
    }

    @Override
    public ST visitPathEltOrInverse(SparqlParser.PathEltOrInverseContext ctx) {
        // pathEltOrInverse :
        //   INVERSE? pathElt

        ST pathEltOrInverse = g.getInstanceOf("pathEltOrInverse");

        if (ctx.INVERSE() != null) {
            pathEltOrInverse.add("INVERSE", ctx.INVERSE().getSymbol().getText());
        }

        pathEltOrInverse.add("pathElt", visitPathElt(ctx.pathElt()));

        return pathEltOrInverse;
    }

    @Override
    public ST visitPathMod(SparqlParser.PathModContext ctx) {
        // pathMod :
        //   QUESTION_MARK | ASTERISK | PLUS_SIGN 

        ST pathMod = g.getInstanceOf("pathMod");

        if (ctx.op.getType() == SparqlParser.QUESTION_MARK) {
            pathMod.add("QUESTION_MARK", "?");
        } else if (ctx.op.getType() == SparqlParser.ASTERISK) {
            pathMod.add("ASTERISK", "*");
        } else if (ctx.op.getType() == SparqlParser.PLUS_SIGN) {
            pathMod.add("PLUS", "+");
        }

        return pathMod;
    }

    @Override
    public ST visitPathPrimary(SparqlParser.PathPrimaryContext ctx) {
        // pathPrimary :
        //   iri | A | NEGATION pathNegatedPropertySet | OPEN_BRACE path CLOSE_BRACE

        ST pathPrimary = g.getInstanceOf("pathPrimary");

        if (ctx.iri() != null) {
            pathPrimary.add("iri", visitIri(ctx.iri()));
        } else if (ctx.A() != null) {
            pathPrimary.add("A", ctx.A().getSymbol().getText());
        } else if (ctx.pathNegatedPropertySet() != null) {
            pathPrimary.add("pathNegatedPropertySet", visitPathNegatedPropertySet(ctx.pathNegatedPropertySet()));
        } else if (ctx.path() != null) {
            pathPrimary.add("path", visitPath(ctx.path()));
        }

        return pathPrimary;
    }

    @Override
    public ST visitPathNegatedPropertySet(SparqlParser.PathNegatedPropertySetContext ctx) {
        // pathNegatedPropertySet
        //   pathOneInPropertySet | OPEN_BRACE (pathOneInPropertySet (PIPE pathOneInPropertySet)*)? CLOSE_BRACE

        ST pathNegatedPropertySet = g.getInstanceOf("pathNegatedPropertySet");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.PathOneInPropertySetContext) {
                pathNegatedPropertySet.add("pathOneInPropertySet", visitPathOneInPropertySet((SparqlParser.PathOneInPropertySetContext) c));
            }
        }

        return pathNegatedPropertySet;
    }

    @Override
    public ST visitPathOneInPropertySet(SparqlParser.PathOneInPropertySetContext ctx) {
        // pathOneInPropertySet :
        //   INVERSE? (iri | A)

        ST pathOneInPropertySet = g.getInstanceOf("pathOneInPropertySet");

        if (ctx.INVERSE() != null) {
            pathOneInPropertySet.add("INVERSE", ctx.INVERSE().getSymbol().getText());
        }

        if (ctx.iri() != null) {
            pathOneInPropertySet.add("iri", visitIri(ctx.iri()));
        } else if (ctx.A() != null) {
            pathOneInPropertySet.add("A", ctx.A().getSymbol().getText());
        }

        return pathOneInPropertySet;
    }

    @Override
    public ST visitInteger(SparqlParser.IntegerContext ctx) {
        // integer :
        //   INTEGER

        ST integer = g.getInstanceOf("integer");

        integer.add("INTEGER", ctx.INTEGER().getSymbol().getText());

        return integer;
    }

    @Override
    public ST visitTriplesNode(SparqlParser.TriplesNodeContext ctx) {
        // triplesNode :
        //   collection | blankNodePropertyList

        ST triplesNode = g.getInstanceOf("triplesNode");

        if (ctx.collection() != null) {
            triplesNode.add("collection", visitCollection(ctx.collection()));
        } else if (ctx.blankNodePropertyList() != null) {
            triplesNode.add("blankNodePropertyList", visitBlankNodePropertyList(ctx.blankNodePropertyList()));
        }

        return triplesNode;
    }

    @Override
    public ST visitBlankNodePropertyList(SparqlParser.BlankNodePropertyListContext ctx) {
        // blankNodePropertyList :
        //   OPEN_SQUARE_BRACKET propertyListNotEmpty CLOSE_SQUARE_BRACKET

        ST blankNodePropertyList = g.getInstanceOf("blankNodePropertyList");

        blankNodePropertyList.add("propertyListNotEmpty", visitPropertyListNotEmpty(ctx.propertyListNotEmpty()));

        return blankNodePropertyList;
    }

    @Override
    public ST visitTriplesNodePath(SparqlParser.TriplesNodePathContext ctx) {
        // triplesNodePath :
        //   collectionPath | blankNodePropertyListPath

        ST triplesNodePath = g.getInstanceOf("triplesNodePath");

        if (ctx.collectionPath() != null) {
            triplesNodePath.add("collectionPath", visitCollectionPath(ctx.collectionPath()));
        } else if (ctx.blankNodePropertyListPath() != null) {
            triplesNodePath.add("blankNodePropertyListPath", visitBlankNodePropertyListPath(ctx.blankNodePropertyListPath()));
        }

        return triplesNodePath;
    }

    @Override
    public ST visitBlankNodePropertyListPath(SparqlParser.BlankNodePropertyListPathContext ctx) {
        // blankNodePropertyListPath :
        //   OPEN_SQUARE_BRACKET propertyListPathNotEmpty CLOSE_SQUARE_BRACKET

        ST blankNodePropertyListPath = g.getInstanceOf("blankNodePropertyListPath");

        blankNodePropertyListPath.add("propertyListPathNotEmpty", visitPropertyListPathNotEmpty(ctx.propertyListPathNotEmpty()));

        return blankNodePropertyListPath;
    }

    @Override
    public ST visitCollection(SparqlParser.CollectionContext ctx) {
        // collection :
        //   OPEN_BRACE graphNode+ CLOSE_BRACE

        ST collection = g.getInstanceOf("collection");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.GraphNodeContext) {
                collection.add("graphNode", visitGraphNode((SparqlParser.GraphNodeContext) c));
            }
        }

        return collection;
    }

    @Override
    public ST visitCollectionPath(SparqlParser.CollectionPathContext ctx) {
        // collectionPath :
        //   OPEN_BRACE graphNodePath+ CLOSE_BRACE

        ST collectionPath = g.getInstanceOf("collectionPath");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.GraphNodePathContext) {
                collectionPath.add("graphNodePath", visitGraphNodePath((SparqlParser.GraphNodePathContext) c));
            }
        }

        return collectionPath;
    }

    @Override
    public ST visitGraphNode(SparqlParser.GraphNodeContext ctx) {
        // graphNode :
        //   varOrTerm | triplesNode

        ST graphNode = g.getInstanceOf("graphNode");

        if (ctx.varOrTerm() != null) {
            graphNode.add("varOrTerm", visitVarOrTerm(ctx.varOrTerm()));
        } else if (ctx.triplesNode() != null) {
            graphNode.add("triplesNode", visitTriplesNode(ctx.triplesNode()));
        }

        return graphNode;
    }

    @Override
    public ST visitGraphNodePath(SparqlParser.GraphNodePathContext ctx) {
        // graphNodePath :
        //   varOrTerm | triplesNodePath

        ST graphNodePath = g.getInstanceOf("graphNodePath");

        if (ctx.varOrTerm() != null) {
            graphNodePath.add("varOrTerm", visitVarOrTerm(ctx.varOrTerm()));
        } else if (ctx.triplesNodePath() != null) {
            graphNodePath.add("triplesNodePath", visitTriplesNodePath(ctx.triplesNodePath()));
        }

        return graphNodePath;
    }

    @Override
    public ST visitVarOrTerm(SparqlParser.VarOrTermContext ctx) {
        // varOrTerm :
        //   var | graphTerm

        ST varOrTerm = g.getInstanceOf("varOrTerm");

        if (ctx.var() != null) {
            varOrTerm.add("var", visitVar(ctx.var()));
        } else if (ctx.graphTerm() != null) {
            varOrTerm.add("graphTerm", visitGraphTerm(ctx.graphTerm()));
        }

        return varOrTerm;
    }

    @Override
    public ST visitVarOrIRI(SparqlParser.VarOrIRIContext ctx) {
        // varOrIRI :
        //   var | iri

        ST varOrIRI = g.getInstanceOf("varOrIRI");

        if (ctx.var() != null) {
            varOrIRI.add("var", visitVar(ctx.var()));
        } else if (ctx.iri() != null) {
            varOrIRI.add("iri", visitIri(ctx.iri()));
        }

        return varOrIRI;
    }

    @Override
    public ST visitVar(SparqlParser.VarContext ctx) {
        // var :
        //   VAR1 | VAR2

        ST var = g.getInstanceOf("var");

        if (ctx.VAR1() != null) {
            var.add("value", ctx.VAR1().getSymbol().getText());
        } else if (ctx.VAR2() != null) {
            var.add("value", ctx.VAR2().getSymbol().getText());
        }

        return var;
    }

    @Override
    public ST visitGraphTerm(SparqlParser.GraphTermContext ctx) {
        // graphTerm
        //   iri | rdfLiteral | numericLiteral | booleanLiteral | blankNode | nil

        ST graphTerm = g.getInstanceOf("graphTerm");

        if (ctx.iri() != null) {
            graphTerm.add("iri", visitIri(ctx.iri()));
        } else if (ctx.rdfLiteral() != null) {
            graphTerm.add("rdfLiteral", visitRdfLiteral(ctx.rdfLiteral()));
        } else if (ctx.numericLiteral() != null) {
            graphTerm.add("numericLiteral", visitNumericLiteral(ctx.numericLiteral()));
        } else if (ctx.booleanLiteral() != null) {
            graphTerm.add("booleanLiteral", visitBooleanLiteral(ctx.booleanLiteral()));
        } else if (ctx.blankNode() != null) {
            graphTerm.add("blankNode", visitBlankNode(ctx.blankNode()));
        } else if (ctx.nil() != null) {
            graphTerm.add("nil", visitNil(ctx.nil()));
        }

        return graphTerm;
    }

    @Override
    public ST visitNil(SparqlParser.NilContext ctx) {
        // nil :
        //   OPEN_BRACE CLOSE_BRACE

        ST nil = g.getInstanceOf("nil");

        return nil;
    }

    @Override
    public ST visitBaseExpression(SparqlParser.BaseExpressionContext ctx) {
        // expression :
        //   primaryExpression

        ST baseExpression = g.getInstanceOf("baseExpression");

        baseExpression.add("primaryExpression", visitPrimaryExpression(ctx.primaryExpression()));

        return baseExpression;
    }

    @Override
    public ST visitUnaryMultiplicativeExpression(SparqlParser.UnaryMultiplicativeExpressionContext ctx) {
        // expression : 
        //   op=('*'|'/') expression 

        ST unaryMultiplicativeExpression = g.getInstanceOf("unaryMultiplicativeExpression");

        if (ctx.op.getType() == SparqlParser.ASTERISK) {
            unaryMultiplicativeExpression.add("MULTIPLY", "*");
        } else if (ctx.op.getType() == SparqlParser.DIVIDE) {
            unaryMultiplicativeExpression.add("DIVIDE", "/");
        }

        unaryMultiplicativeExpression.add("expression", visit(ctx.expression()));

        return unaryMultiplicativeExpression;
    }

    @Override
    public ST visitUnaryAdditiveExpression(SparqlParser.UnaryAdditiveExpressionContext ctx) {
        // expression : 
        //   (ADD|SUBTRACT) expression 

        ST unaryAdditiveExpression = g.getInstanceOf("unaryAdditiveExpression");

        if (ctx.op.getType() == SparqlParser.PLUS_SIGN) {
            unaryAdditiveExpression.add("ADD", "+");
        } else if (ctx.op.getType() == SparqlParser.MINUS_SIGN) {
            unaryAdditiveExpression.add("SUBTRACT", "-");
        }

        unaryAdditiveExpression.add("expression", visit(ctx.expression()));

        return unaryAdditiveExpression;
    }

    @Override
    public ST visitUnaryNegationExpression(SparqlParser.UnaryNegationExpressionContext ctx) {
        // expression : 
        //   NEGATION expression 

        ST unaryNegationExpression = g.getInstanceOf("unaryNegationExpression");

        unaryNegationExpression.add("expression", visit(ctx.expression()));

        return unaryNegationExpression;
    }

    @Override
    public ST visitMultiplicativeExpression(SparqlParser.MultiplicativeExpressionContext ctx) {
        // expression : 
        //   expression op=(ASTERISK|DIVIDE) expression 

        ST multiplicativeExpression = g.getInstanceOf("multiplicativeExpression");

        multiplicativeExpression.add("leftExpression", visit(ctx.expression(0)));

        if (ctx.op.getType() == SparqlParser.ASTERISK) {
            multiplicativeExpression.add("MULTIPLY", "*");
        } else if (ctx.op.getType() == SparqlParser.DIVIDE) {
            multiplicativeExpression.add("DIVIDE", "/");
        }

        multiplicativeExpression.add("rightExpression", visit(ctx.expression(1)));

        return multiplicativeExpression;
    }

    @Override
    public ST visitAdditiveExpression(SparqlParser.AdditiveExpressionContext ctx) {
        // expression : 
        //   expression op=(PLUS_SIGN|MINUS_SIGN) expression 

        ST additiveExpression = g.getInstanceOf("additiveExpression");

        additiveExpression.add("leftExpression", visit(ctx.expression(0)));

        if (ctx.op.getType() == SparqlParser.PLUS_SIGN) {
            additiveExpression.add("ADD", "+");
        } else if (ctx.op.getType() == SparqlParser.MINUS_SIGN) {
            additiveExpression.add("SUBTRACT", "-");
        }

        additiveExpression.add("rightExpression", visit(ctx.expression(1)));

        return additiveExpression;
    }

    @Override
    public ST visitUnarySignedLiteralExpression(SparqlParser.UnarySignedLiteralExpressionContext ctx) {
        // expression : 
        //   expression unaryLiteralExpression

        ST unarySignedLiteralExpression = g.getInstanceOf("unarySignedLiteralExpression");

        unarySignedLiteralExpression.add("expression", visit(ctx.expression()));
        unarySignedLiteralExpression.add("unaryLiteralExpression", visit(ctx.unaryLiteralExpression()));

        return unarySignedLiteralExpression;
    }

    @Override
    public ST visitRelationalSetExpression(SparqlParser.RelationalSetExpressionContext ctx) {
        // expression : 
        //   expression NOT? IN '(' expressionList? ')' 

        ST relationalSetExpression = g.getInstanceOf("relationalSetExpression");

        relationalSetExpression.add("leftExpression", visit(ctx.expression()));

        if (ctx.NOT() != null) {
            relationalSetExpression.add("NOT", ctx.NOT().getText());
        }

        relationalSetExpression.add("IN", ctx.IN().getText());

        if (ctx.expressionList() != null) {
            relationalSetExpression.add("expressionList", visit(ctx.expressionList()));
        }

        return relationalSetExpression;
    }

    @Override
    public ST visitRelationalExpression(SparqlParser.RelationalExpressionContext ctx) {
        // expression :
        //   expression op=('='|'!='|'<'|'>'|'<='|'>=') expression

        ST relationalExpression = g.getInstanceOf("relationalExpression");

        relationalExpression.add("leftExpression", visit(ctx.expression(0)));

        if (ctx.op.getType() == SparqlParser.EQUAL) {
            relationalExpression.add("operator", ctx.op.getText());
        } else if (ctx.op.getType() == SparqlParser.NOT_EQUAL) {
            relationalExpression.add("operator", ctx.op.getText());
        } else if (ctx.op.getType() == SparqlParser.LESS) {
            relationalExpression.add("operator", ctx.op.getText());
        } else if (ctx.op.getType() == SparqlParser.GREATER) {
            relationalExpression.add("operator", ctx.op.getText());
        } else if (ctx.op.getType() == SparqlParser.LESS_EQUAL) {
            relationalExpression.add("operator", ctx.op.getText());
        } else if (ctx.op.getType() == SparqlParser.GREATER_EQUAL) {
            relationalExpression.add("operator", ctx.op.getText());
        }

        relationalExpression.add("rightExpression", visit(ctx.expression(1)));

        return relationalExpression;
    }

    @Override
    public ST visitConditionalAndExpression(SparqlParser.ConditionalAndExpressionContext ctx) {
        // expression :
        //   expression (&& expression)

        ST conditionalAndExpression = g.getInstanceOf("conditionalAndExpression");

        conditionalAndExpression.add("leftExpression", visit(ctx.expression(0)));

        conditionalAndExpression.add("rightExpression", visit(ctx.expression(1)));

        return conditionalAndExpression;
    }

    @Override
    public ST visitConditionalOrExpression(SparqlParser.ConditionalOrExpressionContext ctx) {
        // expression :
        //   expression (|| expression)

        ST conditionalOrExpression = g.getInstanceOf("conditionalOrExpression");

        conditionalOrExpression.add("leftExpression", visit(ctx.expression(0)));

        conditionalOrExpression.add("rightExpression", visit(ctx.expression(1)));

        return conditionalOrExpression;
    }

    @Override
    public ST visitUnaryLiteralExpression(SparqlParser.UnaryLiteralExpressionContext ctx) {
        // expression :
        //   (numericLiteralPositive|numericLiteralNegative) (op=('*'|'/') unaryExpression)? 

        ST unaryLiteralExpression = g.getInstanceOf("unaryLiteralExpression");

        if (ctx.numericLiteralPositive() != null) {
            unaryLiteralExpression.add("numericLiteralPositive", visitNumericLiteralPositive(ctx.numericLiteralPositive()));
        } else if (ctx.numericLiteralNegative() != null) {
            unaryLiteralExpression.add("numericLiteralNegative", visitNumericLiteralNegative(ctx.numericLiteralNegative()));
        }

        if (ctx.op != null) {
            if (ctx.op.getType() == SparqlParser.ASTERISK) {
                unaryLiteralExpression.add("operator", "*");
            } else if (ctx.op.getType() == SparqlParser.DIVIDE) {
                unaryLiteralExpression.add("operator", "/");
            }
        }

        if (ctx.unaryExpression() != null) {
            unaryLiteralExpression.add("unaryExpression", visitUnaryExpression(ctx.unaryExpression()));
        }

        return unaryLiteralExpression;
    }

    @Override
    public ST visitUnaryExpression(SparqlParser.UnaryExpressionContext ctx) {
        // expression :
        //    op=('!'|'+'|'-')? primaryExpression

        ST unaryExpression = g.getInstanceOf("unaryExpression");

        if (ctx.op != null) {
            if (ctx.op.getType() == SparqlParser.NEGATION) {
                unaryExpression.add("operator", "!");
            } else if (ctx.op.getType() == SparqlParser.PLUS_SIGN) {
                unaryExpression.add("operator", "+");
            } else if (ctx.op.getType() == SparqlParser.MINUS_SIGN) {
                unaryExpression.add("operator", "-");
            }
        }

        unaryExpression.add("primaryExpression", visit(ctx.primaryExpression()));

        return unaryExpression;
    }

    @Override
    public ST visitPrimaryExpression(SparqlParser.PrimaryExpressionContext ctx) {
        // expression :
        //   '(' expression ')' | builtInCall | iriRefOrFunction | rdfLiteral | numericLiteral | booleanLiteral | var

        ST primaryExpression = g.getInstanceOf("primaryExpression");

        if (ctx.expression() != null) {
            primaryExpression.add("expression", visit(ctx.expression()));
        } else if (ctx.builtInCall() != null) {
            primaryExpression.add("builtInCall", visitBuiltInCall(ctx.builtInCall()));
        } else if (ctx.iriRefOrFunction() != null) {
            primaryExpression.add("iriRefOrFunction", visitIriRefOrFunction(ctx.iriRefOrFunction()));
        } else if (ctx.rdfLiteral() != null) {
            primaryExpression.add("rdfLiteral", visitRdfLiteral(ctx.rdfLiteral()));
        } else if (ctx.numericLiteral() != null) {
            primaryExpression.add("numericLiteral", visitNumericLiteral(ctx.numericLiteral()));
        } else if (ctx.booleanLiteral() != null) {
            primaryExpression.add("booleanLiteral", visitBooleanLiteral(ctx.booleanLiteral()));
        } else if (ctx.var() != null) {
            primaryExpression.add("var", visitVar(ctx.var()));
        }

        return primaryExpression;
    }

    @Override
    public ST visitBuiltInCall(SparqlParser.BuiltInCallContext ctx) {
        // builtInCall :
        //   aggregate
        //   | STR OPEN_BRACE expression CLOSE_BRACE
        //   | LANG OPEN_BRACE expression CLOSE_BRACE
        //   | LANGMATCHES OPEN_BRACE expression COMMA expression CLOSE_BRACE
        //   | DATATYPE OPEN_BRACE expression CLOSE_BRACE
        //   | BOUND OPEN_BRACE var CLOSE_BRACE
        //   | IRI OPEN_BRACE expression CLOSE_BRACE
        //   | URI OPEN_BRACE expression CLOSE_BRACE
        //   | BNODE OPEN_BRACE expression? CLOSE_BRACE
        //   | RAND nil
        //   | ABS OPEN_BRACE expression CLOSE_BRACE
        //   | CEIL OPEN_BRACE expression CLOSE_BRACE
        //   | FLOOR OPEN_BRACE expression CLOSE_BRACE
        //   | ROUND OPEN_BRACE expression CLOSE_BRACE
        //   | CONCAT (expressionList?)
        //   | subStringExpression
        //   | strReplaceExpression
        //   | STRLEN OPEN_BRACE expression CLOSE_BRACE
        //   | UCASE OPEN_BRACE expression CLOSE_BRACE
        //   | LCASE OPEN_BRACE expression CLOSE_BRACE
        //   | ENCODE_FOR_URI OPEN_BRACE expression CLOSE_BRACE
        //   | CONTAINS OPEN_BRACE expression COMMA expression CLOSE_BRACE
        //   | STRSTARTS OPEN_BRACE expression COMMA expression CLOSE_BRACE
        //   | STRENDS OPEN_BRACE expression COMMA expression CLOSE_BRACE
        //   | STRBEFORE OPEN_BRACE expression COMMA expression CLOSE_BRACE
        //   | STRAFTER OPEN_BRACE expression COMMA expression CLOSE_BRACE
        //   | YEAR OPEN_BRACE expression CLOSE_BRACE
        //   | MONTH OPEN_BRACE expression CLOSE_BRACE
        //   | DAY OPEN_BRACE expression CLOSE_BRACE
        //   | HOURS OPEN_BRACE expression CLOSE_BRACE
        //   | MINUTES OPEN_BRACE expression CLOSE_BRACE
        //   | SECONDS OPEN_BRACE expression CLOSE_BRACE
        //   | TIMEZONE OPEN_BRACE expression CLOSE_BRACE
        //   | TZ OPEN_BRACE expression CLOSE_BRACE
        //   | NOW nil
        //   | UUID nil
        //   | STRUUID nil
        //   | MD5 OPEN_BRACE expression CLOSE_BRACE
        //   | SHA1 OPEN_BRACE expression CLOSE_BRACE
        //   | SHA256 OPEN_BRACE expression CLOSE_BRACE
        //   | SHA384 OPEN_BRACE expression CLOSE_BRACE
        //   | SHA512 OPEN_BRACE expression CLOSE_BRACE
        //   | COALESCE (expressionList?)
        //   | IF OPEN_BRACE e1=expression COMMA e2=expression COMMA e3=expression CLOSE_BRACE
        //   | STRLANG OPEN_BRACE expression COMMA expression CLOSE_BRACE
        //   | STRDT OPEN_BRACE expression COMMA expression CLOSE_BRACE
        //   | SAMETERM OPEN_BRACE expression COMMA expression CLOSE_BRACE
        //   | ISIRI OPEN_BRACE expression CLOSE_BRACE
        //   | ISURI OPEN_BRACE expression CLOSE_BRACE
        //   | ISBLANK OPEN_BRACE expression CLOSE_BRACE
        //   | ISLITERAL OPEN_BRACE expression CLOSE_BRACE
        //   | ISNUMERIC OPEN_BRACE expression CLOSE_BRACE
        //   | regexExpression
        //   | existsFunction
        //   | notExistsFunction

        ST builtInCall = g.getInstanceOf("builtInCall");

        if (ctx.aggregate() != null) {
            builtInCall.add("aggregate", visitAggregate(ctx.aggregate()));
        } else if (ctx.STR() != null) {
            builtInCall.add("builtInFunction", ctx.STR().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.LANG() != null) {
            builtInCall.add("builtInFunction", ctx.LANG().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.LANGMATCHES() != null) {
            builtInCall.add("builtInFunction", ctx.LANGMATCHES().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
            builtInCall.add("expression", visit(ctx.expression(1)));
        } else if (ctx.DATATYPE() != null) {
            builtInCall.add("builtInFunction", ctx.DATATYPE().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.BOUND() != null) {
            builtInCall.add("builtInFunction", ctx.BOUND().getSymbol().getText().toUpperCase());
            builtInCall.add("var", visitVar((SparqlParser.VarContext) ctx.var()));
        } else if (ctx.IRI() != null) {
            builtInCall.add("builtInFunction", ctx.IRI().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.URI() != null) {
            builtInCall.add("builtInFunction", ctx.URI().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.BNODE() != null) {
            builtInCall.add("builtInFunction", ctx.BNODE().getSymbol().getText().toUpperCase());
            if (ctx.expression(0) != null) {
                builtInCall.add("expression", visit(ctx.expression(0)));
            }
        } else if (ctx.RAND() != null) {
            builtInCall.add("builtInFunction", ctx.RAND().getSymbol().getText().toUpperCase());
        } else if (ctx.ABS() != null) {
            builtInCall.add("builtInFunction", ctx.ABS().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.CEIL() != null) {
            builtInCall.add("builtInFunction", ctx.CEIL().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.FLOOR() != null) {
            builtInCall.add("builtInFunction", ctx.FLOOR().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.ROUND() != null) {
            builtInCall.add("builtInFunction", ctx.ROUND().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.CONCAT() != null) {
            builtInCall.add("builtInFunction", ctx.CONCAT().getSymbol().getText().toUpperCase());
            if (ctx.expressionList() != null) {
                builtInCall.add("expressionList", visitExpressionList(ctx.expressionList()));
            }
        } else if (ctx.STRLEN() != null) {
            builtInCall.add("builtInFunction", ctx.STRLEN().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.UCASE() != null) {
            builtInCall.add("builtInFunction", ctx.UCASE().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.LCASE() != null) {
            builtInCall.add("builtInFunction", ctx.LCASE().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.ENCODE_FOR_URI() != null) {
            builtInCall.add("builtInFunction", ctx.ENCODE_FOR_URI().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.CONTAINS() != null) {
            builtInCall.add("builtInFunction", ctx.CONTAINS().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
            builtInCall.add("expression", visit(ctx.expression(1)));
        } else if (ctx.STRSTARTS() != null) {
            builtInCall.add("builtInFunction", ctx.STRSTARTS().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
            builtInCall.add("expression", visit(ctx.expression(1)));
        } else if (ctx.STRENDS() != null) {
            builtInCall.add("builtInFunction", ctx.STRENDS().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
            builtInCall.add("expression", visit(ctx.expression(1)));
        } else if (ctx.STRBEFORE() != null) {
            builtInCall.add("builtInFunction", ctx.STRBEFORE().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
            builtInCall.add("expression", visit(ctx.expression(1)));
        } else if (ctx.STRAFTER() != null) {
            builtInCall.add("builtInFunction", ctx.STRAFTER().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
            builtInCall.add("expression", visit(ctx.expression(1)));
        } else if (ctx.YEAR() != null) {
            builtInCall.add("builtInFunction", ctx.YEAR().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.MONTH() != null) {
            builtInCall.add("builtInFunction", ctx.MONTH().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.DAY() != null) {
            builtInCall.add("builtInFunction", ctx.DAY().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.HOURS() != null) {
            builtInCall.add("builtInFunction", ctx.HOURS().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.MINUTES() != null) {
            builtInCall.add("builtInFunction", ctx.MINUTES().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.SECONDS() != null) {
            builtInCall.add("builtInFunction", ctx.SECONDS().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.TIMEZONE() != null) {
            builtInCall.add("builtInFunction", ctx.TIMEZONE().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.TZ() != null) {
            builtInCall.add("builtInFunction", ctx.TZ().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.NOW() != null) {
            builtInCall.add("builtInFunction", ctx.NOW().getSymbol().getText().toUpperCase());
        } else if (ctx.UUID() != null) {
            builtInCall.add("builtInFunction", ctx.UUID().getSymbol().getText().toUpperCase());
        } else if (ctx.STRUUID() != null) {
            builtInCall.add("builtInFunction", ctx.STRUUID().getSymbol().getText().toUpperCase());
        } else if (ctx.MD5() != null) {
            builtInCall.add("builtInFunction", ctx.MD5().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.SHA1() != null) {
            builtInCall.add("builtInFunction", ctx.SHA1().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.SHA256() != null) {
            builtInCall.add("builtInFunction", ctx.SHA256().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.SHA384() != null) {
            builtInCall.add("builtInFunction", ctx.SHA384().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.SHA512() != null) {
            builtInCall.add("builtInFunction", ctx.SHA512().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.COALESCE() != null) {
            builtInCall.add("builtInFunction", ctx.COALESCE().getSymbol().getText().toUpperCase());
            if (ctx.expressionList() != null) {
                builtInCall.add("expressionList", visitExpressionList(ctx.expressionList()));
            }
        } else if (ctx.IF() != null) {
            builtInCall.add("builtInFunction", ctx.IF().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
            builtInCall.add("expression", visit(ctx.expression(1)));
            builtInCall.add("expression", visit(ctx.expression(2)));
        } else if (ctx.STRLANG() != null) {
            builtInCall.add("builtInFunction", ctx.STRLANG().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
            builtInCall.add("expression", visit(ctx.expression(1)));
        } else if (ctx.STRDT() != null) {
            builtInCall.add("builtInFunction", ctx.STRDT().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
            builtInCall.add("expression", visit(ctx.expression(1)));
        } else if (ctx.SAMETERM() != null) {
            builtInCall.add("builtInFunction", ctx.SAMETERM().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
            builtInCall.add("expression", visit(ctx.expression(1)));
        } else if (ctx.ISIRI() != null) {
            builtInCall.add("builtInFunction", ctx.ISIRI().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.ISURI() != null) {
            builtInCall.add("builtInFunction", ctx.ISURI().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.ISBLANK() != null) {
            builtInCall.add("builtInFunction", ctx.ISBLANK().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.ISLITERAL() != null) {
            builtInCall.add("builtInFunction", ctx.ISLITERAL().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.ISNUMERIC() != null) {
            builtInCall.add("builtInFunction", ctx.ISNUMERIC().getSymbol().getText().toUpperCase());
            builtInCall.add("expression", visit(ctx.expression(0)));
        } else if (ctx.subStringExpression() != null) {
            builtInCall.add("subStringExpression", visitSubStringExpression(ctx.subStringExpression()));
        } else if (ctx.strReplaceExpression() != null) {
            builtInCall.add("strReplaceExpression", visitStrReplaceExpression(ctx.strReplaceExpression()));
        } else if (ctx.regexExpression() != null) {
            builtInCall.add("regexExpression", visitRegexExpression(ctx.regexExpression()));
        } else if (ctx.existsFunction() != null) {
            builtInCall.add("existsFunction", visitExistsFunction(ctx.existsFunction()));
        } else if (ctx.notExistsFunction() != null) {
            builtInCall.add("notExistsFunction", visitNotExistsFunction(ctx.notExistsFunction()));
        }

        return builtInCall;
    }

    @Override
    public ST visitRegexExpression(SparqlParser.RegexExpressionContext ctx) {
        // regexExpression :
        //   REGEX OPEN_BRACE expression COMMA expression (COMMA expression)? CLOSE_BRACE

        ST regexExpression = g.getInstanceOf("regexExpression");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.ExpressionContext) {
                regexExpression.add("expression", visit((SparqlParser.ExpressionContext) c));
            }
        }

        return regexExpression;
    }

    @Override
    public ST visitSubStringExpression(SparqlParser.SubStringExpressionContext ctx) {
        // subStringExpression :
        //   SUBSTR OPEN_BRACE expression COMMA expression (COMMA expression)? CLOSE_BRACE

        ST subStringExpression = g.getInstanceOf("subStringExpression");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.ExpressionContext) {
                subStringExpression.add("expression", visit((SparqlParser.ExpressionContext) c));
            }
        }

        return subStringExpression;
    }

    @Override
    public ST visitStrReplaceExpression(SparqlParser.StrReplaceExpressionContext ctx) {
        // strReplaceExpression :
        //   REPLACE OPEN_BRACE expression COMMA expression COMMA expression (COMMA expression)? CLOSE_BRACE

        ST strReplaceExpression = g.getInstanceOf("strReplaceExpression");

        for (ParseTree c : ctx.children) {
            if (c instanceof SparqlParser.ExpressionContext) {
                strReplaceExpression.add("expression", visit((SparqlParser.ExpressionContext) c));
            }
        }

        return strReplaceExpression;
    }

    @Override
    public ST visitExistsFunction(SparqlParser.ExistsFunctionContext ctx) {
        // existsFunction :
        //   EXISTS groupGraphPattern

        ST existsFunction = g.getInstanceOf("existsFunction");

        existsFunction.add("groupGraphPattern", visitGroupGraphPattern(ctx.groupGraphPattern()));

        return existsFunction;
    }

    @Override
    public ST visitNotExistsFunction(SparqlParser.NotExistsFunctionContext ctx) {
        // notExistsFunction :
        //   NOT EXISTS groupGraphPattern

        ST notExistsFunction = g.getInstanceOf("notExistsFunction");

        notExistsFunction.add("groupGraphPattern", visitGroupGraphPattern(ctx.groupGraphPattern()));

        return notExistsFunction;
    }

    @Override
    public ST visitAggregate(SparqlParser.AggregateContext ctx) {
        // aggregate :
        //    COUNT OPEN_BRACE DISTINCT? (ASTERISK | expression) CLOSE_BRACE
        //  | SUM OPEN_BRACE DISTINCT? expression CLOSE_BRACE
        //  | MIN OPEN_BRACE DISTINCT? expression CLOSE_BRACE
        //  | MAX OPEN_BRACE DISTINCT? expression CLOSE_BRACE
        //  | AVG OPEN_BRACE DISTINCT? expression CLOSE_BRACE
        //  | SAMPLE OPEN_BRACE DISTINCT? expression CLOSE_BRACE
        //  | GROUP_CONCAT OPEN_BRACE DISTINCT? expression (SEMICOLON SEPARATOR EQUAL string)? CLOSE_BRACE

        ST aggregate = g.getInstanceOf("aggregate");

        if (ctx.COUNT() != null) {
            aggregate.add("operation", ctx.COUNT().getSymbol().getText().toUpperCase());
            if (ctx.DISTINCT() != null) {
                aggregate.add("attribute", ctx.DISTINCT().getSymbol().getText().toUpperCase());
            }
            if (ctx.ASTERISK() != null) {
                aggregate.add("whatever", ctx.ASTERISK().getSymbol().getText());
            } else if (ctx.expression() != null) {
                aggregate.add("expression", visit(ctx.expression()));
            }
        } else if (ctx.SUM() != null) {
            aggregate.add("operation", ctx.SUM().getSymbol().getText().toUpperCase());
            if (ctx.DISTINCT() != null) {
                aggregate.add("attribute", ctx.DISTINCT().getSymbol().getText().toUpperCase());
            }
            aggregate.add("expression", visit(ctx.expression()));
        } else if (ctx.MIN() != null) {
            aggregate.add("operation", ctx.MIN().getSymbol().getText().toUpperCase());
            if (ctx.DISTINCT() != null) {
                aggregate.add("attribute", ctx.DISTINCT().getSymbol().getText().toUpperCase());
            }
            aggregate.add("expression", visit(ctx.expression()));
        } else if (ctx.MAX() != null) {
            aggregate.add("operation", ctx.MAX().getSymbol().getText().toUpperCase());
            if (ctx.DISTINCT() != null) {
                aggregate.add("attribute", ctx.DISTINCT().getSymbol().getText().toUpperCase());
            }
            aggregate.add("expression", visit(ctx.expression()));
        } else if (ctx.AVG() != null) {
            aggregate.add("operation", ctx.AVG().getSymbol().getText().toUpperCase());
            if (ctx.DISTINCT() != null) {
                aggregate.add("attribute", ctx.DISTINCT().getSymbol().getText().toUpperCase());
            }
            aggregate.add("expression", visit(ctx.expression()));
        } else if (ctx.SAMPLE() != null) {
            aggregate.add("operation", ctx.SAMPLE().getSymbol().getText().toUpperCase());
            if (ctx.DISTINCT() != null) {
                aggregate.add("attribute", ctx.DISTINCT().getSymbol().getText().toUpperCase());
            }
            aggregate.add("expression", visit(ctx.expression()));
        } else if (ctx.GROUP_CONCAT() != null) {
            aggregate.add("operation", ctx.GROUP_CONCAT().getSymbol().getText().toUpperCase());
            if (ctx.DISTINCT() != null) {
                aggregate.add("attribute", ctx.DISTINCT().getSymbol().getText().toUpperCase());
            }
            aggregate.add("expression", visit(ctx.expression()));
            if (ctx.string() != null) {
                aggregate.add("string", visitString(ctx.string()));
            }
        }

        return aggregate;
    }

    @Override
    public ST visitIriRefOrFunction(SparqlParser.IriRefOrFunctionContext ctx) {
        // iriRefOrFunction :
        //   iri argList?

        ST iriRefOrFunction = g.getInstanceOf("iriRefOrFunction");

        iriRefOrFunction.add("iri", visitIri(ctx.iri()));

        if (ctx.argList() != null) {
            iriRefOrFunction.add("argList", visitArgList(ctx.argList()));
        }

        return iriRefOrFunction;
    }

    @Override
    public ST visitRdfLiteral(SparqlParser.RdfLiteralContext ctx) {
        // rdfLiteral :
        //   string (LANGTAG | (REFERENCE iri))?

        ST rdfLiteral = g.getInstanceOf("rdfLiteral");

        rdfLiteral.add("string", visitString(ctx.string()));

        if (ctx.LANGTAG() != null) {
            rdfLiteral.add("langTag", ctx.LANGTAG().getSymbol().getText());
        } else if (ctx.iri() != null) {
            rdfLiteral.add("iri", visitIri(ctx.iri()));
        }

        return rdfLiteral;
    }

    @Override
    public ST visitNumericLiteral(SparqlParser.NumericLiteralContext ctx) {
        // numericLiteral :
        //   numericLiteralUnsigned | numericLiteralPositive | numericLiteralNegative

        ST numericLiteral = g.getInstanceOf("numericLiteral");

        if (ctx.numericLiteralUnsigned() != null) {
            numericLiteral.add("numericLiteralUnsigned", visitNumericLiteralUnsigned(ctx.numericLiteralUnsigned()));
        } else if (ctx.numericLiteralPositive() != null) {
            numericLiteral.add("numericLiteralPositive", visitNumericLiteralPositive(ctx.numericLiteralPositive()));
        } else if (ctx.numericLiteralNegative() != null) {
            numericLiteral.add("numericLiteralPositive", visitNumericLiteralNegative(ctx.numericLiteralNegative()));
        }

        return numericLiteral;
    }

    @Override
    public ST visitNumericLiteralUnsigned(SparqlParser.NumericLiteralUnsignedContext ctx) {
        // numericLiteralUnsigned :
        //  INTEGER | DECIMAL | DOUBLE

        ST numericLiteralUnsigned = g.getInstanceOf("numericLiteralUnsigned");

        if (ctx.INTEGER() != null) {
            numericLiteralUnsigned.add("value", ctx.INTEGER().getSymbol().getText());
        } else if (ctx.DECIMAL() != null) {
            numericLiteralUnsigned.add("value", ctx.DECIMAL().getSymbol().getText());
        } else if (ctx.DOUBLE() != null) {
            numericLiteralUnsigned.add("value", ctx.DOUBLE().getSymbol().getText());
        }

        return numericLiteralUnsigned;
    }

    @Override
    public ST visitNumericLiteralPositive(SparqlParser.NumericLiteralPositiveContext ctx) {
        // numericLiteralPositive :
        //   INTEGER_POSITIVE | DECIMAL_POSITIVE | DOUBLE_POSITIVE

        ST numericLiteralPositive = g.getInstanceOf("numericLiteralPositive");

        if (ctx.INTEGER_POSITIVE() != null) {
            numericLiteralPositive.add("value", ctx.INTEGER_POSITIVE().getSymbol().getText());
        } else if (ctx.DECIMAL_POSITIVE() != null) {
            numericLiteralPositive.add("value", ctx.DECIMAL_POSITIVE().getSymbol().getText());
        } else if (ctx.DOUBLE_POSITIVE() != null) {
            numericLiteralPositive.add("value", ctx.DOUBLE_POSITIVE().getSymbol().getText());
        }

        return numericLiteralPositive;
    }

    @Override
    public ST visitNumericLiteralNegative(SparqlParser.NumericLiteralNegativeContext ctx) {
        // numericLiteralNegative :
        //   INTEGER_NEGATIVE | DECIMAL_NEGATIVE | DOUBLE_NEGATIVE

        ST numericLiteralNegative = g.getInstanceOf("numericLiteralNegative");

        if (ctx.INTEGER_NEGATIVE() != null) {
            numericLiteralNegative.add("value", ctx.INTEGER_NEGATIVE().getSymbol().getText());
        } else if (ctx.DECIMAL_NEGATIVE() != null) {
            numericLiteralNegative.add("value", ctx.DECIMAL_NEGATIVE().getSymbol().getText());
        } else if (ctx.DOUBLE_NEGATIVE() != null) {
            numericLiteralNegative.add("value", ctx.DOUBLE_NEGATIVE().getSymbol().getText());
        }

        return numericLiteralNegative;
    }

    @Override
    public ST visitBooleanLiteral(SparqlParser.BooleanLiteralContext ctx) {
        // booleanLiteral :
        //   TRUE | FALSE

        ST booleanLiteral = g.getInstanceOf("booleanLiteral");

        if (ctx.TRUE() != null) {
            booleanLiteral.add("value", ctx.TRUE().getSymbol().getText().toUpperCase());
        } else if (ctx.FALSE() != null) {
            booleanLiteral.add("value", ctx.FALSE().getSymbol().getText().toUpperCase());
        }

        return booleanLiteral;
    }

    @Override
    public ST visitString(SparqlParser.StringContext ctx) {
        // string :
        //   STRING_LITERAL1 | STRING_LITERAL2 | STRING_LITERAL_LONG1 | STRING_LITERAL_LONG2

        ST string = g.getInstanceOf("string");

        if (ctx.STRING_LITERAL1() != null) {
            string.add("value", ctx.STRING_LITERAL1().getSymbol().getText());
        } else if (ctx.STRING_LITERAL2() != null) {
            string.add("value", ctx.STRING_LITERAL2().getSymbol().getText());
        } else if (ctx.STRING_LITERAL_LONG1() != null) {
            string.add("value", ctx.STRING_LITERAL_LONG1().getSymbol().getText());
        } else if (ctx.STRING_LITERAL_LONG2() != null) {
            string.add("value", ctx.STRING_LITERAL_LONG2().getSymbol().getText());
        }

        return string;
    }

    @Override
    public ST visitIri(SparqlParser.IriContext ctx) {
        // iri :
        //   IRIREF | prefixedName

        ST iri = g.getInstanceOf("iri");

        if (ctx.IRIREF() != null) {
            String s = ctx.IRIREF().getSymbol().getText();
            iri.add("IRIREF", s.substring(1, s.length() - 1));
        } else if (ctx.prefixedName() != null) {
            iri.add("prefixedName", visitPrefixedName(ctx.prefixedName()));
        }

        return iri;
    }

    @Override
    public ST visitPrefixedName(SparqlParser.PrefixedNameContext ctx) {
        // prefixedName :
        //   PNAME_LN | PNAME_NS

        ST prefixedName = g.getInstanceOf("prefixedName");

        if (ctx.PNAME_LN() != null) {
            prefixedName.add("value", ctx.PNAME_LN().getSymbol().getText());
        } else if (ctx.PNAME_NS() != null) {
            prefixedName.add("value", ctx.PNAME_NS().getSymbol().getText());
        }

        return prefixedName;
    }

    @Override
    public ST visitBlankNode(SparqlParser.BlankNodeContext ctx) {
        // blankNode :
        //   BLANK_NODE_LABEL | anon

        ST blankNode = g.getInstanceOf("blankNode");

        if (ctx.BLANK_NODE_LABEL() != null) {
            blankNode.add("value", ctx.BLANK_NODE_LABEL().getSymbol().getText());
        } else if (ctx.anon() != null) {
            blankNode.add("anon", visitAnon(ctx.anon()));
        }

        return blankNode;
    }

    @Override
    public ST visitAnon(SparqlParser.AnonContext ctx) {
        // anon :
        //   OPEN_SQUARE_BRACKET CLOSE_SQUARE_BRACKET

        ST anon = g.getInstanceOf("anon");

        return anon;
    }
}
