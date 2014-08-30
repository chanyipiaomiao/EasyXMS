package org.easyxms;


import com.jcraft.jsch.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * 多线程类
 */
class MultiThread{


    /**
     * 新建会话开始多线程
     * @param objects ServerInfo对象列表
     */
    public void startMultiThread(List<ServerInfo> objects,String ssh_sftp){

        int host_num = objects.size();
        CountDownLatch wait_thread_run_end = new CountDownLatch(host_num);
        ArrayList<Thread> threadArrayList = new ArrayList<Thread>();             //初始化用于存放线程的列表
        if ("ssh".equals(ssh_sftp)){
            ExecCommand.setCountDownLatch(wait_thread_run_end); //设置线程同步计数器的数目
            ExecCommand.setIs_use_session_pool(0);
            for (ServerInfo serverInfo : objects){
                threadArrayList.add(new Thread(new ExecCommand(serverInfo)));
            }
        } else {
            UploadFile.setCountDownLatch(wait_thread_run_end);
            UploadFile.setIs_use_session_pool(0);
            for (ServerInfo serverInfo : objects){
                threadArrayList.add(new Thread(new UploadFile(serverInfo)));
            }
        }
        threadControl(threadArrayList,wait_thread_run_end);
    }


    /**
     * 使用已经连接的会话开始多线程
     * @param session_pool 会话池
     */
    public void startMultiThread(HashMap<String, Session> session_pool){

        int host_num = session_pool.size();
        CountDownLatch wait_thread_run_end = new CountDownLatch(host_num);
        ExecCommand.setCountDownLatch(wait_thread_run_end); //设置线程同步计数器的数目

        //初始化用于存放线程的列表
        ArrayList<Thread> threadArrayList = new ArrayList<Thread>();
        ExecCommand.setIs_use_session_pool(1);
        for (String ip : session_pool.keySet()){
            Session session = session_pool.get(ip);
            if (session.isConnected()){
                threadArrayList.add(new Thread(new ExecCommand(ip,session)));
            } else {
                HelpPrompt.printIPSessionAlreadyDisconnect(ip);
            }
        }
        threadControl(threadArrayList,wait_thread_run_end);
    }


    /**
     * 控制主线程等待所有子线程运行结束
     * @param threadArrayList   要运行的线程对象列表
     * @param wait_thread_run_end 程同步计数器的数目
     */
    public void threadControl(ArrayList<Thread> threadArrayList, CountDownLatch wait_thread_run_end){

        //开始执行多线程
        for (Thread thread : threadArrayList){
            thread.start();
        }

        //等待所有子线程运行结束
        try {
            wait_thread_run_end.await();
        } catch (InterruptedException e){
            HelpPrompt.printInfo(e.getMessage());
        }
    }
}

