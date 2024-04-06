package ru.rodipit.petshelper.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.ui.TaskItemWidget
import ru.rodipit.petshelper.ui.ui_states.MainScreenUiState
import ru.rodipit.petshelper.viewModels.MainScreenViewModel

@Composable
fun MainScreen(viewModel: MainScreenViewModel, innerPadding: PaddingValues) {


    val uiState by viewModel.uiState.collectAsState()
    Log.d("TAG", "MainScreen")
    if (uiState.currentAnimal.id != -1) {
        AnimalScreen(uiState = uiState, viewModel, innerPadding = innerPadding)
    } else {
        NeedAnimalScreen(innerPadding = innerPadding)
    }

}

@Composable
fun AnimalScreen(
    uiState: MainScreenUiState,
    viewModel: MainScreenViewModel,
    innerPadding: PaddingValues
) {
    Log.d("TAG", "Animal Screen")
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(5.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .heightIn(30.dp, 40.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Info",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_edit_24),
                    contentDescription = "edit",
                    Modifier.scale(1.5f)
                )
            }

        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Fullname: ${uiState.currentAnimal.fullName}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Age: ${uiState.currentAnimal.getAge()}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Birthday: ${uiState.currentAnimal.birthday}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Breed: ${uiState.currentAnimal.breed}")
        }
        Box(
            Modifier
                .fillMaxWidth()
                .heightIn(40.dp, 50.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.LightGray)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Today Tasks",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = { viewModel.addTask() },
                modifier = Modifier.align(Alignment.CenterEnd),
                ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_circle_24),
                    contentDescription = "edit",
                    Modifier.scale(1.5f)
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.currentTasks, key = { task ->
                task.id!!
            }) { task ->
                TaskItemWidget.DailyTaskItem(task = task, onChangeState = {
                    viewModel.updateTask(task.copy(state = it))
                })
            }
        }
    }
}

