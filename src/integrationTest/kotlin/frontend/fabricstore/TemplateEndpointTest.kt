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

    val timeMillis = System.currentTimeMillis() // to make template names unique

    val templateA = Template(
        null,
        "templateA-" + timeMillis + ".html",
        "<html><body>A</body></html>",
        "desc A"
    )
    var templateA_URI: String? = null
    val templateB = Template(
        null,
        "templateB-" + timeMillis + ".html",
        "<html><body>B</body></html>",
        "desc B"
    )
    var templateB_URI: String? = null
    val templateB2 = Template(
        null,
        "templateB-" + timeMillis + ".html",
        "<html><body>B 2</body></html>",
        "desc B 2"
    )
    var templateB2_URI: String? = null

    var totalExpectedCount: Int = 0

    @org.junit.jupiter.api.BeforeAll
    fun setUp() {
        // Do not delete existing entries - just record the existing count for later assertions
        var existing: ResponseEntity<TemplateCollection> = testRestTemplate.getForEntity("/templates/?page=0&size=10000", TemplateCollection::class.java)
        totalExpectedCount = existing.body._embedded?.templates?.size ?: 0

        var result: ResponseEntity<String> = testRestTemplate.postForEntity("/templates", templateA, String::class.java)
        assertEquals(HttpStatus.CREATED, result.statusCode)
        val discoverer = HalLinkDiscoverer()
        templateA_URI = discoverer.findLinkWithRel("self", result.body).href

        result = testRestTemplate.postForEntity("/templates", templateB, String::class.java)
        assertEquals(HttpStatus.CREATED, result.statusCode)
        templateB_URI = discoverer.findLinkWithRel("self", result.body).href

        result = testRestTemplate.postForEntity("/templates", templateB2, String::class.java)
        assertEquals(HttpStatus.CREATED, result.statusCode)
        templateB2_URI = discoverer.findLinkWithRel("self", result.body).href

        totalExpectedCount += 2 // increase only by 2, as B and B2 are only different versions of same template
    }

    @Test
    fun testTemplateEndpointGetAll() {
        val result: ResponseEntity<TemplateCollection> = testRestTemplate.getForEntity("/templates/?page=0&size=10000", TemplateCollection::class.java)
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(totalExpectedCount, result.body._embedded?.templates?.size)
    }

    fun xtestTemplateEndpointGetSingleProject() {
        val result: ResponseEntity<String> = testRestTemplate.getForEntity(templateA_URI, String::class.java)
        assertNotNull(result)
    }


    @Test
    fun testTemplateEndpointGetSingleProject() {
        val result: ResponseEntity<String> = testRestTemplate.getForEntity(templateA_URI, String::class.java)
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result.statusCode)
        // assertEquals(templateA.content, result.body.content)
        //assertEquals(templateA.fileName, result.body.fileName)
    }

}