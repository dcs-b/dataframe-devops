package fr.uga.devops;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.ArrayList;

public class Dataframe {
    private ArrayList<Col> cols;
    private String fileName;


    public Dataframe(String filename) { //if we want our dataframe from a csv file
        this.cols = new ArrayList<>();
        this.fileName = filename;
        if(isCSVFile()){
            readCsv();
        } else {
            throw new RuntimeException("invalid file provided.");
        }
    }

    public Dataframe() { // if we want to manually add every column
        this.cols = new ArrayList<>();
    }

    private boolean isCSVFile() { //checks if the provided file is a .csv // returns boolean
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }

        return extension.equalsIgnoreCase("csv");
    }

    private void readCsv(){
        InputStream in = Dataframe.class.getClassLoader().getResourceAsStream(fileName);
        if (in == null) {
            throw new RuntimeException("Invalid file.");
        }

        boolean first;
        String label = "";
        try (CSVReader reader = new CSVReader(new InputStreamReader(in))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                first = true;
                ArrayList<Object> data = new ArrayList<>();
                for (String element : line) {
                    if(first){
                        label = element;
                        first = false;
                    } else {
                        data.add(element);
                    }
                }
                Col col = new Col(label, data);
                this.cols.add(col);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    //print the rows from start index to end index
    private void print(int start, int end) {
        int index = start;
        for (Col col : this.cols) {
            System.out.printf("%-15s", col.getLabel());
        }
        System.out.printf("\n");
        while(index <= end){
            for (Col col : this.cols) {
                if (index < col.getSize()) {
                    System.out.printf("%-15s", col.getElem(index));
                } else {
                    System.out.printf("%-15s", " ");
                }
            }
            index += 1;
            System.out.printf("\n");
        }
    }

    //print the first 7 rows
    public void head(){
        int max = getBiggestSize();
        if(max > 7){
            max = 7;
        }
        print(0,max);
    }

    //print the last 7 rows
    public void tail(){
        int max = getBiggestSize();
        if(max < 7){
            print(0,max);
            return;
        }
        print(max-7, max);
    }

    //print all the rows
    public void show(){
        print(0,getBiggestSize());
    }

    private int getBiggestSize(){
        int max = 0;
        for (Col col : this.cols) {
            if(col.getSize() > max){
                max = col.getSize();
            }
        }
        return max;
    }

    public void addColumn(String label, ArrayList<Object> data ){
        Col col = new Col(label, data);
        this.cols.add(col);
    }

    public void removeColumn(String label){
        for(Col col:cols ){
            if(col.getLabel().equalsIgnoreCase(label)){
                cols.remove(col);
                return;
            }
        }
    }

    public Dataframe extractRows(ArrayList<Integer> indexes){
        Dataframe df = new Dataframe();
        for(Col col: this.cols){
            ArrayList<Object> data = new ArrayList<>();
            String label = col.getLabel();
            for(Integer i: indexes){
                if(i < col.getSize()){
                data.add(col.getElem(i));
                }
            }
            df.addColumn(label, data);
        }
        return df;
    }


    //user must provide an arraylist containing
    //the indexes of the columns they want to extract

    public Dataframe extractColumns(ArrayList<Integer> indexes){
        Dataframe df = new Dataframe();
        for(Integer i : indexes){
            if(i < this.cols.size()) {
                df.duplicateCol(this.getColumn(i));
            }
        }
        return df;
    }

    public Col getColumn(int index){
        return cols.get(index);
    }

    private void duplicateCol(Col col){
        this.cols.add(col);
    }

    public int getSize(){
        return this.cols.size();
    }

}

