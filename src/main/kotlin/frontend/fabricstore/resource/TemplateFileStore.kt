package frontend.fabricstore.resource

import frontend.fabricstore.model.Template
import org.springframework.content.commons.repository.ContentStore
import org.springframework.content.rest.StoreRestResource

@StoreRestResource
interface TemplateFileStore : ContentStore<Template, String> {
}