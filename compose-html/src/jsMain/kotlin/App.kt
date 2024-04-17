import androidx.compose.runtime.*
import de.iits.techtalk.kmp.dto.TodoDto
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.web.attributes.onSubmit
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.events.SyntheticInputEvent
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement

@Composable
fun App() {
    Div(attrs = {
        classes(
            "bg-black",
            "w-screen",
            "h-screen",
            "relative",
            "text-amber-500",
            "p-5"
        )
    }) {
        Div {
            H1(attrs = {
                classes(
                    "text-3xl",
                    "text-center",
                    "mb-12"
                )
            }) {
                Text("My Todo List")
            }
        }
        TodoList()
    }
}

@Composable
fun TodoList(
    todoViewModel: TodoViewModel = viewModel(TodoViewModel::class) { TodoViewModel() }
) {
    val todos by todoViewModel.todos.collectAsState()

    Div(attrs = {
        classes(
            "flex",
            "flex-col",
            "gap-2"
        )
    }) {
        for (todo in todos) {
            TodoItem(
                todo,
                onDelete = {
                    todoViewModel.remove(it)
                },
                onDone = {
                    todoViewModel.done(it)
                }
            )
        }
        AddTodoForm(onTodoAdded = {
            todoViewModel.add(it)
        })
    }
}

@Composable
fun AddTodoForm(onTodoAdded: (task: String) -> Unit) {
    var inputValue by remember { mutableStateOf("") }
    Box {
        Form(attrs = {
            classes("w-full")
            onSubmit {
                it.preventDefault()
                onTodoAdded(inputValue)
                inputValue = ""
            }
        }) {
            TextField(value = inputValue, placeholder = "New Todo") {
                inputValue = it.value
            }
        }
    }
}

@Composable
fun TextField(
    value: String,
    placeholder: String? = null,
    listener: (SyntheticInputEvent<String, HTMLInputElement>) -> Unit
) {
    TextInput(value) {
        classes(
            "w-full",
            "bg-transparent",
            "focus:outline-none",
            "border-b",
            "focus:border-amber-600"
        )
        placeholder?.let { placeholder(it) }
        onInput(listener)
    }
}

@Composable
fun Box(block: ContentBuilder<HTMLDivElement>? = null) =
    Div(attrs = {
        classes(
            "flex",
            "justify-between",
            "items-center",
            "bg-gray-500",
            "text-amber-500",
            "px-3",
            "py-2",
            "rounded",
        )
    }, block)


@Composable
fun TodoItem(todo: TodoDto, onDelete: (todo: TodoDto) -> Unit, onDone: (todo: TodoDto) -> Unit) =
    Box {
        Span(attrs = {
            if (todo.done) {
                classes("line-through")
            }
        }) {
            Text(todo.task)
        }
        Div(attrs = {
            classes("flex", "gap-1")
        }) {
            if (todo.done) {
                Button(attrs = {
                    onClick {
                        onDelete(todo)
                    }
                }) {
                    TrashCan()
                }
            }
            Button(attrs = {
                onClick {
                    onDone(todo)
                }
            }) {
                if (!todo.done) Square() else SquareCheck()
            }
        }
    }
