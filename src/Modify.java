import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Modify {

    public static void main(String[] args) {
        try {
            /* 读入TXT文件 */
            String pathname = "./1.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = new File(pathname); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";

            List<String> listX = new ArrayList<String>();
            List<String> listY = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                line = br.readLine(); // 一次读入一行数据
                String[] str = line.split(" ");
                String[] str1 = Arrays.copyOfRange(str, 8, str.length);
                for (int i = 0; i < str1.length; i++) {
                    if (i % 2 == 0) {//X列集合
                        listX.add(str1[i]);
                    }
                    if ((i & 1) == 1) {//Y列集合
                        listY.add(str1[i]);
                    }
                    System.out.print(str1[i] + " ");
                }
//                System.out.println(listX);
//                System.out.println(listY);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
