package com.example.chromskynormalform;

import java.util.ArrayList;

public class CFG {
        private ArrayList<Rule> rules;
        private ArrayList<String> capitals;

        public CFG(ArrayList<Rule> rules){
            this.rules= rules;
        }

        public  void addRule(Rule r){
            rules.add(r);
        }

        public void removeRule(Rule r){
            rules.remove(r);
            for(Rule rule : rules){
                for(String s : rule.getRHS()){
                    s= s.replaceAll(r.getCapital()+"", "");
                }
            }
        }

    public ArrayList<Rule> getRules(){
        return rules;
    }
    public ArrayList<String> getCapitals(){
        for(Rule r : getRules()) {
            capitals.add(r.getCapital()+"");
        }
        return capitals;
    }

}
