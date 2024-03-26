import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/*
 * Notes:只能生成S-Concatenation的序列
 *每个序列的每个项集中只包含单个的项，也就是说每个-1之间只有单个项
 * */
public class ConvertTransDBToHUSeqDB {
    public static void main(String... args) {
        String inputFilePath = "./Datasets/TransactionData/test_Trans.txt";  // 输入文件路径
        String outputFilePath = "./Datasets/HighUtilitySequentialData/test_UDB.txt";  // 输出文件路径

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            FileWriter writer = new FileWriter(outputFilePath);

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();  // 去除行首和行尾的空格
                String[] parts = line.split(":");  // 用冒号分割数据

                // 检查数组长度是否足够
                if (parts.length >= 3) {
                    String[] items = parts[0].split("\\s+");  // 使用正则表达式匹配空格分割项
                    String sUtility = parts[1].trim();  // 直接获取SUtility
                    String[] itemUtilities = parts[2].split("\\s+");  // 使用正则表达式匹配空格分割项对应的效用值

                    StringBuilder convertedLine = new StringBuilder();

                    for (int i = 0; i < items.length; i++) {
                        String item = items[i];
                        String utility = itemUtilities[i];

                        convertedLine.append(item).append("[").append(utility).append("] -1 ");
                    }

                    convertedLine.append("-2 SUtility:").append(sUtility);

                    writer.write(convertedLine.toString().trim() + "\n");  // 写入转换后的行到输出文件
                } else {
                    // 处理数组长度不足的情况
                    System.out.println("数据格式不正确: " + line);
                }
            }

            reader.close();
            writer.close();

            System.out.println("转换完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
