package cn.sakuraxiafan;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    public static final Properties properties =new Properties();
    public static void main(String[] args) throws IOException {
        clearScreen();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("prompt.properties"));

        boolean flag = true;
        while (flag){System.out.println(properties.getProperty("modeSelect"));
            String input = null;
            while (input==null){
                input = getInput("[0-3]");
                if (input==null){
                    System.out.println(properties.getProperty("inputError"));
                }else if (input.equals("0")){
                    return;
                }else {
                    int choice = Integer.parseInt(input);
                    switch (choice) {
                        case 0 -> flag=false;
                        case 1 -> CustomerMode.process();
                        case 2 -> StoreMode.process();
                        case 3 -> VendorMode.process();
                    }
                    clearScreen();//从子任务返回，清屏
                }
            }
        }
    }
    public static String getInput(String regex){
        String s = scanner.next();
        if (s.matches(regex)||s.equals("0")){
            return s;
        }
        return null;
    }

    public static void clearScreen(){
        try {//使用命令的过程可能会出现失败，需要捕获异常
            //   Process process = Runtime.getRuntime().exec("cls");
            new ProcessBuilder("cmd", "/c", "cls")
                    // 将 ProcessBuilder 对象的输出管道和 Java 的进程进行关联，这个函数的返回值也是一个
                    // ProcessBuilder
                    .inheritIO()
                    // 开始执行 ProcessBuilder 中的命令
                    .start()
                    // 等待 ProcessBuilder 中的清屏命令执行完毕
                    // 如果不等待则会出现清屏代码后面的输出被清掉的情况
                    .waitFor(); // 清屏命令
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}