package hudson.plugins.sctmexecutor;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import hudson.model.Cause;
import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.Result;
import hudson.model.StreamBuildListener;
import jenkins.branch.BranchProperty;
import jenkins.branch.BranchSource;
import jenkins.branch.DefaultBranchPropertyStrategy;
import jenkins.model.Jenkins;
import jenkins.model.JenkinsMocker;
import jenkins.model.Jenkins.JenkinsHolder;
import jenkins.plugins.git.GitSCMSource;
import jenkins.plugins.git.GitSampleRepoRule;

public class TestSctmExecutor {

  private static final String FREE_STYLE_JOB = "otherProject";

  private static final String JOB2 = "job";

  private static final String MASTER = "master";

  private static final String WORKFLOW_MULTI_BRANCH_PROJECT = "WorkflowMultiBranchProject";

  private static final String JOB_NAME = WORKFLOW_MULTI_BRANCH_PROJECT + "/" + MASTER;

  @ClassRule
  public static JenkinsRule jenkins = new JenkinsRule();

  @ClassRule
  public static GitSampleRepoRule sampleRepo = new GitSampleRepoRule();

  private static JenkinsMocker mocker = new JenkinsMocker();

  private static WorkflowMultiBranchProject mp;

  private static FreeStyleProject triggeredProject;

  @SuppressWarnings("deprecation")
  @BeforeClass
  public static void before() throws Exception {
    mocker.install(new JenkinsHolder() {

      @Override
      public Jenkins getInstance() {
        return jenkins().getInstance();
      }
    });
    initSampleRepo();

    triggeredProject = jenkins().createFreeStyleProject(FREE_STYLE_JOB);

    createWorkFlowJob();

    createMulitBranchProject();

    assert mp.scheduleBuild();
    jenkins().waitUntilNoActivity();
    WorkflowJob p = mp.getItem(MASTER);
    assert p.getLastBuild().getResult().isBetterOrEqualTo(Result.SUCCESS);
  }

  private static void createMulitBranchProject() throws IOException {
    mp = jenkins().createProject(WorkflowMultiBranchProject.class, WORKFLOW_MULTI_BRANCH_PROJECT);
    mp.getSourcesList().add(new BranchSource(new GitSCMSource(null, sampleRepo.toString(), "", "*", "", false),
        new DefaultBranchPropertyStrategy(new BranchProperty[0])));
  }

  private static void createWorkFlowJob() throws IOException {
    WorkflowJob job = jenkins().createProject(WorkflowJob.class, JOB2);
    job.setDefinition(new CpsFlowDefinition("build(job: '" + FREE_STYLE_JOB + "')"));
  }

  private static JenkinsRule jenkins() {
    return jenkins;
  }

  private static void initSampleRepo() throws Exception, IOException {
    sampleRepo.init();
    sampleRepo.write("Jenkinsfile", "echo \"branch=${env.BRANCH_NAME}\"; build(job: '" + JOB2 + "');");
    sampleRepo.write("file", "initial content");
    sampleRepo.git("add", "Jenkinsfile");
    sampleRepo.git("add", "file");
    sampleRepo.git("commit", "--all", "--message=flow");
  }

  @AfterClass
  public static void after() {
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

  @Test
  public void testGetBranchName2() throws Exception {
    FreeStyleBuild triggeredBuild = triggeredProject.getLastBuild();

    SCTMExecutor sctmExecutor = new SCTMExecutor(0, "");
    sctmExecutor.setUseBranchName(true);
    assertEquals(sctmExecutor.getBranchName(triggeredBuild.getCauses()), MASTER);
  }

  @Test
  public void testGetBranchName3() throws Exception {
    SCTMExecutor sctmExecutor = new SCTMExecutor(0, "");
    assertEquals(sctmExecutor.getBranchName(triggeredProject.getLastBuild().getCauses()), "");
  }

  @Test
  public void testGetBuildNumberFromUpstreamBuild() {
    SCTMExecutor sctmExecutor = new SCTMExecutor(0, "");
    sctmExecutor.setBranchName("exampleBranchName");
    assertEquals(-1,sctmExecutor.getBuildNumberFromUpstreamBuild(triggeredProject.getLastBuild(), createDummyListener()));
    sctmExecutor.setJobName(JOB_NAME);
    sctmExecutor.setUseBranchName(false);
    assertEquals(1,sctmExecutor.getBuildNumberFromUpstreamBuild(triggeredProject.getLastBuild(), createDummyListener()));
  }

  @Test
  public void testGetBuildNumberFromLatestBuild() {
    SCTMExecutor sctmExecutor = new SCTMExecutor(0, "");
    sctmExecutor.setBranchName("exampleBranchName");
    assertEquals(sctmExecutor.getBuildNumberFromLatestBuild(JOB_NAME), 1);
    sctmExecutor.setJobName(JOB_NAME);
    sctmExecutor.setUseBranchName(false);
    assertEquals(sctmExecutor.getBuildNumberFromLatestBuild(JOB_NAME), 1);
  }

  private StreamBuildListener createDummyListener() {
    return new StreamBuildListener(new ByteArrayOutputStream());
  }
}
