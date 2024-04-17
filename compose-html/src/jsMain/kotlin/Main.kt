import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.PreComposeWindowHolder
import moe.tlaster.precompose.lifecycle.LocalLifecycleOwner
import moe.tlaster.precompose.stateholder.LocalStateHolder
import moe.tlaster.precompose.ui.LocalBackDispatcherOwner
import org.jetbrains.compose.web.renderComposable


fun main() {
    renderComposable(rootElementId = "root") {
        val holder = remember { PreComposeWindowHolder() }
        CompositionLocalProvider(
            LocalLifecycleOwner provides holder,
            LocalStateHolder provides holder.stateHolder,
            LocalBackDispatcherOwner provides holder
        ) {
            App()
        }
    }
}

val client = HttpClient(Js) {
    install(ContentNegotiation) {
        json(
            Json {
                isLenient = true
            }
        )
    }
    defaultRequest {
        url {
            protocol = URLProtocol.HTTP
            host = "localhost"
            port = 8081
        }
    }
}
