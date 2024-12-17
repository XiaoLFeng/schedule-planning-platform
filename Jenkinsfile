node("centos") {
  stage('SCM') {
    // 拉取代码
    checkout scm
  }
  
  stage('SonarQube Analysis - Backend') {
    // Maven 工具的配置，"maven" 是在 Jenkins 全局工具中配置的 Maven 名称
    def mvn = tool 'maven';
    
    // 使用 SonarQube 环境
    withSonarQubeEnv() {
      // 切换到 back-code 目录并执行 Maven 命令
      sh """
        cd back-code
        ${mvn}/bin/mvn clean verify sonar:sonar \
          -Dsonar.projectKey=XiaoLFeng_schedule-planning-platform_backend \
          -Dsonar.projectName='schedule-planning-platform-backend'
      """
    }
  }
  
  stage('SonarQube Analysis - Frontend') {
    // 使用 SonarQube 环境
    withSonarQubeEnv() {
      // 切换到 front-code 目录并执行 SonarQube 分析命令
      sh """
        cd front-code
        npx sonar-scanner \
          -Dsonar.projectKey=XiaoLFeng_schedule-planning-platform_frontend \
          -Dsonar.projectName='schedule-planning-platform-frontend' \
          -Dsonar.sources=src \
          -Dsonar.language=ts \
          -Dsonar.sourceEncoding=UTF-8
      """
    }
  }
}