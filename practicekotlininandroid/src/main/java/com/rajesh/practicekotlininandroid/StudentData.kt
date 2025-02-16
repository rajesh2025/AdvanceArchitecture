package com.rajesh.practicekotlininandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

data class StudentData(
    val name: String = "",
    val marks: Int = 0,
    val college: String = "",
    val address: String = "",
    val gift: String = ""
)

class StudentViewModel : ViewModel() {

    private val _studentState = MutableStateFlow<StudentData?>(null)
    val studentState: StateFlow<StudentData?> = _studentState

    fun fetchStudentData() {
        viewModelScope.launch {
             supervisorScope {  // Ensures independent task failures don't crash all tasks
                 val job = launch {
                     val studentId = async { fetchStudentId() }  // Task 1

                     val studentDetails = async {
                         try {
                             fetchStudentDetails(studentId.await())  // Task 2 (Dependent on Task 1)
                         } catch (e: Exception) {
                             StudentData(name = "Unknown", marks = 0)  // Fallback
                         }
                     }

                     val collegeDetails = async { fetchCollegeDetails() }  // Task 3 (Independent)

                     val studentAddress = async {
                         try {
                             fetchStudentAddress(studentId.await())  // Task 4 (Dependent on Task 1)
                         } catch (e: Exception) {
                             "Address Not Available"  // Fallback
                         }
                     }

                     val randomGift = async { generateRandomGift() }  // Task 5 (Independent)

                     // Wait for all tasks to complete before updating UI
                     _studentState.value = StudentData(
                         name = studentDetails.await().name,
                         marks = studentDetails.await().marks,
                         college = collegeDetails.await(),
                         address = studentAddress.await(),
                         gift = randomGift.await()
                     )
                 }
                 job.join()
             }
            // Wait until everything is completed
        }
    }

    private suspend fun fetchStudentId(): Int {
        delay(Random.nextLong(1000, 3000))  // Simulating network delay
        return Random.nextInt(1000, 9999)  // Returning a random student ID
    }

    private suspend fun fetchStudentDetails(studentId: Int): StudentData {
        delay(Random.nextLong(1000, 3000))
        if (Random.nextBoolean()) throw Exception("Failed to fetch student details")
        return StudentData(name = "John Doe", marks = 85)
    }

    private suspend fun fetchCollegeDetails(): String {
        delay(Random.nextLong(1000, 3000))
        return "XYZ University"
    }

    private suspend fun fetchStudentAddress(studentId: Int): String {
        delay(Random.nextLong(1000, 3000))
        if (Random.nextBoolean()) throw Exception("Failed to fetch student address")
        return "123 Main St, Springfield"
    }

    private suspend fun generateRandomGift(): String {
        delay(Random.nextLong(500, 2000))
        val gifts = listOf("Laptop", "Smartphone", "Tablet", "Gift Card")
        return gifts.random()
    }
}
