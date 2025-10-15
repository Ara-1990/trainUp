package com.the.trainup.ui.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.the.trainup.domain.ExerciseRepository
import com.the.trainup.domain.ExercisesUiState
import com.the.trainup.domain.SyncExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.Job


@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val exerciseRepo: ExerciseRepository,
    private val syncExercises: SyncExercisesUseCase
) : ViewModel() {

    private val _ui = MutableStateFlow(ExercisesUiState())
    val ui: StateFlow<ExercisesUiState> = _ui

    private var syncJob: Job? = null


    fun onQueryChange(q: String) { _ui.value = _ui.value.copy(query = q) }
    fun onMuscleChange(m: String?) { _ui.value = _ui.value.copy(muscle = m); refresh() }
    fun onTypeChange(t: String?) { _ui.value = _ui.value.copy(type = t); refresh() }
    fun onDifficultyChange(d: String?) { _ui.value = _ui.value.copy(difficulty = d); refresh() }

    fun refresh() {
        syncJob?.cancel()
        syncJob = viewModelScope.launch {
            try {
                _ui.value = _ui.value.copy(isLoading = true, error = null)
                val s = _ui.value

                val muscle = if (s.query.isBlank()) s.muscle else null
                val name = if (s.query.isNotBlank()) s.query else null

                syncExercises(
                    muscle = muscle,
                    type = if (name == null) s.type else null,
                    difficulty = if (name == null) s.difficulty else null,
                    name = name
                )
                _ui.value = _ui.value.copy(items = exerciseRepo.getAll(), isLoading = false)
            } catch (e: Exception) {
                val cached = exerciseRepo.getAll()
                _ui.value = _ui.value.copy(items = cached, isLoading = false, error = e.message ?: "Loading error")
            }
        }
    }
}

