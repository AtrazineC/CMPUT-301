import java.util.ArrayList;

class Q1 {
    /**
     * Class for project tasks
     */
    static class ProjectTask {
        private String title;
        private int estimate;
        private ProjectTask dependency;
        private ArrayList<ProjectMember> assignedTo;
        private boolean done;

        public ProjectTask(String title, int estimate, ProjectTask dependency) {
            this.title = title;
            this.estimate = estimate;
            this.dependency = dependency;
            this.assignedTo = new ArrayList<>();
            this.done = false;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getEstimate() {
            return estimate;
        }

        public void setEstimate(int estimate) {
            this.estimate = estimate;
        }

        public ProjectTask getDependency() {
            return dependency;
        }

        public void setDependency(ProjectTask dependency) {
            this.dependency = dependency;
        }

        public ArrayList<ProjectMember> getAssignees() {
            return assignedTo;
        }

        public void addAssignee(ProjectMember projectMember) {
            assignedTo.add(projectMember);
        }

        public boolean getDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }
    }
    
    /**
     * Class for project members
     */
    static class ProjectMember {
        private String name;
        private ArrayList<ProjectTask> assignedTasks;

        public ProjectMember(String name) {
            this.name = name;
            this.assignedTasks = new ArrayList<>();
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<ProjectTask> getAssignedTasks() {
            return assignedTasks;
        }

        public void assignTask(ProjectTask projectTask) {
            assignedTasks.add(projectTask);
        }
    }

    /**
     * Test code
     */
    public static void main(String[] args) {
        // Make a new task
        ProjectTask newProjectTask = new ProjectTask("Write a compiler", 20, null);
        
        // Make a new team member
        ProjectMember newProjectMember = new ProjectMember("Benjamin Kong");

        // Assign task
        newProjectTask.addAssignee(newProjectMember);
        newProjectMember.assignTask(newProjectTask);

        // Check it was done correctly
        if (newProjectTask.getAssignees().get(0) != newProjectMember) {
            System.err.println("Task wasn't assigned the team member");
        }

        if (newProjectMember.getAssignedTasks().get(0) != newProjectTask) {
            System.err.println("Team member wasn't assigned the task");
        }
    }
}