package org.easyxms;


import java.util.concurrent.CountDownLatch;

/**
 * 子线程控制类
 * 用于主线程等待所有的子线程运行结束
 * */
class SubProcessControl {

    private CountDownLatch wait_all_thread_run_end = null;

    SubProcessControl(int num) {
        this.wait_all_thread_run_end =new CountDownLatch(num);
    }

    public CountDownLatch getWait_all_thread_run_end() {
        return this.wait_all_thread_run_end;
    }

    public void wait_all_thread_end(){
        try {
            this.wait_all_thread_run_end.await();
        } catch (InterruptedException e){
            HelpPrompt.printInfo(e.getMessage());
        }
    }
}
