package frontend.fabricstore.model

import org.springframework.hateoas.Link

data class TemplateLinks (
    val self: Link?,
    val template: Link?,
    val templates: Link?
)