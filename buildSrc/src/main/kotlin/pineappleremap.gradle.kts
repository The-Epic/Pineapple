plugins {
    id("java")
    id("io.github.patrick.remapper")
}

dependencies {
    compileOnly(project(":pineapple-nms:api"))
}

tasks.jar {
    finalizedBy(tasks.remap)
}
