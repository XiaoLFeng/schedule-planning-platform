node {
  stage('SCM') {
    // 拉取代码
    checkout scm
  }
  
  stage('SonarQube Analysis') {
    // Maven 工具的配置，"maven" 是在 Jenkins 全局工具中配置的 Maven 名称
    def mvn = tool 'maven';
    
    // 使用 SonarQube 环境
    withSonarQubeEnv() {
      // 切换到 back-code 目录并执行 Maven 命令
      sh """
        cd back-code
        ${mvn}/bin/mvn clean verify sonar:sonar \
          -Dsonar.projectKey=XiaoLFeng_schedule-planning-platform_af05b4d0-2540-4061-98f9-0ea54ac0f845 \
          -Dsonar.projectName='schedule-planning-platform'
      """
    }
  }
}