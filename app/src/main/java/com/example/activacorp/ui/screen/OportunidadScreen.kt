package com.example.activacorp.ui.screen

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.data.local.seed.OportunidadSeed.listaOportunidades
import com.example.activacorp.model.EstadoGestion
import com.example.activacorp.model.PotencialComercial
import com.example.activacorp.model.TipoOportunidad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OportunidadScreen(navController: NavController) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text("Oportunidades registradas")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("cuenta")
            }) {
                Icon(Icons.Default.Replay, contentDescription = "Go back")
            }
        }
    ) {
        innerPadding ->
        Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(innerPadding)) {
            listaOportunidades.forEach { item -> OportunidadCard(item) }
        }
    }


}

@Composable
fun OportunidadCard(oportunidad: OportunidadEntity) {
    Column(modifier = Modifier) {
        Card(modifier = Modifier.padding(all = 16.dp)) {
            Column(modifier = Modifier.padding(all = 20.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Oportunidad", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
            }
            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
               Text("Nombre", textAlign = TextAlign.Center)
            }

            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = oportunidad.nombre, textAlign = TextAlign.Center)
            }

            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Tipo de oportunidad")
            }

            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("${oportunidad.tipo}", textAlign = TextAlign.Center)
            }

            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Descripcion de oportunidad")
            }
            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("${oportunidad.descripcion}", textAlign = TextAlign.Center)
            }

            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Fuente de detección", textAlign = TextAlign.Center)
            }
            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("${oportunidad.fuente}", textAlign = TextAlign.Center)
            }
            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Fecha de detección")
            }
            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("${oportunidad.fechaActualizacion}")
            }
            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Responsable")
            }
            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("${oportunidad.responsable}")
            }
            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Prioridad")
            }
            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("${oportunidad.prioridad}")
            }
            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Potencial comercial")
            }
            Row(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("${oportunidad.potencial}")
            }
        }
    }
}