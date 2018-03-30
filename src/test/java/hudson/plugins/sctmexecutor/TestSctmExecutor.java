package hudson.plugins.sctmexecutor;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;

import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import hudson.model.Cause;
import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import jenkins.branch.BranchProperty;
import jenkins.branch.BranchSource;
import jenkins.branch.DefaultBranchPropertyStrategy;
import jenkins.model.Jenkins;
import jenkins.model.JenkinsMocker;
import jenkins.model.Jenkins.JenkinsHolder;
import jenkins.plugins.git.GitSCMSource;
import jenkins.plugins.git.GitSampleRepoRule;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.SCMSource;

public class TestSctmExecutor {

  @Rule
  public JenkinsRule r = new JenkinsRule();

  @Rule
  public GitSampleRepoRule sampleRepo = new GitSampleRepoRule();

  private JenkinsMocker mocker = new JenkinsMocker();

  @Before
  public void before() {
    mocker.install(new JenkinsHolder() {

      @Override
      public Jenkins getInstance() {
        return r.getInstance();
      }
    });
  }

  @After
  public void after() {
    mocker.uninstall();
  }

  @Test
  public void testConstruction() {
    new SCTMExecutor(0, "");
  }

  @Test
  public void testGetBranchName1() {
    String branchName = "exampleBranchName";
    SCTMExecutor sctmExecutor = new SCTMExecutor(0, "");
    sctmExecutor.setBranchName(branchName);
    assertEquals(sctmExecutor.getBranchName(Collections.<Cause> emptyList()), branchName);
  }

//  @SuppressWarnings("deprecation")
//  @Test
//  public void testGetBranchName2() throws Exception {
//
//    WorkflowMultiBranchProject mp = r.createProject(WorkflowMultiBranchProject.class, "WorkflowMultiBranchProject");
//    FreeStyleProject otherProject = r.createFreeStyleProject("otherProject");
//    initSampleRepo();
//    mp.getSourcesList().add(new BranchSource(new GitSCMSource(null, sampleRepo.toString(), "", "*", "", false),
//        new DefaultBranchPropertyStrategy(new BranchProperty[0])));
//    mp.scheduleBuild();
//    r.waitUntilNoActivity();
//    WorkflowJob item = mp.getItem("master");
//    FreeStyleBuild buildByNumber = otherProject.getLastBuild();
//
//    // SCTMExecutor sctmExecutor = new SCTMExecutor(0, "", 0, 0, true, true, true, false, "", "", "", "");
//    // sctmExecutor.useBranchName = true;
//    // assertEquals(sctmExecutor.getBranchName(buildByNumber.getCauses()), "master");
//  }
//
//  private void initSampleRepo() throws Exception, IOException {
//    sampleRepo.init();
//    sampleRepo.write("Jenkinsfile", "echo \"branch=${env.BRANCH_NAME}\"; build(job: 'otherProject');");
//    sampleRepo.write("file", "initial content");
//    sampleRepo.git("add", "Jenkinsfile");
//    sampleRepo.git("add", "file");
//    sampleRepo.git("commit", "--all", "--message=flow");
//  }

}
