package frontend.fabricstore

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

internal class ResponseHeaderFilterTest {

    var cut = ResponseHeaderFilter()

    @org.junit.jupiter.api.Test
    fun doFilter() {
        val req = MockHttpServletRequest()
        req.setServletPath("/admin.html")
        val res = MockHttpServletResponse()
        val chain = MockFilterChain()
        cut.doFilter(req, res, chain)
        assertNotNull(res.getHeader("Link"))
    }
}