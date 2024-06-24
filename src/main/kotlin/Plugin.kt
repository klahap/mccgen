package io.github.klahap.mccgen

import io.github.klahap.mccgen.models.MccSource
import io.github.klahap.mccgen.services.MccCodeGenService
import org.gradle.api.Project
import java.nio.file.Path
import kotlin.io.path.*


class Plugin : org.gradle.api.Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create(
            "mccCodeGenerator",
            MccCodeGeneratorExtension::class.java,
        )

        project.task("generateMccCodes") {
            doLast {
                val config = extension.toValid()
                MccCodeGenService().generate(
                    packageName = config.packageName,
                    output = config.output,
                    source = config.source,
                )
            }
        }
        project.task("cleanMccCodes") {
            doLast {
                @OptIn(ExperimentalPathApi::class) extension.toValid().output.deleteRecursively()
            }
        }
    }
}

open class MccCodeGeneratorExtension {
    var source: MccSource? = null
    var output: String? = null
    var packageName: String? = null

    data class Valid(
        val source: MccSource,
        val output: Path,
        val packageName: String,
    )
}


fun MccCodeGeneratorExtension.toValid() = MccCodeGeneratorExtension.Valid(
    source = source ?: throw Exception("no mcc source defined"),
    output = output?.let { Path(it) } ?: throw Exception("no output path defined"),
    packageName = packageName ?: throw Exception("no output package name defined"),
)
