package com.example.zarvis_dashboard.screens.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.zarvis_dashboard.R
import com.example.zarvis_dashboard.components.AppBar
import com.example.zarvis_dashboard.navigation.AppScreens
import com.example.zarvis_dashboard.rememberQrBitmapPainter
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

@Composable
fun HomeScreen(navController: NavController){
    val currentUser= FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {
            AppBar(title = "Profile",
                icon = null,
                showProfile = false,
                navController = navController){
                navController.popBackStack()
            }
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Image(
                            painter= painterResource(id = R.drawable.user),
                            modifier = Modifier.padding(start=90.dp, top=10.dp),

                            contentDescription = "icon",
                        )
                        Row(Modifier.padding(start=100.dp, top=30.dp), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center){
                        Text(
                                text = "Hi, ${
                                    currentUser?.email.toString()
                                        .split("@")[0].uppercase(Locale.getDefault())
                                }"
                        )}
                        Row(Modifier.padding(start=80.dp, top=30.dp),verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = currentUser?.email.toString()
                            )
                        }
                        Row(Modifier.padding(start=90.dp, top=50.dp, bottom=30.dp),verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center){
                        Image(
                            painter = rememberQrBitmapPainter(currentUser?.email.toString()),
                            contentDescription = "email",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.size(200.dp),
                        )
                        }
                    }
            Row(Modifier.padding(start=100.dp, top=50.dp, end=100.dp),verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                FilledTonalButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                            .run { navController.navigate(AppScreens.LoginScreen.name) {
                                popUpTo (AppScreens.HomeScreen.name) {
                                    inclusive = true
                                }
                            } }
                    }) {
                    Text("Logout")

                }
            }
                }
            }
        }

@Preview
@Composable
fun Prev(){
    HomeScreen(navController = rememberNavController())
}