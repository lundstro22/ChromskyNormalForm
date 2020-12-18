package com.example.chromskynormalform;

import java.util.ArrayList;

public class CFG {
        private ArrayList<Rule> rules;

        public CFG(ArrayList<Rule> rules){
            this.rules= rules;
        }

        public ArrayList<Rule> getRules(){
            return rules;
        }

        public  void addRule(Rule r){
            rules.add(r);


        }
}
