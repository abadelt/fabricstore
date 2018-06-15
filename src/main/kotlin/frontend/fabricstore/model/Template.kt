package frontend.fabricstore.model

import org.springframework.content.commons.annotations.ContentId
import org.springframework.content.commons.annotations.MimeType
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Template(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long,
    @ContentId val contentId: String = "xyz.json",
    val name: String,
    val content: String,
    val created: Date = Date(),
    @MimeType var mimeType: String = "text/plain"
) {
}