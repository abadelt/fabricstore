package frontend.fabricstore.resource

import frontend.fabricstore.model.Template
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TemplateRepository : MongoRepository<Template, String>, TemplateRepositoryCustom {

}
