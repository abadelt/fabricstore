package frontend.fabricstore

import frontend.fabricstore.model.Template
import frontend.fabricstore.model.TemplateCollection
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.hateoas.hal.HalLinkDiscoverer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TemplateEndpointTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    val templateA = Template(
        null,
        "templateA.html",
        "<html><body>1</body></html>",
        "desc 1",
        1
    )
    var templateA_URI: String? = null
    val templateB = Template(
        null,
        "templateB.html",
        "<html><body>2</body></html>",
        "desc 1",
        1
    )
    var templateB_URI: String? = null

    @org.junit.jupiter.api.BeforeAll
    fun setUp() {
        var result: ResponseEntity<String> = testRestTemplate.postForEntity("/templates", templateA, String::class.java)
        assertEquals(HttpStatus.CREATED, result.statusCode)
        val discoverer = HalLinkDiscoverer()
        templateA_URI = discoverer.findLinkWithRel("self", result.body).href

        result = testRestTemplate.postForEntity("/templates", templateB, String::class.java)
        assertEquals(HttpStatus.CREATED, result.statusCode)
        templateB_URI = discoverer.findLinkWithRel("self", result.body).href
    }

    @Test
    fun testTemplateEndpointGetAll() {
        val result: ResponseEntity<TemplateCollection> = testRestTemplate.getForEntity("/templates", TemplateCollection::class.java)
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(2, result.body._embedded?.templates?.size)
    }

    fun xtestTemplateEndpointGetSingleProject() {
        val result: ResponseEntity<String> = testRestTemplate.getForEntity(templateA_URI, String::class.java)
        assertNotNull(result)
    }


    @Test
    fun testTemplateEndpointGetSingleProject() {
        val result: ResponseEntity<Template> = testRestTemplate.getForEntity(templateA_URI, Template::class.java)
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(templateA.content, result.body.content)
        assertEquals(templateA.fileName, result.body.fileName)
    }

}