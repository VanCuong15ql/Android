package com.example.test

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import kotlin.math.floor

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var showUnder: TextView
    lateinit var showUp: TextView

    var state: Int = 1
    var op: Int = 0
    var op1: Int = 0
    var op2: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showUnder = findViewById(R.id.showUnder)
        showUp=findViewById(R.id.showTop)
        findViewById<Button>(R.id.button0).setOnClickListener(this)
        findViewById<Button>(R.id.button1).setOnClickListener(this)
        findViewById<Button>(R.id.button2).setOnClickListener(this)
        findViewById<Button>(R.id.button3).setOnClickListener(this)
        findViewById<Button>(R.id.button4).setOnClickListener(this)
        findViewById<Button>(R.id.button5).setOnClickListener(this)
        findViewById<Button>(R.id.button6).setOnClickListener(this)
        findViewById<Button>(R.id.button7).setOnClickListener(this)
        findViewById<Button>(R.id.button8).setOnClickListener(this)
        findViewById<Button>(R.id.button9).setOnClickListener(this)
        findViewById<Button>(R.id.buttonAdd).setOnClickListener(this)
        findViewById<Button>(R.id.buttonsub).setOnClickListener(this)
        findViewById<Button>(R.id.buttonx).setOnClickListener(this)
        findViewById<Button>(R.id.buttonC).setOnClickListener(this)
        findViewById<Button>(R.id.buttondiv).setOnClickListener(this)
        findViewById<Button>(R.id.buttonCE).setOnClickListener(this)
        findViewById<Button>(R.id.buttonBS).setOnClickListener(this)
        findViewById<Button>(R.id.buttonInt).setOnClickListener(this)
        findViewById<Button>(R.id.buttonResult).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        if (id == R.id.button0) {
            addDigit(0)
        } else if (id == R.id.button1) {
            addDigit(1)
        } else if (id == R.id.button2) {
            addDigit(2)
        } else if (id == R.id.button3) {
            addDigit(3)
        } else if (id == R.id.button4){
            addDigit(4)
        } else if (id == R.id.button5){
            addDigit(5)
        }else if (id == R.id.button6){
            addDigit(6)
        } else if (id == R.id.button7){
            addDigit(7)
        } else if (id == R.id.button8){
            addDigit(8)
        } else if (id == R.id.button9){
            addDigit(9)
        } else if (id == R.id.buttonAdd) {
            op = 1
            state = 2
            showUp.text= "$op1+"
        } else if(id == R.id.buttondiv){
            op = 2
            state = 2
            showUp.text= "$op1/"
        } else if(id == R.id.buttonx){
            op=3
            state=2
            showUp.text="$op1"+"x"
        }else if(id==R.id.buttonsub){
            op=4
            state=2
            showUp.text= "$op1-"
        }else if(id==R.id.buttonBS){
            if(state==1){
                op1= floor((op1/10).toDouble()).toInt()
                showUnder.text="$op1"
            }else{
                op2= floor((op2/10).toDouble()).toInt()
                showUnder.text="$op2"
            }
        }else if(id==R.id.buttonC){
            op=0
            state=1
            op1=0
            op2=0
            showUp.text=""
            showUnder.text="0"
        }else if(id==R.id.buttonCE){
            showUnder.text="0"
            if(state==1){
                op1=0
            }else{
                op2=0
            }
        }else if(id==R.id.buttonInt){
            if(state==1){
                op1=-op1
                showUnder.text="$op1"
            }else{
                op2=-op2
                showUnder.text="$op2"
            }
        }
        else if (id == R.id.buttonResult) {
            var result = 0
            if (op == 1) {
                result = op1 + op2
                showUp.text="$op1 + $op2 ="
            }else if (op == 2) {
                result = op1 / op2
                showUp.text="$op1 / $op2 ="
            }else if (op == 3) {
                result = op1 * op2
                showUp.text="$op1 x $op2 ="
            }else if (op == 4) {
                result = op1 - op2
                showUp.text="$op1 - $op2 ="
            }
            showUnder.text = "$result"
            state = 2
            op1 = result
            op2 = 0
            op = 0
        }

    }

    fun addDigit(c: Int) {
        if (state == 1) {
            op1 = op1 * 10 + c
            showUnder.text = "$op1"
        } else {
            op2 = op2 * 10 + c
            showUnder.text = "$op2"
        }
    }
}