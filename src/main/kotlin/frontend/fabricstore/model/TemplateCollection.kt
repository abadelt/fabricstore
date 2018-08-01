package frontend.fabricstore.model

data class TemplateCollection(
    val _embedded: TemplatesWrapper?,
    val _links: TemplatesLinks?
) {
}

data class TemplatesWrapper(
    val templates: Array<Template>
)