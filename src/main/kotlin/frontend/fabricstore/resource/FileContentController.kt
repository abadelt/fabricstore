package frontend.fabricstore.resource

import frontend.fabricstore.model.Template
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
class FileContentController {

    private var mongoTemplate: MongoTemplate? = null

    @Autowired
    fun TemplateRepositoryImpl(mongoTemplate: MongoTemplate) {
        this.mongoTemplate = mongoTemplate
    }

    @PutMapping("/templates/{id}/content", consumes = arrayOf("multipart/form-data"))
    fun uploadFileContent(@PathVariable("id") id: String, @RequestParam("content") content: MultipartFile): ResponseEntity<Template> {
        val query = Query()
        query.addCriteria(Criteria.where("id").`is`(id))
        val template : Template = mongoTemplate!!.find<Template>(query, Template::class.java, "template").first()
        val inputAsString = content.inputStream.bufferedReader().use { it.readText() }  // defaults to UTF-8
        template!!.content = inputAsString
        mongoTemplate!!.save(template)
        template.content = "-- stripped -- GET /templates/{id}/content to see template content." // for response
        val location = ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(location)).body(template)
    }

    @GetMapping("/templates/{id}/content", produces = arrayOf("text/plain"))
    fun getFileContent(@PathVariable("id") id: String): ResponseEntity<String> {
        val query = Query()
        query.addCriteria(Criteria.where("id").`is`(id))
        val template : Template = mongoTemplate!!.find<Template>(query, Template::class.java, "template").first()
        return ResponseEntity.status(HttpStatus.OK).body(template.content)
    }
}