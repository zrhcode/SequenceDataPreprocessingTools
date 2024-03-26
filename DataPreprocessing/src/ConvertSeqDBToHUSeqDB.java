import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ConvertSeqDBToHUSeqDB {
    public static void main(String[] args) {
        String inputFilePath = "./Datasets/SequentialData/simple.txt";  // 输入文件路径
        String outputFilePath = "./Datasets/HighUtilitySequentialData/simple_UDB.txt";  // 输出文件路径

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            FileWriter writer = new FileWriter(outputFilePath);

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();  // 去除行首和行尾的空格
                String[] numbers = line.split(" ");  // 按空格分割每个数字

                StringBuilder convertedLine = new StringBuilder();
                int sum = 0;  // 用于计算每行中[]内数字之和

                for (int i = 0; i < numbers.length; i++) {
                    String number = numbers[i];
                    if (number.equals("-1")) {
                        convertedLine.append("-1 ");
                    } else if (number.equals("-2")) {
                        convertedLine.append("-2 SUtility:").append(sum);
                    } else {
                         //生成外部效用值
                        int maxQuantity = new Random().nextInt(5) + 1;  // [1, 5] 之间的随机数

                        // 生成内部效用值
                        double externalUtility = Math.abs(new Random().nextGaussian()) * 5 + 1;  // 正态分布，均值为5，标准差为1

                        //计算效用
                        int utility = (int) (maxQuantity * externalUtility);
                        convertedLine.append(number).append("[").append(utility).append("] ");
                        sum += utility;

                        //直接生成效用
//                        int randomNum = new Random().nextInt(1000) + 1;  // 生成1-1000之间的随机数字
//                        convertedLine.append(number).append("[").append(randomNum).append("] ");
//                        sum += randomNum;
                    }
                }

                writer.write(convertedLine.toString().trim() + "\n");  // 写入转换后的行到输出文件
            }

            reader.close();
            writer.close();

            System.out.println("序列数据库转换高效用序列数据库完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
