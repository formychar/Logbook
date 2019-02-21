package com.example.shaha.logbook;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.graphics.Color.parseColor;

public class ViewLogbook extends AppCompatActivity {
    Database DB;
    TableLayout tbLayout;
    TextView TV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = new Database(this);
        setContentView(R.layout.activity_view_logbook);



        populateTable();
    }

    protected void populateTable(){
        Cursor result;
        result = DB.getAllData();
        if(result.getCount() != 0){
            String [] strArray = new String[result.getCount()];
            StringBuffer buffer = new StringBuffer();
            int count = 0;
            while(result.moveToNext()){
                buffer.append(result.getString(0)+","); // Date
                buffer.append(result.getString(1)+","); // Type
                buffer.append(result.getString(2)+","); // Registration
                buffer.append(result.getString(3)+","); // Callsign
                buffer.append(result.getString(4)+","); // Flight Time
                buffer.append(result.getString(5)+","); // Start Time
                buffer.append(result.getString(6)); // End Time
                strArray[count] = buffer.toString();
                count++;
               // System.out.println("***********   buffer: "+buffer.toString());
                buffer.delete(0, buffer.length());
            }
            addHeader();
            addRowView(strArray);


        }else{
            // ERROR
        }


    }

    protected  void addHeader(){
        tbLayout = (TableLayout) findViewById(R.id.ViewTable);
        TableRow header = new TableRow(this);
        TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(trParams);
        header.setBackgroundColor(parseColor("#008080"));
        TextView date = new TextView(this);
        TextView type = new TextView(this);
        TextView registration = new TextView(this);
        TextView callsign = new TextView(this);
        TextView flightTime = new TextView(this);

        date.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        type.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        registration.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        callsign.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        flightTime.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        date.setTypeface(date.getTypeface(), Typeface.BOLD);
        type.setTypeface(date.getTypeface(), Typeface.BOLD);
        registration.setTypeface(date.getTypeface(), Typeface.BOLD);
        callsign.setTypeface(date.getTypeface(), Typeface.BOLD);
        flightTime.setTypeface(date.getTypeface(), Typeface.BOLD);

        date.setTextColor(Color.WHITE);
        type.setTextColor(Color.WHITE);
        registration.setTextColor(Color.WHITE);
        callsign.setTextColor(Color.WHITE);
        flightTime.setTextColor(Color.WHITE);

        date.setGravity(Gravity.LEFT);
        type.setGravity(Gravity.LEFT);
        registration.setGravity(Gravity.LEFT);
        callsign.setGravity(Gravity.LEFT);
        flightTime.setGravity(Gravity.LEFT);

        date.setText("Date");
        type.setText("Type");
        registration.setText("Registration");
        callsign.setText("Callsign");
        flightTime.setText("Flight Time");

        header.addView(date);
        header.addView(type);
        header.addView(registration);
        header.addView(callsign);
        header.addView(flightTime);

        tbLayout.addView(header);




    }


    protected void addRowView(String [] strArray){
        String data = "";
        tbLayout = (TableLayout) findViewById(R.id.ViewTable);
        //TV = (TextView) findViewById(R.id.sampleText);

        String [] fields;
        for(int i = 0; i<strArray.length; i++){

            TableRow row = new TableRow(this);
            TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(trParams);
            fields = strArray[i].split(",");

            TextView Date = new TextView(this);
            TextView Type = new TextView(this);
            TextView Reg = new TextView(this);
            TextView Callsign = new TextView(this);
            TextView FltTime = new TextView(this);

            Date.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            Type.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            Reg.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            Callsign.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            FltTime.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));



            Date.setGravity(Gravity.CENTER);
            Type.setGravity(Gravity.CENTER);
            Reg.setGravity(Gravity.CENTER);
            Callsign.setGravity(Gravity.CENTER);
            FltTime.setGravity(Gravity.CENTER);

            Date.setText(fields[0]);
            Type.setText(fields[1]);
            Reg.setText(fields[2]);
            Callsign.setText(fields[3]);
            FltTime.setText(fields[4]);


            row.addView(Date);
            row.addView(Type);
            row.addView(Reg);
            row.addView(Callsign);
            row.addView(FltTime);

            if(i % 2 == 0){ // i is even
                row.setBackgroundColor(parseColor("#d9d9d9"));
            }else{
                row.setBackgroundColor(parseColor("#ffffff"));
            }

            tbLayout.addView(row);

        }

    }

}
