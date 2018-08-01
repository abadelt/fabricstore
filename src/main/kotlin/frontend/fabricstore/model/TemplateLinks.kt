package frontend.fabricstore.model

import org.springframework.hateoas.Link
import javax.persistence.Embeddable

data class TemplateLinks (
    val self: Link?,
    val template: Link?,
    val templates: Link?
)