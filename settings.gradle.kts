pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net") {
            name = "Fabric"
        }
        maven("https://maven.neoforged.net/releases") {
            name = "NeoForge"
        }
        maven("https://repo.terradevelopment.net/repository/maven-releases/") {
            // https://maven.galacticraft.net/repository/maven-releases
            name = "Galacticraft"
            content {
                includeGroup("dev.galacticraft")
                includeGroup("dev.galacticraft.mojarn")
            }
        }
    }
}

rootProject.name = "DynamicDimensions"

include("common")
include("fabric")
include("neoforge")
project(":common").name = "common"
project(":fabric").name = "fabric"
project(":neoforge").name = "neoforge"
