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
        input= findViewById(R.id.input);
        output= findViewById(R.id.output);
        test1.setOnClickListener(this);
        test2.setOnClickListener(this);
        test3.setOnClickListener(this);
        step1.setOnClickListener(this);
        step2.setOnClickListener(this);
        step3.setOnClickListener(this);
        step4.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.Test1){
            ArrayList<Rule> rules= new ArrayList<Rule>();
            rules.add(new Rule('S', "ASA", "aB"));
            rules.add(new Rule('A', "B", "S"));
            rules.add(new Rule('B', "b", "e"));

            grammar= new CFG(rules);
            input.setText("");
            printCFG(grammar, input);

        }
        if(view.getId()== R.id.Test2){
            ArrayList<Rule> rules= new ArrayList<Rule>();
            rules.add(new Rule('S', "BA", "aS"));
            rules.add(new Rule('A', "BbA", "e"));
            rules.add(new Rule('B', "b", "a"));

            grammar= new CFG(rules);
            input.setText("");
            printCFG(grammar, input);
        }
        if(view.getId()== R.id.Test3){
            ArrayList<Rule> rules= new ArrayList<Rule>();
            rules.add(new Rule('S', "aaB", "bA"));
            rules.add(new Rule('A', "BbA", "e"));
            rules.add(new Rule('B', "b", "a"));

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
    }
    public void stepThree(CFG cfg){

    }
    public void  stepFour(CFG cfg){

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


}