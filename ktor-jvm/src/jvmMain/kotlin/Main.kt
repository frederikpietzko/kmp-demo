import de.iits.techtalk.kmp.module
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*


fun main() {
    embeddedServer(
        CIO,
        port = 8081,
        host = "0.0.0.0",
        module = Application::module,
    ).start(wait = true)
}

