package films;

import java.io.*;
import java.net.*;

/** This class does a simple HTTP GET and writes the retrieved content to a local file
 *
 * @author Brian Pipa - http://pipasoft.com
 * @version 1.0
 */
public class Wget {

    static final String FS = File.separator;

    /** This method does the actual GET
     *
     * @param theUrl The URL to retrieve
     * @exception IOException
     */
    public String get(String theUrl) throws IOException
    {
        try {
            URL gotoUrl = new URL(theUrl);
            InputStreamReader isr = new InputStreamReader(gotoUrl.openStream(), "UTF-8");
            BufferedReader in = new BufferedReader(isr);

            URLConnection conn = gotoUrl.openConnection();
            conn.connect();
            String contentType = conn.getContentType();
            System.out.print(contentType.toString());
            System.out.print(contentType.indexOf("html"));

            StringBuffer sb = new StringBuffer();
            String inputLine;
            boolean isFirst = true;

            //grab the contents at the URL
            while ((inputLine = in.readLine()) != null){
                //System.out.println(inputLine);
                sb.append(inputLine+"\r\n");
            }
            //write it locally
            //createAFile(filename, sb.toString());
            return sb.toString();
            //return contentType;
        }
        catch (MalformedURLException mue) {
            mue.printStackTrace();
            return "";
        }
        catch (IOException ioe) {
            throw ioe;
        }
    }

    //creates a local file
    /** Writes a String to a local file
     *
     * @param outfile the file to write to
     * @param content the contents of the file
     * @exception IOException
     */
    public static void createAFile(String outfile, String content) throws IOException {
        FileOutputStream fileoutputstream = new FileOutputStream(outfile);
        DataOutputStream dataoutputstream = new DataOutputStream(fileoutputstream);
        dataoutputstream.writeBytes(content);
        dataoutputstream.flush();
        dataoutputstream.close();
    }

    /** The main method.
     *
     * @param args
     */

}