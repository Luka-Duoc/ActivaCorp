package com.example.activacorp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import com.example.activacorp.viewmodel.UsuarioViewModel
import androidx.compose.runtime.getValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.activacorp.model.RolUsuario
import com.example.activacorp.ui.screen.tab.tabCuenta
import com.example.activacorp.ui.screen.tab.tabEventos
import com.example.activacorp.ui.screen.tab.tabOportunidades
import com.example.activacorp.ui.screen.tab.tabOrganizaciones
import com.example.activacorp.viewmodel.EventoViewModel
import com.example.activacorp.viewmodel.OportunidadViewModel
import com.example.activacorp.viewmodel.OrganizacionViewModel
import kotlinx.coroutines.launch

// Estructura para las pestañas
private data class ItemNav(
    val titulo: String,
    val icono: ImageVector
)

private val itemsMenu = listOf(
    ItemNav("Cuenta", Icons.Default.Person),
    ItemNav("Eventos", Icons.Default.Event),
    ItemNav("Oportunidades", Icons.Default.Campaign),
    ItemNav("Organizaciones", Icons.Default.Business)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuentaScreen(
    viewModel: UsuarioViewModel,
    navController: NavController,
    eventoViewModel: EventoViewModel,
    oportunidadViewModel: OportunidadViewModel,
    organizacionViewModel: OrganizacionViewModel
) {
    val estadoGlobal by viewModel.estado.collectAsState() // control de estado para el renderizado dinamico.

    val rolEnum = remember(estadoGlobal.rol) {
        try {
            if (estadoGlobal.rol.isNotBlank()) RolUsuario.valueOf(estadoGlobal.rol.uppercase()).titulo else "sin rol"
        } catch (e: IllegalArgumentException) {
            "Sin rol"
        }
    }

    val pagerState = rememberPagerState(pageCount = { 4 })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(bottomBar = {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = Color.White,
            edgePadding = 0.dp,
            divider = {},
            indicator = { tabPositions ->
                if (pagerState.currentPage < tabPositions.size) {
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        height = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }) {
            // Iteracion
            itemsMenu.forEachIndexed { index, item ->
                Tab(
                    modifier = Modifier.fillMaxWidth(),
                    selected = pagerState.currentPage == index,
                    text = { Text(item.titulo, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center) },
                    icon = { Icon(imageVector = item.icono, contentDescription = item.titulo) },
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }

        }
    }) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            contentPadding = innerPadding,
            beyondViewportPageCount = 0
        ) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (page) {
                    0 -> tabCuenta(viewModel, navController)
                    1 -> tabEventos(navController, eventoViewModel)
                    2 -> tabOportunidades(oportunidadViewModel, navController, rolEnum, estadoGlobal.id)
                    3 -> tabOrganizaciones(
                        navController,
                        eventoViewModel,
                        oportunidadViewModel,
                        organizacionViewModel,
                        rolEnum
                    )
                }
            }
        }
    }
}