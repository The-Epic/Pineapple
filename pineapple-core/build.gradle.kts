plugins {
    id("pineapplecheckstyle")
}

java {
    withJavadocJar()
}

val compileOnlyAndTest = configurations.create("compileOnlyAndTest")
configurations {
    compileOnlyAndTest.setTransitive(false)
    testImplementation.get().extendsFrom(compileOnlyAndTest)
    compileOnly.get().extendsFrom(compileOnlyAndTest)
}

dependencies {
    testImplementation("com.github.seeseemelk:MockBukkit-v1.20:3.9.0")
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")

    implementation("sh.miles.pineapplechat:pineapplechat-core:1.0.0-SNAPSHOT") { isChanging = true }
    implementation("sh.miles.pineapplechat:pineapplechat-bungee:1.0.0-SNAPSHOT") { isChanging = true }
    implementation("com.jeff_media:MorePersistentDataTypes:2.4.0")

    compileOnly(project(":pineapple-nms:api"))
    compileOnlyAndTest(project(":pineapple-utils"))
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}
