package io.github.klahap.mccgen.services

import com.squareup.kotlinpoet.ClassName
import io.github.klahap.mccgen.models.MccCategory
import io.github.klahap.mccgen.models.MccData
import io.github.klahap.mccgen.models.MccSource
import io.github.klahap.mccgen.util.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.writeText


class MccCodeGenService {
    fun generate(source: MccSource, packageName: String, output: Path) = when (source) {
        MccSource.STRIPE -> generateStripeMcc(packageName = packageName, output = output)
    }

    private fun generateStripeMcc(packageName: String, output: Path) {
        val doc = Jsoup.connect("https://docs.stripe.com/connect/setting-mcc").get()

        val rows = doc.select("table tbody").first()!!.select("tr")
            .map { row ->
                MccData(
                    name = (row.childNode(0).childNodes().last()!! as Element).text(),
                    label = (row.childNode(0).childNode(0) as TextNode).text(),
                    id = (row.children().last()!!.childNode(0) as TextNode).text().toInt(),
                )
            }

        fileBuilder(
            packageName = packageName,
            fileName = "Mcc.kt",
        ) {
            addEnum("MccCategory") {
                primaryConstructor {
                    addPrimaryConstructorProperty("label", String::class) {}
                }
                MccCategory.values().forEach { category ->
                    addEnumConstant(category.name) {
                        addSuperclassConstructorParameter("%S", category.label)
                    }
                }
            }
            addEnum("Mcc") {
                primaryConstructor {
                    addPrimaryConstructorProperty("value", String::class) {}
                    addPrimaryConstructorProperty("label", String::class) {}
                    addPrimaryConstructorProperty("code", Int::class) {}
                    addPrimaryConstructorProperty("category", ClassName(packageName, "MccCategory")) {}
                }
                rows.forEach { mcc ->
                    val category = MccCategory.values().first { mcc.id in it.range }
                    addEnumConstant(mcc.name.toCamelCase(capitalized = true)) {
                        addSuperclassConstructorParameter("%S", mcc.name)
                        addSuperclassConstructorParameter("%S", mcc.label)
                        addSuperclassConstructorParameter("${mcc.id}")
                        addSuperclassConstructorParameter("MccCategory.${category.name}")
                    }
                }
            }
        }.let {
            output.resolve("Mcc.kt")
                .apply { parent.createDirectories() }
                .writeText(it.toString())
        }
    }
}