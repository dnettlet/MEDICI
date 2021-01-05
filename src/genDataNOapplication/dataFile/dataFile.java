//**************process whole line with regex to extract variables*****************
package genDataNOapplication.dataFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.*;
import java.lang.CharSequence;

public class dataFile {
	private static final String DELIMITADORS = ";";
	private Pattern patro = Pattern.compile( "(\\d+(,\\d+)*(\\.\\d+)?)|([A-Z]\\w*)" );
   
	public void processarLinea(String text, String delim, 
							  Object[] data, int j){
		String oneToken;
		int i=0;
		StringTokenizer tokens = new StringTokenizer(text);
		while(tokens.hasMoreTokens() && i < j){
			oneToken = tokens.nextToken(delim);
			data[i] = oneToken;
			i++;
		} // fin de while
	} // fin de processarLinea
				
} // end of public class dataFile