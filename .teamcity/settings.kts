import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.project as project



version = "2020.2"

project {

    buildType(Build)


    sequential {
        buildType(Build)

    }
}


object Build : BuildType({
    name = "Build"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            goals = "clean compile"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
    }
})
object Package : BuildType({
    name = "package"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            goals = "clean compile"
            runnerArgs = "-Dmaven.test.failure.ignore=true -DskipTest"
        }
    }

    dependencies {
        snapshot(Build) {}
    }
    triggers {
        vcs {
        }
    }
})

