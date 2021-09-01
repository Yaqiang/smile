name := "smile-spark"

// Spark doesn't support 2.13+
scalaVersion := "2.12.14"

libraryDependencies ++= {
  val sparkV = "3.1.2"
  Seq(
    "org.apache.spark" %% "spark-core"  % sparkV % Provided,
    "org.apache.spark" %% "spark-sql"   % sparkV % Provided,
    "org.apache.spark" %% "spark-mllib" % sparkV % Provided,
    "org.bytedeco" % "javacpp"   % "1.5.6"        % "test" classifier "macosx-x86_64" classifier "windows-x86_64" classifier "linux-x86_64",
    "org.bytedeco" % "openblas"  % "0.3.17-1.5.6" % "test" classifier "macosx-x86_64" classifier "windows-x86_64" classifier "linux-x86_64",
    "org.bytedeco" % "arpack-ng" % "3.8.0-1.5.6"  % "test" classifier "macosx-x86_64" classifier "windows-x86_64" classifier "linux-x86_64"
  )
}
