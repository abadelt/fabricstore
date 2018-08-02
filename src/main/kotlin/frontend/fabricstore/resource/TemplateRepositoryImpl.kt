package frontend.fabricstore.resource

import frontend.fabricstore.model.Template
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.*
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@Repository
class TemplateRepositoryImpl(@Autowired val mongoTemplate: MongoTemplate) : TemplateRepositoryCustom {

    private fun findAllLatest(): List<Template> {
        val query = Query()
        query.addCriteria(Criteria.where("latest").`is`(true))
        val list: List<Template> = mongoTemplate.find(query, Template::class.java)
        return list
    }

    override fun findAll(): Iterable<Template> {
        return findAllLatest()
    }

    override fun findAll(pageable: Pageable): Page<Template> {
        val list = findAllLatest()
        val total = list.size
        var start = pageable.pageNumber * pageable.pageSize
        if (total < start + pageable.pageSize) {
            start = Integer.max(total - pageable.pageSize, 0)
        }
        var end = start + pageable.pageSize
        if (end > total) {
            end = total
        }
        val newPage = start / pageable.pageSize
        val newPageable = PageRequest.of(newPage, pageable.pageSize)

        val page = PageImpl<Template>(list.subList(start, end), newPageable, total.toLong())
        return page
    }

    override fun findByFileNameAndVersion(fileName: String, version: Integer): Template? {
        val query = Query()
        query.addCriteria(Criteria.where("fileName").`is`(fileName).and("version").`is`(version))
        val template = mongoTemplate.findOne(query, Template::class.java)
        return template
    }

    override fun save(entity: Template): Template {
        val query = Query()
        query.addCriteria(Criteria.where("fileName").`is`(entity.fileName))
        val versionsInDB : List<Template> = mongoTemplate!!.find<Template>(query, Template::class.java, "template").sortedByDescending({it.version})
        entity.version = 1
        if (versionsInDB.size > 0) {
            val latestVersionInDB = versionsInDB.first()
            latestVersionInDB.latest = false
            mongoTemplate.save(latestVersionInDB)
            entity.version = latestVersionInDB.version.inc()
        }
        entity.latest = true
        mongoTemplate.insert(entity)
        val location = ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        return entity
    }

}
