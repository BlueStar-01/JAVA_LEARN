package test6;

//运行下面的Run类，仔细观察运行结果，找出导致运行结果错误的原因。用文字说明原因并修改代码使得main线程能够正常结束。
class PrintString {
    private boolean isContinuePrint = true;

    public boolean isContinuePrint() {
        return this.isContinuePrint;
    }

    public void setContinuePrint(boolean isContinuePrint) {
        this.isContinuePrint = isContinuePrint;
    }

    public void printStringMethod() {
        try {
            while (isContinuePrint == true) {
                System.out.println("run printStringMethod threadName="
                        + Thread.currentThread().getName());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class printStringThread extends Thread {
    private PrintString printString;

    public printStringThread(PrintString printString) {
        this.printString = printString;
    }

    public void run() {
        printString.printStringMethod();
    }
}

public class RunPrintString {
    public static void main(String[] args) throws Exception {
        PrintString printStringService = new PrintString();
        printStringThread thread = new printStringThread(printStringService);
        thread.start();
        //printStringService.printStringMethod();
        //没有分开子线程一直在主线线程里面运行，无法到达下面的停止语句
        System.out.println("我要停止它！stopThread=" + Thread.currentThread().getName());

        printStringService.setContinuePrint(false);
    }
}
