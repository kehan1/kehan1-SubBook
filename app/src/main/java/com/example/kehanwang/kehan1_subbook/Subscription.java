package com.example.kehanwang.kehan1_subbook;

/**
 * @author Kehan Wang
 * @version 1
 *
 */



public class Subscription {
    private String name;
    private String date;
    private double monthlyCharge;
    private String comment;

    /**
     *
     * @param name
     * @param date
     * @param monthlyCharge
     * @param comment
     */

    public Subscription(String name, String date, double monthlyCharge, String comment) {
        super();
        this.name = name;
        this.date = date;
        this.monthlyCharge=monthlyCharge;
        this.comment = comment;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String Name){
        this.name=Name;
    }
    public String getDate(){return this.date;}
    public void setDate(String Date){this.date = Date; }
    public double getmonthlyCharge(){
        return this.monthlyCharge;
    }
    public void setmonthlyCharge(double count){this.monthlyCharge = count;}
    public String getComment(){
        return this.comment;
    }
    public void setComment(String comment){
        this.comment=comment;
    }
    public String toString(){
        return "Name: "+name+"\n"+"Date: "+date+"\n"+"Count: "+monthlyCharge+ "\n"+"Comment:"+comment;
    }
}

