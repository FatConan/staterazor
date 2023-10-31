name := "staterazor"
organization := "de.themonstrouscavalca"
// maintainer := "oss@themonstrouscavalca.de"
version := "2022.8.1-SNAPSHOT"
scalaVersion := "2.13.12"


resolvers ++= Seq(Resolver.mavenLocal,
    "Sonatype snapshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/",
    "Shibboleth releases" at "https://build.shibboleth.net/nexus/content/repositories/releases/",
    "Aspose Releases" at "https://artifact.aspose.com/repo/",
    "Atlassian Releases" at "https://maven.atlassian.com/public/",
    "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"
)

val slf4jVersion = "1.7.36"

libraryDependencies ++= Seq(
    "junit"                         % "junit"                   % "4.13.1"                  % Test,
    "com.novocode"                  % "junit-interface"         % "0.11"                    % Test,
    "org.slf4j"                     % "slf4j-api"               % slf4jVersion,
    "org.slf4j"                     % "slf4j-simple"            % slf4jVersion
)

Test / test / logLevel := util.Level.Error
logBuffered := false
scalacOptions += "-deprecation"