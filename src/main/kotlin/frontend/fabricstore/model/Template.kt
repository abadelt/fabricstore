package frontend.fabricstore.model

import org.springframework.data.annotation.Id
import java.util.*

data class Template(
    @Id val id: String?,
    val fileName: String,
    var content: String = "<html><!-- empty --></html>",
    val description: String = "",
    var version: Int = 1,
    var latest: Boolean = false,
    val created: Date = Date(),
    var mimeType: String = "text/html",
    @Transient val _links: TemplateLinks? = null
)