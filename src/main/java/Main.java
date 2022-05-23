import jsonFile.JsonFile;
import menu.Menu;
import textFile.TextFile;
import train.Train;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {

        Train tr = new Train("Коломия",12,15,15,10,10,10,10);

        JsonFile jsonFile = new JsonFile();
        TextFile textFile = new TextFile();
        List<Train> trains = new ArrayList<>();
        Menu menu = new Menu();
        int item;
        do {
            menu.show();
            item = menu.getSelection();
            switch (item) {
                case 1:
                    trains = textFile.readFromFile("laba8.txt");
                    break;
                case 2:
                    textFile.writeToFile(trains,"laba8.txt");
                    break;
                case 3:
                    trains = jsonFile.readFromFile("target/trains.json");
                    break;
                case 4:
                    jsonFile.writeToFile(trains,"target/trains.json");
                    break;
                case 5:
                    trains.add(tr);
                    break;
                case 6:
                    deleteElement(trains);
                    break;
                case 7:
                    sortByTime(trains);
                    break;
                case 8:
                    findSentAfterHour(trains);
                    break;
                case 9:
                    findTrainsHaveSeat(trains);
                    break;
                case 10:
                    printPointBySize(trains);
                    break;
                case 11:
                    printAllTrainsByPoint(trains);
                    break;
            }
        } while (item != 0);
    }

    private void printPointBySize(List<Train> trains) {

        Map<String, Long> res = trains.stream().collect(Collectors.groupingBy(Train::getPoint, Collectors.counting()));
        res.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.comparingByValue()).forEach(System.out::println);
    }

    private void printAllTrainsByPoint(List<Train> trains) {

        Map<String,List<Train>> result = findAllTrainsByPoint(trains);
        for (Map.Entry<String,List<Train>> entry:result.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (Train train : entry.getValue()) {
                System.out.println("\t" + train);
            }
        }
    }

    private Map<String, List<Train>> findAllTrainsByPoint(List<Train> trains) {

        return trains.stream().collect(Collectors.groupingBy(Train::getPoint));
    }

    private void findTrainsHaveSeat(List<Train> arr) {

        System.out.println("Введіть пункт призначення");
        Scanner in = new Scanner(System.in);
        String point = in.next();
        List<Train> trains = findTrainsByPoint(arr,point);
        List<Train> result = new ArrayList<>();
        for(Train train: trains) if (train.getCommon() > 0) result.add(train);
        printTrains(result);
    }

    private void findSentAfterHour(List<Train> trains) {

        System.out.println("Введіть пункт призначення");
        Scanner in = new Scanner(System.in);
        String point = in.next();
        System.out.println("Введіть годину після якої відправляється потяг");
        int hour = in.nextInt();
        List<Train> arr = findTrainsByPoint(trains,point);
        List<Train> trains1 = new ArrayList<>();
        for (Train train : arr) if (train.getHour() >= hour) trains1.add(train);
        printTrains(trains1);
    }

    private void sortByTime(List<Train> trains) {

        System.out.println("Введіть пункт призначення");
        Scanner in = new Scanner(System.in);
        String point = in.next();
        List<Train> result = findTrainsByPoint(trains,point);
        Collections.sort(result);
        printTrains(result);
    }

    private void printTrains(List<Train> result) {

        for (Train train : result) {
            System.out.println(train);
        }
    }

    private List<Train> findTrainsByPoint(List<Train> trains, String point) {

        List<Train> result = new ArrayList<>();
        for (Train train : trains) {
            if (train.getPoint().equals(point)) {
                result.add(train);
            }
        }
        return result;
    }

    private void deleteElement(List<Train> trains) {

        System.out.println("Введіть номер поїзду");
        Scanner in = new Scanner(System.in);
        int number = in.nextInt();
        for (int i = 0; i < trains.size(); i++) {
            if (trains.get(i).getNumber() == number) {
                trains.remove(i);
                break;
            }
        }
    }
}
