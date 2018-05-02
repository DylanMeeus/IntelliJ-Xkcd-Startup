package xkcdotd;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowManager;

public class Launcher implements ProjectComponent  {

    private final Project project;
    public Launcher(Project p){
        this.project = p;
    }

    @Override
    public void projectOpened() {
        ToolWindowManager.getInstance(project).invokeLater(() -> {
            if (project.isDisposed()) {
                return;
            }
            XkcdDialog.createForProject(project).show();
        });
    }

    @Override
    public void projectClosed() {

    }
}
