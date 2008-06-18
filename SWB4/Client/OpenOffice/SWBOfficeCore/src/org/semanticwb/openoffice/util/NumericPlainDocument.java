/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 *
 * @author victor.lorenzana
 */
public class NumericPlainDocument extends FixedLengthPlainDocument
{

    protected InsertErrorListener errorListener;
    protected DecimalFormat format;
    protected char decimalSeparator;
    protected char groupingSeparator;
    protected String positivePrefix;
    protected String negativePrefix;
    protected int positivePrefixLen;
    protected int negativePrefixLen;
    protected String positiveSuffix;
    protected String negativeSuffix;
    protected int positiveSuffixLen;
    protected int negativeSuffixLen;
    protected ParsePosition parsePos = new ParsePosition(0);
    protected final static DecimalFormat defaultFormat = new DecimalFormat();

    public NumericPlainDocument(int maxLength, DecimalFormat format)
    {
        super(maxLength);
        setFormat(format);
    }

    public void setFormat(DecimalFormat fmt)
    {
        this.format = fmt != null ? fmt : (DecimalFormat) defaultFormat.clone();

        decimalSeparator = format.getDecimalFormatSymbols().getDecimalSeparator();
        groupingSeparator = format.getDecimalFormatSymbols().getGroupingSeparator();
        positivePrefix = format.getPositivePrefix();
        positivePrefixLen = positivePrefix.length();
        negativePrefix = format.getNegativePrefix();
        negativePrefixLen = negativePrefix.length();
        positiveSuffix = format.getPositiveSuffix();
        positiveSuffixLen = positiveSuffix.length();
        negativeSuffix = format.getNegativeSuffix();
        negativeSuffixLen = negativeSuffix.length();
    }

    public DecimalFormat getFormat()
    {
        return format;
    }

    public Number getNumberValue() throws ParseException
    {
        try
        {
            String content = getText(0, getLength());
            parsePos.setIndex(0);
            Number result = format.parse(content, parsePos);
            if (parsePos.getIndex() != getLength())
            {
                throw new ParseException("Not a valid number: " + content, 0);
            }

            return result;
        }
        catch (BadLocationException e)
        {
            throw new ParseException("Not a valid number", 0);
        }
    }

    public Long getLongValue() throws ParseException
    {
        Number result = getNumberValue();
        if ((result instanceof Long) == false)
        {
            throw new ParseException("Not a valid long", 0);
        }

        return (Long) result;
    }

    public Double getDoubleValue() throws ParseException
    {
        Number result = getNumberValue();
        if ((result instanceof Long) == false && (result instanceof Double) == false)
        {
            throw new ParseException("Not a valid double", 0);
        }

        if (result instanceof Long)
        {
            result = new Double(result.doubleValue());
        }

        return (Double) result;
    }

    public void insertString(int offset, String str, AttributeSet a)
            throws BadLocationException
    {
        if (!(str == null || str.length() == 0))        
        {
            Content content = getContent();
            int length = content.length();
            int originalLength = length;

            parsePos.setIndex(0);

            // Create the result of inserting the new data,
            // but ignore the trailing newline
            StringBuilder targetString = new StringBuilder(content.getString(0, offset) + str + content.getString(offset, length - offset - 1));

            // Parse the input string and check for errors
            do
            {
                boolean gotPositive = targetString.toString().startsWith(positivePrefix);
                boolean gotNegative = targetString.toString().startsWith(negativePrefix);

                length = targetString.length();

                // If we have a valid prefix, the parse fails if the
                // suffix is not present and the error is reported
                // at index 0. So, we need to add the appropriate
                // suffix if it is not present at this point.
                if (gotPositive == true || gotNegative == true)
                {
                    String suffix;
                    int suffixLength;
                    int prefixLength;
                    gotNegative=gotPositive == true && gotNegative == true ? false : true;                
                    if (gotPositive == true)
                    {
                        suffix = positiveSuffix;
                        suffixLength = positiveSuffixLen;
                        prefixLength = positivePrefixLen;
                    }
                    else
                    {
                        // Must have the negative prefix
                        suffix = negativeSuffix;
                        suffixLength = negativeSuffixLen;
                        prefixLength = negativePrefixLen;
                    }

                    // If the string consists of the prefix alone,
                    // do nothing, or the result won't parse.
                    if (length == prefixLength)
                    {
                        break;
                    }

                    // We can't just add the suffix, because part of it
                    // may already be there. For example, suppose the
                    // negative prefix is "(" and the negative suffix is
                    // "$)". If the user has typed "(345$", then it is not
                    // correct to add "$)". Instead, only the missing part
                    // should be added, in this case ")".
                    if (targetString.toString().endsWith(suffix) == false)
                    {
                        int i;
                        for (i = suffixLength - 1; i > 0; i--)
                        {
                            if (targetString.toString().regionMatches(length - i, suffix, 0, i))
                            {
                                targetString.append(suffix.substring(i));
                                break;
                            }
                        }

                        if (i == 0)
                        {
                            // None of the suffix was present
                            targetString.append(suffix);
                        }

                        length = targetString.length();
                    }
                }

                format.parse(targetString.toString(), parsePos);

                int endIndex = parsePos.getIndex();
                if (endIndex == length)
                {
                    break; // Number is acceptable
                }

                // Parse ended early
                // Since incomplete numbers don't always parse, try
                // to work out what went wrong.
                // First check for an incomplete positive prefix
                if (positivePrefixLen > 0 && endIndex < positivePrefixLen && length <= positivePrefixLen && targetString.toString().regionMatches(0, positivePrefix, 0, length))
                {
                    break; // Accept for now
                }

                // Next check for an incomplete negative prefix
                if (negativePrefixLen > 0 && endIndex < negativePrefixLen && length <= negativePrefixLen && targetString.toString().regionMatches(0, negativePrefix, 0, length))
                {
                    break; // Accept for now
                }

                // Allow a number that ends with the group
                // or decimal separator, if these are in use
                char lastChar = targetString.toString().charAt(originalLength - 1);
                int decimalIndex = targetString.toString().indexOf(decimalSeparator);
                if (format.isGroupingUsed() && lastChar == groupingSeparator && decimalIndex == -1)
                {
                    // Allow a "," but only in integer part
                    break;
                }

                if (format.isParseIntegerOnly() == false && lastChar == decimalSeparator && decimalIndex == originalLength - 1)
                {
                    // Allow a ".", but only one
                    break;
                }

                // No more corrections to make: must be an error
                if (errorListener != null)
                {
                    errorListener.insertFailed(this, offset, str, a);
                }
                return;
            }
            while (true == false);

            // Finally, add to the model
            super.insertString(offset, str, a);
        }


    }

    public void addInsertErrorListener(InsertErrorListener l)
    {
        if (errorListener == null)
        {
            errorListener = l;
            return;
        }
        throw new IllegalArgumentException(
                "InsertErrorListener already registered");
    }

    public void removeInsertErrorListener(InsertErrorListener l)
    {
        if (errorListener == l)
        {
            errorListener = null;
        }
    }

    public interface InsertErrorListener
    {

        public abstract void insertFailed(NumericPlainDocument doc, int offset,
                String str, AttributeSet a);
    }
}


