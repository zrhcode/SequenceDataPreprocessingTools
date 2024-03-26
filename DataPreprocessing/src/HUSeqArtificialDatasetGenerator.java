import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class HUSeqArtificialDatasetGenerator {
    public static void main(String[] args) {
        Random random = new Random();
        int SequenceNum=500000;    //设置数据库大小
        int maxItemsets = 8;  // 设置项集的数量
        int maxItemsPerItemset = 3;  // 设置每个项集最大项目数量
        int maxItem = 9999;
        StringBuilder itemsetBuilder = new StringBuilder();
        int suValue=0;



        try {
            FileWriter fileWriter = new FileWriter("./Datasets/HighUtilitySequentialData/S10I5E2D500k.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (int k= 0; k < SequenceNum; k++) {
                int ItemsetInSeq = random.nextInt(maxItemsets)+1;
                for (int i = 0; i < ItemsetInSeq; i++) {    //每行最大项集数量其决定了最大序列长度

                    int itemsInItemset = random.nextInt(maxItemsPerItemset) + 1;  // 随机确定项目数量

                    // 生成每个项集的项目及其效用值
                    for (int j = 1; j <= itemsInItemset; j++) {
                        int item = random.nextInt(maxItem) + 1;  // 生成随机的项目编号

                        //生成内部效用值
                        int maxQuantity=5;  //设置最大内部效用
                        maxQuantity = new Random().nextInt(maxQuantity) + 1;  // [1, 5] 之间的随机数

                        // 生成外部效用值
                        double minRange = 1;
                        double maxRange = 100;
                        double mean = (maxRange-minRange)/2;    //用于调整正态分布的均值
                        double stdDev = (maxRange-minRange)/4;  //用于调整正态分布的标准差，4是一个可调整参数
                        double externalUtility;
                        do {
                            externalUtility = Math.abs(new Random().nextGaussian() * stdDev+ mean);  // 正态分布，均值为5，标准差为1
                        }while (externalUtility<minRange || externalUtility>maxRange);

                        /*
                        * new Random().nextGaussian() 生成一个均值为0，标准差为1的正态分布的随机数。
                        * 乘 stdDev 将该值进行缩放，使其标准差为 stdDev。
                        * + mean 将均值 mean 添加到缩放后的值上。
                        * Math.abs(...) 取绝对值，确保结果是正数。
                        * */

                        //计算效用
                        int utility = (int) (maxQuantity * externalUtility);


                        //直接生成效用值
//                        int maxUtility = 100;  // 设置最大效用值,用于直接生成效用
//                        int utility = (int) (Math.abs(random.nextGaussian()) * maxUtility) + 1;  // 正态分布的效用值
                        /*
                         * random.nextGaussian() 产生一个均值为0，标准差为1的正态分布随机数。
                         * Math.abs(...) 取随机数的绝对值，确保它是正的。
                         * 乘 maxUtility 将值映射到 [0, maxUtility] 的范围。
                         * + 1 将值移动到 [1, maxUtility+1] 的范围。
                         * */


                        suValue += utility;

                        // 将项目和效用值添加到项集中
                        itemsetBuilder.append(item).append("[").append(utility).append("] ");
                    }

                    itemsetBuilder.append("-1 ");
                }
                itemsetBuilder.append("-2 ");
                itemsetBuilder.append("SUtility:" + suValue);
                printWriter.println(itemsetBuilder.toString());//写完一行后，清空重写下一行
                suValue=0;
                itemsetBuilder.setLength(0);

            }

            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("数据生成完毕！");
    }
}



