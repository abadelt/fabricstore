def templatePath = 'https://raw.githubusercontent.com/abadelt/fabricstore/master/openshift-files/fabricstore.yaml'
def templateName = 'fabricstore'
pipeline {
    agent any
    environment {
        ENVIRONMENT = 'dev'
    }
    options {
        // set a timeout of 5 minutes for this pipeline
        timeout(time: 5, unit: 'MINUTES')
    }

    stages {
        stage('preamble') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject() {
                            echo "Using project: ${openshift.project()}"
                        }
                    }
                }
            }
        }
        stage('cleanup') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject() {
                            // delete everything with this template label
                            openshift.selector("all", [template: templateName]).delete()
                            // delete any secrets with this template label
                            if (openshift.selector("secrets", templateName).exists()) {
                                openshift.selector("secrets", templateName).delete()
                            }
                        }
                    }
                } // script
            } // steps
        } // stage
        stage('create') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject() {
                            // create a new application from the templatePath
                            openshift.newApp(templatePath)
                        }
                    }
                } // script
            } // steps
        } // stage
        stage('build') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject() {
                            def builds = openshift.selector("bc", templateName).related('builds')
                            echo "Builds size is: ${builds.count()}"
                            if (builds.count() == 0) {
                                return false
                            } else {
                                builds.untilEach(1) {
                                    echo "Build status for ${it.name()} is ${it.object().status.phase}"
                                    return (it.object().status.phase == "Complete")
                                }
                            }
                            try {
                                sh 'gradle clean test'
                            } finally {
                                junit 'build/test-results/test/TEST-*.xml'
                            }
                        }
                    }
                } // script
            } // steps
        } // stage
        stage('deploy') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject() {
                            // def rolloutMgr =
                            openshift.selector("dc", templateName).rollout()
                            def deployments = openshift.selector("dc", templateName).related('pods')
                            echo "Deployments size is: ${deployments.count()}"
                            if (deployments.count() == 0) {
                                return false
                            } else {
                                deployments.untilEach(1) {
                                    echo "Deployments status for ${it.name()} is ${it.object().status.phase}"
                                    return (it.object().status.phase == "Running")
                                }
                            }
                        }
                    }
                } // script
            } // steps
        } // stage
        stage('tag') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject() {
                            // if everything else succeeded, tag the ${templateName}:latest image as ${templateName}-staging:latest
                            // a pipeline build config for the staging environment can watch for the ${templateName}-staging:latest
                            // image to change and then deploy it to the staging environment
                            openshift.tag("${templateName}:latest", "${templateName}-staging:latest")
                        }
                    }
                } // script
            } // steps
        } // stage
    } // stages
} // pipeline
