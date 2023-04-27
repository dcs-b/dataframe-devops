package fr.uga.devops;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        System.out.println("Creating dataframe from csv file: ");
        String csvFile = "example.csv"; // ../../../../../resources
        Dataframe df = new Dataframe(csvFile);
        df.show();

        System.out.println("extracting the three first columns: ");
        ArrayList<Integer> indexes = new ArrayList<>();
        indexes.add(0);
        indexes.add(1);
        indexes.add(2);
        Dataframe df2 = df.extractColumns(indexes);
        df2.show();

        System.out.println("extracting the three last rows: ");
        ArrayList<Integer> indexes2 = new ArrayList<>();
        indexes2.add(7);
        indexes2.add(8);
        indexes2.add(9);
        Dataframe df3 = df.extractRows(indexes2);
        df3.show();

        System.out.println("tail df 1: ");
        df.tail();
        System.out.println("head df 1: ");
        df.head();
        System.out.println("tail df 3 (it has fewer than 7 rows): ");
        df3.tail();

        System.out.println("remove column 2 of df 3: ");
        df3.removeColumn("label2");
        df3.show();

        System.out.println("average of df3 columns: ");
        df3.show();
        for(int i = 0; i<df3.getSize(); i++){
            System.out.printf("%-15s", df3.getColumn(i).avg());
        }
    }
}
