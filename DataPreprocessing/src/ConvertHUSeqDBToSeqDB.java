import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class ConvertHUSeqDBToSeqDB {
    public static void main(String... args) {
        String inputFilePath = "./Datasets/HighUtilitySequentialData/simple_UDB.txt";  // 输入文件路径
        String outputFilePath = "./Datasets/SequentialData/simple.txt";  // 输出文件路径

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            FileWriter writer = new FileWriter(outputFilePath);

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();  // 去除行首和行尾的空格
                String[] parts = line.split(" ");  // 用空格分割数据

                StringBuilder convertedLine = new StringBuilder();

                // 处理项
                for (int i = 0; i < parts.length - 2; i ++) {
                    String itemString = parts[i];
                    itemString.trim();

                    if(!"-1".equals(itemString)) {
                        if (itemString.equals("-2") || itemString.equals("") || itemString.charAt(0)=='S'){
                            continue;
                        }
                        String item = itemString.substring(0, itemString.indexOf('['));
                        convertedLine.append(item).append(" ");
                    }else{
                        convertedLine.append(-1).append(" ");
                    }
                }

                // 处理末尾项和标识符
                convertedLine.append(-2).append(" ");

                writer.write(convertedLine.toString().trim() + "\n");  // 写入转换后的行到输出文件
            }

            reader.close();
            writer.close();

            System.out.println("高效用序列转换序列数据完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
