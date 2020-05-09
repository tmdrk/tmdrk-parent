package org.tmdrk.toturial.thread.estimate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName AsyncIOTask
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/7 14:44
 * @Version 1.0
 **/
public class AsyncIOTask implements Runnable{
    @Override
    public void run() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("http://baidu.com");

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            String line;
            StringBuilder stringBuilder;
            while ((line = reader.readLine()) != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(line);
            }
        }

        catch (IOException e) {

        } finally {
            if(reader != null) {
                try {
                    reader.close();
                }
                catch(Exception e) {

                }
            }
            if (connection != null)
                connection.disconnect();
        }

    }
}
