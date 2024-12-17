pipeline {
    agent { label 'centos' }  // 选择带有标签 "centos" 的节点

    environment {
        SONAR_TOKEN = credentials('xiaolfeng-sonar-token')  // 引用 Jenkins Credentials 中的 Token
    }

    tools {
        maven 'maven'  // 配置 Maven 工具，名称是 Jenkins 全局工具配置里的名称
    }

    stages {
        stage('SCM') {
            steps {
                ansiColor('xterm') {
                    echo "拉取代码..."
                    checkout scm  // 拉取代码
                }
            }
        }

        stage('SonarQube Analysis - Backend') {
            steps {
                ansiColor('xterm') {
                    withSonarQubeEnv('SonarScanner') {  // 替换 'SonarQube' 为你配置的服务器名称
                        sh '''
                            cd back-code
                            mvn clean verify sonar:sonar \
                                -Dsonar.projectKey=XiaoLFeng_schedule-planning-platform_backend \
                                -Dsonar.projectName="学生日程规划平台后端" \
                                -Dsonar.token=${SONAR_TOKEN}
                        '''
                    }
                }
            }
        }

        stage('SonarQube Analysis - Frontend') {
            steps {
                ansiColor('xterm') {
                    withSonarQubeEnv('SonarScanner') {  // 替换 'SonarQube' 为你配置的服务器名称
                        sh '''
                            cd front-code
                            npx sonar-scanner \
                                -Dsonar.projectKey=schedule-planning-platform_frontend \
                                -Dsonar.projectName="学生日程规划平台前端" \
                                -Dsonar.token=${SONAR_TOKEN} \
                                -Dsonar.sources=src \
                                -Dsonar.language=ts \
                                -Dsonar.sourceEncoding=UTF-8
                        '''
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'SonarQube 分析完成 🎉'
        }
        failure {
            error(message: 'SonarQube 分析失败，请检查日志！')
        }
    }
}