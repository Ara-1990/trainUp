package com.the.trainup.ui.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ExercisesScreen(
    onOpenWorkout: (Long) -> Unit,
    vm: ExercisesViewModel = hiltViewModel()
){
    val ui by vm.ui.collectAsState()


    Column(Modifier.fillMaxSize().padding(16.dp)) {

        SearchAndFilters(
            query = ui.query,
            onQueryChange = vm::onQueryChange,
            onSearch = vm::refresh,
            muscle = ui.muscle,
            onMuscleChange = vm::onMuscleChange,
            type = ui.type,
            onTypeChange = vm::onTypeChange,
            difficulty = ui.difficulty,
            onDifficultyChange = vm::onDifficultyChange
        )
        Spacer(Modifier.height(12.dp))

        if (ui.items.isEmpty() && !ui.isLoading && ui.error == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("nothing is find ")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(ui.items) { ex ->
                    ExerciseCard(
                        name = ex.name,
                        muscle = ex.muscleGroup,
                        difficulty = ex.difficulty ?: "",
                        equipment = ex.equipment ?: "",
                        onAddToWorkout = { onOpenWorkout(1L) }
                    )
                }
            }
        }
    }


    if (ui.isLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }


    ui.error?.let { err ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            AssistChip(
                onClick = vm::refresh,
                label = { Text("Repeat: $err") }
            )
        }
    }
}





@Composable
private fun SearchAndFilters(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    muscle: String?,
    onMuscleChange: (String?) -> Unit,
    type: String?,
    onTypeChange: (String?) -> Unit,
    difficulty: String?,
    onDifficultyChange: (String?) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            label = { Text("Search by name") },
            trailingIcon = {
                TextButton(onClick = {onSearch()}) { Text("Find") }
            },
            modifier = Modifier.fillMaxWidth()
        )


        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterDropdown(
                label = "Muscle",
                options = listOf(null, "biceps", "triceps", "chest", "back", "legs", "shoulders"),
                selected = muscle,
                onSelected = onMuscleChange,
                modifier = Modifier.weight(1f)
            )
            FilterDropdown(
                label = "Type",
                options = listOf(null, "strength", "cardio", "stretching"),
                selected = type,
                onSelected = onTypeChange,
                modifier = Modifier.weight(1f)
            )
        }

            FilterDropdown(
                label = "Complexity",
                options = listOf(null, "beginner", "intermediate", "expert"),
                selected = difficulty,
                onSelected = onDifficultyChange,
                modifier = Modifier.weight(1f)
            )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> FilterDropdown(
    label: String,
    options: List<T?>,
    selected: T?,
    onSelected: (T?) -> Unit,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = (selected?.toString() ?: "Any"),
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { opt ->
                DropdownMenuItem(
                    text = { Text(opt?.toString() ?: "Any") },
                    onClick = {
                        onSelected(opt)
                        expanded = false
                    }
                )
            }
        }
    }
}



@Composable
private fun ExerciseCard(
    name: String,
    muscle: String,
    difficulty: String,
    equipment: String,
    onAddToWorkout: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(6.dp))
            InfoRow("Muscle", muscle)
            if (difficulty.isNotBlank()) InfoRow("Complexity", difficulty)
            if (equipment.isNotBlank()) InfoRow("Equipment", equipment)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilledTonalButton(onClick = onAddToWorkout) { Text("Add to workout") }
            }
        }
    }
}

@Composable
private fun InfoRow(title: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        AssistChip(onClick = {}, label = { Text(title) })
        Spacer(Modifier.width(8.dp))
        Surface(tonalElevation = 2.dp, shape = MaterialTheme.shapes.large) {
            Text(
                value,
                modifier = Modifier.clip(MaterialTheme.shapes.large).background(MaterialTheme.colorScheme.surfaceVariant).padding(horizontal = 10.dp, vertical = 6.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}