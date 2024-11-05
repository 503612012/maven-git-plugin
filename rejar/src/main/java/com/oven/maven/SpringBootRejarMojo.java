package com.oven.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.zeroturnaround.zip.ZipUtil;
import org.zeroturnaround.zip.commons.FileUtils;

import java.io.File;

/**
 * 去掉BOOT-INFO中的文件
 *
 * @author Oven
 */
@Mojo(name = "rejar", defaultPhase = LifecyclePhase.PACKAGE)
public class SpringBootRejarMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.directory}", required = true)
    private File outputDirectory;
    @Parameter(defaultValue = "${project.build.finalName}", readonly = true)
    private String finalName;

    @Override
    public void execute() throws MojoExecutionException {
        getLog().info("rejar start");
        try {
            String jarFilePath = this.outputDirectory.getPath() + File.separator + this.finalName + ".jar";
            getLog().info("jarFilePath=" + jarFilePath);
            File jarFile = new File(jarFilePath);
            File jarFileTemp = new File(this.outputDirectory.getPath() + File.separator + "temp");
            getLog().info("Start un jar to temp directory");
            ZipUtil.unpack(jarFile, jarFileTemp);
            getLog().info("Start delete temp BOOT-INF/classes directory");
            FileUtils.deleteDirectory(new File(jarFileTemp.getPath() + File.separator + "BOOT-INF" + File.separator + "classes"));
            getLog().info("move jar to jar.bak");
            FileUtils.moveFile(jarFile, new File(jarFilePath + ".bak"));
            getLog().info("jar temp files");
            ZipUtil.pack(jarFileTemp, new File(this.outputDirectory.getPath() + File.separator + this.finalName + ".jar"));
            getLog().info("Start delete temp directory");
            FileUtils.deleteDirectory(jarFileTemp);
        }catch (Exception e){
            getLog().error(e);
            throw new MojoExecutionException(e.getMessage(), e);
        }
        getLog().info("rejar end");
    }

}
