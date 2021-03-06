name := """BulletinBoard"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  evolutions,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.scalikejdbc"  %%  "scalikejdbc"  %  "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc-config" % "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.5.1",
  "mysql" % "mysql-connector-java" % "5.1.39",
  "org.webjars" %% "webjars-play" % "2.5.0",
  "org.webjars" % "bootstrap" % "3.2.0"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


