package com.the.trainup.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.the.trainup.domain.AddSetUseCase
import com.the.trainup.domain.Exercise
import com.the.trainup.domain.ExerciseRepository
import com.the.trainup.domain.SetEntry
import com.the.trainup.domain.SetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
    class WorkoutDetailViewModel @Inject constructor(
    private val setRepo: SetRepository,
    private val exerciseRepo: ExerciseRepository,

    savedStateHandle: SavedStateHandle
    ) : ViewModel() {

        private val workoutId: Long = checkNotNull(savedStateHandle["workoutId"])

        private val _uiState = MutableStateFlow(WorkoutDetailState())
        val uiState: StateFlow<WorkoutDetailState> = _uiState

        init {
            viewModelScope.launch {
                val sets = setRepo.getSetsForWorkout(workoutId)
                val exercises = exerciseRepo.getAll().associateBy { it.id }
                _uiState.update { it.copy(sets = sets, exerciseMap = exercises) }
            }
        }

        fun addSet(exerciseId: Long, reps: Int, weight: Float) {
            viewModelScope.launch {
                setRepo.addSet(SetEntry(0, workoutId, exerciseId, reps, weight))
                val refreshed = setRepo.getSetsForWorkout(workoutId)
                _uiState.update { it.copy(sets = refreshed) }
            }
        }
    }

    data class WorkoutDetailState(
        val sets: List<SetEntry> = emptyList(),
        val exerciseMap: Map<Long, Exercise> = emptyMap()
    )

