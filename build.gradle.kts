plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version ("1.6.10")
  id("com.github.johnrengelman.shadow") version ("7.1.2")
  id("maven-publish")
}

group = "com.asledgehammer"
version = "1.0_0"

apply(plugin = "java")
apply(plugin = "kotlin")

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(15))
  }
}

repositories {
  mavenCentral()
  mavenLocal()
}

dependencies {
  implementation("org.yaml:snakeyaml:1.29")
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      groupId = project.group.toString()
      artifactId = project.name
      version = project.version.toString()
      from(components["java"])
    }
  }
}

tasks.compileJava {
  options.encoding = "UTF-8"
}

tasks.compileKotlin {
  kotlinOptions {
    freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
  }
}

tasks.jar {
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
  manifest {
    attributes["Implementation-Title"] = project.name
    attributes["Implementation-Version"] = project.version
    attributes["Main-Class"] = "com.asledgehammer.woodglue.Main"
    attributes["Premain-Class"] = "com.asledgehammer.woodglue.Agent"
    attributes["Can-Redefine-Classes"] = true
    attributes["Can-Set-Native-Method-Prefix"] = true
  }
}

tasks.shadowJar {
  archiveBaseName.set(project.name)
  archiveVersion.set(project.version.toString())
  archiveClassifier.set("")
}
