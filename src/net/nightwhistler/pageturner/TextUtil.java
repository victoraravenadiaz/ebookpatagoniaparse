/*
 * Copyright (C) 2013 Alex Kuiper
 *
 * This file is part of PageTurner
 *
 * PageTurner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PageTurner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PageTurner.  If not, see <http://www.gnu.org/licenses/>.*
 */
package net.nightwhistler.pageturner;

import android.text.Spannable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

public class TextUtil {

    private static final Pattern PUNCTUATION = Pattern.compile("\\.( ?\\.)*[\"'”]?|[\\?!] ?[\"'”]?|, ?[\"']|”");

    private TextUtil() {}

    /**
     * Processes an input string and enters a newline after full stops,
     * question marks, etc.
     *
     * @param input
     * @return
     */
    public static String splitOnPunctuation(String input) {

        StringBuffer result = new StringBuffer();

        Matcher matcher = PUNCTUATION.matcher(input);

        while (matcher.find()) {

            String match = matcher.group();
            matcher.appendReplacement(result, match + "\n");
        }

        matcher.appendTail(result);
        return result.toString();

    }

    public static String shortenText( String original ) {

        String text = original;

        if ( text.length() > 40 ) {
            text = text.substring(0, 40) + "…";
        }

        return text;
    }
    public static boolean existeTexto(String sTexto, String sTextoBuscado){

        if(sTexto.indexOf(sTextoBuscado) > -1){
            return true;
        }
        return false;
    }

    /**
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static  String convertStreamToString(InputStream is)
            throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = is.read();
        while (i != -1)
        {
            baos.write(i);
            i = is.read();
        }
        return baos.toString();
    }
    /** Convierte bytes en texto
     * @param inputStream de tipo InputStream
     * */
    public static String btoString( InputStream inputStream ) throws IOException
    {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        byte[] bytes = new byte[4096];
        int len=0;
        while ( (len=inputStream.read(bytes))>0 )
        {
            b.write(bytes,0,len);
        }
        return new String( b.toByteArray(),"UTF8");
    }
}
