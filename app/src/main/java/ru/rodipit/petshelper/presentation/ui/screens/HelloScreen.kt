package ru.rodipit.petshelper.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.presentation.ui.Navigation
import ru.rodipit.petshelper.presentation.viewModels.HelloViewModel


@Composable
fun HelloScreen(navController: NavController, viewModel: HelloViewModel) {

    val name by viewModel.userName.collectAsState()

    LaunchedEffect(Unit) {
        launch {
            viewModel.regIsComplete.collect {
                if (it) {
                    navController.navigate(Navigation.MAIN_ROUTE) { popUpTo(Navigation.HELLO_ROUTE) }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.greeting),
                modifier = Modifier.fillMaxWidth(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(34.dp))
            Row {
                OutlinedTextField(value = name, onValueChange = {
                    viewModel.updateUserName(it)
                })
                IconButton(onClick = { viewModel.createUser(name) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                        contentDescription = "Next",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HelloScreenPreview(
    navController: NavController = rememberNavController(),
    viewModel: HelloViewModel = hiltViewModel()
) {
    HelloScreen(navController = navController, viewModel = viewModel)
}