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
            throw new IllegalArgumentException("Unrecognized data type - Exiting.");
        }
    }


    private Boolean setType(Object o) {
        if (o instanceof Integer) {
            this.type = dataType.INT;
        } else if (o instanceof Double) {
            this.type = dataType.DOUBLE;
        } else if (o instanceof Float) {
            this.type = dataType.FLOAT;
        } else if (o instanceof String) {
            String s = (String) o;
            try {
                int i = Integer.parseInt(s);
                this.type = dataType.INT;
            } catch (NumberFormatException e1) {
                try {
                    float f = Float.parseFloat(s);
                    this.type = dataType.FLOAT;
                } catch (NumberFormatException e2) {
                    try {
                        double d = Double.parseDouble(s);
                        this.type = dataType.DOUBLE;
                    } catch (NumberFormatException e3) {
                        this.type = dataType.STRING;
                    }
                }
            }
        } else if (o instanceof Boolean) {
            this.type = dataType.BOOL;


        }
        return true;
    }

    private Boolean checkType(Object o){
        if (o instanceof Integer) {
            return this.type == dataType.INT;
        } else if (o instanceof Double) {
            return this.type == dataType.DOUBLE;
        } else if (o instanceof Float) {
            return this.type == dataType.FLOAT;
        } else if (o instanceof String) {
            String s = (String) o;
            try {
                int i = Integer.parseInt(s);
                return this.type == dataType.INT;
            } catch (NumberFormatException e1) {
                try {
                    float f = Float.parseFloat(s);
                    return this.type == dataType.FLOAT;
                } catch (NumberFormatException e2) {
                    try {
                        double d = Double.parseDouble(s);
                        return this.type == dataType.DOUBLE;
                    } catch (NumberFormatException e3) {
                        return this.type == dataType.STRING;
                    }
                }
            }
        } else if (o instanceof Boolean) {
            return this.type == dataType.BOOL;


        }
        return true;
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
        throw new RuntimeException("Operation not supported for this type");
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
        throw new RuntimeException("Operation not supported for this type");
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
        throw new RuntimeException("Operation not supported for this type");
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
        throw new RuntimeException("Operation not supported for this type");
    }
}
