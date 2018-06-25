package frontend.fabricstore.resource

import frontend.fabricstore.model.Template
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(path="templates", collectionResourceRel="templates")
interface TemplateRepository : JpaRepository<Template, Long> {
}