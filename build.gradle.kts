plugins {
    `java-library`

    id("com.github.johnrengelman.shadow") version "8.1.1" // Shades and relocates dependencies, See https://imperceptiblethoughts.com/shadow/introduction/
    id("xyz.jpenilla.run-paper") version "2.1.0" // Adds runServer and runMojangMappedServer tasks for testing
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0" // Automatic plugin.yml generation
}

group = "com.github.Xavbeat03.Tariffs"
version = "0.0.1"
description = "Plugin that allows players to create Tariffs and Embargo's to other players."

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17)) // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
}

repositories {
    mavenCentral()

    maven("https://papermc.io/repo/repository/maven-public/")

    maven("https://repo.glaremasters.me/repository/towny/") {
        content {
            includeGroup("com.palmergames.bukkit.towny")
        }
    }

    maven("https://jitpack.io/") {
        content {
            includeGroup("com.github.milkdrinkers")
            includeGroup("com.github.TownyAdvanced")
        }
    }

    maven("https://repo.codemc.org/repository/maven-public/") {
        content { includeGroup("dev.jorel") }
    }

    maven("https://repo.minebench.de/"){
        content{includeGroup("com.acrobot.chestshop")}
    }

    maven("https://github.com/"){
        content{includeGroup("dev.triumphteam")}
    }


}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")

    compileOnly("org.jetbrains:annotations:24.0.1")

    implementation("com.github.milkdrinkers:simplixstorage:3.2.7")
    implementation("com.github.milkdrinkers:colorparser:1.0.7")

    implementation("dev.jorel:commandapi-bukkit-shade:9.0.3")

    implementation("dev.triumphteam:triumph-gui:3.1.5")

    compileOnly("dev.jorel:commandapi-annotations:9.0.3")
    annotationProcessor("dev.jorel:commandapi-annotations:9.0.3")

    compileOnly("com.acrobot.chestshop:chestshop:3.12")

    compileOnly("com.palmergames.bukkit.towny:towny:0.99.5.0")

    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation("org.mariadb.jdbc:mariadb-java-client:3.1.4")
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(17)
        options.compilerArgs.add("-Xlint:-deprecation")
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    shadowJar {
        archiveBaseName.set(project.name)
        archiveClassifier.set("")

        // Shadow classes
        // helper function to relocate a package into our package
        fun reloc(originPkg: String, targetPkg: String) = relocate(originPkg, "${project.group}.${targetPkg}")

        reloc("de.leonhard.storage", "storageapi")
        reloc("com.github.milkdrinkers.colorparser", "colorparser")
        reloc("dev.jorel.commandapi", "commandapi")
    }

    runServer {
        // Configure the Minecraft version for our task.
        minecraftVersion("1.20.1")
    }
}

bukkit {
    // Plugin main class (required)
    main = "${project.group}.Main"

    // Plugin Information
    name = "${project.name}"
    prefix = "${project.name}"
    version = "${project.version}"
    description = "${project.description}"
    authors = listOf("AuthorName")
    contributors = listOf("ContributorName")
    apiVersion = "1.19"

    // Misc properties
    load = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder.POSTWORLD // STARTUP or POSTWORLD
    depend = listOf("Towny", "Chestshop")
    softDepend = listOf()
}