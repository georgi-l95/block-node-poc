import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.protobuf

plugins {
    id("com.google.protobuf") version "0.9.4"
    id("java")
}

group = "com.hedera"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java
    implementation("com.google.protobuf:protobuf-java:3.25.2")
// https://mvnrepository.com/artifact/io.grpc/grpc-all
    implementation("io.grpc:grpc-all:1.61.1")
// https://mvnrepository.com/artifact/javax.annotation/javax.annotation-api
    implementation("javax.annotation:javax.annotation-api:1.3.2")
// https://mvnrepository.com/artifact/com.hedera.hashgraph/hedera-protobuf-java-api
    implementation("com.hedera.hashgraph:hedera-protobuf-java-api:0.47.0")
//    api("com.hedera.hashgraph:hedera-protobuf-java-api:0.47.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    protobuf("com.hedera.hashgraph:hedera-protobuf-java-api:0.47.0")
}

sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/grpc")
            srcDirs("build/generated/source/proto/main/java")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.10.1:osx-x86_64"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.25.0:osx-x86_64"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
