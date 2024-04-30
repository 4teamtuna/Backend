plugins {
	java
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

tasks.compileJava {
	options.compilerArgs.add("-parameters")
}


repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation ("org.springframework.boot:spring-boot-starter-jdbc")
	compileOnly ("org.projectlombok:lombok")
	runtimeOnly ("com.mysql:mysql-connector-j")
	annotationProcessor ("org.projectlombok:lombok")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
//	implementation ("io.springfox","springfox-swagger2", version = "2.0.2")
	implementation ("org.springdoc","springdoc-openapi-starter-webmvc-ui", version = "2.0.2")
	implementation("org.springframework.boot:spring-boot-starter-security:2.7.3")
	implementation("org.springframework.security:spring-security-oauth2-client")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
	implementation("org.springframework.security:spring-security-test")
	// websocket
	implementation ("org.springframework.boot:spring-boot-starter-websocket")
	implementation ("org.springframework.boot:spring-boot-starter-thymeleaf")

	// sockjs
	implementation ("org.webjars:sockjs-client:1.5.1")
	// stomp
	implementation ("org.webjars:stomp-websocket:2.3.4")
	// gson
	implementation ("com.google.code.gson:gson:2.9.0")

	implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.1.RELEASE")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
