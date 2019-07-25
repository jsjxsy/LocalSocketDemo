package com.youximao.demo.localsocketdemo;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    LocalSocket localSocketSender,localSocketReceiver;
    LocalServerSocket localServerSocket;
    final static String SOCKET_ADDRESS = "LocalSocketAddress";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            localServerSocket = new LocalServerSocket(SOCKET_ADDRESS);
            localSocketSender = new LocalSocket();
            localSocketSender.connect(new LocalSocketAddress(SOCKET_ADDRESS));
            localSocketReceiver = localServerSocket.accept();
            send();
            receiver();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(){
        new Thread(){
            @Override
            public void run() {
                try {
                    OutputStream outputStream = localSocketSender.getOutputStream();
                    outputStream.write(20);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void receiver(){
        new Thread(){
            @Override
            public void run() {
                try {
                    InputStream inputStream = localSocketReceiver.getInputStream();
                    int ret = inputStream.read();
                    Log.e("ret","" + ret);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
