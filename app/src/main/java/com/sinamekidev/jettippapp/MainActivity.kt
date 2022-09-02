package com.sinamekidev.jettippapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sinamekidev.jettippapp.components.InputField
import com.sinamekidev.jettippapp.components.RoundedIconButton
import com.sinamekidev.jettippapp.ui.theme.JetTippAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                val person_cost = remember {
                    mutableStateOf(0.0)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(30.dp)) {
                    TopHeader(person_cost.value)
                    Spacer(modifier = Modifier.height(20.dp))
                    MainContent(){
                        person_cost.value = it.toDouble()
                    }
                }
            }
        }
    }
}
@Composable
fun MyApp(context:@Composable () -> Unit){
    JetTippAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            context()
        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun TopHeader(totalcost:Double = 20.0){
    Surface(modifier = Modifier
        .height(100.dp)
        .width(250.dp)
        .fillMaxHeight()
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp)))
        , color = Color(0xFFF393EC),
        elevation = 5.dp,
        contentColor = Color.Black
    ) {
            val total = "%.2f".format(totalcost)
            Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Per Person", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = "$${total}", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold )
            }
    }
}

@Composable
fun MainContent(getBill:(String) -> Unit){
    var bill = remember {
        mutableStateOf(0.0)
    }
    BillForm{
        println("Value: ${it.toString()}")
        bill.value = it
    }
    getBill(bill.value.toString())
}

@Composable
fun BillForm(changeVal:(Double) -> Unit){
    var normal_bill = remember{
        mutableStateOf("")
    }
    var person_count = remember {
        mutableStateOf(1)
    }
    var tip_percent = remember {
        mutableStateOf(0f)
    }
    var total_tip = remember {
       mutableStateOf(0f)
    }
    println("Test")
    Surface(modifier = Modifier
        .width(250.dp)
        //.height(200.dp)
        .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        color = Color.White) {
        Column() {
            InputField(mutableValue =normal_bill , action = KeyboardActions{
                if(normal_bill.value.isNotEmpty()){
                    changeVal(((normal_bill.value.toFloat() + total_tip.value.toFloat()) / person_count.value.toFloat()).toDouble())
                }
            },
                enabled = true,
                singleLine = true,
                labelId = "Enter Bill",
                valueType = KeyboardType.Number,
                imeActions = ImeAction.Default,
                modifier = Modifier.fillMaxWidth())
            if(normal_bill.value.isNotEmpty()){
                Row(horizontalArrangement =Arrangement.Start,
                    modifier = Modifier.padding(start = 7.dp, end = 7.dp)) {
                    Text(text = "Split")
                    Spacer(modifier = Modifier.width(100.dp))
                    Row(horizontalArrangement =Arrangement.End) {
                        RoundedIconButton(modifier = Modifier.size(30.dp),
                            icon = Icons.Default.Add,
                            onClick = {
                                println("Plus button clicked")
                                person_count.value++
                                changeVal(((normal_bill.value.toFloat() + total_tip.value.toFloat()) / person_count.value.toFloat()).toDouble())
                            }, elevetion = 5.dp, bgcolor = MaterialTheme.colors.background, tint = Color.Black)
                        Text(text = person_count.value.toString(), modifier = Modifier.padding(start = 6.dp, end = 4.dp))
                        RoundedIconButton(modifier = Modifier.size(30.dp),
                            icon = Icons.Default.Remove,
                            onClick = {
                                println("Remove button clicked")
                                if (person_count.value > 1)
                                    person_count.value--
                                changeVal(((normal_bill.value.toFloat() + total_tip.value.toFloat()) / person_count.value.toFloat()).toDouble())
                            }, elevetion = 5.dp, bgcolor = MaterialTheme.colors.background, tint = Color.Black)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(start = 7.dp, end = 7.dp)){
                    Text(text = "Tip")
                    Spacer(modifier = Modifier.width(140.dp))
                    Text(text = "${"%.2f".format(total_tip.value)}$")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "%${(tip_percent.value * 100).toInt()}")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Slider(value = tip_percent.value,
                    onValueChange ={tip_percent.value = it
                        total_tip.value = tip_percent.value * normal_bill.value.toFloat()
                        changeVal(((normal_bill.value.toFloat() + total_tip.value.toFloat()) / person_count.value.toFloat()).toDouble())
                                   println(tip_percent.value.toString())},
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                steps = 5)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MyApp {
        val person_cost = remember {
            mutableStateOf(0.0)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(30.dp)) {
            TopHeader(person_cost.value)
            Spacer(modifier = Modifier.height(20.dp))
            MainContent(){
                person_cost.value = it.toDouble()
            }
        }
    }
}