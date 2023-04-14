package utils;

import java.util.ArrayList;
import java.util.Iterator;

public class StringUtil {

    public final static ArrayList<String> splitToArrayList(final String pInput, final char pDelim)
    {
        return splitToArrayListWithStopper(pInput, pDelim, null);
    }

    public final static String[] toStringArray(final String pList, final char pDelimiter)
    {
        if (pList == null)
        {
            return null;
        }
        else if (pList.length() == 0)
        {
            return new String[] { pList };
        }

        ArrayList<String> list = StringUtil.splitToArrayList(pList, pDelimiter);
        String[] stringArray = new String[list.size()];
        int count = -1;
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext())
        {
            count++;
            stringArray[count] = (String) iterator.next();
        }
        return stringArray;
    }

    /**
     * Split a string by a given character, into sub-strings (array list
     * format),
     */
    public final static ArrayList<String> splitToArrayListWithStopper(final String pInput, final char pDelim, String pStopAtLineVal)
    {
        // split string based on charachers

        StringBuilder sbThisRow = new StringBuilder(200);
        ArrayList<String> out = new ArrayList<>(100);

        //String str = new String(pInputString);

        // if the string is empty, then retun an empty array list
        if (pInput.length() > 0)
        {
            char c;

            for (int i = 0; i < pInput.length(); i++)
            {
                c = pInput.charAt(i);

                if (c == pDelim)
                {
                    String line = sbThisRow.toString().trim();
                    out.add(line);

                    if (pStopAtLineVal != null && pStopAtLineVal.equals(line))
                        break;

                    sbThisRow.setLength(0);
                }
                else
                {
                    sbThisRow.append(c);
                }
            }

            // last row (if any)
            if (sbThisRow.length() > 0)
            {
                String line = sbThisRow.toString().trim();
                out.add(line);
            }
        }

        return out;
    }


    public static String getAtRight(String pList, char pDelimiter, int pIndex)
    {
        ArrayList<String> list = StringUtil.splitToArrayList(pList, pDelimiter);

        if (pIndex <= list.size())
        {
            return (String) list.get(list.size() - pIndex);
        }
        else
        {
            return "";
        }
    }

    public static int size(String pList, char pDelimiter)
    {
        ArrayList<String> list = StringUtil.splitToArrayList(pList, pDelimiter);
        return list.size();
    }

    public static String deleteAt(String pList, char pDelimiter, int pIndex)
    {
        ArrayList<String> list = splitToArrayList(pList, pDelimiter);

        StringBuilder s = new StringBuilder(pList.length());

        int count = 0;

        if (list.size() > 0 && pIndex <= list.size())
        {
            for (int i = 0; i < list.size(); i++)
            {
                if (i != (pIndex - 1))
                {
                    if (count > 0)
                    {
                        s.append(pDelimiter);
                    }
                    s.append((String) list.get(i));

                    count++;
                }
            }

            return s.toString();
        }
        else
        {
            return pList;
        }
    }
    public static String deleteAtRight(String pList, char pDelimiter, int pIndex)
    {
        ArrayList<String> list = StringUtil.splitToArrayList(pList, pDelimiter);

        return deleteAt(pList, pDelimiter, list.size() - pIndex + 1);
    }

    /**
     * Replace an occurrence of a string with another
     */
    public static String replaceAll(String pInputString, String pSource, String pReplacement)
    {
        if (pInputString == null || pInputString.length() == 0)
            return pInputString;

        if (pSource == null || pSource.length() == 0)
        {
            throw new IllegalArgumentException("Source string cannot be null!");
        }
        if (pReplacement == null)
        {
            throw new IllegalArgumentException("Replacement string cannot be null!");
        }

        //String out = new String(pInputString);

        int index = 0;
        int lastIndex = 0;

        ArrayList<Integer> posList = new ArrayList<>();
        int replacementLen = pReplacement.length();
        int sourceLen = pSource.length();

        int stringLen = pInputString.length();

        while (lastIndex < stringLen)
        {
            index = pInputString.indexOf(pSource, lastIndex);

            if (index != -1)
            {
                posList.add(index);

                lastIndex = index + sourceLen;
            }
            else
            {
                break;
            }
        }

        if (posList.size() > 0)
        {
            ArrayList<Integer> posFromList = new ArrayList(posList.size() + 1);
            ArrayList<Integer> posToList = new ArrayList(posList.size() + 1);

            int posFrom = -1;
            int posTo = -1;
            int idx = -1;

            if (posList.size() == 1)
            {
                idx = posList.get(0);
                posFrom = 0;
                posTo = idx;

                posFromList.add(posFrom);
                posToList.add(posTo);

                // -1 indicates we insert the replacement here
                posFromList.add(-1);
                posToList.add(-1);

                if (posTo < stringLen)
                {
                    posFrom = posTo + sourceLen;
                    posTo = stringLen;

                    posFromList.add(posFrom);
                    posToList.add(posTo);
                }

            }
            else
            {
                for (int i = 0; i < posList.size(); i++)
                {
                    idx = posList.get(i);

                    if (i == 0)
                    {
                        posFrom = 0;
                        posTo = idx;
                    }
                    else
                    {
                        posFrom = posTo + sourceLen;
                        posTo = idx;
                    }

                    posFromList.add(posFrom);
                    posToList.add(posTo);

                    // -1 indicates we insert the replacement here
                    posFromList.add(-1);
                    posToList.add(-1);

                }

                // last item
                if (posTo < stringLen)
                {
                    posFrom = posTo + sourceLen;
                    posTo = stringLen;

                    posFromList.add(posFrom);
                    posToList.add(posTo);
                }
            }

            StringBuilder sb = new StringBuilder(stringLen);

            String sub = null;
            for (int i = 0; i < posFromList.size(); i++)
            {
                posFrom = posFromList.get(i);
                posTo = posToList.get(i);

                if (posFrom == -1)
                {
                    if (replacementLen > 0)
                        sb.append(pReplacement);
                }
                else
                {
                    sub = pInputString.substring(posFrom, posTo);
                    sb.append(sub);
                }

            }

            return sb.toString();

        }
        else
        {
            return pInputString;
        }
    }

    public static String zeros(int pNumber, int pLength)
    {
        String val = pNumber + "";

        int num = pLength - val.length();

        if (num > 0)
            return StringUtil.repeat('0', num) + pNumber;

        return val;
    }

    public static String repeat(char pRepeat, int pNumber)
    {
        if (pNumber == 0)
            return "";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < pNumber; i++)
        {
            sb.append(pRepeat);
        }

        return sb.toString();
    }
}
