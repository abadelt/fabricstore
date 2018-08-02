package frontend.fabricstore.http

import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter




@Component
class ResponseHeaderFilter : Filter {

    val LINK_HEADER_VALUE : String = """`<http://localhost:8082/styles.css>;rel="stylesheet",<http://localhost:8082/js/bundle.js>;rel="fragment-script"`"""
    val ADMIN_PAGE_PATH : String = "/admin.html"

    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain?) {
        val request: HttpServletRequest = req as HttpServletRequest
        if (request.servletPath.endsWith(ADMIN_PAGE_PATH)) {
            val response: HttpServletResponse = res as HttpServletResponse
            response.setHeader("Link", LINK_HEADER_VALUE)
        }
        chain?.doFilter(req, res)
    }

    override fun init(filterConfig: FilterConfig?) {
    }

    override fun destroy() {
    }

}

@Component
class CORSHeaderAdapter : RepositoryRestConfigurerAdapter() {

    override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration) {

        config.corsRegistry.addMapping("/templates.js")
            .allowedOrigins("http://localhost:8080", "http://localhost:9090")
            .allowedMethods("OPTIONS", "GET")

        config.corsRegistry.addMapping("/templates/**")
            .allowedOrigins("http://localhost:8080", "http://localhost:9090")
            .allowedMethods("OPTIONS", "GET", "PUT", "POST")
    }
}