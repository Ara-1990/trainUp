package com.the.trainup.ui

import android.widget.GridLayout.Spec
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun WorkoutDetailScreen(
    vm: WorkoutDetailViewModel = hiltViewModel()
) {
    val state by vm.uiState.collectAsState()



    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Workout", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(12.dp))



        var reps by remember { mutableStateOf("") }
        var weight by remember { mutableStateOf("") }



        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = reps, onValueChange = { reps = it }, label = { Text("Reps") })
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight (kg)") })
        }
        Spacer(Modifier.height(8.dp))
        FilledTonalButton(onClick = {
            val exId = state.exerciseMap.keys.firstOrNull()
            if (exId != null && reps.isNotBlank() && weight.isNotBlank()){
                 vm.addSet(exId, reps.toInt(), weight.toFloat())
                reps = ""; weight = ""
            }
        })  {
            Text("Add a set to the first exercise")
        }

        Spacer(Modifier.height(16.dp))

        Divider()
        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.sets){ set->
                val name = state.exerciseMap[set.exerciseId]?.name ?: "Exercise ${set.exerciseId}"
                ListItem(
                    headlineContent = { Text(name) },
                    supportingContent = { Text("${set.reps} repeat. × ${set.weightKg} kg") }
                )
                Divider()
            }
        }


    }
}
