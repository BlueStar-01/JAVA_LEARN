package 自己学习用.javacv;
import org.bytedeco.javacv.*;
import javax.swing.*;

public class CameraPreview {
    public static void main(String[] args) throws FrameGrabber.Exception, InterruptedException {
        // 加载OpenCV本地库（需配置系统环境变量或指定路径）
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0); // 0表示默认摄像头
        grabber.start(); // 启动摄像头

        // 创建显示窗口
        CanvasFrame canvas = new CanvasFrame("摄像头实时画面");
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);

        // 实时读取并显示帧
        while (true) {
            if (!canvas.isDisplayable()) { // 检查窗口是否关闭
                grabber.stop();
                System.exit(0);
            }
            Frame frame = grabber.grab(); // 捕获一帧画面
            canvas.showImage(frame);      // 显示画面
            Thread.sleep(30);            // 控制帧率（30ms刷新）
        }
    }
}