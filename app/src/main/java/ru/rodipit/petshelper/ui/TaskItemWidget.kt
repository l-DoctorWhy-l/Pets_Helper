package ru.rodipit.petshelper.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.data.entities.Task
import ru.rodipit.petshelper.viewModels.MainScreenViewModel

class TaskItemWidget() {

    companion object{

        @Composable
        fun DailyTaskItem(task: Task, onChangeState: (Boolean) -> Unit ){
            OutlinedCard(modifier = Modifier
                .fillMaxWidth()
                .heightIn(20.dp, 120.dp),
                border = BorderStroke(2.dp, Color.LightGray)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth(0.9f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row( modifier = Modifier
                            .fillMaxHeight(0.25f)
                            .fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = task.getTaskTypeResourceId()),
                                contentDescription = "",
                                modifier = Modifier.scale(0.8f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = task.title,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(1.dp)
                            .background(Color.LightGray)){}
                        Spacer(modifier = Modifier.height(4.dp))
                        if(task.description.isNotBlank()){
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.8f)
                            ) {
                                Text(text = task.description,
                                    maxLines = 2,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.fillMaxHeight()
                                )
                            }
                            Row(modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()

                            ) {
                                Spacer(modifier = Modifier.width(40.dp))
                                Text(text = "time")
                            }
                        } else{
                            Row(modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                                verticalAlignment = Alignment.Bottom

                            ) {
                                Spacer(modifier = Modifier.width(40.dp))
                                Text(text = "time")
                            }
                        }

                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Checkbox(checked = task.state,
                            onCheckedChange = onChangeState
                        )
                    }
                }

            }
        }
    }

}