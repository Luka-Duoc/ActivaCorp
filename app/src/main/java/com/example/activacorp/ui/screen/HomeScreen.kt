package com.example.activacorp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

val contenidos = listOf(
    "Cada oportunidad cuenta",
    "Descubre, organiza y gestiona nuevas oportunidades de activación corporativa desde un solo lugar."
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val purpleColor = Color(0xFF800080)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = purpleColor,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text("Activa Corp", style = MaterialTheme.typography.headlineSmall)
                }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(contenidos) { index, item ->
                Row {
                    if (index == 0) {
                        Text(
                            text = "$item",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    } else {
                        Text(
                            text = "$item",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }
            }
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(15.dp),
                    thickness = 5.dp
                )
            }
            item {
                TextButton(
                    modifier = Modifier.padding(15.dp),
                    onClick = { navController.navigate("registrar") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = purpleColor,
                        contentColor = Color.White
                    )
                ) {
                    Text("Crea tu cuenta gratuita", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
