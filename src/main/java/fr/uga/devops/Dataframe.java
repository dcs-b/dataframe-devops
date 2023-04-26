package fr.uga.devops;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dataframe {
    private ArrayList<Col> cols;


    public Dataframe(File f) { //if we want our dataframe from a csv file
        this.cols = new ArrayList<>();
        if(isCSVFile(f)){
            readCsv(f);
        }else {
            throw new RuntimeException("invalid file provided.");
        }
    }

    public Dataframe() { // if we want to manually add every column
        this.cols = new ArrayList<>();
    }

    public boolean isCSVFile(File file) { //checks if the provided file is a .csv // returns boolean
        String fileName = file.getName();
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }

        return extension.equalsIgnoreCase("csv");
    }

    public void readCsv(File f){
        boolean first;
        String label = "";
        try (CSVReader reader = new CSVReader(new FileReader(f))) {
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
            System.out.printf("%-30s", col.getLabel());
        }
        while(index <= end){
            for (Col col : this.cols) {
                if (index < col.getSize()) {
                    System.out.printf("%-30s", col.getElem(index));
                } else {
                    System.out.printf("%-30s", " ");
                }
            }
        index += 1;
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
                data.add(col.getElem(i));
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
            df.duplicateCol(this.getColumn(i));
        }
        return df;
    }

    private Col getColumn(int index){
        return cols.get(index);
    }

    private void duplicateCol(Col col){
        this.cols.add(col);
    }


    public static int main(String[] args){
        String csvFile = "TODO";

        return 0;
    }
}
