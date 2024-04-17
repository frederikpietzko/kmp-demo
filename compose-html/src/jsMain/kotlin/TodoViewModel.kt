import de.iits.techtalk.kmp.dto.CreateTodoRequest
import de.iits.techtalk.kmp.dto.TodoDto
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.promise
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class TodoViewModel : ViewModel() {
    private val _todos = MutableStateFlow<List<TodoDto>>(listOf())
    val todos = _todos.asStateFlow()

    init {
        viewModelScope.promise {
            val res = client.get("/todos")
            val body: List<TodoDto> = res.body()
            _todos.update { body }
        }
    }

    fun remove(todo: TodoDto) {
        viewModelScope.promise {
            val res = client.delete("/todo/${todo.id}")
            if (res.status.isSuccess()) {
                _todos.update { prev ->
                    prev.filter { it.id != todo.id }
                }
            }
        }
    }

    fun add(task: String) {
        viewModelScope.promise {
            val res = client.post("/todos") {
                contentType(ContentType.Application.Json)
                setBody(CreateTodoRequest(task))
            }
            val todo: TodoDto = res.body()
            _todos.update { it + todo }
        }
    }

    fun done(todo: TodoDto) {
        viewModelScope.promise {
            val res = client.patch("/todo/${todo.id}/done")
            val todo: TodoDto = res.body()
            _todos.update { prev -> prev.map { if (it.id == todo.id) todo else it } }
        }
    }
}