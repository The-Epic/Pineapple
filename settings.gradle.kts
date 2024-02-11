rootProject.name = "Pineapple"

gradle.rootProject {
    this.version = "1.0.0-SNAPSHOT"
    this.group = "sh.miles"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/central")
        maven("https://libraries.minecraft.net/")
        maven("https://repo.jeff-media.com/public")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://maven.miles.sh/libraries")
        maven("https://repo.codemc.io/repository/nms/")
    }
}

findProject(":pineapple-nms:api")?.name = "api"
findProject(":pineapple-nms:spigot-v1_20_R3")?.name = "spigot-v1_20_R3"
findProject(":pineapple-nms:spigot-v1_20_R2")?.name = "spigot-v1_20_R2"
findProject(":pineapple-nms:spigot-v1_20_R1")?.name = "spigot-v1_20_R1"

include("pineapple-utils", "pineapple-nms", "pineapple-nms:api", "pineapple-core")
include("pineapple-nms:spigot-v1_20_R3")
include("pineapple-nms:spigot-v1_20_R2")
include("pineapple-nms:spigot-v1_20_R1")
