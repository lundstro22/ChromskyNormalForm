package com.example.chromskynormalform;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CFG grammar;
    TextView input;
    TextView output;
    boolean yesS= false;
    ArrayList<String> toDelete= new ArrayList<String>();
    ArrayList<Rule> containsE= new ArrayList<Rule>();
    ArrayList<Rule> containsCapital= new ArrayList<Rule>();
    ArrayList<String> stringContains= new ArrayList<String>();
    Character capital;
    Character remove= 'x';
    Rule rule= null;
    Rule ruleToAdd;
    Character upperCase;
    String tempCap;
    String tempLow;
    Rule tempRule;
    Rule ruleHolder;
    Rule ruleHolder3;
    String holder3;
    String holder4;
    String holder;
    Rule ruleHolder4;
    ArrayList<String> rulesHolder= new ArrayList<>();
    int upper;
    int lower;
    Character cha;
    int store;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioButton test1= findViewById(R.id.Test1);
        RadioButton test2= findViewById(R.id.Test2);
        RadioButton test3= findViewById(R.id.Test3);
        Button step1= findViewById(R.id.step1);
        Button step2= findViewById(R.id.step2);
        Button step3= findViewById(R.id.step3);
        Button step4= findViewById(R.id.step4);
        Button step5= findViewById(R.id.step5);
        input= findViewById(R.id.input);
        output= findViewById(R.id.output);
        test1.setOnClickListener(this);
        test2.setOnClickListener(this);
        test3.setOnClickListener(this);
        step1.setOnClickListener(this);
        step2.setOnClickListener(this);
        step3.setOnClickListener(this);
        step4.setOnClickListener(this);
        step5.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.Test1){
            ArrayList<Rule> rules= new ArrayList<Rule>();
            rules.add(new Rule('S', "SA", ""));
            rules.add(new Rule('A', "Bb", "S"));
            rules.add(new Rule('B', "a", "e"));

            grammar= new CFG(rules);
            input.setText("");
            printCFG(grammar, input);

        }
        if(view.getId()== R.id.Test2){
            ArrayList<Rule> rules= new ArrayList<Rule>();
            rules.add(new Rule('S', "BA", ""));
            rules.add(new Rule('A', "Bb", "e"));
            rules.add(new Rule('B', "b", "a"));

            grammar= new CFG(rules);
            input.setText("");
            printCFG(grammar, input);
        }
        if(view.getId()== R.id.Test3){
            ArrayList<Rule> rules= new ArrayList<Rule>();
            rules.add(new Rule('S', "B", "b"));
            rules.add(new Rule('A', "aA", "e"));
            rules.add(new Rule('B', "B", "a"));

            grammar= new CFG(rules);
            input.setText("");

            printCFG(grammar, input);

        }
        if(view.getId()== R.id.step1){
            stepOne(grammar);
            printCFG(grammar, output);
        }
        if(view.getId()== R.id.step2){
            stepTwo(grammar);
            printCFG(grammar, output);
        }
        if(view.getId()== R.id.step3){
            stepThree(grammar);
            printCFG(grammar, output);
        }
        if(view.getId()== R.id.step4){
            stepFour(grammar);
            printCFG(grammar, output);
        }
        if(view.getId()== R.id.step5){
            stepFive(grammar);
            printCFG(grammar, output);
        }


    }

    public void stepOne(CFG cfg){
        for(Rule r : cfg.getRules()){
            if(r.getCapital()== 'S'){
                //do nothing
            }else{
                for(String s : r.getRHS()){
                    if(r.getCapital() != 'S'){
                        if (s.contains("S")) {
                            yesS = true;
                        }
                    }
                }
            }
        }
        if(yesS){
            cfg.addRule(new Rule('0', "S", ""));
        }
    }
    public void  stepTwo(CFG cfg){
        toDelete= new ArrayList<String>();
        containsE= new ArrayList<Rule>();

        //remove e from RHS
        for(Rule r : cfg.getRules()) {
            for (String s : r.getRHS()) {
                if (s != "e") {
                     //do nothing
                }else{
                    toDelete.add("e");
                    containsE.add(r);
                }
            }
        }
        handleE(cfg, toDelete, containsE);

        //add extra rule without b to all rules containing containsE
        //for each rule in containsE
        for(Rule rule: containsE) {
            //for each rule in CFG rules
            for (Rule r : cfg.getRules()) {
                //for each string on the RHS of rules
                for (String s : r.getRHS()) {
                    //temp variable to hold Capital
                    String temp= (rule.getCapital() +"");
                    if (s.contains(temp)) {
                        containsCapital.add(r);
                        stringContains.add(s);
                        capital= rule.getCapital();
                    }
                }
            }
        }
        handleAddRule(containsCapital,stringContains,capital);
    }

    public void stepThree(CFG cfg){
        //remove rules A->A
        //for each rule in the cfg
        for(Rule r : cfg.getRules()){
            //for each string in the rhs side of the rule
            for(String S : r.getRHS()){
                //if the string equals the rule name
                String cap= r.getCapital()+"";
                if(S.equals(cap)){
                    remove=r.getCapital();
                    rule=r;
                    break;
                }else{
                    remove= 'x';
                    rule= null;
                }
            }
        }
        if(remove != 'x' && rule != null) {
            String capital = remove + "";
            rule.deleteRHS(capital);
        }
        for(Rule r : cfg.getRules()){
            if(r.getCapital() == '0'){
                cfg.removeRule(r);

            }
            if(r.getCapital() == 'S'){
                r.setCapital('0');
            }
        }

        //for each rule in the cfg
        for(Rule r : cfg.getRules()){
            //for each string on the right hand side
            for(String s : r.getRHS()) {
                if(s.contains("S")){
                    ruleHolder=r;
                    holder=s;
                }
            }
        }

        for(Rule r : cfg.getRules()) {
            if(r.getRHS().contains(holder)){
                r.deleteRHS(holder);
                holder= holder.replace('S','0');
                r.addRHS(holder);
            }
        }

        //remove rules A->B, B->b
        //for each rule in the cfg
        for(Rule r : cfg.getRules()){
            //for each string on the right hand side
            for(String s : r.getRHS()){
                //if it is a single lower case on the RHS, make the lower
                // case and the capital
                if(s.equals("a")||s.equals("b") && r.getRHS().size() == 1){
                    r.setSingleLowercase(s);
                    upperCase= r.getCapital();


                }

            }

        }
        //go through the rules and look for any uppercase matching single Rule
        //for each rule
        for(Rule r: cfg.getRules()){
            //for each string in rule
            for(String s : r.getRHS()){
                String temp= upperCase + "";
                    //if the string equals Uppercase
                    if (temp.equals(s)) {
                        ruleToAdd = r;
                        break;
                    }

            }
        }

        //for each rule in cfg find
        for(Rule r : cfg.getRules()) {
            //if the rules single lower case is not empty
            if(!r.getSingleLowercase().equals("")){
                tempRule= r;
                tempCap= r.getCapital() +"";
                tempLow= r.getSingleLowercase();
            }

        }

        //if the rules capital letter matches the single capital
        if(tempCap != null && tempLow != null) {
            for (Rule r : cfg.getRules()) {
                if (r == ruleToAdd){
                    //remove the capital from rule
                    r.deleteRHS(tempCap);
                    //add the single lowercase
                    r.addRHS(tempLow);
                }
            }
            //for(Rule r: cfg.getRules()){
              //  if(tempCap.equals(r.getCapital()+"")){
                //    ruleHolder2= r;
               // }
            //}
            //cfg.removeRule(ruleHolder2);
            //go through rules and change every cap to low
            for (Rule r : cfg.getRules()) {
                for (String s : r.getRHS()) {
                    s.replaceAll(tempCap, tempLow);
                }
            }
        }
    }
    public void  stepFour(CFG cfg) {

        for (Rule r : cfg.getRules()) {
            rulesHolder = new ArrayList<String>();
            for (String s : r.getRHS()) {
                if (!rulesHolder.contains(s)) {
                    rulesHolder.add(s);
                }
            }
            r.setRHS(rulesHolder);
        }

        ruleHolder3 = null;
        ruleHolder4 = null;
        holder3 = null;
        holder4 = null;
        for (Rule r : cfg.getRules()) {
            for (String s : r.getRHS()) {
                upper = 0;
                lower = 0;
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if (Character.isUpperCase(c)) {
                        upper++;
                    }
                    if (Character.isLowerCase(c)) {
                        lower++;
                    }
                }
                if (upper == 0 && lower > 1) {
                    s = s.substring(0, 1) + "C";
                    cfg.addRule(new Rule('C', s.substring(1), ""));
                }
                if (upper > 2 && lower == 0) {
                    s = s.substring(0, 1) + "D";
                    cfg.addRule(new Rule('D', s.substring(1, 2) + "E", ""));
                    cfg.addRule(new Rule('E', s.substring(2), ""));
                    //ruleHolder4=r;
                }
                if (upper < 2 && lower == 0) {
                    ruleHolder3= r;
                    holder3=s;
                }
            }
        }
        if(ruleHolder3 != null && holder3 != null){
            for(Rule r : cfg.getRules()){
                if(holder3.equals(r.getCapital()+"")){
                    for(String s : r.getRHS()) {
                        ruleHolder3.addRHS(s);
                    }
                }
            }
        }
    }
    ArrayList<Rule> ruless= new ArrayList<>();
    ArrayList<String> stringss= new ArrayList<>();
    int n=0;
    int m=0;

    public void stepFive(CFG cfg){
        // remove all A->aB
        for(Rule t :cfg.getRules()) {
            for (String s : t.getRHS()) {
                n=0;
                m=0;
                for (int i = 0; i < s.length(); i++){
                    char c = s.charAt(i);
                    if(Character.isLowerCase(c)){
                        n=1;
                        if(m==1){
                            ruless.add(t);
                            stringss.add(s);
                        }
                    }
                    if(Character.isUpperCase(c)){
                        m=1;
                        if(n==1){
                            ruless.add(t);
                            stringss.add(s);
                        }
                    }
                }
            }
        }

        for(Rule r : ruless){
            for(int j=0; j< r.getRHS().size(); j++){
                if(stringss.contains(r.getRHS().get(j))){
                    for (int i = 0; i < r.getRHS().get(j).length(); i++){
                        char c = r.getRHS().get(j).charAt(i);
                        if(Character.isLowerCase(c)) {
                            cfg.addRule(new Rule('D', c+"", ""));
                            cha=c;
                            holder4=r.getRHS().get(j);
                            store=j;
                        }
                    }
                }

            }
        }
        for(Rule r : cfg.getRules()){
            if(ruless.contains(r)){
                r.deleteRHS(holder4);
                holder4= holder4.replace(cha,'D');
                r.addRHS(holder4);
            }
        }
    }

    public void printCFG(CFG cfg, TextView text){
        text.setText("");
        ArrayList<Rule> rules= cfg.getRules();
        for(Rule r : rules){

            text.append(r.getCapital()+r.getRHSString()+"\n");
        }
    }

    public void handleE(CFG cfg, ArrayList<String> toDelete,
                        ArrayList<Rule> containsE) {
        //remove the e
        //for each rule in CFG
        for (Rule r : cfg.getRules()) {
            //for each rule that contains e
            for (int i = 0; i < containsE.size(); i++) {
                //if the rules match
                if (r == containsE.get(i)) {
                    //delete the e
                    r.deleteRHS(toDelete.get(i));
                }
            }

        }
        //add extra rules for all rules containing toDelete on the RHS
    }

    private void handleAddRule(ArrayList<Rule> containsCapital, ArrayList<String> stringContains,
                               Character capital) {
        ArrayList<String> newStrings = new ArrayList<String>();
        String character;
        ArrayList<Rule> rulesToAddTo = new ArrayList<Rule>();
        //for each rule in the grammar
        for (Rule r : grammar.getRules()) {
            //for each rule that contains the Capital
            for (Rule c : containsCapital) {
                //if the rule equals the rule that contains capital
                if (r == c) {
                    for (String s : r.getRHS()) {
                        for (String sC : stringContains) {
                            if (s == sC) {
                                character = capital + "";
                                newStrings.add(sC.replace(character, ""));
                                rulesToAddTo.add(r);
                            }
                        }
                    }
                }

            }
        }
        for (int i = 0; i < newStrings.size(); i++) {
            rulesToAddTo.get(i).addRHS(newStrings.get(i));
        }
    }
}


