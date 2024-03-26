import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConvertHUSeqDBToTransDB {
    public static void main(String... args) {
        String inputFilePath = "./Datasets/HighUtilitySequentialData/test_UDB.txt";  // 输入文件路径
        String outputFilePath = "./Datasets/TransactionData/test_Trans.txt";  // 输出文件路径

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            FileWriter writer = new FileWriter(outputFilePath);

            String line;
            int SUtility = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();  // 去除行首和行尾的空格
                String[] parts = line.split(" ");

                StringBuilder convertedLine = new StringBuilder();

                // 处理项
                for (int i = 0; i < parts.length - 3; i ++) {
                    String itemString = parts[i];
                    if (itemString.equals("-1") || itemString.equals("") || itemString.charAt(0)=='S'){
                        continue;
                    }
                    String item = itemString.substring(0,itemString.indexOf('['));

                    convertedLine.append(item).append(" ");

                }

                String trimmedString = convertedLine.toString().trim();
                convertedLine.setLength(0);
                convertedLine.append(trimmedString);



                // 处理SUtility值
                String SUtilityString = parts[parts.length-1];
                SUtility=Integer.parseInt(SUtilityString.substring(SUtilityString.indexOf(':')+1));
                convertedLine.append(":").append(SUtility).append(":");

                //处理效用
                for (int i = 0; i < parts.length - 3; i ++) {
                    String UtilityString = parts[i];
                    if (UtilityString.equals("-1") || UtilityString.equals("") || UtilityString.charAt(0)=='S'){
                        continue;
                    }
                    String utility = UtilityString.substring(UtilityString.indexOf('[')+1,UtilityString.indexOf(']'));
                    convertedLine.append(utility).append(" ");
                }

                writer.write(convertedLine.toString().trim() + "\n");  // 写入转换后的行到输出文件
            }

            reader.close();
            writer.close();

            System.out.println("高效用序列数据库转换事务数据库完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
