package cn.sakuraxiafan;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class TxtRead {
    public static ArrayList<String> readStates(){
        ArrayList<String> states = new ArrayList<>();
        InputStream is = Thread.currentThread().
                getContextClassLoader().getResourceAsStream("states.txt");
        assert is != null;
        Scanner sc = new Scanner(is);
        while (sc.hasNext()){
            sc.nextInt();
            String state = sc.next();
            sc.nextLine();
            states.add(state);
        }
        return states;
    }

    public static void main(String[] args) {
        readStates();
    }
}
