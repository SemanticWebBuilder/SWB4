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
package org.semanticwb.base.util.parser.html;

import java.io.*;

// TODO: Auto-generated Javadoc
/**
 * The Class HTMLParserTokenManager.
 */
public class HTMLParserTokenManager implements HTMLParserConstants {

    /** The debug stream. */
    public java.io.PrintStream debugStream = System.out;

    /**
     * Sets the debug stream.
     * 
     * @param ds the new debug stream
     */
    public void setDebugStream(java.io.PrintStream ds)
    {
        debugStream = ds;
    }

    /**
     * Jj stop string literal dfa_0.
     * 
     * @param pos the pos
     * @param active0 the active0
     * @return the int
     */
    private final int jjStopStringLiteralDfa_0(int pos, long active0)
    {
        switch (pos)
        {
            case 0:
                if ((active0 & 0x18L) != 0L)
                {
                    return 17;
                }
                return -1;
            case 1:
                if ((active0 & 0x18L) != 0L)
                {
                    return 22;
                }
                return -1;
            default:
                return -1;
        }
    }

    /**
     * Jj start nfa_0.
     * 
     * @param pos the pos
     * @param active0 the active0
     * @return the int
     */
    private final int jjStartNfa_0(int pos, long active0)
    {
        return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
    }

    /**
     * Jj stop at pos.
     * 
     * @param pos the pos
     * @param kind the kind
     * @return the int
     */
    private final int jjStopAtPos(int pos, int kind)
    {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        return pos + 1;
    }

    /**
     * Jj start nfa with states_0.
     * 
     * @param pos the pos
     * @param kind the kind
     * @param state the state
     * @return the int
     */
    private final int jjStartNfaWithStates_0(int pos, int kind, int state)
    {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        try
        {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e)
        {
            return pos + 1;
        }
        return jjMoveNfa_0(state, pos + 1);
    }

    /**
     * Jj move string literal dfa0_0.
     * 
     * @return the int
     */
    private final int jjMoveStringLiteralDfa0_0()
    {
        switch (curChar)
        {
            case 60:
                return jjMoveStringLiteralDfa1_0(0x18L);
            default:
                return jjMoveNfa_0(11, 0);
        }
    }

    /**
     * Jj move string literal dfa1_0.
     * 
     * @param active0 the active0
     * @return the int
     */
    private final int jjMoveStringLiteralDfa1_0(long active0)
    {
        try
        {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e)
        {
            jjStopStringLiteralDfa_0(0, active0);
            return 1;
        }
        switch (curChar)
        {
            case 33:
                if ((active0 & 0x10L) != 0L)
                {
                    jjmatchedKind = 4;
                    jjmatchedPos = 1;
                }
                return jjMoveStringLiteralDfa2_0(active0, 0x8L);
            default:
                break;
        }
        return jjStartNfa_0(0, active0);
    }

    /**
     * Jj move string literal dfa2_0.
     * 
     * @param old0 the old0
     * @param active0 the active0
     * @return the int
     */
    private final int jjMoveStringLiteralDfa2_0(long old0, long active0)
    {
        if (((active0 &= old0)) == 0L)
        {
            return jjStartNfa_0(0, old0);
        }
        try
        {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e)
        {
            jjStopStringLiteralDfa_0(1, active0);
            return 2;
        }
        switch (curChar)
        {
            case 45:
                return jjMoveStringLiteralDfa3_0(active0, 0x8L);
            default:
                break;
        }
        return jjStartNfa_0(1, active0);
    }

    /**
     * Jj move string literal dfa3_0.
     * 
     * @param old0 the old0
     * @param active0 the active0
     * @return the int
     */
    private final int jjMoveStringLiteralDfa3_0(long old0, long active0)
    {
        if (((active0 &= old0)) == 0L)
        {
            return jjStartNfa_0(1, old0);
        }
        try
        {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e)
        {
            jjStopStringLiteralDfa_0(2, active0);
            return 3;
        }
        switch (curChar)
        {
            case 45:
                if ((active0 & 0x8L) != 0L)
                {
                    return jjStopAtPos(3, 3);
                }
                break;
            default:
                break;
        }
        return jjStartNfa_0(2, active0);
    }

    /**
     * Jj check n add.
     * 
     * @param state the state
     */
    private final void jjCheckNAdd(int state)
    {
        if (jjrounds[state] != jjround)
        {
            jjstateSet[jjnewStateCnt++] = state;
            jjrounds[state] = jjround;
        }
    }

    /**
     * Jj add states.
     * 
     * @param start the start
     * @param end the end
     */
    private final void jjAddStates(int start, int end)
    {
        do
        {
            jjstateSet[jjnewStateCnt++] = jjnextStates[start];
        } while (start++ != end);
    }

    /**
     * Jj check n add two states.
     * 
     * @param state1 the state1
     * @param state2 the state2
     */
    private final void jjCheckNAddTwoStates(int state1, int state2)
    {
        jjCheckNAdd(state1);
        jjCheckNAdd(state2);
    }

    /**
     * Jj check n add states.
     * 
     * @param start the start
     * @param end the end
     */
    private final void jjCheckNAddStates(int start, int end)
    {
        do
        {
            jjCheckNAdd(jjnextStates[start]);
        } while (start++ != end);
    }

//    private final void jjCheckNAddStates(int start)
//    {
//        jjCheckNAdd(jjnextStates[start]);
//        jjCheckNAdd(jjnextStates[start + 1]);
//    }
    /** The Constant jjbitVec0. */
static final long[] jjbitVec0 =
    {
        0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
    };

    /**
     * Jj move nfa_0.
     * 
     * @param startState the start state
     * @param curPos the cur pos
     * @return the int
     */
    private final int jjMoveNfa_0(int startState, int curPos)
    {
        int[] nextStates;
        int startsAt = 0;
        jjnewStateCnt = 25;
        int i = 1;
        jjstateSet[0] = startState;
        int j, kind = 0x7fffffff;
        for (;;)
        {
            if (++jjround == 0x7fffffff)
            {
                ReInitRounds();
            }
            if (curChar < 64)
            {
                long l = 1L << curChar;
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 11:
                            if ((0x3ff000000000000L & l) != 0L)
                            {
                                jjCheckNAddTwoStates(7, 2);
                            } else if ((0x100002600L & l) != 0L)
                            {
                                if (kind > 9)
                                {
                                    kind = 9;
                                }
                                jjCheckNAdd(10);
                            } else if (curChar == 60)
                            {
                                jjCheckNAddStates(0, 2);
                            } else if (curChar == 38)
                            {
                                jjAddStates(3, 4);
                            } else if (curChar == 36)
                            {
                                jjstateSet[jjnewStateCnt++] = 1;
                            }
                            if ((0x3ff000000000000L & l) != 0L)
                            {
                                if (kind > 5)
                                {
                                    kind = 5;
                                }
                                jjCheckNAddStates(5, 9);
                            }
                            break;
                        case 17:
                            if (curChar == 33)
                            {
                                jjstateSet[jjnewStateCnt++] = 22;
                            } else if (curChar == 47)
                            {
                                jjCheckNAdd(18);
                            }
                            break;
                        case 0:
                            if (curChar == 36)
                            {
                                jjstateSet[jjnewStateCnt++] = 1;
                            }
                            break;
                        case 1:
                            if ((0x3ff000000000000L & l) != 0L)
                            {
                                jjCheckNAdd(2);
                            }
                            break;
                        case 2:
                            if ((0x500000000000L & l) != 0L)
                            {
                                jjstateSet[jjnewStateCnt++] = 3;
                            }
                            break;
                        case 3:
                        case 9:
                            if ((0x3ff000000000000L & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 5)
                            {
                                kind = 5;
                            }
                            jjCheckNAddStates(10, 12);
                            break;
                        case 4:
                            if ((0x3ff000000000000L & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 5)
                            {
                                kind = 5;
                            }
                            jjCheckNAddStates(5, 9);
                            break;
                        case 5:
                            if ((0x880000000000L & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 5)
                            {
                                kind = 5;
                            }
                            jjCheckNAddStates(13, 16);
                            break;
                        case 6:
                            if ((0x3ff000000000000L & l) != 0L)
                            {
                                jjCheckNAddTwoStates(7, 2);
                            }
                            break;
                        case 7:
                            if (curChar != 34)
                            {
                                break;
                            }
                            if (kind > 5)
                            {
                                kind = 5;
                            }
                            jjCheckNAddStates(10, 12);
                            break;
                        case 8:
                            if ((0x208000000000L & l) != 0L)
                            {
                                jjstateSet[jjnewStateCnt++] = 9;
                            }
                            break;
                        case 10:
                            if ((0x100002600L & l) == 0L)
                            {
                                break;
                            }
                            kind = 9;
                            jjCheckNAdd(10);
                            break;
                        case 13:
                            if (curChar == 59 && kind > 8)
                            {
                                kind = 8;
                            }
                            break;
                        case 14:
                            if (curChar == 35)
                            {
                                jjCheckNAdd(15);
                            }
                            break;
                        case 15:
                            if ((0x3ff000000000000L & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 8)
                            {
                                kind = 8;
                            }
                            jjCheckNAddTwoStates(15, 13);
                            break;
                        case 16:
                            if (curChar == 60)
                            {
                                jjCheckNAddStates(0, 2);
                            }
                            break;
                        case 19:
                            if ((0x9fffff7affffd9ffL & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 1)
                            {
                                kind = 1;
                            }
                            jjCheckNAdd(20);
                            break;
                        case 20:
                            if ((0x9ffffffeffffd9ffL & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 1)
                            {
                                kind = 1;
                            }
                            jjCheckNAdd(20);
                            break;
                        case 21:
                            if (curChar == 33)
                            {
                                jjstateSet[jjnewStateCnt++] = 22;
                            }
                            break;
                        case 23:
                            if ((0x9fffff7affffd9ffL & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 2)
                            {
                                kind = 2;
                            }
                            jjCheckNAdd(24);
                            break;
                        case 24:
                            if ((0x9ffffffeffffd9ffL & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 2)
                            {
                                kind = 2;
                            }
                            jjCheckNAdd(24);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else if (curChar < 128)
            {
                long l = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 11:
                        case 4:
                            if ((0x7fffffe07fffffeL & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 5)
                            {
                                kind = 5;
                            }
                            jjCheckNAddStates(5, 9);
                            break;
                        case 17:
                        case 18:
                            if ((0x7fffffe07fffffeL & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 1)
                            {
                                kind = 1;
                            }
                            jjstateSet[jjnewStateCnt++] = 19;
                            break;
                        case 9:
                            if ((0x7fffffe07fffffeL & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 5)
                            {
                                kind = 5;
                            }
                            jjCheckNAddStates(10, 12);
                            break;
                        case 12:
                            if ((0x7fffffe07fffffeL & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 8)
                            {
                                kind = 8;
                            }
                            jjAddStates(17, 18);
                            break;
                        case 19:
                        case 20:
                            if (kind > 1)
                            {
                                kind = 1;
                            }
                            jjCheckNAdd(20);
                            break;
                        case 22:
                            if ((0x7fffffe07fffffeL & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 2)
                            {
                                kind = 2;
                            }
                            jjstateSet[jjnewStateCnt++] = 23;
                            break;
                        case 23:
                        case 24:
                            if (kind > 2)
                            {
                                kind = 2;
                            }
                            jjCheckNAdd(24);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else
            {
                int i2 = (curChar & 0xff) >> 6;
                long l2 = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 19:
                        case 20:
                            if ((jjbitVec0[i2] & l2) == 0L)
                            {
                                break;
                            }
                            if (kind > 1)
                            {
                                kind = 1;
                            }
                            jjCheckNAdd(20);
                            break;
                        case 23:
                        case 24:
                            if ((jjbitVec0[i2] & l2) == 0L)
                            {
                                break;
                            }
                            if (kind > 2)
                            {
                                kind = 2;
                            }
                            jjCheckNAdd(24);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            if (kind != 0x7fffffff)
            {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            ++curPos;
            if ((i = jjnewStateCnt) == (startsAt = 25 - (jjnewStateCnt = startsAt)))
            {
                return curPos;
            }
            try
            {
                curChar = input_stream.readChar();
            } catch (java.io.IOException e)
            {
                return curPos;
            }
        }
    }

    /**
     * Jj move string literal dfa0_4.
     * 
     * @return the int
     */
    private final int jjMoveStringLiteralDfa0_4()
    {
        return jjMoveNfa_4(1, 0);
    }

    /**
     * Jj move nfa_4.
     * 
     * @param startState the start state
     * @param curPos the cur pos
     * @return the int
     */
    private final int jjMoveNfa_4(int startState, int curPos)
    {
        int[] nextStates;
        int startsAt = 0;
        jjnewStateCnt = 2;
        int i = 1;
        jjstateSet[0] = startState;
        int j, kind = 0x7fffffff;
        for (;;)
        {
            if (++jjround == 0x7fffffff)
            {
                ReInitRounds();
            }
            if (curChar < 64)
            {
                long l = 1L << curChar;
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 1:
                            if ((0xfffffffbffffffffL & l) != 0L)
                            {
                                if (kind > 21)
                                {
                                    kind = 21;
                                }
                                jjCheckNAdd(0);
                            } else if (curChar == 34)
                            {
                                if (kind > 22)
                                {
                                    kind = 22;
                                }
                            }
                            break;
                        case 0:
                            if ((0xfffffffbffffffffL & l) == 0L)
                            {
                                break;
                            }
                            kind = 21;
                            jjCheckNAdd(0);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else if (curChar < 128)
            {
                long l = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 1:
                        case 0:
                            kind = 21;
                            jjCheckNAdd(0);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else
            {
                int i2 = (curChar & 0xff) >> 6;
                long l2 = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 1:
                        case 0:
                            if ((jjbitVec0[i2] & l2) == 0L)
                            {
                                break;
                            }
                            if (kind > 21)
                            {
                                kind = 21;
                            }
                            jjCheckNAdd(0);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            if (kind != 0x7fffffff)
            {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            ++curPos;
            if ((i = jjnewStateCnt) == (startsAt = 2 - (jjnewStateCnt = startsAt)))
            {
                return curPos;
            }
            try
            {
                curChar = input_stream.readChar();
            } catch (java.io.IOException e)
            {
                return curPos;
            }
        }
    }

    /**
     * Jj stop string literal dfa_6.
     * 
     * @param pos the pos
     * @param active0 the active0
     * @return the int
     */
    private final int jjStopStringLiteralDfa_6(int pos, long active0)
    {
        switch (pos)
        {
            default:
                return -1;
        }
    }

    /**
     * Jj start nfa_6.
     * 
     * @param pos the pos
     * @param active0 the active0
     * @return the int
     */
    private final int jjStartNfa_6(int pos, long active0)
    {
        return jjMoveNfa_6(jjStopStringLiteralDfa_6(pos, active0), pos + 1);
    }

    /**
     * Jj start nfa with states_6.
     * 
     * @param pos the pos
     * @param kind the kind
     * @param state the state
     * @return the int
     */
    private final int jjStartNfaWithStates_6(int pos, int kind, int state)
    {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        try
        {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e)
        {
            return pos + 1;
        }
        return jjMoveNfa_6(state, pos + 1);
    }

    /**
     * Jj move string literal dfa0_6.
     * 
     * @return the int
     */
    private final int jjMoveStringLiteralDfa0_6()
    {
        switch (curChar)
        {
            case 62:
                return jjStopAtPos(0, 26);
            default:
                return jjMoveNfa_6(0, 0);
        }
    }

    /**
     * Jj move nfa_6.
     * 
     * @param startState the start state
     * @param curPos the cur pos
     * @return the int
     */
    private final int jjMoveNfa_6(int startState, int curPos)
    {
        int[] nextStates;
        int startsAt = 0;
        jjnewStateCnt = 1;
        int i = 1;
        jjstateSet[0] = startState;
        int j, kind = 0x7fffffff;
        for (;;)
        {
            if (++jjround == 0x7fffffff)
            {
                ReInitRounds();
            }
            if (curChar < 64)
            {
                long l = 1L << curChar;
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 0:
                            if ((0xbfffffffffffffffL & l) == 0L)
                            {
                                break;
                            }
                            kind = 25;
                            jjstateSet[jjnewStateCnt++] = 0;
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else if (curChar < 128)
            {
                long l = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 0:
                            kind = 25;
                            jjstateSet[jjnewStateCnt++] = 0;
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else
            {
                int i2 = (curChar & 0xff) >> 6;
                long l2 = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 0:
                            if ((jjbitVec0[i2] & l2) == 0L)
                            {
                                break;
                            }
                            if (kind > 25)
                            {
                                kind = 25;
                            }
                            jjstateSet[jjnewStateCnt++] = 0;
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            if (kind != 0x7fffffff)
            {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            ++curPos;
            if ((i = jjnewStateCnt) == (startsAt = 1 - (jjnewStateCnt = startsAt)))
            {
                return curPos;
            }
            try
            {
                curChar = input_stream.readChar();
            } catch (java.io.IOException e)
            {
                return curPos;
            }
        }
    }

    /**
     * Jj move string literal dfa0_3.
     * 
     * @return the int
     */
    private final int jjMoveStringLiteralDfa0_3()
    {
        return jjMoveNfa_3(1, 0);
    }

    /**
     * Jj move nfa_3.
     * 
     * @param startState the start state
     * @param curPos the cur pos
     * @return the int
     */
    private final int jjMoveNfa_3(int startState, int curPos)
    {
        int[] nextStates;
        int startsAt = 0;
        jjnewStateCnt = 2;
        int i = 1;
        jjstateSet[0] = startState;
        int j, kind = 0x7fffffff;
        for (;;)
        {
            if (++jjround == 0x7fffffff)
            {
                ReInitRounds();
            }
            if (curChar < 64)
            {
                long l = 1L << curChar;
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 1:
                            if ((0xffffff7fffffffffL & l) != 0L)
                            {
                                if (kind > 19)
                                {
                                    kind = 19;
                                }
                                jjCheckNAdd(0);
                            } else if (curChar == 39)
                            {
                                if (kind > 20)
                                {
                                    kind = 20;
                                }
                            }
                            break;
                        case 0:
                            if ((0xffffff7fffffffffL & l) == 0L)
                            {
                                break;
                            }
                            kind = 19;
                            jjCheckNAdd(0);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else if (curChar < 128)
            {
                long l = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 1:
                        case 0:
                            kind = 19;
                            jjCheckNAdd(0);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else
            {
                int i2 = (curChar & 0xff) >> 6;
                long l2 = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 1:
                        case 0:
                            if ((jjbitVec0[i2] & l2) == 0L)
                            {
                                break;
                            }
                            if (kind > 19)
                            {
                                kind = 19;
                            }
                            jjCheckNAdd(0);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            if (kind != 0x7fffffff)
            {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            ++curPos;
            if ((i = jjnewStateCnt) == (startsAt = 2 - (jjnewStateCnt = startsAt)))
            {
                return curPos;
            }
            try
            {
                curChar = input_stream.readChar();
            } catch (java.io.IOException e)
            {
                return curPos;
            }
        }
    }

    /**
     * Jj stop string literal dfa_2.
     * 
     * @param pos the pos
     * @param active0 the active0
     * @return the int
     */
    private final int jjStopStringLiteralDfa_2(int pos, long active0)
    {
        switch (pos)
        {
            default:
                return -1;
        }
    }

    /**
     * Jj start nfa_2.
     * 
     * @param pos the pos
     * @param active0 the active0
     * @return the int
     */
    private final int jjStartNfa_2(int pos, long active0)
    {
        return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
    }

    /**
     * Jj start nfa with states_2.
     * 
     * @param pos the pos
     * @param kind the kind
     * @param state the state
     * @return the int
     */
    private final int jjStartNfaWithStates_2(int pos, int kind, int state)
    {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        try
        {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e)
        {
            return pos + 1;
        }
        return jjMoveNfa_2(state, pos + 1);
    }

    /**
     * Jj move string literal dfa0_2.
     * 
     * @return the int
     */
    private final int jjMoveStringLiteralDfa0_2()
    {
        switch (curChar)
        {
            case 34:
                return jjStopAtPos(0, 17);
            case 39:
                return jjStopAtPos(0, 16);
            default:
                return jjMoveNfa_2(0, 0);
        }
    }

    /**
     * Jj move nfa_2.
     * 
     * @param startState the start state
     * @param curPos the cur pos
     * @return the int
     */
    private final int jjMoveNfa_2(int startState, int curPos)
    {
        int[] nextStates;
        int startsAt = 0;
        jjnewStateCnt = 3;
        int i = 1;
        jjstateSet[0] = startState;
        int j, kind = 0x7fffffff;
        for (;;)
        {
            if (++jjround == 0x7fffffff)
            {
                ReInitRounds();
            }
            if (curChar < 64)
            {
                long l = 1L << curChar;
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 0:
                            if ((0x9fffff7affffd9ffL & l) != 0L)
                            {
                                if (kind > 15)
                                {
                                    kind = 15;
                                }
                                jjCheckNAdd(1);
                            } else if ((0x100002600L & l) != 0L)
                            {
                                if (kind > 18)
                                {
                                    kind = 18;
                                }
                                jjCheckNAdd(2);
                            }
                            break;
                        case 1:
                            if ((0xbffffffeffffd9ffL & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 15)
                            {
                                kind = 15;
                            }
                            jjCheckNAdd(1);
                            break;
                        case 2:
                            if ((0x100002600L & l) == 0L)
                            {
                                break;
                            }
                            kind = 18;
                            jjCheckNAdd(2);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else if (curChar < 128)
            {
                long l = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 0:
                        case 1:
                            if (kind > 15)
                            {
                                kind = 15;
                            }
                            jjCheckNAdd(1);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else
            {
                int i2 = (curChar & 0xff) >> 6;
                long l2 = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 0:
                        case 1:
                            if ((jjbitVec0[i2] & l2) == 0L)
                            {
                                break;
                            }
                            if (kind > 15)
                            {
                                kind = 15;
                            }
                            jjCheckNAdd(1);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            if (kind != 0x7fffffff)
            {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            ++curPos;
            if ((i = jjnewStateCnt) == (startsAt = 3 - (jjnewStateCnt = startsAt)))
            {
                return curPos;
            }
            try
            {
                curChar = input_stream.readChar();
            } catch (java.io.IOException e)
            {
                return curPos;
            }
        }
    }

    /**
     * Jj stop string literal dfa_5.
     * 
     * @param pos the pos
     * @param active0 the active0
     * @return the int
     */
    private final int jjStopStringLiteralDfa_5(int pos, long active0)
    {
        switch (pos)
        {
            case 0:
                if ((active0 & 0x1000000L) != 0L)
                {
                    jjmatchedKind = 23;
                    return -1;
                }
                return -1;
            case 1:
                if ((active0 & 0x1000000L) != 0L)
                {
                    if (jjmatchedPos == 0)
                    {
                        jjmatchedKind = 23;
                        jjmatchedPos = 0;
                    }
                    return -1;
                }
                return -1;
            default:
                return -1;
        }
    }

    /**
     * Jj start nfa_5.
     * 
     * @param pos the pos
     * @param active0 the active0
     * @return the int
     */
    private final int jjStartNfa_5(int pos, long active0)
    {
        return jjMoveNfa_5(jjStopStringLiteralDfa_5(pos, active0), pos + 1);
    }

    /**
     * Jj start nfa with states_5.
     * 
     * @param pos the pos
     * @param kind the kind
     * @param state the state
     * @return the int
     */
    private final int jjStartNfaWithStates_5(int pos, int kind, int state)
    {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        try
        {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e)
        {
            return pos + 1;
        }
        return jjMoveNfa_5(state, pos + 1);
    }

    /**
     * Jj move string literal dfa0_5.
     * 
     * @return the int
     */
    private final int jjMoveStringLiteralDfa0_5()
    {
        switch (curChar)
        {
            case 45:
                return jjMoveStringLiteralDfa1_5(0x1000000L);
            default:
                return jjMoveNfa_5(1, 0);
        }
    }

    /**
     * Jj move string literal dfa1_5.
     * 
     * @param active0 the active0
     * @return the int
     */
    private final int jjMoveStringLiteralDfa1_5(long active0)
    {
        try
        {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e)
        {
            jjStopStringLiteralDfa_5(0, active0);
            return 1;
        }
        switch (curChar)
        {
            case 45:
                return jjMoveStringLiteralDfa2_5(active0, 0x1000000L);
            default:
                break;
        }
        return jjStartNfa_5(0, active0);
    }

    /**
     * Jj move string literal dfa2_5.
     * 
     * @param old0 the old0
     * @param active0 the active0
     * @return the int
     */
    private final int jjMoveStringLiteralDfa2_5(long old0, long active0)
    {
        if (((active0 &= old0)) == 0L)
        {
            return jjStartNfa_5(0, old0);
        }
        try
        {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e)
        {
            jjStopStringLiteralDfa_5(1, active0);
            return 2;
        }
        switch (curChar)
        {
            case 62:
                if ((active0 & 0x1000000L) != 0L)
                {
                    return jjStopAtPos(2, 24);
                }
                break;
            default:
                break;
        }
        return jjStartNfa_5(1, active0);
    }

    /**
     * Jj move nfa_5.
     * 
     * @param startState the start state
     * @param curPos the cur pos
     * @return the int
     */
    private final int jjMoveNfa_5(int startState, int curPos)
    {
        int[] nextStates;
        int startsAt = 0;
        jjnewStateCnt = 2;
        int i = 1;
        jjstateSet[0] = startState;
        int j, kind = 0x7fffffff;
        for (;;)
        {
            if (++jjround == 0x7fffffff)
            {
                ReInitRounds();
            }
            if (curChar < 64)
            {
                long l = 1L << curChar;
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 1:
                            if ((0xffffdfffffffffffL & l) != 0L)
                            {
                                if (kind > 23)
                                {
                                    kind = 23;
                                }
                                jjCheckNAdd(0);
                            } else if (curChar == 45)
                            {
                                if (kind > 23)
                                {
                                    kind = 23;
                                }
                            }
                            break;
                        case 0:
                            if ((0xffffdfffffffffffL & l) == 0L)
                            {
                                break;
                            }
                            kind = 23;
                            jjCheckNAdd(0);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else if (curChar < 128)
            {
                long l = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 1:
                        case 0:
                            kind = 23;
                            jjCheckNAdd(0);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else
            {
                int i2 = (curChar & 0xff) >> 6;
                long l2 = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 1:
                        case 0:
                            if ((jjbitVec0[i2] & l2) == 0L)
                            {
                                break;
                            }
                            if (kind > 23)
                            {
                                kind = 23;
                            }
                            jjCheckNAdd(0);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            if (kind != 0x7fffffff)
            {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            ++curPos;
            if ((i = jjnewStateCnt) == (startsAt = 2 - (jjnewStateCnt = startsAt)))
            {
                return curPos;
            }
            try
            {
                curChar = input_stream.readChar();
            } catch (java.io.IOException e)
            {
                return curPos;
            }
        }
    }

    /**
     * Jj stop string literal dfa_1.
     * 
     * @param pos the pos
     * @param active0 the active0
     * @return the int
     */
    private final int jjStopStringLiteralDfa_1(int pos, long active0)
    {
        switch (pos)
        {
            default:
                return -1;
        }
    }

    /**
     * Jj start nfa_1.
     * 
     * @param pos the pos
     * @param active0 the active0
     * @return the int
     */
    private final int jjStartNfa_1(int pos, long active0)
    {
        return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
    }

    /**
     * Jj start nfa with states_1.
     * 
     * @param pos the pos
     * @param kind the kind
     * @param state the state
     * @return the int
     */
    private final int jjStartNfaWithStates_1(int pos, int kind, int state)
    {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        try
        {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e)
        {
            return pos + 1;
        }
        return jjMoveNfa_1(state, pos + 1);
    }

    /**
     * Jj move string literal dfa0_1.
     * 
     * @return the int
     */
    private final int jjMoveStringLiteralDfa0_1()
    {
        switch (curChar)
        {
            case 34:
                return jjStopAtPos(0, 17);
            case 39:
                return jjStopAtPos(0, 16);
            case 61:
                return jjStartNfaWithStates_1(0, 13, 3);
            default:
                return jjMoveNfa_1(0, 0);
        }
    }

    /**
     * Jj move nfa_1.
     * 
     * @param startState the start state
     * @param curPos the cur pos
     * @return the int
     */
    private final int jjMoveNfa_1(int startState, int curPos)
    {
        int[] nextStates;
        int startsAt = 0;
        jjnewStateCnt = 6;
        int i = 1;
        jjstateSet[0] = startState;
        int j, kind = 0x7fffffff;
        for (;;)
        {
            if (++jjround == 0x7fffffff)
            {
                ReInitRounds();
            }
            if (curChar < 64)
            {
                long l = 1L << curChar;
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 0:
                            if ((0x9fffff7affffd9ffL & l) != 0L)
                            {
                                if (kind > 12)
                                {
                                    kind = 12;
                                }
                                jjCheckNAdd(1);
                            } else if ((0x100002600L & l) != 0L)
                            {
                                if (kind > 18)
                                {
                                    kind = 18;
                                }
                                jjCheckNAdd(5);
                            } else if (curChar == 61)
                            {
                                jjstateSet[jjnewStateCnt++] = 3;
                            } else if (curChar == 62)
                            {
                                if (kind > 14)
                                {
                                    kind = 14;
                                }
                            }
                            break;
                        case 1:
                            if ((0x9ffffffeffffd9ffL & l) == 0L)
                            {
                                break;
                            }
                            if (kind > 12)
                            {
                                kind = 12;
                            }
                            jjCheckNAdd(1);
                            break;
                        case 2:
                        case 3:
                            if (curChar == 62 && kind > 14)
                            {
                                kind = 14;
                            }
                            break;
                        case 4:
                            if (curChar == 61)
                            {
                                jjstateSet[jjnewStateCnt++] = 3;
                            }
                            break;
                        case 5:
                            if ((0x100002600L & l) == 0L)
                            {
                                break;
                            }
                            kind = 18;
                            jjCheckNAdd(5);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else if (curChar < 128)
            {
                long l = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 0:
                        case 1:
                            if (kind > 12)
                            {
                                kind = 12;
                            }
                            jjCheckNAdd(1);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else
            {
                int i2 = (curChar & 0xff) >> 6;
                long l2 = 1L << (curChar & 077);
                MatchLoop:
                do
                {
                    switch (jjstateSet[--i])
                    {
                        case 0:
                        case 1:
                            if ((jjbitVec0[i2] & l2) == 0L)
                            {
                                break;
                            }
                            if (kind > 12)
                            {
                                kind = 12;
                            }
                            jjCheckNAdd(1);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            if (kind != 0x7fffffff)
            {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            ++curPos;
            if ((i = jjnewStateCnt) == (startsAt = 6 - (jjnewStateCnt = startsAt)))
            {
                return curPos;
            }
            try
            {
                curChar = input_stream.readChar();
            } catch (java.io.IOException e)
            {
                return curPos;
            }
        }
    }
    
    /** The Constant jjnextStates. */
    static final int[] jjnextStates =
    {
        17, 18, 21, 12, 14, 5, 8, 0, 4, 6, 0, 4, 6, 5, 0, 4,
        6, 12, 13,
    };
    
    /** The Constant jjstrLiteralImages. */
    public static final String[] jjstrLiteralImages =
    {
        "", null, null, "\74\41\55\55", "\74\41", null, null, null, null, null, null,
        null, null, "\75", null, null, "\47", "\42", null, null, null, null, null, null,
        "\55\55\76", null, "\76",
    };
    
    /** The Constant lexStateNames. */
    public static final String[] lexStateNames =
    {
        "DEFAULT",
        "WithinTag",
        "AfterEquals",
        "WithinQuote1",
        "WithinQuote2",
        "WithinComment1",
        "WithinComment2",
    };
    
    /** The Constant jjnewLexState. */
    public static final int[] jjnewLexState =
    {
        -1, 1, 1, 5, 6, -1, -1, -1, -1, -1, -1, -1, -1, 2, 0, 1, 3, 4, -1, -1, 1, -1, 1, -1, 0,
        -1, 0,
    };
    
    /** The Constant jjtoToken. */
    static final long[] jjtoToken =
    {
        0x7fbfb3fL,
    };
    
    /** The Constant jjtoSkip. */
    static final long[] jjtoSkip =
    {
        0x40000L,
    };
    
    /** The input_stream. */
    protected SimpleCharStream input_stream;
    
    /** The jjrounds. */
    private final int[] jjrounds = new int[25];
    
    /** The jjstate set. */
    private final int[] jjstateSet = new int[50];
    
    /** The cur char. */
    protected char curChar;

    /**
     * Instantiates a new hTML parser token manager.
     * 
     * @param stream the stream
     */
    public HTMLParserTokenManager(SimpleCharStream stream)
    {
        if (SimpleCharStream.staticFlag)
        {
            throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
        }
        input_stream = stream;
    }

    /**
     * Instantiates a new hTML parser token manager.
     * 
     * @param stream the stream
     * @param lexState the lex state
     */
    public HTMLParserTokenManager(SimpleCharStream stream, int lexState)
    {
        this(stream);
        SwitchTo(lexState);
    }

    /**
     * Re init.
     * 
     * @param stream the stream
     */
    public void ReInit(SimpleCharStream stream)
    {
        jjmatchedPos = jjnewStateCnt = 0;
        curLexState = defaultLexState;
        input_stream = stream;
        ReInitRounds();
    }

    /**
     * Re init rounds.
     */
    private final void ReInitRounds()
    {
        int i;
        jjround = 0x80000001;
        for (i = 25; i-- > 0;)
        {
            jjrounds[i] = 0x80000000;
        }
    }

    /**
     * Re init.
     * 
     * @param stream the stream
     * @param lexState the lex state
     */
    public void ReInit(SimpleCharStream stream, int lexState)
    {
        ReInit(stream);
        SwitchTo(lexState);
    }

    /**
     * Switch to.
     * 
     * @param lexState the lex state
     */
    public void SwitchTo(int lexState)
    {
        if (lexState >= 7 || lexState < 0)
        {
            throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
        } else
        {
            curLexState = lexState;
        }
    }

    /**
     * Jj fill token.
     * 
     * @return the token
     */
    protected Token jjFillToken()
    {
        Token t = Token.newToken(jjmatchedKind);
        t.kind = jjmatchedKind;
        String im = jjstrLiteralImages[jjmatchedKind];
        t.image = (im == null) ? input_stream.GetImage() : im;
        t.beginLine = input_stream.getBeginLine();
        t.beginColumn = input_stream.getBeginColumn();
        t.endLine = input_stream.getEndLine();
        t.endColumn = input_stream.getEndColumn();
        return t;
    }
    
    /** The cur lex state. */
    int curLexState = 0;
    
    /** The default lex state. */
    int defaultLexState = 0;
    
    /** The jjnew state cnt. */
    int jjnewStateCnt;
    
    /** The jjround. */
    int jjround;
    
    /** The jjmatched pos. */
    int jjmatchedPos;
    
    /** The jjmatched kind. */
    int jjmatchedKind;

    /**
     * Gets the next token.
     * 
     * @return the next token
     */
    public Token getNextToken()
    {
        int kind;
        Token specialToken = null;
        Token matchedToken;
        int curPos = 0;

        EOFLoop:
        for (;;)
        {
            try
            {
                curChar = input_stream.BeginToken();
            } catch (java.io.IOException e)
            {
                jjmatchedKind = 0;
                matchedToken = jjFillToken();
                return matchedToken;
            }

            switch (curLexState)
            {
                case 0:
                    jjmatchedKind = 0x7fffffff;
                    jjmatchedPos = 0;
                    curPos = jjMoveStringLiteralDfa0_0();
                    if (jjmatchedPos == 0 && jjmatchedKind > 11)
                    {
                        jjmatchedKind = 11;
                    }
                    break;
                case 1:
                    jjmatchedKind = 0x7fffffff;
                    jjmatchedPos = 0;
                    curPos = jjMoveStringLiteralDfa0_1();
                    break;
                case 2:
                    jjmatchedKind = 0x7fffffff;
                    jjmatchedPos = 0;
                    curPos = jjMoveStringLiteralDfa0_2();
                    break;
                case 3:
                    jjmatchedKind = 0x7fffffff;
                    jjmatchedPos = 0;
                    curPos = jjMoveStringLiteralDfa0_3();
                    break;
                case 4:
                    jjmatchedKind = 0x7fffffff;
                    jjmatchedPos = 0;
                    curPos = jjMoveStringLiteralDfa0_4();
                    break;
                case 5:
                    jjmatchedKind = 0x7fffffff;
                    jjmatchedPos = 0;
                    curPos = jjMoveStringLiteralDfa0_5();
                    break;
                case 6:
                    jjmatchedKind = 0x7fffffff;
                    jjmatchedPos = 0;
                    curPos = jjMoveStringLiteralDfa0_6();
                    break;
            }
            if (jjmatchedKind != 0x7fffffff)
            {
                if (jjmatchedPos + 1 < curPos)
                {
                    input_stream.backup(curPos - jjmatchedPos - 1);
                }
                if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
                {
                    matchedToken = jjFillToken();
                    if (jjnewLexState[jjmatchedKind] != -1)
                    {
                        curLexState = jjnewLexState[jjmatchedKind];
                    }
                    return matchedToken;
                } else
                {
                    if (jjnewLexState[jjmatchedKind] != -1)
                    {
                        curLexState = jjnewLexState[jjmatchedKind];
                    }
                    continue EOFLoop;
                }
            }
            int error_line = input_stream.getEndLine();
            int error_column = input_stream.getEndColumn();
            String error_after = null;
            boolean EOFSeen = false;
            try
            {
                input_stream.readChar();
                input_stream.backup(1);
            } catch (java.io.IOException e1)
            {
                EOFSeen = true;
                error_after = curPos <= 1 ? "" : input_stream.GetImage();
                if (curChar == '\n' || curChar == '\r')
                {
                    error_line++;
                    error_column = 0;
                } else
                {
                    error_column++;
                }
            }
            if (!EOFSeen)
            {
                input_stream.backup(1);
                error_after = curPos <= 1 ? "" : input_stream.GetImage();
            }
            throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
        }
    }
}
