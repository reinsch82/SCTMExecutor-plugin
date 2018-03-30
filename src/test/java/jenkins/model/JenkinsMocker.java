package jenkins.model;

import jenkins.model.Jenkins.JenkinsHolder;

public class JenkinsMocker {

  private static JenkinsHolder holder;

  public void install(JenkinsHolder jh) {
    holder = Jenkins.HOLDER;
    Jenkins.HOLDER = jh;
  }

  public JenkinsHolder uninstall() {
    Jenkins.HOLDER = holder;
    return holder;

  }

}
