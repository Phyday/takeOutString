import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    //{"pic_name":
    //  { "filename":"...",
    //    "size":...,
    //    "regions":
    //      [{"shape_attributes":
    //        {"name":"polygon","all_points_x":[,,,,,],"all_points_y":[,,,,,]}
    //      }]
    //    "regions_attributes":{""}
    //  }
    //}

    public static void main(String[] args) {
        try {
            FileOutputStream bos = new FileOutputStream("output.txt");
            System.setOut(new PrintStream(bos));
            List<Map<List<String>, List<String>>> list = addAllMap(getAllCoordsMap());
            System.out.println("{\n" +
                    "\"filename\":\"" +
                    "valb3.jpg" +
                    "\"\n" +
                    "\"regions\":[");
            for (Map<List<String>, List<String>> item : list) {
                System.out.println("{");
                for (Map.Entry<List<String>, List<String>> map : item.entrySet()) {
                    System.out.println("\"shape_attributes\":{");
                    System.out.println("\"all_points_x\":" + map.getKey() + ",");
                    System.out.println("\"all_points_y\":" + map.getValue() + "");
                    System.out.println("}");
                }
                System.out.println("},");
            }
            System.out.println("],\n" +
                    "}");
//        int a = 5;
//        int b = 4;
//        int c = a++ - --b * ++a / b-- >> 2 % a;
//        System.out.println(c);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<List<Map<List<String>, List<String>>>> getAllCoordsMap() {
        List<List<Map<List<String>, List<String>>>> contentList = new ArrayList<>();
        List<Map<List<String>, List<String>>> content = new ArrayList<>();
        FileReader read = null;
        BufferedReader fileReader = null;
        try {
            read = new FileReader(new File(Main.class.getClassLoader().getResource("valb3.txt").getPath()));
            fileReader = new BufferedReader(read);

            String line;
            List<String> tempList = new ArrayList<>();//存放每行所有XY
            String[] temp;
            List<String> codeList = new ArrayList<>();
            List<Map<List<String>, List<String>>> bufferList = new ArrayList<>();//存放XY的K-V对

            while ((line = fileReader.readLine()) != null) {
                line = (line.substring(1, line.length())).trim();//去除前后空格
                temp = line.split(" ");//空格分割
                if (codeList.size() == 0 || !codeList.contains(temp[0])) {
                    if (!codeList.contains(temp[0]) && codeList.size() > 0) {
                        contentList.add(bufferList);
                        bufferList = new ArrayList<>();
                    }
                    codeList.add(temp[0]);
                }
                tempList = getCoordsList(temp);//获取坐标队列
                bufferList.add(splitXYtoMap(tempList));//坐标XY作为K-V存在一个表中
            }
            contentList.add(bufferList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return contentList;
    }

    public static List<String> getCoordsList(String[] temp) {
        List<String> list = new ArrayList<>();
        for (String item : temp) {
            if (!item.isEmpty()) {
                list.add(item);
            }
        }
        return list.subList(7, list.size());
    }


    public static Map<List<String>, List<String>> splitXYtoMap(List<String> list) {
        Map<List<String>, List<String>> map = new HashMap<>();

        List<String> xList = new ArrayList<>();
        List<String> yList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0 || i % 2 == 0) {
                xList.add(list.get(i));
            } else {
                yList.add(list.get(i));
            }
        }
        map.put(xList, yList);
        return map;
    }

    public static List<Map<List<String>, List<String>>> addAllMap(List<List<Map<List<String>, List<String>>>> list) {
        List<Map<List<String>, List<String>>> temp = new ArrayList<>();
        Map<List<String>, List<String>> tempMap = null;
        List<String> xList = null;
        List<String> yList = null;
        for (List<Map<List<String>, List<String>>> item : list) {
            tempMap = new HashMap<>();
            xList = new ArrayList<>();
            yList = new ArrayList<>();
            for (Map<List<String>, List<String>> map : item) {
                for (Map.Entry<List<String>, List<String>> mapItem : map.entrySet()) {
                    xList.addAll(mapItem.getKey());
                    yList.addAll(mapItem.getValue());
                }
            }
            tempMap.put(xList, yList);
            temp.add(tempMap);
        }
        return temp;
    }
}
