package frontend.fabricstore.resource

import frontend.fabricstore.model.Template
import org.springframework.data.mongodb.repository.MongoRepository

interface TemplateRepository : MongoRepository<Template, String> {

}
