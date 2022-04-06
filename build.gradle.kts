buildscript {
    apply(from = "src/main/resources/include/repositories.gradle", to = this)

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
    }
}

plugins {
    kotlin("jvm") version "1.6.20"
    java
    id("tech.harmonysoft.oss.custom-gradle-dist-plugin") version "1.8"
    `maven-publish`
}

repositories {
    mavenCentral()
}

val baseVersion = "7.4.2"
var zerocodeVersion = "0.0.6"
val distributionName = "springboot-kotlin-gradle-distribution"
val customGradleDistFilename = "gradle-$baseVersion-$distributionName-$zerocodeVersion"

gradleDist {
    gradleVersion = baseVersion
    customDistributionVersion = zerocodeVersion
    customDistributionName = distributionName
}

if(project.hasProperty("buildVersion") ) {
    zerocodeVersion = project.properties["buildVersion"] as String
}

publishing {
    publications {
        create<MavenPublication>(distributionName) {
            groupId = "zerocode"
            artifactId = distributionName
            version = "$baseVersion-$zerocodeVersion"
            artifact {
                file("$buildDir/gradle-dist/$customGradleDistFilename.zip")
            }
        }
    }
}