package fr.uga.devops;

import java.util.ArrayList;

enum dataType {
    INT,
    FLOAT,
    DOUBLE,
    BOOL,
    STRING,
}

public class Col { //public methods : read_array, get_type
    private String label;
    private dataType type;
    private ArrayList<Object> elems;


    public Col(String label, ArrayList<Object> data) {
        this.label = label;
        this.elems = new ArrayList<>();
        int i = 0;
        if(data.size() == 0){
            throw new RuntimeException("Empty data column provided");
        }
        if(setType(data.get(0))){
            for(Object o : data){
                if(checkType(o)){
                    this.elems.add(i, o);
                } else {
                    this.elems.add(i, null); //sets null value if an element is of the wrong type
                }
                i = i + 1;
            }
        }else {
            System.out.println("Unrecognized data type - Exiting.");
        }
    }


    private Boolean setType(Object o){

        if (o instanceof Integer) {
            this.type = dataType.INT;
        } else if (o instanceof String) {
            this.type = dataType.STRING;
        } else if (o instanceof Double) {
            this.type = dataType.DOUBLE;
        }else if (o instanceof Float) {
            this.type = dataType.FLOAT;
        }else if (o instanceof Boolean) {
            this.type = dataType.BOOL;
        }
        else {
            System.out.println("Unrecognized data type - Error.");
            return false;
        }
        return true;
    }

    private Boolean checkType(Object o){
        if(this.type == dataType.INT){
            return o instanceof Integer;
        } else if(this.type == dataType.STRING){
            return o instanceof String;
        } else if(this.type == dataType.DOUBLE){
            return o instanceof Double;
        } else if(this.type == dataType.FLOAT){
            return o instanceof Float;
        } else if(this.type == dataType.BOOL){
            return o instanceof Boolean;
        }
        else {
            return null;
        }
    }

    public dataType getType(){
        return this.type;
    }

    public Object getElem(int i){
        return this.elems.get(i);
    }

    public String getLabel(){
        return this.label;
    }

    public int getSize(){
        return this.elems.size();
    }

    // in these methods we cast Objects doubles. this cast is safe because we check the dataType beforehand and
    // double is stored on 64bits, so no loss of precision takes place.

    public double avg(){
        double total = 0;
        if(this.type == dataType.DOUBLE || this.type == dataType.INT || this.type == dataType.FLOAT){
            for (Object elem : this.elems) {
                if(elem != null) {
                    total = total + Double.parseDouble(String.valueOf(elem));
                } //else + 0 i.e. do nothing
            }
            return total / elems.size(); // size can't be 0 by design
        }
        return 0; // avg for non maths values is 0
    }

    public double max(){
        double max = Double.NEGATIVE_INFINITY;
        if(this.type == dataType.DOUBLE || this.type == dataType.INT || this.type == dataType.FLOAT){
            for (Object elem : this.elems) {
                if(elem != null && Double.parseDouble(String.valueOf(elem)) > max) {
                    max = Double.parseDouble(String.valueOf(elem));
                }
            }
            return max;
        }
        return 0; // max for non maths values is 0
    }

    public double min(){
        double min = Double.POSITIVE_INFINITY;
        if(this.type == dataType.DOUBLE || this.type == dataType.INT || this.type == dataType.FLOAT){
            for (Object elem : this.elems) {
                if(elem != null && Double.parseDouble(String.valueOf(elem)) < min) {
                    min = Double.parseDouble(String.valueOf(elem));
                }
            }
            return min;
        }
        return 0; // min for non maths values is 0
    }

    public double total(){
        double tot = 0;
        if(this.type == dataType.DOUBLE || this.type == dataType.INT || this.type == dataType.FLOAT){
            for (Object elem : this.elems) {
                if(elem != null ) {
                    tot = tot + Double.parseDouble(String.valueOf(elem));
                }
            }
            return tot;
        }
        return 0; // min for non maths values is 0
    }
}
