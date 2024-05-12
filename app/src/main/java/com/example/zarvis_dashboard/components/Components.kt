package com.example.zarvis_dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.zarvis_dashboard.R
import com.example.zarvis_dashboard.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Logo(modifier: Modifier = Modifier) {
    Text(
        text = "Zarvis", modifier = modifier.padding(16.dp),
        color = Color.Red.copy(alpha = 0.5f), style = MaterialTheme.typography.displayMedium
    )
}

@Composable
fun EmailInput(modifier: Modifier = Modifier,
               emailState: MutableState<String>,
               labelId: String="Email",
               enabled: Boolean=true,
               imeAction: ImeAction = ImeAction.Next,
               onAction: KeyboardActions = KeyboardActions.Default

){
    InputField(modifier=modifier,
        valueState = emailState,
        labelId=labelId,
        enabled=enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction
    )
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean=true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default

) {
    OutlinedTextField(
        value=(valueState.value),
        onValueChange ={ valueState.value = it},
        label ={ Text(text=labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
            color= MaterialTheme.colorScheme.onBackground),
        modifier= Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled=enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
    )
}


@Composable
fun PasswordInput(modifier: Modifier,
                  passwordState: MutableState<String>,
                  labelId: String,
                  enabled: Boolean,
                  passwordVisibility: MutableState<Boolean>,
                  onAction: KeyboardActions = KeyboardActions.Default,
                  imeAction: ImeAction = ImeAction.Done) {
    val visualTransformation=if(passwordVisibility.value) VisualTransformation.None else
        PasswordVisualTransformation()

    OutlinedTextField(value = passwordState.value,
        onValueChange ={
            passwordState.value=it} ,
        label={ Text(text=labelId) },
        singleLine=true,
        textStyle = TextStyle(fontSize=18.sp, color= MaterialTheme.colorScheme.onBackground),
        modifier= modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled=enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction,
        ),
        visualTransformation = visualTransformation, trailingIcon = {PasswordVisibility(passwordVisibility)},
        keyboardActions = onAction
    )

}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible=passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value= !visible}) {
        Icons.Default.Close
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String, icon: ImageVector?=null,
                 showProfile:Boolean=true,
                 navController: NavController,
                 onBackArrowClicked: ()->Unit={}
){
    TopAppBar(title = {
        Row(verticalAlignment = Alignment.CenterVertically){
            if(showProfile){
                Image(painter = painterResource(id = R.drawable.icons8_book_100), contentDescription ="icon")
            }

            if(icon!=null){
                Icon(imageVector = icon, contentDescription = "Arrow Back",
                    tint = Color.Red.copy(alpha=0.7f), modifier = Modifier.clickable { onBackArrowClicked.invoke() })
            }
            Text(text = title, modifier = Modifier.padding(start=20.dp), color = Color.Red.copy(alpha = 0.7f),
                style= TextStyle( fontWeight = FontWeight.Bold, fontSize = 25.sp))
            Spacer(modifier=Modifier.width(150.dp))

        }

    }, actions = { IconButton(onClick = {
        FirebaseAuth.getInstance().signOut().run {navController.navigate(AppScreens.LoginScreen.name)}
    }) {
        if(showProfile) Row() {
            Image(
                painter = painterResource(id = R.drawable.icons8_logout_96),
                contentDescription = "icon"
            )
        } else Box{}

    }},colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer, titleContentColor = MaterialTheme.colorScheme.primary,
    )
    )
}