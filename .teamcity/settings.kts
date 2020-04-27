import jetbrains.buildServer.configs.kotlin.v2019_2.*

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2019.2"

project {

    buildType(DeployPreProd_1)

    subProject(DeployUat)
    subProject(DeployPreProd)
}

object DeployPreProd_1 : BuildType({
    id("DeployPreProd")
    name = "Deploy - PreProd"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    dependencies {
        snapshot(AbsoluteId("PetClinic_SpringPetclinic2_DeployTest")) {
            synchronizeRevisions = false
        }
        snapshot(AbsoluteId("SpringPetclinic_DeployTest")) {
            synchronizeRevisions = false
        }
        artifacts(AbsoluteId("PetClinic_SpringPetclinic2_Build")) {
            cleanDestination = true
            artifactRules = "**/*.jar"
        }
        artifacts(AbsoluteId("SpringPetclinic_Build")) {
            cleanDestination = true
            artifactRules = "**/*.jar"
        }
    }
})


object DeployPreProd : Project({
    name = "Deploy - PreProd"
})


object DeployUat : Project({
    name = "Deploy - UAT"
})
