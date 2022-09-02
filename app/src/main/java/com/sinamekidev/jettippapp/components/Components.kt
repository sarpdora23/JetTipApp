package com.sinamekidev.jettippapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//@Preview(showSystemUi = false)
@Composable
fun InputField(modifier:Modifier=Modifier,
               labelId:String="Default",
               mutableValue:MutableState<String>,
               valueType:KeyboardType = KeyboardType.Number,
               action:KeyboardActions,
               enabled:Boolean,
               imeActions: ImeAction = ImeAction.Next,
               singleLine:Boolean
){
    OutlinedTextField(value = mutableValue.value, onValueChange = {
        mutableValue.value = it
    }, modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
    label = { Text(text = labelId)},
    singleLine = singleLine,
    keyboardActions = action,
    keyboardOptions = KeyboardOptions(keyboardType = valueType, imeAction = imeActions),
        leadingIcon = { Icon(imageVector = Icons.Rounded.AttachMoney, contentDescription = "Money Icon")},
        enabled = enabled
    )

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoundedIconButton(modifier: Modifier = Modifier,
onClick:() -> Unit,
icon:ImageVector,
elevetion:Dp,
bgcolor:Color,
tint:Color
){
    Card(shape = CircleShape,
    modifier = modifier.padding(start = 4.dp),
    backgroundColor = bgcolor,
    onClick = {onClick.invoke()},
    elevation = elevetion) {
        Icon(imageVector =icon ,
            contentDescription = "Plus or Remove",
        tint=tint)
    }
}