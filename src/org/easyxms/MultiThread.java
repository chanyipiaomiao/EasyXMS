package org.easyxms;


import com.jcraft.jsch.Session;
import java.util.ArrayList;
import java.util.HashMap;


class MultiThread{

    /**
     * type 为连接的类型(新建/利用已经建立的会话连接)
     * 当type为 new 时,会初始化session连接
     * 当为 session时,会利用现有的session会话连接
     * */
    public void startMultiThread(String type,HashMap<String,Object> hashMap){

        SubProcessControl count_down = new SubProcessControl(hashMap.size());
        ExecCommand.setCountDownLatch(count_down); //设置线程同步计数器的数目

        //初始化用于存放线程的列表
        ArrayList<Thread> threadArrayList = new ArrayList<Thread>();
        if ("new".equals(type)){
            ExecCommand.setIs_use_session_pool(0);
            for (String ip : hashMap.keySet()){
                ServerInfo info = (ServerInfo)hashMap.get(ip);
                threadArrayList.add(new Thread(new ExecCommand(ip,info)));
            }
        } else if ("session".equals(type)){
            ExecCommand.setIs_use_session_pool(1);
            for (String ip : hashMap.keySet()){
                Session session = ((ConnectionObject)hashMap.get(ip)).getSession();
                if (session.isConnected()){
                    threadArrayList.add(new Thread(new ExecCommand(ip,session)));
                } else {
                    HelpPrompt.printIPSessionAlreadyDisconnect(ip);
                }
            }
        }

        //开始执行多线程
        for (Thread thread : threadArrayList){
            thread.start();
        }

        //等待所有子线程运行结束
        count_down.wait_all_thread_end();
    }
}

