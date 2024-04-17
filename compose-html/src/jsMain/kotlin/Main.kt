import androidx.compose.runtime.*
import org.jetbrains.compose.web.attributes.onSubmit
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.events.SyntheticInputEvent
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import kotlin.random.Random

fun main() {
    renderComposable(rootElementId = "root") {
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
}


data class Todo(
    val id: String,
    val name: String,
    var done: Boolean,
)

@Composable
fun TodoList() {
    val todos = remember {
        mutableStateListOf(
            Todo("1", "Hello", true),
            Todo("2", "World", false),
        )
    }


    Div(attrs = {
        classes(
            "flex",
            "flex-col",
            "gap-2"
        )
    }) {
        for (i in 0..<todos.size) {
            val todo = todos[i]
            TodoItem(
                todo,
                onDelete = {
                    todos.remove(it)
                },
                onDone = {
                    todos[i] = it.copy(done = !it.done)
                }
            )
        }
        AddTodoForm(onTodoAdded = { todos.add(it) })
    }
}

@Composable
fun AddTodoForm(onTodoAdded: (todo: Todo) -> Unit) {
    var inputValue by remember { mutableStateOf("") }
    Box {
        Form(attrs = {
            classes("w-full")
            onSubmit {
                it.preventDefault()
                onTodoAdded(
                    Todo(
                        Random(1).nextInt().toString(),
                        name = inputValue,
                        done = false
                    )
                )
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
fun TextField(value: String, placeholder: String? = null, listener: (SyntheticInputEvent<String, HTMLInputElement>) -> Unit) {
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
fun TodoItem(todo: Todo, onDelete: (todo: Todo) -> Unit, onDone: (todo: Todo) -> Unit) =
    Box {
        Span(attrs = {
            if (todo.done) {
                classes("line-through")
            }
        }) {
            Text(todo.name)
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
