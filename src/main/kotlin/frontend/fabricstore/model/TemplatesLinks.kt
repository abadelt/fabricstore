package frontend.fabricstore.model

import org.springframework.hateoas.Link

data class TemplatesLinks (
    val self: Link?,
    val profile: Link?
)