package com.example.zarvis_dashboard.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.zarvis_dashboard.R
import com.example.zarvis_dashboard.components.EmailInput
import com.example.zarvis_dashboard.components.Logo
import com.example.zarvis_dashboard.components.PasswordInput
import com.example.zarvis_dashboard.navigation.AppScreens


@ExperimentalComposeUiApi
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginScreenViewModel= viewModel()){
    val showLoginForm= rememberSaveable{ mutableStateOf(true) }

    Surface(modifier= Modifier.fillMaxSize()){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            Logo()
            if(showLoginForm.value) {
                UserForm(loading = false, isCreateAccount = false) { email, password ->
                    //TODO FB login
                    viewModel.signInWithEmailANdPassword(email,password,){
                        navController.navigate(AppScreens.HomeScreen.name)
                    }
                    Log.d("Form", "ReaderLoginScreen: $email $password")
                }
            }else{
                UserForm(loading = false, isCreateAccount = true,) { email, password ->

                    viewModel.createUserWithEmailANdPassword(email, password){
                        navController.navigate(AppScreens.HomeScreen.name)
                    }
                    Log.d("Form", "LoginScreen: $email $password")
                }
            }
        }
        Spacer(modifier= Modifier.height(15.dp))
        Row(
            modifier= Modifier.padding(15.dp),
            horizontalArrangement= Arrangement.Center,
            verticalAlignment= Alignment.CenterVertically
        ){
            val text2=if(showLoginForm.value) "Sign Up" else "Login"
            val text1=if(showLoginForm.value) "New User ?" else "Have an account ?"
            Text(text1)
            Text(text2, modifier= Modifier
                .clickable {
                    showLoginForm.value = !showLoginForm.value
                }
                .padding(start = 5.dp),
                fontWeight = FontWeight.Bold,
                color= MaterialTheme.colorScheme.tertiary
            )
        }
    }
}




@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(loading: Boolean=false,
             isCreateAccount:Boolean=false,
             onDone:(String,String)-> Unit= {email, pwd ->}
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password= rememberSaveable { mutableStateOf("") }
    val passwordVisibility= rememberSaveable{ mutableStateOf(false) }
    val passwordFocusRequest= FocusRequester.Default
    val keyboardController= LocalSoftwareKeyboardController.current
    val valid= remember(email.value, password.value){
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    val modifier= Modifier
        .height(300.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())
    Column(modifier,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        if(isCreateAccount) Text(text= stringResource(id = R.string.create_act), modifier= Modifier.padding(4.dp) )
        else Text(text= stringResource(id = R.string.login_cred), modifier= Modifier.padding(4.dp) )


        EmailInput(emailState = email,
            enabled = !loading,
            onAction = KeyboardActions{
                passwordFocusRequest.requestFocus()
            }, imeAction = ImeAction.Next
        )
        PasswordInput(modifier= Modifier.focusRequester(passwordFocusRequest),
            passwordState=password,
            labelId="Password",
            enabled= !loading,
            passwordVisibility=passwordVisibility,
            onAction= KeyboardActions{
                if(!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            }
        )

        SubmitButton(
            textId=if(isCreateAccount) "Create Account" else "Login",
            loading=loading,
            validInputs=valid,
        ){
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }


}

@Composable
fun SubmitButton(textId: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: ()-> Unit
) {
    Button(onClick=onClick,
        modifier= Modifier
            .padding()
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape= CircleShape
    ){
        if(loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text=textId, modifier= Modifier.padding(5.dp))
    }

}

