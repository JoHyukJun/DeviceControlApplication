package com.example.johyukjun.project;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.DocumentsContract;
import android.renderscript.ScriptGroup;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.Socket;
import java.util.logging.LogRecord;



public class ClientThread extends Thread {
    public static final int serverPort = 33265;
    //public static final String serverIp = "39.118.142.34";
    public static final String serverIp = "13.125.221.145";

    private Handler mMainHandler;

    Socket m_Sock;


    public ClientThread() {
        m_Sock = null;
    }


    public ClientThread(Handler mainHandler) {
        m_Sock = null;
        mMainHandler = mainHandler;
    }

    @Override
    public void run() {
        try {
            m_Sock = new Socket(serverIp, serverPort);

            doPrintln("SERVER CONEETED");

            SendThread sendThread = new SendThread(this, m_Sock.getOutputStream());
            RecvThread recvThread = new RecvThread(this, m_Sock.getInputStream());
            //

            sendThread.start();
            recvThread.start();


            //
            sendThread.join();
            recvThread.join();

        }
        catch (Exception e) {
            doPrintln(e.getMessage());
        }
        finally {
            try {
                if (m_Sock != null) {
                    m_Sock.close();

                    doPrintln("SERVER DISCONNECTED");
                }
            }
            catch (IOException e) {
                doPrintln(e.getMessage());
            }
        }
    }


    public void doPrintln(String inStr) {
        Message tempMsg = Message.obtain();
        tempMsg.what = 1;
        tempMsg.obj = inStr + "\n";

        mMainHandler.sendMessage(tempMsg);

        return;
    }
}


class SendThread extends Thread {
    private ClientThread mClientThread;
    private OutputStream mOutStream;
    public static Handler mHandler;

    public SendThread(ClientThread clientThread, OutputStream outputStream) {
        mClientThread = clientThread;
        mOutStream = outputStream;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        Looper.prepare();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1: // 데이터 송신 메시지
                        try {
                            String tempStr = (String) msg.obj;
                            mOutStream.write(tempStr.getBytes());
                            mOutStream.flush();
                            mClientThread.doPrintln("SEND MESSAGE" + tempStr);
                        }
                        catch (IOException e) {
                            mClientThread.doPrintln(e.getMessage());
                        }

                        break;

                    case 2:
                        getLooper().quit();

                        break;
                }
            }
        };

        Looper.loop();
    }
}


class RecvThread extends Thread {
    private ClientThread mClientThread;
    private InputStream mInStream;
    public static String mRecvData;

    public RecvThread(ClientThread clientThread, InputStream inputStream) {
        mClientThread = clientThread;
        mInStream = inputStream;
    }

    public String GetRecvData() {
        return mRecvData;
    }

    @Override
    public void run() {
        byte[] buf = new byte[1024];

        while (true) {
            try {
                int nbytes = mInStream.read(buf);

                if(nbytes > 0) {
                    String tempStr = new String(buf, 0, nbytes);
                    mRecvData = tempStr;
                    mClientThread.doPrintln("*" + tempStr);
                }
                else {
                    mClientThread.doPrintln("SERVER DISCONNECTED");

                    if (SendThread.mHandler != null) {
                        Message msg = Message.obtain();
                        msg.what = 2;
                        SendThread.mHandler.sendMessage(msg);
                    }

                    break;
                }
            }
            catch (IOException e) {
                mClientThread.doPrintln(e.getMessage());
            }
        }
    }
}