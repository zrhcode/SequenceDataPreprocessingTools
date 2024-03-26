import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HUSeqDBStats {

    public static void main(String[] args) throws IOException {
        String inputFilePath = "./Datasets/HighUtilitySequentialData/S10I5E2D200k.txt";
        HUSeqDBStats Stats = new HUSeqDBStats();
        Stats.printStats(inputFilePath);

    }

    void printStats(String inputFilePath) throws IOException{
        int sequenceCount = 0;
        int totalDBUtility = 0;
        Set<String> distinctItems = new HashSet<>();
        int maxItem = Integer.MIN_VALUE;
        int totalBracketCount = 0;
        int maxBracketCount = 0;
        int itemsetNum = 0;
        int size = 0;
        List<String> list = new ArrayList<>();
        int AvgItemInItemset = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sequenceCount++;

                // Split the line by space
                String[] parts = line.split(" ");

                // Extract SUtility value
                int suUtilityValue = Integer.parseInt(parts[parts.length - 1].substring("SUtility:".length()));

                // Update totalDBUtility
                totalDBUtility += suUtilityValue;

                // Extract items and update distinctItems, maxItem
                Set<String> differentItems = new HashSet<>();
                for (String part : parts) {
                    if (part.contains("[")) {
                        String item = part.substring(0, part.indexOf('['));
                        differentItems.add(item);
                        distinctItems.add(item);
                        list.add(item);

                        int currentItem = Integer.parseInt(item);
                        maxItem = Math.max(maxItem, currentItem);
                    } else if (part.equals("-1")) {
                        itemsetNum++;
                        AvgItemInItemset = list.size();
                    }
                }
                size += differentItems.size();
                differentItems.clear();


                // Update totalBracketCount, maxBracketCount
                int bracketCount = line.split("\\[").length - 1;
                totalBracketCount += bracketCount;
                maxBracketCount = Math.max(maxBracketCount, bracketCount);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Calculate averages
        double averageSequenceLength = (double) totalBracketCount / sequenceCount;
        double averageitemsetNum = (double) itemsetNum / sequenceCount;
        double differentItemOneSequence = (double) size / sequenceCount;
        double averageItemInItemset = (double) AvgItemInItemset / itemsetNum;
        double itemsetDensity =  (averageitemsetNum / itemsetNum)*100;
        double itemDensity = (differentItemOneSequence / distinctItems.size())*100;

        System.out.println("==========Sequence Database Stats==========");
        System.out.println("1.File: "+inputFilePath);
        System.out.println("2. Sequence Count: " + sequenceCount);
        System.out.println("3. Total SUtility: " + totalDBUtility);
        System.out.println("4. Distinct Items Count: " + distinctItems.size());
        System.out.println("5. Max Item: " + maxItem);
        System.out.println("6. Average Sequence Length: " + averageSequenceLength);
        System.out.println("7. Max Sequence Length: " + maxBracketCount);
        System.out.println("8. Average number of itemsets per sequence: " + averageitemsetNum);
        System.out.println("9. Average number of items per itemset: " + averageItemInItemset);
        System.out.println("10. Average number of distinct item per sequence: " + differentItemOneSequence);
        System.out.println("11.itemDensity:" + itemDensity);    //平均每个序列所含的项数量除以项的总数
        System.out.println("12.itemsetDensity:" + itemsetDensity);  //平均每个序列所含的项集数量除以项集总数
    }

}
