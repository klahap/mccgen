package io.github.klahap.mccgen

import io.github.klahap.mccgen.models.MccSource
import io.github.klahap.mccgen.services.MccCodeGenService
import java.nio.file.Path
import kotlin.io.path.*
import kotlin.system.exitProcess


fun printHelper(exitCode: Int): Nothing {
    println("invalid args")
    exitProcess(exitCode)
}

fun main(args: Array<String>) {
    val source: MccSource
    val output: Path
    val packageName: String
    args.also { if (it.size % 2 != 0) printHelper(1) }
        .toList().chunked(2).associate { it[0] to it[1] }
        .let { argDict ->
            output = argDict["--output"]?.let { Path(it) } ?: printHelper(1)
            packageName = argDict["--packageName"] ?: printHelper(1)
            source = argDict["--source"]?.let { MccSource.valueOf(it) } ?: printHelper(1)
        }

    MccCodeGenService().generate(
        source = source,
        output = output,
        packageName = packageName,
    )
    exitProcess(0)
}
