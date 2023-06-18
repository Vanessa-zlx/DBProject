package cn.sakuraxiafan;

import static cn.sakuraxiafan.App.getInput;
import static cn.sakuraxiafan.App.properties;

public class VendorMode {
    public static void process() {
        boolean flag = true;
        while (flag) {
            System.out.println(properties.getProperty("storeMode"));
            String input = null;
            while (input == null) {
                input = getInput("[0-4]+");
                if (input == null) {
                    System.out.println(properties.getProperty("inputError"));
                }
            }
            int choice = Integer.parseInt(input);
            switch (choice) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }
    }
}
