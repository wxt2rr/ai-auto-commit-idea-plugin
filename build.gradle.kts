// 开发环境
plugins {
    id("java")
    //id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij") version "1.16.1"
}

group = "com.wangxt"
version = "1.0-SNAPSHOT"

// 插件依赖仓库
repositories {
    //mavenCentral()
    maven("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    maven("https://maven.aliyun.com/repository/central/")
    maven("https://maven.aliyun.com/repository/public/")
    maven("https://maven.aliyun.com/repository/google/")
    maven("https://maven.aliyun.com/repository/jcenter/")
    maven("https://maven.aliyun.com/repository/gradle-plugin")
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.openai:openai-java:0.22.0")
}

// 定义了gradle构建时依赖的idea版本，debug调试时就是使用这个版本调试
intellij {
    version.set("2023.3.3")
    type.set("IC") // Target IDE Platform

    // 依赖git插件，因为我们要在git提交时拦截
    plugins.set(listOf("java"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    /*withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }*/

    patchPluginXml {
        sinceBuild.set("231")
        untilBuild.set("241.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
