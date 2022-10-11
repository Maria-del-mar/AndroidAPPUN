package co.edu.unal.gpsreto9;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUrl {

    public String retireveUrl(String url) throws IOException{

        String urlData="";

        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;

        try{
            URL getUrl= new URL (url);
            httpURLConnection=(HttpURLConnection) getUrl.openConnection();
            httpURLConnection.connect();

            //read the data from the url
            inputStream=httpURLConnection.getInputStream();

            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb= new StringBuffer();

            //read each line one by one
            String line="";//stores each line
            //append it to the String buffer

            while((line=bufferedReader.readLine())!=null){
                sb.append(line);
            }

            urlData=sb.toString();//convert string buffer to string and store it in urlData
            bufferedReader.close();

        }catch (Exception e){
            Log.d("Exception",e.toString());
        }finally {
            inputStream.close();
            httpURLConnection.disconnect();
        }
        return urlData;
    }
}
