package com.example.chromskynormalform;

import java.util.ArrayList;

public class Rule {

    private char capital;
    private String a;
    private String b;
    private ArrayList<String> RHS;
    private String RHSString;
    private String singleLowercase;


    public Rule(char capital, String a, String b){
        this.capital= capital;
        this.a= a;
        this.b= b;
        RHS= new ArrayList<String>();
        RHS.add(a);
        RHS.add(b);
        RHSString= "->";
        singleLowercase= "";
    }

    public char getCapital(){
        return capital;
    }

    public String getB(){
        return b;
    }

    public ArrayList<String> getRHS(){
        return RHS;
    }

    public void addRHS(String s){
        RHS.add(s);
    }

    public void deleteRHS(String s){
        RHS.remove(s);
    }


    public String getSingleLowercase() {
        return singleLowercase;
    }


    public void setCapital(char capital) {
        this.capital = capital;
    }


    public String getRHSString(){
        RHSString= "->";
        for(String s : RHS){
            if(s == RHS.get(RHS.size()-1)|| getB()== ""){
                RHSString= RHSString.concat(s);
            }else {
                RHSString = RHSString.concat(s + "|");
            }
        }
        return RHSString;
    }
    public void setRHS(ArrayList<String> RHS) {
        this.RHS = RHS;
    }
    public void setSingleLowercase(String singleLowercase) {
        this.singleLowercase = singleLowercase;
    }

}
