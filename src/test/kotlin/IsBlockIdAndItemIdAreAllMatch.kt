import io.github.bruce0203.downloadAndGetRegistries
import io.kotest.core.spec.style.StringSpec

class IsBlockIdAndItemIdAreAllMatch : StringSpec({
    "is block id and item id are match" {
        val registry = downloadAndGetRegistries()
    }
})