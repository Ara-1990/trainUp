package com.the.trainup.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.ListAlt

import androidx.compose.material3.*

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.the.trainup.ui.exercises.ExercisesScreen
import androidx.compose.material3.TopAppBar
import androidx.navigation.NavType
import androidx.navigation.navArgument

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainUpApp() {
    val nav = rememberNavController()



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TrainUp") }
            ) },


        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        nav.navigate("explore") {
                            popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true; restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Outlined.FitnessCenter, null) },
                    label = { Text("Exercises") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {

                        nav.navigate("workout/1") {
                            popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true; restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Outlined.ListAlt, null) },
                    label = { Text("Training") }
                )
            }
        }

    ) {

            padding ->
        NavHost(navController = nav, startDestination = "explore", modifier = Modifier.padding(padding)) {
            composable("explore") {

                ExercisesScreen(onOpenWorkout = { id -> nav.navigate("workout/$id") })
            }
            composable(
                "workout/{workoutId}",
                arguments = listOf(navArgument("workoutId"){ type = NavType.LongType })
            ) {
                WorkoutDetailScreen()
            }
        }

    }

        }

