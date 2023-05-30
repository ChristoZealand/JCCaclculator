package com.example.calculatorjc

import android.os.Bundle
import android.text.style.ClickableSpan
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculatorjc.ui.theme.CalculatorJCTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorJCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator(
                        options = listOf("+", "-", "*", "/"),
                        onValueChange = { /* Do something */ }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calculator(
    options: List<String>,
    onValueChange: (Int) -> Unit
) {
    var num1 by remember { mutableStateOf(0) }
    var num2 by remember { mutableStateOf(0) }
    var operator by remember { mutableStateOf("+") }
    var result by remember { mutableStateOf("") }

    Row(modifier = Modifier.padding(all = 8.dp)) {
        TextField(
            value = num1.toString(),
            onValueChange = { num1 = it.toIntOrNull() ?: 0 },
            modifier = Modifier.width(75.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(modifier = Modifier.padding(2.dp)) {
            var expanded by remember { mutableStateOf(false) }
            IconButton(onClick = { expanded = true}) {
                if(expanded === true){
                    Icon(Icons.Default.KeyboardArrowUp, "")
                }
                else{
                    Icon(Icons.Default.KeyboardArrowDown, "")
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            operator = option
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        TextField(
            value = num2.toString(),
            onValueChange = { num2 = it.toIntOrNull() ?: 0 },
            modifier = Modifier.width(75.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = {
            result = calculate(operator, num1.toString(), num2.toString())
        }) {
            Text("=")
        }
        Spacer(modifier = Modifier.width(10.dp))
       Text(
           result,
           modifier = Modifier
               .padding(bottom = 16.dp)
       )
    }
}

private fun calculate(operator: String, num1: String, num2: String): String {
    val n1 = num1.toIntOrNull() ?: 0
    val n2 = num2.toIntOrNull() ?: 0
    return when (operator) {
        "+" -> (n1 + n2).toString()
        "-" -> (n1 - n2).toString()
        "*" -> (n1 * n2).toString()
        "/" -> if (n2 == 0) "Undefined" else (n1 / n2).toString()
        else -> "Invalid operator"
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorJCTheme {
        Surface() {
            Calculator(onValueChange = {}, options = listOf("+", "-"))
        }
    }
}