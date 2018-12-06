package com.example.johyukjun.project;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class NetworkManager extends AsyncTask<Void, Void, Void> {

    private String ip = "39.118.142.34";
    private int port_number = 33265;

    private Socket m_socket;
    private OutputStream m_out_stream;

    private NetType funcval = NetType.NULL;
    private String m_id = "";
    private String m_pw = "";

    private static NetworkManager one;
    private NetworkManager() {
    }

    public static NetworkManager getInstance() {
        if(one == null) {
            one = new NetworkManager();
        }
        return one;
}

    public void setFunc(NetType val)
    {
        funcval = val;
    }

    public void setUserInfo(String _id, String _pw)
    {
        m_id = _id;
        m_pw = _pw;
        funcval = NetType.LOGIN;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if(funcval == NetType.SOCKET)
            TryConnect();
        else if(funcval == NetType.LOGIN)
            SendData(m_id, m_pw);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("N", "onPostExecute");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public int TryConnect() {
        // IP 주소와 포트 번호를 관리하는 InetSocketAddress 객체를 생성하여 부모 클래스인
        // SocketAddress 클래스 객체로 받는다.
        try {
            // 서버에 연결을 시도한다.
            Log.d("N", "connecting...");
            m_socket = new Socket(ip, port_number);
        } catch (UnknownHostException ue) {
            // 서버의 IP 주소와 관련된 문제가 있을 때 발생한다.
            // 주로 IP 주소나 포트 번호를 잘못 입력하거나 서버가 실행되어 있지 않아서
            // 연결할 수 없는 상태일 때 발생한다.
            Log.d("N", "UnknownHostException");
            return -1;
        } catch (IOException ie) {
            // 소켓을 생성하는 중에 에러가 생겼을 때 발생한다.
            Log.d("N", "IOException");
            return -2;
        }
        Log.d("N", "Connection Established");
        return 0;
    }

    public int SendData(String id, String pw)
    {
        try {
            m_out_stream = m_socket.getOutputStream();
        } catch(IOException ie) {
            // 스트림을 생성하면서 에러가 발생했거나, 클라이언트 소켓이 서버와 연결되어 있지 않은 경우
            // 예외를 발생시킨다.
            return -1;
        }

        try {
            // xmlstream to string
            // String 형식의 문자열 구성
            XmlManager.getInstance();
            String str = XmlManager.MakeXmlStr(id, pw);
            // String 형식의 문자열을 byte 배열로 변환
            byte[] data = str.getBytes();
            // byte 배열의 길이를 구한다.
            int data_size = data.length;

            // 배열의 길이(헤더)와 문자열(바디)을 프레임으로 구성할 배열을 생성한다.
            byte[] send_data = new byte[data_size + 1];
            // 선두 1 바이트에 문자열의 길이를 저장한다.
            send_data[0] = (byte)data_size;
            // 두번째 index 에서부터 문자열 내용을 문자열 바이트의 배열 길이만큼 복사한다.
            System.arraycopy(data, 0, send_data, 1, data_size);
            // 구성한 프레임을 서버로 전송한다.
            m_out_stream.write(send_data);
        } catch (IOException ie) {
            // 소켓이 정상적으로 열려있지 않은 경우 발생
            return -2;
        }
        return 0;
    }

    public void ReceiveData()
    {


    }
}
