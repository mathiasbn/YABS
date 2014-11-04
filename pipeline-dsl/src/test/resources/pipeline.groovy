pipeline {
    gradle {
        isolatedHomeForEachStage = true
    }

    variants {
        standalone
        regionNord
        regionMidt
        regionSyd
        International
    }

    stage build(triggeredBy: repository.newChanges) {
        preActions {
            wipeWorkspace()
        }

        action {
            gradle build publish
        }

        postActions {
            junit {
                reportDirs = 'build/junit/**/*.xml'
            }
        }

        downstream {
            environment {
                version = repository.version
            }
        }
    }

    stage IntegrationTest(dependsOn: build, variants: fanOut) {
        action {
            gradle.env.version = upstream.version
            gradle.env.vm = config.integrationTestMachine
            gradle.env.integration = variant
            gradle deploy integrationTest
        }

        postActions {
            junit {
                reportDirs = 'build/junit/**/*.xml'
            }
        }
    }




    stage guiTest(dependsOn: standaloneIntegrationTest) {
        action {
            gradle.env.version = upstream.version
            gradle.env.vm = config.guiTestMachine
            gradle deploy guiTest
        }
    }

    integrations.each {
        integration ->
            stage manualTest(dependsOn: "${integration}IntegrationTest", type: manual) {
                input {
                    choice {
                        vm = ['bpj-machine1', 'bpj-machine2']
                    }
                }

                action {
                    gradle.env.version = upstream.version
                    gradle.env.vm = input.vm
                    gradle.env.integration = integration
                    gradle deploy
                }

                approval {
                    choice {
                        type = ['beta', 'RC', 'release']
                    }
                    custom {
                        version = [String]
                        releaseNotes = [String]
                    }

                }
            }
    }

    stage release(dependsOn: manualTest.approval) {
        action {
            gradle.env.version = upstream.approval.version
            gradle.env.releaseNotes = upstream.approval.releaseNotes
            gradle.env.type = upstream.approval.type
            gradle release
        }
    }
}
