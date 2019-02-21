package com.example.shaha.logbook;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.util.Locale;


public class MainPage extends AppCompatActivity {
    Button submit;
    Button dateAutoFill;
    EditText dateField;
    EditText acType;
    EditText reg;
    EditText callsign;
    EditText blockStart;
    EditText blockEnd;
    Context context;
    Database DB;
    Button viewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        //root = new File(Environment.getExternalStorageDirectory().toString());
        context = getApplicationContext();
        DB = new Database(this);
        this.blockTextEditor();
        this.dateTextEditor();
        this.initButton();
        this.initDateAutoFill();
        this.viewButtonPressed();
        DB.removeTEST(); // remove test files
        //TESTSyncData();

    }

    protected void viewButtonPressed(){
        viewButton = (Button) findViewById(R.id.viewData);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainPage.this, ViewLogbook.class);
                MainPage.this.startActivity(myIntent);
            }
        });
    }

    // created for testing only. Migrates text file data to the DB
    protected void TESTSyncData(){
        String test = "01/25/2019,DH8D,FHEN,WEN3226,1.3,16:03,17:21\n" +
                "01/25/2019,DH8D,FHEN,WEN3211,1.3,17:47,19:05 \n" +
                "01/25/2019,DH8D,FHEN,WEN3255,1.3,20:00,21:16\n" +
                "01/25/2019,DH8D,FGWJK,WEN3309,1.0,14:59,15:59 \n" +
                "01/26/2019,DH8D,FGWJK,WEN3202,1.8,17:00,18:48\n" +
                "01/27/2019,DH8D,GWEN,WEN3307,1.4,13:00,14:25\n" +
                "01/27/2019,DH8D,GWEN,WEN3296,1.4,15:55,17:21 \n" +
                "01/27/2019,DH8D,GWEN,WEN3291,1.8,17:48,19:33\n" +
                "02/06/2019,DH8D,FNEN,WEN3147,1.7,13:25,15:07\n" +
                "02/06/2019,DH8D,FNEN,WEN3146,1.4,15:48,17:12\n" +
                "02/06/2019,DH8D,FWEJ,WEN3163,1.2,19:55,21:08\n" +
                "02/06/2019,DH8D,FWEJ,WEN3163,1.2,19:55,21:08\n" +
                "02/07/2019,DH8D,GEWR,WEN3222,1.6,13:43,15:18 \n" +
                "02/07/2019,DH8D,GENV,WEN3223,1.2,16:07,17:19\n" +
                "02/07/2019,DH8D,GENV,WEN3323,1.1,18:10,19:13\n" +
                "02/07/2019,DH8D,GENV,WEN3277,1.3,19:53,21:12\n" +
                "02/08/2019,DH8D,FWEW,WEN3290,1.6,13:56,15:34 \n" +
                "02/08/2019,DH8D,FWEW,WEN3283,1.6,16:04,17:39\n" +
                "02/08/2019,DH8D,FWEW,WEN3130,1.5,18:00,19:28\n" +
                "02/08/2019,DH8D,FWEW,WEN3161,0.6,20:02,20:37\n" +
                "02/09/2019,DH8D,FWEP,WEN3304,1.8,13:52,15:40\n" +
                "02/11/2019,DH8D,FWEW,WEN3368,1.8,18:19,20:06\n" +
                "02/11/2019,DH8D,GTWE,WEN3193,1.7,21:59,23:43\n" +
                "02/12/2019,DH8D,GKWE,WEN3187,1.6,12:51,14:29\n" +
                "02/12/2019,DH8D,GKWE,WEN3186,1.7,15:01,16:44\n" +
                "02/12/2019,DH8D,GKWE,WEN3131,1.7,17:19,19:00\n" +
                "02/12/2019,DH8D,GKWE,WEN3136,1.9,19:29,21:21\n" +
                "02/14/2019,DH8D,FWEP,WEN3221,1.6,14:12,15:46\n" +
                "02/14/2019,DH8D,FWEP,WEN3119,1.1,16:25,17:28\n" +
                "02/14/2019,DH8D,FWEP,WEN3260,1.0,18:05,19:02\n" +
                "02/15/2019,DH8D,GENK,WEN3345,1.5,13:59,15:31\n" +
                "02/15/2019,DH8D,GENK,WEN3316,1.0,16:27,17:28\n" +
                "02/15/2019,DH8D,GENK,WEN3321,1.2,18:02,19:12\n" +
                "02/15/2019,DH8D,GENK,WEN3320,1.2,19:53,21:02\n" +
                "02/15/2019,DH8D,GENU,WEN3162,1.6,22:04,23:40\n" +
                "02/16/2019,DH8D,FSWE,WEN3361,1.1,19:22,20:26\n" +
                "02/16/2019,DH8D,FSWE,WEN3162,1.2,21:06,22:15";

        String[] lines = test.split("\n");
        String [] values = new String[7];
        for(int i = 0; i<lines.length; i++){
            values = lines[i].split(",");
            writeToDB(values);
        }
    }

    protected void initDateAutoFill(){

        dateAutoFill = (Button) findViewById(R.id.dateFill);

        dateAutoFill.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
                currentDate = currentDate.replace('-', '/');
                dateField = (EditText) findViewById(R.id.date);
                dateField.setText(currentDate);
            }
        });

    }
    // Adds / to the date
    protected void dateTextEditor(){
        dateField = (EditText) findViewById(R.id.date);
        dateField.addTextChangedListener(new TextWatcher() {
            int size = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                if(s.length() < 1){
                    dateField.setTextColor(Color.BLACK);
                }
                if(size < s.length()){ // size is increasing
                    if(s.length() == 2 || s.length() == 5){

                        dateField.setText(s+"/");
                        dateField.setSelection(s.length()+1); // reset cursor to the end
                    }
                }else{ // size is decreasing

                }
                size = s.length();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }


    // Adds the : to the block time automatically
    protected void blockTextEditor(){
    blockStart = (EditText) findViewById(R.id.blockStart);
    blockEnd = (EditText) findViewById(R.id.blockEnd);

    blockStart.addTextChangedListener(new TextWatcher() {
        boolean startContainsColon = false;
        int size = 0;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(size < s.length()){ // size is increasing
                if(!s.toString().contains(":")){
                    startContainsColon = false;
                }else{
                    startContainsColon = true;
                }
                if(s.length() == 2 && !startContainsColon){
                    blockStart.setText(s+":");
                    blockStart.setSelection(s.length()+1); // reset cursor to the end
                    startContainsColon = true;
                }
            }else{ // size is decreasing

            }
            size = s.length();
        }
        @Override
        public void afterTextChanged(Editable s) {}
    });

        blockEnd.addTextChangedListener(new TextWatcher() {
            boolean startContainsColon = false;
            int size = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                if(size < s.length()){ // size is increasing
                    if(!s.toString().contains(":")){
                        startContainsColon = false;
                    }else{
                        startContainsColon = true;
                    }
                    if(s.length() == 2 && !startContainsColon){
                        blockEnd.setText(s+":");
                        blockEnd.setSelection(s.length()+1); // reset cursor to the end
                        startContainsColon = true;
                    }
                }else{ // size is decreasing

                }
                size = s.length();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    protected boolean validateDate(String date){
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        char slash = '/';
        int month;
        int day;
        int year;
        //dateField = (EditText) findViewById(R.id.date);
        try{
            if(date.length() == 10){
                // check to see if / is inserted correctly
                if(date.charAt(2) == slash && date.charAt(5) == slash ){
                    // check to see if Month is valid
                    month = Integer.parseInt(date.substring(0, 2));
                    if(month >= 1 && month <= 12){ // month is between 1 and 12
                        // validate day
                        day = Integer.parseInt(date.substring(3, 5));
                        if(day >= 1 && day <= 31){ // day is valid
                            // year validity
                            year= Integer.parseInt(date.substring(6, 10));
                            if(year <= Integer.parseInt(currentDate.substring(0, 4))){ // year is valid
                                dateField.setTextColor(Color.BLACK);
                                return true;
                            }else{ // year is ahead of current year
                                dateField.setTextColor(Color.RED);
                                Toast.makeText(context, "Year is incorrect",Toast.LENGTH_LONG).show();
                                return false;
                            }

                        }else{// day is not valid
                            dateField.setTextColor(Color.RED);
                            Toast.makeText(context, "Day is incorrect",Toast.LENGTH_LONG).show();
                            return false;
                        }
                    }else{ // month is not between 1 and 12
                        dateField.setTextColor(Color.RED);
                        Toast.makeText(context, "Month is incorrect",Toast.LENGTH_LONG).show();
                        return false;
                    }
                }else{// Slashes are not inserted properly
                    dateField.setTextColor(Color.RED);
                    Toast.makeText(context, "Slashes are not entered properly",Toast.LENGTH_LONG).show();
                    return false;
                }
            }else{
                if(date.length() != 0)
                    dateField.setTextColor(Color.RED);
                Toast.makeText(context, "Date not entered properly or is Empty",Toast.LENGTH_LONG).show();
                return false;
            }
        }catch (Exception e){
            dateField.setTextColor(Color.RED);
            Toast.makeText(context, "Date not entered properly or is Empty",Toast.LENGTH_LONG).show();
            return false;
        }



    }
    protected void initButton(){
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] stringArray = new String[10];
                String temp ="";

                // add date
                temp = getDate();
                if(validateDate(temp))
                    stringArray[0] = temp;
                else
                   return;
                temp = ""; // reset Temp

                // add ac Type
                temp = getacType();
                stringArray[1] = temp;
                temp = ""; // reset Temp

                // add registration
                temp = getReg();
                stringArray[2] = temp;
                temp = ""; // reset Temp

                // add rcallsign
                temp = getCallsign();
                stringArray[3] = temp;
                temp = ""; // reset Temp

                // add Flight Time
                temp += getBlockTime();
                stringArray[4] = temp;
                temp = ""; // reset Temp

                // add Block Start
                temp += getBlockStart();
                stringArray[5] = temp;
                temp = ""; // reset Temp

                // add Block End
                temp += getBlockEnd();
                stringArray[6] = temp;
                temp = ""; // reset Temp

                writeToFile(stringArray);
                writeToDB(stringArray);
                 context = getApplicationContext();
                Toast.makeText(context, "Entry Added",Toast.LENGTH_LONG).show();

            }

        });
    }

    protected void writeToDB(String [] array){
        boolean flag;
        flag = DB.insertData(array[0],array[1],array[2],array[3],array[4],array[5],array[6]);
        if(flag)
            Toast.makeText(context, "Entry Added",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "Entry Failed To Add",Toast.LENGTH_LONG).show();
    }

    @TargetApi(26)
    protected void writeToFile(String[] array){
                String str = "\n";


                String path = Environment.getExternalStorageDirectory().toString()+"/Logbook/logbook.txt";
                try {
                    for(int i = 0; i<array.length;i++){
                        str += array[i] + "  ";
                    }
                    Files.write(Paths.get(path),str.getBytes(), StandardOpenOption.APPEND);
                    System.out.println("FILE EXISTS");
                } catch (NoSuchFileException e) {
                    System.out.println("ERROR No Such File Exists");
                    File file = new File(Environment.getExternalStorageDirectory().toString()+"/Logbook");

                    try{
                        file.mkdir();
                        file = new File(path);
                        file.createNewFile();
                        Files.write(Paths.get(path),str.getBytes(), StandardOpenOption.APPEND);
                    }catch (Exception e2){
                        System.out.println(e2);
                        Toast.makeText(context, e2.getMessage(),Toast.LENGTH_LONG).show();
                    }

                } catch (IOException e) {
                    Toast.makeText(context, e.getMessage(),Toast.LENGTH_LONG).show();
                }
    }
    protected String getBlockStart(){
        String temp = "";
        if(blockStart != null)
            temp = blockStart.getText()+"";
        else
            temp = "NULL";
        return temp;
    }
    protected String getBlockEnd(){
        String temp = "";
        if(blockEnd != null)
            temp = blockEnd.getText()+"";
        else
            temp = "NULL";
        return temp;
    }

    protected String getDate(){
        String temp = "";
        dateField = (EditText) findViewById(R.id.date);
        temp += dateField.getText();
        return temp;
    }

    protected  String getacType(){
        String temp = "";
        acType = (EditText) findViewById(R.id.acType);
        temp += acType.getText();
        return temp;
    }

    protected  String getReg(){
        String temp = "";
        reg = (EditText) findViewById(R.id.registration);
        temp += reg.getText();
        return temp;
    }

    protected  String getCallsign(){
        String temp = "";
        callsign = (EditText) findViewById(R.id.callsign);
        temp += callsign.getText();
        return temp;
    }

    protected  double getBlockTime(){

        double fltTime = 0;

        blockStart = (EditText) findViewById(R.id.blockStart);
        blockEnd = (EditText) findViewById(R.id.blockEnd);
        String time1 = blockStart.getText()+":00";
        String time2 = blockEnd.getText()+":00";
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        try{
            Date date1 = format.parse(time1);
            Date date2 = format.parse(time2);
            if(past24(time1, time2)){ // Has passed 24hr mark
                fltTime = getFlightTime(date1, format.parse("24:00:00"))+getFlightTime(format.parse("00:00:00"), date2);
            }else{ // hast passed 24hr mark

                    fltTime = getFlightTime(date1, date2);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return fltTime;
    }

    protected double getFlightTime(Date date1, Date date2){
        double fltTime = 0;
        fltTime = (date2.getTime() - date1.getTime());
        fltTime /= 3600000;
        fltTime = round(fltTime, 1);
        System.out.println(fltTime);
        return fltTime;
    }

    // checks to see if the block time passes 24hr mark
    protected boolean past24(String blockStart, String blockEnd){
        int startHrs = Integer.parseInt((blockStart.charAt(0)+"")+(blockStart.charAt(1)+""));
        int endHrs = Integer.parseInt((blockEnd.charAt(0)+"")+(blockEnd.charAt(1)+""));
        if(startHrs > endHrs){
            return true; // Has past 24hr mark
        }else{
            return false;   // hasnt past 24hr mark
        }
    }

    private double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }








}
