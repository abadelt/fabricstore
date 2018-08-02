package frontend.fabricstore.resource

import frontend.fabricstore.model.Template
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface TemplateRepositoryCustom {

    fun save(entity: Template): Template
    fun findAll(): Iterable<Template>
    fun findAll(pageable: Pageable): Page<Template>
    fun findByFileNameAndVersion(fileName: String, version: Integer): Template?

}