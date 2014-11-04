pipeline {
    stage build(triggeredBy: repository.newChanges) {
        action {
            gradle build publish
        }

        postActions {
            junit {
                reportDirs = 'build/junit/**/*.xml'
            }
        }
    }
}
