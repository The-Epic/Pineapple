plugins {
    id("java")
    id("checkstyle")
}

checkstyle {
    toolVersion = "10.12.5"
    configFile = file("config/checkstyle/checkstyle.xml")
    sourceSets = mutableListOf(project.sourceSets.main.get())
}

