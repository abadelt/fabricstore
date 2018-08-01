package frontend.fabricstore.model

import org.springframework.hateoas.Link
import javax.persistence.Embeddable

data class TemplatesLinks (
    val self: Link?,
    val profile: Link?
)