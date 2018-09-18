package io.pivotal.pal.tracker

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EnvController constructor(@Value("\${PORT:NOT SET}") port: String,
                                @Value("\${MEMORY_LIMIT:NOT SET}") memoryLimit: String,
                                @Value("\${CF_INSTANCE_INDEX:NOT SET}") cfInstanceIndex: String,
                                @Value("\${CF_INSTANCE_ADDR:NOT SET}") cfInstanceAddr: String) {

    var port: String? = null

    var memoryLimit: String? = null

    var cfInstanceIndex: String? = null

    var cfInstanceAddr: String? = null


    init {
        this.port = port
        this.memoryLimit = memoryLimit
        this.cfInstanceIndex = cfInstanceIndex
        this.cfInstanceAddr = cfInstanceAddr
    }

    @GetMapping("/env")
    fun getEnv(): Map<String, String?> {
        var out: HashMap<String, String?> = HashMap()
        out.put("PORT", port)
        out.put("MEMORY_LIMIT", memoryLimit)
        out.put("CF_INSTANCE_INDEX", cfInstanceIndex)
        out.put("CF_INSTANCE_ADDR", cfInstanceAddr)
        return out
    }
}

