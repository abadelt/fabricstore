package frontend.fabricstore.model

import org.springframework.data.annotation.Id
import java.util.*

data class Template(
    @Id val id: String?,
    val fileName: String,
    var content: String = "<html><!-- empty --></html>",
    val description: String = "",
    val version: Int = 1,
    val created: Date = Date(),
    var mimeType: String = "text/html",
    @Transient val _links: TemplateLinks? = null
)