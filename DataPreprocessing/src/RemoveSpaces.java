/**
 * @Author：zrh
 * @Description:每行数据中，每个数字之间应该由一个空格隔开，但是由于处理数据时的疏忽，有时会导致数字之间由多个空格，如:2[5] -1 3[5] -1 -2  SUtility:10
 * 其中，-2和SUtility之间有两个空格
 * 该方法是用于去除每行数据的每个数据之间存在的多个空格的，中间只保留一个空格
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RemoveSpaces {
    public static void main(String[] args) {
        String inputFilePath = "./Datasets/HighUtilitySequentialData/BIBLE.txt";  // 输入文件路径
        String outputFilePath = "./Datasets/HighUtilitySequentialData/output.txt";  // 输出文件路径

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            FileWriter writer = new FileWriter(outputFilePath);

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();  // 去除行首和行尾的空格
                line = line.replaceAll("\\s+", " ");  // 将多个连续空格替换为一个空格
                writer.write(line + "\n");  // 写入转换后的行到输出文件
            }

            reader.close();
            writer.close();

            System.out.println("去除空格完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
