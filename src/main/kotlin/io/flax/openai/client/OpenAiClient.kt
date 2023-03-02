package io.flax.openai.client

import com.aallam.openai.api.completion.completionRequest
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import io.ktor.network.sockets.*
import kotlinx.coroutines.runBlocking

object OpenAiClient {
    private val key = System.getenv("openai-key")
    private val openAi = OpenAI(key)
    fun explainCode(code: String, language: String) = try {
        runBlocking {
            openAi.completion(
                completionRequest {
                    model = ModelId("code-cushman-001")
                    maxTokens = 64
                    temperature = 0.0
                    topP = 1.0
                    frequencyPenalty = 0.0
                    presencePenalty = 0.0
                    prompt = """
                        $code
                        
                        // Explanation of the above $language code in human readable format
                    """.trimIndent()
                    echo = false
                    stop = listOf("\"\"\"")
                },
            ).choices.map { it.text }
        }
    } catch (e: SocketTimeoutException) {
        listOf("Open Ai timed out, try again")
    }

    fun generateCode(description: String) = try {
        runBlocking {
            openAi.completion(
                completionRequest {
                    model = ModelId("code-davinci-002")
                    maxTokens = 256
                    temperature = 0.0
                    topP = 1.0
                    frequencyPenalty = 0.0
                    presencePenalty = 0.0
                    prompt = """
                        $description
                    """.trimIndent()
                    echo = false
                    stop = listOf("\"\"\"")
                },
            ).choices.firstOrNull()?.text ?: "I have nothing..."
        }
    } catch (e: SocketTimeoutException) {
        "Open Ai timed out, try again"
    }
}
