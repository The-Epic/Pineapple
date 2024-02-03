rootProject.name = "Pineapple"

gradle.rootProject {
    this.version = "1.0.0-SNAPSHOT"
    this.group = "sh.miles"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/central") }
        maven { url = uri("https://libraries.minecraft.net/") }
        maven { url = uri("https://repo.jeff-media.com/public") }
        maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
        maven { url = uri("https://maven.miles.sh/libraries") }
    }
}

findProject(":pineapple-nms:api")?.name = "api"
findProject(":pineapple-nms:spigot-v1_20_R3")?.name = "spigot-v1_20_R3"

include("pineapple-utils", "pineapple-nms", "pineapple-nms:api", "pineapple-core")
include("pineapple-nms:spigot-v1_20_R3")
