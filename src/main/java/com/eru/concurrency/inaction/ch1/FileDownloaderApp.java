package com.eru.concurrency.inaction.ch1;

import com.eru.concurrency.inaction.util.Debug;
import com.eru.concurrency.inaction.util.Tools;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by eru on 2020/3/11.
 */
public class FileDownloaderApp {
    public static void main(String[] args) {
        Thread downloaderThread = null;
        for (String url: args){
            downloaderThread = new Thread(new FileDownloader(url));
        }
        downloaderThread.start();
    }
    static class FileDownloader implements Runnable{
        private final String fileUrl;

        public FileDownloader(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        @Override
        public void run() {
            Debug.info("Downloading from " + fileUrl);
            String fileBaseName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            try {
                URL url = new URL(fileUrl);
                String localFileName = System.getProperty("java.io.tmpdir")
                        + "/file-"
                        + fileBaseName;
                Debug.info("Saving to: " + localFileName);
                downloadFile(url, new FileOutputStream(localFileName), 1024);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Debug.info("Done downloading from " + fileUrl);
        }

        // 从指定URL下载文件, 并保存到指定的输出流中
        private void downloadFile(URL fileURL, OutputStream fileOutputStream, int bufSize) throws IOException {
            final HttpURLConnection httpConn = (HttpURLConnection)fileURL.openConnection();
            httpConn.setRequestMethod("GET");
            ReadableByteChannel inChannel = null;
            WritableByteChannel outChannel = null;
            try {


                int responseCode = httpConn.getResponseCode();
                if (2 != responseCode / 100) {
                    throw new IOException("Error: HTTP" + responseCode);
                }

                if (0 == httpConn.getContentLength()) {
                    Debug.info("Nothing to be downloaded " + fileURL);
                    return;
                }
                inChannel = Channels.newChannel(new BufferedInputStream(httpConn.getInputStream()));
                outChannel = Channels.newChannel(new BufferedOutputStream(httpConn.getOutputStream()));
                ByteBuffer buf = ByteBuffer.allocate(bufSize);
                while (-1 != inChannel.read(buf)) {
                    buf.flip();
                    outChannel.write(buf);
                    buf.clear();
                }
            }finally {
                // 关闭指定channel以及HttpUrlConnection
                Tools.silentClose(inChannel, outChannel);
                httpConn.disconnect();
            }

        }
    }
}

